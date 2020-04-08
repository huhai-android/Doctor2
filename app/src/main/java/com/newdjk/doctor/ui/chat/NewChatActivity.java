package com.newdjk.doctor.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.VideoInterrogationSettingActivity;
import com.newdjk.doctor.ui.adapter.VideoInterrogationFragmentAdapter;
import com.newdjk.doctor.utils.SQLiteUtils;
import com.newdjk.doctor.utils.SpUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewChatActivity extends BasicActivity {

    private static final String TAG = "NewChatActivity";
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
    MagicIndicator tab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;


    private VideoInterrogationFragmentAdapter adapter;
    private String tabTitles[];
    private List<Fragment> mList;
    private ChatWebviewFragment chatFragment1;

    private ChatFragment chatFragment2;
    private ChatSetupFragment chatFragment3;
    private int mDoctype;
    private String mName;
    private int mStatus = 0;

    @Override
    protected int initViewResId() {
        return R.layout.video_newchat;
    }

    @Override
    protected void initView() {
        mList = new ArrayList<>();
        mDoctype = SpUtils.getInt(Contants.DocType, 0);
        chatFragment1 = ChatWebviewFragment.newInstance();
        chatFragment2 = ChatFragment.newInstance();
        chatFragment3 = ChatSetupFragment.newInstance();
        mList.add(chatFragment1);
        mList.add(chatFragment2);
        mList.add(chatFragment3);
        tvRight.setOnClickListener(this);
        tabTitles = new String[]{"患者档案", "患者聊天", "设置价格"};
        initBackTitle("患者");

        adapter = new VideoInterrogationFragmentAdapter(getSupportFragmentManager(), NewChatActivity.this, mList);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(2);

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);  //ture 即标题平分屏幕宽度的模式
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tabTitles == null ? 0 : tabTitles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setSelectedColor(R.color.theme);
                colorTransitionPagerTitleView.setText(tabTitles[index]);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewpager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setYOffset(UIUtil.dip2px(context, 3));
                indicator.setColors(Color.parseColor("#3069CF"));
                return indicator;
            }
        });
        tab.setNavigator(commonNavigator);
        ViewPagerHelper.bind(tab, viewpager);

        viewpager.setCurrentItem(1);

        // EventBus.getDefault().register(VideoInterrogationListFragment.class);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        initToolbar();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                Intent videoInterrationSettingIntent = new Intent(NewChatActivity.this, VideoInterrogationSettingActivity.class);
                startActivity(videoInterrationSettingIntent);
                break;

        }
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initToolbar() {
        mName = getIntent().getStringExtra(Contants.FRIEND_NAME);
        mStatus = getIntent().getIntExtra("status", 8);

        if (!TextUtils.isEmpty(mName)) {
            //topTitle.setText(name);
            if (mStatus == 0) {
                initBackTitle(mName);
            } else {
                initBackTitle(mName);
            }
        } else {
            if (mStatus == 0) {
                initBackTitle(getIntent().getStringExtra(Contants.FRIEND_IDENTIFIER));
            } else {
                initBackTitle(getIntent().getStringExtra(Contants.FRIEND_IDENTIFIER));
            }
            // topTitle.setText(getIntent().getStringExtra(Contants.FRIEND_IDENTIFIER));
        }


    }
}
