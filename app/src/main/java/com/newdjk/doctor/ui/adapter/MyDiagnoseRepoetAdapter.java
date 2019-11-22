package com.newdjk.doctor.ui.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.activity.ShowOriginPictureActivity;
import com.newdjk.doctor.ui.entity.LookMedicalEntity;

import java.util.List;


public class MyDiagnoseRepoetAdapter extends BaseQuickAdapter<LookMedicalEntity.PatientMedicalsBean, BaseViewHolder> {
    private String TAG = "MyDiagnoseAdapter";
    private Gson mGson;
    private List<LookMedicalEntity.PatientMedicalsBean> datalist;
    private String mPersonalDesc;


    public MyDiagnoseRepoetAdapter(@Nullable List<LookMedicalEntity.PatientMedicalsBean> data,String personalDesc) {
        super(R.layout.diagnose_report_item, data);
        mPersonalDesc = personalDesc;
        datalist = data;
        mGson = new Gson();
    }
    public void setPersonalDesc(String personalDesc) {
        mPersonalDesc = personalDesc;
    }

    @Override
    protected void convert(BaseViewHolder helper, final LookMedicalEntity.PatientMedicalsBean item) {


        try {
            Log.d(TAG, helper.getAdapterPosition() + "-------" + datalist.size());
            EditText editResult = helper.itemView.findViewById(R.id.et_result);
            RecyclerView recyclerView = helper.itemView.findViewById(R.id.recyleviewitem);
            GridLayoutManager  mManagerLayout = new GridLayoutManager(mContext, 3);
            recyclerView.setLayoutManager(mManagerLayout);
            MyDiagnoseImageAdapter adapter=new MyDiagnoseImageAdapter(item.getImagePahts());
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent(mContext, ShowOriginPictureActivity.class);
                    intent.putExtra("path", item.getImagePahts().get(position).getImagePath());
                    mContext.startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
            String medical = item.getMedicalName();
            if (medical != null && medical.equals("用药记录")) {
                helper.setVisible(R.id.et_message,true);
                helper.setText(R.id.et_message,item.getRemark());
            }
            else {
                helper.setVisible(R.id.et_message,false);
            }
           /* helper.setText(R.id.tv_title, "专家意见");
            helper.setText(R.id.tv_desc, "");*/
            if (medical != null && medical.equals("个人描述") && mPersonalDesc != null && !mPersonalDesc.equals("")) {
                helper.setVisible(R.id.et_message,true);
                helper.setText(R.id.et_message,mPersonalDesc);
            }
            else {
                helper.setVisible(R.id.et_message,false);
            }
            helper.setText(R.id.tv_desc, item.getMedicalDesc());
            helper.setText(R.id.tv_title, item.getMedicalName());


        } catch (Exception e) {
            Log.i("ConsultMessageAdapter", "e=" + e.toString());
            e.printStackTrace();
        }
    }
}
