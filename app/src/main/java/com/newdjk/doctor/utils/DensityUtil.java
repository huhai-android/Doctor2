package com.newdjk.doctor.utils;

import android.content.Context;

import com.newdjk.doctor.MyApplication;

/**
 *
 * @author lishi
 *
 */
public class DensityUtil {

    /**
     * The context
     */
    private static Context mContext = MyApplication.getContext();

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(float dpValue) {
		final float scale = mContext.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * dp转 px
	 *
	 * @param value the value
	 * @return the int
	 */
	public static int dp2px(float value) {
		final float scale = mContext.getResources().getDisplayMetrics().densityDpi;
		return (int) (value * (scale / 160) + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(float pxValue) {
		final float scale = mContext.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
