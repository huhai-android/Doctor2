package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
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

import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.SymptomSelectAdapter;
import com.newdjk.doctor.ui.entity.GetSecondDiagnosisSettingEntity;
import com.newdjk.doctor.ui.entity.QueryDiseaseEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SymptomsSelectActivity extends BasicActivity {

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
    @BindView(R.id.has_select_symptoms)
    TextView hasSelectSymptoms;
    @BindView(R.id.message_list)
    RecyclerView messageList;
    private SymptomSelectAdapter mSymptomSelectAdapter;
    private QueryDiseaseEntity mDataList;
    private List<GetSecondDiagnosisSettingEntity.DoctorDiseasesBean> mSelectedList;
    private Gson mGson;

    @Override
    protected int initViewResId() {
        return R.layout.symptoms_select;
    }

    @Override
    protected void initView() {
        mGson = new Gson();
        initBackTitle("适应病症").setRightText("确定");
        mSelectedList = new ArrayList<>();
        mSymptomSelectAdapter = new SymptomSelectAdapter(this);
        mSymptomSelectAdapter.setItemClickListener(new SymptomSelectAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                List<QueryDiseaseEntity.ReturnDataBean> list = mSymptomSelectAdapter.getDatalist();
                StringBuffer sb = new StringBuffer();
                mSelectedList.clear();
                for (int i = 0; i < list.size(); i++) {

                    GetSecondDiagnosisSettingEntity.DoctorDiseasesBean diseasesBean = new GetSecondDiagnosisSettingEntity.DoctorDiseasesBean();
                    diseasesBean.setDiseaseId(list.get(i).getDiseaseId());
                    diseasesBean.setDiseaseName(list.get(i).getDiseaseName());
                    mSelectedList.add(diseasesBean);
                    sb.append(" " + list.get(i).getDiseaseName());
                }
                hasSelectSymptoms.setText(sb.toString());
                mSymptomSelectAdapter.notifyItemChanged(postion);
            }
        });
        messageList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        messageList.setAdapter(mSymptomSelectAdapter);
        messageList.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
    }

    @Override
    protected void initListener() {
        tvRight.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        QueryDiseaseByPage();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                if (mSelectedList.size() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra("selectDisease",(Serializable)mSelectedList);
                    setResult(RESULT_OK,intent);
                    finish();
                   // SaveDoctorDisease();
                } else {
                    toast("你没有选择任何病症，不能保存！");
                }
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void QueryDiseaseByPage() {
        loading(true);
        Map<String, String> map = new HashMap<>();
        map.put("PageIndex", "1");
        map.put("PageSize", "10");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        //   String url =HttpUrl.QueryDoctorDiseases+"?DrId="+SpUtils.getInt(Contants.Id,0);
        // String url = "http://172.18.30.4/NetHospSecondDiagnosisAPI/MedicalService/QueryDoctorDiseases?DrId="+SpUtils.getInt(Contants.Id,0);
        mMyOkhttp.post().url(HttpUrl.QueryDiseaseByPage).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<QueryDiseaseEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<QueryDiseaseEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    Log.i("SymptomsSelectActivity", "entity=" + entity.getData().toString());
                    mDataList = entity.getData();
                    mSymptomSelectAdapter.setDatalist(mDataList);

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
    /*    mDataList = new ArrayList<>();
        for (int i = 0;i < 20;i++) {
            SymptomEntity SymptomEntity = new SymptomEntity();
            SymptomEntity.setSymptom("喉癌"+i);
            mDataList.add(SymptomEntity);
        }
        mSymptomSelectAdapter.setDatalist(mDataList);*/
    }

   /* private void SaveDoctorDisease() {
        SaveSymptomsRequestEntity SaveSymptomsRequestEntity = new SaveSymptomsRequestEntity();
        SaveSymptomsRequestEntity.setDrId(SpUtils.getInt(Contants.Id, 0));
        SaveSymptomsRequestEntity.setDrName(SpUtils.getString(Contants.Name));
        SaveSymptomsRequestEntity.setDiseases(mSelectedList);
        String json = mGson.toJson(SaveSymptomsRequestEntity);
        loading(true);

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.SaveDoctorDisease).headers(headMap).jsonParams(json).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    boolean result = (boolean) entity.getData();
                    if (result) {
                        toast("保存成功");
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        toast("保存失败");
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
    }*/
}
