package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.newdjk.doctor.ui.adapter.PartyFramentAdapter;
import com.newdjk.doctor.ui.entity.ConsultDataEntity;
import com.newdjk.doctor.ui.entity.DoctorInfoByDrIdEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.OnlineRenewalMessageEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UnreadMessageEntity;
import com.newdjk.doctor.ui.entity.UpdateConsultUnreadNum;
import com.newdjk.doctor.ui.entity.UpdateImMessageEntity;
import com.newdjk.doctor.ui.entity.UpdateRenewalUnreadNum;
import com.newdjk.doctor.ui.fragment.ConsultListFragment;
import com.newdjk.doctor.ui.fragment.ConsultListFragment1;
import com.newdjk.doctor.ui.fragment.ConsultListFragment2;
import com.newdjk.doctor.ui.fragment.OnlineRenewalListFragment;
import com.newdjk.doctor.ui.fragment.OnlineRenewalListFragment1;
import com.newdjk.doctor.ui.fragment.OnlineRenewalListFragment2;
import com.newdjk.doctor.utils.SQLiteUtils;
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

public class OnlineRenewalPartyActivity extends BasicActivity {
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
    @BindView(R.id.cumulative_party)
    TextView cumulativeParty;
    @BindView(R.id.party_score)
    TextView partyScore;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private PartyFramentAdapter adapter;
    private List<Fragment> mList;
    private String tabTitles[] = new String[]{"待续方", "续方中","已完成"};
    private OnlineRenewalListFragment mOnlineRenewalListFragment;
    private OnlineRenewalListFragment1 mOnlineRenewalListFragment1;
    private OnlineRenewalListFragment2 mOnlineRenewalListFragment2;
    @Override
    protected int initViewResId() {
        return R.layout.online_renewal_party;
    }

    @Override
    protected void initView() {

        List<UnreadMessageEntity> allUnreadMessageList = (List<UnreadMessageEntity>)getIntent().getSerializableExtra("allUnReadList");
        mList = new ArrayList<>();
        mOnlineRenewalListFragment = OnlineRenewalListFragment.newInstance(allUnreadMessageList);
        mOnlineRenewalListFragment1 = OnlineRenewalListFragment1.newInstance(allUnreadMessageList);
        mOnlineRenewalListFragment2 = OnlineRenewalListFragment2.newInstance(allUnreadMessageList);
        mList.add(mOnlineRenewalListFragment);
        mList.add(mOnlineRenewalListFragment1);
        mList.add(mOnlineRenewalListFragment2);
        initBackTitle(getString(R.string.my_online_party)).setRightText(getString(R.string.set));
      //  tvRight.setTextColor(getResources().getColor(R.color.theme));
        tvRight.setOnClickListener(this);
        adapter = new PartyFramentAdapter(getSupportFragmentManager(), OnlineRenewalPartyActivity.this,mList);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(2);
        tvRight.setOnClickListener(this);
        tab.setupWithViewPager(viewpager);

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
                View view = tab.getCustomView();
                if (null != view && view instanceof TextView) {
                    ((TextView) view).setTextSize(19);
                    ((TextView) view).setTextColor(ContextCompat.getColor(mContext, R.color.theme));
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (null != view && view instanceof TextView) {
                    ((TextView) view).setTextSize(14);
                    ((TextView) view).setTextColor(ContextCompat.getColor(mContext, R.color.font_gray_5));
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        List<UnreadMessageEntity> unreadMessageList = (List<UnreadMessageEntity>)getIntent().getSerializableExtra("unReadList");
        long consultUnread = SQLiteUtils.getInstance().selectSqlImDataCountByServiceCodeAndType("1103",0);
        long consultingUnread =  SQLiteUtils.getInstance().selectSqlImDataCountByServiceCodeAndType("1103",1);
     /*   for (int k = 0;k<unreadMessageList.size();k++) {
            int status = unreadMessageList.get(k).getStatus();
            switch (status) {
                case 0:
                    long unReadNum1 = unreadMessageList.get(k).getUnReadNum();
                    if (unReadNum1>0) {
                        consultUnread = consultUnread+1;
                    }

                    break;
                case 1:
                    long unReadNum2 = unreadMessageList.get(k).getUnReadNum();
                    if (unReadNum2>0) {
                        consultingUnread = consultingUnread+1;
                    }
                    break;
            }
        }*/
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
            title.setText(tabTitles[i]);//设置tab上的文字
            if (i == 0) {
                if (consultUnread>0) {
                    unReadLayout.setVisibility(View.VISIBLE);
                    unreadNum.setText(String.valueOf(consultUnread));

                }
                else  {
                    unReadLayout.setVisibility(View.GONE);
                }
            }
            else if (i==1) {
                if (consultingUnread>0) {
                    unReadLayout.setVisibility(View.VISIBLE);
                    unreadNum.setText(String.valueOf(consultingUnread));
                    Log.i("OnlineConsulting","consultUnread="+consultUnread);
                }
                else  {
                    unReadLayout.setVisibility(View.GONE);
                }
            }
            else {
                unReadLayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        getDoctorInfo();
    }

    @Override
    protected void otherViewClick(View view) {
    switch (view.getId()) {
        case R.id.tv_right:
            Intent partySettingIntent = new Intent(OnlineRenewalPartyActivity.this,OnlinePartySettingActivity.class);
            startActivity(partySettingIntent);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateImMessageEntity userEvent) {

        long consultUnread = SQLiteUtils.getInstance().selectSqlImDataCountByServiceCodeAndType("1103",0);
        long consultingUnread =  SQLiteUtils.getInstance().selectSqlImDataCountByServiceCodeAndType("1103",1);

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
                if (consultUnread>0) {
                    unReadLayout.setVisibility(View.VISIBLE);
                    unreadNum.setText(String.valueOf(consultUnread));

                }
                else  {
                    unReadLayout.setVisibility(View.GONE);
                }
            }
            else if (i==1) {
                if (consultingUnread>0) {
                    unReadLayout.setVisibility(View.VISIBLE);
                    unreadNum.setText(String.valueOf(consultingUnread));
                    Log.i("OnlineConsulting","consultUnread="+consultUnread);
                }
                else  {
                    unReadLayout.setVisibility(View.GONE);
                }
            }
            else {
                unReadLayout.setVisibility(View.GONE);
            }
        }
    }
    private void getDoctorInfo() {
        String url = HttpUrl.QueryDoctorInfoByDrId + "?DrId="+SpUtils.getInt(Contants.Id,0)+"&PatientId=-1";
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(url).tag(this).headers(headMap).enqueue(new GsonResponseHandler<ResponseEntity<DoctorInfoByDrIdEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<DoctorInfoByDrIdEntity> entity) {
                final DoctorInfoByDrIdEntity doctorInfoByDrIdEntity =  entity.getData();

                if (entity.getCode() == 0) {
                    OnlineRenewalPartyActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (doctorInfoByDrIdEntity != null) {
                                String cumulative = doctorInfoByDrIdEntity.getOnPrescription();
                                String score = doctorInfoByDrIdEntity.getPraiseRate() + "%";
                                if (cumulative != null) {
                                    cumulativeParty.setText(cumulative);
                                }


                                // promptResponseRate.setText(doctorInfoByDrIdEntity.getResponseRate()+"%");
                                partyScore.setText(score);
                            }
                            else {
                                cumulativeParty.setText("0");
                                partyScore.setText("0%");
                            }
                        }
                    });
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
