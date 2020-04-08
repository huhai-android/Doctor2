package com.newdjk.doctor.ui.chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.ArchivesActivity;
import com.newdjk.doctor.ui.activity.CustomLinkActivity;
import com.newdjk.doctor.ui.activity.FenjiZhuanzhenActivity;
import com.newdjk.doctor.ui.activity.IM.ChatActivity;
import com.newdjk.doctor.ui.activity.PictureViewerActivity;
import com.newdjk.doctor.ui.activity.ServiceDetailActivity;
import com.newdjk.doctor.ui.activity.ShowOriginPictureActivity;
import com.newdjk.doctor.ui.activity.WebViewActivity;
import com.newdjk.doctor.ui.entity.ConsultMessageEntity;
import com.newdjk.doctor.ui.entity.H5PictureMessageEntity;
import com.newdjk.doctor.ui.entity.IsShowIncome;
import com.newdjk.doctor.ui.entity.PatientInfoEntity;
import com.newdjk.doctor.ui.entity.PatientStatusEntity;
import com.newdjk.doctor.ui.entity.PictureMessageEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.ui.entity.RemarkAndDrKeyEntity;
import com.newdjk.doctor.ui.entity.ServiceIndrocutionEntity;
import com.newdjk.doctor.ui.entity.UpdatePatientViewEntity;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.ui.entity.ZhuanhuiSuccess;
import com.newdjk.doctor.utils.ChatActivityUtils;
import com.newdjk.doctor.utils.LogOutUtil;
import com.newdjk.doctor.utils.PDFviewUtils;
import com.newdjk.doctor.utils.SQLiteUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.SelectedPictureDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChatWebviewFragment extends BasicFragment {


    Unbinder unbinder;
    @BindView(R.id.test_bridge_webView)
    BridgeWebView testBridgeWebView;
    @BindView(R.id.view_cover)
    View viewCover;
    @BindView(R.id.btn_share)
    Button btnShare;
    private static final int LOADING_SUCCESS = 2;
    private String mAction;
    private Gson mGson;
    private SelectedPictureDialog mSelectedPictureDialog;
    private CallBackFunction mFunction;
    private PictureMessageEntity mPictureList;
    private static final String TAG = "ChatWebviewFragment";

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADING_SUCCESS:
                    loading(false);
                    if (viewCover!=null){
                        viewCover.setVisibility(View.GONE);

                        testBridgeWebView.setFocusable(true);
                        testBridgeWebView.setFocusableInTouchMode(true);
                        testBridgeWebView.requestFocus();
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private String userInfo;


    public static ChatWebviewFragment newInstance() {
        Bundle args = new Bundle();
        ChatWebviewFragment myFragment = new ChatWebviewFragment();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    protected int initViewResId() {
        return R.layout.newchatfragment1;
    }


    //收到刷新界面通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdatePatientViewEntity userEvent) {
        testBridgeWebView.callHandler("freshView", userInfo, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.i("zdp", data);
            }

        });
    }

    @Override
    protected void initView() {
        mAction = getActivity().getIntent().getStringExtra("action");
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

        testBridgeWebView.loadUrl("file:///android_asset/index.html#/archives?isImEntry=1");

        testBridgeWebView.registerHandler("Back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                getActivity().finish();
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


                Intent intent = new Intent(getContext(), ArchivesActivity.class);

                intent.putExtra("action", "chat");
                intent.putExtra("LinkUrl", data);
                intent.putExtra("prescriptionMessage",userInfo+"");
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
                LogOutUtil.getInstance().loginOut((BasicActivity) getActivity(), true);
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
                    String userInfo = getActivity().getIntent().getStringExtra("prescriptionMessage");
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
                    Intent intent = new Intent(getContext(), ServiceDetailActivity.class);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //  mInquiryRecordListEntity = (InquiryRecordListEntity)getArguments().getSerializable("consultMessage");
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void sendNative() {
        userInfo = getActivity().getIntent().getStringExtra("prescriptionMessage");

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

                    Intent intent = new Intent(getContext(), ServiceDetailActivity.class);
                    intent.putExtra("title", entity.getTitle());
                    intent.putExtra("LinkUrl", entity.getLinkUrl());
                    intent.putExtra("prescriptionMessage", userInfo+"");
                    startActivity(intent);
                } catch (Exception e) {

                }

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

        testBridgeWebView.setFocusable(true);

        testBridgeWebView.setFocusableInTouchMode(true);
        testBridgeWebView.requestFocus();
    }


}