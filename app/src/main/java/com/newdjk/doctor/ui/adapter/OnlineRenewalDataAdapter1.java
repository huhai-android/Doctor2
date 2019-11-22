package com.newdjk.doctor.ui.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.newdjk.doctor.ui.activity.IM.ChatActivity;
import com.newdjk.doctor.ui.entity.AllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.ConSultEntity;
import com.newdjk.doctor.ui.entity.ConsuLEndtentity;
import com.newdjk.doctor.ui.entity.ConsultMessageEntity;
import com.newdjk.doctor.ui.entity.ImRelationRecode;
import com.newdjk.doctor.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.doctor.ui.entity.MessageEventRecent;
import com.newdjk.doctor.ui.entity.OnlineRenewalDataEntity;
import com.newdjk.doctor.ui.entity.PatientInfoEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.ui.entity.RecordDataEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.GlideMediaLoader;
import com.newdjk.doctor.utils.NetworkUtil;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.LoadDialog;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnlineRenewalDataAdapter1  extends BaseQuickAdapter<AllRecordForDoctorEntity, BaseViewHolder> {

    private final MyOkHttp mMyOkhttp;
    private Gson mGson;
    private int mType;
    public OnlineRenewalDataAdapter1(@Nullable List<AllRecordForDoctorEntity> data,int type) {
        super(R.layout.online_renewal_item,data);
        mType = type;
        mGson = new Gson();
        mMyOkhttp = MyApplication.getInstance().getMyOkHttp();
    }

    @Override
    protected void convert(BaseViewHolder helper, final AllRecordForDoctorEntity item) {
        Log.i("Onlinezaixian", "AllRecordForDoctorEntity=" + item.toString());

        long unReadNum = item.getUnReadNum();
//        try {
            if (unReadNum > 0) {
                helper.setVisible(R.id.consult_unread_num_layout, true);
                helper.setText(R.id.renewal_unread_num, unReadNum + "");
            } else {
                helper.setVisible(R.id.consult_unread_num_layout, false);
            }
            helper.setText(R.id.patient_name, item.getPatientName());
            if (mType==3){
                if (!TextUtils.isEmpty(item.getEndTime())){
                    helper.setText(R.id.create_time, item.getEndTime().substring(0,16));

                }else {
                    helper.setText(R.id.create_time, "");
                }

                //状态（0：待续方，1：已接单/续方中，2：已退单，3：已回复-暂时没用，4：超时未接单，5：已结束）
                if (item.getStatus()==2){
                    helper.setVisible(R.id.im_status,true);
                    helper.setImageResource(R.id.im_status,R.drawable.icon_tuizhen);
                }else if (item.getStatus()==4){
                    helper.setVisible(R.id.im_status,true);
                    helper.setImageResource(R.id.im_status,R.drawable.icon_guoqi);
                }else {
                    helper.setVisible(R.id.im_status,false);
                }
            }else {
                helper.setText(R.id.create_time, item.getPayTime().substring(0,16));
            }
            ImageView avatar = helper.getView(R.id.avatar);
            GlideMediaLoader.load(mContext,avatar,item.getApplicantHeadImgUrl());
            Log.d("zaixian","图片地址"+item.toString());
            try {
                if (mType!=3){
                    ConSultEntity conSultEntity=mGson.fromJson(item.getRecordData(),ConSultEntity.class);
                    if (conSultEntity.getRecordInfo()!=null){
                        helper.setText(R.id.dynamic, conSultEntity.getRecordInfo().getAppeal()+"");
                    }else {
                        helper.setText(R.id.dynamic, "");
                    }

                }else {
                    ConsuLEndtentity conSultEntity=mGson.fromJson(item.getRecordData(),ConsuLEndtentity.class);
                    helper.setText(R.id.dynamic, conSultEntity.getAppeal()+"");
                }

            }catch (Exception e){
                helper.setText(R.id.dynamic, "暂无备注");

            }
            int sexType = item.getPatientSex();
            String area = item.getAreaName();
            if (area != null) {
                helper.setText(R.id.address,area);
            } else {
                helper.setText(R.id.address,"");
            }
            String sex = "";
            switch (sexType) {
                case 1:
                    sex = "男";
                    break;
                case 2:
                    sex = "女";
                    break;
                case 3:
                    sex = "未知";
                    break;
            }
            helper.setText(R.id.sex, sex);
            String areaName = item.getAreaName();
            if (areaName != null) {
                helper.setText(R.id.address, areaName);
            } else {
                helper.setText(R.id.address, "");
            }
            helper.setText(R.id.age, item.getAge());
//        } catch (Exception e) {
//            Log.e("OnlineAdapter","errorMesssage="+e.toString());
//            e.printStackTrace();
//        }
        helper.getView(R.id.consult_message_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json ;
                if (mType == 3) {
                    Type jsonType = new TypeToken<PrescriptionMessageEntity<OnlineRenewalDataEntity>>() {
                    }.getType();
                    OnlineRenewalDataEntity recordDataEntity = mGson.fromJson(item.getRecordData(),OnlineRenewalDataEntity.class);
                    PrescriptionMessageEntity<OnlineRenewalDataEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), jsonType);
                    prescriptionMessageEntity.setPatient(recordDataEntity);
                    json= mGson.toJson(prescriptionMessageEntity);

                    try {
                        if (NetworkUtil.isNetworkAvailable(mContext)) {
                            LoadDialog.show(mContext);
                            final String faceUrl = item.getApplicantHeadImgUrl();
                            String identifier = item.getApplicantIMId();
                            String imId = SpUtils.getString(Contants.identifier);
                            getIMRelationRecord(identifier, imId, faceUrl);
                        } else {
                            ToastUtil.setToast("网络连接异常，请检查网络");

                        }
                    }catch (Exception e){

                    }

                }
                else {
                    Type jsonType = new TypeToken<PrescriptionMessageEntity<RecordDataEntity>>() {
                    }.getType();
                    RecordDataEntity recordDataEntity = mGson.fromJson(item.getRecordData(),RecordDataEntity.class);
                    PrescriptionMessageEntity<RecordDataEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), jsonType);
                    prescriptionMessageEntity.setPatient(recordDataEntity);
                    json= mGson.toJson(prescriptionMessageEntity);

                    Log.i("zdp", "json=" + json);
                    Intent intentTalk = new Intent(mContext, ChatActivity.class);
                    intentTalk.putExtra(Contants.FRIEND_NAME, item.getPatientName());
                    intentTalk.putExtra("onlineRenewalDataEntity", item);
                    intentTalk.putExtra("action", "onlineRenewal");
                    intentTalk.putExtra("accountId",item.getApplicantId());
                    intentTalk.putExtra(Contants.FRIEND_IDENTIFIER,  item.getApplicantIMId());
                    intentTalk.putExtra("prescriptionMessage", json);
                    intentTalk.putExtra("imgPath", item.getApplicantHeadImgUrl());
                    mContext.startActivity(intentTalk);
                }

                //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);




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
                            SpUtils.put(Contants.patientName,imRelationRecode.getPatientName());
                            SpUtils.put(Contants.patientID,imRelationRecode.getPatientId());
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
}
