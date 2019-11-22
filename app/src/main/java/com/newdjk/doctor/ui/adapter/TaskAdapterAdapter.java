package com.newdjk.doctor.ui.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxq.okhttp.MyOkHttp;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.tools.MainConstant;
import com.newdjk.doctor.ui.MianzhenDetailActivity;
import com.newdjk.doctor.ui.activity.IM.ChatActivity;
import com.newdjk.doctor.ui.activity.IM.GroupChatActivity;
import com.newdjk.doctor.ui.activity.SecondDiagnosisActivity;
import com.newdjk.doctor.ui.entity.ConsultMessageEntity;
import com.newdjk.doctor.ui.entity.DoctorMedicalRecordsEntity;
import com.newdjk.doctor.ui.entity.ImRelationRecode;
import com.newdjk.doctor.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.ui.entity.MessageEventRecent;
import com.newdjk.doctor.ui.entity.MianzhenListEntity;
import com.newdjk.doctor.ui.entity.OnlineRenewalDataEntity;
import com.newdjk.doctor.ui.entity.PatientInfoEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.ui.entity.RefeshTaskListEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.TaskEntity;
import com.newdjk.doctor.utils.HeadUitl;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.TimeUtil;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.MianzhenDialog;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskAdapterAdapter extends BaseQuickAdapter<TaskEntity.DataBean, BaseViewHolder> {

    private final MyOkHttp mMyOkhttp;
    private Gson mGson;
    private int mType;
    private MianzhenDialog mDialog;
    private int mStatus;

    public TaskAdapterAdapter(@Nullable List<TaskEntity.DataBean> data, int type) {
        super(R.layout.task_item, data);
        mType = type;
        mGson = new Gson();
        mMyOkhttp = MyApplication.getInstance().getMyOkHttp();

    }

    @Override
    protected void convert(BaseViewHolder helper, final TaskEntity.DataBean item) {
        RecyclerView recyclerView = helper.itemView.findViewById(R.id.recyleviewitem);
        List<TaskEntity.DataBean.DoctorOrderMergesBean> datalist = item.getDoctorOrderMerges();
        final TaskItemChildAdapter mMianzhenAdapterr = new TaskItemChildAdapter(datalist);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mMianzhenAdapterr);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false));

        if (item.isOpen()) {
            helper.setImageResource(R.id.im_arrow, R.mipmap.decline);
            recyclerView.setVisibility(View.GONE);
        } else {
            helper.setImageResource(R.id.im_arrow, R.mipmap.rise);
            recyclerView.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(item.getServiceTime())) {
            helper.setText(R.id.tv_time, item.getServiceTime().substring(0, 10) + " " + item.getWeek());
        } else {
            helper.setText(R.id.tv_time, "");
        }
        helper.setText(R.id.tv_number, item.getNum() + "");
        try {
            if (TimeUtil.IsToday(item.getServiceTime())) {
                helper.setText(R.id.tv_day, "今日任务");
                helper.setText(R.id.tv_jiezhen_type, "待接诊");
            } else {
                helper.setText(R.id.tv_day, item.getServiceTime().substring(0, 10));
                helper.setText(R.id.tv_jiezhen_type, "预约接诊");
            }

        } catch (Exception e) {

        }
        mMianzhenAdapterr.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_cancel) {
                    showdialog(item.getDoctorOrderMerges(), position, mMianzhenAdapterr);
                }
            }
        });

        mMianzhenAdapterr.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                gotoIM(position, item);
            }
        });
        helper.addOnClickListener(R.id.im_arrow);
    }

    private void gotoIM(int position, TaskEntity.DataBean item) {
        //需要判读是否可以取消预约
        //业务类型(1-图文问诊，2-视频问诊，3-名医续方，4-处方费，5-护理咨询，6-远程护理，7-上门服务，
        // 8-耗材，9-服务包，11-第二诊疗，12-检验检查，13-预约金，14-预约诊金，15-远程门诊，
        // 16-专科转诊，17-中药处方，18-优选推荐，19-面诊预约，20-拼团购买，21-母胎解读（21-39为设备解读），
        // 50-MDT咨询，51-MDT分诊，52-MDT会诊，99-其他
        TaskEntity.DataBean.DoctorOrderMergesBean data = item.getDoctorOrderMerges().get(position);

        int type = data.getServiceType();

        //1 图文 2 视频 3 续方  5 护理咨询  6远程护理
        if (type == 1||type == 5||type == 15) {
            QueryConsultDoctorAppMessageByPage(data.getRelationId()+"", data.getAccountId(), data.getPatientName());
        } else if (type == 2||type == 6) {
            QueryvideoDoctorAppMessageByPage(data.getRelationId()+"", data.getAccountId(), data.getPatientName());

        } else if (type == 3) {
            QueryRenewalDoctorAppMessageByPage(data.getRelationId()+"", data.getAccountId(), data.getPatientName());

        } else if (type == 50) {
            //跳转咨询im
            getIMRelationRecord(item.getDoctorOrderMerges().get(position).getIMGroupId());

        } else if (type == 51 || type == 52|| type == 16) {
            //跳转咨询im
            getIMRelationRecord(item.getDoctorOrderMerges().get(position).getIMGroupId());


        } else if (type == 19) { //面诊预约

            MianzhenListEntity.ReturnDataBean.FaceConsultationRecordsBean dataBean = mGson.fromJson(item.getDoctorOrderMerges().get(position).getExtendJson(), MianzhenListEntity.ReturnDataBean.FaceConsultationRecordsBean.class);
            Intent adviceintent = new Intent(mContext, MianzhenDetailActivity.class);
            adviceintent.putExtra("id", dataBean.getRecordId() + "");
            mContext.startActivity(adviceintent);

        } else if (type == 11) {//11-第二诊疗
//            Intent treatIntent = new Intent(mContext, TreatActivity.class);
//            mContext.startActivity(treatIntent);
            DoctorMedicalRecordsEntity.ReturnDataBean dataBean = mGson.fromJson(item.getDoctorOrderMerges().get(position).getExtendJson(), DoctorMedicalRecordsEntity.ReturnDataBean.class);
            Intent SecondDiagnosisIntent = new Intent(mContext, SecondDiagnosisActivity.class);
            SecondDiagnosisIntent.putExtra("MedicalRecordId", dataBean.getMedicalRecordId());
            mContext.startActivity(SecondDiagnosisIntent);
        } else {

        }

    }


    public void CancelFaceConsultation(final List<TaskEntity.DataBean.DoctorOrderMergesBean> list, final int positionout, final TaskItemChildAdapter adapter) {
        MianzhenListEntity.ReturnDataBean.FaceConsultationRecordsBean dataBean = mGson.fromJson(list.get(positionout).getExtendJson(), MianzhenListEntity.ReturnDataBean.FaceConsultationRecordsBean.class);
        Map<String, String> params = new HashMap<>();
        params.put("RecordId", dataBean.getRecordId() + "");
        params.put("PatientIdOrDrId", MyApplication.mDoctorInfoByIdEntity.getDrId() + "");//就诊人id/医生id。如果是会员取消传就诊人id，如果是医生取消传医生id
        params.put("CancelType", "2");//取消途径。1-会员主动取消；2-医生取消
        params.put("CancelReason", "");
        //  params.put("DrId", "68");
        // String json = mGson.toJson(params);

        mMyOkhttp.post().url(HttpUrl.CancelFaceConsultation).params(params).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity entity) {
                if (entity.getCode() == 0) {
//                    list.remove(positionout);
//                    adapter.setNewData(list);

                    EventBus.getDefault().post(new RefeshTaskListEntity(true));

                } else {
                    // toast(entity.getMessage());
                    ToastUtil.setToast(entity.getMessage());

                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
//                if (easylayout == null) {
//                    return;
//                }
//                if (easylayout.isRefreshing()) {
//                    easylayout.refreshComplete();
//                }
//                Log.d("errMsg", errorMsg);
                ToastUtil.setToast(errorMsg);
            }
        });

    }


    private void showdialog(final List<TaskEntity.DataBean.DoctorOrderMergesBean> list, final int positionout, final TaskItemChildAdapter adapter) {

        if (mDialog == null) {
            mDialog = new MianzhenDialog(mContext);
        }
        mDialog.show("", "", new MianzhenDialog.DialogListener() {
            @Override
            public void cancel() {
                mDialog = null;
            }

            @Override
            public void confirm() {
                mDialog = null;
                CancelFaceConsultation(list, positionout, adapter);
            }
        });

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
