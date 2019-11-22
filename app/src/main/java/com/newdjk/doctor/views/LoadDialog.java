package com.newdjk.doctor.views;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.utils.LogUtils;


/**
 * 加载等待效果
 * Created by jhon on 2017/3/20 0020.
 */

public class LoadDialog extends Dialog {
    private Context context;
    private static LoadDialog dialog;
    private ImageView ivProgress;

    private LoadDialog(Context context) {
        super(context);
        this.context = context;
    }

    private LoadDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;

    }

    //显示dialog的方法
    public static void show(Context context) {
        try {
            if (dialog != null) {
                dialog.dismiss();
            }
            dialog = new LoadDialog(context, R.style.loadDialog);//dialog样式
            View view = View.inflate(MyApplication.getContext(), R.layout.dialog_load, null);
//            ImageView img = view.findViewById(R.id.ivProgress);
            dialog.setContentView(view);//dialog布局文件
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);//点击外部不允许关闭dialog
            dialog.show();
        } catch (Exception e) {
            LogUtils.e("LoadDialog  error!!!");
        }
    }

    //显示dialog的方法
    public static void show(Context context, String content) {
        try {
            if (dialog != null) {
                dialog.dismiss();
            }
            dialog = new LoadDialog(context, R.style.loadDialog);//dialog样式
            View view = View.inflate(MyApplication.getContext(), R.layout.dialog_load, null);
//            ImageView img = view.findViewById(R.id.ivProgress);
            TextView tvText = view.findViewById(R.id.tvText);
            tvText.setText(content);
            dialog.setContentView(view);//dialog布局文件
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);//点击外部不允许关闭dialog
            dialog.show();
        } catch (Exception e) {
            LogUtils.e("LoadDialog  error!!!");
        }
    }

    //关闭dialog的方法
    public static void clear() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && dialog != null) {
            ivProgress = dialog.findViewById(R.id.ivProgress);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.shape_dialog_bg);
            ivProgress.startAnimation(animation);
        }
    }
}
