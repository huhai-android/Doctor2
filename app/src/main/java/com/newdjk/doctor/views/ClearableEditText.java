package com.newdjk.doctor.views;


import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.newdjk.doctor.R;


/**
 * 带有删除按钮的EditText
 * Created by hzwangchenyan on 2016/3/5.
 */
public class ClearableEditText extends AppCompatEditText {
    private static final int EXTRA_AREA = 30;//额外点击范围
    //默认图标
    private int delIconId = R.mipmap.ic_edit_text_clear;
    private Drawable delDrawalbe;
    private boolean isIconShow;


    public ClearableEditText(Context context) {
        this(context, null);
    }


    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        delDrawalbe = ContextCompat.getDrawable(context, delIconId);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    isIconShow = true;
                    setCompoundDrawablesWithIntrinsicBounds(null, null, delDrawalbe, null);
                } else {
                    isIconShow = false;
                    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isIconShow) {
            Rect bounds = getCompoundDrawables()[2].getBounds();
            int x = (int) event.getX();
            int rectX = getWidth() - bounds.width() - EXTRA_AREA - getPaddingRight();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (x > rectX) {
                        clearText();
                        return true;
                    }
                    break;
            }

        }
        return super.onTouchEvent(event);
    }

    private void clearText() {
        setText("");
        isIconShow = false;
        //获取输入焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        findFocus();
    }

}
