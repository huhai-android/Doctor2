package com.newdjk.doctor.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.DiagnosisAndTreatAdapter;
import com.newdjk.doctor.ui.entity.ConsultMessageEntity;
import com.newdjk.doctor.ui.entity.DoctorMedicalRecordsEntity;
import com.newdjk.doctor.ui.entity.InquiryRecordListEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UnreadMessageEntity;
import com.newdjk.doctor.ui.entity.UpdateConsultUnreadNum;
import com.newdjk.doctor.ui.entity.UpdateTreatMessageEntity;
import com.newdjk.doctor.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DiagnosisAndTreatFragment1  extends BasicFragment {

    @BindView(R.id.mRecyclerview)
    RecyclerView mRecyclerview;
    Unbinder unbinder;
    @BindView(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;
    // @BindView(R.id.click_refresh)
//    TextView clickRefresh;
//    @BindView(R.id.no_data_tip)
//    LinearLayout noDataTip;
    private DiagnosisAndTreatAdapter mDiagnosisAndTreatAdapter;
    private InquiryRecordListEntity mInquiryRecordListEntity;
    private List<DoctorMedicalRecordsEntity.ReturnDataBean> dataList1 = new ArrayList<>();
    private List<UnreadMessageEntity> mAllUnreadMessageList;
    private int index = 1;
    List<ConsultMessageEntity> dataList = new ArrayList<>();

    public static DiagnosisAndTreatFragment1 newInstance(List<UnreadMessageEntity> allUnreadMessageList) {
        Bundle args = new Bundle();
        args.putSerializable("allUnreadMessageList", (Serializable) allUnreadMessageList);
        DiagnosisAndTreatFragment1 myFragment = new DiagnosisAndTreatFragment1();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    protected int initViewResId() {
        return R.layout.consult_list;
    }

    @Override
    protected void initView() {
        mDiagnosisAndTreatAdapter = new DiagnosisAndTreatAdapter(dataList1,1);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerview.setAdapter(mDiagnosisAndTreatAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        mEasylayout.setLoadMoreModel(LoadModel.NONE);
    }

    @Override
    protected void initListener() {

        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                queryDoctorMedicalRecords();
            }

            @Override
            public void onRefreshing() {
                queryDoctorMedicalRecords();
            }
        });

    }

    @Override
    protected void initData() {
        try {

            queryDoctorMedicalRecords();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void otherViewClick(View view) {
//        switch (view.getId()) {
//            case R.id.click_refresh:
////                refreshLayout.setRefreshing(true);
//                getConsultRecordList("1");
//                break;
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //  mInquiryRecordListEntity = (InquiryRecordListEntity)getArguments().getSerializable("consultMessage");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    public void flashView () {
        queryDoctorMedicalRecords();
    }

    public void freshUnreadList (UpdateConsultUnreadNum userEvent) {
        mAllUnreadMessageList = userEvent.UnreadMessageList;
        int index1 =  0;
        for (int i = 0; i < mAllUnreadMessageList.size(); i++) {
            String patientId = mAllUnreadMessageList.get(i).getPatientId();
            for (int k = 0; k < dataList.size(); k++) {
                String id = dataList.get(k).getApplicantIMId();
                if (id.equals(patientId)) {
                    dataList.get(k).setUnReadNum(mAllUnreadMessageList.get(i).getUnReadNum());
                    dataList.get(k).setCreateTime(mAllUnreadMessageList.get(i).getTimeStamp());
                    ConsultMessageEntity consultMessageEntity = dataList.get(k);
                    dataList.remove(dataList.get(k));
                    dataList.add(index1,consultMessageEntity);
                    //  Collections.swap(list,index1,k);
                    index1 = index1+1;
                    break;
                }
            }
        }
        mDiagnosisAndTreatAdapter.notifyDataSetChanged();
    }

    private void queryDoctorMedicalRecords() {
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        params.put("MedicalType", "1");
        params.put("MedicalStatus", "2");
        params.put("PageIndex", "1");
        params.put("PageSize", "1000");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.QueryDoctorMedicalRecords).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<DoctorMedicalRecordsEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<DoctorMedicalRecordsEntity> entity) {

                final DoctorMedicalRecordsEntity doctorMedicalRecordsEntity = entity.getData();
                Log.i("DiagnosisAndTreat","entity="+doctorMedicalRecordsEntity.toString());
                List<DoctorMedicalRecordsEntity.ReturnDataBean> list = doctorMedicalRecordsEntity.getReturnData();
                if (entity.getCode() == 0) {
                    if (mEasylayout == null) return;
                    dataList1.clear();
                    if (mEasylayout.isRefreshing())  mEasylayout.refreshComplete();
                    if (list.size() >0) {
                        mNodataContainer.setVisibility(View.GONE);
                        mEasylayout.setVisibility(View.VISIBLE);

                        dataList1 = list;
                        mDiagnosisAndTreatAdapter.setNewData(dataList1);

                        mEasylayout.refreshComplete();
                    }
                    else {
                        mNodataContainer.setVisibility(View.VISIBLE);
                        mEasylayout.setVisibility(View.GONE);
                    }
                    EventBus.getDefault().post(new UpdateTreatMessageEntity("getSize"));
                } else {
                    toast(entity.getMessage());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }
    public int getSize() {
        return dataList1.size();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

