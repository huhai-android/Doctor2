package com.newdjk.doctor.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxq.okhttp.response.DownloadResponseHandler;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.BuildConfig;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.service.MyService;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.Mdt.MDTActivity;
import com.newdjk.doctor.ui.activity.login.ForgetActivity;
import com.newdjk.doctor.ui.activity.login.LoginActivity;
import com.newdjk.doctor.ui.activity.min.SuggestionActivity;
import com.newdjk.doctor.ui.entity.AppEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.utils.CheckUpdateVersion;
import com.newdjk.doctor.utils.CleanMessageUtil;
import com.newdjk.doctor.utils.LogUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.SystemUitl;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.GroupButtonDialog;
import com.newdjk.doctor.views.LoadDialog;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class SystemSettingActivity extends BasicActivity {
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
    @BindView(R.id.clear_cache)
    RelativeLayout clearCache;
    @BindView(R.id.mSuggesstion)
    RelativeLayout mSuggesstion;
    @BindView(R.id.change_password)
    RelativeLayout changePassword;
    @BindView(R.id.login_out)
    TextView loginOut;
    @BindView(R.id.about_our)
    RelativeLayout aboutOur;
    @BindView(R.id.mCacheCount)
    TextView mCacheCount;
    @BindView(R.id.rl_update_version)
    RelativeLayout rlUpdateVersion;
    @BindView(R.id.iv_switch_button)
    ImageView ivSwitchButton;
    @BindView(R.id.re_status)
    RelativeLayout reStatus;
    @BindView(R.id.custom_desktop)
    RelativeLayout customDesktop;
    @BindView(R.id.mdt_test)
    RelativeLayout mdtTest;
    private String totalCacheSize;
    private int mIsOnLine;
    private final static String TAG = "sys";
    private GroupButtonDialog mDialog;
    private int mDoctype;
    private static final int LOGIN_OUT = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOGIN_OUT:

                    loginOut(SystemSettingActivity.this, true);
                    break;

            }
        }
    };

    @Override
    protected int initViewResId() {
        return R.layout.system_setting;
    }

    @Override
    protected void initView() {
        initBackTitle("系统设置");
    }

    @Override
    protected void initListener() {
        changePassword.setOnClickListener(this);
        loginOut.setOnClickListener(this);
        mSuggesstion.setOnClickListener(this);
        clearCache.setOnClickListener(this);
        aboutOur.setOnClickListener(this);
        rlUpdateVersion.setOnClickListener(this);
        ivSwitchButton.setOnClickListener(this);
        customDesktop.setOnClickListener(this);
        mdtTest.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (MyApplication.mDoctorInfoByIdEntity != null) {
            mIsOnLine = MyApplication.mDoctorInfoByIdEntity.getIsOnline();
        }
        mDoctype = SpUtils.getInt(Contants.DocType, 0);

        Log.e("tag", "userEventBus: " + mIsOnLine);
        setOnLineStatus(mIsOnLine);
        //获取缓存数据
        getCache();
        //显示缓存数据的大小
        mCacheCount.setText(totalCacheSize);
        mDialog = new GroupButtonDialog(this);
        if (MyApplication.mDoctorInfoByIdEntity != null) {
            if (MyApplication.mDoctorInfoByIdEntity.getDrType() == 1) {
                reStatus.setVisibility(View.VISIBLE);
            }
        }

        if (mDoctype == 1) {
            customDesktop.setVisibility(View.VISIBLE);
        }


        if (!BuildConfig.IS_DEBUG){
            mdtTest.setVisibility(View.GONE);
        }
    }


    private String getCache() {
        try {
            totalCacheSize = CleanMessageUtil.getTotalCacheSize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalCacheSize;

    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.change_password:
                Intent changPasswordIntent = new Intent(SystemSettingActivity.this, ForgetActivity.class);
                changPasswordIntent.putExtra("title", R.string.update_pass);
                startActivity(changPasswordIntent);
                break;
            case R.id.custom_desktop:
                Intent customDesktopIntent = new Intent(SystemSettingActivity.this, AllApplicationActivity.class);
                startActivity(customDesktopIntent);
                break;
            case R.id.login_out:
                CommonMethod.mIsCanStartService = false;
              /*  Intent stopServiceIntent = new Intent(SystemSettingActivity.this, MyService.class);
                stopService(stopServiceIntent);*/
                Logout();
                mHandler.sendEmptyMessage(LOGIN_OUT);
                break;
            case R.id.mSuggesstion:
                Intent feedbackIntent = new Intent(SystemSettingActivity.this, SuggestionActivity.class);
                startActivity(feedbackIntent);
                break;
            case R.id.clear_cache:
                CleanMessageUtil.clearAllCache(this);
                ToastUtil.setToast("已经清除了" + totalCacheSize);
                mCacheCount.setText(getCache());
                totalCacheSize = getCache();
                break;
            case R.id.about_our:
                Intent intent = new Intent(SystemSettingActivity.this, AboutUsActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_update_version:

                break;

            case R.id.iv_switch_button:
                if (mIsOnLine == 1) {
                    if (mDialog == null) {
                        mDialog = new GroupButtonDialog(SystemSettingActivity.this);
                    }
                    mDialog.show("", "", new GroupButtonDialog.DialogListener() {
                        @Override
                        public void cancel() {
                            mDialog = null;
                        }

                        @Override
                        public void confirm() {
                            mDialog = null;
                            updateOnline(0);
                        }
                    });
                } else {
                    updateOnline(1);
                }
                break;

            case R.id.mdt_test:
                Intent mdtintent = new Intent(SystemSettingActivity.this, MDTActivity.class);
                startActivity(mdtintent);

                break;

        }
    }

    private void checkAppInfo() {
        CheckUpdateVersion.getInstance().doHttpCheckUpdate(mMyOkhttp, this, new CheckUpdateVersion.CheckAppInfoListener() {
            @Override
            public void success(AppEntity entity) {
                if (entity.getCode() == 0) {

                    String[] code = SystemUitl.packageCode(SystemSettingActivity.this);
                    String serviceCode = entity.getData().getAppVersion();
                    final String url = entity.getData().getAppPath();
                    boolean flag = Integer.parseInt(serviceCode.substring(0, 1)) > Integer.parseInt(code[0]);
                    if (flag) {
                        mDialog.show(getString(R.string.versionUpdate), getString(R.string.findNewVersionContent), new GroupButtonDialog.DialogListener() {
                            @Override
                            public void cancel() {
                                Map<String, String> headMap = new HashMap<>();
                                headMap.put("Authorization", SpUtils.getString(Contants.Token));
                                mMyOkhttp.download().url(url).headers(headMap).filePath(Environment.getExternalStorageDirectory() + "xqd/芸医生.apk").tag(this).enqueue(new DownloadResponseHandler() {
                                    @Override
                                    public void onFinish(File downloadFile) {
                                        Log.d(TAG, "onFinish: " + "下载完成");
                                    }

                                    @Override
                                    public void onProgress(long currentBytes, long totalBytes) {
                                        Log.d(TAG, "onProgress: " + currentBytes + "   " + totalBytes);
                                    }

                                    @Override
                                    public void onFailure(String error_msg) {
                                        Log.d(TAG, "onFailure: " + "下载失败");
                                    }
                                });
                            }

                            @Override
                            public void confirm() {

                            }
                        });
                    }
                } else {
                    toast(getString(R.string.checkVersionFailed));
                }
            }

            @Override
            public void failed(String errorMsg) {

            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

//    private void Logout() {
//        mHandler.sendEmptyMessageDelayed(LOGIN_OUT, 2000);
////        Map<String, String> map = new HashMap<>();
////        map.put("Token", SpUtils.getString(Contants.Token));
////        Map<String, String> headMap = new HashMap<>();
////        headMap.put("Authorization", SpUtils.getString(Contants.Token));
////        mMyOkhttp.post().url(HttpUrl.Logout).headers(headMap).tag(this).enqueue(new GsonResponseHandler<Entity>() {
////            @Override
////            public void onSuccess(int statusCode, Entity entituy) {
////
////                if (entituy.getCode() == 0) {
////                    SpUtils.put(Contants.canLogin, 1);
////                    boolean result = (boolean) entituy.getData();
////                    if (result) {
////                        try {
////
////                            loginOut(SystemSettingActivity.this, true);
////
////                        } catch (Exception e) {
////
////                        }
////                    }
////                } else {
////                    toast(entituy.getMessage());
////                    try {
////                        LogOutUtil.getInstance().loginOut(SystemSettingActivity.this, true);
////
////                    } catch (Exception e) {
////
////                    }
////                }
////            }
////
////            @Override
////            public void onFailures(int statusCode, String errorMsg) {
////                LogOutUtil.getInstance().loginOut(SystemSettingActivity.this, true);
////            }
////        });
//    }


    private void updateOnline(final int flag) {
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        params.put("IsOnline", String.valueOf(flag));
        mMyOkhttp.get().url(HttpUrl.UpdateDoctorOnline).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity response) {
                LoadDialog.clear();
                if (response.getCode() == 0) {
                    if (flag == 1) {
                        mIsOnLine = 1;
                        setOnLineStatus(1);
                        MyApplication.mDoctorInfoByIdEntity.setIsOnline(1);
                    } else {
                        mIsOnLine = 0;
                        setOnLineStatus(0);
                        MyApplication.mDoctorInfoByIdEntity.setIsOnline(0);
                    }

                } else {
                    toast(response.getMessage());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                LoadDialog.clear();
                toast(errorMsg);
            }
        });
    }

    private void setOnLineStatus(int flag) {
        if (flag == 1) {
            ivSwitchButton.setImageResource(R.mipmap.ic_switch_open);

        } else {
            ivSwitchButton.setImageResource(R.mipmap.ic_switch_off);
        }

    }

    public void loginOut(BasicActivity activity, boolean isLogin) {
        mActivity = activity;
        if (mActivity != null) {
            activity.loading(true);
            if (isLogin) {
                //   TIMManager.getInstance().logout(this);
                Intent intent = new Intent(mActivity, MyService.class);
                mActivity.stopService(intent);
                iLiveLogout();
                clearAllAndJump();
            } else {
                clearAllAndJump();
            }
        } else {
            LogUtils.e("no activity result");

        }

    }

    public void iLiveLogout() {
        //TODO 新方式登出ILiveSDK
        ILiveLoginManager.getInstance().iLiveLogout(new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                Log.i("LogOutUtil", "iLiveLogoutdata=" + data.toString());
                mHandler.removeMessages(LOGIN_OUT);
                clearAllAndJump();

            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                clearAllAndJump();
                mHandler.removeMessages(LOGIN_OUT);
                Log.i("LogOutUtil", "iLiveLogoutonError=" + errCode + ",errMsg=" + errMsg);
            }
        });
    }

    protected void clearAllAndJump() {


        if (mActivity != null) {
            LoadDialog.clear();
            String userName = SpUtils.getString(Contants.userName);
            String password = SpUtils.getString(Contants.Password);
            Intent loginOutIntent = new Intent(mActivity, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            SpUtils.clear();
            SpUtils.put(Contants.IS_FIRST_USE, false);
            SpUtils.put(Contants.userName, userName);
            SpUtils.put(Contants.Password, password);
            mActivity.startActivity(loginOutIntent);
        }

    }


}
