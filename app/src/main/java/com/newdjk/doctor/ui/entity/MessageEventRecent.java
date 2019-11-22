package com.newdjk.doctor.ui.entity;

import com.tencent.TIMMessage;

import java.util.List;

/**
 * Created by EDZ on 2018/9/20.
 */

public class MessageEventRecent {

    private int mSericeItemCode;
    private String mType;
    private int payCode;
    private WXUserInfo userInfo;
    private List<TIMMessage> list;
    public int action;
    public String identifity;
    public long time;
    public String content;

    public int getSericeItemCode() {
        return mSericeItemCode;
    }

    public void setSericeItemCode(int sericeItemCode) {
        mSericeItemCode = sericeItemCode;
    }

    public String getIdentifity() {
        return identifity;
    }

    public void setIdentifity(String identifity) {
        this.identifity = identifity;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public List<TIMMessage> getList() {
        return list;
    }

    public void setList(List<TIMMessage> list) {
        this.list = list;
    }

    public int getPayCode() {
        return payCode;
    }

    public void setPayCode(int payCode) {
        this.payCode = payCode;
    }

    public WXUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(WXUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    private int DrType;

    public int getDrType() {
        return DrType;
    }

    public void setDrType(int drType) {
        DrType = drType;
    }

    public MessageEventRecent() {
    }

    public int getmSericeItemCode() {
        return mSericeItemCode;
    }

    public void setmSericeItemCode(int mSericeItemCode) {
        this.mSericeItemCode = mSericeItemCode;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
