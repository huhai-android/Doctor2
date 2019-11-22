package com.newdjk.doctor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.VideoInterrogationFragmentAdapter;
import com.newdjk.doctor.ui.entity.UpdateImMessageEntity;
import com.newdjk.doctor.ui.fragment.TaskFragment1;
import com.newdjk.doctor.ui.fragment.TaskFragment2;
import com.newdjk.doctor.utils.SQLiteUtils;
import com.newdjk.doctor.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskActivity extends BasicActivity {


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
    private VideoInterrogationFragmentAdapter adapter;
    private String tabTitles[];
    private List<Fragment> mList;
    private TaskFragment1 mVideoInterrogationListFragment1;
    private TaskFragment2 mVideoInterrogationListFragment;
    private int mDoctype;
    private int type;

    @Override
    protected int initViewResId() {
        return R.layout.activity_task;
    }


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, TaskActivity.class);
        return intent;
    }


    @Override
    protected void initView() {
        mList = new ArrayList<>();
        mDoctype = SpUtils.getInt(Contants.DocType, 0);
        type = getIntent().getIntExtra("type", 0);
        mVideoInterrogationListFragment1 = TaskFragment1.newInstance();
        mVideoInterrogationListFragment = TaskFragment2.newInstance();
        // mVideoInterrogationListFragment2 = VideoInterrogationListFragment2.newInstance();
        mList.add(mVideoInterrogationListFragment1);
        mList.add(mVideoInterrogationListFragment);

        //  mList.add(mVideoInterrogationListFragment2);
        tvRight.setOnClickListener(this);
        if (mDoctype == 1) {
            initBackTitle("待处理").setRightText("设置");
            tabTitles = new String[]{"待接诊", "接诊中"};
        } else if (mDoctype == 2) {
            tabTitles = new String[]{"待咨询", "咨询中"};
            initBackTitle("待处理").setRightText(getString(R.string.set));
        }

        adapter = new VideoInterrogationFragmentAdapter(getSupportFragmentManager(), TaskActivity.this, mList);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(2);
        if (type==2){
            viewpager.setCurrentItem(1);
        }else {
            viewpager.setCurrentItem(0);
        }

        tab.setupWithViewPager(viewpager);
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

        }
        // EventBus.getDefault().register(VideoInterrogationListFragment.class);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                Intent serviceSettingIntent = new Intent(this, ServiceSettingActivity.class);
                startActivity(serviceSettingIntent);
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

        }
    }


}
