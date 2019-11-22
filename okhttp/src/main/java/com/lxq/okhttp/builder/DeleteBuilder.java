package com.lxq.okhttp.builder;

import com.lxq.okhttp.MyOkHttp;
import com.lxq.okhttp.callback.MyCallback;
import com.lxq.okhttp.response.IResponseHandler;
import com.lxq.okhttp.utils.OkHttpLogUtils;

import okhttp3.Request;

/**
 * delete builder
 * Created by tsy on 2016/12/6.
 */

public class DeleteBuilder extends OkHttpRequestBuilder<DeleteBuilder> {

    public DeleteBuilder(MyOkHttp myOkHttp) {
        super(myOkHttp);
    }

    @Override
    public void enqueue(final IResponseHandler responseHandler) {
        try {
            if(mUrl == null || mUrl.length() == 0) {
                throw new IllegalArgumentException("url can not be null !");
            }

            Request.Builder builder = new Request.Builder().url(mUrl).delete();
            appendHeaders(builder, mHeaders);

            if (mTag != null) {
                builder.tag(mTag);
            }

            Request request = builder.build();

            mMyOkHttp.getOkHttpClient()
                    .newCall(request)
                    .enqueue(new MyCallback(responseHandler));
        } catch (Exception e) {
            OkHttpLogUtils.e("Delete enqueue error:" + e.getMessage());
            responseHandler.onFailure(0, e.getMessage());
        }
    }
}

