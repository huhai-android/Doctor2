package com.newdjk.doctor.broadcast;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.tools.MainConstant;
import com.newdjk.doctor.ui.activity.SplashActivity;
import com.newdjk.doctor.ui.entity.JPushDataMessageEntity;
import com.newdjk.doctor.ui.entity.MessageEvent;
import com.newdjk.doctor.ui.entity.PushDataDaoEntity;
import com.newdjk.doctor.ui.entity.UpdatePatientViewEntity;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.utils.AuthenticationCommentUtil;
import com.newdjk.doctor.utils.SQLiteUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.broadcast
 *  @文件名:   MiPushMessageReceiver
 *  @创建者:   huhai
 *  @创建时间:  2019/4/1 16:22
 *  @描述：
 */
public class MiPushMessageReceiver extends PushMessageReceiver {
    private final String TAG = "MiPushMessageReceiver";
    private String mRegId;
    private NotificationManager notificationManager;
    String PRIMARY_CHANNEL = "my_channel_02";
    private NotificationCompat.Builder builder;
    private Notification mNotification;
    private int channelID = 2;

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        Log.d(TAG, "onNotificationMessageClicked is called. " + message.toString());
        Log.d(TAG, getSimpleDate() + " " + message.getContent());

        //打开自定义的Activity
        Intent i = new Intent(context, SplashActivity.class);
        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);

    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        // Log.d(TAG, "onNotificationMessageArrived is called. " + message.toString());
        try {
            Gson gson = new Gson();
            Log.d(TAG, "收到服务器的消息 " + message.getContent());
            JPushDataMessageEntity jPushDataMessageEntity = gson.fromJson(message.getContent(), JPushDataMessageEntity.class);
            String content = message.getDescription();
            String title = message.getTitle();
            String data = message.getContent();
            Log.i("MyReceiver", "data=" + data);
            String time = jPushDataMessageEntity.getTime();
            boolean isRead;
            isRead = data == null || data.equals("");

            int type = jPushDataMessageEntity.getType();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = null;
            curDate = formatter.parse(time);
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
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        Log.d(TAG, "onReceiveRegisterResult is called. " + message.toString());
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        Log.d(TAG, "cmd: " + command + " | arg: " + cmdArg1
                + " | result: " + message.getResultCode() + " | reason: " + message.getReason());
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        }
        Log.d(TAG, "获取注册idregId: " + mRegId);
        MyApplication.mRegId = mRegId;

    }

    @SuppressLint("SimpleDateFormat")
    private static String getSimpleDate() {
        return new SimpleDateFormat("MM-dd hh:mm:ss").format(new Date());
    }

    //透传消息到达客户端时调用
    //作用：可通过参数message从而获得透传消息，具体请看官方SDK文档
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        Log.d(TAG, "收到服务器的消息 " + message.toString());
        initNotification(context);

    }

    private void initNotification(Context context) {

//        notificationManager = (NotificationManager) MyApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
//        builder = new NotificationCompat.Builder(MyApplication.getInstance());
//        builder.setContentTitle("正在更新...") //设置通知标题
//                .setSmallIcon(R.mipmap.icon)
//                .setLargeIcon(BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.mipmap.icon)) //设置通知的大图标
//                .setDefaults(Notification.DEFAULT_LIGHTS) //设置通知的提醒方式： 呼吸灯
//                .setPriority(NotificationCompat.PRIORITY_MAX) //设置通知的优先级：最大
//                .setAutoCancel(true)//设置通知被点击一次是否自动取消
//                .setContentText("下载进度:" + "0%")
//                .setProgress(100, 0, false);
//        notification = builder.build();//构建通知对象

        notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//适配8.0,自行查看8.0的通知，主要是NotificationChannel
            NotificationChannel chan1 = new NotificationChannel(PRIMARY_CHANNEL,
                    "Primary Channel", NotificationManager.IMPORTANCE_DEFAULT);
            chan1.setLightColor(Color.GREEN);
            chan1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(chan1);
            builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL);
        } else {
            builder = new NotificationCompat.Builder(context, null);
        }
        builder.setContentText("下载进度:" + "0%")//notification的一些设置，具体的可以去官网查看
                .setContentTitle("正在更新...") //设置通知标题
                .setTicker("正在下载")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setOnlyAlertOnce(true)
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon))
               .setSmallIcon(R.mipmap.icon);
        builder.setProgress(100, 0, false);//显示下载进度
        Intent intent = new Intent(context, NotificationClickReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        // 如果存在，启动安装流程
        mNotification = builder.build();
        notificationManager.notify(channelID, mNotification);
    }

}
