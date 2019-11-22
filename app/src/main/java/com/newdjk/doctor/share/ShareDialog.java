package com.newdjk.doctor.share;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.newdjk.doctor.R;
import com.newdjk.doctor.platform.WechatInfo;
import com.newdjk.doctor.platform.WeiXinContext;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 底部弹出对话框
 *
 * @author 李金华
 */
public class ShareDialog extends Dialog {

    public interface OnClickButtons {
        void onClickWechatFriend();
        void onClickWechatZone();
    }
    @BindView(R.id.mWechatFriend)
    LinearLayout mWechatFriend;
    @BindView(R.id.mWechatZone)
    LinearLayout mWechatZone;
    @BindView(R.id.mCancel)
    TextView mCancel;
    private Activity mActivity;
    private String mTitle;
    private String mText;
    private String mImgUrl;
    private String mShareUrl;
    public static final String TYPE_LINK = "link";
    public static final String TYPE_IMG = "img";
    /** 分享方式：link或img.link是分享链接，img是分享图片  */
    private String mType;

    private Bitmap mShareBitmap;

    private OnClickButtons mOnClickButtons;

    public ShareDialog(Activity activity, String title, String text, String imgUrl, String shareUrl, String type,
                       OnClickButtons onClickButtons) {
        super(activity);
        mActivity = activity;
        mOnClickButtons = onClickButtons;

        mTitle = title;
        mText = text;
        mImgUrl = imgUrl;
        mType = type;

//        try {
//            String token = new SharePrefsContext(activity).getString(SharePrefsContext.KEY_ACCESS_TOKEN, "");
//            String encodedToken = java.net.URLEncoder.encode(token, "utf-8");
//            mShareUrl = shareUrl + "?token=" + encodedToken;
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setContentView(R.layout.dialog_share);
        ButterKnife.bind(this);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(layoutParams);

        getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        setCanceledOnTouchOutside(true);
    }

    public void setShareBitmap(Bitmap shareBitmap) {
        mShareBitmap = shareBitmap;
    }

    @OnClick({R.id.mWechatFriend, R.id.mWechatZone, R.id.mCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mWechatFriend:
//                shareToWechatFriend();
                if(null != mOnClickButtons) {
                    mOnClickButtons.onClickWechatFriend();
                }
                break;
            case R.id.mWechatZone:
//                shareToWechatZone();
                if(null != mOnClickButtons) {
                    mOnClickButtons.onClickWechatZone();
                }
                break;
            case R.id.mCancel:
                break;
        }

        dismiss();
    }

//    private void shareToWechatFriend() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if(TYPE_LINK.equals(mType)) {
//                    // 分享链接
//                    Bitmap bitmap;
//                    if (TextUtils.isEmpty(mImgUrl)) {
//                        bitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.mipmap.logo);
//                    } else {
//                        bitmap = ImageLoaderUtil.loadBitmapSync(mActivity, mImgUrl, 100, 100);
//                    }
//                    final Bitmap shareIcon = bitmap;
//                    mActivity.runOnUiThread(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            WeiXinContext weiXinUtil = WeiXinContext.getInstance(WechatInfo.APP_ID);
//                            weiXinUtil.sendMsgToFriend(mTitle, mText, mShareUrl, shareIcon, mActivity);
//                        }
//                    });
//                } else if(TYPE_IMG.equals(mType)){
//                    // 分享图片
//                    Bitmap bitmap = mShareBitmap;
//                    if(null == bitmap) {
//                        if (TextUtils.isEmpty(mImgUrl)) {
//
//                        } else {
//                            bitmap = ImageLoaderUtil.loadBitmapSync(mActivity, mImgUrl);
//                        }
//                    }
//
//                    final Bitmap shareImg = bitmap;
//                    mActivity.runOnUiThread(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            if(null == shareImg) {
//                                Toast.makeText(mActivity, "图片地址为空", Toast.LENGTH_SHORT).show();
//                            } else {
//                                WeiXinContext weiXinUtil = WeiXinContext.getInstance(WechatInfo.APP_ID);
//                                weiXinUtil.sendImageToWechatFriend(shareImg, mActivity);
//                            }
//                        }
//                    });
//                }
//            }
//        }).start();
//    }

//    private void shareToWechatZone() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if(TYPE_LINK.equals(mType)) {
//                    // 分享链接
//                    Bitmap bitmap;
//                    if (TextUtils.isEmpty(mImgUrl)) {
//                        bitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.mipmap.logo);
//                    } else {
//                        bitmap = ImageLoaderUtil.loadBitmapSync(mActivity, mImgUrl, 100, 100);
//                    }
//                    final Bitmap shareIcon = bitmap;
//                    mActivity.runOnUiThread(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            WeiXinContext weiXinUtil = WeiXinContext.getInstance(WechatInfo.APP_ID);
//                            weiXinUtil.sendMsgToWechatZone(mTitle, mText, mShareUrl, shareIcon, mActivity);
//                        }
//                    });
//                } else if(TYPE_IMG.equals(mType)){
//                    // 分享图片
//                    Bitmap bitmap = mShareBitmap;
//                    if(null == bitmap) {
//                        if (TextUtils.isEmpty(mImgUrl)) {
//
//                        } else {
//                            bitmap = ImageLoaderUtil.loadBitmapSync(mActivity, mImgUrl);
//                        }
//                    }
//
//                    final Bitmap shareImg = bitmap;
//                    mActivity.runOnUiThread(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            if(null == shareImg) {
//                                Toast.makeText(mActivity, "图片地址为空", Toast.LENGTH_SHORT).show();
//                            } else {
//                                WeiXinContext weiXinUtil = WeiXinContext.getInstance(WechatInfo.APP_ID);
//                                weiXinUtil.sendImageToWechatZone(shareImg, mActivity);
//                            }
//                        }
//                    });
//                }
//            }
//        }).start();
//    }
}
