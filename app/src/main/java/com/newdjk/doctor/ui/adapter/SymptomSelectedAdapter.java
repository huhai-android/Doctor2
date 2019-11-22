package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.GetSecondDiagnosisSettingEntity;

import java.util.List;

public class SymptomSelectedAdapter extends BaseQuickAdapter<GetSecondDiagnosisSettingEntity.DoctorDiseasesBean, BaseViewHolder> {
private String TAG = "MyDiagnoseImageAdapter";
private Gson mGson;
private List<GetSecondDiagnosisSettingEntity.DoctorDiseasesBean>  datalist;


public SymptomSelectedAdapter(@Nullable List<GetSecondDiagnosisSettingEntity.DoctorDiseasesBean>  data) {
        super(R.layout.symptoms_item, data);

        datalist = data;
        mGson = new Gson();
        }
    @Override
    protected void convert(BaseViewHolder helper, final GetSecondDiagnosisSettingEntity.DoctorDiseasesBean item) {


        try {
            Log.d(TAG,helper.getAdapterPosition()+"");
            helper.setText(R.id.symptom,item.getDiseaseName());
        } catch (Exception e) {
            Log.i("ConsultMessageAdapter", "e=" + e.toString());
            e.printStackTrace();
        }
    }

}
