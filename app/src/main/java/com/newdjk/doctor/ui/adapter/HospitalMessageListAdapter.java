package com.newdjk.doctor.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.HospitalEntity;
import com.newdjk.doctor.ui.entity.ItemEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HospitalMessageListAdapter extends BaseQuickAdapter<HospitalEntity.DataBean.ReturnDataBean,BaseViewHolder>{


    public HospitalMessageListAdapter(@Nullable List<HospitalEntity.DataBean.ReturnDataBean> data) {
        super(R.layout.hospital_message_item,data);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, HospitalEntity.DataBean.ReturnDataBean itemEntity) {

        baseViewHolder.setText(R.id.hospital_message_item,itemEntity.getHospitalName());
        baseViewHolder.addOnClickListener(R.id.hospital_message_item);

    }


}


