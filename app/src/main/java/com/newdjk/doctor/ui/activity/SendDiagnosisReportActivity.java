package com.newdjk.doctor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.ui.entity.UpdateTreatMessageEntity;
import com.newdjk.doctor.views.EmptyRecyclerView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   SecondDiagnosisQuestionActivity
 *  @创建者:   huhai
 *  @创建时间:  2018/12/17 15:34
 *  @描述：
 */
public class SendDiagnosisReportActivity extends BasicActivity {


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
    @BindView(R.id.tv_review_report)
    TextView tvReviewReport;
    private EmptyRecyclerView recyleview;
    private String mMedicalReportPath;
    @Override
    protected int initViewResId() {
        return R.layout.activity_send_diagnosis_report;
    }

    @Override
    protected void initView() {
        mMedicalReportPath = getIntent().getStringExtra("MedicalReportPath");

    }

    @Override
    protected void initListener() {

        tvRight.setOnClickListener(this);
        tvReviewReport.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        initBackTitle("发送报告").setRightText("完成");


    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_review_report:
                Intent systemSettingIntent = new Intent(SendDiagnosisReportActivity.this, ReviewDiagnosisReportActivity.class);
                systemSettingIntent.putExtra("MedicalReportPath",mMedicalReportPath);
                startActivity(systemSettingIntent);
                break;
            case R.id.tv_right:
                finish();
                break;
        }
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SendDiagnosisReportActivity.class);
        return intent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new UpdateTreatMessageEntity("end"));
    }
}
