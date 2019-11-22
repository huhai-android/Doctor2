package com.newdjk.doctor.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.AppEntity;
import com.newdjk.doctor.views.AppUpdateDialog;
import com.newdjk.doctor.views.DialogProgress;
import com.tencent.qalsdk.util.ALog;

import org.greenrobot.eventbus.EventBus;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.utils
 *  @文件名:   AppUpdateUtils
 *  @创建者:   huhai
 *  @创建时间:  2019/3/4 10:00
 *  @描述：
 */
public class AppUpdateUtils {


    private static final String TAG = "AppUpdateUtils";
    public static AppUpdateUtils mAppLicationUtils;
    private Context mContext;
    private AppEntity appentity;
    private int mMustupdate = 0;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private Notification notification;
    private int channelID = 1;
    private Dialog mDownloadDialog; // 下载对话框
    private ProgressBar mDownloadProgress; // 进度条
    private TextView mProgressText; // 显示下载数字
    private String mApkUrl = "";
    private int mCurentPercent = 0;
    private static String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + "doctor.apk";
    private AppUpdateDialog mDialog;
    private DialogProgress mProgress;

    public void update(final AppEntity entity, Context context, final Activity activity) {
        mContext = context;
        appentity = entity;
        if (entity.getCode() == 0) {
            if (entity.getData() != null) {
                final String apkUrl = entity.getData().getAppPath();
                int apkCode = Integer.parseInt(entity.getData().getAppVersion());
                int versionCode = AppUtils.getAppVersionCode(mContext);
                mMustupdate = entity.getData().getMustUpdate();
                Log.d(TAG, apkCode + "  " + versionCode);
                if (apkCode > versionCode) {
                    if (mDialog == null) {
                        mDialog = new AppUpdateDialog(mContext, mMustupdate);
                    }

                    Log.d(TAG, "3333");
                    try {
                        mDialog.show(mContext.getResources().getString(R.string.versionUpdate), entity.getData().getRemark() == null ? mContext.getString(R.string.findNewVersion) : entity.getData().getRemark(), new AppUpdateDialog.DialogListener() {
                            @Override
                            public void cancel() {
                                mDialog = null;
                                if (entity.getData().getMustUpdate() == 1) {
                                    activity.finish();
                                }
                            }

                            @Override
                            public void confirm() {
                                   /* UpdateManage updateManage = new UpdateManage(getContext(), apkUrl);
                                    updateManage.showDownloadDialog();*/
                                mDialog = null;
                                if (!NetworkUtil.isNetworkAvailable(mContext)) {
                                    ALog.w(TAG, "下载任务失败，网络未连接");
                                    ToastUtil.setToast("网络连接失败，请检查网络");
                                    return;
                                }
                                mApkUrl = apkUrl;
                                if (TextUtils.isEmpty(mApkUrl)) {
                                    return;
                                }
                                if (mMustupdate == 0) {
                                    //无需强制升级
                                    //  initNotification();
                                    EventBus.getDefault().post(entity);
                                } else {
                                    //必须强制升级
                                    startDownload();
                                }

                            }
                        });
                    } catch (Exception ee) {

                    }
                }
            }
        }

    }


    private void startDownload() {
        FileDownloader.getImpl().setMaxNetworkThreadCount(3);
        FileDownloader.getImpl().create(mApkUrl).setWifiRequired(false).setPath(DOWNLOAD_PATH).setListener(new FileDownloadListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                showProgressDialog();
            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                int p = (int) ((double) soFarBytes / (double) totalBytes * 100);
                Log.d(TAG, "正在下载" + p);
                mCurentPercent = p;
                mDownloadProgress.setProgress(p);
                mProgressText.setText(p + "%");

            }

            @Override
            protected void blockComplete(BaseDownloadTask task) {


            }

            @Override
            protected void completed(BaseDownloadTask task) {
                Log.d(TAG, "下载完成");
                if (mDownloadDialog != null) {
                    mDownloadDialog.dismiss();
                    mDownloadDialog = null;
                }

                InstallApkUtil installApkUtil = new InstallApkUtil();
                installApkUtil.installApk(mContext, DOWNLOAD_PATH);

            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {

                Log.d(TAG, "下载失败");
                if (mDownloadDialog != null) {
                    mDownloadDialog.dismiss();
                    mDownloadDialog = null;
                }
                if (builder != null) {
                    builder.setContentTitle("下载失败");
                    notification = builder.build();
                    notificationManager.notify(channelID, notification);
                }

                if (mDialog == null) {
                    mDialog = new AppUpdateDialog(mContext, mMustupdate);
                }
                mDialog.show("下载失败", "下载失败，是否重新下载？", new AppUpdateDialog.DialogListener() {
                    @Override
                    public void cancel() {
                        mDialog = null;
                    }

                    @Override
                    public void confirm() {
                                   /* UpdateManage updateManage = new UpdateManage(getContext(), apkUrl);
                                    updateManage.showDownloadDialog();*/
                        mDialog = null;
                        if (TextUtils.isEmpty(mApkUrl)) {
                            return;
                        }
                        Log.d(TAG, "重新下载");
                        if (!NetworkUtil.isNetworkAvailable(mContext)) {
                            ALog.w(TAG, "恢复任务失败，网络未连接");
                            ToastUtil.setToast("网络连接失败，请检查网络");
                            return;
                        }
                        startDownload();

                    }
                });
            }

            @Override
            protected void warn(BaseDownloadTask task) {


            }
        }).start();


    }


    private void showProgressDialog() {
        // 构造软件下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.loadDialog);
        builder.setTitle(R.string.download);

        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        mDownloadDialog.setCancelable(false);
        mDownloadDialog.setCanceledOnTouchOutside(false);

        Window window = mDownloadDialog.getWindow();
        window.setContentView(R.layout.softupdate_progress);
        mDownloadProgress = window.findViewById(R.id.update_progress);
        mProgressText = window.findViewById(R.id.tv_Progresstext);
        WindowManager.LayoutParams params = mDownloadDialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        mDownloadProgress.setProgress(mCurentPercent);
        mDownloadDialog.getWindow().setAttributes(params);
    }


    public static AppUpdateUtils getInstance() {
        if (mAppLicationUtils == null) {
            return mAppLicationUtils = new AppUpdateUtils();
        }
        return mAppLicationUtils;


    }
    
    
    
}
