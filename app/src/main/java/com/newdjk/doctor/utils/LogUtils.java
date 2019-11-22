package com.newdjk.doctor.utils;

import android.util.Log;

import com.newdjk.doctor.MyApplication;


/**
 * Created by jhon on 2016/7/29.
 */

public class LogUtils {

    public static boolean isDebug = MyApplication.LOG_DEBUG;

    /**
     * 打印一个debug等级的 log
     */
    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d("" + tag, msg);
        }
    }

    /**
     * 打印一个debug等级的 log
     */
    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e("" + tag, msg);
        }
    }

    /**
     * 打印一个debug等级的 log
     */
    public static void e(String msg) {
        if (isDebug) {
            Log.e("", msg);
        }
    }

    /**
     * 打印一个debug等级的 log
     */
    public static void e(Class cls, String msg) {
        if (isDebug) {
            Log.e("jiemo_" + cls.getSimpleName(), msg);
        }
    }



}
