package com.newdjk.doctor.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.ui.adapter.DepartmentChildAdapter;
import com.newdjk.doctor.ui.adapter.DepartmentRootAdapter;
import com.newdjk.doctor.ui.entity.DePartmentEntity;
import com.newdjk.doctor.views.LoadDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class ChooseDepartmentActivity extends BasicActivity {


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
    @BindView(R.id.mRootRecyclerView)
    RecyclerView mRootRecyclerView;
    @BindView(R.id.mChildRecyclerView)
    RecyclerView mChildRecyclerView;
    private List<DePartmentEntity.DataBean.ReturnDataBean> mRootList;
    private LinearLayoutManager layoutManager;
    private DepartmentRootAdapter mDepartmentRootAdapter;
    private List<DePartmentEntity.DataBean.ReturnDataBean> mChildList;
    private DepartmentChildAdapter mDepartmentChildAdapter;

    @Override
    protected int initViewResId() {
        return R.layout.activity_choose_department;
    }

    @Override
    protected void initView() {
        initTitle("选择科室").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        requestRootData();

        initRootRecycleView();
        initChildRecycleView();
    }

    private void requestRootData() {
        //loading(true);
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("DepartmentName", "");
        bodyMap.put("ParentId", 0 + "");
        bodyMap.put("PageSize", 100 + "");
        mMyOkhttp.post().url(HttpUrl.QueryDepartmentPage).params(bodyMap).tag(this).enqueue(new GsonResponseHandler<DePartmentEntity>() {
            @Override
            public void onSuccess(int statusCode, DePartmentEntity entituy) {
                LoadDialog.clear();
                LoadDialog.clear();
                if (entituy.getCode() == 0) {
                    mRootList = entituy.getData().getReturnData();
                    mDepartmentRootAdapter.getData().addAll(mRootList);
                    mDepartmentRootAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                LoadDialog.clear();
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }


    private void initRootRecycleView() {
        mRootList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        mRootRecyclerView.setLayoutManager(layoutManager);
        mRootRecyclerView.setHasFixedSize(true);
        mDepartmentRootAdapter = new DepartmentRootAdapter(mRootList);
        mRootRecyclerView.setAdapter(mDepartmentRootAdapter);
        mDepartmentRootAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                LoadDialog.show(ChooseDepartmentActivity.this);
                DePartmentEntity.DataBean.ReturnDataBean returnDataBean = mRootList.get(position);
                requestChildData(returnDataBean.getDepartmentId());
                mDepartmentRootAdapter.setThisPosition(position);
                adapter.notifyDataSetChanged();
            }

        });
    }

    /**
     * 请求科室的详细的数据
     *
     * @param departmentId
     */
    private void requestChildData(int departmentId) {
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("DepartmentName", "");
        bodyMap.put("ParentId", departmentId + "");
        bodyMap.put("PageSize", 100 + "");
        mMyOkhttp.post().url(HttpUrl.QueryDepartmentPage).params(bodyMap).tag(this).enqueue(new GsonResponseHandler<DePartmentEntity>() {
            @Override
            public void onSuccess(int statusCode, DePartmentEntity entituy) {
                LoadDialog.clear();
                if (entituy.getCode() == 0) {
                    mChildList = entituy.getData().getReturnData();
                    mDepartmentChildAdapter.setNewData(mChildList);
                    mDepartmentChildAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void initChildRecycleView() {
        mChildList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        mChildRecyclerView.setLayoutManager(layoutManager);
        mChildRecyclerView.setHasFixedSize(true);
        mDepartmentChildAdapter = new DepartmentChildAdapter(mChildList);
        mChildRecyclerView.setAdapter(mDepartmentChildAdapter);
        mDepartmentChildAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                DePartmentEntity.DataBean.ReturnDataBean returnDataBean = mChildList.get(position);
                Intent intent = new Intent();
                intent.putExtra("returnDataBean", returnDataBean);
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

    }

    @Override
    protected void otherViewClick(View view) {

    }

}
