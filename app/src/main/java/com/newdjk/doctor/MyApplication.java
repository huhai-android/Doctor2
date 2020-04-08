package com.newdjk.doctor;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.bilibili.boxing.BoxingCrop;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingMediaLoader;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.huawei.android.hms.agent.HMSAgent;
import com.liulishuo.filedownloader.FileDownloader;
import com.lxq.okhttp.MyOkHttp;
import com.lxq.okhttp.demo.DownloadMgr;
import com.lxq.okhttp.utils.OkHttpLogUtils;
import com.lxq.okhttp.utils.ReceivedCookiesInterceptor;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.greendao.gen.DaoMaster;
import com.newdjk.doctor.greendao.gen.DaoSession;
import com.newdjk.doctor.iInterface.MyLifecycleHandler;
import com.newdjk.doctor.ui.entity.DoctorInfoByIdEntity;
import com.newdjk.doctor.ui.entity.GotoMainactivity;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.utils.BadgeUtil;
import com.newdjk.doctor.utils.BoxingGlideLoader;
import com.newdjk.doctor.utils.BoxingUcrop;
import com.newdjk.doctor.utils.DefaultMediaLoader;
import com.newdjk.doctor.utils.FixDefaultMediaLoader;
import com.newdjk.doctor.utils.MessageObservable;
import com.newdjk.doctor.utils.TokenInterceptor;
import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushListener;
import com.tencent.TIMOfflinePushNotification;
import com.tencent.TIMOfflinePushSettings;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.ILiveSDK;
import com.tencent.ilivesdk.adapter.CommonConstants;
import com.tencent.ilivesdk.core.ILiveLog;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.qalsdk.sdk.MsfSdkUtils;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import lib_zxing.activity.ZXingLibrary;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private MyOkHttp mMyOkHttp;
    private DownloadMgr mDownloadMgr;
    private static MyApplication application;
    private static int mainTid;
    private static Context context;
    public static boolean LOG_DEBUG = true;//输出日志工具类
    public static String mRegistrationId;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static String token = "";
    public final static List<BasicActivity> mActivities = new LinkedList<BasicActivity>();
    public static DoctorInfoByIdEntity mDoctorInfoByIdEntity;
    public static MDTDetailEntity mMDTDetailEntity;
    public static int tag = 0;
    public static String baiduToken = "";
    public static String mImId;
    public static String mRegId;
    public static long busid;
    private static MyLifecycleHandler myActivityLifecycle;
    public static int gotoMainactivity = 0;//判读是否进入了mainactivity
    public static long backTime = 0;//记录入后台时间
    public static long FrontTime = 0;//记录入前台时间
    public static boolean ismainDoctor = false;//自己是否只主诊医生
    public static int badgeNumber = 0;//红点显示

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        FileDownloader.setup(this);
        CrashReport.initCrashReport(getApplicationContext(), "44e87dc41f", false);
        application = this;
        context = this;
        mainTid = android.os.Process.myTid();
        OkHttpLogUtils.isDebug = LOG_DEBUG;
        openStetho();
        if (BuildConfig.DEBUG) {
            openStetho();
        }
        //红点清零
        badgeNumber=0;
        Log.d("BadgeUtil","myapplication红点显示个数"+MyApplication.badgeNumber);

        BadgeUtil.setBadgeCount(this,MyApplication.badgeNumber);

        IBoxingMediaLoader loader = new BoxingGlideLoader();
        BoxingMediaLoader.getInstance().init(loader);
        BoxingCrop.getInstance().init(new BoxingUcrop());
        ((DefaultMediaLoader) DefaultMediaLoader.getInstance()).init(new FixDefaultMediaLoader());
        closeAndroidPDialog();//消除android 9.0的调用隐藏api对话框
        setDatabase();
        registerPush();
        ILiveSDK.getInstance().setCaptureMode(ILiveConstants.CAPTURE_MODE_SURFACETEXTURE);
        ILiveLog.setLogLevel(ILiveLog.TILVBLogLevel.DEBUG);

        Log.i("MyApplication", "BuildConfig.DEBUG=" + BuildConfig.DEBUG);
        if (BuildConfig.DEBUG) {
            //ILiveSDK.getInstance().initSdk(this, 1400161483, 36862);//测试环境
            ILiveSDK.getInstance().initSdk(this, 1400197132, 36862);//测试环境
        } else {
            ILiveSDK.getInstance().initSdk(this, 1400129246, 36176);//正式环境
        }


        // 老用户使用IMSDK通道
        ILiveSDK.getInstance().setChannelMode(CommonConstants.E_ChannelMode.E_ChannelIMSDK);
        ILVLiveManager.getInstance().init(new ILVLiveConfig()
                .setLiveMsgListener(MessageObservable.getInstance()));
        //腾讯云初始化
        if (MsfSdkUtils.isMainProcess(this)) {    // 仅在主线程初始化


            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification notification) {
                    Log.d("MyApplication", "recv offline push");
                    // 这里的 doNotify 是 ImSDK 内置的通知栏提醒，应用也可以选择自己利用回调参数 notification 来构造自己的通知栏提醒
                    notification.doNotify(getApplicationContext(), R.mipmap.icon);
                }
            });


            TIMOfflinePushSettings settings = new TIMOfflinePushSettings();
//开启离线推送
            settings.setEnabled(true);
//设置收到 C2C 离线消息时的提示声音，这里把声音文件放到了 res/raw 文件夹下
//设置收到群离线消息时的提示声音，这里把声音文件放到了 res/raw 文件夹下
            TIMManager.getInstance().configOfflinePushSettings(settings);

        }
        TIMManager.getInstance().init(getApplicationContext());


        //初始化zxing的配置信息
        ZXingLibrary.initDisplayOpinion(this);


        initOkhttp();


        //默认
//        mMyOkHttp = new MyOkHttp();

        mDownloadMgr = (DownloadMgr) new DownloadMgr.Builder()
                .myOkHttp(mMyOkHttp)
                .maxDownloadIngNum(5)       //设置最大同时下载数量（不设置默认5）
                .saveProgressBytes(10 * 1204)   //设置每50kb触发一次saveProgress保存进度 （不能在onProgress每次都保存 过于频繁） 不设置默认50kb
                .build();

        //  mDownloadMgr.resumeTasks();     //恢复本地所有未完成的任务
        //测试提交代码
        mRegistrationId = JPushInterface.getRegistrationID(this);
        Log.i("MyApplication", "appId=myapp-----" + mRegistrationId + "    时间" + System.currentTimeMillis());

        initBackAndFront();



    }

    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initOkhttp() {
        //自定义OkHttp
        //持久化存储cookie
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));

        //log拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(15000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)       //设置开启cookie
                .addInterceptor(logging)            //设置开启log
                .addInterceptor(new ReceivedCookiesInterceptor())//你定义的cookie接收监听器
                .addInterceptor(new TokenInterceptor())
                .addNetworkInterceptor(new StethoInterceptor()) //添加抓包工具
                .build();
        mMyOkHttp = new MyOkHttp(okHttpClient);
    }

    private void initBackAndFront() {

        MyLifecycleHandler helper = new MyLifecycleHandler();
        helper.register(this, new MyLifecycleHandler.OnAppStatusListener() {
            @Override
            public void onFront() {
                //应用切到前台处理
                Log.i("MyLifecycleHandler", "前台");
                EventBus.getDefault().post(new GotoMainactivity(true));
            }

            @Override
            public void onBack() {
                //应用切到后台处理
                Log.i("MyLifecycleHandler", "后台");
                EventBus.getDefault().post(new GotoMainactivity(false));

            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void registerPush() {
        String vendor = Build.MANUFACTURER;
        if (vendor.toLowerCase(Locale.ENGLISH).contains("xiaomi")) {
            //注册小米推送服务
            //初始化push推送服务
            if (shouldInit()) {
                //注册小米推送服务
                if (BuildConfig.IS_DEBUG) {
                    busid = 5524;
                } else {
                    busid = 5591;
                }

                MiPushClient.registerPush(this, "2882303761517897635", "5751789746635");
            }

            LoggerInterface newLogger = new LoggerInterface() {
                @Override
                public void setTag(String tag) {
                    // ignore
                }

                @Override
                public void log(String content, Throwable t) {
                    Log.d(TAG, content, t);
                }

                @Override
                public void log(String content) {
                    Log.d(TAG, content);
                }
            };
            Logger.setLogger(this, newLogger);


        } else if (vendor.toLowerCase(Locale.ENGLISH).contains("huawei")) {

            //请求华为推送设备 token
            //  PushManager.requestToken(this);
            //请求华为推送设备 token
            if (BuildConfig.IS_DEBUG) {
                busid = 5530;
            } else {
                busid = 5592;
            }

            HMSAgent.init(this);
        }
    }

    public static Context getContext() {
        return context;
    }

    public static Context getApplication() {
        return application;
    }

    public static int getMainTid() {
        return mainTid;
    }


    public static synchronized MyApplication getInstance() {
        return application;
    }

    public MyOkHttp getMyOkHttp() {
        if (mMyOkHttp == null) {
            initOkhttp();
        }
        return mMyOkHttp;
    }

    public DownloadMgr getDownloadMgr() {
        return mDownloadMgr;
    }

    /**
     * * 设置greenDao
     */
    private void setDatabase() {
        try {
            // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。   
            //    // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。     
            // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。     
            // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。     
            mHelper = new DaoMaster.DevOpenHelper(this, "JPUSH-DB", null);
            db = mHelper.getWritableDatabase();
            // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。     
            mDaoMaster = new DaoMaster(db);
            mDaoSession = mDaoMaster.newSession();
       } catch (Exception e) {

       }

    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void openStetho() {
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }


    // 遍历所有Activity并finish
    public static void exit() {
        if (mActivities != null && mActivities.size() > 0) {

            for (Activity activity : mActivities) {
                activity.finish();
            }
        }

    }

    /**
     * 移除活动
     *
     * @param activity
     */
    public static void remove(Activity activity) {
        if (mActivities != null) {
            mActivities.remove(activity);
        }
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }


}
