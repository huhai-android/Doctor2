package com.newdjk.doctor.ui.activity.min;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.tools.MainConstant;
import com.newdjk.doctor.ui.DiseaseInformationActivity;
import com.newdjk.doctor.ui.activity.PrescriptionActivity;
import com.newdjk.doctor.ui.adapter.GroupMemberAdapter;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.ui.entity.MessageEventRecent;
import com.newdjk.doctor.views.ItemView;
import com.tencent.TIMCallBack;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMGroupManager;
import com.tencent.TIMGroupMemberInfo;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   MDTActivity
 *  @创建者:   huhai
 *  @创建时间:  2019/8/29 10:26
 *  @描述：
 */
public class GroupMemberActivity extends BasicActivity {


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
    @BindView(R.id.tv_groupName)
    ItemView tvGroupName;
    @BindView(R.id.tv_Archives)
    ItemView tvArchives;
    @BindView(R.id.tv_patient_mdt)
    ItemView tvPatientMdt;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.line3)
    View line3;
    private String TAG = "MDTActivity";
    private List<TIMUserProfile> groupMemberList = new ArrayList<>();
    public Gson mGson = new Gson();
    private GroupMemberAdapter mAdapter;
    private String mIdentifier;
    private String mNAME;
    private MDTDetailEntity mMDTDetailEntity;

    @Override
    protected int initViewResId() {
        return R.layout.activity_groupmember;
    }

    @Override
    protected void initView() {
        mIdentifier = getIntent().getStringExtra(Contants.GroupID);
        mNAME = getIntent().getStringExtra(Contants.GroupNAME);
        mMDTDetailEntity = (MDTDetailEntity) getIntent().getSerializableExtra("MDTDetailEntity");

        MyApplication.mActivities.add(this);
        if (!TextUtils.isEmpty(mNAME)) {
            initTitle("群管理").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            initTitle(mNAME).setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        if (mMDTDetailEntity.getSubjectType()==1){
            line3.setVisibility(View.GONE);
            tvPatientMdt.setVisibility(View.GONE);

        }




        tvGroupName.setRightText(mNAME);
        mAdapter = new GroupMemberAdapter(groupMemberList);
        messageRecyclerView.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        messageRecyclerView.setAdapter(mAdapter);
        messageRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        initGroupMember();
    }


    @Override
    protected void initListener() {
        tvGroupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyGroupName();
            }
        });

        tvArchives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mdtIntent = new Intent(GroupMemberActivity.this, PrescriptionActivity.class);
                mdtIntent.putExtra("type", 26);
                mdtIntent.putExtra("patientId", mMDTDetailEntity.getPatientId() + "");

                startActivity(mdtIntent);

            }
        });

        tvPatientMdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupMemberActivity.this, DiseaseInformationActivity.class);
                intent.putExtra(Contants.Type, 3);
                intent.putExtra(Contants.MDTINFO, mMDTDetailEntity);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void initData() {

    }


    @Override
    protected void otherViewClick(View view) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.mActivities.remove(this);
        EventBus.getDefault().unregister(this);

    }

    //初始化群成员
    private void initGroupMember() {

        List<String> var1 = new ArrayList<>();
        var1.add(mIdentifier);
        TIMGroupManager.getInstance().getGroupMembers(mIdentifier, new TIMValueCallBack<List<TIMGroupMemberInfo>>() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "打印群成员" + i+"   "+s);
            }

            @Override
            public void onSuccess(List<TIMGroupMemberInfo> timGroupMemberInfos) {
                ArrayList<String> memberIds = new ArrayList<>();
                if (timGroupMemberInfos.size() == 0) {
                    return;
                }
                for (int i = 0; i < timGroupMemberInfos.size(); i++) {
                    memberIds.add(timGroupMemberInfos.get(i).getUser());
                    Log.d(TAG, "打印群成员" + timGroupMemberInfos.get(i).getUser());
                }
                loadGroupMembersDetail(memberIds);

            }
        });


    }

    private void loadGroupMembersDetail(ArrayList<String> memberIds) {
        TIMFriendshipManager.getInstance().getUsersProfile(memberIds, new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                groupMemberList.clear();
                groupMemberList.addAll(timUserProfiles);
                mAdapter.notifyDataSetChanged();
                for (int i = 0; i <timUserProfiles.size() ; i++) {
                    Log.d(TAG, "打印群成员" + timUserProfiles.get(i).getIdentifier()+" 姓名"+timUserProfiles.get(i).getNickName());
                }
            }
        });
    }

    private void modifyGroupName() {
        final Dialog dialog = new Dialog(this, R.style.ActionSheetDialogStyle);//dialog样式
        View view = View.inflate(this, R.layout.reject_modify_groupname, null);
        dialog.setContentView(view);
        TextView sure = view.findViewById(R.id.sure);
        TextView cancel = view.findViewById(R.id.cancel);
        final EditText rejectReson = view.findViewById(R.id.remark);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rejection = rejectReson.getText().toString();
                if (!rejection.equals("")) {

                    changeGroupName(rejection);
                    dialog.dismiss();
                } else {
                    toast("群名不能为空！");
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void changeGroupName(final String newname) {

        TIMGroupManager.getInstance().modifyGroupName(mIdentifier, newname, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                Log.d(TAG, "modify group info failed, code:" + code + "|desc:" + desc);
                if (code == 10007) {
                    toast("权限不足，无法修改群名！");
                }
            }

            @Override
            public void onSuccess() {
                toast("修改群名成功");
                topTitle.setText(newname);
                MessageEventRecent messageEvent = new MessageEventRecent();
                messageEvent.setContent(newname);
                messageEvent.setmType(MainConstant.ChangeGroupName);
                EventBus.getDefault().post(messageEvent);

            }
        });
    }


}

