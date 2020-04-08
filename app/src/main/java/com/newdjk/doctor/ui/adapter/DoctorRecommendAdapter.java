package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.DoctorRecommend;
import com.newdjk.doctor.utils.GlideUtils;
import com.newdjk.doctor.views.CircleImageView;

import java.util.List;

public class DoctorRecommendAdapter extends BaseQuickAdapter<DoctorRecommend.ReturnDataBean, BaseViewHolder> {
    private String TAG = "MyDiagnoseImageAdapter";
    private Gson mGson;
    private List<DoctorRecommend.ReturnDataBean> datalist;
    private String type = "编辑";


    public DoctorRecommendAdapter(@Nullable List<DoctorRecommend.ReturnDataBean> data) {
        super(R.layout.doctor_recommend_item, data);

        datalist = data;
        mGson = new Gson();
    }

    @Override
    protected void convert(BaseViewHolder helper, final DoctorRecommend.ReturnDataBean item) {
        String doctorName = item.getDrName();
        if (!TextUtils.isEmpty(doctorName)) {
            helper.setText(R.id.doctor_name, item.getDrName() + "");
        }
        else {
            helper.setText(R.id.doctor_name, "**"+item.getMobile().substring(7,11));
        }
          int status = item.getDrStatus();
          switch (status) {
              case 0:
                  helper.setText(R.id.tv_authentication,"未认证");
                  break;
              case 1:
                  helper.setText(R.id.tv_authentication,"已认证");
                  break;
              case 2:
                  helper.setText(R.id.tv_authentication,"审核失败");
                  break;
              case 3:
                  helper.setText(R.id.tv_authentication,"审核中");
                  break;
          }
        CircleImageView circleImageView = helper.itemView.findViewById(R.id.im_appicon);
//        Glide.with(MyApplication.getContext())
//                .load(item.getPicturePath())
//                .dontAnimate()
//                .placeholder(R.drawable.doctor_default_img)
//                //.diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(circleImageView);


        GlideUtils.loadDoctorImage(item.getPicturePath(),circleImageView);
        }




}
