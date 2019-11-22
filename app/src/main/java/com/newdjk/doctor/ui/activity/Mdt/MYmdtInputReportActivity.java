package com.newdjk.doctor.ui.activity.Mdt;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.GlideUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.CircleImageView;
import com.newdjk.doctor.views.LoadDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   MDTActivity
 *  @创建者:   huhai
 *  @创建时间:  2019/8/29 10:26
 *  @描述：
 */
public class MYmdtInputReportActivity extends BasicActivity {


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
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_content)
    TextView etContent;
    @BindView(R.id.lv_parent)
    LinearLayout lvParent;
    @BindView(R.id.tv_title_desc)
    TextView tvTitleDesc;
    private String TAG = "MDTActivity";
    public Gson mGson = new Gson();

    private String DrId;
    private String SubjectBuyRecordId;
    private MDTDetailEntity mAdviceEntity;

    @Override
    protected int initViewResId() {
        return R.layout.activity_my_input_mdtreport;
    }

    @Override
    protected void initView() {
        MyApplication.mActivities.add(this);

        DrId = getIntent().getStringExtra("DrId");
        SubjectBuyRecordId = getIntent().getStringExtra("SubjectBuyRecordId");

        //如果是自己发送的，可以编辑
        if (Integer.parseInt(DrId) == MyApplication.mDoctorInfoByIdEntity.getDrId()) {
            initTitle("多学科会诊专家意见").setRightText("编辑").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            }).setRightOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAdviceEntity != null) {
                        Intent intent = new Intent(MYmdtInputReportActivity.this, MDTInputReportActivity.class);
                        intent.putExtra("MDTDetailEntity", mAdviceEntity);
                        startActivity(intent);
                    }

                }
            });
            tvTitleDesc.setVisibility(View.GONE);
            //不能编辑
        } else {
            initTitle("多学科会诊专家意见").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        tvRight.setTextColor(ContextCompat.getColor(this, R.color.theme));
        tvRight.setVisibility(View.GONE);

        QueryManySubjectDoctor();
//        tvName.setText(mMDTDetailEntity.getPatientName() + "");
//        tvAge.setText(mMDTDetailEntity.getPatientAge());
//        if (mMDTDetailEntity.getPatientSex() == 1) {
//            tvSex.setText("男");
//        } else if (mMDTDetailEntity.getPatientSex() == 2) {
//            tvSex.setText("女");
//        } else {
//            tvSex.setText("未知");
//        }
//        Glide.with(MyApplication.getContext())
//                .load(mMDTDetailEntity.getPatPicturePath())
//                .into(civImg);
//
//        tvDiseaseDesc.setText(mMDTDetailEntity.getDescription() + "");
//
//        String diseaseName = mMDTDetailEntity.getDiseases();
//        String diseaseTime = mMDTDetailEntity.getDiseasesTimeText();
//        String seeDoctorText = mMDTDetailEntity.getSeeDoctorText();
//        String hospital = mMDTDetailEntity.getSeeDoctor();
//        String department = mMDTDetailEntity.getSeeDepartment();
//        if (TextUtils.isEmpty(hospital)) {
//            hospital = "";
//        } else {
//            hospital = hospital + ",";
//        }
//        if (TextUtils.isEmpty(department)) {
//            department = "";
//        } else {
//            department = department + ",";
//        }
//        tvPatientHistory.setText(diseaseName + ",患病" + diseaseTime + "," + seeDoctorText + "," + hospital + "," + department);

    }

    @Override
    protected void initListener() {
        topRight.setOnClickListener(this);
    }


    @Override
    protected void initData() {

    }

    private void QueryManySubjectDoctor() {
        Map<String, String> map = new HashMap<>();
        map.put("SubjectBuyRecordId", SubjectBuyRecordId + "");
        map.put("DrId", DrId);

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(HttpUrl.QueryManySubjectDoctor).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTDetailEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTDetailEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    if (entity.getData() != null) {
                        if (!TextUtils.isEmpty(entity.getData().getAdvice())) {
                            etContent.setText(entity.getData().getAdvice());
                        }
                        //        tvName.setText(mMDTDetailEntity.getPatientName() + "");
                        mAdviceEntity = entity.getData();
                        tvAge.setText(mAdviceEntity.getPatientAge());
                        if (mAdviceEntity.getPatientSex() == 1) {
                            tvSex.setText("男");
                        } else if (mAdviceEntity.getPatientSex() == 2) {
                            tvSex.setText("女");
                        } else {
                            tvSex.setText("未知");
                        }

                        GlideUtils.loadPatientImage(mAdviceEntity.getPatPicturePath(), civImg);

                        tvDiseaseDesc.setText(mAdviceEntity.getDescription() + "");
                        tvName.setText(mAdviceEntity.getPatientName() + "");
                        String diseaseName = mAdviceEntity.getDiseases();
                        String diseaseTime = mAdviceEntity.getDiseasesTimeText();
                        String seeDoctorText = mAdviceEntity.getSeeDoctorText();
                        String hospital = mAdviceEntity.getSeeDoctor();
                        String department = mAdviceEntity.getSeeDepartment();
                        if (TextUtils.isEmpty(diseaseTime)) {
                            diseaseTime = "";
                        } else {
                            diseaseTime = diseaseTime + ",";
                        }

                        if (TextUtils.isEmpty(diseaseName)) {
                            diseaseName = "";
                        } else {
                            diseaseName = diseaseName + ",";
                        }

                        if (TextUtils.isEmpty(seeDoctorText)) {
                            seeDoctorText = "";
                        } else {
                            seeDoctorText = seeDoctorText + ",";
                        }

                        if (TextUtils.isEmpty(hospital)) {
                            hospital = "";
                        } else {
                            hospital = hospital + ",";
                        }
                        if (TextUtils.isEmpty(department)) {
                            department = "";
                        }
                        //组合文字
                        tvPatientHistory.setText(diseaseName + diseaseTime + seeDoctorText + hospital + department);
                        if (mAdviceEntity.getSubjectStatus() == 1) {
                            tvRight.setVisibility(View.VISIBLE);
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


    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.top_right:
                if (mAdviceEntity != null) {
                    Intent intent = new Intent(MYmdtInputReportActivity.this, MDTInputReportActivity.class);
                    intent.putExtra("MDTDetailEntity", mAdviceEntity);
                    startActivity(intent);
                }

                break;

            case R.id.btn_send_report:
                if (TextUtils.isEmpty(etContent.getText().toString())) {
                    toast("专家意见不能为空");
                } else {

                }

                break;


        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        MyApplication.mActivities.remove(this);
    }



}

