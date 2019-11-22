package com.newdjk.doctor.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;

import butterknife.BindView;

public class ChoosePersonWebActivity extends BasicActivity {


    @BindView(R.id.mWebView)
    BridgeWebView mWebView;
    @BindView(R.id.view_cover)
    View viewCover;
    private String mUrl = "file:///android_asset/index.html#/patient/groups/2";
    private static final int LOADING_SUCCESS = 2;
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
        return R.layout.activity_choose_person_web;
    }

    @Override
    protected void initView() {
        // 这里一定要加这个  不然会有缓存
        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setTextZoom(100);  //消除系统大小的设置对webview字体大小的影响
        mWebView.getSettings().setDomStorageEnabled(true); //解决加载不出webview白屏问题
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebView.setBackgroundColor(0); // 设置背景色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebView.setWebViewClient(new BridgeWebViewClient(mWebView) {

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LoadDialog.clear();
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
//      mWebView.loadUrl("file:///android_asset/index.html#/seekDoctor");
        mWebView.loadUrl(mUrl);
        initJsBridge();
        sendDataToHtml();
    }


    private void sendDataToHtml() {
        String mUserInfo = SpUtils.getString(Contants.LoginJson);
        Log.d("mUserInfo", mUserInfo);
        mWebView.callHandler("UserInfo", mUserInfo, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.d("onCallBack", "data=" + data);
            }
        });
    }

    private void initJsBridge() {
        // 注册一个方法给JS调用：关闭网页
        mWebView.registerHandler("Back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d("handler", "Back");
                // 关闭页面
                if (mWebView.canGoBack()) {
                    mWebView.canGoBack();
                } else {
                    finish();
                }
            }
        });


        // 注册一个方法给JS调用：关闭网页
        mWebView.registerHandler("groups", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d("handler", "data===" + data);
                Intent intent = new Intent();
                intent.putExtra("ids", data);
                setResult(RESULT_OK, intent);
                finish();
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



}
