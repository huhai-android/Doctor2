package com.newdjk.doctor.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newdjk.doctor.R;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.IM.ChatActivity;
import com.newdjk.doctor.ui.entity.AllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.ui.entity.RecordDataEntity;
import com.newdjk.doctor.ui.entity.VideoInterrogationEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.AnimatedExpandableListView;
import com.newdjk.doctor.views.CircleImageView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TaskAllAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private List<VideoInterrogationEntity> mList;
    private Context mContext;
    private Gson mGson;

    public TaskAllAdapter(Context mContext) {
        this.mContext = mContext;
        mGson = new Gson();
        mList = new ArrayList<>();
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder childViewHolder;
      final  AllRecordForDoctorEntity allRecordForDoctorEntity =mList.get(groupPosition).getAllRecordForDoctorEntity().get(childPosition);
        Log.i("video22222", "AllRecordForDoctorEntity=" + allRecordForDoctorEntity.toString());

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.interrogation_item, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.avatar =  convertView.findViewById(R.id.avatar);
            childViewHolder.videoUnreadNumLayout =  convertView.findViewById(R.id.video_unread_num_layout);
            childViewHolder.videoUnreadNum =  convertView.findViewById(R.id.video_unread_num);
            childViewHolder.patientName =  convertView.findViewById(R.id.patient_name);
            childViewHolder.signatureType =  convertView.findViewById(R.id.signature_type);
            childViewHolder.sex =  convertView.findViewById(R.id.sex);
            childViewHolder.age =  convertView.findViewById(R.id.age);
            childViewHolder.address =  convertView.findViewById(R.id.address);
            childViewHolder.dynamic =  convertView.findViewById(R.id.dynamic);
            childViewHolder.appointment_time =  convertView.findViewById(R.id.appointment_time);
            childViewHolder.create_time =  convertView.findViewById(R.id.create_time);
            childViewHolder.consult_message_item = convertView.findViewById(R.id.consult_message_item);
            convertView.setTag(childViewHolder);
        }
        else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        if (allRecordForDoctorEntity != null) {
            long unReadNum = allRecordForDoctorEntity.getUnReadNum();
            if (unReadNum > 0) {
                childViewHolder.videoUnreadNumLayout.setVisibility(View.VISIBLE);
                childViewHolder.videoUnreadNum.setText(unReadNum + "");
            } else {
                childViewHolder.videoUnreadNumLayout.setVisibility(View.GONE);
            }
            String content = allRecordForDoctorEntity.getContent();
            childViewHolder.dynamic.setText(content + "");
            try {
                String createTime = allRecordForDoctorEntity.getDealWithTime().substring(0,16);
                if (!TextUtils.isEmpty(createTime)  ) {
                    childViewHolder.create_time.setText(createTime);
                }
                else {
                    childViewHolder.create_time.setText("");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            int  IsDrKey = allRecordForDoctorEntity.getIsDrKey();
            switch (IsDrKey) {
                case 0 :

                    break;
                case 1 :
                    childViewHolder.signatureType.setText("重点关注");
                    break;
            }

            childViewHolder.patientName.setText(allRecordForDoctorEntity.getPatientName());
            childViewHolder.age.setText(allRecordForDoctorEntity.getAge());
            String sex = "";
            int sexType = allRecordForDoctorEntity.getType();
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
            childViewHolder.sex.setText(sex);
            String areaName = allRecordForDoctorEntity.getAreaName();
            if (!TextUtils.isEmpty(areaName)) {
                childViewHolder.address.setText(areaName);
            }
            else {
                childViewHolder.address.setText("");
            }
            String createTime = allRecordForDoctorEntity.getPayTime().substring(0,16);
            if (!TextUtils.isEmpty(createTime) ) {
                childViewHolder.create_time.setText(createTime);
            }
            else {
                childViewHolder.create_time.setText("");
            }
            String reExaminationDate = allRecordForDoctorEntity.getReExaminationDate();
            String reExaminationStartTime = allRecordForDoctorEntity.getReExaminationStartTime();
            String reExaminationEndTime = allRecordForDoctorEntity.getReExaminationEndTime();
            String startTime = reExaminationStartTime.substring(0,reExaminationStartTime.lastIndexOf(":"));

            String endTime =  reExaminationEndTime.substring(0,reExaminationEndTime.lastIndexOf(":"));
            try {
                if (reExaminationDate != null && reExaminationStartTime!= null && reExaminationEndTime!= null) {
                    childViewHolder.appointment_time.setText(reExaminationDate.substring(0,reExaminationDate.indexOf(" "))+" "+startTime+"-"+endTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        childViewHolder.consult_message_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Type jsonType = new TypeToken<PrescriptionMessageEntity<RecordDataEntity>>() {
                    }.getType();
                    RecordDataEntity recordDataEntity = mGson.fromJson(allRecordForDoctorEntity.getRecordData(),RecordDataEntity.class);
                    PrescriptionMessageEntity<RecordDataEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), jsonType);
                    prescriptionMessageEntity.setPatient(recordDataEntity);
                    String   json= mGson.toJson(prescriptionMessageEntity);

                    Log.i("zdp", "json=" + json);
                    Intent intentTalk = new Intent(mContext, ChatActivity.class);
                    intentTalk.putExtra(Contants.FRIEND_NAME, allRecordForDoctorEntity.getPatientName());
                    intentTalk.putExtra(Contants.FRIEND_IDENTIFIER,  allRecordForDoctorEntity.getApplicantIMId());
                    intentTalk.putExtra("inquiryRecordListDataEntity",allRecordForDoctorEntity);
                    intentTalk.putExtra("action", "videoInterrogation");
                    intentTalk.putExtra("accountId",allRecordForDoctorEntity.getApplicantId());
                    intentTalk.putExtra("prescriptionMessage", json);
                    intentTalk.putExtra("imgPath", allRecordForDoctorEntity.getApplicantHeadImgUrl());
                    mContext.startActivity(intentTalk);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
     /*   if (i1 == mList.get(i).getStocks().size()-1) {
            // childViewHolder.mProductlayout.setVisibility(View.GONE);
            childViewHolder.mLastChildItemDriver.setVisibility(View.VISIBLE);
            childViewHolder.mLastProductlayout.setVisibility(View.VISIBLE);
            childViewHolder.mChildItemDriver.setVisibility(View.GONE);
        }
        else {
            // childViewHolder.mProductlayout.setVisibility(View.VISIBLE);
            childViewHolder.mLastChildItemDriver.setVisibility(View.GONE);
            childViewHolder.mLastProductlayout.setVisibility(View.GONE);
            childViewHolder.mChildItemDriver.setVisibility(View.VISIBLE);
        }*/
        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return mList.get(groupPosition).getAllRecordForDoctorEntity().size();
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mList.get(groupPosition).getAllRecordForDoctorEntity().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.date_time_adapter, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.dateTime =  convertView.findViewById(R.id.date_time);
            groupViewHolder.scalingImageView =  convertView.findViewById(R.id.scalingIcon);
            groupViewHolder.number = convertView.findViewById(R.id.number);
            convertView.setTag(groupViewHolder);
        }
        else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.dateTime.setText(mList.get(groupPosition).getDateTime());
        groupViewHolder.number.setText(String.valueOf(mList.get(groupPosition).getNumber()));


        if (isExpanded) {
            groupViewHolder.scalingImageView.setImageResource(R.mipmap.rise);
        /*    groupViewHolder.mDataLayout.setVisibility(View.VISIBLE);
            groupViewHolder.mWarehouseLayout.setEnabled(true);*/
            // groupViewHolder.mWarehouseLayout.setBackgroundColor(R.drawable.warehouse_open_style);
        }
        else {
            groupViewHolder.scalingImageView.setImageResource(R.mipmap.decline);
           /* groupViewHolder.mDataLayout.setVisibility(View.GONE);
            groupViewHolder.mWarehouseLayout.setEnabled(false);*/
            //  groupViewHolder.mWarehouseLayout.setBackgroundColor(R.drawable.warehouse_close_style);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    class GroupViewHolder {
        TextView dateTime;
        ImageView scalingImageView;
        TextView number;
    }
    class ChildViewHolder {
        LinearLayout consult_message_item;
        CircleImageView avatar;
        LinearLayout videoUnreadNumLayout;
        TextView videoUnreadNum;
        TextView patientName;
        TextView signatureType;
        TextView sex;
        TextView age;
        TextView address;
        TextView dynamic;
        TextView appointment_time;
        TextView create_time;
    }
    public void refreshData(List<VideoInterrogationEntity> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }
}
