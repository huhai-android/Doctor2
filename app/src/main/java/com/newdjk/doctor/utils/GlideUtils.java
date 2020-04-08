package com.newdjk.doctor.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
    public static void loadPatientImage(String imagepath, ImageView view) {
        Glide.with(MyApplication.getContext())
                .load(imagepath).apply(new RequestOptions()
                .placeholder(R.drawable.patient_default_img).
                        error(R.drawable.patient_default_img))
                .into(view);
    }

    public static void loadDoctorImage(String imagepath, ImageView view) {
        Glide.with(MyApplication.getContext())
                .load(imagepath).
                apply(new RequestOptions()
                        .placeholder(R.drawable.doctor_default_img).
                                error(R.drawable.doctor_default_img))
                .into(view);
    }

    public static void loadCommonmage(String path, ImageView imageView) {
        Glide.with(MyApplication.getContext())
                .load(path).
                apply(new RequestOptions()
                        .placeholder(R.drawable.new_nopic).
                                error(R.drawable.new_nopic))
                .into(imageView);
    }

    public static void loadCommonmage(String path, ImageView imageView, int placeholder) {
        Glide.with(MyApplication.getContext())
                .load(path).
                apply(new RequestOptions()
                        .placeholder(placeholder).
                                error(placeholder))
                .into(imageView);
    }
}
