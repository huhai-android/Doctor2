package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.MedicalRecordByIdEntity;

import java.util.List;

public class SecondDiagnosisAdapter extends BaseQuickAdapter<MedicalRecordByIdEntity.MainProblemsBean, BaseViewHolder> {
    private String TAG = "MyDiagnoseAdapter";
    private Gson mGson;
    private List<MedicalRecordByIdEntity.MainProblemsBean> datalist;


    public SecondDiagnosisAdapter(@Nullable List<MedicalRecordByIdEntity.MainProblemsBean> data) {
        super(R.layout.diagnose_item, data);
        datalist = data;
        mGson = new Gson();
    }



    @Override
    protected void convert(final BaseViewHolder helper, final MedicalRecordByIdEntity.MainProblemsBean item) {


        try {
            helper.setVisible(R.id.et_result_layout,false);
                helper.setText(R.id.tv_number, "Q"+(helper.getAdapterPosition()+1));
                helper.setText(R.id.tv_title, item.getProblem());

        } catch (Exception e) {
            Log.i("ConsultMessageAdapter", "e=" + e.toString());
            e.printStackTrace();
        }
    }

}
