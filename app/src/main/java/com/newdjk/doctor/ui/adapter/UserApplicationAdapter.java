package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.AppLicationEntity;
import com.newdjk.doctor.utils.AppLicationUtils;

import java.util.List;


public class UserApplicationAdapter extends BaseQuickAdapter<AppLicationEntity, BaseViewHolder> {
    private String TAG = "MyDiagnoseImageAdapter";
    private Gson mGson;
    private List<AppLicationEntity> datalist;
    private String type="完成";

    public UserApplicationAdapter(@Nullable List<AppLicationEntity> data) {
        super(R.layout.application_user_item, data);

        datalist = data;
        mGson = new Gson();
    }

    @Override
    protected void convert(BaseViewHolder helper, final AppLicationEntity item) {
        if (type=="编辑"){
            helper.getView(R.id.im_edit).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_desc, item.getAppDesc() + "");
            ImageView avatar = helper.getView(R.id.im_appicon);
            Glide.with(MyApplication.getContext())
                    .load(AppLicationUtils.getimageID(item.getImageID()))

                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(avatar);
            helper.addOnClickListener(R.id.im_edit);
        }else {
            helper.getView(R.id.im_edit).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_desc, item.getAppDesc() + "");
            ImageView avatar = helper.getView(R.id.im_appicon);
            Glide.with(MyApplication.getContext())
                    .load(AppLicationUtils.getimageID(item.getImageID()))

                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(avatar);
            helper.addOnClickListener(R.id.im_edit);
        }

    }

    public void settype(String type) {
        this.type=type;
        notifyDataSetChanged();
    }
}
