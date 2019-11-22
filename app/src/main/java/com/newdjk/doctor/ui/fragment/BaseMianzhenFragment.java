package com.newdjk.doctor.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.ui.adapter.MianzhenAdapter;
import com.newdjk.doctor.ui.entity.MianzhenListEntity;

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
public abstract class BaseMianzhenFragment extends BasicFragment {

    private static final String TAG = "BaseMianzhenFragment";


    @BindView(R.id.recyleview)
    RecyclerView recyleview;
    @BindView(R.id.easylayout)
    EasyRefreshLayout easylayout;
    public int index = 1;
    public Gson mGson;
    public List<MianzhenListEntity.ReturnDataBean> dataList = new ArrayList<>();
    public MianzhenAdapter mMianzhenAdapterr;
    @BindView(R.id.iv_no)
    ImageView ivNo;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;
    Unbinder unbinder;


    @Override
    protected int initViewResId() {
        return R.layout.fragment_mianzhen;
    }

    @Override
    protected void initView() {
        mGson = new Gson();
        mMianzhenAdapterr = new MianzhenAdapter(dataList, this);
        recyleview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyleview.setAdapter(mMianzhenAdapterr);
        recyleview.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));

    }

    @Override
    protected void initListener() {
        mMianzhenAdapterr.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.im_arrow) {
                    if (dataList.get(position).isIshide()) {
                        dataList.get(position).setIshide(false);
                    } else {
                        dataList.get(position).setIshide(true);
                    }
                    mMianzhenAdapterr.notifyItemChanged(position);
                }
            }
        });
        mMianzhenAdapterr.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (dataList.get(position).isIshide()) {
                    dataList.get(position).setIshide(false);
                } else {
                    dataList.get(position).setIshide(true);
                }
                mMianzhenAdapterr.notifyItemChanged(position);
            }
        });

    }

    @Override
    protected void initData() {

    }

    public abstract void GetDrFaceConsultationRecord(int index, int i);

    @Override
    protected void otherViewClick(View view) {

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
}

