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
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.iInterface.OnPrescriptionChangeListener;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.PrescriptionCheckActivity;
import com.newdjk.doctor.ui.adapter.WaitPrescriptionCheckAdapter;
import com.newdjk.doctor.ui.entity.OnlineRenewalMessageEntity;
import com.newdjk.doctor.ui.entity.PrescriptionEntity;
import com.newdjk.doctor.ui.entity.UnreadMessageEntity;
import com.newdjk.doctor.ui.entity.YWXListenerEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.DownloadCertDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.org.bjca.sdk.core.kit.BJCASDK;
import cn.org.bjca.sdk.core.kit.YWXListener;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.fragment
 *  @文件名:   BaseCheckFragment
 *  @创建者:   huhai
 *  @创建时间:  2018/11/30 11:15
 *  @描述：
 */
public abstract class BaseCheckFragment extends BasicFragment {
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
    public OnlineRenewalMessageEntity mOnlineRenewalMessageEntity;
    public WaitPrescriptionCheckAdapter mOnlineRenewalDataAdapter;
    public List<UnreadMessageEntity> mAllUnreadMessageList;
    public int index = 1;
    public List<PrescriptionEntity.ReturnDataBean> dataList = new ArrayList<>();
    public PrescriptionEntity onlineRenewalMessageEntity;
    public PrescriptionEntity.ReturnDataBean returnDataBean;
    public int clickposition;
    public int total = 0;
    private Gson mGson;

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
        return rootView;
    }

    @Override
    protected int initViewResId() {
        return R.layout.consult_list;
    }

    @Override
    protected void initView() {
        mGson = new Gson();
        mOnlineRenewalDataAdapter = new WaitPrescriptionCheckAdapter(dataList, this);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerview.setAdapter(mOnlineRenewalDataAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
    }

    @Override
    protected void initListener() {
        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                Log.d(TAG, "价值更多");
                index++;
                getOnlinePartyList(index, "0");
            }

            @Override
            public void onRefreshing() {
                index = 1;
                mEasylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
                getOnlinePartyList(index, "0");
            }
        });

        mOnlineRenewalDataAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (dataList.size()>0){
                    returnDataBean = dataList.get(position);
                }

                clickposition = position;
                Log.i("AllDoctorDataAdapter", "跳转");
                boolean isExists = BJCASDK.getInstance().existsCert(getContext());
                boolean ExistStamp = BJCASDK.getInstance().existsStamp(getContext());
                /// BJCASDK.getInstance().clearCert(ChatActivity.this);
                // BJCASDK.getInstance().startUrl(ChatActivity.this, Contants.clientId, 3);
                // BJCASDK.getInstance().startUrl(ChatActivity.this, Contants.clientId, 1);
                if (!isExists) {

                    DownloadCertDialog mDialog = new DownloadCertDialog(mActivity);
                    mDialog.show("", "", new DownloadCertDialog.DialogListener() {

                        @Override
                        public void confirm() {
                            BJCASDK.getInstance().certDown(mActivity, Contants.clientId, SpUtils.getString(Contants.userName), new YWXListener() {
                                @Override
                                public void callback(String s) {
                                    YWXListenerEntity yWXListenerEntity = mGson.fromJson(s, YWXListenerEntity.class);
                                    String status = yWXListenerEntity.getStatus();
                                    String message = yWXListenerEntity.getMessage();
                                    if (status != null && status.equals("0")) {
                                        boolean ExistStamp1 = BJCASDK.getInstance().existsStamp(getContext());
                                        if (!ExistStamp1) {
                                            BJCASDK.getInstance().drawStamp(mActivity, Contants.clientId, new YWXListener() {
                                                @Override
                                                public void callback(String msg) {
                                                    YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                                    String status = yWXListenerEntity.getStatus();
                                                    String message = yWXListenerEntity.getMessage();
                                                    if (status != null && status.equals("0")) {
                                                        Intent prescriptionIntent = new Intent(getContext(), PrescriptionCheckActivity.class);
                                                        prescriptionIntent.putExtra("rejectId", String.valueOf(returnDataBean.getRejectReasonId()));
                                                        prescriptionIntent.putExtra("action", String.valueOf(returnDataBean.getId()));
                                                        prescriptionIntent.putExtra("prescriptionMessage", SpUtils.getString(Contants.LoginJson));
                                                        prescriptionIntent.putExtra("from", 1);
                                                        prescriptionIntent.putExtra("prescriptionType", returnDataBean.getPrescriptionType());

                                                        if (BaseCheckFragment.this instanceof HaveCheckPrescriptionFragment) {
                                                            prescriptionIntent.putExtra("type", -1);

                                                        } else if (BaseCheckFragment.this instanceof RefuseCheckPrescriptionFragment) {
                                                            prescriptionIntent.putExtra("type", 2);

                                                        } else if (BaseCheckFragment.this instanceof WaitForCheckPrescriptionFragment) {
                                                            prescriptionIntent.putExtra("type", 0);

                                                        }


                                                        startActivity(prescriptionIntent);
                                                    } else {
                                                        //  Toast.makeText(mActivity,message,Toast.LENGTH_SHORT).show();
                                                        ToastUtil.setToast("设置签章失败，请重试！+(" + status + ")");
                                                    }
                                                }
                                            });
                                        } else {
                                            Intent prescriptionIntent = new Intent(getContext(), PrescriptionCheckActivity.class);
                                            prescriptionIntent.putExtra("rejectId", String.valueOf(returnDataBean.getRejectReasonId()));
                                            prescriptionIntent.putExtra("action", String.valueOf(returnDataBean.getId()));
                                            prescriptionIntent.putExtra("prescriptionMessage", SpUtils.getString(Contants.LoginJson));
                                            prescriptionIntent.putExtra("from", 1);
                                            prescriptionIntent.putExtra("prescriptionType", returnDataBean.getPrescriptionType());
                                            if (BaseCheckFragment.this instanceof HaveCheckPrescriptionFragment) {
                                                prescriptionIntent.putExtra("type", -1);

                                            } else if (BaseCheckFragment.this instanceof RefuseCheckPrescriptionFragment) {
                                                prescriptionIntent.putExtra("type", 2);

                                            } else if (BaseCheckFragment.this instanceof WaitForCheckPrescriptionFragment) {
                                                prescriptionIntent.putExtra("type", 0);

                                            }


                                            startActivity(prescriptionIntent);
                                        }
                                    } else {
                                        //  Toast.makeText(mActivity,message,Toast.LENGTH_SHORT).show();
                                        Toast.makeText(mActivity, "下载证书失败，请重试!(" + status + ")", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                        }
                    });


                } else {
                    //   BJCASDK.getInstance().clearCert(ChatActivity.this);
                    Log.i("zdp", "证书已下载");

                    if (!ExistStamp) {
                        BJCASDK.getInstance().drawStamp(mActivity, Contants.clientId, new YWXListener() {
                            @Override
                            public void callback(String msg) {
                                YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                String status = yWXListenerEntity.getStatus();
                                String message = yWXListenerEntity.getMessage();
                                if (status != null && status.equals("0")) {
                                    Intent prescriptionIntent = new Intent(getContext(), PrescriptionCheckActivity.class);
                                    prescriptionIntent.putExtra("rejectId", String.valueOf(returnDataBean.getRejectReasonId()));
                                    prescriptionIntent.putExtra("action", String.valueOf(returnDataBean.getId()));
                                    prescriptionIntent.putExtra("prescriptionMessage", SpUtils.getString(Contants.LoginJson));
                                    prescriptionIntent.putExtra("from", 1);
                                    prescriptionIntent.putExtra("prescriptionType", returnDataBean.getPrescriptionType());
                                    if (BaseCheckFragment.this instanceof HaveCheckPrescriptionFragment) {
                                        prescriptionIntent.putExtra("type", -1);

                                    } else if (BaseCheckFragment.this instanceof RefuseCheckPrescriptionFragment) {
                                        prescriptionIntent.putExtra("type", 2);

                                    } else if (BaseCheckFragment.this instanceof WaitForCheckPrescriptionFragment) {
                                        prescriptionIntent.putExtra("type", 0);

                                    }


                                    startActivity(prescriptionIntent);
                                } else {
                                    //   Toast.makeText(mActivity,message,Toast.LENGTH_SHORT).show();
                                    ToastUtil.setToast("设置签章失败，请重试！+(" + status + ")");
                                }
                            }
                        });
                    } else {
                        Intent prescriptionIntent = new Intent(getContext(), PrescriptionCheckActivity.class);
                        prescriptionIntent.putExtra("rejectId", String.valueOf(returnDataBean.getRejectReasonId()));
                        prescriptionIntent.putExtra("action", String.valueOf(returnDataBean.getId()));
                        prescriptionIntent.putExtra("prescriptionMessage", SpUtils.getString(Contants.LoginJson));
                        prescriptionIntent.putExtra("from", 1);
                        prescriptionIntent.putExtra("prescriptionType", returnDataBean.getPrescriptionType());
                        if (BaseCheckFragment.this instanceof HaveCheckPrescriptionFragment) {
                            prescriptionIntent.putExtra("type", -1);

                        } else if (BaseCheckFragment.this instanceof RefuseCheckPrescriptionFragment) {
                            prescriptionIntent.putExtra("type", 2);

                        } else if (BaseCheckFragment.this instanceof WaitForCheckPrescriptionFragment) {
                            prescriptionIntent.putExtra("type", 0);

                        }


                        startActivity(prescriptionIntent);
                    }
                }
            }
        });

    }

    @Override
    protected void initData() {


        if (this instanceof HaveCheckPrescriptionFragment) {
            getOnlinePartyList(index, "-1");

        } else if (this instanceof RefuseCheckPrescriptionFragment) {
            getOnlinePartyList(index, "2");

        } else if (this instanceof WaitForCheckPrescriptionFragment) {
            getOnlinePartyList(index, "0");

        }

    }

    protected abstract void getOnlinePartyList(int index, String s);


    @Override
    protected void otherViewClick(View view) {

    }


    public static BaseCheckFragment newInstance(List<UnreadMessageEntity> allUnreadMessageList, OnPrescriptionChangeListener changeListener, int type) {
        BaseCheckFragment myFragment = null;
        switch (type) {
            case 0:
                myFragment = new WaitForCheckPrescriptionFragment();
                break;
            case 1:
                myFragment = new RefuseCheckPrescriptionFragment();
                break;
            case 2:
                myFragment = new HaveCheckPrescriptionFragment();
                break;
        }
        listener = changeListener;
        return myFragment;
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
