package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.fragment.HomeFragment;
import com.newdjk.doctor.utils.AppUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.ItemView;

import java.io.Serializable;

import butterknife.BindView;

public class AllReportsActivity extends BasicActivity {
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
    @BindView(R.id.picture_record)
    ItemView pictureRecord;
    @BindView(R.id.renewal_record)
    ItemView renewalRecord;
    @BindView(R.id.video_record)
    ItemView videoRecord;
    @BindView(R.id.mFunReport)
    ItemView mFunReport;
    @BindView(R.id.mSecondDiagnose)
    ItemView mSecondDiagnose;
    @BindView(R.id.video_line)
    View videoLine;
    @BindView(R.id.mMianzhen)
    ItemView mMianzhen;
    private int mDoctype;
    private int mStatus;

    @Override
    protected int initViewResId() {
        return R.layout.all_reports;
    }

    @Override
    protected void initView() {
        mDoctype = SpUtils.getInt(Contants.DocType, 0);
        mStatus = SpUtils.getInt(Contants.Status, 0);
        if (mDoctype == 2) {
            pictureRecord.getTvItemLeftText().setText("护理咨询记录");
            videoRecord.setVisibility(View.VISIBLE);
            renewalRecord.setVisibility(View.GONE);
            mSecondDiagnose.setVisibility(View.GONE);
            mMianzhen.setVisibility(View.GONE);
            videoRecord.getTvItemLeftText().setText("远程护理记录");
            pictureRecord.setLeftTextDrawableLeft(getResources().getDrawable(R.drawable.hulizixunjilu), "护理咨询记录");
            videoRecord.setLeftTextDrawableLeft(getResources().getDrawable(R.drawable.yuancheng), "远程护理记录");

        } else if (mDoctype == 1) {

            videoLine.setVisibility(View.VISIBLE);
            videoRecord.setVisibility(View.VISIBLE);

        }
        initBackTitle("全部记录");
    }

    @Override
    protected void initListener() {
        pictureRecord.setOnClickListener(this);
        renewalRecord.setOnClickListener(this);
        videoRecord.setOnClickListener(this);
        mFunReport.setOnClickListener(this);
        mSecondDiagnose.setOnClickListener(this);
        mMianzhen.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.picture_record:
                Intent pictureIntent = new Intent(AllReportsActivity.this, OnlineConsultingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("unReadList", (Serializable) HomeFragment.mPictureUnreadMessageList);
                bundle.putSerializable("allUnReadList", (Serializable) HomeFragment.mAllUnreadMessageList);
                pictureIntent.putExtras(bundle);
                startActivity(pictureIntent);
                break;
            case R.id.renewal_record:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, AllReportsActivity.this);
                } else {
                    Intent renewalIntent = new Intent(AllReportsActivity.this, OnlineRenewalPartyActivity.class);
                    Bundle renewalBundle = new Bundle();
                    renewalBundle.putSerializable("allUnReadList", (Serializable) HomeFragment.mAllUnreadMessageList);
                    renewalBundle.putSerializable("unReadList", (Serializable) HomeFragment.mRenewalUnreadMessageList);
                    renewalIntent.putExtras(renewalBundle);
                    startActivity(renewalIntent);
                }
                break;
            case R.id.video_record:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, AllReportsActivity.this);
                } else {
                    Intent videoIntent = new Intent(AllReportsActivity.this, VideoInterrogationActivity.class);
                    Bundle videoBundle = new Bundle();
                    videoBundle.putSerializable("unReadList", (Serializable) HomeFragment.mVideoUnreadMessageList);

                    videoIntent.putExtras(videoBundle);
                    startActivity(videoIntent);
                }
                break;
            case R.id.mFunReport:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, AllReportsActivity.this);
                } else {
                    //定制服务包
                    Intent medicalServiceIntent = new Intent(this, MedicalServiceActivity.class);
                    medicalServiceIntent.putExtra("type", 1);
                    startActivity(medicalServiceIntent);
                }


                break;

            case R.id.mSecondDiagnose:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, AllReportsActivity.this);
                } else {
                    Intent treatIntent = new Intent(AllReportsActivity.this, TreatActivity.class);
                    startActivity(treatIntent);
                }
                break;

            case R.id.mMianzhen:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, AllReportsActivity.this);
                } else {
                    Intent mianzhenintent = new Intent(AllReportsActivity.this, MianZhenActivity.class);

                    startActivity(mianzhenintent);
                }
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }



}
