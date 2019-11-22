package com.newdjk.doctor.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
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
import com.newdjk.doctor.views.LoadDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedicalServiceActivity extends BasicActivity {
    @BindView(R.id.test_bridge_webView)
    BridgeWebView testBridgeWebView;
    @BindView(R.id.view_cover)
    View viewCover;
    private Gson mGson;
    private String mId;
    private int mType;
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
    private String mMissionMessage;
    @Override
    protected int initViewResId() {
        return R.layout.my_pharmacy;
    }

    @Override
    protected void initView() {
        mId = getIntent().getStringExtra("action");
        mType = getIntent().getIntExtra("type", 0);
        mMissionMessage = getIntent().getStringExtra("prescriptionMessage");
        testBridgeWebView.clearCache(true);
        testBridgeWebView.clearHistory();
        testBridgeWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        testBridgeWebView.getSettings().setSupportZoom(true);
        testBridgeWebView.getSettings().setBuiltInZoomControls(true);
        testBridgeWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        testBridgeWebView.getSettings().setUseWideViewPort(true);
        testBridgeWebView.getSettings().setTextZoom(100);  //消除系统大小的设置对webview字体大小的影响
        testBridgeWebView.getSettings().setDomStorageEnabled(true); //解决加载不出webview白屏问题
        testBridgeWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        testBridgeWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        testBridgeWebView.setBackgroundColor(0); // 设置背
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            testBridgeWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (mId != null && !mId.equals("")) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/package-detail?serviceId=" + mId);
        } else if (mType == 1) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/service-order");
        } else if (mType == 2) {
           // testBridgeWebView.loadUrl("file:///android_asset/index.html#/my-service?fromService=1");
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/person-service");
        } else {
           // testBridgeWebView.loadUrl("file:///android_asset/index.html#/my-service");
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/recom-service");
        }
        testBridgeWebView.registerHandler("createWebView", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(mContext, CustomLinkActivity.class);
                intent.putExtra("url", data);
                mContext.startActivity(intent);
            }
        });
        testBridgeWebView.registerHandler("Back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("zdp", "data=" + data);
                finish();
            }
        });
        testBridgeWebView.registerHandler("tokenInvalid", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                LogOutUtil.getInstance().loginOut(MedicalServiceActivity.this, true);
            }
        });
        testBridgeWebView.registerHandler("ServicePack", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("MedicalServiceActivity", "data=" + data);
                Intent intent = new Intent();
                intent.putExtra("servicePackage", data);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        testBridgeWebView.setWebViewClient(new BridgeWebViewClient(testBridgeWebView) {

            @Override
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

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mGson = new Gson();
        sendNative();
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

    }

    public void sendNative() {
        String userInfo = SpUtils.getString(Contants.LoginJson);
        if (mMissionMessage!=null){
            testBridgeWebView.callHandler("UserInfo", mMissionMessage, new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                    Log.i("zdp", data);
                }

            });
        }else {
            testBridgeWebView.callHandler("UserInfo", userInfo, new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                    Log.i("zdp", data);
                }

            });
        }

        Log.i("打印数据zdp", userInfo);


        testBridgeWebView.callHandler("UserInfo", userInfo, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.i("zdp", data);
            }

        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (testBridgeWebView.canGoBack()) {
                testBridgeWebView.goBack(); //goBack()表示返回WebView的上一页面
                return true;
            } else {
                finish();
                return true;
            }

        }
        return false;
    }
}
