package com.newdjk.doctor.ui.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.utils.GlideUtils;
import com.newdjk.doctor.views.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;


public class ExpertAdviceListAdapter extends BaseQuickAdapter<MDTDetailEntity.MDTReportDoctorsBean, BaseViewHolder> {

    private final Activity fragment;
    private Gson mGson;
    private TimerTask mTask2;
    private int count = 0;
    private boolean isScroll = false;
    private ScheduledExecutorService mScheduledExecutorService;
    private long curentTime;
    private final long VALID_TIME = 86400000;
    private boolean ismainDoctor=false;

    public ExpertAdviceListAdapter(@Nullable List<MDTDetailEntity.MDTReportDoctorsBean> data,boolean ismain, Activity context) {
        super(R.layout.expert_report_item, data);
        mGson = new Gson();
        this.fragment = context;
        this.ismainDoctor=ismain;
    }


    @Override
    protected void convert(BaseViewHolder helper, MDTDetailEntity.MDTReportDoctorsBean item) {



        helper.setText(R.id.tv_name, item.getDrName());
        if (TextUtils.isEmpty(item.getLevelDesc())){
            helper.setVisible(R.id.tv_level, false);
        }else {
            helper.setVisible(R.id.tv_level, true);
            helper.setText(R.id.tv_level, item.getLevelDesc());
        }

        helper.setText(R.id.tv_expert, item.getPositionName());

        helper.setText(R.id.tv_hospitical, item.getHospitalName() + "");
        helper.setText(R.id.tv_diseasetype, item.getDepartmentName()+ "");
        if (TextUtils.isEmpty(item.getAdvice())){
            helper.setText(R.id.tv_expert_advice,  "未提交");
        }else {
            helper.setText(R.id.tv_expert_advice, item.getAdvice()+ "");
        }


        //要区分自己是不是主治医生
        if (item.getIsSubmit()==0){
            if (ismainDoctor){
                if (item.getDrId()==MyApplication.mDoctorInfoByIdEntity.getDrId()){
                    helper.setVisible(R.id.accept_text,  false);
                }else {
                    helper.setVisible(R.id.accept_text,  true);
                }

            }else {
                helper.setVisible(R.id.accept_text,  false);
            }

        }else {
            helper.setVisible(R.id.accept_text,  false);
        }

        CircleImageView circleImageView=helper.getView(R.id.civImg);
//        Glide.with(MyApplication.getContext())
//                .load(item.getPicturePath())
//                .placeholder(R.drawable.doctor_default_img)
//                .into(circleImageView);
        GlideUtils.loadDoctorImage(item.getPicturePath(),circleImageView);

        helper.addOnClickListener(R.id.accept_text);

    }

    public static long date2TimeStamp(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void addScrollStatus(boolean b) {
        isScroll = b;
    }

    public void currentTime(long nowTime) {
        this.curentTime = nowTime;
    }


}
