package com.newdjk.doctor.ui.entity;

public class DoctorSendReportResponseEntity {

    /**
     * UniqueId : sample string 1
     * MedicalReportPath : sample string 2
     */

    private String UniqueId;
    private String MedicalReportPath;

    public String getUniqueId() {
        return UniqueId;
    }

    public void setUniqueId(String UniqueId) {
        this.UniqueId = UniqueId;
    }

    public String getMedicalReportPath() {
        return MedicalReportPath;
    }

    public void setMedicalReportPath(String MedicalReportPath) {
        this.MedicalReportPath = MedicalReportPath;
    }
}
