package com.newdjk.doctor.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.DoctorHomeCardEntity;
import com.newdjk.doctor.ui.entity.DoctorInfoByIdEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.CircleImageView;
import com.newdjk.doctor.views.LoadDialog;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DoctorHomeCardActivity extends BasicActivity implements IWXAPIEventHandler {
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
    @BindView(R.id.qr_code)
    ImageView qrCode;
    @BindView(R.id.doctor_name)
    TextView doctorName;
    @BindView(R.id.hospital)
    TextView hospital;
    @BindView(R.id.civImg)
    CircleImageView mCircleImageView;

    private String mQrName = "QrImage";

    private String mPath;

    private Dialog mDialog;

    private View mInflate;

    private LinearLayout mFriend, mZoom;

    private String mSdPath;

    private String APP_ID = "wx55eb72942a9dad20";

    private IWXAPI iwxapi;

    private TextView mTvCancel;

    private DoctorInfoByIdEntity mDoctorInfoByIdEntity;



    enum SHARE_TYPE {Type_WXSceneSession, Type_WXSceneTimeline}

    @Override
    protected int initViewResId() {
        return R.layout.doctor_home_card;
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra("title");
        if (title !=null ){
            initBackTitle(title).setRightImage(R.mipmap.ic_wx_share);

        }
        topRight.setVisibility(View.GONE);
        liearTitlebar.getBackground().setAlpha(255);
        doctorName.setText(SpUtils.getString(Contants.Name));
       // hospital.setText(SpUtils.getString(Contants.));
        topTitle.setTextColor(Color.WHITE);
        iwxapi = WXAPIFactory.createWXAPI(this, APP_ID, false);
        iwxapi.handleIntent(getIntent(), this);

        mDoctorInfoByIdEntity = (DoctorInfoByIdEntity)getIntent().getSerializableExtra("doctorInfoByIdEntity");
        hospital.setText(mDoctorInfoByIdEntity.getHospitalName()+"");
        if (mDoctorInfoByIdEntity != null){
          String path = mDoctorInfoByIdEntity.getPicturePath();
          if (path != null) {
              Glide.with(MyApplication.getContext())
                      .load(mDoctorInfoByIdEntity.getPicturePath())
                      .into(mCircleImageView);
          }
        }
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            topRight.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void initListener() {
        topRight.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getDoctorQrCode();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.top_right:
                showBottomDialog(view);
                break;
            case R.id.mWechatFriend:
                share(SHARE_TYPE.Type_WXSceneSession);
                mDialog.dismiss();
                break;
            case R.id.mWechatZone:
                share(SHARE_TYPE.Type_WXSceneTimeline);
                mDialog.dismiss();
                break;
            case R.id.mCancel:
                if (mDialog.isShowing())
                    mDialog.dismiss();
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
    }

    private void getDoctorQrCode() {
       /* Map<String, String> map = new HashMap<>();
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id,0)));*/
        String url = HttpUrl.QueryDoctorQRCodeByDrId + "?DrId=" + SpUtils.getInt(Contants.Id, 0);
        Log.i("bb", "url=" + url);
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization",SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(url).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<List<DoctorHomeCardEntity>>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<List<DoctorHomeCardEntity>> entity) {
                if (entity.getCode() == 0) {
                    LoadDialog.clear();
                    mPath = entity.getData().get(0).getQRCodePath();
                    Glide.with(MyApplication.getContext())
                            .load(mPath)
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(qrCode);
                    // mConsultMessageAdapter.setDatalist( entity.getData());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getImagePath(mPath);
                        }
                    }).start();

                } else {
                    toast(entity.getMessage());
                }

            }


            @Override
            public void onFailures(int statusCode, String errorMsg) {
                LoadDialog.clear();
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }


    public void showBottomDialog(View view) {
        mDialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        mInflate = LayoutInflater.from(this).inflate(R.layout.dialog_share, null);
        //初始化控件
        mFriend = mInflate.findViewById(R.id.mWechatFriend);
        mZoom = mInflate.findViewById(R.id.mWechatZone);
        mTvCancel = mInflate.findViewById(R.id.mCancel);
        mFriend.setOnClickListener(this);
        mZoom.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
        //将布局设置给Dialog
        mDialog.setContentView(mInflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = mDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 10;//设置Dialog距离底部的距离
//       将属性设置给窗体
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
        mDialog.show();//显示对话框
    }


    private void getImagePath(String imgUrl) {
        FutureTarget<File> future = Glide.with(this)
                .load(imgUrl)
                .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        try {
            File cacheFile = future.get();
            mSdPath = cacheFile.getAbsolutePath();
            mHandler.sendEmptyMessage(0);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    private void share(SHARE_TYPE type) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(mSdPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fis == null){
            return;
        }
        Bitmap thumb  = BitmapFactory.decodeStream(fis);
        WXImageObject imageObject = new WXImageObject(thumb);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imageObject;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        switch (type) {
            case Type_WXSceneSession:
                req.scene = SendMessageToWX.Req.WXSceneSession;
                break;
            case Type_WXSceneTimeline:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
        }

        iwxapi.sendReq(req);

    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent,this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void  onResp(BaseResp baseResp) {
        String result;
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "分享成功";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "取消分享";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "分享被拒绝";
                break;
            default:
                result = "发送返回";
                break;
        }
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDialog != null){
            mDialog.cancel();
            mDialog = null;
        }
    }
}
