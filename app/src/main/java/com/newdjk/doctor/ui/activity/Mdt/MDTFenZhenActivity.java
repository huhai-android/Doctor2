package com.newdjk.doctor.ui.activity.Mdt;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.iInterface.OnPrescriptionChangeListener;
import com.newdjk.doctor.ui.adapter.GroupMessageAdapter;
import com.newdjk.doctor.ui.adapter.MyZhuanzhenAdapter;
import com.newdjk.doctor.ui.entity.ImDataEntity;
import com.newdjk.doctor.ui.fragment.BaseMDTfenzhenFragment;
import com.newdjk.doctor.ui.fragment.MDTfenzhenFragment1;
import com.newdjk.doctor.ui.fragment.MDTfenzhenFragment2;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   MDTActivity
 *  @创建者:   huhai
 *  @创建时间:  2019/8/29 10:26
 *  @描述：
 */
public class MDTFenZhenActivity extends BasicActivity implements OnPrescriptionChangeListener {


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

    private String TAG = "MDTActivity";
    private List<ImDataEntity> mConversationListData = new ArrayList<>();
    public Gson mGson = new Gson();
    private GroupMessageAdapter mAdapter;
    private String tabTitles[] = new String[]{"抢单", "接单"};
    private List<Fragment> mList;

    private BaseMDTfenzhenFragment zhuanZhenFragment1;
    private BaseMDTfenzhenFragment zhuanZhenFragment2;
    private MyZhuanzhenAdapter adapter;

    @Override
    protected int initViewResId() {
        return R.layout.activity_mdt_fenzhen;
    }

    @Override
    protected void initView() {
        initTitle("我要接诊").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mList = new ArrayList<>();
        zhuanZhenFragment1 = MDTfenzhenFragment1.newInstance(null, this, 0);
        mList.add(zhuanZhenFragment1);

        zhuanZhenFragment2 = MDTfenzhenFragment2.newInstance(null, this, 1);
        mList.add(zhuanZhenFragment2);

        adapter = new MyZhuanzhenAdapter(getSupportFragmentManager(), MDTFenZhenActivity.this, mList, tabTitles);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(2);
        tab.setupWithViewPager(viewpager);
        MyApplication.mActivities.add(this);
        setTitle(0, 0);
    }

    private void setTitle(int consultUnread, int consultingUnread) {
        for (int i = 0; i < adapter.getCount(); i++) {
            Log.d(TAG, "" + i + " " + tabTitles[i]);
            TabLayout.Tab tab1 = tab.getTabAt(i);//获得每一个tab
            tab1.setCustomView(R.layout.custom_tab);//给每一个tab设置view
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

    }


    @Override
    protected void otherViewClick(View view) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.mActivities.remove(this);
        EventBus.getDefault().unregister(this);

    }


    @Override
    public void onWaitForcheckchange(int number) {
//第一个title
        if (number!=0){
            adapter.setPageTitle(0, "抢单(" + number + ")", tab);
        }

    }

    @Override
    public void onHaveCountChange(int number) {
//第二个title

        if (number!=0){
            adapter.setPageTitle(1, "接单(" + number + ")", tab);

        }

    }

    @Override
    public void onRefuseCountChange(int number) {

    }

}

