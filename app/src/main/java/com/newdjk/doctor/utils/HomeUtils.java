package com.newdjk.doctor.utils;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.ui.entity.AppEntity;

import java.util.HashMap;
import java.util.Map;

public enum HomeUtils {
    INSTANCE;

    public void checkVersion(final UpdateListener listener) {
        String url = HttpUrl.ip + "/PlatFormAPI/KnowledgeBase/GetAppManage?AppCode=newdjkapp";
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        MyApplication.getInstance().getMyOkHttp().get().headers(headMap).url(url).tag(this).enqueue(new GsonResponseHandler<AppEntity>() {
            @Override
            public void onSuccess(int statusCode, AppEntity response) {

                listener.success(response);
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                listener.failed(statusCode, errorMsg);

            }
        });
    }

    public interface UpdateListener {
        void success(AppEntity entity);

        void failed(int statusCode, String errorMsg);
    }
}
