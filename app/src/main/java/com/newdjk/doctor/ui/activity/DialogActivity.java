package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.ui.activity.IM.RoomActivity;
import com.newdjk.doctor.ui.entity.NotifyServiceToSendMessageEntity;
import com.newdjk.doctor.ui.entity.RejectCallTip;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogActivity extends BasicActivity {
    @BindView(R.id.tv_title_custom_dialog)
    TextView tvTitleCustomDialog;
    @BindView(R.id.tv_message_custom_dialog)
    TextView tvMessageCustomDialog;
    @BindView(R.id.btn_negative_custom_dialog)
    Button btnNegativeCustomDialog;
    @BindView(R.id.btn_positive_custom_dialog)
    Button btnPositiveCustomDialog;
    private String mSender;
    private int mCallId = 0;
    private String userSig = "";
    private String mIdentifier;
    @Override
    protected int initViewResId() {
        return R.layout.video_call_dialog;
    }

    @Override
    protected void initView() {
        mSender = getIntent().getStringExtra("sender");
        mCallId = getIntent().getIntExtra("callId", 0);
        userSig =  getIntent().getStringExtra("userSig");
        mIdentifier = getIntent().getStringExtra("identifier");
        tvTitleCustomDialog.setText("视频邀请");
        tvMessageCustomDialog.setText("来自"+mSender+"的视频邀请");
        btnNegativeCustomDialog.setText("拒绝");
        btnPositiveCustomDialog.setText("接受");
    }

    @Override
    protected void initListener() {
        btnNegativeCustomDialog.setOnClickListener(this);
        btnPositiveCustomDialog.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {
    switch (view.getId()) {
        case R.id.btn_negative_custom_dialog :
            EventBus.getDefault().post(new NotifyServiceToSendMessageEntity(133,mIdentifier,-1));
            finish();
            break;
        case R.id.btn_positive_custom_dialog:
            //acceptCall(callId, notification.getSponsorId(), callType);
            EventBus.getDefault().post(new NotifyServiceToSendMessageEntity(130,mIdentifier,-1));
            Intent roomIntent = new Intent(DialogActivity.this, RoomActivity.class);
            roomIntent.putExtra("callId", mCallId);
            roomIntent.putExtra("target", "other");
            roomIntent.putExtra("userSig", userSig);
            roomIntent.putExtra("identifier",mIdentifier);
            roomIntent.putExtra("action", "dialogActivity");
            startActivity(roomIntent);
            finish();
            break;
    }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(RejectCallTip rejectCallTip) {
      finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
