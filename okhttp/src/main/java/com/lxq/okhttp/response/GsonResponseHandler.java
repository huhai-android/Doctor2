package com.lxq.okhttp.response;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.lxq.okhttp.MyOkHttp;
import com.lxq.okhttp.exception.ExceptionEngine;
import com.lxq.okhttp.utils.BaseEntity;
import com.lxq.okhttp.utils.OkHttpLogUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.content.ContentValues.TAG;
import static com.lxq.okhttp.exception.ExceptionEngine.GSON_ERROR;

/**
 * Gson类型的回调接口
 * Created by tsy on 16/8/15.
 */
public abstract class GsonResponseHandler<T> implements IResponseHandler {

    private Type mType;

    public GsonResponseHandler() {
        Type myclass = getClass().getGenericSuperclass();    //反射获取带泛型的class
        if (myclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameter = (ParameterizedType) myclass;      //获取所有泛型
        mType = $Gson$Types.canonicalize(parameter.getActualTypeArguments()[0]);  //将泛型转为type
    }

    private Type getType() {
        return mType;
    }

    @Override
    public final void onSuccess(final Response response) {
        ResponseBody responseBody = response.body();
        String responseBodyStr = "";

        try {
            responseBodyStr = responseBody.string();
            OkHttpLogUtils.e("-->responseBodyStr:" + responseBodyStr);
        } catch (IOException e) {
            e.printStackTrace();
            OkHttpLogUtils.e("onResponse fail read response body:" + responseBodyStr);
            MyOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {

                    onFailure(response.code(), "fail read response body");
                }
            });
            return;
        } finally {
            responseBody.close();
        }

        final String finalResponseBodyStr = responseBodyStr;
        if (finalResponseBodyStr.contains("用户不存在")){
            MyOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailures(2, ExceptionEngine.getException(response.code()));
                }
            });
        }else {
        try {
           final Gson gson = new Gson();
            final T gsonResponse = gson.fromJson(finalResponseBodyStr, getType());
            MyOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    BaseEntity baseEntity=gson.fromJson(finalResponseBodyStr,BaseEntity.class);
                    if (response.code()==200){
                        if (baseEntity.getCode()==0){
                            onSuccess(response.code(), gsonResponse);
                        }else {
                            Log.d(TAG,"TOKEN失效1111"+response.code());
                            onFailures(baseEntity.getCode(), baseEntity.getMessage());
                        }
                    }else {
                        onFailures(response.code(), ExceptionEngine.getException(response.code()));

                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
//            onFailures(statusCode, ExceptionEngine.getException(statusCode));
            OkHttpLogUtils.e("onResponse fail parse gson, body=" + finalResponseBodyStr);
            MyOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Gson gson = new Gson();
                        BaseEntity baseEntity=gson.fromJson(finalResponseBodyStr,BaseEntity.class);
                        onFailures(baseEntity.getCode(), baseEntity.getMessage());

                    } catch (Exception e){
                        onFailures(GSON_ERROR, ExceptionEngine.getException(GSON_ERROR));

                    }

                }
            });
        }
        }
    }

    public abstract void onSuccess(int statusCode, T response);

    @Override
    public void onFailure(int statusCode, String error_msg) {
        onFailures(statusCode, ExceptionEngine.getException(statusCode));
    }

    public abstract void onFailures(int statusCode, String errorMsg);

    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }
}
