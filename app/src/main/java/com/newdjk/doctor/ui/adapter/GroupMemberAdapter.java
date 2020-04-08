package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.lxq.okhttp.MyOkHttp;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.utils.GlideUtils;
import com.newdjk.doctor.views.CircleImageView;
import com.tencent.TIMUserProfile;

import java.util.List;

/**
 * Created by gabriel on 2017/2/28.
 */

public class GroupMemberAdapter extends BaseQuickAdapter<TIMUserProfile, BaseViewHolder> {

    protected MyOkHttp mMyOkhttp;
    private Gson mGson;

    public GroupMemberAdapter(@Nullable List<TIMUserProfile> data) {
        super(R.layout.item_groupmember_list, data);
        mGson = new Gson();
        mMyOkhttp = MyApplication.getInstance().getMyOkHttp();
    }


    @Override
    protected void convert(final BaseViewHolder helper, final TIMUserProfile item) {

        String identifier = item.getIdentifier();
        String  name = item.getNickName();
        String path = item.getFaceUrl();
        Log.d(TAG, "图片地址" + path);
//        Glide.with(MyApplication.getContext())
//                .load(path)
//                .dontAnimate()
//                .placeholder(R.drawable.patient_default_img)
//                .into(((CircleImageView) helper.getView(R.id.avatar)));
        GlideUtils.loadPatientImage(path,((CircleImageView) helper.getView(R.id.avatar)));
        if (!TextUtils.isEmpty(name)) {
            helper.setText(R.id.name, name);
        } else {
            helper.setText(R.id.name, identifier);
        }

        helper.setOnClickListener(R.id.itemview, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  try {


            }
        });
    }



}
