package com.newdjk.doctor.ui.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.lxq.okhttp.MyOkHttp;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.IM.GroupChatActivity;
import com.newdjk.doctor.ui.entity.ImDataEntity;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.NetworkUtil;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.TimeUtil;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.CircleImageView;
import com.newdjk.doctor.views.LoadDialog;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gabriel on 2017/2/28.
 */

public class GroupMessageAdapter extends BaseQuickAdapter<ImDataEntity, BaseViewHolder> {

    protected MyOkHttp mMyOkhttp;
    private Gson mGson;

    public GroupMessageAdapter(@Nullable List<ImDataEntity> data) {
        super(R.layout.item_recent_conversation, data);
        mGson = new Gson();
        mMyOkhttp = MyApplication.getInstance().getMyOkHttp();
    }


    @Override
    protected void convert(final BaseViewHolder helper, final ImDataEntity item) {
        long unReadNum = item.getUnReadNum();
        String identifier = item.getIdentifier();
        String name = "";
        if (unReadNum > 0) {
            helper.setVisible(R.id.unread_num, true);
        } else {
            helper.setVisible(R.id.unread_num, false);
        }
        name = item.getNickName();
        String path = item.getFaceUrl();
        Log.d(TAG, "图片地址" + path);
        Glide.with(MyApplication.getContext())
                .load(path)
                .dontAnimate()
                .placeholder(R.drawable.patient_default_img)
                .into(((CircleImageView) helper.getView(R.id.avatar)));
        if (!TextUtils.isEmpty(name)) {
            helper.setText(R.id.name, name);
        } else {
            helper.setText(R.id.name, identifier);
        }
        helper.setText(R.id.message_time, TimeUtil.getChatTimeStr(item.getLastTime()));
        helper.setText(R.id.last_message, item.getLastMsg() + "");
        helper.setOnClickListener(R.id.itemview, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  try {
                    if (NetworkUtil.isNetworkAvailable(mContext)) {
                        LoadDialog.show(mContext);
                        final String faceUrl = item.getFaceUrl();
                        TIMConversation conversation = TIMManager.getInstance().getConversation(
                                TIMConversationType.Group,    //会话类型：单聊
                                item.getIdentifier());      //会话对方用户帐号
                        //将此会话的所有消息标记为已读
                        List<TIMMessage> lastMsgs = conversation.getLastMsgs(1);
                        TIMMessage msg = lastMsgs.get(0);
                        conversation.setReadMessage(msg, new TIMCallBack() {
                            @Override
                            public void onError(int code, String desc) {
                                Log.e("setReadMessage", "setReadMessage failed, code: " + code + "|desc: " + desc);
                                ToastUtil.setToast("网络连接异常，请检查网络");
                                LoadDialog.clear();
                            }

                            @Override
                            public void onSuccess() {
                                Log.d("setReadMessage", "setReadMessage succ");
                                helper.setVisible(R.id.unread_num, false);

                            }
                        });

                        getIMRelationRecord(item);
                    } else {
                        ToastUtil.setToast("网络连接异常，请检查网络");

                    }
            }
        });
    }


    private void getIMRelationRecord(final ImDataEntity imDataEntity) {

        Map<String, String> map = new HashMap<>();
        map.put("IMGroupId", imDataEntity.getIdentifier());

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(HttpUrl.QueryMDTBySubjectBuyIMGroupId).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTDetailEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTDetailEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                        Intent intent = new Intent(mContext, GroupChatActivity.class);
                        intent.putExtra("action", "MDT");
                        intent.putExtra("MDTDetailEntity", entity.getData());
                        intent.putExtra(Contants.FRIEND_NAME, imDataEntity.getNickName());
                        intent.putExtra(Contants.FRIEND_IDENTIFIER, imDataEntity.getIdentifier());
                        mContext.startActivity(intent);

                } else {
                    Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
                LoadDialog.clear();
            }
        });
    }

}
