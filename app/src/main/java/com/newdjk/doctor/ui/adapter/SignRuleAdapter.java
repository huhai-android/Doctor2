package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.SignRuleEntity;
import com.newdjk.doctor.views.CircleImageView;

import java.util.List;


public class SignRuleAdapter extends BaseQuickAdapter<SignRuleEntity.DataBean, BaseViewHolder> {
    private String TAG = "SignRuleAdapter";
    private Gson mGson;
    private List<SignRuleEntity.DataBean> datalist;

    public SignRuleAdapter(@Nullable List<SignRuleEntity.DataBean> data) {
        super(R.layout.sign_rule_item, data);
        datalist = data;
        mGson = new Gson();
    }

    @Override
    protected void convert(BaseViewHolder helper, final SignRuleEntity.DataBean item) {
        Log.d(TAG, "数据" + item.toString());
        helper.setText(R.id.tv_title, item.getIntegralTypeName() + "");
        RecyclerView recyclerView = helper.itemView.findViewById(R.id.recyleview);
        SignRuleItemAdapter signRuleItemAdapter = new SignRuleItemAdapter(item.getIntegralSets());
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(signRuleItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false));
        if (item.isCompleted()) {
            helper.setText(R.id.tv_complete, "已完成");
            helper.setTextColor(R.id.tv_complete, mContext.getResources().getColor(R.color.colorPrimary));
            helper.setBackgroundRes(R.id.tv_complete, R.drawable.shape_gotofinish_complete_point);

        } else {
            helper.setText(R.id.tv_complete, "去完成");
            helper.setTextColor(R.id.tv_complete, mContext.getResources().getColor(R.color.colorPrimary));
            helper.setBackgroundRes(R.id.tv_complete, R.drawable.shape_gotofinish_point);
        }
        String type = item.getIntegralTypeCode();
        CircleImageView imageView = helper.itemView.findViewById(R.id.im_appicon);
        switch (type) {
            case "101"://推荐医生  跳转推荐医生二维码
                Glide.with(MyApplication.getContext())
                        .load(R.mipmap.recommend_doctor)

                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
                break;
            case "102"://患者关注 跳转我的二维码
                Glide.with(MyApplication.getContext())
                        .load(R.mipmap.patient_attention)

                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
                break;
            case "103"://患者报道  跳转我的二维码
                Glide.with(MyApplication.getContext())
                        .load(R.mipmap.patient_trport_award)

                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
                break;
            case "104": //销售积分  跳转我的服务
                Glide.with(MyApplication.getContext())
                        .load(R.mipmap.sales_award)

                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);

                break;
            case "105"://开处方单  跳转患者列表
                Glide.with(MyApplication.getContext())
                        .load(R.mipmap.renewal_award)

                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
                break;
            case "106"://首次注册  跳转认证
                Glide.with(MyApplication.getContext())
                        .load(R.mipmap.first_registration_award)

                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
                break;
            case "107"://签到积分  跳转我的积分
                Glide.with(MyApplication.getContext())
                        .load(R.mipmap.recommend_doctor)

                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
                break;
            case "108"://药品销售 跳转患者列表
                Glide.with(MyApplication.getContext())
                        .load(R.mipmap.drug_award)

                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);

                break;
        }
    }


}
