package com.newdjk.doctor.ui.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.SecondDiagnosisAdapter;
import com.newdjk.doctor.ui.entity.MedicalRecordByIdEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class SecondDiagnosisUntreatActivity extends BasicActivity {


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
    @BindView(R.id.foot_root)
    RelativeLayout footRoot;
    @BindView(R.id.diagnose_description)
    TextView diagnoseDescription;
    @BindView(R.id.recyleview)
    RecyclerView recyleview;
    private int mMedicalRecordId;
    private String mMedicalReportPath;
    private SecondDiagnosisAdapter mMyDiagnoseAdapter;
    private List<MedicalRecordByIdEntity.MainProblemsBean> mList=new ArrayList<>();
    @Override
    protected int initViewResId() {
        return R.layout.second_diagnosis_untreat;
    }

    @Override
    protected void initView() {

        initBackTitle("退单");
        mMedicalRecordId = getIntent().getIntExtra("MedicalRecordId", 0);
        mMyDiagnoseAdapter = new SecondDiagnosisAdapter(mList);
        recyleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyleview.setAdapter(mMyDiagnoseAdapter);
        recyleview.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        GetMedicalRecordById();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private void GetMedicalRecordById() {
        loading(true, "正在加载");
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        String url = HttpUrl.GetMedicalRecordById + "?MedicalRecordId=" + mMedicalRecordId;
        // String url = "http://172.18.30.4/NetHospSecondDiagnosisAPI/MedicalService/QueryDoctorDiseases?DrId="+SpUtils.getInt(Contants.Id,0);
        mMyOkhttp.get().url(url).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MedicalRecordByIdEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MedicalRecordByIdEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    MedicalRecordByIdEntity medicalRecordByIdEntity = entity.getData();
                    if (medicalRecordByIdEntity != null) {
                        illnessDescription.setText((medicalRecordByIdEntity.getCancellation() == null ? "" : medicalRecordByIdEntity.getCancellation()) + "");
                        diagnoseDescription.setText((medicalRecordByIdEntity.getDescription() == null ? "" : medicalRecordByIdEntity.getDescription()) + "");
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
                        medicalRecordDate.setText(medicalRecordDate1 == null ? "" : medicalRecordDate1);
                        mList.clear();
                        final MedicalRecordByIdEntity medicalRecordByIdEntity2 = entity.getData();
                        mList.addAll(medicalRecordByIdEntity2.getMainProblems());
                        mMyDiagnoseAdapter.setNewData(mList);

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


}
