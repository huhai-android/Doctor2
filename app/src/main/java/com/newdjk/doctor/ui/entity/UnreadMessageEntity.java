package com.newdjk.doctor.ui.entity;

import java.io.Serializable;

public class UnreadMessageEntity implements Serializable {
    private String patientId;
    private long unReadNum;
    private int Status;
    private String timeStamp;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public long getUnReadNum() {
        return unReadNum;
    }

    public void setUnReadNum(long unReadNum) {
        this.unReadNum = unReadNum;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
