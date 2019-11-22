package com.newdjk.doctor.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.GroupSendHistoryAdapter;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.GroupSendEntity;
import com.newdjk.doctor.ui.entity.GroupsEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class GroupSendHistoryActivity extends BasicActivity {


    @BindView(R.id.mGroupSendBtn)
    TextView mGroupSendBtn;
    @BindView(R.id.mRecycleView)
    RecyclerView mRecycleView;
    @BindView(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
    @BindView(R.id.mDataContainer)
    LinearLayout mDataContainer;

    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;
    private int mPageIndex = 1;

    private List<GroupSendEntity.DataBean.ReturnDataBean> returnDataBeans = new ArrayList<>();
    private GroupSendHistoryAdapter adapter;

    @Override
    protected int initViewResId() {
        return R.layout.activity_group_send_history;
    }

    @Override
    protected void initView() {
        initTitle("群发历史").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initRecycleView();
    }

    private void initRecycleView() {

        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(layout);
        adapter = new GroupSendHistoryAdapter(returnDataBeans);
        mRecycleView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                GroupSendEntity.DataBean.ReturnDataBean item = (GroupSendEntity.DataBean.ReturnDataBean) adapter.getItem(position);
                if (item == null) return;
                sendGroupAgin(item);
            }
        });
    }

    /**
     * 再发一条消息
     *
     * @param item
     */
    private void sendGroupAgin(GroupSendEntity.DataBean.ReturnDataBean item) {
        Map<String, String> map = new HashMap<>();
        map.put("GroupMesId", item.getGroupMesId() + "");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization",SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(HttpUrl.ResendGroupMessage).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<GroupSendEntity>() {
            @Override
            public void onSuccess(int statusCode, GroupSendEntity entituy) {
                if (entituy.getCode() == 0) {

                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.e("zdp", "statusCode=" + statusCode + ",errorMsg=" + errorMsg);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Override
    protected void initListener() {
        mGroupSendBtn.setOnClickListener(this);

        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                mPageIndex++;
                queryHistorySendData(mPageIndex);
            }

            @Override
            public void onRefreshing() {
                mPageIndex = 1;
                queryHistorySendData(mPageIndex);
            }
        });

    }

    @Override
    protected void initData() {
        mPageIndex = 1;
        queryHistorySendData(mPageIndex);
    }

    private void queryHistorySendData(final int mPageIndex) {
        Integer DrId = (Integer) SpUtils.get(Contants.Id, 0);
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("DrId", DrId + "");
        bodyMap.put("PageIndex", mPageIndex + "");
        bodyMap.put("PageSize", "10");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization",SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.QueryGroupMessagePage).headers(headMap).params(bodyMap).tag(this).enqueue(new GsonResponseHandler<GroupSendEntity>() {
            @Override
            public void onSuccess(int statusCode, GroupSendEntity entituy) {
                if (mEasylayout == null) return;
                if (mEasylayout.isRefreshing()) mEasylayout.refreshComplete();
                if (mEasylayout.isLoading()) mEasylayout.closeLoadView();
                if (entituy.getCode() == 0) {
                    GroupSendEntity.DataBean data = entituy.getData();
                    if (data != null) {
                        int total = data.getTotal();
                        if (total > 0) {
                            mNodataContainer.setVisibility(View.GONE);
                            mEasylayout.setVisibility(View.VISIBLE);
                            List<GroupSendEntity.DataBean.ReturnDataBean> returnData = data.getReturnData();
                            if (returnData.size()<10) {
                                mEasylayout.setLoadMoreModel(LoadModel.NONE);
                            }  else {
                                mEasylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
                            }
                            if (mPageIndex == 1) {
                                returnDataBeans.clear();
                                returnDataBeans = returnData;
                                adapter.setNewData(returnDataBeans);
                                mEasylayout.refreshComplete();
                            } else {
                                mEasylayout.closeLoadView();
                                adapter.getData().addAll(returnData);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            mNodataContainer.setVisibility(View.VISIBLE);
                            mDataContainer.setVisibility(View.GONE);
                        }

                    }
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.e("zdp", "statusCode=" + statusCode + ",errorMsg=" + errorMsg);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.mGroupSendBtn:
                finish();
                break;
        }
    }
}
