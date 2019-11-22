package com.newdjk.doctor.ui.entity;

public class NotifyServiceToSendMessageEntity {
    public int action;
    public String identify;
    public long time;

    public NotifyServiceToSendMessageEntity(int action, String identify, long time) {
        this.action = action;
        this.identify = identify;
        this.time = time;
    }
}
