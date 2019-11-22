package com.newdjk.doctor.ui.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
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
import com.newdjk.doctor.ui.entity.OnlineRenewalDataEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.utils.GlideMediaLoader;
import com.newdjk.doctor.utils.SpUtils;

import java.lang.reflect.Type;
import java.util.List;


public class OnlineRenewalDataAdapter extends BaseQuickAdapter<OnlineRenewalDataEntity,BaseViewHolder> {

    private Gson mGson;

    public OnlineRenewalDataAdapter(@Nullable List<OnlineRenewalDataEntity> data) {
        super(R.layout.online_renewal_item,data);
        mGson = new Gson();
    }

    @Override
    protected void convert(BaseViewHolder helper, final OnlineRenewalDataEntity item) {

        long unReadNum = item.getUnReadNum();
        try {
            if (unReadNum > 0) {
                helper.setVisible(R.id.consult_unread_num_layout, true);
                helper.setText(R.id.renewal_unread_num, unReadNum + "");
            } else {
                helper.setVisible(R.id.consult_unread_num_layout, false);
            }
            helper.setText(R.id.patient_name, item.getPatientName());
            helper.setText(R.id.create_time, item.getCreateTime());
            ImageView avatar = helper.getView(R.id.avatar);
            GlideMediaLoader.load(mContext,avatar,item.getApplicantHeadImgUrl());
            String Diagnoses = item.getDiagnoses();
            if(Diagnoses!= null && !Diagnoses.equals("")) {
                helper.setText(R.id.dynamic, Diagnoses);
            }
            else {
                helper.setText(R.id.dynamic, "暂无备注");
            }
            int sexType = item.getPatientInfo().getPatientSex();
            String area = item.getPatientInfo().getAreaName();
            if (area != null) {
                helper.setText(R.id.address,area);
            } else {
                helper.setText(R.id.address,"");
            }
            String sex = "";
            switch (sexType) {
                case 1:
                    sex = "男";
                    break;
                case 2:
                    sex = "女";
                    break;
                case 3:
                    sex = "未知";
                    break;
            }
            helper.setText(R.id.sex, sex);
            String areaName = item.getPatientInfo().getAreaName();
            if (areaName != null) {
                helper.setText(R.id.address, areaName);
            } else {
                helper.setText(R.id.address, "");
            }
            helper.setText(R.id.age, item.getPatientInfo().getAge());
        } catch (Exception e) {
            Log.e("OnlineAdapter","errorMesssage="+e.toString());
            e.printStackTrace();
        }
        helper.getView(R.id.consult_message_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Type jsonType = new TypeToken<PrescriptionMessageEntity<OnlineRenewalDataEntity>>() {}.getType();
                //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                PrescriptionMessageEntity<OnlineRenewalDataEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), jsonType);
                prescriptionMessageEntity.setPatient(item);
                String json = mGson.toJson(prescriptionMessageEntity);
                Log.i("zdp", "json=" + json);
                Intent intentTalk = new Intent(mContext, ChatActivity.class);
                intentTalk.putExtra(Contants.FRIEND_NAME, item.getPatientName());
                intentTalk.putExtra("onlineRenewalDataEntity", item);
                intentTalk.putExtra("action", "onlineRenewal");
                intentTalk.putExtra(Contants.FRIEND_IDENTIFIER,  item.getApplicantIMId());
                intentTalk.putExtra("prescriptionMessage", json);
                intentTalk.putExtra("imgPath", item.getApplicantHeadImgUrl());
                mContext.startActivity(intentTalk);
            }
        });
    }
}
