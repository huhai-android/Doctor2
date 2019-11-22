package com.newdjk.doctor.tools;

import android.content.Intent;
import android.util.Log;

import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.service.MyService;
import com.newdjk.doctor.ui.activity.login.LoginActivity;
import com.newdjk.doctor.utils.LogUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.LoadDialog;

public class CommonMethod<T> {
    public static boolean isCanToast = true;

    public static boolean mIsCanStartService = false;

    /**
     * 请求失败统一处理
     *
     * @param statusCode
     * @param errorMsg
     */
    public static void requestError(int statusCode, String errorMsg) {
        LogUtils.e("statusCode:" + statusCode + "    errorMsg:" + errorMsg);
        Log.e("kk", "statusCode:" + statusCode + "    errorMsg:" + errorMsg);

        if (statusCode == 401) {

            Intent intent = new Intent(MyApplication.getContext(), LoginActivity.class);
            String userName = SpUtils.getString(Contants.userName);
            String password = SpUtils.getString(Contants.Password);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            SpUtils.clear();
            SpUtils.put(Contants.IS_FIRST_USE, false);
            SpUtils.put(Contants.userName, userName + "");
            SpUtils.put(Contants.Password, password + "");
            SpUtils.put(Contants.canLogin, 1);
            MyApplication.getContext().stopService(new Intent(MyApplication.getContext(), MyService.class));
            MyApplication.getContext().startActivity(intent);
            if (isCanToast) {
                ToastUtil.setToast("登录过期，请重新登录！");
                isCanToast = false;

            }

        } else {
            ToastUtil.setToast(errorMsg);

        }
        LoadDialog.clear();
    }

}
