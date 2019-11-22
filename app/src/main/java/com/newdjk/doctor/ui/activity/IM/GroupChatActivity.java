package com.newdjk.doctor.ui.activity.IM;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.BuildConfig;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.iInterface.OnTabItemClickListener;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.service.MyService;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.tools.MainConstant;
import com.newdjk.doctor.ui.activity.ArchivesActivity;
import com.newdjk.doctor.ui.activity.ChickUnitActivity;
import com.newdjk.doctor.ui.activity.Mdt.AddMDTDocumentActivity;
import com.newdjk.doctor.ui.activity.Mdt.MDTInputReportActivity;
import com.newdjk.doctor.ui.activity.Mdt.MDTInputReportFromListActivity;
import com.newdjk.doctor.ui.activity.MedicalServiceActivity;
import com.newdjk.doctor.ui.activity.MissionActivity;
import com.newdjk.doctor.ui.activity.PrescriptionActivity;
import com.newdjk.doctor.ui.activity.ReplyQuickActivity;
import com.newdjk.doctor.ui.activity.min.GroupMemberActivity;
import com.newdjk.doctor.ui.adapter.GroupChatAdapter;
import com.newdjk.doctor.ui.entity.AboutUsEntity;
import com.newdjk.doctor.ui.entity.AllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.AppLicationEntity;
import com.newdjk.doctor.ui.entity.AutheninfoEntity;
import com.newdjk.doctor.ui.entity.ChatHistoryDataEntity;
import com.newdjk.doctor.ui.entity.ChatHistoryEntity;
import com.newdjk.doctor.ui.entity.CloseMDTEntity;
import com.newdjk.doctor.ui.entity.ConsultMessageEntity;
import com.newdjk.doctor.ui.entity.CustomMessageEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.HangUpTipEntity;
import com.newdjk.doctor.ui.entity.ImageInfoArrayEntity;
import com.newdjk.doctor.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.ui.entity.MessageEventRecent;
import com.newdjk.doctor.ui.entity.MsgBodyEntity;
import com.newdjk.doctor.ui.entity.MsgContentEntity;
import com.newdjk.doctor.ui.entity.OnlineRenewalDataEntity;
import com.newdjk.doctor.ui.entity.PatientInfoEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.ui.entity.RejectCallTip;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.TestDataEntity;
import com.newdjk.doctor.ui.entity.UpdateAllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.UpdateImMessageEntity;
import com.newdjk.doctor.ui.entity.UpdateImStatusEntity;
import com.newdjk.doctor.ui.entity.UpdateMessageListEntity;
import com.newdjk.doctor.ui.entity.UpdateViewEntity;
import com.newdjk.doctor.ui.entity.YWXListenerEntity;
import com.newdjk.doctor.ui.fragment.TabChatFragment;
import com.newdjk.doctor.utils.AppLicationUtils;
import com.newdjk.doctor.utils.AppUtils;
import com.newdjk.doctor.utils.FragmentFactory;
import com.newdjk.doctor.utils.LogOutUtil;
import com.newdjk.doctor.utils.LogUtils;
import com.newdjk.doctor.utils.MyTIMMessage;
import com.newdjk.doctor.utils.NetworkUtil;
import com.newdjk.doctor.utils.SQLiteUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.TimeUtil;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.DownloadCertDialog;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.PageIndicator;
import com.tencent.TIMCallBack;
import com.tencent.TIMConnListener;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElemType;
import com.tencent.TIMGroupDetailInfo;
import com.tencent.TIMGroupManager;
import com.tencent.TIMImageElem;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageLocator;
import com.tencent.TIMMessageOfflinePushSettings;
import com.tencent.TIMMessageRevokedListener;
import com.tencent.TIMOfflinePushToken;
import com.tencent.TIMSoundElem;
import com.tencent.TIMTextElem;
import com.tencent.TIMUserProfile;
import com.tencent.TIMUserStatusListener;
import com.tencent.TIMValueCallBack;
import com.tencent.callsdk.ILVCallConfig;
import com.tencent.callsdk.ILVCallListener;
import com.tencent.callsdk.ILVCallManager;
import com.tencent.callsdk.ILVCallNotification;
import com.tencent.callsdk.ILVCallNotificationListener;
import com.tencent.callsdk.ILVIncomingListener;
import com.tencent.callsdk.ILVIncomingNotification;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.ILiveFunc;
import com.tencent.ilivesdk.core.ILiveLoginManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.org.bjca.sdk.core.kit.BJCASDK;
import cn.org.bjca.sdk.core.kit.YWXListener;
import cn.org.bjca.sdk.core.values.ConstantParams;
import cn.org.bjca.sdk.core.values.EnvType;
import omrecorder.AudioChunk;
import omrecorder.AudioRecordConfig;
import omrecorder.OmRecorder;
import omrecorder.PullTransport;
import omrecorder.PullableSource;
import omrecorder.Recorder;

public class GroupChatActivity extends BasicActivity implements ILVIncomingListener, ILVCallListener, ILVCallNotificationListener, Observer, TIMMessageRevokedListener {


    private static final String TAG = "ChatActivity";
    @BindView(R.id.top_left)
    ImageView topLeft;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.top_right)
    ImageView topRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.relat_titlebar)
    RelativeLayout relatTitlebar;
    @BindView(R.id.liear_titlebar)
    LinearLayout liearTitlebar;
    @BindView(R.id.reject_tip)
    TextView rejectTip;
    @BindView(R.id.count_time)
    TextView countTime;
    @BindView(R.id.count_time_border)
    LinearLayout countTimeBorder;
    @BindView(R.id.accept_valid_time)
    TextView acceptValidTime;
    @BindView(R.id.accept)
    LinearLayout accept;
    @BindView(R.id.reject)
    LinearLayout reject;
    @BindView(R.id.accept_border)
    LinearLayout acceptBorder;
    @BindView(R.id.accept_layout)
    LinearLayout acceptLayout;
    @BindView(R.id.prescription)
    TextView prescription;
    @BindView(R.id.prescription_layout)
    FrameLayout prescriptionLayout;
    @BindView(R.id.zhongyao_prescription)
    TextView zhongyaoPrescription;
    @BindView(R.id.zhongyao_prescription_layout)
    FrameLayout zhongyaoPrescriptionLayout;
    @BindView(R.id.video_interrogation)
    TextView videoInterrogation;
    @BindView(R.id.video_interrogation_layout)
    FrameLayout videoInterrogationLayout;
    @BindView(R.id.medical_service)
    TextView medicalService;
    @BindView(R.id.checkTable)
    TextView checkTable;
    @BindView(R.id.fr_checkTable)
    FrameLayout frCheckTable;
    @BindView(R.id.empty1)
    FrameLayout empty1;
    @BindView(R.id.empty2)
    FrameLayout empty2;
    @BindView(R.id.empty3)
    FrameLayout empty3;
    @BindView(R.id.reception_functions)
    LinearLayout receptionFunctions;
    @BindView(R.id.btn_voice)
    ImageButton btnVoice;
    @BindView(R.id.btn_keyboard)
    ImageButton btnKeyboard;
    @BindView(R.id.voice_panel)
    TextView voicePanel;
    @BindView(R.id.input)
    EditText input;
    @BindView(R.id.btnEmoticon)
    ImageButton btnEmoticon;
    @BindView(R.id.text_panel)
    LinearLayout textPanel;
    @BindView(R.id.btn_add)
    ImageButton btnAdd;
    @BindView(R.id.btn_send)
    ImageButton btnSend;
    @BindView(R.id.input_1)
    LinearLayout input1;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.dot_horizontal)
    LinearLayout dotHorizontal;
    @BindView(R.id.morePanel)
    LinearLayout morePanel;
    @BindView(R.id.emoticonPanel)
    LinearLayout emoticonPanel;
    @BindView(R.id.input_layout)
    RelativeLayout inputLayout;
    @BindView(R.id.input_border)
    LinearLayout inputBorder;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.accept_time)
    TextView acceptTime;
    @BindView(R.id.accept_tip)
    RelativeLayout acceptTip;
    @BindView(R.id.chat_recycler_view)
    RecyclerView chatRecyclerView;
    @BindView(R.id.easylayout)
    EasyRefreshLayout easylayout;
    @BindView(R.id.voice_sending)
    ImageView voiceSending;
    @BindView(R.id.cancle_voice_sending)
    TextView cancleVoiceSending;
    @BindView(R.id.activity_chat)
    RelativeLayout activityChat;
    @BindView(R.id.medical_advice_doctor)
    TextView medicalAdviceDoctor;
    @BindView(R.id.medical_advice_report)
    TextView medicalAdviceReport;
    @BindView(R.id.medical_advice_suggest)
    TextView medicalAdviceSuggest;
    @BindView(R.id.medical_end_suggest)
    TextView medicalEndSuggest;
    @BindView(R.id.medical_service_layout)
    FrameLayout medicalServiceLayout;
    @BindView(R.id.medical_advice_doctor_layout)
    FrameLayout medicalAdviceDoctorLayout;
    @BindView(R.id.medical_advice_report_layout)
    FrameLayout medicalAdviceReportLayout;
    @BindView(R.id.medical_advice_suggest_layout)
    FrameLayout medicalAdviceSuggestLayout;
    @BindView(R.id.medical_end_suggest_layout)
    FrameLayout medicalEndSuggestLayout;
    @BindView(R.id.tv_chat_stype)
    TextView tvChatStype;

    private boolean mIsRecording;
    private List<MyTIMMessage> mTimMessages;
    private int mLastY;
    private TIMConversation mConversation;
    private String mIdentifier;
    private MediaRecorder mRecorder;
    private String mSoundPath;
    private long mStartRecordeTime;
    private int mAccountId;
    private final static int REQ_PERMISSION_CODE = 0x100;
    private GroupChatAdapter mAdapter;
    private final int FLING_MIN_DISTANCE = 200;// 手指滑动距离
    private boolean mIsCancle = false;
    private final int REQUEST_CODE_TAKE_PICTURE = 1;
    private final int IMAGE_REQUEST_CODE = 2;
    private final int SERVICE_PACKAGE_CODE = 3;
    private final int PRESCRIPTION_CODE = 4;
    private final int MISSION_CODE = 5;
    private final int QUICK_REPLY_CODE = 6;
    private String mPicturePath;
    public AnimationDrawable mAnimationDrawable;
    private LinearLayout callView, loginView, llDstNums;
    private AlertDialog mIncomingDlg;
    private int mCurIncomingId;
    private String mPrescriptionMessage;
    private AllRecordForDoctorEntity mAllRecordForDoctorEntity;
    private String mAction;
    private long mAcceptTime;
    private long mNowTime;
    private CountDownTimer mTimer;
    private long remainingTime;
    //问诊的有效时间48小时
    private final long VALID_TIME = 86400000 * 2;
    private final long VALID_TIME_HALF = 86400000;
    private int mMedicalTempId = 1;
    private Gson mGson = new Gson();
    private String mImagePath;
    private int mStatus = 0;
    private String validTime;
    private String mServiceCode = null;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int mMsgTimestampStart;
    private String mName = "";
    private String mofflinName = "医生回复消息:";
    // 缓冲区字节大小
    private int bufferSizeInBytes = 0;

    //AudioName裸音频数据文件 ，麦克风
    private String AudioName = "";

    //NewAudioName可播放的音频文件
    private String NewAudioName = "";

    private Recorder audioRecord;
    private boolean mIsImsdkReflashComplete = false;
    private boolean mIsLocalDatabaseReflashComplete = false;
    private boolean mIsServerReflashComplete = false;
    //private TIMMessage mTIMMessage = new TIMMessage();
    private TIMMessage mTIMMessage;
    private int index = 1;
    private boolean isRecord = false;// 设置正在录制的状态
    private String mAcceptLastTimeStr;
    private long mAcceptLastTime;
    private int mDoctype;
    private String mTitle;
    private int mPosition;
    public static final int REQUEST_CALL_PERMISSION = 10111; //拨号请求码
    private int mLoginStatus;
    private boolean mIsTimeout = false;
    private CountDownTimer mOutTimeTimer;
    private boolean mIsHasOpenPres = false;
    private boolean mIsHasOpenTCMPres = false;
    private TIMMessageOfflinePushSettings.AndroidSettings mAndroidSettings;
    private TIMMessageOfflinePushSettings mSettings;
    private DownloadCertDialog mDialog;
    private OnlineRenewalDataEntity mOnlineRenewalDataEntity;
    private ConsultMessageEntity mConsultMessageEntity;
    private InquiryRecordListDataEntity mInquiryRecordListDataEntity;
    private FragmentFactory fragmentFactory;
    private PagerAdapter mbuttonAdapter;
    private int fromHome = 0;
    private boolean cancallVideo = false;
    private List<TIMUserProfile> UserProfile;
    List<AppLicationEntity> listuse = new ArrayList<>();
    private MDTDetailEntity mMDTDetailEntity;

    @Override
    protected int initViewResId() {
        return R.layout.activity_chat_group;
    }

    @Override
    protected void initView() {
        mDoctype = SpUtils.getInt(Contants.DocType, 0);
        mPosition = SpUtils.getInt(Contants.Position, 0);
        mLoginStatus = SpUtils.getInt(Contants.Status, 0);
        mStatus = getIntent().getIntExtra("status", 8);
        mAction = getIntent().getStringExtra("action");
        mMDTDetailEntity = (MDTDetailEntity) getIntent().getSerializableExtra("MDTDetailEntity");
        if (mMDTDetailEntity!=null){
            if (mMDTDetailEntity.getDrId()==MyApplication.mDoctorInfoByIdEntity.getDrId()){
                MyApplication.ismainDoctor=true;
            }else {
                MyApplication.ismainDoctor=false;
            }
        }


        if (BuildConfig.IS_DEBUG) {
            tvChatStype.setVisibility(View.VISIBLE);
            if (mMDTDetailEntity.getSubjectType() == 1) {
                tvChatStype.setText("咨询");
            } else if (mMDTDetailEntity.getSubjectType() == 2) {
                tvChatStype.setText("分诊");
            } else {
                tvChatStype.setText("会诊");
            }
        }

        fromHome = getIntent().getIntExtra("fromHome", 0);
        if (mDoctype == 2) {
            prescriptionLayout.setVisibility(View.GONE);
            zhongyaoPrescriptionLayout.setVisibility(View.GONE);
            videoInterrogationLayout.setVisibility(View.GONE);
            frCheckTable.setVisibility(View.GONE);
//            empty1.setVisibility(View.GONE);
//            empty2.setVisibility(View.GONE);
//            empty3.setVisibility(View.GONE);
            videoInterrogation.setText("远程护理");
            medicalService.setText("推荐服务");
            if (mAction != null && mAction.equals("videoInterrogation") && mStatus != 0) {
                videoInterrogationLayout.setVisibility(View.GONE);

            } else if (mAction != null && mAction.equals("videoInterrogationfromhhome") && mStatus != 0) {
                videoInterrogationLayout.setVisibility(View.GONE);
            } else {
                videoInterrogationLayout.setVisibility(View.GONE);
            }

        } else if (mDoctype == 1) {


            if (mAction != null && mAction.equals("videoInterrogation") && mStatus != 0) {

                videoInterrogationLayout.setVisibility(View.GONE);
            } else if (mAction != null && mAction.equals("videoInterrogationfromhhome") && mStatus != 0) {
                videoInterrogationLayout.setVisibility(View.GONE);
            } else {
                videoInterrogationLayout.setVisibility(View.GONE);
            }
        }
     /*   inputBorder.setVisibility(View.VISIBLE);
        acceptLayout.setVisibility(View.GONE);*/
        creatAudioRecord();

        mAccountId = getIntent().getIntExtra("accountId", 0);
        Log.i("ChatActivity", "accountId=" + mAccountId);
        // mStatus == 0 表示从患者列表的患者档案发送消息进去，其他的就是从业务列表进去
        easylayout.setLoadMoreModel(LoadModel.NONE);

        if (mStatus == 0) {
            acceptTip.setVisibility(View.GONE);
            inputBorder.setVisibility(View.VISIBLE);
            acceptLayout.setVisibility(View.GONE);
            tvRight.setVisibility(View.VISIBLE);

            //   receptionFunctions.setVisibility(View.GONE);
        } else {
            //mAction 表示业务类型，videoInterrogation表示视频问诊，onlineRenewal表示续方问诊，pictureConsult表示图文问诊
            //type 表示业务状态，0是待问诊，1是问诊中，2是已完成
            getCurrentTime();
        }
        mImagePath = getIntent().getStringExtra("imgPath");
        chatRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        hideInput();
                        morePanel.setVisibility(View.GONE);
                        break;
                }
                return false;
            }
        });
        input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    morePanel.setVisibility(View.GONE);
                }
            }
        });

        voiceSending.setImageResource(R.drawable.voice_tip);
        mAnimationDrawable = (AnimationDrawable) voiceSending.getDrawable();
        input.addTextChangedListener(new TextWatcher() {
                                         @Override
                                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                         }

                                         @Override
                                         public void onTextChanged(CharSequence s, int start, int before, int count) {

                                         }

                                         @Override
                                         public void afterTextChanged(Editable s) {
                                             if (input.getText() != null && !input.getText().toString().trim().equals("")) {
                                                 btnAdd.setVisibility(View.GONE);
                                                 btnSend.setVisibility(View.VISIBLE);
                                             } else {
                                                 btnAdd.setVisibility(View.VISIBLE);
                                                 btnSend.setVisibility(View.GONE);
                                             }
                                         }
                                     }
        );
        mOutTimeTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                mIsTimeout = true;
                voicePanel.setText(getString(R.string.chat_press_talk));
                voiceSending.setVisibility(View.GONE);
                mAnimationDrawable.selectDrawable(0);
                mAnimationDrawable.stop();
                sendSoundMessage(true);
            }
        };
        voicePanel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    voicePanel.setText(getString(R.string.chat_release_send));
                    mLastY = (int) event.getY();
                    voiceSending.setVisibility(View.VISIBLE);
                    mAnimationDrawable.start();
                    mOutTimeTimer.start();
                    startRecordAndFile();

                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    int Y = (int) event.getY();
                    if (!mIsTimeout) {
                        if (upMove(Y)) {
                            mIsCancle = true;
                            voicePanel.setText(getString(R.string.chat_release_cancel));
                            cancleVoiceSending.setVisibility(View.VISIBLE);
                            voiceSending.setVisibility(View.GONE);
                        } else {
                            mIsCancle = false;
                            voiceSending.setVisibility(View.VISIBLE);
                            cancleVoiceSending.setVisibility(View.GONE);
                            voicePanel.setText(getString(R.string.chat_release_send));
                        }
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    if (!mIsTimeout) {
                        mOutTimeTimer.cancel();
                        voicePanel.setText(getString(R.string.chat_press_talk));
                        voiceSending.setVisibility(View.GONE);
                        mAnimationDrawable.selectDrawable(0);
                        mAnimationDrawable.stop();
                        if (mIsCancle) {
                            cancleVoiceSending.setVisibility(View.GONE);
                            sendSoundMessage(false);
                        } else {
                            sendSoundMessage(true);
                        }
                    }
                    mIsTimeout = false;
                }

                return false;
            }
        });
        mTimMessages = new ArrayList<>();
        mIdentifier = getIntent().getStringExtra(Contants.FRIEND_IDENTIFIER);

        mPrescriptionMessage = getIntent().getStringExtra("prescriptionMessage");

        initToolbar();
        Log.i("zdp", "mIdentifier=" + mIdentifier);
        //  TIMManager.getInstance().addMessageListener(this);
        mConversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, mIdentifier);
        // PushUtil.getInstance();
        // MessageEvent.getInstance().addObserver(this);
        mConversation.setReadMessage();
       /* List<AllRecordForDoctorEntity>  list =  SQLiteUtils.getInstance().selectImDataByServiceCodeAndId(mServiceCode,mIdentifier);
        if (list.size() > 0) {
            AllRecordForDoctorEntity allRecordForDoctorEntity = list.get(0);
            allRecordForDoctorEntity.setUnReadNum(0);
            SQLiteUtils.getInstance().updateImData(allRecordForDoctorEntity);
        }*/
        SQLiteUtils.getInstance().updateImData(mIdentifier);
        setAllDataRed(mIdentifier);
        Log.e("MainActivity", "mIdentifier=" + mIdentifier);
        EventBus.getDefault().post(new UpdateImMessageEntity("-1"));
        mAdapter = new GroupChatAdapter(mTimMessages, this, mPrescriptionMessage, mImagePath, mName, mConversation, mIsHasOpenPres, mIsHasOpenTCMPres);
        mAdapter.setIdentifer(mIdentifier);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        chatRecyclerView.setLayoutManager(layoutManager);
        // layoutManager.setReverseLayout(true);
        chatRecyclerView.setAdapter(mAdapter);


        initMessage(mTIMMessage);
        //设置当前消息的离线推送配置
        mSettings = new TIMMessageOfflinePushSettings();
        mSettings.setEnabled(true);

//设置离线推送扩展信息
        JSONObject object = new JSONObject();
        try {
            object.put("level", 15);
            object.put("task", "TASK15");
            mSettings.setExt(object.toString().getBytes("utf-8"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置在 Android 设备上收到消息时的离线配置
        mAndroidSettings = new TIMMessageOfflinePushSettings.AndroidSettings();
//ImSDK 2.5.3 之前的构造方式
//TIMMessageOfflinePushSettings.AndroidSettings androidSettings = settings.new AndroidSettings();
        mAndroidSettings.setTitle(mofflinName);
//推送自定义通知栏消息，接收方收到消息后单击通知栏消息会给应用回调（针对小米、华为离线推送）
        mAndroidSettings.setNotifyMode(TIMMessageOfflinePushSettings.NotifyMode.Normal);
//设置 Android 设备收到消息时的提示音，声音文件需要放置到 raw 文件夹
        // mAndroidSettings.setSound(Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.weixin2));
       /* mMsgTimestampStart = 1542875244;
        GetChatHistoryByPage(String.valueOf(SpUtils.getInt(Contants.Id, 0)), String.valueOf(mAccountId), mMsgTimestampStart);*/
        //input.setO

        chatRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    chatRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mAdapter.getItemCount() > 0) {
                                chatRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                            }
                        }
                    });
                }
            }
        });


        //  initviewpager();
    }


    private void initviewpager() {

        listuse.clear();


        //影藏MDT分诊
        if (mMDTDetailEntity.getSubjectType() == 1) {

            //没有开通分诊
            listuse.addAll(AppLicationUtils.getListChat());

        } else if (mMDTDetailEntity.getSubjectType() == 2) { //分诊
            listuse.addAll(AppLicationUtils.getListChatwithoutMDT());
            //诊疗状态(0-待接诊，1-已接诊，2-已拒诊，3：未接诊，4：已出报告，5：已结束，6：已取消)  会诊IM中，如果已经发送过结论性建议，此处显示：查看结论性建议，点击进去就只能查看
//            if (mMDTDetailEntity.getPayStatus() == 2) {
//                if (mMDTDetailEntity.getSubjectStatus() == 4 || mMDTDetailEntity.getSubjectStatus() == 5) {
//                    //查看结论性建议
//                    medicalAdviceReport.setText("查看会诊专家意见");
//                }
//            }

        } else if (mMDTDetailEntity.getSubjectType() == 3) {//会诊
            listuse.addAll(AppLicationUtils.getListChatHuizhen());
            if (mMDTDetailEntity.getPayStatus() == 2) {
                if (mMDTDetailEntity.getSubjectStatus() == 4 || mMDTDetailEntity.getSubjectStatus() == 5) {
                    //查看结论性建议
                    medicalEndSuggest.setText("查看最终结论性意见");
                }
            }


        }
        mbuttonAdapter = new PagerAdapter(getSupportFragmentManager());

        viewpager.setAdapter(mbuttonAdapter);
        viewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                viewpager.setCurrentItem(position, false);
                TabChatFragment homeTabFragment = (TabChatFragment) mbuttonAdapter.getItem(position);
                homeTabFragment.setdata(listuse, position);
            }
        });
        if (listuse.size() <= 8) {
            dotHorizontal.setVisibility(View.GONE);
        } else {
            dotHorizontal.setVisibility(View.VISIBLE);
            viewpager.addOnPageChangeListener(new PageIndicator(getContext(), dotHorizontal, listuse.size() / 8 + 1));

        }
        viewpager.setCurrentItem(0, false);
        mbuttonAdapter.notifyDataSetChanged();
    }

    private void setAllDataRed(final String mIdentifier) {

        TIMConversation conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.C2C,    //会话类型：单聊
                mIdentifier);      //会话对方用户帐号
        //将此会话的所有消息标记为已读
        List<TIMMessage> lastMsgs = conversation.getLastMsgs(1);
        if (lastMsgs != null) {
            if (lastMsgs.size() > 0) {
                TIMMessage msg = lastMsgs.get(0);
                conversation.setReadMessage(msg, new TIMCallBack() {
                    @Override
                    public void onError(int code, String desc) {
                        Log.e("setReadMessage", "设置消息已读setReadMessage failed, code: " + code + "|desc: " + desc);
                    }

                    @Override
                    public void onSuccess() {
                        Log.e("setReadMessageon", "设置消息已读Success-----" + mIdentifier);

                        MessageEventRecent messageEvent = new MessageEventRecent();
                        messageEvent.setmType(MainConstant.UpdateRecentList);
                        EventBus.getDefault().post(messageEvent);
                    }
                });
            }
        }


    }


    private void getCurrentTime() {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(HttpUrl.getCurrentTime).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity response) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = response.getData().toString();
                try {
                    Date date = format.parse(time);
                    mNowTime = date.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (mAction != null && mAction.equals("videoInterrogation")) {
                    mServiceCode = "1102";
                    mAllRecordForDoctorEntity = (AllRecordForDoctorEntity) getIntent().getSerializableExtra("inquiryRecordListDataEntity");
                    if (mAllRecordForDoctorEntity != null) {
                        mImagePath = mAllRecordForDoctorEntity.getApplicantHeadImgUrl();
                        int type = mAllRecordForDoctorEntity.getStatus();
                        SpUtils.put(Contants.patientName, mAllRecordForDoctorEntity.getPatientName());
                        SpUtils.put(Contants.patientID, mAllRecordForDoctorEntity.getPatientId());
                        if (type == 0) {
                            acceptLayout.setVisibility(View.VISIBLE);

                            inputBorder.setVisibility(View.GONE);

                            acceptTip.setVisibility(View.VISIBLE);
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.huang));
                            int end = mAllRecordForDoctorEntity.getReExaminationDate().indexOf(" ");
                            String date = "";
                            if (end == -1) {
                                date = mAllRecordForDoctorEntity.getReExaminationDate();

                            } else {
                                date = mAllRecordForDoctorEntity.getReExaminationDate().substring(0, end);

                            }
                            String startTime = mAllRecordForDoctorEntity.getReExaminationStartTime();
                            String endTime = mAllRecordForDoctorEntity.getReExaminationEndTime();
                            String dateTime = date + " " + startTime;
                            final String remainAcceptTime = date + " " + endTime;
                            mAcceptTime = date2TimeStamp(dateTime, "yyyy-MM-dd HH:mm:ss");
                            mAcceptLastTime = date2TimeStamp(remainAcceptTime, "yyyy-MM-dd HH:mm:ss");
                            remainingTime = mAcceptTime - mNowTime;

                            if (remainingTime > 0) {
                                countTimeBorder.setVisibility(View.GONE);
                                acceptTip.setBackgroundColor(getResources().getColor(R.color.huang));
                                acceptBorder.setVisibility(View.VISIBLE);

                                inputBorder.setVisibility(View.GONE);

                                accept.setVisibility(View.GONE);
                                reject.setVisibility(View.VISIBLE);
                                mTimer = new CountDownTimer(remainingTime, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        String time = TimeUtil.formatTime(millisUntilFinished);
                                        acceptValidTime.setText("距离预约时间还剩余" + time);
                                    }

                                    @Override
                                    public void onFinish() {

                                        accept.setVisibility(View.VISIBLE);
                                        reject.setVisibility(View.VISIBLE);
                                        mTimer = new CountDownTimer(mAcceptLastTime - mAcceptTime, 1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {
                                                String time = TimeUtil.formatTime(millisUntilFinished);
                                                acceptValidTime.setText("接诊时间剩余：" + time);

                                            }

                                            @Override
                                            public void onFinish() {
                                                if (mTimer != null) {
                                                    mTimer.cancel();
                                                }
                                                acceptValidTime.setText("接诊剩余时间：00:00");
                                            }
                                        }.start();
                                    }
                                }.start();
                            } else {
                                acceptBorder.setVisibility(View.VISIBLE);

                                inputBorder.setVisibility(View.GONE);

                                accept.setVisibility(View.VISIBLE);
                                reject.setVisibility(View.VISIBLE);
                                mTimer = new CountDownTimer(mAcceptLastTime - mNowTime, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        String time = TimeUtil.formatTime(millisUntilFinished);
                                        acceptValidTime.setText("接诊时间剩余：" + time);

                                    }

                                    @Override
                                    public void onFinish() {
                                        if (mTimer != null) {
                                            mTimer.cancel();
                                        }
                                        acceptValidTime.setText("接诊剩余时间：00:00");
                                    }
                                }.start();

                            }
                            status.setText("待问诊");
                            cancallVideo = false;
                            acceptTime.setText("预约时间： " + date + " " + startTime + "-" + endTime);
                        } else if (type == 1) {
                            String startTime = mAllRecordForDoctorEntity.getStartTime();
                            mAcceptTime = date2TimeStamp(startTime, "yyyy-MM-dd HH:mm:ss");
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));
                            status.setText("接诊中");
                            inputBorder.setVisibility(View.VISIBLE);
                            acceptLayout.setVisibility(View.GONE);
                            cancallVideo = true;
                            mTimer = new CountDownTimer(VALID_TIME - mNowTime + mAcceptTime, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    String time = TimeUtil.formatTime(millisUntilFinished);
                                    acceptTime.setText("问诊时间还剩：" + time);
                                    Log.d("chat", "定时器2" + time);
                                }

                                @Override
                                public void onFinish() {
                                    // updateInquiryRecordStatus(2, SpUtils.getInt(Contants.Id, 0), 5, mInquiryRecordListDataEntity.getId());
                                    status.setText("已结束");
                                    inputBorder.setVisibility(View.VISIBLE);

                                    acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                                    acceptTime.setVisibility(View.GONE);
                                    if (mTimer != null) {
                                        mTimer.cancel();
                                    }
                                    stopWenzhen();
                                    if (mTimer != null) {
                                        mTimer.cancel();
                                    }
                                }
                            }.start();
                            //  acceptTime.setText(date + " " + startTime + "-" + endTime);
                        } else if (type == 2 || type == 4 || type == 5) {
                            // mAcceptTime = date2TimeStamp(startTime, "yyyy-MM-dd HH:mm:ss");
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                            status.setText("已结束");
                            videoInterrogationLayout.setVisibility(View.GONE);
                            inputBorder.setVisibility(View.VISIBLE);
                            cancallVideo = false;
                            acceptLayout.setVisibility(View.GONE);
                        }
                    }

                } else if (mAction != null && mAction.equals("videoInterrogationfromhhome")) {
                    mServiceCode = "1102";
                    mInquiryRecordListDataEntity = (InquiryRecordListDataEntity) getIntent().getSerializableExtra("inquiryRecordListDataEntity");
                    if (mInquiryRecordListDataEntity != null) {
                        mImagePath = mInquiryRecordListDataEntity.getApplicantHeadImgUrl();
                        SpUtils.put(Contants.patientName, mInquiryRecordListDataEntity.getPatientName());
                        SpUtils.put(Contants.patientID, mInquiryRecordListDataEntity.getPatientId());
                        int type = mInquiryRecordListDataEntity.getStatus();

                        if (type == 0) {
                            acceptLayout.setVisibility(View.VISIBLE);

                            inputBorder.setVisibility(View.GONE);
                            cancallVideo = false;
                            acceptTip.setVisibility(View.VISIBLE);
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.huang));
                            int end = mInquiryRecordListDataEntity.getReExaminationDate().indexOf(" ");
                            String date = "";
                            if (end == -1) {
                                date = mInquiryRecordListDataEntity.getReExaminationDate();

                            } else {
                                date = mInquiryRecordListDataEntity.getReExaminationDate().substring(0, end);

                            }
                            String startTime = mInquiryRecordListDataEntity.getReExaminationStartTime();
                            String endTime = mInquiryRecordListDataEntity.getReExaminationEndTime();
                            String dateTime = date + " " + startTime;
                            final String remainAcceptTime = date + " " + endTime;
                            mAcceptTime = date2TimeStamp(dateTime, "yyyy-MM-dd HH:mm:ss");
                            mAcceptLastTime = date2TimeStamp(remainAcceptTime, "yyyy-MM-dd HH:mm:ss");
                            remainingTime = mAcceptTime - mNowTime;

                            if (remainingTime > 0) {
                                countTimeBorder.setVisibility(View.GONE);
                                acceptTip.setBackgroundColor(getResources().getColor(R.color.huang));
                                acceptBorder.setVisibility(View.VISIBLE);

                                inputBorder.setVisibility(View.GONE);

                                accept.setVisibility(View.GONE);
                                reject.setVisibility(View.VISIBLE);
                                mTimer = new CountDownTimer(remainingTime, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        String time = TimeUtil.formatTime(millisUntilFinished);
                                        acceptValidTime.setText("距离预约时间还剩余" + time);
                                    }

                                    @Override
                                    public void onFinish() {

                                        accept.setVisibility(View.VISIBLE);
                                        reject.setVisibility(View.VISIBLE);
                                        mTimer = new CountDownTimer(mAcceptLastTime - mAcceptTime, 1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {
                                                String time = TimeUtil.formatTime(millisUntilFinished);
                                                acceptValidTime.setText("接诊时间剩余：" + time);

                                            }

                                            @Override
                                            public void onFinish() {
                                                if (mTimer != null) {
                                                    mTimer.cancel();
                                                }
                                                acceptValidTime.setText("接诊剩余时间：00:00");

                                            }
                                        }.start();
                                    }
                                }.start();
                            } else {
                                acceptBorder.setVisibility(View.VISIBLE);

                                inputBorder.setVisibility(View.GONE);

                                accept.setVisibility(View.VISIBLE);
                                reject.setVisibility(View.VISIBLE);
                                mTimer = new CountDownTimer(mAcceptLastTime - mNowTime, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        String time = TimeUtil.formatTime(millisUntilFinished);
                                        acceptValidTime.setText("接诊时间剩余：" + time);

                                    }

                                    @Override
                                    public void onFinish() {
                                        if (mTimer != null) {
                                            mTimer.cancel();
                                        }
                                        acceptValidTime.setText("接诊剩余时间：00:00");
                                    }
                                }.start();

                            }
                            status.setText("待问诊");
                            cancallVideo = false;
                            acceptTime.setText("预约时间： " + date + " " + startTime + "-" + endTime);
                        } else if (type == 1) {
                            String startTime = mInquiryRecordListDataEntity.getStartTime();
                            mAcceptTime = date2TimeStamp(startTime, "yyyy-MM-dd HH:mm:ss");
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));
                            status.setText("接诊中");
                            inputBorder.setVisibility(View.VISIBLE);
                            acceptLayout.setVisibility(View.GONE);
                            cancallVideo = true;
                            mTimer = new CountDownTimer(VALID_TIME - mNowTime + mAcceptTime, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    String time = TimeUtil.formatTime(millisUntilFinished);
                                    acceptTime.setText("问诊时间还剩：" + time);
                                    Log.d("chat", "定时器2" + time);
                                }

                                @Override
                                public void onFinish() {
                                    // updateInquiryRecordStatus(2, SpUtils.getInt(Contants.Id, 0), 5, mInquiryRecordListDataEntity.getId());
                                    status.setText("已结束");

                                    inputBorder.setVisibility(View.VISIBLE);

                                    acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                                    acceptTime.setVisibility(View.GONE);
                                    if (mTimer != null) {
                                        mTimer.cancel();
                                    }
                                    stopWenzhen();
                                    if (mTimer != null) {
                                        mTimer.cancel();
                                    }
                                }
                            }.start();
                            //  acceptTime.setText(date + " " + startTime + "-" + endTime);
                        } else if (type == 2 || type == 4 || type == 5) {
                            // mAcceptTime = date2TimeStamp(startTime, "yyyy-MM-dd HH:mm:ss");
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                            status.setText("已结束");
                            videoInterrogationLayout.setVisibility(View.GONE);
                            cancallVideo = false;
                            inputBorder.setVisibility(View.VISIBLE);

                            acceptLayout.setVisibility(View.GONE);
                        }
                    }
                } else if (mAction != null && mAction.equals("onlineRenewal")) {
                    mServiceCode = "1103";
                    mAllRecordForDoctorEntity = (AllRecordForDoctorEntity) getIntent().getSerializableExtra("onlineRenewalDataEntity");
                    if (mAllRecordForDoctorEntity != null) {
                        mImagePath = mAllRecordForDoctorEntity.getApplicantHeadImgUrl();
                        SpUtils.put(Contants.patientName, mAllRecordForDoctorEntity.getPatientName());
                        SpUtils.put(Contants.patientID, mAllRecordForDoctorEntity.getPatientId());
                        //   acceptValidTime.setVisibility(View.GONE);
                        int type = mAllRecordForDoctorEntity.getStatus();
                        if (type == 0) {
                            status.setText("未接诊");
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.huang));
                            acceptLayout.setVisibility(View.VISIBLE);
                            acceptBorder.setVisibility(View.VISIBLE);

                            inputBorder.setVisibility(View.GONE);


                            validTime = mAllRecordForDoctorEntity.getPayTime();
                            mAcceptTime = date2TimeStamp(validTime, "yyyy-MM-dd HH:mm:ss");
                            //remainingTime = VALID_TIME - mNowTime;
                            mTimer = new CountDownTimer(VALID_TIME - mNowTime + mAcceptTime, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    String time = TimeUtil.formatTime(millisUntilFinished);
                                    acceptValidTime.setText("接诊剩余时间：" + time);

                                }

                                @Override
                                public void onFinish() {
                                    // update(2, SpUtils.getInt(Contants.Id, 0), 5, mInquiryRecordListDataEntity.getId());
                                    acceptValidTime.setText("接诊剩余时间：00:00");
                                }
                            }.start();

                        } else if (type == 1) {
                            inputBorder.setVisibility(View.VISIBLE);
                            acceptLayout.setVisibility(View.GONE);
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));

                            status.setText("接诊中");
                            try {
                                validTime = mAllRecordForDoctorEntity.getStartTime();
                                mAcceptTime = date2TimeStamp(validTime, "yyyy-MM-dd HH:mm:ss");
                                mTimer = new CountDownTimer(VALID_TIME - mNowTime + mAcceptTime, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {


                                        String time = TimeUtil.formatTime(millisUntilFinished);
                                        Log.d("chat", "定时器1" + time);
                                        acceptTime.setText("问诊时间还剩" + time);

                                    }

                                    @Override
                                    public void onFinish() {
                                        status.setText("已结束");

                                        inputBorder.setVisibility(View.VISIBLE);

                                        acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                                        acceptTime.setVisibility(View.GONE);
                                        if (mTimer != null) {
                                            mTimer.cancel();
                                        }
                                        stopWenzhen();
                                    }
                                }.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                            status.setText("已结束");

                            inputBorder.setVisibility(View.VISIBLE);

                            acceptLayout.setVisibility(View.GONE);
                        }
                    }
                } else if (mAction != null && mAction.equals("onlineRenewalfromHome")) {

                    mServiceCode = "1103";
                    mOnlineRenewalDataEntity = (OnlineRenewalDataEntity) getIntent().getSerializableExtra("onlineRenewalDataEntity");
                    if (mOnlineRenewalDataEntity != null) {
                        mImagePath = mOnlineRenewalDataEntity.getApplicantHeadImgUrl();

                        SpUtils.put(Contants.patientName, mOnlineRenewalDataEntity.getPatientName());
                        SpUtils.put(Contants.patientID, mOnlineRenewalDataEntity.getPatientId());
                        //   acceptValidTime.setVisibility(View.GONE);
                        int type = mOnlineRenewalDataEntity.getStatus();
                        if (type == 0) {
                            status.setText("未接诊");
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.huang));
                            acceptLayout.setVisibility(View.VISIBLE);
                            acceptBorder.setVisibility(View.VISIBLE);
                            inputBorder.setVisibility(View.GONE);
                            validTime = mOnlineRenewalDataEntity.getPayTime();
                            mAcceptTime = date2TimeStamp(validTime, "yyyy-MM-dd HH:mm:ss");
                            //remainingTime = VALID_TIME - mNowTime;
                            mTimer = new CountDownTimer(VALID_TIME - mNowTime + mAcceptTime, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    String time = TimeUtil.formatTime(millisUntilFinished);
                                    acceptValidTime.setText("接诊剩余时间：" + time);

                                }

                                @Override
                                public void onFinish() {
                                    // update(2, SpUtils.getInt(Contants.Id, 0), 5, mInquiryRecordListDataEntity.getId());
                                    acceptValidTime.setText("接诊剩余时间：00:00");

                                }
                            }.start();

                        } else if (type == 1) {
                            inputBorder.setVisibility(View.VISIBLE);
                            acceptLayout.setVisibility(View.GONE);
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));

                            status.setText("接诊中");
                            try {
                                validTime = mOnlineRenewalDataEntity.getStartTime();
                                mAcceptTime = date2TimeStamp(validTime, "yyyy-MM-dd HH:mm:ss");
                                mTimer = new CountDownTimer(VALID_TIME - mNowTime + mAcceptTime, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {


                                        String time = TimeUtil.formatTime(millisUntilFinished);
                                        Log.d("chat", "定时器1" + time);
                                        acceptTime.setText("问诊时间还剩" + time);

                                    }

                                    @Override
                                    public void onFinish() {
                                        status.setText("已结束");

                                        inputBorder.setVisibility(View.VISIBLE);

                                        acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                                        acceptTime.setVisibility(View.GONE);
                                        if (mTimer != null) {
                                            mTimer.cancel();
                                        }
                                        stopWenzhen();
                                    }
                                }.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                            status.setText("已结束");

                            inputBorder.setVisibility(View.VISIBLE);

                            acceptLayout.setVisibility(View.GONE);
                        }
                    }


                } else if (mAction != null && mAction.equals("pictureConsult")) {
                    mServiceCode = "1101";
                    mAllRecordForDoctorEntity = (AllRecordForDoctorEntity) getIntent().getSerializableExtra("consultMessageEntity");

                    Log.d("pictureConsult", "倒计时开始时间" + mAllRecordForDoctorEntity.toString());
                    if (mAllRecordForDoctorEntity != null) {
                        SpUtils.put(Contants.patientName, mAllRecordForDoctorEntity.getPatientName());
                        SpUtils.put(Contants.patientID, mAllRecordForDoctorEntity.getPatientId());
                        mImagePath = mAllRecordForDoctorEntity.getApplicantHeadImgUrl();
                        // acceptValidTime.setVisibility(View.GONE);
                        int type = mAllRecordForDoctorEntity.getStatus();
                        if (type == 0) {
                            status.setText("待接诊");
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.huang));
                            acceptLayout.setVisibility(View.VISIBLE);
                            acceptBorder.setVisibility(View.VISIBLE);

                            inputBorder.setVisibility(View.GONE);

                            validTime = mAllRecordForDoctorEntity.getPayTime();

                            mAcceptTime = date2TimeStamp(validTime, "yyyy-MM-dd HH:mm:ss");

                            Log.d("pictureConsult", "倒计时开始时间" + (VALID_TIME - mNowTime + mAcceptTime) + "");
                            Long comedowntime = VALID_TIME - mNowTime + mAcceptTime;

                            mTimer = new CountDownTimer(comedowntime, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    String time = TimeUtil.formatTime(millisUntilFinished);
                                    acceptValidTime.setText("接诊剩余时间：" + time);

                                }

                                @Override
                                public void onFinish() {
                                    if (mTimer != null) {
                                        mTimer.cancel();
                                    }
                                    acceptValidTime.setText("接诊剩余时间：00:00");
                                }
                            }.start();
                        } else if (type == 1) {
                            inputBorder.setVisibility(View.VISIBLE);
                            acceptLayout.setVisibility(View.GONE);

                            status.setText("接诊中");
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));
                            validTime = mAllRecordForDoctorEntity.getStartTime();
                            try {
                                mAcceptTime = date2TimeStamp(validTime, "yyyy-MM-dd HH:mm:ss");
                                if (mAcceptTime <= 0) {
                                    return;
                                }
                                mTimer = new CountDownTimer(VALID_TIME - mNowTime + mAcceptTime, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        String time = TimeUtil.formatTime(millisUntilFinished);
                                        acceptTime.setText("问诊时间还剩" + time);
                                        Log.d("chat", "定时器6" + time);
                                    }

                                    @Override
                                    public void onFinish() {
                                        Log.d("chat", "定时器7" + "已结束");
                                        // update(2, SpUtils.getInt(Contants.Id, 0), 5, mInquiryRecordListDataEntity.getId());
                                        status.setText("已结束");

                                        inputBorder.setVisibility(View.VISIBLE);

                                        acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                                        acceptTime.setVisibility(View.GONE);
                                        if (mTimer != null) {
                                            mTimer.cancel();
                                        }
                                        stopWenzhen();

                                    }
                                }.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } else {
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                            status.setText("已结束");

                            inputBorder.setVisibility(View.VISIBLE);

                            acceptLayout.setVisibility(View.GONE);
                        }
                    }
                } else if (mAction != null && mAction.equals("pictureConsultfromhome")) {

                    mServiceCode = "1101";
                    mConsultMessageEntity = (ConsultMessageEntity) getIntent().getSerializableExtra("consultMessageEntity");
                    if (mConsultMessageEntity != null) {
                        mImagePath = mConsultMessageEntity.getApplicantHeadImgUrl();
                        SpUtils.put(Contants.patientName, mConsultMessageEntity.getPatientName());
                        SpUtils.put(Contants.patientID, mConsultMessageEntity.getPatientId());
                        // acceptValidTime.setVisibility(View.GONE);
                        int type = mConsultMessageEntity.getStatus();
                        if (type == 0) {
                            status.setText("待接诊");
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.huang));
                            acceptLayout.setVisibility(View.VISIBLE);
                            acceptBorder.setVisibility(View.VISIBLE);

                            inputBorder.setVisibility(View.GONE);

                            validTime = mConsultMessageEntity.getPayTime();

                            mAcceptTime = date2TimeStamp(validTime, "yyyy-MM-dd HH:mm:ss");
                            mTimer = new CountDownTimer(VALID_TIME - mNowTime + mAcceptTime, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    String time = TimeUtil.formatTime(millisUntilFinished);
                                    acceptValidTime.setText("接诊剩余时间：" + time);

                                }

                                @Override
                                public void onFinish() {
                                    status.setText("已结束");

                                    inputBorder.setVisibility(View.VISIBLE);

                                    acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                                    acceptTime.setVisibility(View.GONE);
                                    if (mTimer != null) {
                                        mTimer.cancel();
                                    }
                                    acceptLayout.setVisibility(View.GONE);
                                    acceptBorder.setVisibility(View.GONE);
                                    stopWenzhen();
                                }
                            }.start();
                        } else if (type == 1) {
                            inputBorder.setVisibility(View.VISIBLE);
                            acceptLayout.setVisibility(View.GONE);

                            status.setText("接诊中");
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));
                            validTime = mConsultMessageEntity.getStartTime();
                            try {
                                mAcceptTime = date2TimeStamp(validTime, "yyyy-MM-dd HH:mm:ss");
                                if (mAcceptTime <= 0) {
                                    return;
                                }
                                mTimer = new CountDownTimer(VALID_TIME - mNowTime + mAcceptTime, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        String time = TimeUtil.formatTime(millisUntilFinished);
                                        acceptTime.setText("问诊时间还剩" + time);
                                        Log.d("chat", "定时器6" + time);
                                    }

                                    @Override
                                    public void onFinish() {
                                        // update(2, SpUtils.getInt(Contants.Id, 0), 5, mInquiryRecordListDataEntity.getId());
                                        status.setText("已结束");

                                        inputBorder.setVisibility(View.VISIBLE);

                                        acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                                        acceptTime.setVisibility(View.GONE);
                                        if (mTimer != null) {
                                            mTimer.cancel();
                                        }
                                        acceptLayout.setVisibility(View.GONE);
                                        stopWenzhen();

                                    }
                                }.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } else {
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                            status.setText("已结束");

                            inputBorder.setVisibility(View.VISIBLE);

                            acceptLayout.setVisibility(View.GONE);
                        }
                    }


                } else if (mAction != null && mAction.equals("MDT")) {
                    UpdateOrderStatus();


                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {

            }
        });
    }

    private void UpdateOrderStatus() {
        if (mMDTDetailEntity == null) {
            mMDTDetailEntity = (MDTDetailEntity) getIntent().getSerializableExtra("MDTDetailEntity");
        }
        //开中药与开西药处方隐藏掉了


        if (mMDTDetailEntity != null) {
            //   toast("当前状态"+mMDTDetailEntity.getSubjectStatus());
            //更新诊疗类型(1-咨询，2-分诊，3-会诊)
            if (mMDTDetailEntity.getSubjectType() == 1) {
                receptionFunctions.setVisibility(View.GONE);
                prescriptionLayout.setVisibility(View.GONE);//开处方显示
                zhongyaoPrescriptionLayout.setVisibility(View.GONE);//中药开处方显示
                medicalServiceLayout.setVisibility(View.VISIBLE);//推荐服务
                frCheckTable.setVisibility(View.VISIBLE);//推荐检查

                medicalAdviceDoctorLayout.setVisibility(View.GONE);//推荐会诊医生
                medicalAdviceReportLayout.setVisibility(View.GONE);//出具诊疗报告

                medicalAdviceSuggestLayout.setVisibility(View.GONE);//出具诊疗建议
                medicalEndSuggestLayout.setVisibility(View.GONE);//填写结论性建议


                empty1.setVisibility(View.GONE);
                empty2.setVisibility(View.GONE);
                empty3.setVisibility(View.GONE);



            } else if (mMDTDetailEntity.getSubjectType() == 2) {
                prescriptionLayout.setVisibility(View.GONE);//开处方显示
                zhongyaoPrescriptionLayout.setVisibility(View.GONE);//中药开处方显示
                medicalServiceLayout.setVisibility(View.GONE);//推荐服务
                frCheckTable.setVisibility(View.GONE);//推荐检查

                medicalAdviceDoctorLayout.setVisibility(View.VISIBLE);//推荐会诊医生
                medicalAdviceReportLayout.setVisibility(View.VISIBLE);//出具诊疗报告


                //如果本人是分诊医生，那么需要显示推荐会诊医生 出具诊疗报告，否则不显示
                if (mMDTDetailEntity == null || MyApplication.mDoctorInfoByIdEntity == null) {
                    return;
                }
                if (mMDTDetailEntity.getDrId() == MyApplication.mDoctorInfoByIdEntity.getDrId()) {
                    medicalAdviceSuggestLayout.setVisibility(View.GONE);//出具诊疗建议
                    medicalEndSuggestLayout.setVisibility(View.GONE);//填写结论性建议
                } else {
                    receptionFunctions.setVisibility(View.GONE);
                }

                empty1.setVisibility(View.GONE);
                empty2.setVisibility(View.GONE);
                empty3.setVisibility(View.GONE);

                //没有报告
                if (TextUtils.isEmpty(mMDTDetailEntity.getSubjectReportPath())){
                    if (mMDTDetailEntity.getIsNext()==0){

                        medicalAdviceReport.setText("填写专家意见");
                        medicalAdviceReport.setTextColor(getResources().getColor(R.color.greenyelloow));
                        medicalAdviceReportLayout.setClickable(true);
                        medicalAdviceReport.setClickable(true);
                    }else {
                        //都要变灰
                        medicalAdviceReport.setText("填写专家意见");
                        medicalAdviceReport.setTextColor(getResources().getColor(R.color.greenyelloow));
                        medicalAdviceReportLayout.setClickable(true);
                        medicalAdviceReport.setClickable(true);

                        //都要变灰
                        medicalAdviceDoctor.setTextColor(getResources().getColor(R.color.greenyelloow));
                        medicalAdviceDoctor.setClickable(true);
                        medicalAdviceDoctorLayout.setClickable(true);

                    }

                //有报告  就显示查看报告
                }else {
                    medicalAdviceReport.setText("查看专家意见");
                    medicalAdviceReportLayout.setClickable(true);
                    medicalAdviceReport.setClickable(true);

                }


            } else if (mMDTDetailEntity.getSubjectType() == 3) {
                prescriptionLayout.setVisibility(View.GONE);//开处方显示
                zhongyaoPrescriptionLayout.setVisibility(View.GONE);//中药开处方显示
                medicalServiceLayout.setVisibility(View.GONE);//推荐服务
                frCheckTable.setVisibility(View.GONE);//推荐检查
                medicalAdviceDoctorLayout.setVisibility(View.GONE);//推荐会诊医生
                medicalAdviceReportLayout.setVisibility(View.GONE);//出具诊疗报告


                //如果本人是分诊医生，那么需要显示推荐会诊医生 出具诊疗建议，填写结论性建议
//                if (mMDTDetailEntity.getDrId()==MyApplication.mDoctorInfoByIdEntity.getDrId()){
//
//                    receptionFunctions.setVisibility(View.GONE);
//                }else {
//                    medicalAdviceSuggestLayout.setVisibility(View.VISIBLE);//出具诊疗建议
//                    medicalEndSuggestLayout.setVisibility(View.VISIBLE);//填写结论性建议
//                }

                acceptTip.setVisibility(View.GONE);
                empty1.setVisibility(View.GONE);
                empty2.setVisibility(View.GONE);
                empty3.setVisibility(View.GONE);
                //基层医生  不显示
                if (mMDTDetailEntity.getMajorDrId() == MyApplication.mDoctorInfoByIdEntity.getDrId()) {
                    receptionFunctions.setVisibility(View.VISIBLE);
                    medicalAdviceSuggestLayout.setVisibility(View.VISIBLE);//出具诊疗建议
                    medicalEndSuggestLayout.setVisibility(View.VISIBLE);//填写结论性建议
                    //如果自己是分诊医生
                } else if (mMDTDetailEntity.getConsultDrId() == MyApplication.mDoctorInfoByIdEntity.getDrId()) {
                    //如果自己是咨询医生
                    receptionFunctions.setVisibility(View.GONE);
                } else { //其他的只能填写诊疗建议
                    receptionFunctions.setVisibility(View.VISIBLE);
                    medicalAdviceSuggestLayout.setVisibility(View.VISIBLE);//出具诊疗建议
                    medicalEndSuggestLayout.setVisibility(View.INVISIBLE);//填写结论性建议
                }


            }

            int type = mMDTDetailEntity.getSubjectStatus();
            if (type == 0) {
                status.setText("待接诊");

                //如果在会诊窗口，只有主治医生才能接诊
                if (mMDTDetailEntity.getSubjectType() == 2) {
                    if (mMDTDetailEntity.getMajorDrId() == MyApplication.mDoctorInfoByIdEntity.getDrId()) {
                        acceptLayout.setVisibility(View.VISIBLE);
                        acceptBorder.setVisibility(View.VISIBLE);
                        inputBorder.setVisibility(View.GONE);

                    } else {
                        acceptLayout.setVisibility(View.GONE);
                        acceptBorder.setVisibility(View.GONE);
                        inputBorder.setVisibility(View.VISIBLE);
                    }
                } else {
                    acceptLayout.setVisibility(View.VISIBLE);
                    acceptBorder.setVisibility(View.VISIBLE);
                    inputBorder.setVisibility(View.GONE);
                }

                acceptValidTime.setVisibility(View.GONE);
                acceptTip.setBackgroundColor(getResources().getColor(R.color.huang));

                validTime = mMDTDetailEntity.getPayTime();

                mAcceptTime = date2TimeStamp(validTime, "yyyy-MM-dd HH:mm:ss");
//                mTimer = new CountDownTimer(VALID_TIME_HALF - mNowTime + mAcceptTime, 1000) {
//                    @Override
//                    public void onTick(long millisUntilFinished) {
//                        String time = TimeUtil.formatTime(millisUntilFinished);
//                        acceptValidTime.setText("接诊剩余时间：" + time);
//
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        status.setText("已结束");
//
//                        inputBorder.setVisibility(View.VISIBLE);
//
//                        acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
//                        acceptTime.setVisibility(View.GONE);
//                        if (mTimer != null) {
//                            mTimer.cancel();
//                        }
//                        acceptLayout.setVisibility(View.GONE);
//                        acceptBorder.setVisibility(View.GONE);
//                        stopWenzhen();
//                    }
//                }.start();


            } else if (type == 1) {
                inputBorder.setVisibility(View.VISIBLE);
                acceptLayout.setVisibility(View.GONE);

                status.setText("接诊中");
                acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));

            } else if (type == 2) {
                acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                status.setText("已拒诊");

                inputBorder.setVisibility(View.VISIBLE);

                acceptLayout.setVisibility(View.GONE);
            } else if (type == 3) {
                inputBorder.setVisibility(View.VISIBLE);
                acceptLayout.setVisibility(View.GONE);

                status.setText("未接诊");
                acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));

            } else if (type == 4) {
                inputBorder.setVisibility(View.VISIBLE);
                acceptLayout.setVisibility(View.GONE);
                status.setText("已出报告");
                acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));
                validTime = mMDTDetailEntity.getIssueTime();//从出报告时间开始算起
                acceptTime.setVisibility(View.VISIBLE);
                mAcceptTime = date2TimeStamp(validTime, "yyyy-MM-dd HH:mm:ss");
                try {
                    mTimer = new CountDownTimer(VALID_TIME - mNowTime + mAcceptTime, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {


                            String time = TimeUtil.formatTime(millisUntilFinished);
                            Log.d("chat", "定时器1" + time);
                            acceptTime.setText("问诊时间还剩" + time);

                        }

                        @Override
                        public void onFinish() {
                            status.setText("已结束");

                            inputBorder.setVisibility(View.VISIBLE);

                            acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                            acceptTime.setVisibility(View.GONE);
                            if (mTimer != null) {
                                mTimer.cancel();
                            }

                        }
                    }.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {
                acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                status.setText("已结束");

                inputBorder.setVisibility(View.VISIBLE);

                acceptLayout.setVisibility(View.GONE);
            }

        }
    }


    @Override
    protected void initListener() {
        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {

                getMessage(mTIMMessage);
            }

            @Override
            public void onRefreshing() {
                if (mTimMessages.size() > 0) {
                    mTIMMessage = mTimMessages.get(0).getTimMessage();
                }
                getMessage(mTIMMessage);
            }
        });
        accept.setOnClickListener(this);
        reject.setOnClickListener(this);

        // interrogationTable.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        videoInterrogation.setOnClickListener(this);
        // btnPhoto.setOnClickListener(this);
        btnVoice.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        btnKeyboard.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        //btnImage.setOnClickListener(this);
        prescription.setOnClickListener(this);
        medicalService.setOnClickListener(this);
        // theQuickReply.setOnClickListener(this);
        // addYourResume.setOnClickListener(this);
        // plusSign.setOnClickListener(this);
        checkTable.setOnClickListener(this);
        zhongyaoPrescriptionLayout.setOnClickListener(this);
        medicalAdviceDoctor.setOnClickListener(this);
        medicalAdviceReport.setOnClickListener(this);

        medicalAdviceSuggest.setOnClickListener(this);//出具诊疗建议
        medicalEndSuggest.setOnClickListener(this);//填写结论性建议

    }

    @Override
    protected void initData() {
        if (BuildConfig.DEBUG) {
            BJCASDK.getInstance().setServerUrl(EnvType.INTEGRATE);
        } else {
            BJCASDK.getInstance().setServerUrl(EnvType.PUBLIC);
        }
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {

            case R.id.video_interrogation:
                if (NetworkUtil.isNetworkAvailable(this)) {
                    // makeCall(ILVCallConstants.CALL_TYPE_VIDEO, mIdentifier);
                    int callId = ILiveFunc.generateAVCallRoomID();
                    CustomMessageEntity customMessageEntity = new CustomMessageEntity();
                    //   customMessageEntity.setTitle(SpUtils.getString(Contants.Name)+"医生提醒您填写一下病历，这对于您的病情很有帮助");
                    customMessageEntity.setIsSystem(false);
                    customMessageEntity.setContent(null);
                    CustomMessageEntity.ExtDataBean extData = new CustomMessageEntity.ExtDataBean();
                    CustomMessageEntity.ExtDataBean.DataBean data = new CustomMessageEntity.ExtDataBean.DataBean();
                    extData.setType(129);
                    data.setAVRoomID(callId);
                    data.setId(SpUtils.getString(Contants.identifier));
                    data.setTargets(SpUtils.getString(Contants.Name));
                    extData.setData(data);
                    customMessageEntity.setExtData(extData);
                    String json = new Gson().toJson(customMessageEntity);
                    sendCustomMessage(json, "");
                    sendPushVideo(callId);
                    mTimer = new CountDownTimer(30000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            sendVideoMessage(132, -1);
                            EventBus.getDefault().post(new RejectCallTip(true));
                        }
                    }.start();
                    Intent roomIntent = new Intent(GroupChatActivity.this, RoomActivity.class);
                    roomIntent.putExtra("callId", callId);
                    roomIntent.putExtra("identifier", mIdentifier);
                    roomIntent.putExtra("patientiinfo", mAllRecordForDoctorEntity);

                    startActivity(roomIntent);
                } else {
                    toast("网络连接异常，请检查网络连接");
                }

                break;
            case R.id.btn_send:
                String message = input.getText().toString();
                if (!TextUtils.isEmpty(message)) {
                    sendTextMessage(message);
                    input.setText("");
                    btnAdd.setVisibility(View.VISIBLE);
                    btnSend.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_voice:
                hideInput();
                btnVoice.setVisibility(View.GONE);
                textPanel.setVisibility(View.GONE);
                btnKeyboard.setVisibility(View.VISIBLE);
                morePanel.setVisibility(View.GONE);
                voicePanel.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_keyboard:
                btnVoice.setVisibility(View.VISIBLE);
                textPanel.setVisibility(View.VISIBLE);
                showInput();
                btnKeyboard.setVisibility(View.GONE);
                voicePanel.setVisibility(View.GONE);
                break;
            case R.id.btn_add:
                if (morePanel.getVisibility() == View.GONE) {
                    hideInput();

                    morePanel.setVisibility(View.VISIBLE);
                } else {
                    if (textPanel.getVisibility() == View.VISIBLE) {
                        showInput();
                    }
                    morePanel.setVisibility(View.GONE);
                }
                break;
//            case R.id.btn_photo:
//                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                String f = System.currentTimeMillis() + ".jpg";
//                mPicturePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + f;
//                File file = new File(mPicturePath);
//                file.getParentFile().mkdirs();
//                Log.i("zdp", mPicturePath);
//                Uri uri;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    uri = FileProvider.getUriForFile(mContext, "com.newdjk.doctor.file.provider", file);
//                } else {
//                    uri = Uri.fromFile(file);
//                }
//                //添加权限
//                openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                startActivityForResult(openCameraIntent, REQUEST_CODE_TAKE_PICTURE);
//                break;
//            case R.id.btn_image:
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, IMAGE_REQUEST_CODE);
//                break;
            case R.id.prescription:
                mLoginStatus = SpUtils.getInt(Contants.Status, 0);
                if (mLoginStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, GroupChatActivity.this);
                } else {

                    if (mIsHasOpenPres) {
                        SharedPreferences sp = getSharedPreferences("yunyisheng",
                                Context.MODE_PRIVATE);
                        final int isIgnore = sp.getInt(Contants.IsIgnore, 0);
                        boolean isExists = BJCASDK.getInstance().existsCert(GroupChatActivity.this);

                        boolean ExistStamp = BJCASDK.getInstance().existsStamp(GroupChatActivity.this);
                        /// BJCASDK.getInstance().clearCert(ChatActivity.this);
                        // BJCASDK.getInstance().startUrl(ChatActivity.this, Contants.clientId, 3);
                        // BJCASDK.getInstance().startUrl(ChatActivity.this, Contants.clientId, 1);
                        if (!isExists) {
                            mDialog = new DownloadCertDialog(GroupChatActivity.this);
                            mDialog.show("温馨提示", "", new DownloadCertDialog.DialogListener() {
                                @Override
                                public void confirm() {
                                    Log.i("ChatActivity", "aaa");
                                    BJCASDK.getInstance().certDown(GroupChatActivity.this, Contants.clientId, SpUtils.getString(Contants.userName), new YWXListener() {
                                        @Override
                                        public void callback(String s) {
                                            Log.d("chat", "callback" + s);
                                            YWXListenerEntity yWXListenerEntity = mGson.fromJson(s, YWXListenerEntity.class);
                                            String status = yWXListenerEntity.getStatus();
                                            String message = yWXListenerEntity.getMessage();

                                            BJCASDK.getInstance().getUserInfo(GroupChatActivity.this, Contants.clientId, new YWXListener() {
                                                @Override
                                                public void callback(String s) {
                                                    Log.d("chat", "证书" + s);
                                                }
                                            });

                                            boolean ExistStamp2 = BJCASDK.getInstance().existsStamp(GroupChatActivity.this);
                                            Log.d("chat", "证书" + ExistStamp2);

                                            if (status != null && status.equals("0")) {
                                                boolean ExistStamp1 = BJCASDK.getInstance().existsStamp(GroupChatActivity.this);
                                                if (!ExistStamp1) {

                                                    BJCASDK.getInstance().drawStamp(mActivity, Contants.clientId, new YWXListener() {
                                                        @Override
                                                        public void callback(String msg) {
                                                            YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                                            String status = yWXListenerEntity.getStatus();
                                                            String message = yWXListenerEntity.getMessage();
                                                            if (status != null && status.equals("0")) {
                                                                if (isIgnore == 0) {
                                                                    Intent ChickUnitIntent = new Intent(GroupChatActivity.this, ChickUnitActivity.class);
                                                                    ChickUnitIntent.putExtra("prescriptionMessage", mPrescriptionMessage);
                                                                    ChickUnitIntent.putExtra("type", "prescription");
                                                                    startActivity(ChickUnitIntent);
                                                                } else {
                                                                    Intent prescriptionIntent = new Intent(GroupChatActivity.this, PrescriptionActivity.class);
                                                                    prescriptionIntent.putExtra("prescriptionMessage", mPrescriptionMessage);
                                                                    startActivity(prescriptionIntent);
                                                                }
                                                            } else {
                                                                //   toast(message);
                                                                // ToastUtil.setToast("设置签章失败，请重试！"+(status));
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    if (isIgnore == 0) {
                                                        Intent ChickUnitIntent = new Intent(GroupChatActivity.this, ChickUnitActivity.class);
                                                        ChickUnitIntent.putExtra("prescriptionMessage", mPrescriptionMessage);
                                                        ChickUnitIntent.putExtra("type", "prescription");
                                                        startActivity(ChickUnitIntent);
                                                    } else {
                                                        Intent prescriptionIntent = new Intent(GroupChatActivity.this, PrescriptionActivity.class);
                                                        prescriptionIntent.putExtra("prescriptionMessage", mPrescriptionMessage);
                                                        startActivity(prescriptionIntent);
                                                    }
                                                }
                                            } else {
                                                // toast(message);
                                                Toast.makeText(mActivity, "下载证书失败，请重试(" + status + ")", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    });

                                }
                            });


                        } else {
                            //   BJCASDK.getInstance().clearCert(ChatActivity.this);
                            Log.i("zdp", "证书已下载");
                            if (!ExistStamp) {
                                BJCASDK.getInstance().drawStamp(mActivity, Contants.clientId, new YWXListener() {
                                    @Override
                                    public void callback(String msg) {
                                        YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                        String status = yWXListenerEntity.getStatus();
                                        String message = yWXListenerEntity.getMessage();
                                        if (status != null && status.equals("0")) {
                                            if (isIgnore == 0) {
                                                Intent ChickUnitIntent = new Intent(GroupChatActivity.this, ChickUnitActivity.class);
                                                ChickUnitIntent.putExtra("prescriptionMessage", mPrescriptionMessage);
                                                ChickUnitIntent.putExtra("type", "prescription");
                                                startActivity(ChickUnitIntent);
                                            } else {
                                                Intent prescriptionIntent = new Intent(GroupChatActivity.this, PrescriptionActivity.class);
                                                prescriptionIntent.putExtra("prescriptionMessage", mPrescriptionMessage);
                                                startActivity(prescriptionIntent);
                                            }
                                        } else {
                                            //  toast(message);
                                            ToastUtil.setToast("设置签章失败，请重试！+(" + status + ")");
                                        }
                                    }
                                });
                            } else {
                                if (isIgnore == 0) {
                                    Intent chickUnitIntent = new Intent(GroupChatActivity.this, ChickUnitActivity.class);
                                    chickUnitIntent.putExtra("prescriptionMessage", mPrescriptionMessage);
                                    chickUnitIntent.putExtra("type", "prescription");
                                    startActivity(chickUnitIntent);
                                } else {
                                    Intent prescriptionIntent = new Intent(GroupChatActivity.this, PrescriptionActivity.class);
                                    prescriptionIntent.putExtra("prescriptionMessage", mPrescriptionMessage);
                                    startActivity(prescriptionIntent);
                                }
                            }

                        }

                    } else {
                        alertDialog("prescription");
                    }


                    //  IsHasOpenPres();
                }

                break;
            case R.id.medical_service:
//                mLoginStatus = SpUtils.getInt(Contants.Status, 0);
//                if (mLoginStatus != 1) {
//                    AppUtils.checkAuthenStatus(mStatus, ChatActivity.this);
//                } else {
                Intent medicalServiceIntent = new Intent(GroupChatActivity.this, MedicalServiceActivity.class);
                medicalServiceIntent.putExtra("prescriptionMessage", mPrescriptionMessage);
                startActivityForResult(medicalServiceIntent, SERVICE_PACKAGE_CODE);
                //}
                break;

            case R.id.tv_right:
                Intent ArchivesIntent = new Intent(GroupChatActivity.this, ArchivesActivity.class);
                if (TextUtils.isEmpty(mPrescriptionMessage)) {
                    mPrescriptionMessage = SpUtils.getString(Contants.LoginJson);
                }
                Log.d("chat", "mPrescriptionMessage-----" + mPrescriptionMessage);
                ArchivesIntent.putExtra("prescriptionMessage", mPrescriptionMessage);
                startActivity(ArchivesIntent);
                break;
            case R.id.accept:

                acceptWenzhen();

                break;
            case R.id.reject:
                // operateTip("reject");
                if (mAction != null && mAction.equals("MDT")) {
                    rejectMDTOpration();
                } else {
                    rejectOpration();
                }
                break;
//            case R.id.end_of_the_interrogation:
//                alertDialog("end");
//
//                break;
//            case R.id.interrogation_table:
//                Intent consultationIntent = new Intent(GroupChatActivity.this, MissionActivity.class);
//                consultationIntent.putExtra("missionMessage", mPrescriptionMessage);
//                consultationIntent.putExtra("action", "consultation");
//                startActivityForResult(consultationIntent, MISSION_CODE);
//                break;
//            case R.id.the_quick_reply:
//                Intent replyIntent = new Intent(GroupChatActivity.this, ReplyQuickActivity.class);
//
//                startActivityForResult(replyIntent, QUICK_REPLY_CODE);
//                break;
//            case R.id.add_your_resume:
//                medicalRecordSelection();
//                break;
//            case R.id.plus_sign:
//                mLoginStatus = SpUtils.getInt(Contants.Status, 0);
//                if (mLoginStatus != 1) {
//                    AppUtils.checkAuthenStatus(mStatus, GroupChatActivity.this);
//                } else {
//                    alertDialog("plus");
//
//                    // sendCustomMessage(json,"");
//                }
//                // sendCustomMessage(json,"");
//                break;


            case R.id.checkTable:
                Intent intentcheck = new Intent(getContext(), PrescriptionActivity.class);
                intentcheck.putExtra("type", 8);
                intentcheck.putExtra("prescriptionMessage", mPrescriptionMessage);
                startActivity(intentcheck);
                break;

            case R.id.zhongyao_prescription_layout:
                // toast("中药处方");
                mLoginStatus = SpUtils.getInt(Contants.Status, 0);
                if (mLoginStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, GroupChatActivity.this);
                } else {
                    if (mIsHasOpenTCMPres) {
                        SharedPreferences sp = getSharedPreferences("yunyisheng",
                                Context.MODE_PRIVATE);
                        boolean isExists = BJCASDK.getInstance().existsCert(GroupChatActivity.this);

                        boolean ExistStamp = BJCASDK.getInstance().existsStamp(GroupChatActivity.this);
                        /// BJCASDK.getInstance().clearCert(ChatActivity.this);
                        // BJCASDK.getInstance().startUrl(ChatActivity.this, Contants.clientId, 3);
                        // BJCASDK.getInstance().startUrl(ChatActivity.this, Contants.clientId, 1);
                        if (!isExists) {
                            mDialog = new DownloadCertDialog(GroupChatActivity.this);
                            mDialog.show("温馨提示", "", new DownloadCertDialog.DialogListener() {
                                @Override
                                public void confirm() {
                                    Log.i("ChatActivity", "aaa");
                                    BJCASDK.getInstance().certDown(GroupChatActivity.this, Contants.clientId, SpUtils.getString(Contants.userName), new YWXListener() {
                                        @Override
                                        public void callback(String s) {
                                            Log.d("chat", "callback" + s);
                                            YWXListenerEntity yWXListenerEntity = mGson.fromJson(s, YWXListenerEntity.class);
                                            String status = yWXListenerEntity.getStatus();
                                            String message = yWXListenerEntity.getMessage();

                                            BJCASDK.getInstance().getUserInfo(GroupChatActivity.this, Contants.clientId, new YWXListener() {
                                                @Override
                                                public void callback(String s) {
                                                    Log.d("chat", "证书" + s);
                                                }
                                            });

                                            boolean ExistStamp2 = BJCASDK.getInstance().existsStamp(GroupChatActivity.this);
                                            Log.d("chat", "证书" + ExistStamp2);

                                            if (status != null && status.equals("0")) {
                                                boolean ExistStamp1 = BJCASDK.getInstance().existsStamp(GroupChatActivity.this);
                                                if (!ExistStamp1) {

                                                    BJCASDK.getInstance().drawStamp(mActivity, Contants.clientId, new YWXListener() {
                                                        @Override
                                                        public void callback(String msg) {
                                                            YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                                            String status = yWXListenerEntity.getStatus();
                                                            String message = yWXListenerEntity.getMessage();
                                                            if (status != null && status.equals("0")) {
                                                                Intent zhoangyaochufang = new Intent(getContext(), PrescriptionActivity.class);
                                                                zhoangyaochufang.putExtra("type", 16);
                                                                zhoangyaochufang.putExtra("prescriptionMessage", mPrescriptionMessage);
                                                                startActivity(zhoangyaochufang);
                                                            } else {
                                                                //   toast(message);
                                                                // ToastUtil.setToast("设置签章失败，请重试！"+(status));
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    Intent zhoangyaochufang = new Intent(getContext(), PrescriptionActivity.class);
                                                    zhoangyaochufang.putExtra("type", 16);
                                                    zhoangyaochufang.putExtra("prescriptionMessage", mPrescriptionMessage);
                                                    startActivity(zhoangyaochufang);
                                                }
                                            } else {
                                                // toast(message);
                                                Toast.makeText(mActivity, "下载证书失败，请重试(" + status + ")", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    });

                                }
                            });


                        } else {
                            //   BJCASDK.getInstance().clearCert(ChatActivity.this);
                            Log.i("zdp", "证书已下载");
                            if (!ExistStamp) {
                                BJCASDK.getInstance().drawStamp(mActivity, Contants.clientId, new YWXListener() {
                                    @Override
                                    public void callback(String msg) {
                                        YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                        String status = yWXListenerEntity.getStatus();
                                        String message = yWXListenerEntity.getMessage();
                                        if (status != null && status.equals("0")) {
                                            Intent zhoangyaochufang = new Intent(getContext(), PrescriptionActivity.class);
                                            zhoangyaochufang.putExtra("type", 16);
                                            zhoangyaochufang.putExtra("prescriptionMessage", mPrescriptionMessage);
                                            startActivity(zhoangyaochufang);
                                        } else {
                                            //  toast(message);
                                            ToastUtil.setToast("设置签章失败，请重试！+(" + status + ")");
                                        }
                                    }
                                });
                            } else {
                                Intent zhoangyaochufang = new Intent(getContext(), PrescriptionActivity.class);
                                zhoangyaochufang.putExtra("type", 16);
                                zhoangyaochufang.putExtra("prescriptionMessage", mPrescriptionMessage);
                                startActivity(zhoangyaochufang);
                            }

                        }
                    } else {
                        alertDialog("zhongyaoprescription");
                    }
                }

                break;

            case R.id.medical_advice_doctor: //推荐会诊医生


                // toast("MDT分诊");
//                Intent mdtIntent = new Intent(GroupChatActivity.this, PrescriptionActivity.class);
//                mdtIntent.putExtra("type", 23);
//                mdtIntent.putExtra("SubjectBuyRecordId", mMDTDetailEntity.getSubjectBuyRecordId() + "");
//                startActivity(mdtIntent);
                Intent mdtIntent = new Intent(GroupChatActivity.this, PrescriptionActivity.class);
                //先取出来

                //组装病人信息
                PatientInfoEntity patientInfoEntity = new PatientInfoEntity();
                patientInfoEntity.setPatientId(mMDTDetailEntity.getPatientId());
                patientInfoEntity.setPatientName(mMDTDetailEntity.getPatientName());
                patientInfoEntity.setPatientSex(mMDTDetailEntity.getPatientSex());

                //解析医生病人数据
                Type jsonType = new TypeToken<PrescriptionMessageEntity<ConsultMessageEntity>>() {
                }.getType();
                PrescriptionMessageEntity<ConsultMessageEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), jsonType);
                ConsultMessageEntity ConsultMessageEntity = new ConsultMessageEntity();
                ConsultMessageEntity.setPatientInfo(patientInfoEntity);
                prescriptionMessageEntity.setPatient(ConsultMessageEntity);
                String json = mGson.toJson(prescriptionMessageEntity);
                mdtIntent.putExtra("prescriptionMessage", json);
                mdtIntent.putExtra("type", 23);
                mdtIntent.putExtra("SubjectBuyRecordId", mMDTDetailEntity.getSubjectBuyRecordId() + "");
                startActivity(mdtIntent);
                break;

            case R.id.medical_advice_report: //出具诊疗报告

                //如果是已经支付，并且是一结束或者出报告状态，才能查看报告
                if (TextUtils.isEmpty(mMDTDetailEntity.getSubjectReportPath())){
                    Intent intent = new Intent(this, MDTInputReportActivity.class);
                    intent.putExtra("MDTDetailEntity", mMDTDetailEntity);
                    startActivity(intent);
                }else {
                    Intent intentsuggest = new Intent(getContext(), MDTInputReportFromListActivity.class);
                    intentsuggest.putExtra("MDTDetailEntity", mMDTDetailEntity);
                    intentsuggest.putExtra("type", 2);//已完成
                    startActivity(intentsuggest);

                }

//                        if (mMDTDetailEntity.getPayStatus() == 2) {
//
//                    if (mMDTDetailEntity.getSubjectStatus() == 4 || mMDTDetailEntity.getSubjectStatus() == 5) {
//                        //查看结论性建议
//
//                    } else {
//                        Intent intent = new Intent(this, MDTInputReportActivity.class);
//                        intent.putExtra("MDTDetailEntity", mMDTDetailEntity);
//                        startActivity(intent);
//                    }
//
//                } else {
//
//                }
                break;

            case R.id.medical_advice_suggest://出具诊疗建议
//如果是已经支付，并且是一结束或者出报告状态，才能查看报告
                if (mMDTDetailEntity.getPayStatus() == 2) {

                    if (mMDTDetailEntity.getSubjectStatus() == 4 || mMDTDetailEntity.getSubjectStatus() == 5) {
                        //查看结论性建议
                        Intent intentsuggest = new Intent(getContext(), MDTInputReportFromListActivity.class);
                        intentsuggest.putExtra("MDTDetailEntity", mMDTDetailEntity);
                        intentsuggest.putExtra("type", 2);//已完成
                        startActivity(intentsuggest);

                    } else {
                        Intent intentsuggest = new Intent(this, MDTInputReportActivity.class);
                        intentsuggest.putExtra("MDTDetailEntity", mMDTDetailEntity);
                        startActivity(intentsuggest);
                    }

                } else {
                    Intent intentsuggest = new Intent(this, MDTInputReportActivity.class);
                    intentsuggest.putExtra("MDTDetailEntity", mMDTDetailEntity);
                    startActivity(intentsuggest);
                }

                break;

            case R.id.medical_end_suggest: //填写结论性建议

                if (mMDTDetailEntity.getPayStatus() == 2) {

                    if (mMDTDetailEntity.getSubjectStatus() == 4 || mMDTDetailEntity.getSubjectStatus() == 5) {
                        //查看结论性建议
                        Intent intentsuggest = new Intent(getContext(), MDTInputReportFromListActivity.class);
                        intentsuggest.putExtra("MDTDetailEntity", mMDTDetailEntity);
                        intentsuggest.putExtra("type", 2);//已完成
                        startActivity(intentsuggest);

                    } else {

                        Intent intentsuggest2 = new Intent(getContext(), MDTInputReportFromListActivity.class);
                        intentsuggest2.putExtra("MDTDetailEntity", mMDTDetailEntity);
                        intentsuggest2.putExtra("type", 1);//已完成
                        startActivity(intentsuggest2);
                    }

                } else {
                    List<MDTDetailEntity.MDTReportDoctorsBean> reportlist = mMDTDetailEntity.getMDTReportDoctors();
                    if (mMDTDetailEntity.getSubjectType() == 3) {
                        for (int i = 0; i < reportlist.size(); i++) {
                            if (reportlist.get(i).getIsSubmit() == 0) {
                                toast("请先提醒其他会诊医生填写诊疗意见后再填写结论性意见");
                                return;
                            }
                        }
                    }
                    Intent intentsuggest2 = new Intent(getContext(), MDTInputReportFromListActivity.class);
                    intentsuggest2.putExtra("MDTDetailEntity", mMDTDetailEntity);
                    intentsuggest2.putExtra("type", 1);//已完成
                    startActivity(intentsuggest2);
                }

                break;


        }
    }


    private void sendPushVideo(int RoomId) {

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        HashMap<String, String> params = new HashMap<>();
        params.put("SenderId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        params.put("SenderName", String.valueOf(SpUtils.getString(Contants.Name)));
        if (mAction != null && mAction.equals("videoInterrogation")) {
            if (mAllRecordForDoctorEntity != null) {
                params.put("ReceiverId", mAllRecordForDoctorEntity.getPatientId() + "");
                params.put("ReceiverName", mAllRecordForDoctorEntity.getPatientName());
            }

        } else if (mAction != null && mAction.equals("videoInterrogationfromhhome")) {
            if (mAllRecordForDoctorEntity != null) {
                params.put("ReceiverId", mInquiryRecordListDataEntity.getPatientId() + "");
                params.put("ReceiverName", mInquiryRecordListDataEntity.getPatientName());

            }

        }

        params.put("Action", "1");
        params.put("RoomId", RoomId + "");
        params.put("ToType", "1");
        params.put("Message", "");

        mMyOkhttp.post().url(HttpUrl.sendVideo).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity response) {
                Log.d("chat", "给IOS发送视频聊天成功");
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
            }
        });

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        try {
            checkPermissionsIfCanContinue(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        //  StatusBarConfig.assistActivity(this);
        ILVCallManager.getInstance().init(new ILVCallConfig()
                .setNotificationListener(this)
                .setAutoBusy(true));

        TIMManager.getInstance().setConnectionListener(new TIMConnListener() {
            @Override
            public void onConnected() {
                Log.e("zdp", "[DEV]onConnected->enter");
            }

            @Override
            public void onDisconnected(int i, String s) {
                Log.e("zdp", "[DEV]onDisconnected->enter: " + i + ", " + s);
            }

            @Override
            public void onWifiNeedAuth(String s) {
                Log.e("zdp", "[DEV]onWifiNeedAuth->enter:" + s);
            }
        });
        // 设置通话回调
        ILVCallManager.getInstance().addIncomingListener(this);
        ILVCallManager.getInstance().addCallListener(this);
        TIMManager.getInstance().setMessageRevokedListener(this);
        //   initView();
    }

    private void initToolbar() {
        //mName = getIntent().getStringExtra(Contants.FRIEND_NAME);

//        if (!TextUtils.isEmpty(mName)) {
//            //topTitle.setText(name);
////            if (mStatus == 0) {
////                initBackTitle(mName).setRightText("患者档案");
////            } else {
////                initBackTitle(mName).setRightText("患者档案");
//
//
//
////            }
//
//            initBackTitle(mName);
//
//        } else {
            getGroupInfo();
//            if (mStatus == 0) {
//                initBackTitle(getIntent().getStringExtra(Contants.FRIEND_IDENTIFIER)).setRightText("患者档案");
//            } else {
//                initBackTitle(getIntent().getStringExtra(Contants.FRIEND_IDENTIFIER)).setRightText("患者档案");
//            }
            // topTitle.setText(getIntent().getStringExtra(Contants.FRIEND_IDENTIFIER));
       // }
        mofflinName = getIntent().getStringExtra(Contants.Name);
        tvRight.setVisibility(View.GONE);
        topRight.setVisibility(View.VISIBLE);
        topRight.setImageResource(R.drawable.ic_group);

        topRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent groupIntent = new Intent(GroupChatActivity.this, GroupMemberActivity.class);
                groupIntent.putExtra(Contants.GroupID, mIdentifier);
                groupIntent.putExtra(Contants.GroupNAME, mName);
                groupIntent.putExtra("MDTDetailEntity", mMDTDetailEntity);

                startActivity(groupIntent);
            }
        });
    }

    private void getGroupInfo() {

        List<String> var1 = new ArrayList<>();
        var1.add(mIdentifier);
        TIMGroupManager.getInstance().getGroupDetailInfo(var1, new TIMValueCallBack<List<TIMGroupDetailInfo>>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(List<TIMGroupDetailInfo> timGroupDetailInfos) {
                if (timGroupDetailInfos.size()>0){
                    initBackTitle(timGroupDetailInfos.get(0).getGroupName());
                }
            }
        });
    }

    public List<MyTIMMessage> getAdapterData() {

        return mTimMessages;
    }

    public static long date2TimeStamp(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    private void hideInput() {
        input.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }

    private void showInput() {
        input.setFocusable(true);
        input.setFocusableInTouchMode(true);
        input.requestFocus();
        morePanel.setVisibility(View.GONE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(input, 0);
    }

    public void recordFail() {
        if (mIsRecording) {
            mIsRecording = false;
            // voiceSending.setText(getText(R.string.chat_cancel_send));
            voiceSending.setVisibility(View.GONE);
            if (mAnimationDrawable != null) {
                mAnimationDrawable.selectDrawable(0);
                mAnimationDrawable.stop();
            }
            voicePanel.setText(getText(R.string.chat_press_talk));
            sendSoundMessage(false);
        }
    }


    public void newIncomingCall(final int callId, String sender, final String userSig) {
        Log.i("dd", "callId=" + callId);
        if (null != mIncomingDlg) {  // 关闭遗留来电对话框
            mIncomingDlg.dismiss();
        }
        //  mCurIncomingId = callId;
        mIncomingDlg = new AlertDialog.Builder(this)
                .setTitle("来电提醒 ")
                .setMessage("New Call From " + sender)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mIncomingDlg.dismiss();
                        sendVideoMessage(130, -1);
                        //acceptCall(callId, notification.getSponsorId(), callType);
                        Intent roomIntent = new Intent(GroupChatActivity.this, RoomActivity.class);
                        roomIntent.putExtra("callId", callId);
                        roomIntent.putExtra("target", "other");
                        roomIntent.putExtra("userSig", userSig);
                        startActivity(roomIntent);
                    }
                })
                .setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendVideoMessage(133, -1);
                        //   int ret = ILVCallManager.getInstance().rejectCall(mCurIncomingId);
                    }
                })
                .create();
        mIncomingDlg.setCanceledOnTouchOutside(false);
        mIncomingDlg.show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void initMessage(TIMMessage timMessage) {
        mConversation.getMessage(20, timMessage, new TIMValueCallBack<List<TIMMessage>>() {
            @Override
            public void onError(int i, String s) {
                if (easylayout == null) {
                    return;
                }
                if (easylayout.isRefreshing()) {
                    easylayout.refreshComplete();
                }
                final String userSig = SpUtils.getString(Contants.UserSig);
                final String id = SpUtils.getString(Contants.identifier);
                ILiveLoginManager.getInstance().iLiveLogout(new ILiveCallBack() {
                    @Override
                    public void onSuccess(Object data) {
                        Log.i("LogOutUtil", "iLiveLogoutdata=" + data.toString());
                        loginSDK(id, userSig);
                    }

                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        loginSDK(id, userSig);
                        Log.i("LogOutUtil", "iLiveLogoutonError=" + errCode + ",errMsg=" + errMsg);
                    }
                });

             /*   if (i == 6013) {
                    toast("服务器断开，请重新登录");
                }
                else {
                    toast(s);
                }*/
                Log.i("ChatActivity", "error=" + s + "  " + i);
            }

            @Override
            public void onSuccess(final List<TIMMessage> timMessages) {
               /* TIMTextElem textElem = (TIMTextElem) timMessages.get(0).getElement(0);
                Log.i("zdp","id="+ textElem.getText());*/
                //  mTimMessages.clear();
                if (easylayout == null) {
                    return;
                }
                if (easylayout.isRefreshing()) {
                    easylayout.refreshComplete();
                }
                if (timMessages.size() == 0) {
                    mIsImsdkReflashComplete = true;
                }
                List<MyTIMMessage> list = new ArrayList<>();
                for (int i = 0; i < timMessages.size(); i++) {
                    MyTIMMessage myTIMMessage = new MyTIMMessage();
                    myTIMMessage.setTimMessage(timMessages.get(i));
                    list.add(myTIMMessage);
                    Log.d("数据类型", "" + myTIMMessage.getTimMessage().getElement(0).getType() + "    " + i);

                    try {
                        TIMCustomElem element = (TIMCustomElem) myTIMMessage.getTimMessage().getElement(0);

                    } catch (Exception e) {

                    }

                }
                getAdapterData().addAll(list);
                // updateRecyclerView();


                if (list.size() == 0) {
                    Log.d("ChatActivity", "本地没数据直接刷新后台的消息");
                    mIsImsdkReflashComplete = true;
                    mMsgTimestampStart = (int) (System.currentTimeMillis() / 1000);
                    GetChatHistoryByPage(String.valueOf(SpUtils.getInt(Contants.Id, 0)), String.valueOf(mAccountId), mMsgTimestampStart, 20, true, 0);
                } else if (list.size() < 20) {
                    Log.d("ChatActivity", "本地有数据" + list.size() + " 不够20，拉取后台消息" + String.valueOf(SpUtils.getInt(Contants.Id, 0)));
                    //  getMessage(mTIMMessage);
                    mIsImsdkReflashComplete = true;
                    mMsgTimestampStart = (int) mTimMessages.get(mTimMessages.size() - 1).getTimMessage().timestamp();
                    int pageSize = 20 - list.size();
                    GetChatHistoryByPage(String.valueOf(SpUtils.getInt(Contants.Id, 0)), String.valueOf(mAccountId), mMsgTimestampStart, pageSize, true, 0);
                } else {
                    updateRecyclerView();
                }
                updateRecyclerView();
            }

        });
    }

    @Override
    public void onMessageRevoked(TIMMessageLocator timMessageLocator) {
        for (int i = 0; i < mTimMessages.size(); i++) {
            MyTIMMessage myTIMMessage = mTimMessages.get(i);
            TIMMessage timMessage = myTIMMessage.getTimMessage();
            if (timMessage.checkEquals(timMessageLocator)) {
                myTIMMessage.setRevoke(true);
                mAdapter.notifyDataSetChanged();
                break;
            }
        }

    }

    public void getMessage(TIMMessage timMessage) {
        if (mIsImsdkReflashComplete) {
            if (easylayout == null) {
                return;
            }
            if (easylayout.isRefreshing()) {
                easylayout.refreshComplete();
            }
            if (mTimMessages.size() > 0) {
                boolean isLocalMessage = mTimMessages.get(mTimMessages.size() - 1).isLocalMessage();
                if (isLocalMessage) {
                    mMsgTimestampStart = (int) mTimMessages.get(mTimMessages.size() - 1).getMsgTimestamp();
                } else {
                    mMsgTimestampStart = (int) mTimMessages.get(mTimMessages.size() - 1).getTimMessage().timestamp();
                }
            }
            GetChatHistoryByPage(String.valueOf(SpUtils.getInt(Contants.Id, 0)), String.valueOf(mAccountId), mMsgTimestampStart, 20, false, 0);
            //   getLocalDatabaseMessage(SpUtils.getInt(Contants.Id, 0),mAccountId,mMsgTimestampStart);
        } else {
            mConversation.getMessage(20, timMessage, new TIMValueCallBack<List<TIMMessage>>() {
                @Override
                public void onError(int i, String s) {
                    if (easylayout == null) {
                        return;
                    }
                    if (easylayout.isRefreshing()) {
                        easylayout.refreshComplete();
                    }
                    if (i == 6013) {
                        toast("服务器断开，请重新登录");
                    } else {
                        toast("网络连接异常，请检查网络");
                    }
                }

                @Override
                public void onSuccess(final List<TIMMessage> timMessages) {
               /* TIMTextElem textElem = (TIMTextElem) timMessages.get(0).getElement(0);
                Log.i("zdp","id="+ textElem.getText());*/
                    //  mTimMessages.clear();
                    if (easylayout == null) {
                        return;
                    }
                    if (easylayout.isRefreshing()) {
                        easylayout.refreshComplete();
                    }
                    if (timMessages.size() == 0) {
                        mIsImsdkReflashComplete = true;
                        mMsgTimestampStart = (int) mTimMessages.get(mTimMessages.size() - 1).getTimMessage().timestamp();

                        GetChatHistoryByPage(String.valueOf(SpUtils.getInt(Contants.Id, 0)), String.valueOf(mAccountId), mMsgTimestampStart, 20, false, 0);
                        // getLocalDatabaseMessage(SpUtils.getInt(Contants.Id, 0),mAccountId,mMsgTimestampStart);
                    } else if (timMessages.size() < 20) {
                        mIsImsdkReflashComplete = true;
                        List<MyTIMMessage> list = new ArrayList<>();
                        for (int i = 0; i < timMessages.size(); i++) {
                            MyTIMMessage myTIMMessage = new MyTIMMessage();
                            myTIMMessage.setTimMessage(timMessages.get(i));
                            list.add(myTIMMessage);

                        }
                        getAdapterData().addAll(list);
                        int pageSize = 20 - timMessages.size();
                        mMsgTimestampStart = (int) mTimMessages.get(mTimMessages.size() - 1).getTimMessage().timestamp();
                        GetChatHistoryByPage(String.valueOf(SpUtils.getInt(Contants.Id, 0)), String.valueOf(mAccountId), mMsgTimestampStart, pageSize, false, timMessages.size());
                    } else {
                        List<MyTIMMessage> list = new ArrayList<>();
                        for (int i = 0; i < timMessages.size(); i++) {
                            MyTIMMessage myTIMMessage = new MyTIMMessage();
                            myTIMMessage.setTimMessage(timMessages.get(i));
                            list.add(myTIMMessage);

                        }
                        getAdapterData().addAll(list);
                        mTIMMessage = mTimMessages.get(mTimMessages.size() - 1).getTimMessage();
                        mAdapter.notifyDataSetChanged();

                        if (list.size() > 0) {
                            LinearLayoutManager mLayoutManager = (LinearLayoutManager) chatRecyclerView.getLayoutManager();
                            mLayoutManager.scrollToPositionWithOffset(list.size(), 0);
                        }
                    }
                    //  updateRecyclerView();
                }
            });
        }
    }


    public void sendPictureMessage(String path) {
        if (new File(path).exists()) {
            TIMMessage timMessage = new TIMMessage();
//添加图片
            TIMImageElem elem = new TIMImageElem();
            elem.setPath(path);
//将 Elem 添加到消息
            if (timMessage.addElement(elem) != 0) {
                Log.d("zdp", "addElement failed");
                return;
            }
            MyTIMMessage myTIMMessage = new MyTIMMessage();
            myTIMMessage.setTimMessage(timMessage);
            getAdapterData().add(0, myTIMMessage);
            updateRecyclerView();
//发送消息
            mSettings.setDescr("[图片信息]");
            mSettings.setAndroidSettings(mAndroidSettings);
            timMessage.setOfflinePushSettings(mSettings);
            mConversation.sendMessage(timMessage, new TIMValueCallBack<TIMMessage>() {//发送消息回调
                @Override
                public void onError(int code, String desc) {//发送消息失败
                    //错误码 code 和错误描述 desc，可用于定位请求失败原因
                    //错误码 code 列表请参见错误码表
                    //   updateRecyclerView();
                    if (code == 6013) {
                        toast("服务器断开，请重新登录");
                    } else {
                        toast("网络连接异常，请检查网络");
                    }
                    Log.d("zdp", "send message failed. code: " + code + " errmsg: " + desc);
                }

                @Override
                public void onSuccess(TIMMessage msg) {//发送消息成功
                    updateRecyclerView();

                    MessageEventRecent messageEvent = new MessageEventRecent();
                    messageEvent.setmType(MainConstant.UpdateRecentList);
                    EventBus.getDefault().post(messageEvent);
                    Log.e("zdp", "SendMsg ok");
                }
            });
        }
    }

    public void sendVideoMessage(int type, long time) {
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
        data.setTime(textTime);
        data.setTargets(SpUtils.getString(Contants.Name));
        extData.setData(data);
        customMessageEntity.setExtData(extData);
        String json = new Gson().toJson(customMessageEntity);
        sendCustomMessage(json, "");
    }

    public void sendTextMessage(String input) {
        TIMMessage timMessage = new TIMMessage();
        TIMTextElem timTextElem = new TIMTextElem();
        timTextElem.setText(input);
        timMessage.addElement(timTextElem);
        MyTIMMessage myTIMMessage = new MyTIMMessage();
        myTIMMessage.setTimMessage(timMessage);
        getAdapterData().add(0, myTIMMessage);
        updateRecyclerView();

        mSettings.setDescr(input);
        mSettings.setAndroidSettings(mAndroidSettings);
        timMessage.setOfflinePushSettings(mSettings);

        mConversation.sendMessage(timMessage, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int i, String s) {
                Log.i("zdp", "error=" + s + "   " + i);
                //  updateRecyclerView();
                if (i == 6013) {
                    toast("服务器断开，请重新登录");
                } else {
                    toast("网络连接异常，请检查网络");
                }
            }

            @Override
            public void onSuccess(TIMMessage timMessage) {
                Log.i("zdp", "success");

                MessageEventRecent messageEvent = new MessageEventRecent();
                messageEvent.setmType(MainConstant.UpdateRecentList);
                EventBus.getDefault().post(messageEvent);
                updateRecyclerView();
            }
        });
    }


    public void updateRecyclerView() {
        if (mAdapter == null) {
            mAdapter = new GroupChatAdapter(mTimMessages, this, mPrescriptionMessage, mImagePath, mName, mConversation, mIsHasOpenPres, mIsHasOpenTCMPres);
            mAdapter.setIdentifer(mIdentifier);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            chatRecyclerView.setLayoutManager(layoutManager);
            //layoutManager.setReverseLayout(true);
            chatRecyclerView.setAdapter(mAdapter);
        } else {
            mConversation.setReadMessage();
            //    EventBus.getDefault().post(new UpdateViewEntity(true));
            mAdapter.notifyDataSetChanged();
        }
        if (mAdapter.getItemCount() > 0) {
            mTIMMessage = mTimMessages.get(mTimMessages.size() - 1).getTimMessage();
            chatRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
        }
    }


    public void startRecordAndFile() {
        //判断是否有外部存储设备sdcard

        if (audioRecord == null) {
            creatAudioRecord();
        }

        mStartRecordeTime = System.currentTimeMillis();
        audioRecord.startRecording();
        // 让录制状态为true
        isRecord = true;
        // 开启音频文件写入线程
        //    new Thread(new AudioRecordThread()).start();
    }

    public void stopRecordAndFile() {
        try {
            audioRecord.stopRecording();
            audioRecord = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void creatAudioRecord() {
        audioRecord = OmRecorder.wav(
                new PullTransport.Default(mic(), new PullTransport.OnAudioChunkPulledListener() {
                    @Override
                    public void onAudioChunkPulled(AudioChunk audioChunk) {
                        animateVoice((float) (audioChunk.maxAmplitude() / 200.0));
                    }
                }), file());

    }

    private PullableSource mic() {
        return new PullableSource.Default(
                new AudioRecordConfig.Default(
                        MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT,
                        AudioFormat.CHANNEL_IN_MONO, 16000
                )
        );
    }

    private void animateVoice(final float maxPeak) {
        ImageView recordButton = new ImageView(GroupChatActivity.this);
        recordButton.animate().scaleX(1 + maxPeak).scaleY(1 + maxPeak).setDuration(10).start();
    }

    @NonNull
    private File file() {

        File file = new File(Environment.getExternalStorageDirectory(), "huhai.wav");
        mSoundPath = file.getPath();
        return file;
    }


    public void sendSoundMessage(boolean needToSend) {
        long duration = (System.currentTimeMillis() - mStartRecordeTime) / 1000;


      /*  if (mRecorder != null) {
            try {
                mRecorder.stop();
                mRecorder.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        stopRecordAndFile();
        if (needToSend) {
            if (duration < 1) {
                Toast.makeText(MyApplication.getContext(), "录音时间过短", Toast.LENGTH_SHORT).show();
                return;
            }
            TIMMessage timMessage = new TIMMessage();
            TIMSoundElem soundElem = new TIMSoundElem();
            soundElem.setPath(mSoundPath);
            Log.i("ChatActivity", "SoundPath=" + mSoundPath);
            soundElem.setDuration(duration);
            if (timMessage.addElement(soundElem) != 0) {
                Toast.makeText(MyApplication.getContext(), "发送失败", Toast.LENGTH_SHORT).show();
                return;
            }
            MyTIMMessage myTIMMessage = new MyTIMMessage();
            myTIMMessage.setTimMessage(timMessage);
            getAdapterData().add(0, myTIMMessage);
            updateRecyclerView();
            mSettings.setDescr("[语音信息]");
            mSettings.setAndroidSettings(mAndroidSettings);
            timMessage.setOfflinePushSettings(mSettings);
            mConversation.sendMessage(timMessage, new TIMValueCallBack<TIMMessage>() {//发送消息回调
                @Override
                public void onError(int code, String desc) {//发送消息失败
                    //   updateRecyclerView();
                    if (code == 6013) {
                        toast("服务器断开，请重新登录");
                    } else {
                        toast("网络连接异常，请检查网络");
                    }
                }

                @Override
                public void onSuccess(TIMMessage msg) {//发送消息成功
                    updateRecyclerView();

                    MessageEventRecent messageEvent = new MessageEventRecent();
                    messageEvent.setmType(MainConstant.UpdateRecentList);
                    EventBus.getDefault().post(messageEvent);
                }
            });
        } else {
            File file = new File(mSoundPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Override
    protected void onDestroy() {
        try {
            if (mAdapter != null)
                mAdapter.stopMediaPlayer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ILVCallManager.getInstance().removeIncomingListener(this);
        MyApplication.mImId = null;
        ILVCallManager.getInstance().removeCallListener(this);
        //   TIMManager.getInstance().removeMessageListener(this);
        if (mTimer != null) {
            mTimer.cancel();
        }
        if (mRecorder != null) {
            mRecorder.release();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION_CODE:
                for (int ret : grantResults) {
                    if (PackageManager.PERMISSION_GRANTED != ret) {
                        Toast.makeText(this, "用户没有允许需要的权限，使用可能会受到限制！", Toast.LENGTH_SHORT).show();
                        GroupChatActivity.this.finish();
                        //  addLogMessage("用户没有允许需要的权限，使用可能会受到限制！");
                    }
                }
                audioRecord = null;
                creatAudioRecord();
                break;
            case REQUEST_CALL_PERMISSION: //拨打电话
                if (permissions.length != 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {//失败
                    Toast.makeText(this, "请允许拨号权限后再试", Toast.LENGTH_SHORT).show();
                } else {//成功
                    obtainAboutInfo();
                }


            default:
                break;
        }
    }

    private Context getContext() {
        return this;
    }

    public void call(String telPhone) {
        if (checkReadPermission(Manifest.permission.CALL_PHONE, REQUEST_CALL_PERMISSION)) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(telPhone));
            startActivity(intent);
        }
    }

    boolean checkPermissionsIfCanContinue(Activity activity) throws Exception {

        LinkedList<String> llNoGrandPermission = new LinkedList<String>();
        List<String> permissions = new ArrayList<>();
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)) {
            permissions.add(Manifest.permission.CAMERA);
        }
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)) {
            permissions.add(Manifest.permission.RECORD_AUDIO);
        }
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE)) {
            permissions.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        boolean[] isAllows = new boolean[permissions.size()];
        for (int i = 0; i < permissions.size(); i++) {
            isAllows[i] = ContextCompat.checkSelfPermission(activity,
                    permissions.get(i)) == PackageManager.PERMISSION_GRANTED;
            if (!isAllows[i]) {
                llNoGrandPermission.add(permissions.get(i));
            }
        }

        boolean isAllPass = true;
        for (int i = 0; i < isAllows.length; i++) {
            if (!isAllows[i]) {
                isAllPass = false;
                break;
            }
        }

        if (isAllPass) {
            return true;
        }


        String[] noGrandPers = new String[llNoGrandPermission.size()];
        ActivityCompat
                .requestPermissions(this,
                        llNoGrandPermission.toArray(noGrandPers),
                        REQ_PERMISSION_CODE);

        for (int i = 0; i < permissions.size(); i++) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    permissions.get(i))) {
                throw new Exception(permissions.get(i));
            }
        }
        return false;

    }

    private boolean upMove(int y) {
        return (mLastY - y) > FLING_MIN_DISTANCE;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_TAKE_PICTURE:

                if (resultCode == RESULT_OK) {

                    Log.i("zdp", mPicturePath);
                    sendPictureMessage(mPicturePath);
                }
                break;
            case IMAGE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);  //获取照片路径
                        sendPictureMessage(path);
                        cursor.close();
                    } catch (Exception e) {
                        // TODO Auto-generatedcatch block
                        e.printStackTrace();
                    }
                }
                break;
            case SERVICE_PACKAGE_CODE:
                if (resultCode == RESULT_OK) {
                    String servicePackage = data.getExtras().getString("servicePackage");
                    sendCustomMessage(servicePackage, "customer");
                    Log.i("zdp", "servicePackage=" + servicePackage);
                }
                break;
            case PRESCRIPTION_CODE:
                if (resultCode == RESULT_OK) {
//                    String prescriptionCode = data.getExtras().getString("uniqueId");
//                    int result = BJCASDK.getInstance().signRecipe(ChatActivity.this, Contants.clientId, prescriptionCode);
//
//                    if (result != ConstantParams.CALL_SUCCESS) {
//                        //调用失败，可以根据集成文档查看失败原因
//                        toast("调用失败！错误返回码为:" + result);
//                    } else {
//                        toast("调用成功");
//                    }

                    toast("调用成功");
                }
                break;
            case ConstantParams.ACTIVITY_SIGN_DATA:
               /* BJCASDK.getInstance().getUserInfo(ChatActivity.this, Contants.clientId, new OperateListener() {
                    @Override
                    public void callback(String s) {
                        Log.i("zdp", "result=" + s);
                    }
                });*/
                String result = null;
                try {
                    result = data.getStringExtra(ConstantParams.KEY_SIGN_BACK);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case MISSION_CODE:
                if (resultCode == RESULT_OK) {
                    String mission = data.getExtras().getString("Health");
                    sendCustomMessage(mission, "customer");
                }
                break;
            case QUICK_REPLY_CODE:
                if (resultCode == RESULT_OK) {
                    String replyMessage = data.getExtras().getString("reply_data");
                    sendTextMessage(replyMessage);
                    //sendCustomMessage(replyMessage, "customer");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 发起呼叫
     */
    private void makeCall(int callType, String callNum) {
        Log.i("zdp", "HostId=" + ILiveLoginManager.getInstance().getMyUserId() + "CallType=" + callType);
        Intent intent = new Intent();
        intent.setClass(this, CallActivity.class);
        intent.putExtra("HostId", ILiveLoginManager.getInstance().getMyUserId());
        intent.putExtra("CallId", 0);
        intent.putExtra("CallType", callType);
        intent.putExtra("CallNumber", callNum);
        startActivity(intent);
    }

    private void acceptCall(int callId, String hostId, int callType) {
        Log.i("ChatActivity", "callId=" + mCurIncomingId + ",hostId=" + hostId + ",callType=" + callType);
        Intent intent = new Intent();
        intent.setClass(GroupChatActivity.this, CallActivity.class);
        intent.putExtra("HostId", hostId);
        intent.putExtra("CallId", mCurIncomingId);
        intent.putExtra("CallType", callType);
        startActivity(intent);
    }

    @Override
    public void onCallEstablish(int callId) {

    }

    @Override
    public void onCallEnd(int callId, int endResult, String endInfo) {
        if (mCurIncomingId == callId) {
            mIncomingDlg.dismiss();
        }
    }

    @Override
    public void onException(int iExceptionId, int errCode, String errMsg) {

    }

    @Override
    public void onRecvNotification(int callid, ILVCallNotification notification) {

    }

    @Override
    public void onNewIncomingCall(final int callId, final int callType, final ILVIncomingNotification notification) {
        Log.i("dd", "callId=" + callId + ",callType=" + callType);
        if (null != mIncomingDlg) {  // 关闭遗留来电对话框
            mIncomingDlg.dismiss();
        }
        mCurIncomingId = callId;
        mIncomingDlg = new AlertDialog.Builder(this)
                .setTitle("New Call From " + notification.getSender())
                .setMessage(notification.getNotifDesc())
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        acceptCall(callId, notification.getSponsorId(), callType);
                    }
                })
                .setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int ret = ILVCallManager.getInstance().rejectCall(mCurIncomingId);
                    }
                })
                .create();
        mIncomingDlg.setCanceledOnTouchOutside(false);
        mIncomingDlg.show();
    }

    private void sendCustomMessage(String data, String desc) {
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
        MyTIMMessage myTIMMessage = new MyTIMMessage();
        myTIMMessage.setTimMessage(timMessage);
        getAdapterData().add(0, myTIMMessage);
        updateRecyclerView();
        mSettings.setDescr(desc);
        mSettings.setAndroidSettings(mAndroidSettings);
        timMessage.setOfflinePushSettings(mSettings);
//发送消息
        mConversation.sendMessage(timMessage, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 含义请参见错误码表
                //   updateRecyclerView();
                if (code == 6013) {
                    toast("服务器断开，请重新登录");
                } else {
                    toast("网络连接异常，请检查网络");
                }
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                updateRecyclerView();

                MessageEventRecent messageEvent = new MessageEventRecent();
                messageEvent.setmType(MainConstant.UpdateRecentList);
                EventBus.getDefault().post(messageEvent);
            }
        });
    }


    //视频业务状态变更
    private void updateInquiryRecordStatus(int operatorRole, int operatorId, final int recodeStatus, int id, String remark) {
        Map<String, String> map = new HashMap<>();
        map.put("OperatorRole", String.valueOf(operatorRole));
        map.put("OperatorId", String.valueOf(operatorId));
        map.put("Status", String.valueOf(recodeStatus));
        map.put("Id", String.valueOf(id));
        if (remark != null) {
            map.put("Remark", remark);
        }
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.UpdateInquiryRecordStatus).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                LoadDialog.clear();
                if (entity.getCode() == 0) {
                    boolean result = (boolean) entity.getData();
                    if (result) {
                        List<AllRecordForDoctorEntity> list = SQLiteUtils.getInstance().selectImDataByServiceCodeAndId(mServiceCode, mIdentifier);
                        if (recodeStatus == 1) {
                            if (list.size() > 0) {
                                AllRecordForDoctorEntity allRecordForDoctorEntity = list.get(0);
                                allRecordForDoctorEntity.setStatus(1);
                                Date date = new Date(System.currentTimeMillis());
                                String dateTime = mFormatter.format(date);
                                allRecordForDoctorEntity.setStartTime(dateTime);
                                allRecordForDoctorEntity.setDealWithTime(dateTime);
                                SQLiteUtils.getInstance().updateImData(allRecordForDoctorEntity);
                            }
                            inputBorder.setVisibility(View.VISIBLE);
                            acceptLayout.setVisibility(View.GONE);
                            acceptTime.setVisibility(View.VISIBLE);
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));
                            status.setText("接诊中");
                            cancallVideo = true;
                            try {

                                mTimer = new CountDownTimer(VALID_TIME, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        String time = TimeUtil.formatTime(millisUntilFinished);
                                        acceptTime.setText("问诊时间还剩" + time);
                                        Log.d("chat", "定时器5" + time);
                                    }

                                    @Override
                                    public void onFinish() {
                                        // update(2, SpUtils.getInt(Contants.Id, 0), 5, mInquiryRecordListDataEntity.getId());
                                        status.setText("已结束");
                                        inputBorder.setVisibility(View.VISIBLE);
                                        acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                                        acceptTime.setVisibility(View.GONE);
                                        if (mTimer != null) {
                                            mTimer.cancel();
                                        }
                                        stopWenzhen();

                                    }
                                }.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else if (recodeStatus == 2 || recodeStatus == 5) {
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                            inputBorder.setVisibility(View.VISIBLE);
                            acceptLayout.setVisibility(View.GONE);
                            status.setText("已结束");
                            cancallVideo = false;
                            acceptTime.setVisibility(View.GONE);
                            if (list.size() > 0) {
                                AllRecordForDoctorEntity allRecordForDoctorEntity = list.get(0);
                                SQLiteUtils.getInstance().deleteImData(allRecordForDoctorEntity);
                            }
                            if (mTimer != null) {
                                mTimer.cancel();
                            }

                        }
                        EventBus.getDefault().post(new UpdateImMessageEntity(null));
                    } else {
                        toast("接诊失败");
                    }
                } else {
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    //加号
    private void plusSign() {
        String url = HttpUrl.GetDoctorAuthenInfo + "?DrId=" + SpUtils.getInt(Contants.Id, 0);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(url).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<AutheninfoEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<AutheninfoEntity> entity) {
                if (entity.getCode() == 0) {
                    CustomMessageEntity customMessageEntity = new CustomMessageEntity();
                    customMessageEntity.setFocusTitle("加号凭证");
                    customMessageEntity.setIsSystem(false);
                    customMessageEntity.setContent(null);
                    List<CustomMessageEntity.ContentBean> list = new ArrayList<>();
                    CustomMessageEntity.ContentBean contentBean = new CustomMessageEntity.ContentBean();
                    CustomMessageEntity.ContentBean.ContentElemBean contentElemBean1 = new CustomMessageEntity.ContentBean.ContentElemBean();
                    contentBean.setType("text");
                    long time = System.currentTimeMillis();
                    contentElemBean1.setText("加号编号：" + time);
                    contentBean.setContentElem(contentElemBean1);
                    list.add(contentBean);
                    CustomMessageEntity.ContentBean contentBean1 = new CustomMessageEntity.ContentBean();
                    CustomMessageEntity.ContentBean.ContentElemBean contentElemBean2 = new CustomMessageEntity.ContentBean.ContentElemBean();
                    contentBean1.setType("text");
                    contentElemBean2.setText("请加" + SpUtils.getString(Contants.Name) + "医生号一个");
                    contentBean1.setContentElem(contentElemBean2);
                    list.add(contentBean1);
                    customMessageEntity.setContent(list);
                    CustomMessageEntity.ExtDataBean extData = new CustomMessageEntity.ExtDataBean();
                    CustomMessageEntity.ExtDataBean.DataBean data = new CustomMessageEntity.ExtDataBean.DataBean();
                    extData.setType(28);
                    data.setStamp(entity.getData().getStamp());
                    extData.setData(data);
                    customMessageEntity.setExtData(extData);
                    String json = new Gson().toJson(customMessageEntity);
                    Log.i("ChatActivity", "json=" + json);
                    sendCustomMessage(json, "");
                    //  mVideoInterrogationAdapter.setDatalist( entity.getData());
                } else {
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    //变更续方业务状态
    private void updatePrescriptionApplyStatus(int operatorRole, int operatorId, final int recodeStatus, int id, String remark) {
        Map<String, String> map = new HashMap<>();
        map.put("OperatorRole", String.valueOf(operatorRole));
        map.put("OperatorId", String.valueOf(operatorId));
        map.put("Status", String.valueOf(recodeStatus));
        map.put("Id", String.valueOf(id));
        if (remark != null) {
            map.put("Remark", remark);
        }
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.UpdatePrescriptionApplyStatus).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                LoadDialog.clear();
                if (entity.getCode() == 0) {
                    boolean result = (boolean) entity.getData();
                    if (result) {
                        List<AllRecordForDoctorEntity> list = SQLiteUtils.getInstance().selectImDataByServiceCodeAndId(mServiceCode, mIdentifier);
                        if (recodeStatus == 1) {

                            if (list.size() > 0) {
                                AllRecordForDoctorEntity allRecordForDoctorEntity = list.get(0);
                                allRecordForDoctorEntity.setStatus(1);
                                Date date = new Date(System.currentTimeMillis());
                                String dateTime = mFormatter.format(date);
                                allRecordForDoctorEntity.setStartTime(dateTime);
                                allRecordForDoctorEntity.setDealWithTime(dateTime);
                                SQLiteUtils.getInstance().updateImData(allRecordForDoctorEntity);
                            }
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));
                            inputBorder.setVisibility(View.VISIBLE);
                            acceptLayout.setVisibility(View.GONE);
                            acceptTime.setVisibility(View.VISIBLE);
                            status.setText("接诊中");
                            try {

                                mTimer = new CountDownTimer(VALID_TIME, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        String time = TimeUtil.formatTime(millisUntilFinished);
                                        acceptTime.setText("问诊时间还剩" + time);
                                        Log.d("chat", "定时器4" + time);
                                    }

                                    @Override
                                    public void onFinish() {
                                        // update(2, SpUtils.getInt(Contants.Id, 0), 5, mInquiryRecordListDataEntity.getId());
                                        status.setText("已结束");
                                        inputBorder.setVisibility(View.VISIBLE);
                                        acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                                        acceptTime.setVisibility(View.GONE);
                                        if (mTimer != null) {
                                            mTimer.cancel();
                                        }
                                        stopWenzhen();
                                    }
                                }.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else if (recodeStatus == 2 || recodeStatus == 5) {
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                            inputBorder.setVisibility(View.VISIBLE);

                            acceptLayout.setVisibility(View.GONE);
                            status.setText("已结束");
                            if (list.size() > 0) {
                                AllRecordForDoctorEntity allRecordForDoctorEntity = list.get(0);
                                SQLiteUtils.getInstance().deleteImData(allRecordForDoctorEntity);
                            }
                            acceptTime.setVisibility(View.GONE);
                            if (mTimer != null) {
                                mTimer.cancel();
                            }

                        }
                        EventBus.getDefault().post(new UpdateImMessageEntity(null));
                    } else {
                        toast("接诊失败");
                    }
                    //  mVideoInterrogationAdapter.setDatalist( entity.getData());
                } else {
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    //变更图文问诊状态
    private void updateConsultRecordStatus(int operatorRole, int operatorId, final int recordStatus, int id, String remark) {
        Map<String, String> map = new HashMap<>();
        map.put("OperatorRole", String.valueOf(operatorRole));
        map.put("OperatorId", String.valueOf(operatorId));
        map.put("Status", String.valueOf(recordStatus));
        map.put("Id", String.valueOf(id));
        if (remark != null) {
            map.put("Remark", remark);
        }
        loading(true);
        Log.i("ChatActivity", "operatorId=" + operatorId + ",id=" + id);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.UpdateConsultRecordStatus).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                LoadDialog.clear();
                if (entity.getCode() == 0) {
                    boolean result = (boolean) entity.getData();
                    if (result) {
                        //  EventBus.getDefault().post(new UpdateConsultViewEntity1(true));
                        List<AllRecordForDoctorEntity> list = SQLiteUtils.getInstance().selectImDataByServiceCodeAndId(mServiceCode, mIdentifier);
                        if (recordStatus == 1) {

                            if (list.size() > 0) {
                                AllRecordForDoctorEntity allRecordForDoctorEntity = list.get(0);
                                allRecordForDoctorEntity.setStatus(1);
                                Date date = new Date(System.currentTimeMillis());
                                String dateTime = mFormatter.format(date);
                                allRecordForDoctorEntity.setStartTime(dateTime);
                                allRecordForDoctorEntity.setDealWithTime(dateTime);
                                SQLiteUtils.getInstance().updateImData(allRecordForDoctorEntity);
                            }
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));
                            inputBorder.setVisibility(View.VISIBLE);
                            acceptLayout.setVisibility(View.GONE);
                            morePanel.setVisibility(View.GONE);
                            acceptTime.setVisibility(View.VISIBLE);
                            status.setText("接诊中");
                            if (mTimer != null) {
                                mTimer.cancel();
                            }
                            try {
                                mTimer = new CountDownTimer(VALID_TIME, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        String time = TimeUtil.formatTime(millisUntilFinished);
                                        acceptTime.setText("问诊时间还剩" + time);
                                        Log.d("chat", "定时器3" + time);
                                    }

                                    @Override
                                    public void onFinish() {
                                        // update(2, SpUtils.getInt(Contants.Id, 0), 5, mInquiryRecordListDataEntity.getId());
                                        status.setText("已结束");

                                        inputBorder.setVisibility(View.VISIBLE);

                                        acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                                        acceptTime.setVisibility(View.GONE);
                                        if (mTimer != null) {
                                            mTimer.cancel();
                                        }

                                        acceptTime.setVisibility(View.GONE);
                                        acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                                        if (mTimer != null) {
                                            mTimer.cancel();
                                        }

                                    }
                                }.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else if (recordStatus == 2 || recordStatus == 5) {

                            inputBorder.setVisibility(View.VISIBLE);

                            acceptLayout.setVisibility(View.GONE);
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                            status.setText("已结束");
                            if (list.size() > 0) {
                                AllRecordForDoctorEntity allRecordForDoctorEntity = list.get(0);
                                SQLiteUtils.getInstance().deleteImData(allRecordForDoctorEntity);
                            }
                            acceptTime.setVisibility(View.GONE);
                            if (mTimer != null) {
                                mTimer.cancel();
                            }

                        }
                        EventBus.getDefault().post(new UpdateImMessageEntity(null));
                    } else {
                        //   toast("操作失败");
                    }
                } else {
                    Log.i("ChatActivity", "error=" + entity.getCode() + ",errormessage=" + entity.getMessage());
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }


    @Override
    public void update(Observable o, Object arg) {

    }

    //发服务包
    public void medicalRecordSelection() {
        try {
            mMedicalTempId = 1;
            final Dialog dialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);//dialog样式
            View view = View.inflate(mContext, R.layout.seledct_group_medical_record_dialog, null);
            dialog.setContentView(view);
            RadioButton medicalRecord1 = view.findViewById(R.id.medical_record1);
            RadioButton medicalRecord2 = view.findViewById(R.id.medical_record2);
            RadioGroup radioGroupGender = view.findViewById(R.id.radioGroup_gender);

            TextView sure = view.findViewById(R.id.sure);
            radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.medical_record1:
                            mMedicalTempId = 1;
                            break;
                        case R.id.medical_record2:
                            mMedicalTempId = 2;
                            break;
                    }
                }
            });
            sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    CustomMessageEntity customMessageEntity = new CustomMessageEntity();
                    customMessageEntity.setTitle(SpUtils.getString(Contants.Name) + "医生提醒您填写一下病历，这对于您的病情很有帮助");
                    customMessageEntity.setIsSystem(false);
                    customMessageEntity.setContent(null);

                    CustomMessageEntity.ExtDataBean extData = new CustomMessageEntity.ExtDataBean();
                    CustomMessageEntity.ExtDataBean.DataBean data = new CustomMessageEntity.ExtDataBean.DataBean();
                    if (mMDTDetailEntity.getSubjectType() != 1) {
                        extData.setType(317);
                    } else {
                        extData.setType(26);
                    }

                    data.setMedicalTempId(mMedicalTempId);
                    data.setSubjectBuyRecordId(mMDTDetailEntity.getSubjectBuyRecordId() + "");
                    extData.setData(data);
                    customMessageEntity.setExtData(extData);
                    String json = new Gson().toJson(customMessageEntity);
                    sendCustomMessage(json, "");
                }
            });
            medicalRecord1.setChecked(true);
            dialog.show();
        } catch (Exception e) {
            LogUtils.e("LoadDialog  error!!!");
        }
    }

    /* private void medicalRecordSelection() {

         final String items[] = {"普通病历", "喉癌病历"};
         AlertDialog.Builder builder = new AlertDialog.Builder(this,2);
         builder.setTitle("病历选择");
         builder.setIcon(R.mipmap.ic_launcher);
         builder.setSingleChoiceItems(items, mMedicalTempId,
                 new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         mMedicalTempId = which+1;

                     }
                 });
         builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();
                 CustomMessageEntity customMessageEntity = new CustomMessageEntity();
                 customMessageEntity.setTitle(SpUtils.getString(Contants.Name)+"医生提醒您填写一下病历，这对于您的病情很有帮助");
                 customMessageEntity.setIsSystem(false);
                 customMessageEntity.setContent(null);
                 CustomMessageEntity.ExtDataBean extData = new CustomMessageEntity.ExtDataBean();
                 CustomMessageEntity.ExtDataBean.DataBean data = new   CustomMessageEntity.ExtDataBean.DataBean();
                 extData.setType(26);
                 data.setMedicalTempId(mMedicalTempId);
                 extData.setData(data);
                 customMessageEntity.setExtData(extData);
                 String json = new Gson().toJson(customMessageEntity);
                 sendCustomMessage(json,"");
             }
         });
         builder.create().show();
     }
 */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(HangUpTipEntity HangUpTipEntity) {
        int action = HangUpTipEntity.action;
        long time = HangUpTipEntity.time;
        sendVideoMessage(action, time);
        if (mTimer != null) {
            Log.i("ChatActivity", "stay here");
            mTimer.cancel();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventRecent event) {
        Log.d(TAG, "收到新消息" + event.getmType());
        switch (event.getmType()) {
            case MainConstant.ChangeGroupName:
                String newname = event.getContent();
                mName = newname;
                topTitle.setText(newname);
                break;

            case MainConstant.UpdateOrderStatus:
                getIMRelationRecord();
                break;
        }
    }


    private void rejectOpration() {
        final Dialog dialog = new Dialog(this, R.style.ActionSheetDialogStyle);//dialog样式
        View view = View.inflate(this, R.layout.reject_diagnose, null);
        dialog.setContentView(view);
        TextView sure = view.findViewById(R.id.sure);
        TextView cancel = view.findViewById(R.id.cancel);
        final EditText rejectReson = view.findViewById(R.id.remark);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rejection = rejectReson.getText().toString();
                if (!rejection.equals("")) {

                    refuseWenzhen(rejection);

                    dialog.dismiss();
                } else {
                    toast("拒诊理由不能为空！");
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void rejectMDTOpration() {
        final Dialog dialog = new Dialog(this, R.style.ActionSheetDialogStyle);//dialog样式
        View view = View.inflate(this, R.layout.reject_mdt_diagnose, null);
        dialog.setContentView(view);
        TextView sure = view.findViewById(R.id.sure);
        TextView cancel = view.findViewById(R.id.cancel);
        final EditText rejectReson = view.findViewById(R.id.remark);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rejection = rejectReson.getText().toString();
                acceptMDTwenzhen(2);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //拒绝问诊
    private void refuseWenzhen(String rejection) {
        if (mAction != null && mAction.equals("videoInterrogation")) {
            updateInquiryRecordStatus(2, SpUtils.getInt(Contants.Id, 0), 2, mAllRecordForDoctorEntity.getRecordId(), rejection);
        } else if (mAction != null && mAction.equals("videoInterrogationfromhhome")) {
            if (mAllRecordForDoctorEntity != null) {
                updateInquiryRecordStatus(2, SpUtils.getInt(Contants.Id, 0), 2, mAllRecordForDoctorEntity.getRecordId(), rejection);
            } else {
                updateInquiryRecordStatus(2, SpUtils.getInt(Contants.Id, 0), 2, Integer.valueOf(mInquiryRecordListDataEntity.getRecordId()), rejection);
            }
        } else if (mAction != null && mAction.equals("onlineRenewal")) {
            updatePrescriptionApplyStatus(2, SpUtils.getInt(Contants.Id, 0), 2, mAllRecordForDoctorEntity.getRecordId(), rejection);
        } else if (mAction != null && mAction.equals("onlineRenewalfromHome")) {
            if (mAllRecordForDoctorEntity != null) {
                updatePrescriptionApplyStatus(2, SpUtils.getInt(Contants.Id, 0), 2, mAllRecordForDoctorEntity.getRecordId(), rejection);
            } else {
                updatePrescriptionApplyStatus(2, SpUtils.getInt(Contants.Id, 0), 2, Integer.valueOf(mOnlineRenewalDataEntity.getRecordId()), rejection);
            }
        } else if (mAction != null && mAction.equals("pictureConsult")) {
            updateConsultRecordStatus(2, SpUtils.getInt(Contants.Id, 0), 2, mAllRecordForDoctorEntity.getRecordId(), rejection);
        } else if (mAction != null && mAction.equals("pictureConsultfromhome")) {
            if (mAllRecordForDoctorEntity != null) {
                updateConsultRecordStatus(2, SpUtils.getInt(Contants.Id, 0), 2, mAllRecordForDoctorEntity.getRecordId(), rejection);

            } else {
                updateConsultRecordStatus(2, SpUtils.getInt(Contants.Id, 0), 2, Integer.valueOf(mConsultMessageEntity.getRecordId()), rejection);

            }
        }
    }

    private void acceptWenzhen() {
        if (mAction != null && mAction.equals("videoInterrogation")) {
            updateInquiryRecordStatus(2, SpUtils.getInt(Contants.Id, 0), 1, mAllRecordForDoctorEntity.getRecordId(), null);
        } else if (mAction != null && mAction.equals("videoInterrogationfromhhome")) {
            if (mAllRecordForDoctorEntity != null) {
                updateInquiryRecordStatus(2, SpUtils.getInt(Contants.Id, 0), 1, mAllRecordForDoctorEntity.getRecordId(), null);
            } else {
                updateInquiryRecordStatus(2, SpUtils.getInt(Contants.Id, 0), 1, Integer.valueOf(mInquiryRecordListDataEntity.getRecordId()), null);
            }
        } else if (mAction != null && mAction.equals("onlineRenewal")) {
            updatePrescriptionApplyStatus(2, SpUtils.getInt(Contants.Id, 0), 1, mAllRecordForDoctorEntity.getRecordId(), null);

        } else if (mAction != null && mAction.equals("onlineRenewalfromHome")) {
            if (mAllRecordForDoctorEntity != null) {
                updatePrescriptionApplyStatus(2, SpUtils.getInt(Contants.Id, 0), 1, mAllRecordForDoctorEntity.getRecordId(), null);
            } else {
                updatePrescriptionApplyStatus(2, SpUtils.getInt(Contants.Id, 0), 1, Integer.valueOf(mOnlineRenewalDataEntity.getRecordId()), null);
            }
        } else if (mAction != null && mAction.equals("pictureConsult")) {
            updateConsultRecordStatus(2, SpUtils.getInt(Contants.Id, 0), 1, mAllRecordForDoctorEntity.getRecordId(), null);
        } else if (mAction != null && mAction.equals("pictureConsultfromhome")) {
            if (mAllRecordForDoctorEntity != null) {
                updateConsultRecordStatus(2, SpUtils.getInt(Contants.Id, 0), 1, mAllRecordForDoctorEntity.getRecordId(), null);
            } else {
                updateConsultRecordStatus(2, SpUtils.getInt(Contants.Id, 0), 1, Integer.valueOf(mConsultMessageEntity.getRecordId()), null);
            }
        } else if (mAction != null && mAction.equals("MDT")) {
            // toast("MDT接诊");
            acceptMDTwenzhen(1);
        }


    }

    //诊疗状态(1-接诊，2-拒诊)
    private void acceptMDTwenzhen(int SubjectStatus) {

        Map<String, String> map = new HashMap<>();
        map.put("SubjectBuyRecordId", mMDTDetailEntity.getSubjectBuyRecordId() + "");
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        map.put("DrName", mMDTDetailEntity.getDrName());
        map.put("SubjectStatus", SubjectStatus + "");
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.ReceiveOrRejecteMDT).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                LoadDialog.clear();
                if (entity.getCode() == 0) {
                    try {
                        boolean isAccept = (boolean) entity.getData();
                        //更新im状态
                        getIMRelationRecord();

                    } catch (Exception e) {

                    }
                }


            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }


    public boolean checkReadPermission(String string_permission, int request_code) {
        boolean flag = false;
        if (ContextCompat.checkSelfPermission(this, string_permission) == PackageManager.PERMISSION_GRANTED) {//已有权限
            flag = true;
        } else {//申请权限
            ActivityCompat.requestPermissions(this, new String[]{string_permission}, request_code);
        }
        return flag;
    }

    private void stopWenzhen() {

        if (mAction != null && mAction.equals("videoInterrogation")) {
            updateInquiryRecordStatus(2, SpUtils.getInt(Contants.Id, 0), 5, mAllRecordForDoctorEntity.getRecordId(), null);
        } else if (mAction != null && mAction.equals("videoInterrogationfromhhome")) {
            updateInquiryRecordStatus(2, SpUtils.getInt(Contants.Id, 0), 5, Integer.valueOf(mInquiryRecordListDataEntity.getRecordId()), null);
        } else if (mAction != null && mAction.equals("onlineRenewal")) {
            updatePrescriptionApplyStatus(2, SpUtils.getInt(Contants.Id, 0), 5, mAllRecordForDoctorEntity.getRecordId(), null);
        } else if (mAction != null && mAction.equals("onlineRenewalfromHome")) {
            updatePrescriptionApplyStatus(2, SpUtils.getInt(Contants.Id, 0), 5, Integer.valueOf(mOnlineRenewalDataEntity.getRecordId()), null);

        } else if (mAction != null && mAction.equals("pictureConsult")) {
            if (mAllRecordForDoctorEntity != null) {
                updateConsultRecordStatus(2, SpUtils.getInt(Contants.Id, 0), 5, mAllRecordForDoctorEntity.getRecordId(), null);
            }
        } else if (mAction != null && mAction.equals("pictureConsultfromhome")) {
            updateConsultRecordStatus(2, SpUtils.getInt(Contants.Id, 0), 5, Integer.valueOf(mConsultMessageEntity.getRecordId()), null);

        } else if (mAction != null && mAction.equals("MDT")) {
            stopMDTwenzhen();
        }
    }

    private void stopMDTwenzhen() {

        loading(true);
        Map<String, String> headMap = new HashMap<>();
        CloseMDTEntity closeMDTEntity = new CloseMDTEntity();
        List<Integer> list = new ArrayList();
        list.add(mMDTDetailEntity.getSubjectBuyRecordId());
        closeMDTEntity.setSubjectBuyRecordIds(list);
        String json = mGson.toJson(closeMDTEntity);
        loading(true);
        headMap.put("Authorization", SpUtils.getString(Contants.Token));

        mMyOkhttp.post().url(HttpUrl.CloseMDT).headers(headMap).jsonParams(json).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity response) {
                LoadDialog.clear();
                if (response.getCode() == 0) {
                    getIMRelationRecord();
                } else {
                    toast(response.getMessage());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                LoadDialog.clear();
            }
        });

    }

    private void alertDialog(final String action) {
        final Dialog dialog = new Dialog(this, R.style.ActionSheetDialogStyle);//dialog样式
        View view = View.inflate(this, R.layout.alert_dialog, null);
        dialog.setContentView(view);
        Button sure = view.findViewById(R.id.btn_positive_custom_dialog);
        Button cancel = view.findViewById(R.id.btn_negative_custom_dialog);
        TextView titleText = view.findViewById(R.id.tv_title_custom_dialog);
        TextView contentText = view.findViewById(R.id.tv_message_custom_dialog);
        if (action.equals("plus")) {
            titleText.setText("加号提醒");
            contentText.setText("您确定要给该患者加号吗");
        } else if (action.equals("end")) {
            titleText.setText("结束问诊提醒");
            contentText.setText("你确定要结束问诊吗");
        } else if (action.equals("prescription")) {
            titleText.setText("处方权限不足提醒");
            contentText.setText("对不起，您目前没有互联网西药处方权，不能开具处方，如有疑问，请联系客服，取消，联系客服");
        } else if (action.equals("zhongyaoprescription")) {
            titleText.setText("处方权限不足提醒");
            contentText.setText("对不起，您目前没有互联网中药处方权，不能开具处方，如有疑问，请联系客服，取消，联系客服");
        } else if (action.equals("video")) {
            titleText.setText("提示");
            contentText.setText("当前服务无法使用视频通话功能！");
        }
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (action.equals("plus")) {
                    plusSign();

                } else if (action.equals("end")) {
                    stopWenzhen();
                } else if (action.equals("prescription")) {
                    obtainAboutInfo();
                }
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateMessageListEntity updateMessageListEntity) {
        List<TIMMessage> list = updateMessageListEntity.list;
        List<MyTIMMessage> chatList = new ArrayList<>();
        for (TIMMessage timMessage : list) {

            if (mIdentifier.equals(timMessage.getConversation().getPeer())) {
                MyTIMMessage myTIMMessage = new MyTIMMessage();
                myTIMMessage.setTimMessage(timMessage);
                chatList.add(myTIMMessage);
            }
            Log.i("zdp", "22222222" + timMessage.toString());
            if (timMessage.getElement(0).getType() == TIMElemType.Custom) {

                TIMCustomElem customElem = (TIMCustomElem) timMessage.getElement(0);
                String s = new String(customElem.getData());
                CustomMessageEntity CustomMessageEntity = mGson.fromJson(s, CustomMessageEntity.class);
                CustomMessageEntity.ExtDataBean extraData = CustomMessageEntity.getExtData();
                if (extraData == null) {
                    return;
                }
                Log.i("zdp", "数据内容" + extraData.toString());
                if (extraData != null) {
                    int type = extraData.getType();
                    if (!timMessage.isSelf()) {
                        if (type > 100) {
                            switch (type) {
                                case 129:
                                    int callId = extraData.getData().getAVRoomID();
                                    String sender = extraData.getData().getTargets();
                                    String userSig = extraData.getData().getUserSig();
                                    newIncomingCall(callId, sender, userSig);
                                    break;
                                case 131:
                                    EventBus.getDefault().post(new RejectCallTip(true));
                                    if (null != mIncomingDlg) {  // 关闭遗留来电对话框
                                        mIncomingDlg.dismiss();
                                    }
                                    if (mTimer != null) {
                                        mTimer.cancel();
                                    }
                                    break;
                                case 134:
                                    EventBus.getDefault().post(new RejectCallTip(true));
                                    if (null != mIncomingDlg) {  // 关闭遗留来电对话框
                                        mIncomingDlg.dismiss();
                                    }
                                    if (mTimer != null) {
                                        mTimer.cancel();
                                    }
                                    break;
                                case 132:
                                    EventBus.getDefault().post(new RejectCallTip(true));
                                    if (null != mIncomingDlg) {  // 关闭遗留来电对话框
                                        mIncomingDlg.dismiss();
                                    }
                                    if (mTimer != null) {
                                        mTimer.cancel();
                                    }
                                    break;
                                case 133:
                                    EventBus.getDefault().post(new RejectCallTip(true));
                                    if (null != mIncomingDlg) {  // 关闭遗留来电对话框
                                        mIncomingDlg.dismiss();
                                    }
                                    if (mTimer != null) {
                                        mTimer.cancel();
                                    }
                                    break;
                                case 130:
                                    if (mTimer != null) {
                                        mTimer.cancel();
                                    }
                                    break;

                            }
                        }
                    }
                    if (type == 36) {
                        Log.i("ChatActivity", "Type=" + type);
                        com.newdjk.doctor.ui.entity.CustomMessageEntity.ExtDataBean.DataBean dataBean = extraData.getData();
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
                            if (allRecordForDoctorList.size() > 0) {
                                AllRecordForDoctorEntity AllRecordForDoctorEntity = allRecordForDoctorList.get(0);
                                SQLiteUtils.getInstance().deleteImData(AllRecordForDoctorEntity);
                            }
                        }
                        EventBus.getDefault().post(new UpdateImMessageEntity(null));
                        if (mStatus != 0) {
                            inputBorder.setVisibility(View.VISIBLE);
                            acceptLayout.setVisibility(View.GONE);
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                            status.setText("已结束");
                            acceptTime.setVisibility(View.GONE);
                            if (mTimer != null) {
                                mTimer.cancel();
                            }
                        }
                    } else if (type == 38) {
                        EventBus.getDefault().post(new UpdateAllRecordForDoctorEntity(mIdentifier));

                    }

                }

            }
        }
        if (chatList.size() == 0) {

        } else {
            getAdapterData().addAll(0, chatList);
            updateRecyclerView();
        }

    }

    //视频业务状态变更
    private void GetChatHistoryByPage(String doctorId, String accountId, int msgTimestampStart, int pageSize, final boolean isFirstData, final int imListSize) {

        Log.i("ChatActivity", "doctorId=" + doctorId + ",accountId=" + accountId + ",msgTimestampStart=" + msgTimestampStart);
        Map<String, String> map = new HashMap<>();
        map.put("MsgTimestampStart", String.valueOf(msgTimestampStart));
        map.put("GroupId", mIdentifier);
        map.put("PageSize", String.valueOf(pageSize));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetGroupChatHistoryByPage).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ChatHistoryDataEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ChatHistoryDataEntity> entity) {

                if (entity.getCode() == 0) {
                    ChatHistoryDataEntity chatHistoryDataEntity = entity.getData();
                    List<ChatHistoryEntity> list = chatHistoryDataEntity.getReturnData();
                    if (list.size() == 0) {
                        mIsServerReflashComplete = true;
                        if (!isFirstData) {
                            toast("没有更多数据");
                        }
                    }
                    for (int i = 0; i < list.size(); i++) {
                        long id = list.get(i).getId();
                        String msgBody = list.get(i).getMsgBody();
                        Type jsonType = new TypeToken<List<MsgBodyEntity>>() {
                        }.getType();
                        try {
                            List<MsgBodyEntity> msgBodyList = mGson.fromJson(msgBody, jsonType);
                            if (msgBodyList != null) {
                                String MsgType = msgBodyList.get(0).getMsgType();
                                MsgContentEntity msgContentEntity = msgBodyList.get(0).getMsgContent();
                                if (MsgType != null) {
                                    if (MsgType.equals("TIMTextElem")) {
                                        TIMMessage timMessage = new TIMMessage();
                                        TIMTextElem timTextElem = new TIMTextElem();
                                        timTextElem.setText(msgContentEntity.getText());
                                        timMessage.addElement(timTextElem);
                                        MyTIMMessage myTIMMessage = new MyTIMMessage();
                                        myTIMMessage.setTimMessage(timMessage);
                                        myTIMMessage.setLocalMessage(true);
                                        myTIMMessage.setMsgTimestamp(list.get(i).getMsgTimestamp());
                                        myTIMMessage.setSendTarget(list.get(i).getFrom_Account());
                                        getAdapterData().add(mAdapter.getItemCount(), myTIMMessage);

                                    } else if (MsgType.equals("TIMCustomElem")) {
                                        TIMMessage timMessage = new TIMMessage();
                                        TIMCustomElem elem = new TIMCustomElem();
                                        elem.setData(msgContentEntity.getData().getBytes());      //自定义 byte[]
                                        elem.setDesc(msgContentEntity.getDesc()); //自定义描述信息
                                        if (timMessage.addElement(elem) != 0) {
                                            Log.d("zdp", "addElement failed");
                                            return;
                                        }

                                        //  timMessage.addElement(elem);
                                        MyTIMMessage myTIMMessage = new MyTIMMessage();
                                        myTIMMessage.setTimMessage(timMessage);
                                        myTIMMessage.setLocalMessage(true);
                                        myTIMMessage.setMsgTimestamp(list.get(i).getMsgTimestamp());
                                        myTIMMessage.setSendTarget(list.get(i).getFrom_Account());
                                        getAdapterData().add(mAdapter.getItemCount(), myTIMMessage);
                                        //  updateRecyclerView();
                                    } else if (MsgType.equals("TIMSoundElem")) {
                                        String url = msgContentEntity.getFileUrl();
                                        String uuid = msgContentEntity.getUUID();
                                        if (url != null && uuid != null) {
                                            String content = mGson.toJson(msgContentEntity);
                                            TIMMessage timMessage = new TIMMessage();
                                            TIMCustomElem elem = new TIMCustomElem();
                                            elem.setData(content.getBytes());      //自定义 byte[]
                                            elem.setDesc("TIMSoundElem"); //自定义描述信息
                                            if (timMessage.addElement(elem) != 0) {
                                                Log.d("zdp", "addElement failed");
                                                return;
                                            }

                                            //  timMessage.addElement(elem);
                                            MyTIMMessage myTIMMessage = new MyTIMMessage();
                                            myTIMMessage.setTimMessage(timMessage);
                                            myTIMMessage.setLocalMessage(true);
                                            myTIMMessage.setMsgTimestamp(list.get(i).getMsgTimestamp());
                                            myTIMMessage.setSendTarget(list.get(i).getFrom_Account());
                                            getAdapterData().add(mAdapter.getItemCount(), myTIMMessage);
                                        }
                                        //  downFile(msgContentEntity.getFileUrl(), msgContentEntity.getUUID());
                                    } else if (MsgType.equals("TIMImageElem")) {
                                        List<ImageInfoArrayEntity> imageInfoArray = msgContentEntity.getImageInfoArray();
                                        String content = mGson.toJson(imageInfoArray);
                                        Log.i("ChatActivity", "content=" + content);
                                        TIMMessage timMessage = new TIMMessage();
                                        TIMCustomElem elem = new TIMCustomElem();
                                        elem.setData(content.getBytes());      //自定义 byte[]
                                        elem.setDesc("TIMImageElem"); //自定义描述信息
                                        if (timMessage.addElement(elem) != 0) {
                                            Log.d("zdp", "addElement failed");
                                            return;
                                        }

                                        //  timMessage.addElement(elem);
                                        MyTIMMessage myTIMMessage = new MyTIMMessage();
                                        myTIMMessage.setTimMessage(timMessage);
                                        myTIMMessage.setLocalMessage(true);
                                        myTIMMessage.setMsgTimestamp(list.get(i).getMsgTimestamp());
                                        myTIMMessage.setSendTarget(list.get(i).getFrom_Account());
                                        getAdapterData().add(mAdapter.getItemCount(), myTIMMessage);
                                        //  downFile(msgContentEntity.getFileUrl(), msgContentEntity.getUUID());
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Log.d("haha", msgBody.toString());
                        }
                           /* List<ChatHistoryEntity> list1 = SQLiteUtils.getInstance().selectHistoryDataById(id);
                            if (list1.size() == 0) {
                                SQLiteUtils.getInstance().addHistoryData(list.get(i));
                            }*/
                    }
                    mAdapter.notifyDataSetChanged();
                    if (!isFirstData) {
                        LinearLayoutManager mLayoutManager = (LinearLayoutManager) chatRecyclerView.getLayoutManager();
                        mLayoutManager.scrollToPositionWithOffset(list.size() + imListSize, 0);
                    } else if (mAdapter.getItemCount() > 0) {
                        mTIMMessage = mTimMessages.get(mTimMessages.size() - 1).getTimMessage();
                        chatRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                    }
                    //          List<ChatHistoryEntity> list2 = SQLiteUtils.getInstance().selectHistoryData();
                    //          Log.i("ChatActivity", "list2=" + list2.size());
                } else {
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
        //  }
    }

    private void obtainAboutInfo() {
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetAboutInfo).headers(headMap).tag(this).enqueue(new GsonResponseHandler<AboutUsEntity>() {
            @Override
            public void onSuccess(int statusCode, AboutUsEntity response) {
                LoadDialog.clear();
                if (response.getCode() == 0) {
                    call("tel:" + response.getData().getMobile());
                } else {
                    toast(response.getMessage());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                LoadDialog.clear();
            }
        });
    }

    private void getCurrentTimeToUpdateCountTime() {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(HttpUrl.getCurrentTime).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity response) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = response.getData().toString();
                try {
                    Date date = format.parse(time);
                    mNowTime = date.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                acceptLayout.setVisibility(View.VISIBLE);
                inputBorder.setVisibility(View.GONE);
                acceptTip.setVisibility(View.VISIBLE);
                acceptTip.setBackgroundColor(getResources().getColor(R.color.huang));
                Log.d(TAG, "" + mAllRecordForDoctorEntity.toString());
                String date = "";
                if (TextUtils.isEmpty(mAllRecordForDoctorEntity.getReExaminationDate())) {
                    date = "";
                } else {
                    date = mAllRecordForDoctorEntity.getReExaminationDate().substring(0, mAllRecordForDoctorEntity.getReExaminationDate().indexOf(" "));

                }
                String startTime = "";
                startTime = mAllRecordForDoctorEntity.getReExaminationStartTime();
                if (TextUtils.isEmpty(startTime)) {
                    startTime = "";
                }

                String endTime = "";
                endTime = mAllRecordForDoctorEntity.getReExaminationEndTime();
                if (TextUtils.isEmpty(endTime)) {
                    endTime = "";
                }
                String dateTime = date + " " + startTime;
                final String remainAcceptTime = date + " " + endTime;
                mAcceptTime = date2TimeStamp(dateTime, "yyyy-MM-dd HH:mm:ss");
                mAcceptLastTime = date2TimeStamp(remainAcceptTime, "yyyy-MM-dd HH:mm:ss");
                remainingTime = mAcceptTime - mNowTime;

                if (remainingTime > 0) {
                    countTimeBorder.setVisibility(View.GONE);
                    acceptTip.setBackgroundColor(getResources().getColor(R.color.huang));
                    acceptBorder.setVisibility(View.VISIBLE);
                    inputBorder.setVisibility(View.GONE);
                    accept.setVisibility(View.GONE);
                    reject.setVisibility(View.VISIBLE);
                    mTimer = new CountDownTimer(remainingTime, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            String time = TimeUtil.formatTime(millisUntilFinished);
                            acceptValidTime.setText("距离预约时间还剩余" + time);
                        }

                        @Override
                        public void onFinish() {

                            accept.setVisibility(View.VISIBLE);
                            reject.setVisibility(View.VISIBLE);
                            mTimer = new CountDownTimer(mAcceptLastTime - mAcceptTime, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    String time = TimeUtil.formatTime(millisUntilFinished);
                                    acceptValidTime.setText("接诊时间剩余：" + time);

                                }

                                @Override
                                public void onFinish() {
                                    mTimer.cancel();
                                    acceptValidTime.setVisibility(View.GONE);
                                }
                            }.start();
                        }
                    }.start();
                } else {
                    inputBorder.setVisibility(View.VISIBLE);
                    accept.setVisibility(View.VISIBLE);
                    reject.setVisibility(View.VISIBLE);
                    mTimer = new CountDownTimer(mAcceptLastTime - mNowTime, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            String time = TimeUtil.formatTime(millisUntilFinished);
                            acceptValidTime.setText("接诊时间剩余：" + time);

                        }

                        @Override
                        public void onFinish() {
                            if (mTimer != null) {
                                mTimer.cancel();
                            }
                            acceptValidTime.setText("接诊时间剩余：00:00:00");
                                   /* inputBorder.setVisibility(View.GONE);
                                    acceptLayout.setVisibility(View.VISIBLE);
                                    countTimeBorder.setVisibility(View.GONE);
                                    acceptBorder.setVisibility(View.GONE);
                                    rejectTip.setVisibility(View.VISIBLE);
                                    acceptTip.setVisibility(View.GONE);*/
                        }
                    }.start();

                }
                status.setText("待问诊");
                acceptTime.setText("预约时间： " + date + " " + startTime + "-" + endTime);

            }


            @Override
            public void onFailures(int statusCode, String errorMsg) {

            }
        });


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateImStatusEntity userEvent) {
        Log.d("推送消息", userEvent.allRecordForDoctorEntity.toString());
        if (mStatus != 0) {
            mAllRecordForDoctorEntity = userEvent.allRecordForDoctorEntity;
            String serviceCode = mAllRecordForDoctorEntity.getServiceCode();
            String imId = mAllRecordForDoctorEntity.getApplicantIMId();
            mServiceCode = serviceCode;
            Log.d("推送消息", serviceCode + "  " + mServiceCode + "   " + imId + "   " + mIdentifier + "  " + mStatus);

            //1101|图文问诊|1.00|0.00,1102|视频问诊|6.00|0.01,1103|名医续方|10.00|0.01,1110|
            if (serviceCode != null && imId != null && imId.equals(mIdentifier)) {
                if (serviceCode.equals("1101")) {
                    mAction = "pictureConsult";
                } else if (serviceCode.equals("1102")) {
                    mAction = "videoInterrogation";
                } else if (serviceCode.equals("1103")) {
                    mAction = "onlineRenewal";
                } else if (serviceCode.equals("0")) {
                }

                if (mServiceCode.equals("1102")) {
                    getCurrentTimeToUpdateCountTime();
                }

                //更新业务状态
                if (mAllRecordForDoctorEntity != null) {
                    Log.d("推送消息", "走这里1");
                    SpUtils.put(Contants.patientName, mAllRecordForDoctorEntity.getPatientName());
                    SpUtils.put(Contants.patientID, mAllRecordForDoctorEntity.getPatientId());
                    mImagePath = mAllRecordForDoctorEntity.getApplicantHeadImgUrl();
                    // acceptValidTime.setVisibility(View.GONE);
                    int type = mAllRecordForDoctorEntity.getStatus();
                    if (type == 0) {

                        Log.d("推送消息", "走这里2");
                        status.setText("待接诊");
                        acceptTip.setBackgroundColor(getResources().getColor(R.color.huang));
                        acceptLayout.setVisibility(View.VISIBLE);
                        acceptBorder.setVisibility(View.VISIBLE);

                        inputBorder.setVisibility(View.GONE);

                        validTime = mAllRecordForDoctorEntity.getPayTime();

                        mAcceptTime = date2TimeStamp(validTime, "yyyy-MM-dd HH:mm:ss");

                        Log.d("推送消息", "倒计时开始时间" + (VALID_TIME - mNowTime - mAcceptTime) + "");
                        //  Long comedowntime = VALID_TIME - mNowTime + mAcceptTime;
                        Long comedowntime = VALID_TIME;
                        mTimer = null;
                        mTimer = new CountDownTimer(comedowntime, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                String time = TimeUtil.formatTime(millisUntilFinished);
                                acceptValidTime.setText("接诊剩余时间：" + time);

                            }

                            @Override
                            public void onFinish() {
                                if (mTimer != null) {
                                    mTimer.cancel();
                                }
                                acceptValidTime.setText("接诊剩余时间：00:00");
                            }
                        }.start();
                    } else if (type == 1) {
                        inputBorder.setVisibility(View.VISIBLE);
                        acceptLayout.setVisibility(View.GONE);
                        acceptTime.setVisibility(View.VISIBLE);
                        status.setText("接诊中");
                        acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));
                        validTime = mAllRecordForDoctorEntity.getStartTime();
                        if (mAction.equals("videoInterrogation")) {
                            cancallVideo = true;
                        }
                        mAcceptTime = date2TimeStamp(validTime, "yyyy-MM-dd HH:mm:ss");
                        if (mAcceptTime <= 0) {
                            Log.d("推送消息", "定时器6" + mAcceptTime);
                            return;
                        }

                        mNowTime = date2TimeStamp(mAllRecordForDoctorEntity.getDealWithTime(), "yyyy-MM-dd HH:mm:ss");

                        Log.d("推送消息", "倒计时时间" + (VALID_TIME - mNowTime + mAcceptTime));
                        mTimer = null;
                        mTimer = new CountDownTimer(VALID_TIME - mNowTime + mAcceptTime, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                String time = TimeUtil.formatTime(millisUntilFinished);
                                acceptTime.setText("问诊时间还剩" + time);
                                Log.d("推送消息", "定时器6" + time);
                            }

                            @Override
                            public void onFinish() {
                                Log.d("推送消息", "定时器7" + "已结束");
                                // update(2, SpUtils.getInt(Contants.Id, 0), 5, mInquiryRecordListDataEntity.getId());
                                status.setText("已结束");
                                if (mAction.equals("videoInterrogation")) {
                                    cancallVideo = false;
                                }
                                inputBorder.setVisibility(View.VISIBLE);

                                acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                                acceptTime.setVisibility(View.GONE);
                                if (mTimer != null) {
                                    mTimer.cancel();
                                }
                                stopWenzhen();

                            }
                        }.start();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }


                    } else {
                        acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                        status.setText("已结束");
                        if (mAction.equals("videoInterrogation")) {
                            cancallVideo = false;
                        }
                        inputBorder.setVisibility(View.VISIBLE);

                        acceptLayout.setVisibility(View.GONE);
                    }
                }
//                else {
//
//                    status.setText("未接诊");
//                    acceptTip.setBackgroundColor(getResources().getColor(R.color.huang));
//                    acceptLayout.setVisibility(View.VISIBLE);
//                    acceptBorder.setVisibility(View.VISIBLE);
//
//                    inputBorder.setVisibility(View.GONE);
//
//                    //remainingTime = VALID_TIME - mNowTime;
//                    mTimer = new CountDownTimer(VALID_TIME, 1000) {
//                        @Override
//                        public void onTick(long millisUntilFinished) {
//                            String time = TimeUtil.formatTime(millisUntilFinished);
//                            acceptValidTime.setText("接诊剩余时间：" + time);
//
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            // update(2, SpUtils.getInt(Contants.Id, 0), 5, mInquiryRecordListDataEntity.getId());
//                            acceptValidTime.setText("接诊剩余时间：00:00");
//
//                        }
//                    }.start();
//                }
            }
        }
    }

    private void loginSDK(final String id, final String userSig) {
        ILiveLoginManager.getInstance().iLiveLogin(id, userSig, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                TIMOfflinePushToken param = new TIMOfflinePushToken();
                param.setToken(MyApplication.mRegId);
                param.setBussid(MyApplication.busid);
                TIMManager.getInstance().setOfflinePushToken(param, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.i("ChatActivity", "errorCode=" + i + ",s=" + s);
                    }

                    @Override
                    public void onSuccess() {
                        Log.i("ChatActivity", "onSuccess=");
                    }
                });
               /* getFriendsList();
                refreshView();*/
                //设置用户状态变更监听器，在回调中进行相应的处理
                GetAllRecordForDoctor(null);
                TIMManager.getInstance().setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {
                        Log.i("LogOutUtil", ILiveLoginManager.getInstance().getMyUserId() + "    " + TIMManager.getInstance().getLoginUser());
                        //  EventBus.getDefault().post(new NoticeLoginEntity(true));
                        Logout("当前账号在其它设备登录，请重新登录");

                    }

                    @Override
                    public void onUserSigExpired() {
                        Logout("当前账号凭证已过期，请重新登录");

                    }
                });
                Log.i("LogOutUtil", ILiveLoginManager.getInstance().getMyUserId() + "    " + TIMManager.getInstance().getLoginUser());
                CommonMethod.mIsCanStartService = true;
                initMessage(mTIMMessage);
                //   startService(new Intent(MainActivity.this, MyService.class));
                String identifier = TIMManager.getInstance().getLoginUser();
                if (identifier != null && !identifier.equals("")) {
                    SpUtils.put(Contants.identifier, id);
                    EventBus.getDefault().post(new UpdateViewEntity(null));
                } else {
                    LogOutUtil.getInstance().loginOut(GroupChatActivity.this, false);
                }
                //  Toast.makeText(mContext, ILiveLoginManager.getInstance().getMyUserId(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
              /*  if (errCode == 6208) {
                    loginSDK(id, userSig);
                } else {
                    LogOutUtil.getInstance().loginOut(ChatActivity.this, false);
                    Toast.makeText(mContext, "Login failed:" + module + "|" + errCode + "|" + errMsg, Toast.LENGTH_SHORT).show();
                }*/
                Toast.makeText(mContext, "Login failed:" + module + "|" + errCode + "|" + errMsg, Toast.LENGTH_SHORT).show();
                //  loginSDK(id, userSig);
            }
        });
    }

    private void Logout(final String error) {
        Map<String, String> map = new HashMap<>();
        map.put("Token", SpUtils.getString(Contants.Token));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.Logout).headers(headMap).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entituy) {

                if (entituy.getCode() == 0) {
                    boolean result = (boolean) entituy.getData();
                    if (result) {
                        CommonMethod.mIsCanStartService = false;
                        Toast.makeText(GroupChatActivity.this, error, Toast.LENGTH_SHORT).show();
                        stopService(new Intent(GroupChatActivity.this, MyService.class));
                        LogOutUtil.getInstance().loginOut(GroupChatActivity.this, true);
                        //   EventBus.getDefault().post(new LoginOutEntity(true));
                    }
                } else {
                    toast(entituy.getMessage());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(TestDataEntity testDataEntity) {
        Log.d("chat", "准备发送自定义消息");


    }


    private class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // Log.d(TAG,"数据长度"+listuse.size());
            TabChatFragment homeTabFragment = new TabChatFragment();
            homeTabFragment.setdata(listuse, position);
            homeTabFragment.setonclickListener(new OnTabItemClickListener() {
                @Override
                public void onItemChildClick(AppLicationEntity appLicationEntity) {
                    tabitemclick(appLicationEntity);
                }
            });
            return homeTabFragment;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabChatFragment fragment = (TabChatFragment) super.instantiateItem(container, position);
            fragment.setdata(listuse, position);
            return fragment;
        }


        @Override
        public int getCount() {
            if (listuse.size() <= 8) {
                return 1;
            } else if (listuse.size() > 8 && listuse.size() <= 16) {
                return 2;
            } else {
                return 3;
            }
        }

        @Override
        public int getItemPosition(Object object) {
            //注意：默认是PagerAdapter.POSITION_UNCHANGED，不会重新加载
            return PagerAdapter.POSITION_NONE;
        }

    }


    private void getIMRelationRecord() {

        Map<String, String> map = new HashMap<>();
        map.put("IMGroupId", mIdentifier);

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(HttpUrl.QueryMDTBySubjectBuyIMGroupId).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTDetailEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTDetailEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    mMDTDetailEntity = entity.getData();
                    if (mTimer != null) {
                        mTimer.cancel();
                    }
                    UpdateOrderStatus();
                } else {
                    Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
                LoadDialog.clear();
            }
        });
    }


    private void onFragmentViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_image:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
                break;
            case R.id.btn_photo:
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String f = System.currentTimeMillis() + ".jpg";
                mPicturePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + f;
                File file = new File(mPicturePath);
                file.getParentFile().mkdirs();
                Log.i("zdp", mPicturePath);
                Uri uri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    uri = FileProvider.getUriForFile(mContext, "com.newdjk.doctor.file.provider", file);
                } else {
                    uri = Uri.fromFile(file);
                }
                //添加权限
                openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(openCameraIntent, REQUEST_CODE_TAKE_PICTURE);
                break;
            case R.id.the_quick_reply:
                Intent replyIntent = new Intent(GroupChatActivity.this, ReplyQuickActivity.class);

                startActivityForResult(replyIntent, QUICK_REPLY_CODE);
                break;

            case R.id.add_your_resume:
                medicalRecordSelection();
                break;

            case R.id.interrogation_table:
                Intent consultationIntent = new Intent(GroupChatActivity.this, MissionActivity.class);
                consultationIntent.putExtra("missionMessage", mPrescriptionMessage);
                consultationIntent.putExtra("action", "consultation");
                startActivityForResult(consultationIntent, MISSION_CODE);
                break;

            case R.id.plus_sign:
                mLoginStatus = SpUtils.getInt(Contants.Status, 0);
                if (mLoginStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, GroupChatActivity.this);
                } else {
                    alertDialog("plus");

                    // sendCustomMessage(json,"");
                }
                // sendCustomMessage(json,"");
                break;

            case R.id.mission:
                Intent missionIntent = new Intent(GroupChatActivity.this, MissionActivity.class);
                missionIntent.putExtra("missionMessage", mPrescriptionMessage);
                missionIntent.putExtra("action", "health");
                startActivityForResult(missionIntent, MISSION_CODE);
                break;
            case R.id.end_of_the_interrogation:
                alertDialog("end");

                break;

            case R.id.advice_shop:
                /// toast("推荐商品");
                Intent adviceintent = new Intent(getContext(), PrescriptionActivity.class);
                adviceintent.putExtra("type", 11);
                adviceintent.putExtra("fromIM", 1);
                adviceintent.putExtra("prescriptionMessage", mPrescriptionMessage);
                startActivity(adviceintent);
                break;

            case R.id.video_table:

                if (NetworkUtil.isNetworkAvailable(this)) {

                    if (cancallVideo) {
                        // makeCall(ILVCallConstants.CALL_TYPE_VIDEO, mIdentifier);
                        int callId = ILiveFunc.generateAVCallRoomID();
                        CustomMessageEntity customMessageEntity = new CustomMessageEntity();
                        //   customMessageEntity.setTitle(SpUtils.getString(Contants.Name)+"医生提醒您填写一下病历，这对于您的病情很有帮助");
                        customMessageEntity.setIsSystem(false);
                        customMessageEntity.setContent(null);
                        CustomMessageEntity.ExtDataBean extData = new CustomMessageEntity.ExtDataBean();
                        CustomMessageEntity.ExtDataBean.DataBean data = new CustomMessageEntity.ExtDataBean.DataBean();
                        extData.setType(129);
                        data.setAVRoomID(callId);
                        data.setId(SpUtils.getString(Contants.identifier));
                        data.setTargets(SpUtils.getString(Contants.Name));
                        extData.setData(data);
                        customMessageEntity.setExtData(extData);
                        String json = new Gson().toJson(customMessageEntity);
                        sendCustomMessage(json, "");
                        sendPushVideo(callId);
                        mTimer = new CountDownTimer(30000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                sendVideoMessage(132, -1);
                                EventBus.getDefault().post(new RejectCallTip(true));
                            }
                        }.start();
                        Intent roomIntent = new Intent(GroupChatActivity.this, RoomActivity.class);
                        roomIntent.putExtra("callId", callId);
                        roomIntent.putExtra("identifier", mIdentifier);
                        roomIntent.putExtra("patientiinfo", mAllRecordForDoctorEntity);

                        startActivity(roomIntent);
                    } else {
                        alertDialog("video");
                    }

                } else {
                    toast("网络连接异常，请检查网络连接");
                }
                break;
        }

    }


    private void tabitemclick(AppLicationEntity appLicationEntity) {
        String desc = appLicationEntity.getAppDesc();
        Log.d(TAG, "分级转诊" + desc);

        switch (desc) {
            case "图片":
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);

                break;
            case "拍摄":
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String f = System.currentTimeMillis() + ".jpg";
                mPicturePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + f;
                File file = new File(mPicturePath);
                file.getParentFile().mkdirs();
                Log.i("zdp", mPicturePath);
                Uri uri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    uri = FileProvider.getUriForFile(mContext, "com.newdjk.doctor.file.provider", file);
                } else {
                    uri = Uri.fromFile(file);
                }
                //添加权限
                openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(openCameraIntent, REQUEST_CODE_TAKE_PICTURE);
                break;
            case "快速回复":
                Intent replyIntent = new Intent(GroupChatActivity.this, ReplyQuickActivity.class);

                startActivityForResult(replyIntent, QUICK_REPLY_CODE);
                break;

            case "补充病历":
                Intent addmdtIntent = new Intent(GroupChatActivity.this, AddMDTDocumentActivity.class);
                addmdtIntent.putExtra("MDTDetailEntity", mMDTDetailEntity);
                startActivity(addmdtIntent);
                break;

            case "优选推荐":
                Intent adviceintent = new Intent(getContext(), PrescriptionActivity.class);
                adviceintent.putExtra("type", 11);
                adviceintent.putExtra("fromIM", 1);
                adviceintent.putExtra("prescriptionMessage", mPrescriptionMessage);
                startActivity(adviceintent);
                break;

            case "加号":
                mLoginStatus = SpUtils.getInt(Contants.Status, 0);
                if (mLoginStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, GroupChatActivity.this);
                } else {
                    alertDialog("plus");

                    // sendCustomMessage(json,"");
                }
                break;


            case "视频通话":
                if (NetworkUtil.isNetworkAvailable(this)) {

                    if (cancallVideo) {
                        // makeCall(ILVCallConstants.CALL_TYPE_VIDEO, mIdentifier);
                        int callId = ILiveFunc.generateAVCallRoomID();
                        CustomMessageEntity customMessageEntity = new CustomMessageEntity();
                        //   customMessageEntity.setTitle(SpUtils.getString(Contants.Name)+"医生提醒您填写一下病历，这对于您的病情很有帮助");
                        customMessageEntity.setIsSystem(false);
                        customMessageEntity.setContent(null);
                        CustomMessageEntity.ExtDataBean extData = new CustomMessageEntity.ExtDataBean();
                        CustomMessageEntity.ExtDataBean.DataBean data = new CustomMessageEntity.ExtDataBean.DataBean();
                        extData.setType(129);
                        data.setAVRoomID(callId);
                        data.setId(SpUtils.getString(Contants.identifier));
                        data.setTargets(SpUtils.getString(Contants.Name));
                        extData.setData(data);
                        customMessageEntity.setExtData(extData);
                        String json = new Gson().toJson(customMessageEntity);
                        sendCustomMessage(json, "");
                        sendPushVideo(callId);
                        mTimer = new CountDownTimer(30000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                sendVideoMessage(132, -1);
                                EventBus.getDefault().post(new RejectCallTip(true));
                            }
                        }.start();
                        Intent roomIntent = new Intent(GroupChatActivity.this, RoomActivity.class);
                        roomIntent.putExtra("callId", callId);
                        roomIntent.putExtra("identifier", mIdentifier);
                        roomIntent.putExtra("patientiinfo", mAllRecordForDoctorEntity);

                        startActivity(roomIntent);
                    } else {
                        alertDialog("video");
                    }

                } else {
                    toast("网络连接异常，请检查网络连接");
                }
                break;


            case "结束问诊":
                alertDialog("end");
                break;


            case "问诊表":
                Intent consultationIntent = new Intent(GroupChatActivity.this, MissionActivity.class);
                consultationIntent.putExtra("missionMessage", mPrescriptionMessage);
                consultationIntent.putExtra("action", "consultation");
                startActivityForResult(consultationIntent, MISSION_CODE);
                break;

            case "宣教":
                Intent missionIntent = new Intent(GroupChatActivity.this, MissionActivity.class);
                missionIntent.putExtra("missionMessage", mPrescriptionMessage);
                missionIntent.putExtra("action", "health");
                startActivityForResult(missionIntent, MISSION_CODE);
                break;

            case "MDT分诊":
                // toast("MDT分诊");
                Intent mdtIntent = new Intent(GroupChatActivity.this, PrescriptionActivity.class);
                //先取出来

                //组装病人信息
                PatientInfoEntity patientInfoEntity = new PatientInfoEntity();
                patientInfoEntity.setPatientId(mMDTDetailEntity.getPatientId());
                patientInfoEntity.setPatientName(mMDTDetailEntity.getPatientName());
                patientInfoEntity.setPatientSex(mMDTDetailEntity.getPatientSex());

                //解析医生病人数据
                Type jsonType = new TypeToken<PrescriptionMessageEntity<ConsultMessageEntity>>() {
                }.getType();
                PrescriptionMessageEntity<ConsultMessageEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), jsonType);
                ConsultMessageEntity ConsultMessageEntity = new ConsultMessageEntity();
                ConsultMessageEntity.setPatientInfo(patientInfoEntity);
                prescriptionMessageEntity.setPatient(ConsultMessageEntity);
                String json = mGson.toJson(prescriptionMessageEntity);
                mdtIntent.putExtra("prescriptionMessage", json);
                mdtIntent.putExtra("type", 22);
                mdtIntent.putExtra("SubjectBuyRecordId", mMDTDetailEntity.getSubjectBuyRecordId() + "");
                startActivity(mdtIntent);
                break;


        }
    }


    private void IsHasOpenPres() {

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        mMyOkhttp.get().url(HttpUrl.IsHasOpenPres).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity response) {

                if (response.getCode() == 0) {
                    mIsHasOpenPres = (boolean) response.getData();
                    mAdapter.setIsHasOpenPres(mIsHasOpenPres);

                } else {
                    toast(response.getMessage());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                toast(errorMsg);
            }
        });
    }


    private void IsHasOpenTCMPres() {

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        mMyOkhttp.get().url(HttpUrl.IsHasOpenTCMPres).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity response) {

                if (response.getCode() == 0) {
                    mIsHasOpenTCMPres = (boolean) response.getData();
                    mAdapter.setIsHasOpenTCMPres(mIsHasOpenTCMPres);

                } else {
                    toast(response.getMessage());
                }
                initviewpager();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                initviewpager();
                toast(errorMsg);
            }
        });
    }


    //查询是否有开处方权限权限
    @Override
    protected void onResume() {
        super.onResume();
        IsHasOpenPres();
        IsHasOpenTCMPres();
    }


}
