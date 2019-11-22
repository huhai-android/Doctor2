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

import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.iInterface.OnPrescriptionChangeListener;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.MyZhuanzhenAdapter;
import com.newdjk.doctor.ui.entity.PrescriptionDataEntity;
import com.newdjk.doctor.ui.entity.SignReportSuccess;
import com.newdjk.doctor.ui.entity.ZhenZhenCountEntity;
import com.newdjk.doctor.ui.entity.ZhuanhuiSuccess;
import com.newdjk.doctor.ui.entity.ZhuanzhenSuccess;
import com.newdjk.doctor.ui.fragment.BaseZhuanZhenFragment;
import com.newdjk.doctor.ui.fragment.ZhuanZhenFragment1;
import com.newdjk.doctor.ui.fragment.ZhuanZhenFragment2;
import com.newdjk.doctor.ui.fragment.ZhuanZhenFragment3;
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
public class FenjiZhuanzhenActivity extends BasicActivity implements OnPrescriptionChangeListener {
    private static final String TAG = "FenjiZhuanzhenActivity";
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
    @BindView(R.id.tv_up_zhuanzhen)
    TextView tvUpZhuanzhen;
    @BindView(R.id.tv_down_zhuanzhen)
    TextView tvDownZhuanzhen;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;


    private String tabTitles[] = new String[]{"接转诊", "接诊记录", "转出记录"};
    private List<Fragment> mList;
    private BaseZhuanZhenFragment zhuanZhenFragment1;
    private BaseZhuanZhenFragment zhuanZhenFragment2;
    private BaseZhuanZhenFragment zhuanZhenFragment3;
    private MyZhuanzhenAdapter adapter;
    private int allCount = 0;
    private PrescriptionDataEntity prescriptionDataEntity;
    private Gson mGson;
    //向上转诊量，向下转诊量
    int upcount=0;
    int Downcount=0;

    @Override
    protected int initViewResId() {
        return R.layout.activity_zhuanzhen;
    }

    @Override
    protected void initView() {
        mGson = new Gson();
        EventBus.getDefault().register(this);
        initBackTitle("分级转诊").setRightText(getString(R.string.set));
        mList = new ArrayList<>();
        zhuanZhenFragment1 = ZhuanZhenFragment1.newInstance(null, this, 0);
        mList.add(zhuanZhenFragment1);

        zhuanZhenFragment2 = ZhuanZhenFragment2.newInstance(null, this, 1);
        mList.add(zhuanZhenFragment2);

        zhuanZhenFragment3 = ZhuanZhenFragment3.newInstance(null, this, 2);
        mList.add(zhuanZhenFragment3);

        adapter = new MyZhuanzhenAdapter(getSupportFragmentManager(), FenjiZhuanzhenActivity.this, mList, tabTitles);
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
        tvTotalCheckNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // toast("点击了");
            }
        });
    }

    @Override
    protected void initData() {
        getDoctorInfo();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:

                Intent intent = new Intent(FenjiZhuanzhenActivity.this, WebViewActivity.class);
                if (MyApplication.mDoctorInfoByIdEntity.getProfessionalLevel() == 1) {
                    intent.putExtra("type", 5);
                } else {
                    intent.putExtra("type", 6);
                }

                startActivity(intent);

                break;
        }
    }


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, FenjiZhuanzhenActivity.class);
        return intent;
    }

    private void getDoctorInfo() {
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.get().url(HttpUrl.QueryReferralRecordCount).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ZhenZhenCountEntity>() {
            @Override
            public void onSuccess(int statusCode, ZhenZhenCountEntity entity) {
                if (entity.getCode() == 0) {
                    List<ZhenZhenCountEntity.DataBean> list = entity.getData();
                    if (list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getReferralType() == 1) {
                                upcount=list.get(i).getNum();
                                //tvUpZhuanzhen.setText(list.get(i).getNum() + "");
                            } else if (list.get(i).getReferralType() == 2) {
                               // tvDownZhuanzhen.setText(list.get(i).getNum() + "");
                                Downcount=list.get(i).getNum();
                            } else if (list.get(i).getReferralType() == 3) {
                                tvTotalCheckNumber.setText(list.get(i).getNum() + "");
                            }
                        }
                    }
                    tvDownZhuanzhen.setText(""+(upcount+Downcount));
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
     //   adapter.setPageTitle(0, tabTitles[0], number, 0, tab);
    }

    //当拒绝发生变化
    @Override
    public void onRefuseCountChange(int number) {
        Log.d(TAG, "更新审核通过红点显示" + number);

        // adapter.setPageTitle(2, "已驳回(" + number + ")", allCount, 0, tab);

     //   adapter.setPageTitle(2, "转出记录"+ "(" + number + ")", number, 0, tab);

    }

    //当总数发生变化
    @Override
    public void onHaveCountChange(int number) {

        Log.d(TAG, "更新驳回红点显示" + number);
        //   adapter.setPageTitle(1, "已审核(" + number + ")", allCount, 0, tab);
     //   adapter.setPageTitle(1, tabTitles[1], number, 1, tab);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(ZhuanzhenSuccess zhuanzhenSuccess) {
        Log.d(TAG, "收到转诊成功消息，切换界面");
        if (zhuanzhenSuccess.isZhenzhuan()) {
            viewpager.setCurrentItem(1);


        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(ZhuanhuiSuccess zhuanzhenSuccess) {
        Log.d(TAG, "转回成功");
        if (zhuanzhenSuccess.isZhenzhuan()) {

        getDoctorInfo();
        }

    }

}
