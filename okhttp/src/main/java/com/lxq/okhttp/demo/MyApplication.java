package com.lxq.okhttp.demo;//package com.lxq.lxqlibrary.myokhttp.demo;
//
//import android.app.Application;
//
//import com.franmontiel.persistentcookiejar.ClearableCookieJar;
//import com.franmontiel.persistentcookiejar.PersistentCookieJar;
//import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
//import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
//import com.tsy.sdk.myokhttp.MyOkHttp;
//
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
//
///**
// * Created by tsy on 2016/12/6.
// */
//
//public class MyApplication extends Application {
//
//    private static MyApplication mInstance;
//    private MyOkHttp mMyOkHttp;
//    private DownloadMgr mDownloadMgr;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        mInstance = this;
//
//        //持久化存储cookie
//        ClearableCookieJar cookieJar =
//                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));
//
//        //log拦截器
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        //自定义OkHttp
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                .cookieJar(cookieJar)       //设置开启cookie
//                .addInterceptor(logging)            //设置开启log
//                .build();
//        mMyOkHttp = new MyOkHttp(okHttpClient);
//
//        //默认
////        mMyOkHttp = new MyOkHttp();
//
//        mDownloadMgr = (DownloadMgr) new DownloadMgr.Builder()
//                .myOkHttp(mMyOkHttp)
//                .maxDownloadIngNum(5)       //设置最大同时下载数量（不设置默认5）
//                .saveProgressBytes(50 * 1204)   //设置每50kb触发一次saveProgress保存进度 （不能在onProgress每次都保存 过于频繁） 不设置默认50kb
//                .build();
//
//        mDownloadMgr.resumeTasks();     //恢复本地所有未完成的任务
//    }
//
//    public static synchronized MyApplication getInstance() {
//        return mInstance;
//    }
//
//    public MyOkHttp getMyOkHttp() {
//        return mMyOkHttp;
//    }
//
//    public DownloadMgr getDownloadMgr() {
//        return mDownloadMgr;
//    }
//}
