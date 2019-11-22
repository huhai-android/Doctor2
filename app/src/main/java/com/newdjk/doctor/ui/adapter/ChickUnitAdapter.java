package com.newdjk.doctor.ui.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.ChickUnitEntity;

import java.util.List;

public class ChickUnitAdapter extends BaseQuickAdapter<ChickUnitEntity, BaseViewHolder> {


    private final Gson mGson;
    private List<ChickUnitEntity> mDataList;

    private LayoutInflater mLayoutInflater;
    private Activity mActivity;


    public ChickUnitAdapter( @Nullable List<ChickUnitEntity> data) {
        super(R.layout.chick_unit_gongyao, data);
        mGson = new Gson();
    }


    @Override
    protected void convert(final BaseViewHolder helper, ChickUnitEntity item) {
        helper.setText(R.id.chick_unit, item.getName());

        helper.setText(R.id.chick_unit_count, "药品"+item.getMedicationCount()+"种");

        int type = item.getIsDefault();
        Log.d("ChickUnitAdapter","type="+type);
        CheckBox checkBox=helper.itemView.findViewById(R.id.cb_not_show);
        switch (type) {
            case 1:
              //  helper.getView(R.id.im_select).setVisibility(View.VISIBLE);
                checkBox.setChecked(true);
                break;
            case 0:
              //  helper.getView(R.id.im_select).setVisibility(View.GONE);
                checkBox.setChecked(false);
                break;
        }
        String method = item.getDeliveryMethod();
        if (method.contains("1")) {
            helper.getView(R.id.tv_type1).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_type1).setVisibility(View.GONE);
        }

        if (method.contains("2")) {
            helper.getView(R.id.tv_type2).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_type2).setVisibility(View.GONE);
        }

        if (method.contains("3")) {
            helper.getView(R.id.tv_type3).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_type3).setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(item.getServiceScope())) {

            helper.getView(R.id.tv_service_area).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.tv_service_area).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_service_area,"服务区域:" + (item.getServiceScope() == null ? "" : item.getServiceScope()));

        }

        if (item.getActivityNum()==0) {

            helper.setText(R.id.chick_unit_huoyue,"活跃指数 " + 0);

        }else {

            helper.setText(R.id.chick_unit_huoyue,"活跃指数 " + item.getActivityNum());
        }



        helper.addOnClickListener(R.id.cb_not_show);

    }



}
