package com.newdjk.doctor.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.SignFinshEntity;
import com.newdjk.doctor.ui.entity.SignIDEntity;
import com.newdjk.doctor.ui.entity.UpdatePatientViewEntity;
import com.newdjk.doctor.ui.entity.YWXListenerEntity;
import com.newdjk.doctor.utils.CertUtis;
import com.newdjk.doctor.utils.LogOutUtil;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.DownloadCertDialog;
import com.newdjk.doctor.views.RememberPasswordDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.org.bjca.sdk.core.kit.BJCASDK;
import cn.org.bjca.sdk.core.kit.YWXListener;

public class MyPrescriptionActivity extends BasicActivity {
    private static final String TAG = "MyPrescriptionActivity";
    @BindView(R.id.test_bridge_webView)
    BridgeWebView testBridgeWebView;
    @BindView(R.id.view_cover)
    View viewCover;
    private int mAction;
    private static final int LOADING_SUCCESS = 2;
    private Gson mGson;
    private CallBackFunction mFunction;
    private boolean isTCMPrescription = false;
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
    private int prescriptionType = 1;

    @Override
    protected int initViewResId() {
        return R.layout.my_pharmacy;
    }


    @Override
    protected void initView() {
        mGson = new Gson();
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
        prescriptionType = getIntent().getIntExtra("prescriptionType", 1);
        if (prescriptionType == 1) {

            testBridgeWebView.loadUrl("file:///android_asset/index.html#/my-prescription");
        } else {
            isTCMPrescription = true;
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/my-prescription?fromTCM=1");

        }


        sendNative();
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
                LogOutUtil.getInstance().loginOut(MyPrescriptionActivity.this, true);
            }
        });
        testBridgeWebView.registerHandler("BackToIM", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
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
                mHandler.sendEmptyMessageDelayed(LOADING_SUCCESS, 400);
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

        testBridgeWebView.registerHandler("signId", new BridgeHandler() {
            @Override
            public void handler(final String data, final CallBackFunction function) {
                Log.i("PrescriptionActivity", "data=" + data);
                mFunction = function;
                boolean isExists = BJCASDK.getInstance().existsCert(MyPrescriptionActivity.this);
                boolean ExistStamp = BJCASDK.getInstance().existsStamp(MyPrescriptionActivity.this);
                /// BJCASDK.getInstance().clearCert(ChatActivity.this);
                // BJCASDK.getInstance().startUrl(ChatActivity.this, Contants.clientId, 3);
                // BJCASDK.getInstance().startUrl(ChatActivity.this, Contants.clientId, 1);
                if (!isExists) {

                    DownloadCertDialog mDialog = new DownloadCertDialog(mActivity);
                    mDialog.show("", "", new DownloadCertDialog.DialogListener() {

                        @Override
                        public void confirm() {
                            BJCASDK.getInstance().certDown(MyPrescriptionActivity.this, Contants.clientId, SpUtils.getString(Contants.userName), new YWXListener() {
                                @Override
                                public void callback(String s) {
                                    YWXListenerEntity yWXListenerEntity = mGson.fromJson(s, YWXListenerEntity.class);
                                    String status = yWXListenerEntity.getStatus();
                                    String message = yWXListenerEntity.getMessage();
                                    if (status != null && status.equals("0")) {
                                        boolean ExistStamp1 = BJCASDK.getInstance().existsStamp(MyPrescriptionActivity.this);
                                        if (!ExistStamp1) {
                                            BJCASDK.getInstance().drawStamp(MyPrescriptionActivity.this, Contants.clientId, new YWXListener() {
                                                @Override
                                                public void callback(String msg) {
                                                    YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                                    String status = yWXListenerEntity.getStatus();
                                                    String message = yWXListenerEntity.getMessage();
                                                    if (status != null && status.equals("0")) {

                                                        Log.i("PrescriptionActivity", "data2222=" + data);
                                                        SignIDEntity signIDEntity = mGson.fromJson(data, SignIDEntity.class);
                                                        List<String> uniqueIds = new ArrayList<>();
                                                        uniqueIds.add(signIDEntity.getUniqueId());

                                                        boolean isPinExempt = BJCASDK.getInstance().isPinExempt(MyPrescriptionActivity.this);
                                                        showRememberDialog(MyPrescriptionActivity.this, uniqueIds, isPinExempt, signIDEntity.getPrescriptionId());
                                                    } else {
                                                        //  ToastUtil.setToast(message);
                                                        ToastUtil.setToast("设置签章失败，请重试！+(" + status + ")");
                                                    }
                                                }
                                            });
                                        } else {
                                            SignIDEntity signIDEntity = mGson.fromJson(data, SignIDEntity.class);
                                            List<String> uniqueIds = new ArrayList<>();
                                            uniqueIds.add(signIDEntity.getUniqueId());
                                            boolean isPinExempt = BJCASDK.getInstance().isPinExempt(MyPrescriptionActivity.this);
                                            showRememberDialog(MyPrescriptionActivity.this, uniqueIds, isPinExempt, signIDEntity.getPrescriptionId());
                                        }
                                    } else {
                                        Toast.makeText(mActivity, "下载证书失败，请重试(" + status + ")", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });


                        }
                    });


                } else {
                    //   BJCASDK.getInstance().clearCert(ChatActivity.this);
                    Log.i("zdp", "证书已下载");
                    if (!ExistStamp) {
                        BJCASDK.getInstance().drawStamp(MyPrescriptionActivity.this, Contants.clientId, new YWXListener() {
                            @Override
                            public void callback(String msg) {
                                YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                String status = yWXListenerEntity.getStatus();
                                String message = yWXListenerEntity.getMessage();
                                if (status != null && status.equals("0")) {
                                    SignIDEntity signIDEntity = mGson.fromJson(data, SignIDEntity.class);
                                    List<String> uniqueIds = new ArrayList<>();
                                    uniqueIds.add(signIDEntity.getUniqueId());
                                    boolean isPinExempt = BJCASDK.getInstance().isPinExempt(MyPrescriptionActivity.this);
                                    showRememberDialog(MyPrescriptionActivity.this, uniqueIds, isPinExempt, signIDEntity.getPrescriptionId());
                                } else {
                                    // ToastUtil.setToast(message);
                                    ToastUtil.setToast("设置签章失败，请重试！+(" + status + ")");
                                }
                            }
                        });
                    } else {
                        SignIDEntity signIDEntity = mGson.fromJson(data, SignIDEntity.class);
                        List<String> uniqueIds = new ArrayList<>();
                        uniqueIds.add(signIDEntity.getUniqueId());
                        boolean isPinExempt = BJCASDK.getInstance().isPinExempt(MyPrescriptionActivity.this);
                        showRememberDialog(MyPrescriptionActivity.this, uniqueIds, isPinExempt, signIDEntity.getPrescriptionId());

                    }
                }

            }
        });

        testBridgeWebView.registerHandler("printdata", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d(TAG, "H5打印数据" + data);
            }
        });

        testBridgeWebView.registerHandler("changePharmacy", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent chickUnitIntent = new Intent(MyPrescriptionActivity.this, ChickUnitActivity.class);
                startActivityForResult(chickUnitIntent, 5);
            }
        });
    }

    public void showRememberDialog(final Activity context, final List<String> uniqueIds, boolean isPinExempt, final String prescriptionId) {

        Log.i("PrescriptionActivity", "data8888==" + uniqueIds);

        if (!isPinExempt) {
            RememberPasswordDialog dialog = new RememberPasswordDialog(context);
            dialog.show("", "", new RememberPasswordDialog.DialogListener() {
                @Override
                public void cancel() {

                }

                @Override
                public void confirm(int keeDay) {
                    if (keeDay > 0) {
                        BJCASDK.getInstance().keepPin(context, Contants.clientId, keeDay, new YWXListener() {
                            @Override
                            public void callback(String msg) {
                                YWXListenerEntity yWXListenerEntity = new Gson().fromJson(msg, YWXListenerEntity.class);
                                String status = yWXListenerEntity.getStatus();
                                String message = yWXListenerEntity.getMessage();
                                if (status.equals("0")) {
                                    BJCASDK.getInstance().sign(MyPrescriptionActivity.this, Contants.clientId, uniqueIds, new YWXListener() {
                                        @Override
                                        public void callback(String result) {
                                            if (result != null) {
                                                SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                                                String status = signFinshEntity.getStatus();
                                                if (status.equals("0")) {
                                                    mFunction.onCallBack("成功");
                                                    if (uniqueIds.size() > 0) {
                                                        signPrescription(prescriptionId);

                                                    }
                                                } else {
                                                    //   toast(signFinshEntity.getMessage());
                                                    if (status.toLowerCase().contains("004x030")){
                                                        CertUtis.showCertUpdateDialog(MyPrescriptionActivity.this);
                                                    }else {
                                                        ToastUtil.setToast("电子签名失败，请核对签名密码是否正确和网络是否正常！+(" + status + ")");
                                                    }
                                                }
                                            }
                                        }
                                    });
                                } else {
                                    // Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    ToastUtil.setToast("记住密码失效，请重试！+(" + status + ")");

                                }

                            }
                        });
                    } else {

                        Log.i("PrescriptionActivity", "data333=" + uniqueIds);
                        BJCASDK.getInstance().sign(MyPrescriptionActivity.this, Contants.clientId, uniqueIds, new YWXListener() {
                            @Override
                            public void callback(String result) {
                                if (result != null) {
                                    SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                                    String status = signFinshEntity.getStatus();
                                    if (status.equals("0")) {
                                        mFunction.onCallBack("成功");
                                        signPrescription(prescriptionId);
                                    } else {
                                        //   toast(signFinshEntity.getMessage());
                                        if (status.toLowerCase().contains("004x030")){
                                            CertUtis.showCertUpdateDialog(MyPrescriptionActivity.this);
                                        }else {
                                            ToastUtil.setToast("电子签名失败，请核对签名密码是否正确和网络是否正常！+(" + status + ")");
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
            });
        } else {
            BJCASDK.getInstance().sign(MyPrescriptionActivity.this, Contants.clientId, uniqueIds, new YWXListener() {

                @Override
                public void callback(String result) {
                    Log.i("PrescriptionActivity", "data7777=" + result);

                    if (result != null) {
                        SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                        String status = signFinshEntity.getStatus();
                        if (status.equals("0")) {
                            mFunction.onCallBack("成功");
                            signPrescription(prescriptionId);
                        } else {
                            //toast(signFinshEntity.getMessage());
                            if (status.toLowerCase().contains("004x030")){
                                CertUtis.showCertUpdateDialog(MyPrescriptionActivity.this);
                            }else {
                                ToastUtil.setToast("电子签名失败，请核对签名密码是否正确和网络是否正常！+(" + status + ")");
                            }

                        }

                    }
                }
            });
        }
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
        EventBus.getDefault().post(new UpdatePatientViewEntity(true));
        super.onDestroy();
    }


    private void signPrescription(String PrescriptionId) {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        paramsMap.put("PrescriptionId", PrescriptionId);
        //处方类型 1-西药处方,2-中药处方
        paramsMap.put("PrescriptionType", (isTCMPrescription ? 2 : 1) + "");
        mMyOkhttp.post().url(HttpUrl.signChufang).headers(headMap).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                if (entity.getCode() == 0) {
                    Log.d(TAG, "告诉后台开处方成功");
                } else {
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5) {

            if (data != null) {
                String id = data.getStringExtra("result");
                testBridgeWebView.callHandler("pharmacyChange", "NIMEI", new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        Log.i("zdp", data);

                        // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ data, Toast.LENGTH_SHORT).show();
                    }

                });

                Log.d(TAG, "药房id" + id);
                testBridgeWebView.callHandler("PharmacyID", id, new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        Log.i("zdp", data);

                        // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ data, Toast.LENGTH_SHORT).show();
                    }

                });
            } else {
                testBridgeWebView.callHandler("pharmacyChange", "NIMEI", new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        Log.i("zdp", data);

                        // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ data, Toast.LENGTH_SHORT).show();
                    }

                });

                Log.d(TAG, "药房id-1");
                testBridgeWebView.callHandler("PharmacyID", "-1", new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        Log.i("zdp", data);

                        // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ data, Toast.LENGTH_SHORT).show();
                    }

                });
            }
        }
    }
}
