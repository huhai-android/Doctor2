package com.newdjk.doctor.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.utils.LogOutUtil;
import com.newdjk.doctor.utils.SpUtils;

import butterknife.BindView;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   MyPointsActivity
 *  @创建者:   huhai
 *  @创建时间:  2019/1/21 9:45
 *  @描述：
 */
public class MyPointsRecodeActivity extends BasicActivity {
    private static final int LOADING_SUCCESS = 2;
    @BindView(R.id.test_bridge_webView)
    BridgeWebView mBridgeWebView;
    @BindView(R.id.view_cover)
    View viewCover;
    private Gson mGson;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADING_SUCCESS:
                    loading(false);
                    viewCover.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int initViewResId() {
        return R.layout.activity_mypoint_recode;
    }

    @Override
    protected void initView() {
        mGson = new Gson();
        sendNative();
        mBridgeWebView.loadUrl("file:///android_asset/index.html#/score-record");
        mBridgeWebView.registerHandler("Back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("zdp", "data=" + data);
                finish();
            }
        });

        mBridgeWebView.registerHandler("tokenInvalid", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                LogOutUtil.getInstance().loginOut(MyPointsRecodeActivity.this, true);
            }
        });

        mBridgeWebView.setWebViewClient(new BridgeWebViewClient(mBridgeWebView) {

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mHandler.sendEmptyMessageDelayed(LOADING_SUCCESS,400);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {

    }

    public void sendNative() {
        String userInfo = SpUtils.getString(Contants.LoginJson);
        Log.d("MyPointsRecodeActivity", "userInfo=" + userInfo);
        mBridgeWebView.callHandler("UserInfo", userInfo, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.d("MyPointsRecodeActivity", data);

                // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ data, Toast.LENGTH_SHORT).show();
            }

        });

    }

}
