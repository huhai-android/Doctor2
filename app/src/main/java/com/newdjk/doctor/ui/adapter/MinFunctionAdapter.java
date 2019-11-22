package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
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

public class MinFunctionAdapter extends BaseQuickAdapter<AppLicationEntity, BaseViewHolder> {
    private String TAG = "MyDiagnoseImageAdapter";
    private Gson mGson;
    private List<AppLicationEntity> datalist;
    private int type=0;


    public MinFunctionAdapter(@Nullable List<AppLicationEntity> data, int type) {
        super(R.layout.min_function_item, data);

        datalist = data;
        mGson = new Gson();
        this.type=type;
    }
    public MinFunctionAdapter(@Nullable List<AppLicationEntity> data) {
        super(R.layout.function_item_mine, data);

        datalist = data;
        mGson = new Gson();
    }
    @Override
    protected void convert(BaseViewHolder helper, final AppLicationEntity item) {
        long  unReadNum = item.getUnReadNum();
        if (unReadNum > 0) {
            helper.setVisible(R.id.unread_num_layout, true) ;
            helper.setText(R.id.unread_num, unReadNum + "");
        }
        else {
            helper.setVisible(R.id.unread_num_layout, false) ;
        }
        helper.setText(R.id.mFuncOne, item.getAppDesc() + "");

        ImageView avatar = helper.getView(R.id.im_picture);
        Glide.with(MyApplication.getContext())
                .load(AppLicationUtils.getimageID(item.getImageID()))

                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(avatar);
        helper.addOnClickListener(R.id.function);
    }
}
