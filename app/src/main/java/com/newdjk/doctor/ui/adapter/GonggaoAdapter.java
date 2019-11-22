package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.lxq.okhttp.MyOkHttp;
import com.makeramen.roundedimageview.RoundedImageView;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.GongGaoListEntity;

import java.util.List;

/**
 * Created by gabriel on 2017/2/28.
 */

public class GonggaoAdapter extends BaseQuickAdapter<GongGaoListEntity.ReturnDataBean, BaseViewHolder> {

    protected MyOkHttp mMyOkhttp;
    private Gson mGson;

    public GonggaoAdapter(@Nullable List<GongGaoListEntity.ReturnDataBean> data) {
        super(R.layout.item_list_gonggao, data);
        mGson = new Gson();
        mMyOkhttp = MyApplication.getInstance().getMyOkHttp();
    }


    @Override
    protected void convert(final BaseViewHolder helper, final GongGaoListEntity.ReturnDataBean item) {

        RoundedImageView imageView = helper.itemView.findViewById(R.id.image);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(mContext)
                .load(item.getMasterMap())
                .error(R.drawable.default_gonggao)
                .into(imageView);


        helper.setText(R.id.tv_time, item.getPublishTime().substring(0, 10) + "");
        helper.setText(R.id.tv_title, item.getTitle() + "");
    }
}
