package com.lxq.okhttp.callback;


import com.lxq.okhttp.MyOkHttp;
import com.lxq.okhttp.response.IResponseHandler;
import com.lxq.okhttp.utils.OkHttpLogUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by tsy on 16/9/18.
 */
public class MyCallback implements Callback {

    private IResponseHandler mResponseHandler;

    public MyCallback(IResponseHandler responseHandler) {
        mResponseHandler = responseHandler;
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        OkHttpLogUtils.e("onFailure", e.toString());
        if (e.toString().contains("Cancele")||e.toString().contains("Socket")) {
           return;
        }else {
            MyOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResponseHandler.onFailure(0, e.toString());
                }
            });
        }

    }

    @Override
    public void onResponse(Call call, final Response response) {
        if (response.isSuccessful()) {
            mResponseHandler.onSuccess(response);
        } else {
            OkHttpLogUtils.e("onResponse fail status=" + response.code());

            MyOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResponseHandler.onFailure(response.code(), "fail status=" + response.code());
                }
            });
        }
    }
}
