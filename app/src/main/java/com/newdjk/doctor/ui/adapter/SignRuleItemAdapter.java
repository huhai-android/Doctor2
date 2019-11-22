package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.SignRuleEntity;

import java.util.List;


public class SignRuleItemAdapter extends BaseQuickAdapter<SignRuleEntity.DataBean.IntegralSetsBean, BaseViewHolder> {
    private String TAG = "SignRuleAdapter";
    private Gson mGson;
    private List<SignRuleEntity.DataBean.IntegralSetsBean> datalist;

    public SignRuleItemAdapter(@Nullable List<SignRuleEntity.DataBean.IntegralSetsBean> data) {
        super(R.layout.sign_rule_child_item, data);
        datalist = data;
        mGson = new Gson();
    }

    @Override
    protected void convert(BaseViewHolder helper, final SignRuleEntity.DataBean.IntegralSetsBean item) {
        Log.d(TAG,"数据"+item.toString());
        helper.setText(R.id.tv_title1,item.getDescription()+"");
        if (item.getIntegralTypeClass()==1){
            helper.setText(R.id.tv_point,item.getIntegralSetValue()+"%");
        }else if (item.getIntegralTypeClass()==2){
            helper.setText(R.id.tv_point,item.getIntegralSetValue()+"");

        }else {
            helper.setText(R.id.tv_point,item.getIntegralSetValue()+"");

        }

        if (item.getIntegralTypeCode().equals("104")){
            helper.setText(R.id.tv_name,"奖励订单费");

        }else {
            helper.setText(R.id.tv_name,"奖励");
        }

    }


}
