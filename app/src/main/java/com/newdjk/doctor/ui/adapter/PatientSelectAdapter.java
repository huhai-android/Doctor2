package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.PatientListDataEntity;
import com.newdjk.doctor.views.CircleImageView;

import java.util.List;

public class PatientSelectAdapter extends BaseQuickAdapter<PatientListDataEntity, BaseViewHolder> {
    private String TAG = "MyDiagnoseImageAdapter";
    private List<PatientListDataEntity> datalist;



    public PatientSelectAdapter(@Nullable List<PatientListDataEntity> data) {
        super(R.layout.patient_select_item, data);

        datalist = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final PatientListDataEntity item) {
        String patientName= item.getPatientName();
        if (!TextUtils.isEmpty(patientName)) {
            helper.setText(R.id.tv_name, patientName + "");
        }
        else {
            helper.setText(R.id.tv_name, "**");
        }

        CircleImageView circleImageView = helper.itemView.findViewById(R.id.head_icon);
        Glide.with(MyApplication.getContext())
                .load(item.getPaPicturePath())
                .dontAnimate()
                .placeholder(R.drawable.doctor_default_img)
                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(circleImageView);
        }

}
