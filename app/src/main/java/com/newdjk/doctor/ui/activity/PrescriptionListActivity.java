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
import com.newdjk.doctor.iInterface.OnPrescriptionChangeListener;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.MyFragmentAdapter;
import com.newdjk.doctor.ui.entity.PrescriptionDataEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.SignReportSuccess;
import com.newdjk.doctor.ui.fragment.BaseCheckFragment;
import com.newdjk.doctor.ui.fragment.HaveCheckPrescriptionFragment;
import com.newdjk.doctor.ui.fragment.RefuseCheckPrescriptionFragment;
import com.newdjk.doctor.ui.fragment.WaitForCheckPrescriptionFragment;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;

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
public class PrescriptionListActivity extends BasicActivity implements OnPrescriptionChangeListener {
    private static final String TAG = "PrescriptionActivity";
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

    private String tabTitles[] = new String[]{"待审核", "已审核", "已驳回"};
    private List<Fragment> mList;
    private BaseCheckFragment mWaitForCheckFragment;
    private BaseCheckFragment mAllCheckFragment;
    private BaseCheckFragment mHaveCheckPrescriptionFragment;
    private MyFragmentAdapter adapter;
    private int allCount = 0;
    private PrescriptionDataEntity prescriptionDataEntity;


    @Override
    protected int initViewResId() {
        return R.layout.activity_prescription;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        initBackTitle(getString(R.string.hchufang_check));
        mList = new ArrayList<>();
        mWaitForCheckFragment = WaitForCheckPrescriptionFragment.newInstance(null, this, 0);
        mList.add(mWaitForCheckFragment);
        mHaveCheckPrescriptionFragment = HaveCheckPrescriptionFragment.newInstance(null, this, 2);
        mList.add(mHaveCheckPrescriptionFragment);

        mAllCheckFragment = RefuseCheckPrescriptionFragment.newInstance(null, this, 1);
        mList.add(mAllCheckFragment);

        adapter = new MyFragmentAdapter(getSupportFragmentManager(), PrescriptionListActivity.this, mList, tabTitles);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(3);
        tab.setupWithViewPager(viewpager);
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

                break;
        }
    }


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, PrescriptionListActivity.class);
        return intent;
    }

    private void getDoctorInfo() {

        Map<String, String> map = new HashMap<>();
        map.put("AuditorId", String.valueOf(SpUtils.getInt(Contants.Id, 0))); //状态（-1：全部，0：待审核，1：已审核，2：被驳回）（必填）
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.getPrescriptionDetail).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<PrescriptionDataEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<PrescriptionDataEntity> entity) {
                LoadDialog.clear();

                if (entity.getCode() == 0) {
                    prescriptionDataEntity = entity.getData();
                    if (prescriptionDataEntity != null) {
                        tvTotalCheckNumber.setText(prescriptionDataEntity.getTotal() + "");
                        tvThirtyMinutesCheckNumber.setText(prescriptionDataEntity.getResponseRate() + "%");
                        EventBus.getDefault().post(prescriptionDataEntity);
                    } else {
                        tvTotalCheckNumber.setText("0");
                        tvThirtyMinutesCheckNumber.setText("0%");
                    }
                } else {
                    tvTotalCheckNumber.setText("0");
                    tvThirtyMinutesCheckNumber.setText("0%");
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
    public void onWaitForcheckchange(int number) {
        this.allCount = number;
        if (allCount <= 0) {
            allCount = 0;
        }
        Log.d(TAG, "更新待审核红点显示" + number);
        adapter.setPageTitle(0, tabTitles[0], number, 0, tab);
    }

    //当拒绝发生变化
    @Override
    public void onRefuseCountChange(int number) {
        Log.d(TAG, "更新审核通过红点显示" + number);

        adapter.setPageTitle(2, "已驳回(" + number + ")", allCount, 0, tab);

    }

    //当总数发生变化
    @Override
    public void onHaveCountChange(int number) {

        Log.d(TAG, "更新驳回红点显示" + number);
        adapter.setPageTitle(1, "已审核(" + number + ")", allCount, 0, tab);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(SignReportSuccess signReportSuccess) {

        //签发报告成功



        getDoctorInfo();

    }
}
