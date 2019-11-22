package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   CertInfoEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/9/29 17:42
 *  @描述：
 */
public class CertInfoEntity {

    /**
     * certUpdateTipDay : 30
     * deviceFit : true
     * endTime : 2020-04-03 23:59:59
     * existsStamp : true
     * hasStamp : true
     * message : success
     * nowTime : 2019-09-29 17:16:19
     * openId : 89b7c0f4bec537a9q7404wb155y1eacf4d4
     * startTime : 2019-04-03 00:00:00
     * status : 0
     */

    private int certUpdateTipDay;
    private boolean deviceFit;
    private String endTime;
    private boolean existsStamp;
    private boolean hasStamp;
    private String message;
    private String nowTime;
    private String openId;
    private String startTime;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
