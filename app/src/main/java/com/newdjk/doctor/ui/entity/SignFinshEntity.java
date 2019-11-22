package com.newdjk.doctor.ui.entity;

public class SignFinshEntity {
    private String message;
    private String stamp;
    private String stampPic;
    private String[] uniqueIds;
    private String clientId;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getStampPic() {
        return stampPic;
    }

    public void setStampPic(String stampPic) {
        this.stampPic = stampPic;
    }

    public String[] getUniqueIds() {
        return uniqueIds;
    }

    public void setUniqueIds(String[] uniqueIds) {
        this.uniqueIds = uniqueIds;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
