package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.DePartmentEntity;

import java.util.List;

public class DepartmentChildAdapter extends BaseQuickAdapter<DePartmentEntity.DataBean.ReturnDataBean,BaseViewHolder>{


    public DepartmentChildAdapter(@Nullable List<DePartmentEntity.DataBean.ReturnDataBean> data) {
        super(R.layout.child_item,data);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, DePartmentEntity.DataBean.ReturnDataBean itemEntity) {

        baseViewHolder.setText(R.id.mCityName,itemEntity.getDepartmentName());
        baseViewHolder.addOnClickListener(R.id.mCityName);
//        Intent intent = new Intent();
//        intent.putExtra("hospital_message",mDataList.get(position));
//        mActivity.setResult(Activity.RESULT_OK,intent);
//        mActivity.finish();
    }


}


