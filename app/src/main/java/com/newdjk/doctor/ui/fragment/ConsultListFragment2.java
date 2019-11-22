package com.newdjk.doctor.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.ConsultMessageAdapter1;
import com.newdjk.doctor.ui.entity.AllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.ConsultDataEntity;
import com.newdjk.doctor.ui.entity.ConsultMessageEntity;
import com.newdjk.doctor.ui.entity.DoctorPatientRelationEntity;
import com.newdjk.doctor.ui.entity.InquiryRecordListEntity;
import com.newdjk.doctor.ui.entity.PatientInfoEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UnreadMessageEntity;
import com.newdjk.doctor.ui.entity.UpdateImMessageEntity;
import com.newdjk.doctor.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ConsultListFragment2 extends BasicFragment {

    @BindView(R.id.mRecyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
    Unbinder unbinder;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;
    private ConsultMessageAdapter1 mConsultMessageAdapter;
    private InquiryRecordListEntity mInquiryRecordListEntity;
    public static boolean isreflash = false;
    private List<UnreadMessageEntity> mAllUnreadMessageList;
    private int index = 1;
    List<AllRecordForDoctorEntity> dataList = new ArrayList<>();
    public static ConsultListFragment2 newInstance( List<UnreadMessageEntity> allUnreadMessageList) {
        Bundle args = new Bundle();
        args.putSerializable("allUnreadMessageList", (Serializable) allUnreadMessageList);
        ConsultListFragment2 myFragment = new ConsultListFragment2();
        myFragment.setArguments(args);
        return myFragment;
    }


    @Override
    protected int initViewResId() {
        return R.layout.consult_list;
    }

    @Override
    protected void initView() {

        mConsultMessageAdapter = new ConsultMessageAdapter1(dataList,3);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerview.setAdapter(mConsultMessageAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
    }

    @Override
    protected void initListener() {
//        refreshLayout.setOnRefreshListener(this);
//        clickRefresh.setOnClickListener(this);


        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                index++;
                getConsultRecordList(index, "7");
            }

            @Override
            public void onRefreshing() {
                index = 1;
                getConsultRecordList(index, "7");
            }
        });
    }

    @Override
    protected void initData() {
        try {
            mAllUnreadMessageList = (List<UnreadMessageEntity>) getArguments().getSerializable("allUnreadMessageList");
            getConsultRecordList(index,"7");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void otherViewClick(View view) {
//        switch (view.getId()) {
//            case R.id.click_refresh:
//                getConsultRecordList("7");
//                break;
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    private void getConsultRecordList(final int index, String chatStatus) {
        if (mEasylayout == null) return;
        Map<String, String> map = new HashMap<>();
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("ChatStatus", chatStatus);
        map.put("PageIndex", index+"");
        map.put("PageSize", "10");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization",SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetConsultRecordList).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ConsultDataEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ConsultDataEntity> entity) {
                if (mEasylayout == null) return;
                if (mEasylayout.isRefreshing())  mEasylayout.refreshComplete();
                if (mEasylayout.isLoading())  mEasylayout.closeLoadView();
                if (entity.getCode() == 0) {
                    ConsultDataEntity consultDataEntity = entity.getData();
                    //   int j = 0;
                    if (consultDataEntity != null && consultDataEntity.getTotal() > 0) {
                        mNodataContainer.setVisibility(View.GONE);
                        mEasylayout.setVisibility(View.VISIBLE);
                        if (consultDataEntity.getTotal() < 10)
                            mEasylayout.setLoadMoreModel(LoadModel.NONE);
                        List<ConsultMessageEntity> list = consultDataEntity.getReturnData();
                        List<AllRecordForDoctorEntity> list1 = new ArrayList<>();
                            for (int k = 0; k < list.size(); k++) {
                                ConsultMessageEntity consultMessageEntity =list.get(k);
                                String Content = consultMessageEntity.getContent();
                                String PatientName = consultMessageEntity.getPatientName();
                                String ApplicantHeadImgUrl = consultMessageEntity.getApplicantHeadImgUrl();
                                String EndTime = consultMessageEntity.getEndTime();
                                String PayTime = consultMessageEntity.getPayTime();
                                String CreateTime = consultMessageEntity.getCreateTime();
                                int RecordId = consultMessageEntity.getId();
                                int Type =consultMessageEntity.getType();
                                int Status = consultMessageEntity.getStatus();
                                String DealWithTime = consultMessageEntity.getDealWithTime();
                                String StartTime = consultMessageEntity.getStartTime();
                                String ApplicantIMId = consultMessageEntity.getApplicantIMId();

                                PatientInfoEntity patientInfoEntity =consultMessageEntity.getPatientInfo() ;
                                String Age = null;
                                String AreaName = null;
                                int PatientSex = 0;
                                if (patientInfoEntity!= null){
                                    Age = patientInfoEntity.getAge();
                                    AreaName = patientInfoEntity.getAreaName();
                                    PatientSex = patientInfoEntity.getPatientSex();
                                }
                                DoctorPatientRelationEntity DoctorPatientRelationEntity = consultMessageEntity.getDoctorPatientRelation();
                                int IsDrKey = 0;
                                int IsPatientMain = 0;
                                String Disease =null;
                                if (DoctorPatientRelationEntity != null) {
                                     IsDrKey = consultMessageEntity.getDoctorPatientRelation().getIsDrKey();
                                     IsPatientMain = consultMessageEntity.getDoctorPatientRelation().getIsPatientMain();
                                     Disease = consultMessageEntity.getDoctorPatientRelation().getDisease();
                                }
                               int ApplicantId = consultMessageEntity.getApplicantId();

                                String ServiceCode = "1101";
                                int PatientId = consultMessageEntity.getPatientId();
                                String recordData = new Gson().toJson(consultMessageEntity);
                                AllRecordForDoctorEntity allRecordForDoctorEntity =    new AllRecordForDoctorEntity(null,Content,PatientName,ApplicantHeadImgUrl,EndTime,PayTime,CreateTime,RecordId,Type,Status,DealWithTime,StartTime,ApplicantIMId,0,null,0,null,null,null,Age,AreaName,PatientSex,IsDrKey,IsPatientMain,Disease,ServiceCode,0,recordData,ApplicantId,PatientId,null);
                                list1.add(allRecordForDoctorEntity);
                            }

                        if (list.size() == 0)  {
                            toast("没有更多数据");
                            mEasylayout.setLoadMoreModel(LoadModel.NONE);
                        }

                        if (index ==1) {
                            dataList.clear();
                            dataList.addAll(list1);
                            mConsultMessageAdapter.setNewData(dataList);
                            mEasylayout.refreshComplete();
                        } else {

                            mEasylayout.closeLoadView();
                        //    dataList.addAll(list);
                            mConsultMessageAdapter.getData().addAll(list1);
                            int postion = mConsultMessageAdapter.getData().size();
                            mConsultMessageAdapter.notifyDataSetChanged();
                            mRecyclerview.scrollToPosition(postion);
                        }
                    } else {

                        mNodataContainer.setVisibility(View.VISIBLE);
                        mEasylayout.setVisibility(View.GONE);
                    }
                } else {
                    mNodataContainer.setVisibility(View.VISIBLE);
                    mEasylayout.setVisibility(View.GONE);
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                if (mEasylayout == null) return;
                if (mEasylayout.isLoading())  mEasylayout.closeLoadView();
                if (mEasylayout.isRefreshing())  mEasylayout.refreshComplete();
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateImMessageEntity userEvent) {
        index = 1;
        getConsultRecordList(index,"7");
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}