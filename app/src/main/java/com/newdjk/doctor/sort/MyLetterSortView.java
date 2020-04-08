package com.newdjk.doctor.sort;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.newdjk.doctor.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;


public class MyLetterSortView extends View {
    private static final String TAG = "MyLetterSortView";
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
//	public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
//			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
//			"W", "X", "Y", "Z", "#" };

    private List<String> letterListData = new ArrayList<>();

    private int choose = -1;
    private Paint paint = new Paint();

    private TextView mTextDialog;
    private int mSingleHeight;
    private int mSingleHeight1;
    private int mHeith;
    private int firstNumberPosition;

    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public MyLetterSortView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyLetterSortView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLetterSortView(Context context) {
        super(context);
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        if (letterListData.size() > 0) {
            mSingleHeight1 = height / letterListData.size();

        }

        //最大27份
        mHeith = height / 28;

        for (int i = 0; i < letterListData.size(); i++) {
            paint.setColor(Color.parseColor("#3069CF"));
            //paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            //	paint.setTextSize(PixelUtil.sp2px(12));
            paint.setTextSize(DensityUtil.dp2px(14));
            if (i == choose) {
                paint.setColor(Color.parseColor("#CCCCCC"));
                paint.setFakeBoldText(true);
            }
            float xPos = width / 2 - paint.measureText(letterListData.get(i)) / 2;
            //float yPos = singleHeight * i + singleHeight;
            firstNumberPosition = (height - letterListData.size() * mHeith) / 2;
            float yPos = (height - letterListData.size() * mHeith) / 2 + i * mHeith;
            canvas.drawText(letterListData.get(i), xPos, yPos, paint);
            paint.reset();

        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        // final int c = (int) (y / getHeight() * letterListData.size());
        int c = (int) ((y-firstNumberPosition)/mHeith)+1;
        Log.e(TAG, "onTouchingLetterChanged: " + c);
        if (c <= 0) {
            c = 0;
        }
        switch (action) {
            case MotionEvent.ACTION_UP:
               // setBackgroundDrawable(new ColorDrawable(0x00000000));
                choose = -1;//
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                if (listener != null) {
                    listener.onCancel();
                }
                break;

            default:
              //  setBackgroundResource(R.drawable.letter_sort_background);
                if (oldChoose != c) {
                    if (c >= 0 && c < letterListData.size()) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(letterListData.get(c));
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(letterListData.get(c));
                            mTextDialog.setVisibility(View.VISIBLE);
                        }

                        choose = c;
                        invalidate();
                    }
                }

                break;
        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public void setdata(List<String> letterList) {
        letterListData.clear();
        letterListData.addAll(letterList);
        requestLayout();
    }


    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
        void onCancel();
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
    }
}
