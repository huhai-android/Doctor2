package com.newdjk.doctor.utils;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.CertInfoEntity;
import com.newdjk.doctor.ui.entity.YWXListenerEntity;
import com.newdjk.doctor.views.UpdateCertDialog;

import java.text.SimpleDateFormat;

import cn.org.bjca.sdk.core.kit.BJCASDK;
import cn.org.bjca.sdk.core.kit.YWXListener;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.utils
 *  @文件名:   CertUtis
 *  @创建者:   huhai
 *  @创建时间:  2019/11/6 14:06
 *  @描述：
 */
public class CertUtis {
    public static Gson mGson = new Gson();

    public static void updateCert(final Activity activity) {

        BJCASDK.getInstance().certUpdate(activity, Contants.clientId, new YWXListener() {
            @Override
            public void callback(String s) {
                Log.d("chat", "证书更新" + s);
                YWXListenerEntity yWXListenerEntity = mGson.fromJson(s, YWXListenerEntity.class);
                String message = yWXListenerEntity.getMessage();
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static void getUserInfo(final Activity activity) {
        boolean isExists = BJCASDK.getInstance().existsCert(activity);
        if (isExists) {
            BJCASDK.getInstance().getUserInfo(activity, Contants.clientId, new YWXListener() {
                @Override
                public void callback(String s) {
                    Log.d("chat", "证书" + s);

                    if (s != null && !s.equals("")) {
                        try {
                            Gson mg = new Gson();
                            CertInfoEntity certInfoEntity = mg.fromJson(s, CertInfoEntity.class);
                            //判断证书是否过期
                            if (certInfoEntity != null) {
                                Long endtime = date2TimeStamp(certInfoEntity.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                                Long nowtime = date2TimeStamp(certInfoEntity.getNowTime(), "yyyy-MM-dd HH:mm:ss");
                                Log.d("chat", "证书" + endtime + "  " + nowtime);
//                                if (endtime != 0 && nowtime != 0) {
//                                    if (endtime < nowtime + 7 * 24 * 3600 * 1000) {
//
//                                        showCertUpdateDialog(activity);
//
//                                    }
//                                }
//
//                                if (!certInfoEntity.isDeviceFit()) {
//                                    BJCASDK.getInstance().clearCert(activity);
//                                }
                            }


                        } catch (Exception e) {

                        }
                    }

                }
            });
        }


    }

    public static void showCertUpdateDialog(final Activity activity) {

        UpdateCertDialog mDialog = new UpdateCertDialog(activity);
        mDialog.show("", "", new UpdateCertDialog.DialogListener() {
            @Override
            public void confirm() {
                updateCert(activity);

            }
        });

    }

    public static long date2TimeStamp(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
