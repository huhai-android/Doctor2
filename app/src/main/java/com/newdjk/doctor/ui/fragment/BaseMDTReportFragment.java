package com.newdjk.doctor.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.iInterface.OnPrescriptionChangeListener;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.IM.GroupChatActivity;
import com.newdjk.doctor.ui.activity.Mdt.MDTInputReportActivity;
import com.newdjk.doctor.ui.activity.Mdt.MDTInputReportFromListActivity;
import com.newdjk.doctor.ui.adapter.MDTfenzhenReportAdapter;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UnreadMessageEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.ZhuanZhenListItemDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.fragment
 *  @文件名:   BaseCheckFragment
 *  @创建者:   huhai
 *  @创建时间:  2018/11/30 11:15
 *  @描述：
 */
public abstract class BaseMDTReportFragment extends BasicFragment {
    private static final String TAG = "BaseCheckFragment";
    public static OnPrescriptionChangeListener listener;

    Unbinder unbinder;
    public Gson mGson;
    public int index = 1;
    @BindView(R.id.mRecyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.easylayout)
    EasyRefreshLayout easylayout;
    @BindView(R.id.iv_no)
    ImageView ivNo;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;
    private ZhuanZhenListItemDialog mDialog;
    public MDTfenzhenReportAdapter mOnlineRenewalDataAdapter;
    public List<MDTDetailEntity> dataList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        initView();
        initListener();
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        refreshView();
        return rootView;
    }

    protected abstract void refreshView();

    @Override
    protected int initViewResId() {
        return R.layout.fragment_mdt_zhuanzhen;
    }

    @Override
    protected void initView() {
        mGson = new Gson();
        mOnlineRenewalDataAdapter = new MDTfenzhenReportAdapter(dataList, this);

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_divider));
        mRecyclerview.addItemDecoration(divider);

        mRecyclerview.setAdapter(mOnlineRenewalDataAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        mOnlineRenewalDataAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (BaseMDTReportFragment.this instanceof MDTfenzhenReportFragment1) {

                } else if (BaseMDTReportFragment.this instanceof MDTfenzhenReportFragment2) {

                    if (dataList.get(position).getSubjectType() == 3) {

                       // List<MDTDetailEntity.MDTReportDoctorsBean> reportlist = dataList.get(position).getMDTReportDoctors();
                        //如果有未填写报告的，需要先填写报告
//                        for (int i = 0; i < reportlist.size(); i++) {
//                            if (reportlist.get(i).getIsSubmit() == 0) {
//                                toast("请先提醒其他会诊医生填写诊疗意见后再填写结论性建议");
//                                return;
//                            }
//                        }

                        Intent intentsuggest = new Intent(getContext(), MDTInputReportFromListActivity.class);
                        MDTDetailEntity data = dataList.get(position);
                        intentsuggest.putExtra("MDTDetailEntity", data);
                        intentsuggest.putExtra("type", 2);//已完成
                        startActivity(intentsuggest);
                    } else {
                        MDTDetailEntity data = dataList.get(position);
                        //首先需要判断是否已经出了报告  如果没有出报告，就跳转到先关的会诊报告界面
                            if (data.getIsNext()==1){  //表示正在进行中
                                //跳转相关会诊报告
//                                Intent intentsuggest = new Intent(getContext(), MDTInputReportFromListForNetActivity.class);
//                                intentsuggest.putExtra("MDTDetailEntity", data);
//                                intentsuggest.putExtra("type", 2);//已完成
//                                startActivity(intentsuggest);

                                Intent intentsuggest = new Intent(getContext(), MDTInputReportFromListActivity.class);
                                intentsuggest.putExtra("MDTDetailEntity", data);
                                intentsuggest.putExtra("type", 2);//已完成
                                startActivity(intentsuggest);
                            }else {
                                //否则就跳自己的报告
                                Intent intentsuggest = new Intent(getContext(), MDTInputReportFromListActivity.class);
                                intentsuggest.putExtra("MDTDetailEntity", data);
                                intentsuggest.putExtra("type", 2);//已完成
                                startActivity(intentsuggest);
                            }
                    }
                }
            }
        });

        mOnlineRenewalDataAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.accept_text:
                        List<MDTDetailEntity.MDTReportDoctorsBean> reportlist = dataList.get(position).getMDTReportDoctors();

                        if (BaseMDTReportFragment.this instanceof MDTfenzhenReportFragment1) {
                            MDTDetailEntity data = dataList.get(position);
                            if (data.getSubjectType()==2){
                                Intent intentsuggest = new Intent(getContext(), MDTInputReportActivity.class);
                                intentsuggest.putExtra("MDTDetailEntity", data);
                                startActivity(intentsuggest);
                            }else {
                                Intent intentsuggest = new Intent(getContext(), MDTInputReportFromListActivity.class);

                                intentsuggest.putExtra("MDTDetailEntity", data);
                                intentsuggest.putExtra("type", 1);//未完成
                                startActivity(intentsuggest);
                            }

                        } else if (BaseMDTReportFragment.this instanceof MDTfenzhenReportFragment2) {

                            MDTDetailEntity data = dataList.get(position);
                            if (data.getIsNext()==1){  //表示正在进行中
                                //跳转相关会诊报告
                                Intent intentsuggest = new Intent(getContext(), MDTInputReportFromListActivity.class);
                                intentsuggest.putExtra("MDTDetailEntity", data);
                                intentsuggest.putExtra("type", 2);//已完成
                                startActivity(intentsuggest);
                            }else {
                                //否则就跳自己的报告
                                Intent intentsuggest = new Intent(getContext(), MDTInputReportFromListActivity.class);
                                intentsuggest.putExtra("MDTDetailEntity", data);
                                intentsuggest.putExtra("type", 2);//已完成
                                startActivity(intentsuggest);
                            }

                        }


                        break;


                    case R.id.to_group_chat:
                        MDTDetailEntity data = dataList.get(position);
                        getIMRelationRecord(data);

                        break;
                }
            }
        });
    }

    //跳转群聊
    private void getIMRelationRecord(final MDTDetailEntity data) {

        Map<String, String> map = new HashMap<>();
        map.put("IMGroupId", data.getIMGroupId());
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        MyApplication.getInstance().getMyOkHttp().get().url(HttpUrl.QueryMDTBySubjectBuyIMGroupId).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTDetailEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTDetailEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    Intent intent = new Intent(mContext, GroupChatActivity.class);
                    intent.putExtra("action", "MDT");
                    intent.putExtra("MDTDetailEntity", entity.getData());
                    intent.putExtra(Contants.FRIEND_NAME, "");
                    intent.putExtra(Contants.FRIEND_IDENTIFIER, data.getIMGroupId());
                    mContext.startActivity(intent);

                } else {
                    Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }
                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
                LoadDialog.clear();
            }
        });
    }



    protected abstract void toDiseaseInfomation();


    @Override
    protected void initListener() {
        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                Log.d(TAG, "价值更多");
                index++;

            }

            @Override
            public void onRefreshing() {
                index = 1;
                easylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);

            }
        });

    }


    @Override
    protected void initData() {


        if (this instanceof MDTfenzhenReportFragment1) {

            getQueryDocReferralRecordPage(index, "1");


        } else if (this instanceof MDTfenzhenReportFragment2) {
            getQueryDocReferralRecordPage(index, "2");
        }

    }


    protected abstract void getQueryDocReferralRecordPage(int index, String s);


    @Override
    protected void otherViewClick(View view) {

    }


    public static BaseMDTReportFragment newInstance(List<UnreadMessageEntity> allUnreadMessageList, OnPrescriptionChangeListener changeListener, int type) {
        BaseMDTReportFragment myFragment = null;
        switch (type) {
            case 0:
                myFragment = new MDTfenzhenReportFragment1();
                break;
            case 1:
                myFragment = new MDTfenzhenReportFragment2();
                break;
            case 2:

        }
        listener = changeListener;
        return myFragment;
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
