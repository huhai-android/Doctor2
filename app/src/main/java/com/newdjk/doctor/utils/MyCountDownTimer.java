package com.newdjk.doctor.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

//复写倒计时
public class MyCountDownTimer extends CountDownTimer {
    private TextView tv;

    public MyCountDownTimer(TextView tv, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.tv = tv;
    }
    //计时完毕的方法
    public void clear() {
        cancel();
        //重新给Button设置文字

        tv.setText("发送验证码");
        //设置可点击
        tv.setClickable(true);
    }
    //计时过程
    @Override
    public void onTick(long l) {
        //防止计时过程中重复点击
        tv.setClickable(false);
        tv.setText(l / 1000 + "s");

    }

    //计时完毕的方法
    @Override
    public void onFinish() {
        //重新给Button设置文字
        tv.setText("发送验证码");
        //设置可点击
        tv.setClickable(true);
    }
}
