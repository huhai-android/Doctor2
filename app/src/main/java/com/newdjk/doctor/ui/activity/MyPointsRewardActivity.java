package com.newdjk.doctor.ui.activity;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.DoctorRecommendAdapter;
import com.newdjk.doctor.ui.entity.AdviceDoctorEntity;
import com.newdjk.doctor.ui.entity.DoctorRecommend;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.SignRuleEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.ButtonView;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   MyPointsActivity
 *  @创建者:   huhai
 *  @创建时间:  2019/1/21 9:45
 *  @描述：
 */
public class MyPointsRewardActivity extends BasicActivity implements IWXAPIEventHandler {
    private static final String TAG = "MyPointsRewardActivity";
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
    @BindView(R.id.tv_url)
    TextView tvUrl;
    @BindView(R.id.tv_copy)
    ButtonView tvCopy;
    @BindView(R.id.btn_share)
    ButtonView btnShare;
    @BindView(R.id.recyleview)
    RecyclerView recyleview;
    @BindView(R.id.recommend_doctor_number)
    TextView recommendDoctorNumber;
    private DoctorRecommendAdapter mDoctorRecommendAdapter;
    private List<DoctorRecommend.ReturnDataBean> mList = new ArrayList<>();
    private IWXAPI iwxapi;
    private final String YUNAPPLOAD = "https://doctor.newstartcare.com/yunAppLoad.html";
    private String APP_ID = "wx55eb72942a9dad20";


    enum SHARE_TYPE {Type_WXSceneSession, Type_WXSceneTimeline}

    private List<SignRuleEntity.DataBean> list = new ArrayList<>();
    private Dialog mDialog;
    private View mInflate;
    private LinearLayout mFriend, mZoom;
    private TextView mTvCancel;

    @Override
    protected int initViewResId() {
        return R.layout.activity_mypoint_reward;
    }

    @Override
    protected void initView() {
        initBackTitle("邀请医护");
        iwxapi = WXAPIFactory.createWXAPI(MyPointsRewardActivity.this, APP_ID, false);
        iwxapi.handleIntent(this.getIntent(), this);
        mDoctorRecommendAdapter = new DoctorRecommendAdapter(mList);

        GridLayoutManager mManagerLayout = new GridLayoutManager(mContext, 4);
        recyleview.setLayoutManager(mManagerLayout);
        recyleview.setAdapter(mDoctorRecommendAdapter);
    }

    @Override
    protected void initListener() {
        tvCopy.setOnClickListener(this);
        btnShare.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getDoctorRecommender();
        getQRcode();
    }

    private void getQRcode() {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        mMyOkhttp.get().url(HttpUrl.getDoctorQrcode).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<AdviceDoctorEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<AdviceDoctorEntity> response) {
                Log.d(TAG, "VVVVVV" + response.toString());
                if (response.getCode() == 0) {
                    //Bitmap mBitmap = CodeUtils.createImage(response.getData().getCodeAddress(), 400, 400, null);
                    Glide.with(MyApplication.getContext())
                            .load(response.getData().getCodeAddress()).
                            crossFade().centerCrop().
                            into(qrCode);
                    tvUrl.setText(response.getData().getLinkAddress());
                    //  qrCode.setImageBitmap(mBitmap);
                    Log.d(TAG, "XXXXXX");
                } else {
                    Log.d(TAG, "AAAAA");
                    toast(response.getMessage());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.d(TAG, "TTTT");
                toast(errorMsg);
            }
        });
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_copy:
                ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(tvUrl.getText() + "");
                toast("已成功复制到粘贴板");
                break;
            case R.id.btn_share:
                showBottomDialog();

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


    public void showBottomDialog() {
        mDialog = new Dialog(MyPointsRewardActivity.this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        mInflate = LayoutInflater.from(MyPointsRewardActivity.this).inflate(R.layout.dialog_share, null);
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


    private void share(SHARE_TYPE type) {
      /*  FileInputStream fis = null;
        try {
            fis = new FileInputStream(mSdPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fis == null){
            return;
        }
        Bitmap thumb  = BitmapFactory.decodeStream(fis);*/

        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = tvUrl.getText() + "";
        //用WXWebpageObject对象初始化一个WXMediaMessage，天下标题，描述
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = "芸医生医护版邀请函";
        msg.description = "芸医生为用户提供咨询、续方、复诊、康复护理、诊后随访等医疗健康服务";
        //这块需要注意，图片的像素千万不要太大，不然的话会调不起来微信分享，
        //我在做的时候和我们这的UIMM说随便给我一张图，她给了我一张1024*1024的图片
        //当时也不知道什么原因，后来在我的机智之下换了一张像素小一点的图片好了！
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.icon);
        msg.setThumbImage(thumb);

         /* WXImageObject imageObject = new WXImageObject(bitmap);
          WXMediaMessage msg = new WXMediaMessage();
          msg.mediaObject = imageObject;*/
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
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

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
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
        Toast.makeText(MyPointsRewardActivity.this, result, Toast.LENGTH_SHORT).show();
    }

    private void getDoctorRecommender() {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        params.put("PageIndex", "1");
        params.put("PageSize", "10000");
        mMyOkhttp.post().url(HttpUrl.DoctorRecommends).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<DoctorRecommend>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<DoctorRecommend> response) {
                Log.d(TAG, "VVVVVV" + response.toString());
                if (response.getCode() == 0) {
                    DoctorRecommend doctorRecommend = response.getData();
                    if (doctorRecommend != null) {
                        List<DoctorRecommend.ReturnDataBean> list = response.getData().getReturnData();
                        mDoctorRecommendAdapter.setNewData(list);
                        recommendDoctorNumber.setText("我推荐的医生(" + response.getData().getTotal() + "位)");
                    }
                    Log.d(TAG, "VVVVVV" + response.getData().toString());
                } else {
                    Log.d(TAG, "AAAAA");
                    toast(response.getMessage());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.d(TAG, "TTTT");
                toast(errorMsg);
            }
        });
    }
}
