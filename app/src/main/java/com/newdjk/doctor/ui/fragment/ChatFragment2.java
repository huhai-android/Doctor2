package com.newdjk.doctor.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.newdjk.doctor.BuildConfig;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.iInterface.ViewClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.fragment
 *  @文件名:   ChatFragment1
 *  @创建者:   huhai
 *  @创建时间:  2019/4/17 10:57
 *  @描述：
 */
public class ChatFragment2 extends BasicFragment {

    Unbinder unbinder;
    @BindView(R.id.mdt_fenzhen)
    LinearLayout mdtFenzhen;
    @BindView(R.id.video_recode)
    LinearLayout videoRecode;
    @BindView(R.id.plus_sign)
    LinearLayout plusSign;
    private ViewClickListener listener;

    @BindView(R.id.interrogation_table)
    LinearLayout interrogationTable;
    @BindView(R.id.mission)
    LinearLayout mission;

    @Override
    protected int initViewResId() {
        return R.layout.fragment_chat2;
    }

    @Override
    protected void initView() {
        if (BuildConfig.IS_DEBUG){
            videoRecode.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {

    }

    public static ChatFragment2 getFragment() {
        return new ChatFragment2();
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


    public void setonclickListener(ViewClickListener listener) {
        this.listener = listener;
    }

    @OnClick({R.id.interrogation_table, R.id.mission, R.id.mdt_fenzhen, R.id.video_recode,R.id.plus_sign})
    public void onViewClicked(View view) {
        if (listener != null) {
            listener.onclick(view);
        }
    }
}
