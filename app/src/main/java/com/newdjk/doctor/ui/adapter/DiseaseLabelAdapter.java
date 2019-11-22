package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.DiseaseLabelEntity;

import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.adapter
 *  @文件名:   DiseaseLabelAdapter
 *  @创建者:   huhai
 *  @创建时间:  2019/3/12 15:59
 *  @描述：
 */
public class DiseaseLabelAdapter extends BaseQuickAdapter<DiseaseLabelEntity, BaseViewHolder> {
    public DiseaseLabelAdapter(int layoutResId, @Nullable List<DiseaseLabelEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiseaseLabelEntity item) {
        helper.setText(R.id.tv_title, item.getDisGroupName() + "");
        helper.addOnClickListener(R.id.tv_title);
        helper.addOnClickListener(R.id.im_delete);
    }
}
