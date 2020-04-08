package com.newdjk.doctor.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.google.gson.reflect.TypeToken;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.IM.ChatActivity;
import com.newdjk.doctor.ui.entity.ConsultMessageEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.H5PictureMessageEntity;
import com.newdjk.doctor.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.doctor.ui.entity.IsShowIncome;
import com.newdjk.doctor.ui.entity.OnlineRenewalDataEntity;
import com.newdjk.doctor.ui.entity.PatientInfoEntity;
import com.newdjk.doctor.ui.entity.PatientStatusEntity;
import com.newdjk.doctor.ui.entity.PictureMessageEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.ui.entity.RemarkAndDrKeyEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.ServiceIndrocutionEntity;
import com.newdjk.doctor.ui.entity.UpdatePatientViewEntity;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.ui.entity.ZhuanhuiSuccess;
import com.newdjk.doctor.utils.ChatActivityUtils;
import com.newdjk.doctor.utils.ImageBase64;
import com.newdjk.doctor.utils.LogOutUtil;
import com.newdjk.doctor.utils.PDFviewUtils;
import com.newdjk.doctor.utils.SQLiteUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.SelectedPictureDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArchivesActivity extends BasicActivity {
    private static final String TAG = "ArchivesActivity";
    @BindView(R.id.test_bridge_webView)
    BridgeWebView testBridgeWebView;
    @BindView(R.id.view_cover)
    View viewCover;
    private Gson mGson;
    private SelectedPictureDialog mSelectedPictureDialog;
    private CallBackFunction mFunction;
    private PictureMessageEntity mPictureList;
    private String mAction;
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
    private String mLinkUrl;

    @Override
    protected int initViewResId() {
        return R.layout.my_pharmacy_webview;
    }

    @Override
    protected void initView() {
        mAction = getIntent().getStringExtra("action");
        mLinkUrl = getIntent().getStringExtra("LinkUrl");
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
        if (mAction != null && mAction.equals("income")) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/Center/income");
            setStatusBarColor(this, R.color.colorPrimary);
        } else if (mAction != null && mAction.equals("check")) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/inspect/checklist");
        } else if (mAction != null && mAction.equals("chat")){
            if (!TextUtils.isEmpty(mLinkUrl)){
                if (mLinkUrl.startsWith("http")){
                    testBridgeWebView.loadUrl(mLinkUrl);
                }else {
                    testBridgeWebView.loadUrl("file:///android_asset/index.html#"+mLinkUrl);
                }
            }
        }else {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/archives");
        }
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
                    mSelectedPictureDialog = new SelectedPictureDialog(ArchivesActivity.this, "first");
                    mSelectedPictureDialog.show();
                }
            }
        });
        testBridgeWebView.registerHandler("BackToIM", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                finish();
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
                    Intent intent = new Intent(ArchivesActivity.this, PictureViewerActivity.class);
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
        testBridgeWebView.registerHandler("saveSucess", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d(TAG, "保存数据成功");
                EventBus.getDefault().post(new UpdatePatientViewEntity(true));
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
                try {
                    if (mAction != null && mAction.equals("patientList")) {
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

                    } else {
                        finish();
                    }
                } catch (Exception e) {

                }

            }


        });
        testBridgeWebView.registerHandler("tokenInvalid", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                LogOutUtil.getInstance().loginOut(ArchivesActivity.this, true);
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
                    String userInfo = getIntent().getStringExtra("prescriptionMessage");
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
                    Intent intent = new Intent(ArchivesActivity.this, ServiceDetailActivity.class);
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
        String userInfo = getIntent().getStringExtra("prescriptionMessage");
        Log.i("zdp", userInfo + "");

        testBridgeWebView.callHandler("UserInfo", userInfo, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.i("zdp", data);
            }

        });

        testBridgeWebView.callHandler("Token", MyApplication.token, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.i("zdp", data);
            }

        });

        if (MyApplication.mDoctorInfoByIdEntity != null) {
            testBridgeWebView.callHandler("ProfessionalLevel", MyApplication.mDoctorInfoByIdEntity.getProfessionalLevel() + "", new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                    Log.i("zdp", data);

                    // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ data, Toast.LENGTH_SHORT).show();
                }

            });
        }

        //获取是是否显示收入状态
        int isShowIncome = SpUtils.getInt(Contants.isShowIncome, 0);
        IsShowIncome isShowIncome1 = new IsShowIncome(isShowIncome);
        mGson = new Gson();
        String strUser1 = mGson.toJson(isShowIncome1);//将bean数据转换成json数据
        testBridgeWebView.callHandler("GetHideIncomeState", strUser1, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.d(TAG, "获取数据成功" + data);
            }

        });
        testBridgeWebView.registerHandler("ServiceIntroduction", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d(TAG, data);
                try {
                    ServiceIndrocutionEntity entity = mGson.fromJson(data, ServiceIndrocutionEntity.class);

                    Intent intent = new Intent(ArchivesActivity.this, ServiceDetailActivity.class);
                    intent.putExtra("title", entity.getTitle());
                    intent.putExtra("LinkUrl", entity.getLinkUrl());
                    startActivity(intent);
                } catch (Exception e) {

                }

            }
        });
    }

    @Override
    protected void onDestroy() {
       // EventBus.getDefault().post(new UpdatePatientViewEntity(true));
        super.onDestroy();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                    }

                    @Override
                    public void onFailures(int statusCode, String errorMsg) {
                        CommonMethod.requestError(statusCode, errorMsg);
                        loading(false);
                    }
                });
            }
        }.execute(path);

    }

    private void QueryConsultDoctorAppMessageByPage(String id) {
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
                    prescriptionMessageEntity.setPatient(consultMessageEntity);
                    String json = mGson.toJson(prescriptionMessageEntity);

                    SpUtils.put(Contants.patientName, consultMessageEntity.getPatientName());
                    SpUtils.put(Contants.patientID, consultMessageEntity.getPatientId());
                    Intent consultIntentTalk = new Intent(mActivity, ChatActivity.class);
                    Log.i("zdp", "json=" + json);
                    consultIntentTalk.putExtra(Contants.FRIEND_NAME, consultMessageEntity.getPatientName());
                    consultIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, consultMessageEntity.getApplicantIMId());
                    consultIntentTalk.putExtra("consultMessageEntity", consultMessageEntity);
                    consultIntentTalk.putExtra("action", "pictureConsult");
                    consultIntentTalk.putExtra("prescriptionMessage", json);
                    mActivity.startActivity(consultIntentTalk);

                    //  Log.i("HomeFragment","entity="+entity.getData().toString());
                } else {
                    Toast.makeText(mActivity, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void QueryRenewalDoctorAppMessageByPage(String id) {
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
                    Type renewalJsonType = new TypeToken<PrescriptionMessageEntity<OnlineRenewalDataEntity>>() {
                    }.getType();
                    //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                    PrescriptionMessageEntity<OnlineRenewalDataEntity> renewalMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), renewalJsonType);
                    renewalMessageEntity.setPatient(onlineRenewalDataEntity);
                    String renewalJson = mGson.toJson(renewalMessageEntity);

                    Intent renewalIntentTalk = new Intent(mActivity, ChatActivity.class);
                    renewalIntentTalk.putExtra(Contants.FRIEND_NAME, onlineRenewalDataEntity.getPatientName());
                    SpUtils.put(Contants.patientName, onlineRenewalDataEntity.getPatientName());
                    SpUtils.put(Contants.patientID, onlineRenewalDataEntity.getPatientId());
                    renewalIntentTalk.putExtra("onlineRenewalDataEntity", onlineRenewalDataEntity);
                    renewalIntentTalk.putExtra("action", "onlineRenewal");
                    renewalIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, onlineRenewalDataEntity.getApplicantIMId());
                    renewalIntentTalk.putExtra("prescriptionMessage", renewalJson);
                    mActivity.startActivity(renewalIntentTalk);


                    //  Log.i("HomeFragment","entity="+entity.getData().toString());
                } else {
                    Toast.makeText(mActivity, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void QueryvideoDoctorAppMessageByPage(String id) {
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
                    Type videoJsonType = new TypeToken<PrescriptionMessageEntity<InquiryRecordListDataEntity>>() {
                    }.getType();
                    //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                    PrescriptionMessageEntity<InquiryRecordListDataEntity> videoMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), videoJsonType);
                    videoMessageEntity.setPatient(inquiryRecordListDataEntity);
                    String videoJson = mGson.toJson(videoMessageEntity);
                    Intent videoIntentTalk = new Intent(mActivity, ChatActivity.class);

                    SpUtils.put(Contants.patientName, inquiryRecordListDataEntity.getPatientName());
                    SpUtils.put(Contants.patientID, inquiryRecordListDataEntity.getPatientId());
                    videoIntentTalk.putExtra(Contants.FRIEND_NAME, inquiryRecordListDataEntity.getPatientName());
                    videoIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, inquiryRecordListDataEntity.getApplicantIMId());
                    videoIntentTalk.putExtra("inquiryRecordListDataEntity", inquiryRecordListDataEntity);
                    videoIntentTalk.putExtra("action", "videoInterrogation");
                    videoIntentTalk.putExtra("prescriptionMessage", videoJson);
                    mActivity.startActivity(videoIntentTalk);

                    //  Log.i("HomeFragment","entity="+entity.getData().toString());
                } else {
                    Toast.makeText(mActivity, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }
}
