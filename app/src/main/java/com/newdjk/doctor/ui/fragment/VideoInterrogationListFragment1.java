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
import com.newdjk.doctor.ui.adapter.VideoInterrogationAdapter;
import com.newdjk.doctor.ui.adapter.VideoInterrogationAdapter1;
import com.newdjk.doctor.ui.entity.AllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.doctor.ui.entity.InquiryRecordListEntity;
import com.newdjk.doctor.ui.entity.OnlineRenewalDataEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UnreadMessageEntity;
import com.newdjk.doctor.ui.entity.UpdateConsultViewEntity;
import com.newdjk.doctor.ui.entity.UpdateConsultViewEntity1;
import com.newdjk.doctor.ui.entity.UpdateImMessageEntity;
import com.newdjk.doctor.ui.entity.UpdateRenewalUnreadNum;
import com.newdjk.doctor.ui.entity.UpdateVideoUnreadNum;
import com.newdjk.doctor.utils.SQLiteUtils;
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

public class VideoInterrogationListFragment1 extends BasicFragment {

    @BindView(R.id.mRecyclerview)
    RecyclerView mRecyclerview;
    Unbinder unbinder;
    @BindView(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;
    private VideoInterrogationAdapter1 mVideoInterrogationAdapter;
    private InquiryRecordListEntity mInquiryRecordListEntity;
    private List<AllRecordForDoctorEntity> dataList = new ArrayList<>();


    public static VideoInterrogationListFragment1 newInstance() {
        Bundle args = new Bundle();
        VideoInterrogationListFragment1 myFragment = new VideoInterrogationListFragment1();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    protected int initViewResId() {
        return R.layout.consult_list;
    }



    @Override
    protected void initView() {
        mVideoInterrogationAdapter = new VideoInterrogationAdapter1(dataList,2);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerview.setAdapter(mVideoInterrogationAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        mEasylayout.setLoadMoreModel(LoadModel.NONE);
    }

    @Override
    protected void initListener() {
        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {

                getInquiryRecord( );
            }

            @Override
            public void onRefreshing() {

                getInquiryRecord( );
            }
        });
    }


    @Override
    protected void initData() {

        getInquiryRecord();


    }

    @Override
    protected void otherViewClick(View view) {
//        switch (view.getId()) {
//            case R.id.click_refresh :
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
        EventBus.getDefault().register(this);
        //  mInquiryRecordListEntity = (InquiryRecordListEntity)getArguments().getSerializable("consultMessage");
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private void getInquiryRecord() {
        List<AllRecordForDoctorEntity> list = SQLiteUtils.getInstance().selectImDataByServiceCodeAndStatus("1102",1);
        if (mEasylayout == null) return;
        if (mEasylayout.isRefreshing())  mEasylayout.refreshComplete();
        if (list.size() >0) {
            mNodataContainer.setVisibility(View.GONE);
            mEasylayout.setVisibility(View.VISIBLE);
            dataList.clear();
            dataList = list;
            mVideoInterrogationAdapter.setNewData(dataList);
            mEasylayout.refreshComplete();
        }
        else {
            mNodataContainer.setVisibility(View.VISIBLE);
            mEasylayout.setVisibility(View.GONE);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateImMessageEntity userEvent) {
        // GetAllRecordForDoctor();
        //  refreshView(userEvent.ImId);
        getInquiryRecord();
    }
}