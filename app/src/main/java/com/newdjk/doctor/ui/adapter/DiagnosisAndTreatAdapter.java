package com.newdjk.doctor.ui.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.activity.ReviewDiagnosisReportActivity;
import com.newdjk.doctor.ui.activity.SecondDiagnosisActivity;
import com.newdjk.doctor.ui.activity.SecondDiagnosisQuestionActivity;
import com.newdjk.doctor.ui.activity.SecondDiagnosisUntreatActivity;
import com.newdjk.doctor.ui.entity.DoctorMedicalRecordsEntity;
import com.newdjk.doctor.utils.GlideMediaLoader;
import com.newdjk.doctor.utils.TimeUtil;

import java.util.List;

public class DiagnosisAndTreatAdapter extends BaseQuickAdapter<DoctorMedicalRecordsEntity.ReturnDataBean, BaseViewHolder> {


    private final Gson mGson;
    private int mType;

    public DiagnosisAndTreatAdapter(@Nullable List<DoctorMedicalRecordsEntity.ReturnDataBean> data, int type) {
        super(R.layout.consult_item_second, data);
        mGson = new Gson();
        mType = type;
    }


    @Override
    protected void convert(BaseViewHolder helper, final DoctorMedicalRecordsEntity.ReturnDataBean item) {
        long unReadNum = item.getUnReadNum();
        Log.i("ConsultMessage", "unReadNum=" + unReadNum);
//        try {
            if (unReadNum > 0) {
                helper.setVisible(R.id.consult_unread_num_layout, true);
                helper.setText(R.id.renewal_unread_num, unReadNum + "");
            } else {
                helper.setVisible(R.id.consult_unread_num_layout, false);
            }
            ImageView avatar = helper.getView(R.id.avatar);
            GlideMediaLoader.loadImage(mContext, avatar, item.getPatientPic());
            helper.setText(R.id.create_time, TimeUtil.showTime(item.getPayOrderTime(), "yyyy-MM-dd HH:mm:ss"));

            helper.setText(R.id.patient_name, item.getPatientName());
            helper.setText(R.id.dynamic, item.getDescription());
            String sex = item.getSex();

            helper.setText(R.id.sex, sex);

            helper.setText(R.id.age, item.getAge());
            if (mType == 2) { //已完成
              // helper.setVisible(R.id.deal_status,true);
                if (item.getMedicalStatus() == 5) {
                    helper.setVisible(R.id.im_status,true);
                    helper.setImageResource(R.id.im_status,R.drawable.icon_tuizhen);
                } else{
                    helper.setVisible(R.id.im_status,false);
                }

            } else {
               // helper.setVisible(R.id.deal_status,false);
                helper.setVisible(R.id.im_status,false);
            }
//        } catch (Exception e) {
//            Log.i("ConsultMessageAdapter", "e=" + e.toString());
//            e.printStackTrace();
//        }
        helper.getView(R.id.consult_message_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mType == 0) {
                    Intent SecondDiagnosisIntent = new Intent(mContext, SecondDiagnosisActivity.class);
                    SecondDiagnosisIntent.putExtra("MedicalRecordId", item.getMedicalRecordId());
                    mContext.startActivity(SecondDiagnosisIntent);
                } else if (mType == 1) {
                    Intent SecondDiagnosisIntent = new Intent(mContext, SecondDiagnosisQuestionActivity.class);
                    SecondDiagnosisIntent.putExtra("MedicalRecordId", item.getMedicalRecordId());
                    mContext.startActivity(SecondDiagnosisIntent);
                } else if (mType == 2) {
                    //点击第三个adapter，先判断订单状态
                    if (item.getMedicalStatus() == 5) {
                        Intent SecondDiagnosisIntent = new Intent(mContext, SecondDiagnosisUntreatActivity.class);
                        SecondDiagnosisIntent.putExtra("MedicalRecordId", item.getMedicalRecordId());
                        mContext.startActivity(SecondDiagnosisIntent);

                    } else {

                        Intent SecondDiagnosisIntent = new Intent(mContext, ReviewDiagnosisReportActivity.class);
                        SecondDiagnosisIntent.putExtra("MedicalRecordId", item.getMedicalRecordId());
                        mContext.startActivity(SecondDiagnosisIntent);
                    }
                }


              /*  String json;
                if (mType == 3) {
                    Type jsonType = new TypeToken<PrescriptionMessageEntity<ConsultMessageEntity>>() {
                    }.getType();
                    ConsultMessageEntity recordDataEntity = mGson.fromJson(item.getRecordData(), ConsultMessageEntity.class);
                    PrescriptionMessageEntity<ConsultMessageEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), jsonType);
                    prescriptionMessageEntity.setPatient(recordDataEntity);
                    json = mGson.toJson(prescriptionMessageEntity);
                } else {
                    Type jsonType = new TypeToken<PrescriptionMessageEntity<RecordDataEntity>>() {
                    }.getType();
                    RecordDataEntity recordDataEntity = mGson.fromJson(item.getRecordData(), RecordDataEntity.class);
                    PrescriptionMessageEntity<RecordDataEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), jsonType);
                    prescriptionMessageEntity.setPatient(recordDataEntity);
                    json = mGson.toJson(prescriptionMessageEntity);
                }
                Log.i("zdp", "json=" + json);
                Intent intentTalk = new Intent(mContext, ChatActivity.class);
                intentTalk.putExtra(Contants.FRIEND_NAME, item.getPatientName());
                intentTalk.putExtra(Contants.FRIEND_IDENTIFIER, item.getApplicantIMId());
                intentTalk.putExtra("consultMessageEntity", item);
                intentTalk.putExtra("action", "pictureConsult");
                intentTalk.putExtra("accountId", item.getApplicantId());
                intentTalk.putExtra("prescriptionMessage", json);
                mContext.startActivity(intentTalk);*/
            }
        });
    }

    public void setdata() {

    }
}