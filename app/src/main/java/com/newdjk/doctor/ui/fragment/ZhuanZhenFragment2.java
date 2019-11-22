package com.newdjk.doctor.ui.fragment;

import android.util.Log;
import android.view.View;

import com.ajguan.library.LoadModel;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.ZhanZhenListEntity;
import com.newdjk.doctor.ui.entity.ZhuanhuiSuccess;
import com.newdjk.doctor.ui.entity.ZhuanzhenSuccess;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZhuanZhenFragment2 extends BaseZhuanZhenFragment {

    private static final String TAG = "ZhuanZhenFragment2";

    @Override
    protected void refreshView() {

        reScanContainer.setVisibility(View.GONE);

    }

    @Override
    protected void refuseZhuanzhen(String referralRecordId, int position) {
        Map<String, String> map = new HashMap<>();
        map.put("ReferralRecordId", referralRecordId);
        //  map.put("DoctorName", SpUtils.getString(Contants.Name));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.SaveReferralRecordBack).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity entity) {
                try {
                    if (entity.getCode() == 0) {
                        boolean iszhuanhui = (boolean) entity.getData();
                        if (iszhuanhui) {
                            toast("转回成功");
                            getQueryDocReferralRecordPage(1, "0");
                            EventBus.getDefault().post(new ZhuanhuiSuccess(true));

                        }

                    } else {
                        toast(entity.getMessage() + "");
                    }
                } catch (Exception e) {
                    toast("转回失败");
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });


    }

    @Override
    protected void getQueryDocReferralRecordPage(final int index, String s) {
        if (index == 1) {
            dataList.clear();
        }
        Map<String, String> map = new HashMap<>();
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("ReferralType", "0");
        //  map.put("DoctorName", SpUtils.getString(Contants.Name));
        Log.d(TAG, "医生id" + String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("PageIndex", index + "");
        map.put("PageSize", "10");
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.QueryDocReferralRecordPage).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ZhanZhenListEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ZhanZhenListEntity> entity) {
                LoadDialog.clear();

                if (mEasylayout == null) {
                    return;
                }
                mEasylayout.refreshComplete();
                mEasylayout.loadMoreComplete();
                if (entity.getCode() == 0) {
                    onlineRenewalMessageEntity = entity.getData();

                    if (onlineRenewalMessageEntity != null) {
                        mNodataContainer.setVisibility(View.GONE);
                        mEasylayout.setVisibility(View.VISIBLE);
                        List<ZhanZhenListEntity.ReturnDataBean> list = onlineRenewalMessageEntity.getReturnData();
                        if (list == null) {
                            mEasylayout.setLoadMoreModel(LoadModel.NONE);
                        } else {
                            if (list.size() < 10) {
                                mEasylayout.closeLoadView();
                                mEasylayout.setLoadMoreModel(LoadModel.NONE);
                                int postion = mOnlineRenewalDataAdapter.getData().size();
                                dataList.addAll(list);
                                total = onlineRenewalMessageEntity.getTotal();
                                listener.onHaveCountChange(onlineRenewalMessageEntity.getTotal());
                                mOnlineRenewalDataAdapter.setNewData(dataList);
                            } else {
                                mEasylayout.closeLoadView();
                                int postion = mOnlineRenewalDataAdapter.getData().size();
                                dataList.addAll(list);
                                total = onlineRenewalMessageEntity.getTotal();
                                listener.onHaveCountChange(onlineRenewalMessageEntity.getTotal());
                                mOnlineRenewalDataAdapter.setNewData(dataList);

                            }
                        }
                    } else {
                        dataList.clear();
                        total = 0;
                        listener.onHaveCountChange(0);
                        mNodataContainer.setVisibility(View.VISIBLE);
                        mEasylayout.setVisibility(View.GONE);
                    }

                    if (total == 0) {
                        dataList.clear();
                        listener.onHaveCountChange(0);
                        mNodataContainer.setVisibility(View.VISIBLE);
                        mEasylayout.setVisibility(View.GONE);
                    }

                } else {
                    dataList.clear();
                    total = 0;
                    listener.onHaveCountChange(0);
                    mNodataContainer.setVisibility(View.VISIBLE);
                    mEasylayout.setVisibility(View.GONE);
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                mNodataContainer.setVisibility(View.VISIBLE);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(ZhuanzhenSuccess zhuanzhenSuccess) {
        Log.d(TAG, "收到转诊成功消息，切换界面");
        if (zhuanzhenSuccess.isZhenzhuan()) {

            getQueryDocReferralRecordPage(index, "0");
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(ZhuanhuiSuccess zhuanzhenSuccess) {
        Log.d(TAG, "转回成功");
        if (zhuanzhenSuccess.isZhenzhuan()) {

            getQueryDocReferralRecordPage(index, "0");
        }

    }
}
