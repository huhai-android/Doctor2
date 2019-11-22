package com.newdjk.doctor.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.activity.SecondDiagnosisReportActivity;

public class MedicalView extends RelativeLayout {

    // 悬浮栏位置
    private final static int LEFT = 0;
    private final static int RIGHT = 1;
    private final static int TOP = 3;
    private final static int BUTTOM = 4;

    private int dpi;
    private int screenHeight;
    private int screenWidth;
    private WindowManager.LayoutParams wmParams;
    private WindowManager wm;
    private float x, y;
    private float mTouchStartX;
    private float mTouchStartY;
    private boolean isScroll;
    private long mStartTime;
    private Activity mActivity;
    private int mMedicalRecordId;

    public MedicalView(Activity activity,int medicalRecordId) {
        super(activity);
        mActivity = activity;
        mMedicalRecordId = medicalRecordId;
       View view = LayoutInflater.from(activity).inflate(R.layout.medical_view, this);
        LinearLayout iconLayout = view.findViewById(R.id.medical_layout);
        wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        //通过像素密度来设置按钮的大小
        dpi = dpi(dm.densityDpi);
        //屏宽
        screenWidth = wm.getDefaultDisplay().getWidth();
        //屏高
        screenHeight = wm.getDefaultDisplay().getHeight();
        //布局设置
        wmParams = new WindowManager.LayoutParams();
        // 设置window type
        wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        wmParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 设置Window flag
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.width = dpi;
        wmParams.height = dpi;
        wmParams.y = screenHeight/5;
        wmParams.x = screenWidth;
        wm.addView(this, wmParams);
        hide();
    }


    /**
     * 根据密度选择控件大小
     *
     */
    private int dpi(int densityDpi) {
        if (densityDpi <= 240) {
            return 72;
        } else if (densityDpi <= 320) {
            return 96;
        } else if (densityDpi <= 480) {
            return 144;
        } else if (densityDpi <= 640) {
            return 192;
        }
        return 216;
    }

    public void show() {
        if (isShown()) {
            return;
        }
        setVisibility(View.VISIBLE);
    }


    public void hide() {
        setVisibility(View.GONE);
    }

    public void destory() {
        hide();
        wm.removeViewImmediate(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取相对屏幕的坐标， 以屏幕左上角为原点
        x = event.getRawX();
        y = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartTime = System.currentTimeMillis();
                // setBackgroundDrawable(openDrawable);
                // invalidate();
                // 获取相对View的坐标，即以此View左上角为原点
                mTouchStartX = event.getX();
                mTouchStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isScroll) {
                    updateViewPosition();
                } else {
                    // 当前不处于连续滑动状态 则滑动小于图标1/3则不滑动
                    if (Math.abs(mTouchStartX - event.getX()) > dpi / 3
                            || Math.abs(mTouchStartY - event.getY()) > dpi / 3) {
                        updateViewPosition();
                    } else {
                        break;
                    }
                }
                isScroll = true;
                break;
            case MotionEvent.ACTION_UP:
                if  (System.currentTimeMillis() - mStartTime <300) {
                    Intent systemSettingIntent = new Intent(mActivity, SecondDiagnosisReportActivity.class);
                    systemSettingIntent.putExtra("MedicalRecordId",mMedicalRecordId);
                    mActivity.startActivity(systemSettingIntent);
                }
                // 拖动
                if (isScroll) {
                    autoView();
                    // setBackgroundDrawable(closeDrawable);
                    // invalidate();
                } else {
                    // 当前显示功能区，则隐藏
                    // setBackgroundDrawable(openDrawable);
                    // invalidate();

                }
                isScroll = false;
                mTouchStartX = mTouchStartY = 0;
                break;
        }
        return true;
    }

    /**
     * 自动移动位置
     */
    private void autoView() {
        // 得到view在屏幕中的位置
        int[] location = new int[2];
        getLocationOnScreen(location);
        //左侧
        if (location[0] < screenWidth / 2 - getWidth() / 2) {
            updateViewPosition(LEFT);
        } else {
            updateViewPosition(RIGHT);
        }
    }

    /**
     * 手指释放更新悬浮窗位置
     *
     */
    private void updateViewPosition(int l) {
        switch (l) {
            case LEFT:
                wmParams.x = 0;
                break;
            case RIGHT:
                int x = screenWidth - dpi;
                wmParams.x = x;
                break;
            case TOP:
                wmParams.y = 0;
                break;
            case BUTTOM:
                wmParams.y = screenHeight - dpi;
                break;
        }
        wm.updateViewLayout(this, wmParams);
    }

    // 更新浮动窗口位置参数
    private void updateViewPosition() {
        wmParams.x = (int) (x - mTouchStartX);
        //是否存在状态栏（提升滑动效果）
        // 不设置为全屏（状态栏存在） 标题栏是屏幕的1/25
        wmParams.y = (int) (y - mTouchStartY - screenHeight / 25);
        wm.updateViewLayout(this, wmParams);
    }
}
