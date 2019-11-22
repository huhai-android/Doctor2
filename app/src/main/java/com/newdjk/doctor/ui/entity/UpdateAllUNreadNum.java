package com.newdjk.doctor.ui.entity;

import java.util.List;

public class UpdateAllUNreadNum {
    public List<UnreadMessageEntity> UnreadMessageList;

    public UpdateAllUNreadNum(List<UnreadMessageEntity> unreadMessageList) {
        UnreadMessageList = unreadMessageList;
    }
}
