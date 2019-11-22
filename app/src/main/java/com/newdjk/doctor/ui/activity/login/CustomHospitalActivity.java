package com.newdjk.doctor.ui.activity.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.HospitalEntity;
import com.newdjk.doctor.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class CustomHospitalActivity extends BasicActivity {

    private static final String TAG = "CustomHospitalActivity";
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
    @BindView(R.id.et_hospital)
    EditText etHospital;


    @Override
    protected int initViewResId() {
        return R.layout.activity_custom_hospital;
    }

    @Override
    protected void initView() {
        MyApplication.mActivities.add(this);
        initBackTitle("填写医院").setRightText("保存");
        String customHospital = SpUtils.getString(Contants.customHospital);
        etHospital.setText(customHospital);

    }

    @Override
    protected void initListener() {
        tvRight.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                if (TextUtils.isEmpty(etHospital.getText().toString())) {
                    toast("请输入您所在医院名称！");
                    return;
                }
                SpUtils.put(Contants.customHospital, etHospital.getText().toString());
                HospitalEntity.DataBean.ReturnDataBean dataBean = new HospitalEntity.DataBean.ReturnDataBean();
                dataBean.setHospitalName(etHospital.getText().toString());
                dataBean.setCustom(true);
                EventBus.getDefault().post(dataBean);
                MyApplication.exit();
                break;


        }
    }

}
