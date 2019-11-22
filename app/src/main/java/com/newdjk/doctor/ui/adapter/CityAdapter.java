package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.CityEntity;

import java.util.List;

public class CityAdapter extends BaseQuickAdapter<CityEntity.DataBean,BaseViewHolder>{


    public CityAdapter(@Nullable List<CityEntity.DataBean> data) {
        super(R.layout.choose_city_item,data);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, CityEntity.DataBean itemEntity) {

        baseViewHolder.setText(R.id.mCityName,itemEntity.getAreaName());
        baseViewHolder.addOnClickListener(R.id.mCityName);
//        Intent intent = new Intent();
//        intent.putExtra("hospital_message",mDataList.get(position));
//        mActivity.setResult(Activity.RESULT_OK,intent);
//        mActivity.finish();
    }


}


