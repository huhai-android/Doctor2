package com.newdjk.doctor.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.newdjk.doctor.R;
import com.youth.banner.loader.ImageLoaderInterface;


/**
 * Created by WQ on 2017/5/5.
 */

public class GlideMediaLoader implements ImageLoaderInterface {

    public static void load(Object context, View imgview, String path, int placeholder) {
        if (!String.valueOf(path).startsWith("http")) {
//             path = "file://" + path;
        }
        RequestOptions options = new RequestOptions()
                .placeholder(placeholder)  //加载成功之前占位图
                .error(placeholder) ;   //加载错误之后的错误图


        Glide.with((Context) context)
                .load(path)
                .apply(options)
                .into((ImageView) imgview);



    }
    public static void loadRezise(Context context, ImageView imgview, String path, int placeholder) {
        Log.d("图片缩放", " " + path);
        RequestOptions options = new RequestOptions()
                .placeholder(placeholder)  //加载成功之前占位图
                .error(placeholder)    //加载错误之后的错误图
                //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
                .fitCenter();

        Glide.with((Context) context)
                .load(path)
                .apply(options)
                .into((ImageView) imgview);
    }

    public static void load(Object context, View imgview, String path) {
        load(context, imgview, path, R.drawable.new_nopic);
    }

    public static void loadImage(Object context, View imgview, String path) {
        load(context, imgview, path, R.drawable.patient_default_img);
    }

    static RequestManager with(Object context) {
        if (context instanceof Activity) {
            return Glide.with((Activity) context);
        } else if (context instanceof Fragment) {
            return Glide.with((Fragment) context);
        } else if (context instanceof Context) {
            return Glide.with((Context) context);
        }
        return null;
    }

    @Override
    public void displayImage(Context context, Object path, View imageView) {
        load(context, imageView, (String) path);
    }

    @Override
    public View createImageView(Context context) {
        ImageView view= (ImageView) View.inflate(context,R.layout.image,null);
        return view;
    }
}
