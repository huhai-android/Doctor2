package com.newdjk.doctor.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * fragment切换控制器, 初始化时直接add全部fragment, 然后利用show和hide进行切换控制
 */
public class FragmentController {

    private int containerId;
    private FragmentManager fm;
    private List<Fragment> fragments;

    /**
     * @param activity
     * @param containerId 容器Id
     * @param fragments
     */

    public FragmentController(FragmentActivity activity, int containerId, List<Fragment> fragments) {
        this.containerId = containerId;
        this.fragments = fragments;
        this.fm = activity.getSupportFragmentManager();
        initFragment();
    }

    public void initFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            ft.add(containerId, fragments.get(i), String.valueOf(i));
        }
        ft.commit();
    }

    /**
     * @param position
     */
    public void showFragment(int position) {
        hideFragments();

        Fragment fragment = fragments.get(position);
        FragmentTransaction ft = fm.beginTransaction();
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.show(fragment);
        ft.commit();
    }

    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commit();
    }


}