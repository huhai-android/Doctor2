package com.newdjk.doctor.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ajguan.library.EasyRefreshLayout;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.ui.adapter.VideoInterrogationExpandAdapter;
import com.newdjk.doctor.ui.entity.AllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.InquiryRecordListEntity;
import com.newdjk.doctor.ui.entity.UpdateImMessageEntity;
import com.newdjk.doctor.ui.entity.VideoInterrogationEntity;
import com.newdjk.doctor.utils.SQLiteUtils;
import com.newdjk.doctor.views.AnimatedExpandableListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VideoInterrogationListFragment extends BasicFragment {


    @BindView(R.id.iv_no)
    ImageView ivNo;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;
    @BindView(R.id.interrogation_data)
    AnimatedExpandableListView interrogationData;
    @BindView(R.id.easylayout)
    EasyRefreshLayout easylayout;
    Unbinder unbinder;
    private VideoInterrogationExpandAdapter mVideoInterrogationExpandAdapter;
    private InquiryRecordListEntity mInquiryRecordListEntity;
    private List<AllRecordForDoctorEntity> dataList = new ArrayList<>();
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private Set<String> mTimeList;
    private  List<VideoInterrogationEntity> mList;
    private int sign = -1;

    public static VideoInterrogationListFragment newInstance() {
        VideoInterrogationListFragment myFragment = new VideoInterrogationListFragment();
        return myFragment;
    }

    @Override
    protected int initViewResId() {
        return R.layout.video_interrogation_list;
    }


    @Override
    protected void initView() {
        mTimeList = new HashSet<>();
        mList = new ArrayList<>();
        mVideoInterrogationExpandAdapter = new VideoInterrogationExpandAdapter(getContext());
        interrogationData.setAdapter(mVideoInterrogationExpandAdapter);

        /*mVideoInterrogationAdapter = new VideoInterrogationAdapter1(dataList,1);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerview.setAdapter(mVideoInterrogationAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        mEasylayout.setLoadMoreModel(LoadModel.NONE);*/
    }

    @Override
    protected void initListener() {
        interrogationData.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (sign == -1) {
                    // 展开被选的group
                    interrogationData.expandGroupWithAnimation(groupPosition);
                    // 设置被选中的group置于顶端
                    interrogationData.setSelectedGroup(groupPosition);
                    sign = groupPosition;
                } else if (sign == groupPosition) {
                    interrogationData.collapseGroupWithAnimation(sign);
                    sign = -1;
                } else {
                    interrogationData.collapseGroupWithAnimation(sign);
                    // 展开被选的group
                    interrogationData.expandGroupWithAnimation(groupPosition);
                    // 设置被选中的group置于顶端
                    interrogationData.setSelectedGroup(groupPosition);
                    sign = groupPosition;
                }
                return true;
            }
        });
        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {

                getInquiryRecord();
            }

            @Override
            public void onRefreshing() {

                getInquiryRecord();
            }
        });
    }


    @Override
    protected void initData() {

        getInquiryRecord();


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

    private void getInquiryRecord() {
        mList.clear();
        mTimeList.clear();
        List<AllRecordForDoctorEntity> list = SQLiteUtils.getInstance().selectImDataByServiceCodeAndStatus("1102", 0);
          if (list.size() >0) {
              Date curDate = new Date(System.currentTimeMillis());
              String str  = mFormatter.format(curDate);
              for (int i = 0; i < list.size(); i++) {
                  AllRecordForDoctorEntity allRecordForDoctorEntity = list.get(i);
                  String dateTime = allRecordForDoctorEntity.getDateTime();
                  mTimeList.add(dateTime);
              }
              for (String dateTime : mTimeList) {
                  try {
                      List<AllRecordForDoctorEntity> list1 = SQLiteUtils.getInstance().selectImDataByServiceCodeAnDStatueAndDateTime("1102", 0, dateTime);

                      VideoInterrogationEntity videoInterrogationEntity = new VideoInterrogationEntity();
                      videoInterrogationEntity.setAllRecordForDoctorEntity(list1);
                      videoInterrogationEntity.setNumber(list1.size());

                      if (dateTime.equals(str)) {
                          dateTime = "今日任务";
                          videoInterrogationEntity.setDateTime(dateTime);
                          mList.add(0,videoInterrogationEntity);
                      }
                      else {
                          videoInterrogationEntity.setDateTime(dateTime);
                          mList.add(videoInterrogationEntity);
                      }
                  }catch (Exception e){

                  }


              }


          }
        if (easylayout == null) return;
        if (easylayout.isRefreshing())  easylayout.refreshComplete();
        if (mList.size() >0) {
            mNodataContainer.setVisibility(View.GONE);
            easylayout.setVisibility(View.VISIBLE);
            mVideoInterrogationExpandAdapter.refreshData(mList);
            interrogationData.expandGroupWithAnimation(0);
            sign= 0;
        }
        else {
            mNodataContainer.setVisibility(View.VISIBLE);
            easylayout.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateImMessageEntity userEvent) {
        // GetAllRecordForDoctor();
        //  refreshView(userEvent.ImId);
        getInquiryRecord();
    }


}
