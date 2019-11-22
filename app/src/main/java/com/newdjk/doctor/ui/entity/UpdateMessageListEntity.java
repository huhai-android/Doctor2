package com.newdjk.doctor.ui.entity;

import com.tencent.TIMMessage;

import java.util.List;

public class UpdateMessageListEntity {
    public List<TIMMessage> list;

    public UpdateMessageListEntity(List<TIMMessage> list) {
        this.list = list;
    }
}
