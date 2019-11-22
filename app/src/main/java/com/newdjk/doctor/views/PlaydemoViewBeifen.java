package com.newdjk.doctor.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.newdjk.doctor.R;
import com.newdjk.doctor.utils.DensityUtil;
import com.newdjk.doctor.utils.Listener;

import java.math.BigDecimal;

/**
 * 胎心曲线图
 */
public class PlaydemoViewBeifen extends View {
    private static final String TAG = "PlaydemoView";
    private Context context;
    private static final int NONE = 0;
    private static final int ZOOM = 1;
    private static final int ZOOM_FINISH = 2;
    private static final int SCROLL = 3;
    private float oldDist = 1f;
    private int mode = NONE;
    private boolean reloadData = false;
    /**
     * 起始点
     */
    private float START_X_NUM = 3f;
    private int lineWidth;
    /**
     * 一共划分多少份
     */
    private final float hNum = 17.0f;
    private int wNum = 9;
    /**
     * 屏幕宽度
     */
    private int screenWidth;
    private float stepWidth;
    /**
     * 屏幕高度
     */
    private int screenHeight;
    /**
     * 一份高度
     */
    private float oneHeight;
    /**
     * 一份宽度
     */
//    private float oneWidth;
    private int oneWidth = DensityUtil.dip2px(58);
    // fhr 曲线断线阈值
    private final int breakValue = 30;
    // 以下是监护数据的最值
    private final int fhrMax = 210;
    private final int fhrMin = 40;
    private final int tocoMax = 100;
    private final int tocoMin = 0;
    // 以下是网络标志在屏幕位置的值
    private float fhrTop;
    private float fhrBottom;
    private float fhrVer;
    private float tocoTop;
    private float tocoBottom;
    private float tocoVer;

    private Paint mFhr1Paint;
    private Paint mVerticalLine;
    private Paint mBoldPaint;
    private Paint mThinPaint;
    private Paint areaPaint;
    private Paint timePaint;
    private Listener.TimeData[] datas;
    private MediaPlayer mediaPlayer;
    private Bitmap beatZdbmp;
    private Bitmap tocoResetBmp;
    /**
     * 多少个像素显示一个数据
     */
    private float wide = 2;
    /**
     * 一屏曲线的数据总长度 ，一屏有三分钟的数据，共 360 个数据
     */
    private int maxSize = 360;
    Listener.TimeData[] demoData = null;
    private boolean isToastMax;
    private boolean isToastMin;
    // 屏幕宽像素
    private int widthPixels;

    private void initDemoData() {
        demoData = new Listener.TimeData[1];
        demoData[0] = new Listener.TimeData();
        demoData[0].heartRate = 0;
        demoData[0].tocoWave = 0;
        demoData[0].afmWave = 0;
        demoData[0].status1 = 0;
        demoData[0].status2 = 0;
        demoData[0].beatZd = 0;
    }

    public PlaydemoViewBeifen(Context context) {
        this(context, null, 0);
        this.context = context;
        initDemoData();
        initBitmap();
    }

    public PlaydemoViewBeifen(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
        initDemoData();
        initBitmap();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public PlaydemoViewBeifen(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initDemoData();
        initBitmap();
    }

    private void initBitmap(){
        float width = context.getResources().getDimension(R.dimen.line_hight);
        // 粗直线
        mBoldPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBoldPaint.setColor(context.getResources().getColor(R.color.lightgray));
        mBoldPaint.setStrokeWidth(1.5f);
        // 细直线
        mThinPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mThinPaint.setColor(context.getResources().getColor(R.color.black));
        mThinPaint.setStrokeWidth(0.08f);
        // 胎心宫缩曲线
        mFhr1Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFhr1Paint.setColor(context.getResources().getColor(R.color.black));
        mFhr1Paint.setStrokeWidth(width);
        // 一条垂直竖线
        mVerticalLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mVerticalLine.setColor(context.getResources().getColor(R.color.lightskyblue));
        mVerticalLine.setStrokeWidth(width);
        // 手动胎动、宫缩标识图标
        beatZdbmp = BitmapFactory.decodeResource(getResources(), R.drawable.bpm);
        tocoResetBmp = BitmapFactory.decodeResource(getResources(), R.drawable.toco_reset_mark);
        // 110-160区域背景
        areaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        areaPaint.setColor(context.getResources().getColor(R.color.mistyrose));
        // 时间刻度
        DisplayMetrics dm = getResources().getDisplayMetrics();//获取显示屏属性
        widthPixels = dm.widthPixels;
        timePaint = new Paint();
        if (widthPixels < 800) {
            timePaint.setTextSize(18);
        } else {
            timePaint.setTextSize(28);
        }
        timePaint.setColor(Color.BLACK);
        timePaint.setTextAlign(Paint.Align.CENTER);
        timePaint.setStrokeWidth(2);
    }

    public void setDatas(Listener.TimeData[] datas) {
        reloadData = true;
        if (datas == null)
            this.datas = demoData;
        else
            this.datas = datas;
        invalidate();
        super.requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if ((changed) || (reloadData)) {
            this.screenWidth = right - left;
            this.screenHeight = bottom - top;
            this.screenWidth = oneWidth * 9;
            stepWidth = oneWidth * 3.0f;
            maxSize = 9 * 40;
            fhrTop = this.screenHeight * 18 / 760;
            fhrBottom = this.screenHeight * 700 / 760;
            fhrVer = (fhrBottom - fhrTop) / (fhrMax - fhrMin);
            tocoTop = this.screenHeight * 530 / 760;
            tocoBottom = this.screenHeight * 700 / 760;
            tocoVer = (tocoBottom - tocoTop) / (tocoMax - tocoMin);
            Log.d(TAG,"屏幕高度"+screenHeight+" bottom"+bottom+" top"+top+"  fhrTop"+fhrTop+" fhrBottom"+fhrBottom);
            oneHeight = (float) screenHeight / hNum;
            wide = 1.0f * screenWidth / maxSize;
            if (datas != null) {
                lineWidth = (int) ((datas.length - 1) * wide);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        /** 110-160区域背景*/
        canvas.drawRect(stepWidth/ 3.0f, fhrToValue(160), screenWidth*50, fhrToValue(110), areaPaint);

        int offX = getScrollX();
        int startIndex = (int) (offX / stepWidth);
        int num = (int) (screenWidth * 2 / stepWidth) / 2;
        int multiple = (int) (screenWidth / 3 / stepWidth);
        for (int i = startIndex - num < 0 ? 0 : startIndex - num; i < startIndex + num; i++) {
            if (i % multiple == 0) {
                // 时间坐标（3厘米/分钟，一格大约1厘米）
                canvas.drawText(String.valueOf(i) + "min", screenWidth / 3.0f + stepWidth * i, fhrToValue(32), timePaint);
            }

            // 垂直竖线（3厘米/分钟，一格大约1厘米）
            canvas.drawLine(stepWidth + stepWidth*i, fhrToValue(210), stepWidth + stepWidth*i, fhrToValue(fhrMin), mBoldPaint);
            canvas.drawLine(stepWidth / 3.0f *1 + stepWidth*i, fhrToValue(210), stepWidth / 3.0f *1 + stepWidth*i, fhrToValue(fhrMin), mBoldPaint);
            canvas.drawLine(stepWidth / 3.0f *2 + stepWidth*i, fhrToValue(210), stepWidth / 3.0f *2+ stepWidth*i, fhrToValue(fhrMin), mBoldPaint);

//            canvas.drawLine(stepWidth + stepWidth*i, tocoToValue(100), stepWidth + stepWidth*i, tocoToValue(0), mBoldPaint);
//            canvas.drawLine(stepWidth / 3.0f *1 + stepWidth*i, tocoToValue(100), stepWidth / 3.0f *1 + stepWidth*i, tocoToValue(0), mBoldPaint);
//            canvas.drawLine(stepWidth / 3.0f *2 + stepWidth*i, tocoToValue(100), stepWidth / 3.0f *2 + stepWidth*i, tocoToValue(0), mBoldPaint);

            // 胎心区域水平直线
            //  canvas.drawLine(stepWidth/ 3.0f, fhrToValue(0), stepWidth*2 + stepWidth*i, fhrToValue(0), mThinPaint);
            // canvas.drawLine(stepWidth/ 3.0f, fhrToValue(10), stepWidth*2 + stepWidth*i, fhrToValue(10), mThinPaint);
            // canvas.drawLine(stepWidth/ 3.0f, fhrToValue(20), stepWidth*2 + stepWidth*i, fhrToValue(20), mThinPaint);
            // canvas.drawLine(stepWidth/ 3.0f, fhrToValue(30), stepWidth*2 + stepWidth*i, fhrToValue(30), mThinPaint);
            canvas.drawLine(stepWidth/ 3.0f, fhrToValue(40), stepWidth*2 + stepWidth*i, fhrToValue(40), mThinPaint);
            canvas.drawLine(stepWidth/ 3.0f, fhrToValue(50), stepWidth*2 + stepWidth*i, fhrToValue(50), mThinPaint);
            canvas.drawLine(stepWidth/ 3.0f, fhrToValue(60), stepWidth*2 + stepWidth*i, fhrToValue(60), mBoldPaint);
            canvas.drawLine(stepWidth/ 3.0f, fhrToValue(70), stepWidth*2 + stepWidth*i, fhrToValue(70), mThinPaint);
            canvas.drawLine(stepWidth/ 3.0f, fhrToValue(80), stepWidth*2 + stepWidth*i, fhrToValue(80), mThinPaint);
            canvas.drawLine(stepWidth/ 3.0f, fhrToValue(90), stepWidth*2 + stepWidth*i, fhrToValue(90), mBoldPaint);
            canvas.drawLine(stepWidth/ 3.0f, fhrToValue(100), stepWidth*2 + stepWidth*i, fhrToValue(100), mThinPaint);
            canvas.drawLine(stepWidth/ 3.0f, fhrToValue(110), stepWidth*2 + stepWidth*i, fhrToValue(110), mThinPaint);
            canvas.drawLine(stepWidth/ 3.0f, fhrToValue(120), stepWidth*2 + stepWidth*i, fhrToValue(120), mBoldPaint);
            canvas.drawLine(stepWidth/ 3.0f, fhrToValue(130), stepWidth*2 + stepWidth*i, fhrToValue(130), mThinPaint);
            canvas.drawLine(stepWidth/ 3.0f, fhrToValue(140), stepWidth*2 + stepWidth*i, fhrToValue(140), mThinPaint);
            canvas.drawLine(stepWidth/ 3.0f, fhrToValue(150), stepWidth*2 + stepWidth*i, fhrToValue(150), mBoldPaint);
            canvas.drawLine(stepWidth/ 3.0f, fhrToValue(160), stepWidth*2 + stepWidth*i, fhrToValue(160), mThinPaint);
            canvas.drawLine(stepWidth/ 3.0f, fhrToValue(170), stepWidth*2 + stepWidth*i, fhrToValue(170), mThinPaint);
            canvas.drawLine(stepWidth/ 3.0f, fhrToValue(180), stepWidth*2 + stepWidth*i, fhrToValue(180), mBoldPaint);
            canvas.drawLine(stepWidth/ 3.0f, fhrToValue(190), stepWidth*2 + stepWidth*i, fhrToValue(190), mThinPaint);
            canvas.drawLine(stepWidth/ 3.0f, fhrToValue(200), stepWidth*2 + stepWidth*i, fhrToValue(200), mThinPaint);
            canvas.drawLine(stepWidth/ 3.0f, fhrToValue(210), stepWidth*2 + stepWidth*i, fhrToValue(210), mBoldPaint);

            // 宫缩区域水平直线
//            canvas.drawLine(stepWidth/ 3.0f, tocoToValue(0), stepWidth*2 + stepWidth*i, tocoToValue(0), mBoldPaint);
//            canvas.drawLine(stepWidth/ 3.0f, tocoToValue(10), stepWidth*2 + stepWidth*i, tocoToValue(10), mThinPaint);
//            canvas.drawLine(stepWidth/ 3.0f, tocoToValue(20), stepWidth*2 + stepWidth*i, tocoToValue(20), mBoldPaint);
//            canvas.drawLine(stepWidth/ 3.0f, tocoToValue(30), stepWidth*2 + stepWidth*i, tocoToValue(30), mThinPaint);
//            canvas.drawLine(stepWidth/ 3.0f, tocoToValue(40), stepWidth*2 + stepWidth*i, tocoToValue(40), mBoldPaint);
//            canvas.drawLine(stepWidth/ 3.0f, tocoToValue(50), stepWidth*2 + stepWidth*i, tocoToValue(50), mThinPaint);
//            canvas.drawLine(stepWidth/ 3.0f, tocoToValue(60), stepWidth*2 + stepWidth*i, tocoToValue(60), mBoldPaint);
//            canvas.drawLine(stepWidth/ 3.0f, tocoToValue(70), stepWidth*2 + stepWidth*i, tocoToValue(70), mThinPaint);
//            canvas.drawLine(stepWidth/ 3.0f, tocoToValue(80), stepWidth*2 + stepWidth*i, tocoToValue(80), mBoldPaint);
//            canvas.drawLine(stepWidth/ 3.0f, tocoToValue(90), stepWidth*2 + stepWidth*i, tocoToValue(90), mThinPaint);
//            canvas.drawLine(stepWidth/ 3.0f, tocoToValue(100), stepWidth*2 + stepWidth*i, tocoToValue(100), mBoldPaint);
        }

        // 胎心区域纵坐标
        //  canvas.drawText("0", offX + oneWidth /2, fhrToValue(-7), timePaint);
        // canvas.drawText("20", offX + oneWidth /2, fhrToValue(17), timePaint);
         canvas.drawText("40", offX + oneWidth /2, fhrToValue(37), timePaint);
        canvas.drawText("60", offX + oneWidth /2, fhrToValue(57), timePaint);
        canvas.drawText("80", offX + oneWidth /2, fhrToValue(77), timePaint);
        canvas.drawText("100", offX + oneWidth /2, fhrToValue(97), timePaint);
        canvas.drawText("120", offX + oneWidth /2, fhrToValue(117), timePaint);
        canvas.drawText("140", offX + oneWidth /2, fhrToValue(137), timePaint);
        canvas.drawText("160", offX + oneWidth /2, fhrToValue(157), timePaint);
        canvas.drawText("180", offX + oneWidth /2, fhrToValue(177), timePaint);
        canvas.drawText("200", offX + oneWidth /2, fhrToValue(197), timePaint);
        canvas.drawText("210", offX + oneWidth /2, fhrToValue(207), timePaint);

        // 宫缩区域纵坐标
//        canvas.drawText("0", offX + oneWidth /2, tocoToValue(-4), timePaint);
//        canvas.drawText("20", offX + oneWidth /2, tocoToValue(16), timePaint);
//        canvas.drawText("40", offX + oneWidth /2, tocoToValue(36), timePaint);
//        canvas.drawText("60", offX + oneWidth /2, tocoToValue(56), timePaint);
//        canvas.drawText("80", offX + oneWidth /2, tocoToValue(76), timePaint);
//        canvas.drawText("100", offX + oneWidth /2, tocoToValue(96), timePaint);

        // 画一条竖线
        canvas.drawLine(offX + screenWidth / 3.0f, fhrToValue(210), offX + screenWidth / 3.0f, fhrToValue(fhrMin), mVerticalLine);

        if (datas != null) {
            int length = datas.length;
            for (int i = 1; i < length; i++) {
                int lastRate = datas[i - 1].heartRate;
                int currRate = datas[i].heartRate;
                int lastToco = datas[i - 1].tocoWave;
                int currToco = datas[i].tocoWave;
                int status1 = datas[i].status1;

                float startX = (i - 1) * wide + oneWidth * START_X_NUM;
                float stopX = i * wide + oneWidth * START_X_NUM;
                // 胎心率值
                float fhrStartY = fhrToValue(lastRate);
                float fhrStopY = fhrToValue(currRate);
                // 宫缩值
//                float tocoStartY = tocoToValue(lastToco);
//                float tocoStopY = tocoToValue(currToco);
                // 判断是否断线
                boolean breakFlag = (new BigDecimal(lastRate - currRate).abs().intValue()) <= breakValue;
//                if (lastRate < 50 || lastRate > 210 || currRate < 50 || currRate > 210) {
//                } else {
//                    if (breakFlag) {
//
//                    } else {
//                        canvas.drawPoint(stopX, fhrStopY, mFhr1Paint);
//                    }
//                }
                canvas.drawLine(startX, fhrStartY, stopX, fhrStopY, mFhr1Paint);

                // 自动胎动
				/*if (datas[i].beatBd == Listener.BEAT) {
					canvas.drawRect(stopX-wide/2, fhrToValue(200), stopX+wide/2, fhrToValue(190), mAfmPaint);
				}*/

                // 手动胎动
                if (datas[i].status1 == Listener.BEAT) {
                    canvas.drawBitmap(beatZdbmp, stopX - wide / 2-beatZdbmp.getWidth()/2,fhrToValue(50)-beatZdbmp.getHeight()/2, timePaint);
                }
                // 宫缩复位
                if ((status1 & 0x10) != 0) {
                   // canvas.drawBitmap(tocoResetBmp, stopX - wide / 2, fhrToValue(200), null);
                }
            }
        }
    }

    /**
     * 根据转换坐标值(这个与给定的背景图片有关)
     */
    private float fhrToValue(int fhr) {
        float v = fhrTop + fhrVer * (fhrMax - fhr);
        return v;
    }

//    private float tocoToValue(int toco) {
//        float v = tocoTop + tocoVer * (tocoMax - toco);
//        return v;
//    }

    int last;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction() & event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mode = NONE;
                last = (int) event.getX();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    mode = ZOOM;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == ZOOM) {
                    isToastMin = false;
                    isToastMax = false;
                    float newDist = spacing(event);
                    float offset = newDist - oldDist;
                    if (Math.abs(offset) > 33.0f) {
                        //缩放操作
                        oldDist = newDist;
                        if (offset < 0) {
                            wide = wide / 2.0f;
                            float minWide = screenWidth / maxSize / 4.0f;
                            wide = wide < minWide ? minWide : wide;
                            stepWidth = stepWidth / 2.0f;
                            float minWidth = screenWidth / 12.0f;
                            if (stepWidth < minWidth) {
                                stepWidth = minWidth;
                                isToastMin = true;
                            }
                        } else {
                            wide = wide * 2.0f;
                            float maxWide = 1.0f * screenWidth / maxSize;
                            wide = wide > maxWide ? maxWide : wide;
                            stepWidth = stepWidth * 2.0f;
                            float maxWidth = screenWidth / 3.0f;
                            if (stepWidth > maxWidth) {
                                stepWidth = maxWidth;
                                isToastMax = true;
                            }
                        }
                        // 提示
                        String toastString = "X" + (int) (stepWidth * 12 / screenWidth);
                        if (isToastMax) {
                            toastString = getResources().getString(R.string.max);
                        } else if (isToastMin) {
                            toastString = getResources().getString(R.string.min);
                        }
                        Toast mToast = Toast.makeText(getContext(), toastString, Toast.LENGTH_SHORT);
                        mToast.setGravity(Gravity.CENTER, 0, 0);
                        mToast.show();
                        mode = ZOOM_FINISH;
                        invalidate();
                    }
                } else {
                    float offset = Math.abs(event.getX() - last);
                    mode = offset > 10f ? SCROLL : NONE;
                }
            case MotionEvent.ACTION_UP:
                if (mode == SCROLL) {
                    int scrollX = getScrollX();
                    int cur = (int) event.getX();
                    int offX = last - cur;
                    last = cur;
                    offX = scrollX + offX;
                    if (offX < 0) {
                        offX = 0;
                    } else if (offX > lineWidth) {
                        offX = lineWidth;
                    }
                    if (offX > 0)
                        setPostion(offX);
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (mode == ZOOM)
                    mode = NONE;
                break;
            default:
                break;
        }
        return true;
    }

    private void setPostion(int offX) {
        scrollTo(offX, 0);
        int time = (int) (offX * 500 / wide);
        if (mediaPlayer != null)
            mediaPlayer.seekTo(time);
        else {
            if (mNotifyListener != null)
                mNotifyListener.notifyScrolled(time);
        }
    }

    public void setTime(int time) {
        int offX = (int) (time * wide / 500);
        scrollTo(offX, 0);
    }

    public void scrollEnd() {
        scrollTo(lineWidth, 0);
    }

    public void setMediaPlay(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    private notifyScrolledListener mNotifyListener;

    public void setNotifycrolledListener(notifyScrolledListener listener) {

        mNotifyListener = listener;
    }

    public interface notifyScrolledListener {

        void notifyScrolled(int time);
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }
}
