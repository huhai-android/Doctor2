package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.login.LoginActivity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.LoginEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.utils.AppUtils;
import com.newdjk.doctor.utils.AuthenticationCommentUtil;
import com.newdjk.doctor.utils.SQLiteUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.StrUtil;
import com.newdjk.doctor.views.LoadDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.org.bjca.sdk.core.kit.BJCASDK;

/**
 * Created by xiangzhihong on 2015/12/10 on 11:33.
 */
public class SplashActivity extends BasicActivity {
    @BindView(R.id.splash_image)
    ImageView splashImage;
    @BindView(R.id.splash_timeCount)
    TextView splashTimeCount;
    @BindView(R.id.step_over)
    Button stepOver;
    private Gson mGson;
    private Handler mHandler = new Handler();
    int retryCount = 1;

    @Override
    protected int initViewResId() {
        return R.layout.activity_splash;
    }

    @Override
    public void init() {
        super.init();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    }

    @Override
    protected void initView() {
        mGson = new Gson();
//        splashImage.setImageResource(R.drawable.lunach);
        new Thread(new cutdown()).start();



    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        //清除数据库
        Log.d("MainActivity", "华为手机SplashActivity");
        try {
            SQLiteUtils.getInstance().deleteAllImData();
        } catch (Exception e) {

        }

    }

    @Override
    protected void otherViewClick(View view) {

    }

    @OnClick(R.id.step_over)
    public void stepOver(View view) {

    }

    class cutdown implements Runnable {
        @Override
        public void run() {
            while (retryCount > 0) {
                retryCount--;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //显示剩余时间
                        splashTimeCount.setText(retryCount + "s");
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (retryCount <= 0) {
                    jumpPage();
                }
            }
        }
    }

    private void jumpPage() {
        MyApplication.mRegistrationId = JPushInterface.getRegistrationID(this);
        boolean flag = SpUtils.getBoolean(Contants.IS_FIRST_USE, true);
        if (flag) {
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
            finish();
        } else {
//            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            String loginJson = SpUtils.getString(Contants.LoginJson);
            if (loginJson != null && !loginJson.equals("")) {
//                MyApplication.token = SpUtils.getString(Contants.Token);
//                toActivity(MainActivity.getAct(mContext));
//                finish();
                // quitLogin();
                String name = SpUtils.getString(Contants.userName);
                String password = SpUtils.getString(Contants.Password);
                int canlogin = SpUtils.getInt(Contants.canLogin, 0);
                Log.d("SplashActivity", "是否能够进入登录界面" + canlogin);
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password) && canlogin == 2) {
                    autoLogin(name, password);
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }

            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }

    }

    private void autoLogin(String name, String inputPassword) {
//        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Access-Client", "APP");
        headMap.put("Access-RegistrationId", MyApplication.mRegistrationId);//极光推送的设备唯一性标识RegistrationID
        headMap.put("Access-Platform", "Android");
        Log.i("LoginActivity", "appId=" + MyApplication.mRegistrationId);
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("Mobile", name);
        bodyMap.put("Type", "2");//登录类型:1验证码，2账号
        bodyMap.put("Code", StrUtil.getString(inputPassword));
        Log.i("MyApplication", "appId=splash-----" + MyApplication.mRegistrationId + "    时间" + System.currentTimeMillis());
        mMyOkhttp.post().url(HttpUrl.DoctorLogin).headers(headMap).params(bodyMap).tag(this).enqueue(new GsonResponseHandler<LoginEntity>() {
            @Override
            public void onSuccess(int statusCode, LoginEntity entituy) {
               // LoadDialog.clear();
                if (entituy.getCode() == 0) {
                    SpUtils.put(Contants.canLogin, 2);
                    String mobile = SpUtils.getString(Contants.userName);
                    if (mobile != null && !mobile.equals(entituy.getData().getData().getMobile())) {
                        BJCASDK.getInstance().clearCert(SplashActivity.this);
                    }
                    myShared(entituy);
                    if (TextUtils.isEmpty(entituy.getData().getData().getDoctorName())) {

                        AppUtils.jumpAuthActivity(SplashActivity.this);
                        finish();
                    } else {
                        int status = entituy.getData().getData().getStatus();
                        AuthenticationCommentUtil.STATUS = status;
                        MyApplication.token = entituy.getData().getToken();
                        CommonMethod.isCanToast = true;
                        toActivity(MainActivity.getAct(mContext));
                        finish();
                    }

                    //  queryDoctorRegImagByDrId(entituy);
                } else {
                    SpUtils.put(Contants.canLogin, 1);
                    toast(entituy.getMessage());
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.e("zdp", "statusCode=" + statusCode + ",errorMsg=" + errorMsg + Thread.currentThread());
                if (statusCode == 1001) {
                    errorMsg = getString(R.string.accountOrPassError);
                } else if (statusCode == 2) {
                    errorMsg = "用户不存在";

                }
                SpUtils.put(Contants.canLogin, 1);
              //  LoadDialog.clear();
                CommonMethod.requestError(statusCode, errorMsg);
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        });
    }


    private void quitLogin() {

        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Access-Client", "APP");
        headMap.put("Access-RegistrationId", MyApplication.mRegistrationId);//极光推送的设备唯一性标识RegistrationID
        headMap.put("Access-Platform", "Android");

        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("Mobile", SpUtils.getString(Contants.userName));
        bodyMap.put("Type", "3");//登录类型:1验证码，2账号
        bodyMap.put("Code", SpUtils.getString(Contants.Token));
        Log.i("LoginActivity", "TOKEN=" + SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.DoctorLogin).headers(headMap).params(bodyMap).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entituy) {
                LoadDialog.clear();
                //Log.i("zdp","data="+entituy.getData());
                if (entituy.getCode() == 0) {
                    Gson gson = new Gson();
                    LoginEntity loginEntity = gson.fromJson(gson.toJson(entituy), LoginEntity.class);
                    MyApplication.token = loginEntity.getData().getToken();
                    int status = loginEntity.getData().getData().getStatus();
                    AuthenticationCommentUtil.STATUS = status;
                    if (TextUtils.isEmpty(loginEntity.getData().getData().getDoctorName())) {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        toActivity(intent);
                    } else {
                        toActivity(MainActivity.getAct(mContext));
                    }
                    finish();
                } else {
                    String userName = SpUtils.getString(Contants.userName);
                    // String password = SpUtils.getString(Contants.Password);
                    Intent loginOutIntent = new Intent(mActivity, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    SpUtils.clear();
                    SpUtils.put(Contants.IS_FIRST_USE, false);
                    SpUtils.put(Contants.userName, userName);
                    //    SpUtils.put(Contants.Password, password);
                    mActivity.startActivity(loginOutIntent);

                    SplashActivity.this.finish();
                    toast(entituy.getMessage());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.e("zdp", "statusCode=" + statusCode + ",errorMsg=" + errorMsg);
                String userName = SpUtils.getString(Contants.userName);
                // String password = SpUtils.getString(Contants.Password);
                Intent loginOutIntent = new Intent(mActivity, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                SpUtils.clear();
                SpUtils.put(Contants.IS_FIRST_USE, false);
                SpUtils.put(Contants.userName, userName);
                //    SpUtils.put(Contants.Password, password);
                mActivity.startActivity(loginOutIntent);
                finish();
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    /**
     * 本地保存
     *
     * @param entituy
     */
    private void myShared(LoginEntity entituy) {
        PrescriptionMessageEntity prescriptionMessageEntity = new PrescriptionMessageEntity();
        prescriptionMessageEntity.setDoctor(entituy);
        String loginJson = mGson.toJson(prescriptionMessageEntity);
        SpUtils.put(Contants.LoginJson, loginJson);
        Log.d("login", "id=" + entituy.toString());
        SpUtils.put(Contants.Id, entituy.getData().getData().getDoctorId());
        SpUtils.put(Contants.OrgName, entituy.getData().getData().getOrgName());
        SpUtils.put(Contants.Name, entituy.getData().getData().getDoctorName());
        SpUtils.put(Contants.Sex, entituy.getData().getData().getSex());
        SpUtils.put(Contants.Token, entituy.getData().getToken());
        SpUtils.put(Contants.Status, entituy.getData().getData().getStatus());
        SpUtils.put(Contants.DocType, entituy.getData().getData().getDrType());
        SpUtils.put(Contants.Position, entituy.getData().getData().getPosition());
        Log.d("22222》》》》》", System.currentTimeMillis() + "");
    }

    private void toLoginActivity() {
        toActivity(LoginActivity.getAct(this));
    }
}
