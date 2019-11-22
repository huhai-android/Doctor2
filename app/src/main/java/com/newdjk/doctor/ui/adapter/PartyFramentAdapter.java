package com.newdjk.doctor.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.newdjk.doctor.ui.fragment.ConsultListFragment;
import com.newdjk.doctor.ui.fragment.ConsultListFragment1;
import com.newdjk.doctor.ui.fragment.ConsultListFragment2;
import com.newdjk.doctor.ui.fragment.OnlineRenewalListFragment;
import com.newdjk.doctor.ui.fragment.OnlineRenewalListFragment1;
import com.newdjk.doctor.ui.fragment.OnlineRenewalListFragment2;

import java.util.List;

public class PartyFramentAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"待续方", "续方中","已完成"};
    private Context context;
    private List<Fragment> mList;

    public PartyFramentAdapter(FragmentManager fm, Context context, List<Fragment> list) {
        super(fm);
        this.context = context;
        mList = list;
    }

    @Override
    public Fragment getItem(int position) {
       /* if  (position==0) {
            return  OnlineRenewalListFragment.newInstance(position);
        }
        else if  (position==1) {
            return  OnlineRenewalListFragment1.newInstance(position);
        }
        else {
            return  OnlineRenewalListFragment2.newInstance(position);
        }*/
        return mList.get(position);
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
