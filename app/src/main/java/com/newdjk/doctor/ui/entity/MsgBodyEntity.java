package com.newdjk.doctor.ui.entity;

import java.util.List;

public class MsgBodyEntity {
    private String MsgType;
    private MsgContentEntity MsgContent;


    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public MsgContentEntity getMsgContent() {
        return MsgContent;
    }

    public void setMsgContent(MsgContentEntity msgContent) {
        MsgContent = msgContent;
    }


}
