package com.newdjk.doctor.ui.activity;

import android.content.Context;
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

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.tools.MainConstant;
import com.newdjk.doctor.ui.adapter.MyDiagnoseRepoetAdapter;
import com.newdjk.doctor.ui.entity.LookMedicalEntity;
import com.newdjk.doctor.ui.entity.MessageEvent;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   SecondDiagnosisQuestionActivity
 *  @创建者:   huhai
 *  @创建时间:  2018/12/17 15:34
 *  @描述：
 */
public class SecondDiagnosisReportActivity extends BasicActivity {


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
    @BindView(R.id.recyleview)
    RecyclerView recyleview;
    @BindView(R.id.no_data_tip)
    LinearLayout noDataTip;
    private int mMedicalRecordId;
    private MyDiagnoseRepoetAdapter myDiagnoseAdapter;
    private List<LookMedicalEntity.PatientMedicalsBean> mDatalist;
    private int type;
    private String mPersonalDesc = null;
    private boolean mIsHasReport;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int initViewResId() {
        return R.layout.activity_diagnosis_report;
    }

    @Override
    protected void initView() {
        mMedicalRecordId = getIntent().getIntExtra("MedicalRecordId", 0);
        mIsHasReport = getIntent().getBooleanExtra("isHasReport",false);
        type = getIntent().getIntExtra("type", 0);
        if (type == 1 || mIsHasReport) {
            initBackTitle(getString(R.string.my_document));
        } else {
            initBackTitle(getString(R.string.my_document)).setRightText("补充材料");
        }

        mDatalist = new ArrayList<>();
        myDiagnoseAdapter = new MyDiagnoseRepoetAdapter(mDatalist, mPersonalDesc);
        recyleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyleview.setAdapter(myDiagnoseAdapter);
        recyleview.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
    }

    @Override
    protected void initListener() {
        tvRight.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        queryLookMedical();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                Intent systemSettingIntent2 = new Intent(SecondDiagnosisReportActivity.this, AddDocumentActivity.class);
                systemSettingIntent2.putExtra("MedicalRecordId", mMedicalRecordId);
                startActivity(systemSettingIntent2);

                break;
        }
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SecondDiagnosisReportActivity.class);
        return intent;
    }

    private void queryLookMedical() {
        loading(true);

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        String url = HttpUrl.QueryLookMedical + "?MedicalRecordId=" + mMedicalRecordId;
        mMyOkhttp.get().url(url).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<LookMedicalEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<LookMedicalEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    LookMedicalEntity lookMedicalEntity = entity.getData();
                    mDatalist.clear();
                    if (lookMedicalEntity != null) {
                        mPersonalDesc = lookMedicalEntity.getPersonalDesc();
                        mDatalist.addAll(lookMedicalEntity.getPatientMedicals());
                        myDiagnoseAdapter.setPersonalDesc(mPersonalDesc);
                    }
                    if (mDatalist.size() == 0) {
                        noDataTip.setVisibility(View.VISIBLE);
                        recyleview.setVisibility(View.GONE);
                    } else {
                        noDataTip.setVisibility(View.GONE);
                        recyleview.setVisibility(View.VISIBLE);
                        myDiagnoseAdapter.setNewData(mDatalist);
                    }

                  /*  Log.i("SymptomsSelectActivity","entity="+entity.getData().toString());
                    mSymptomSelectedAdapter.setDataList(entity.getData());*/

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(MessageEvent event) {
        switch (event.getType()) {
            case MainConstant.UPDATE_MEDICAL:
                queryLookMedical();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
