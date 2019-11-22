package com.lxq.okhttp.callback;


import com.lxq.okhttp.MyOkHttp;
import com.lxq.okhttp.response.IResponseHandler;
import com.lxq.okhttp.utils.OkHttpLogUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 *
 */
public interface SuccessCallback<E>   {
    void success(E e);
}
