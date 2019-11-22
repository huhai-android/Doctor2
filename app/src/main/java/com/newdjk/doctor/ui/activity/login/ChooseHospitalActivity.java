package com.newdjk.doctor.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.SearchHospitalActivity;
import com.newdjk.doctor.ui.adapter.HospitalAdapter;
import com.newdjk.doctor.ui.adapter.HospitalMessageListAdapter;
import com.newdjk.doctor.ui.entity.CityEntity;
import com.newdjk.doctor.ui.entity.HospitalEntity;
import com.newdjk.doctor.utils.LocationUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.SwitchAreaDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class ChooseHospitalActivity extends BasicActivity {

    private static final String TAG = "ChooseHospitalActivity";
    @BindView(R.id.mTvAddress)
    TextView mTvAddress;
    @BindView(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
    @BindView(R.id.mRecyclerview_hospital)
    RecyclerView mRecyclerview_hospital;
    @BindView(R.id.mRecyclerview_city)
    RecyclerView mRecyclerviewCity;
    @BindView(R.id.et_patient_msg)
    TextView editSearch;
    @BindView(R.id.mSearchContainer)
    LinearLayout mSearchContainer;
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
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.lv_choosearea)
    LinearLayout lvChoosearea;
    @BindView(R.id.lv_custom_hospital)
    LinearLayout lvCustomHospital;
    private LinearLayoutManager layoutManager;
    private HospitalMessageListAdapter mHospitalMessageListAdapter;
    private Gson mGson;
    private ArrayList<HospitalEntity.DataBean.ReturnDataBean> mHospitalMessageList;
    int index = 1;
    private int mHospitalPageCount = 20;
    private HospitalAdapter mCityAdapter;
    private ArrayList<CityEntity.DataBean> mCityList;
    private int id = 0;
    private String queryAreaByParentId = HttpUrl.QueryAreaByParentId + "?ParentId=";

    private String HospitalName = "";
    private boolean isCity;
    private int areaId;
    private String mProvinceName;
    private String mCityName;
    private boolean isSearch = false;
    private String address = "";
    private int requestcode = 2;
    private int requestcode3 = 2;
    private String mChooseaddress = "";
    private SwitchAreaDialog mDialog;

    @Override
    protected int initViewResId() {
        return R.layout.activity_choose_hospital;
    }


    private void request(String url, int AreaId, int pageIndex, int pageSize) {
        // loading(true);
        mRecyclerviewCity.setVisibility(View.GONE);
        mRecyclerview_hospital.setVisibility(View.VISIBLE);
        HospitalName = editSearch.getText().toString();
        Map<String, String> paramsMap = new HashMap<>();
        if (isSearch == true) {
            if (!HospitalName.equals("")) {
                paramsMap.put("HospitalName", HospitalName);
            }
        } else {
            if (!HospitalName.equals("")) {
                paramsMap.put("HospitalName", "");
            }
        }

        paramsMap.put("HospitalLevel", "-1");
        paramsMap.put("AreaId", AreaId + "");
        paramsMap.put("PageIndex", pageIndex + "");
        if (!TextUtils.isEmpty(address)) {
            paramsMap.put("AreaName", address + "");
        }
        if (pageIndex == 1) {
            paramsMap.put("PageSize", pageSize + "");
        } else {
            paramsMap.put("PageSize", pageSize + "");
        }

        mMyOkhttp.post().url(url).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<HospitalEntity>() {
            @Override
            public void onSuccess(int statusCode, HospitalEntity entituy) {
                LoadDialog.clear();
                if (mEasylayout == null) {
                    return;
                }
                if (mEasylayout.isRefreshing()) {
                    mEasylayout.refreshComplete();
                }
                if (mEasylayout.isLoading()) {
                    mEasylayout.loadMoreComplete();
                }
                if (entituy.getCode() == 0) {
                    List<HospitalEntity.DataBean.ReturnDataBean> returnData = entituy.getData().getReturnData();
                    if (returnData != null && returnData.size() > 0) {
                        if (returnData.size() < mHospitalPageCount) {
                            mEasylayout.setLoadMoreModel(LoadModel.NONE);
                        } else {
                            mEasylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
                        }
                        if (index == 1) {
                            mHospitalMessageList.clear();
                            mHospitalMessageList.addAll(returnData);
                            mHospitalMessageListAdapter.setNewData(mHospitalMessageList);
                        } else {
                            int postion = mHospitalMessageListAdapter.getData().size();
                            mHospitalMessageList.addAll(entituy.getData().getReturnData());
                            mHospitalMessageListAdapter.setNewData(mHospitalMessageList);
                            mRecyclerview_hospital.scrollToPosition(postion);
                        }
                    } else {
                        if (index != 1) {
                            toast("没有更多数据");
                        }
                        mHospitalMessageList.addAll(returnData);
                        mHospitalMessageListAdapter.setNewData(mHospitalMessageList);
                    }


                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                if (mEasylayout == null) {
                    return;
                }
                if (mEasylayout.isRefreshing()) {
                    mEasylayout.refreshComplete();
                }
                if (mEasylayout.isLoading()) {
                    mEasylayout.loadMoreComplete();
                }
                Log.e("zdp", "statusCode=" + statusCode + ",errorMsg=" + errorMsg);
                LoadDialog.clear();
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Override
    protected void initView() {

        initTitle("选择医院").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mGson = new Gson();
        initCityRecycleView();
        initHospitalRecycleView();
        //requestCityData(id);

        MyApplication.mActivities.add(this);
    }

    private void initlocation() {

        LocationUtils.getinstanse().initlocation(this, new LocationUtils.locationStatusListener() {
            @Override
            public void locationSuccess(final String data) {
                //如果历史不为空，并且定位到了其他城市
                if (!TextUtils.isEmpty(mChooseaddress) && !mChooseaddress.equals(data)) {

                    mDialog = new SwitchAreaDialog(ChooseHospitalActivity.this);
                    mDialog.show("温馨提示", data, new SwitchAreaDialog.DialogListener() {
                        @Override
                        public void cancel() {

                        }

                        @Override
                        public void confirm() {
                            SpUtils.put(Contants.chosearea, data);
                            tvLocation.setText(data);
                            //定位成功之后进行医院数据查询
                            address = data;
                            request(HttpUrl.QueryHospitalPage, 0, 1, mHospitalPageCount);
                        }
                    });
                    //定位的城市与之前保存的一致
                } else {
                    // toast("当前所在城市" + data);
                    if (!TextUtils.isEmpty(mChooseaddress)) {
                        tvLocation.setText(mChooseaddress);
                        //定位成功之后进行医院数据查询
                        address = mChooseaddress;
                        // request(HttpUrl.QueryHospitalPage, 0, 1, mHospitalPageCount);
                    } else {
                        tvLocation.setText(TextUtils.isEmpty(data)?"全国":data);
                        //定位成功之后进行医院数据查询
                        address = data;
                        request(HttpUrl.QueryHospitalPage, 0, 1, mHospitalPageCount);
                    }

                }
            }

            @Override
            public void locationFailed(String data) {
                if (TextUtils.isEmpty(mChooseaddress)) {
                    toast("定位失败"+data );
                    tvLocation.setText("全国");
                    request(HttpUrl.QueryHospitalPage, 0, 1, mHospitalPageCount);
                }
            }
        });
        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseHospitalActivity.this, ChooseAreaActivity.class);
                startActivityForResult(intent, requestcode);
            }
        });

        lvChoosearea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseHospitalActivity.this, ChooseAreaActivity.class);
                startActivityForResult(intent, requestcode);
            }
        });

        lvCustomHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseHospitalActivity.this, CustomHospitalActivity.class);
                startActivityForResult(intent, requestcode3);
            }
        });
    }

    private void requestCityData(int id) {
        queryAreaByParentId = queryAreaByParentId + id;
        mMyOkhttp.get().url(queryAreaByParentId).tag(this).enqueue(new GsonResponseHandler<CityEntity>() {
            @Override
            public void onSuccess(int statusCode, CityEntity entituy) {
                if (entituy.getCode() == 0) {
                    mCityList.clear();
                    mCityList.addAll(entituy.getData());
                    mCityAdapter.setNewData(mCityList);
                } else {
                    toast(entituy.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
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
        mCityAdapter = new HospitalAdapter(mCityList);
        mRecyclerviewCity.setAdapter(mCityAdapter);
        mCityAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                if (!isCity) {
                    CityEntity.DataBean mProvinceDataBean = mCityList.get(position);
                    mProvinceName = mProvinceDataBean.getAreaName();
                    requestCityData(mProvinceDataBean.getAreaId());
                    isCity = true;
                } else {
                    mSearchContainer.setVisibility(View.VISIBLE);
                    CityEntity.DataBean mCityDataBean = mCityList.get(position);
                    mCityName = mCityDataBean.getAreaName();
                    areaId = mCityDataBean.getAreaId();
                    mTvAddress.setText(mProvinceName + "  " + mCityName);
                    request(HttpUrl.QueryHospitalPage, areaId, 1, mHospitalPageCount);
                }
            }
        });
    }

    private void initHospitalRecycleView() {
        mHospitalMessageList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        mRecyclerview_hospital.setLayoutManager(layoutManager);
        mRecyclerview_hospital.setHasFixedSize(true);
        mHospitalMessageListAdapter = new HospitalMessageListAdapter(mHospitalMessageList);
        mRecyclerview_hospital.setAdapter(mHospitalMessageListAdapter);
        mHospitalMessageListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                HospitalEntity.DataBean.ReturnDataBean returnDataBean = mHospitalMessageList.get(position);
                Intent intent = new Intent();
                intent.putExtra("hospital_message", returnDataBean);
                mActivity.setResult(Activity.RESULT_OK, intent);
                mActivity.finish();
            }
        });
    }

    @Override
    protected void initListener() {
        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                index++;
                request(HttpUrl.QueryHospitalPage, areaId, index, mHospitalPageCount);
            }

            @Override
            public void onRefreshing() {
                index = 1;
                request(HttpUrl.QueryHospitalPage, areaId, index, mHospitalPageCount);
            }
        });
//
//        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    //搜索
//                    String content = editSearch.getText().toString();
////                    if (TextUtils.isEmpty(content)) {
////                        toast("请输入关键字");
////                        return true;
////                    }
//                    index = 1;
//                    isSearch=true;
//                    request(HttpUrl.QueryHospitalPage, areaId, index, mHospitalPageCount);
//                }
//                return false;
//            }
//        });
        editSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseHospitalActivity.this, SearchHospitalActivity.class);
                startActivityForResult(intent, requestcode);
            }
        });

//        editSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String content = editSearch.getText().toString();
////                    if (TextUtils.isEmpty(content)) {
////                        toast("请输入关键字");
////                        return true;
////                    }
//                index = 1;
//                isSearch = true;
//                request(HttpUrl.QueryHospitalPage, areaId, index, mHospitalPageCount);
//            }
//        });
    }


    @Override
    protected void initData() {
        mChooseaddress = SpUtils.getString(Contants.chosearea);

        address = mChooseaddress;
        if (!TextUtils.isEmpty(mChooseaddress)) {
            tvLocation.setText(mChooseaddress);
            request(HttpUrl.QueryHospitalPage, 0, 1, mHospitalPageCount);
        }

        initlocation();
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocationUtils.getinstanse().stopLocation();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String result = data.getExtras().getString("area");
                areaId = data.getExtras().getInt("areaid");
                Log.d(TAG, "切换地址" + result);
                SpUtils.put(Contants.chosearea, result);
                tvLocation.setText(result);
                //定位成功之后进行医院数据查询
                address = result;

                index = 1;
                mHospitalMessageList.clear();
                request(HttpUrl.QueryHospitalPage, areaId, 1, mHospitalPageCount);

            }
        }
    }


}
