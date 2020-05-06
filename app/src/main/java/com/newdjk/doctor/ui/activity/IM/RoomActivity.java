package com.newdjk.doctor.ui.activity.IM;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.BuildConfig;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.AllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.HangUpTipEntity;
import com.newdjk.doctor.ui.entity.NotifyServiceToSendMessageEntity;
import com.newdjk.doctor.ui.entity.RejectCallTip;
import com.newdjk.doctor.utils.DlgMgr;
import com.newdjk.doctor.utils.MessageObservable;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.av.sdk.AVVideoCtrl;
import com.tencent.callsdk.ILVBCallMemberListener;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.ILiveSDK;
import com.tencent.ilivesdk.adapter.CommonConstants;
import com.tencent.ilivesdk.core.ILiveLoginManager;
import com.tencent.ilivesdk.core.ILiveRoomManager;
import com.tencent.ilivesdk.core.ILiveRoomOption;
import com.tencent.ilivesdk.listener.ILiveEventHandler;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.ilivesdk.view.AVVideoView;
import com.tencent.livesdk.ILVCustomCmd;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveConstants;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.livesdk.ILVLiveRoomOption;
import com.tencent.livesdk.ILVText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tencent on 2018/5/21.
 */
public class RoomActivity extends BasicActivity implements ILVLiveConfig.ILVLiveMsgListener, ILiveLoginManager.TILVBStatusListener, ILiveRoomOption.onExceptionListener, ILiveRoomOption.onRoomDisconnectListener, ILVBCallMemberListener {
    private final String TAG = "DemoHost";
    @BindView(R.id.arv_root)
    AVRootView arvRoot;
    @BindView(R.id.tv_status)
    TextView tvStatus;

    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.sv_scroll)
    ScrollView svScroll;
    @BindView(R.id.iv_info)
    ImageView ivInfo;
    @BindView(R.id.iv_role)
    ImageView ivRole;
    @BindView(R.id.iv_camera)
    ImageView ivCamera;
    @BindView(R.id.iv_switch)
    ImageView ivSwitch;
    @BindView(R.id.iv_flash)
    ImageView ivFlash;
    @BindView(R.id.iv_mic)
    ImageView ivMic;
    @BindView(R.id.ll_controller)
    LinearLayout llController;
    @BindView(R.id.btn_end)
    TextView btnEnd;
    @BindView(R.id.nick_name)
    TextView nickName;
    @BindView(R.id.face_url)
    ImageView faceUrl;
    @BindView(R.id.user_data_layout)
    LinearLayout userDataLayout;
    @BindView(R.id.chronometer)
    Chronometer chronometer;

   /* private TextView tvMsg;
    private ScrollView svScroll;
    private AVRootView arvRoot;*/

    private boolean isCameraOn = true;
    private boolean isMicOn = true;
    private boolean isFlashOn = false;
    private boolean isInfoOn = true;
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private String target;
    private int mCallId;
    private String strMsg = "";
    private String userSig = "";
    private String mAction;
    private String mIdentify;
    private String mFaceUrl;
    private String mName;
    //定义一个HashMap用于存放音频流的ID
    private SoundPool mSoundPool;
    private HashMap mMusicId;
    private int mSoundId;
    public static boolean isVideoing = false;
    public boolean mIsCountTime = false;
    private String patientiinfo;
    private AllRecordForDoctorEntity mAllRecordForDoctorEntity;

    @Override
    protected int initViewResId() {
        return R.layout.demo_host;
    }

    @Override
    protected void initView() {
        isVideoing = true;
        initSoundPool();
        ILiveSDK.getInstance().setChannelMode(CommonConstants.E_ChannelMode.E_ChannelIMSDK);
        mCallId = getIntent().getIntExtra("callId", 0);
        target = getIntent().getStringExtra("target");
        userSig = getIntent().getStringExtra("userSig");
        mAction = getIntent().getStringExtra("action");
        mIdentify = getIntent().getStringExtra("identifier");
        mAllRecordForDoctorEntity =  (AllRecordForDoctorEntity) getIntent().getSerializableExtra("patientiinfo");
        getUserData();
        if (target != null && !target.equals("")) {
            Log.i("RoomActivity", "join");
            joinRoom(mCallId);
        } else {
            Log.i("RoomActivity", "create"+mCallId);
            createRoom(mCallId);
        }
        ILiveSDK.getInstance().addEventHandler(new ILiveEventHandler() {
            @Override
            public void onRoomMemberIn(int roomId, String groupId, String userId) {
                super.onRoomMemberIn(roomId, groupId, userId);
                mSoundPool.release();
                chronometer.setVisibility(View.VISIBLE);
                chronometer.setBase(SystemClock.elapsedRealtime());
                //启动计时器
                chronometer.start();
                mIsCountTime = true;
                userDataLayout.setVisibility(View.GONE);
                Log.i("RoomActivity", "onRoomMemberIn:roomId=" + roomId + ",groupId=" + groupId + ",userId=" + userId);
            }

            @Override
            public void onRoomMemberOut(int roomId, String groupId, String userId) {
                super.onRoomMemberOut(roomId, groupId, userId);
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                chronometer.stop();
                Log.i("RoomActivity", "onRoomMemberOut:roomId=" + roomId + ",groupId=" + groupId + ",userId=" + userId);
                finish();
            }
        });
        ILVLiveManager.getInstance().setAvVideoView(arvRoot);
          MessageObservable.getInstance().addObserver(this);


        //   initRoleDialog();

        arvRoot.setAutoOrientation(false);
        // 打开摄像头预览
        ILiveRoomManager.getInstance().enableCamera(ILiveConstants.FRONT_CAMERA, true);
        /*arvRoot.setSubCreatedListener(new AVRootView.onSubViewCreatedListener() {
            @Override
            public void onSubViewCreated() {
                Log.i("RoomActivity", "UserId=" + ILiveLoginManager.getInstance().getMyUserId());
                arvRoot.renderVideoView(true, ILiveLoginManager.getInstance().getMyUserId(), CommonConstants.Const_VideoType_Camera, true);
            }
        });*/
        arvRoot.setLocalFullScreen(false);
        arvRoot.setSubMarginY(80);
        arvRoot.setGravity(AVRootView.LAYOUT_GRAVITY_RIGHT);
        arvRoot.setSubCreatedListener(new AVRootView.onSubViewCreatedListener() {
            @Override
            public void onSubViewCreated() {
                for (int i = 1; i < ILiveConstants.MAX_AV_VIDEO_NUM; i++) {
                    final int index = i;
                    final AVVideoView videoView = arvRoot.getViewByIndex(i);
                    videoView.setDragable(true);
                    videoView.setGestureListener(new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onSingleTapConfirmed(MotionEvent e) {
                            arvRoot.swapVideoView(0, index);
                            return super.onSingleTapConfirmed(e);
                        }
                    });
                }
            }
        });
    }




    private void getUserData() {
        List<String> list = new ArrayList<>();
        list.add(mIdentify);
        TIMFriendshipManager.getInstance().getUsersProfile(list, new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                if (timUserProfiles.size() > 0) {
                    TIMUserProfile tIMUserProfile = timUserProfiles.get(0);
                    mName = tIMUserProfile.getNickName();
                    mFaceUrl = tIMUserProfile.getFaceUrl();
                    nickName.setText(mName);
                    Glide.with(MyApplication.getContext())
                            .load(mFaceUrl)
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(faceUrl);
                }
            }
        });
    }

    @Override
    protected void initListener() {
        btnEnd.setOnClickListener(this);
        ivSwitch.setOnClickListener(this);
        ivCamera.setOnClickListener(this);
        ivFlash.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        //createRoom();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            /*  case R.id.tv_create :
             *//* getPrivateMapKey(ILiveLoginManager.getInstance().getMyUserId(),
                        getIntValue(etRoom.getText().toString(), -1));*//*
               createRoom(mCallId);
                break;*/
            case R.id.iv_camera:
                isCameraOn = !isCameraOn;
                ILiveRoomManager.getInstance().enableCamera(ILiveRoomManager.getInstance().getCurCameraId(),
                        isCameraOn);
                ((ImageView) findViewById(R.id.iv_camera)).setImageResource(
                        isCameraOn ? R.mipmap.ic_camera_on : R.mipmap.ic_camera_off);
                break;
            case R.id.iv_switch:
                Log.v(TAG, "switch->cur: " + ILiveRoomManager.getInstance().getActiveCameraId() + "/" + ILiveRoomManager.getInstance().getCurCameraId());
                if (ILiveConstants.NONE_CAMERA != ILiveRoomManager.getInstance().getActiveCameraId()) {
                    ILiveRoomManager.getInstance().switchCamera(1 - ILiveRoomManager.getInstance().getActiveCameraId());
                } else {
                    ILiveRoomManager.getInstance().switchCamera(ILiveConstants.FRONT_CAMERA);
                }
                break;
            case R.id.iv_flash:
                toggleFlash();
                break;
            case R.id.iv_info:
                isInfoOn = !isInfoOn;
                ((ImageView) findViewById(R.id.iv_info)).setImageResource(isInfoOn ? R.mipmap.ic_info_on : R.mipmap.ic_info_off);
                findViewById(R.id.tv_status).setVisibility(isInfoOn ? View.VISIBLE : View.INVISIBLE);
                break;
            case R.id.iv_mic:
                isMicOn = !isMicOn;
                ILiveRoomManager.getInstance().enableMic(isMicOn);
                ((ImageView) findViewById(R.id.iv_mic)).setImageResource(
                        isMicOn ? R.mipmap.ic_mic_on : R.mipmap.ic_mic_off);
                break;

            case R.id.iv_role:
               /* if (null != roleDialog)
                    roleDialog.show();*/
                break;
            case R.id.btn_end:
                long time ;
                if (mIsCountTime) {
                     time = SystemClock.elapsedRealtime() - chronometer.getBase();
                    Log.i("RoomActivity", "time=" + time);
                    chronometer.stop();
                }
                else {
                    time = -1;
                }
                if (mAction != null && mAction.equals("dialogActivity")) {
                    EventBus.getDefault().post(new NotifyServiceToSendMessageEntity(134, mIdentify,time));
                } else {
                    EventBus.getDefault().post(new HangUpTipEntity(134,time));
                }
                finish();
               /* if (null != roleDialog)
                    roleDialog.show();*/
                break;
        }
    }

    @Override
    public void onNewTextMsg(ILVText text, String SenderId, TIMUserProfile userProfile) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

 /*   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_host);
        ButterKnife.bind(this);

        // UserInfo.getInstance().getCache(getApplicationContext());

        arvRoot = (AVRootView) findViewById(R.id.arv_root);
        etRoom = (DemoEditText) findViewById(R.id.et_room);
//        etRoom.setText(""+UserInfo.getInstance().getRoom());
        tvMsg = (TextView) findViewById(R.id.tv_msg);
        svScroll = (ScrollView) findViewById(R.id.sv_scroll);

        ILVLiveManager.getInstance().setAvVideoView(arvRoot);
        MessageObservable.getInstance().addObserver(this);
        StatusObservable.getInstance().addObserver(this);

        //   initRoleDialog();

        arvRoot.setAutoOrientation(false);
        // 打开摄像头预览
        ILiveRoomManager.getInstance().enableCamera(ILiveConstants.FRONT_CAMERA, true);
        arvRoot.setSubCreatedListener(new AVRootView.onSubViewCreatedListener() {
            @Override
            public void onSubViewCreated() {
                arvRoot.renderVideoView(true, ILiveLoginManager.getInstance().getMyUserId(), CommonConstants.Const_VideoType_Camera, true);
            }
        });
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        ILVLiveManager.getInstance().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ILVLiveManager.getInstance().onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isVideoing = false;
        if (ILiveConstants.NONE_CAMERA != ILiveRoomManager.getInstance().getActiveCameraId()) {
            ILiveRoomManager.getInstance().enableCamera(ILiveRoomManager.getInstance().getActiveCameraId(), false);
        }
        quitRoom();
         MessageObservable.getInstance().deleteObserver(this);
        EventBus.getDefault().unregister(this);
        //  StatusObservable.getInstance().deleteObserver(this);
        ILVLiveManager.getInstance().onDestory();
        mSoundPool.release();
    }

    // 退出房间
    public int quitRoom() {
        return ILiveRoomManager.getInstance().quitRoom(new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                Log.i("RoomActivity","quitSuccess:data="+data.toString());
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                Log.i("RoomActivity","quitFail:module="+module+",errCode="+errCode+",errMsg="+errMsg);
            }
        });
    }


  /*  @Override
    public void onNewTextMsg(ILVText text, String SenderId, TIMUserProfile userProfile) {
        addMessage(SenderId, DemoFunc.getLimitString(text.getText(), Constants.MAX_SIZE));
    }*/

    @Override
    public void onNewCustomMsg(ILVCustomCmd cmd, String id, TIMUserProfile userProfile) {
        switch (cmd.getCmd()) {
            case ILVLiveConstants.ILVLIVE_CMD_LINKROOM_REQ:     // 跨房邀请
                linkRoomReq(id);
                break;
        }
    }

    @Override
    public void onNewOtherMsg(TIMMessage message) {

    }

    @Override
    public void onForceOffline(int error, String message) {
        finish();
    }

  /*  // 角色对话框
    private RadioGroupDialog roleDialog;
    private void initRoleDialog() {
        final String[] roles = new String[]{ "高清(960*540,25fps)","标清(640*368,20fps)", "流畅(640*368,15fps)"};
        final String[] values = new String[]{Constants.HD_ROLE, Constants.SD_ROLE, Constants.LD_ROLE};
        roleDialog = new RadioGroupDialog(this, roles);
        roleDialog.setTitle(R.string.str_dt_change_role);
        roleDialog.setSelected(0);

        roleDialog.setOnItemClickListener(new RadioGroupDialog.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ILiveRoomManager.getInstance().changeRole(values[position], new ILiveCallBack() {
                    @Override
                    public void onSuccess(Object data) {}

                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        DlgMgr.showMsg(getContenxt(), "change failed:"+module+"|"+errCode+"|"+errMsg);
                    }
                });
            }
        });
    }*/


    private Context getContenxt() {
        return RoomActivity.this;
    }

    // 添加消息
    private void addMessage(String sender, String msg) {
        strMsg += "[" + sender + "]  " + msg + "\n";
        tvMsg.setText(strMsg);
        svScroll.fullScroll(View.FOCUS_DOWN);
    }

    private void joinRoom(int callId) {
        int roomId1 = callId;
        if (-1 == roomId1) {
            DlgMgr.showMsg(getContenxt(), "请输入正确的房间号");
            return;
        }

        if (BuildConfig.IS_DEBUG){
            ILVLiveRoomOption option = new ILVLiveRoomOption("")
                    .privateMapKey(userSig)
                    .autoCamera(ILiveConstants.NONE_CAMERA == ILiveRoomManager.getInstance().getActiveCameraId())
                    .videoMode(ILiveConstants.VIDEOMODE_NORMAL)
                    .roomDisconnectListener(this)
                    .imsupport(true)
                    .controlRole("user")
                    .autoFocus(true);
            ILiveRoomManager.getInstance().joinRoom(roomId1, option, new ILiveCallBack() {
                @Override
                public void onSuccess(Object data) {
                    afterCreate();
                }

                @Override
                public void onError(String module, int errCode, String errMsg) {
                    Log.i("RoomActivity", "module2=" + module + ",errCode=" + errCode + ",errMsg=" + errMsg);
                    //  DlgMgr.showMsg(getContenxt(), "create failed:"+module+"|"+errCode+"|"+errMsg);
                }
            });
        }else {
            ILVLiveRoomOption option = new ILVLiveRoomOption("")
                    .privateMapKey(userSig)
                    .autoCamera(ILiveConstants.NONE_CAMERA == ILiveRoomManager.getInstance().getActiveCameraId())
                    .videoMode(ILiveConstants.VIDEOMODE_NORMAL)
                    .roomDisconnectListener(this)
                    .imsupport(true)
                    .controlRole("ed640")
                    .autoFocus(true);
            ILiveRoomManager.getInstance().joinRoom(roomId1, option, new ILiveCallBack() {
                @Override
                public void onSuccess(Object data) {
                    afterCreate();
                }

                @Override
                public void onError(String module, int errCode, String errMsg) {
                    Log.i("RoomActivity", "module2=" + module + ",errCode=" + errCode + ",errMsg=" + errMsg);
                    //  DlgMgr.showMsg(getContenxt(), "create failed:"+module+"|"+errCode+"|"+errMsg);
                }
            });
        }




       /* ILVLiveRoomOption option = new ILVLiveRoomOption("")
                .autoCamera(ILiveConstants.NONE_CAMERA == ILiveRoomManager.getInstance().getActiveCameraId())
                .videoMode(ILiveConstants.VIDEOMODE_NORMAL)
                .controlRole("HD")
                .autoMic(true)
                .autoFocus(true);*/
       /* ILVLiveRoomOption option = new ILVLiveRoomOption("")
                .privateMapKey(userSig)
                .autoCamera(ILiveConstants.NONE_CAMERA == ILiveRoomManager.getInstance().getActiveCameraId())
                .videoMode(ILiveConstants.VIDEOMODE_NORMAL)
                .roomDisconnectListener(this)
                .controlRole("HD")
                .autoFocus(true);*/
     /*   ILiveRoomOption option = new ILiveRoomOption()
                .imsupport(false)       // 不需要IM功能
                .exceptionListener(this)  // 监听异常事件处理
                .roomDisconnectListener(this)   // 监听房间中断事件
                .controlRole("LiveMaster")  // 使用LiveMaster角色
                .autoCamera(true)       // 进房间后自动打开摄像头并上行
                .autoMic(true);         // 进房间后自动要开Mic并上行*/

    }

    private void showChoiceDlg(final int callId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("房间已存在，是否加入房间？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        joinRoom(callId);
                        dialogInterface.dismiss();
                    }
                });
        DlgMgr.showAlertDlg(this, builder);
    }

    // 加入房间
    private void createRoom(int callId) {
        final int roomId = callId;
        if (-1 == roomId) {
            DlgMgr.showMsg(getContenxt(), "请输入正确的房间号");
            return;
        }
        ILVLiveRoomOption option = new ILVLiveRoomOption(ILiveLoginManager.getInstance().getMyUserId())
                .autoCamera(true)
                .videoMode(ILiveConstants.VIDEOMODE_NORMAL)
                .controlRole("ed640")
                .imsupport(true)
                .roomDisconnectListener(this)
                .autoFocus(true);
        ILVLiveManager.getInstance().createRoom(roomId,
                option, new ILiveCallBack() {
                    @Override
                    public void onSuccess(Object data) {
                        Log.i("RoomActivity", "创建房间成功"+data.toString());

                        afterCreate();
                    }

                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        Log.i("RoomActivity", "module1=" + module + ",errCode=" + errCode + ",errMsg=" + errMsg);
                        if (module.equals(ILiveConstants.Module_IMSDK) && 10021 == errCode) {
                            // 被占用，改加入
                            showChoiceDlg(roomId);
                        } else {
                            if (mAction != null && mAction.equals("dialogActivity")) {
                                EventBus.getDefault().post(new NotifyServiceToSendMessageEntity(134, mIdentify,-1));
                            } else {
                                EventBus.getDefault().post(new HangUpTipEntity(134,-1));
                            }
                            finish();
                            //     toast( "create failed:" + module + "|" + errCode + "|" + errMsg);
                        }
                    }
                });


    }

    private void afterCreate() {
       /* UserInfo.getInstance().setRoom(ILiveRoomManager.getInstance().getRoomId());
        UserInfo.getInstance().writeToCache(this);
        etRoom.setEnabled(false);*/
        // findViewById(R.id.tv_create).setVisibility(View.INVISIBLE);

        findViewById(R.id.iv_camera).setVisibility(View.VISIBLE);
        findViewById(R.id.iv_flash).setVisibility(View.VISIBLE);
        findViewById(R.id.iv_mic).setVisibility(View.VISIBLE);
        findViewById(R.id.iv_info).setVisibility(View.VISIBLE);
        findViewById(R.id.iv_role).setVisibility(View.VISIBLE);
        ILiveRoomManager.getInstance().enableCamera(ILiveRoomManager.getInstance().getCurCameraId(), isCameraOn);
        //   mainHandler.postDelayed(infoRun, 500);
    }

    private void toggleFlash() {
        if (ILiveConstants.BACK_CAMERA != ILiveRoomManager.getInstance().getActiveCameraId()) {
            return;
        }
        AVVideoCtrl videoCtrl = ILiveSDK.getInstance().getAvVideoCtrl();
        if (null == videoCtrl) {
            return;
        }

        final Object cam = videoCtrl.getCamera();
        if ((cam == null) || (!(cam instanceof Camera))) {
            return;
        }
        final Camera.Parameters camParam = ((Camera) cam).getParameters();
        if (null == camParam) {
            return;
        }

        Object camHandler = videoCtrl.getCameraHandler();
        if ((camHandler == null) || (!(camHandler instanceof Handler))) {
            return;
        }

        //对摄像头的操作放在摄像头线程
        if (isFlashOn == false) {
            ((Handler) camHandler).post(new Runnable() {
                public void run() {
                    try {
                        camParam.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        ((Camera) cam).setParameters(camParam);
                        isFlashOn = true;
                    } catch (RuntimeException e) {
                        Log.d(TAG, "setParameters->RuntimeException");
                    }
                }
            });
        } else {
            ((Handler) camHandler).post(new Runnable() {
                public void run() {
                    try {
                        camParam.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        ((Camera) cam).setParameters(camParam);
                        isFlashOn = false;
                    } catch (RuntimeException e) {
                        Log.d(TAG, "setParameters->RuntimeException");
                    }
                }
            });
        }
    }

    // 拒绝跨房连麦
    private void refuseLink(String id) {
        ILVLiveManager.getInstance().refuseLinkRoom(id, null);
    }

    // 同意跨房连麦
    private void acceptLink(String id) {
        ILVLiveManager.getInstance().acceptLinkRoom(id, null);
    }

    private void linkRoomReq(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("视频邀请");
        builder.setMessage("接受邀请");
        builder.setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                refuseLink(id);
            }
        });
        builder.setPositiveButton("接受", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                acceptLink(id);
            }
        });
        DlgMgr.showAlertDlg(this, builder);
    }

    protected boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this,
                        permissions.toArray(new String[0]),
                        100);
                return false;
            }
        }

        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        //   super.onBackPressed();
    }

    public int getIntValue(String strData, int defValue) {
        try {
            return Integer.parseInt(strData);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    /**
     * 从业务服务器获取privateMapKey信息
     */
    public void getPrivateMapKey(String userId, int roomId) {
        try {
           /* JSONObject jsonReq = new JSONObject();
            jsonReq.put("userID", userId);
            jsonReq.put("roomID", roomId);
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonReq.toString());
            Request req = new Request.Builder()
                    .url("https://xzb.qcloud.com/webrtc/weapp/webrtc_room"+"/get_privatemapkey")
                    .post(body)
                    .build();
            Log.i(TAG, "getPrivateMapKey->url: "+req.url().toString());
            Log.i(TAG, "getPrivateMapKey->post: "+body.toString());*/
            Map<String, String> map = new HashMap<>();
            map.put("userID", userId);
            map.put("roomID", String.valueOf(roomId));
            mMyOkhttp.post().url("https://xzb.qcloud.com/webrtc/weapp/webrtc_room/get_privatemapkey").params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
                @Override
                public void onSuccess(int statusCode, Entity entity) {
                    Log.i("cc", "data11=" + entity.getData().toString());
                    LoadDialog.clear();
                    if (entity.getCode() == 0) {

                    } else {
                        Log.i("cc", "errorcode1=" + entity.getCode() + "errormessage1=" + entity.getMessage());
                        toast(entity.getMessage());
                    }

                }

                @Override
                public void onFailures(int statusCode, String errorMsg) {
                    CommonMethod.requestError(statusCode, errorMsg);
                }
            });
     /*
            okHttpClient.newCall(req).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    notifySyncPrivateMapKeyFailed(1, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()){
                        notifySyncPrivateMapKeyFailed(response.code(), response.message());
                    }else{
                        parseKeyInfo(response.body().string());
                    }
                }
            });*/
        } catch (Exception e) {
            // notifySyncPrivateMapKeyFailed(3, e.toString());
        }
    }

    @Override
    public void onException(int exceptionId, int errCode, String errMsg) {
        Log.i("room", "exceptionId=" + exceptionId+"   errCode"+errCode+"   errMsg"+errMsg);
    }

    @Override
    public void onRoomDisconnect(int errCode, String errMsg) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(RejectCallTip userEvent) {

        cancel(mCallId);
        finish();
        mSoundPool.release();
    }

    @Override
    public void onCameraEvent(String id, boolean bEnable) {

    }

    @Override
    public void onMicEvent(String id, boolean bEnable) {

    }


    private void initSoundPool() {
        if (Build.VERSION.SDK_INT >= 21) {
            mSoundPool = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(new AudioAttributes.Builder()
                            .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                            .build())
                    .build();
        } else {
            // This old constructor is deprecated, but we need it for
            // compatibility.
            mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        mSoundId = mSoundPool.load(this, R.raw.weixin2, 1);
        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

                Log.d(TAG, "load完毕" + status);
                mSoundPool.play(mSoundId, 1.0f, 1.0f, 1, -1, 1.0f);

            }
        });


    }


    private void cancel(int RoomId) {

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        HashMap<String, String> params = new HashMap<>();
        params.put("SenderId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        params.put("SenderName", String.valueOf(SpUtils.getString(Contants.Name)));
        if (mAllRecordForDoctorEntity!=null){
            params.put("ReceiverId", mAllRecordForDoctorEntity.getPatientId()+"");
            params.put("ReceiverName", mAllRecordForDoctorEntity.getPatientName());
            params.put("Action", "2");
            params.put("RoomId", RoomId+"");
            params.put("ToType", "1");
            params.put("Message", "");
        }


        mMyOkhttp.post().url(HttpUrl.sendVideo).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity response) {
                Log.d("chat","给IOS发送视频聊天成功");
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
            }
        });

    }
}
