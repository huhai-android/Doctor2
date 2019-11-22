package com.newdjk.doctor.utils;

import android.content.Intent;
import android.util.Log;

import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.login.LoginActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        Log.i("TokenInterceptor", "response.code=" + response.code());

        if (isTokenExpired(response)) {//根据和服务端的约定判断token过期
            Intent intent = new Intent(MyApplication.getContext(), LoginActivity.class);
            String userName = SpUtils.getString(Contants.userName);
            String password = SpUtils.getString(Contants.Password);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            SpUtils.clear();
            SpUtils.put(Contants.IS_FIRST_USE, false);
            SpUtils.put(Contants.userName, userName);
            SpUtils.put(Contants.Password, password);
            MyApplication.getContext().startActivity(intent);
        }
        return response;
    }

    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
    private boolean isTokenExpired(Response response) {
        return response.code() == 401;
    }


}
