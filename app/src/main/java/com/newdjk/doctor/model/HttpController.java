package com.newdjk.doctor.model;

import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.lxq.okhttp.MyOkHttp;
import com.lxq.okhttp.callback.SuccessCallback;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.lxq.okhttp.response.RawResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.ui.activity.login.LoginActivity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.utils.LogUtils;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.LoadDialog;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public class HttpController<T> {
    private  MyOkHttp mMyOkhttp;
//    private static final boolean flag = true;//是否打印
    private static volatile HttpController instance = null;
    private Type mType;

    public HttpController() {
        mMyOkhttp = MyApplication.getInstance().getMyOkHttp();
        Type myclass = getClass().getGenericSuperclass();    //反射获取带泛型的class
        if (myclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameter = (ParameterizedType) myclass;      //获取所有泛型
        mType = $Gson$Types.canonicalize(parameter.getActualTypeArguments()[0]);  //将泛型转为type
    }

//    public static HttpController getInstance() {
//        if (instance == null) {
//            synchronized (HttpController.class) {
//                if (instance == null) {
//                    instance = new HttpController();
//                }
//            }
//        }
//        return instance;
//    }

    private Type getType() {
        return mType;
    }

    /**
     * 超时后返回登录界面
     */
    public static void errorLoginActivty() {
        Intent intent = new Intent(MyApplication.getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getContext().startActivity(intent);
    }

    /**
     * 请求失败处理
     *
     * @param statusCode
     * @param errorMsg
     */
    private void error(int statusCode, String errorMsg) {
        LogUtils.e("statusCode:" + statusCode + "    errorMsg:" + errorMsg);
        ToastUtil.setToast(errorMsg);
        if (statusCode == 401) {
            errorLoginActivty();
        }
        LoadDialog.clear();
    }

    /**
     * 请求成功处理
     */
    private void success(int statusCode, String response, SuccessCallback<T> callback) {
        try {
            Gson gson = new Gson();
            final T gsonResponse = gson.fromJson(response, getType());
            callback.success(gsonResponse);
        } catch (Exception e) {
            LogUtils.e(e.toString());
            ToastUtil.setToast("数据解析失败");
        } finally {
            LoadDialog.clear();
        }
    }

    /***
     *  提交
     */
    public void submit(String url, Map<String, String> params, final SuccessCallback<Entity> callback) {

        mMyOkhttp.post().url(url).params(params).tag(this)
                .enqueue(new GsonResponseHandler<Entity>() {
                    @Override
                    public void onSuccess(int statusCode, Entity entity) {
                        LogUtils.e("statusCode:" + statusCode);
                        if (entity.getCode() == 200) {
                            callback.success(entity);
                        } else {
                            ToastUtil.setToast(entity.getMessage());
                        }

                    }

                    @Override
                    public void onFailures(int statusCode, String errorMsg) {
                        error(statusCode, errorMsg);
                    }
                });
    }

    /**
     * 请求
     *
     * @param url
     * @param params
     * @param callback
     */
    public void request(String url, Map<String, String> params, final SuccessCallback<T> callback) {
        mMyOkhttp.post().url(url).params(params).tag(this)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
//                        success(statusCode, response, callback);
                    }

                    @Override
                    public void onFailure(int statusCode, String errorMsg) {
//                        error(statusCode, errorMsg);
                    }
                });
    }

}
