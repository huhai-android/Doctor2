package com.newdjk.doctor.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.ui.adapter.HospitalMessageListAdapter;
import com.newdjk.doctor.ui.entity.HospitalEntity;
import com.newdjk.doctor.views.ClearEditText;
import com.newdjk.doctor.views.LoadDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   SearchHospitalActivity
 *  @创建者:   huhai
 *  @创建时间:  2019/5/21 11:35
 *  @描述：
 */
public class SearchHospitalActivity extends BasicActivity {
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
    @BindView(R.id.et_patient_msg)
    ClearEditText etPatientMsg;
    @BindView(R.id.mSearchContainer)
    LinearLayout mSearchContainer;
    @BindView(R.id.mRecyclerview_hospital)
    RecyclerView mRecyclerviewHospital;
    @BindView(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    private boolean isSearch;
    int index = 1;
    private int mHospitalPageCount = 10;
    private int areaId = 0;
    private String HospitalName = "";
    private ArrayList<HospitalEntity.DataBean.ReturnDataBean> mHospitalMessageList;
    private HospitalMessageListAdapter mHospitalMessageListAdapter;

    @Override
    protected int initViewResId() {
        return R.layout.activity_search_hospital;
    }

    @Override
    protected void initView() {
        initTitle("搜索医院").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initHospitalRecycleView();
        MyApplication.mActivities.add(this);
    }

    @Override
    protected void initListener() {
        etPatientMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = etPatientMsg.getText().toString();
//                    if (TextUtils.isEmpty(content)) {
//                        toast("请输入关键字");
//                        return true;
//                    }
                index = 1;
                isSearch = true;
                request(HttpUrl.QueryHospitalPage, areaId, index, mHospitalPageCount);
            }
        });


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


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        request(HttpUrl.QueryHospitalPage, areaId, index, mHospitalPageCount);
    }

    @Override
    protected void otherViewClick(View view) {

    }

    private void request(String url, int AreaId, int pageIndex, int pageSize) {
        // loading(true);

        mRecyclerviewHospital.setVisibility(View.VISIBLE);
        HospitalName = etPatientMsg.getText().toString();
        Map<String, String> paramsMap = new HashMap<>();
        if (isSearch == true) {
            if (!etPatientMsg.equals("")) {
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

        if (pageIndex == 1) {
            paramsMap.put("PageSize", "20");
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
                            mHospitalMessageListAdapter.getData().addAll(mHospitalMessageList);
                            mHospitalMessageListAdapter.notifyDataSetChanged();
                            mRecyclerviewHospital.scrollToPosition(postion);
                        }
                    } else {

                        if (index == 1) {
                            mHospitalMessageList.clear();
                        }else {
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

    private void initHospitalRecycleView() {
        mHospitalMessageList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerviewHospital.setLayoutManager(layoutManager);
        mRecyclerviewHospital.setHasFixedSize(true);
        mHospitalMessageListAdapter = new HospitalMessageListAdapter(mHospitalMessageList);
        mRecyclerviewHospital.setAdapter(mHospitalMessageListAdapter);
        mHospitalMessageListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                HospitalEntity.DataBean.ReturnDataBean returnDataBean = mHospitalMessageList.get(position);
//                Intent intent = new Intent();
//                intent.putExtra("hospital_message", returnDataBean);
//                mActivity.setResult(Activity.RESULT_OK, intent);
//                mActivity.finish();
                EventBus.getDefault().post(mHospitalMessageList.get(position));
                MyApplication.exit();
            }
        });
    }


}
