package com.newdjk.doctor.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.ConsultMessageEntity;
import com.newdjk.doctor.ui.entity.H5PictureMessageEntity;
import com.newdjk.doctor.ui.entity.PatientEntity;
import com.newdjk.doctor.ui.entity.PatientStatusEntity;
import com.newdjk.doctor.ui.entity.PictureMessageEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.ui.entity.RemarkAndDrKeyEntity;
import com.newdjk.doctor.ui.entity.ServiceIndrocutionEntity;
import com.newdjk.doctor.ui.entity.ShopLoginEntity;
import com.newdjk.doctor.ui.entity.UpdatePatientCountEntity;
import com.newdjk.doctor.ui.entity.UpdatePatientViewEntity;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.ui.entity.WebShopUseinfoEntity;
import com.newdjk.doctor.ui.entity.ZhuanhuiSuccess;
import com.newdjk.doctor.utils.ChatActivityUtils;
import com.newdjk.doctor.utils.LogOutUtil;
import com.newdjk.doctor.utils.MathUtils;
import com.newdjk.doctor.utils.PDFviewUtils;
import com.newdjk.doctor.utils.SQLiteUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.SelectedPictureDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;

import static com.newdjk.doctor.MyApplication.getContext;

public class WebViewActivity extends BasicActivity {


    private static final String TAG = "WebViewActivity";
    @BindView(R.id.test_bridge_webView)
    BridgeWebView testBridgeWebView;
    @BindView(R.id.view_cover)
    View viewCover;
    private String mUrl = "";
    private int type;
    private int id, mDoctorType;
    private int code;
    private String mName;
    private Gson mGson;


    private static final int LOADING_SUCCESS = 2;
    private String mPayType;
    private PictureMessageEntity mPictureList;

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
    private String mPatientName;
    private int mImgId;
    private int medicalTempId;
    private String mServiceType;
    private String mRelationId;
    private String mDocId;
    private int mPatientId;
    private int index;
    private String mName2;
    private String mAccountId;
    private String mSex;
    private String mMobilePhone;
    private int mDoctorId;
    private String mDoctorName;
    private String goodid;
    private String recommendId;
    private String mLinkUrl;
    private String mUseinfo;
    private CallBackFunction mFunction;
    private SelectedPictureDialog mSelectedPictureDialog;

    @Override
    protected int initViewResId() {
        return R.layout.my_pharmacy_webview;
    }

    @Override
    protected void initView() {

        mGson = new Gson();
        Intent intent = getIntent();
        loading(true);
        if (intent != null) {
            type = intent.getIntExtra("type", 0);
            id = intent.getIntExtra("id", 0);
            code = intent.getIntExtra("code", 0);
            mName = intent.getStringExtra("doctorName");
            mDoctorType = intent.getIntExtra("doctorType", -1);
            index = intent.getIntExtra("index", 0);
            mImgId = intent.getIntExtra("imgId", 0);
            medicalTempId = intent.getIntExtra("medicalTempId", 0);
            mPatientName = intent.getStringExtra("patientName");
            mDocId = intent.getStringExtra("docId");
            mServiceType = intent.getStringExtra("serviceType"); //服务类型
            mRelationId = intent.getStringExtra("relationId");//服务id
            mPatientId = intent.getIntExtra("patientId", 0);//就诊人id
            mName2 = intent.getStringExtra("Name");//就诊人id
            mAccountId = intent.getStringExtra("AccountId");//就诊人id
            mSex = intent.getStringExtra("Sex");//就诊人id
            mMobilePhone = intent.getStringExtra("Mobile");//就诊人id
            mDoctorId = intent.getIntExtra("doctorId", 0);
            mDoctorName = getIntent().getStringExtra("doctorName");
            mLinkUrl = getIntent().getStringExtra("LinkUrl");
            mUseinfo = getIntent().getStringExtra("prescriptionMessage");


        }
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







        mGson = new Gson();
        if (type == 1) {
            mUrl = "file:///android_asset/kefu.html?userId=" + mAccountId + "&nickName=" + mName2 + "&sex=" + mSex + "&phone=" + mMobilePhone;

        } else if (type == 29) {
            mUrl = "file:///android_asset/index.html#/" + "Patient-report?DrId=" + mDoctorId + "&DrName=" + mDoctorName + "&DrType=" + mDoctorType;

        } else if (type == 3) {
            mUrl = "file:///android_asset/index.html#/" + "gradeCoop";


        } else if (type == 5) {
            mUrl = "file:///android_asset/index.html#/" + "setReferralList";

        } else if (type == 6) {
            mUrl = "file:///android_asset/index.html#/" + "setReferralList";


        } else if (type == 7) {
            mUrl = "file:///android_asset/index.html#/" + "referral-record";


        } else if (type == 8) {
            mUrl = "file:///android_asset/index.html#/" + "inspect/checklist";

        } else if (type == 9) {
            mUrl = "http://cf.newstarthealth.cn/#/recommendSort";

        } else if (type == 10) {

            mUrl = mLinkUrl;
        }else if (type == 11) {
            mUrl = "file:///android_asset/index.html#/" + "teamList";
        }else if (type == 12){
            mUrl = mLinkUrl;
        }
        else if (type == 13){
            mUrl ="file:///android_asset/index.html#"+mLinkUrl;
        }
        // /understandSignature  了解电子签名       /help 帮助


        testBridgeWebView.loadUrl(mUrl);
        sendNative();
        testBridgeWebView.registerHandler("BackToIM", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                finish();
            }
        });
        testBridgeWebView.registerHandler("Back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("zdp", "data=" + data);
                finish();
            }
        });


        testBridgeWebView.registerHandler("Preview", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                mPictureList = mGson.fromJson(data, PictureMessageEntity.class);
                Intent intent = new Intent(WebViewActivity.this, PictureViewerActivity.class);
                intent.putExtra("pic_all", (ArrayList<String>) mPictureList.getUrl());
                intent.putExtra("pic_position", mPictureList.getPosition());
                startActivity(intent);
            }
        });
        testBridgeWebView.registerHandler("APP", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {

            }
        });
        testBridgeWebView.registerHandler("tokenInvalid", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                LogOutUtil.getInstance().loginOut(WebViewActivity.this, true);
            }
        });
        testBridgeWebView.registerHandler("gotofenjizhuanzhen", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent fenjizhuanzhen = new Intent(mContext, FenjiZhuanzhenActivity.class);
                startActivity(fenjizhuanzhen);
                finish();
            }
        });
        testBridgeWebView.registerHandler("GradedReferralsRollout", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                EventBus.getDefault().post(new ZhuanhuiSuccess(true));
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
        testBridgeWebView.registerHandler("Back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                finish();
            }
        });
        testBridgeWebView.registerHandler("changePatTap", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                EventBus.getDefault().post(new UpdatePatientViewEntity(true));
            }
        });
        testBridgeWebView.registerHandler("APP", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (data != null && data.equals("Img")) {
                    mFunction = function;
                    mSelectedPictureDialog = new SelectedPictureDialog(getContext(), "first");
                    mSelectedPictureDialog.show();
                }
            }
        });
        testBridgeWebView.registerHandler("createWebView", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(mContext, CustomLinkActivity.class);
                intent.putExtra("url", data);
                mContext.startActivity(intent);
            }
        });
        testBridgeWebView.registerHandler("Preview", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d(TAG, "显示大图" + data);
                try {
                    mPictureList = mGson.fromJson(data, PictureMessageEntity.class);
                    Intent intent = new Intent(getContext(), PictureViewerActivity.class);
                    intent.putExtra("pic_all", (ArrayList<String>) mPictureList.getUrl());
                    intent.putExtra("pic_position", mPictureList.getPosition());
                    startActivity(intent);
                } catch (Exception e) {
                    H5PictureMessageEntity mH5PictureList = mGson.fromJson(data, H5PictureMessageEntity.class);
//                    Intent intent = new Intent(ArchivesActivity.this, PictureViewerActivity.class);
//                    intent.putExtra("pic_all", (ArrayList<String>) mH5PictureList.getUrl());
//                    intent.putExtra("pic_position", mH5PictureList.g
//                    omJson(data, H5PictureMessageEntity.class);

                    Intent intent = new Intent(mContext, ShowOriginPictureActivity.class);
                    intent.putExtra("path", mH5PictureList.getUrl().get(mH5PictureList.getPosition()).getDisplayPath());
                    Log.d(TAG, "图片消息右边大图Original" + "   " + mH5PictureList.getUrl().get(mH5PictureList.getPosition()).getDisplayPath());
                    mContext.startActivity(intent);

                }

            }
        });
        testBridgeWebView.registerHandler("ToNewLink", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {


                Intent intent = new Intent(getContext(), WebViewActivity.class);

                intent.putExtra("type", 13);
                intent.putExtra("LinkUrl", data);
                intent.putExtra("prescriptionMessage",mUseinfo+"");
                startActivity(intent);

            }
        });


        testBridgeWebView.registerHandler("GradedReferralsRollout", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                EventBus.getDefault().post(new ZhuanhuiSuccess(true));
            }
        });
        testBridgeWebView.registerHandler("ZhuanZhenSuccess", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d(TAG, "转诊成功收到消息");
                EventBus.getDefault().post(new ZhuanhuiSuccess(true));
            }
        });

        testBridgeWebView.registerHandler("changeRemark", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("ArchivesActivity", "changeRemark=" + data);
                //     RemarkAndDrKeyEntity remarkAndDrKeyEntity = mGson.fromJson(data,RemarkAndDrKeyEntity.class);

            }
        });
        testBridgeWebView.registerHandler("changeDrKey", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    RemarkAndDrKeyEntity remarkAndDrKeyEntity = mGson.fromJson(data, RemarkAndDrKeyEntity.class);
                    int isKey = remarkAndDrKeyEntity.getIsKey();
                    int patientId = remarkAndDrKeyEntity.getPatientId();
                    SQLiteUtils.getInstance().updateImDataByPatientId(String.valueOf(isKey), String.valueOf(patientId));
                    Log.i("ArchivesActivity", "changeDrKey=" + data);
                } catch (Exception e) {

                }

            }
        });
        testBridgeWebView.registerHandler("SendMessage", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("ArchivesActivity", "data=" + data);
                //try {
                //  if (mAction != null && mAction.equals("patientList")) {
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
//                        Intent intentTalk = new Intent(mActivity, ChatActivity.class);
//                        String name;
//                        PatientInfoEntity patientInfoEntity = patientStatusEntity.getPatientInfo();
//                        if (patientInfoEntity != null) {
//                            name = patientInfoEntity.getPatientName();
//                        } else {
//                            name = patientStatusEntity.getApplicantName();
//                        }
//                        SpUtils.put(Contants.patientName, patientInfoEntity.getPatientName());
//                        SpUtils.put(Contants.patientID, patientInfoEntity.getPatientId());
//                        intentTalk.putExtra(Contants.FRIEND_NAME, name);
//                        intentTalk.putExtra("status", 0);
//                        intentTalk.putExtra("prescriptionMessage", json);
//                        intentTalk.putExtra("accountId", patientStatusEntity.getApplicantId());
//                        intentTalk.putExtra("imgPath", patientStatusEntity.getApplicantHeadImgUrl());
//                        intentTalk.putExtra(Contants.FRIEND_IDENTIFIER, patientStatusEntity.getApplicantIMId());
//                        mActivity.startActivity(intentTalk);
                ChatActivityUtils.getinStanse().toChat(patientStatusEntity.getApplicantIMId(), SpUtils.getString(Contants.identifier), patientStatusEntity.getApplicantHeadImgUrl(),mContext);

//                    } else {
//                        getActivity().finish();
//                    }
//                } catch (Exception e) {
//
//                }

            }


        });
        testBridgeWebView.registerHandler("tokenInvalid", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                LogOutUtil.getInstance().loginOut((BasicActivity) WebViewActivity.this, true);
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

        //更改收入是否显示
        testBridgeWebView.registerHandler("IsHideIncome", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int isShowIncome = jsonObject.getInt("incomeState");
                    SpUtils.put(Contants.isShowIncome, isShowIncome);
                    EventBus.getDefault().post(new UpdatePushView(12));
                } catch (Exception e) {

                }
            }
        });


        testBridgeWebView.registerHandler("GetUserInfo", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    String userInfo = WebViewActivity.this.getIntent().getStringExtra("prescriptionMessage");
                    Log.i("zdp", userInfo + "");
                    function.onCallBack(userInfo);

                } catch (Exception e) {

                }
            }
        });


        testBridgeWebView.registerHandler("ServiceIntroduction", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d(TAG, data);
                try {
                    ServiceIndrocutionEntity entity = mGson.fromJson(data, ServiceIndrocutionEntity.class);
                    Intent intent = new Intent(WebViewActivity.this, ServiceDetailActivity.class);
                    intent.putExtra("title", entity.getTitle());
                    intent.putExtra("LinkUrl", entity.getLinkUrl());
                    startActivity(intent);
                } catch (Exception e) {
                    toast("数据解析失败，无法查看详情");
                }

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
    }

    public void sendNative() {
        String userInfo = "";
        if (type == 9 || type == 10) {
            setFillWindow(getResources().getColor(R.color.theme), this);
            String logininfo = SpUtils.getString(Contants.LoginJson);
            WebShopUseinfoEntity shopUseinfoEntity = new WebShopUseinfoEntity();
            ShopLoginEntity shopLoginEntity = mGson.fromJson(logininfo, ShopLoginEntity.class);
            PatientEntity patientEntity = new PatientEntity();
            patientEntity.setPatientId(SpUtils.getInt(Contants.patientID, 0));
            patientEntity.setPatientName(SpUtils.getString(Contants.patientName));
            shopUseinfoEntity.setShopLoginEntity(shopLoginEntity);
            shopUseinfoEntity.setPatientEntity(patientEntity);
            userInfo = mGson.toJson(shopUseinfoEntity);
            testBridgeWebView.callHandler("UserInfoAndroid",userInfo , new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                    Log.i("zdp", data);

                    // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ data, Toast.LENGTH_SHORT).show();
                }

            });

        } else if (type==13){
            testBridgeWebView.callHandler("UserInfo", mUseinfo, new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                    Log.i("zdp", data);

                    // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ data, Toast.LENGTH_SHORT).show();
                }

            });

        }else {
            userInfo = SpUtils.getString(Contants.LoginJson);
            testBridgeWebView.callHandler("UserInfo", userInfo, new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                    Log.i("zdp", data);

                    // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ data, Toast.LENGTH_SHORT).show();
                }

            });
        }

        Log.i("DoctorPraiseActivity", "userInfo=" + userInfo);


        testBridgeWebView.callHandler("ProfessionalLevel", MyApplication.mDoctorInfoByIdEntity.getProfessionalLevel() + "", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.i("zdp", data);

                // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ data, Toast.LENGTH_SHORT).show();
            }

        });

        testBridgeWebView.callHandler("Token", MyApplication.token, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.i("zdp", data);
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
        if (testBridgeWebView != null) {
            ViewParent viewParent = testBridgeWebView.getParent();
            if (viewParent != null) {
                ((ViewGroup) viewParent).removeView(testBridgeWebView);
            }

            testBridgeWebView.stopLoading();
            testBridgeWebView.getSettings().setJavaScriptEnabled(false);
            testBridgeWebView.clearHistory();
            testBridgeWebView.clearView();
            testBridgeWebView.removeAllViews();
            try {
                testBridgeWebView.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        if (testBridgeWebView.canGoBack()) {
            testBridgeWebView.goBack(); //goBack()表示返回WebView的上一页面
        } else {
            finish();
        }
        super.onBackPressed();
    }
}


