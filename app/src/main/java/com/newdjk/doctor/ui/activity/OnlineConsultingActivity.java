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
import com.newdjk.doctor.ui.entity.DoctorInfoByDrIdEntity;
import com.newdjk.doctor.ui.entity.InquiryRecordListEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UnreadMessageEntity;
import com.newdjk.doctor.ui.entity.UpdateConsultUnreadNum;
import com.newdjk.doctor.ui.entity.UpdateImMessageEntity;
import com.newdjk.doctor.ui.fragment.ConsultListFragment;
import com.newdjk.doctor.ui.fragment.ConsultListFragment1;
import com.newdjk.doctor.ui.fragment.ConsultListFragment2;
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

public class OnlineConsultingActivity extends BasicActivity {

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
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.cumulative_reply)
    TextView cumulativeReply;
    @BindView(R.id.prompt_response_rate)
    TextView promptResponseRate;
    @BindView(R.id.satisfaction)
    TextView satisfaction;
    @BindView(R.id.cumulative_reply_text)
    TextView cumulativeReplyText;
    private MyFragmentAdapter adapter;
    private InquiryRecordListEntity mInquiryRecordListEntity;
    private List<Fragment> mList;
    private String tabTitles[];
    private static boolean mIsReflash = false;
    private ConsultListFragment mConsultListFragment;
    private ConsultListFragment1 mConsultListFragment1;
    private ConsultListFragment2 mConsultListFragment2;
    private int mConsultingNum;
    private int mDoctype;
    private int mConsultNum;

    @Override
    protected int initViewResId() {
        return R.layout.online_consulting;
    }

    @Override
    protected void initView() {
        mDoctype = SpUtils.getInt(Contants.DocType, 0);
        mList = new ArrayList<>();
        List<UnreadMessageEntity> allUnreadMessageList = (List<UnreadMessageEntity>) getIntent().getSerializableExtra("allUnReadList");
        mConsultListFragment = ConsultListFragment.newInstance(allUnreadMessageList);
        mList.add(mConsultListFragment);
        mConsultListFragment1 = ConsultListFragment1.newInstance(allUnreadMessageList);
        mList.add(mConsultListFragment1);
        mConsultListFragment2 = ConsultListFragment2.newInstance(allUnreadMessageList);
        mList.add(mConsultListFragment2);
        if (mDoctype == 1) {
            initBackTitle(getString(R.string.my_advice)).setRightText(getString(R.string.set));
            tabTitles = new String[]{"待问诊", "问诊中", "已完成"};
        } else if (mDoctype == 2) {
            tabTitles = new String[]{"待咨询", "咨询中", "已完成"};
            cumulativeReplyText.setText("护理咨询问诊量");
            initBackTitle("护理咨询").setRightText(getString(R.string.set));
        }
        adapter = new MyFragmentAdapter(getSupportFragmentManager(), OnlineConsultingActivity.this, mList);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(2);
        tvRight.setOnClickListener(this);
        tab.setupWithViewPager(viewpager);
        List<UnreadMessageEntity> unreadMessageList = (List<UnreadMessageEntity>) getIntent().getSerializableExtra("unReadList");
        long consultUnread = SQLiteUtils.getInstance().selectSqlImDataCountByServiceCodeAndType("1101", 0);
        long consultingUnread = SQLiteUtils.getInstance().selectSqlImDataCountByServiceCodeAndType("1101", 1);
      /*  for (int k = 0;k<unreadMessageList.size();k++) {
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
            if (tabTitles != null) {
                title.setText(tabTitles[i]);//设置tab上的文字
            }
            if (i == 0) {
                if (consultUnread > 0) {
                    unReadLayout.setVisibility(View.VISIBLE);
                    unreadNum.setText(String.valueOf(consultUnread));
                } else {
                    unReadLayout.setVisibility(View.GONE);
                }
            } else if (i == 1) {
                if (consultingUnread > 0) {
                    unReadLayout.setVisibility(View.VISIBLE);
                    unreadNum.setText(String.valueOf(consultingUnread));
                } else {
                    unReadLayout.setVisibility(View.GONE);
                }
            } else {
                unReadLayout.setVisibility(View.GONE);
            }
        }


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        // getConsultRecordList();
        //  getAllConsultRecordList();
        getDoctorInfo();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                Intent consultSettingIntent = new Intent(OnlineConsultingActivity.this, ConsultSettingActivity.class);
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

    private void getDoctorInfo() {
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        params.put("PatientId", "-1");

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(HttpUrl.QueryDoctorInfoByDrId).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<DoctorInfoByDrIdEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<DoctorInfoByDrIdEntity> entity) {
                final DoctorInfoByDrIdEntity doctorInfoByDrIdEntity = entity.getData();

                if (entity.getCode() == 0) {
                    if (doctorInfoByDrIdEntity != null) {
                        OnlineConsultingActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                  if (mDoctype == 2) {
                                      cumulativeReply.setText(doctorInfoByDrIdEntity.getConsultCount() + "");
                                }
                                else {
                                      cumulativeReply.setText(doctorInfoByDrIdEntity.getConsultVolume() + "");
                                  }
                                promptResponseRate.setText(doctorInfoByDrIdEntity.getResponseRate() + "%");
                                satisfaction.setText(doctorInfoByDrIdEntity.getPraiseRate() + "%");
                            }
                        });
                    } else {
                        cumulativeReply.setText("0");
                        promptResponseRate.setText("0%");
                        satisfaction.setText("0%");
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
    public void userEventBus(UpdateImMessageEntity userEvent) {
        // GetAllRecordForDoctor();
        //  refreshView(userEvent.ImId);
        long consultUnread = SQLiteUtils.getInstance().selectSqlImDataCountByServiceCodeAndType("1101", 0);
        long consultingUnread = SQLiteUtils.getInstance().selectSqlImDataCountByServiceCodeAndType("1101", 1);
      /*  for (int k = 0;k<unreadMessageList.size();k++) {
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
                if (consultUnread > 0) {
                    unReadLayout.setVisibility(View.VISIBLE);
                    unreadNum.setText(String.valueOf(consultUnread));

                } else {
                    unReadLayout.setVisibility(View.GONE);
                }
            } else if (i == 1) {
                if (consultingUnread > 0) {
                    unReadLayout.setVisibility(View.VISIBLE);
                    unreadNum.setText(String.valueOf(consultingUnread));
                    Log.i("OnlineConsulting", "consultUnread=" + consultUnread);
                } else {
                    unReadLayout.setVisibility(View.GONE);
                }
            } else {
                unReadLayout.setVisibility(View.GONE);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateConsultUnreadNum userEvent) {
        Log.i("OnlineConsulting", "come here");
        int action = userEvent.action;
        if (action == 0) {
            mConsultListFragment.flashView(userEvent);
            mConsultListFragment1.flashView(userEvent);
        } else {
            mConsultListFragment.freshUnreadList(userEvent);
            mConsultListFragment1.freshUnreadList(userEvent);
        }
        // mConsultListFragment2.flashView(userEvent);
        List<UnreadMessageEntity> unreadMessageList = userEvent.UnreadMessageList;
        long consultUnread = SQLiteUtils.getInstance().selectSqlImDataCountByServiceCodeAndType("1101", 0);
        long consultingUnread = SQLiteUtils.getInstance().selectSqlImDataCountByServiceCodeAndType("1101", 1);
      /*  for (int k = 0;k<unreadMessageList.size();k++) {
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
                if (consultUnread > 0) {
                    unReadLayout.setVisibility(View.VISIBLE);
                    unreadNum.setText(String.valueOf(consultUnread));

                } else {
                    unReadLayout.setVisibility(View.GONE);
                }
            } else if (i == 1) {
                if (consultingUnread > 0) {
                    unReadLayout.setVisibility(View.VISIBLE);
                    unreadNum.setText(String.valueOf(consultingUnread));
                    Log.i("OnlineConsulting", "consultUnread=" + consultUnread);
                } else {
                    unReadLayout.setVisibility(View.GONE);
                }
            } else {
                unReadLayout.setVisibility(View.GONE);
            }
        }
    }

}
