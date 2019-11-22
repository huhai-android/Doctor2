package com.newdjk.doctor.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterSuccessActivity extends BasicActivity {
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
    @BindView(R.id.next_step)
    TextView nextStep;
    @BindView(R.id.submit_success_tip1)
    TextView submitSuccessTip1;
    @BindView(R.id.iv_them)
    ImageView ivThem;
    @BindView(R.id.tv_config_description)
    TextView tvConfigDescription;
    private String mFlag;

    @Override
    protected int initViewResId() {
        return R.layout.register_success;
    }

    @Override
    protected void initView() {
        nextStep.setOnClickListener(this);
        mFlag = getIntent().getStringExtra("flag");
        if (!TextUtils.isEmpty(mFlag)){
            if (mFlag.equals("0")) {//还未开始认证
                ivThem.setImageDrawable(getResources().getDrawable(R.mipmap.submittedfail));
                submitSuccessTip1.setText("未认证");
                tvConfigDescription.setText("请先填写认证信息，进行资格认证！");
                nextStep.setText("开始认证");
                topTitle.setText("资格认证");
            } else if (mFlag.equals("2")) {//认证失败
                ivThem.setImageDrawable(getResources().getDrawable(R.mipmap.submittedfail));
                submitSuccessTip1.setText("认证失败");
                tvConfigDescription.setText("资格认证失败，请重新完善认证信息！");
                nextStep.setText("重新认证");
                topTitle.setText("资格认证");
            } else if (mFlag.equals("3")) {//提交成功，等待认证
                ivThem.setImageDrawable(getResources().getDrawable(R.mipmap.submittedsuccessfully));
                nextStep.setVisibility(View.VISIBLE);
                topTitle.setText("资格认证");
                submitSuccessTip1.setText("认证中");
                tvConfigDescription.setText("您的资料已经提交成功，请耐心等待审核！");
                nextStep.setText("返回登录");
            }
        }


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
      /*  int docType = SpUtils.getInt(Contants.DocType, 0);
        if (docType == 2) {
            submitSuccessTip1.setText(getString(R.string.nurse) + getString(R.string.submit_success_tip1));
        } else if (docType == 1) {
            submitSuccessTip1.setText(getString(R.string.doctor) + getString(R.string.submit_success_tip1));
        }*/
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.next_step:
              /* EventBus.getDefault().post(new LoginEb(SpUtils.getString(Contants.Mobile), SpUtils.getString(Contants.Password)));
               toActivity(MainActivity.getAct(mContext));*/
                Intent intent = null;
                switch (Integer.valueOf(mFlag)) {
                    case 0:
                        intent = new Intent(RegisterSuccessActivity.this, Authentication1Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(RegisterSuccessActivity.this, Authentication1Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(RegisterSuccessActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        break;

                }

                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == event.getKeyCode()) {
            Intent intent = new Intent(RegisterSuccessActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }
}
