package com.newdjk.doctor.ui.entity;

public class AutheninfoEntity {

    /**
     * DrId : 1
     * DoctorName : sample string 2
     * ProcessStatus : 64
     * OpenId : sample string 4
     * Stamp : sample string 5
     */

    private int DrId;
    private String DoctorName;
    private int ProcessStatus;
    private String OpenId;
    private String Stamp;

    public int getDrId() {
        return DrId;
    }

    public void setDrId(int DrId) {
        this.DrId = DrId;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String DoctorName) {
        this.DoctorName = DoctorName;
    }

    public int getProcessStatus() {
        return ProcessStatus;
    }

    public void setProcessStatus(int ProcessStatus) {
        this.ProcessStatus = ProcessStatus;
    }

    public String getOpenId() {
        return OpenId;
    }

    public void setOpenId(String OpenId) {
        this.OpenId = OpenId;
    }

    public String getStamp() {
        return Stamp;
    }

    public void setStamp(String Stamp) {
        this.Stamp = Stamp;
    }
}
