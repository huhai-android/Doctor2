package com.newdjk.doctor.ui.activity.Mdt;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
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
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.BuildConfig;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.tools.MainConstant;
import com.newdjk.doctor.ui.activity.PrescriptionActivity;
import com.newdjk.doctor.ui.activity.ServiceDetailActivity;
import com.newdjk.doctor.ui.activity.WebViewActivity;
import com.newdjk.doctor.ui.adapter.GroupMessageAdapter;
import com.newdjk.doctor.ui.entity.ConversationEntity;
import com.newdjk.doctor.ui.entity.CustomMessageEntity;
import com.newdjk.doctor.ui.entity.HosGroupEntity;
import com.newdjk.doctor.ui.entity.ImDataEntity;
import com.newdjk.doctor.ui.entity.MDTOrderEntity;
import com.newdjk.doctor.ui.entity.MDTfenzhenReportEntity;
import com.newdjk.doctor.ui.entity.MessageEventRecent;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UpdateImMessageEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.ItemView;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMGroupDetailInfo;
import com.tencent.TIMGroupManager;
import com.tencent.TIMGroupTipsElem;
import com.tencent.TIMGroupTipsType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.newdjk.doctor.MyApplication.getContext;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   MDTActivity
 *  @创建者:   huhai
 *  @创建时间:  2019/8/29 10:26
 *  @描述：
 */
public class MDTActivity extends BasicActivity {


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
    @BindView(R.id.message_recycler_view)
    RecyclerView messageRecyclerView;
    @BindView(R.id.mFunReport)
    ItemView mFunReport;
    @BindView(R.id.easylayout)
    EasyRefreshLayout easylayout;
    @BindView(R.id.iv_mdtfenzhen)
    LinearLayout ivMdtfenzhen;
    @BindView(R.id.lv_zhuanzhen)
    LinearLayout lvZhuanzhen;
    @BindView(R.id.myreport)
    LinearLayout myreport;
    @BindView(R.id.lv_hos_group)
    LinearLayout lvHosGroup;
    @BindView(R.id.tv_hos_group)
    TextView tvHosGroup;
    @BindView(R.id.image_picture)
    ImageView imagePicture;
    @BindView(R.id.unread_num)
    TextView unreadNum;
    @BindView(R.id.unread_num_layout)
    RelativeLayout unreadNumLayout;
    @BindView(R.id.image_picture2)
    ImageView imagePicture2;
    @BindView(R.id.unread_num_myreport)
    TextView unreadNumMyreport;
    @BindView(R.id.unread_num_layout_myreport)
    RelativeLayout unreadNumLayoutMyreport;
    @BindView(R.id.crd_mdt_group)
    CardView crdMdtGroup;
    private String TAG = "MDTActivity";
    private List<ImDataEntity> mConversationListData = new ArrayList<>();
    public Gson mGson = new Gson();
    private GroupMessageAdapter mAdapter;
    private HosGroupEntity mGroupEntity;
    private int mTotal1;
    private int mTotal2;
    private int total;
    private boolean isRefresh = false;

    @Override
    protected int initViewResId() {
        return R.layout.activity_mdt;
    }

    @Override
    protected void initView() {
        initTitle("专科协作").setRightText("帮助").setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent zhoangyaochufang = new Intent(getContext(), PrescriptionActivity.class);
//                zhoangyaochufang.putExtra("type", 29);
//                startActivity(zhoangyaochufang);

                String url = "";
                if (BuildConfig.IS_DEBUG) {
                    url = "http://drwechat.newstarthealth.cn/index.html#/mdtIntro?fromAPP=1";
                } else {
                    url = "https://drwechat.newstartcare.com/index.html#/mdtIntro?fromAPP=1";
                }

                Intent intent = new Intent(MDTActivity.this, ServiceDetailActivity.class);
                intent.putExtra("title", "帮助中心");
                intent.putExtra("LinkUrl", url);
                startActivity(intent);
            }
        }).setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvRight.setTextColor(ContextCompat.getColor(this, R.color.theme));
        EventBus.getDefault().register(this);
        mAdapter = new GroupMessageAdapter(mConversationListData);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        messageRecyclerView.setAdapter(mAdapter);
        messageRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }


    @Override
    protected void initListener() {
        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onRefreshing() {
                getgroupList();
            }
        });

        lvZhuanzhen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SpecialHelpIntent = new Intent(getContext(), WebViewActivity.class);
                SpecialHelpIntent.putExtra("type", 11);
                startActivity(SpecialHelpIntent);
            }
        });
        myreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fenjizhuanzhen = new Intent(mContext, MDTFenZhenReportActivity.class);
                startActivity(fenjizhuanzhen);
            }
        });

        ivMdtfenzhen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fenjizhuanzhen = new Intent(mContext, MDTFenZhenActivity.class);
                startActivity(fenjizhuanzhen);
            }
        });

        mFunReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentcheck = new Intent(getContext(), PrescriptionActivity.class);
                intentcheck.putExtra("type", 24);
                startActivity(intentcheck);
            }
        });
        lvHosGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGroupEntity!=null){
                    if (mGroupEntity.getTotal() > 0) {
                        Intent intentcheck = new Intent(getContext(), PrescriptionActivity.class);
                        intentcheck.putExtra("type", 25);
                        intentcheck.putExtra("hosGroupid", mGroupEntity.getReturnData().get(0).getSpecialistHosGroupId() + "");

                        startActivity(intentcheck);
                    }
                }


            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventRecent event) {
        Log.d(TAG, "收到新消息" + event.getmType());
        switch (event.getmType()) {

            case MainConstant.UpdateRecentList:
                // getgroupList();
                break;

        }
    }


    @Override
    protected void initData() {

    }

    private void getMyreport() {

        Map<String, String> map = new HashMap<>();
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("CompleteStat", "1");
        map.put("PageIndex", 1 + "");
        map.put("PageSize", "10");


        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.QueryDoctorMDTReportPage).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTfenzhenReportEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTfenzhenReportEntity> entity) {
                if (entity.getCode() == 0) {
                    if (entity.getData() != null) {

                        int total = entity.getData().getTotal();

                        if (total == 0) {
                            unreadNumLayoutMyreport.setVisibility(View.GONE);
                        } else {
                            unreadNumLayoutMyreport.setVisibility(View.VISIBLE);
                            unreadNumMyreport.setText(total + "");
                        }

                    }
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {

                //  toast(errorMsg + "");
            }
        });

    }

    private void initunreadNumber() {

        QueryUnReceiveBuyRecordPage1("1");
        getMyreport();
        getgroupList();
        getHosGroupForPage();
    }

    private void QueryUnReceiveBuyRecordPage1(String type) {

        Map<String, String> map = new HashMap<>();
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("TriageType", type);
        Log.d(TAG, "医生id" + String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("PageIndex", "1");
        map.put("PageSize", "10");

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.QueryUnReceiveBuyRecordPage).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTOrderEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTOrderEntity> entity) {
                mTotal1 = entity.getData().getTotal();
                QueryUnReceiveBuyRecordPage2("2");

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {

            }
        });
    }

    private void QueryUnReceiveBuyRecordPage2(String type) {

        Map<String, String> map = new HashMap<>();
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("TriageType", type);
        Log.d(TAG, "医生id" + String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("PageIndex", "1");
        map.put("PageSize", "10");

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.QueryUnReceiveBuyRecordPage).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTOrderEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTOrderEntity> entity) {
                mTotal2 = entity.getData().getTotal();
                total = mTotal1 + mTotal2;
                if (total == 0) {
                    unreadNumLayout.setVisibility(View.GONE);
                } else {
                    unreadNumLayout.setVisibility(View.VISIBLE);
                    unreadNum.setText(total + "");
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {

            }
        });
    }

    private void getHosGroupForPage() {

        Map<String, String> map = new HashMap<>();
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        headMap.put("IsMDT", "1");
        mMyOkhttp.post().url(HttpUrl.QueryDrSpecialistHosGroupForPage).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<HosGroupEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<HosGroupEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    if (entity.getData() != null) {
                        mGroupEntity = entity.getData();
                        if (mGroupEntity.getTotal() > 1) {

                            mFunReport.setRightText("更多");
                            mFunReport.setClickable(true);
                            lvHosGroup.setClickable(true);
                            tvHosGroup.setText(mGroupEntity.getReturnData().get(0).getHosGroupName() + "(" + mGroupEntity.getReturnData().get(0).getNumMember() + ")");
                        } else if (mGroupEntity.getTotal() == 1) {
                            mFunReport.setClickable(true);
                            lvHosGroup.setClickable(true);
                            tvHosGroup.setText(mGroupEntity.getReturnData().get(0).getHosGroupName() + "(" + mGroupEntity.getReturnData().get(0).getNumMember() + ")");
                        } else {
                            mFunReport.setRightText("");
                            mFunReport.setClickable(false);
                            tvHosGroup.setText("暂无团队");
                            lvHosGroup.setClickable(false);
                        }


                    }
                } else {
                    // Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                // CommonMethod.requestError(statusCode, errorMsg);

            }
        });
    }

    private void getgroupList() {

        if (isRefresh) {
            return;
        }
        List<TIMConversation> conversionList = TIMManager.getInstance().getConversionList();
        Log.d(TAG, "最近会话列表1----" + conversionList.size());
        List<String> doctorIdList = new ArrayList<>();
        doctorIdList.clear();
        isRefresh = true;

        for (TIMConversation timConversation : conversionList) {
            Log.d(TAG, "最近会话列表" + timConversation.getType());
            if (timConversation.getType() == TIMConversationType.Group) {
                String id = timConversation.getPeer();
                Log.d(TAG, "打印群id   " + id);
//                    if (!TextUtils.isEmpty(id) && id.contains("pat")) {
//                        doctorIdList.add(id);
//                    }
                doctorIdList.add(id);
            }
        }
        if (doctorIdList != null && doctorIdList.size() > 0) {
            Log.d(TAG, "最近会话列表2----" + doctorIdList.size() + "   " + doctorIdList.toString());
            mConversationListData.clear();
            if (doctorIdList.size() >= 50) {
                getGropupList(doctorIdList.subList(0, 50));
                // getGropupList(doctorIdList.subList(50, doctorIdList.size()));

            } else {
                getGropupList(doctorIdList);

            }


        } else {
            if (easylayout == null) {
                return;
            }
            if (easylayout.isRefreshing()) {
                easylayout.refreshComplete();
            }
            isRefresh = false;
            mAdapter.setNewData(mConversationListData);
            mAdapter.notifyDataSetChanged();
        }

    }

    private void getGropupList(List<String> doctorIdList) {

        TIMGroupManager.getInstance().getGroupDetailInfo(doctorIdList, new TIMValueCallBack<List<TIMGroupDetailInfo>>() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "获取群消息失败----");
                if (easylayout == null) {
                    return;
                }
                if (easylayout.isRefreshing()) {
                    easylayout.refreshComplete();
                }
                isRefresh = false;
            }

            @Override
            public void onSuccess(List<TIMGroupDetailInfo> timGroupDetailInfos) {

                if (easylayout == null) {
                    return;
                }
                if (easylayout.isRefreshing()) {
                    easylayout.refreshComplete();
                }
                Log.d(TAG, "最近会话列表3----" + timGroupDetailInfos.size());
                for (int i = 0; i < timGroupDetailInfos.size(); i++) {
                    TIMGroupDetailInfo tIMUserProfile = timGroupDetailInfos.get(i);
                    ConversationEntity conversationEntity = new ConversationEntity();
                    //获取会话扩展实例
                    TIMConversation con = TIMManager.getInstance().getConversation(TIMConversationType.Group, tIMUserProfile.getGroupId());
                    long unreadMessageNum = con.getUnreadMessageNum();
                    conversationEntity.setUnReadNum(unreadMessageNum);
                    conversationEntity.setGroupID(tIMUserProfile.getGroupId());
                    List<TIMMessage> lastMsgs = con.getLastMsgs(1);
                    if (lastMsgs.size() > 0) {
                        TIMMessage msg = lastMsgs.get(0);
                        conversationEntity.setTimMessage(lastMsgs.get(0));
                        TIMElem element = msg.getElement(0);
                        if (element.getType() == TIMElemType.Text) {
                            TIMTextElem textElem = (TIMTextElem) element;
                            conversationEntity.setLastMsg(textElem.getText());
                        } else if (element.getType() == TIMElemType.GroupTips) {
                            TIMGroupTipsElem tipsElem = (TIMGroupTipsElem) element;
                            String opname = tipsElem.getOpUserInfo().getNickName();
                            String grouptitle = "";
                            if (tipsElem.getTipsType() == TIMGroupTipsType.ModifyGroupInfo) {
                                //群组事件通知
                                grouptitle = opname + "修改群名为" + tipsElem.getGroupName() + "";
                                conversationEntity.setLastMsg(grouptitle);
                            } else if (tipsElem.getTipsType() == TIMGroupTipsType.Join) {
                                Collection<TIMUserProfile> values = tipsElem.getChangedUserInfo().values();
                                Iterator<TIMUserProfile> iterator2 = values.iterator();
                                while (iterator2.hasNext()) {
                                    grouptitle = grouptitle + (iterator2.next().getNickName() + ", ");
                                }
                                grouptitle = grouptitle + "加入群聊";
                                conversationEntity.setLastMsg(grouptitle);
                            } else if (tipsElem.getTipsType() == TIMGroupTipsType.Quit) {
                                Collection<TIMUserProfile> values = tipsElem.getChangedUserInfo().values();
                                Iterator<TIMUserProfile> iterator2 = values.iterator();
                                while (iterator2.hasNext()) {
                                    grouptitle = grouptitle + (iterator2.next().getNickName() + ", ");
                                }
                                grouptitle = grouptitle + "离开群聊";
                                conversationEntity.setLastMsg(grouptitle);
                            } else if (tipsElem.getTipsType() == TIMGroupTipsType.SetAdmin) {
                                if (tipsElem.getChangedUserInfo() != null) {
                                    if (tipsElem.getChangedUserInfo().size() > 0) {
                                        if (tipsElem.getChangedUserInfo().get(0) != null) {
                                            opname = tipsElem.getChangedUserInfo().get(0).getNickName();
                                            grouptitle = opname + "成为新群主";
                                        }

                                    }

                                }

                                conversationEntity.setLastMsg(grouptitle);
                            } else if (tipsElem.getTipsType() == TIMGroupTipsType.ModifyMemberInfo) {
                                grouptitle = opname + "修改群成员信息";
                                conversationEntity.setLastMsg(grouptitle);
                            }

                        } else if (element.getType() == TIMElemType.Sound) {
                            conversationEntity.setLastMsg("[语音消息]");
                        } else if (element.getType() == TIMElemType.Image) {
                            conversationEntity.setLastMsg("[图片消息]");
                        } else if (element.getType() == TIMElemType.Custom) {
                            TIMCustomElem customElem = (TIMCustomElem) msg.getElement(0);
                            String s = new String(customElem.getData());
                            CustomMessageEntity customMessageEntity = mGson.fromJson(s, CustomMessageEntity.class);
                            Log.d(TAG, "自定义消息----" + s);
                            if (customMessageEntity != null) {
                                Log.d(TAG, "自定义消息pushdesc" + customMessageEntity.getPushDesc());
                                if (!TextUtils.isEmpty(customMessageEntity.getPushDesc())) {
                                    conversationEntity.setLastMsg(customMessageEntity.getPushDesc());
                                } else if (!TextUtils.isEmpty(customMessageEntity.getTitle())) {
                                    conversationEntity.setLastMsg(customMessageEntity.getTitle());
                                } else {
                                    conversationEntity.setLastMsg("[系统消息]");
                                }
                            }
                        }

                        long timestamp = msg.timestamp();
                        long unReadNum = conversationEntity.getUnReadNum();
                        String nickName = tIMUserProfile.getGroupName();
                        String id = tIMUserProfile.getGroupId();
                        String faceUrl = tIMUserProfile.getFaceUrl();
                        String lastMsg = conversationEntity.getLastMsg();

                        Log.d(TAG, "获取头像地址----" + faceUrl);

                        ImDataEntity dataBean = new ImDataEntity(null, lastMsg, timestamp, id, faceUrl, nickName, unReadNum);
                        mConversationListData.add(dataBean);
                    }
                }
                Collections.sort(mConversationListData, new SortByTime());
                mAdapter.setNewData(mConversationListData);
                mAdapter.notifyDataSetChanged();
                isRefresh = false;
            }
        });

    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:


                break;
        }
    }




    class SortByTime implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            ImDataEntity s1 = (ImDataEntity) o1;
            ImDataEntity s2 = (ImDataEntity) o2;
            if (s1.getLastTime() < s2.getLastTime()) {
                return 1;
            }

            return -1;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }


    private void loadGroupMembersDetail(ArrayList<String> memberIds) {
        TIMFriendshipManager.getInstance().getUsersProfile(memberIds, new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                timUserProfiles.get(0).getNickName();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initunreadNumber();
    }


    //收到消息，更新首页红点显示
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateImMessageEntity userEvent) {
        Log.d(TAG, "收到更新首页红点消息");
        // GetAllRecordForDoctor();
        //  refreshView(userEvent.ImId);
        initunreadNumber();
    }


}

