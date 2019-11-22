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
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.ui.adapter.OnlineRenewalDataAdapter1;
import com.newdjk.doctor.ui.entity.AllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.OnlineRenewalDataEntity;
import com.newdjk.doctor.ui.entity.OnlineRenewalMessageEntity;
import com.newdjk.doctor.ui.entity.UnreadMessageEntity;
import com.newdjk.doctor.ui.entity.UpdateImMessageEntity;
import com.newdjk.doctor.utils.SQLiteUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OnlineRenewalListFragment extends BasicFragment {
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
    public static OnlineRenewalListFragment newInstance(List<UnreadMessageEntity> allUnreadMessageList) {
        Bundle args = new Bundle();
        args.putSerializable("allUnreadMessageList", (Serializable) allUnreadMessageList);
        OnlineRenewalListFragment myFragment = new OnlineRenewalListFragment();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    protected int initViewResId() {
        return R.layout.consult_list;
    }

    @Override
    protected void initView() {
        mOnlineRenewalDataAdapter = new OnlineRenewalDataAdapter1(dataList1,1);
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateImMessageEntity userEvent) {
        // GetAllRecordForDoctor();
        //  refreshView(userEvent.ImId);
        getOnlinePartyList();
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
        List<AllRecordForDoctorEntity> list = SQLiteUtils.getInstance().selectImDataByServiceCodeAndStatus("1103",0);
        if (mEasylayout == null) {
            return;
        }
        if (mEasylayout.isRefreshing()) {
            mEasylayout.refreshComplete();
        }
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





    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
