package com.newdjk.doctor.utils;

import com.tencent.TIMMessage;

public class MyTIMMessage {
    private String sendTarget;
    private long MsgTimestamp;
    private  TIMMessage timMessage;
    private boolean isLocalMessage = false;
    private boolean isRevoke = false;
    private String faceurl;

    public String getFaceurl() {
        return faceurl;
    }

    public void setFaceurl(String faceurl) {
        this.faceurl = faceurl;
    }

    public String getSendTarget() {
        return sendTarget;
    }

    public void setSendTarget(String sendTarget) {
        this.sendTarget = sendTarget;
    }

    public long getMsgTimestamp() {
        return MsgTimestamp;
    }

    public void setMsgTimestamp(long msgTimestamp) {
        MsgTimestamp = msgTimestamp;
    }

    public TIMMessage getTimMessage() {
        return timMessage;
    }

    public void setTimMessage(TIMMessage timMessage) {
        this.timMessage = timMessage;
    }

    public boolean isLocalMessage() {
        return isLocalMessage;
    }

    public void setLocalMessage(boolean localMessage) {
        isLocalMessage = localMessage;
    }

    public boolean isRevoke() {
        return isRevoke;
    }

    public void setRevoke(boolean revoke) {
        isRevoke = revoke;
    }
}
