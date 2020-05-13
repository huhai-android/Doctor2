package com.newdjk.doctor.ui.activity;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.service.MyService;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.login.AgreementActivity;
import com.newdjk.doctor.ui.entity.AboutUsEntity;
import com.newdjk.doctor.ui.entity.AppEntity;
import com.newdjk.doctor.utils.AppUpdateUtils;
import com.newdjk.doctor.utils.AppUtils;
import com.newdjk.doctor.utils.HomeUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.SystemUitl;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.GroupButtonDialog;
import com.newdjk.doctor.views.LoadDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.newdjk.doctor.MyApplication.getContext;


public class AboutUsActivity extends BasicActivity {


    @BindView(R.id.top_left)
    ImageView topLeft;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.top_right)
    ImageView topRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.relat_titlebar)
    RelativeLayout relatTitlebar;
    @BindView(R.id.liear_titlebar)
    LinearLayout liearTitlebar;
    @BindView(R.id.mVersion)
    TextView mVersion;
    @BindView(R.id.tv_agreement)
    TextView tv_agreement;
    @BindView(R.id.tv_mail)
    TextView tvMail;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.mBundleID)
    TextView mBundleID;
    @BindView(R.id.tv_update)
    TextView tvUpdate;
    @BindView(R.id.lv_update)
    LinearLayout lvUpdate;
    @BindView(R.id.tv_update_desc)
    TextView tvUpdateDesc;
    @BindView(R.id.tv_privacy)
    TextView tvPrivacy;
    @BindView(R.id.imlogo)
    ImageView imlogo;

    private String[] mAppInfo;
    private final static String TAG = "HomeFragment---2";
    private Dialog mDownloadDialog; // 下载对话框
    private ProgressBar mDownloadProgress; // 进度条
    private TextView mProgressText; // 显示下载数字
    private String mApkUrl = "";
    private int mCurentPercent = 0;
    private static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + "doctor.apk";
    private GroupButtonDialog mDialog;
    private int mMustupdate = 0;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private Notification notification;
    private int channelID = 2;
    private AppEntity appentity;

    @Override
    protected int initViewResId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView() {
        initTitle("关于我们").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getLocalVersion(getContext());
        checkUpdate();
    }


    public void getLocalVersion(Context context) {
        String localVersion;
        try {
            PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            localVersion = packageInfo.versionName;
            int versionCode = AppUtils.getAppVersionCode(this);
            mVersion.setText("当前版本号：V" + localVersion);
            mBundleID.setText("BundleID: " + versionCode);
            Log.d("TAG", "本软件的版本号。。" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initListener() {
        tv_agreement.setOnClickListener(this);
        tvUpdate.setOnClickListener(this);
        mVersion.setOnClickListener(this);
        tvPrivacy.setOnClickListener(this);


        imlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUsActivity.this, ZixunDoctorActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void initData() {
        mAppInfo = SystemUitl.packageCode(this);
        loadInfo();
       /* tvTel.setText(new StringBuffer("客服热线:").append("0755-82569787 "));
        tvMail.setText(new StringBuffer("客服邮箱:").append("kefu@newdjk.com"));*/
        obtainAboutInfo();
    }

    private void obtainAboutInfo() {
        loading(true);
        Log.d(TAG, "obtainAboutInfo: " + HttpUrl.GetAboutInfo);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetAboutInfo).headers(headMap).tag(this).enqueue(new GsonResponseHandler<AboutUsEntity>() {
            @Override
            public void onSuccess(int statusCode, AboutUsEntity response) {
                LoadDialog.clear();
                if (response.getCode() == 0) {
                    tvTel.setText(new StringBuffer("客服热线:").append(response.getData().getMobile()));
                    tvMail.setText(new StringBuffer("客服邮箱:").append(response.getData().getMail()));
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                LoadDialog.clear();
            }
        });
    }

    private void loadInfo() {
        if (mAppInfo != null) {
            if (mAppInfo.length != 0) {
                mVersion.setText("当前版本:v" + mAppInfo[1]);
            }
        }

    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_agreement:
                Intent intent = new Intent(AboutUsActivity.this, AgreementActivity.class);
                intent.putExtra("userInfo", SpUtils.getString(Contants.LoginJson));
                startActivity(intent);
                // toActivity(AgreementActivity.getInten(mContext));
                break;

            case R.id.tv_update:
                checkUpdate2();
                break;


            case R.id.mVersion:
//                Intent areaintent = new Intent(AboutUsActivity.this, ChooseAreaActivity.class);
//                startActivity(areaintent);
                break;

            case R.id.tv_privacy:

                Intent intentPrivacy = new Intent(mContext, PrivacyActivity.class);
                intentPrivacy.putExtra("userInfo", SpUtils.getString(Contants.LoginJson));

                mContext.startActivity(intentPrivacy);
                break;
        }
    }

    private void checkUpdate() {

        HomeUtils.INSTANCE.checkVersion(new HomeUtils.UpdateListener() {
            @Override
            public void success(final AppEntity entity) {
                appentity = entity;
                if (entity.getCode() == 0) {
                    if (entity.getData() != null) {
                        final String apkUrl = entity.getData().getAppPath();
                        int apkCode = Integer.parseInt(entity.getData().getAppVersion());
                        int versionCode = AppUtils.getAppVersionCode(AboutUsActivity.this);
                        mMustupdate = entity.getData().getMustUpdate();
                        Log.d(TAG, apkCode + "  " + versionCode);
                        mApkUrl = apkUrl;
                        if (apkCode > versionCode) {
                            tvUpdateDesc.setText("检测到当前最新版本为" + entity.getData().getReleaseVersion());
                            tvUpdate.setText("立即升级");
                            tvUpdate.setVisibility(View.VISIBLE);
                        } else {
                            tvUpdateDesc.setText("当前版本已经是最新版本");
                            tvUpdate.setVisibility(View.GONE);
                        }
                    }
                }
            }


            @Override
            public void failed(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);

            }
        });
    }

    private void checkUpdate2() {

        HomeUtils.INSTANCE.checkVersion(new HomeUtils.UpdateListener() {
            @Override
            public void success(final AppEntity entity) {
                if (MyService.isDownload) {
                    ToastUtil.setToast("app正在升级中，请稍后");
                    return;
                }
                AppUpdateUtils.getInstance().update(entity, AboutUsActivity.this, AboutUsActivity.this);

            }


            @Override
            public void failed(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);

            }
        });
    }


}
