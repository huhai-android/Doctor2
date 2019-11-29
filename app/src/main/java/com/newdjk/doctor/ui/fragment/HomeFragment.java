package com.newdjk.doctor.ui.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.Controller;
import com.app.hubert.guide.listener.OnGuideChangedListener;
import com.app.hubert.guide.listener.OnLayoutInflatedListener;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighLight;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.iInterface.OnTabItemClickListener;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.tools.MainConstant;
import com.newdjk.doctor.ui.activity.AllApplicationActivity;
import com.newdjk.doctor.ui.activity.BannerDetailActivity;
import com.newdjk.doctor.ui.activity.DoctorHomeCardActivity;
import com.newdjk.doctor.ui.activity.FenjiZhuanzhenActivity;
import com.newdjk.doctor.ui.activity.GongGaoActivity;
import com.newdjk.doctor.ui.activity.HelpCenterActivity;
import com.newdjk.doctor.ui.activity.Mdt.MDTActivity;
import com.newdjk.doctor.ui.activity.MianZhenActivity;
import com.newdjk.doctor.ui.activity.MyCheckCenterActivity;
import com.newdjk.doctor.ui.activity.MypharmacyActivity;
import com.newdjk.doctor.ui.activity.OnlineConsultingActivity;
import com.newdjk.doctor.ui.activity.OnlineRenewalPartyActivity;
import com.newdjk.doctor.ui.activity.PrescriptionActivity;
import com.newdjk.doctor.ui.activity.PrescriptionListActivity;
import com.newdjk.doctor.ui.activity.PushListActivity;
import com.newdjk.doctor.ui.activity.TaskActivity;
import com.newdjk.doctor.ui.activity.TreatActivity;
import com.newdjk.doctor.ui.activity.VideoInterrogationActivity;
import com.newdjk.doctor.ui.adapter.FunctionAdapter;
import com.newdjk.doctor.ui.adapter.LastMessageAdapter;
import com.newdjk.doctor.ui.entity.AdBannerInfo;
import com.newdjk.doctor.ui.entity.AllDoctorCheckEntity;
import com.newdjk.doctor.ui.entity.AllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.AppEntity;
import com.newdjk.doctor.ui.entity.AppLicationEntity;
import com.newdjk.doctor.ui.entity.ConversationEntity;
import com.newdjk.doctor.ui.entity.CustomMessageEntity;
import com.newdjk.doctor.ui.entity.DoctorInfoByIdEntity;
import com.newdjk.doctor.ui.entity.DoctorMedicalRecordsEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.FunctionDataEntity;
import com.newdjk.doctor.ui.entity.GongGaoListEntity;
import com.newdjk.doctor.ui.entity.ImDataEntity;
import com.newdjk.doctor.ui.entity.JpushDataEntity;
import com.newdjk.doctor.ui.entity.JpushDataListEntity;
import com.newdjk.doctor.ui.entity.LoginSuccess;
import com.newdjk.doctor.ui.entity.MDTOrderEntity;
import com.newdjk.doctor.ui.entity.MessageEventRecent;
import com.newdjk.doctor.ui.entity.PrescriptionDataEntity;
import com.newdjk.doctor.ui.entity.PrescriptionEntity;
import com.newdjk.doctor.ui.entity.PushDataDaoEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.ServiceTypeOfPatientDoctorEntity;
import com.newdjk.doctor.ui.entity.TodayTaskEntity;
import com.newdjk.doctor.ui.entity.UnreadMessageEntity;
import com.newdjk.doctor.ui.entity.UpdateImMessageEntity;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.ui.entity.YWXListenerEntity;
import com.newdjk.doctor.utils.AppLicationUtils;
import com.newdjk.doctor.utils.AppUpdateUtils;
import com.newdjk.doctor.utils.AppUtils;
import com.newdjk.doctor.utils.AuthenticationCommentUtil;
import com.newdjk.doctor.utils.GlideMediaLoader;
import com.newdjk.doctor.utils.GuideViewUtil;
import com.newdjk.doctor.utils.HomeUtils;
import com.newdjk.doctor.utils.SQLiteUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.CircleImageView;
import com.newdjk.doctor.views.DialogProgress;
import com.newdjk.doctor.views.DownloadCertDialog;
import com.newdjk.doctor.views.GroupButtonDialog;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.PageIndicator;
import com.newdjk.doctor.views.ViewPagerForScrollView;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMGroupDetailInfo;
import com.tencent.TIMGroupManager;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.org.bjca.sdk.core.kit.BJCASDK;
import cn.org.bjca.sdk.core.kit.YWXListener;
import lib_zxing.activity.CaptureActivity;
import lib_zxing.activity.CodeUtils;


/**
 * 首页
 */
public class HomeFragment extends BasicFragment {


    private static final String TAG = "HomeFragment---1";
    LinearLayout mission;
    Unbinder unbinder;
    @BindView(R.id.sb_default)
    SwitchButton sbDefault;
    @BindView(R.id.iv_switch_button)
    ImageView ivSwitchButton;
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
    @BindView(R.id.mBanner)
    Banner mBanner;
    @BindView(R.id.im_picture)
    ImageView imPicture;
    @BindView(R.id.picture_unread_num)
    TextView pictureUnreadNum;
    @BindView(R.id.picture_unread_num_layout)
    RelativeLayout pictureUnreadNumLayout;
    @BindView(R.id.mFuncOne)
    TextView mFuncOne;
    @BindView(R.id.online_consult)
    LinearLayout onlineConsult;
    @BindView(R.id.im_video)
    ImageView imVideo;
    @BindView(R.id.video_unread_num)
    TextView videoUnreadNum;
    @BindView(R.id.video_unread_num_layout)
    RelativeLayout videoUnreadNumLayout;
    @BindView(R.id.mFuncTwo)
    TextView mFuncTwo;
    @BindView(R.id.my_physician_visits)
    LinearLayout myPhysicianVisits;
    @BindView(R.id.im_renewal)
    ImageView imRenewal;
    @BindView(R.id.renewal_unread_num)
    TextView renewalUnreadNum;
    @BindView(R.id.renewal_unread_num_layout)
    RelativeLayout renewalUnreadNumLayout;
    @BindView(R.id.mFuncThree)
    TextView mFuncThree;
    @BindView(R.id.online_renewal_party)
    LinearLayout onlineRenewalParty;
    @BindView(R.id.mFuncFour)
    TextView mFuncFour;
    @BindView(R.id.my_pharmacy)
    LinearLayout myPharmacy;
    @BindView(R.id.doctor_layout)
    LinearLayout doctorLayout;
    @BindView(R.id.im_heart)
    ImageView imHeart;
    @BindView(R.id.tv_unread_num_heart)
    TextView tvUnreadNumHeart;
    @BindView(R.id.rv_heart_unread_num)
    RelativeLayout rvHeartUnreadNum;
    @BindView(R.id.tv_heartcheck)
    TextView tvHeartcheck;
    @BindView(R.id.lv_heartcheck)
    LinearLayout lvHeartcheck;
    @BindView(R.id.im_chufang)
    ImageView imChufang;
    @BindView(R.id.tv_unread_num_chufang)
    TextView tvUnreadNumChufang;
    @BindView(R.id.rv_chufang_unread_num)
    RelativeLayout rvChufangUnreadNum;
    @BindView(R.id.lv_chufangcheck)
    LinearLayout lvChufangcheck;
    @BindView(R.id.invitate_patient_icon)
    ImageView invitatePatientIcon;
    @BindView(R.id.tv_chufang_check)
    TextView tvChufangCheck;
    @BindView(R.id.invitate_patient)
    LinearLayout invitatePatient;
    @BindView(R.id.performance_record_icon)
    ImageView performanceRecordIcon;
    @BindView(R.id.tv_performance_record)
    TextView tvPerformanceRecord;
    @BindView(R.id.performance_record)
    LinearLayout performanceRecord;
    @BindView(R.id.treatment)
    ImageView treatment;
    @BindView(R.id.tv_unread_num_treatment)
    TextView tvUnreadNumTreatment;
    @BindView(R.id.rv_treatment_unread_num)
    RelativeLayout rvTreatmentUnreadNum;
    @BindView(R.id.tv_diagnosis_and_treatment)
    TextView tvDiagnosisAndTreatment;
    @BindView(R.id.diagnosis_and_treatment)
    LinearLayout diagnosisAndTreatment;
    @BindView(R.id.scan)
    LinearLayout scan;
    @BindView(R.id.lv_empty1)
    LinearLayout lvEmpty1;
    @BindView(R.id.lv_empty2)
    LinearLayout lvEmpty2;
    @BindView(R.id.lv_function2)
    LinearLayout lvFunction2;
    @BindView(R.id.call_test)
    TextView callTest;
    @BindView(R.id.system_avatar)
    CircleImageView systemAvatar;
    @BindView(R.id.system_unread_num)
    TextView systemUnreadNum;
    @BindView(R.id.system_unread_num_layout)
    LinearLayout systemUnreadNumLayout;
    @BindView(R.id.system_avatar_layout)
    RelativeLayout systemAvatarLayout;
    @BindView(R.id.system_name)
    TextView systemName;
    @BindView(R.id.system_content)
    TextView systemContent;
    @BindView(R.id.system_message_layout)
    RelativeLayout systemMessageLayout;
    @BindView(R.id.reports_avatar)
    CircleImageView reportsAvatar;
    @BindView(R.id.reports_unread_num)
    TextView reportsUnreadNum;
    @BindView(R.id.reports_unread_num_layout)
    LinearLayout reportsUnreadNumLayout;
    @BindView(R.id.reports_avatar_layout)
    RelativeLayout reportsAvatarLayout;
    @BindView(R.id.reports_name)
    TextView reportsName;
    @BindView(R.id.reports_content)
    TextView reportsContent;
    @BindView(R.id.reports_message_layout)
    RelativeLayout reportsMessageLayout;
    @BindView(R.id.chufang_avatar)
    CircleImageView chufangAvatar;
    @BindView(R.id.chufang_unread_num)
    TextView chufangUnreadNum;
    @BindView(R.id.chufang_unread_num_layout)
    LinearLayout chufangUnreadNumLayout;
    @BindView(R.id.chufang_avatar_layout)
    RelativeLayout chufangAvatarLayout;
    @BindView(R.id.chufang_name)
    TextView chufangName;
    @BindView(R.id.chufang_content)
    TextView chufangContent;
    @BindView(R.id.chufang_message_layout)
    RelativeLayout chufangMessageLayout;
    @BindView(R.id.function_list)
    RecyclerView functionList;
    @BindView(R.id.function_layout)
    LinearLayout functionLayout;
    @BindView(R.id.lv_emptyA)
    LinearLayout lvEmptyA;
    @BindView(R.id.lv_emptyB)
    LinearLayout lvEmptyB;
    @BindView(R.id.lv_emptyC)
    LinearLayout lvEmptyC;
    @BindView(R.id.message_recycler_view)
    RecyclerView messageRecyclerView;
    @BindView(R.id.im_to_top)
    ImageView imToTop;
    @BindView(R.id.scrollView_root)
    NestedScrollView scrollViewRoot;
    @BindView(R.id.tabviewpager)
    ViewPagerForScrollView tabviewpager;
    @BindView(R.id.dot_horizontal)
    LinearLayout dotHorizontal;
    @BindView(R.id.tabapp)
    LinearLayout tabapp;
    @BindView(R.id.im_advice)
    ImageView imAdvice;
    @BindView(R.id.tv_youxuantuijian)
    TextView tvYouxuantuijian;
    @BindView(R.id.lv_advice)
    LinearLayout lvAdvice;
    public int groupIndex = 0;
    @BindView(R.id.top_name)
    TextView topName;
    @BindView(R.id.top_keshi)
    TextView topKeshi;
    @BindView(R.id.top_level)
    TextView topLevel;
    @BindView(R.id.tv_authentication)
    TextView tvAuthentication;
    @BindView(R.id.icon_lingdang)
    ImageView iconLingdang;
    @BindView(R.id.tv_unread_num)
    TextView tvUnreadNum;
    @BindView(R.id.lv_system_unred)
    LinearLayout lvSystemUnred;
    @BindView(R.id.help_center)
    ImageView helpCenter;
    @BindView(R.id.rv_system_center)
    RelativeLayout rvSystemCenter;
    @BindView(R.id.today_task)
    TextView todayTask;
    @BindView(R.id.accept_task)
    TextView acceptTask;
    @BindView(R.id.future_task)
    TextView futureTask;
    @BindView(R.id.tv_gonggao)
    TextView tvGonggao;
    @BindView(R.id.lv_guanggao)
    LinearLayout lvGuanggao;
    @BindView(R.id.title_today_task)
    TextView titleTodayTask;
    @BindView(R.id.title_accept_task)
    TextView titleAcceptTask;
    @BindView(R.id.title_future_task)
    TextView titleFutureTask;
    @BindView(R.id.liear_titlebar)
    LinearLayout liearTitlebar;
    @BindView(R.id.lv_today_task)
    LinearLayout lvTodayTask;
    @BindView(R.id.lv_today_job)
    LinearLayout lvTodayJob;
    @BindView(R.id.lv_accept_job)
    LinearLayout lvAcceptJob;
    @BindView(R.id.lv_future_job)
    LinearLayout lvFutureJob;
    @BindView(R.id.easylayout)
    EasyRefreshLayout easylayout;
    @BindView(R.id.im_bg)
    ImageView imBg;
    @BindView(R.id.lv_today_job_child)
    LinearLayout lvTodayJobChild;
    @BindView(R.id.lv_tab1)
    LinearLayout lvTab1;
    @BindView(R.id.lv_tab2)
    LinearLayout lvTab2;
    @BindView(R.id.lv_tab3)
    LinearLayout lvTab3;
    @BindView(R.id.lv_tab4)
    LinearLayout lvTab4;
    @BindView(R.id.lv_tab5)
    LinearLayout lvTab5;
    @BindView(R.id.lv_tab6)
    LinearLayout lvTab6;
    @BindView(R.id.lv_tab7)
    LinearLayout lvTab7;
    @BindView(R.id.lv_tab_lead)
    LinearLayout lvTabLead;


    // private MessageAdapter mAdapter;
    private int mDoctype = 0;
    private Gson mGson;
    public static List<UnreadMessageEntity> mAllUnreadMessageList;
    public static List<UnreadMessageEntity> mRenewalUnreadMessageList;
    public static List<UnreadMessageEntity> mPictureUnreadMessageList;
    public static List<UnreadMessageEntity> mVideoUnreadMessageList;
    private long mPictureUnRead = 0;
    private long mVideoUnRead = 0;
    private long mRenewalUnRead = 0;
    private DoctorInfoByIdEntity mDoctorInfoByIdEntity;
    private int mId = 0;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int mIsOnLine;
    List<ServiceTypeOfPatientDoctorEntity> mServiceList;
    public static String serviceTime;
    private GroupButtonDialog mDialog;
    private DialogProgress mProgress;
    private int intTotalYaoshi = 0;
    private int mStatus;
    List<AppLicationEntity> listuse = new ArrayList<>();
    FunctionAdapter mFunctionAdapter;
    private FunctionDataEntity mFunctionDataEntity;
    List<AdBannerInfo.DataBean> bannerdata = new ArrayList<>();
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private Notification notification;
    private int mMustupdate = 0;
    private int channelID = 1;
    private AppEntity appentity;
    private LastMessageAdapter mAdapter;
    private List<ImDataEntity> mConversationListData = new ArrayList<>();
    private PagerAdapter mtabAdapter;
    private static final int LOADING_SUCCESS = 2;
    private int mTotal1;
    private int mTotal2;
    private int total;
    private int groupUnred = 0;
    private boolean isudate = false;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADING_SUCCESS:
                    Log.d(TAG, "登录成功后调用");
                    refreshView();
                    updateMdtReadNumber();
                    break;

                default:
                    break;
            }
        }
    };
    private List<String> mDoctorIdList;
    private GongGaoListEntity mGonggao;
    private GuideViewUtil mGuideViewUtil;


    public static HomeFragment getFragment() {
        return new HomeFragment();
    }

    @Override
    protected int initViewResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mStatus = SpUtils.getInt(Contants.Status, 0);
        topTitle.setText(SpUtils.getString(Contants.Name));
        topRight.setVisibility(View.VISIBLE);
        topRight.setImageResource(R.mipmap.doctor_home_card);
        mProgress = new DialogProgress(getContext());
        mId = SpUtils.getInt(Contants.Id, 0);


        //SQLiteUtils.getInstance().deleteAllPushData();
        mAllUnreadMessageList = new ArrayList<>();
        mRenewalUnreadMessageList = new ArrayList<>();
        mPictureUnreadMessageList = new ArrayList<>();
        mVideoUnreadMessageList = new ArrayList<>();
        mGson = new Gson();
        mDoctype = SpUtils.getInt(Contants.DocType, 0);

        mAdapter = new LastMessageAdapter(mConversationListData);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        messageRecyclerView.setAdapter(mAdapter);
        messageRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


        mtabAdapter = new PagerAdapter(getActivity().getSupportFragmentManager());


        //首页改版新增
        topName.setText("Hi " + SpUtils.getString(Contants.Name));
        changeAuthentication();

    }


    private void initviewpager() {
        tabviewpager.setAdapter(mtabAdapter);
        tabviewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabviewpager.setCurrentItem(position, false);
                HomeTabFragment homeTabFragment = (HomeTabFragment) mtabAdapter.getItem(position);

                homeTabFragment.setdata(listuse, position);
            }
        });
        if (listuse.size() <= 8) {
            dotHorizontal.setVisibility(View.GONE);
        } else {
            dotHorizontal.setVisibility(View.GONE);
            tabviewpager.addOnPageChangeListener(new PageIndicator(getContext(), dotHorizontal, listuse.size() / 8 + 1));

        }
        tabviewpager.setCurrentItem(0, false);
        mtabAdapter.notifyDataSetChanged();
    }


    @Override
    protected void initListener() {
        diagnosisAndTreatment.setOnClickListener(this);
        performanceRecord.setOnClickListener(this);
        invitatePatient.setOnClickListener(this);
        systemMessageLayout.setOnClickListener(this);
        reportsMessageLayout.setOnClickListener(this);
        onlineConsult.setOnClickListener(this);
        myPharmacy.setOnClickListener(this);
        callTest.setOnClickListener(this);
        topRight.setOnClickListener(this);
        onlineRenewalParty.setOnClickListener(this);
        myPhysicianVisits.setOnClickListener(this);
        scan.setOnClickListener(this);
        lvHeartcheck.setOnClickListener(this);
        lvChufangcheck.setOnClickListener(this);
        imToTop.setOnClickListener(this);
        lvAdvice.setOnClickListener(this);
        rvSystemCenter.setOnClickListener(this);
        helpCenter.setOnClickListener(this);
        tvAuthentication.setOnClickListener(this);
        lvGuanggao.setOnClickListener(this);
        lvTodayTask.setOnClickListener(this);
        lvTodayJob.setOnClickListener(this);
        lvAcceptJob.setOnClickListener(this);
        lvFutureJob.setOnClickListener(this);
        // topLeft.setOnCheckedChangeListener(new );

        ivSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsOnLine == 1) {
                    if (mDialog == null) {
                        mDialog = new GroupButtonDialog(getContext());
                    }
                    mDialog.show("", "", new GroupButtonDialog.DialogListener() {
                        @Override
                        public void cancel() {
                            mDialog = null;
                        }

                        @Override
                        public void confirm() {
                            mDialog = null;
                            updateOnline(0);
                        }
                    });
                } else {
                    updateOnline(1);
                }

            }
        });
        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onRefreshing() {
                //刷新广告
                if (mDoctype == 3) {
                    getDoctorInfo();
                } else {
                    QueryCountDoctorOrderMerge();
                }
                GetNoticeManagePageList();
                //刷新banner
                loadBanner();
                //刷新数据显示

            }
        });
    }

    private void updateOnline(final int flag) {
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        params.put("IsOnline", String.valueOf(flag));
        mMyOkhttp.get().url(HttpUrl.UpdateDoctorOnline).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity response) {
                LoadDialog.clear();
                if (response.getCode() == 0) {
                    if (flag == 1) {
                        mIsOnLine = 1;
                        setOnLineStatus(1);
                    } else {
                        mIsOnLine = 0;
                        setOnLineStatus(0);
                    }

                } else {
                    toast(response.getMessage());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                LoadDialog.clear();
                toast(errorMsg);
            }
        });
    }

    @Override
    protected void initData() {
        if (mDoctype == 3) {
            //审方师
            titleTodayTask.setText("待处理");
            titleAcceptTask.setText("已审核");
            titleFutureTask.setText("已审驳回");

            functionList.setVisibility(View.GONE);
            functionLayout.setVisibility(View.GONE);
            systemMessageLayout.setVisibility(View.GONE);
            reportsMessageLayout.setVisibility(View.GONE);
            doctorLayout.setVisibility(View.GONE);
            ivSwitchButton.setVisibility(View.GONE);
            scan.setVisibility(View.VISIBLE);
            lvHeartcheck.setVisibility(View.GONE);
            chufangMessageLayout.setVisibility(View.GONE);
            lvChufangcheck.setVisibility(View.VISIBLE);
            lvEmpty2.setVisibility(View.INVISIBLE);
            performanceRecord.setVisibility(View.GONE);
            invitatePatient.setVisibility(View.GONE);
            diagnosisAndTreatment.setVisibility(View.GONE);
            myPharmacy.setVisibility(View.GONE);
            tabapp.setVisibility(View.GONE);

        } else if (mDoctype == 2) {

            //护士展示
            titleTodayTask.setText("今日任务");
            titleAcceptTask.setText("咨询中");
            titleFutureTask.setText("未来预约");
            functionList.setVisibility(View.GONE);
            functionLayout.setVisibility(View.GONE);

            myPhysicianVisits.setVisibility(View.VISIBLE);
            lvAdvice.setVisibility(View.VISIBLE);
            lvFunction2.setVisibility(View.VISIBLE);
            lvEmpty1.setVisibility(View.INVISIBLE);
            lvEmpty2.setVisibility(View.INVISIBLE);
            lvHeartcheck.setVisibility(View.INVISIBLE);

            mFuncOne.setText("护理咨询");
            mFuncTwo.setText("远程护理");
         /*   myPhysicianVisits.setVisibility(View.VISIBLE);
            videoUnreadNumLayout.setVisibility(View.VISIBLE);*/
            lvEmptyA.setVisibility(View.GONE);
            lvEmptyB.setVisibility(View.GONE);
            lvEmptyC.setVisibility(View.GONE);
            onlineRenewalParty.setVisibility(View.GONE);
            myPharmacy.setVisibility(View.INVISIBLE);
            lvHeartcheck.setVisibility(View.GONE);
            tabapp.setVisibility(View.VISIBLE);
            helpCenter.setVisibility(View.GONE);//帮助中心隐藏

            getAllUseFunction();
        } else if (mDoctype == 1) {
            diagnosisAndTreatment.setVisibility(View.GONE);
            functionList.setVisibility(View.GONE);
            functionLayout.setVisibility(View.GONE);
            //  getNewReportUnreadUnm();
            //   requestAllNewReportPaientMessage();
            doctorLayout.setVisibility(View.VISIBLE);
            scan.setVisibility(View.GONE);
            tabapp.setVisibility(View.VISIBLE);
            getAllUseFunction();


        }
        getAllPushList();
        if (mFunctionAdapter != null) {
            mFunctionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    tabitemclick(position);
                }
            });
        }

        loadBanner();


        //贪心判读红点显示
        //普通
        if (mDoctype == 1) {
            getConsultRecordList();

            //药师
        } else if (mDoctype == 3) {
            getOnlinePartyList(1, "0");

        }
        if (checkPermission()) {
            checkUpdate();
        }

        //queryDoctorMedicalRecords();


        GetNoticeManagePageList();
    }

    //获取公告
    private void GetNoticeManagePageList() {
        HashMap<String, String> params = new HashMap<>();
        params.put("PageIndex", "1");
        params.put("PageSize", "100");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetNoticeManagePageList).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<GongGaoListEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<GongGaoListEntity> entity) {
                if (entity.getCode() == 0) {
                    if (entity.getData() != null) {
                        mGonggao = entity.getData();

                        if (entity.getData().getReturnData() != null) {
                            if (entity.getData().getReturnData().size() > 0) {
                                lvGuanggao.setVisibility(View.VISIBLE);
                                tvGonggao.setText(entity.getData().getReturnData().get(0).getTitle() + "");

                            }
                        }
                    }

                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });

    }

    //获取首页数据
    private void QueryCountDoctorOrderMerge() {
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(HttpUrl.QueryCountDoctorOrderMerge).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<TodayTaskEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<TodayTaskEntity> entity) {

                if (entity.getCode() == 0) {
                    if (entity.getData() != null) {
                        todayTask.setText(entity.getData().getTodayNum() + "");
                        acceptTask.setText(entity.getData().getReceiveNum() + "");
                        futureTask.setText(entity.getData().getFutureNum() + "");

                    }
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                //  CommonMethod.requestError(statusCode, errorMsg);
            }
        });

    }

    private void tabitemclick(int position) {
        Log.d(TAG, "常用" + position);
        int type = listuse.get(position).getAppID();
        switch (type) {
            case 0:
                Intent audio = new Intent(getContext(), AllApplicationActivity.class);
                startActivity(audio);
                break;
            case 1:
                Intent intent = new Intent(getActivity(), OnlineConsultingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("unReadList", (Serializable) mPictureUnreadMessageList);
                bundle.putSerializable("allUnReadList", (Serializable) mAllUnreadMessageList);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 2:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, getContext());
                } else {
                    Intent onlineRenewalPartyIntent = new Intent(mContext, OnlineRenewalPartyActivity.class);
                    Bundle renewalBundle = new Bundle();
                    renewalBundle.putSerializable("allUnReadList", (Serializable) mAllUnreadMessageList);
                    renewalBundle.putSerializable("unReadList", (Serializable) mRenewalUnreadMessageList);
                    onlineRenewalPartyIntent.putExtras(renewalBundle);
                    startActivity(onlineRenewalPartyIntent);
                }
                break;
            case 3:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, getContext());
                } else {
                    Intent treatIntent = new Intent(getActivity(), TreatActivity.class);
                    startActivity(treatIntent);
                }
                break;
            case 4:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                //toast("胎心判读");
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, getContext());
                } else {
                    //toast("胎心判读");
                    Intent intentCheckCenter = MyCheckCenterActivity.newIntent(getContext());
                    startActivity(intentCheckCenter);

                }
                break;
            case 5:
                Intent performanceIntent = new Intent(getActivity(), MypharmacyActivity.class);
                performanceIntent.putExtra("action", 2);
                startActivity(performanceIntent);
                break;
            case 6:
                Intent pharmacyIntent = new Intent(getActivity(), MypharmacyActivity.class);
                pharmacyIntent.putExtra("action", 1);
                startActivity(pharmacyIntent);
                break;
            case 7:
                Intent idCardIntent = new Intent(getContext(), DoctorHomeCardActivity.class);
                idCardIntent.putExtra("title", "我的名片");
                idCardIntent.putExtra("doctorInfoByIdEntity", mDoctorInfoByIdEntity);
                startActivity(idCardIntent);
                break;
            case 8:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, getContext());
                } else {
                    Intent videoInterrogationIntent = new Intent(mContext, VideoInterrogationActivity.class);
                    Bundle videoBundle = new Bundle();
                    videoBundle.putSerializable("unReadList", (Serializable) mVideoUnreadMessageList);
                    videoBundle.putSerializable("allUnReadList", (Serializable) mAllUnreadMessageList);
                    videoInterrogationIntent.putExtras(videoBundle);
                    startActivity(videoInterrogationIntent);
                }

                break;

            case 9:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, getContext());
                } else {

                    ExistDoctorDisGroup();
                }
                //  ExistDoctorDisGroup();
                break;
        }
    }

    private void ExistDoctorDisGroup() {

        Intent fenjizhuanzhen = new Intent(mContext, FenjiZhuanzhenActivity.class);
        startActivity(fenjizhuanzhen);
    }

    private void loadBanner() {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        String bannerUrl = HttpUrl.QueryAdBannerInfo + "?classId=" + 4;
        mMyOkhttp.get().url(bannerUrl).headers(headMap).tag(this).enqueue(new GsonResponseHandler<AdBannerInfo>() {
            @Override
            public void onSuccess(int statusCode, AdBannerInfo entituy) {

                if (easylayout == null) {
                    return;
                }
                if (easylayout.isRefreshing()) {
                    easylayout.refreshComplete();
                }
                final List<AdBannerInfo.DataBean> data = entituy.getData();
                if (data == null || data.size() == 0) {
                    return;
                }
                List<String> images = new ArrayList<>();
                bannerdata.clear();
                bannerdata.addAll(data);
                for (AdBannerInfo.DataBean datum : data) {
                    images.add(datum.getContent());
                }
                dealBannerData(images);
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                if (easylayout == null) {
                    return;
                }
                if (easylayout.isRefreshing()) {
                    easylayout.refreshComplete();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void dealBannerData(List<String> images) {
        if (images == null || images.size() == 0) {
            return;
        }
        if (getActivity() == null) {
            return;
        }
        //设置banner样式
//                mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new GlideMediaLoader());
        //设置图片集合
        mBanner.setImages(images);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//                mBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                // toast("点击了图片"+position);
                if (TextUtils.isEmpty(bannerdata.get(position).getContentLink())) {

                    Intent intent = new Intent(getContext(), BannerDetailActivity.class);
                    intent.putExtra("banner", bannerdata.get(position).getLinkContent());
                    intent.putExtra("bannerInfo", bannerdata.get(position));
                    startActivity(intent);

                } else {
                    Intent helpintent = new Intent(getContext(), PrescriptionActivity.class);
                    helpintent.putExtra("type", 38);
                    helpintent.putExtra("LinkUrl", bannerdata.get(position).getContentLink());
                    startActivity(helpintent);
                }

            }
        });

        mBanner.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 5);
            }
        });

        mBanner.setClipToOutline(true);

        mBanner.start();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.online_consult:
                Intent intent = new Intent(getActivity(), OnlineConsultingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("unReadList", (Serializable) mPictureUnreadMessageList);
                bundle.putSerializable("allUnReadList", (Serializable) mAllUnreadMessageList);
                intent.putExtras(bundle);
                startActivity(intent);

                break;
            case R.id.my_pharmacy:
                Intent pharmacyIntent = new Intent(getActivity(), MypharmacyActivity.class);
                pharmacyIntent.putExtra("action", 1);
                startActivity(pharmacyIntent);
                break;
            case R.id.call_test:
//                Intent callIntent = new Intent(getActivity(), ChatActivity.class);
//                callIntent.putExtra(Contants.FRIEND_IDENTIFIER, "mike");
//                startActivity(callIntent);
                break;
            case R.id.top_right:
                if (mDoctorInfoByIdEntity == null) {
                    return;
                } else {
                    Intent idCardIntent = new Intent(getContext(), DoctorHomeCardActivity.class);
                    idCardIntent.putExtra("title", "我的名片");
                    idCardIntent.putExtra("doctorInfoByIdEntity", mDoctorInfoByIdEntity);
                    startActivity(idCardIntent);
                }
                break;
            case R.id.scan:
                //
                ToastUtil.setToast("扫一扫");
                Intent intent2 = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent2, REQUEST_CODE);
                break;
            case R.id.system_message_layout:
                Intent systemIntent = new Intent(getContext(), PushListActivity.class);
                systemIntent.putExtra("action", "system");
                startActivity(systemIntent);
                break;
            case R.id.reports_message_layout:
                Intent reportedIntent = new Intent(getContext(), PushListActivity.class);
                reportedIntent.putExtra("action", "newReport");
                startActivity(reportedIntent);
              /*  Intent reportedIntent = new Intent(getContext(), PatientActivity.class);
                reportedIntent.putExtra("action", 1);
                startActivity(reportedIntent);*/
                break;
            case R.id.my_physician_visits:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, getContext());
                } else {
                    Intent videoInterrogationIntent = new Intent(mContext, VideoInterrogationActivity.class);
                    Bundle videoBundle = new Bundle();
                    videoBundle.putSerializable("unReadList", (Serializable) mVideoUnreadMessageList);
                    videoBundle.putSerializable("allUnReadList", (Serializable) mAllUnreadMessageList);
                    videoInterrogationIntent.putExtras(videoBundle);
                    startActivity(videoInterrogationIntent);
                }

                break;
            case R.id.online_renewal_party:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, getContext());
                } else {
                    Intent onlineRenewalPartyIntent = new Intent(mContext, OnlineRenewalPartyActivity.class);
                    Bundle renewalBundle = new Bundle();
                    renewalBundle.putSerializable("allUnReadList", (Serializable) mAllUnreadMessageList);
                    renewalBundle.putSerializable("unReadList", (Serializable) mRenewalUnreadMessageList);
                    onlineRenewalPartyIntent.putExtras(renewalBundle);
                    startActivity(onlineRenewalPartyIntent);
                }
                break;

            case R.id.lv_heartcheck:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                //toast("胎心判读");
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, getContext());
                } else {
                    //toast("胎心判读");
                    Intent intentCheckCenter = MyCheckCenterActivity.newIntent(getContext());
                    startActivity(intentCheckCenter);

                }
                break;

            case R.id.lv_chufangcheck:
                //toast("药师");
                Intent intentPrescription = PrescriptionListActivity.newIntent(getContext());
                startActivity(intentPrescription);
                break;
            case R.id.invitate_patient:
                Intent idCardIntent = new Intent(getContext(), DoctorHomeCardActivity.class);
                idCardIntent.putExtra("title", "我的名片");
                idCardIntent.putExtra("doctorInfoByIdEntity", mDoctorInfoByIdEntity);
                startActivity(idCardIntent);
                break;
            case R.id.performance_record:
                Intent performanceIntent = new Intent(getActivity(), MypharmacyActivity.class);
                performanceIntent.putExtra("action", 2);
                startActivity(performanceIntent);
                break;
            case R.id.diagnosis_and_treatment:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, getContext());
                } else {
                    Intent treatIntent = new Intent(getActivity(), TreatActivity.class);
                    startActivity(treatIntent);
                }
                break;
            case R.id.im_to_top:
                scrollViewRoot.fling(0);
                scrollViewRoot.smoothScrollTo(0, 0);
                break;

            case R.id.lv_advice:

                Intent adviceintent = new Intent(getContext(), PrescriptionActivity.class);
                adviceintent.putExtra("type", 11);
                adviceintent.putExtra("fromIM", 0);
                adviceintent.putExtra("prescriptionMessage", "");
                startActivity(adviceintent);
                break;

            case R.id.rv_system_center:

                Intent systemIntent2 = new Intent(getContext(), PushListActivity.class);
                systemIntent2.putExtra("action", "system");
                startActivity(systemIntent2);
                break;

            case R.id.help_center:

                Intent helpcenter = new Intent(getContext(), HelpCenterActivity.class);
                startActivity(helpcenter);
                break;
            case R.id.tv_authentication:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, getContext());
                }

                break;

            case R.id.lv_guanggao:
                Intent gonggaointent = new Intent(getContext(), GongGaoActivity.class);
                gonggaointent.putExtra("Gonggao", mGonggao);
                startActivity(gonggaointent);
                break;


            case R.id.lv_today_task:
                if (mDoctype == 3) {//如果是审方师

                    //toast("药师");
                    Intent chufangPrescription = PrescriptionListActivity.newIntent(getContext());
                    startActivity(chufangPrescription);


                } else { //不是护士

                    Intent chufangPrescription = TaskActivity.newIntent(getContext());
                    startActivity(chufangPrescription);
                }
                break;

            case R.id.lv_today_job:
                if (mDoctype == 3) {//如果是审方师

                    //toast("药师");
                    Intent chufangPrescription = PrescriptionListActivity.newIntent(getContext());
                    startActivity(chufangPrescription);


                } else { //不是护士

                    Intent chufangPrescription = TaskActivity.newIntent(getContext());
                    chufangPrescription.putExtra("type", 1);
                    startActivity(chufangPrescription);
                }
                break;

            case R.id.lv_accept_job:
                if (mDoctype == 3) {//如果是审方师
                    //toast("药师");
                    Intent chufangPrescription = PrescriptionListActivity.newIntent(getContext());
                    startActivity(chufangPrescription);

                } else { //不是护士

                    Intent chufangPrescription = TaskActivity.newIntent(getContext());
                    chufangPrescription.putExtra("type", 2);
                    startActivity(chufangPrescription);
                }
                break;
            case R.id.lv_future_job:
                if (mDoctype == 3) {//如果是审方师

                    //toast("药师");
                    Intent chufangPrescription = PrescriptionListActivity.newIntent(getContext());
                    startActivity(chufangPrescription);


                } else { //不是护士

                    Intent chufangPrescription = TaskActivity.newIntent(getContext());
                    chufangPrescription.putExtra("type", 1);
                    startActivity(chufangPrescription);
                }


                break;

        }
    }

    private void getAllUseFunction() {
        listuse.clear();
//        mFunctionDataEntity = SQLiteUtils.getInstance().selectFunctionDataById(SpUtils.getInt(Contants.Id, 0));
//        String use = null;
//        if (mFunctionDataEntity != null) {
//            use = mFunctionDataEntity.getData();
//        }
//        //   String all = SpUtils.getString(Contants.ALLJSON);
//        Log.d(TAG, "常用" + use);
//        //  Log.d(TAG, "所有" + all);
//        if (TextUtils.isEmpty(use)) {
//            listuse.addAll(AppLicationUtils.getListuse());
//        } else {
//            try {
//                listuse = mGson.fromJson(use, new TypeToken<List<AppLicationEntity>>() {
//                }.getType());
//            } catch (Exception e) {
//                Log.e("HomeFragment", "e = " + e.toString());
//            }
//        }
        //添加定制桌面
//        AppLicationEntity appLicationEntity = new AppLicationEntity();
//        appLicationEntity.setAppDesc("定制桌面");
//        appLicationEntity.setImageID(0);
//        appLicationEntity.setAppID(0);
//        listuse.add(appLicationEntity);

        if (mDoctype == 3) {
            listuse.addAll(AppLicationUtils.getNurseall());

        } else if (mDoctype == 2) {
            listuse.addAll(AppLicationUtils.getNurseall());
        } else {
            listuse.addAll(AppLicationUtils.getListuse());
        }


        if (mFunctionAdapter != null) {
            mFunctionAdapter.setNewData(listuse);
            showUnreadTip();
            //queryDoctorMedicalRecords();

            getConsultRecordList();
            // mFunctionAdapter.notifyDataSetChanged();
        } else {
            GridLayoutManager mManagerLayout = new GridLayoutManager(mContext, 4);
            functionList.setLayoutManager(mManagerLayout);
            mFunctionAdapter = new FunctionAdapter(listuse);
            functionList.setAdapter(mFunctionAdapter);
        }
        initviewpager();


    }

    /**
     * 请求CAMERA权限码
     */
    public static final int REQUEST_CAMERA_PERM = 101;
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            checkUpdate();
        } catch (Exception e) {

        }
    }

    /**
     * 按钮点击事件处理逻辑
     *
     * @param
     */
    private void onClick() {
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    final String result = bundle.getString(CodeUtils.RESULT_STRING);

                    final boolean isExists = BJCASDK.getInstance().existsCert(getContext());
                    boolean ExistStamp = BJCASDK.getInstance().existsStamp(getContext());
                    if (!isExists) {
                        DownloadCertDialog mDialog = new DownloadCertDialog(mActivity);
                        mDialog.show("", "", new DownloadCertDialog.DialogListener() {

                            @Override
                            public void confirm() {
                                BJCASDK.getInstance().certDown(mActivity, Contants.clientId, SpUtils.getString(Contants.userName), new YWXListener() {
                                    @Override
                                    public void callback(String s) {
                                        YWXListenerEntity yWXListenerEntity = mGson.fromJson(s, YWXListenerEntity.class);
                                        String status = yWXListenerEntity.getStatus();
                                        String message = yWXListenerEntity.getMessage();
                                        if (status != null && status.equals("0")) {
                                            boolean ExistStamp1 = BJCASDK.getInstance().existsStamp(getContext());
                                            if (!ExistStamp1) {
                                                BJCASDK.getInstance().drawStamp(mActivity, Contants.clientId, new YWXListener() {
                                                    @Override
                                                    public void callback(String msg) {
                                                        YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                                        String status = yWXListenerEntity.getStatus();
                                                        String message = yWXListenerEntity.getMessage();
                                                        if (status != null && status.equals("0")) {
                                                            BJCASDK.getInstance().qrDispose(mActivity, Contants.clientId, result, new YWXListener() {
                                                                @Override
                                                                public void callback(String msg) {
                                                                    YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                                                    String status = yWXListenerEntity.getStatus();
                                                                    String message = yWXListenerEntity.getMessage();
                                                                    if (status != null && status.equals("0")) {
                                                                        Toast.makeText(mActivity, "调用成功", Toast.LENGTH_SHORT).show();
                                                                    } else {
                                                                        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });
                                                        } else {
                                                            // Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
                                                            ToastUtil.setToast("设置签章失败，请重试！+(" + status + ")");
                                                        }
                                                    }
                                                });
                                            } else {
                                                BJCASDK.getInstance().qrDispose(mActivity, Contants.clientId, result, new YWXListener() {
                                                    @Override
                                                    public void callback(String msg) {
                                                        YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                                        String status = yWXListenerEntity.getStatus();
                                                        String message = yWXListenerEntity.getMessage();
                                                        if (status != null && status.equals("0")) {
                                                            Toast.makeText(mActivity, "调用成功", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        } else {
                                            //  Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
                                            Toast.makeText(mActivity, "下载证书失败，请重试!(" + status + ")", Toast.LENGTH_SHORT).show();
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
                                        BJCASDK.getInstance().qrDispose(mActivity, Contants.clientId, result, new YWXListener() {
                                            @Override
                                            public void callback(String msg) {
                                                YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                                String status = yWXListenerEntity.getStatus();
                                                String message = yWXListenerEntity.getMessage();
                                                if (status != null && status.equals("0")) {
                                                    Toast.makeText(mActivity, "调用成功", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        // Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
                                        ToastUtil.setToast("设置签章失败，请重试！+(" + status + ")");
                                    }
                                }
                            });
                        } else {

                            BJCASDK.getInstance().qrDispose(mActivity, Contants.clientId, result, new YWXListener() {
                                @Override
                                public void callback(String msg) {
                                    YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                    String status = yWXListenerEntity.getStatus();
                                    String message = yWXListenerEntity.getMessage();
                                    if (status != null && status.equals("0")) {
                                        Toast.makeText(mActivity, "调用成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }


                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }


        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, null, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        // mGuideViewUtil = new GuideViewUtil(getActivity(), R.drawable.bg_lead);
//        NewbieGuide.with(getActivity())
//                .setLabel("guide1")
//                .addGuidePage(GuidePage.newInstance()
//                        .addHighLight(tabapp)
//                        .setLayoutRes(R.layout.view_guide))
//                .show();

        boolean isfiestlogin = SpUtils.getBoolean(Contants.Haslogin, false);

        if (mDoctype == 1) {
            if (isfiestlogin) {//已经显示过了引导界面
                tabviewpager.setVisibility(View.VISIBLE);
                lvTabLead.setVisibility(View.GONE);
            } else { //未显示引导界面
                tabviewpager.setVisibility(View.GONE);
                lvTabLead.setVisibility(View.VISIBLE);
            }
            showLead();
        } else if (mDoctype == 2) {
            tabviewpager.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    //收到消息，更新首页红点显示
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateImMessageEntity userEvent) {
        Log.d(TAG, "收到更新首页红点消息");
        // GetAllRecordForDoctor();
        //  refreshView(userEvent.ImId);
        if (userEvent != null) {
            if (("-1").equals(userEvent.serviceCode)) {
                updateMdtReadNumber();
            } else {
                showUnreadTip();
            }
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventRecent event) {
        Log.d(TAG, "收到新消息" + event.getmType());
        switch (event.getmType()) {

            case MainConstant.UpdateConversation:  //患者报道
                refreshView();  //刷新im红点显示
                QueryCountDoctorOrderMerge();
                break;
            case MainConstant.UPDATEPUSHMESSAGELIST:
                getUnreadUnm();
                break;

            case MainConstant.UpdateRecentList:
                refreshView();
                updateMdtReadNumber();
                break;
            //接诊或者退诊
            case MainConstant.acceprtorrefuse:
                updateMdtReadNumber();
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginSuccess event) {
        Log.d(TAG, "im登录成功" + event.isIssuccess());
        if (event.isIssuccess()) {
            mHandler.sendEmptyMessageDelayed(LOADING_SUCCESS, 1500);

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdatePushView userEvent) {
        int action = userEvent.action;
        switch (action) {
            case 1:
                getUnreadUnm();
                break;
            case 2:
                // requestAllNewReportPaientMessage();
                getNewReportUnreadUnm();
                getUnreadUnm();
                break;
            case 3:
                //胎心判读
                getConsultRecordList();
                break;
            case 4:
                //药师
                Log.d(TAG, "收到更新药师通知消息");
                intTotalYaoshi = intTotalYaoshi - 1;
                if (intTotalYaoshi > 0) {
                    rvChufangUnreadNum.setVisibility(View.VISIBLE);
                    tvUnreadNumChufang.setText(intTotalYaoshi + "");
                } else {
                    rvChufangUnreadNum.setVisibility(View.GONE);

                }

                break;

            case 5:
                getOnlinePartyList(1, "0");
                break;
            case 6:
                //第二诊疗红点刷新
                //queryDoctorMedicalRecords();
                QueryCountDoctorOrderMerge();
                break;
            case 11:
                getAllUseFunction();
                QueryCountDoctorOrderMerge();
                updateMdtReadNumber();
                break;
            case 7:
                changeAuthentication();

                break;
            case 8:
                GetNoticeManagePageList();

                break;

            case 12:
                // showLead();

                break;
        }

    }

    //展示引导层
    private void showLead() {
        NewbieGuide.with(getActivity())
                .setLabel("grid_view_guide")
                .alwaysShow(false)
                .setOnGuideChangedListener(new OnGuideChangedListener() {
                    @Override
                    public void onShowed(Controller controller) {
                        Log.e(TAG, "引导层显示");
                        //引导层显示
                        //  SpUtils.put(Contants.Haslogin, true);
                    }

                    @Override
                    public void onRemoved(Controller controller) {
                        Log.e(TAG, "引导层消失");
                        //引导层消失（多页切换不会触发）
                        SpUtils.put(Contants.Haslogin, true);
                        //  EventBus.getDefault().post(new UpdatePushView(12));
                        tabviewpager.setVisibility(View.VISIBLE);
                        lvTabLead.setVisibility(View.GONE);

                    }
                })

                //引导层1
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(lvTodayJobChild, HighLight.Shape.RECTANGLE)
                        .setEverywhereCancelable(true)
                        .setLayoutRes(R.layout.view_guide3, R.id.next_step).setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, final Controller controller) {
                                TextView textView = view.findViewById(R.id.jump_step);
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        controller.remove();
                                    }
                                });
                            }
                        }))
                //引导层2
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(lvTab1, HighLight.Shape.RECTANGLE)
                        .setEverywhereCancelable(true)
                        .setLayoutRes(R.layout.view_guide, R.id.next_step).setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, final Controller controller) {
                                TextView textView = view.findViewById(R.id.jump_step);
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        controller.remove();
                                    }
                                });
                            }
                        }))
                //引导层3
                .addGuidePage(GuidePage.newInstance().addHighLight(lvTab4, HighLight.Shape.RECTANGLE)
                        .setEverywhereCancelable(true)
                        .setLayoutRes(R.layout.view_guide2, R.id.next_step).setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, final Controller controller) {
                                TextView textView = view.findViewById(R.id.jump_step);
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        controller.remove();
                                    }
                                });
                            }
                        })
                )
                //引导层4
                .addGuidePage(GuidePage.newInstance().addHighLight(helpCenter, HighLight.Shape.RECTANGLE)
                        .setEverywhereCancelable(true)
                        .setLayoutRes(R.layout.view_guide4, R.id.next_step).setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, final Controller controller) {
                                TextView textView = view.findViewById(R.id.jump_step);
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        controller.remove();
                                    }
                                });
                            }
                        })
                )
                .show();
    }

    /**
     * 改变医生认证状态
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void authEvent(String event) {
        if (event.equals(AuthenticationCommentUtil.AUTH)) {
            changeAuthentication();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(DoctorInfoByIdEntity doctorInfoByIdEntity) {
        mDoctorInfoByIdEntity = doctorInfoByIdEntity;
        MyApplication.mDoctorInfoByIdEntity = doctorInfoByIdEntity;
        mIsOnLine = mDoctorInfoByIdEntity.getIsOnline();
        Log.e("tag", "userEventBus: " + mIsOnLine);
        setOnLineStatus(mIsOnLine);

        topKeshi.setText(mDoctorInfoByIdEntity.getDepartmentName() + "");
        topLevel.setText(mDoctorInfoByIdEntity.getPositionName() + "");
    }


    //刷新首页红点显示
    private void showUnreadTip() {
        mPictureUnRead = 0;
        mVideoUnRead = 0;
        mRenewalUnRead = 0;
        List<AllRecordForDoctorEntity> pictureList = SQLiteUtils.getInstance().selectAllUnreadImDataByServiceCode("1101");
        List<AllRecordForDoctorEntity> onRenewalList = SQLiteUtils.getInstance().selectAllUnreadImDataByServiceCode("1103");
        List<AllRecordForDoctorEntity> videoList = SQLiteUtils.getInstance().selectAllUnreadImDataByServiceCode("1102");
        for (int i = 0; i < pictureList.size(); i++) {
            mPictureUnRead = mPictureUnRead + 1;
        }
        for (int i = 0; i < videoList.size(); i++) {
            mVideoUnRead = mVideoUnRead + 1;
        }
        for (int i = 0; i < onRenewalList.size(); i++) {
            mRenewalUnRead = mRenewalUnRead + 1;
        }


        Log.i("HomeFragment", "mVideoUnRead=" + mVideoUnRead);
        if (getActivity() == null) {
            return;
        }
        for (int k = 0; k < listuse.size(); k++) {
            int type = listuse.get(k).getAppID();
            switch (type) {
                case 1:
                    listuse.get(k).setUnReadNum(mPictureUnRead);
                    mFunctionAdapter.notifyItemChanged(k);
                    mtabAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    listuse.get(k).setUnReadNum(mRenewalUnRead);
                    mFunctionAdapter.notifyItemChanged(k);
                    mtabAdapter.notifyDataSetChanged();
                    break;
                case 8:
                    listuse.get(k).setUnReadNum(mVideoUnRead);
                    mFunctionAdapter.notifyItemChanged(k);
                    mtabAdapter.notifyDataSetChanged();
                    break;
            }
        }
        if (mPictureUnRead > 0) {
            Log.i("OnlineConsulting", "come here");
            pictureUnreadNumLayout.setVisibility(View.VISIBLE);
            pictureUnreadNum.setText(String.valueOf(mPictureUnRead));
        } else {
            pictureUnreadNumLayout.setVisibility(View.GONE);
        }
        if (mVideoUnRead > 0) {
            videoUnreadNumLayout.setVisibility(View.VISIBLE);
            videoUnreadNum.setText(String.valueOf(mVideoUnRead));
        } else {
            videoUnreadNumLayout.setVisibility(View.GONE);
        }
        if (mRenewalUnRead > 0) {
            renewalUnreadNumLayout.setVisibility(View.VISIBLE);
            renewalUnreadNum.setText(String.valueOf(mRenewalUnRead));
        } else {
            renewalUnreadNumLayout.setVisibility(View.GONE);
        }

    }


    /**
     * 医生状态(0-未审核，1-审核通过，2-审核失败，3-审核中)
     */
    private void changeAuthentication() {
        int status = SpUtils.getInt(Contants.Status, -1);
        if (status == 0) {
            tvAuthentication.setText("未认证");
        } else if (status == 2) {
            tvAuthentication.setText("认证失败");
        } else if (status == 3) {
            tvAuthentication.setText("认证中");
        } else if (status == 1) {
            tvAuthentication.setText("已认证");
        } else {
            tvAuthentication.setText("未认证");
        }
    }

    private void updateMdtReadNumber() {
        total = 0;
        groupUnred = 0;
        Log.d("群组", "查询了一次群红点");
        QueryUnReceiveBuyRecordPage1("1");

    }


    private void getAllPushList() {
        List<PushDataDaoEntity> list = SQLiteUtils.getInstance().selectAllContactsByDoctorId(mId);
        if (list.size() > 0) {
            Date time = list.get(0).getTime();
            String dateTime = mFormatter.format(time);
            int msgId = list.get(0).getMsgId();
            QueryDoctorAppMessageByPage(String.valueOf(SpUtils.getInt(Contants.Id, 0)), dateTime, msgId);
        } else {
            QueryDoctorAppMessageByPage(String.valueOf(SpUtils.getInt(Contants.Id, 0)), null, 0);
        }

    }

    private void getUnreadUnm() {
        List<PushDataDaoEntity> systemList = SQLiteUtils.getInstance().selectAllSystemContactsByDoctorId(mId);
        if (getActivity() == null) {
            return;
        }
        int sysUnreadNum = 0;
        for (int i = 0; i < systemList.size(); i++) {
            boolean isread = systemList.get(i).isRead;
            if (!isread) {
                sysUnreadNum = sysUnreadNum + 1;
            }
        }
        if (sysUnreadNum > 0) {
            systemUnreadNumLayout.setVisibility(View.VISIBLE);
            lvSystemUnred.setVisibility(View.VISIBLE);//新增

            if (sysUnreadNum > 99) {
                systemUnreadNum.setText("99+");
                tvUnreadNum.setText("99+");
            } else {
                systemUnreadNum.setText(String.valueOf(sysUnreadNum));

                tvUnreadNum.setText(String.valueOf(sysUnreadNum));//新增
            }

        } else {
            systemUnreadNumLayout.setVisibility(View.GONE);
            systemUnreadNum.setText(String.valueOf(0));

            lvSystemUnred.setVisibility(View.GONE);//新增
            tvUnreadNum.setText(String.valueOf(0));//新增

        }
        try {
            systemContent.setText(systemList.get(0).getContent());

        } catch (Exception e) {
            systemContent.setText("系统消息、提醒、通知等");

        }

    }

    private void getNewReportUnreadUnm() {
        List<PushDataDaoEntity> systemList = SQLiteUtils.getInstance().selectAllReportsContactsByDoctorId(mId);
        if (getActivity() == null) {
            return;
        }
        int sysUnreadNum = 0;
        for (int i = 0; i < systemList.size(); i++) {
            boolean isread = systemList.get(i).isRead;
            if (!isread) {
                sysUnreadNum = sysUnreadNum + 1;
            }
        }

        if (sysUnreadNum > 0) {
            reportsUnreadNumLayout.setVisibility(View.VISIBLE);
            if (sysUnreadNum > 99) {
                reportsUnreadNum.setText("99+");
            } else {
                reportsUnreadNum.setText(String.valueOf(sysUnreadNum));
            }

        } else {
            reportsUnreadNumLayout.setVisibility(View.GONE);
            reportsUnreadNum.setText(String.valueOf(0));
        }


    }

    //获取所有推送消息
    private void QueryDoctorAppMessageByPage(String id, final String time, final int messageId) {
        loading(true);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("DoctorId", id);
        requestMap.put("IsRead", "0");
        requestMap.put("PageSize", "10000");
        requestMap.put("AppId", "2d77c7dfc65d033c8c3f5f89");
        if (time != null) {
            requestMap.put("BeginTime", time);
        }
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.QueryDoctorAppMessageByPage).headers(headMap).params(requestMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<JpushDataListEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<JpushDataListEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {

                    if (entity.getData().getReturnData() != null && entity.getData().getReturnData().size() > 0) {
                        for (int i = 0; i < entity.getData().getReturnData().size(); i++) {
                            Log.i("HomeFragmenttime", "title=" + time + ",t=" + entity.getData().getReturnData().get(i).getSendTime());
                            /*   Log.i("HomeFragment","isequal="+time.equals(entity.getData().getReturnData().get(i).getSendTime()));*/

                            JpushDataEntity jpushDataEntity = entity.getData().getReturnData().get(i);
                            String extras = jpushDataEntity.getExtras();
                            Log.i("HomeFragment", "extras=" + extras);
                            //  ExtrasDataEntity JpushData = mGson.fromJson(extras, ExtrasDataEntity.class);
                            String title = jpushDataEntity.getTitle();
                            String content = jpushDataEntity.getMessage();
                            String time2 = jpushDataEntity.getSendTime();
                            Date dateTime = null;
                            try {
                                dateTime = mFormatter.parse(time2);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            int isRead = jpushDataEntity.getStatus();
                            int msgId = jpushDataEntity.getId();
                            Log.i("HomeFragment", "time=" + time);
                            //  String data = mGson.toJson(extras);
                            SQLiteUtils.getInstance().addPushData(new PushDataDaoEntity(null, title, content, extras, dateTime, isRead == 1, mId, msgId));

                        }
                    }
                    getUnreadUnm();
                    getNewReportUnreadUnm();

                } else {
                    toast(entity.getMessage() + "aaa");
                }
                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("HomeFragment", "2222");
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();


    }


    private void setOnLineStatus(int flag) {
        if (getActivity() == null) {
            return;
        }
        if (flag == 1) {
            ivSwitchButton.setImageResource(R.mipmap.ic_switch_open);

        } else {
            ivSwitchButton.setImageResource(R.mipmap.ic_switch_off);
        }

    }


    private void getConsultRecordList() {
        Map<String, String> map = new HashMap<>();
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("Status", "0");
        map.put("PageIndex", "1");
        map.put("PageSize", "10000");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.getAllReadData).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<AllDoctorCheckEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<AllDoctorCheckEntity> entity) {

                if (entity.getCode() == 0) {
                    if (getActivity() == null) {
                        return;
                    }
                    AllDoctorCheckEntity consultDataEntity = entity.getData();

                    if (consultDataEntity != null && consultDataEntity.getTotal() > 0) {
                        //需要显示红点
                        rvHeartUnreadNum.setVisibility(View.VISIBLE);
                        tvUnreadNumHeart.setText(consultDataEntity.getTotal() + "");
                        Log.d("xxxxxx", "111111111111");
                        for (int i = 0; i < listuse.size(); i++) {
                            int type = listuse.get(i).getAppID();
                            if (type == 4) {
                                listuse.get(i).setUnReadNum(consultDataEntity.getTotal());
                                mFunctionAdapter.notifyItemChanged(i);
                                mtabAdapter.notifyDataSetChanged();
                            }
                        }
                    } else {
                        //不需要显示胎心判读红点
                        rvHeartUnreadNum.setVisibility(View.GONE);
                        Log.d("xxxxxx", "22222222");
                    }
                } else {
                    rvHeartUnreadNum.setVisibility(View.GONE);
                    Log.d("xxxxxx", "33333333");
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.d("aaaaaaaaaa", "TOKEN失效");
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void checkUpdate() {

        HomeUtils.INSTANCE.checkVersion(new HomeUtils.UpdateListener() {
            @Override
            public void success(final AppEntity entity) {
                AppUpdateUtils.mAppLicationUtils = null;
                AppUpdateUtils.getInstance().update(entity, getContext(), getActivity());

            }


            @Override
            public void failed(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);

            }
        });
    }


    private boolean checkPermission() {

        List<String> permissions = new ArrayList<>();
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)) {
            permissions.add(Manifest.permission.CAMERA);
        }

        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        int len = permissions.size();
        if (len != 0) {
            String[] per = new String[len];
            for (int i = 0; i < len; i++) {
                per[i] = permissions.get(i);
            }
            requestPermissions(
                    per,
                    REQUEST_CODE);
            return false;
        }
        return true;

    }

    protected void getOnlinePartyList(int index, String s) {
        Map<String, String> map = new HashMap<>();
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("OrgId", String.valueOf(SpUtils.getInt(Contants.OrgId, 0)));
        //  map.put("DoctorName", SpUtils.getString(Contants.Name));
        map.put("Diagnoses", "");
        map.put("PrescriptionNo", "");
        map.put("Status", "0"); //状态（-1：全部，0：待审核，1：已审核，2：被驳回）（必填）
        map.put("AuditorId", String.valueOf(SpUtils.getInt(Contants.Id, 0))); //状态（-1：全部，0：待审核，1：已审核，2：被驳回）（必填）
        map.put("PageIndex", index + "");
        map.put("PageSize", "10");
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.PrescriptionCheckList).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<PrescriptionEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<PrescriptionEntity> entity) {
                LoadDialog.clear();
                Log.d("xxxxxx", entity.toString());
                if (entity.getCode() == 0) {
                    PrescriptionEntity onlineRenewalMessageEntity = entity.getData();
                    Log.d("xxxxxx", onlineRenewalMessageEntity.toString());
                    if (onlineRenewalMessageEntity != null) {
                        intTotalYaoshi = onlineRenewalMessageEntity.getTotal();
                        if (onlineRenewalMessageEntity.getTotal() > 0) {
                            rvChufangUnreadNum.setVisibility(View.VISIBLE);
                            tvUnreadNumChufang.setText(onlineRenewalMessageEntity.getTotal() + "");

                        } else {
                            rvChufangUnreadNum.setVisibility(View.GONE);

                        }
                    } else {
                        rvChufangUnreadNum.setVisibility(View.GONE);
                    }
                } else {
                    rvChufangUnreadNum.setVisibility(View.GONE);

                    toast(entity.getMessage());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void queryDoctorMedicalRecords() {
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        params.put("MedicalType", "1");
        params.put("MedicalStatus", "1");
        params.put("PageIndex", "1");
        params.put("PageSize", "1");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.QueryDoctorMedicalRecords).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<DoctorMedicalRecordsEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<DoctorMedicalRecordsEntity> entity) {

                final DoctorMedicalRecordsEntity doctorMedicalRecordsEntity = entity.getData();
                Log.i("DiagnosisAndTreat", "entity=" + doctorMedicalRecordsEntity.toString());
                if (entity.getCode() == 0) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int unReadNum = doctorMedicalRecordsEntity.getTotal();
                            if (unReadNum > 0) {
                                rvTreatmentUnreadNum.setVisibility(View.VISIBLE);
                                tvUnreadNumTreatment.setText(String.valueOf(unReadNum));
                                for (int i = 0; i < listuse.size(); i++) {
                                    int type = listuse.get(i).getAppID();
                                    if (type == 3) {
                                        listuse.get(i).setUnReadNum(unReadNum);
                                        mFunctionAdapter.notifyItemChanged(i);
                                        mtabAdapter.notifyDataSetChanged();
                                    }
                                }
                            } else {
                                rvTreatmentUnreadNum.setVisibility(View.GONE);
                            }
                        }
                    });
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

    private void refreshView() {
        try {

            List<TIMConversation> conversionList = TIMManager.getInstance().getConversionList();

            Log.d(TAG, "最近会话列表单聊----" + conversionList.size());
            List<String> doctorIdList = new ArrayList<>();
            for (TIMConversation timConversation : conversionList) {
                Log.d(TAG, "最近会话列表单聊" + timConversation.getType());
                if (timConversation.getType() == TIMConversationType.C2C) {
                    String id = timConversation.getPeer();
                    Log.d(TAG, "打印单聊id" + id);
//                    if (!TextUtils.isEmpty(id) && id.contains("pat")) {
//                        doctorIdList.add(id);
//                    }
                    doctorIdList.add(id);
                }
            }
            if (doctorIdList != null && doctorIdList.size() > 0) {
                Log.d(TAG, "最近会话列表2----" + doctorIdList.size() + "   " + doctorIdList.toString());
                TIMFriendshipManager.getInstance().getUsersProfile(doctorIdList, new TIMValueCallBack<List<TIMUserProfile>>() {
                    @Override
                    public void onError(int i, String s) {
                        // toast(s);
                    }

                    @Override
                    public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                        mConversationListData.clear();
                        Log.d(TAG, "最近会话列表3----" + timUserProfiles.size());
                        for (int i = 0; i < timUserProfiles.size(); i++) {
                            TIMUserProfile tIMUserProfile = timUserProfiles.get(i);
                            ConversationEntity conversationEntity = new ConversationEntity();
                            //获取会话扩展实例
                            TIMConversation con = TIMManager.getInstance().getConversation(TIMConversationType.C2C, tIMUserProfile.getIdentifier());
                            long unreadMessageNum = con.getUnreadMessageNum();
                            conversationEntity.setUnReadNum(unreadMessageNum);
                            List<TIMMessage> lastMsgs = con.getLastMsgs(1);
                            TIMMessage msg = lastMsgs.get(0);
                            conversationEntity.setTimMessage(lastMsgs.get(0));
                            TIMElem element = msg.getElement(0);
                            if (element.getType() == TIMElemType.Text) {
                                TIMTextElem textElem = (TIMTextElem) element;
                                conversationEntity.setLastMsg(textElem.getText());
                            } else if (element.getType() == TIMElemType.Sound) {
                                conversationEntity.setLastMsg("[语音消息]");
                            } else if (element.getType() == TIMElemType.Video) {
                                conversationEntity.setLastMsg("[视频消息]");
                            } else if (element.getType() == TIMElemType.Image) {
                                conversationEntity.setLastMsg("[图片消息]");
                            } else if (element.getType() == TIMElemType.Custom) {
                                TIMCustomElem customElem = (TIMCustomElem) msg.getElement(0);
                                String s = new String(customElem.getData());
                                CustomMessageEntity customMessageEntity = mGson.fromJson(s, CustomMessageEntity.class);
                                Log.d(TAG, "自定义消息----" + s);
                                if (customMessageEntity != null) {
                                    Log.d(TAG, "自定义消息pushdesc" + customMessageEntity.getPushDesc());
                                    if (!TextUtils.isEmpty(customMessageEntity.getPushDesc())) {
                                        conversationEntity.setLastMsg(customMessageEntity.getPushDesc());
                                    } else if (!TextUtils.isEmpty(customMessageEntity.getTitle())) {
                                        conversationEntity.setLastMsg(customMessageEntity.getTitle());
                                    } else {
                                        conversationEntity.setLastMsg("[系统消息]");
                                    }
                                }
                            }

                            long timestamp = msg.timestamp();
                            long unReadNum = conversationEntity.getUnReadNum();
                            String nickName = tIMUserProfile.getNickName();
                            String id = tIMUserProfile.getIdentifier();
                            String faceUrl = tIMUserProfile.getFaceUrl();
                            String lastMsg = conversationEntity.getLastMsg();

                            Log.d(TAG, "获取头像地址----" + faceUrl);

                            ImDataEntity dataBean = new ImDataEntity(null, lastMsg, timestamp, id, faceUrl, nickName, unReadNum);
                            mConversationListData.add(dataBean);
                        }
                        Collections.sort(mConversationListData, new SortByTime());
                        mAdapter.setNewData(mConversationListData);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                mAdapter.setNewData(mConversationListData);
                mAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {

        }
    }

    class SortByTime implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            ImDataEntity s1 = (ImDataEntity) o1;
            ImDataEntity s2 = (ImDataEntity) o2;
            if (s1.getLastTime() < s2.getLastTime()) {
                return 1;
            }

            return -1;
        }
    }


    private class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // Log.d(TAG,"数据长度"+listuse.size());
            HomeTabFragment homeTabFragment = new HomeTabFragment();
            homeTabFragment.setdata(listuse, position);
            homeTabFragment.setview(helpCenter, lvTodayJobChild);
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
            HomeTabFragment fragment = (HomeTabFragment) super.instantiateItem(container, position);
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

    private void tabitemclick(AppLicationEntity appLicationEntity) {

        int type = appLicationEntity.getAppID();
        Log.d(TAG, "分级转诊" + type);
        switch (type) {
            case 0:
                Intent audio = new Intent(getContext(), AllApplicationActivity.class);
                startActivity(audio);
                break;
            //图文问诊
            case 1:
                Intent intent = new Intent(getActivity(), OnlineConsultingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("unReadList", (Serializable) mPictureUnreadMessageList);
                bundle.putSerializable("allUnReadList", (Serializable) mAllUnreadMessageList);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            //在线续方
            case 2:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, getContext());
                } else {
                    Intent onlineRenewalPartyIntent = new Intent(mContext, OnlineRenewalPartyActivity.class);
                    Bundle renewalBundle = new Bundle();
                    renewalBundle.putSerializable("allUnReadList", (Serializable) mAllUnreadMessageList);
                    renewalBundle.putSerializable("unReadList", (Serializable) mRenewalUnreadMessageList);
                    onlineRenewalPartyIntent.putExtras(renewalBundle);
                    startActivity(onlineRenewalPartyIntent);
                }
                break;
            case 3:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, getContext());
                } else {
                    Intent treatIntent = new Intent(getActivity(), TreatActivity.class);
                    startActivity(treatIntent);
                }
                break;
            case 4:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                //toast("胎心判读");
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, getContext());
                } else {
                    //toast("胎心判读");
                    Intent intentCheckCenter = MyCheckCenterActivity.newIntent(getContext());
                    startActivity(intentCheckCenter);

                }
                break;
            case 5:
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, getContext());
                } else {
                    Intent performanceIntent = new Intent(getActivity(), MypharmacyActivity.class);
                    performanceIntent.putExtra("action", 2);
                    startActivity(performanceIntent);
                }

                break;
            case 6:
                Intent pharmacyIntent = new Intent(getActivity(), MypharmacyActivity.class);
                pharmacyIntent.putExtra("action", 1);
                startActivity(pharmacyIntent);
                break;
            case 7:
                Intent idCardIntent = new Intent(getContext(), DoctorHomeCardActivity.class);
                idCardIntent.putExtra("title", "我的名片");
                idCardIntent.putExtra("doctorInfoByIdEntity", mDoctorInfoByIdEntity);
                startActivity(idCardIntent);
                break;
            case 8:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, getContext());
                } else {
                    Intent videoInterrogationIntent = new Intent(mContext, VideoInterrogationActivity.class);
                    Bundle videoBundle = new Bundle();
                    videoBundle.putSerializable("unReadList", (Serializable) mVideoUnreadMessageList);
                    videoBundle.putSerializable("allUnReadList", (Serializable) mAllUnreadMessageList);
                    videoInterrogationIntent.putExtras(videoBundle);
                    startActivity(videoInterrogationIntent);
                }

                break;

            case 9:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, getContext());
                } else {
                    ExistDoctorDisGroup();
                }
                //  ExistDoctorDisGroup();
                break;

            case 10:

                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, getContext());
                } else {

                    Intent fenjizhuanzhen = new Intent(mContext, MDTActivity.class);
                    startActivity(fenjizhuanzhen);
                }

                break;

            case 11:

                Intent adviceintent = new Intent(getContext(), PrescriptionActivity.class);
                adviceintent.putExtra("type", 11);
                adviceintent.putExtra("fromIM", 0);
                adviceintent.putExtra("prescriptionMessage", "");
                startActivity(adviceintent);
                break;

            case 12:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, getContext());
                } else {
                    Intent mianzhenintent = new Intent(getContext(), MianZhenActivity.class);

                    startActivity(mianzhenintent);
                }
                //  ExistDoctorDisGroup();
                break;
            case 13://帮助中心
                Intent helpcenter = new Intent(getContext(), HelpCenterActivity.class);
                startActivity(helpcenter);
                //  ExistDoctorDisGroup();
                break;
        }
    }


    private void QueryUnReceiveBuyRecordPage1(String type) {
        if (isudate == true) {
            return;
        }
        isudate = true;
        Map<String, String> map = new HashMap<>();
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("TriageType", type);
        map.put("PageIndex", "1");
        map.put("PageSize", "10");

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.QueryUnReceiveBuyRecordPage).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTOrderEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTOrderEntity> entity) {

                mTotal1 = entity.getData().getTotal();
                Log.d("群组", "数据mTotal1====" + mTotal1);
                QueryUnReceiveBuyRecordPage2("2");

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {

            }
        });
    }

    private void QueryUnReceiveBuyRecordPage2(String type) {

        Map<String, String> map = new HashMap<>();
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("TriageType", type);
        map.put("PageIndex", "1");
        map.put("PageSize", "10");

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.QueryUnReceiveBuyRecordPage).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTOrderEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTOrderEntity> entity) {
                mTotal2 = entity.getData().getTotal();
                Log.d(TAG, "数据mTotal2====" + mTotal2);
                total = mTotal1 + mTotal2;
                getGroupUnred();

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {

            }
        });
    }

    private void getGroupUnred() {
        List<TIMConversation> conversionList = TIMManager.getInstance().getConversionList();
        Log.d("群组", "最近会话列表群----" + conversionList.size());
        mDoctorIdList = new ArrayList<>();
        mDoctorIdList.clear();
        if (conversionList.size() == 0) {
            for (int k = 0; k < listuse.size(); k++) {
                int type = listuse.get(k).getAppID();
                switch (type) {
                    case 10:
                        listuse.get(k).setUnReadNum(groupUnred + total);
                        mFunctionAdapter.notifyItemChanged(k);
                        mtabAdapter.notifyDataSetChanged();
                        break;
                }
            }
        }

        for (TIMConversation timConversation : conversionList) {
            if (timConversation.getType() == TIMConversationType.Group) {
                String id = timConversation.getPeer();
                mDoctorIdList.add(id);
            }
        }


        if (mDoctorIdList != null && mDoctorIdList.size() > 0) {
            Log.d("群组", "最近会话列表2----" + mDoctorIdList.size() + "   " + mDoctorIdList.toString());
            groupIndex = 0;
            getGroupList(mDoctorIdList);
        } else {

        }
    }


    public void getGroupList(List<String> doctorIdListitem) {
        List<String> list = new ArrayList<>();
        list.clear();
        if (doctorIdListitem.size() <= groupIndex * 50) {
            for (int k = 0; k < listuse.size(); k++) {
                int type = listuse.get(k).getAppID();
                switch (type) {
                    case 10:
                        listuse.get(k).setUnReadNum(groupUnred + total);
                        mFunctionAdapter.notifyItemChanged(k);
                        mtabAdapter.notifyDataSetChanged();
                        break;
                }
            }

            isudate = false;
            return;
        }
        if (doctorIdListitem.size() > groupIndex * 50 + 50) {
            list.addAll(doctorIdListitem.subList(groupIndex * 50, groupIndex * 50 + 50));
        } else {
            list.addAll(doctorIdListitem.subList(groupIndex * 50, doctorIdListitem.size()));
        }

        TIMGroupManager.getInstance().getGroupDetailInfo(list, new TIMValueCallBack<List<TIMGroupDetailInfo>>() {
            @Override
            public void onError(int i, String s) {
                Log.d("群组", "获取群消息失败----");
                isudate = false;
                for (int k = 0; k < listuse.size(); k++) {
                    int type = listuse.get(k).getAppID();
                    switch (type) {
                        case 10:
                            listuse.get(k).setUnReadNum(groupUnred + total);
                            mFunctionAdapter.notifyItemChanged(k);
                            mtabAdapter.notifyDataSetChanged();
                            break;
                    }
                }
            }

            @Override
            public void onSuccess(List<TIMGroupDetailInfo> timGroupDetailInfos) {
                groupIndex++;
                Log.d("群组", "最近会话列表3----" + timGroupDetailInfos.size());
                for (int i = 0; i < timGroupDetailInfos.size(); i++) {
                    TIMGroupDetailInfo tIMUserProfile = timGroupDetailInfos.get(i);
                    ConversationEntity conversationEntity = new ConversationEntity();
                    //获取会话扩展实例
                    TIMConversation con = TIMManager.getInstance().getConversation(TIMConversationType.Group, tIMUserProfile.getGroupId());
                    long unreadMessageNum = con.getUnreadMessageNum();
                    if (unreadMessageNum > 0) {

                        groupUnred++;

                    }

                    getGroupList(mDoctorIdList);
                    Log.d("群组", "数据mTotal3====" + groupUnred);
//                    for (int k = 0; k < listuse.size(); k++) {
//                        int type = listuse.get(k).getAppID();
//                        switch (type) {
//                            case 10:
//                                listuse.get(k).setUnReadNum(groupUnred + total);
//                                mFunctionAdapter.notifyItemChanged(k);
//                                mtabAdapter.notifyDataSetChanged();
//                                break;
//                        }
//                    }

                }
            }
        });


    }

    private void getDoctorInfo() {

        Map<String, String> map = new HashMap<>();
        map.put("AuditorId", String.valueOf(SpUtils.getInt(Contants.Id, 0))); //状态（-1：全部，0：待审核，1：已审核，2：被驳回）（必填）
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.getPrescriptionDetail).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<PrescriptionDataEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<PrescriptionDataEntity> entity) {
                LoadDialog.clear();

                if (entity.getCode() == 0) {
                    PrescriptionDataEntity prescriptionDataEntity = entity.getData();
                    if (prescriptionDataEntity != null) {
                        todayTask.setText(entity.getData().getPendingCount() + "");
                        acceptTask.setText(entity.getData().getPassCount() + "");
                        futureTask.setText(entity.getData().getRejectCount() + "");

                    } else {

                    }
                } else {

                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mDoctype == 3) {
            getDoctorInfo();
        } else {
            QueryCountDoctorOrderMerge();

        }

    }


}
