package com.newdjk.doctor.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.AdBannerInfo;
import com.newdjk.doctor.utils.SpUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;


public class BannerDetailActivity extends BasicActivity implements IWXAPIEventHandler {


    private static final String TAG = "BannerDetailActivity";
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
    @BindView(R.id.mWebView)
    WebView mWebView;
    private String mContent;
    private Dialog mDialog;
    private View mInflate;
    private LinearLayout mFriend, mZoom;
    private TextView mTvCancel;
    private LinearLayout mPatient;
    private AdBannerInfo.DataBean mDataBean;
    private IWXAPI iwxapi;
    private String APP_ID = "wx55eb72942a9dad20";

    @Override
    protected int initViewResId() {
        return R.layout.activity_banner;
    }

    @Override
    protected void initView() {
        initBackTitle("详情").setRightText("分享");
        iwxapi = WXAPIFactory.createWXAPI(BannerDetailActivity.this, APP_ID, false);
        iwxapi.handleIntent(this.getIntent(), this);

        mContent = getIntent().getStringExtra("banner");

        mDataBean = (AdBannerInfo.DataBean) getIntent().getSerializableExtra("bannerInfo");
//        mDataBean.setShareCotent("hahahhahahahahahahhahaha");
//        mDataBean.setShareLink("www.baidu.com");
//        mDataBean.setShareTitle("芸医生");
        if (mDataBean != null) {
            if (mDataBean.getIsShared() == 1) {
                tvRight.setVisibility(View.GONE);
            }
        }


        Log.d(TAG, "获取内容" + mContent);
        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setTextZoom(100);  //消除系统大小的设置对webview字体大小的影响
        mWebView.getSettings().setDomStorageEnabled(true); //解决加载不出webview白屏问题
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDisplayZoomControls(false); //隐藏webview缩放按钮
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebView.setBackgroundColor(0); // 设置背景色
        mWebView.setWebChromeClient(new WebChromeClient());
        if (mDataBean != null) {
            mWebView.loadDataWithBaseURL(null, mDataBean.getLinkContent(), "text/html", "utf-8", null);
        }

    }

    @Override
    protected void initListener() {
        tvRight.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                showBottomDialog();
                break;
            case R.id.btn_share:
                showBottomDialog();

                break;
            case R.id.mPatient:
                Intent intent = new Intent(BannerDetailActivity.this, PatientListActivity.class);
                intent.putExtra("bannerInfo", mDataBean);
                intent.putExtra("type", 1);
                startActivity(intent);
                mDialog.dismiss();
                break;

            case R.id.mWechatFriend:
                share(MyPointsRewardActivity.SHARE_TYPE.Type_WXSceneSession);
                mDialog.dismiss();
                break;
            case R.id.mWechatZone:
                share(MyPointsRewardActivity.SHARE_TYPE.Type_WXSceneTimeline);
                mDialog.dismiss();
                break;
            case R.id.mCancel:
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                break;
        }
    }

    private void showBottomDialog() {

        mDialog = new Dialog(BannerDetailActivity.this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        mInflate = LayoutInflater.from(BannerDetailActivity.this).inflate(R.layout.dialog_ads_share, null);
        //初始化控件
        mFriend = mInflate.findViewById(R.id.mWechatFriend);
        mZoom = mInflate.findViewById(R.id.mWechatZone);
        mPatient = mInflate.findViewById(R.id.mPatient);
        mTvCancel = mInflate.findViewById(R.id.mCancel);
        mFriend.setOnClickListener(this);
        mZoom.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
        mPatient.setOnClickListener(this);
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


    private void share(MyPointsRewardActivity.SHARE_TYPE type) {

        WXWebpageObject webpageObject = new WXWebpageObject();
        //String.valueOf(SpUtils.getInt(Contants.Id, 0)

        webpageObject.webpageUrl = mDataBean.getShareLink().replace("{drid}",String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        //用WXWebpageObject对象初始化一个WXMediaMessage，天下标题，描述
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = mDataBean.getShareTitle();
        msg.description = mDataBean.getShareCotent();
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
       // Toast.makeText(BannerDetailActivity.this, result, Toast.LENGTH_SHORT).show();
    }
}
