package com.newdjk.doctor.ui.fragment;

import android.util.Log;
import android.view.View;

import com.ajguan.library.LoadModel;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.PrescriptionEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.TimeUtil;
import com.newdjk.doctor.views.LoadDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HaveCheckPrescriptionFragment extends BaseCheckFragment {
    private static final String TAG = "HaveCheckPrescription";


    @Override
    protected void getOnlinePartyList(int index, String s) {
        if (mEasylayout == null) return;
        if (index == 1) dataList.clear();
        Map<String, String> map = new HashMap<>();
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("OrgId", String.valueOf(SpUtils.getInt(Contants.OrgId, 0)));
        //  map.put("DoctorName", SpUtils.getString(Contants.Name));
        map.put("Diagnoses", "");
        map.put("PrescriptionNo", "");
        map.put("Status", "1"); //状态（-1：全部，0：待审核，1：已审核，2：被驳回）（必填）
        map.put("AuditorId", String.valueOf(SpUtils.getInt(Contants.Id, 0))); //状态（-1：全部，0：待审核，1：已审核，2：被驳回）（必填）
        Log.d(TAG, "医生id" + String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("PageIndex", index + "");
        map.put("PageSize", "10");

        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.PrescriptionCheckList).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<PrescriptionEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<PrescriptionEntity> entity) {
                LoadDialog.clear();
                if (mEasylayout == null) return;
                if (mEasylayout.isRefreshing()) mEasylayout.refreshComplete();
                if (entity.getCode() == 0) {
                    onlineRenewalMessageEntity = entity.getData();
                    Log.d(TAG, onlineRenewalMessageEntity.toString());
                    if (onlineRenewalMessageEntity != null) {
                        mNodataContainer.setVisibility(View.GONE);
                        mEasylayout.setVisibility(View.VISIBLE);
                        List<PrescriptionEntity.ReturnDataBean> list = onlineRenewalMessageEntity.getReturnData();
                        if (list == null) {
                            mEasylayout.setLoadMoreModel(LoadModel.NONE);
                        } else {
                            if (list.size() < 10) {
                                mEasylayout.closeLoadView();
                                mEasylayout.setLoadMoreModel(LoadModel.NONE);
                                int postion = mOnlineRenewalDataAdapter.getData().size();
                                dataList.addAll(list);
                                total=onlineRenewalMessageEntity.getTotal();
                                listener.onHaveCountChange(onlineRenewalMessageEntity.getTotal());
                                mOnlineRenewalDataAdapter.setNewData(dataList);
                            } else {
                                mEasylayout.closeLoadView();
                                int postion = mOnlineRenewalDataAdapter.getData().size();
                                dataList.addAll(list);
                                total=onlineRenewalMessageEntity.getTotal();
                                listener.onHaveCountChange(onlineRenewalMessageEntity.getTotal());
                                mOnlineRenewalDataAdapter.setNewData(dataList);
                            }
                        }
                    } else {
                        dataList.clear();
                        total=0;
                        listener.onHaveCountChange(0);
                        mNodataContainer.setVisibility(View.VISIBLE);
                        mEasylayout.setVisibility(View.GONE);
                    }
                } else {
                    dataList.clear();
                    total=0;
                    listener.onHaveCountChange(0);
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(PrescriptionEntity.ReturnDataBean returnDataBean) {
        if (returnDataBean != null) {
            Log.d(TAG, "收到审核结果消息" + returnDataBean.isPass());
            if (returnDataBean.isPass()) {
                returnDataBean.setUpdateTime(TimeUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
                dataList.add(0, returnDataBean);
                mOnlineRenewalDataAdapter.notifyItemChanged(0);
                if (onlineRenewalMessageEntity != null) {
                    total=total+1;
                    listener.onHaveCountChange(total);
                }

            }

        }

    }
}
