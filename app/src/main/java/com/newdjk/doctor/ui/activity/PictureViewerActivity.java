package com.newdjk.doctor.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.ui.fragment.PictureSlideFragment;
import com.newdjk.doctor.views.ImageOriginPager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PictureViewerActivity extends BasicActivity {
    @BindView(R.id.viewpager)
    ImageOriginPager viewpager;
    private List<String> mUrlList; /*所有图片url*/
    private int mPicPosition; /*图片位置*/
    @Override
    protected int initViewResId() {
        return R.layout.picture_viewer;
    }

    @Override
    protected void initView() {
        viewpager = findViewById(R.id.viewpager);


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mUrlList = bundle.getStringArrayList("pic_all");
        mPicPosition = bundle.getInt("pic_position");
        viewpager.setAdapter(new PictureSlidePagerAdapter(getSupportFragmentManager()));
        viewpager.setCurrentItem(mPicPosition);


    }

    @Override
    protected void otherViewClick(View view) {

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

    private class PictureSlidePagerAdapter extends FragmentStatePagerAdapter {
        public PictureSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PictureSlideFragment.newInstance(mUrlList.get(position));
        }

        @Override
        public int getCount() {
            if (mUrlList!=null){
                return mUrlList.size();
            }
          return 0;
        }
    }


}
