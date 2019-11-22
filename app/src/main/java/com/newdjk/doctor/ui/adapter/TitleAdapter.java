package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.TitleEntity;

import java.util.List;

/**
 * Created by EDZ on 2018/10/10.
 */

public class TitleAdapter extends BaseQuickAdapter<TitleEntity.DataBean,BaseViewHolder>{

    public TitleAdapter(@Nullable List<TitleEntity.DataBean> data) {
        super(R.layout.hospital_message_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TitleEntity.DataBean item) {
        helper.setText(R.id.hospital_message_item,item.getCategoryItemName());
        helper.addOnClickListener(R.id.hospital_message_item);

    }
}
