package com.newdjk.doctor.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.AgreementEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.LoginEb;
import com.newdjk.doctor.ui.entity.LoginEntity;
import com.newdjk.doctor.utils.MyCountDownTimer;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.StrUtil;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.MyCheckBox;
import com.newdjk.doctor.views.VerficationCodeView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 注册
 */
public class RegisterActivity extends BasicActivity {
    @BindView(R.id.input_user)
    EditText inputUser;
    @BindView(R.id.input_tx_code)
    EditText inputTxCode;
    @BindView(R.id.vcv_tx_code)
    VerficationCodeView vcvTxCode;
    @BindView(R.id.relate_code_tx)
    RelativeLayout relateCodeTx;
    @BindView(R.id.input_bd_code)
    EditText inputBdCode;
    @BindView(R.id.tv_bd_code)
    TextView tvBdCode;
    @BindView(R.id.relate_code_bd)
    RelativeLayout relateCodeBd;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.CheckBox)
    MyCheckBox CheckBox;
    @BindView(R.id.cb_agreement)
    android.widget.CheckBox cbAgreement;
    private MyCountDownTimer mcdt;
    private int index = 0;//判断发送验证码是否超过2次

    public static Intent getAct(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    @Override
    protected int initViewResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        initBackTitle("注册");
        relateCodeTx.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        CheckBox.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    @Override
    protected void initData() {
        mcdt = new MyCountDownTimer(tvBdCode, 60000, 1000);
    }


    @Override
    protected void otherViewClick(View view) {

    }

    /**
     * 显示密码
     */
    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
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
    };


    /**
     * 用户协议
     */
    @OnClick(R.id.tv_agreement)
    public void onAgreementClicked() {
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setDoctor("1");
        String json = new Gson().toJson(agreementEntity);
        Intent intent = new Intent(RegisterActivity.this, AgreementActivity.class);
        intent.putExtra("userInfo", json);
        startActivity(intent);
        //toActivity(AgreementActivity.getInten(mContext));
    }

    /**
     * 图形验证码
     */
    @OnClick(R.id.vcv_tx_code)
    public void onVcvTxCodeClicked() {
        vcvTxCode.refresh();
    }

    /**
     * 发送验证码
     */
    @OnClick(R.id.tv_bd_code)
    public void onCodeClicked() {
        if (index < 2) {
            requestCode();
        } else {
            relateCodeTx.setVisibility(View.VISIBLE);
            if (vcvTxCode.isEqualsIgnoreCase(StrUtil.getString(inputTxCode))) {
                requestCode();
            }
        }
    }

    /**
     * 发送验证码请求
     */
    public void requestCode() {
        if (StrUtil.isMobile(StrUtil.getString(inputUser))) {
            loading(true);
            Map<String, String> map = new HashMap<>();
            map.put("Mobile", StrUtil.getString(inputUser));
            map.put("MobileCode", "");
            if (mMyOkhttp==null){

                initOKhttpClient();
            }
            mMyOkhttp.post().url(HttpUrl.SendMobileCode).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
                @Override
                public void onSuccess(int statusCode, Entity entity) {
                    Log.i("zdp", "onSuccess=" + statusCode);
                    LoadDialog.clear();
                    if (entity.getCode() == 0) {
                        mcdt.start();
                        index++;
                        toast("发送成功");
                    } else {
                        toast(entity.getMessage());
                    }
                }

                @Override
                public void onFailures(int statusCode, String errorMsg) {
                    Log.i("zdp", "onFailures=" + statusCode);
                    CommonMethod.requestError(statusCode, errorMsg);
                }
            });
        } else {
            toast("手机号码格式不正确");
        }
    }


    /**
     * 注册
     */
    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        //  requstRegister();
        requstVerifyMobileCode();
    }


    /**
     * 验证会员手机验证码(注册验证)
     */
    public void requstVerifyMobileCode() {
        if (!StrUtil.isMobile(StrUtil.getString(inputUser))) {
            toast("手机号码格式不正确");
            return;
        } else if (!StrUtil.isNotEmpty(inputBdCode, true)) {
            toast("验证码不能为空");
            return;
        } else if (!cbAgreement.isChecked()) {
            toast("请确认已阅读用户协议");
            return;
        } else if (!isValidPassword(inputPassword.getText().toString())) {
            toast("密码为8-12位数字或字母");
            return;
        } else {
            requstRegister();
            /*loading(true);
            Map<String, String> map = new HashMap<>();
            map.put("Mobile", StrUtil.getString(inputUser));
            map.put("MobileCode", StrUtil.getString(inputBdCode));
            mMyOkhttp.post().url(HttpUrl.VerifyMobileCode).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
                @Override
                public void onSuccess(int statusCode, Entity entity) {
                    LoadDialog.clear();
                    if (entity.getCode() == 0) {


                    } else {
                        toast(entity.getMessage());
                    }

                }

                @Override
                public void onFailures(int statusCode, String errorMsg) {
                    CommonMethod.requestError(statusCode, errorMsg);
                }
            });*/
        }

    }

    /**
     * 注册请求
     */
    public void requstRegister() {
        loading(true);
        Map<String, String> map = new HashMap<>();
        map.put("Mobile", StrUtil.getString(inputUser));//                手机号
        map.put("Password", StrUtil.getString(inputPassword));//   密码
        map.put("MobileCode", StrUtil.getString(inputBdCode));//
        Log.i("zdp", "url=" + HttpUrl.DoctorRegister + ",mobile=" + StrUtil.getString(inputUser) + ",PatientPassword=" + StrUtil.getString(inputPassword) + "MobileCode=" + StrUtil.getString(inputBdCode));//       验证码
        mMyOkhttp.post().url(HttpUrl.DoctorRegister).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entituy) {
                LoadDialog.clear();
                vcvTxCode.refresh();
                if (entituy.getCode() == 0) {
                    doLoginRequest();
                }else {
                    toast(entituy.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("zdp", "error=" + statusCode + ",errormsg=" + errorMsg);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
        SpUtils.put(Contants.Id, -1);
       /* Intent intent = new Intent(RegisterActivity.this, Authentication1Activity.class);
        intent.putExtra("Mobile", StrUtil.getString(inputUser));
        intent.putExtra("PatientPassword", StrUtil.getString(inputPassword));
        intent.putExtra("MobileCode", StrUtil.getString(inputBdCode));*/

        // startActivity(intent);


    }

    private boolean isValidPassword(String password) {
        return isMatcherFinded("^[a-zA-Z0-9]{6,12}$", password);
    }

    public static boolean isMatcherFinded(String patternStr, CharSequence input) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }


    private void doLoginRequest() {

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Access-Client", "APP");
        headMap.put("Access-RegistrationId", MyApplication.mRegistrationId);//极光推送的设备唯一性标识RegistrationID
        headMap.put("Access-Platform", "Android");
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("Mobile", StrUtil.getString(inputUser));
        bodyMap.put("Type", "2");//登录类型:1验证码，2账号
        bodyMap.put("Code", StrUtil.getString(inputPassword));

        mMyOkhttp.post().url(HttpUrl.DoctorLogin).headers(headMap).params(bodyMap).tag(this).enqueue(new GsonResponseHandler<LoginEntity>() {
            @Override
            public void onSuccess(int statusCode, LoginEntity entituy) {
                LoadDialog.clear();
                Log.e("test", "onSuccess: " + entituy.toString());
                if (entituy.getCode() == 0) {
                    SpUtils.put(Contants.Token, entituy.getData().getToken());
                    SpUtils.put(Contants.Id, entituy.getData().getData().getDoctorId());
                    EventBus.getDefault().post(new LoginEb(StrUtil.getString(inputUser), StrUtil.getString(inputPassword)));
                    //   AuthenticationCommentUtil.getInstance().myShared(entituy);
                    Intent intent = new Intent(RegisterActivity.this, Authentication1Activity.class);
                    intent.putExtra("tag", 1);
                    SpUtils.put(Contants.Password, inputPassword.getText().toString());
                    SpUtils.put(Contants.userName, StrUtil.getString(inputUser));
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.e("zdp", "statusCode=" + statusCode + ",errorMsg=" + errorMsg);
                if (statusCode == 1001) {
                    errorMsg = "账号或密码错误！";
                }
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mcdt.cancel();
    }
}
