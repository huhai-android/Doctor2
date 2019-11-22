package com.newdjk.doctor.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.tools.MainConstant;
import com.newdjk.doctor.ui.DiseaseInformationActivity;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.ui.entity.MDTOrderEntity;
import com.newdjk.doctor.ui.entity.MessageEventRecent;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MDTfenzhenFragment2 extends BaseMDTfenzhenFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    private String TAG = "MDTfenzhenFragment2";


    @Override
    protected void initListener() {
        super.initListener();

        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                Log.d(TAG, "价值更多");
                index++;
                QueryUnReceiveBuyRecordPage();
            }

            @Override
            public void onRefreshing() {
                index = 1;
                easylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
                QueryUnReceiveBuyRecordPage();
            }
        });


    }

    @Override
    protected void refreshView() {

    }


    //接单
    @Override
    protected void toDiseaseInfomation(int position) {
        if (dataList.get(position).getSubjectStatus()==2){
            return;
        }
        Intent intent = new Intent(getContext(), DiseaseInformationActivity.class);
        intent.putExtra(Contants.Type, 2);
        intent.putExtra(Contants.MDTINFO, dataList.get(position));
        startActivity(intent);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventRecent event) {

        Log.d(TAG, "收到新消息" + event.getmType());
        switch (event.getmType()) {
            case MainConstant.acceprtorrefuse:
                index = 1;
                QueryUnReceiveBuyRecordPage();
                break;
        }


    }


    @Override
    protected void QueryUnReceiveBuyRecordPage() {
        if (easylayout == null) {
            return;
        }
        if (index == 1) {
            dataList.clear();
        }
        Map<String, String> map = new HashMap<>();
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("TriageType", "1");
        Log.d(TAG, "医生id" + String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("PageIndex", index + "");
        map.put("PageSize", "10");

        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.QueryUnReceiveBuyRecordPage).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTOrderEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTOrderEntity> entity) {
                LoadDialog.clear();
                if (easylayout == null) {
                    return;
                }
                if (easylayout.isRefreshing()) {
                    easylayout.refreshComplete();
                }
                if (entity.getCode() == 0) {
                    List<MDTDetailEntity> list = entity.getData().getReturnData();
                    if (list == null) {
                        easylayout.refreshComplete();
                        easylayout.loadMoreComplete();
                        easylayout.setLoadMoreModel(LoadModel.NONE);
                    } else {
                        if (list.size() < 10) {
                            easylayout.closeLoadView();
                            easylayout.setLoadMoreModel(LoadModel.NONE);
                            dataList.addAll(entity.getData().getReturnData());
                            mOnlineRenewalDataAdapter.setNewData(dataList);
                            mOnlineRenewalDataAdapter.currentTime(mNowTime);
                            mOnlineRenewalDataAdapter.notifyDataSetChanged();
                        } else {
                            easylayout.closeLoadView();
                            dataList.addAll(list);
                            mOnlineRenewalDataAdapter.setNewData(dataList);
                        }
                    }
                } else {

                }
                if (index == 1) {
                    if (dataList.size() == 0) {
                        easylayout.setVisibility(View.GONE);
                        mNodataContainer.setVisibility(View.VISIBLE);
                    } else {
                        easylayout.setVisibility(View.VISIBLE);
                        mNodataContainer.setVisibility(View.GONE);
                    }
                }
                listener.onHaveCountChange(dataList.size());
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                if (easylayout == null) {
                    return;
                }
                if (easylayout.isRefreshing()) {
                    easylayout.refreshComplete();
                }
                if (index == 1) {
                    if (dataList.size() == 0) {
                        easylayout.setVisibility(View.GONE);
                        mNodataContainer.setVisibility(View.VISIBLE);
                    }

                }
                LoadDialog.clear();
                Log.d("errMsg", errorMsg);
                toast(errorMsg + "");
            }
        });

    }

    @Override
    protected void getQueryDocReferralRecordPage(int index, String s) {

    }
}
