package com.newdjk.doctor.ui.entity;

public class RequireOrderStatusEntity {

    /**
     * DoctorId : 0
     * DoctorName : null
     * Status : 0
     */

    private int DoctorId;
    private String DoctorName;
    private int Status;

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int DoctorId) {
        this.DoctorId = DoctorId;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String DoctorName) {
        this.DoctorName = DoctorName;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }
}
