package com.newdjk.doctor.ui.entity;

import java.util.List;

public class UpdateVideoUnreadNum {
    public List<UnreadMessageEntity> UnreadMessageList;
    public int action;

    public UpdateVideoUnreadNum(List<UnreadMessageEntity> unreadMessageList, int action) {
        UnreadMessageList = unreadMessageList;
        this.action = action;
    }
}
