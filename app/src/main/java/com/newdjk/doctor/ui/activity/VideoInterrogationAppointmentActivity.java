package com.newdjk.doctor.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.VideoInterrogationAdapter;
import com.newdjk.doctor.ui.adapter.VideoInterrogationAdapter1;
import com.newdjk.doctor.ui.entity.AllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.DoctorPatientRelationEntity;
import com.newdjk.doctor.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.doctor.ui.entity.InquiryRecordListEntity;
import com.newdjk.doctor.ui.entity.PatientInfoEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.AppUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VideoInterrogationAppointmentActivity extends BasicActivity {

    @BindView(R.id.mRecyclerview)
    RecyclerView mRecyclerview;
    Unbinder unbinder;
    @BindView(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
    @BindView(R.id.top_left)
    ImageView topLeft;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.top_right)
    ImageView topRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.relat_titlebar)
    RelativeLayout relatTitlebar;
    @BindView(R.id.liear_titlebar)
    LinearLayout liearTitlebar;
    @BindView(R.id.iv_no)
    ImageView ivNo;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;
    private VideoInterrogationAdapter1 mVideoInterrogationAdapter;
    private int mId;
    private List<AllRecordForDoctorEntity> dataList = new ArrayList();
    private int index;

    @Override
    protected int initViewResId() {
        return R.layout.video_interrogation_appointment;
    }

    @Override
    protected void initView() {
        mId = getIntent().getIntExtra("id", 0);

        initBackTitle(getString(R.string.interrogation_list));
        mVideoInterrogationAdapter = new VideoInterrogationAdapter1(dataList,3);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerview.setAdapter(mVideoInterrogationAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
    }

    @Override
    protected void initListener() {
        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                index++;
                getInquiryRecord(index, "0");
            }

            @Override
            public void onRefreshing() {
                index = 1;
                getInquiryRecord(index, "0");
            }
        });
    }

    @Override
    protected void initData() {
        index = 1;
        getInquiryRecord(index, "0");
    }

    @Override
    protected void otherViewClick(View view) {
//        switch (view.getId()) {
//            case R.id.click_refresh :
//                refreshLayout.setRefreshing(true);
//                getInquiryRecord("0");
//                break;
//        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void getInquiryRecord(final int index, String chatStatus) {
        Map<String, String> map = new HashMap<>();
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("ChatStatus", chatStatus);
        map.put("PageIndex", index + "");
        map.put("PageSize", "10");
        if (mId != 0) {
            map.put("DoctorDutyId", String.valueOf(mId));
        }
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization",SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetInquiryRecordList).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<InquiryRecordListEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<InquiryRecordListEntity> entity) {
                if (entity.getCode() == 0) {
                    InquiryRecordListEntity inquiryRecordListEntity = entity.getData();
                    if (inquiryRecordListEntity != null  && inquiryRecordListEntity.getTotal() > 0) {
                            mNodataContainer.setVisibility(View.GONE);
                            mRecyclerview.setVisibility(View.VISIBLE);
                            List<InquiryRecordListDataEntity> list = inquiryRecordListEntity.getReturnData();
                             List<AllRecordForDoctorEntity> list1 = new ArrayList<>();
                            if (list != null && list.size() > 0) {
                                if (list.size()<10)   mEasylayout.setLoadMoreModel(LoadModel.NONE);
                                for (int k = 0; k < list.size(); k++) {
                                    InquiryRecordListDataEntity inquiryInfoListBean = list.get(k);

                                    String Content = inquiryInfoListBean.getContent();
                                    String PatientName = inquiryInfoListBean.getPatientName();
                                    String ApplicantHeadImgUrl = inquiryInfoListBean.getApplicantHeadImgUrl();
                                    String EndTime = inquiryInfoListBean.getEndTime();
                                    String PayTime = inquiryInfoListBean.getPayTime();
                                    String CreateTime = inquiryInfoListBean.getCreateTime();
                                    int RecordId = inquiryInfoListBean.getId();
                                    int Type = inquiryInfoListBean.getType();
                                    int Status = inquiryInfoListBean.getStatus();
                                    String DealWithTime = inquiryInfoListBean.getDealWithTime();
                                    String StartTime = inquiryInfoListBean.getStartTime();
                                    String ApplicantIMId = inquiryInfoListBean.getApplicantIMId();
                                    TIMConversation conversation = TIMManager.getInstance().getConversation(
                                            TIMConversationType.C2C,
                                            ApplicantIMId);
                                    long unReadNum = 0;
                                    long timeStamp = 0;
                                    if (conversation != null) {
                                        unReadNum = AppUtils.getUnreadMessageNum(conversation);
                                        List<TIMMessage> lastMsgs = conversation.getLastMsgs(1);

                                        if (lastMsgs != null && lastMsgs.size() > 0) {
                                            TIMMessage msg = lastMsgs.get(0);
                                            timeStamp = msg.timestamp();
                                        }
                                    }


                                    String ReExaminationDate = inquiryInfoListBean.getReExaminationDate();
                                    String ReExaminationStartTime = inquiryInfoListBean.getReExaminationStartTime();
                                    String ReExaminationEndTime = inquiryInfoListBean.getReExaminationEndTime();


                                    String Age = null;
                                    String AreaName = null;
                                    int PatientSex = 0;
                                    int IsDrKey = 0;
                                    int IsPatientMain = 0;
                                    String Disease = null;
                                    PatientInfoEntity PatientInfoBean = inquiryInfoListBean.getPatientInfo();
                                    DoctorPatientRelationEntity DoctorPatientRelationBean = inquiryInfoListBean.getDoctorPatientRelation();
                                    if (PatientInfoBean != null) {
                                        Age = PatientInfoBean.getAge();
                                        AreaName = PatientInfoBean.getAreaName();
                                        PatientSex = PatientInfoBean.getPatientSex();
                                    }
                                    if (DoctorPatientRelationBean != null) {
                                        IsDrKey = DoctorPatientRelationBean.getIsDrKey();
                                        IsPatientMain = DoctorPatientRelationBean.getIsPatientMain();
                                        Disease = DoctorPatientRelationBean.getDisease();
                                    }
                                    String ServiceCode = "1102";
                                    int ApplicantId = inquiryInfoListBean.getApplicantId();
                                    String RecordData = new Gson().toJson(inquiryInfoListBean);
                                    int PatientId = inquiryInfoListBean.getPatientId();

                                    AllRecordForDoctorEntity allRecordForDoctorEntity =    new AllRecordForDoctorEntity(null, Content, PatientName, ApplicantHeadImgUrl, EndTime, PayTime, CreateTime, RecordId, Type, Status, DealWithTime, StartTime, ApplicantIMId, unReadNum, null, 0, ReExaminationDate, ReExaminationStartTime, ReExaminationEndTime, Age, AreaName, PatientSex, IsDrKey, IsPatientMain, Disease, ServiceCode, timeStamp, RecordData, ApplicantId, PatientId,null);
                                    list1.add(allRecordForDoctorEntity);
                                }
                                if (index == 1) {
                                    mEasylayout.refreshComplete();
                                    dataList.clear();
                                    dataList.addAll(list1);
                                    mVideoInterrogationAdapter.setNewData(dataList);
                                } else {
                                    mEasylayout.closeLoadView();
                                    dataList.addAll(list1);
                                    mVideoInterrogationAdapter.getData().addAll(dataList);
                                    mVideoInterrogationAdapter.notifyDataSetChanged();
                                }

                            } else {
                                mNodataContainer.setVisibility(View.VISIBLE);
                                mRecyclerview.setVisibility(View.GONE);
                            }
                        } else {
                        mNodataContainer.setVisibility(View.VISIBLE);
                        mRecyclerview.setVisibility(View.GONE);
                    }
                } else {
                    toast(entity.getMessage());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }
}
