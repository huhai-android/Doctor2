package com.newdjk.doctor.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.tools.MainConstant;
import com.newdjk.doctor.ui.activity.SplashActivity;
import com.newdjk.doctor.ui.entity.JPushDataMessageEntity;
import com.newdjk.doctor.ui.entity.MessageEvent;
import com.newdjk.doctor.ui.entity.PushDataDaoEntity;
import com.newdjk.doctor.ui.entity.UpdatePatientViewEntity;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.utils.AuthenticationCommentUtil;
import com.newdjk.doctor.utils.BadgeUtil;
import com.newdjk.doctor.utils.SQLiteUtils;
import com.newdjk.doctor.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by EDZ on 2018/9/26.
 */

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "JIGUANG-Example";

    @Override
    public void onReceive(Context context, Intent intent) {
        Gson gson = new Gson();
        try {
            Bundle bundle = intent.getExtras();
            Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//                processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
                String content = bundle.getString(JPushInterface.EXTRA_ALERT);
                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                String data = bundle.getString(JPushInterface.EXTRA_EXTRA);
                Log.i("MyReceiver", "data=" + data);
                Log.i("MyReceiver", "title=" + title);
                Log.i("MyReceiver", "content=" + content);

                JPushDataMessageEntity jPushDataMessageEntity = gson.fromJson(data, JPushDataMessageEntity.class);
                String time = jPushDataMessageEntity.getTime();
                boolean isRead;
                isRead = data == null || data.equals("");
                int type = jPushDataMessageEntity.getType();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date curDate = formatter.parse(time);
                int id = Integer.parseInt(jPushDataMessageEntity.getUserId());
                Log.i("MyReceiver", "id=" + id);
                int msgId = jPushDataMessageEntity.getId();
                SQLiteUtils.getInstance().addPushData(new PushDataDaoEntity(null, title, content, data, curDate, isRead, id, msgId));
                if (type == 10) {
                    EventBus.getDefault().post(new UpdatePushView(2));
                    EventBus.getDefault().post(new UpdatePatientViewEntity(true));
                } else if (type == 100) {
                    EventBus.getDefault().post(new UpdatePushView(3));
                    EventBus.getDefault().post(new UpdatePushView(1));
                } else if (type == 101) {
                    Log.d(TAG, "收到处方审核通知");
                    EventBus.getDefault().post(new UpdatePushView(5));
                    EventBus.getDefault().post(new UpdatePushView(1));

                }else if (type == 103) {
                    Log.d(TAG, "收到处方审核通知");
                    EventBus.getDefault().post(new UpdatePushView(5));
                    EventBus.getDefault().post(new UpdatePushView(1));

                } else if (type == 219) {
                    Log.d(TAG, "收到处方审核通知");
                    EventBus.getDefault().post(new UpdatePushView(6));
                    EventBus.getDefault().post(new UpdatePushView(1));

                } else if (type == 220) {
                    EventBus.getDefault().post(new MessageEvent(MainConstant.UPDATE_MEDICAL));
                    EventBus.getDefault().post(new UpdatePushView(1));
                    Log.d(TAG, "收到处方审核通知");

                } else if (type == 221) {
                    SpUtils.put(Contants.Status, 1);
                    EventBus.getDefault().postSticky(AuthenticationCommentUtil.AUTH);
                    EventBus.getDefault().post(new UpdatePushView(1));
                } else if (type == 222) {
                    SpUtils.put(Contants.Status, 2);
                    EventBus.getDefault().postSticky(AuthenticationCommentUtil.AUTH);
                    EventBus.getDefault().post(new UpdatePushView(1));
                } else {
                    EventBus.getDefault().post(new UpdatePushView(1));
                }
                MyApplication.badgeNumber++;
                Log.d("BadgeUtil","MyReceiver红点显示个数"+MyApplication.badgeNumber);

                BadgeUtil.setBadgeCount(MyApplication.getContext(),MyApplication.badgeNumber);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

                //打开自定义的Activity
                Intent i = new Intent(context, SplashActivity.class);
                Log.i("MyReceiver", "bundle=" + bundle.toString());
                i.putExtras(bundle);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }

        } catch (Exception e) {
            Log.i("MyReceiver", "ERRROR=" + e.toString());
        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
//    private void processCustomMessage(Context context, Bundle bundle) {
//        if (MainActivity.isForeground) {
//            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//            if (!ExampleUtil.isEmpty(extras)) {
//                try {
//                    JSONObject extraJson = new JSONObject(extras);
//                    if (extraJson.length() > 0) {
//                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//                    }
//                } catch (JSONException e) {
//
//                }
//
//            }
//            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//        }
//    }
}
