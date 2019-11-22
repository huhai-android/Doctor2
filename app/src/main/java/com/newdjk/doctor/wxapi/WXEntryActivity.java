package com.newdjk.doctor.wxapi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.ShareSuccessEntity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;

import static com.newdjk.doctor.wxapi.WXEntryActivity.SHARE_TYPE.Type_WXSceneSession;
import static com.newdjk.doctor.wxapi.WXEntryActivity.SHARE_TYPE.Type_WXSceneTimeline;

/**
 * Created by Struggle on 2018/10/12.
 */

public class WXEntryActivity extends FragmentActivity implements IWXAPIEventHandler {
    private String APP_ID = "wx55eb72942a9dad20";

    private IWXAPI iwxapi;


    enum SHARE_TYPE {Type_WXSceneSession, Type_WXSceneTimeline}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        iwxapi = WXAPIFactory.createWXAPI(this, APP_ID, false);
        iwxapi.handleIntent(getIntent(), this);
        iwxapi.registerApp(APP_ID);

    }

    public void shareWXSceneSession(View view) {
        share(Type_WXSceneSession);
    }

    public void shareWXSceneTimeline(View view) {
        share(Type_WXSceneTimeline);
    }


    private void share(SHARE_TYPE type) {
//        WXWebpageObject webpageObject = new WXWebpageObject();
//        webpageObject.webpageUrl = "http://www.initobject.com/";
//        WXMediaMessage msg = new WXMediaMessage(webpageObject);
//        msg.title = "Hi,Tips";
//        msg.description = "测试分享";
//        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_error);
//        msg.thumbData = bmpToByteArray(thumb, true);
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("Req");
//        req.message = msg;
//        switch (type) {
//            case Type_WXSceneSession:
//                req.scene = WXSceneSession;
//                break;
//            case Type_WXSceneTimeline:
//                req.scene = WXSceneTimeline;
//                break;
//        }
//        iwxapi.sendReq(req);
//       // finish();

        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_error);
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
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
                EventBus.getDefault().post(new ShareSuccessEntity(true));
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


      //  Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        finish();

    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
