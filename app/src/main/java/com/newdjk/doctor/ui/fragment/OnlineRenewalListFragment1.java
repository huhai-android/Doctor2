package com.newdjk.doctor.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.OnlineRenewalDataAdapter;
import com.newdjk.doctor.ui.adapter.OnlineRenewalDataAdapter1;
import com.newdjk.doctor.ui.entity.AllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.ConsultDataEntity;
import com.newdjk.doctor.ui.entity.ConsultMessageEntity;
import com.newdjk.doctor.ui.entity.OnlineRenewalDataEntity;
import com.newdjk.doctor.ui.entity.OnlineRenewalMessageEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UnreadMessageEntity;
import com.newdjk.doctor.ui.entity.UpdateAllUNreadNum;
import com.newdjk.doctor.ui.entity.UpdateConsultUnreadNum;
import com.newdjk.doctor.ui.entity.UpdateConsultViewEntity2;
import com.newdjk.doctor.ui.entity.UpdateImMessageEntity;
import com.newdjk.doctor.ui.entity.UpdateRenewalUnreadNum;
import com.newdjk.doctor.utils.SQLiteUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OnlineRenewalListFragment1 extends BasicFragment  {
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
    List<OnlineRenewalDataEntity> dataList = new ArrayList<>();
    private List<AllRecordForDoctorEntity> dataList1 = new ArrayList<>();
    public static OnlineRenewalListFragment1 newInstance(List<UnreadMessageEntity> allUnreadMessageList) {
        Bundle args = new Bundle();
        args.putSerializable("allUnreadMessageList", (Serializable) allUnreadMessageList);
        OnlineRenewalListFragment1 myFragment = new OnlineRenewalListFragment1();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    protected int initViewResId() {
        return R.layout.consult_list;
    }

    @Override
    protected void initView() {
        mOnlineRenewalDataAdapter = new OnlineRenewalDataAdapter1(dataList1,2);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerview.setAdapter(mOnlineRenewalDataAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        mEasylayout.setLoadMoreModel(LoadModel.NONE);
    }

    @Override
    protected void initListener() {

        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                getOnlinePartyList();
            }

            @Override
            public void onRefreshing() {
                getOnlinePartyList();
            }
        });
    }

    @Override
    protected void initData() {
        try {
            //  mAllUnreadMessageList = (List<UnreadMessageEntity>) getArguments().getSerializable("allUnreadMessageList");
            getOnlinePartyList();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void otherViewClick(View view) {
//        switch (view.getId()) {
//            case R.id.click_refresh:
//                getOnlinePartyList(index, "0");
//                break;
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // TODO: inflate a fragment view
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
    private void getOnlinePartyList () {
        List<AllRecordForDoctorEntity> list = SQLiteUtils.getInstance().selectImDataByServiceCodeAndStatus("1103",1);
        if (mEasylayout == null) return;
        if (mEasylayout.isRefreshing())  mEasylayout.refreshComplete();
        if (list.size() >0) {
            mNodataContainer.setVisibility(View.GONE);
            mEasylayout.setVisibility(View.VISIBLE);
            dataList1.clear();
            dataList1 = list;
            mOnlineRenewalDataAdapter.setNewData(dataList1);
            mEasylayout.refreshComplete();
        }
        else {
            mNodataContainer.setVisibility(View.VISIBLE);
            mEasylayout.setVisibility(View.GONE);
        }
    }

   /* private void getOnlinePartyList( String chatStatus) {
        if (mEasylayout == null) return;
        Map<String, String> map = new HashMap<>();
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization",SpUtils.getString(Contants.Token));
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("ChatStatus", chatStatus);
        map.put("PageIndex", "1");
        map.put("PageSize", "10000");
        loading(true);
        mMyOkhttp.post().url(HttpUrl.GetApplyRecordList).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<OnlineRenewalMessageEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<OnlineRenewalMessageEntity> entity) {
                LoadDialog.clear();
                if (mEasylayout == null) return;
                if (mEasylayout.isRefreshing())  mEasylayout.refreshComplete();
                if (entity.getCode() == 0) {
                    OnlineRenewalMessageEntity onlineRenewalMessageEntity = entity.getData();
                    if (onlineRenewalMessageEntity != null && onlineRenewalMessageEntity.getTotal() > 0) {
                        mNodataContainer.setVisibility(View.GONE);
                        mEasylayout.setVisibility(View.VISIBLE);

                        List<OnlineRenewalDataEntity> list = onlineRenewalMessageEntity.getReturnData();
                        int index1 =  0;
                        for (int i = 0; i < mAllUnreadMessageList.size(); i++) {
                            String patientId = mAllUnreadMessageList.get(i).getPatientId();
                            for (int k = 0; k < list.size(); k++) {
                                String id = list.get(k).getApplicantIMId();
                                if (id.equals(patientId)) {
                                    OnlineRenewalDataEntity onlineRenewalDataEntity = list.get(k);
                                    onlineRenewalDataEntity.setUnReadNum(mAllUnreadMessageList.get(i).getUnReadNum());
                                    onlineRenewalDataEntity.setCreateTime(mAllUnreadMessageList.get(i).getTimeStamp());
                                   *//* list.remove(list.get(k));
                                    list.add(onlineRenewalDataEntity);
                                    index1 = index1+1;*//*
                                    break;
                                }
                            }
                        }

                            dataList.clear();
                            dataList = list;
                            mOnlineRenewalDataAdapter.setNewData(dataList);
                            mEasylayout.refreshComplete();

                    } else {
                        mNodataContainer.setVisibility(View.VISIBLE);
                        mEasylayout.setVisibility(View.GONE);
                    }
                } else {
                    mNodataContainer.setVisibility(View.VISIBLE);
                    mEasylayout.setVisibility(View.GONE);
                    toast(entity.getMessage()+"1111");
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                mNodataContainer.setVisibility(View.VISIBLE);
                mEasylayout.setVisibility(View.GONE);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }*/

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
    public void userEventBus(UpdateImMessageEntity userEvent) {
        // GetAllRecordForDoctor();
        //  refreshView(userEvent.ImId);
        getOnlinePartyList();
    }
    public void flashView (UpdateRenewalUnreadNum userEvent) {
        mAllUnreadMessageList = userEvent.UnreadMessageList;
        getOnlinePartyList();
    }
    public void freshUnreadList (UpdateRenewalUnreadNum userEvent) {
        mAllUnreadMessageList = userEvent.UnreadMessageList;
        int index1 =  0;
        for (int i = 0; i < mAllUnreadMessageList.size(); i++) {
            String patientId = mAllUnreadMessageList.get(i).getPatientId();
            for (int k = 0; k < dataList.size(); k++) {
                String id = dataList.get(k).getApplicantIMId();
                if (id.equals(patientId)) {
                    dataList.get(k).setUnReadNum(mAllUnreadMessageList.get(i).getUnReadNum());
                    dataList.get(k).setCreateTime(mAllUnreadMessageList.get(i).getTimeStamp());
                    OnlineRenewalDataEntity onlineRenewalDataEntity = dataList.get(k);
                    dataList.remove(dataList.get(k));
                    dataList.add(index1,onlineRenewalDataEntity);
                    //  Collections.swap(list,index1,k);
                    index1 = index1+1;
                    break;
                }
            }
        }
        mOnlineRenewalDataAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
