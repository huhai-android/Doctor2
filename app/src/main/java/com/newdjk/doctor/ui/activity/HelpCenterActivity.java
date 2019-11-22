package com.newdjk.doctor.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.newdjk.doctor.BuildConfig;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.ui.entity.ServiceIndrocutionEntity;

import butterknife.BindView;


/**
 * @author EDZ
 */
public class HelpCenterActivity extends BasicActivity {



    @BindView(R.id.top_left)
    ImageView topLeft;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.top_right)
    ImageView topRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.relat_titlebar)
    RelativeLayout relatTitlebar;
    @BindView(R.id.liear_titlebar)
    LinearLayout liearTitlebar;
    @BindView(R.id.test_bridge_webView)
    BridgeWebView mWebView;
    @BindView(R.id.view_cover)
    View viewCover;
    @BindView(R.id.btn_share)
    Button btnShare;
    private String TAG = "ServiceDetailActivity";
    private String url = "";
    private String title = "";
    private static final int LOADING_SUCCESS = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
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
    private Gson mGson;

    @Override
    protected int initViewResId() {
        return R.layout.activity_service;
    }

    @Override
    protected void initView() {
        mGson = new Gson();

        initBackTitle("帮助中心");
        loading(true);

        if (BuildConfig.IS_DEBUG){
           url= "http://drwechat.newstarthealth.cn/index.html#/hlepCenter";

        }else {
            url= "https://drwechat.newstartcare.com/index.html#/hlepCenter";
        }

        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setTextZoom(100);  //消除系统大小的设置对webview字体大小的影响
        mWebView.getSettings().setDomStorageEnabled(true); //解决加载不出webview白屏问题
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDisplayZoomControls(false); //隐藏webview缩放按钮
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebView.setBackgroundColor(0); // 设置背景色
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new BridgeWebViewClient(mWebView) {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mHandler.sendEmptyMessageDelayed(LOADING_SUCCESS, 400);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mHandler.sendEmptyMessageDelayed(LOADING_SUCCESS, 400);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mHandler.sendEmptyMessageDelayed(LOADING_SUCCESS, 400);
            }
        });


        mWebView.registerHandler("printdata", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d(TAG, "H5打印数据" + data);
            }
        });


        mWebView.registerHandler("ServiceIntroduction", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d(TAG, data);
                try {
                    ServiceIndrocutionEntity entity = mGson.fromJson(data, ServiceIndrocutionEntity.class);

                    Intent intent = new Intent(HelpCenterActivity.this, ServiceDetailActivity.class);
                    intent.putExtra("title", entity.getTitle());
                    intent.putExtra("LinkUrl", entity.getLinkUrl());
                    startActivity(intent);
                } catch (Exception e) {

                }

            }
        });
    }

    @Override
    protected void initListener() {
        tvRight.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {

    }


}
