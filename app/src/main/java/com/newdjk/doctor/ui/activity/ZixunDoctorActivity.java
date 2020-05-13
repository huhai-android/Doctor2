package com.newdjk.doctor.ui.activity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.ZixunDoctorAdapter;
import com.newdjk.doctor.ui.entity.QueryOrgDoctorSearchByPageEntity;
import com.newdjk.doctor.ui.entity.RequireOrderStatusEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.ClearEditText;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.TransmitDoctorDialog;
import com.newdjk.doctor.views.TransmitRequirementDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ZixunDoctorActivity extends BasicActivity {


    private static final String TAG = "ZixunDoctorActivity";
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
    @BindView(R.id.et_patient_msg)
    ClearEditText etSearch;
    private List<QueryOrgDoctorSearchByPageEntity.ReturnDataBean> datalist = new ArrayList<>();
    private ZixunDoctorAdapter adapter;
    private TransmitDoctorDialog mDialog;
    private TransmitRequirementDialog mdoctorDialog;
    private String medicationCompanyOrgId;
    private String mPatRequireOrderId;
    private int orderstatus;
    private String orderdoctorname;
    private String inputtext="";

    @Override
    protected int initViewResId() {
        return R.layout.activity_zixun;
    }

    @Override
    protected void initView() {


        initTitle("咨询医生").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        medicationCompanyOrgId = getIntent().getStringExtra("MedicationCompanyOrgId");
        mPatRequireOrderId = getIntent().getStringExtra("PatRequireOrderId");
        adapter = new ZixunDoctorAdapter(datalist);
        //mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyleview.setAdapter(adapter);
        recyleview.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));

    }

    private void getDoctorList() {

        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        HashMap<String, String> params = new HashMap<>();
        params.put("KeyName", inputtext);
        params.put("DrType", "-1");
        params.put("HospitalId", "-1");
        params.put("DepartmentId", "-1");
        params.put("Position", "-1");
        params.put("OrgId", medicationCompanyOrgId);
        params.put("IsHasPack", "-1");
        params.put("HospitalLevel", "-1");
        params.put("SericeItemCode", "");
        params.put("AccountId", "");//患者账号Id
        mMyOkhttp.post().url(HttpUrl.QueryOrgDoctorSearchByPage).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<QueryOrgDoctorSearchByPageEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<QueryOrgDoctorSearchByPageEntity> response) {

                datalist.clear();
                datalist.addAll(response.getData().getReturnData());
                adapter.setNewData(datalist);
                GetRequireOrderStatus();


            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                LoadDialog.clear();
            }
        });
    }
    private void getDoctorList2() {

        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        HashMap<String, String> params = new HashMap<>();
        params.put("KeyName", inputtext);
        params.put("DrType", "-1");
        params.put("HospitalId", "-1");
        params.put("DepartmentId", "-1");
        params.put("Position", "-1");
        params.put("OrgId", medicationCompanyOrgId);
        params.put("IsHasPack", "-1");
        params.put("HospitalLevel", "-1");
        params.put("SericeItemCode", "");
        params.put("AccountId", "");//患者账号Id
        mMyOkhttp.post().url(HttpUrl.QueryOrgDoctorSearchByPage).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<QueryOrgDoctorSearchByPageEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<QueryOrgDoctorSearchByPageEntity> response) {

                datalist.clear();
                datalist.addAll(response.getData().getReturnData());
                adapter.setNewData(datalist);
                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                LoadDialog.clear();
            }
        });
    }
    private void GetRequireOrderStatus() {

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        HashMap<String, String> params = new HashMap<>();
        params.put("PatRequireOrderId", mPatRequireOrderId);

        mMyOkhttp.get().url(HttpUrl.GetRequireOrderStatus).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<RequireOrderStatusEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<RequireOrderStatusEntity> response) {
                LoadDialog.clear();
                //需求单状态（0：待处理，1：医生处理中，2：已开处方/完成）
                orderstatus = response.getData().getStatus();
                orderdoctorname = TextUtils.isEmpty(response.getData().getDoctorName()) ? "" : response.getData().getDoctorName();
                if (orderstatus !=0 ) {
                    showTransmitDoctorDialog(orderdoctorname);
                }


            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                LoadDialog.clear();
            }
        });
    }

    private void showTransmitDoctorDialog(String doctorname) {


        if (mDialog == null) {
            mDialog = new TransmitDoctorDialog(ZixunDoctorActivity.this);
        }
        mDialog.show(doctorname,orderstatus, new TransmitDoctorDialog.DialogListener() {
            @Override
            public void cancel() {

            }

            @Override
            public void confirm() {
            finish();
            }
        });

    }

    //转发需求
    private void DistributeRequireOrder(String DoctorName, String DoctorId) {

        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        HashMap<String, String> params = new HashMap<>();
        params.put("PatRequireOrderId", mPatRequireOrderId);
        params.put("DoctorId", DoctorId);
        params.put("DoctorName", DoctorName);

        mMyOkhttp.post().url(HttpUrl.DistributeRequireOrder).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity response) {
                LoadDialog.clear();
                //需求单状态（0：待处理，1：医生处理中，2：已开处方/完成）
                if (response.getCode() == 0) {
                    boolean issuccess = (boolean) response.getData();
                    if (issuccess) {
                        toast("转发需求成功");
                        finish();
                    } else {
                        toast("转发需求失败");
                    }
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                LoadDialog.clear();
            }
        });
    }

    @Override
    protected void initListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                //  toast("点击了item"+position);
                String doctorname = "";
                if (datalist.size() > 0) {
                    doctorname = datalist.get(position).getDrName();
                }

                //如果已经转了，就不能再继续转了
                if (orderstatus == 1) {
                    showTransmitDoctorDialog(orderdoctorname);//提示弹窗
                } else {
                    if (mdoctorDialog == null) {
                        mdoctorDialog = new TransmitRequirementDialog(ZixunDoctorActivity.this);
                    }
                    mdoctorDialog.show(doctorname, new TransmitRequirementDialog.DialogListener() {
                        @Override
                        public void cancel() {

                        }

                        @Override
                        public void confirm() {
                            DistributeRequireOrder(datalist.get(position).getDrName(), datalist.get(position).getDrId() + "");

                        }
                    });
                }

            }
        });


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                inputtext = s.toString();
                Log.d(TAG,"输入内容"+inputtext);

                getDoctorList2();

            }
        });


    }

    @Override
    protected void initData() {
        getDoctorList();
    }

    @Override
    protected void otherViewClick(View view) {

    }


}
