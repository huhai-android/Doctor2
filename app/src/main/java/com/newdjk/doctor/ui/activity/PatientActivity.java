package com.newdjk.doctor.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.google.gson.reflect.TypeToken;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.IM.ChatActivity;
import com.newdjk.doctor.ui.entity.ConsultMessageEntity;
import com.newdjk.doctor.ui.entity.PatientInfoEntity;
import com.newdjk.doctor.ui.entity.PatientStatusEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.ui.entity.UpdatePatientCountEntity;
import com.newdjk.doctor.ui.entity.UpdatePatientViewEntity;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.utils.ChatActivityUtils;
import com.newdjk.doctor.utils.LogOutUtil;
import com.newdjk.doctor.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PatientActivity extends BasicActivity {
    @BindView(R.id.test_bridge_webView)
    BridgeWebView testBridgeWebView;
    @BindView(R.id.view_cover)
    View viewCover;
    private int mAction;
    private Gson mGson = new Gson();
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
        return R.layout.my_pharmacy_webview;
    }

    @Override
    protected void initView() {
        mAction = getIntent().getIntExtra("action", 0);
        testBridgeWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
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
        testBridgeWebView.setBackgroundColor(0); // 设置背景色
        if (mAction == 1 || mAction == 2) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/patient/emphasis/" + mAction);

        } else if (mAction == 3) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/patient/groups/1");

        }
        sendNative();
        testBridgeWebView.registerHandler("Back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("zdp", "data=" + data);
                finish();
            }
        });
        testBridgeWebView.registerHandler("rejectPatient", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                EventBus.getDefault().post(new UpdatePushView(2));
            }
        });
        testBridgeWebView.registerHandler("tokenInvalid", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                LogOutUtil.getInstance().loginOut(PatientActivity.this, true);
            }
        });
        testBridgeWebView.registerHandler("printdata", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d("print", "H5打印数据" + data);
            }
        });
        testBridgeWebView.registerHandler("SendMessage", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("ArchivesActivity", "data=" + data);
                EventBus.getDefault().post(new UpdatePushView(2));
                EventBus.getDefault().post(new UpdatePatientViewEntity(true));
                EventBus.getDefault().post(new UpdatePatientCountEntity(true));
                PatientStatusEntity patientStatusEntity = mGson.fromJson(data, PatientStatusEntity.class);
                Type jsonType = new TypeToken<PrescriptionMessageEntity<ConsultMessageEntity>>() {
                }.getType();
                //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                PrescriptionMessageEntity<ConsultMessageEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), jsonType);
                ConsultMessageEntity ConsultMessageEntity = new ConsultMessageEntity();
                ConsultMessageEntity.setPatientInfo(patientStatusEntity.getPatientInfo());
                ConsultMessageEntity.setDoctorPatientRelation(patientStatusEntity.getDoctorPatientRelation());
                prescriptionMessageEntity.setPatient(ConsultMessageEntity);
                String json = mGson.toJson(prescriptionMessageEntity);
//                String name;
//                PatientInfoEntity patientInfoEntity = patientStatusEntity.getPatientInfo();
//                if (patientInfoEntity != null) {
//                    name = patientInfoEntity.getPatientName();
//                } else {
//                    name = patientStatusEntity.getApplicantName();
//                }
//                SpUtils.put(Contants.patientName,patientInfoEntity.getPatientName());
//                SpUtils.put(Contants.patientID,patientInfoEntity.getPatientId());
//                Intent intentTalk = new Intent(mActivity, ChatActivity.class);
//                intentTalk.putExtra(Contants.FRIEND_NAME, name);
//                intentTalk.putExtra("status", 0);
//                intentTalk.putExtra("prescriptionMessage", json);
//                intentTalk.putExtra("accountId", patientStatusEntity.getApplicantId());
//                intentTalk.putExtra(Contants.FRIEND_IDENTIFIER, patientStatusEntity.getApplicantIMId());
//                intentTalk.putExtra("imgPath", patientStatusEntity.getApplicantHeadImgUrl());
//                mActivity.startActivity(intentTalk);
                //    finish();
                ChatActivityUtils.getinStanse().toChat(patientStatusEntity.getApplicantIMId(), SpUtils.getString(Contants.identifier), patientStatusEntity.getApplicantHeadImgUrl(),mContext);
            }


        });
        testBridgeWebView.registerHandler("toSingleChat", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {

                try {
                    PatientStatusEntity patientStatusEntity = mGson.fromJson(data, PatientStatusEntity.class);


                    String identifier = patientStatusEntity.getApplicantIMId();
                    String imId = SpUtils.getString(Contants.identifier);

                    // getIMRelationRecord(identifier, imId, patientStatusEntity.getApplicantHeadImgUrl());
                    ChatActivityUtils.getinStanse().toChat(identifier, imId, patientStatusEntity.getApplicantHeadImgUrl(),mContext);

                } catch (Exception e) {

                }
            }
        });
        testBridgeWebView.setWebViewClient(new BridgeWebViewClient(testBridgeWebView) {

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

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

}
