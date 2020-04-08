package com.newdjk.doctor.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.login.Authentication1Activity;
import com.newdjk.doctor.ui.entity.YWXListenerEntity;
import com.newdjk.doctor.views.GroupButtonDialog;
import com.newdjk.doctor.views.RememberPasswordDialog;
import com.tencent.TIMConversation;

import java.io.File;

import cn.org.bjca.sdk.core.kit.BJCASDK;
import cn.org.bjca.sdk.core.kit.YWXListener;

/**
 * 关于App的工具类。
 */
public class AppUtils {
    public static int STATUS;

    /**
     * 获取屏幕尺寸
     */
    public static Point getScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            return new Point(display.getWidth(), display.getHeight());
        } else {
            Point point = new Point();
            display.getSize(point);
            return point;
        }
    }

    @SuppressLint("MissingPermission")
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static long getUnreadMessageNum(TIMConversation timConversation) {
        Log.i("zdp", "num=" + timConversation.getUnreadMessageNum());

        return timConversation.getUnreadMessageNum();
    }

    /**
     * 获取SD卡路径
     *
     * @return 如果sd卡不存在则返回null
     */
    public static File getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir;
    }

    /**
     * 显示软键盘
     */
    public static void openSoftInput(EditText et) {
        InputMethodManager inputMethodManager = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(et, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(EditText et) {
        InputMethodManager inputMethodManager = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 安装文件
     *
     * @param data
     */
    public static void promptInstall(Context context, Uri data) {
        Intent promptInstall = new Intent(Intent.ACTION_VIEW)
                .setDataAndType(data, "application/vnd.android.package-archive");
        // FLAG_ACTIVITY_NEW_TASK 可以保证安装成功时可以正常打开 app
        promptInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(promptInstall);
    }

    public static void copy2clipboard(Context context, String text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("clip", text);
        cm.setPrimaryClip(clip);
    }

    /**
     * 获取版本名称
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 获取版本号
     */
    public static int getAppVersionCode(Context context) {
        int versioncode = -1;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versioncode;
    }

    /**
     * @param var 点击标识
     * @param ctx 上下文
     */
    public static GroupButtonDialog mDialog;

    public static void checkAuthenStatus(final int var, final Context ctx) {

        final int s = SpUtils.getInt(Contants.Status, 0);
        String content = "";
        if (var == 4) {
            content = "您还未进行资格认证，部分功能将被限制。";
        } else {
            if (s == 0) {//未认证
                content = "您还未进行资格认证，部分功能将被限制。";
            } else if (s == 2) {//失败
                content = "您的资料审核不通过，请重新认证";
            } else if (s == 3) {
                content = "您的资料正在审核中，请耐心等待";
            }
        }

        mDialog = new GroupButtonDialog(ctx);
        mDialog.show("温馨提示", var,content, new GroupButtonDialog.DialogListener() {
            @Override
            public void cancel() {

            }

            @Override
            public void confirm() {
                if (s == 0 || s == 2) {
                    Intent intent = new Intent(ctx, Authentication1Activity.class);
                    ctx.startActivity(intent);
                } else {

                }
            }
        });


    }

    public static void jumpAuthActivity(Context ctx) {
        Intent intent = new Intent(ctx, Authentication1Activity.class);
        intent.putExtra("tag", 1);
        intent.putExtra("tip", 2);
        ctx.startActivity(intent);
    }

    public static void showRememberDialog(final Activity context) {
        RememberPasswordDialog dialog = new RememberPasswordDialog(context);
        dialog.show("", "", new RememberPasswordDialog.DialogListener() {
            @Override
            public void cancel() {

            }

            @Override
            public void confirm(int keeDay) {
                BJCASDK.getInstance().keepPin(context, Contants.clientId, keeDay, new YWXListener() {
                    @Override
                    public void callback(String msg) {
                        YWXListenerEntity yWXListenerEntity = new Gson().fromJson(msg, YWXListenerEntity.class);
                        String status = yWXListenerEntity.getStatus();
                        String message = yWXListenerEntity.getMessage();
                        if (status.equals("0")) {
                            Toast.makeText(context, "设置免密成功", Toast.LENGTH_SHORT).show();
                        } else {
                            // Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            ToastUtil.setToast("记住密码失效，请重试！+(" + status + ")");

                        }
                    }
                });
            }
        });
    }
}
