package com.lxq.okhttp.utils;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * This Interceptor add all received Cookies to the app DefaultPreferences.
 * Your implementation on how to save the Cookies on the Preferences MAY VARY.
 * <p>
 * Created by tsuharesu on 4/1/15.
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        List<String> cookieList = originalResponse.headers("Set-Cookie");
        if (cookieList != null) {
            if (cookieList.size() > 0) {
                String session = cookieList.get(0);
                String result = session.substring(0, session.indexOf(";"));
                OkHttpLogUtils.e("cookie result:" + result);
            }
            for (String s : cookieList) {//Cookie的格式为:cookieName=cookieValue;path=xxx
                //保存你需要的cookie数据
                OkHttpLogUtils.e("cookie for result:" + s);
            }
        }
        return originalResponse;
    }
}
