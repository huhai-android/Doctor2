package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.AllDoctorCheckReportEntity;

import java.util.List;


public class DoctorCheckReportAdapter extends BaseQuickAdapter<AllDoctorCheckReportEntity.ResultItemsBean, BaseViewHolder> {

    private Gson mGson;

    public DoctorCheckReportAdapter(@Nullable List<AllDoctorCheckReportEntity.ResultItemsBean> data) {
        super(R.layout.doctor_report_item, data);
        mGson = new Gson();
    }

    @Override
    protected void convert(BaseViewHolder helper, final AllDoctorCheckReportEntity.ResultItemsBean item) {

        Log.i("ConsultMessage", "unReadNum=" + item.toString());
        try {
            TextView avatar = helper.getView(R.id.tv_check_desc);
            LinearLayout lvitem = helper.getView(R.id.consult_message_item);
            avatar.setText(item.getName());
            if (item.isChecked()) {
                avatar.setTextColor(mContext.getResources().getColor(R.color.white));
                avatar.setBackground(mContext.getResources().getDrawable(R.drawable.radiobutton_background_checked));
            } else {
                avatar.setTextColor(mContext.getResources().getColor(R.color.text_doctor_report));
                avatar.setBackground(mContext.getResources().getDrawable(R.drawable.radiobutton_background_unchecked));
            }


        } catch (Exception e) {
            Log.i("ConsultMessageAdapter", "e=" + e.toString());
            e.printStackTrace();
        }
    }


}
