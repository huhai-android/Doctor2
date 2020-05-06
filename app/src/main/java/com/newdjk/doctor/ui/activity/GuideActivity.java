package com.newdjk.doctor.ui.activity;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.login.LoginActivity;
import com.newdjk.doctor.ui.fragment.GuideFragment;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.PrivacyDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends BasicActivity {
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected int initViewResId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        initviewpager();
    }

    private void initviewpager() {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(5);
    }

    @Override
    protected void initListener() {
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 4) {
                    PrivacyDialog mDialog = new PrivacyDialog(GuideActivity.this);
                    mDialog.show("", "", new PrivacyDialog.DialogListener() {
                        @Override
                        public void confirm() {
                            //跳转隐私政策
                            SpUtils.put(Contants.IS_FIRST_USE, false);
                            SpUtils.put(Contants.IS_HAS_AGREE, true);
                            Intent i = new Intent(GuideActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();

                        }

                        @Override
                        public void cancel() {
                            SpUtils.put(Contants.IS_HAS_AGREE, false);
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {

    }

    class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            // Log.d(TAG,"数据长度"+listuse.size());
            // Log.d(TAG,"数据长度"+listuse.size());
            GuideFragment guideFragment = new GuideFragment();
            guideFragment.setdata(position);

            return guideFragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
//    }
//
//    @Override
//    public List<SinglePage> buildGuideContent() {
//        // prepare the information for our guide
//        List<SinglePage> guideContent = new ArrayList<SinglePage>();
//
//        SinglePage page01 = new SinglePage();
//        page01.mCustomFragment = GuideFragment.getFgt(1);
//        guideContent.add(page01);
//
//        SinglePage page02 = new SinglePage();
//        page02.mCustomFragment = GuideFragment.getFgt(2);
//        guideContent.add(page02);
//
//        SinglePage page03 = new SinglePage();
//        page03.mCustomFragment = GuideFragment.getFgt(3);
//        guideContent.add(page03);
//        SinglePage page04 = new SinglePage();
//        page04.mCustomFragment = GuideFragment.getFgt(4);
//        guideContent.add(page04);
//
//        SinglePage page05 = new SinglePage();
//        page05.mCustomFragment = GuideFragment.getFgt(5);
//        guideContent.add(page05);
//
//        return guideContent;
//    }
//
//    @Override
//    public Bitmap dotDefault() {
//        return BitmapFactory.decodeResource(getResources(), R.drawable.ic_dot_default);
//    }
//
//    @Override
//    public Bitmap dotSelected() {
//        return BitmapFactory.decodeResource(getResources(), R.drawable.ic_dot_selected);
//    }
//
//    @Override
//    public boolean drawDot() {
//        return false;
//    }
//
//
//    /**
//     * You need provide an id to the pager. You could define an id in
//     * values/ids.xml and use it.
//     */
//    @Override
//    public int getPagerId() {
//        return R.id.guide_container;
//    }
//
//    /**
//     * 注册
//     */
//    public void register() {
//        SpUtils.put(Contants.IS_FIRST_USE, false);
//        finish();
//        Intent i = RegisterActivity.getAct(GuideActivity.this);
//        startActivity(i);
//    }
//
//    /**
//     * 登录
//     */
//    public void login() {
//        SpUtils.put(Contants.IS_FIRST_USE, false);
//        finish();
//        Intent i = LoginActivity.getAct(GuideActivity.this);
//        startActivity(i);
//    }


}
