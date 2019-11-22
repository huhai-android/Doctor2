package com.newdjk.doctor.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.ArchivesActivity;
import com.newdjk.doctor.ui.entity.DoctorPatientRelationEntity;
import com.newdjk.doctor.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.doctor.ui.entity.PatientInfoEntity;
import com.newdjk.doctor.ui.entity.PatientListDataEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.CircleImageView;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.MyViewHolder> {


    private List<PatientListDataEntity> mDataList;

    private LayoutInflater mLayoutInflater;
    private Activity mActivity;
    private Gson mGson;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public PatientAdapter(Activity activity) {
        mActivity = activity;
        mGson = new Gson();
        //mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.patient_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (mDataList != null && mDataList.size() > 0) {

            String path = mDataList.get(position).getPaPicturePath();
            if (path != null && !path.equals("")) {
//                Glide.with(MyApplication.getContext())
//                        .load(path)
//                        .placeholder(R.drawable.patient_default_img)
//                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .into(holder.avatar);

                Glide.with(MyApplication.getContext())
                        .load(path)
                        .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                        .error(R.drawable.patient_default_img)
                        .placeholder(R.drawable.patient_default_img)
                        .into(holder.avatar);
            }
            Log.e("zdp", mDataList.get(position).getPatientName());
            String name = mDataList.get(position).getPatientName();

            if (name.contains("&")) {
                holder.patientName.setText(name.substring(2, name.length()));
            }
            String disease = mDataList.get(position).getDisease();
            if (disease != null && !disease.equals("")) {
                holder.address.setText(disease);
            } else {
                holder.address.setText("暂无标签");
            }

            holder.age.setText(mDataList.get(position).getAge());
            int sexType = mDataList.get(position).getPatientSex();
            if (sexType == 1) {
                holder.sex.setText("男");
            } else if (sexType == 2) {
                holder.sex.setText("女");
            } else if (sexType == 3) {
                holder.sex.setText("未确定");
            }
            int section = getSectionForPosition(position);
            if (position == getPositionForSection(section)) {
                holder.alpha.setVisibility(View.VISIBLE);
                holder.alpha.setText(mDataList.get(position).getPatientName().substring(0, 1));
            } else {
                holder.alpha.setVisibility(View.GONE);
            }
            holder.patientItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Type jsonType = new TypeToken<PrescriptionMessageEntity<InquiryRecordListDataEntity>>() {
                    }.getType();
                    PatientInfoEntity PatientInfo = new PatientInfoEntity();

                    //   PatientInfo.setBirthday(mDataList.get(position).getBirthday());
                    String paName = mDataList.get(position).getPatientName();
                    PatientInfo.setPatientName(paName.substring(2, paName.length()));
                    PatientInfo.setPatientId(mDataList.get(position).getPatientId());
                    PatientInfo.setPatientSex(mDataList.get(position).getPatientSex());
                    PatientInfo.setAge(String.valueOf(mDataList.get(position).getAge()));
                    DoctorPatientRelationEntity DoctorPatientRelation = new DoctorPatientRelationEntity();
                    DoctorPatientRelation.setDrPatientId(mDataList.get(position).getDrPatientId());
                    InquiryRecordListDataEntity InquiryRecordListDataEntity = new InquiryRecordListDataEntity();
                    InquiryRecordListDataEntity.setDoctorPatientRelation(DoctorPatientRelation);
                    InquiryRecordListDataEntity.setPatientInfo(PatientInfo);
                    //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                    PrescriptionMessageEntity<InquiryRecordListDataEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), jsonType);
                   if (prescriptionMessageEntity!=null){
                       prescriptionMessageEntity.setPatient(InquiryRecordListDataEntity);
                       String json = mGson.toJson(prescriptionMessageEntity);
                       Intent ArchivesIntent = new Intent(mActivity, ArchivesActivity.class);
                       ArchivesIntent.putExtra("action", "patientList");
                       ArchivesIntent.putExtra("prescriptionMessage", json);
                       mActivity.startActivity(ArchivesIntent);
                   }

                }
            });
        }
    }

    @Override
    public void onViewRecycled(@NonNull MyViewHolder holder) {
        if (holder != null) {
            Glide.clear(holder.avatar);
        }
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar)
        CircleImageView avatar;
        @BindView(R.id.patient_name)
        TextView patientName;
        @BindView(R.id.sex)
        TextView sex;
        @BindView(R.id.age)
        TextView age;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.patient_item)
        LinearLayout patientItem;
        @BindView(R.id.alpha)
        TextView alpha;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setDatalist(List<PatientListDataEntity> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    public int getSectionForPosition(int position) {
        return mDataList.get(position).getPatientName().charAt(0);
    }


    @SuppressLint("DefaultLocale")
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            char sortStr = mDataList.get(i).getPatientName().charAt(0);
            if (sortStr == section) {
                return i;
            }
        }

        return -1;
    }


}
