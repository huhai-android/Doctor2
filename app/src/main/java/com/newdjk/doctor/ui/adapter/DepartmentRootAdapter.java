package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.CityEntity;
import com.newdjk.doctor.ui.entity.DePartmentEntity;

import java.util.List;

public class DepartmentRootAdapter extends BaseQuickAdapter<DePartmentEntity.DataBean.ReturnDataBean,BaseViewHolder>{
//先声明一个int成员变量

    private int thisPosition = -1;

//再定义一个int类型的返回值方法

    public int getthisPosition() {
        return thisPosition;
    }


    //其次定义一个方法用来绑定当前参数值的方法

    //此方法是在调用此适配器的地方调用的，此适配器内不会被调用到

    public void setThisPosition(int thisPosition) {
        this.thisPosition = thisPosition;
    }

    public DepartmentRootAdapter(@Nullable List<DePartmentEntity.DataBean.ReturnDataBean> data) {
        super(R.layout.city_item,data);
    }


    @Override
    protected void convert(BaseViewHolder holder, DePartmentEntity.DataBean.ReturnDataBean itemEntity) {
        if (holder.getPosition() == getthisPosition()) {
            holder.setBackgroundColor(R.id.container,mContext.getResources().getColor(R.color.activity_bg));
        } else {
            holder.setBackgroundColor(R.id.container,mContext.getResources().getColor(R.color.white));
        }
        holder.setText(R.id.mCityName,itemEntity.getDepartmentName());
        holder.addOnClickListener(R.id.mCityName);
    }


}


