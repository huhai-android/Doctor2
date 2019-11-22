package com.newdjk.doctor.broadcast;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.huawei.hms.support.api.push.PushReceiver;

/*
 *  @项目名：  Member
 *  @包名：    com.newdjk.member.broadcast
 *  @文件名:   HuaweiPushReceiverEx
 *  @创建者:   huhai
 *  @创建时间:  2019/4/3 15:13
 *  @描述：
 */
public class HuaweiPushReceiverEx extends PushReceiver {
    private static final String TAG = "HuaweiPushReceiverEx";

    @Override
    public void onEvent(Context context, Event event, Bundle extras) {

//开发者自行实现相应处理逻辑

        if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {
            int notifyId = extras.getInt(BOUND_KEY.pushNotifyId, 0);
            Log.i(TAG, "收到通知栏消息点击事件,notifyId:" + notifyId);
            if (0 != notifyId) {
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(notifyId);
            }
        }

        String message = extras.getString(BOUND_KEY.pushMsgKey);
        super.onEvent(context, event, extras);
    }
}
