package com.newdjk.doctor.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.TaskAdapterAdapter;
import com.newdjk.doctor.ui.entity.InquiryRecordListEntity;
import com.newdjk.doctor.ui.entity.MianzhenSuccessEntity;
import com.newdjk.doctor.ui.entity.RefeshTaskListEntity;
import com.newdjk.doctor.ui.entity.TaskEntity;
import com.newdjk.doctor.ui.entity.UpdateImMessageEntity;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;

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

public class TaskFragment1 extends BasicFragment {

    private static final String TAG = "TaskFragment1";
    @BindView(R.id.mRecyclerview)
    RecyclerView mRecyclerview;
    Unbinder unbinder;
    @BindView(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;
    private TaskAdapterAdapter mVideoInterrogationAdapter;
    private InquiryRecordListEntity mInquiryRecordListEntity;
    private List<TaskEntity.DataBean> datalist = new ArrayList<>();
    private boolean isrefresh = false;
    private static final int LOADING_SUCCESS = 2;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADING_SUCCESS:

                    if (!isrefresh) {
                        datalist.clear();
                        QueryUnReceiveDoctorOrderMerge();
                    }
                    break;

            }
        }
    };


    public static TaskFragment1 newInstance() {
        Bundle args = new Bundle();
        TaskFragment1 myFragment = new TaskFragment1();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    protected int initViewResId() {
        return R.layout.consult_list;
    }


    @Override
    protected void initView() {
        mVideoInterrogationAdapter = new TaskAdapterAdapter(datalist, 2);
        //mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerview.setAdapter(mVideoInterrogationAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        if (mEasylayout != null) {
            mEasylayout.setLoadMoreModel(LoadModel.NONE);

        }
    }

    @Override
    protected void initListener() {
        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {


            }

            @Override
            public void onRefreshing() {
                datalist.clear();
                QueryUnReceiveDoctorOrderMerge();
            }
        });


        mVideoInterrogationAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.im_arrow) {
                    if (datalist.size() > 0) {
                        if (datalist.get(position).isOpen()) {
                            datalist.get(position).setOpen(false);
                        } else {
                            datalist.get(position).setOpen(true);
                        }
                        mVideoInterrogationAdapter.notifyItemChanged(position);
                    }

                }
            }
        });
        mVideoInterrogationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (datalist.size()>0){
                    if (datalist.get(position).isOpen()) {
                        datalist.get(position).setOpen(false);
                    } else {
                        datalist.get(position).setOpen(true);
                    }
                    mVideoInterrogationAdapter.notifyItemChanged(position);
                }

            }
        });

    }


    @Override
    protected void initData() {
        Log.d(TAG, "执行网络请求");
        QueryUnReceiveDoctorOrderMerge();

    }

    private void QueryUnReceiveDoctorOrderMerge() {
        isrefresh = true;
        loading(true);
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(HttpUrl.QueryUnReceiveDoctorOrderMerge).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<TaskEntity>() {
            @Override
            public void onSuccess(int statusCode, TaskEntity entity) {
                LoadDialog.clear();
                if (mEasylayout == null) {
                    return;
                }
                if (mEasylayout.isRefreshing()) {
                    mEasylayout.refreshComplete();
                }
                if (entity != null) {
                    if (entity.getCode() == 0) {
                        if (entity.getData() != null) {

                            datalist.addAll(entity.getData());
                            mVideoInterrogationAdapter.setNewData(datalist);
                            if (datalist.size() > 0) {
                                mNodataContainer.setVisibility(View.GONE);
                            } else {
                                mNodataContainer.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                }

                if (datalist.size() == 0) {
                    mNodataContainer.setVisibility(View.VISIBLE);
                } else {
                    mNodataContainer.setVisibility(View.GONE);
                }

                isrefresh = false;
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                LoadDialog.clear();
                isrefresh = false;
                if (mEasylayout == null) {
                    return;
                }
                if (mEasylayout != null) {
                    mEasylayout.refreshComplete();
                }
                if (datalist.size() == 0) {
                    mNodataContainer.setVisibility(View.VISIBLE);
                } else {
                    mNodataContainer.setVisibility(View.GONE);
                }
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
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


    //刷新数据使用
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(RefeshTaskListEntity mianzhenSuccessEntity) {
        Log.d(TAG, "收到消息1");
        if (!isrefresh) {
            datalist.clear();
            QueryUnReceiveDoctorOrderMerge();
        }


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UpdatePushView event) {
        Log.d(TAG, "收到消息2");
        mHandler.sendEmptyMessageDelayed(LOADING_SUCCESS, 1500);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(MianzhenSuccessEntity mianzhenSuccessEntity) {
        Log.d(TAG, "收到消息3");
        if (!isrefresh) {
            datalist.clear();
            QueryUnReceiveDoctorOrderMerge();
        }

    }


    //收到消息，更新首页红点显示
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateImMessageEntity userEvent) {
        Log.d(TAG, "收到消息4" + "" + userEvent.serviceCode);

        if (!"-1".equals(userEvent.serviceCode)) {
            if (!isrefresh) {
                datalist.clear();
                QueryUnReceiveDoctorOrderMerge();
            }
        }


    }

}