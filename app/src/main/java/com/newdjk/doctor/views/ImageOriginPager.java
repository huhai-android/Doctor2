package com.newdjk.doctor.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.views
 *  @文件名:   ImageOriginPager
 *  @创建者:   huhai
 *  @创建时间:  2019/3/20 20:44
 *  @描述：
 */
public class ImageOriginPager extends ViewPager {

    public ImageOriginPager(Context context) {
        super (context);
    }

    public ImageOriginPager(Context context, AttributeSet attrs) {
        super (context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            super.onTouchEvent(event);
        } catch (IllegalArgumentException  e) {
            Log.e( "ImageOriginPager-error" , "IllegalArgumentException 错误被活捉了！");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        try {
            super.onInterceptTouchEvent(event);
        } catch (IllegalArgumentException  e) {
            Log.e( "ImageOriginPager-error" , "IllegalArgumentException 错误被活捉了！");
            e.printStackTrace();
        }
        return false ;
    }
}


