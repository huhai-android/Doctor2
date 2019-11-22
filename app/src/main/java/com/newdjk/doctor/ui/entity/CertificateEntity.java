package com.newdjk.doctor.ui.entity;

public class CertificateEntity {

    /**
     * certUpdateTipDay : 30
     * deviceFit : false
     * existsStamp : false
     * hasStamp : false
     * message : 用户证书不存在，请下载证书
     * status : 004x007
     */

    private int certUpdateTipDay;
    private boolean deviceFit;
    private boolean existsStamp;
    private boolean hasStamp;
    private String message;
    private String status;

    public int getCertUpdateTipDay() {
        return certUpdateTipDay;
    }

    public void setCertUpdateTipDay(int certUpdateTipDay) {
        this.certUpdateTipDay = certUpdateTipDay;
    }

    public boolean isDeviceFit() {
        return deviceFit;
    }

    public void setDeviceFit(boolean deviceFit) {
        this.deviceFit = deviceFit;
    }

    public boolean isExistsStamp() {
        return existsStamp;
    }

    public void setExistsStamp(boolean existsStamp) {
        this.existsStamp = existsStamp;
    }

    public boolean isHasStamp() {
        return hasStamp;
    }

    public void setHasStamp(boolean hasStamp) {
        this.hasStamp = hasStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
