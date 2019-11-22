package com.newdjk.doctor.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.views.ItemView;

import butterknife.BindView;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui
 *  @文件名:   MyCheckCenterActivity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/6 10:12
 *  @描述：
 */
public class FenjiZhuanzhenSettingActivity extends BasicActivity {
    private static final String TAG = "FenjiZhuanzhenSettingActivity";
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
    @BindView(R.id.disease_setting)
    ItemView diseaseSetting;
    @BindView(R.id.fenzu_disease_setting)
    ItemView fenzuDiseaseSetting;


    @Override
    protected int initViewResId() {
        return R.layout.activity_fenjizhuanzhen_setting;
    }

    @Override
    protected void initView() {
        initBackTitle("分级转诊设置");

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


}
