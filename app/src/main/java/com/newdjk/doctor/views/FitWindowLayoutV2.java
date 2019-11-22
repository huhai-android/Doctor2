package com.newdjk.doctor.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import android.widget.LinearLayout;

import com.newdjk.doctor.R;


/**
 * Created by huhai on 2017/11/5.
 * 上传代码
 */

public class FitWindowLayoutV2 extends LinearLayout {

    private Drawable mStatusBarBackground;
    private Object mLastInsets;
    private boolean mDrawStatusBarBackground;

//    private static final int[] THEME_ATTRS = {
//            android.R.attr.statusBarColor
//    };

    private static final int[] STATUS_BAR_ATTRS_KITKAT = {
            R.attr.status_bar_color
    };

    public FitWindowLayoutV2(Context context) {
        this(context, null);
    }

    public FitWindowLayoutV2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FitWindowLayoutV2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && (ViewCompat.getFitsSystemWindows(this))) {
                    setOnApplyWindowInsetsListener(new OnApplyWindowInsetsListener() {
                        @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
                        @Override
                        public WindowInsets onApplyWindowInsets(View view, WindowInsets insets) {
                            setChildInsets(insets, insets.getSystemWindowInsetTop() > 0);
                            return FitWindowLayoutV2.this.onApplyWindowInsets(insets);
                        }
                    });

                    if (mStatusBarBackground == null) {
                        final TypedArray a = context.obtainStyledAttributes(new int[]{android.R.attr.statusBarColor});
                        try {
                            mStatusBarBackground = a.getDrawable(0);
                        } finally {
                            a.recycle();
                        }
                    }

                    activity.getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.white));

                    // Now set the sys ui flags to enable us to lay out in the window insets
                int windowLightStatusBar;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int[] windowLightStatusBarAttr = new int[]{
                            android.R.attr.windowLightStatusBar
                    };
                    TypedArray windowLightStatusBarTypedArray = context.obtainStyledAttributes(windowLightStatusBarAttr);
                    try {
                        boolean aBoolean = windowLightStatusBarTypedArray.getBoolean(0, false);
                        if (aBoolean) {
                            windowLightStatusBar = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                        } else {
                            windowLightStatusBar = getSystemUiVisibility() & View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                        }
                    } catch (Exception e){
                        windowLightStatusBar = 0;
                    } finally {
                        windowLightStatusBarTypedArray.recycle();
                    }
                } else {
                    windowLightStatusBar = 0;
                }

                setSystemUiVisibility(windowLightStatusBar | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && (ViewCompat.getFitsSystemWindows(this))){
                // Now set the sys ui flags to enable us to lay out in the window insets
                final TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.FitWindowLayoutV2, defStyleAttr, 0);
                try {
                    int statusBarColor = a.getColor(R.styleable.FitWindowLayoutV2_status_bar_color, -1);
                    if (statusBarColor == -1) {
                        final TypedArray barColorKitkatAttr = context.obtainStyledAttributes(STATUS_BAR_ATTRS_KITKAT);
                        try {
                            statusBarColor = barColorKitkatAttr.getColor(0, -1);
                        } finally {
                            barColorKitkatAttr.recycle();
                        }
                    }

                    if (statusBarColor != -1) {
                        mStatusBarBackground = new ColorDrawable(statusBarColor);
                    }


                } catch (Exception e){
                } finally {
                    a.recycle();
                }

                setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        }
    }

    @Override
    protected boolean fitSystemWindows(Rect insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return super.fitSystemWindows(insets);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            setChildInsets(new Rect(insets), insets.top > 0);
            return super.fitSystemWindows(insets);
        }
        return super.fitSystemWindows(insets);
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        return super.onApplyWindowInsets(insets);
    }

    public void setStatusBarBackground(@ColorInt int color) {
        mStatusBarBackground = new ColorDrawable(color);
    }

    public void setChildInsets(Object insets, boolean draw) {
        mLastInsets = insets;
        mDrawStatusBarBackground = draw;
        setWillNotDraw(!draw && getBackground() == null);
        requestLayout();
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        if (mDrawStatusBarBackground && mStatusBarBackground != null) {
            final int inset;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                inset = mLastInsets != null
                        ? ((WindowInsets) mLastInsets).getSystemWindowInsetTop() : 0;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                inset = 0;
                final int tempInset = mLastInsets != null ? ((Rect)mLastInsets).top : 0;

                mStatusBarBackground.setBounds(0, 0, getWidth(), tempInset);
                mStatusBarBackground.draw(c);
            } else {
                inset = 0;
            }
            if (inset > 0) {
                mStatusBarBackground.setBounds(0, 0, getWidth(), inset);
                mStatusBarBackground.draw(c);
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mLastInsets == null && ViewCompat.getFitsSystemWindows(this)) {
            // We're set to fitSystemWindows but we haven't had any insets yet...
            // We should request a new dispatch of window insets
            ViewCompat.requestApplyInsets(this);
        }
    }

    private void test(){

    }
}
