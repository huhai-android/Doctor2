package com.newdjk.doctor.ui.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxq.okhttp.MyOkHttp;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.greendao.gen.PushDataDaoEntityDao;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.DoctorPraiseActivity;
import com.newdjk.doctor.ui.activity.IM.ChatActivity;
import com.newdjk.doctor.ui.activity.IM.GroupChatActivity;
import com.newdjk.doctor.ui.activity.Mdt.MDTFenZhenActivity;
import com.newdjk.doctor.ui.activity.MyCheckCenterActivity;
import com.newdjk.doctor.ui.activity.PrescriptionActivity;
import com.newdjk.doctor.ui.activity.PrescriptionCheckActivity;
import com.newdjk.doctor.ui.activity.SecondDiagnosisReportActivity;
import com.newdjk.doctor.ui.activity.TaskActivity;
import com.newdjk.doctor.ui.entity.AllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.ChufangIDEntity;
import com.newdjk.doctor.ui.entity.ConsultMessageEntity;
import com.newdjk.doctor.ui.entity.DoctorPatientRelationEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.ExtrasDataEntity;
import com.newdjk.doctor.ui.entity.IMMessageEntity;
import com.newdjk.doctor.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.ui.entity.MDTpushEntity;
import com.newdjk.doctor.ui.entity.OnlineRenewalDataEntity;
import com.newdjk.doctor.ui.entity.PatientInfoEntity;
import com.newdjk.doctor.ui.entity.PatientListDataEntity;
import com.newdjk.doctor.ui.entity.PatientListEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.ui.entity.PushDataDaoEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UpdatePositionEntity;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.ui.entity.YWXListenerEntity;
import com.newdjk.doctor.ui.entity.YouxuanGoodEntity;
import com.newdjk.doctor.utils.AppUtils;
import com.newdjk.doctor.utils.SQLiteUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.CircleImageView;
import com.newdjk.doctor.views.DownloadCertDialog;
import com.newdjk.doctor.views.LoadDialog;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMGroupDetailInfo;
import com.tencent.TIMGroupManager;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.org.bjca.sdk.core.kit.BJCASDK;
import cn.org.bjca.sdk.core.kit.YWXListener;

/**
 * Created by gabriel on 2017/2/28.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    private static final String TAG = "MessageAdapter";
    private Activity mActivity;
    //  private ViewHolder viewHolder;
    private List<PushDataDaoEntity> mTIMMessageList;
    private Gson mGson;
    protected MyOkHttp mMyOkhttp;
    private String mAction;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public MessageAdapter(List<PushDataDaoEntity> list, Activity activity, String action) {
        mActivity = activity;
        mMyOkhttp = MyApplication.getInstance().getMyOkHttp();
        mAction = action;
        mTIMMessageList = list;
        mGson = new Gson();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.
                from(MyApplication.getContext()).inflate(R.layout.item_conversation, parent, false));
        //   this.viewHolder = viewHolder;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PushDataDaoEntity jpushDataEntity = mTIMMessageList.get(position);
        String extras = jpushDataEntity.getData();
        String title = jpushDataEntity.getTitle();
        if (mAction != null && mAction.equals("system")) {
            Glide.with(MyApplication.getContext())
                    .load(R.drawable.systeminfo)

                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.avatar);
        } else {
            Glide.with(MyApplication.getContext())
                    .load(R.mipmap.patient_report_icon)

                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.avatar);
        }
        boolean isRead = jpushDataEntity.getIsRead();

        if (extras != null) {
            ExtrasDataEntity extrasDataEntity = mGson.fromJson(extras, ExtrasDataEntity.class);
            int type = extrasDataEntity.getType();
            switch (type) {
                case 3:
                    Glide.with(MyApplication.getContext())
                            .load(R.drawable.taixincheck)

                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.avatar);
                    break;
                case 10:
                    Glide.with(MyApplication.getContext())
                            .load(R.mipmap.patient_report_icon)

                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.avatar);
                    break;
                case 12:
                    Glide.with(MyApplication.getContext())
                            .load(R.mipmap.evaluate)

                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.avatar);
                    break;
                case 13:
                    Glide.with(MyApplication.getContext())
                            .load(R.mipmap.renewal_icon)

                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.avatar);
                    break;
                case 14:
                    Glide.with(MyApplication.getContext())
                            .load(R.mipmap.consult_icon)

                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.avatar);
                    break;
                case 15:
                    Glide.with(MyApplication.getContext())
                            .load(R.mipmap.video_icon)

                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.avatar);
                    break;
            }

            holder.last_message.setText(jpushDataEntity.getContent());
            String time = extrasDataEntity.getTime();
            holder.message_time.setText(time);
        }
        if (isRead) {
            holder.systemUnreadNumLayout.setVisibility(View.GONE);
        } else {
            holder.systemUnreadNumLayout.setVisibility(View.VISIBLE);
        }

        if (title != null) {
            holder.name.setText(title);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mTIMMessageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView avatar;
        TextView name;
        TextView last_message;
        TextView message_time;
        TextView unread_num;
        LinearLayout systemUnreadNumLayout;

        ViewHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            name = itemView.findViewById(R.id.name);
            last_message = itemView.findViewById(R.id.last_message);
            message_time = itemView.findViewById(R.id.message_time);
            unread_num = itemView.findViewById(R.id.unread_num);
            systemUnreadNumLayout = itemView.findViewById(R.id.system_unread_num_layout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoadDialog.show(mActivity);
                    PushDataDaoEntity JpushDataEntity = mTIMMessageList.get(getAdapterPosition());
                    final String extras = JpushDataEntity.getData();
                    int msgId = JpushDataEntity.getMsgId();
                    Log.i("MessageAdapter", extras);
                    if (extras != null) {
                        final ExtrasDataEntity extrasDataEntity = mGson.fromJson(extras, ExtrasDataEntity.class);
                        if (extrasDataEntity == null) {
                            return;
                        }
                        //     JpushDataEntity.setRead(true);
                        PushDataDaoEntity PushDataDaoEntity = MyApplication.getInstance().getDaoSession().getPushDataDaoEntityDao().queryBuilder().where(PushDataDaoEntityDao.Properties.Id.eq(JpushDataEntity.getId())).build().unique();
                       /* SQLiteUtils.getInstance().addPushData(new PushDataDaoEntity(JpushDataEntity.getId(), JpushDataEntity.getTitle(), JpushDataEntity.getMessage(), extras, JpushDataEntity.getSendTime(),JpushDataEntity.isRead()));
                        EventBus.getDefault().post(new UpdatePushView(true));*/
                        if (PushDataDaoEntity != null) {
                            PushDataDaoEntity.setIsRead(true);
                            setServicePushMessageRead(String.valueOf(msgId));
                            SQLiteUtils.getInstance().updatePushData(PushDataDaoEntity);
                            if (mAction != null && mAction.equals("system")) {
                                EventBus.getDefault().post(new UpdatePushView(1));
                            } else {
                                EventBus.getDefault().post(new UpdatePushView(2));
                            }
                            EventBus.getDefault().post(new UpdatePositionEntity(getAdapterPosition()));
                        }


                        int type = extrasDataEntity.getType();
                        if (extrasDataEntity.getData() != null) {
                            String recordId = extrasDataEntity.getData().getRecordId();
                            switch (type) {
                                //新患者报道
                                case 10:
                                    int patientId = extrasDataEntity.getData().getPatientId();
                                    String name = extrasDataEntity.getData().getPatientName();
                                    requestAllNewReportPaientMessage(patientId);
                                    //  getImMessage(patientId,name);
                                    break;
                                //续方请求提醒
                                case 13:
                                    QueryRenewalDoctorAppMessageByPage(recordId+"");
                                    break;
                                //图文问诊提醒
                                case 14:
                                    QueryConsultDoctorAppMessageByPage(recordId);
                                    break;
                                //视频问诊提醒
                                case 15:
                                    QueryvideoDoctorAppMessageByPage(recordId);
                                    break;
                                // 评价提醒
                                case 12:
                                    Intent doctorPraiseIntent = new Intent(mActivity, DoctorPraiseActivity.class);
                                    mActivity.startActivity(doctorPraiseIntent);
                                    break;
                                // 续方未通过
                                case 16:
                                    Intent prescriptionIntent = new Intent(mActivity, PrescriptionActivity.class);
                                    prescriptionIntent.putExtra("rejectId", String.valueOf(extrasDataEntity.getData().getPrescriptionId()));
                                    prescriptionIntent.putExtra("prescriptionMessage", SpUtils.getString(Contants.LoginJson));
                                    mActivity.startActivity(prescriptionIntent);
                                    break;
                                case 100:
                                    Intent intentCheckCenter = MyCheckCenterActivity.newIntent(mActivity);
                                    mActivity.startActivity(intentCheckCenter);
                                    break;
                                case 219://第二诊疗
//                                    Intent treatIntent = new Intent(mActivity, TreatActivity.class);
//                                    mActivity.startActivity(treatIntent);

                                    Intent chufangPrescription = TaskActivity.newIntent(mActivity);
                                    mActivity.startActivity(chufangPrescription);

                                    break;
                                case 220:
                                    Intent systemSettingIntent = new Intent(mActivity, SecondDiagnosisReportActivity.class);
                                    systemSettingIntent.putExtra("isHasReport", true);
                                    systemSettingIntent.putExtra("MedicalRecordId", extrasDataEntity.getData().getMedicalRecordId());
                                    mActivity.startActivity(systemSettingIntent);
                                    break;
                                case 101:
                                    boolean isExists = BJCASDK.getInstance().existsCert(mActivity);
                                    boolean ExistStamp = BJCASDK.getInstance().existsStamp(mActivity);
                                    if (!isExists) {
                                        DownloadCertDialog mDialog = new DownloadCertDialog(mActivity);
                                        mDialog.show("", "", new DownloadCertDialog.DialogListener() {
                                            @Override
                                            public void confirm() {
                                                BJCASDK.getInstance().certDown(mActivity, Contants.clientId, SpUtils.getString(Contants.userName), new YWXListener() {
                                                    @Override
                                                    public void callback(String s) {
                                                        YWXListenerEntity yWXListenerEntity = mGson.fromJson(s, YWXListenerEntity.class);
                                                        String status = yWXListenerEntity.getStatus();
                                                        String message = yWXListenerEntity.getMessage();
                                                        if (status != null && status.equals("0")) {
                                                            boolean ExistStamp1 = BJCASDK.getInstance().existsStamp(mActivity);
                                                            if (!ExistStamp1) {
                                                                BJCASDK.getInstance().drawStamp(mActivity, Contants.clientId, new YWXListener() {
                                                                    @Override
                                                                    public void callback(String msg) {
                                                                        YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                                                        String status = yWXListenerEntity.getStatus();
                                                                        String message = yWXListenerEntity.getMessage();
                                                                        if (status != null && status.equals("0")) {
                                                                            Intent chufang = new Intent(mActivity, PrescriptionCheckActivity.class);
                                                                            ChufangIDEntity chufangIDEntity = mGson.fromJson(extras, ChufangIDEntity.class);
                                                                            Log.d(TAG, "处方id" + chufangIDEntity.getData().getPrescriptionId() + "  " + extras);
                                                                            chufang.putExtra("action", String.valueOf(chufangIDEntity.getData().getPrescriptionId()));
                                                                            chufang.putExtra("prescriptionMessage", SpUtils.getString(Contants.LoginJson));
                                                                            chufang.putExtra("from", 2);
                                                                            mActivity.startActivity(chufang);
                                                                        } else {
                                                                            //   Toast.makeText(mActivity,message,Toast.LENGTH_SHORT).show();
                                                                            ToastUtil.setToast("设置签章失败，请重试！+(" + status + ")");
                                                                        }
                                                                    }
                                                                });
                                                            } else {
                                                                Intent chufang = new Intent(mActivity, PrescriptionCheckActivity.class);
                                                                ChufangIDEntity chufangIDEntity = mGson.fromJson(extras, ChufangIDEntity.class);
                                                                Log.d(TAG, "处方id" + chufangIDEntity.getData().getPrescriptionId() + "  " + extras);
                                                                chufang.putExtra("action", String.valueOf(chufangIDEntity.getData().getPrescriptionId()));
                                                                chufang.putExtra("prescriptionMessage", SpUtils.getString(Contants.LoginJson));
                                                                chufang.putExtra("from", 2);
                                                                mActivity.startActivity(chufang);
                                                            }
                                                        } else {
                                                            //  Toast.makeText(mActivity,message,Toast.LENGTH_SHORT).show();
                                                            Toast.makeText(mActivity, "下载证书失败，请重试!(" + status + ")", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                });
                                            }
                                        });

                                    } else {
                                        //   BJCASDK.getInstance().clearCert(ChatActivity.this);
                                        Log.i("zdp", "证书已下载");
                                        if (!ExistStamp) {
                                            BJCASDK.getInstance().drawStamp(mActivity, Contants.clientId, new YWXListener() {
                                                @Override
                                                public void callback(String msg) {
                                                    YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                                    String status = yWXListenerEntity.getStatus();
                                                    String message = yWXListenerEntity.getMessage();
                                                    if (status != null && status.equals("0")) {
                                                        Intent chufang = new Intent(mActivity, PrescriptionCheckActivity.class);
                                                        ChufangIDEntity chufangIDEntity = mGson.fromJson(extras, ChufangIDEntity.class);
                                                        Log.d(TAG, "处方id" + chufangIDEntity.getData().getPrescriptionId() + "  " + extras);
                                                        chufang.putExtra("action", String.valueOf(chufangIDEntity.getData().getPrescriptionId()));
                                                        chufang.putExtra("prescriptionMessage", SpUtils.getString(Contants.LoginJson));
                                                        chufang.putExtra("from", 2);
                                                        mActivity.startActivity(chufang);
                                                    } else {
                                                        // Toast.makeText(mActivity,message,Toast.LENGTH_SHORT).show();
                                                        ToastUtil.setToast("设置签章失败，请重试！+(" + status + ")");
                                                    }
                                                }
                                            });
                                        } else {

                                            Intent chufang = new Intent(mActivity, PrescriptionCheckActivity.class);
                                            ChufangIDEntity chufangIDEntity = mGson.fromJson(extras, ChufangIDEntity.class);
                                            Log.d(TAG, "处方id" + chufangIDEntity.getData().getPrescriptionId() + "  " + extras);
                                            chufang.putExtra("action", String.valueOf(chufangIDEntity.getData().getPrescriptionId()));
                                            chufang.putExtra("prescriptionMessage", SpUtils.getString(Contants.LoginJson));
                                            chufang.putExtra("from", 2);
                                            mActivity.startActivity(chufang);
                                        }
                                    }
                                    break;

                                case 103:

                                    boolean isExists2 = BJCASDK.getInstance().existsCert(mActivity);
                                    boolean ExistStamp2 = BJCASDK.getInstance().existsStamp(mActivity);
                                    if (!isExists2) {
                                        DownloadCertDialog mDialog = new DownloadCertDialog(mActivity);
                                        mDialog.show("", "", new DownloadCertDialog.DialogListener() {
                                            @Override
                                            public void confirm() {
                                                BJCASDK.getInstance().certDown(mActivity, Contants.clientId, SpUtils.getString(Contants.userName), new YWXListener() {
                                                    @Override
                                                    public void callback(String s) {
                                                        YWXListenerEntity yWXListenerEntity = mGson.fromJson(s, YWXListenerEntity.class);
                                                        String status = yWXListenerEntity.getStatus();
                                                        String message = yWXListenerEntity.getMessage();
                                                        if (status != null && status.equals("0")) {
                                                            boolean ExistStamp1 = BJCASDK.getInstance().existsStamp(mActivity);
                                                            if (!ExistStamp1) {
                                                                BJCASDK.getInstance().drawStamp(mActivity, Contants.clientId, new YWXListener() {
                                                                    @Override
                                                                    public void callback(String msg) {
                                                                        YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                                                        String status = yWXListenerEntity.getStatus();
                                                                        String message = yWXListenerEntity.getMessage();
                                                                        if (status != null && status.equals("0")) {
                                                                            Intent chufang = new Intent(mActivity, PrescriptionCheckActivity.class);
                                                                            ChufangIDEntity chufangIDEntity = mGson.fromJson(extras, ChufangIDEntity.class);
                                                                            Log.d(TAG, "处方id" + chufangIDEntity.getData().getPrescriptionId() + "  " + extras);
                                                                            chufang.putExtra("action", String.valueOf(chufangIDEntity.getData().getPrescriptionId()));
                                                                            chufang.putExtra("prescriptionMessage", SpUtils.getString(Contants.LoginJson));
                                                                            chufang.putExtra("prescriptionType", chufangIDEntity.getData().getPrescriptionType());
                                                                            chufang.putExtra("from", 3);
                                                                            mActivity.startActivity(chufang);
                                                                        } else {
                                                                            //   Toast.makeText(mActivity,message,Toast.LENGTH_SHORT).show();
                                                                            ToastUtil.setToast("设置签章失败，请重试！+(" + status + ")");
                                                                        }
                                                                    }
                                                                });
                                                            } else {
                                                                Intent chufang = new Intent(mActivity, PrescriptionCheckActivity.class);
                                                                ChufangIDEntity chufangIDEntity = mGson.fromJson(extras, ChufangIDEntity.class);
                                                                Log.d(TAG, "处方id" + chufangIDEntity.getData().getPrescriptionId() + "  " + extras);
                                                                chufang.putExtra("action", String.valueOf(chufangIDEntity.getData().getPrescriptionId()));
                                                                chufang.putExtra("prescriptionType", chufangIDEntity.getData().getPrescriptionType());
                                                                chufang.putExtra("prescriptionMessage", SpUtils.getString(Contants.LoginJson));
                                                                chufang.putExtra("from", 3);
                                                                mActivity.startActivity(chufang);
                                                            }
                                                        } else {
                                                            //  Toast.makeText(mActivity,message,Toast.LENGTH_SHORT).show();
                                                            Toast.makeText(mActivity, "下载证书失败，请重试!(" + status + ")", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                });
                                            }
                                        });

                                    } else {
                                        //   BJCASDK.getInstance().clearCert(ChatActivity.this);
                                        Log.i("zdp", "证书已下载");
                                        if (!ExistStamp2) {
                                            BJCASDK.getInstance().drawStamp(mActivity, Contants.clientId, new YWXListener() {
                                                @Override
                                                public void callback(String msg) {
                                                    YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                                    String status = yWXListenerEntity.getStatus();
                                                    String message = yWXListenerEntity.getMessage();
                                                    if (status != null && status.equals("0")) {
                                                        Intent chufang = new Intent(mActivity, PrescriptionCheckActivity.class);
                                                        ChufangIDEntity chufangIDEntity = mGson.fromJson(extras, ChufangIDEntity.class);
                                                        Log.d(TAG, "处方id" + chufangIDEntity.getData().getPrescriptionId() + "  " + extras);
                                                        chufang.putExtra("action", String.valueOf(chufangIDEntity.getData().getPrescriptionId()));
                                                        chufang.putExtra("prescriptionType", chufangIDEntity.getData().getPrescriptionType());
                                                        chufang.putExtra("prescriptionMessage", SpUtils.getString(Contants.LoginJson));
                                                        chufang.putExtra("from", 3);
                                                        mActivity.startActivity(chufang);
                                                    } else {
                                                        // Toast.makeText(mActivity,message,Toast.LENGTH_SHORT).show();
                                                        ToastUtil.setToast("设置签章失败，请重试！+(" + status + ")");
                                                    }
                                                }
                                            });
                                        } else {

                                            Intent chufang = new Intent(mActivity, PrescriptionCheckActivity.class);
                                            ChufangIDEntity chufangIDEntity = mGson.fromJson(extras, ChufangIDEntity.class);
                                            Log.d(TAG, "处方id" + chufangIDEntity.getData().getPrescriptionId() + "  " + extras);
                                            chufang.putExtra("action", String.valueOf(chufangIDEntity.getData().getPrescriptionId()));
                                            chufang.putExtra("prescriptionType", chufangIDEntity.getData().getPrescriptionType());
                                            chufang.putExtra("prescriptionMessage", SpUtils.getString(Contants.LoginJson));
                                            chufang.putExtra("from", 3);
                                            mActivity.startActivity(chufang);
                                        }
                                    }

                                    break;
                                // 中药续方未通过
                                case 104:
                                    Intent prescriptionTCMIntent = new Intent(mActivity, PrescriptionActivity.class);
                                    prescriptionTCMIntent.putExtra("action", String.valueOf(extrasDataEntity.getData().getPrescriptionId()));
                                    prescriptionTCMIntent.putExtra("prescriptionMessage", SpUtils.getString(Contants.LoginJson));
                                    prescriptionTCMIntent.putExtra("type", 19);
                                    mActivity.startActivity(prescriptionTCMIntent);
                                    break;

                                case 102:
                                    showDialog(JpushDataEntity.getContent());
                                    break;
                                case 221:
                                    showDialog(JpushDataEntity.getContent());
                                    break;

                                case 222:
                                    showDialog(JpushDataEntity.getContent());
                                    break;
                                case 223:
                                    showDialog(JpushDataEntity.getContent());
                                    break;
                                case 227:
                                    Intent youxuanintent = new Intent(mActivity, PrescriptionActivity.class);
                                    YouxuanGoodEntity youxuanGoodEntity = mGson.fromJson(extras, YouxuanGoodEntity.class);
                                    Log.d(TAG, extras + "         " + youxuanGoodEntity.getData().getGoodsRecomBuyId());
                                    youxuanintent.putExtra("GoodsRecomBuyId", youxuanGoodEntity.getData().getGoodsRecomBuyId());
                                    youxuanintent.putExtra("prescriptionMessage", SpUtils.getString(Contants.LoginJson));
                                    youxuanintent.putExtra("type", 20);
                                    mActivity.startActivity(youxuanintent);
                                    break;
                                case 245:
                                    List<String> doctorIdList3 = new ArrayList<>();
                                    MDTpushEntity mdTpushEntity3 = mGson.fromJson(extras, MDTpushEntity.class);
                                    doctorIdList3.add(mdTpushEntity3.getData().getIMGroupId());
                                    LoadDialog.show(mActivity);
                                    TIMGroupManager.getInstance().getGroupDetailInfo(doctorIdList3, new TIMValueCallBack<List<TIMGroupDetailInfo>>() {
                                        @Override
                                        public void onError(int i, String s) {
                                            LoadDialog.clear();
                                        }

                                        @Override
                                        public void onSuccess(List<TIMGroupDetailInfo> timGroupDetailInfos) {
                                            if (timGroupDetailInfos.size() > 0) {
                                                getIMRelationRecord(timGroupDetailInfos.get(0));
                                            }
                                        }
                                    });
                                    break;
                                case 246:

                                    List<String> doctorIdList = new ArrayList<>();
                                    MDTpushEntity mdTpushEntity = mGson.fromJson(extras, MDTpushEntity.class);
                                    doctorIdList.add(mdTpushEntity.getData().getIMGroupId());
                                    LoadDialog.show(mActivity);
                                    TIMGroupManager.getInstance().getGroupDetailInfo(doctorIdList, new TIMValueCallBack<List<TIMGroupDetailInfo>>() {
                                        @Override
                                        public void onError(int i, String s) {
                                            LoadDialog.clear();
                                        }

                                        @Override
                                        public void onSuccess(List<TIMGroupDetailInfo> timGroupDetailInfos) {
                                            if (timGroupDetailInfos.size() > 0) {
                                                getIMRelationRecord(timGroupDetailInfos.get(0));
                                            }
                                        }
                                    });
                                    break;
                                case 239:

                                    List<String> doctorIdList2 = new ArrayList<>();
                                    MDTpushEntity mdTpushEntity2 = mGson.fromJson(extras, MDTpushEntity.class);
                                    doctorIdList2.add(mdTpushEntity2.getData().getIMGroupId());
                                    LoadDialog.show(mActivity);
                                    TIMGroupManager.getInstance().getGroupDetailInfo(doctorIdList2, new TIMValueCallBack<List<TIMGroupDetailInfo>>() {
                                        @Override
                                        public void onError(int i, String s) {
                                            LoadDialog.clear();
                                        }

                                        @Override
                                        public void onSuccess(List<TIMGroupDetailInfo> timGroupDetailInfos) {
                                            if (timGroupDetailInfos.size() > 0) {
                                                getIMRelationRecord(timGroupDetailInfos.get(0));
                                            }
                                        }
                                    });

                                    break;
                                case 251:
                                    List<String> doctorIdList4 = new ArrayList<>();
                                    MDTpushEntity mdTpushEntity4 = mGson.fromJson(extras, MDTpushEntity.class);
                                    doctorIdList4.add(mdTpushEntity4.getData().getIMGroupId());
                                    LoadDialog.show(mActivity);
                                    TIMGroupManager.getInstance().getGroupDetailInfo(doctorIdList4, new TIMValueCallBack<List<TIMGroupDetailInfo>>() {
                                        @Override
                                        public void onError(int i, String s) {
                                            LoadDialog.clear();
                                        }

                                        @Override
                                        public void onSuccess(List<TIMGroupDetailInfo> timGroupDetailInfos) {
                                            if (timGroupDetailInfos.size() > 0) {
                                                getIMRelationRecord(timGroupDetailInfos.get(0));
                                            }
                                        }
                                    });

                                    break;

                                case 244:
                                    Intent fenjizhuanzhen = new Intent(mActivity, MDTFenZhenActivity.class);
                                    mActivity.startActivity(fenjizhuanzhen);

                                    break;

                                case 243:
                                    List<String> doctorIdList5 = new ArrayList<>();
                                    MDTpushEntity mdTpushEntity5 = mGson.fromJson(extras, MDTpushEntity.class);
                                    doctorIdList5.add(mdTpushEntity5.getData().getIMGroupId());
                                    LoadDialog.show(mActivity);
                                    TIMGroupManager.getInstance().getGroupDetailInfo(doctorIdList5, new TIMValueCallBack<List<TIMGroupDetailInfo>>() {
                                        @Override
                                        public void onError(int i, String s) {
                                            LoadDialog.clear();
                                        }

                                        @Override
                                        public void onSuccess(List<TIMGroupDetailInfo> timGroupDetailInfos) {
                                            if (timGroupDetailInfos.size() > 0) {
                                                getIMRelationRecord(timGroupDetailInfos.get(0));
                                            }
                                        }
                                    });

                                    break;


                                case 250:
                                    List<String> doctorIdList6 = new ArrayList<>();
                                    MDTpushEntity mdTpushEntity6 = mGson.fromJson(extras, MDTpushEntity.class);
                                    doctorIdList6.add(mdTpushEntity6.getData().getIMGroupId());
                                    LoadDialog.show(mActivity);
                                    TIMGroupManager.getInstance().getGroupDetailInfo(doctorIdList6, new TIMValueCallBack<List<TIMGroupDetailInfo>>() {
                                        @Override
                                        public void onError(int i, String s) {
                                            LoadDialog.clear();
                                        }

                                        @Override
                                        public void onSuccess(List<TIMGroupDetailInfo> timGroupDetailInfos) {
                                            if (timGroupDetailInfos.size() > 0) {
                                                getIMRelationRecord(timGroupDetailInfos.get(0));
                                            }
                                        }
                                    });

                                    break;
                                case 240:
                                    List<String> doctorIdList7 = new ArrayList<>();
                                    MDTpushEntity mdTpushEntity7 = mGson.fromJson(extras, MDTpushEntity.class);
                                    doctorIdList7.add(mdTpushEntity7.getData().getIMGroupId());
                                    LoadDialog.show(mActivity);
                                    TIMGroupManager.getInstance().getGroupDetailInfo(doctorIdList7, new TIMValueCallBack<List<TIMGroupDetailInfo>>() {
                                        @Override
                                        public void onError(int i, String s) {
                                            LoadDialog.clear();
                                        }

                                        @Override
                                        public void onSuccess(List<TIMGroupDetailInfo> timGroupDetailInfos) {
                                            if (timGroupDetailInfos.size() > 0) {
                                                getIMRelationRecord(timGroupDetailInfos.get(0));
                                            }
                                        }
                                    });

                                    break;

                            }
                        } else {
                            switch (type) {
                                case 102:
                                    //   showDialog(JpushDataEntity.getContent());
                                    break;
                                case 221:
                                    showDialog(JpushDataEntity.getContent());
                                    break;

                                case 222:
                                    showDialog(JpushDataEntity.getContent());
                                    break;
                                case 223:
                                    showDialog(JpushDataEntity.getContent());
                                    break;

                                case 227:

                                    break;
                            }


                        }
                    }


                }
            });
        }
    }

    public void resetData(List<PushDataDaoEntity> timMessageList) {
        mTIMMessageList = timMessageList;
    }

    private void QueryConsultDoctorAppMessageByPage(String id) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("Id", id);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetConsultRecord).headers(headMap).params(requestMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ConsultMessageEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ConsultMessageEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {

                    LoadDialog.clear();
                    ConsultMessageEntity consultMessageEntity = entity.getData();
                    if (consultMessageEntity != null) {
                        String Content = consultMessageEntity.getContent();
                        String PatientName = consultMessageEntity.getPatientName();
                        String ApplicantHeadImgUrl = consultMessageEntity.getApplicantHeadImgUrl();
                        String EndTime = consultMessageEntity.getEndTime();
                        String PayTime = consultMessageEntity.getPayTime();
                        String CreateTime = consultMessageEntity.getCreateTime();
                        int RecordId = consultMessageEntity.getId();
                        int Type = consultMessageEntity.getType();
                        int Status = consultMessageEntity.getStatus();
                        String DealWithTime = consultMessageEntity.getDealWithTime();
                        String StartTime = consultMessageEntity.getStartTime();
                        String ApplicantIMId = consultMessageEntity.getApplicantIMId();

                        PatientInfoEntity patientInfoEntity = consultMessageEntity.getPatientInfo();
                        String Age = null;
                        String AreaName = null;
                        int PatientSex = 0;
                        if (patientInfoEntity != null) {
                            Age = patientInfoEntity.getAge();
                            AreaName = patientInfoEntity.getAreaName();
                            PatientSex = patientInfoEntity.getPatientSex();
                        }
                        DoctorPatientRelationEntity DoctorPatientRelationEntity = consultMessageEntity.getDoctorPatientRelation();
                        int IsDrKey = 0;
                        int IsPatientMain = 0;
                        String Disease = null;
                        if (DoctorPatientRelationEntity != null) {
                            IsDrKey = consultMessageEntity.getDoctorPatientRelation().getIsDrKey();
                            IsPatientMain = consultMessageEntity.getDoctorPatientRelation().getIsPatientMain();
                            Disease = consultMessageEntity.getDoctorPatientRelation().getDisease();
                        }
                        int ApplicantId = consultMessageEntity.getApplicantId();

                        String ServiceCode = "1101";
                        int PatientId = consultMessageEntity.getPatientId();
                        AllRecordForDoctorEntity allRecordForDoctorEntity = new AllRecordForDoctorEntity(null, Content, PatientName, ApplicantHeadImgUrl, EndTime, PayTime, CreateTime, RecordId, Type, Status, DealWithTime, StartTime, ApplicantIMId, 0, null, 0, null, null, null, Age, AreaName, PatientSex, IsDrKey, IsPatientMain, Disease, ServiceCode, 0, null, ApplicantId, PatientId, null);
                        Type caonsultJsonType = new TypeToken<PrescriptionMessageEntity<ConsultMessageEntity>>() {
                        }.getType();
                        //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                        PrescriptionMessageEntity<ConsultMessageEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), caonsultJsonType);
                        prescriptionMessageEntity.setPatient(consultMessageEntity);
                        String json = mGson.toJson(prescriptionMessageEntity);
                        Intent consultIntentTalk = new Intent(mActivity, ChatActivity.class);

                        Log.i("zdp", "json=" + json);
                        SpUtils.put(Contants.patientName, allRecordForDoctorEntity.getPatientName());
                        SpUtils.put(Contants.patientID, allRecordForDoctorEntity.getPatientId());
                        consultIntentTalk.putExtra(Contants.FRIEND_NAME, allRecordForDoctorEntity.getPatientName());
                        consultIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, allRecordForDoctorEntity.getApplicantIMId());
                        consultIntentTalk.putExtra("accountId", allRecordForDoctorEntity.getApplicantId());
                        consultIntentTalk.putExtra("consultMessageEntity", allRecordForDoctorEntity);
                        consultIntentTalk.putExtra("action", "pictureConsult");
                        consultIntentTalk.putExtra("prescriptionMessage", json);
                        consultIntentTalk.putExtra("imgPath", allRecordForDoctorEntity.getApplicantHeadImgUrl());
                        mActivity.startActivity(consultIntentTalk);
                    }
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
                    LoadDialog.clear();

                    OnlineRenewalDataEntity onlineRenewalDataEntity = entity.getData();

                    if (onlineRenewalDataEntity != null) {
                        String PatientName = onlineRenewalDataEntity.getPatientName();
                        String ApplicantHeadImgUrl = onlineRenewalDataEntity.getApplicantHeadImgUrl();
                        String EndTime = onlineRenewalDataEntity.getEndTime();
                        String PayTime = onlineRenewalDataEntity.getPayTime();
                        String CreateTime = onlineRenewalDataEntity.getCreateTime();
                        int RecordId = onlineRenewalDataEntity.getId();
                        int Type = onlineRenewalDataEntity.getType();
                        int Status = onlineRenewalDataEntity.getStatus();
                        String DealWithTime = onlineRenewalDataEntity.getDealWithTime();
                        String StartTime = onlineRenewalDataEntity.getStartTime();
                        String ApplicantIMId = onlineRenewalDataEntity.getApplicantIMId();


                        String Diagnoses = onlineRenewalDataEntity.getDiagnoses();
                        PatientInfoEntity patientInfoEntity = onlineRenewalDataEntity.getPatientInfo();
                        String Age = null;
                        String AreaName = null;
                        int PatientSex = 0;
                        if (patientInfoEntity != null) {
                            Age = patientInfoEntity.getAge();
                            AreaName = patientInfoEntity.getAreaName();
                            PatientSex = patientInfoEntity.getPatientSex();
                        }

                        DoctorPatientRelationEntity DoctorPatientRelationEntity = onlineRenewalDataEntity.getDoctorPatientRelation();
                        int IsDrKey = 0;
                        int IsPatientMain = 0;
                        String Disease = null;
                        if (DoctorPatientRelationEntity != null) {
                            IsDrKey = DoctorPatientRelationEntity.getIsDrKey();
                            IsPatientMain = DoctorPatientRelationEntity.getIsPatientMain();
                            Disease = DoctorPatientRelationEntity.getDisease();
                        }
                        int ApplicantId = onlineRenewalDataEntity.getApplicantId();
                        String ServiceCode = "1103";
                        int PatientId = onlineRenewalDataEntity.getPatientId();
                        AllRecordForDoctorEntity allRecordForDoctorEntity = new AllRecordForDoctorEntity(null, null, PatientName, ApplicantHeadImgUrl, EndTime, PayTime, CreateTime, RecordId, Type, Status, DealWithTime, StartTime, ApplicantIMId, 0, Diagnoses, 0, null, null, null, Age, AreaName, PatientSex, IsDrKey, IsPatientMain, Disease, ServiceCode, 0, null, ApplicantId, PatientId, null);
                        Type renewalJsonType = new TypeToken<PrescriptionMessageEntity<OnlineRenewalDataEntity>>() {
                        }.getType();
                        //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                        PrescriptionMessageEntity<OnlineRenewalDataEntity> renewalMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), renewalJsonType);
                        renewalMessageEntity.setPatient(onlineRenewalDataEntity);
                        String renewalJson = mGson.toJson(renewalMessageEntity);

                        SpUtils.put(Contants.patientName, allRecordForDoctorEntity.getPatientName());
                        SpUtils.put(Contants.patientID, allRecordForDoctorEntity.getPatientId());
                        Intent renewalIntentTalk = new Intent(mActivity, ChatActivity.class);
                        renewalIntentTalk.putExtra(Contants.FRIEND_NAME, allRecordForDoctorEntity.getPatientName());
                        renewalIntentTalk.putExtra("onlineRenewalDataEntity", allRecordForDoctorEntity);
                        renewalIntentTalk.putExtra("action", "onlineRenewal");
                        renewalIntentTalk.putExtra("accountId", allRecordForDoctorEntity.getApplicantId());
                        renewalIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, allRecordForDoctorEntity.getApplicantIMId());
                        renewalIntentTalk.putExtra("prescriptionMessage", renewalJson);
                        renewalIntentTalk.putExtra("imgPath", allRecordForDoctorEntity.getApplicantHeadImgUrl());
                        mActivity.startActivity(renewalIntentTalk);
                    }

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
                    LoadDialog.clear();

                    InquiryRecordListDataEntity inquiryRecordListDataEntity = entity.getData();

                    String Content = inquiryRecordListDataEntity.getContent();
                    String PatientName = inquiryRecordListDataEntity.getPatientName();
                    String ApplicantHeadImgUrl = inquiryRecordListDataEntity.getApplicantHeadImgUrl();
                    String EndTime = inquiryRecordListDataEntity.getEndTime();
                    String PayTime = inquiryRecordListDataEntity.getPayTime();
                    String CreateTime = inquiryRecordListDataEntity.getCreateTime();
                    int RecordId = inquiryRecordListDataEntity.getId();
                    int Type = inquiryRecordListDataEntity.getType();
                    int Status = inquiryRecordListDataEntity.getStatus();
                    String DealWithTime = inquiryRecordListDataEntity.getDealWithTime();
                    String StartTime = inquiryRecordListDataEntity.getStartTime();
                    String ApplicantIMId = inquiryRecordListDataEntity.getApplicantIMId();
                    TIMConversation conversation = TIMManager.getInstance().getConversation(
                            TIMConversationType.C2C,
                            ApplicantIMId);
                    long unReadNum = 0;
                    long timeStamp = 0;
                    if (conversation != null) {
                        unReadNum = AppUtils.getUnreadMessageNum(conversation);
                        List<TIMMessage> lastMsgs = conversation.getLastMsgs(1);

                        if (lastMsgs != null && lastMsgs.size() > 0) {
                            TIMMessage msg = lastMsgs.get(0);
                            timeStamp = msg.timestamp();
                        }
                    }


                    String ReExaminationDate = inquiryRecordListDataEntity.getReExaminationDate();
                    String ReExaminationStartTime = inquiryRecordListDataEntity.getReExaminationStartTime();
                    String ReExaminationEndTime = inquiryRecordListDataEntity.getReExaminationEndTime();


                    String Age = null;
                    String AreaName = null;
                    int PatientSex = 0;
                    int IsDrKey = 0;
                    int IsPatientMain = 0;
                    String Disease = null;
                    PatientInfoEntity PatientInfoBean = inquiryRecordListDataEntity.getPatientInfo();
                    DoctorPatientRelationEntity DoctorPatientRelationBean = inquiryRecordListDataEntity.getDoctorPatientRelation();
                    if (PatientInfoBean != null) {
                        Age = PatientInfoBean.getAge();
                        AreaName = PatientInfoBean.getAreaName();
                        PatientSex = PatientInfoBean.getPatientSex();
                    }
                    if (DoctorPatientRelationBean != null) {
                        IsDrKey = DoctorPatientRelationBean.getIsDrKey();
                        IsPatientMain = DoctorPatientRelationBean.getIsPatientMain();
                        Disease = DoctorPatientRelationBean.getDisease();
                    }
                    String ServiceCode = "1102";
                    int ApplicantId = inquiryRecordListDataEntity.getApplicantId();
                    String RecordData = new Gson().toJson(inquiryRecordListDataEntity);
                    int PatientId = inquiryRecordListDataEntity.getPatientId();

                    AllRecordForDoctorEntity allRecordForDoctorEntity = new AllRecordForDoctorEntity(null, Content, PatientName, ApplicantHeadImgUrl, EndTime, PayTime, CreateTime, RecordId, Type, Status, DealWithTime, StartTime, ApplicantIMId, unReadNum, null, 0, ReExaminationDate, ReExaminationStartTime, ReExaminationEndTime, Age, AreaName, PatientSex, IsDrKey, IsPatientMain, Disease, ServiceCode, timeStamp, RecordData, ApplicantId, PatientId, null);
                    Type videoJsonType = new TypeToken<PrescriptionMessageEntity<InquiryRecordListDataEntity>>() {
                    }.getType();
                    //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                    PrescriptionMessageEntity<InquiryRecordListDataEntity> videoMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), videoJsonType);
                    videoMessageEntity.setPatient(inquiryRecordListDataEntity);
                    String videoJson = mGson.toJson(videoMessageEntity);
                    Intent videoIntentTalk = new Intent(mActivity, ChatActivity.class);
                    videoIntentTalk.putExtra(Contants.FRIEND_NAME, inquiryRecordListDataEntity.getPatientName());
                    videoIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, inquiryRecordListDataEntity.getApplicantIMId());
                    videoIntentTalk.putExtra("inquiryRecordListDataEntity", allRecordForDoctorEntity);
                    videoIntentTalk.putExtra("action", "videoInterrogation");
                    videoIntentTalk.putExtra("accountId", PatientInfoBean.getAccountId());
                    videoIntentTalk.putExtra("prescriptionMessage", videoJson);
                    videoIntentTalk.putExtra("imgPath", allRecordForDoctorEntity.getApplicantHeadImgUrl());

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

    private void setServicePushMessageRead(String msgId) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("MsgId", msgId);
        requestMap.put("UserId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        requestMap.put("AppId", "2d77c7dfc65d033c8c3f5f89");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.Read).headers(headMap).params(requestMap).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    LoadDialog.clear();


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

    private void requestAllNewReportPaientMessage(int patientId) {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        Map<String, String> map = new HashMap<>();
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        map.put("PatientId", String.valueOf(patientId));
        map.put("RelationStatus", "-1");
        map.put("IsPatientMain", "-1");
        map.put("IsDrKey", "-1");
        map.put("PageSize", "10000");

        mMyOkhttp.post().url(HttpUrl.QueryDoctorPatientPage).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<PatientListEntity>>() {
            @Override
            public void onSuccess(int statusCode, final ResponseEntity<PatientListEntity> entity) {

                if (entity.getCode() == 0) {
                    PatientListEntity patientListEntity = entity.getData();
                    List<PatientListDataEntity> list = patientListEntity.getReturnData();
                    if (list.size() > 0) {
                        PatientListDataEntity patientListDataEntity = list.get(0);
                        Type renewalJsonType = new TypeToken<PrescriptionMessageEntity<OnlineRenewalDataEntity>>() {
                        }.getType();
                        //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                        PrescriptionMessageEntity<OnlineRenewalDataEntity> renewalMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), renewalJsonType);
                        OnlineRenewalDataEntity onlineRenewalDataEntity = new OnlineRenewalDataEntity();

                        PatientInfoEntity patientInfoEntity = new PatientInfoEntity();
                        patientInfoEntity.setBirthday(patientListDataEntity.getBirthday());
                        patientInfoEntity.setAge(patientListDataEntity.getAge());
                        patientInfoEntity.setPatientName(patientListDataEntity.getPatientName());
                        patientInfoEntity.setPatientSex(patientListDataEntity.getPatientSex());
                        patientInfoEntity.setPatientId(patientListDataEntity.getPatientId());
                        onlineRenewalDataEntity.setPatientInfo(patientInfoEntity);
                        renewalMessageEntity.setPatient(onlineRenewalDataEntity);
                        String json = mGson.toJson(renewalMessageEntity);
                        Intent intentTalk = new Intent(mActivity, ChatActivity.class);
                        SpUtils.put(Contants.patientName, patientInfoEntity.getPatientName());
                        SpUtils.put(Contants.patientID, patientInfoEntity.getPatientId());
                        intentTalk.putExtra(Contants.FRIEND_NAME, patientListDataEntity.getPatientName());
                        intentTalk.putExtra("status", 0);
                        intentTalk.putExtra("prescriptionMessage", json);
                        intentTalk.putExtra("accountId", patientListDataEntity.getAccountId());
                        intentTalk.putExtra("imgPath", patientListDataEntity.getPaPicturePath());
                        intentTalk.putExtra(Contants.FRIEND_IDENTIFIER, patientListDataEntity.getPatientIMId());
                        mActivity.startActivity(intentTalk);
                    } else {
                        Toast.makeText(mActivity, "该患者不存在或者被删除!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mActivity, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                //mEasylayout.setVisibility(View.GONE);
                Log.i("HomeFragment", "111");
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void getImMessage(int patientId, final String name) {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        Map<String, String> map = new HashMap<>();
        map.put("UserId", String.valueOf(patientId));
        map.put("UserType", "1");
        mMyOkhttp.post().url(HttpUrl.loginIm).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<IMMessageEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<IMMessageEntity> entity) {
                if (entity.getCode() == 0) {
                    IMMessageEntity iMMessageEntity = entity.getData();
                    if (iMMessageEntity != null) {
                        // PatientStatusEntity patientStatusEntity = mGson.fromJson(data, PatientStatusEntity.class);
                        String json = SpUtils.getString(Contants.LoginJson);
                        Intent intentTalk = new Intent(mActivity, ChatActivity.class);

                        intentTalk.putExtra(Contants.FRIEND_NAME, name);
                        intentTalk.putExtra("status", 0);
                        intentTalk.putExtra("prescriptionMessage", json);
                        intentTalk.putExtra("accountId", iMMessageEntity.getUserId());
                        intentTalk.putExtra("imgPath", iMMessageEntity.getFaceUrl());
                        intentTalk.putExtra(Contants.FRIEND_IDENTIFIER, iMMessageEntity.getIdentifier());
                        mActivity.startActivity(intentTalk);
                    }
                } else {
                    Toast.makeText(mActivity, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("zdp", "statusCode=" + statusCode + ",errorMsg=" + errorMsg);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });

    }


    private void getIMRelationRecord(final TIMGroupDetailInfo timGroupDetailInfo) {

        Map<String, String> map = new HashMap<>();
        map.put("IMGroupId", timGroupDetailInfo.getGroupId());

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(HttpUrl.QueryMDTBySubjectBuyIMGroupId).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTDetailEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTDetailEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                LoadDialog.clear();
                if (entity.getCode() == 0) {
                    Intent intent = new Intent(mActivity, GroupChatActivity.class);
                    intent.putExtra("action", "MDT");
                    intent.putExtra("MDTDetailEntity", entity.getData());
                    MyApplication.mMDTDetailEntity = entity.getData();
                    intent.putExtra(Contants.FRIEND_NAME, timGroupDetailInfo.getGroupName());
                    intent.putExtra(Contants.FRIEND_IDENTIFIER, timGroupDetailInfo.getGroupId() + "");
                    mActivity.startActivity(intent);

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


    public void showDialog(String content) {
        AlertDialog dialog = new AlertDialog.Builder(mActivity, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle("")
                .setMessage(content)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();

    }
}
