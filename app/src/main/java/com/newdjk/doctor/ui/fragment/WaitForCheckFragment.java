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
import com.newdjk.doctor.ui.activity.CustodyReportActivity;
import com.newdjk.doctor.ui.adapter.WaitForCheckDoctorDataAdapter;
import com.newdjk.doctor.ui.entity.AllDoctorCheckEntity;
import com.newdjk.doctor.ui.entity.InquiryRecordListEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.SignReportSuccess;
import com.newdjk.doctor.ui.entity.UnreadMessageEntity;
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

public class WaitForCheckFragment extends BasicFragment {

    private static final String TAG = "WaitForCheckFragment";
    private static OnRedNumberChangeListener listener;
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
    private WaitForCheckDoctorDataAdapter mConsultMessageAdapter;
    private InquiryRecordListEntity mInquiryRecordListEntity;
    private List<UnreadMessageEntity> mAllUnreadMessageList;
    private int index = 1;
    List<AllDoctorCheckEntity.ReturnDataBean> dataList = new ArrayList<>();
    private AllDoctorCheckEntity.ReturnDataBean returnDataBean;

    public static WaitForCheckFragment newInstance(List<UnreadMessageEntity> allUnreadMessageList, OnRedNumberChangeListener changeListener) {
        Bundle args = new Bundle();
        args.putSerializable("allUnreadMessageList", (Serializable) allUnreadMessageList);
        WaitForCheckFragment myFragment = new WaitForCheckFragment();
        myFragment.setArguments(args);
        listener = changeListener;
        return myFragment;
    }

    @Override
    protected int initViewResId() {
        return R.layout.consult_list;
    }


    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mConsultMessageAdapter = new WaitForCheckDoctorDataAdapter(dataList);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerview.setAdapter(mConsultMessageAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        mEasylayout.setLoadMoreModel(LoadModel.NONE);
    }

    @Override
    protected void initListener() {
        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                //  getConsultRecordList( "0");
            }

            @Override
            public void onRefreshing() {
                getConsultRecordList("0");
            }
        });
        mConsultMessageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                returnDataBean = dataList.get(position);
                Log.i("AllDoctorDataAdapter", "跳转");
                Intent intent = new Intent(mContext, CustodyReportActivity.class);
                intent.putExtra("askid", dataList.get(position).getAskId());
                intent.putExtra("isSign", false);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        try {
            mAllUnreadMessageList = (List<UnreadMessageEntity>) getArguments().getSerializable("allUnreadMessageList");
            getConsultRecordList("0");
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //  mInquiryRecordListEntity = (InquiryRecordListEntity)getArguments().getSerializable("consultMessage");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getConsultRecordList(String chatStatus) {
        if (mEasylayout == null) return;
        Map<String, String> map = new HashMap<>();
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        Log.d(TAG,"医生id"+String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("Status", "0");
        map.put("PageIndex", "1");
        map.put("PageSize", "10000");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.getAllReadData).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<AllDoctorCheckEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<AllDoctorCheckEntity> entity) {
                if (mEasylayout == null) return;
                if (mEasylayout.isRefreshing()) mEasylayout.refreshComplete();
                if (entity.getCode() == 0) {
                    AllDoctorCheckEntity consultDataEntity = entity.getData();
                    Log.d(TAG, consultDataEntity.toString());
                    if (consultDataEntity != null && consultDataEntity.getTotal() > 0) {
                        mNodataContainer.setVisibility(View.GONE);
                        mEasylayout.setVisibility(View.VISIBLE);
                        dataList.clear();
                        dataList.addAll(entity.getData().getReturnData());
                        listener.onRedCountChange(dataList.size());
                        mConsultMessageAdapter.setNewData(dataList);

                    } else {
                        dataList.clear();
                        mNodataContainer.setVisibility(View.VISIBLE);
                        mEasylayout.setVisibility(View.GONE);
                        listener.onRedCountChange(dataList.size());
                    }
                } else {
                    dataList.clear();
                    mNodataContainer.setVisibility(View.VISIBLE);
                    mEasylayout.setVisibility(View.GONE);
                    listener.onRedCountChange(dataList.size());
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(SignReportSuccess signReportSuccess) {
        if (signReportSuccess.signResule) {
            getConsultRecordList("0");
            //当判定报告成功之后，需要将数据发送到已经判定界面
            EventBus.getDefault().post(returnDataBean);

        }

    }
}
