package com.newdjk.doctor.ui.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.DiseaseInformationActivity;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.ui.entity.MDTfenzhenReportEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MDTfenzhenReportFragment1 extends BaseMDTReportFragment {


    private static final String TAG = "MDTfenzhenReportFragment1";


    @Override
    protected void initListener() {
        super.initListener();
        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                index++;
                getQueryDocReferralRecordPage(index, "1");
            }

            @Override
            public void onRefreshing() {
                index = 1;
                easylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
                getQueryDocReferralRecordPage(index, "1");
            }
        });
    }

    @Override
    protected void refreshView() {

    }

    //抢单
    @Override
    protected void toDiseaseInfomation() {
        Intent intent=new Intent(getContext(), DiseaseInformationActivity.class);
        intent.putExtra(Contants.Type,"1");
        startActivity(intent);
    }


    @Override
    protected void getQueryDocReferralRecordPage(final int index, String type) {

        if (easylayout == null) {
            return;
        }
        if (index == 1) {
            dataList.clear();
        }
        Map<String, String> map = new HashMap<>();
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("CompleteStat", "" + type);
        map.put("PageIndex", index + "");
        map.put("PageSize", "10");

        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.QueryDoctorMDTReportPage).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTfenzhenReportEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTfenzhenReportEntity> entity) {
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
                            mOnlineRenewalDataAdapter.notifyDataSetChanged();
                        } else {
                            easylayout.closeLoadView();
                            dataList.addAll(list);
                            mOnlineRenewalDataAdapter.setNewData(dataList);
                        }
                    }
                    listener.onWaitForcheckchange(entity.getData().getTotal());
                } else {

                }
                if (index==1){
                    if (dataList.size()==0){
                        easylayout.setVisibility(View.GONE);
                        mNodataContainer.setVisibility(View.VISIBLE);
                    }
                    else {
                        easylayout.setVisibility(View.VISIBLE);
                        mNodataContainer.setVisibility(View.GONE);
                    }

                }


            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                if (easylayout == null) {
                    return;
                }
                if (easylayout.isRefreshing()) {
                    easylayout.refreshComplete();
                }
                if (index==1){
                    if (dataList.size()==0){
                        easylayout.setVisibility(View.GONE);
                        mNodataContainer.setVisibility(View.VISIBLE);
                    }

                }
                LoadDialog.clear();
                Log.d("errMsg", errorMsg);
                //toast(errorMsg + "");
            }
        });

    }
}
