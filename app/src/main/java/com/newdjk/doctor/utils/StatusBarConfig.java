package com.newdjk.doctor.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import java.lang.reflect.Method;

/**
 * Created by cnsunrun on 2017-07-12.
 * <p>
 * 解决沉浸式状态栏问题
 */

public class StatusBarConfig {

    // For more information, see https://code.google.com/p/android/issues/detail?id=5497
    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

    private Context context;

    public static void assistActivity(Activity activity) {
        new StatusBarConfig(activity);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;

    private StatusBarConfig(Activity activity) {
        this.context = activity;
        FrameLayout content = activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // 软键盘弹出
                int navigationBarHeight = getNavigationBarHeight();
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference + (navigationBarHeight / 2);

            } else {
                // 软键盘隐藏
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    if (checkDeviceHasNavigationBarByLollipop(context)) {
                        int navigationBarHeight = getNavigationBarHeight();
                        frameLayoutParams.height = usableHeightSansKeyboard - navigationBarHeight;
                    } else {
                        frameLayoutParams.height = usableHeightSansKeyboard;
                    }
                }else{
                    if (checkDeviceHasNavigationBarByKitkat(context)) {
                        int navigationBarHeight = getNavigationBarHeight();
                        frameLayoutParams.height = usableHeightSansKeyboard - navigationBarHeight;
                    } else {
                        frameLayoutParams.height = usableHeightSansKeyboard;
                    }
                }
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);// 全屏模式下： return r.bottom
    }

    private int getNavigationBarHeight() {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }



    public static boolean checkDeviceHasNavigationBarByLollipop(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }

    @SuppressLint("NewApi")
    public static boolean checkDeviceHasNavigationBarByKitkat(Context activity) {
        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(activity)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        return !hasMenuKey && !hasBackKey;
    }

}
