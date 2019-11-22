package com.newdjk.doctor.ui.entity;

public class PatientStatusEntity {

    /**
     * ApplicantId : 45
     * ApplicantIMId : pat_45
     * DoctorId : 18
     * DoctorIMId : doc_18
     * ServiceType : 1
     * ServiceItemCode : 1101
     * RecordId : 505
     * Status : 5
     */

    private int ApplicantId;
    private String ApplicantIMId;
    private int DoctorId;
    private String DoctorIMId;
    private int ServiceType;
    private String ServiceItemCode;
    private int RecordId;
    private int Status;
    private String ApplicantName;
    private String ApplicantHeadImgUrl;
    private PatientInfoEntity PatientInfo;
    private DoctorPatientRelationEntity DoctorPatientRelation;
    public String getApplicantHeadImgUrl() {
        return ApplicantHeadImgUrl;
    }

    public void setApplicantHeadImgUrl(String applicantHeadImgUrl) {
        ApplicantHeadImgUrl = applicantHeadImgUrl;
    }

    public int getApplicantId() {
        return ApplicantId;
    }

    public void setApplicantId(int ApplicantId) {
        this.ApplicantId = ApplicantId;
    }

    public String getApplicantIMId() {
        return ApplicantIMId;
    }

    public void setApplicantIMId(String ApplicantIMId) {
        this.ApplicantIMId = ApplicantIMId;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int DoctorId) {
        this.DoctorId = DoctorId;
    }

    public String getDoctorIMId() {
        return DoctorIMId;
    }

    public void setDoctorIMId(String DoctorIMId) {
        this.DoctorIMId = DoctorIMId;
    }

    public int getServiceType() {
        return ServiceType;
    }

    public void setServiceType(int ServiceType) {
        this.ServiceType = ServiceType;
    }

    public String getServiceItemCode() {
        return ServiceItemCode;
    }

    public void setServiceItemCode(String ServiceItemCode) {
        this.ServiceItemCode = ServiceItemCode;
    }

    public int getRecordId() {
        return RecordId;
    }

    public void setRecordId(int RecordId) {
        this.RecordId = RecordId;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getApplicantName() {
        return ApplicantName;
    }

    public void setApplicantName(String applicantName) {
        ApplicantName = applicantName;
    }

    public PatientInfoEntity getPatientInfo() {
        return PatientInfo;
    }

    public void setPatientInfo(PatientInfoEntity patientInfo) {
        PatientInfo = patientInfo;
    }

    public DoctorPatientRelationEntity getDoctorPatientRelation() {
        return DoctorPatientRelation;
    }

    public void setDoctorPatientRelation(DoctorPatientRelationEntity doctorPatientRelation) {
        DoctorPatientRelation = doctorPatientRelation;
    }
}
