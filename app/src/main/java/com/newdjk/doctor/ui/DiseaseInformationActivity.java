package com.newdjk.doctor.ui;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.tools.MainConstant;
import com.newdjk.doctor.ui.activity.PrescriptionActivity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.ui.entity.MessageEventRecent;
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
public class DiseaseInformationActivity extends BasicActivity {


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
    @BindView(R.id.tv_refuse)
    TextView tvRefuse;
    @BindView(R.id.tv_accept)
    TextView tvAccept;
    @BindView(R.id.civImg)
    CircleImageView civImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_jibing)
    TextView tvJibing;
    @BindView(R.id.tv_bingqing)
    TextView tvBingqing;
    @BindView(R.id.tv_huanbing_time)
    TextView tvHuanbingTime;
    @BindView(R.id.tv_jiuzhen_keshi)
    TextView tvJiuzhenKeshi;
    @BindView(R.id.tv_guominshi)
    TextView tvGuominshi;
    @BindView(R.id.tv_jiwangshi)
    TextView tvJiwangshi;
    @BindView(R.id.tv_check_image)
    TextView tvCheckImage;
    @BindView(R.id.lv_refuse)
    LinearLayout lvRefuse;
    @BindView(R.id.lv_accept_refuse)
    LinearLayout lvAcceptRefuse;
    @BindView(R.id.tv_yaopin)
    TextView tvYaopin;

    private MDTDetailEntity mDetailEntity;
    private MDTDetailEntity mData;
    private String mSubjectBuyRecordId;

    @Override
    protected int initViewResId() {
        return R.layout.activity_disease_information;
    }

    @Override
    protected void initView() {

        MyApplication.mActivities.add(this);
        int type = getIntent().getIntExtra(Contants.Type, 0);
        mDetailEntity = (MDTDetailEntity) getIntent().getSerializableExtra(Contants.MDTINFO);
        mSubjectBuyRecordId = getIntent().getStringExtra(Contants.SubjectBuyRecordId);
        if (type == 1) {
            initTitle("病情资料").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            lvRefuse.setVisibility(View.GONE);
        } else if (type == 3) {
            if (mDetailEntity != null) {
                if (TextUtils.isEmpty(mDetailEntity.getSubjectReportPath())) {
                    initTitle("病情资料").setRightText("编辑").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                } else {
                    initTitle("病情资料").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                }
            }


            lvAcceptRefuse.setVisibility(View.GONE);
        }

        tvRight.setTextColor(ContextCompat.getColor(this, R.color.theme));

    }


    @Override
    protected void initListener() {
        tvRefuse.setOnClickListener(this);
        tvAccept.setOnClickListener(this);
        tvCheckImage.setOnClickListener(this);
        tvRight.setOnClickListener(this);
    }


    @Override
    protected void initData() {
        // getgroupList();
    }

    private void getgroupList() {

        Map<String, String> map = new HashMap<>();
        if (mDetailEntity == null) {
            map.put("SubjectBuyRecordId", mSubjectBuyRecordId + "");
        } else {
            map.put("SubjectBuyRecordId", mDetailEntity.getSubjectBuyRecordId() + "");

        }
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(HttpUrl.QueryMDTBySubjectBuyRecordId).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTDetailEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTDetailEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    if (entity.getData() != null) {
                        mData = entity.getData();
                        if (mData.getPatientSex() == 1) {
                            tvName.setText("" + mData.getPatientName() + "  男" + "  " + mData.getPatientAge());
                        } else if (entity.getData().getPatientSex() == 2) {
                            tvName.setText("" + mData.getPatientName() + "  女" + "  " + mData.getPatientAge());
                        } else {
                            tvName.setText("" + mData.getPatientName() + "  未知" + "  " + mData.getPatientAge());
                        }

                        GlideUtils.loadPatientImage(mData.getPatPicturePath(), civImg);

                        tvJibing.setText(mData.getDiseases());

                        tvBingqing.setText((TextUtils.isEmpty(mData.getDescription()) ? "暂无内容" : mData.getDescription()) + "");
                        tvHuanbingTime.setText((TextUtils.isEmpty(mData.getDiseasesTimeText()) ? "暂无内容" : mData.getDiseasesTimeText()) + "");

                        String keshihospital = "";
                        keshihospital = (TextUtils.isEmpty(mData.getSeeDoctor()) ? "" : mData.getSeeDoctor()) + "" + (TextUtils.isEmpty(mData.getSeeDepartment()) ? "" : mData.getSeeDepartment())
                        ;
                        tvJiuzhenKeshi.setText("" + (TextUtils.isEmpty(keshihospital) ? "暂无内容" : keshihospital));

                        if (mData.getIsAllergy() == 1) {
                            tvGuominshi.setText("有过敏" + (TextUtils.isEmpty(mData.getAllergyDes()) ? "" : "," + mData.getAllergyDes()) + "");

                        } else {
                            tvGuominshi.setText("无过敏" + (TextUtils.isEmpty(mData.getAllergyDes()) ? "" : "," + mData.getAllergyDes()) + "");

                        }
                        tvYaopin.setText((TextUtils.isEmpty(mData.getDrugUse()) ? "暂无内容" : mData.getDrugUse()) + "");

                        String shoushu = mData.getOperationDes();
                        if (TextUtils.isEmpty(shoushu)) {
                            shoushu = "暂无内容";
                        }
                        String fangliao = mData.getChemoDes();
                        if (TextUtils.isEmpty(fangliao)) {
                            fangliao = "暂无内容";
                        }
                        String manxing = mData.getChronicDisease();
                        if (TextUtils.isEmpty(manxing)) {
                            manxing = "暂无内容";
                        }
                        String zhongda = mData.getSeriousDisease();
                        if (TextUtils.isEmpty(zhongda)) {
                            zhongda = "暂无内容";
                        }


                        tvJiwangshi.setText("手术描述：" + shoushu + "\n放化疗描述：" + fangliao + "\n慢性疾病" +
                                "：" + manxing + "\n重大疾病：" + zhongda);

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
            case R.id.tv_refuse:
                acceptMDTwenzhen(2);
                break;

            case R.id.tv_accept:
                acceptMDTwenzhen(1);
                break;
            case R.id.tv_check_image:
                try {
                    Intent mdtIntent = new Intent(DiseaseInformationActivity.this, PrescriptionActivity.class);
                    mdtIntent.putExtra("type", 27);
                    mdtIntent.putExtra("SubjectBuyRecordId", mData.getSubjectBuyRecordId() + "");
                    mdtIntent.putExtra("MDTDetailEntity", mData);
                    startActivity(mdtIntent);
                } catch (Exception e) {

                }
                break;

            case R.id.tv_right:
                // toast("编辑");

                Intent mdtIntent = new Intent(DiseaseInformationActivity.this, PrescriptionActivity.class);
                mdtIntent.putExtra("type", 31);
                mdtIntent.putExtra("SubjectBuyRecordId", mData.getSubjectBuyRecordId() + "");
                mdtIntent.putExtra("MDTDetailEntity", mData);
                startActivity(mdtIntent);

                break;

        }
    }


    //诊疗状态(1-接诊，2-拒诊)
    private void acceptMDTwenzhen(final int SubjectStatus) {

        Map<String, String> map = new HashMap<>();
        map.put("SubjectBuyRecordId", mData.getSubjectBuyRecordId() + "");
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        map.put("DrName", mData.getDrName());
        map.put("SubjectStatus", SubjectStatus + "");
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.ReceiveOrRejecteMDT).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                LoadDialog.clear();
                if (entity.getCode() == 0) {
                    if (SubjectStatus == 2) {
                        toast("拒诊成功");
                    } else if (SubjectStatus == 1) {
                        toast("接诊成功");
                    }
                    MessageEventRecent messageEvent = new MessageEventRecent();
                    messageEvent.setmType(MainConstant.acceprtorrefuse);
                    EventBus.getDefault().post(messageEvent);
                    MyApplication.exit();


                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.mActivities.remove(this);
        EventBus.getDefault().unregister(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        getgroupList();
    }
}

