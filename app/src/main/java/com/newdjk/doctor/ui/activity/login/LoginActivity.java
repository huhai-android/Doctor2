package com.newdjk.doctor.ui.activity.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.BuildConfig;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.MainActivity;
import com.newdjk.doctor.ui.activity.PrivacyActivity;
import com.newdjk.doctor.ui.entity.AgreementEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.LoginEb;
import com.newdjk.doctor.ui.entity.LoginEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.AppUtils;
import com.newdjk.doctor.utils.AuthenticationCommentUtil;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.StrUtil;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.MyCheckBox;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.org.bjca.sdk.core.kit.BJCASDK;
import cn.org.bjca.sdk.core.values.EnvType;

import static com.newdjk.doctor.MyApplication.getContext;

/**
 * 登录
 */
public class LoginActivity extends BasicActivity {
    @BindView(R.id.input_user)
    EditText inputUser;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.CheckBox)
    MyCheckBox CheckBox;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.password)
    LinearLayout password;
    @BindView(R.id.tv_privacy)
    TextView tvPrivacy;
    private Gson mGson;
    public static final int REQUEST_CODE = 111;

    public static Intent getAct(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected int initViewResId() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        super.init();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        if (BuildConfig.DEBUG) {
            BJCASDK.getInstance().setServerUrl(EnvType.INTEGRATE);
        } else {
            BJCASDK.getInstance().setServerUrl(EnvType.PUBLIC);
        }

        checkPermission();
        mGson = new Gson();
        EventBus.getDefault().register(this);
        inputUser.setText(StrUtil.getString(SpUtils.getString(Contants.userName)));
    }

    @Override
    protected void initListener() {
        CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                inputPassword.setSelection(inputPassword.length());//将光标移至文字末尾
            }
        });
        tvPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgreementEntity agreementEntity = new AgreementEntity();
                agreementEntity.setDoctor("1");
                String json = new Gson().toJson(agreementEntity);

                Intent intentPrivacy=new Intent(mContext, PrivacyActivity.class);
                intentPrivacy.putExtra("userInfo", json);

                mContext.startActivity(intentPrivacy);
            }
        });
    }

    @Override
    protected void initData() {
        EventBus.getDefault().post(new LoginEb(SpUtils.getString(Contants.userName), SpUtils.getString(Contants.Password)));

    }

    public void getCurrentTime() {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        if (mMyOkhttp == null) {

            initOKhttpClient();
        }
        mMyOkhttp.get().url(HttpUrl.getCurrentTime).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity response) {
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {

            }
        });
    }


    @Override
    protected void otherViewClick(View view) {

    }

    /**
     * 更新界面
     * 注册成功后会把账号密码回传到这里填充数据
     *
     * @param loginEb
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void upDataUI(LoginEb loginEb) {
        inputUser.setText(loginEb.getUserName());
        inputPassword.setText(loginEb.getPwd());
    }

    /**
     * 登录
     */
    @OnClick(R.id.btn_login)
    public void onBtnLoginClicked() {
//        Access-Client 是 string app app端固定填写"APP"
//        注：此参数固定放在请求头里面
//        Access-RegistrationId 是 string zUxODI5ND 极光推送的设备唯一性标识RegistrationID
//        注：此参数固定放在请求头里面
//        Access-Platform 是 string Android APP平台 安卓固定填写"Android",苹果固定填写"IOS"
//        注：此参数固定放在请求头里面
//        Mobile 是 string 17773930653 手机号(登陆方式为账号时为账号)
//        Type 是 string 2 登录类型:1验证码，2账号
//        Code 是 string 123456 账号方式时为“密码”，验证码方式时为“验证码”

        if (isNullPwd()) {
            loading(true);
            Map<String, String> headMap = new HashMap<>();
            headMap.put("Access-Client", "APP");
            headMap.put("Access-RegistrationId", MyApplication.mRegistrationId);//极光推送的设备唯一性标识RegistrationID
            headMap.put("Access-Platform", "Android");
            Log.i("LoginActivity", "appId=" + MyApplication.mRegistrationId);
            Map<String, String> bodyMap = new HashMap<>();
            bodyMap.put("Mobile", StrUtil.getString(inputUser));
            bodyMap.put("Type", "2");//登录类型:1验证码，2账号
            bodyMap.put("Code", StrUtil.getString(inputPassword));
            Log.i("MyApplication", "appId=login-----" + MyApplication.mRegistrationId + "    时间" + System.currentTimeMillis());
            mMyOkhttp.post().url(HttpUrl.DoctorLogin).headers(headMap).params(bodyMap).tag(this).enqueue(new GsonResponseHandler<LoginEntity>() {
                @Override
                public void onSuccess(int statusCode, LoginEntity entituy) {
                    LoadDialog.clear();
                    if (entituy.getCode() == 0) {
                        SpUtils.put(Contants.canLogin, 2);
                        String mobile = SpUtils.getString(Contants.userName);
                        if (mobile != null && !mobile.equals(entituy.getData().getData().getMobile())) {
                            BJCASDK.getInstance().clearCert(LoginActivity.this);
                        }
                        myShared(entituy);
                        if (TextUtils.isEmpty(entituy.getData().getData().getDoctorName())) {
                            AppUtils.jumpAuthActivity(LoginActivity.this);
                        } else {
                            int status = entituy.getData().getData().getStatus();
                            AuthenticationCommentUtil.STATUS = status;
                            MyApplication.token = entituy.getData().getToken();
                            CommonMethod.isCanToast = true;
                            toActivity(MainActivity.getAct(mContext));
                            finish();
                        }

                        //  queryDoctorRegImagByDrId(entituy);
                    }
                    toast(entituy.getMessage());
                }

                @Override
                public void onFailures(int statusCode, String errorMsg) {
                    Log.e("zdp", "statusCode=" + statusCode + ",errorMsg=" + errorMsg + Thread.currentThread());
                    if (statusCode == 1001) {
                        errorMsg = getString(R.string.accountOrPassError);
                    }
                    LoadDialog.clear();
                    CommonMethod.requestError(statusCode, errorMsg);
                }
            });
        }

    }

    /**
     * 激光推送注册
     */
    private void quitLogin() {
        if (isNullPwd()) {
            loading(true);
            Map<String, String> headMap = new HashMap<>();
            headMap.put("Access-Client", "APP");
            headMap.put("Access-RegistrationId", MyApplication.mRegistrationId);//极光推送的设备唯一性标识RegistrationID
            headMap.put("Access-Platform", "Android");

            Map<String, String> bodyMap = new HashMap<>();
            bodyMap.put("Mobile", StrUtil.getString(inputUser));
            bodyMap.put("Type", "2");//登录类型:1验证码，2账号
            bodyMap.put("Code", StrUtil.getString(inputPassword));
            Log.i("LoginActivity", "appId=" + MyApplication.mRegistrationId);
            mMyOkhttp.post().url(HttpUrl.DoctorLogin).headers(headMap).params(bodyMap).tag(this).enqueue(new GsonResponseHandler<LoginEntity>() {
                @Override
                public void onSuccess(int statusCode, LoginEntity entituy) {
                    LoadDialog.clear();
                    if (entituy.getCode() == 0) {
                        queryDoctorRegImagByDrId(entituy);
                    }
                    toast(entituy.getMessage());
                }

                @Override
                public void onFailures(int statusCode, String errorMsg) {
                    Log.e("zdp", "statusCode=" + statusCode + ",errorMsg=" + errorMsg);
                    CommonMethod.requestError(statusCode, errorMsg);
                }
            });
        }
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
        SpUtils.put(Contants.Password, inputPassword.getText().toString());
        SpUtils.put(Contants.userName, StrUtil.getString(inputUser));
        SpUtils.put(Contants.Status, entituy.getData().getData().getStatus());
        SpUtils.put(Contants.DocType, entituy.getData().getData().getDrType());
        SpUtils.put(Contants.Position, entituy.getData().getData().getPosition());

        Log.d("22222》》》》》", System.currentTimeMillis() + "");
    }


    /**
     * 密码登录校验
     *
     * @return
     */
    private boolean isNullPwd() {
        if (TextUtils.isEmpty(StrUtil.getString(inputUser))) {
            toast(getResources().getString(R.string.enter_mobile_empty));
            return false;
        }
        if (!StrUtil.isMobile(StrUtil.getString(inputUser))) {
            toast(getResources().getString(R.string.mobile_empty));
            return false;
        }
        if (!StrUtil.isNotEmpty(inputPassword, true)) {
            toast(getResources().getString(R.string.password_empty));
            return false;
        }
        if (StrUtil.getString(inputPassword).length() < 6) {
            toast(getResources().getString(R.string.password_length));
            return false;
        }
        return true;
    }

    /**
     * 忘记密码
     */
    @OnClick(R.id.tv_forget)
    public void onTvForgetClicked() {
        toActivity(ForgetActivity.getIntent(this));
    }

    /**
     * 注册
     */
    @OnClick(R.id.tv_register)
    public void onTvRegisterClicked() {

        toActivity(RegisterActivity.getAct(this));
    }


    private void queryDoctorRegImagByDrId(final LoginEntity loginEntity) {
        String url = HttpUrl.IsUploadDoctorRegImag + "?DrId=" + loginEntity.getData().getData().getDoctorId();
        Log.i("LoginActivity", "url=" + url);
        mMyOkhttp.get().url(url).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {


                if (entity.getCode() == 0) {
                    boolean certification = (Boolean) entity.getData();
                    Log.i("LoginActivity", "certification=" + certification);
                    if (certification) {
                        myShared(loginEntity);
                        toActivity(MainActivity.getAct(mContext));
                        //finish();
                    } else {
                        toast("请先通过资质认证");
                        Intent intent = new Intent(LoginActivity.this, Authentication2Activity.class);
                        startActivity(intent);
                    }
                } else {
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("zdp", "statusCode=" + statusCode + ",errorMsg=" + errorMsg);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCurrentTime();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkPermission() {

        List<String> permissions = new ArrayList<>();
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)) {
            permissions.add(Manifest.permission.CAMERA);
        }

        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        int len = permissions.size();
        if (len != 0) {
            String[] per = new String[len];
            for (int i = 0; i < len; i++) {
                per[i] = permissions.get(i);
            }
            requestPermissions(
                    per,
                    REQUEST_CODE);
            return false;
        }
        return true;

    }


}
