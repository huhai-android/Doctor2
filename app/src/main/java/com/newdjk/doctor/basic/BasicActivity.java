package com.newdjk.doctor.basic;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.ColorUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.lxq.okhttp.MyOkHttp;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.lxq.okhttp.utils.ReceivedCookiesInterceptor;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.service.MyService;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.AllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.AllRecordForDoctorMessageEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UpdateImMessageEntity;
import com.newdjk.doctor.ui.entity.UpdateImStatusEntity;
import com.newdjk.doctor.utils.AppUtils;
import com.newdjk.doctor.utils.LogUtils;
import com.newdjk.doctor.utils.SQLiteUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.utils.TokenInterceptor;
import com.newdjk.doctor.utils.UtilsStyle;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.TitleBuilder;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2018-3-12.
 */

public abstract class BasicActivity<T> extends FragmentActivity implements View.OnClickListener {

    // 管理activity

    private static final String TAG = "BasicActivity";
    public static BasicActivity activity;
    protected Context mContext;
    protected Activity mActivity;
    protected boolean isBlack = true;
    protected TitleBuilder titleBuilder;
    protected MyOkHttp mMyOkhttp;
    public String serviceTime;
    public SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd");
    public static final String MYSERVICE = "com.newdjk.doctor.service.MyService";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        setContentView(initViewResId());
        ButterKnife.bind(this);
        //设置透明
        mContext = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

        }

      //  设置状态栏上的字体为黑色-因为本页面是白色必须设置
        UtilsStyle.statusBarLightMode(this);
        //  setStatusBarColor(this,R.color.tm);
        setAndroidNativeLightStatusBar(this,true);

        titleBuilder = new TitleBuilder(this);
        mActivity = this;
        mMyOkhttp = MyApplication.getInstance().getMyOkHttp();

        initView();
        initListener();
        initData();
    }

    public static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    protected void changeStatusBarTextColor(boolean isBlack) {
       // if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (isBlack) {
                //设置状态栏黑色字体
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            } else {
                //恢复状态栏白色字体
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
      //  }
    }

    /**
     * 在setConvetView之前
     */
    public void init() {
    }

    /**
     * 初始化界面
     */
    protected abstract int initViewResId();

    /**
     * 初始化界面
     */
    protected abstract void initView();

    /**
     * 初始化界面
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 点击事件
     *
     * @param view
     */
    protected abstract void otherViewClick(View view);

    /**
     * 点击的事件的统一的处理
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                otherViewClick(view);
                break;
        }
    }

    /**
     * @param str 日志的处理
     */
    protected void logE(String str) {
        LogUtils.e("" + this.getClass().getSimpleName(), str);
    }

    /**
     * @param str 显示一个内容为str的toast
     */
    protected void toast(String str) {
        ToastUtil.setToast(str);
    }

    //标题栏 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 左侧没有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param title 标题
     */
    protected TitleBuilder initTitle(String title) {
        return titleBuilder.setTitleText(title);
    }

    /**
     * 左侧有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param title 标题
     */
    protected TitleBuilder initBackTitleBg(String title) {
        return new TitleBuilder(mActivity).setTitleBgRes(R.color.tm).setTitleText(title)
                .setLeftImage(R.drawable.head_back_selectot)
                .setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    /**
     * 左侧有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param title 标题
     */
    protected TitleBuilder initBackTitleBgRes(int color, String title) {
        return new TitleBuilder(mActivity).setTitleBgRes(color).setTitleText(title)
                .setLeftImage(R.drawable.head_back_selectot)
                .setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    /**
     * 左侧有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param title 标题
     */
    protected TitleBuilder initBackTitle(String title) {
        return new TitleBuilder(mActivity)
                .setTitleText(title)
                .setLeftImage(R.drawable.head_back_selectot)
                .setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    /**
     * 左侧有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param txt 右边文字
     */
    protected TitleBuilder initTitleRightStr(String txt, View.OnClickListener rightOnCLick) {
        return new TitleBuilder(mActivity)
                .setRightText(txt)
                .setLeftOnClickListener(rightOnCLick);
    }

    /**
     * 左侧有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param ResId 右边图片
     */
    protected TitleBuilder initTitleRightImg(int ResId, View.OnClickListener rightOnCLick) {
        return new TitleBuilder(mActivity)
                .setRightImage(ResId)
                .setLeftOnClickListener(rightOnCLick);
    }
    //标题栏 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //启动新Activity方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 打开新的Activity，向左滑入效果
     *
     * @param intent
     */
    public void toActivity(Intent intent) {
        toActivity(intent, true);
    }

    /**
     * 打开新的Activity
     *
     * @param intent
     * @param showAnimation
     */
    public void toActivity(Intent intent, boolean showAnimation) {
        toActivity(intent, -1, showAnimation);
    }

    /**
     * 打开新的Activity，向左滑入效果
     *
     * @param intent
     * @param requestCode
     */
    public void toActivity(Intent intent, int requestCode) {
        toActivity(intent, requestCode, true);
    }

    /**
     * 打开新的Activity
     *
     * @param intent
     * @param requestCode
     * @param showAnimation
     */
    public void toActivity(final Intent intent, final int requestCode, final boolean showAnimation) {
        if (intent == null) {
            logE("toActivity  intent == null >> return;");
            return;
        }
        //fragment中使用context.startActivity会导致在fragment中不能正常接收onActivityResult
        if (requestCode < 0) {
            startActivity(intent);
        } else {
            startActivityForResult(intent, requestCode);
        }
        if (showAnimation) {
            overridePendingTransition(R.anim.right_push_in, R.anim.hold);
        } else {
            overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
        }
    }
    //启动新Activity方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //手机返回键和菜单键实现同点击标题栏左右按钮效果<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    /**
     * 退出时之前的界面进入动画,可在finish();前通过改变它的值来改变动画效果
     */
    protected int enterAnim = R.anim.fade;
    /**
     * 退出时该界面动画,可在finish();前通过改变它的值来改变动画效果
     */
    protected int exitAnim = R.anim.right_push_out;

    @Override
    public void onBackPressed() {
        finish();
        LoadDialog.clear();
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();//必须写在最前才能显示自定义动画
        if (enterAnim > 0 && exitAnim > 0) {
            try {
                overridePendingTransition(enterAnim, exitAnim);
            } catch (Exception e) {
                logE("finish overridePendingTransition(enterAnim, exitAnim);" +
                        " >> catch (Exception e) {  " + e.getMessage());
            }
        }
        LoadDialog.clear();
    }

    /**
     * 退出
     */
    public void killAll() {
        MyApplication.exit();
        finish();
      /*  Intent intent = new Intent(BasicActivity.this, MyService.class);
        stopService(intent);*/
        // android.os.Process.killProcess(android.os.Process.myPid());
    }
    //手机返回键和菜单键实现同点击标题栏左右按钮效果>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (MyApplication.mActivities) {
            MyApplication.remove(this);
        }
        if (mMyOkhttp!=null){
            mMyOkhttp.cancel(this);
        }
        LoadDialog.clear();
        EventBus.getDefault().unregister(this);//
    }

    @Override
    protected void onPause() {
        super.onPause();
        activity = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        activity = this;
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
           // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        //  Log.i(TAG,"isServiceWork="+isServiceWork(this,MYSERVICE)+",mIsCanStartService="+CommonMethod. mIsCanStartService);
        if (CommonMethod.mIsCanStartService && !isServiceWork(this, MYSERVICE)) {
            serviceTime = null;
            try {
                SQLiteUtils.getInstance().deleteAllImData();
                GetAllRecordForDoctor(null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(new Intent(this, MyService.class));
                } else {
                    startService(new Intent(this, MyService.class));
                }
            }catch (Exception e){

            }

        }



    }


    /**
     * 加载效果
     *
     * @param bool
     */
    public void loading(boolean bool) {
        if (bool) {
            LoadDialog.show(this);
        } else {
            LoadDialog.clear();
        }
    }

    /**
     * 加载效果
     *
     * @param bool
     */
    protected void loading(boolean bool, String content) {
        if (bool) {
            LoadDialog.show(this, content);
        } else {
            LoadDialog.clear();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       // PermissionReq.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public boolean isServiceWork(Context mContext, String className) {
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            ActivityManager.RunningServiceInfo serviceInfo = serviceList.get(i);
            ComponentName serviceName = serviceInfo.service;
            if (serviceName.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    public void GetAllRecordForDoctor(String imId) {

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        Map<String, String> map = new HashMap<>();
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        Log.i("MyService", "serviceTime=" + serviceTime + ",Authorization=" + SpUtils.getString(Contants.Token) + ",DoctorId=" + SpUtils.getInt(Contants.Id, -1));
        if (serviceTime != null) {
            map.put("StartTime", serviceTime);
        }
        mMyOkhttp.post().url(HttpUrl.GetAllRecordForDoctor).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<AllRecordForDoctorMessageEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<AllRecordForDoctorMessageEntity> entity) {

                if (entity.getCode() == 0) {
                    AllRecordForDoctorMessageEntity allRecordForDoctorMessageEntity = entity.getData();
                    List<AllRecordForDoctorMessageEntity.ConsultInfoListBean> consultInfoList = allRecordForDoctorMessageEntity.getConsultInfoList();
                    List<AllRecordForDoctorMessageEntity.InquiryInfoListBean> inquiryInfoList = allRecordForDoctorMessageEntity.getInquiryInfoList();
                    List<AllRecordForDoctorMessageEntity.ApplyInfoListBean> ApplyInfoList = allRecordForDoctorMessageEntity.getApplyInfoList();
                    serviceTime = allRecordForDoctorMessageEntity.getServerTime();
                    for (int i = 0; i < consultInfoList.size(); i++) {
                        AllRecordForDoctorMessageEntity.ConsultInfoListBean consultInfoListBean = consultInfoList.get(i);
                        AllRecordForDoctorMessageEntity.ConsultInfoListBean.RecordInfoBean recordInfoBean = consultInfoListBean.getRecordInfo();
                        String Content = recordInfoBean.getContent();
                        String PatientName = recordInfoBean.getPatientName();

                        String EndTime = recordInfoBean.getEndTime();
                        String PayTime = recordInfoBean.getPayTime();
                        String CreateTime = recordInfoBean.getCreateTime();
                        int RecordId = recordInfoBean.getId();
                        int Type = recordInfoBean.getType();
                        int Status = recordInfoBean.getStatus();
                        String DealWithTime = recordInfoBean.getDealWithTime();
                        String StartTime = recordInfoBean.getStartTime();
                        String ApplicantIMId = consultInfoListBean.getPatientIMId();
                        TIMConversation conversation = TIMManager.getInstance().getConversation(
                                TIMConversationType.C2C,
                                ApplicantIMId);
                        long unReadNum = 0;
                        long timeStamp = 0;
                        String DateTime = null;
                        if (conversation != null) {
                            unReadNum = AppUtils.getUnreadMessageNum(conversation);
                            List<TIMMessage> lastMsgs = conversation.getLastMsgs(1);

                            if (lastMsgs != null && lastMsgs.size() > 0) {
                                TIMMessage msg = lastMsgs.get(0);
                                timeStamp = msg.timestamp();
                                DateTime = mFormatter.format(new Date(timeStamp));
                            }
                        }

                        String Age = null;
                        String AreaName = null;
                        int PatientSex = 0;
                        int IsDrKey = 0;
                        int IsPatientMain = 0;
                        String Disease = null;
                        String ApplicantHeadImgUrl = null;
                        AllRecordForDoctorMessageEntity.ConsultInfoListBean.PatientInfoBean PatientInfoBean = consultInfoListBean.getPatientInfo();
                        AllRecordForDoctorMessageEntity.ConsultInfoListBean.DoctorPatientRelationBean DoctorPatientRelationBean = consultInfoListBean.getDoctorPatientRelation();
                        if (PatientInfoBean != null) {
                            Age = PatientInfoBean.getAge();
                            ApplicantHeadImgUrl = PatientInfoBean.getPatientHeadImgUrl();
                            AreaName = PatientInfoBean.getAreaName();
                            PatientSex = PatientInfoBean.getPatientSex();
                        }
                        if (DoctorPatientRelationBean != null) {
                            IsDrKey = DoctorPatientRelationBean.getIsDrKey();
                            IsPatientMain = DoctorPatientRelationBean.getIsPatientMain();
                            Disease = DoctorPatientRelationBean.getDisease();
                        }
                        String ServiceCode = "1101";
                        String RecordData = new Gson().toJson(consultInfoListBean);
                        int ApplicantId = consultInfoListBean.getRecordInfo().getApplicantId();
                        int PatientId = consultInfoListBean.getRecordInfo().getPatientId();
                        List<AllRecordForDoctorEntity> list = SQLiteUtils.getInstance().selectImDataByServiceCodeAndRecord(ServiceCode, RecordId);
                        if (list.size() == 0) {
                            AllRecordForDoctorEntity allRecordForDoctorEntity = new AllRecordForDoctorEntity(null, Content, PatientName, ApplicantHeadImgUrl, EndTime, PayTime, CreateTime, RecordId, Type, Status, DealWithTime, StartTime, ApplicantIMId, unReadNum, null, 0, null, null, null, Age, AreaName, PatientSex, IsDrKey, IsPatientMain, Disease, ServiceCode, timeStamp, RecordData, ApplicantId, PatientId, DateTime);
                            SQLiteUtils.getInstance().addImData(allRecordForDoctorEntity);
                            EventBus.getDefault().post(new UpdateImStatusEntity(allRecordForDoctorEntity));
                        }
                    }
                    for (int i = 0; i < inquiryInfoList.size(); i++) {
                        AllRecordForDoctorMessageEntity.InquiryInfoListBean inquiryInfoListBean = inquiryInfoList.get(i);

                        String Content = inquiryInfoListBean.getRecordInfo().getContent();
                        String PatientName = inquiryInfoListBean.getRecordInfo().getPatientName();

                        String EndTime = inquiryInfoListBean.getRecordInfo().getEndTime();
                        String PayTime = inquiryInfoListBean.getRecordInfo().getPayTime();
                        String CreateTime = inquiryInfoListBean.getRecordInfo().getCreateTime();
                        int RecordId = inquiryInfoListBean.getRecordInfo().getId();
                        int Type = inquiryInfoListBean.getRecordInfo().getType();
                        int Status = inquiryInfoListBean.getRecordInfo().getStatus();
                        String DealWithTime = inquiryInfoListBean.getRecordInfo().getDealWithTime();
                        String StartTime = inquiryInfoListBean.getRecordInfo().getStartTime();
                        String ApplicantIMId = inquiryInfoListBean.getPatientIMId();
                        TIMConversation conversation = TIMManager.getInstance().getConversation(
                                TIMConversationType.C2C,
                                ApplicantIMId);
                        long unReadNum = 0;
                        long timeStamp = 0;
                        String DateTime = null;
                        if (conversation != null) {
                            unReadNum = AppUtils.getUnreadMessageNum(conversation);
                            List<TIMMessage> lastMsgs = conversation.getLastMsgs(1);

                            if (lastMsgs != null && lastMsgs.size() > 0) {
                                TIMMessage msg = lastMsgs.get(0);
                                timeStamp = msg.timestamp();

                            }
                        }


                        String ReExaminationDate = inquiryInfoListBean.getRecordInfo().getReExaminationDate();
                        if (ReExaminationDate != null && !ReExaminationDate.equals("")) {
                            DateTime = ReExaminationDate.substring(0, ReExaminationDate.indexOf(" "));
                        }
                        String ReExaminationStartTime = inquiryInfoListBean.getRecordInfo().getReExaminationStartTime();
                        String ReExaminationEndTime = inquiryInfoListBean.getRecordInfo().getReExaminationEndTime();


                        String Age = null;
                        String AreaName = null;
                        int PatientSex = 0;
                        int IsDrKey = 0;
                        int IsPatientMain = 0;
                        String Disease = null;
                        String ApplicantHeadImgUrl = null;
                        AllRecordForDoctorMessageEntity.InquiryInfoListBean.PatientInfoBeanX PatientInfoBean = inquiryInfoListBean.getPatientInfo();
                        AllRecordForDoctorMessageEntity.InquiryInfoListBean.DoctorPatientRelationBeanX DoctorPatientRelationBean = inquiryInfoListBean.getDoctorPatientRelation();
                        if (PatientInfoBean != null) {
                            Age = PatientInfoBean.getAge();
                            AreaName = PatientInfoBean.getAreaName();
                            ApplicantHeadImgUrl = PatientInfoBean.getPatientHeadImgUrl();
                            PatientSex = PatientInfoBean.getPatientSex();
                        }
                        if (DoctorPatientRelationBean != null) {
                            IsDrKey = DoctorPatientRelationBean.getIsDrKey();
                            IsPatientMain = DoctorPatientRelationBean.getIsPatientMain();
                            Disease = DoctorPatientRelationBean.getDisease();
                        }
                        String ServiceCode = "1102";
                        int ApplicantId = inquiryInfoListBean.getRecordInfo().getApplicantId();
                        String RecordData = new Gson().toJson(inquiryInfoListBean);
                        int PatientId = inquiryInfoListBean.getRecordInfo().getPatientId();
                        List<AllRecordForDoctorEntity> list = SQLiteUtils.getInstance().selectImDataByServiceCodeAndRecord(ServiceCode, RecordId);
                        if (list.size() == 0) {
                            AllRecordForDoctorEntity allRecordForDoctorEntity = new AllRecordForDoctorEntity(null, Content, PatientName, ApplicantHeadImgUrl, EndTime, PayTime, CreateTime, RecordId, Type, Status, DealWithTime, StartTime, ApplicantIMId, unReadNum, null, 0, ReExaminationDate, ReExaminationStartTime, ReExaminationEndTime, Age, AreaName, PatientSex, IsDrKey, IsPatientMain, Disease, ServiceCode, timeStamp, RecordData, ApplicantId, PatientId, DateTime);
                            SQLiteUtils.getInstance().addImData(allRecordForDoctorEntity);
                            EventBus.getDefault().post(new UpdateImStatusEntity(allRecordForDoctorEntity));
                        }
                    }
                    for (int i = 0; i < ApplyInfoList.size(); i++) {
                        AllRecordForDoctorMessageEntity.ApplyInfoListBean applyInfoListBean = ApplyInfoList.get(i);

                        // String Content = applyInfoListBean.getRecordInfo().getContent();
                        String PatientName = applyInfoListBean.getRecordInfo().getPatientName();

                        String EndTime = applyInfoListBean.getRecordInfo().getEndTime();
                        String PayTime = applyInfoListBean.getRecordInfo().getPayTime();
                        String CreateTime = applyInfoListBean.getRecordInfo().getCreateTime();
                        int RecordId = applyInfoListBean.getRecordInfo().getId();
                        int Type = applyInfoListBean.getRecordInfo().getType();
                        int Status = applyInfoListBean.getRecordInfo().getStatus();
                        String DealWithTime = applyInfoListBean.getRecordInfo().getDealWithTime();
                        String StartTime = applyInfoListBean.getRecordInfo().getStartTime();
                        String ApplicantIMId = applyInfoListBean.getPatientIMId();
                        TIMConversation conversation = TIMManager.getInstance().getConversation(
                                TIMConversationType.C2C,
                                ApplicantIMId);
                        long unReadNum = 0;
                        long timeStamp = 0;
                        String DateTime = null;
                        if (conversation != null) {
                            unReadNum = AppUtils.getUnreadMessageNum(conversation);
                            List<TIMMessage> lastMsgs = conversation.getLastMsgs(1);

                            if (lastMsgs != null && lastMsgs.size() > 0) {
                                TIMMessage msg = lastMsgs.get(0);
                                timeStamp = msg.timestamp();
                                DateTime = mFormatter.format(new Date(timeStamp));
                            }
                        }
                      /*  String ReExaminationDate = applyInfoListBean.getRecordInfo().getReExaminationDate();
                        String ReExaminationStartTime  = applyInfoListBean.getRecordInfo().getReExaminationStartTime();
                        String ReExaminationEndTime  = applyInfoListBean.getRecordInfo().getReExaminationEndTime();*/
                        String Diagnoses = applyInfoListBean.getRecordInfo().getDiagnoses();

                        String Age = null;
                        String AreaName = null;
                        int PatientSex = 0;
                        int IsDrKey = 0;
                        int IsPatientMain = 0;
                        String Disease = null;
                        String ApplicantHeadImgUrl = null;
                        AllRecordForDoctorMessageEntity.ApplyInfoListBean.PatientInfoBeanXX PatientInfoBean = applyInfoListBean.getPatientInfo();
                        AllRecordForDoctorMessageEntity.ApplyInfoListBean.DoctorPatientRelationBeanXX DoctorPatientRelationBean = applyInfoListBean.getDoctorPatientRelation();
                        if (PatientInfoBean != null) {
                            Age = PatientInfoBean.getAge();
                            AreaName = PatientInfoBean.getAreaName();
                            ApplicantHeadImgUrl = PatientInfoBean.getPatientHeadImgUrl();
                            PatientSex = PatientInfoBean.getPatientSex();
                        }
                        if (DoctorPatientRelationBean != null) {
                            IsDrKey = DoctorPatientRelationBean.getIsDrKey();
                            IsPatientMain = DoctorPatientRelationBean.getIsPatientMain();
                            Disease = DoctorPatientRelationBean.getDisease();
                        }
                        String ServiceCode = "1103";
                        int ApplicantId = applyInfoListBean.getRecordInfo().getApplicantId();
                        String RecordData = new Gson().toJson(applyInfoListBean);
                        int PatientId = applyInfoListBean.getRecordInfo().getPatientId();
                        List<AllRecordForDoctorEntity> list = SQLiteUtils.getInstance().selectImDataByServiceCodeAndRecord(ServiceCode, RecordId);

                        if (list.size() == 0) {
                            AllRecordForDoctorEntity allRecordForDoctorEntity = new AllRecordForDoctorEntity(null, null, PatientName, ApplicantHeadImgUrl, EndTime, PayTime, CreateTime, RecordId, Type, Status, DealWithTime, StartTime, ApplicantIMId, unReadNum, Diagnoses, 0, null, null, null, Age, AreaName, PatientSex, IsDrKey, IsPatientMain, Disease, ServiceCode, timeStamp, RecordData, ApplicantId, PatientId, DateTime);

                            SQLiteUtils.getInstance().addImData(allRecordForDoctorEntity);
                            EventBus.getDefault().post(new UpdateImStatusEntity(allRecordForDoctorEntity));
                        }
                    }
                    EventBus.getDefault().post(new UpdateImMessageEntity(null));
                    // int k =SQLiteUtils.getInstance().selectSqlImDataCount();
                    //  Log.i("HomeFragment","k="+k);
                } else {
                    Toast.makeText(BasicActivity.this, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }
                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                //mEasylayout.setVisibility(View.GONE);
                Log.i("HomeFragment", "111");
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    /**
     *
     * @param event
     * @return
     * 点击空白处隐藏键盘
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(null != this.getCurrentFocus()){
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super .onTouchEvent(event);

    }

    public void setFillWindow(int color, Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = context.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    /**
     * 修改状态栏颜色，支持4.4以上版本
     * @param activity
     * @param colorId
     */
    public static void setStatusBarColor(Activity activity, int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(colorId));
        }
    }

    public void initOKhttpClient() {

        //持久化存储cookie
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));

        //log拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //自定义OkHttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)       //设置开启cookie
                .addInterceptor(logging)            //设置开启log
                .addInterceptor(new ReceivedCookiesInterceptor())//你定义的cookie接收监听器
                .addInterceptor(new TokenInterceptor())
                .addNetworkInterceptor(new StethoInterceptor()) //添加抓包工具
                .build();
        mMyOkhttp = new MyOkHttp(okHttpClient);
    }



    public void Logout() {
        Map<String, String> map = new HashMap<>();
        map.put("Token", SpUtils.getString(Contants.Token));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.Logout).headers(headMap).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entituy) {


            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {

            }
        });
    }



    /**
     * 判断颜色是不是亮色
     *
     * @param color
     * @return
     * @from https://stackoverflow.com/questions/24260853/check-if-color-is-dark-or-light-in-android
     */
    private static boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }

    /**
     * 获取StatusBar颜色，默认白色
     *
     * @return
     */
    protected @ColorInt int getStatusBarColor() {
        return Color.WHITE;
    }

}
