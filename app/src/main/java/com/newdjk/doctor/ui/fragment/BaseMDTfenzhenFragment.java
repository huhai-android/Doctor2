package com.newdjk.doctor.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.iInterface.OnPrescriptionChangeListener;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.ui.adapter.ComeDownTimeAdapter;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UnreadMessageEntity;
import com.newdjk.doctor.views.ZhuanZhenListItemDialog;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.fragment
 *  @文件名:   BaseCheckFragment
 *  @创建者:   huhai
 *  @创建时间:  2018/11/30 11:15
 *  @描述：
 */
public abstract class BaseMDTfenzhenFragment extends BasicFragment {
    private static final String TAG = "BaseCheckFragment";
    public static OnPrescriptionChangeListener listener;

    Unbinder unbinder;
    public Gson mGson;
    public int index = 1;
    @BindView(R.id.mRecyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.easylayout)
    EasyRefreshLayout easylayout;
    @BindView(R.id.iv_no)
    ImageView ivNo;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;
    private ZhuanZhenListItemDialog mDialog;
    public ComeDownTimeAdapter mOnlineRenewalDataAdapter;
    public List<MDTDetailEntity> dataList = new ArrayList<>();
    public long mNowTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        initView();
        initListener();
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        refreshView();
        return rootView;
    }

    protected abstract void refreshView();

    @Override
    protected int initViewResId() {
        return R.layout.fragment_mdt_zhuanzhen;
    }

    @Override
    protected void initView() {
        mGson = new Gson();

        mOnlineRenewalDataAdapter = new ComeDownTimeAdapter(dataList, this);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerview.setAdapter(mOnlineRenewalDataAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        mOnlineRenewalDataAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                toDiseaseInfomation(position);

            }
        });

        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d(TAG, "SCROLL_STATE_IDLE---111");
                    mOnlineRenewalDataAdapter.addScrollStatus(false);
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    Log.d(TAG, "SCROLL_STATE_DRAGGING---222");
                } else if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    Log.d(TAG, "SCROLL_STATE_SETTLING---333");
                    mOnlineRenewalDataAdapter.addScrollStatus(true);
                }
            }
        });
    }

    protected abstract void toDiseaseInfomation(int position);



    @Override
    protected void initListener() {
        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                Log.d(TAG, "价值更多");
                index++;

            }

            @Override
            public void onRefreshing() {
                index = 1;
                easylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);

            }
        });

    }


    @Override
    protected void initData() {
        getcurrentTime();
    }

    private void getcurrentTime() {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.get().url(HttpUrl.getCurrentTime).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity entity) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = entity.getData().toString();
                try {
                    Date date = format.parse(time);
                    mNowTime = date.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                QueryUnReceiveBuyRecordPage();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {

            }
        });

    }

    protected abstract void QueryUnReceiveBuyRecordPage();

    protected abstract void getQueryDocReferralRecordPage(int index, String s);


    @Override
    protected void otherViewClick(View view) {

    }


    public static BaseMDTfenzhenFragment newInstance(List<UnreadMessageEntity> allUnreadMessageList, OnPrescriptionChangeListener changeListener, int type) {
        BaseMDTfenzhenFragment myFragment = null;
        switch (type) {
            case 0:
                myFragment = new MDTfenzhenFragment1();
                break;
            case 1:
                myFragment = new MDTfenzhenFragment2();
                break;
            case 2:

        }
        listener = changeListener;
        return myFragment;
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


}
