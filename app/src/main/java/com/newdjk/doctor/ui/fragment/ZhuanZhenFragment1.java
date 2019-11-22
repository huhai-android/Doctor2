package com.newdjk.doctor.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.ArchivesActivity;
import com.newdjk.doctor.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.doctor.ui.entity.PatientInfoEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.SignReportSuccess;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.ui.entity.ZhuanZhenPatirntEntity;
import com.newdjk.doctor.ui.entity.ZhuanzhenSuccess;
import com.newdjk.doctor.utils.HeadUitl;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.ZhuanZhenDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import lib_zxing.activity.CaptureActivity;
import lib_zxing.activity.CodeUtils;
import pub.devrel.easypermissions.EasyPermissions;

public class ZhuanZhenFragment1 extends BaseZhuanZhenFragment {

    private static final String TAG = "WaitForCheck";
    /**
     * 请求CAMERA权限码
     */
    public static final int REQUEST_CAMERA_PERM = 101;
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    private ZhuanZhenDialog mDialog;

    @Override
    protected void getQueryDocReferralRecordPage(final int index, String s) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdatePushView userEvent) {
        int action = userEvent.action;
        switch (action) {
            case 5:
                getQueryDocReferralRecordPage(index, "0");
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(SignReportSuccess signReportSuccess) {
        if (signReportSuccess.signResule) {
            //审核通过
            // getConsultRecordList("0");
            //当判定报告成功之后，需要将数据发送到已经判定界面
            dataList.remove(clickposition);
            mOnlineRenewalDataAdapter.notifyItemRemoved(clickposition);
            returnDataBean.setPass(true);
            EventBus.getDefault().post(returnDataBean);
            if (onlineRenewalMessageEntity != null) {
                total = total - 1;
                listener.onWaitForcheckchange(total);
            }

        } else {
            //审核不通过
            dataList.remove(clickposition);
            mOnlineRenewalDataAdapter.notifyItemRemoved(clickposition);
            returnDataBean.setPass(false);
            EventBus.getDefault().post(returnDataBean);
            if (onlineRenewalMessageEntity != null) {
                total = total - 1;
                listener.onWaitForcheckchange(total);
            }

        }

    }

    //接诊

    @Override
    protected void refreshView() {
        reScanContainer.setVisibility(View.VISIBLE);

    }

    @Override
    protected void refuseZhuanzhen(String referralRecordId, int position) {

    }

    @Override
    public void startScanJiezhen() {
        super.startScanJiezhen();
        cameraTask();
    }

    public void cameraTask() {
        if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            onClick();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "需要获取拍照权限", REQUEST_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    /**
     * 按钮点击事件处理逻辑
     *
     * @param
     */
    private void onClick() {
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    if (!TextUtils.isEmpty(result)) {
                        Log.d("onActivityResult", result);
                        int accountId = MyApplication.mDoctorInfoByIdEntity.getDrId();
                        Map<String, String> params = new HashMap<>();
                        params.put("ReferralRecordId", result);
                        params.put("DrId", MyApplication.mDoctorInfoByIdEntity.getDrId() + "");
                        //  params.put("DrId", "68");
                        mMyOkhttp.post().url(HttpUrl.ScanDoctorQRCode).params(params).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ZhuanZhenPatirntEntity>>() {
                            @Override
                            public void onSuccess(int statusCode, ResponseEntity<ZhuanZhenPatirntEntity> entituy) {
                                if (entituy.getCode() == 0) {
                                    try {
                                        boolean iszhuanzhen = entituy.getData().isIsSuccess();
                                        if (iszhuanzhen) {
                                           toast("接诊成功");

                                            EventBus.getDefault().post(new ZhuanzhenSuccess(true));
                                            showZhuanZhenDialog(entituy.getData());
                                        }
                                    } catch (Exception e) {
                                        toast("转诊失败");
                                    }

                                } else {
                                    toast(entituy.getMessage() + "");
                                }

                            }

                            @Override
                            public void onFailures(int statusCode, String errorMsg) {
                                Log.d("errMsg", errorMsg);
                                toast(errorMsg + "");
                            }
                        });

                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    private void showZhuanZhenDialog(final ZhuanZhenPatirntEntity data) {

        mDialog = new ZhuanZhenDialog(getContext());
        mDialog.show("转诊记录", data, new ZhuanZhenDialog.DialogListener() {
            @Override
            public void cancel() {


            }

            @Override
            public void confirm() {
                toPatientDesc(data);
            }
        });
    }

    private void toPatientDesc(ZhuanZhenPatirntEntity entityResponseEntity) {

        PatientInfoEntity PatientInfo = new PatientInfoEntity();

        //   PatientInfo.setBirthday(mDataList.get(position).getBirthday());
        String paName = entityResponseEntity.getPatientName();

        PatientInfo.setPatientName(paName.substring(2, paName.length()));
        PatientInfo.setPatientId(entityResponseEntity.getPatientId());
        PatientInfo.setPatientSex(entityResponseEntity.getPatientSex());
        PatientInfo.setAge(String.valueOf(entityResponseEntity.getAge()));
//            DoctorPatientRelationEntity DoctorPatientRelation = new DoctorPatientRelationEntity();
//            DoctorPatientRelation.setDrPatientId(entityResponseEntity.getDrPatientId());


        Type jsonType = new TypeToken<PrescriptionMessageEntity<InquiryRecordListDataEntity>>() {
        }.getType();
        InquiryRecordListDataEntity InquiryRecordListDataEntity = new InquiryRecordListDataEntity();
        //InquiryRecordListDataEntity.setDoctorPatientRelation(DoctorPatientRelation);
        InquiryRecordListDataEntity.setPatientInfo(PatientInfo);
        PrescriptionMessageEntity<InquiryRecordListDataEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), jsonType);
        prescriptionMessageEntity.setPatient(InquiryRecordListDataEntity);
        String json = mGson.toJson(prescriptionMessageEntity);
        Intent ArchivesIntent = new Intent(mActivity, ArchivesActivity.class);
        ArchivesIntent.putExtra("action", "patientList");
        ArchivesIntent.putExtra("prescriptionMessage", json);
        mActivity.startActivity(ArchivesIntent);
    }
}
