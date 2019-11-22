package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   PrescriptionDataEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/12/5 9:21
 *  @描述：
 */
public class PrescriptionDataEntity {


    /**
     * PrescriptionType : 64
     * AuditorId : 2
     * AuditorName : sample string 3
     * PendingCount : 4
     * PassCount : 5
     * RejectCount : 6
     * Total : 7
     * PassRate : 8.0
     * ResponseRate : 9.0
     */

    private int PrescriptionType;
    private int AuditorId;
    private String AuditorName;
    private int PendingCount;
    private int PassCount;
    private int RejectCount;
    private int Total;
    private double PassRate;
    private double ResponseRate;

    public int getPrescriptionType() {
        return PrescriptionType;
    }

    public void setPrescriptionType(int PrescriptionType) {
        this.PrescriptionType = PrescriptionType;
    }

    public int getAuditorId() {
        return AuditorId;
    }

    public void setAuditorId(int AuditorId) {
        this.AuditorId = AuditorId;
    }

    public String getAuditorName() {
        return AuditorName;
    }

    public void setAuditorName(String AuditorName) {
        this.AuditorName = AuditorName;
    }

    public int getPendingCount() {
        return PendingCount;
    }

    public void setPendingCount(int PendingCount) {
        this.PendingCount = PendingCount;
    }

    public int getPassCount() {
        return PassCount;
    }

    public void setPassCount(int PassCount) {
        this.PassCount = PassCount;
    }

    public int getRejectCount() {
        return RejectCount;
    }

    public void setRejectCount(int RejectCount) {
        this.RejectCount = RejectCount;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public double getPassRate() {
        return PassRate;
    }

    public void setPassRate(double PassRate) {
        this.PassRate = PassRate;
    }

    public double getResponseRate() {
        return ResponseRate;
    }

    public void setResponseRate(double ResponseRate) {
        this.ResponseRate = ResponseRate;
    }
}
