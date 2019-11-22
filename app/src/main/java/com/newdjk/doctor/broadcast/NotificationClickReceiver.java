package com.newdjk.doctor.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.newdjk.doctor.ui.entity.UpdateEntity;

import org.greenrobot.eventbus.EventBus;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.broadcast
 *  @文件名:   NotificationClickReceiver
 *  @创建者:   huhai
 *  @创建时间:  2019/3/4 15:49
 *  @描述：
 */
public class NotificationClickReceiver extends BroadcastReceiver {

    private static final String TAG = "Notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        //todo 跳转之前要处理的逻辑
        Log.d(TAG, "userClick:我被点击啦！！！ ");
        UpdateEntity updateEntity=new UpdateEntity();
        updateEntity.setUrl("");
        EventBus.getDefault().post(updateEntity);
    }
}

