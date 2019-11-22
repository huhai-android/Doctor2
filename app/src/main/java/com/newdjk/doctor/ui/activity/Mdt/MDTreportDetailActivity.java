package com.newdjk.doctor.ui.activity.Mdt;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.ExpertAdviceListAdapter;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.GlideUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.CircleImageView;
import com.newdjk.doctor.views.LoadDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MDTreportDetailActivity extends BasicActivity {


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
    @BindView(R.id.civImg)
    CircleImageView civImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_patient_history)
    TextView tvPatientHistory;
    @BindView(R.id.tv_disease_desc)
    TextView tvDiseaseDesc;
    @BindView(R.id.tv_expert_desc)
    TextView tvExpertDesc;
    @BindView(R.id.recyleview)
    RecyclerView recyleview;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.im_record)
    LinearLayout imRecord;
    @BindView(R.id.rv_input_advice)
    RelativeLayout rvInputAdvice;
    @BindView(R.id.btn_send_report)
    AppCompatButton btnSendReport;
    @BindView(R.id.lv_parent)
    LinearLayout lvParent;
    private String mSubjectBuyRecordId;
    private MDTDetailEntity mMDTDetailEntity;
    private int type; //已完成
    private ExpertAdviceListAdapter mAdviceListAdapter;
    private List<MDTDetailEntity.MDTReportDoctorsBean> list = new ArrayList<>();

    @Override
    protected int initViewResId() {
        return R.layout.activity_input_mdtreport_lianhe_last;
    }

    @Override
    protected void initView() {
        initBackTitle("多学科会诊报告");
        initRecyleview();
        mSubjectBuyRecordId = getIntent().getStringExtra(Contants.SubjectBuyRecordId);
        type = getIntent().getIntExtra("type", 0);
        btnSendReport.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
    }

    private void initRecyleview() {

        recyleview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mAdviceListAdapter = new ExpertAdviceListAdapter(list, false,this);
        recyleview.setAdapter(mAdviceListAdapter);
        recyleview.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false));
    }
    @Override
    protected void initData() {
        GetreportDetail();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void GetreportDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("SubjectBuyRecordId", mSubjectBuyRecordId);
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(HttpUrl.QueryDrSubjectBuyRecordDetail).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTDetailEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTDetailEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    if (entity.getData() != null) {
                        mMDTDetailEntity=entity.getData();
                        tvName.setText(mMDTDetailEntity.getPatientName() + "");
                        tvAge.setText(mMDTDetailEntity.getPatientAge());
                        if (mMDTDetailEntity.getPatientSex() == 1) {
                            tvSex.setText("男");
                        } else if (mMDTDetailEntity.getPatientSex() == 2) {
                            tvSex.setText("女");
                        } else {
                            tvSex.setText("未知");
                        }

                        list.clear();
                        list.addAll(mMDTDetailEntity.getMDTReportDoctors());
                        mAdviceListAdapter.notifyDataSetChanged();




                        GlideUtils.loadPatientImage(mMDTDetailEntity.getPatPicturePath(),civImg);

                        tvDiseaseDesc.setText(mMDTDetailEntity.getDiseases() + "");
                        tvExpertDesc.setText(mMDTDetailEntity.getAdvice());

                        if (type == 2) { //已完成的报告
                            recyleview.setVisibility(View.VISIBLE);
//                            if (mMDTDetailEntity.getSubjectType() == 1) {//诊疗类型(1-咨询，2-分诊，3-会诊)
//                                recyleview.setVisibility(View.GONE);
//
//                            } else if (mMDTDetailEntity.getSubjectType() == 2) {
//                                recyleview.setVisibility(View.GONE);
//
//                            } else if (mMDTDetailEntity.getSubjectType() == 3) {
//                                recyleview.setVisibility(View.VISIBLE);
//
//                            }
                            recyleview.setVisibility(View.VISIBLE);
                        } else {
                            if (mMDTDetailEntity.getSubjectType() == 1) {//诊疗类型(1-咨询，2-分诊，3-会诊)
                                rvInputAdvice.setVisibility(View.GONE);


                            } else if (mMDTDetailEntity.getSubjectType() == 2) {
                                tvExpertDesc.setVisibility(View.GONE);


                            } else if (mMDTDetailEntity.getSubjectType() == 3) {
                                tvExpertDesc.setVisibility(View.GONE);


                            }
                        }

                        String diseaseName=mMDTDetailEntity.getDiseases();
                        String diseaseTime=mMDTDetailEntity.getDiseasesTimeText();
                        String seeDoctorText=mMDTDetailEntity.getSeeDoctorText();
                        String hospital=mMDTDetailEntity.getSeeDoctor();
                        String department=mMDTDetailEntity.getSeeDepartment();


                        if (TextUtils.isEmpty(diseaseTime)){
                            diseaseTime="";
                        }else {
                            diseaseTime=diseaseTime+",";
                        }

                        if (TextUtils.isEmpty(diseaseName)){
                            diseaseName="";
                        }else {
                            diseaseName=diseaseName+",";
                        }

                        if (TextUtils.isEmpty(seeDoctorText)){
                            seeDoctorText="";
                        }else {
                            seeDoctorText=seeDoctorText+",";
                        }

                        if (TextUtils.isEmpty(hospital)){
                            hospital="";
                        }else {
                            hospital=hospital+",";
                        }
                        if (TextUtils.isEmpty(department)){
                            department="";
                        }
                        //组合文字
                        tvPatientHistory.setText(diseaseName+diseaseTime+seeDoctorText+hospital+department);

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


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
