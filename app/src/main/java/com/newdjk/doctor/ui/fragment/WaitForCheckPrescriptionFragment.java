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
import com.newdjk.doctor.ui.entity.SignReportSuccess;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaitForCheckPrescriptionFragment extends BaseCheckFragment {

    private static final String TAG = "WaitForCheck";

    @Override
    protected void getOnlinePartyList(final int index, String s) {
        if (index == 1) dataList.clear();
        if (mEasylayout == null) return;
        Map<String, String> map = new HashMap<>();
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("OrgId", String.valueOf(SpUtils.getInt(Contants.OrgId, 0)));
        //  map.put("DoctorName", SpUtils.getString(Contants.Name));
        map.put("Diagnoses", "");
        map.put("PrescriptionNo", "");
        map.put("Status", "0"); //状态（-1：全部，0：待审核，1：已审核，2：被驳回）（必填）
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
                                listener.onWaitForcheckchange(onlineRenewalMessageEntity.getTotal());
                                mOnlineRenewalDataAdapter.setNewData(dataList);
                            } else {
                                mEasylayout.closeLoadView();
                                int postion = mOnlineRenewalDataAdapter.getData().size();
                                dataList.addAll(list);
                                total=onlineRenewalMessageEntity.getTotal();
                                listener.onWaitForcheckchange(onlineRenewalMessageEntity.getTotal());
                                mOnlineRenewalDataAdapter.setNewData(dataList);

                            }
                        }
                    } else {
                        dataList.clear();
                        total=0;
                        listener.onWaitForcheckchange(0);
                        mNodataContainer.setVisibility(View.VISIBLE);
                        mEasylayout.setVisibility(View.GONE);
                    }
                } else {
                    dataList.clear();
                    total=0;
                    listener.onWaitForcheckchange(0);
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
    public void userEventBus(UpdatePushView userEvent) {
        int action = userEvent.action;
        switch (action) {
            case 5:
                getOnlinePartyList(index, "0");
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(SignReportSuccess signReportSuccess) {
        if (signReportSuccess.signResule) {
            //审核通过
            // getConsultRecordList("0");
            //当判定报告成功之后，需要将数据发送到已经判定界面
            dataList.remove(clickposition);
            mOnlineRenewalDataAdapter.notifyItemRemoved(clickposition);
            returnDataBean.setPass(true);
            EventBus.getDefault().post(returnDataBean);
            if (onlineRenewalMessageEntity != null) {
                total=total-1;
                listener.onWaitForcheckchange(total);
            }

        } else {
            //审核不通过
            dataList.remove(clickposition);
            mOnlineRenewalDataAdapter.notifyItemRemoved(clickposition);
            returnDataBean.setPass(false);
            EventBus.getDefault().post(returnDataBean);
            if (onlineRenewalMessageEntity != null) {
                total=total-1;
                listener.onWaitForcheckchange(total);
            }

        }

    }
}
