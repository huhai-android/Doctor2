package com.newdjk.doctor.ui.adapter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.TextView;

import com.newdjk.doctor.R;

import java.util.List;

public class MyZhuanzhenAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"待问诊", "", ""};
    private Context context;
    private List<Fragment> mList;

    public MyZhuanzhenAdapter(FragmentManager fm, Context context, List<Fragment> list) {
        super(fm);
        mList = list;
        this.context = context;
    }

    public MyZhuanzhenAdapter(FragmentManager fm, Context context, List<Fragment> list, String tabTitles[]) {
        super(fm);
        mList = list;
        this.context = context;
        this.tabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
       /* if  (position==0) {
                return ConsultListFragment.newInstance(position);
        }
       else if  (position==1) {
            return ConsultListFragment1.newInstance(position);
        }
        else {
            return ConsultListFragment2.newInstance(position);
        }*/

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    /* @Override
     public CharSequence getPageTitle(int position) {
         return tabTitles[position];
     }*/
// 动态设置我们标题的方法
    public void setPageTitle(int position, String title, TabLayout tab) {
        if (position >= 0 && position < tabTitles.length) {
            tabTitles[position] = title;
            Log.d("MyFragmentAdapter", "" + title);
            setTitle(position, tab);
        }
    }





    private void setTitle(int position, TabLayout tab) {
        TabLayout.Tab tab1 = tab.getTabAt(position);//获得每一个tab
        tab1.getCustomView();//给每一个tab设置view
          /*  if (i == 0) {
                // 设置第一个tab的TextView是被选择的样式
                tab1.getCustomView().findViewById(R.id.tab_text).setSelected(true);//第一个tab被选中
            }*/
        TextView title = tab1.getCustomView().findViewById(R.id.tab_title);
        title.setText(tabTitles[position]);//设置tab上的文字
    }
}
