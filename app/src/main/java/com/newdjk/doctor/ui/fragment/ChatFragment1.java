package com.newdjk.doctor.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
public class ChatFragment1 extends BasicFragment {

    Unbinder unbinder;
    @BindView(R.id.btn_image)
    LinearLayout btnImage;
    @BindView(R.id.btn_photo)
    LinearLayout btnPhoto;
    @BindView(R.id.the_quick_reply)
    LinearLayout theQuickReply;
    @BindView(R.id.add_your_resume)
    LinearLayout addYourResume;
    @BindView(R.id.advice_shop)
    LinearLayout adviceShop;

    @BindView(R.id.end_of_the_interrogation)
    LinearLayout endOfTheInterrogation;
    @BindView(R.id.video_table)
    LinearLayout videoTable;
    @BindView(R.id.mdt_fenzhen)
    LinearLayout mdtFenzhen;
    private ViewClickListener listener;

    @Override
    protected int initViewResId() {
        return R.layout.fragment_chat1;
    }

    @Override
    protected void initView() {

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

    public static ChatFragment1 getFragment() {
        return new ChatFragment1();
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

    @OnClick({R.id.btn_image, R.id.btn_photo, R.id.the_quick_reply, R.id.add_your_resume, R.id.advice_shop, R.id.video_table, R.id.end_of_the_interrogation,R.id.mdt_fenzhen})
    public void onViewClicked(View view) {

        if (listener != null) {
            listener.onclick(view);
        }
    }
}

