package com.newdjk.doctor.ui.entity;

public class RemarkAndDrKeyEntity {
    private int PatientId;
    private String DrRemark;
    private int isKey;

    public int getPatientId() {
        return PatientId;
    }

    public void setPatientId(int patientId) {
        PatientId = patientId;
    }

    public String getDrRemark() {
        return DrRemark;
    }

    public void setDrRemark(String drRemark) {
        DrRemark = drRemark;
    }

    public int getIsKey() {
        return isKey;
    }

    public void setIsKey(int isKey) {
        this.isKey = isKey;
    }
}
