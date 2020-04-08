package com.newdjk.doctor.ui.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newdjk.doctor.R;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.IM.ChatActivity;
import com.newdjk.doctor.ui.entity.DoctorPatientRelationEntity;
import com.newdjk.doctor.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.utils.ChatActivityUtils;
import com.newdjk.doctor.utils.GlideMediaLoader;
import com.newdjk.doctor.utils.SpUtils;

import java.lang.reflect.Type;
import java.util.List;


public class VideoInterrogationAdapter extends BaseQuickAdapter<InquiryRecordListDataEntity,BaseViewHolder> {

    private Gson mGson;

    public VideoInterrogationAdapter(@Nullable List<InquiryRecordListDataEntity> data) {
        super(R.layout.interrogation_item,data);
        mGson = new Gson();
    }

    @Override
    protected void convert(BaseViewHolder helper, final InquiryRecordListDataEntity item) {
        try {
            DoctorPatientRelationEntity DoctorPatientRelationEntity = item.getDoctorPatientRelation();
            long unReadNum = item.getUnReadNum();
            if (unReadNum > 0) {
                helper.setVisible(R.id.video_unread_num_layout, true);
                helper.setText(R.id.video_unread_num, unReadNum + "");
            } else {
                helper.setVisible(R.id.video_unread_num_layout, false);
            }
            int IsDrKey = 0;
            if (DoctorPatientRelationEntity != null) {
                IsDrKey = DoctorPatientRelationEntity.getIsDrKey();
            }
            switch (IsDrKey) {
                case 0 :

                    break;
                case 1 :
                    helper.setText(R.id.signature_type,"重点关注");
                    break;
            }
            helper.setText(R.id.patient_name,item.getPatientName());
            helper.setText(R.id.dynamic,item.getContent());
            ImageView avatar = helper.getView(R.id.avatar);
            GlideMediaLoader.load(mContext,avatar,item.getApplicantHeadImgUrl());
            int sexType = item.getPatientInfo().getPatientSex();
            String createTime = item.getCreateTime();
            if (createTime != null ) {
                helper.setText(R.id.create_time,createTime);
            }
            else {
                helper.setText(R.id.create_time,"");
            }
            String areaName = item.getPatientInfo().getAreaName();
            if (TextUtils.isEmpty(areaName)) {
                helper.setText(R.id.address,areaName);
            }
            else {
                helper.setText(R.id.address,"");
            }
            String sex = "";
            switch (sexType) {
                case 1:
                    sex = "男"  ;
                    break;
                case 2:
                    sex = "女"  ;
                    break;
                case 3:
                    sex = "未知"  ;
                    break;
            }
            helper.setText(R.id.sex,sex);
            helper.setText(R.id.age,item.getPatientInfo().getAge());
            helper.setText(R.id.appointment_time,item.getReExaminationDate()+"  "+item.getReExaminationStartTime()+"-"+item.getReExaminationEndTime());
        } catch (Exception e) {
            Log.e("VideoAdapter","errorMsg="+e.toString());
            e.printStackTrace();
        }
        helper.getView(R.id.consult_message_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Type jsonType = new TypeToken<PrescriptionMessageEntity<InquiryRecordListDataEntity>>() {
                }.getType();
                //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                PrescriptionMessageEntity<InquiryRecordListDataEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), jsonType);
                prescriptionMessageEntity.setPatient(item);
                String json = mGson.toJson(prescriptionMessageEntity);
                Log.i("zdp", "json=" + json);
//                Intent intentTalk = new Intent(mContext, ChatActivity.class);
//                intentTalk.putExtra(Contants.FRIEND_NAME, item.getPatientName());
//                intentTalk.putExtra(Contants.FRIEND_IDENTIFIER,  item.getApplicantIMId());
//                intentTalk.putExtra("inquiryRecordListDataEntity",item);
//                intentTalk.putExtra("action", "videoInterrogation");
//                intentTalk.putExtra("accountId",item.getApplicantId());
//                intentTalk.putExtra("prescriptionMessage", json);
//                intentTalk.putExtra("imgPath", item.getApplicantHeadImgUrl());
//                mContext.startActivity(intentTalk);
                ChatActivityUtils.getinStanse().toChat(item.getApplicantIMId(), SpUtils.getString(Contants.identifier), item.getApplicantHeadImgUrl(),mContext);

            }
        });
    }

}
