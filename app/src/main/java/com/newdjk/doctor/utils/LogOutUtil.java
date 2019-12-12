package com.newdjk.doctor.utils;

import android.content.Intent;
import android.util.Log;

import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.service.MyService;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.login.LoginActivity;
import com.newdjk.doctor.views.LoadDialog;
import com.tencent.TIMCallBack;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;


public class LogOutUtil implements TIMCallBack {
    private BasicActivity mActivity;

    @Override
    public void onError(int i, String s) {
        Log.i("LogOutUtil", "onError=" + i + ",s=" + s);
        clearAllAndJump();
    }

    @Override
    public void onSuccess() {
        Log.i("LogOutUtil", "onSuccess");
        clearAllAndJump();
    }

    private static class SingletonHolder {
        private static LogOutUtil instance = new LogOutUtil();
    }

    private LogOutUtil() {
    }

    public static LogOutUtil getInstance() {
        return SingletonHolder.instance;
    }

    public void loginOut(BasicActivity activity, boolean isLogin) {
        mActivity = activity;
        if (mActivity != null) {
            activity.loading(true);
            if (isLogin) {
                //   TIMManager.getInstance().logout(this);
                Intent intent = new Intent(mActivity, MyService.class);
                mActivity.stopService(intent);
                iLiveLogout();
                clearAllAndJump();
            } else {
                clearAllAndJump();
            }
        } else {
            LogUtils.e("no activity result");

        }

    }

    public void iLiveLogout() {
        //TODO 新方式登出ILiveSDK
        ILiveLoginManager.getInstance().iLiveLogout(new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                Log.i("LogOutUtil", "iLiveLogoutdata=" + data.toString());
                clearAllAndJump();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                clearAllAndJump();
                Log.i("LogOutUtil", "iLiveLogoutonError=" + errCode + ",errMsg=" + errMsg);
            }
        });
    }


    protected void clearAllAndJump() {

        try {
            if (mActivity != null) {
                LoadDialog.clear();
                String userName = SpUtils.getString(Contants.userName);
                String password = SpUtils.getString(Contants.Password);
                Intent loginOutIntent = new Intent(mActivity, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                SpUtils.clear();
                SpUtils.put(Contants.IS_FIRST_USE, false);
                SpUtils.put(Contants.userName, userName+"");
                SpUtils.put(Contants.Password, password+"");
                SpUtils.put(Contants.isShowIncome, 0);
                SpUtils.put(Contants.Haslogin, true);
                mActivity.startActivity(loginOutIntent);
            }

        }catch (Exception e){

        }

    }

}
