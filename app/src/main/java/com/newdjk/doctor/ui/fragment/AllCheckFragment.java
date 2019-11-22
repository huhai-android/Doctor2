package com.newdjk.doctor.ui.fragment;

import android.content.Intent;
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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.iInterface.OnRedNumberChangeListener;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.CustodyReportDetailActivity;
import com.newdjk.doctor.ui.adapter.AllDoctorDataAdapter;
import com.newdjk.doctor.ui.entity.AllDoctorCheckEntity;
import com.newdjk.doctor.ui.entity.OnlineRenewalMessageEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UnreadMessageEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.TimeUtil;
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

public class AllCheckFragment extends BasicFragment {
    private static final String TAG = "AllCheckFragment";
    private static OnRedNumberChangeListener listener;
    @BindView(R.id.mRecyclerview)
    RecyclerView mRecyclerview;
    Unbinder unbinder;
    @BindView(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;
    private OnlineRenewalMessageEntity mOnlineRenewalMessageEntity;
    private AllDoctorDataAdapter mOnlineRenewalDataAdapter;
    private List<UnreadMessageEntity> mAllUnreadMessageList;
    private int index = 1;
    List<AllDoctorCheckEntity.ReturnDataBean> dataList = new ArrayList<>();
    private int total=0;

    public static AllCheckFragment newInstance(List<UnreadMessageEntity> allUnreadMessageList, OnRedNumberChangeListener changeListener) {
        Bundle args = new Bundle();
        args.putSerializable("allUnreadMessageList", (Serializable) allUnreadMessageList);
        AllCheckFragment myFragment = new AllCheckFragment();
        listener = changeListener;
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    protected int initViewResId() {
        return R.layout.consult_list;
    }

    @Override
    protected void initView() {

        mOnlineRenewalDataAdapter = new AllDoctorDataAdapter(dataList);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerview.setAdapter(mOnlineRenewalDataAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
    }

    @Override
    protected void initListener() {
        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                Log.d(TAG, "价值更多");
                index++;
                getOnlinePartyList(index, "-1");
            }

            @Override
            public void onRefreshing() {
                index = 1;
                mEasylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
                getOnlinePartyList(index, "-1");
            }
        });

        mOnlineRenewalDataAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Log.i("AllDoctorDataAdapter", "跳转");
                Intent intent = new Intent(mContext, CustodyReportDetailActivity.class);
                intent.putExtra("askid", dataList.get(position).getAskId());
                intent.putExtra("isSign", true);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    protected void initData() {
        try {
            mAllUnreadMessageList = (List<UnreadMessageEntity>) getArguments().getSerializable("allUnreadMessageList");
            getOnlinePartyList(index, "-1");
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

    private void getOnlinePartyList(final int index, String chatStatus) {
        if (mEasylayout == null) return;
        Map<String, String> map = new HashMap<>();
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("Status", "-1");
        map.put("PageIndex", index + "");
        map.put("PageSize", "10");
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.getAllReadData).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<AllDoctorCheckEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<AllDoctorCheckEntity> entity) {
                LoadDialog.clear();
                if (mEasylayout == null) return;
                if (mEasylayout.isRefreshing()) mEasylayout.refreshComplete();
                if (entity.getCode() == 0) {
                    AllDoctorCheckEntity onlineRenewalMessageEntity = entity.getData();
                    Log.d(TAG, onlineRenewalMessageEntity.toString());
                    if (onlineRenewalMessageEntity != null && onlineRenewalMessageEntity.getTotal() > 0) {
                        mNodataContainer.setVisibility(View.GONE);
                        mEasylayout.setVisibility(View.VISIBLE);
                        if (onlineRenewalMessageEntity.getTotal() < 10)
                            mEasylayout.setLoadMoreModel(LoadModel.NONE);
                        List<AllDoctorCheckEntity.ReturnDataBean> list = onlineRenewalMessageEntity.getReturnData();
                        if (list==null){
                            toast("没有更多数据");
                            mEasylayout.setLoadMoreModel(LoadModel.NONE);
                        }else {
                            if (list.size() == 0) {
                                toast("没有更多数据");
                                mEasylayout.setLoadMoreModel(LoadModel.NONE);
                            }
                        }
                        if (index == 1) {
                            dataList.clear();
                            dataList.addAll(list);
                            listener.onAllCountChange(onlineRenewalMessageEntity.getTotal());
                            total=onlineRenewalMessageEntity.getTotal();
                            mOnlineRenewalDataAdapter.setNewData(dataList);
                            mEasylayout.refreshComplete();
                        } else {
                            mEasylayout.closeLoadView();
                            int postion = mOnlineRenewalDataAdapter.getData().size();
                            dataList.addAll(list);
                            total=onlineRenewalMessageEntity.getTotal();
                            listener.onAllCountChange(onlineRenewalMessageEntity.getTotal());
                            mOnlineRenewalDataAdapter.setNewData(dataList);
                            mRecyclerview.scrollToPosition(postion);
                        }
                    } else {
                        dataList.clear();
                        listener.onAllCountChange(0);
                        mNodataContainer.setVisibility(View.VISIBLE);
                        mEasylayout.setVisibility(View.GONE);
                    }
                } else {
                    listener.onAllCountChange(0);
                    mNodataContainer.setVisibility(View.VISIBLE);
                    mEasylayout.setVisibility(View.GONE);
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                mNodataContainer.setVisibility(View.VISIBLE);
                mEasylayout.setVisibility(View.GONE);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(AllDoctorCheckEntity.ReturnDataBean returnDataBean) {
        if (returnDataBean != null) {
            returnDataBean.setReplytime(TimeUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
            dataList.add(0, returnDataBean);
            mOnlineRenewalDataAdapter.notifyItemChanged(0);
            total=total+1;
            listener.onAllCountChange(total);
        }

    }
}
