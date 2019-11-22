package com.newdjk.doctor.ui.entity;

import java.util.List;

public class UpdateConsultUnreadNum {
    public List<UnreadMessageEntity> UnreadMessageList;
    public int action;

    public UpdateConsultUnreadNum(List<UnreadMessageEntity> unreadMessageList, int action) {
        UnreadMessageList = unreadMessageList;
        this.action = action;
    }
}
