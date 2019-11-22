package com.newdjk.doctor.broadcast;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.huawei.hms.support.api.push.PushReceiver;
import com.newdjk.doctor.MyApplication;

/*
 *  @项目名：  Member
 *  @包名：    com.newdjk.member.broadcast
 *  @文件名:   HuaweiPushRevicer
 *  @创建者:   huhai
 *  @创建时间:  2019/4/3 14:54
 *  @描述：
 */
public class HuaweiPushRevicer extends PushReceiver {
    private static final String TAG = "HuaweiPushRevicer";

    @Override
    public void onToken(Context context, String token, Bundle extras) {

        //开发者自行实现Token保存逻辑。

        String belongId = extras.getString("belongId");
        Log.i(TAG, "belongId:" + belongId);
        Log.i(TAG, "Token:" + token);
        MyApplication.mRegId=token;
    }

    @Override
    public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {
        try {
            //开发者可以自己解析消息内容，然后做相应的处理
            String content = new String(msg, "UTF-8");
            Log.i(TAG, "收到PUSH透传消息,消息内容为:" + content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //开发者自行实现透传消息处理。

        return false;
    }


}