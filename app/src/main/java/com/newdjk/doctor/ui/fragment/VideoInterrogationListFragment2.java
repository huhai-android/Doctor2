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
import com.newdjk.doctor.ui.adapter.VideoInterrogationAdapter1;
import com.newdjk.doctor.ui.entity.AllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.DoctorPatientRelationEntity;
import com.newdjk.doctor.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.doctor.ui.entity.InquiryRecordListEntity;
import com.newdjk.doctor.ui.entity.PatientInfoEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UpdateConsultViewEntity1;
import com.newdjk.doctor.ui.entity.UpdateImMessageEntity;
import com.newdjk.doctor.utils.AppUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VideoInterrogationListFragment2 extends BasicFragment {

    @BindView(R.id.mRecyclerview)
    RecyclerView mRecyclerview;
    Unbinder unbinder;
    @BindView(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;
    private VideoInterrogationAdapter1 mVideoInterrogationAdapter;
    private InquiryRecordListEntity mInquiryRecordListEntity;
    private int index = 1;
    private List<AllRecordForDoctorEntity> dataList = new ArrayList<>();

    public static VideoInterrogationListFragment2 newInstance() {

        Bundle args = new Bundle();
        VideoInterrogationListFragment2 myFragment = new VideoInterrogationListFragment2();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    protected int initViewResId() {
        return R.layout.consult_list;
    }

    @Override
    protected void initView() {

        mVideoInterrogationAdapter = new VideoInterrogationAdapter1(dataList, 3);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerview.setAdapter(mVideoInterrogationAdapter);
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
                getInquiryRecord(index, "7");
            }

            @Override
            public void onRefreshing() {
                index = 1;
                getInquiryRecord(index, "7");
            }
        });

    }

    @Override
    protected void initData() {
        index = 1;
        getInquiryRecord(index, "7");

    }

    @Override
    protected void otherViewClick(View view) {
//        switch (view.getId()) {
//            case R.id.click_refresh:
//                refreshLayout.setRefreshing(true);
//                if (mAction == 0) {
//                    getInquiryRecord("0");
//                } else if (mAction == 1) {
//                    getInquiryRecord("1");
//                } else if (mAction == 2) {
//                    getInquiryRecord("7");
//                }
//                break;
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //  mInquiryRecordListEntity = (InquiryRecordListEntity)getArguments().getSerializable("consultMessage");
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void getInquiryRecord(final int index, String chatStatus) {
        if (mEasylayout == null) return;
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        Map<String, String> map = new HashMap<>();
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("ChatStatus", chatStatus);
        map.put("PageIndex", index + "");
        map.put("PageSize", "10");
        loading(true);
        mMyOkhttp.post().url(HttpUrl.GetInquiryRecordList).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<InquiryRecordListEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<InquiryRecordListEntity> entity) {
                LoadDialog.clear();
                if (mEasylayout == null) {
                    return;
                }
                if (mEasylayout.isRefreshing()){
                    mEasylayout.refreshComplete();
                }
                if (mEasylayout.isLoading()) {
                    mEasylayout.closeLoadView();
                }
                if (entity.getCode() == 0) {
                    InquiryRecordListEntity inquiryRecordListEntity = entity.getData();
                    if (inquiryRecordListEntity != null && inquiryRecordListEntity.getTotal() > 0) {
                        mNodataContainer.setVisibility(View.GONE);
                        mEasylayout.setVisibility(View.VISIBLE);
                        if (inquiryRecordListEntity.getTotal() < 10){
                            mEasylayout.setLoadMoreModel(LoadModel.NONE);
                        }
                        List<InquiryRecordListDataEntity> list = inquiryRecordListEntity.getReturnData();
                        List<AllRecordForDoctorEntity> list1 = new ArrayList<>();
                        if (list.size() == 0) {
                            toast("没有更多数据");
                            mEasylayout.setLoadMoreModel(LoadModel.NONE);
                        }
                        for (int k = 0; k < list.size(); k++) {
                            InquiryRecordListDataEntity inquiryInfoListBean = list.get(k);

                            String Content = inquiryInfoListBean.getContent();
                            String PatientName = inquiryInfoListBean.getPatientName();
                            String ApplicantHeadImgUrl = inquiryInfoListBean.getApplicantHeadImgUrl();
                            String EndTime = inquiryInfoListBean.getEndTime();
                            String PayTime = inquiryInfoListBean.getPayTime();
                            String CreateTime = inquiryInfoListBean.getCreateTime();
                            int RecordId = inquiryInfoListBean.getId();
                            int Type = inquiryInfoListBean.getType();
                            int Status = inquiryInfoListBean.getStatus();
                            String DealWithTime = inquiryInfoListBean.getDealWithTime();
                            String StartTime = inquiryInfoListBean.getStartTime();
                            String ApplicantIMId = inquiryInfoListBean.getApplicantIMId();
                            TIMConversation conversation = TIMManager.getInstance().getConversation(
                                    TIMConversationType.C2C,
                                    ApplicantIMId);
                            long unReadNum = 0;
                            long timeStamp = 0;
                            if (conversation != null) {
                                unReadNum = AppUtils.getUnreadMessageNum(conversation);
                                List<TIMMessage> lastMsgs = conversation.getLastMsgs(1);

                                if (lastMsgs != null && lastMsgs.size() > 0) {
                                    TIMMessage msg = lastMsgs.get(0);
                                    timeStamp = msg.timestamp();
                                }
                            }
                            String ReExaminationDate = inquiryInfoListBean.getReExaminationDate();
                            String ReExaminationStartTime = inquiryInfoListBean.getReExaminationStartTime();
                            String ReExaminationEndTime = inquiryInfoListBean.getReExaminationEndTime();
                            String Age = null;
                            String AreaName = null;
                            int PatientSex = 0;
                            int IsDrKey = 0;
                            int IsPatientMain = 0;
                            String Disease = null;
                            PatientInfoEntity PatientInfoBean = inquiryInfoListBean.getPatientInfo();
                            DoctorPatientRelationEntity DoctorPatientRelationBean = inquiryInfoListBean.getDoctorPatientRelation();
                            if (PatientInfoBean != null) {
                                Age = PatientInfoBean.getAge();
                                AreaName = PatientInfoBean.getAreaName();
                                PatientSex = PatientInfoBean.getPatientSex();
                            }
                            if (DoctorPatientRelationBean != null) {
                                IsDrKey = DoctorPatientRelationBean.getIsDrKey();
                                IsPatientMain = DoctorPatientRelationBean.getIsPatientMain();
                                Disease = DoctorPatientRelationBean.getDisease();
                            }
                            String ServiceCode = "1102";
                            int ApplicantId = inquiryInfoListBean.getApplicantId();
                            String RecordData = new Gson().toJson(inquiryInfoListBean);
                            int PatientId = inquiryInfoListBean.getPatientId();

                            AllRecordForDoctorEntity allRecordForDoctorEntity = new AllRecordForDoctorEntity(null, Content, PatientName, ApplicantHeadImgUrl, EndTime, PayTime, CreateTime, RecordId, Type, Status, DealWithTime, StartTime, ApplicantIMId, unReadNum, null, 0, ReExaminationDate, ReExaminationStartTime, ReExaminationEndTime, Age, AreaName, PatientSex, IsDrKey, IsPatientMain, Disease, ServiceCode, timeStamp, RecordData, ApplicantId, PatientId, null);
                            list1.add(allRecordForDoctorEntity);
                        }
                        if (index == 1) {
                            dataList.clear();
                            dataList.addAll(list1);
                            mVideoInterrogationAdapter.setNewData(dataList);
                            mEasylayout.refreshComplete();
                        } else {
                            mEasylayout.closeLoadView();
                            // dataList.addAll(entity.getData().getReturnData());
                            mVideoInterrogationAdapter.getData().addAll(list1);
                            int postion = mVideoInterrogationAdapter.getData().size();
                            mVideoInterrogationAdapter.notifyDataSetChanged();
                            mRecyclerview.scrollToPosition(postion);
                        }
                    } else {
                        mNodataContainer.setVisibility(View.VISIBLE);
                        mEasylayout.setVisibility(View.GONE);
                    }
                } else {
                    mNodataContainer.setVisibility(View.GONE);
                    mEasylayout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
                if (mEasylayout == null) {
                    return;
                }
                if (mEasylayout.isRefreshing()) {
                    mEasylayout.refreshComplete();
                }
                if (mEasylayout.isLoading()) {
                    mEasylayout.closeLoadView();
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateConsultViewEntity1 userEvent) {
        index = 1;
        getInquiryRecord(index, "7");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateImMessageEntity userEvent) {
        // GetAllRecordForDoctor();
        //  refreshView(userEvent.ImId);
        index = 1;
        getInquiryRecord(index, "7");
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}