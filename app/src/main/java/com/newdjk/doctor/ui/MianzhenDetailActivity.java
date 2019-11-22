package com.newdjk.doctor.ui;


import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.tools.MainConstant;
import com.newdjk.doctor.ui.activity.IM.ChatActivity;
import com.newdjk.doctor.ui.entity.ConsultMessageEntity;
import com.newdjk.doctor.ui.entity.ImRelationRecode;
import com.newdjk.doctor.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.doctor.ui.entity.MessageEventRecent;
import com.newdjk.doctor.ui.entity.MianzhenDetailEntity;
import com.newdjk.doctor.ui.entity.OnlineRenewalDataEntity;
import com.newdjk.doctor.ui.entity.PatientInfoEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.GlideMediaLoader;
import com.newdjk.doctor.utils.HeadUitl;
import com.newdjk.doctor.utils.NetworkUtil;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.CircleImageView;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.MianzhenDialog;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MianzhenDetailActivity extends BasicActivity {

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
    @BindView(R.id.avatar)
    CircleImageView avatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_appointtime)
    TextView tvAppointtime;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_cancel_desc)
    TextView tvCancelDesc;
    @BindView(R.id.im_status)
    ImageView imStatus;
    @BindView(R.id.lv_status)
    LinearLayout lvStatus;
    @BindView(R.id.tv_hospitical)
    TextView tvHospitical;
    private String recordid;
    private MianzhenDialog mDialog;
    private Gson mGson;
    private MianzhenDetailEntity mMianzhenDetailEntity;

    @Override
    protected int initViewResId() {
        return R.layout.activity_mianzhendetail;
    }

    @Override
    protected void initView() {
        initBackTitle("预约详情");
        mGson = new Gson();

    }

    @Override
    protected void initListener() {
        tvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mMianzhenDetailEntity.getAppointmentStatus()) {
                    case 0:
                        showdialog();

                        break;
                    case 1:

                        // toast("已完成");

                        break;
                    case 2:

                        // toast("已取消预约");
                        break;
                }
            }
        });

        tvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(mMianzhenDetailEntity.getApplicantIMId())) {
                    toast("接口中暂时未提供病人imid，暂时无法进入");
                    return;
                }

                try {
                    if (NetworkUtil.isNetworkAvailable(mContext)) {
                        LoadDialog.show(mContext);
                        TIMConversation conversation = TIMManager.getInstance().getConversation(
                                TIMConversationType.C2C,    //会话类型：单聊
                                mMianzhenDetailEntity.getApplicantIMId());      //会话对方用户帐号
                        //将此会话的所有消息标记为已读
                        List<TIMMessage> lastMsgs = conversation.getLastMsgs(1);
                        TIMMessage msg = lastMsgs.get(0);
                        conversation.setReadMessage(msg, new TIMCallBack() {
                            @Override
                            public void onError(int code, String desc) {
                                Log.e("setReadMessage", "setReadMessage failed, code: " + code + "|desc: " + desc);
                                ToastUtil.setToast("网络连接异常，请检查网络");
                                LoadDialog.clear();
                            }

                            @Override
                            public void onSuccess() {
                                final String faceUrl = mMianzhenDetailEntity.getPatientPic();
                                String identifier = mMianzhenDetailEntity.getApplicantIMId();
                                String imId = SpUtils.getString(Contants.identifier);
                                getIMRelationRecord(identifier, imId, faceUrl);
                            }
                        });
                    } else {
                        ToastUtil.setToast("网络连接异常，请检查网络");

                    }
                } catch (Exception e) {

                }


            }
        });
    }

    private void showdialog() {

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
                CancelFaceConsultation();
            }
        });

    }

    @Override
    protected void initData() {
        recordid = getIntent().getStringExtra("id");
        GetFaceConsultationDetail();


    }

    @Override
    protected void otherViewClick(View view) {

    }

    //获取订单详情
    public void GetFaceConsultationDetail() {

        Map<String, String> params = new HashMap<>();
        params.put("RecordId", recordid + "");
        //  params.put("DrId", "68");
        // String json = mGson.toJson(params);

        mMyOkhttp.get().url(HttpUrl.GetFaceConsultationDetail).params(params).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MianzhenDetailEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MianzhenDetailEntity> entity) {
                if (entity.getCode() == 0) {
                    mMianzhenDetailEntity = entity.getData();
                    if (mMianzhenDetailEntity != null) {
                        //        if (mDataBean != null) {
                        tvName.setText(mMianzhenDetailEntity.getPatientName() + "");
                        switch (mMianzhenDetailEntity.getPatientSex()) {
                            case 1:
                                tvSex.setText("男");
                                break;
                            case 2:
                                tvSex.setText("女");

                                break;
                            default:
                                tvSex.setText("未知");
                                break;
                        }

                        tvAge.setText(mMianzhenDetailEntity.getPatientAge() + "");
                        tvPhone.setText(mMianzhenDetailEntity.getPatientMobile() + "");
                        tvAddress.setText(mMianzhenDetailEntity.getConsultationAddress() + "");
                        tvHospitical.setText((TextUtils.isEmpty(mMianzhenDetailEntity.getConsultationHospital())?"":mMianzhenDetailEntity.getConsultationHospital()) + "");

                        tvTime.setText(mMianzhenDetailEntity.getAppointmentDate().substring(0, 11) + getweek(mMianzhenDetailEntity.getWeekDay()) + "  " + mMianzhenDetailEntity.getStartTime().substring(0, 5) + " - " + mMianzhenDetailEntity.getEndTime().substring(0, 5));

                        tvDesc.setText((TextUtils.isEmpty(mMianzhenDetailEntity.getDescription()) ? "" : mMianzhenDetailEntity.getDescription()) + "");
                        GlideMediaLoader.load(mContext, avatar, mMianzhenDetailEntity.getPatientPic());

                        //是否可以取消
                        //0-待就诊
                        if (mMianzhenDetailEntity.getAppointmentStatus() == 0) {
                            //正常
                            if (mMianzhenDetailEntity.isCanCancel()) {

                                tvStatus.setClickable(true);
                                tvCancelDesc.setVisibility(View.GONE);
                                tvCancelDesc.setVisibility(View.GONE);
                                tvStatus.setBackgroundResource(R.drawable.bg_quxiao_lianxi_mianzhen);
                                tvStatus.setTextColor(getResources().getColor(R.color.quxiaoyuyue));
                                //不足24h
                            } else {
                                tvStatus.setClickable(false);
                                tvCancelDesc.setVisibility(View.VISIBLE);
                                tvCancelDesc.setText(mMianzhenDetailEntity.getCannotCancelReason() + "");
                                tvStatus.setBackgroundResource(R.drawable.bg_quxiao_lianxi_mianzhen_enable);
                                tvStatus.setTextColor(getResources().getColor(R.color.quxiaoyuyueenable));
                            }

                            //1-已完成；2-已取消
                        } else if (mMianzhenDetailEntity.getAppointmentStatus() == 1) {
                            imStatus.setVisibility(View.GONE);//影藏取消按钮
                            lvStatus.setVisibility(View.GONE);
                            tvStatus.setClickable(false);
                            tvCancelDesc.setVisibility(View.GONE);
                            tvStatus.setBackgroundResource(R.drawable.bg_quxiao_lianxi_mianzhen_enable);
                            tvStatus.setTextColor(getResources().getColor(R.color.quxiaoyuyueenable));

                            //2-已取消
                        } else if (mMianzhenDetailEntity.getAppointmentStatus() == 2) {
                            imStatus.setVisibility(View.VISIBLE);//显示取消按钮
                            lvStatus.setVisibility(View.GONE);
                            tvStatus.setClickable(false);
                            tvCancelDesc.setVisibility(View.GONE);
                            tvStatus.setBackgroundResource(R.drawable.bg_quxiao_lianxi_mianzhen_enable);
                            tvStatus.setTextColor(getResources().getColor(R.color.quxiaoyuyueenable));
                        }

                    }


                } else {
                    // toast(entity.getMessage());
                    ToastUtil.setToast(entity.getMessage());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                ToastUtil.setToast(errorMsg);
            }
        });

    }

    public void CancelFaceConsultation() {

        Map<String, String> params = new HashMap<>();
        params.put("RecordId", recordid + "");
        params.put("PatientIdOrDrId", MyApplication.mDoctorInfoByIdEntity.getDrId() + "");//就诊人id/医生id。如果是会员取消传就诊人id，如果是医生取消传医生id
        params.put("CancelType", "2");//取消途径。1-会员主动取消；2-医生取消
        params.put("CancelReason", "");
        //  params.put("DrId", "68");
        // String json = mGson.toJson(params);

        mMyOkhttp.post().url(HttpUrl.CancelFaceConsultation).params(params).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity entity) {
                if (entity.getCode() == 0) {
                    tvStatus.setText("已取消预约");
                    lvStatus.setVisibility(View.GONE);
                    imStatus.setVisibility(View.VISIBLE);
                    mMianzhenDetailEntity.setAppointmentStatus(2);
                   // EventBus.getDefault().post(new MianzhenSuccessEntity(true));

                } else {
                    // toast(entity.getMessage());
                    ToastUtil.setToast(entity.getMessage());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                ToastUtil.setToast(errorMsg);
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

    public String getweek(int weekday) {
        String week = "";
        switch (weekday) {
            case 1:
                week = "(星期一)";
                break;
            case 2:
                week = "(星期二)";
                break;
            case 3:
                week = "(星期三)";
                break;
            case 4:
                week = "(星期四)";
                break;
            case 5:
                week = "(星期五)";
                break;
            case 6:
                week = "(星期六)";
                break;
            case 7:
                week = "(星期日)";
                break;

        }
        return week;
    }



}
