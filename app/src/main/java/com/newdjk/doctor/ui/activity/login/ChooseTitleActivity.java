package com.newdjk.doctor.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.CityAdapter;
import com.newdjk.doctor.ui.adapter.TitleAdapter;
import com.newdjk.doctor.ui.entity.HospitalEntity;
import com.newdjk.doctor.ui.entity.LoginEntity;
import com.newdjk.doctor.ui.entity.TitleEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.StrUtil;
import com.newdjk.doctor.views.LoadDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseTitleActivity extends BasicActivity {

    @BindView(R.id.top_left)
    ImageView topLeft;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.top_right)
    ImageView topRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.relat_titlebar)
    RelativeLayout relatTitlebar;
    @BindView(R.id.liear_titlebar)
    LinearLayout liearTitlebar;
    @BindView(R.id.mRecycleView)
    RecyclerView mRecycleView;
    private List<TitleEntity.DataBean> mTitleList;
    private LinearLayoutManager layoutManager;
    private TitleAdapter mTitleAdapter;

    private String mUrl = HttpUrl.QueryItemByCategoryId + "?CategoryId=";
    private int mUserType;

    @Override
    protected int initViewResId() {
        return R.layout.activity_choose_title;
    }

    @Override
    protected void initView() {
        initTitle("选择职称").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initRecycleView();
    }

    private void initRecycleView() {
        mTitleList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setHasFixedSize(true);
        mTitleAdapter = new TitleAdapter(mTitleList);
        mRecycleView.setAdapter(mTitleAdapter);
        mTitleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TitleEntity.DataBean titleBean = mTitleList.get(position);
                Intent intent = new Intent();
                intent.putExtra("titleBean", titleBean);
                mActivity.setResult(Activity.RESULT_OK, intent);
                mActivity.finish();
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mUserType = intent.getIntExtra("type", 0);
            mUrl = mUrl + mUserType;
        }
        LoadDialog.show(this);

        mMyOkhttp.get().url(mUrl).tag(this).enqueue(new GsonResponseHandler<TitleEntity>() {
            @Override
            public void onSuccess(int statusCode, TitleEntity entituy) {
                LoadDialog.clear();
                if (entituy.getCode() == 0) {
                    mTitleList = entituy.getData();
                    //mTitleAdapter.getData().addAll(mTitleList);
                    mTitleAdapter.setNewData(mTitleList);
                    mTitleAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.e("zdp", "statusCode=" + statusCode + ",errorMsg=" + errorMsg);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }


    @Override
    protected void otherViewClick(View view) {

    }


}
