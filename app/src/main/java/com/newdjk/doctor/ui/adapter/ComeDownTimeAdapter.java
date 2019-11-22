package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.utils.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ComeDownTimeAdapter extends BaseQuickAdapter<MDTDetailEntity, BaseViewHolder> {

    private final Fragment fragment;
    private Gson mGson;
    private TimerTask mTask2;
    private int count = 0;
    private boolean isScroll = false;
    private ScheduledExecutorService mScheduledExecutorService;
    private long curentTime;
    private final long VALID_TIME = 86400000;

    public ComeDownTimeAdapter(@Nullable List<MDTDetailEntity> data, Fragment context) {
        super(R.layout.comedown_item, data);
        mGson = new Gson();
        this.fragment = context;
       // startTime();
    }

    private void startTime() {
        mScheduledExecutorService = new ScheduledThreadPoolExecutor(3);
        mScheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                fragment.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        count++;
                        if (!isScroll) {
                            notifyDataSetChanged();
                        }


                    }
                });
            }
        }, 1, 1, TimeUnit.SECONDS);

    }

    @Override
    protected void convert(BaseViewHolder helper, MDTDetailEntity item) {


//        int resttime = item - count;
//        if (resttime <= 0) {
//            helper.setText(R.id.tv_comedown_time, "倒计时 " + 0 + "");
//        } else {
//            helper.setText(R.id.tv_comedown_time, "倒计时 " + resttime + "");
//        }
        String validTime = item.getPayTime();
        long mAcceptTime = date2TimeStamp(validTime, "yyyy-MM-dd HH:mm:ss");
        helper.setText(R.id.tv_name, item.getPatientName());
        helper.setText(R.id.tv_age, item.getPatientAge());
        if (TextUtils.isEmpty( item.getDescription())){
            helper.setText(R.id.tv_patient_desc, "暂无病情描述");
        }else {
            helper.setText(R.id.tv_patient_desc, item.getDescription());
        }

        if (item.getPatientSex()==1){
            helper.setText(R.id.tv_sex, "男");
        }else if (item.getPatientSex()==2){
            helper.setText(R.id.tv_sex,  "女");
        }else {
            helper.setText(R.id.tv_sex, "未知");
        }

        long totaltime = VALID_TIME - curentTime + mAcceptTime;
        long resttime = totaltime - count*1000;
        String time = TimeUtil.formatTime(resttime);
        if (item.getSubjectStatus()==2){
            helper.setVisible(R.id.tv_comedown_time, true);
            helper.setText(R.id.tv_comedown_time,  "已退诊");
        }else {
            helper.setVisible(R.id.tv_comedown_time, false);
        }



        String diseaseName=item.getDiseases();
        String diseaseTime=item.getDiseasesTimeText();
        String seeDoctorText=item.getSeeDoctorText();
        String hospital=item.getSeeDoctor();
        String department=item.getSeeDepartment();
        if (TextUtils.isEmpty(diseaseTime)){
            diseaseTime="";
        }else {
            diseaseTime=diseaseTime+",";
        }

        if (TextUtils.isEmpty(diseaseName)){
            diseaseName="";
        }else {
            diseaseName=diseaseName+",";
        }

        if (TextUtils.isEmpty(seeDoctorText)){
            seeDoctorText="";
        }else {
            seeDoctorText=seeDoctorText+",";
        }

        if (TextUtils.isEmpty(hospital)){
            hospital="";
        }else {
            hospital=hospital+",";
        }
        if (TextUtils.isEmpty(department)){
            department="";
        }
        //组合文字
        helper.setText(R.id.tv_patient_history, diseaseName+diseaseTime+seeDoctorText+hospital+department);



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
