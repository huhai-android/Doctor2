package com.newdjk.doctor.platform;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.widget.Toast;

import com.newdjk.doctor.R;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

/**
 * 第三方平台工具类，上下文-微信， 包括微信账号登陆，微信好友分享，微信朋友圈分享
 * 
 * @author 李金华
 * 
 */
public class WeiXinContext {
	
	/**
	 * 权限范围
	 */
	public static final String WEIXIN_SCOPE = "snsapi_userinfo";
	
	public static final String WEIXIN_LOGIN_STATE = "login";
	
	/**
	 * 微信登陆凭证
	 */
	public static class WechatLoginToken {
		/** 接口调用凭证  */
		private String access_token;
		/** access_token接口调用凭证超时时间，单位（秒）  */
		private int expires_in;	
		/** 用户刷新access_token  */
		private String refresh_token;
		/** 授权用户唯一标识  */
		private String openid;
		/** 用户授权的作用域，使用逗号（,）分隔  */
		private String scope;

		public WechatLoginToken(String json) {
			try {
				JSONObject jsonObject = new JSONObject(json);
				access_token = jsonObject.getString("access_token");
				expires_in = jsonObject.getInt("expires_in");
				refresh_token = jsonObject.getString("refresh_token");
				openid = jsonObject.getString("openid");
				scope = jsonObject.getString("scope");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		public String getAccess_token() {
			return access_token;
		}
		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}
		public int getExpires_in() {
			return expires_in;
		}
		public void setExpires_in(int expires_in) {
			this.expires_in = expires_in;
		}
		public String getRefresh_token() {
			return refresh_token;
		}
		public void setRefresh_token(String refresh_token) {
			this.refresh_token = refresh_token;
		}
		public String getOpenid() {
			return openid;
		}
		public void setOpenid(String openid) {
			this.openid = openid;
		}
		public String getScope() {
			return scope;
		}
		public void setScope(String scope) {
			this.scope = scope;
		}	
	}
	
	/**
	 * 单个实例
	 */
	private static WeiXinContext instance;

	// IWXAPI 是第三方app和微信通信的openapi接口
	// private IWXAPI api;
	 
	/** 微信登录凭证 */
	 private WechatLoginToken wechatLoginToken;
	 
	 public final String APP_ID;

	public static WeiXinContext getInstance(String appID) {
		if (null == instance) {
			synchronized (WeiXinContext.class) {
				if (null == instance) {
					instance = new WeiXinContext(appID);
				}
			}
		}
		return instance;
	}

	private WeiXinContext(String appID) {
		this.APP_ID = appID;
	}
	
//	/**
//	 * 将app注册到微信
//	 */
//	public void registerAppToWX(Context context) {
//		if(null == api) {
//			api = WXAPIFactory.createWXAPI(context, cn.rainbow.westore.common.Constants.ThirdPartyID.WEIXIN_APP_ID, true);
//		}
//		// 将该app注册到微信
//	    api.registerApp(cn.rainbow.westore.common.Constants.ThirdPartyID.WEIXIN_APP_ID); 
//	}

	/**
	 * 微信登陆
	 */
	public void weChatLogin(Activity activity) {
		IWXAPI api = WXAPIFactory.createWXAPI(activity, APP_ID, true);
		
		if (!api.isWXAppInstalled()) {
			// 提醒用户没有安装微信
			Toast.makeText(activity, R.string.not_installed_weixin, Toast.LENGTH_SHORT).show();
			return;
		}
		SendAuth.Req req = new SendAuth.Req();
		req.scope = WEIXIN_SCOPE;
		req.state = WEIXIN_LOGIN_STATE;
		api.sendReq(req);
	}

	/**
	 * 分享信息给微信好友
	 * 
	 * @param activity
	 */
	public void sendMsgToFriend(String title, String desc, String url, Bitmap goods_photo, Activity activity) {
		IWXAPI api = WXAPIFactory.createWXAPI(activity, APP_ID, true);
		
		if (!api.isWXAppInstalled()) {
			// 提醒用户没有安装微信
			Toast.makeText(activity, R.string.not_installed_weixin, Toast.LENGTH_SHORT).show();
			return;
		}
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = url;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = title;
		msg.description = desc;
		
//		if(null != goods_photo) {
//			WXImageObject imgObj = new WXImageObject();
//			imgObj.imageData = bmpToByteArray(goods_photo, false);
//			msg.mediaObject = imgObj;
//		}
		if(null != goods_photo) {
		//msg.thumbData = bmpToByteArray(goods_photo, false);
			msg.thumbData = getBitmapBytes(goods_photo, false);
	}
		
//		Bitmap thumb = goods_photo;
//		if(null == thumb) {
//			BitmapFactory.decodeResource(activity.getResources(), R.drawable.icon_launcher);
//		}
//		if(null != thumb) {
//			msg.thumbData = bmpToByteArray(thumb, true);
//		}
		
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction(url);
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;

		api.sendReq(req);
	}

	/**
	 * 分享信息到微信朋友圈
	 * 
	 * @param activity
	 */
	public void sendMsgToWechatZone(String title, String desc, String url, Bitmap goods_photo, Activity activity) {
		
		IWXAPI api = WXAPIFactory.createWXAPI(activity, APP_ID, true);
		
		if (!api.isWXAppInstalled()) {
			// 提醒用户没有安装微信
			Toast.makeText(activity, R.string.not_installed_weixin, Toast.LENGTH_SHORT).show();
			return;
		}
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = url;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = title;
		msg.description = desc;
		
//		if(null != goods_photo) {
//			WXImageObject imgObj = new WXImageObject();
//			imgObj.imageData = bmpToByteArray(goods_photo, false);
//			msg.mediaObject = imgObj;
//		}
		
		if(null != goods_photo) {
			//msg.thumbData = bmpToByteArray(goods_photo, false);
			msg.thumbData = getBitmapBytes(goods_photo, false);
		}
		
//		Bitmap thumb = goods_photo;
//		if(null == thumb) {
//			BitmapFactory.decodeResource(activity.getResources(), R.drawable.icon_launcher);
//		}
//		if(null != thumb) {
//			msg.thumbData = bmpToByteArray(thumb, true);
//		}
		
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction(url);
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;
		
		api.sendReq(req);
	}

	/**
	 * 分享图片给微信好友
	 *
	 * @param activity
	 */
	public void sendImageToWechatFriend(Bitmap bigBmp, Activity activity) {
		IWXAPI api = WXAPIFactory.createWXAPI(activity, APP_ID, true);

		if (!api.isWXAppInstalled()) {
			// 提醒用户没有安装微信
			Toast.makeText(activity, R.string.not_installed_weixin, Toast.LENGTH_SHORT).show();
			return;
		}

		WXImageObject imgObj = new WXImageObject(bigBmp);
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;

		msg.thumbData = getBitmapBytes(bigBmp, false);


		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		api.sendReq(req);
	}

	/**
	 * 分享图片到微信朋友圈
	 *
	 * @param activity
	 */
	public void sendImageToWechatZone(Bitmap bigBmp, Activity activity) {

		IWXAPI api = WXAPIFactory.createWXAPI(activity, APP_ID, true);

		if (!api.isWXAppInstalled()) {
			// 提醒用户没有安装微信
			Toast.makeText(activity, R.string.not_installed_weixin, Toast.LENGTH_SHORT).show();
			return;
		}

		WXImageObject imgObj = new WXImageObject(bigBmp);
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;

		msg.thumbData = getBitmapBytes(bigBmp, false);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;
		api.sendReq(req);
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}
	
//	private byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
//		ByteArrayOutputStream output = new ByteArrayOutputStream();
//		bmp.compress(CompressFormat.PNG, 100, output);
//		if (needRecycle) {
//			bmp.recycle();
//		}
//		
//		byte[] result = output.toByteArray();
//		try {
//			output.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return result;
//	}
	
	 //需要对图片进行处理，否则微信会在log中输出thumbData检查错误
    private byte[] getBitmapBytes(Bitmap bitmap, boolean paramBoolean) {
        Bitmap localBitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);
        int i;
        int j;
        if (bitmap.getHeight() > bitmap.getWidth()) {
            i = bitmap.getWidth();
            j = bitmap.getWidth();
        } else {
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
        while (true) {
            localCanvas.drawBitmap(bitmap, new Rect(0, 0, i, j), new Rect(0, 0, 80, 80), null);
            if (paramBoolean)
                bitmap.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
            	e.printStackTrace();
            }
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
    }
    
	
	public WechatLoginToken getWechatLoginToken() {
		return wechatLoginToken;
	}
	
	public void setWechatLoginToken(WechatLoginToken wechatLoginToken) {
		this.wechatLoginToken = wechatLoginToken;
	}
}
