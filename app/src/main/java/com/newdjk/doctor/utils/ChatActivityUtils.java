package com.newdjk.doctor.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.artifex.mupdf.fitz.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.tools.MainConstant;
import com.newdjk.doctor.ui.activity.IM.ChatActivity;
import com.newdjk.doctor.ui.chat.NewChatActivity;
import com.newdjk.doctor.ui.entity.ConsultMessageEntity;
import com.newdjk.doctor.ui.entity.ImRelationRecode;
import com.newdjk.doctor.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.doctor.ui.entity.MessageEventRecent;
import com.newdjk.doctor.ui.entity.OnlineRenewalDataEntity;
import com.newdjk.doctor.ui.entity.PatientInfoEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.views.LoadDialog;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ChatActivityUtils {

    Gson mGson=new Gson();
    public static ChatActivityUtils chatActivityUtils;

   public static ChatActivityUtils getinStanse(){
        if (chatActivityUtils==null){
            chatActivityUtils=new ChatActivityUtils();
        }

        return chatActivityUtils;
    }


    public   void  toChat(String patientImId, String doctorIMId, final String faceUrl, android.content.Context context){


        getIMRelationRecord(patientImId,doctorIMId,faceUrl,context);

    }

    private void getIMRelationRecord(String patientImId, String doctorIMId, final String faceUrl,final android.content.Context mActivity) {

        Map<String, String> map = new HashMap<>();
        map.put("DoctorIMId", doctorIMId);
        map.put("PatientIMId", patientImId);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        MyApplication.getInstance().getMyOkHttp().post().url(HttpUrl.GetIMRelationRecord).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ImRelationRecode>>() {
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
                            QueryConsultDoctorAppMessageByPage(recordId, imRelationRecode.getAccountId(), imRelationRecode.getPatientName(),mActivity);
                        } else if (serviceCode == 2 || serviceCode == 6) {
                            QueryvideoDoctorAppMessageByPage(serviceCode,recordId, imRelationRecode.getAccountId(), imRelationRecode.getPatientName(),mActivity);
                        } else if (serviceCode == 3) {
                            QueryRenewalDoctorAppMessageByPage(recordId, imRelationRecode.getAccountId(), imRelationRecode.getPatientName(),mActivity);
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
                            Intent intent = new Intent(mActivity, NewChatActivity.class);
                            intent.putExtra("status", 0);
                            intent.putExtra(Contants.FRIEND_NAME, imRelationRecode.getPatientName());
                            intent.putExtra(Contants.FRIEND_IDENTIFIER, imRelationRecode.getPatientIMId());
                            intent.putExtra("drId", imRelationRecode.getDoctorId());
                            intent.putExtra("mLeftAvatorImagePath", faceUrl);
                            intent.putExtra("prescriptionMessage", json);
                            intent.putExtra("accountId", imRelationRecode.getAccountId());
                            intent.putExtra("fromHome", 1);
                            intent.putExtra("imgPath", imRelationRecode.getApplicantHeadImgUrl());
                            intent.putExtra("AccountId", imRelationRecode.getAccountId()+"");

                            //intent.putExtra("head",imRelationRecode.)
                            mActivity.startActivity(intent);
                        }
                    }
                } else {
                    Toast.makeText(mActivity, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
                LoadDialog.clear();
            }
        });
    }

    private void QueryConsultDoctorAppMessageByPage(final String id, final int accoundid, final String patientname,final android.content.Context mActivity) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("Id", id);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        MyApplication.getInstance().getMyOkHttp().post().url(HttpUrl.GetConsultRecord).params(requestMap).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ConsultMessageEntity>>() {
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
                    Intent consultIntentTalk = new Intent(mActivity, NewChatActivity.class);
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
                    consultIntentTalk.putExtra("AccountId", accoundid+"");

                    mActivity.startActivity(consultIntentTalk);
                    MessageEventRecent messageEvent = new MessageEventRecent();
                    messageEvent.setmType(MainConstant.UPDATEPUSHMESSAGELIST);
                    EventBus.getDefault().post(messageEvent);
                    //  Log.i("HomeFragment","entity="+entity.getData().toString());
                } else {
                    Toast.makeText(mActivity, entity.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void QueryRenewalDoctorAppMessageByPage(final String id, final int AccountId, final String patientname,final android.content.Context mActivity) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("Id", id);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        MyApplication.getInstance().getMyOkHttp().post().url(HttpUrl.GetPrescriptionApply).headers(headMap).params(requestMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<OnlineRenewalDataEntity>>() {
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
                    Intent renewalIntentTalk = new Intent(mActivity, NewChatActivity.class);
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
                    renewalIntentTalk.putExtra("AccountId", AccountId+"");
                    mActivity.startActivity(renewalIntentTalk);
                    MessageEventRecent messageEvent = new MessageEventRecent();
                    messageEvent.setmType(MainConstant.UPDATEPUSHMESSAGELIST);
                    EventBus.getDefault().post(messageEvent);

                    //  Log.i("HomeFragment","entity="+entity.getData().toString());
                } else {
                    Toast.makeText(mActivity, entity.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void QueryvideoDoctorAppMessageByPage(final  int serviceCode,final String id, final int AccountId, final String patientname,final android.content.Context mActivity) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("Id", id);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        MyApplication.getInstance().getMyOkHttp().post().url(HttpUrl.GetInquiryRecord).headers(headMap).params(requestMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<InquiryRecordListDataEntity>>() {
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
                    Intent videoIntentTalk = new Intent(mActivity, NewChatActivity.class);
                    videoIntentTalk.putExtra(Contants.FRIEND_NAME, doctorName);
                    videoIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, inquiryRecordListDataEntity.getApplicantIMId());
                    videoIntentTalk.putExtra("inquiryRecordListDataEntity", inquiryRecordListDataEntity);
                    videoIntentTalk.putExtra("action", "videoInterrogationfromhhome");
                    videoIntentTalk.putExtra("drId", inquiryRecordListDataEntity.getDoctorId());
                    videoIntentTalk.putExtra("prescriptionMessage", videoJson);
                    videoIntentTalk.putExtra("accountId", AccountId);
                    videoIntentTalk.putExtra("fromHome", 1);
                    videoIntentTalk.putExtra("serviceCode", serviceCode+"");
                    videoIntentTalk.putExtra("RecordId", id);
                    videoIntentTalk.putExtra("imgPath", inquiryRecordListDataEntity.getApplicantHeadImgUrl());
                    videoIntentTalk.putExtra(Contants.FRIEND_NAME, patientname);
                    videoIntentTalk.putExtra("AccountId", AccountId+"");
                    mActivity.startActivity(videoIntentTalk);
                    MessageEventRecent messageEvent = new MessageEventRecent();
                    messageEvent.setmType(MainConstant.UPDATEPUSHMESSAGELIST);
                    EventBus.getDefault().post(messageEvent);
                } else {
                    Toast.makeText(mActivity, entity.getMessage(), Toast.LENGTH_SHORT).show();
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
