package com.newdjk.doctor.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
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

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.lxq.okhttp.utils.ReceivedCookiesInterceptor;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.iInterface.RefuseDialogClickLister;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.PictureMessageEntity;
import com.newdjk.doctor.ui.entity.PrescriptionAdviceEntity;
import com.newdjk.doctor.ui.entity.PrescriptionDescEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.SignFinshEntity;
import com.newdjk.doctor.ui.entity.SignReportSuccess;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.ui.entity.YWXListenerEntity;
import com.newdjk.doctor.utils.CertUtis;
import com.newdjk.doctor.utils.ImageBase64;
import com.newdjk.doctor.utils.LogOutUtil;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.utils.TokenInterceptor;
import com.newdjk.doctor.views.RefuseDialog;
import com.newdjk.doctor.views.RememberPasswordDialog;
import com.newdjk.doctor.views.SelectedPictureDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.org.bjca.sdk.core.kit.BJCASDK;
import cn.org.bjca.sdk.core.kit.YWXListener;
import cn.org.bjca.sdk.core.values.ConstantParams;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui
 *  @文件名:   MyCheckCenterActivity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/6 10:12
 *  @描述：
 */
public class PrescriptionCheckActivity extends BasicActivity {

    @BindView(R.id.test_bridge_webView)
    BridgeWebView testBridgeWebView;
    @BindView(R.id.radio)
    LinearLayout radio;
    @BindView(R.id.radio_refuse)
    Button radioRefuse;
    @BindView(R.id.radio_pass)
    Button radioPass;
    private String mPrescriptionMessage;
    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调
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
    private String TAG = "PrescriptionCheckActivity";
    private PrescriptionAdviceEntity prescription;
    private List<String> adviceList = new ArrayList<>();
    private List<PrescriptionAdviceEntity.DataBean> adviceListbean = new ArrayList<>();
    private boolean isPassSelect = true;
    private int from;
    private int prescriptionType;

    @Override
    protected int initViewResId() {
        return R.layout.my_prescription;
    }

    @Override
    protected void initView() {
        mGson = new Gson();

        //处方类型  1西药处方  2中药处方
        prescriptionType = getIntent().getIntExtra("prescriptionType", 1);
        if (prescriptionType == 1) {
            requestPost(37);
        } else {
            requestPost(57);
        }

        mPrescriptionMessage = getIntent().getStringExtra("prescriptionMessage");
        int type = getIntent().getIntExtra("type", 0);
        if (type != 0) {
            radio.setVisibility(View.GONE);
        }
        mId = getIntent().getStringExtra("action");
        mPrescriptionId = getIntent().getStringExtra("id");
        mRejectId = getIntent().getStringExtra("rejectId");
        from = getIntent().getIntExtra("from", 0);


        Log.i("PrescriptionActivity", "data" + mPrescriptionMessage + "  " + mId + "  " + mPrescriptionId + "  " + mRejectId);
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
        testBridgeWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        testBridgeWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        testBridgeWebView.setBackgroundColor(0); // 设置背景色
        testBridgeWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        testBridgeWebView.setWebViewClient(new BridgeWebViewClient(testBridgeWebView) {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                testBridgeWebView.setVisibility(View.VISIBLE);
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
        if (prescriptionType==2){
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/chineseMed/prescription?id=" + mId + "&drtype=3");

        }else {
            if (mId != null && !mId.equals("")) {
                testBridgeWebView.loadUrl("file:///android_asset/index.html#/PrescriptionLlist?id=" + mId + "&drtype=3");
            }
//        else if (mRejectId != null && !mRejectId.equals("")) {
//            testBridgeWebView.loadUrl("file:///android_asset/index.html#/PrescriptionLlist?rejectId="+mRejectId);
//        }
//        else if (mPrescriptionId != null && !mPrescriptionId.equals("")){
//            testBridgeWebView.loadUrl("file:///android_asset/index.html#/prescription?MPrescriptionId="+mPrescriptionId);
//        }
            else {
                //testBridgeWebView.loadUrl("file:///android_asset/index.html#/prescription");
                testBridgeWebView.loadUrl("file:///android_asset/index.html#/pharmacy?from=p");
            }
        }

        // Log.i("zdp","mPrescriptionMessage="+mPrescriptionMessage);
        sendNative(mPrescriptionMessage);
        testBridgeWebView.registerHandler("Back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                finish();
            }
        });
        testBridgeWebView.registerHandler("tokenInvalid", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                LogOutUtil.getInstance().loginOut(PrescriptionCheckActivity.this, true);
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
        testBridgeWebView.registerHandler("signId", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
               /* Intent intent = new Intent();
                intent.putExtra("uniqueId", data);
                setResult(RESULT_OK, intent);
                finish();*/
                mFunction = function;
                Log.i("PrescriptionActivity", "data=" + data);
                List<String> uniqueIds = new ArrayList<>();
                uniqueIds.add(data);
                boolean isPinExempt = BJCASDK.getInstance().isPinExempt(PrescriptionCheckActivity.this);
                showRememberDialog(PrescriptionCheckActivity.this, uniqueIds, isPinExempt);

            }
        });
        testBridgeWebView.registerHandler("Preview", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                mPictureList = mGson.fromJson(data, PictureMessageEntity.class);
                Intent intent = new Intent(PrescriptionCheckActivity.this, PictureViewerActivity.class);
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
                    mSelectedPictureDialog = new SelectedPictureDialog(PrescriptionCheckActivity.this, "first");
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
        if (from == 2) {
            radio.setVisibility(View.GONE);
            getPrescriptionData();
        }else if (from == 3){
            radio.setVisibility(View.GONE);
            getTCMPrescriptionData();
        }


    }

    public void showRememberDialog(final Activity context, final List<String> uniqueIds, boolean isPinExempt) {
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
                                    BJCASDK.getInstance().sign(PrescriptionCheckActivity.this, Contants.clientId, uniqueIds, new YWXListener() {
                                        @Override
                                        public void callback(String result) {
                                            if (result != null) {
                                                SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                                                String status = signFinshEntity.getStatus();
                                                if (status.equals("0")) {
                                                    toast("审核通过");
                                                    EventBus.getDefault().post(new SignReportSuccess(true));
                                                    EventBus.getDefault().post(new UpdatePushView(4));
                                                    finish();
                                                } else {
                                                    //toast(signFinshEntity.getMessage());
                                                    if (status.toLowerCase().contains("004x030")){
                                                        CertUtis.showCertUpdateDialog(PrescriptionCheckActivity.this);
                                                    }else {
                                                        ToastUtil.setToast("电子签名失败，请核对签名密码是否正确和网络是否正常！+(" + status + ")");
                                                    }                                                }
                                            }
                                        }
                                    });
                                } else {
                                    //  Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    ToastUtil.setToast("记住密码失效，请重试！+(" + status + ")");


                                }

                            }
                        });
                    } else {
                        BJCASDK.getInstance().sign(PrescriptionCheckActivity.this, Contants.clientId, uniqueIds, new YWXListener() {
                            @Override
                            public void callback(String result) {
                                if (result != null) {
                                    SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                                    String status = signFinshEntity.getStatus();
                                    Log.d("chat","签名回调");
                                    if (status.equals("0")) {
                                        toast("审核通过");
                                        EventBus.getDefault().post(new SignReportSuccess(true));
                                        EventBus.getDefault().post(new UpdatePushView(4));
                                        finish();
                                    } else {
                                        // toast(signFinshEntity.getMessage());
                                        if (status.toLowerCase().contains("004x030")){
                                            CertUtis.showCertUpdateDialog(PrescriptionCheckActivity.this);
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
            BJCASDK.getInstance().sign(PrescriptionCheckActivity.this, Contants.clientId, uniqueIds, new YWXListener() {
                @Override
                public void callback(String result) {
                    if (result != null) {
                        SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                        String status = signFinshEntity.getStatus();
                        if (status.equals("0")) {
                            toast("审核通过");
                            EventBus.getDefault().post(new SignReportSuccess(true));
                            EventBus.getDefault().post(new UpdatePushView(4));
                            finish();
                        } else {
                            // toast(signFinshEntity.getMessage());
                            if (status.toLowerCase().contains("004x030")){
                                CertUtis.showCertUpdateDialog(PrescriptionCheckActivity.this);
                            }else {
                                ToastUtil.setToast("电子签名失败，请核对签名密码是否正确和网络是否正常！+(" + status + ")");
                            }

                        }
                    }
                }
            });
        }
    }

    /*  public  void showRememberDialog (final Activity context) {
          RememberPasswordDialog dialog = new RememberPasswordDialog(context);
          dialog.show("", "", new RememberPasswordDialog.DialogListener() {
              @Override
              public void cancel() {

              }

              @Override
              public void confirm(int keeDay) {
                  BJCASDK.getInstance().keepPin(context,Contants.clientId,keeDay,	new	YWXListener()	{
                      @Override
                      public	void	callback(String	msg)	{

                          YWXListenerEntity yWXListenerEntity = new Gson().fromJson(msg, YWXListenerEntity.class);
                          String status = yWXListenerEntity.getStatus();
                          String message = yWXListenerEntity.getMessage();
                          if (status.equals("0")) {
                              Toast.makeText(context,"设置免密成功",Toast.LENGTH_SHORT).show();
                          }
                          else {
                              Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                          }
                          BJCASDK.getInstance().sign(PrescriptionCheckActivity.this,	Contants.clientId,	uniqueIds,	new	YWXListener()	{
                              @Override
                              public	void callback(String	result)	{
                                  if (result != null) {
                                      SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                                      String status = signFinshEntity.getStatus();
                                      if (status.equals("0")) {
                                          toast("审核通过");
                                          EventBus.getDefault().post(new SignReportSuccess(true));
                                          EventBus.getDefault().post(new UpdatePushView(4));
                                          finish();
                                      }
                                      else {
                                          toast(signFinshEntity.getMessage());
                                      }
                                  }
                              }
                          });
                      }
                  });
              }
          });
      }*/
    private void getPrescriptionData() {

        loading(true);
        Map<String, String> map = new HashMap<>();
        map.put("Id", mId);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.getetPrescriptionDta).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<PrescriptionDescEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<PrescriptionDescEntity> entity) {
                Log.d(TAG, entity.toString());
                if (entity.getCode() == 0) {
                    PrescriptionDescEntity descEntity = entity.getData();
                    if (descEntity != null) {
                        int type = descEntity.getStatus();
                        if (type == 0) {
                            radio.setVisibility(View.VISIBLE);

                        } else {
                            radio.setVisibility(View.GONE);
                        }
                    }

                    loading(false);
                } else {
                    loading(false);
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                loading(false);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });

    }
    private void getTCMPrescriptionData() {

        loading(true);
        Map<String, String> map = new HashMap<>();
        map.put("Id", mId);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.getetTCMPrescriptionDta).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<PrescriptionDescEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<PrescriptionDescEntity> entity) {
                Log.d(TAG, entity.toString());
                if (entity.getCode() == 0) {
                    PrescriptionDescEntity descEntity = entity.getData();
                    if (descEntity != null) {
                        int type = descEntity.getStatus();
                        if (type == 0) {
                            radio.setVisibility(View.VISIBLE);

                        } else {
                            radio.setVisibility(View.GONE);
                        }
                    }

                    loading(false);
                } else {
                    loading(false);
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                loading(false);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });

    }
    @Override
    protected void initListener() {
        radioRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPassSelect = false;
//                radioPass.setBackgroundResource(R.drawable.bg_radio_unchecked);
//                radioRefuse.setBackgroundResource(R.drawable.bg_radiao_checked);
//                radioPass.setTextColor(getResources().getColor(R.color.text_color_normal));
//                radioRefuse.setTextColor(getResources().getColor(R.color.white));
                alertPassDialog();
            }
        });


        radioPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isPassSelect = true;
//                radioPass.setBackgroundResource(R.drawable.bg_radiao_checked);
//                radioRefuse.setBackgroundResource(R.drawable.bg_radio_unchecked);
//                radioPass.setTextColor(getResources().getColor(R.color.white));
//                radioRefuse.setTextColor(getResources().getColor(R.color.text_color_normal));
                refuseReport("1", 0, "");

            }
        });


    }

    private void alertPassDialog() {
        RefuseDialog.Builder builder = new RefuseDialog.Builder(this, adviceList);
        RefuseDialog mDialog = builder.setMessage("wwwww")
                .setSingleButton("ahsa", new RefuseDialogClickLister() {
                    @Override
                    public void onPositiveButtonClick(View view, int seleadvice, String adviece) {

                        refuseReport("2", seleadvice, adviece);

                    }

                    @Override
                    public void onNegativeButtonClick(View view) {

                    }
                })
                .createSingleButtonDialog();
        mDialog.show();

        Window dialogWindow = mDialog.getWindow();
        WindowManager m = PrescriptionCheckActivity.this.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.8); // 高度设置为屏幕的0.6，根据实际情况调整
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65，根据实际情况调整
        dialogWindow.setAttributes(p);

    }

    private void refuseReport(final String type, int seleadviceid, String adviece) {
        loading(true);
        Map<String, String> map = new HashMap<>();
        map.put("PrescriptionId", mId);
        map.put("Status", type);//审核结果（1：通过，2：驳回）(必填)
        map.put("AuditorId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("AuditorName", SpUtils.getString(Contants.Name));
        map.put("ClientType", "2");//1.web 2.app
        map.put("AuditRemark", adviece + "");
        map.put("PrescriptionType", prescriptionType + "");
        if (adviceListbean.size() > 0) {
            map.put("RejectReasonId", adviceListbean.get(seleadviceid).getCategoryItemValue() + "");
        }
        //  map.put("DoctorName", SpUtils.getString(Contants.Name));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.changeDealStatus).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity entity) {
                Log.d(TAG, entity.toString());
                if (entity.getCode() == 0) {
                    loading(false);
                    if (type.equals("1")) {
                        Log.d(TAG, "data=" + entity.getData());
                        String data = "";
                        if (entity.getData() != null) {
                            data = entity.getData().toString();
                        }
                        List<String> uniqueIds = new ArrayList<>();
                        uniqueIds.add(data);
                        boolean isPinExempt = BJCASDK.getInstance().isPinExempt(PrescriptionCheckActivity.this);
                        showRememberDialog(PrescriptionCheckActivity.this, uniqueIds, isPinExempt);
                    } else if (type.equals("2")) {
                        EventBus.getDefault().post(new SignReportSuccess(false));
                        EventBus.getDefault().post(new UpdatePushView(4));
                        toast("驳回成功");
                        finish();

                    }
                } else {
                    loading(false);
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                loading(false);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });

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


    public void sendNative(String prescriptionMessage) {
        testBridgeWebView.callHandler("UserInfo", prescriptionMessage, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

                // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ data, Toast.LENGTH_SHORT).show();
            }

        });

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

                    String result = null;
                    try {
                        result = data.getStringExtra(ConstantParams.KEY_SIGN_BACK);
                        if (result != null) {
                            SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                            String status = signFinshEntity.getStatus();
                            if (status.equals("0")) {
                                toast("审核通过");
                                refuseReport("3", 0, "");
                                EventBus.getDefault().post(new SignReportSuccess(true));
                                EventBus.getDefault().post(new UpdatePushView(4));
                                finish();
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

                    }

                    @Override
                    public void onFailures(int statusCode, String errorMsg) {
                        CommonMethod.requestError(statusCode, errorMsg);
                    }
                });
            }
        }.execute(path);

    }

    private void requestPost(int categoryId) {
        String url = HttpUrl.QueryItemByCategoryId + "?CategoryId="+categoryId;
        Log.d("zdp", "url=" + url);
        //自定义OkHttp
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new ReceivedCookiesInterceptor())//你定义的cookie接收监听器
                .addInterceptor(new TokenInterceptor())
                .addNetworkInterceptor(new StethoInterceptor()) //添加抓包工具
                .build();
        //构建FormBody，传入要提交的参数

        final Request request = new Request.Builder()
                .url(url).addHeader("Authorization", SpUtils.getString(Contants.Token)+"")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                Log.i(TAG, "responseStr=" + responseStr);
                prescription = mGson.fromJson(responseStr, PrescriptionAdviceEntity.class);
                adviceList.clear();
                adviceListbean.clear();
                adviceListbean.addAll(prescription.getData());
                if (prescription.getData() != null) {
                    for (int i = 0; i < prescription.getData().size(); i++) {
                        adviceList.add(prescription.getData().get(i).getCategoryItemName());
                    }

                }
            }
        });

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "上滑或者下滑");
        return true;
    }
}
