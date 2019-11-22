package com.newdjk.doctor.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.ui.adapter.MianzhenAdapter;
import com.newdjk.doctor.ui.entity.MianzhenListEntity;
import com.newdjk.doctor.ui.entity.MianzhenSuccessEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.HeadUitl;
import com.newdjk.doctor.views.MianzhenDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.fragment
 *  @文件名:   ChatFragment1
 *  @创建者:   huhai
 *  @创建时间:  2019/4/17 10:57
 *  @描述：
 */
public class MianzhenFragment1 extends BaseMianzhenFragment {

    private static final String TAG = "MianzhenFragment1";
    public int total = 0;
    private MianzhenDialog mDialog;

    @Override
    protected void initData() {
        super.initData();

        //面诊预约状态。0-待就诊,1-已完成。（不传或者-1 不过滤）
        GetDrFaceConsultationRecord(index, 0);
    }




    @Override
    protected void initListener() {
        super.initListener();
        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                Log.d(TAG, "价值更多");
                index++;
                GetDrFaceConsultationRecord(index, 0);
            }

            @Override
            public void onRefreshing() {
                index = 1;
                easylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
                GetDrFaceConsultationRecord(index, 0);
            }
        });

        mMianzhenAdapterr.addOnCancelListener(new MianzhenAdapter.OnCancelListener() {
            @Override
            public void cancel(int positionout, int positionInside) {
                Log.d(TAG, "点击外部" + positionout + " 内部" + positionInside);
                //showdialog(positionout, positionInside);
            }
        });


    }


    @Override
    public void GetDrFaceConsultationRecord(int pageindex, int AppointmentStatus) {
        if (index == 1) {
            dataList.clear();
        }
        Map<String, String> params = new HashMap<>();
        params.put("AppointmentStatus", AppointmentStatus + "");
        params.put("DrId", MyApplication.mDoctorInfoByIdEntity.getDrId() + "");
        params.put("PageIndex", index + "");
        params.put("PageSize", "10");
        //  params.put("DrId", "68");
        // String json = mGson.toJson(params);

        mMyOkhttp.post().url(HttpUrl.GetDrFaceConsultationRecord).params(params).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MianzhenListEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MianzhenListEntity> entity) {
                if (easylayout == null) {
                    return;
                }
                if (easylayout.isRefreshing()) {
                    easylayout.refreshComplete();
                }
                if (entity.getCode() == 0) {
                    MianzhenListEntity onlineRenewalMessageEntity = entity.getData();
                    Log.d(TAG, onlineRenewalMessageEntity.toString());
                    if (onlineRenewalMessageEntity != null) {

                        List<MianzhenListEntity.ReturnDataBean> list = onlineRenewalMessageEntity.getReturnData();
                        if (list == null) {
                            easylayout.setLoadMoreModel(LoadModel.NONE);
                        } else {
                            if (list.size() < 10) {
                                easylayout.closeLoadView();
                                easylayout.setLoadMoreModel(LoadModel.NONE);
                                dataList.addAll(list);
                                mMianzhenAdapterr.setNewData(dataList);
                            } else {
                                easylayout.closeLoadView();
                                dataList.addAll(list);
                                total = onlineRenewalMessageEntity.getTotal();
                                mMianzhenAdapterr.setNewData(dataList);
                            }
                        }
                    } else {

                    }
                } else {
                    toast(entity.getMessage());

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
                Log.d("errMsg", errorMsg);
                toast(errorMsg + "");
            }
        });

    }

    public static Fragment getFragment() {

        return new MianzhenFragment1();
    }

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(MianzhenSuccessEntity mianzhenSuccessEntity) {
        Log.d(TAG,"收到消息取消预约成功");
        index=1;
        GetDrFaceConsultationRecord(index, 0);

    }
}

