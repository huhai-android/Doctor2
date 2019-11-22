package com.newdjk.doctor.utils;

import com.bumptech.glide.Glide;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.views.CircleImageView;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.utils
 *  @文件名:   GlideUtils
 *  @创建者:   huhai
 *  @创建时间:  2019/10/14 11:48
 *  @描述：
 */
public class GlideUtils {
    public static void loadPatientImage(String imagepath, CircleImageView view) {
        Glide.with(MyApplication.getContext())
                .load(imagepath)
                .centerCrop()
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .error(R.drawable.patient_default_img)
                .placeholder(R.drawable.patient_default_img)
                .into(view);
    }

    public static void loadDoctorImage(String imagepath, CircleImageView view) {
        Glide.with(MyApplication.getContext())
                .load(imagepath)
                .centerCrop()
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .error(R.drawable.patient_default_img)
                .placeholder(R.drawable.patient_default_img)
                .into(view);
    }

    public static void loadCommonmage() {

    }
}
