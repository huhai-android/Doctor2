package com.newdjk.doctor.ui.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.activity.ShowOriginPictureActivity;
import com.newdjk.doctor.ui.entity.LookMedicalEntity;
import com.newdjk.doctor.utils.GlideUtils;

import java.util.List;


public class MyDiagnoseImageAdapter extends BaseQuickAdapter<LookMedicalEntity.PatientMedicalsBean.ImagePahtsBean, BaseViewHolder> {
    private String TAG = "MyDiagnoseImageAdapter";
    private Gson mGson;
    private List<LookMedicalEntity.PatientMedicalsBean.ImagePahtsBean> datalist;


    public MyDiagnoseImageAdapter(@Nullable List<LookMedicalEntity.PatientMedicalsBean.ImagePahtsBean> data) {
        super(R.layout.diagnose_image_item, data);

        datalist = data;
        mGson = new Gson();
    }

    @Override
    protected void convert(BaseViewHolder helper, final LookMedicalEntity.PatientMedicalsBean.ImagePahtsBean item) {

        try {
            Log.d(TAG,helper.getAdapterPosition()+"");
            ImageView imageView = helper.itemView.findViewById(R.id.medical_picture);
//            Glide.with(MyApplication.getContext())
//                    .load(item.getImagePath())
//                    .centerCrop()
//                    .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
//                    .error(R.drawable.no_medical_tip)
//                    .placeholder(R.drawable.no_medical_tip)
//                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(imageView);

            GlideUtils.loadCommonmage(item.getImagePath(),imageView,R.drawable.no_medical_tip);
            helper.addOnClickListener(R.id.medical_picture);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ShowOriginPictureActivity.class);
                    intent.putExtra("path", item.getImagePath());
                    mContext.startActivity(intent);
                }
            });

        } catch (Exception e) {
            Log.i("ConsultMessageAdapter", "e=" + e.toString());
            e.printStackTrace();
        }

    }
}
