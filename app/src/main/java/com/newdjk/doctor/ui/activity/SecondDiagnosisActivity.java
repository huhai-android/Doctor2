package com.newdjk.doctor.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.tools.MainConstant;
import com.newdjk.doctor.ui.adapter.SecondDiagnosisAdapter;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.MedicalRecordByIdEntity;
import com.newdjk.doctor.ui.entity.MessageEvent;
import com.newdjk.doctor.ui.entity.RefeshTaskListEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.ui.entity.UpdateTreatMessageEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.ButtonView;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.MedicalView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondDiagnosisActivity extends BasicActivity {


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
    @BindView(R.id.patient_name)
    TextView patientName;
    @BindView(R.id.patient_report)
    TextView patientReport;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.age)
    TextView age;
    @BindView(R.id.medical_record_date)
    TextView medicalRecordDate;
    @BindView(R.id.illness_description)
    TextView illnessDescription;
    @BindView(R.id.recyleview)
    RecyclerView recyleview;
    @BindView(R.id.reject_text)
    ButtonView rejectText;
    @BindView(R.id.reject)
    FrameLayout reject;
    @BindView(R.id.accept_text)
    ButtonView acceptText;
    @BindView(R.id.accept)
    FrameLayout accept;
    @BindView(R.id.operate_layout)
    LinearLayout operateLayout;
    private int mMedicalRecordId;
    private List<MedicalRecordByIdEntity.MainProblemsBean> mList;
    private SecondDiagnosisAdapter mMyDiagnoseAdapter;
    private MedicalView mMedicalView;
    private int type;

    @Override
    protected int initViewResId() {
        return R.layout.second_diagnosis;
    }

    @Override
    protected void initView() {
        MyApplication.mActivities.add(this);
        mMedicalRecordId = getIntent().getIntExtra("MedicalRecordId", 0);
        type = getIntent().getIntExtra("type", 0);
        mMedicalView = new MedicalView(this, mMedicalRecordId);
        mMedicalView.show();
        initBackTitle(getString(R.string.my_diagnose));
        mList = new ArrayList<>();
        mMyDiagnoseAdapter = new SecondDiagnosisAdapter(mList);
        recyleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyleview.setAdapter(mMyDiagnoseAdapter);
        recyleview.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        if (type==1){

            operateLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {
        rejectText.setOnClickListener(this);
        acceptText.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        GetMedicalRecordById();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.reject_text:
                rejectOpration();
                break;
            case R.id.accept_text:
                doctorOrderTaking();
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void GetMedicalRecordById() {
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        String url = HttpUrl.GetMedicalRecordById + "?MedicalRecordId=" + mMedicalRecordId;
        // String url = "http://172.18.30.4/NetHospSecondDiagnosisAPI/MedicalService/QueryDoctorDiseases?DrId="+SpUtils.getInt(Contants.Id,0);
        mMyOkhttp.get().url(url).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MedicalRecordByIdEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MedicalRecordByIdEntity> entity) {
                //   mMyDiagnoseAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    mList.clear();
                    final MedicalRecordByIdEntity medicalRecordByIdEntity = entity.getData();
                    mList.addAll(medicalRecordByIdEntity.getMainProblems());
                    mMyDiagnoseAdapter.setNewData(mList);
                    SecondDiagnosisReportActivity.activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String patientName1 = medicalRecordByIdEntity.getPatientName();
                            String age1 = medicalRecordByIdEntity.getAge();
                            String sex1 = medicalRecordByIdEntity.getSex();
                            String medicalRecordDate1 = medicalRecordByIdEntity.getMedicalRecordDate();
                            if (patientName1 != null) {
                                patientName.setText("姓名：" + patientName1);
                            } else {
                                patientName.setText("姓名：");
                            }
                            if (age != null) {
                                age.setText("年龄：" + age1);
                            } else {
                                age.setText("年龄：");
                            }
                            if (sex != null) {
                                sex.setText("性别：" + sex1);
                            } else {
                                sex.setText("性别：");
                            }
                            String description = medicalRecordByIdEntity.getDescription();
                            illnessDescription.setText(description == null ? "" : description);
                            medicalRecordDate.setText(medicalRecordDate1 == null ? "" : medicalRecordDate1);
                        }

                    });
                    Log.i("SymptomsSelectActivity", "entity=" + entity.getData().toString());

                } else {
                    toast(entity.getMessage() + "aaa");
                }
                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("HomeFragment", "2222");
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void doctorOrderTaking() {
        loading(true);
        Map<String, String> map = new HashMap<>();
        map.put("MedicalRecordId", String.valueOf(mMedicalRecordId));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));

        // String url = "http://172.18.30.4/NetHospSecondDiagnosisAPI/MedicalService/QueryDoctorDiseases?DrId="+SpUtils.getInt(Contants.Id,0);
        mMyOkhttp.post().url(HttpUrl.DoctorOrderTaking).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                //   mMyDiagnoseAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    boolean result = (boolean) entity.getData();
                    if (result) {
                        toast("接单成功！");
                        EventBus.getDefault().post(new UpdatePushView(6));
                        EventBus.getDefault().post(new RefeshTaskListEntity(true));

                        acceptOpration();
                    } else {
                        toast("接单失败！");
                    }

                } else {
                    toast(entity.getMessage() + "aaa");
                }
                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("HomeFragment", "2222");
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void doctorOrderCancel(String cancellation) {
        loading(true);
        Map<String, String> map = new HashMap<>();
        map.put("MedicalRecordId", String.valueOf(mMedicalRecordId));
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("DrName", SpUtils.getString(Contants.Name));
        map.put("Cancellation", cancellation);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));

        // String url = "http://172.18.30.4/NetHospSecondDiagnosisAPI/MedicalService/QueryDoctorDiseases?DrId="+SpUtils.getInt(Contants.Id,0);
        mMyOkhttp.post().url(HttpUrl.DoctorOrderCancel).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                //   mMyDiagnoseAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    boolean result = (boolean) entity.getData();
                    if (result) {
                        toast("退单成功！");
                        EventBus.getDefault().post(new UpdatePushView(6));
                        EventBus.getDefault().post(new UpdateTreatMessageEntity("reject"));
                        EventBus.getDefault().post(new RefeshTaskListEntity(true));
                        finish();
                    } else {
                        toast("退单失败！");
                    }

                } else {
                    toast(entity.getMessage() + "aaa");
                }
                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("HomeFragment", "2222");
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void rejectOpration() {
        final Dialog dialog = new Dialog(this, R.style.ActionSheetDialogStyle);//dialog样式
        View view = View.inflate(this, R.layout.reject_diagnose, null);
        dialog.setContentView(view);
        TextView sure = view.findViewById(R.id.sure);
        TextView cancel = view.findViewById(R.id.cancel);
        final EditText rejectReson = view.findViewById(R.id.remark);
        rejectReson.setHint("请输入退单理由");
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rejection = rejectReson.getText().toString();
                if (!rejection.equals("")) {
                    doctorOrderCancel(rejection);
                    dialog.dismiss();
                } else {
                    toast("退单理由不能为空！");
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void acceptOpration() {
        final Dialog dialog = new Dialog(this, R.style.ActionSheetDialogStyle);//dialog样式
        View view = View.inflate(this, R.layout.reject_diagnose, null);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        TextView sure = view.findViewById(R.id.sure);
        TextView title = view.findViewById(R.id.title);
        TextView cancel = view.findViewById(R.id.cancel);
        title.setText("是否需要补充其它病情材料");
        sure.setText("需要");
        cancel.setText("不需要");
        final EditText rejectReson = view.findViewById(R.id.remark);
        rejectReson.setVisibility(View.GONE);
        EventBus.getDefault().post(new UpdateTreatMessageEntity("accept"));
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent systemSettingIntent2 = new Intent(SecondDiagnosisActivity.this, AddDocumentActivity.class);
                systemSettingIntent2.putExtra("MedicalRecordId", mMedicalRecordId);
                startActivity(systemSettingIntent2);
                dialog.dismiss();
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondDiagnosisActivity.this, SecondDiagnosisQuestionActivity.class);
                intent.putExtra("MedicalRecordId", mMedicalRecordId);
                startActivity(intent);
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mMedicalView.destory();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(MessageEvent event) {
        switch (event.getType()) {
            case MainConstant.UPDATE_MEDICAL:
                GetMedicalRecordById();

                break;
        }

    }
}
