package com.newdjk.doctor.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.google.gson.Gson;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.MessageAdapter;
import com.newdjk.doctor.ui.entity.PushDataDaoEntity;
import com.newdjk.doctor.ui.entity.UpdatePositionEntity;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.utils.SQLiteUtils;
import com.newdjk.doctor.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PushListActivity extends BasicActivity {
    private static final String TAG = "PushListActivity";
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
    @BindView(R.id.message_recycler_view)
    RecyclerView messageRecyclerView;
    @BindView(R.id.iv_no)
    ImageView ivNo;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;
    @BindView(R.id.easylayout)
    EasyRefreshLayout easylayout;
    private MessageAdapter mAdapter;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int mId = 0;
    private Gson mGson = new Gson();
    private String mAction;
    private List<PushDataDaoEntity> mPushDataList;
    private Date mTime;

    @Override
    protected int initViewResId() {
        return R.layout.push_list;
    }

    @Override
    protected void initView() {
        mTime = new Date();
        mAction = getIntent().getStringExtra("action");
        if (mAction != null && mAction.equals("system")) {
            initBackTitle("系统消息");
        } else {
            initBackTitle("新报道患者");
        }
        mPushDataList = new ArrayList<>();
        mAdapter = new MessageAdapter(mPushDataList, this, mAction);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        messageRecyclerView.setAdapter(mAdapter);
        messageRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initListener() {
        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                getPushList(mAction);
            }

            @Override
            public void onRefreshing() {
                mPushDataList.clear();
                mTime = new Date();
                getPushList(mAction);
            }
        });
    }

    @Override
    protected void initData() {

        mId = SpUtils.getInt(Contants.Id, 0);
        getPushList(mAction);
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
    }

    private void getPushList(String action) {
        List<PushDataDaoEntity> list = new ArrayList<>();
//        if (action.equals("system")) {
//            list = SQLiteUtils.getInstance().selectSystemContactsByDoctorId(mId, mTime);
//        }
//        else  if (action.equals("newReport")) {
//            list = SQLiteUtils.getInstance().selectNewReportContactsByDoctorId(mId, mTime);
//        }
        list = SQLiteUtils.getInstance().selectSystemContactsByDoctorId(mId, mTime);

        for (int i = 0; i <list.size() ; i++) {
            Log.d(TAG,list.get(i).toString());
        }
        mPushDataList.addAll(list);
        if (mPushDataList.size() > 0) {
            if (list.size() == 0) {
                toast("没有更多数据");
            }
            mTime = mPushDataList.get(mPushDataList.size() - 1).getTime();
            mNodataContainer.setVisibility(View.GONE);
            messageRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.resetData(mPushDataList);
            mAdapter.notifyDataSetChanged();
        } else {
            mNodataContainer.setVisibility(View.VISIBLE);
            messageRecyclerView.setVisibility(View.GONE);
        }
        if (easylayout.isRefreshing()) easylayout.refreshComplete();
        if (easylayout.isLoading()) easylayout.closeLoadView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdatePushView userEvent) {
//        mPushDataList.clear();
//        mTime = new Date();
      //  getPushList(mAction);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdatePositionEntity event) {
        mPushDataList.get(event.getPosition()).setIsRead(true);
        mAdapter.notifyItemChanged(event.getPosition());
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
