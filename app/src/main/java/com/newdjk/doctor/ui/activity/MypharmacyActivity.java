package com.newdjk.doctor.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
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
import com.newdjk.doctor.utils.LogOutUtil;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MypharmacyActivity extends BasicActivity {


    @BindView(R.id.test_bridge_webView)
    BridgeWebView testBridgeWebView;
    @BindView(R.id.view_cover)
    View viewCover;
    private String mIsCancel = "false";
    private int mAction;
    private String mUrl;
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
        return R.layout.my_pharmacy;
    }

    @Override
    protected void initView() {
        mAction = getIntent().getIntExtra("action", 1);
        testBridgeWebView.clearCache(true);
        testBridgeWebView.clearHistory();
        testBridgeWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        testBridgeWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        testBridgeWebView.getSettings().setSupportZoom(true);
        testBridgeWebView.getSettings().setBuiltInZoomControls(true);
        testBridgeWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        testBridgeWebView.getSettings().setUseWideViewPort(true);
        testBridgeWebView.getSettings().setTextZoom(100);  //消除系统大小的设置对webview字体大小的影响
        testBridgeWebView.getSettings().setDomStorageEnabled(true); //解决加载不出webview白屏问题
        testBridgeWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        testBridgeWebView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        testBridgeWebView.getSettings().setUseWideViewPort(true);
        testBridgeWebView.getSettings().setJavaScriptEnabled(true);
        testBridgeWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        testBridgeWebView.setBackgroundColor(0); // 设置背景色
        if (mAction == 1) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/pharmacy?back=2");
        } else if (mAction == 2) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/record-list");
        } else if (mAction == 3) {
            mUrl = getIntent().getStringExtra("url");
            testBridgeWebView.loadUrl(mUrl);
        }

        sendNative();
        testBridgeWebView.registerHandler("backConfirm", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("zdp", "data=" + data);
                mIsCancel = data;
                // finish();
            }
        });
        testBridgeWebView.registerHandler("changePharmacy", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent chickUnitIntent = new Intent(MypharmacyActivity.this, ChickUnitActivity.class);
                startActivityForResult(chickUnitIntent, 1);
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
                LogOutUtil.getInstance().loginOut(MypharmacyActivity.this, true);
            }
        });
        testBridgeWebView.registerHandler("printdata", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d("MypharmacyActivity", "H5打印数据" + data);
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
        testBridgeWebView.callHandler("UserInfo", userInfo, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.i("zdp", data);

                // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ data, Toast.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Log.i("MypharmacyActivity", "mIsCancel=" + mIsCancel);
            testBridgeWebView.callHandler("confirmBack", "nimei", new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                    Log.i("MypharmacyActivity", "1111");
                    //    mIsCancel = true;
                    // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ data, Toast.LENGTH_SHORT).show();
                }

            });
            if (mIsCancel.equals("true")) {

            } else {
                if (testBridgeWebView.canGoBack()) {
                    testBridgeWebView.goBack(); //goBack()表示返回WebView的上一页面
                    return true;
                } else {
                    finish();
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (testBridgeWebView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = testBridgeWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(testBridgeWebView);
            }

            testBridgeWebView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            testBridgeWebView.getSettings().setJavaScriptEnabled(false);
            testBridgeWebView.clearHistory();
            testBridgeWebView.clearView();
            testBridgeWebView.removeAllViews();

            try {
                testBridgeWebView.destroy();
            } catch (Throwable ex) {

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                testBridgeWebView.callHandler("pharmacyChange", "NIMEI", new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        Log.i("zdp", data);

                        // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ data, Toast.LENGTH_SHORT).show();
                    }

                });
                break;
        }
    }
}
