package com.newdjk.doctor.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.utils.SpUtils;

import butterknife.BindView;


public class ServiceDetailLinkActivity extends BasicActivity {


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

    @Override
    protected int initViewResId() {
        return R.layout.my_pharmacy_webview;
    }

    @Override
    protected void initView() {
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("LinkUrl");
        loading(true);
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
        mWebView.loadUrl("file:///android_asset/index.html#" + url);

        sendNative();

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

        //显示pdf报告
        mWebView.registerHandler("goDetailGoodsId", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d(TAG, data);
                Intent prescriptionTCMIntent = new Intent(mContext, PrescriptionActivity.class);
                prescriptionTCMIntent.putExtra("prefRecommend", data);
                prescriptionTCMIntent.putExtra("type", 36);

                mContext.startActivity(prescriptionTCMIntent);

            }
        });


    }

    public void sendNative() {
        String prescriptionMessage = "";
        if (TextUtils.isEmpty(prescriptionMessage)) {
            prescriptionMessage = SpUtils.getString(Contants.LoginJson);
        }

        mWebView.callHandler("UserInfo", prescriptionMessage, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

                // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ data, Toast.LENGTH_SHORT).show();
            }

        });

        Log.i("Doctor22222", "prescriptionMessage=" + prescriptionMessage);
        //  }


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
