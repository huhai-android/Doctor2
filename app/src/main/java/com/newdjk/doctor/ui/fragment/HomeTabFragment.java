package com.newdjk.doctor.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighLight;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newdjk.doctor.BuildConfig;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.iInterface.OnTabItemClickListener;
import com.newdjk.doctor.ui.adapter.FunctionAdapter;
import com.newdjk.doctor.ui.entity.AppLicationEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.newdjk.doctor.basic.BasicActivity.activity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.fragment
 *  @文件名:   ChatFragment1
 *  @创建者:   huhai
 *  @创建时间:  2019/4/17 10:57
 *  @描述：
 */
public class HomeTabFragment extends BasicFragment {

    private static final String TAG = "HomeTabFragment";
    private List<AppLicationEntity> listall = new ArrayList<>();
    @BindView(R.id.function_list)
    RecyclerView functionList;
    Unbinder unbinder;
    private OnTabItemClickListener listener;
    private FunctionAdapter mFunctionAdapter;
    private GridLayoutManager mManagerLayout;
    private boolean isshow = false;

    @Override
    protected int initViewResId() {
        return R.layout.fragment_app;
    }

    @Override
    protected void initView() {
        mManagerLayout = new GridLayoutManager(mContext, 4);
        functionList.setLayoutManager(mManagerLayout);
        mFunctionAdapter = new FunctionAdapter(listall);
        functionList.setAdapter(mFunctionAdapter);
        if (BuildConfig.DEBUG){
            functionList.post(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "显示了");
                    //高亮gridView的第2个子view

                    if (isshow) {
                        return;
                    }
                    View childAt = functionList.getChildAt(0);
                    NewbieGuide.with(getActivity())
                            .setLabel("grid_view_guide")
                            .alwaysShow(false)
                            .setShowCounts(1)
                            .addGuidePage(GuidePage.newInstance()
                                    .addHighLight(childAt, HighLight.Shape.RECTANGLE)
                                    .setEverywhereCancelable(true)
                                    .setLayoutRes(R.layout.view_guide))
                            .show();
                    isshow = true;
                }
            });
        }


    }

    @Override
    protected void initListener() {
        mFunctionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                listener.onItemChildClick(listall.get(position));
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {

    }

    public static HomeTabFragment getFragment() {

        return new HomeTabFragment();
    }


    public void setonclickListener(OnTabItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    public void setdata(List<AppLicationEntity> appList, int position) {
        switch (position) {
            case 0: {
                listall.clear();
                if (appList.size() >= 8) {
                    listall.addAll(appList.subList(0, 8));

                } else {
                    listall.addAll(appList.subList(0, appList.size()));
                }
                break;
            }

            case 1:
                listall.clear();
                if (appList.size() >= 16) {
                    listall.addAll(appList.subList(8, 16));
                } else {
                    listall.addAll(appList.subList(8, appList.size()));
                }

                break;

            case 2:

                listall.clear();
                if (appList.size() >= 24) {
                    listall.addAll(appList.subList(16, 24));
                } else {
                    listall.addAll(appList.subList(16, appList.size()));
                }
                break;
        }

        // Log.d(TAG, position + "数据长度-----" + listall.size() + "---" + mFunctionAdapter);
        if (mFunctionAdapter != null) {
            // Log.d(TAG, position + "刷新");

            mFunctionAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }


}
