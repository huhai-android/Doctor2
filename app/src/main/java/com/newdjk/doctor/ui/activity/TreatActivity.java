package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.MyFragmentAdapter;
import com.newdjk.doctor.ui.entity.DoctorMedicalStatisticsEntity;
import com.newdjk.doctor.ui.entity.InquiryRecordListEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UnreadMessageEntity;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.ui.entity.UpdateTreatMessageEntity;
import com.newdjk.doctor.ui.fragment.DiagnosisAndTreatFragment;
import com.newdjk.doctor.ui.fragment.DiagnosisAndTreatFragment1;
import com.newdjk.doctor.ui.fragment.DiagnosisAndTreatFragment2;
import com.newdjk.doctor.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TreatActivity extends BasicActivity {


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
    @BindView(R.id.cumulative_report)
    TextView cumulativeReport;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private MyFragmentAdapter adapter;
    private InquiryRecordListEntity mInquiryRecordListEntity;
    private List<Fragment> mList;
    private String tabTitles[];
    private static boolean mIsReflash = false;
    private DiagnosisAndTreatFragment mDiagnosisAndTreatFragment;
    private DiagnosisAndTreatFragment1 mDiagnosisAndTreatFragment1;
    private DiagnosisAndTreatFragment2 mDiagnosisAndTreatFragment2;
    private int mConsultingNum;
    private int mDoctype;
    private int mConsultNum;

    @Override
    protected int initViewResId() {
        return R.layout.diagnosis_and_treat;
    }

    @Override
    protected void initView() {
        mDoctype = SpUtils.getInt(Contants.DocType, 0);
        mList = new ArrayList<>();
        List<UnreadMessageEntity> allUnreadMessageList = (List<UnreadMessageEntity>) getIntent().getSerializableExtra("allUnReadList");
        mDiagnosisAndTreatFragment = DiagnosisAndTreatFragment.newInstance(allUnreadMessageList);
        mList.add(mDiagnosisAndTreatFragment);
        mDiagnosisAndTreatFragment1 = DiagnosisAndTreatFragment1.newInstance(allUnreadMessageList);
        mList.add(mDiagnosisAndTreatFragment1);
        mDiagnosisAndTreatFragment2 = DiagnosisAndTreatFragment2.newInstance(allUnreadMessageList);
        mList.add(mDiagnosisAndTreatFragment2);
        if (mDoctype == 1) {
            initBackTitle(getString(R.string.diagnosis_and_treatment)).setRightText(getString(R.string.set));
            tabTitles = new String[]{"待接单", "进行中", "已完成"};
        } else if (mDoctype == 2) {
            tabTitles = new String[]{"待咨询", "咨询中", "已完成"};
            initBackTitle("护理咨询记录").setRightText(getString(R.string.set));
        }
        adapter = new MyFragmentAdapter(getSupportFragmentManager(), TreatActivity.this, mList);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(2);
        tvRight.setOnClickListener(this);
        tab.setupWithViewPager(viewpager);

        for (int i = 0; i < adapter.getCount(); i++) {
            TabLayout.Tab tab1 = tab.getTabAt(i);//获得每一个tab
            tab1.setCustomView(R.layout.custom_tab);//给每一个tab设置view
          /*  if (i == 0) {
                // 设置第一个tab的TextView是被选择的样式
                tab1.getCustomView().findViewById(R.id.tab_text).setSelected(true);//第一个tab被选中
            }*/
            TextView title = tab1.getCustomView().findViewById(R.id.tab_title);
            RelativeLayout unReadLayout = tab1.getCustomView().findViewById(R.id.unRead_layout);
            TextView unreadNum = tab1.getCustomView().findViewById(R.id.unread_num);
            if (tabTitles != null) {
                title.setText(tabTitles[i] + "");//设置tab上的文字
            }

        }


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        GetDoctorMedicalStatistics();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                Intent consultSettingIntent = new Intent(TreatActivity.this, TreatSettingActivity.class);
                startActivity(consultSettingIntent);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void GetDoctorMedicalStatistics() {
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(HttpUrl.GetDoctorMedicalStatistics).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<DoctorMedicalStatisticsEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<DoctorMedicalStatisticsEntity> entity) {
                final DoctorMedicalStatisticsEntity doctorMedicalStatisticsEntity = entity.getData();

                if (entity.getCode() == 0) {
                    if (doctorMedicalStatisticsEntity != null) {
                        TreatActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cumulativeReport.setText(doctorMedicalStatisticsEntity.getReportNum() + "");
                            }
                        });
                    } else {
                        cumulativeReport.setText("0");
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdatePushView userEvent) {
        int action = userEvent.action;
        switch (action) {
            case 6:
                mDiagnosisAndTreatFragment.flashView();
                break;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateTreatMessageEntity userEvent) {
        Log.i("OnlineConsulting", "come here");
        String action = userEvent.action;

        if (action.equals("newAppoint")) {
            mDiagnosisAndTreatFragment.flashView();
        } else if (action.equals("accept")) {
            mDiagnosisAndTreatFragment.flashView();
            mDiagnosisAndTreatFragment1.flashView();
        } else if (action.equals("end")) {
            GetDoctorMedicalStatistics();
            mDiagnosisAndTreatFragment1.flashView();
            mDiagnosisAndTreatFragment2.flashView();
        } else if (action.equals("reject")) {
            GetDoctorMedicalStatistics();
            mDiagnosisAndTreatFragment.flashView();
            mDiagnosisAndTreatFragment2.flashView();

        }
        // mConsultListFragment2.flashView(userEvent);
        //   List<UnreadMessageEntity> unreadMessageList = userEvent.UnreadMessageList;
        else if (action.equals("getSize")) {
            int treatUnread = mDiagnosisAndTreatFragment.getSize();
            int treatUnread1 = mDiagnosisAndTreatFragment1.getSize();
            int treatUnread2 = mDiagnosisAndTreatFragment2.getSize();
            for (int i = 0; i < adapter.getCount(); i++) {
                TabLayout.Tab tab1 = tab.getTabAt(i);//获得每一个tab
                tab1.getCustomView();//给每一个tab设置view
          /*  if (i == 0) {
                // 设置第一个tab的TextView是被选择的样式
                tab1.getCustomView().findViewById(R.id.tab_text).setSelected(true);//第一个tab被选中
            }*/
                TextView title = tab1.getCustomView().findViewById(R.id.tab_title);
                RelativeLayout unReadLayout = tab1.getCustomView().findViewById(R.id.unRead_layout);
                TextView unreadNum = tab1.getCustomView().findViewById(R.id.unread_num);
                title.setText(tabTitles[i]);//设置tab上的文字
                if (i == 0) {
                    if (treatUnread > 0) {
                        unReadLayout.setVisibility(View.VISIBLE);
                        unreadNum.setText(String.valueOf(treatUnread));

                    } else {
                        unReadLayout.setVisibility(View.GONE);
                    }
                } else if (i == 1) {
                    if (treatUnread1 > 0) {
                        unReadLayout.setVisibility(View.VISIBLE);
                        unreadNum.setText(String.valueOf(treatUnread1));
                        Log.i("OnlineConsulting", "consultUnread=" + treatUnread1);
                    } else {
                        unReadLayout.setVisibility(View.GONE);
                    }
                } else {
                    unReadLayout.setVisibility(View.GONE);
                }
            }
        }
    }

}
