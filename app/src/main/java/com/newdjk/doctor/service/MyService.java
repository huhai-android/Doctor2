package com.newdjk.doctor.service;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.broadcast.NotificationClickReceiver;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.tools.MainConstant;
import com.newdjk.doctor.ui.activity.IM.RoomActivity;
import com.newdjk.doctor.ui.activity.MainActivity;
import com.newdjk.doctor.ui.entity.AllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.AppEntity;
import com.newdjk.doctor.ui.entity.CustomMessageEntity;
import com.newdjk.doctor.ui.entity.MessageEventRecent;
import com.newdjk.doctor.ui.entity.MianzhenSuccessEntity;
import com.newdjk.doctor.ui.entity.NotifyServiceToSendMessageEntity;
import com.newdjk.doctor.ui.entity.RefeshTaskListEntity;
import com.newdjk.doctor.ui.entity.RejectCallTip;
import com.newdjk.doctor.ui.entity.UpdateAllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.UpdateEntity;
import com.newdjk.doctor.ui.entity.UpdateImMessageEntity;
import com.newdjk.doctor.ui.entity.UpdateMessageListEntity;
import com.newdjk.doctor.utils.AppUtils;
import com.newdjk.doctor.utils.BadgeUtil;
import com.newdjk.doctor.utils.InstallApkUtil;
import com.newdjk.doctor.utils.NetworkUtil;
import com.newdjk.doctor.utils.SQLiteUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.TimeUtil;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.GroupButtonDialog;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;
import com.tencent.TIMSNSSystemElem;
import com.tencent.TIMTextElem;
import com.tencent.TIMValueCallBack;
import com.tencent.qalsdk.util.ALog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service implements TIMMessageListener {
    private static final String TAG = "MyService";
    private Ringtone mRingtone;
    private Gson mGson = new Gson();
    private TIMConversation mConversation;
    private AlertDialog mIncomingDlg;
    private static final int PUSH_NOTIFICATION_ID = (0x001);
    private static final String PUSH_CHANNEL_ID = "PUSH_NOTIFY_ID";
    private static final String PUSH_CHANNEL_NAME = "PUSH_NOTIFY_NAME";

    protected com.lxq.okhttp.MyOkHttp mMyOkhttp;
    private String mContent;
    private GroupButtonDialog mDialog;
    private NotificationCompat.Builder mBuilder;
    private Notification mNotification;
    public static final String CHANNEL_ID_STRING = "nyd001";
    private NotificationManager notificationManager2;

    public MyService() {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mRingtone = RingtoneManager.getRingtone(MyApplication.getContext(), notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private String notificationId = "channelId";
    private String notificationName = "channelName";

    private Notification getNotification() {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.icon))
                .setContentTitle("芸医生")
                .setContentText("我正在运行");
        //设置Notification的ChannelID,否则不能正常显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(notificationId);
        }
        Notification notification = builder.build();
        return notification;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager2 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // startForeground(1, new Notification()); //这个id不要和应用内的其他同志id一样，不行就写 int.maxValue()        //context.startForeground(SERVICE_ID, builder.getNotification());
            NotificationChannel channel = new NotificationChannel(notificationId, notificationName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager2.createNotificationChannel(channel);
            startForeground(1, getNotification());
        }

        initNotification();
        mMyOkhttp = MyApplication.getInstance().getMyOkHttp();

        EventBus.getDefault().register(this);
        TIMManager.getInstance().addMessageListener(this);

    }

    @Override
    public boolean onNewMessages(List<TIMMessage> list) {
        boolean needToNotify = false;
        String imId = null;
        long unreadNum = 0;
        long timeStamp = 0;
        EventBus.getDefault().post(new UpdateMessageListEntity(list));

        MessageEventRecent messageEvent = new MessageEventRecent();
//        messageEvent.setmType(MainConstant.UpdateMessageListEntity);
        messageEvent.setmType(MainConstant.UpdateConversation);
        messageEvent.setList(list);
        EventBus.getDefault().post(messageEvent);

        for (TIMMessage timMessage : list) {
            if (timMessage.getConversation().getType().equals(TIMConversationType.C2C)) {
                //只要有一条c2c消息，就响铃通知用户
                imId = timMessage.getConversation().getPeer();

                if (MyApplication.mImId != null && MyApplication.mImId.equals(imId)) {
                    return false;
                }
                mConversation = timMessage.getConversation();
                List<TIMMessage> lastMsgs = mConversation.getLastMsgs(1);
                TIMMessage msg = lastMsgs.get(0);
                TIMElem element = msg.getElement(0);
                timeStamp = msg.timestamp();
                if (element.getType() == TIMElemType.Text) {
                    TIMTextElem textElem = (TIMTextElem) element;
                    mContent = textElem.getText();
                } else if (element.getType() == TIMElemType.Sound) {
                    mContent = "[语音消息]";
                } else if (element.getType() == TIMElemType.Video) {
                    mContent = "[视频消息]";
                } else if (element.getType() == TIMElemType.Image) {
                    mContent = "[图片消息]";
                }
                else if (element.getType() == TIMElemType.Custom) {
                    //只要有自定义消息过来，就需要去刷新数据
                    Log.i("zdp", "tipsTHFGH");
                    TIMCustomElem customElem = (TIMCustomElem) timMessage.getElement(0);
                    String s = new String(customElem.getData());
                    Log.i("MyService", "s=" + s);
                    CustomMessageEntity CustomMessageEntity = mGson.fromJson(s, CustomMessageEntity.class);
                    CustomMessageEntity.ExtDataBean extraData = CustomMessageEntity.getExtData();
                    Log.d(TAG, "自定义消息----" + s);
                    if (CustomMessageEntity != null) {
                        Log.d(TAG, "自定义消息pushdesc" + CustomMessageEntity.getPushDesc()+"  "+timMessage.getSender()+"   "+SpUtils.getString(Contants.identifier)+" "+ timMessage.getSenderProfile().getNickName());
                        if (!TextUtils.isEmpty(CustomMessageEntity.getPushDesc())) {
                            mContent =CustomMessageEntity.getPushDesc();
                        } else if (!TextUtils.isEmpty(CustomMessageEntity.getTitle())) {
                            Log.d(TAG, "自定义消息----" +timMessage.getSender()+"   "+SpUtils.getString(Contants.identifier));
                            mContent =CustomMessageEntity.getTitle();

                        } else {
                            mContent ="[系统消息]";
                        }
                    }

                    if (extraData != null) {
                        int type = extraData.getType();
                        if (type == 38) {

                            //   EventBus.getDefault().post(new UpdateViewEntity(null));
                            // GetAllRecordForDoctor();
                            EventBus.getDefault().post(new UpdateAllRecordForDoctorEntity(null));
                            //     EventBus.getDefault().post(new UpdateImMessageEntity(null));
                           /* Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                            vibrator.vibrate(new long[]{0, 200, 200, 200}, -1);
                            mRingtone.play();*/

                        } else if (type == 36) {
                            com.newdjk.doctor.ui.entity.CustomMessageEntity.ExtDataBean.DataBean dataBean = extraData.getData();
                            Log.i("MyService", "dataBean=" + dataBean);
                            if (dataBean != null) {
                                int recordId = dataBean.getRecordId();
                                int serviceType = dataBean.getServiceType();
                                List<AllRecordForDoctorEntity> allRecordForDoctorList = new ArrayList<>();
                                switch (serviceType) {
                                    case 1:
                                        allRecordForDoctorList = SQLiteUtils.getInstance().selectImDataByServiceCodeAndRecord("1101", recordId);
                                        break;
                                    case 2:
                                        allRecordForDoctorList = SQLiteUtils.getInstance().selectImDataByServiceCodeAndRecord("1102", recordId);
                                        break;
                                    case 3:
                                        allRecordForDoctorList = SQLiteUtils.getInstance().selectImDataByServiceCodeAndRecord("1103", recordId);
                                        break;

                                }
                                Log.i("MyService", "allRecordForDoctorList.size=" + allRecordForDoctorList.size());
                                if (allRecordForDoctorList.size() > 0) {
                                    AllRecordForDoctorEntity AllRecordForDoctorEntity = allRecordForDoctorList.get(0);
                                    SQLiteUtils.getInstance().deleteImData(AllRecordForDoctorEntity);
                                }

                                EventBus.getDefault().post(new UpdateImMessageEntity(null));

                            }
                        }
                        switch (type) {
                            case 129:
                                if (RoomActivity.isVideoing) {
                                    sendVideoMessage(133, imId, -1);
                                } else {
//                                    int callId = extraData.getData().getAVRoomID();
//                                    String sender = extraData.getData().getTargets();
//                                    String userSig = extraData.getData().getUserSig();
//                                    //newIncomingCall(callId, sender, userSig);
//                                    Intent intent = new Intent(MyService.this, DialogActivity.class);
//                                    intent.putExtra("sender", sender);
//                                    intent.putExtra("identifier", imId);
//                                    intent.putExtra("callId", callId);
//                                    intent.putExtra("userSig", userSig);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    startActivity(intent);
                                }

                                break;
                            case 131:
                                EventBus.getDefault().post(new RejectCallTip(true));

                                break;
                            case 134:
                                EventBus.getDefault().post(new RejectCallTip(true));

                                break;
                            case 133:
                                EventBus.getDefault().post(new RejectCallTip(true));
                                break;
                            case 130:

                                break;
                            //面诊预约消息
                            case 305:
                                EventBus.getDefault().post(new MianzhenSuccessEntity(true));
                                break;

                            //面诊预约消息
                            case 34:
                                EventBus.getDefault().post(new MianzhenSuccessEntity(true));
                                break;
                            //第二诊疗
                            case 219:
                                EventBus.getDefault().post(new RefeshTaskListEntity(true));
                                break;
                        }
                    }

                }
                needToNotify = !timMessage.isSelf();
                Log.d(TAG, "发送id----" + timMessage.getSender()+" 登录"+SpUtils.getString(Contants.identifier));
                if (MyApplication.gotoMainactivity==1){
                    if (timMessage.getSender().startsWith("pat")) {     //如果是病人发送的消息
                        String name = timMessage.getSenderProfile().getNickName();

                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationChannel channel = new NotificationChannel(PUSH_CHANNEL_ID, PUSH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                            channel.enableLights(true);
                            channel.enableVibration(true);
                            if (mNotificationManager != null) {
                                mNotificationManager.createNotificationChannel(channel);
                            }
                        }
                        MyApplication.badgeNumber++;
                        Log.d("BadgeUtil","Myservice红点显示个数"+MyApplication.badgeNumber);

                        BadgeUtil.setBadgeCount(this,MyApplication.badgeNumber);
                        Log.i("Myservice", "99999");
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "default");
                        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
                        mBuilder.setChannelId("com.newdjk.doctor")
                                .setContentTitle(name)//设置通知栏标题
                                .setContentText(mContent)
                                .setChannelId(PUSH_CHANNEL_ID)
                                .setContentIntent(intent) //设置通知栏单击意图
                                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                                .setPriority(Notification.PRIORITY_DEFAULT)
                                .setDefaults(Notification.DEFAULT_VIBRATE)
                                .setOnlyAlertOnce(true)
                                .setWhen(System.currentTimeMillis())
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon))
                                .setSmallIcon(R.mipmap.icon);

                        Notification notify = mBuilder.build();
                        notify.flags |= Notification.FLAG_AUTO_CANCEL;
                        mNotificationManager.notify(1110, notify);

                    }
                }
            } else if (timMessage.getConversation().getType().equals(TIMConversationType.System)) {
                //如果有系统通知，刷新好友列表
                      /*  ContactModel contactModel = new ContactModel();
                        contactModel.getFriendList();*/
            } else if (timMessage.getConversation().getType().equals(TIMConversationType.Group)) {
                //更新群组消息

                mConversation = timMessage.getConversation();
                List<TIMMessage> lastMsgs = mConversation.getLastMsgs(1);
                TIMMessage msg = lastMsgs.get(0);
                TIMElem element = msg.getElement(0);

                if (element.getType() == TIMElemType.Custom) {

                    TIMCustomElem customElem = (TIMCustomElem) msg.getElement(0);
                    String s = new String(customElem.getData());
                    CustomMessageEntity customMessageEntity = mGson.fromJson(s, CustomMessageEntity.class);
                    CustomMessageEntity.ExtDataBean extraData = customMessageEntity.getExtData();

                    if (extraData != null) {
                        int type = extraData.getType();
                        Log.d(TAG, "收到群消息" + type);
                        if (type > 0) {
                            MessageEventRecent messagegroup = new MessageEventRecent();
                            messagegroup.setmType(MainConstant.UpdateOrderStatus);
                            EventBus.getDefault().post(messagegroup);
                        }
                    }
                }
                Log.d(TAG, "收到群消息");
                MessageEventRecent messagegroup = new MessageEventRecent();
                messagegroup.setmType(MainConstant.UpdateRecentList);
                EventBus.getDefault().post(messagegroup);
                EventBus.getDefault().post(new UpdateImMessageEntity(null));

            } else if (timMessage.getElement(0) instanceof TIMSNSSystemElem) {
                      /*  ContactModel contactModel = new ContactModel();
                        contactModel.getFriendList();*/
                needToNotify = true;
            }
        }
        if (!needToNotify) {
            return false;
        }

        String serviceCode = null;
        List<AllRecordForDoctorEntity> videoList = SQLiteUtils.getInstance().selectImDataByServiceCodeAndId("1102", imId);
        AllRecordForDoctorEntity allRecordForDoctorEntity = null;

        if (videoList.size() > 0) {
            allRecordForDoctorEntity = videoList.get(0);
            serviceCode = "1102";

        } else {
            List<AllRecordForDoctorEntity> pictureList = SQLiteUtils.getInstance().selectImDataByServiceCodeAndId("1101", imId);
            if (pictureList.size() > 0) {
                allRecordForDoctorEntity = pictureList.get(0);
                serviceCode = "1101";
            } else {
                List<AllRecordForDoctorEntity> renewalList = SQLiteUtils.getInstance().selectImDataByServiceCodeAndId("1103", imId);
                if (renewalList.size() > 0) {
                    allRecordForDoctorEntity = renewalList.get(0);
                    serviceCode = "1103";
                }
            }

        }
        if (allRecordForDoctorEntity != null) {
            allRecordForDoctorEntity.setUnReadNum(unreadNum);
            allRecordForDoctorEntity.setTimeStamp(timeStamp);
            SQLiteUtils.getInstance().updateImData(allRecordForDoctorEntity);

        } else {
            EventBus.getDefault().post(new UpdateAllRecordForDoctorEntity(null));
            //     EventBus.getDefault().post(new UpdateImMessageEntity(null));
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(new long[]{0, 200, 200, 200}, -1);
            mRingtone.play();
            return false;
        }
        EventBus.getDefault().post(new UpdateImMessageEntity(serviceCode));
     /*  if (isBackground(getApplicationContext())) {
            Log.i("MyService","true");
              Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(new long[]{0, 200, 200, 200}, -1);
            mRingtone.play();
        }
        else {*/
        Log.i("MyService", "imId=" + imId);

        //    }

        return true;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        TIMManager.getInstance().removeMessageListener(this);
        super.onDestroy();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(NotifyServiceToSendMessageEntity notifyServiceToSendMessageEntity) {
        int type = notifyServiceToSendMessageEntity.action;
        String identify = notifyServiceToSendMessageEntity.identify;
        long time = notifyServiceToSendMessageEntity.time;
        sendVideoMessage(type, identify, time);
    }

    /*  public void newIncomingCall(final int callId, String sender, final String userSig) {
          Log.i("dd", "callId=" + callId);
          if (null != mIncomingDlg) {  // 关闭遗留来电对话框
              mIncomingDlg.dismiss();
          }
          //  mCurIncomingId = callId;
          mIncomingDlg = new AlertDialog.Builder(getApplicationContext())
                  .setTitle("来电提醒 ")
                  .setMessage("New Call From " + sender)
                  .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          mIncomingDlg.dismiss();
                          sendVideoMessage(130);
                          //acceptCall(callId, notification.getSponsorId(), callType);
                          Intent roomIntent = new Intent(MyService.this, RoomActivity.class);
                          roomIntent.putExtra("callId", callId);
                          roomIntent.putExtra("target", "other");
                          roomIntent.putExtra("userSig", userSig);
                          startActivity(roomIntent);
                      }
                  })
                  .setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          sendVideoMessage(133);
                          //   int ret = ILVCallManager.getInstance().rejectCall(mCurIncomingId);
                      }
                  })
                  .create();
          mIncomingDlg.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
          mIncomingDlg.setCanceledOnTouchOutside(false);
          mIncomingDlg.show();
      }*/
    public void sendVideoMessage(int type, String identify, long time) {
        CustomMessageEntity customMessageEntity = new CustomMessageEntity();
        String textTime;
        if (time != -1) {
            textTime = TimeUtil.getCountTime(time / 1000);
        } else {
            textTime = "";
        }
        //   customMessageEntity.setTitle(SpUtils.getString(Contants.Name)+"医生提醒您填写一下病历，这对于您的病情很有帮助");
        customMessageEntity.setIsSystem(false);
        customMessageEntity.setContent(null);
        CustomMessageEntity.ExtDataBean extData = new CustomMessageEntity.ExtDataBean();
        CustomMessageEntity.ExtDataBean.DataBean data = new CustomMessageEntity.ExtDataBean.DataBean();
        extData.setType(type);
        data.setTargets(SpUtils.getString(Contants.Name));
        data.setTime(textTime);
        extData.setData(data);
        customMessageEntity.setExtData(extData);
        String json = new Gson().toJson(customMessageEntity);
        sendCustomMessage(json, "", identify);
    }

    private void sendCustomMessage(String data, String desc, String identify) {
        TIMConversation conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C, identify);
        //构造一条消息
        TIMMessage timMessage = new TIMMessage();
//向 TIMMessage 中添加自定义内容
        TIMCustomElem elem = new TIMCustomElem();
        elem.setData(data.getBytes());      //自定义 byte[]
        elem.setDesc(desc); //自定义描述信息
//将 elem 添加到消息
        if (timMessage.addElement(elem) != 0) {
            Log.d("zdp", "addElement failed");
            return;
        }
       /* TIMMessage timMessage = new TIMMessage();
        TIMTextElem timTextElem = new TIMTextElem();
        timTextElem.setText(input);*/
        //  timMessage.addElement(elem);
//发送消息
        conversation.sendMessage(timMessage, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 含义请参见错误码表
                //updateRecyclerView();
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                //  updateRecyclerView();
            }
        });
    }


    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.i("Myservice", appProcess.processName);
                    return true;
                } else {
                    Log.i("Myservice", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void downloadApp(AppEntity entity) {
        Log.d("Myservice", "收到下载app消息提示");

        final String apkUrl = entity.getData().getAppPath();
        mApkUrl = apkUrl;
        int apkCode = Integer.parseInt(entity.getData().getAppVersion());
        int versionCode = AppUtils.getAppVersionCode(this);
        ToastUtil.setToast("正在下载中，在顶部状态栏中可以查看进度！");
        startDownload();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateEntity updateEntity) {
        Log.d("Myservice", "收到安装app消息");
        if (isDownload) {
            ToastUtil.setToast("app正在更新中，请下载完成后点击进行安装");
            return;
        }
        InstallApkUtil installApkUtil = new InstallApkUtil();
        installApkUtil.installApk(MyService.this, DOWNLOAD_PATH);

    }

    private void startDownload() {

        FileDownloader.getImpl().create(mApkUrl).setWifiRequired(false).setPath(DOWNLOAD_PATH).setListener(new FileDownloadListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                isDownload = true;
                int p = (int) ((double) soFarBytes / (double) totalBytes * 100);
                Log.d(TAG, "正在下载" + soFarBytes + "   总数" + totalBytes);

                mCurentPercent = p;
                builder.setProgress(100, p, false);
                builder.setContentText("下载进度:" + p + "%");
                builder.setContentTitle("正在更新...");
                notification = builder.build();
                notificationManager.notify(channelID, notification);

            }

            @Override
            protected void blockComplete(BaseDownloadTask task) {


            }

            @Override
            protected void completed(BaseDownloadTask task) {
                Log.d(TAG, "下载完成");
                isDownload = false;
                if (builder != null) {

                    mCurentPercent = 100;
                    builder.setProgress(100, 100, false);
                    builder.setContentText("下载完成:" + 100 + "%");
                    builder.setContentTitle("下载完成，点击进行安装");
                    notification = builder.build();
                    notificationManager.notify(channelID, notification);
                }

                InstallApkUtil installApkUtil = new InstallApkUtil();
                installApkUtil.installApk(MyService.this, DOWNLOAD_PATH);

            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                Log.d(TAG, "下载失败");
                isDownload = false;
                if (builder != null) {
                    builder.setContentTitle("下载失败");
                    notification = builder.build();
                    notificationManager.notify(channelID, notification);
                }

                if (mDialog == null) {
                    mDialog = new GroupButtonDialog(MyService.this);
                }
                mDialog.show("下载失败", "下载失败，是否重新下载？", new GroupButtonDialog.DialogListener() {
                    @Override
                    public void cancel() {
                        mDialog = null;
                    }

                    @Override
                    public void confirm() {
                                   /* UpdateManage updateManage = new UpdateManage(getContext(), apkUrl);
                                    updateManage.showDownloadDialog();*/
                        mDialog = null;
                        if (TextUtils.isEmpty(mApkUrl)) {
                            return;
                        }
                        Log.d(TAG, "重新下载");
                        if (NetworkUtil.isNetworkAvailable(getApplicationContext())) {
                            ALog.w(TAG, "恢复任务失败，网络未连接");
                            ToastUtil.setToast("网络连接失败，请检查网络");
                            return;
                        }
                        initNotification();
                    }
                });
            }

            @Override
            protected void warn(BaseDownloadTask task) {


            }
        }).start();
    }

    //-----------------------------------------------app下载部分内容-------------------------------------------------
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private Notification notification;
    private int mCurentPercent = 0;
    private int channelID = 1;
    private String mApkUrl = "";
    private static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + "doctor.apk";
    public static boolean isDownload = false;
    String PRIMARY_CHANNEL = "my_channel_01";
    String name = "我是渠道名字";

    //初始化通知
    private void initNotification() {

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
                getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//适配8.0,自行查看8.0的通知，主要是NotificationChannel
            NotificationChannel chan1 = new NotificationChannel(PRIMARY_CHANNEL,
                    "Primary Channel", NotificationManager.IMPORTANCE_DEFAULT);
            chan1.setLightColor(Color.GREEN);
            chan1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(chan1);
            builder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL);
        } else {
            builder = new NotificationCompat.Builder(this, null);
        }
        builder.setContentText("下载进度:" + "0%")//notification的一些设置，具体的可以去官网查看
                .setContentTitle("正在更新...") //设置通知标题
                .setTicker("正在下载")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setOnlyAlertOnce(true)
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon))
                .setSmallIcon(R.mipmap.icon);
        builder.setProgress(100, 0, false);//显示下载进度
        Intent intent = new Intent(this, NotificationClickReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        // 如果存在，启动安装流程
        mNotification = builder.build();
    }


}
