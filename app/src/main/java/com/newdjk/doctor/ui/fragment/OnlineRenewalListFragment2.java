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
import com.newdjk.doctor.ui.adapter.OnlineRenewalDataAdapter1;
import com.newdjk.doctor.ui.entity.AllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.DoctorPatientRelationEntity;
import com.newdjk.doctor.ui.entity.OnlineRenewalDataEntity;
import com.newdjk.doctor.ui.entity.OnlineRenewalMessageEntity;
import com.newdjk.doctor.ui.entity.PatientInfoEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UnreadMessageEntity;
import com.newdjk.doctor.ui.entity.UpdateAllUNreadNum;
import com.newdjk.doctor.ui.entity.UpdateConsultViewEntity2;
import com.newdjk.doctor.ui.entity.UpdateImMessageEntity;
import com.newdjk.doctor.ui.entity.UpdateRenewalUnreadNum;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;

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

public class OnlineRenewalListFragment2 extends BasicFragment {
    @BindView(R.id.mRecyclerview)
    RecyclerView mRecyclerview;
    Unbinder unbinder;
    @BindView(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;
    private OnlineRenewalMessageEntity mOnlineRenewalMessageEntity;
    private OnlineRenewalDataAdapter1 mOnlineRenewalDataAdapter;
    private List<UnreadMessageEntity> mAllUnreadMessageList;
    private int index = 1;
    List<AllRecordForDoctorEntity> dataList = new ArrayList<>();

    public static OnlineRenewalListFragment2 newInstance(List<UnreadMessageEntity> allUnreadMessageList) {
        Bundle args = new Bundle();
        args.putSerializable("allUnreadMessageList", (Serializable) allUnreadMessageList);
        OnlineRenewalListFragment2 myFragment = new OnlineRenewalListFragment2();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    protected int initViewResId() {
        return R.layout.consult_list;
    }

    @Override
    protected void initView() {

        mOnlineRenewalDataAdapter = new OnlineRenewalDataAdapter1(dataList,3);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerview.setAdapter(mOnlineRenewalDataAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
    }

    @Override
    protected void initListener() {
        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                index++;
                getOnlinePartyList(index, "7");
            }

            @Override
            public void onRefreshing() {
                index = 1;
                mEasylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
                getOnlinePartyList(index, "7");
            }
        });

    }

    @Override
    protected void initData() {
        try {
            mAllUnreadMessageList = (List<UnreadMessageEntity>) getArguments().getSerializable("allUnreadMessageList");

            getOnlinePartyList(index,"7");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        super.onCreate(savedInstanceState);
    }

    private void getOnlinePartyList(final int index,String chatStatus) {
        if (mEasylayout == null) return;
        Map<String, String> map = new HashMap<>();
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("ChatStatus", chatStatus);
        map.put("PageIndex", index+"");
        map.put("PageSize", "10");
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization",SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetApplyRecordList).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<OnlineRenewalMessageEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<OnlineRenewalMessageEntity> entity) {
                LoadDialog.clear();
                if (mEasylayout == null) return;
                if (mEasylayout.isLoading())  mEasylayout.closeLoadView();
                if (mEasylayout.isRefreshing())  mEasylayout.refreshComplete();
                if (entity.getCode() == 0) {
                    OnlineRenewalMessageEntity onlineRenewalMessageEntity = entity.getData();
                    if (onlineRenewalMessageEntity != null && onlineRenewalMessageEntity.getTotal() > 0) {
                        mNodataContainer.setVisibility(View.GONE);
                        mEasylayout.setVisibility(View.VISIBLE);
                        if (onlineRenewalMessageEntity.getTotal() < 10)
                            mEasylayout.setLoadMoreModel(LoadModel.NONE);
                        List<OnlineRenewalDataEntity> list = onlineRenewalMessageEntity.getReturnData();
                        List<AllRecordForDoctorEntity> list1 = new ArrayList<>();
                        if (list.size() == 0)  {
                            toast("没有更多数据");
                            mEasylayout.setLoadMoreModel(LoadModel.NONE);
                        }

                            for (int k = 0; k < list.size(); k++) {
                                OnlineRenewalDataEntity onlineRenewalDataEntity = list.get(k);
                                String PatientName = onlineRenewalDataEntity.getPatientName();
                                String ApplicantHeadImgUrl =onlineRenewalDataEntity.getApplicantHeadImgUrl();
                                String EndTime = onlineRenewalDataEntity.getEndTime();
                                String PayTime = onlineRenewalDataEntity.getPayTime();
                                String CreateTime =onlineRenewalDataEntity.getCreateTime();
                                int RecordId = onlineRenewalDataEntity.getId();
                                int Type = onlineRenewalDataEntity.getType();
                                int Status = onlineRenewalDataEntity.getStatus();
                                String DealWithTime = onlineRenewalDataEntity.getDealWithTime();
                                String StartTime = onlineRenewalDataEntity.getStartTime();
                                String ApplicantIMId = onlineRenewalDataEntity.getApplicantIMId();
                              /*  RecordInfoEntity recordInfoEntity = new RecordInfoEntity();
                                recordInfoEntity.setPatientName(PatientName);
                                recordInfoEntity.setApplicantHeadImgUrl(ApplicantHeadImgUrl);
                                recordInfoEntity.setEndTime(EndTime);
                                recordInfoEntity.setPayTime(PayTime);
                                recordInfoEntity.setCreateTime(CreateTime);
                                recordInfoEntity.setRecordId(RecordId);
                                recordInfoEntity.setType(Type);
                                recordInfoEntity.setStatus(Status);
                                recordInfoEntity.setDealWithTime(DealWithTime);
                                recordInfoEntity.setStartTime(StartTime);*/

                                String Diagnoses = onlineRenewalDataEntity.getDiagnoses();
                                PatientInfoEntity patientInfoEntity =onlineRenewalDataEntity.getPatientInfo() ;
                                String Age = null;
                                String AreaName = null;
                                int PatientSex = 0;
                                if (patientInfoEntity!= null){
                                     Age = patientInfoEntity.getAge();
                                     AreaName = patientInfoEntity.getAreaName();
                                     PatientSex = patientInfoEntity.getPatientSex();
                                }

                                DoctorPatientRelationEntity DoctorPatientRelationEntity = onlineRenewalDataEntity.getDoctorPatientRelation();
                                int IsDrKey = 0;
                                int IsPatientMain = 0;
                                String Disease =null;
                                if (DoctorPatientRelationEntity != null) {
                                    IsDrKey = DoctorPatientRelationEntity.getIsDrKey();
                                    IsPatientMain = DoctorPatientRelationEntity.getIsPatientMain();
                                    Disease = DoctorPatientRelationEntity.getDisease();
                                }
                               int  ApplicantId = onlineRenewalDataEntity.getApplicantId();
                                String ServiceCode = "1103";
                                int PatientId = onlineRenewalDataEntity.getPatientId();
                                String recordData = new Gson().toJson(onlineRenewalDataEntity);
                                AllRecordForDoctorEntity allRecordForDoctorEntity =    new AllRecordForDoctorEntity(null,null,PatientName,ApplicantHeadImgUrl,EndTime,PayTime,CreateTime,RecordId,Type,Status,DealWithTime,StartTime,ApplicantIMId,0,Diagnoses,0,null,null,null,Age,AreaName,PatientSex,IsDrKey,IsPatientMain,Disease,ServiceCode,0,recordData,ApplicantId,PatientId,null);
                                list1.add(allRecordForDoctorEntity);
                            }

                        if (index ==1) {
                            dataList.clear();
                            dataList = list1;
                            mOnlineRenewalDataAdapter.setNewData(dataList);
                            mEasylayout.refreshComplete();
                        } else {

                            mEasylayout.closeLoadView();
                           // int postion = mOnlineRenewalDataAdapter.getData().size();
                           // dataList.addAll(list);
                            mOnlineRenewalDataAdapter.getData().addAll(list1);
                            int postion = mOnlineRenewalDataAdapter.getData().size();
                            mOnlineRenewalDataAdapter.notifyDataSetChanged();
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
                if (mEasylayout.isRefreshing())  mEasylayout.refreshComplete();
                if (mEasylayout.isLoading())  mEasylayout.closeLoadView();
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateConsultViewEntity2 userEvent) {
//        Log.i("ll", "action=" + mAction);
//        refreshLayout.setRefreshing(true);
//        if (mAction == 0) {
//            getOnlinePartyList("0");
//        } else if (mAction == 1) {
//            getOnlinePartyList("1");
//        } else if (mAction == 2) {
//            getOnlinePartyList("7");
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateAllUNreadNum userEvent) {
        mAllUnreadMessageList = userEvent.UnreadMessageList;
//        refreshLayout.setRefreshing(true);
//        Log.i("ll", "action=" + mAction);
//        refreshLayout.setRefreshing(true);
//        if (mAction == 0) {
//            getOnlinePartyList("0");
//        } else if (mAction == 1) {
//            getOnlinePartyList("1");
//        } else if (mAction == 2) {
//            getOnlinePartyList("7");
//        }
    }

    public void flashView (UpdateRenewalUnreadNum userEvent) {
        mAllUnreadMessageList = userEvent.UnreadMessageList;
        index = 1;
        getOnlinePartyList(index,"7");
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateImMessageEntity userEvent) {
        index = 1;
        getOnlinePartyList(index,"7");
    }
    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
