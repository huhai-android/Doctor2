package com.newdjk.doctor.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class VideoInterrogationFragmentAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> mList;
    public VideoInterrogationFragmentAdapter(FragmentManager fm, Context context, List<Fragment> list) {
        super(fm);
        mList = list;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
       /* if (position ==0) {
            return VideoInterrogationScheduleFragment.newInstance(position);
        }else if (position ==1) {
            return VideoInterrogationListFragment1.newInstance(position);
        }
        else {
            return VideoInterrogationListFragment2.newInstance(position);
        }*/
       return  mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

  /*  @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }*/
}
