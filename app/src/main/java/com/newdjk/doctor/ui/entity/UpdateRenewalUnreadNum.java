package com.newdjk.doctor.ui.entity;

import java.util.List;

public class UpdateRenewalUnreadNum {
    public List<UnreadMessageEntity> UnreadMessageList;
    public int action;

    public UpdateRenewalUnreadNum(List<UnreadMessageEntity> unreadMessageList, int action) {
        UnreadMessageList = unreadMessageList;
        this.action = action;
    }
}
