package com.newdjk.doctor.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.iInterface.OnPrescriptionChangeListener;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.ArchivesActivity;
import com.newdjk.doctor.ui.adapter.FenjizhuanzhenAdapter;
import com.newdjk.doctor.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.doctor.ui.entity.OnlineRenewalMessageEntity;
import com.newdjk.doctor.ui.entity.PatientInfoEntity;
import com.newdjk.doctor.ui.entity.PrescriptionEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.ui.entity.UnreadMessageEntity;
import com.newdjk.doctor.ui.entity.ZhanZhenListEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.GroupButtonDialog;
import com.newdjk.doctor.views.ZhuanZhenListItemDialog;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
public abstract class BaseZhuanZhenFragment extends BasicFragment {
    private static final String TAG = "BaseCheckFragment";
    public static OnPrescriptionChangeListener listener;
    @BindView(R.id.iv_no)
    public ImageView ivNo;
    @BindView(R.id.mNodataContainer)
    public RelativeLayout mNodataContainer;
    @BindView(R.id.mRecyclerview)
    public RecyclerView mRecyclerview;
    @BindView(R.id.easylayout)
    public EasyRefreshLayout mEasylayout;
    @BindView(R.id.im_scan)
    ImageView imScan;
    @BindView(R.id.re_scan_container)
    RelativeLayout reScanContainer;
    Unbinder unbinder;
    public Gson mGson;
    public OnlineRenewalMessageEntity mOnlineRenewalMessageEntity;
    public FenjizhuanzhenAdapter mOnlineRenewalDataAdapter;
    public List<UnreadMessageEntity> mAllUnreadMessageList;
    public int index = 1;
    public List<ZhanZhenListEntity.ReturnDataBean> dataList = new ArrayList<>();
    public ZhanZhenListEntity onlineRenewalMessageEntity;
    public PrescriptionEntity.ReturnDataBean returnDataBean;
    public int clickposition;
    public int total = 0;
    private ZhuanZhenListItemDialog mDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

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
        return R.layout.fragment_zhuanzhen;
    }

    @Override
    protected void initView() {
        mGson = new Gson();
        mOnlineRenewalDataAdapter = new FenjizhuanzhenAdapter(dataList, this);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerview.setAdapter(mOnlineRenewalDataAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));

        mOnlineRenewalDataAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

               // toPatientDesc(dataList.get(position));
                showZhuanZhenDialog(dataList.get(position));

            }
        });
        //确认将xx患者转回给xx医生
        mOnlineRenewalDataAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                if (view.getId() == R.id.tv_refuse) {
                    final GroupButtonDialog groupButtonDialog = new GroupButtonDialog(mContext);

                    String patien = dataList.get(position).getPatientName();
                    String doctor = dataList.get(position).getFromDrName();
                    final int referralRecordId = dataList.get(position).getReferralRecordId();
                    groupButtonDialog.show("转回提醒", "确认将" + patien + "患者转回给" + doctor + "医生", new GroupButtonDialog.DialogListener() {
                        @Override
                        public void cancel() {

                        }

                        @Override
                        public void confirm() {
                            //   ToastUtil.setToast("已经撤回");
                            refuseZhuanzhen(referralRecordId+"",position);
                        }
                    });
                }
            }
        });
    }

    private void showZhuanZhenDialog(final ZhanZhenListEntity.ReturnDataBean returnDataBean) {

        mDialog = new ZhuanZhenListItemDialog(getContext());
        mDialog.show("转诊记录", returnDataBean, new ZhuanZhenListItemDialog.DialogListener() {
            @Override
            public void cancel() {

            }

            @Override
            public void confirm() {
                toPatientDesc(returnDataBean);
            }
        });
    }

    protected abstract void refuseZhuanzhen(String referralRecordId,int position);

    @Override
    protected void initListener() {
        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                Log.d(TAG, "价值更多");
                index++;
                getQueryDocReferralRecordPage(index, "0");
            }

            @Override
            public void onRefreshing() {
                index = 1;
                mEasylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
                getQueryDocReferralRecordPage(index, "0");
            }
        });


        imScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScanJiezhen();
            }
        });

    }

    public void startScanJiezhen() {
    }

    @Override
    protected void initData() {


        if (this instanceof ZhuanZhenFragment1) {


        } else if (this instanceof ZhuanZhenFragment2) {
            getQueryDocReferralRecordPage(index, "0");

        } else if (this instanceof ZhuanZhenFragment3) {
            getQueryDocReferralRecordPage(index, "1");

        }

    }

    protected abstract void getQueryDocReferralRecordPage(int index, String s);


    @Override
    protected void otherViewClick(View view) {

    }


    public static BaseZhuanZhenFragment newInstance(List<UnreadMessageEntity> allUnreadMessageList, OnPrescriptionChangeListener changeListener, int type) {
        BaseZhuanZhenFragment myFragment = null;
        switch (type) {
            case 0:
                myFragment = new ZhuanZhenFragment1();
                break;
            case 1:
                myFragment = new ZhuanZhenFragment2();
                break;
            case 2:
                myFragment = new ZhuanZhenFragment3();
                break;
        }
        listener = changeListener;
        return myFragment;
    }

    private void toPatientDesc(ZhanZhenListEntity.ReturnDataBean entityResponseEntity) {

        PatientInfoEntity PatientInfo = new PatientInfoEntity();

        //   PatientInfo.setBirthday(mDataList.get(position).getBirthday());
        String paName = entityResponseEntity.getPatientName();

        PatientInfo.setPatientName(paName);
        PatientInfo.setPatientId(entityResponseEntity.getPatientId());
        PatientInfo.setPatientSex(entityResponseEntity.getPatientSex());
        PatientInfo.setAge(String.valueOf(entityResponseEntity.getAge()));
//            DoctorPatientRelationEntity DoctorPatientRelation = new DoctorPatientRelationEntity();
//            DoctorPatientRelation.setDrPatientId(entityResponseEntity.getDrPatientId());

        Type jsonType = new TypeToken<PrescriptionMessageEntity<InquiryRecordListDataEntity>>() {
        }.getType();
        InquiryRecordListDataEntity InquiryRecordListDataEntity = new InquiryRecordListDataEntity();
        //InquiryRecordListDataEntity.setDoctorPatientRelation(DoctorPatientRelation);
        InquiryRecordListDataEntity.setPatientInfo(PatientInfo);
        PrescriptionMessageEntity<InquiryRecordListDataEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), jsonType);
        prescriptionMessageEntity.setPatient(InquiryRecordListDataEntity);
        String json = mGson.toJson(prescriptionMessageEntity);
        Intent ArchivesIntent = new Intent(mActivity, ArchivesActivity.class);
        ArchivesIntent.putExtra("action", "patientList");
        ArchivesIntent.putExtra("prescriptionMessage", json);
        mActivity.startActivity(ArchivesIntent);
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
