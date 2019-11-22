package com.newdjk.doctor.ui.activity.login;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.ui.entity.DoctorAccountMessageEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.MyCountDownTimer;
import com.newdjk.doctor.utils.StrUtil;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.VerficationCodeView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 忘记密码
 */
public class ForgetActivity extends BasicActivity {

    @BindView(R.id.input_user)
    EditText inputUser;

    @BindView(R.id.input_tx_code)
    EditText inputTxCode;
    @BindView(R.id.vcv_tx_code)
    VerficationCodeView vcvTxCode;

    @BindView(R.id.relate_code_tx)
    RelativeLayout relateCodeTx;
    @BindView(R.id.input_bd_code)
    EditText inputCode;
    @BindView(R.id.tv_bd_code)
    TextView tvCode;
    private String mTitle;
    private MyCountDownTimer mcdt;
    private final static String TAG = "forgetPass";

    public static Intent getIntent(Context context) {
        return new Intent(context, ForgetActivity.class);
    }

    @Override
    protected int initViewResId() {
        return R.layout.activity_forget;
    }

    @Override
    protected void initView() {
        mTitle = getIntent().getStringExtra("title");
        if (mTitle != null) {
            initBackTitle(mTitle);
        } else {
            initBackTitle("忘记密码");
        }
        relateCodeTx.setVisibility(View.GONE);
        mcdt = new MyCountDownTimer(tvCode, 60000, 1000);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {

    }


    /**
     * 发送验证码
     */
    @OnClick(R.id.tv_bd_code)
    public void onCodeClicked() {
        requestCode();
    }

    /**
     * 发送验证码请求
     */
    public void requestCode() {
        if (!StrUtil.isMobile(StrUtil.getString(inputUser))) {
            toast("手机号码格式不正确");
        } else {
            loading(true);
            Map<String, String> map = new HashMap<>();
            map.put("Mobile", StrUtil.getString(inputUser));
            map.put("MobileCode", "");
            mMyOkhttp.post().url(HttpUrl.SendMobileCode).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
                @Override
                public void onSuccess(int statusCode, Entity entity) {
                    LoadDialog.clear();
                    if (entity.getCode() == 0) {
                        mcdt.start();

                    }
                    toast("发送成功");
                }

                @Override
                public void onFailures(int statusCode, String errorMsg) {
                    CommonMethod.requestError(statusCode, errorMsg);
                }
            });
        }
    }

    /**
     * 下一步
     */
    @OnClick(R.id.btn_submit)
    public void onBtnSubmitClicked() {
        if (!StrUtil.isMobile(StrUtil.getString(inputUser))) {
            toast("手机号码格式不正确");
            return;
        } else if (!StrUtil.isNotEmpty(inputCode, true)) {
            toast("验证码不能为空");
            return;
        }  else if (inputCode.getText().toString().trim().length() != 4) {
            toast("验证码不对");
        } else {
            loading(true);
            Map<String, String> map = new HashMap<>();
            map.put("Mobile", StrUtil.getString(inputUser));
            map.put("MobileCode", StrUtil.getString(inputCode));
            mMyOkhttp.post().url(HttpUrl.QueryDoctorByMobileCode).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<DoctorAccountMessageEntity>>() {
                @Override
                public void onSuccess(int statusCode, ResponseEntity<DoctorAccountMessageEntity> entity) {
                    LoadDialog.clear();
                    if (entity.getCode() == 0) {
                        toActivity(ResettingActivity.getIntent(mContext, StrUtil.getString(entity.getData().getDrId())));
                        finish();
                    } else {
                        toast(entity.getMessage());
                    }
                }

                @Override
                public void onFailures(int statusCode, String errorMsg) {
                    CommonMethod.requestError(statusCode, errorMsg);
                }
            });
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mcdt != null){
            mcdt.cancel();
        }

    }
}
