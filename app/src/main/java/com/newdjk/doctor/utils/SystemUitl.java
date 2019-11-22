package com.newdjk.doctor.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Struggle on 2018/10/14.
 */

public class SystemUitl {
    public static String[] packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        String code = null;
        String versionName = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = String.valueOf(info.versionCode);
            versionName = info.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return new String[]{code, versionName};
    }

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }
}
