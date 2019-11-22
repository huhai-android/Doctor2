package com.newdjk.doctor.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.newdjk.doctor.R;

public class ButtonView extends AppCompatTextView {
    private float mRadius;
    private int mStroke;
    private int strokeColor;
    private int solidColor;
    public ButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }



    public ButtonView(Context context) {
        super(context);
    }

    public ButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyTextView, 0, 0);
        strokeColor = typedArray.getColor(R.styleable.MyTextView_stroke_color, 0);
        solidColor = typedArray.getColor(R.styleable.MyTextView_solid_color, 0);
        mRadius = typedArray.getDimensionPixelSize(R.styleable.MyTextView_radius, 0);
        mStroke = typedArray.getDimensionPixelSize(R.styleable.MyTextView_stroke,0);
        typedArray.recycle();
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(mRadius);
        drawable.setStroke(mStroke,strokeColor);
        drawable.setColor(solidColor);
        this.setBackgroundDrawable(drawable);
    }
}
