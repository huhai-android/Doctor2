package com.newdjk.doctor.ui.activity;

import android.content.Context;
import android.content.Intent;
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
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.iInterface.OnRedNumberChangeListener;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.MyFragmentAdapter;
import com.newdjk.doctor.ui.entity.CheckEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.SignReportSuccess;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.ui.fragment.AllCheckFragment;
import com.newdjk.doctor.ui.fragment.WaitForCheckFragment;
import com.newdjk.doctor.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui
 *  @文件名:   MyCheckCenterActivity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/6 10:12
 *  @描述：
 */
public class MyCheckCenterActivity extends BasicActivity implements OnRedNumberChangeListener {
    private static final String TAG = "MyCheckCenterActivity";
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
    @BindView(R.id.tv_total_check_number)
    TextView tvTotalCheckNumber;
    @BindView(R.id.tv_thirty_minutes_check_number)
    TextView tvThirtyMinutesCheckNumber;

    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private String tabTitles[] = new String[]{"待判读", "全部"};
    private List<Fragment> mList;
    private WaitForCheckFragment mWaitForCheckFragment;
    private AllCheckFragment mAllCheckFragment;
    private MyFragmentAdapter adapter;
    private int allCount = 0;
    private CheckEntity doctorInfoByDrIdEntity;

    @Override
    protected int initViewResId() {
        return R.layout.activity_checkheart;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        initBackTitle(getString(R.string.title_check_center));
        mList = new ArrayList<>();
        mWaitForCheckFragment = WaitForCheckFragment.newInstance(null, this);
        mList.add(mWaitForCheckFragment);
        mAllCheckFragment = AllCheckFragment.newInstance(null, this);
        mList.add(mAllCheckFragment);
        adapter = new MyFragmentAdapter(getSupportFragmentManager(), MyCheckCenterActivity.this, mList);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(2);
        tab.setupWithViewPager(viewpager);
        setTitle(0, 0);
    }

    private void setTitle(int consultUnread, int consultingUnread) {
        for (int i = 0; i < adapter.getCount(); i++) {
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
        tvRight.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getDoctorInfo();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                Intent intent = new Intent(mContext, CheckSettingActivity.class);
                startActivity(intent);
                break;
        }
    }


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MyCheckCenterActivity.class);
        return intent;
    }

    private void getDoctorInfo() {
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.get().url(HttpUrl.getDoctorData).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<CheckEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<CheckEntity> entity) {
                doctorInfoByDrIdEntity = entity.getData();

              //  Log.d(TAG, entity.getData().toString());
                if (entity.getCode() == 0) {
                    if (doctorInfoByDrIdEntity != null) {
                        tvTotalCheckNumber.setText(doctorInfoByDrIdEntity.getReadedCount() + "");
                        tvThirtyMinutesCheckNumber.setText(doctorInfoByDrIdEntity.getResponseRate() + "%");
                    } else {
                        tvTotalCheckNumber.setText("0");
                        tvThirtyMinutesCheckNumber.setText("0%");
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

    //当红点数量发生变化
    @Override
    public void onRedCountChange(final int number) {
        this.allCount = number;
        adapter.setPageTitle(0, tabTitles[0], number, 0, tab);
    }

    //当总数发生变化
    @Override
    public void onAllCountChange(int number) {
        adapter.setPageTitle(1, "全部(" + number + ")", allCount, 0, tab);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(SignReportSuccess signReportSuccess) {
        if (signReportSuccess.signResule) {
            //签发报告成功
            Log.d(TAG,"收到签发报告成功消息");
            if (doctorInfoByDrIdEntity!=null){
                doctorInfoByDrIdEntity.setReadedCount( doctorInfoByDrIdEntity.getReadedCount()+1);
                EventBus.getDefault().post(new UpdatePushView(3));
            }else {
                doctorInfoByDrIdEntity=new CheckEntity();
                doctorInfoByDrIdEntity.setReadedCount(1);
            }
            tvTotalCheckNumber.setText(doctorInfoByDrIdEntity.getReadedCount() + "");
        }
    }
}
