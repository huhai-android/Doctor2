package com.newdjk.doctor.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.newdjk.doctor.R;


public class DrawableEditText extends AppCompatEditText {

    private int widthPx;
    private int heightPx;

    public DrawableEditText(Context context) {
        super(context);
    }

    public DrawableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context, attrs);
    }

    public DrawableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.DrawableTextView);

        widthPx = ta.getDimensionPixelSize(
                R.styleable.DrawableTextView_drawableWidth, -1);
        heightPx = ta.getDimensionPixelSize(
                R.styleable.DrawableTextView_drawableHeight, -1);

        // 左右上下四个图片
        Drawable[] drawables = getCompoundDrawables();

        setDrawables(drawables);

        ta.recycle();
    }

    /**
     * 左上右下四个图片,只会取一个方向图片使用
     */
    public void setDrawables(Drawable[] drawables) {
        // 左右上下四个图片
        Drawable drawable = null;
        // 如果其中一个方向有图片,获取之
        for (int i = 0; i < drawables.length; i++) {
            if (drawables[i] != null) {
                drawable = drawables[i];
                break;
            }
        }

        // 设置宽高
        if (drawable != null && widthPx != -1 && heightPx != -1) {
            drawable.setBounds(0, 0, widthPx, heightPx);
        }

        // 将图片放回到TextView中
        setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

}