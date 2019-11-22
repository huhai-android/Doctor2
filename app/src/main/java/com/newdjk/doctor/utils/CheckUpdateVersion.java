package com.newdjk.doctor.utils;

import android.content.Context;

import com.lxq.okhttp.MyOkHttp;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.ui.entity.AppEntity;

import java.util.HashMap;


public class CheckUpdateVersion {
    private CheckUpdateVersion() {
    }

    public static CheckUpdateVersion getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static CheckUpdateVersion instance = new CheckUpdateVersion();
    }

    public void doHttpCheckUpdate(MyOkHttp client, Context context, final CheckAppInfoListener listener) {
        HashMap<String, String> params = new HashMap<>();
        String packageName ="newdjkapp";
        params.put("AppCode", packageName);
        client.get().url(com.newdjk.doctor.model.HttpUrl.GetAppManage).params(params).tag(this).enqueue(new GsonResponseHandler<AppEntity>() {
            @Override
            public void onSuccess(int statusCode, AppEntity response) {
                listener.success(response);
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                listener.failed(errorMsg);
            }
        });
    }

    public interface CheckAppInfoListener {
        void success(AppEntity entity);

        void failed(String errorMsg);
    }
}
