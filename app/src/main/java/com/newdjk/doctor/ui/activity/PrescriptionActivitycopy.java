package com.newdjk.doctor.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.BuildConfig;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.PatientEntity;
import com.newdjk.doctor.ui.entity.PictureMessageEntity;
import com.newdjk.doctor.ui.entity.SendGoodsEntity;
import com.newdjk.doctor.ui.entity.ShopLoginEntity;
import com.newdjk.doctor.ui.entity.SignFinshEntity;
import com.newdjk.doctor.ui.entity.SignIDEntity;
import com.newdjk.doctor.ui.entity.WebShopUseinfoEntity;
import com.newdjk.doctor.ui.entity.YWXListenerEntity;
import com.newdjk.doctor.utils.CertUtis;
import com.newdjk.doctor.utils.ImageBase64;
import com.newdjk.doctor.utils.LocationUtils;
import com.newdjk.doctor.utils.LogOutUtil;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.DownloadCertDialog;
import com.newdjk.doctor.views.RememberPasswordDialog;
import com.newdjk.doctor.views.SelectedPictureDialog;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.org.bjca.sdk.core.kit.BJCASDK;
import cn.org.bjca.sdk.core.kit.YWXListener;
import cn.org.bjca.sdk.core.values.ConstantParams;

public class PrescriptionActivitycopy extends BasicActivity implements IWXAPIEventHandler {
    private static final String TAG = "PrescriptionActivity";
    @BindView(R.id.test_bridge_webView)
    BridgeWebView testBridgeWebView;
    @BindView(R.id.view_cover)
    View viewCover;
    @BindView(R.id.btn_share)
    Button btnShare;
    private String mPrescriptionMessage;
    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调
    private static final int LOADING_SUCCESS = 2;
    private static final int SHARE_GOOD = 3;
    private Uri imageUri;
    private String testMessage = "{\"doctor\":{\"Code\":0,\"Data\":{\"Token\":\"eyJpZCI6NDcsImlhdCI6MTUzNzM1MTY5NCwiZXhwIjoxNTM3MzUxNjk0LCJ0eXBlIjoyLCJjbGllbnQiOm51bGwsInJlZ2lzdHJhdGlvbklkIjpudWxsfQ.qSng8Q6W-ACEEvruV5A9y_dV6lyknEzgiM9aqUusHXU\",\"User\":{\"DepartmentId\":0,\"DoctorId\":47,\"DoctorName\":\"赵季\",\"DrType\":1,\"Mobile\":\"18603016010\",\"OrgId\":[1],\"Position\":0,\"Sex\":3}},\"Message\":\"登录成功\"},\"patient\":{\"ChatStatus\":0,\"Content\":\"ceshi \",\"CreateId\":0,\"CreateTime\":\"\",\"DiseaseId\":\"0\",\"DoctorId\":47,\"DoctorName\":\"赵季\",\"EndTime\":\"\",\"EndType\":0,\"EvaluationTime\":\"\",\"Id\":9,\"OrgId\":1,\"PatientId\":16,\"PatientInfo\":{\"AccountId\":10,\"Birthday\":\"1993-09-18 00:00:00\",\"CreateId\":0,\"Education\":0,\"Invalid\":0,\"Job\":0,\"MedicalType\":0,\"PatientId\":16,\"PatientName\":\"hu\",\"PatientSex\":1,\"PatientType\":0,\"Region\":0,\"UpdateId\":0},\"PatientName\":\"hu\",\"PayStatus\":0,\"PayTime\":\"\",\"StartTime\":\"2018-09-18 19:00:59\",\"Status\":0,\"Type\":1,\"UpdateTime\":\"\"}}";
    private SelectedPictureDialog mSelectedPictureDialog;
    private CallBackFunction mFunction;
    private Gson mGson;
    private PictureMessageEntity mPictureList;
    private String mId;
    private String mPrescriptionId;
    private String mRejectId;
    private String mIsCancel = "false";
    private int type;
    private String APP_ID = "wx55eb72942a9dad20";
    private IWXAPI iwxapi;
    enum SHARE_TYPE {Type_WXSceneSession, Type_WXSceneTimeline}
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
                case SHARE_GOOD:
                    share(SHARE_TYPE.Type_WXSceneSession);
                    break;
                default:
                    break;
            }
        }
    };
    private String CensorateRecordId;
    private String mLinkUrl;
    private String NIMEI;
    private boolean isTCMPrescription = false;
    private SendGoodsEntity mSendGoodsEntity;
    private String sharedata = "";

    @Override
    protected int initViewResId() {
        return R.layout.my_pharmacy_webview;
    }

    @Override
    protected void initView() {
        mGson = new Gson();
        loading(true);
        iwxapi = WXAPIFactory.createWXAPI(PrescriptionActivitycopy.this, APP_ID, false);
        iwxapi.handleIntent(this.getIntent(), this);

        type = getIntent().getIntExtra("type", 0);
        CensorateRecordId = getIntent().getStringExtra("CensorateRecordId");
        mPrescriptionMessage = getIntent().getStringExtra("prescriptionMessage");
        mId = getIntent().getStringExtra("action");
        mPrescriptionId = getIntent().getStringExtra("id");
        mRejectId = getIntent().getStringExtra("rejectId");
        mLinkUrl = getIntent().getStringExtra("LinkUrl");
        NIMEI = getIntent().getStringExtra("NIMEI");


        Log.i("PrescriptionActivity", mPrescriptionMessage + "xxxx");
        testBridgeWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        testBridgeWebView.clearCache(true);
        testBridgeWebView.clearHistory();
        testBridgeWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        testBridgeWebView.getSettings().setSupportZoom(true);
        testBridgeWebView.getSettings().setJavaScriptEnabled(true);
        testBridgeWebView.getSettings().setBuiltInZoomControls(true);
        testBridgeWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        testBridgeWebView.getSettings().setUseWideViewPort(true);
        testBridgeWebView.getSettings().setTextZoom(100);  //消除系统大小的设置对webview字体大小的影响
        testBridgeWebView.getSettings().setDomStorageEnabled(true); //解决加载不出webview白屏问题
        testBridgeWebView.getSettings().setDatabaseEnabled(true);
        testBridgeWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        testBridgeWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        testBridgeWebView.setBackgroundColor(0); // 设置背景色
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

        if (type == 8) {

            testBridgeWebView.loadUrl("file:///android_asset/index.html#/" + "CooperativeOrganization");

        } else if (type == 9) {

            testBridgeWebView.loadUrl("file:///android_asset/index.html#/" + "inspect/prescriptionLlist?CensorateRecordId=" + CensorateRecordId);
            //  testBridgeWebView.loadUrl("file:///android_asset/index.html#/" + "inspect/test");

        } else if (type == 10) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/" + "inspect/my-check");

        } else if (type == 11) {
            changeStatusBarTextColor(false);
            // setStatusBarColor(this, R.color.colorPrimary);
            //testBridgeWebView.loadUrl(BuildConfig.SHOP_IP + "recommendSort");
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/" + "prefRecommend/preEntry");
            // testBridgeWebView.loadUrl("http://wx.newstarthealth.cn/Home/PageRedirect?redirectUrl=http%3a%2f%2fwx.newstarthealth.cn%2findex.html%23%2fdr-home%3fappid%3dwxce06749b2a6e9352%26orgid%3d1");

        } else if (type == 12) {
            setStatusBarColor(this, R.color.colorPrimary);
            changeStatusBarTextColor(false);
            testBridgeWebView.loadUrl(mLinkUrl);

        } else if (type == 13) {
            changeStatusBarTextColor(false);
            setStatusBarColor(this, R.color.colorPrimary);
            testBridgeWebView.loadUrl(BuildConfig.SHOP_IP + "recommendOrder");
        } else if (type == 14) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/" + "understandSignature");
        } else if (type == 15) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/" + "help");
            //中药处方
        } else if (type == 16) {
            isTCMPrescription = true;
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/" + "choosePharmacy");
        } else if (type == 17) {
            isTCMPrescription = true;
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/" + "cM/prescription?DataSource=2&DataId=" + mId);
        } else if (type == 18) {
            isTCMPrescription = true;
            Log.d(TAG, "调用11111111111111111");
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/chineseMed/prescription?id=" + mId);
        } else if (type == 19) {
            //中药处方被驳回
            isTCMPrescription = true;
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/chineseMed/prescription?id=" + mId);
        } else {
            Log.d(TAG, "是否执行了111---" + mRejectId);
            if (mId != null && !mId.equals("")) {
                Log.d(TAG, "是否执行了222");
                testBridgeWebView.loadUrl("file:///android_asset/index.html#/PrescriptionLlist?id=" + mId);
            } else if (mRejectId != null && !mRejectId.equals("")) {
                Log.d(TAG, "是否执行了555---" + mRejectId);
                testBridgeWebView.loadUrl("file:///android_asset/index.html#/PrescriptionLlist?id=" + mRejectId);
            } else if (mPrescriptionId != null && !mPrescriptionId.equals("")) {
                Log.d(TAG, "是否执行了3333" + "   " + mPrescriptionId);
                testBridgeWebView.loadUrl("file:///android_asset/index.html#/prescription?MPrescriptionId=" + mPrescriptionId);
            } else {
                Log.d(TAG, "是否执行了444" + "   " + mPrescriptionId);
                //  testBridgeWebView.loadUrl("file:///android_asset/index.html#/prescription");
                testBridgeWebView.loadUrl("file:///android_asset/index.html#/pharmacy?from=p&back=2");
            }
        }

        // Log.i("zdp","mPrescriptionMessage="+mPrescriptionMessage);
        sendNative(mPrescriptionMessage);
        testBridgeWebView.registerHandler("BackToIM", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                finish();
            }
        });
        testBridgeWebView.registerHandler("Back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                finish();
            }
        });
        testBridgeWebView.registerHandler("tokenInvalid", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                LogOutUtil.getInstance().loginOut(PrescriptionActivitycopy.this, true);
            }
        });
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
                Intent chickUnitIntent = new Intent(PrescriptionActivitycopy.this, ChickUnitActivity.class);
                startActivityForResult(chickUnitIntent, 5);
            }
        });
        testBridgeWebView.registerHandler("signId", new BridgeHandler() {
            @Override
            public void handler(final String data, CallBackFunction function) {
                Log.i("PrescriptionActivity", "data=" + data);
                mFunction = function;
                boolean isExists = BJCASDK.getInstance().existsCert(PrescriptionActivitycopy.this);
                boolean ExistStamp = BJCASDK.getInstance().existsStamp(PrescriptionActivitycopy.this);
                /// BJCASDK.getInstance().clearCert(ChatActivity.this);
                // BJCASDK.getInstance().startUrl(ChatActivity.this, Contants.clientId, 3);
                // BJCASDK.getInstance().startUrl(ChatActivity.this, Contants.clientId, 1);
                if (!isExists) {

                    DownloadCertDialog mDialog = new DownloadCertDialog(mActivity);
                    mDialog.show("", "", new DownloadCertDialog.DialogListener() {

                        @Override
                        public void confirm() {
                            BJCASDK.getInstance().certDown(PrescriptionActivitycopy.this, Contants.clientId, SpUtils.getString(Contants.userName), new YWXListener() {
                                @Override
                                public void callback(String s) {
                                    YWXListenerEntity yWXListenerEntity = mGson.fromJson(s, YWXListenerEntity.class);
                                    String status = yWXListenerEntity.getStatus();
                                    String message = yWXListenerEntity.getMessage();
                                    if (status != null && status.equals("0")) {
                                        boolean ExistStamp1 = BJCASDK.getInstance().existsStamp(PrescriptionActivitycopy.this);
                                        if (!ExistStamp1) {
                                            BJCASDK.getInstance().drawStamp(PrescriptionActivitycopy.this, Contants.clientId, new YWXListener() {
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

                                                        boolean isPinExempt = BJCASDK.getInstance().isPinExempt(PrescriptionActivitycopy.this);
                                                        showRememberDialog(PrescriptionActivitycopy.this, uniqueIds, isPinExempt, signIDEntity.getPrescriptionId());
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
                                            boolean isPinExempt = BJCASDK.getInstance().isPinExempt(PrescriptionActivitycopy.this);
                                            showRememberDialog(PrescriptionActivitycopy.this, uniqueIds, isPinExempt, signIDEntity.getPrescriptionId());
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
                        BJCASDK.getInstance().drawStamp(PrescriptionActivitycopy.this, Contants.clientId, new YWXListener() {
                            @Override
                            public void callback(String msg) {
                                YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                String status = yWXListenerEntity.getStatus();
                                String message = yWXListenerEntity.getMessage();
                                if (status != null && status.equals("0")) {
                                    SignIDEntity signIDEntity = mGson.fromJson(data, SignIDEntity.class);
                                    List<String> uniqueIds = new ArrayList<>();
                                    uniqueIds.add(signIDEntity.getUniqueId());
                                    boolean isPinExempt = BJCASDK.getInstance().isPinExempt(PrescriptionActivitycopy.this);
                                    showRememberDialog(PrescriptionActivitycopy.this, uniqueIds, isPinExempt, signIDEntity.getPrescriptionId());
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
                        boolean isPinExempt = BJCASDK.getInstance().isPinExempt(PrescriptionActivitycopy.this);
                        showRememberDialog(PrescriptionActivitycopy.this, uniqueIds, isPinExempt, signIDEntity.getPrescriptionId());

                    }
                }
            }
        });
        testBridgeWebView.registerHandler("Preview", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                mPictureList = mGson.fromJson(data, PictureMessageEntity.class);
                Intent intent = new Intent(PrescriptionActivitycopy.this, PictureViewerActivity.class);
                intent.putExtra("pic_all", (ArrayList<String>) mPictureList.getUrl());
                intent.putExtra("pic_position", mPictureList.getPosition());
                startActivity(intent);
            }
        });
        testBridgeWebView.registerHandler("APP", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (data != null && data.equals("Img")) {
                    mFunction = function;
                    mSelectedPictureDialog = new SelectedPictureDialog(PrescriptionActivitycopy.this, "first");
                    mSelectedPictureDialog.show();
                }
            }
        });

        testBridgeWebView.registerHandler("SendMessage", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                finish();

            }
        });

        testBridgeWebView.registerHandler("ZhuanZhenSuccess", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (data.contains("true")) {


                }
            }
        });
        testBridgeWebView.registerHandler("printdata", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d(TAG, "H5打印数据" + data);
            }
        });
        testBridgeWebView.registerHandler("pullArea", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                Log.d(TAG, "H5打印数据" + data);


                LocationUtils.getinstanse().initlocation(PrescriptionActivitycopy.this, new LocationUtils.locationStatusListener() {
                    @Override
                    public void locationSuccess(final String data) {
                        //如果历史不为空，并且定位到了其他城市
                        function.onCallBack(data);
                        LocationUtils.getinstanse().stopLocation();
                    }

                    @Override
                    public void locationFailed(String data) {
                        function.onCallBack("");
                        LocationUtils.getinstanse().stopLocation();
                    }
                });
            }
        });


        testBridgeWebView.registerHandler("SendGoodsRecommend", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d(TAG, "分享推荐" + data);

                    mSendGoodsEntity = mGson.fromJson(data, SendGoodsEntity.class);
                    sharedata = data;
                    showBottomDialog();


            }
        });

    }

    public void showRememberDialog(final Activity context, final List<String> uniqueIds, boolean isPinExempt, final String prescriptionId) {
        if (!isPinExempt) {
            RememberPasswordDialog dialog = new RememberPasswordDialog(context);
            dialog.show("", "", new RememberPasswordDialog.DialogListener() {
                @Override
                public void cancel() {

                }

                @Override
                public void confirm(int keeDay) {
                    if (keeDay == 0) {
                        //签名
                        BJCASDK.getInstance().sign(PrescriptionActivitycopy.this, Contants.clientId, uniqueIds, new YWXListener() {
                            @Override
                            public void callback(String result) {
                                Log.i("RoomActivity", "bbbb" + result);
                                if (result != null) {
                                    SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                                    String status = signFinshEntity.getStatus();
                                    if (status.equals("0")) {
                                        mFunction.onCallBack("true");
                                        if (uniqueIds.size() > 0) {
                                            signPrescription(prescriptionId);

                                        }

                                    } else {
                                        // toast(signFinshEntity.getMessage());
                                        if (status.toLowerCase().contains("004x030")){
                                            CertUtis.showCertUpdateDialog(PrescriptionActivitycopy.this);
                                        }else {
                                            ToastUtil.setToast("电子签名失败，请核对签名密码是否正确和网络是否正常！+(" + status + ")");
                                        }
                                    }
                                }
                            }
                        });
                    } else {
                        //设置免密记录时间
                        BJCASDK.getInstance().keepPin(context, Contants.clientId, keeDay, new YWXListener() {
                            @Override
                            public void callback(String msg) {
                                Log.i("RoomActivity", "msg=" + msg);
                                YWXListenerEntity yWXListenerEntity = new Gson().fromJson(msg, YWXListenerEntity.class);
                                String status = yWXListenerEntity.getStatus();
                                String message = yWXListenerEntity.getMessage();
                                if (status.equals("0")) {
                                    BJCASDK.getInstance().sign(PrescriptionActivitycopy.this, Contants.clientId, uniqueIds, new YWXListener() {
                                        @Override
                                        public void callback(String result) {
                                            Log.i("RoomActivity", "bbbb");
                                            if (result != null) {
                                                SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                                                String status = signFinshEntity.getStatus();
                                                if (status.equals("0")) {
                                                    mFunction.onCallBack("true");
                                                    if (uniqueIds.size() > 0) {
                                                        signPrescription(prescriptionId);
                                                    }
                                                } else {
                                                    //  toast(signFinshEntity.getMessage());
                                                    ToastUtil.setToast("请核对签名密码是否正确和网络是否正常！+(" + status + ")");

                                                }
                                            }
                                        }
                                    });
                                } else {
                                    //   Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    ToastUtil.setToast("记住密码失效，请重试！+(" + status + ")");

                                }
                                Log.i("RoomActivity", "aaaaa");

                            }
                        });
                    }
                }
            });
        } else {
            BJCASDK.getInstance().sign(PrescriptionActivitycopy.this, Contants.clientId, uniqueIds, new YWXListener() {
                @Override
                public void callback(String result) {
                    Log.i("RoomActivity", "aaaa" + result);
                    if (result != null) {
                        SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                        String status = signFinshEntity.getStatus();
                        if (status.equals("0")) {
                            mFunction.onCallBack("true");
                            if (uniqueIds.size() > 0) {
                                signPrescription(prescriptionId);
                            }

                        } else {
                            // toast(signFinshEntity.getMessage());
                            if (status.toLowerCase().contains("004x030")){
                                CertUtis.showCertUpdateDialog(PrescriptionActivitycopy.this);
                            }else {
                                if (status.toLowerCase().contains("004x030")){
                                    CertUtis.showCertUpdateDialog(PrescriptionActivitycopy.this);
                                }else {
                                    ToastUtil.setToast("电子签名失败，请核对签名密码是否正确和网络是否正常！+(" + status + ")");
                                }
                            }
                        }
                    }
                }
            });
        }
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
    protected void initListener() {
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.mWechatFriend:

                share(SHARE_TYPE.Type_WXSceneSession);
                mDialog.dismiss();
                break;
            case R.id.mWechatZone:
                Intent intent = new Intent(PrescriptionActivitycopy.this, PatientListActivity.class);
                intent.putExtra("SendGoodsEntity", sharedata);
                startActivity(intent);
                mDialog.dismiss();
                break;
            case R.id.mCancel:
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                break;
        }
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

    public void sendNative(String prescriptionMessage) {


        String userInfo = "";
        if (type == 12) {
            String logininfo = SpUtils.getString(Contants.LoginJson);
            WebShopUseinfoEntity shopUseinfoEntity = new WebShopUseinfoEntity();
            ShopLoginEntity shopLoginEntity = mGson.fromJson(logininfo, ShopLoginEntity.class);
            PatientEntity patientEntity = new PatientEntity();
            patientEntity.setPatientId(SpUtils.getInt(Contants.patientID, 0));
            patientEntity.setPatientName(SpUtils.getString(Contants.patientName));
            shopUseinfoEntity.setShopLoginEntity(shopLoginEntity);
            shopUseinfoEntity.setPatientEntity(patientEntity);
            userInfo = mGson.toJson(shopUseinfoEntity);
            testBridgeWebView.callHandler("UserInfoAndroid", userInfo, new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                    Log.i("zdp", data);

                    // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ dat, Toast.LENGTH_SHORT).show();
                }

            });

            Log.i("Doctor111111", "userInfo=" + userInfo);

        } else {
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
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
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
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    uploadPicture(mSelectedPictureDialog.getPicturePath());
                    break;
                case 3:
                    Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String path = cursor.getString(columnIndex);  //获取照片路径
                    uploadPicture(path);
                    break;
                case ConstantParams.ACTIVITY_SIGN_DATA:
             /*  BJCASDK.getInstance().getUserInfo(ChatActivity.this, Contants.clientId, new OperateListener() {
                    @Override
                    public void callback(String s) {
                        Log.i("zdp", "result=" + s);
                    }
                });*/
                    String result = null;
                    try {
                        result = data.getStringExtra(ConstantParams.KEY_SIGN_BACK);
                        if (result != null) {
                            SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                            String status = signFinshEntity.getStatus();
                            if (status.equals("0")) {
                                mFunction.onCallBack("true");
                            } else {
                                toast(signFinshEntity.getMessage());
                            }
                        }
                        Log.i("Prescription", "result=" + result);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                    break;
            }
        }
    }

    private void uploadPicture(String path) {

        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String image64 = ImageBase64.bitmapToString(strings[0]);
                return image64;
            }

            @Override
            protected void onPostExecute(String s) {
                loading(true);
                Map<String, String> map = new HashMap<>();

                map.put("Base64Str", s);
                Map<String, String> headMap = new HashMap<>();
                headMap.put("Authorization", SpUtils.getString(Contants.Token));
                mMyOkhttp.post().url(HttpUrl.ReportImageUpload).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
                    @Override
                    public void onSuccess(int statusCode, Entity entituy) {
                        if (entituy.getCode() == 0) {
                            mFunction.onCallBack(entituy.getData().toString());
                        } else {
                            toast(entituy.getMessage());
                        }
                        loading(false);
                       /* DoctorRegImgEntity doctorRegImgEntity = new DoctorRegImgEntity();
                        if (imageType>3) {
                            if (!practiceLicense.getText().toString().equals("")) {
                                doctorRegImgEntity.setNumber(practiceLicense.getText().toString());
                            }
                            if (registerDate.getText().toString().equals("")) {
                                doctorRegImgEntity.setRegisterTime(registerDate.getText().toString());
                            }
                            if(registerValidity.getText().toString().equals("")) {
                                doctorRegImgEntity.setValidTime(registerValidity.getText().toString());
                            }
                        }
                        doctorRegImgEntity.setImgPath(entituy.getData().getSavePath());
                        doctorRegImgEntity.setImgType(imageType);
                        mList.add(doctorRegImgEntity);*/
                    }

                    @Override
                    public void onFailures(int statusCode, String errorMsg) {
                        loading(false);
                        CommonMethod.requestError(statusCode, errorMsg);
                    }
                });
            }
        }.execute(path);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    private Dialog mDialog;
    private View mInflate;
    private LinearLayout mFriend, mZoom;
    private TextView mTvCancel;

    public void showBottomDialog() {
        mDialog = new Dialog(PrescriptionActivitycopy.this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        mInflate = LayoutInflater.from(PrescriptionActivitycopy.this).inflate(R.layout.dialog_shop_share, null);
        //初始化控件
        mFriend = mInflate.findViewById(R.id.mWechatFriend);
        mZoom = mInflate.findViewById(R.id.mWechatZone);
        mTvCancel = mInflate.findViewById(R.id.mCancel);
        mFriend.setOnClickListener(this);
        mZoom.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
        //将布局设置给Dialog
        mDialog.setContentView(mInflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = mDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 10;//设置Dialog距离底部的距离
//       将属性设置给窗体
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
        mDialog.show();//显示对话框
    }


    private void share(SHARE_TYPE type) {
      /*  FileInputStream fis = null;
        try {
            fis = new FileInputStream(mSdPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fis == null){
            return;
        }
        Bitmap thumb  = BitmapFactory.decodeStream(fis);*/

        WXWebpageObject webpageObject = new WXWebpageObject();
        if (mSendGoodsEntity != null) {
            webpageObject.webpageUrl = mSendGoodsEntity.getLinkAddress() + "";
        }else {
            webpageObject.webpageUrl="http://www.newstartcare.com/";
        }
        //用WXWebpageObject对象初始化一个WXMediaMessage，天下标题，描述
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = MyApplication.mDoctorInfoByIdEntity.getDrName() + "为你优选推荐";
        msg.description = "我已为您推荐了合适的优选服务,请点击查看";
        //这块需要注意，图片的像素千万不要太大，不然的话会调不起来微信分享，
        //我在做的时候和我们这的UIMM说随便给我一张图，她给了我一张1024*1024的图片
        //当时也不知道什么原因，后来在我的机智之下换了一张像素小一点的图片好了！
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.icon);
        msg.setThumbImage(thumb);

         /* WXImageObject imageObject = new WXImageObject(bitmap);
          WXMediaMessage msg = new WXMediaMessage();
          msg.mediaObject = imageObject;*/
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;

        switch (type) {
            case Type_WXSceneSession:
                req.scene = SendMessageToWX.Req.WXSceneSession;
                break;
            case Type_WXSceneTimeline:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
        }

        iwxapi.sendReq(req);


    }


    //微信分享回调
    @Override
    public void onReq(BaseReq baseReq) {
        Log.d(TAG,""+baseReq.toString());
    }

    @Override
    public void onResp(BaseResp baseResp) {
        String result;
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "分享成功";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "取消分享";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "分享被拒绝";
                break;
            default:
                result = "发送返回";
                break;
        }
        Toast.makeText(PrescriptionActivitycopy.this, result, Toast.LENGTH_SHORT).show();
    }
}
