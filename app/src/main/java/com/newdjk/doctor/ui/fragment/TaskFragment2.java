package com.newdjk.doctor.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.tools.MainConstant;
import com.newdjk.doctor.ui.MianzhenDetailActivity;
import com.newdjk.doctor.ui.activity.IM.ChatActivity;
import com.newdjk.doctor.ui.activity.IM.GroupChatActivity;
import com.newdjk.doctor.ui.activity.SecondDiagnosisQuestionActivity;
import com.newdjk.doctor.ui.adapter.TaskfragmentAdapter;
import com.newdjk.doctor.ui.entity.ConsultMessageEntity;
import com.newdjk.doctor.ui.entity.DoctorMedicalRecordsEntity;
import com.newdjk.doctor.ui.entity.ImRelationRecode;
import com.newdjk.doctor.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.ui.entity.MessageEventRecent;
import com.newdjk.doctor.ui.entity.MianzhenListEntity;
import com.newdjk.doctor.ui.entity.MianzhenSuccessEntity;
import com.newdjk.doctor.ui.entity.OnlineRenewalDataEntity;
import com.newdjk.doctor.ui.entity.PatientInfoEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.ui.entity.RefeshTaskListEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.TaskAllEntity;
import com.newdjk.doctor.ui.entity.UpdateImMessageEntity;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.utils.AppUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class TaskFragment2 extends BasicFragment {


    private static Gson mGson;
    @BindView(R.id.iv_no)
    ImageView ivNo;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;
    @BindView(R.id.recyleview)
    RecyclerView recyleview;
    @BindView(R.id.easylayout)
    EasyRefreshLayout easylayout;
    private List<TaskAllEntity.ReturnDataBean> dataList = new ArrayList<>();
    private TaskfragmentAdapter mTaskfragmentAdapter;
    public int index = 1;
    private String TAG = "TaskFragment2";
    private int mStatus;
    private boolean isrefresh = false;
    private static final int LOADING_SUCCESS = 2;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADING_SUCCESS:
                    if (!isrefresh) {
                        index = 1;
                        easylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
                        QueryDoctorOrderMergePage();
                    }
                    break;

            }
        }
    };

    public static TaskFragment2 newInstance() {
        mGson = new Gson();
        TaskFragment2 myFragment = new TaskFragment2();
        return myFragment;
    }

    @Override
    protected int initViewResId() {
        return R.layout.task_fragment;
    }


    @Override
    protected void initView() {


        mTaskfragmentAdapter = new TaskfragmentAdapter(dataList);
        recyleview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyleview.setAdapter(mTaskfragmentAdapter);
        recyleview.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));

    }

    @Override
    protected void initListener() {

        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                Log.d(TAG, "价值更多");
                index++;
                QueryDoctorOrderMergePage();
            }

            @Override
            public void onRefreshing() {
                index = 1;
                easylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
                QueryDoctorOrderMergePage();
            }
        });

        mTaskfragmentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (dataList.size() - 1 >= position) {
                    gotoIM(position, dataList.get(position));

                }
            }
        });
    }

    private void gotoIM(int position, TaskAllEntity.ReturnDataBean item) {
        //需要判读是否可以取消预约
        //业务类型(1-图文问诊，2-视频问诊，3-名医续方，4-处方费，5-护理咨询，6-远程护理，7-上门服务，
        // 8-耗材，9-服务包，11-第二诊疗，12-检验检查，13-预约金，14-预约诊金，15-远程门诊，
        // 16-专科转诊，17-中药处方，18-优选推荐，19-面诊预约，20-拼团购买，21-母胎解读（21-39为设备解读），
        // 50-MDT咨询，51-MDT分诊，52-MDT会诊，99-其他
        //需要判读是否可以取消预约
        //业务类型(1-图文问诊，2-视频问诊，3-名医续方，4-处方费，5-护理咨询，6-远程护理，7-上门服务，
        // 8-耗材，9-服务包，11-第二诊疗，12-检验检查，13-预约金，14-预约诊金，15-远程门诊，
        // 16-专科转诊，17-中药处方，18-优选推荐，19-面诊预约，20-拼团购买，21-母胎解读（21-39为设备解读），
        // 50-MDT咨询，51-MDT分诊，52-MDT会诊，99-其他

        int type = item.getServiceType();
        //1 图文 2 视频 3 续方  5 护理咨询  6远程护理
        if (type == 1 || type == 5||type == 15) {
            QueryConsultDoctorAppMessageByPage(item.getRelationId() + "", item.getAccountId(), item.getPatientName());
        } else if (type == 2 || type == 6) {
            QueryvideoDoctorAppMessageByPage(item.getRelationId() + "", item.getAccountId(), item.getPatientName());

        } else if (type == 3) {
            QueryRenewalDoctorAppMessageByPage(item.getRelationId() + "", item.getAccountId(), item.getPatientName());

        } else if (type == 50) {
            //跳转咨询im
            getIMRelationRecord(item.getIMGroupId());

        } else if (type == 51 || type == 52|| type == 16) {
            //跳转咨询im
            getIMRelationRecord(item.getIMGroupId());

        } else if (type == 19) { //面诊预约
            mStatus = SpUtils.getInt(Contants.Status, 0);
            if (mStatus != 1) {
                AppUtils.checkAuthenStatus(mStatus, mContext);
            } else {

                MianzhenListEntity.ReturnDataBean.FaceConsultationRecordsBean dataBean = mGson.fromJson(item.getExtendJson(), MianzhenListEntity.ReturnDataBean.FaceConsultationRecordsBean.class);
                Intent adviceintent = new Intent(mContext, MianzhenDetailActivity.class);
                adviceintent.putExtra("id", dataBean.getRecordId() + "");
                mContext.startActivity(adviceintent);
            }
        } else if (type == 11) {//11-第二诊疗
//            Intent treatIntent = new Intent(mContext, TreatActivity.class);
//            mContext.startActivity(treatIntent);
            DoctorMedicalRecordsEntity.ReturnDataBean dataBean = mGson.fromJson(item.getExtendJson(), DoctorMedicalRecordsEntity.ReturnDataBean.class);
            Intent SecondDiagnosisIntent = new Intent(mContext, SecondDiagnosisQuestionActivity.class);
            SecondDiagnosisIntent.putExtra("MedicalRecordId", dataBean.getMedicalRecordId());
            mContext.startActivity(SecondDiagnosisIntent);
        } else {

        }

    }

    @Override
    protected void initData() {
        QueryDoctorOrderMergePage();

    }

    private void QueryDoctorOrderMergePage() {
        if (index == 1) {
            dataList.clear();
        }
        loading(true);
        isrefresh = true;
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        params.put("ServiceStatus", "1");//业务状态(0-待问诊，1-问诊中，6-已取消) -1查询所有
        params.put("PageIndex", index + "");//业务状态(0-待问诊，1-问诊中，6-已取消) -1查询所有
        params.put("PageSize", "10");//业务状态(0-待问诊，1-问诊中，6-已取消) -1查询所有
        params.put("ServiceType", "-1");//业务类型(1-图文问诊，2-视频问诊，3-名医续方，5-护理咨询，6-远程护理，11-第二诊疗，15-远程门诊，19-面诊预约，50-MDT咨询，51-MDT分诊，52-MDT会诊) -1查询所有
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.QueryDoctorOrderMergePage).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<TaskAllEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<TaskAllEntity> entity) {
                LoadDialog.clear();
                if (easylayout == null) {
                    return;
                }
                if (easylayout.isRefreshing()) {
                    easylayout.refreshComplete();
                }

                if (entity != null) {
                    if (entity.getCode() == 0) {
                        TaskAllEntity onlineRenewalMessageEntity = entity.getData();
                        Log.d(TAG, onlineRenewalMessageEntity.toString());
                        if (onlineRenewalMessageEntity != null) {

                            List<TaskAllEntity.ReturnDataBean> list = onlineRenewalMessageEntity.getReturnData();
                            if (list == null) {
                                easylayout.setLoadMoreModel(LoadModel.NONE);
                            } else {
                                if (list.size() < 10) {
                                    easylayout.closeLoadView();
                                    easylayout.setLoadMoreModel(LoadModel.NONE);
                                    dataList.addAll(list);
                                    mTaskfragmentAdapter.setNewData(dataList);
                                } else {
                                    easylayout.closeLoadView();
                                    dataList.addAll(list);
                                    mTaskfragmentAdapter.setNewData(dataList);
                                }
                            }
                        } else {

                        }
                    } else {
                        toast(entity.getMessage());

                    }
                }

                if (index == 1) {
                    if (dataList.size() == 0) {
                        easylayout.setVisibility(View.GONE);
                        mNodataContainer.setVisibility(View.VISIBLE);
                    } else {
                        easylayout.setVisibility(View.VISIBLE);
                        mNodataContainer.setVisibility(View.GONE);
                    }

                }
                isrefresh = false;
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                if (easylayout == null) {
                    return;
                }
                LoadDialog.clear();
                if (easylayout != null) {
                    if (index > 1) {
                        easylayout.loadMoreComplete();

                    } else {
                        easylayout.refreshComplete();
                    }

                }
                if (index == 1) {
                    if (dataList.size() == 0) {
                        easylayout.setVisibility(View.GONE);
                        mNodataContainer.setVisibility(View.VISIBLE);
                    }

                }
                isrefresh = false;
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Override
    protected void otherViewClick(View view) {
//        switch (view.getId()) {
//            case R.id.click_refresh :
//                refreshLayout.setRefreshing(true);
//                if (mAction == 0) {
//                    getInquiryRecord("0");
//                } else if (mAction == 1) {
//                    getInquiryRecord("1");
//                } else if (mAction == 2) {
//                    getInquiryRecord("7");
//                }
//                break;
//        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        //  mInquiryRecordListEntity = (InquiryRecordListEntity)getArguments().getSerializable("consultMessage");
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    //刷新数据使用
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(RefeshTaskListEntity mianzhenSuccessEntity) {
        Log.d(TAG, "收到消息1");
        index = 1;
        easylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
        QueryDoctorOrderMergePage();

    }


    //    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(MessageEventRecent event) {
//        Log.d(TAG,"收到消息2");
//        index = 1;
//        easylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
//        QueryDoctorOrderMergePage();
//
//
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UpdatePushView event) {
        Log.d(TAG, "收到消息2");

        mHandler.sendEmptyMessageDelayed(LOADING_SUCCESS, 1500);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(MianzhenSuccessEntity mianzhenSuccessEntity) {
        Log.d(TAG, "收到消息3");
        index = 1;
        easylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
        QueryDoctorOrderMergePage();

    }


    //收到消息，更新首页红点显示
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateImMessageEntity userEvent) {
        Log.d(TAG, "收到消息4" + isrefresh + "---" + userEvent.serviceCode);
        if (!isrefresh) {
            index = 1;
            if (!"-1".equals(userEvent.serviceCode)) {
                easylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
                QueryDoctorOrderMergePage();
            }


        }


    }

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


    private void getIMRelationRecord(String mIMGroupId) {

        LoadDialog.show(mContext);
        Map<String, String> map = new HashMap<>();
        map.put("IMGroupId", mIMGroupId);
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
                    intent.putExtra(Contants.FRIEND_NAME, entity.getData().getPatientName());
                    intent.putExtra(Contants.FRIEND_IDENTIFIER, entity.getData().getIMGroupId());
                    mContext.startActivity(intent);

                } else {
                    Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }

                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
                LoadDialog.clear();
            }
        });
    }

}
