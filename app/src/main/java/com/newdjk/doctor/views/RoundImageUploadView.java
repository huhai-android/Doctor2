package com.newdjk.doctor.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.newdjk.doctor.R;


/**
 * 圆角的图片上传控件
 * Created by WQ on 2017/5/16.
 */

public class RoundImageUploadView extends MultiImageUploadView {
    public RoundImageUploadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RoundImageUploadView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundImageUploadView(Context context) {
        super(context);
    }

  //  @Override
    protected ImageView createImageView(Context context) {
        RoundImageView roundImageView = new RoundImageView(context);
        roundImageView.setRadius(DisplayUtil.dp2px(context, 5));
        return roundImageView;
    }

    @Override
    public void updateHandlerStatus() {
        super.updateHandlerStatus();
        if (textView != null) {
            textView.setText(String.format("(%d/%d)", getFiles().size(), getMax()));
            textView.setLayoutParams(getParams());//重算宽高
        }
    }

    TextView textView;
    boolean isShowNumber = false;

    public void setShowNumber() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                isShowNumber = true;
                textView = new TextView(getContext());
                textView.setGravity(Gravity.BOTTOM);
                textView.setTextColor(getResources().getColor(R.color.font_gray_3));
                addView(textView, getParams());
                textView.setText(String.format("(%d/%d)", getFiles().size(), getMax()));
            }
        }, 200);

    }
}
