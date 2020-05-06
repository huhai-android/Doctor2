package com.newdjk.doctor.utils;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class CheckSysUtils {
    private static final String TAG = "PACheckSysUtils";

    /**
     * 检测是否root
     * modify by hiyi 20180928，移除雁联SDK的root检测，重新开发
     *
     * @param
     * @return
     */
    public static boolean isRoot() {
        String[] paths = {
                "/system/xbin/su",
                "/system/bin/su",
                "/system/sbin/su",
                "/sbin/su",
                "/vendor/bin/su",
                "/su/bin/su"
        };
        try {
            for (String path : paths) {
                if (new File(path).exists()) {
                    String execResult = exec(new String[]{"ls", "-l", path});
                    Log.d(TAG, "isRooted = " + execResult);
                    //形如(rooted)：-rwxr-xr-x root     root        75348 1970-01-01 08:32 su
                    if (TextUtils.isEmpty(execResult)
                            || execResult.indexOf("root") == execResult.lastIndexOf("root")) {
                        return false;
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String exec(String[] exec) {
        if (exec == null || exec.length <= 0) {
            return null;
        }
        StringBuilder ret = new StringBuilder();
        ProcessBuilder processBuilder = new ProcessBuilder(exec);
        try {
            Process process = processBuilder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                ret.append(line);
            }
            process.getInputStream().close();
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }


}
