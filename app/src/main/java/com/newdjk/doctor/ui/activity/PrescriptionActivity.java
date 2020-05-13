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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.BuildConfig;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.tools.MainConstant;
import com.newdjk.doctor.ui.activity.IM.ChatActivity;
import com.newdjk.doctor.ui.activity.IM.GroupChatActivity;
import com.newdjk.doctor.ui.activity.Mdt.AddMDTDocumentActivity;
import com.newdjk.doctor.ui.activity.Mdt.MDTInputReportFromListForNet2Activity;
import com.newdjk.doctor.ui.activity.login.Authentication1Activity;
import com.newdjk.doctor.ui.entity.ConsultMessageEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.ImRelationRecode;
import com.newdjk.doctor.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.ui.entity.MessageEventRecent;
import com.newdjk.doctor.ui.entity.OnlineRenewalDataEntity;
import com.newdjk.doctor.ui.entity.PatientInfoEntity;
import com.newdjk.doctor.ui.entity.PatientStatusEntity;
import com.newdjk.doctor.ui.entity.PictureMessageEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.SendGoodsEntity;
import com.newdjk.doctor.ui.entity.ServiceIndrocutionEntity;
import com.newdjk.doctor.ui.entity.ShareSuccessEntity;
import com.newdjk.doctor.ui.entity.SignFinshEntity;
import com.newdjk.doctor.ui.entity.SignIDEntity;
import com.newdjk.doctor.ui.entity.YWXListenerEntity;
import com.newdjk.doctor.utils.AppUtils;
import com.newdjk.doctor.utils.AppUtils2;
import com.newdjk.doctor.utils.CertUtis;
import com.newdjk.doctor.utils.ChatActivityUtils;
import com.newdjk.doctor.utils.ImageBase64;
import com.newdjk.doctor.utils.LocationUtils;
import com.newdjk.doctor.utils.LogOutUtil;
import com.newdjk.doctor.utils.PDFviewUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.DownloadCertDialog;
import com.newdjk.doctor.views.LoadDialog;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.org.bjca.sdk.core.kit.BJCASDK;
import cn.org.bjca.sdk.core.kit.YWXListener;
import cn.org.bjca.sdk.core.values.ConstantParams;

public class PrescriptionActivity extends BasicActivity implements IWXAPIEventHandler {
    private static final String TAG = "PrescriptionActivity";
    @BindView(R.id.test_bridge_webView)
    BridgeWebView testBridgeWebView;
    @BindView(R.id.view_cover)
    View viewCover;

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
    private String mPrescriptionMessage;
    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调
    private static final int LOADING_SUCCESS = 2;
    private static final int SHARE_GOOD = 3;
    private static final int SHARE_DIALOG = 4;
    private Uri imageUri;
    private String testMessage = "{\"doctor\":{\"Code\":0,\"Data\":{\"Token\":\"eyJpZCI6NDcsImlhdCI6MTUzNzM1MTY5NCwiZXhwIjoxNTM3MzUxNjk0LCJ0eXBlIjoyLCJjbGllbnQiOm51bGwsInJlZ2lzdHJhdGlvbklkIjpudWxsfQ.qSng8Q6W-ACEEvruV5A9y_dV6lyknEzgiM9aqUusHXU\",\"User\":{\"DepartmentId\":0,\"DoctorId\":47,\"DoctorName\":\"赵季\",\"DrType\":1,\"Mobile\":\"18603016010\",\"OrgId\":[1],\"Position\":0,\"Sex\":3}},\"Message\":\"登录成功\"},\"patient\":{\"ChatStatus\":0,\"Content\":\"ceshi \",\"CreateId\":0,\"CreateTime\":\"\",\"DiseaseId\":\"0\",\"DoctorId\":47,\"DoctorName\":\"赵季\",\"EndTime\":\"\",\"EndType\":0,\"EvaluationTime\":\"\",\"Id\":9,\"OrgId\":1,\"PatientId\":16,\"PatientInfo\":{\"AccountId\":10,\"Birthday\":\"1993-09-18 00:00:00\",\"CreateId\":0,\"Education\":0,\"Invalid\":0,\"Job\":0,\"MedicalType\":0,\"PatientId\":16,\"PatientName\":\"hu\",\"PatientSex\":1,\"PatientType\":0,\"Region\":0,\"UpdateId\":0},\"PatientName\":\"hu\",\"PayStatus\":0,\"PayTime\":\"\",\"StartTime\":\"2018-09-18 19:00:59\",\"Status\":0,\"Type\":1,\"UpdateTime\":\"\"}}";
    private SelectedPictureDialog mSelectedPictureDialog;
    private CallBackFunction mFunction;
    private CallBackFunction mFunction2;
    private Gson mGson;
    private PictureMessageEntity mPictureList;
    private String mId;
    private String mPrescriptionId;
    private String mRejectId;
    private String mIsCancel = "false";
    private int type;
    private int fromIM;
    private String mGoodsRecomBuyId;
    private String APP_ID = "wx55eb72942a9dad20";
    private IWXAPI iwxapi;
    private String mDataSource;
    private String mDataId;
    private String mAction;
    private String shareTitle;
    private String mSubjectBuyRecordId;
    private String hosGroupid;
    private String patientId;
    private MDTDetailEntity mMDTDetailEntity;
    private int mNoticeManageId;
    private String mRecordId;
    private String mServiceType;
    private String prefRecommend;

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

                case SHARE_DIALOG:
                    showBottomDialog();
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
        MyApplication.mActivities.add(this);
        mGson = new Gson();
        loading(true);
        iwxapi = WXAPIFactory.createWXAPI(PrescriptionActivity.this, APP_ID, false);
        iwxapi.handleIntent(this.getIntent(), this);
        type = getIntent().getIntExtra("type", 0);
        fromIM = getIntent().getIntExtra("fromIM", -1);
        CensorateRecordId = getIntent().getStringExtra("CensorateRecordId");
        mPrescriptionMessage = getIntent().getStringExtra("prescriptionMessage");
        mId = getIntent().getStringExtra("action");
        mPrescriptionId = getIntent().getStringExtra("id");
        mRejectId = getIntent().getStringExtra("rejectId");
        mLinkUrl = getIntent().getStringExtra("LinkUrl");
        NIMEI = getIntent().getStringExtra("NIMEI");
        mDataSource = getIntent().getStringExtra("DataSource");
        mDataId = getIntent().getStringExtra("DataId");
        mGoodsRecomBuyId = getIntent().getStringExtra("GoodsRecomBuyId");
        mSubjectBuyRecordId = getIntent().getStringExtra("SubjectBuyRecordId");
        hosGroupid = getIntent().getStringExtra("hosGroupid");
        patientId = getIntent().getStringExtra("patientId");
        mRecordId = getIntent().getStringExtra("RecordId");
        mServiceType = getIntent().getStringExtra("ServiceType");
        mMDTDetailEntity = (MDTDetailEntity) getIntent().getSerializableExtra("MDTDetailEntity");
        mNoticeManageId = getIntent().getIntExtra("NoticeManageId", 0);
        prefRecommend = getIntent().getStringExtra("prefRecommend");



        Log.i("PrescriptionActivity", mPrescriptionMessage + "mSubjectBuyRecordId    " + mSubjectBuyRecordId);
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
        testBridgeWebView.getSettings().setAllowFileAccess(true);
        testBridgeWebView.getSettings().setAppCacheEnabled(true);
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
//            if (fromIM == 1) {
//                testBridgeWebView.loadUrl("file:///android_asset/index.html#/" + "prefRecommend/preEntry?fromIM=1");
//            } else {
//                testBridgeWebView.loadUrl("file:///android_asset/index.html#/" + "prefRecommend/preEntry");
//            }

            if (fromIM == 1) {
                testBridgeWebView.loadUrl("file:///android_asset/index.html#/" + "goodsIndex?fromIM=1");
            } else {
                testBridgeWebView.loadUrl("file:///android_asset/index.html#/" + "goodsIndex");
            }
            // testBridgeWebView.loadUrl("http://wx.newstarthealth.cn/Home/PageRedirect?redirectUrl=http%3a%2f%2fwx.newstarthealth.cn%2findex.html%23%2fdr-home%3fappid%3dwxce06749b2a6e9352%26orgid%3d1");

        } else if (type == 12) {
//            setStatusBarColor(this, R.color.colorPrimary);
//            changeStatusBarTextColor(false);
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/" + "prefRecommend/reDetail?fromIMForAPP=1&DataSource=" + mDataSource + "&DataId=" + mDataId);

        } else if (type == 13) {
//            changeStatusBarTextColor(false);
//            setStatusBarColor(this, R.color.colorPrimary);
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/" + "prefRecommend/myRecom");
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
        } else if (type == 20) {
            //查看推荐详情
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/prefRecommend/myRecomDetail?id=" + mGoodsRecomBuyId);
        } else if (type == 21) {

            testBridgeWebView.loadUrl("file:///android_asset/index.html#/faceConsultation/SetUp");

        } else if (type == 22) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/mdt/triageDr");

        } else if (type == 23) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/mdt/GroupDr");

        } else if (type == 24) {///specialist-home?drid   专科医联体列表
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/specialist-home?drid=" + String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        } else if (type == 25) {//specialist-intro?id专  科医联体id/专科团队id
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/specialist-intro?id=" + hosGroupid);
        } else if (type == 26) {

            testBridgeWebView.loadUrl("file:///android_asset/index.html#/nearly_info?patientId=" + patientId);
        } else if (type == 27) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/mdt/picData");
        } else if (type == 28) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/mdt/Order");
        } else if (type == 29) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/mdtIntror");
        } else if (type == 30) {
            if (BuildConfig.IS_DEBUG) {
                testBridgeWebView.loadUrl("http://drwechat.newstarthealth.cn/index.html#/hlepCenter");
            } else {
                testBridgeWebView.loadUrl("https://drwechat.newstartcare.com/index.html#/hlepCenter");
            }
        } else if (type == 31) {

            testBridgeWebView.loadUrl("file:///android_asset/index.html#/mdt/editData");
            //公告跳转
        } else if (type == 32) {

            testBridgeWebView.loadUrl("file:///android_asset/index.html#/notice-detail?noticeId=" + mNoticeManageId);
        } else if (type == 33) {//问诊订单

            testBridgeWebView.loadUrl("file:///android_asset/index.html#/inquiryOrder");
        } else if (type == 34) {///my-prescription

            testBridgeWebView.loadUrl("file:///android_asset/index.html#/my-prescription");
        } else if (type == 35) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/depictDetail?id=" + mRecordId + "&type=" + mServiceType);

        } else if (type == 36) {
            // /prefRecommend/preDetail?id=59
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/prefRecommend/preDetail?id=" + prefRecommend);

            //内部链接
        } else if (type == 37) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#" + mLinkUrl);
            //外部链接
        } else if (type == 38) {
            liearTitlebar.setVisibility(View.VISIBLE);
            initBackTitle("详情");
            testBridgeWebView.loadUrl(mLinkUrl);
        } else if (type == 39){//开处方
            testBridgeWebView.loadUrl("file:///android_asset/index.html#" + "/prescription?DataSource=6&DataId="+mId);
        }else if (type == 40){//需求详情
            testBridgeWebView.loadUrl("file:///android_asset/index.html#" + "/demandDetail?PatRequireOrderId="+mId);

        }

        else {
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
                LogOutUtil.getInstance().loginOut(PrescriptionActivity.this, true);
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
                Intent chickUnitIntent = new Intent(PrescriptionActivity.this, ChickUnitActivity.class);
                startActivityForResult(chickUnitIntent, 5);
            }
        });
        testBridgeWebView.registerHandler("signId", new BridgeHandler() {
            @Override
            public void handler(final String data, CallBackFunction function) {
                Log.i("PrescriptionActivity", "data=" + data);
                mFunction = function;
                boolean isExists = BJCASDK.getInstance().existsCert(PrescriptionActivity.this);
                boolean ExistStamp = BJCASDK.getInstance().existsStamp(PrescriptionActivity.this);
                /// BJCASDK.getInstance().clearCert(ChatActivity.this);
                // BJCASDK.getInstance().startUrl(ChatActivity.this, Contants.clientId, 3);
                // BJCASDK.getInstance().startUrl(ChatActivity.this, Contants.clientId, 1);


                if (!isExists) {

                    DownloadCertDialog mDialog = new DownloadCertDialog(mActivity);
                    mDialog.show("", "", new DownloadCertDialog.DialogListener() {

                        @Override
                        public void confirm() {
                            BJCASDK.getInstance().certDown(PrescriptionActivity.this, Contants.clientId, SpUtils.getString(Contants.userName), new YWXListener() {
                                @Override
                                public void callback(String s) {
                                    YWXListenerEntity yWXListenerEntity = mGson.fromJson(s, YWXListenerEntity.class);
                                    String status = yWXListenerEntity.getStatus();
                                    String message = yWXListenerEntity.getMessage();
                                    if (status != null && status.equals("0")) {
                                        boolean ExistStamp1 = BJCASDK.getInstance().existsStamp(PrescriptionActivity.this);
                                        if (!ExistStamp1) {
                                            BJCASDK.getInstance().drawStamp(PrescriptionActivity.this, Contants.clientId, new YWXListener() {
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

                                                        boolean isPinExempt = BJCASDK.getInstance().isPinExempt(PrescriptionActivity.this);
                                                        showRememberDialog(PrescriptionActivity.this, uniqueIds, isPinExempt, signIDEntity.getPrescriptionId());
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
                                            boolean isPinExempt = BJCASDK.getInstance().isPinExempt(PrescriptionActivity.this);
                                            showRememberDialog(PrescriptionActivity.this, uniqueIds, isPinExempt, signIDEntity.getPrescriptionId());
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
                        BJCASDK.getInstance().drawStamp(PrescriptionActivity.this, Contants.clientId, new YWXListener() {
                            @Override
                            public void callback(String msg) {
                                YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                String status = yWXListenerEntity.getStatus();
                                String message = yWXListenerEntity.getMessage();
                                if (status != null && status.equals("0")) {
                                    SignIDEntity signIDEntity = mGson.fromJson(data, SignIDEntity.class);
                                    List<String> uniqueIds = new ArrayList<>();
                                    uniqueIds.add(signIDEntity.getUniqueId());
                                    boolean isPinExempt = BJCASDK.getInstance().isPinExempt(PrescriptionActivity.this);
                                    showRememberDialog(PrescriptionActivity.this, uniqueIds, isPinExempt, signIDEntity.getPrescriptionId());
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
                        boolean isPinExempt = BJCASDK.getInstance().isPinExempt(PrescriptionActivity.this);
                        showRememberDialog(PrescriptionActivity.this, uniqueIds, isPinExempt, signIDEntity.getPrescriptionId());

                    }
                }
            }
        });
        testBridgeWebView.registerHandler("Preview", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                mPictureList = mGson.fromJson(data, PictureMessageEntity.class);
                Intent intent = new Intent(PrescriptionActivity.this, PictureViewerActivity.class);
                intent.putExtra("pic_all", (ArrayList<String>) mPictureList.getUrl());
                intent.putExtra("pic_position", mPictureList.getPosition());
                startActivity(intent);
            }
        });
        testBridgeWebView.registerHandler("APP", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (data != null && data.equals("Img")) {
                    mFunction2 = function;
                    mSelectedPictureDialog = new SelectedPictureDialog(PrescriptionActivity.this, "first");
                    mSelectedPictureDialog.show();
                }
            }
        });

        testBridgeWebView.registerHandler("SendMessage", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("ArchivesActivity", "data=" + data);


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
//                    Intent intentTalk = new Intent(mActivity, ChatActivity.class);
//                    String name;
//                    PatientInfoEntity patientInfoEntity = patientStatusEntity.getPatientInfo();
//                    if (patientInfoEntity != null) {
//                        name = patientInfoEntity.getPatientName();
//                    } else {
//                        name = patientStatusEntity.getApplicantName();
//                    }
//                    SpUtils.put(Contants.patientName, patientInfoEntity.getPatientName());
//                    SpUtils.put(Contants.patientID, patientInfoEntity.getPatientId());
//                    intentTalk.putExtra(Contants.FRIEND_NAME, name);
//                    intentTalk.putExtra("status", 0);
//                    intentTalk.putExtra("prescriptionMessage", json);
//                    intentTalk.putExtra("accountId", patientStatusEntity.getApplicantId());
//                    intentTalk.putExtra("imgPath", patientStatusEntity.getApplicantHeadImgUrl());
//                    intentTalk.putExtra(Contants.FRIEND_IDENTIFIER, patientStatusEntity.getApplicantIMId());
//                    mActivity.startActivity(intentTalk);

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


                LocationUtils.getinstanse().initlocation(PrescriptionActivity.this, new LocationUtils.locationStatusListener() {
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
                mSendGoodsEntity = mGson.fromJson(data, SendGoodsEntity.class);
                GetGoodsRecommendWxShareTitle();
                sharedata = data;
                showBottomDialog();


            }
        });

        testBridgeWebView.registerHandler("ServiceIntroduction", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d(TAG, data);
                try {
                    ServiceIndrocutionEntity entity = mGson.fromJson(data, ServiceIndrocutionEntity.class);
                    Intent intent = new Intent(PrescriptionActivity.this, ServiceDetailActivity.class);
                    intent.putExtra("title", entity.getTitle());
                    intent.putExtra("LinkUrl", entity.getLinkUrl());
                    startActivity(intent);
                } catch (Exception e) {

                }

            }
        });
        testBridgeWebView.registerHandler("toRecommendInsideLink", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d(TAG, data);
                try {
                    ServiceIndrocutionEntity entity = mGson.fromJson(data, ServiceIndrocutionEntity.class);
                    Intent intent = new Intent(PrescriptionActivity.this, PrescriptionActivity.class);
                    intent.putExtra("title", entity.getTitle());
                    intent.putExtra("LinkUrl", entity.getLinkUrl());
                    intent.putExtra("type", 37);
                    startActivity(intent);
                } catch (Exception e) {

                }

            }
        });

        testBridgeWebView.registerHandler("getSubjectBuyRecordId", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {


                function.onCallBack(mSubjectBuyRecordId);

            }
        });

        testBridgeWebView.registerHandler("addMdtDocuments", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {


                Intent mdtIntent = new Intent(PrescriptionActivity.this, AddMDTDocumentActivity.class);
                mdtIntent.putExtra("MDTDetailEntity", mMDTDetailEntity);
                startActivity(mdtIntent);

            }
        });


        testBridgeWebView.registerHandler("toGroupChat", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {

                getIMRelationRecord(data);

            }
        });

        testBridgeWebView.registerHandler("toMyReport", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {

                Intent mdtintent2 = new Intent(mContext, MDTInputReportFromListForNet2Activity.class);
                mdtintent2.putExtra(Contants.SubjectBuyRecordId, data);
                mContext.startActivity(mdtintent2);

            }
        });


        testBridgeWebView.registerHandler("ServiceIntroduction", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d(TAG, data);
                try {
                    ServiceIndrocutionEntity entity = mGson.fromJson(data, ServiceIndrocutionEntity.class);

                    Intent intent = new Intent(mContext, ServiceDetailActivity.class);
                    intent.putExtra("title", entity.getTitle());
                    intent.putExtra("LinkUrl", entity.getLinkUrl());
                    startActivity(intent);
                } catch (Exception e) {

                }

            }
        });

        testBridgeWebView.registerHandler("GetNoticeManageById", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {

                function.onCallBack(mNoticeManageId + "");

            }
        });

        testBridgeWebView.registerHandler("SecondTreatDetail", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {

                Intent SecondDiagnosisIntent = new Intent(mContext, SecondDiagnosisActivity.class);
                SecondDiagnosisIntent.putExtra("MedicalRecordId", Integer.parseInt(data));
                mContext.startActivity(SecondDiagnosisIntent);

            }
        });
        testBridgeWebView.registerHandler("SecondTreatCancelDetail", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {

                Intent SecondDiagnosisIntent = new Intent(mContext, SecondDiagnosisActivity.class);
                SecondDiagnosisIntent.putExtra("MedicalRecordId", Integer.parseInt(data));
                SecondDiagnosisIntent.putExtra("type", 1);
                mContext.startActivity(SecondDiagnosisIntent);

            }
        });
        testBridgeWebView.registerHandler("SecondTreatReport", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {

                Intent intent = new Intent(mContext, SecondDiagnosisQuestionActivity.class);
                intent.putExtra("MedicalRecordId", Integer.parseInt(data));
                mContext.startActivity(intent);

            }
        });

        //显示pdf报告
        testBridgeWebView.registerHandler("showPdfReport", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d(TAG, data);
                try {
                    ServiceIndrocutionEntity entity = mGson.fromJson(data, ServiceIndrocutionEntity.class);
                    PDFviewUtils.getInstanse().showPDF(mContext, entity.getLinkUrl(), entity.getTitle());
                } catch (Exception e) {
                    toast("数据解析失败，无法查阅报告");
                }
            }
        });

        //显示pdf报告

        testBridgeWebView.registerHandler("goDetailGoodsId", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d(TAG, data);
                Intent prescriptionTCMIntent = new Intent(mContext, PrescriptionActivity.class);
                prescriptionTCMIntent.putExtra("prefRecommend", data);
                prescriptionTCMIntent.putExtra("type", 36);

                mContext.startActivity(prescriptionTCMIntent);

            }
        });
        //去认证
        testBridgeWebView.registerHandler("toAuthenticate", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                int  mStatus = SpUtils.getInt(Contants.Status, 0);
              //  医生状态(0-未审核，1-审核通过，2-审核失败，3-审核中)
                    if (mStatus==0||mStatus==2){
                        Intent intent = new Intent(PrescriptionActivity.this, Authentication1Activity.class);
                        startActivity(intent);
                    }else if (mStatus==3||mStatus==1){
                        AppUtils2.checkAuthenStatus(mStatus, PrescriptionActivity.this);

                    }


            }
        });
    }

    private void GetGoodsRecommendWxShareTitle() {
        Map<String, String> map = new HashMap<>();
        map.put("DataId", mSendGoodsEntity.getDataId() + "");
        map.put("DataSource", mSendGoodsEntity.getDataSource() + "");

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);

        mMyOkhttp.post().url(HttpUrl.GetGoodsRecommendWxShareConten).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity entity) {

                if (entity.getCode() == 0) {
                    try {
                        JSONObject jsonObject = new JSONObject(entity.getData().toString());
                        shareTitle = jsonObject.getString("Title");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                }
                loading(false);
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {

                CommonMethod.requestError(statusCode, errorMsg);
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
                        BJCASDK.getInstance().sign(PrescriptionActivity.this, Contants.clientId, uniqueIds, new YWXListener() {
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
                                            CertUtis.showCertUpdateDialog(PrescriptionActivity.this);
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
                                    BJCASDK.getInstance().sign(PrescriptionActivity.this, Contants.clientId, uniqueIds, new YWXListener() {
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

                                                    if (status.toLowerCase().contains("004x030")){
                                                        CertUtis.showCertUpdateDialog(PrescriptionActivity.this);
                                                    }else {
                                                        ToastUtil.setToast("电子签名失败，请核对签名密码是否正确和网络是否正常！+(" + status + ")");
                                                    }

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
            BJCASDK.getInstance().sign(PrescriptionActivity.this, Contants.clientId, uniqueIds, new YWXListener() {
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
                                CertUtis.showCertUpdateDialog(PrescriptionActivity.this);
                            }else {
                                ToastUtil.setToast("电子签名失败，请核对签名密码是否正确和网络是否正常！+(" + status + ")");
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

        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQQweixinDialog();
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
            case R.id.mhuanzhe:
                Intent intent = new Intent(PrescriptionActivity.this, PatientListActivity.class);
                intent.putExtra("SendGoodsEntity", sharedata);
                startActivity(intent);
                mDialog.dismiss();
                break;
            case R.id.mWechatZone:


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
        EventBus.getDefault().register(this);
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
                            mFunction2.onCallBack(entituy.getData().toString());
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
        EventBus.getDefault().unregister(this);
        MyApplication.mActivities.remove(this);
    }


    private Dialog mDialog;
    private View mInflate;
    private LinearLayout mFriend, mZoom,mhuanzhe;
    private TextView mTvCancel;

    public void showBottomDialog() {
        mDialog = new Dialog(PrescriptionActivity.this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        mInflate = LayoutInflater.from(PrescriptionActivity.this).inflate(R.layout.dialog_shop_share, null);
        //初始化控件
        mFriend = mInflate.findViewById(R.id.mWechatFriend);
        mhuanzhe = mInflate.findViewById(R.id.mhuanzhe);
        mTvCancel = mInflate.findViewById(R.id.mCancel);
        mFriend.setOnClickListener(this);
        mhuanzhe.setOnClickListener(this);
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

    public void showQQweixinDialog() {
        mDialog = new Dialog(PrescriptionActivity.this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        mInflate = LayoutInflater.from(PrescriptionActivity.this).inflate(R.layout.dialog_weixin_share, null);
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
        // mSendGoodsEntity = mGson.fromJson(sharedata, SendGoodsEntity.class);
        if (mSendGoodsEntity != null) {
            if (!TextUtils.isEmpty(mSendGoodsEntity.getLinkAddress())) {
                webpageObject.webpageUrl = mSendGoodsEntity.getLinkAddress();

            } else {
                webpageObject.webpageUrl = "http://www.newstartcare.com/";
            }
        } else {
            webpageObject.webpageUrl = "http://www.newstartcare.com/";

        }
        //用WXWebpageObject对象初始化一个WXMediaMessage，天下标题，描述
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        if (TextUtils.isEmpty(shareTitle)) {
            msg.title = MyApplication.mDoctorInfoByIdEntity.getDrName() + "为你优选推荐";
        } else {
            msg.title = shareTitle;
        }

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
        Log.d(TAG, "" + baseReq.toString());
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
        Toast.makeText(PrescriptionActivity.this, result, Toast.LENGTH_SHORT).show();
        if (result.equals("分享成功")) {
            MyApplication.exit();
        }
    }

    //群聊
    private void getIMRelationRecord(final String imgroupID) {

        Map<String, String> map = new HashMap<>();
        map.put("IMGroupId", imgroupID);

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(HttpUrl.QueryMDTBySubjectBuyIMGroupId).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTDetailEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTDetailEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    Intent intent = new Intent(mContext, GroupChatActivity.class);
                    intent.putExtra("action", "MDT");
                    intent.putExtra("MDTDetailEntity", entity.getData());
                    intent.putExtra(Contants.FRIEND_NAME, "");
                    intent.putExtra(Contants.FRIEND_IDENTIFIER, imgroupID);
                    mContext.startActivity(intent);

                } else {
                    Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
                LoadDialog.clear();
            }
        });
    }

    //单聊
    private void getIMRelationRecord(String patientImId, String doctorIMId, final String faceUrl) {

        Map<String, String> map = new HashMap<>();
        map.put("DoctorIMId", doctorIMId);
        map.put("PatientIMId", patientImId);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetIMRelationRecord).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ImRelationRecode>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ImRelationRecode> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    ImRelationRecode imRelationRecode = entity.getData();
                    if (imRelationRecode != null) {
                        int serviceCode = imRelationRecode.getServiceType();
                        String recordId = String.valueOf(imRelationRecode.getRecordId());
                        //1 图文 2 视频 3 续方  5 护理咨询  6远程护理
                        SpUtils.put(Contants.patientName, imRelationRecode.getPatientName());
                        SpUtils.put(Contants.patientID, imRelationRecode.getPatientId());
                        Log.i("ChatActivity111", "accountId=" + imRelationRecode.getAccountId());

                        if (serviceCode == 1 || serviceCode == 5) {
                            QueryConsultDoctorAppMessageByPage(recordId, imRelationRecode.getAccountId(), imRelationRecode.getPatientName());
                        } else if (serviceCode == 2 || serviceCode == 6) {
                            QueryvideoDoctorAppMessageByPage(recordId, imRelationRecode.getAccountId(), imRelationRecode.getPatientName());
                        } else if (serviceCode == 3) {
                            QueryRenewalDoctorAppMessageByPage(recordId, imRelationRecode.getAccountId(), imRelationRecode.getPatientName());
                        } else if (serviceCode == 0) {

                            PatientInfoEntity PatientInfo = new PatientInfoEntity();

                            //   PatientInfo.setBirthday(mDataList.get(position).getBirthday());
                            String paName = imRelationRecode.getPatientName();

                            PatientInfo.setPatientName(paName);
                            PatientInfo.setPatientId(imRelationRecode.getPatientId());
                            Type jsonType = new TypeToken<PrescriptionMessageEntity<InquiryRecordListDataEntity>>() {
                            }.getType();
                            InquiryRecordListDataEntity InquiryRecordListDataEntity = new InquiryRecordListDataEntity();
                            //InquiryRecordListDataEntity.setDoctorPatientRelation(DoctorPatientRelation);
                            InquiryRecordListDataEntity.setPatientInfo(PatientInfo);
                            PrescriptionMessageEntity<InquiryRecordListDataEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), jsonType);
                            prescriptionMessageEntity.setPatient(InquiryRecordListDataEntity);
                            String json = mGson.toJson(prescriptionMessageEntity);
                            SpUtils.put(Contants.patientName, imRelationRecode.getPatientName());
                            SpUtils.put(Contants.patientID, imRelationRecode.getPatientId());
                            Intent intent = new Intent(mContext, ChatActivity.class);
                            intent.putExtra("status", 0);
                            intent.putExtra(Contants.FRIEND_NAME, imRelationRecode.getPatientName());
                            intent.putExtra(Contants.FRIEND_IDENTIFIER, imRelationRecode.getPatientIMId());
                            intent.putExtra("drId", imRelationRecode.getDoctorId());
                            intent.putExtra("mLeftAvatorImagePath", faceUrl);
                            intent.putExtra("prescriptionMessage", json);
                            intent.putExtra("accountId", imRelationRecode.getAccountId());
                            intent.putExtra("fromHome", 1);
                            intent.putExtra("imgPath", imRelationRecode.getApplicantHeadImgUrl());

                            //intent.putExtra("head",imRelationRecode.)
                            mContext.startActivity(intent);
                        }
                    }
                } else {
                    Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
                LoadDialog.clear();
            }
        });
    }

    private void QueryConsultDoctorAppMessageByPage(final String id, final int accoundid, final String patientname) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("Id", id);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetConsultRecord).params(requestMap).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ConsultMessageEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ConsultMessageEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    ConsultMessageEntity consultMessageEntity = entity.getData();
                    Type caonsultJsonType = new TypeToken<PrescriptionMessageEntity<ConsultMessageEntity>>() {
                    }.getType();
                    //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                    PrescriptionMessageEntity<ConsultMessageEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), caonsultJsonType);
                    consultMessageEntity.setRecordId(id);
                    prescriptionMessageEntity.setPatient(consultMessageEntity);
                    String json = mGson.toJson(prescriptionMessageEntity);
                    String doctorImId = consultMessageEntity.getDoctorIMId();
                    String doctorName = consultMessageEntity.getDoctorName();
                    Intent consultIntentTalk = new Intent(mContext, ChatActivity.class);
                    Log.i("zdp", "json=" + json);
                    consultIntentTalk.putExtra(Contants.FRIEND_NAME, doctorName);
                    consultIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, consultMessageEntity.getApplicantIMId());
                    consultIntentTalk.putExtra("consultMessageEntity", consultMessageEntity);
                    consultIntentTalk.putExtra("drId", consultMessageEntity.getDoctorId());
                    consultIntentTalk.putExtra("action", "pictureConsultfromhome");
                    consultIntentTalk.putExtra("prescriptionMessage", json);
                    consultIntentTalk.putExtra("accountId", accoundid);
                    consultIntentTalk.putExtra("fromHome", 1);
                    consultIntentTalk.putExtra("imgPath", consultMessageEntity.getApplicantHeadImgUrl());
                    consultIntentTalk.putExtra(Contants.FRIEND_NAME, patientname);
                    mContext.startActivity(consultIntentTalk);
                    MessageEventRecent messageEvent = new MessageEventRecent();
                    messageEvent.setmType(MainConstant.UPDATEPUSHMESSAGELIST);
                    EventBus.getDefault().post(messageEvent);
                    //  Log.i("HomeFragment","entity="+entity.getData().toString());
                } else {
                    Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }
                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                LoadDialog.clear();
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void QueryRenewalDoctorAppMessageByPage(final String id, final int AccountId, final String patientname) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("Id", id);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetPrescriptionApply).headers(headMap).params(requestMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<OnlineRenewalDataEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<OnlineRenewalDataEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    OnlineRenewalDataEntity onlineRenewalDataEntity = entity.getData();
                    onlineRenewalDataEntity.setRecordId(id);
                    Type renewalJsonType = new TypeToken<PrescriptionMessageEntity<OnlineRenewalDataEntity>>() {
                    }.getType();
                    //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                    PrescriptionMessageEntity<OnlineRenewalDataEntity> renewalMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), renewalJsonType);
                    renewalMessageEntity.setPatient(onlineRenewalDataEntity);
                    String renewalJson = mGson.toJson(renewalMessageEntity);
                    String doctorImId = onlineRenewalDataEntity.getDoctorIMId();
                    String doctorName = onlineRenewalDataEntity.getDoctorName();
                    Intent renewalIntentTalk = new Intent(mContext, ChatActivity.class);
                    renewalIntentTalk.putExtra(Contants.FRIEND_NAME, onlineRenewalDataEntity.getApplicantName());
                    renewalIntentTalk.putExtra("onlineRenewalDataEntity", onlineRenewalDataEntity);
                    renewalIntentTalk.putExtra("action", "onlineRenewalfromHome");
                    renewalIntentTalk.putExtra("drId", onlineRenewalDataEntity.getDoctorId());
                    renewalIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, onlineRenewalDataEntity.getApplicantIMId());
                    renewalIntentTalk.putExtra("prescriptionMessage", renewalJson);
                    renewalIntentTalk.putExtra("accountId", AccountId);
                    renewalIntentTalk.putExtra(Contants.FRIEND_NAME, patientname);
                    renewalIntentTalk.putExtra("fromHome", 1);
                    renewalIntentTalk.putExtra("imgPath", onlineRenewalDataEntity.getApplicantHeadImgUrl());
                    mContext.startActivity(renewalIntentTalk);
                    MessageEventRecent messageEvent = new MessageEventRecent();
                    messageEvent.setmType(MainConstant.UPDATEPUSHMESSAGELIST);
                    EventBus.getDefault().post(messageEvent);

                    //  Log.i("HomeFragment","entity="+entity.getData().toString());
                } else {
                    Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }
                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                LoadDialog.clear();
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void QueryvideoDoctorAppMessageByPage(final String id, final int AccountId, final String patientname) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("Id", id);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetInquiryRecord).headers(headMap).params(requestMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<InquiryRecordListDataEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<InquiryRecordListDataEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    InquiryRecordListDataEntity inquiryRecordListDataEntity = entity.getData();
                    inquiryRecordListDataEntity.setRecordId(id);
                    Type videoJsonType = new TypeToken<PrescriptionMessageEntity<InquiryRecordListDataEntity>>() {
                    }.getType();
                    //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                    PrescriptionMessageEntity<InquiryRecordListDataEntity> videoMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), videoJsonType);
                    videoMessageEntity.setPatient(inquiryRecordListDataEntity);
                    String videoJson = mGson.toJson(videoMessageEntity);
                    String doctorImId = inquiryRecordListDataEntity.getDoctorIMId();
                    String doctorName = inquiryRecordListDataEntity.getDoctorName();
                    Intent videoIntentTalk = new Intent(mContext, ChatActivity.class);
                    videoIntentTalk.putExtra(Contants.FRIEND_NAME, doctorName);
                    videoIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, inquiryRecordListDataEntity.getApplicantIMId());
                    videoIntentTalk.putExtra("inquiryRecordListDataEntity", inquiryRecordListDataEntity);
                    videoIntentTalk.putExtra("action", "videoInterrogationfromhhome");
                    videoIntentTalk.putExtra("drId", inquiryRecordListDataEntity.getDoctorId());
                    videoIntentTalk.putExtra("prescriptionMessage", videoJson);
                    videoIntentTalk.putExtra("accountId", AccountId);
                    videoIntentTalk.putExtra("fromHome", 1);
                    videoIntentTalk.putExtra("imgPath", inquiryRecordListDataEntity.getApplicantHeadImgUrl());

                    videoIntentTalk.putExtra(Contants.FRIEND_NAME, patientname);
                    mContext.startActivity(videoIntentTalk);
                    MessageEventRecent messageEvent = new MessageEventRecent();
                    messageEvent.setmType(MainConstant.UPDATEPUSHMESSAGELIST);
                    EventBus.getDefault().post(messageEvent);
                } else {
                    Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }
                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                LoadDialog.clear();
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(ShareSuccessEntity userEvent) {
        if (userEvent.isshareSuccess) {
            finish();
        }
    }
}
