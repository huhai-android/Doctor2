package com.newdjk.doctor.ui.entity;

public class ImRelationRecode {


    /**
     * PatientIMId : pat_172
     * PatientName : 婶婶
     * DoctorIMId : doc_306
     * DoctorId : 306
     * DoctorName : 艾美
     * ServiceType : 1
     * RecordId : 1883
     */

    private String PatientIMId;
    private String PatientName;
    private String DoctorIMId;
    private String ApplicantHeadImgUrl;
    private String DoctorHeadImgUrl;
    private int DoctorId;
    private String DoctorName;

    public String getApplicantHeadImgUrl() {
        return ApplicantHeadImgUrl;
    }

    public void setApplicantHeadImgUrl(String applicantHeadImgUrl) {
        ApplicantHeadImgUrl = applicantHeadImgUrl;
    }

    public String getDoctorHeadImgUrl() {
        return DoctorHeadImgUrl;
    }

    public void setDoctorHeadImgUrl(String doctorHeadImgUrl) {
        DoctorHeadImgUrl = doctorHeadImgUrl;
    }

    private int ServiceType;
    private int RecordId;
    private int AccountId;
    private int PatientId;

    public int getPatientId() {
        return PatientId;
    }

    public void setPatientId(int patientId) {
        PatientId = patientId;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }

    public String getPatientIMId() {
        return PatientIMId;
    }

    public void setPatientIMId(String PatientIMId) {
        this.PatientIMId = PatientIMId;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String PatientName) {
        this.PatientName = PatientName;
    }

    public String getDoctorIMId() {
        return DoctorIMId;
    }

    public void setDoctorIMId(String DoctorIMId) {
        this.DoctorIMId = DoctorIMId;
    }

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

    public int getServiceType() {
        return ServiceType;
    }

    public void setServiceType(int ServiceType) {
        this.ServiceType = ServiceType;
    }

    public int getRecordId() {
        return RecordId;
    }

    public void setRecordId(int RecordId) {
        this.RecordId = RecordId;
    }
}
