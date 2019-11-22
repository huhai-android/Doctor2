package com.newdjk.doctor.ui.camera;


import android.util.Log;

import com.tencent.TIMConversation;
import com.tencent.TIMGroupTipsElem;
import com.tencent.TIMMessage;

import java.util.List;

/**
 * IM事件监听
 */

public abstract class IMEventListener {
    private final static String TAG = IMEventListener.class.getSimpleName();

    /**
     * 被踢下线时回调
     */
    public void onForceOffline() {
        Log.d(TAG, "recv onForceOffline");
    }

    /**
     * 用户票据过期
     */
    public void onUserSigExpired() {
        Log.d(TAG, "recv onUserSigExpired");
    }

    /**
     * 连接建立
     */
    public void onConnected() {
        Log.d(TAG, "recv onConnected");
    }

    /**
     * 连接断开
     *
     * @param code 错误码
     * @param desc 错误描述
     */
    public void onDisconnected(int code, String desc) {
       Log.d(TAG, "recv onDisconnected, code " + code + "|desc " + desc);
    }

    /**
     * WIFI需要验证
     *
     * @param name wifi名称
     */
    public void onWifiNeedAuth(String name) {
       Log.d(TAG, "recv onWifiNeedAuth, wifi name " + name);
    }

    /**
     * 部分会话刷新（包括多终端已读上报同步）
     *
     * @param conversations 需要刷新的会话列表
     */
    public void onRefreshConversation(List<TIMConversation> conversations) {
        Log.d(TAG, "recv onRefreshConversation, size " + (conversations != null ? conversations.size() : 0));
    }

    /**
     * 收到新消息回调
     *
     * @param msgs 收到的新消息
     */
    public void onNewMessages(List<TIMMessage> msgs) {
        Log.d(TAG, "recv onNewMessages, size " + (msgs != null ? msgs.size() : 0));
    }

    /**
     * 群Tips事件通知回调
     *
     * @param elem 群tips消息
     */
    public void onGroupTipsEvent(TIMGroupTipsElem elem) {
        Log.d(TAG, "recv onGroupTipsEvent, groupid: " + elem.getGroupId() + "|type: " + elem.getTipsType());
    }
}
