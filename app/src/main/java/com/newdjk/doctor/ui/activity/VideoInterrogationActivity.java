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
import com.newdjk.doctor.ui.adapter.VideoInterrogationFragmentAdapter;
import com.newdjk.doctor.ui.entity.DoctorInfoByDrIdEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UpdateImMessageEntity;
import com.newdjk.doctor.ui.fragment.VideoInterrogationListFragment;
import com.newdjk.doctor.ui.fragment.VideoInterrogationListFragment1;
import com.newdjk.doctor.ui.fragment.VideoInterrogationListFragment2;
import com.newdjk.doctor.utils.SQLiteUtils;
import com.newdjk.doctor.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoInterrogationActivity extends BasicActivity {

    private static final String TAG = VideoInterrogationActivity.class.getSimpleName();
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
    @BindView(R.id.cumulative_reply)
    TextView cumulativeReply;
    //    @BindView(R.id.prompt_response_rate)
//    TextView promptResponseRate;
    @BindView(R.id.satisfaction)
    TextView satisfaction;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.cumulative_reply_text)
    TextView cumulativeReplyText;

    private VideoInterrogationFragmentAdapter adapter;
    private String tabTitles[];
    private List<Fragment> mList;
    private VideoInterrogationListFragment1 mVideoInterrogationListFragment1;
    private VideoInterrogationListFragment2 mVideoInterrogationListFragment2;
    private VideoInterrogationListFragment mVideoInterrogationListFragment;
    private int mDoctype;

    @Override
    protected int initViewResId() {
        return R.layout.video_interrogation;
    }

    @Override
    protected void initView() {
        mList = new ArrayList<>();
        mDoctype = SpUtils.getInt(Contants.DocType, 0);
        mVideoInterrogationListFragment1 = VideoInterrogationListFragment1.newInstance();
        mVideoInterrogationListFragment = VideoInterrogationListFragment.newInstance();
        mVideoInterrogationListFragment2 = VideoInterrogationListFragment2.newInstance();
        mList.add(mVideoInterrogationListFragment);
        mList.add(mVideoInterrogationListFragment1);
        mList.add(mVideoInterrogationListFragment2);
        tvRight.setOnClickListener(this);
        if (mDoctype == 1) {
            initBackTitle("视频问诊").setRightText("设置");
            tabTitles = new String[]{"待问诊", "问诊中", "已完成"};
        } else if (mDoctype == 2) {
            tabTitles = new String[]{"待咨询", "咨询中", "已完成"};
            cumulativeReplyText.setText("远程护理问诊量");
            initBackTitle("远程护理").setRightText(getString(R.string.set));
        }

        adapter = new VideoInterrogationFragmentAdapter(getSupportFragmentManager(), VideoInterrogationActivity.this, mList);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(2);
        tab.setupWithViewPager(viewpager);
      /*  List<UnreadMessageEntity> unreadMessageList = (List<UnreadMessageEntity>)getIntent().getSerializableExtra("unReadList");
        long consultUnread = 0;
        long consultingUnread = 0;
        for (int k = 0;k<unreadMessageList.size();k++) {
            int status = unreadMessageList.get(k).getStatus();
            switch (status) {
                case 0:
                    consultUnread = consultUnread+unreadMessageList.get(k).getUnReadNum();
                    break;
                case 1:
                    consultingUnread = consultingUnread+unreadMessageList.get(k).getUnReadNum();
                    break;
            }
        }*/
        long consultUnread = SQLiteUtils.getInstance().selectSqlImDataCountByServiceCodeAndType("1102", 0);
        long consultingUnread = SQLiteUtils.getInstance().selectSqlImDataCountByServiceCodeAndType("1102", 1);
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
        // EventBus.getDefault().register(VideoInterrogationListFragment.class);
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
                Intent videoInterrationSettingIntent = new Intent(VideoInterrogationActivity.this, VideoInterrogationSettingActivity.class);
                startActivity(videoInterrationSettingIntent);
                break;
      /*   case R.id.now_task:
             Intent videoInterrogationAppointmentIntent = new Intent(VideoInterrogationActivity.this,VideoInterrogationAppointmentActivity.class);
             startActivity(videoInterrogationAppointmentIntent);
             break;*/
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
        long consultUnread = SQLiteUtils.getInstance().selectSqlImDataCountByServiceCodeAndType("1102", 0);
        long consultingUnread = SQLiteUtils.getInstance().selectSqlImDataCountByServiceCodeAndType("1102", 1);
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

    private void getDoctorInfo() {
        String url = HttpUrl.QueryDoctorInfoByDrId + "?DrId=" + SpUtils.getInt(Contants.Id, 0) + "&PatientId=-1";
        mMyOkhttp.get().url(url).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<DoctorInfoByDrIdEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<DoctorInfoByDrIdEntity> entity) {
                final DoctorInfoByDrIdEntity doctorInfoByDrIdEntity = entity.getData();

                if (entity.getCode() == 0) {
                    if (doctorInfoByDrIdEntity != null) {
                        VideoInterrogationActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cumulativeReply.setText(doctorInfoByDrIdEntity.getVolumeVideo() + "");
//                            promptResponseRate.setText(doctorInfoByDrIdEntity.getResponseRate()+"%");
                                satisfaction.setText(doctorInfoByDrIdEntity.getPraiseRate() + "%");
                            }
                        });
                    } else {
                        cumulativeReply.setText("0");
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
}
