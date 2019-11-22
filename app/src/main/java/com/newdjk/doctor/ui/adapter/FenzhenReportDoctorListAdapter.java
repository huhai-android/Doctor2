package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.ui.fragment.MDTfenzhenReportFragment1;

import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;


public class FenzhenReportDoctorListAdapter extends BaseQuickAdapter<MDTDetailEntity.MDTReportDoctorsBean, BaseViewHolder> {

    private final Fragment fragment;
    private boolean isMainDoctor;
    private Gson mGson;
    private TimerTask mTask2;
    private int count = 0;
    private boolean isScroll = false;
    private ScheduledExecutorService mScheduledExecutorService;
    private long curentTime;
    private final long VALID_TIME = 86400000;

    public FenzhenReportDoctorListAdapter(@Nullable List<MDTDetailEntity.MDTReportDoctorsBean> data, boolean isMainDoctor, Fragment context) {
        super(R.layout.mdt_fenzhen_doctor_report_item, data);
        mGson = new Gson();
        this.fragment = context;
        this.isMainDoctor = isMainDoctor;

    }


    @Override
    protected void convert(BaseViewHolder helper, final MDTDetailEntity.MDTReportDoctorsBean item) {

        helper.setText(R.id.tv_name, item.getDrName());
        helper.setText(R.id.tv_level, item.getPositionName());
        helper.setText(R.id.tv_hospitical, (TextUtils.isEmpty(item.getHospitalName()) ? "" : item.getHospitalName()) + "  " + (TextUtils.isEmpty(item.getDepartmentName()) ? "" : item.getDepartmentName()));
        // helper.setText(R.id.tv_diseasetype, item.getDepartmentName() + "");
        helper.setText(R.id.tv_time, item.getAdviceTime() + "");
        TextView buttonView = helper.getView(R.id.accept_text);


        //先区分是已完成还是未完成的
        //未完成
        if (fragment instanceof MDTfenzhenReportFragment1) {

            //如果自己是主诊医生
            if (isMainDoctor) {
                //已完成
                if (item.getIsSubmit() == 1) {

                    if (item.getDrId() == MyApplication.mDoctorInfoByIdEntity.getDrId()) {
                        buttonView.setText("编辑专家意见");
                        buttonView.setClickable(true);
                        buttonView.setBackgroundResource(R.drawable.shape_check_document);
                        buttonView.setTextColor(mContext.getResources().getColor(R.color.tianxie));
                        helper.addOnClickListener(R.id.accept_text);
                    } else {
                        buttonView.setText("已完成");
                        buttonView.setClickable(false);
                        buttonView.setTextColor(mContext.getResources().getColor(R.color.yiwancheng));
                    }

                } //未完成
                else {
                    //如果未完成，还要区分是否是自己
                    if (item.getDrId() == MyApplication.mDoctorInfoByIdEntity.getDrId()) {
                        buttonView.setText("填写专家意见");
                        buttonView.setClickable(true);
                        buttonView.setBackgroundResource(R.drawable.shape_check_document);
                        buttonView.setTextColor(mContext.getResources().getColor(R.color.tianxie));
                        helper.addOnClickListener(R.id.accept_text);
                    } else {
                        //如果自己是主诊医生，就显示已完成
                        buttonView.setText("提醒专家填写");
                        buttonView.setClickable(true);
                        buttonView.setBackgroundResource(R.drawable.shape_check_document);
                        buttonView.setTextColor(mContext.getResources().getColor(R.color.tianxie));
                        helper.addOnClickListener(R.id.accept_text);
                    }

                }

                //不是主诊医生，只需要有编辑，已完成，未完成三种状态

            } else {
                //如果是自己已完成的，需要显示编辑
                if (item.getIsSubmit() == 1) {
                    if (item.getDrId() == MyApplication.mDoctorInfoByIdEntity.getDrId()) {
                        buttonView.setText("编辑专家意见");
                        buttonView.setClickable(true);
                        buttonView.setBackgroundResource(R.drawable.shape_check_document);
                        buttonView.setTextColor(mContext.getResources().getColor(R.color.tianxie));
                        helper.addOnClickListener(R.id.accept_text);
                    } else {
                        buttonView.setText("已完成");
                        buttonView.setClickable(false);
                        buttonView.setTextColor(mContext.getResources().getColor(R.color.yiwancheng));
                    }

                    //如果是未完成的，不是自己就需要显示自己填写
                } else {

                    if (item.getDrId() == MyApplication.mDoctorInfoByIdEntity.getDrId()) {
                        buttonView.setText("填写专家意见");
                        buttonView.setClickable(true);
                        buttonView.setBackgroundResource(R.drawable.shape_check_document);
                        buttonView.setTextColor(mContext.getResources().getColor(R.color.tianxie));
                        helper.addOnClickListener(R.id.accept_text);
                    } else {
                        buttonView.setText("未完成");
                        buttonView.setClickable(false);
                        buttonView.setTextColor(mContext.getResources().getColor(R.color.yiwancheng));
                    }
                }
            }

            //已完成列表中，都不需要点击事件
        } else {
            if (item.getIsSubmit() == 1) {
                buttonView.setText("已完成");
                buttonView.setClickable(false);
                buttonView.setTextColor(mContext.getResources().getColor(R.color.yiwancheng));

            } else {
                buttonView.setText("未完成");
                buttonView.setClickable(false);
                buttonView.setBackgroundResource(R.drawable.shape_check_document);
                buttonView.setTextColor(mContext.getResources().getColor(R.color.yiwancheng));
            }
        }




    }


    public void currentTime(long nowTime) {
        this.curentTime = nowTime;
    }
}
