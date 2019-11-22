package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.utils.FragmentFactory;

import butterknife.BindView;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   MianZhenActivity
 *  @创建者:   huhai
 *  @创建时间:  2019/6/24 10:20
 *  @描述：
 */
public class MianZhenActivity extends BasicActivity {
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
    @BindView(R.id.rb_tab1)
    RadioButton rbTab1;
    @BindView(R.id.rb_tab2)
    RadioButton rbTab2;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    private Gson mGson;
    private FragmentFactory fragmentFactory;
    private PagerAdapter mMianzhenAdapter;
    private String TAG = "MianZhenActivity";

    @Override
    protected int initViewResId() {
        return R.layout.activity_mianzhen;
    }

    @Override
    protected void initView() {
        mGson = new Gson();
        // EventBus.getDefault().register(this);
        initBackTitle("面诊预约").setRightText(getString(R.string.set));
        initviewpager();
    }

    private void initviewpager() {

        fragmentFactory = new FragmentFactory();

        viewpager.setOffscreenPageLimit(2);
        mMianzhenAdapter = new PagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(mMianzhenAdapter);
        viewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                viewpager.setCurrentItem(position, false);
                if (position == 0) {
                    rbTab1.setChecked(true);
                    rbTab2.setChecked(false);
                } else if (position == 1) {
                    rbTab1.setChecked(false);
                    rbTab2.setChecked(true);
                }
            }
        });

    }

    @Override
    protected void initListener() {
        tvRight.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG, "" + checkedId);
                if (checkedId == R.id.rb_tab1) {
                    viewpager.setCurrentItem(0);
                } else if (checkedId == R.id.rb_tab2) {
                    viewpager.setCurrentItem(1);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                Intent mianzhen = new Intent(this, PrescriptionActivity.class);
                mianzhen.putExtra("type", 21);
                startActivity(mianzhen);
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);
    }


    private class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return fragmentFactory.createMianzhenFragment(position);

        }

        @Override
        public int getCount() {
            return 2;
        }

    }
}
