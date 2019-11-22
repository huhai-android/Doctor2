package com.newdjk.doctor.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.newdjk.doctor.R;


/**
 * Created by huhai on 2016/6/25.
 */
public class DialogUtil {


    public static Dialog showProgressDialog(Context context, String title, String message) {
        ProgressDialog dialog = ProgressDialog.show(context, title, message);
        return dialog;
    }


    public static void logOut(final Context context, final Activity activity) {

        AlertDialog dialog = new AlertDialog.Builder(context, R.style.DialogButton).setTitle("")
                .setMessage("确认退出登录么？")
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        }).setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        })
                .create();
        dialog.show();
    }


    public static void addComment(final Context context, final Activity activity) {

        AlertDialog dialog = new AlertDialog.Builder(context, R.style.DialogButton).setTitle("")
                .setMessage("您的评论已发表，后台筛选之后显示，对所有人可见")
                .setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        })
                .create();
        dialog.show();
    }

}
