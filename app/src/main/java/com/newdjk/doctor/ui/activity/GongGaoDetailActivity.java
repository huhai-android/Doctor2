package com.newdjk.doctor.ui.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.utils.SpUtils;

import butterknife.BindView;


public class GongGaoDetailActivity extends BasicActivity {


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
    BridgeWebView testBridgeWebView;
    private int mNoticeManageId;
    private static final int LOADING_SUCCESS = 2;
    private String mLinkUrl;


    @Override
    protected int initViewResId() {
        return R.layout.activity_gonggao_detail;
    }

    @Override
    protected void initView() {
        initBackTitle("公告详情");
        mLinkUrl = getIntent().getStringExtra("LinkUrl");
        mNoticeManageId = getIntent().getIntExtra("NoticeManageId", 0);
        testBridgeWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        testBridgeWebView.clearCache(true);
        testBridgeWebView.clearHistory();
        testBridgeWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        testBridgeWebView.getSettings().setSupportZoom(true);
        testBridgeWebView.getSettings().setBuiltInZoomControls(true);
        testBridgeWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        testBridgeWebView.getSettings().setUseWideViewPort(true);
        testBridgeWebView.getSettings().setDisplayZoomControls(false);
        testBridgeWebView.getSettings().setJavaScriptEnabled(true);
        testBridgeWebView.getSettings().setTextZoom(100);  //消除系统大小的设置对webview字体大小的影响
        testBridgeWebView.getSettings().setDomStorageEnabled(true); //解决加载不出webview白屏问题
        testBridgeWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        testBridgeWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        testBridgeWebView.setBackgroundColor(0); // 设置背景色

        //自适应屏幕
        testBridgeWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        // 自适应 屏幕大小界面
        testBridgeWebView.getSettings().setLoadWithOverviewMode(true);

        testBridgeWebView.loadUrl(mLinkUrl);

        sendNative("");
    }

    @Override
    protected void initListener() {

    }
    public void sendNative(String prescriptionMessage) {
        if (TextUtils.isEmpty(prescriptionMessage)) {
            prescriptionMessage = SpUtils.getString(Contants.LoginJson);
        }


        testBridgeWebView.callHandler("UserInfo", prescriptionMessage, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

                // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ data, Toast.LENGTH_SHORT).show();
            }

        });

        Log.i("Doctor22222", "prescriptionMessage=" + prescriptionMessage);
        //  }


    }
    @Override
    protected void initData() {
       // GetNoticeManageById();
    }

    @Override
    protected void otherViewClick(View view) {

    }



}
