package com.newdjk.doctor.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.artifex.mupdf.viewer.DocumentActivity;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.SignFinshEntity;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.ui.entity.UpdateTreatMessageEntity;
import com.newdjk.doctor.ui.entity.YWXListenerEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.DownloadCertDialog;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.RememberPasswordDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.org.bjca.sdk.core.kit.BJCASDK;
import cn.org.bjca.sdk.core.kit.YWXListener;
import cn.org.bjca.sdk.core.values.ConstantParams;


/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   SecondDiagnosisQuestionActivity
 *  @创建者:   huhai
 *  @创建时间:  2018/12/17 15:34
 *  @描述：
 */
public class SignDiagnosisReportActivity extends DocumentActivity {
    private static final String TAG = "ReportActivity";
    private Gson mGson;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGson = new Gson();
        if (tvRigth != null) {
            tvRigth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent systemSettingIntent = new Intent(SignDiagnosisReportActivity.this, SecondDiagnosisReportActivity.class);
                    systemSettingIntent.putExtra("MedicalRecordId", mMedicalRecordId);
                    systemSettingIntent.putExtra("type", 1);
                    startActivity(systemSettingIntent);
                }
            });
        }
        if (tvSignReport != null) {
            tvSignReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                ToastUtil.setToast("签发报告");


                    sendReport();
                }
            });
        }

    }

    private void sendReport() {
        // String url = "http://172.18.30.4/NetHospSecondDiagnosisAPI/MedicalService/QueryDoctorDiseases?DrId="+SpUtils.getInt(Contants.Id,0);
        LoadDialog.show(SignDiagnosisReportActivity.this, "发送中");
        Map<String, String> map = new HashMap<>();
        map.put("MedicalRecordId", String.valueOf(mMedicalRecordId));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        MyApplication.getInstance().getMyOkHttp().post().url(HttpUrl.sendRepoetTosign).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, final ResponseEntity entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                LoadDialog.clear();
                if (entity.getCode() == 0) {
                    boolean isExists = BJCASDK.getInstance().existsCert(SignDiagnosisReportActivity.this);
                    boolean ExistStamp = BJCASDK.getInstance().existsStamp(SignDiagnosisReportActivity.this);
                    /// BJCASDK.getInstance().clearCert(ChatActivity.this);
                    // BJCASDK.getInstance().startUrl(ChatActivity.this, Contants.clientId, 3);
                    // BJCASDK.getInstance().startUrl(ChatActivity.this, Contants.clientId, 1);
                    if (!isExists) {
                        DownloadCertDialog mDialog = new DownloadCertDialog(SignDiagnosisReportActivity.this);
                        mDialog.show("温馨提示", "", new DownloadCertDialog.DialogListener() {
                            @Override
                            public void confirm() {
                                BJCASDK.getInstance().certDown(SignDiagnosisReportActivity.this, Contants.clientId, SpUtils.getString(Contants.userName), new YWXListener() {
                                    @Override
                                    public void callback(String s) {
                                        YWXListenerEntity yWXListenerEntity = mGson.fromJson(s, YWXListenerEntity.class);
                                        String status = yWXListenerEntity.getStatus();
                                        String message = yWXListenerEntity.getMessage();
                                        if (status != null && status.equals("0")) {
                                            boolean ExistStamp1 = BJCASDK.getInstance().existsStamp(SignDiagnosisReportActivity.this);
                                            if (!ExistStamp1) {
                                                BJCASDK.getInstance().drawStamp(SignDiagnosisReportActivity.this, Contants.clientId, new YWXListener() {
                                                    @Override
                                                    public void callback(String msg) {
                                                        YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                                        String status = yWXListenerEntity.getStatus();
                                                        String message = yWXListenerEntity.getMessage();
                                                        if (status != null && status.equals("0")) {
                                                            String signId = entity.getData().toString();
                                                            List<String> uniqueIds = new ArrayList<>();
                                                            uniqueIds.add(signId);
                                                            boolean isPinExempt = BJCASDK.getInstance().isPinExempt(SignDiagnosisReportActivity.this);
                                                            showRememberDialog(SignDiagnosisReportActivity.this, uniqueIds, isPinExempt);
                                                        } else {
                                                            //  ToastUtil.setToast(message);
                                                            ToastUtil.setToast("设置签章失败，请重试！+("+status+")");
                                                        }
                                                    }
                                                });
                                            } else {
                                                String signId = entity.getData().toString();
                                                List<String> uniqueIds = new ArrayList<>();
                                                uniqueIds.add(signId);
                                                boolean isPinExempt = BJCASDK.getInstance().isPinExempt(SignDiagnosisReportActivity.this);
                                                showRememberDialog(SignDiagnosisReportActivity.this, uniqueIds, isPinExempt);
                                            }
                                        } else {
                                            Toast.makeText(SignDiagnosisReportActivity.this, "下载证书失败，请重试!(" + status+")", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                });
                            }
                        });


                    } else {
                        //   BJCASDK.getInstance().clearCert(ChatActivity.this);
                        Log.i("zdp", "证书已下载");
                        if (!ExistStamp) {
                            BJCASDK.getInstance().drawStamp(SignDiagnosisReportActivity.this, Contants.clientId, new YWXListener() {
                                @Override
                                public void callback(String msg) {
                                    YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                    String status = yWXListenerEntity.getStatus();
                                    String message = yWXListenerEntity.getMessage();
                                    if (status != null && status.equals("0")) {
                                        String signId = entity.getData().toString();
                                        List<String> uniqueIds = new ArrayList<>();
                                        uniqueIds.add(signId);
                                        boolean isPinExempt = BJCASDK.getInstance().isPinExempt(SignDiagnosisReportActivity.this);
                                        showRememberDialog(SignDiagnosisReportActivity.this, uniqueIds, isPinExempt);
                                    } else {
                                        //  ToastUtil.setToast(message);
                                        ToastUtil.setToast("设置签章失败，请重试！+("+status+")");
                                    }
                                }
                            });
                        } else {
                            String signId = entity.getData().toString();
                            List<String> uniqueIds = new ArrayList<>();
                            uniqueIds.add(signId);
                            boolean isPinExempt = BJCASDK.getInstance().isPinExempt(SignDiagnosisReportActivity.this);
                            showRememberDialog(SignDiagnosisReportActivity.this, uniqueIds, isPinExempt);
                        }
                    }

                } else {
                    ToastUtil.setToast(entity.getMessage() + "");
                    LoadDialog.clear();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("HomeFragment", "2222");
                CommonMethod.requestError(statusCode, errorMsg);
                LoadDialog.clear();
            }
        });

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
                    if (keeDay == 0) {
                        BJCASDK.getInstance().sign(SignDiagnosisReportActivity.this, Contants.clientId, uniqueIds, new YWXListener() {
                            @Override
                            public void callback(String result) {
                                if (result != null) {
                                    SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                                    String status = signFinshEntity.getStatus();
                                    if (status.equals("0")) {
                                        tellServiceSignSuccess();
                                    } else {
                                        ToastUtil.setToast(signFinshEntity.getMessage());
                                    }
                                }
                            }
                        });
                    } else {
                        BJCASDK.getInstance().keepPin(context, Contants.clientId, keeDay, new YWXListener() {
                            @Override
                            public void callback(String msg) {
                                YWXListenerEntity yWXListenerEntity = new Gson().fromJson(msg, YWXListenerEntity.class);
                                String status = yWXListenerEntity.getStatus();
                                String message = yWXListenerEntity.getMessage();
                                if (status.equals("0")) {
                                    BJCASDK.getInstance().sign(SignDiagnosisReportActivity.this, Contants.clientId, uniqueIds, new YWXListener() {
                                        @Override
                                        public void callback(String result) {
                                            if (result != null) {
                                                SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                                                String status = signFinshEntity.getStatus();
                                                if (status.equals("0")) {
                                                    tellServiceSignSuccess();
                                                } else {
                                                    ToastUtil.setToast(signFinshEntity.getMessage());
                                                }
                                            }
                                        }
                                    });

                                } else {
                                    // Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    ToastUtil.setToast("记住密码失效，请重试！+("+status+")");

                                }

                            }
                        });
                    }
                }
            });
        } else {
            BJCASDK.getInstance().sign(SignDiagnosisReportActivity.this, Contants.clientId, uniqueIds, new YWXListener() {
                @Override
                public void callback(String result) {
                    if (result != null) {
                        SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                        String status = signFinshEntity.getStatus();
                        if (status.equals("0")) {
                            tellServiceSignSuccess();
                        } else {
                            ToastUtil.setToast(signFinshEntity.getMessage());
                        }
                    }
                }
            });
        }
    }

    //告诉后台签名成功
    private void tellServiceSignSuccess() {
        // String url = "http://172.18.30.4/NetHospSecondDiagnosisAPI/MedicalService/QueryDoctorDiseases?DrId="+SpUtils.getInt(Contants.Id,0);
        Map<String, String> map = new HashMap<>();
        map.put("MedicalRecordId", String.valueOf(mMedicalRecordId));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        MyApplication.getInstance().getMyOkHttp().post().url(HttpUrl.signSuccess).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    ToastUtil.setToast("发送报告成功");
                    EventBus.getDefault().post(new UpdateTreatMessageEntity("end"));
                    EventBus.getDefault().post(new UpdateTreatMessageEntity("accept"));
                    EventBus.getDefault().post(new UpdatePushView(7));
                    MyApplication.exit();
                    finish();
                    Log.d(TAG, "已经高度后台了");

                } else {
                    ToastUtil.setToast(entity.getMessage() + "");
                }
                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("HomeFragment", "2222");
                CommonMethod.requestError(statusCode, errorMsg);
                LoadDialog.clear();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ConstantParams.ACTIVITY_SIGN_DATA:
                    String result = null;
                    try {
                        result = data.getStringExtra(ConstantParams.KEY_SIGN_BACK);
                        if (result != null) {
                            SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                            String status = signFinshEntity.getStatus();
                            if (status.equals("0")) {
                                tellServiceSignSuccess();
                            } else {
                                LoadDialog.clear();
                                ToastUtil.setToast(signFinshEntity.getMessage());
                            }
                        }
                        Log.i("Prescription", "result=" + result);
                    } catch (Exception e) {
                        LoadDialog.clear();
                        e.printStackTrace();
                    }

                    break;
            }
        }
    }
}
