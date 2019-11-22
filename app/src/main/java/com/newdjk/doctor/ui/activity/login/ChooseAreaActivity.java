package com.newdjk.doctor.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.CityAdapter;
import com.newdjk.doctor.ui.entity.CityEntity;
import com.newdjk.doctor.utils.SpUtils;

import java.util.ArrayList;

import butterknife.BindView;

public class ChooseAreaActivity extends BasicActivity {

    private static final String TAG = "ChooseAreaActivity";


    @BindView(R.id.mRecyclerview_city)
    RecyclerView mRecyclerviewCity;
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
    @BindView(R.id.mRecyclerview_province)
    RecyclerView mRecyclerviewProvince;

    private Gson mGson;
    private LinearLayoutManager layoutManager;
    int index = 1;
    private int mHospitalPageCount = 10;
    private CityAdapter mCityAdapter;
    private ArrayList<CityEntity.DataBean> mCityList;
    private ArrayList<CityEntity.DataBean> mProvinceList;
    private int id = 0;
    private String queryAreaByParentId = HttpUrl.QueryAreaByParentId + "?ParentId=";

    private String HospitalName = "";
    private boolean isCity;
    private int areaId;
    private String mProvinceName;
    private String mCityName;
    private CityAdapter mProvinceAdapter;
    private LinearLayoutManager provincelayoutManager;
    private int mArreaid;
    private int mProvinceID;

    @Override
    protected int initViewResId() {
        return R.layout.activity_choose_area;
    }


    @Override
    protected void initView() {
        initTitle("选择地区").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mArreaid = SpUtils.getInt(Contants.chooseAreaID, 0);
        mGson = new Gson();
        //初始化省的列表
        initProvinceRecycleView();
        //初始化城市列表
        initCityRecycleView();
        //获取地区
        requestCityData(id, false);

    }

    private void requestCityData(int id, final boolean isCity) {
        loading(true);
        mMyOkhttp.get().url(queryAreaByParentId + id).tag(this).enqueue(new GsonResponseHandler<CityEntity>() {
            @Override
            public void onSuccess(int statusCode, CityEntity entituy) {
                loading(false);
                if (isCity) {
                    //市
                    if (entituy.getCode() == 0) {
                        mCityList.clear();
                        mCityList.addAll(entituy.getData());
                        mCityAdapter.setNewData(mCityList);

                        //获取省会成功之后，再根据省会获取市
                    } else {
                        toast(entituy.getMessage());
                    }
                } else {
                    //省分
                    if (entituy.getCode() == 0) {
                        mProvinceList.clear();
                        mProvinceList.addAll(entituy.getData());
                        mProvinceAdapter.setNewData(mProvinceList);
                        if (mProvinceList.size() > 0) {
                            // 保存之前选中的省份  滑动到之前选中的省份
                            for (int i = 0; i < mProvinceList.size(); i++) {
                                Log.d(TAG,"areaId "+areaId+"  "+mProvinceList.get(i).getAreaId());
                                if (mArreaid == mProvinceList.get(i).getAreaId()) {
                                    mRecyclerviewProvince.scrollToPosition(i);
                                }
                            }

                            if (mArreaid != 0) {
                                CityEntity.DataBean mProvinceDataBean = mProvinceList.get(0);
                                mProvinceName = mProvinceDataBean.getAreaName();
                                requestCityData(mArreaid, true);
                            } else {
                                //如果之前没有保存省份，就直接用第一个省份
                                CityEntity.DataBean mProvinceDataBean = mProvinceList.get(0);
                                mProvinceName = mProvinceDataBean.getAreaName();
                                requestCityData(mProvinceDataBean.getAreaId(), true);
                            }

                        }
                    } else {
                        toast(entituy.getMessage());
                    }
                }


            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                loading(false);
                Log.e("zdp", "statusCode=" + statusCode + ",errorMsg=" + errorMsg);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });

    }

    private void initCityRecycleView() {
        mCityList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        mRecyclerviewCity.setLayoutManager(layoutManager);
        mRecyclerviewCity.setHasFixedSize(true);
        mCityAdapter = new CityAdapter(mCityList);
        mRecyclerviewCity.setAdapter(mCityAdapter);
        mCityAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                // toast("获取地区"+mCityList.get(position).getAreaName());
                if (mCityList.size()>0){
                    Intent intent = new Intent();
                    intent.putExtra("area", mCityList.get(position).getAreaName());
                    intent.putExtra("areaid", mCityList.get(position).getAreaId());
                    SpUtils.put(Contants.chooseAreaID, mProvinceID);
                    mActivity.setResult(Activity.RESULT_OK, intent);
                    mActivity.finish();
                }


            }
        });
    }

    private void initProvinceRecycleView() {
        mProvinceList = new ArrayList<>();
        provincelayoutManager = new LinearLayoutManager(this);
        mRecyclerviewProvince.setLayoutManager(provincelayoutManager);
        mRecyclerviewProvince.setHasFixedSize(true);
        mProvinceAdapter = new CityAdapter(mProvinceList);
        mRecyclerviewProvince.setAdapter(mProvinceAdapter);
        mProvinceAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                HospitalEntity.DataBean.ReturnDataBean returnDataBean = mHospitalMessageList.get(position);
//                Intent intent = new Intent();
//                intent.putExtra("hospital_message", returnDataBean);
//                mActivity.setResult(Activity.RESULT_OK, intent);
//                mActivity.finish();
                CityEntity.DataBean mProvinceDataBean = mProvinceList.get(position);
                mProvinceName = mProvinceDataBean.getAreaName();
                mProvinceID = mProvinceDataBean.getAreaId();
                requestCityData(mProvinceDataBean.getAreaId(), true);
            }
        });
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

}
