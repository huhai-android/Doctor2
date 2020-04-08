package com.newdjk.doctor.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.iInterface.OnTabItemClickListener;
import com.newdjk.doctor.ui.adapter.FunctionChatAdapter;
import com.newdjk.doctor.ui.adapter.TabChatAdapter;
import com.newdjk.doctor.ui.entity.AppLicationEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.fragment
 *  @文件名:   ChatFragment1
 *  @创建者:   huhai
 *  @创建时间:  2019/4/17 10:57
 *  @描述：
 */
public class FunctionChatFragment extends BasicFragment {

    private static final String TAG = "HomeTabFragment";
    private List<AppLicationEntity> listall= new ArrayList<>();
    @BindView(R.id.function_list)
    RecyclerView functionList;
    Unbinder unbinder;
    private OnTabItemClickListener listener;
    private FunctionChatAdapter mFunctionAdapter;

    @Override
    protected int initViewResId() {
        return R.layout.fragment_app;
    }

    @Override
    protected void initView() {
        GridLayoutManager mManagerLayout = new GridLayoutManager(mContext, 4);
        functionList.setLayoutManager(mManagerLayout);
        mFunctionAdapter = new FunctionChatAdapter(listall);
        functionList.setAdapter(mFunctionAdapter);
    }

    @Override
    protected void initListener() {
        mFunctionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
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

    public static FunctionChatFragment getFragment() {

        return new FunctionChatFragment();
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
                if (appList.size()>=16){
                    listall.addAll(appList.subList(8,16));
                }else {
                    listall.addAll(appList.subList(8,appList.size()));
                }

                break;

            case 2:

                listall.clear();
                if (appList.size()>=24){
                    listall.addAll(appList.subList(16,24));
                }else {
                    listall.addAll(appList.subList(16,appList.size()));
                }
                break;
        }

        Log.d(TAG,position+"数据长度-----"+ listall.size()+"---"+mFunctionAdapter);
        if (mFunctionAdapter != null) {
            Log.d(TAG,position+"刷新");

            mFunctionAdapter.notifyDataSetChanged();

        }
    }


}
