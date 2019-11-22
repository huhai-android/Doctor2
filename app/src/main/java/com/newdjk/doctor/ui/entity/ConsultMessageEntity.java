package com.newdjk.doctor.ui.entity;

import java.io.Serializable;

public class ConsultMessageEntity implements Serializable {
    private DoctorPatientRelationEntity DoctorPatientRelation;
    private String DiseaseName;
    private String Content;
    private String PatientName;
    private String  ApplicantName;
    private String OrgName;
    private String ApplicantHeadImgUrl;
    private String OrgPath;
    private String EndTime;
    private String ConsultPrice;
    private String PayChannel;
    private String PayTime;
    private String PayOrderNo;
    private String EvaluationStatus;
    private String EvaluationTime;
    private String Remark;
    private String UpdateTime;
    private String CreateTime;
    private String DoctorName;
    private int Id;
    private int DiseaseId;
    private int Type;
    private int PatientId;
    private int DoctorId;
    private int ApplicantId;
    private int OrgId;
    private int Status;
    private String RejectReason;
    private String DealWithTime;
    private String StartTime;
    private int EndType;
    private int PayStatus;
    private int CreateId;
    private PatientInfoEntity PatientInfo;
    private String ApplicantIMId;
    private long unReadNum;
    private String DoctorIMId;
    private String  recordId;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getDoctorIMId() {
        return DoctorIMId;
    }

    public void setDoctorIMId(String doctorIMId) {
        DoctorIMId = doctorIMId;
    }

    public String getApplicantHeadImgUrl() {
        return ApplicantHeadImgUrl;
    }

    public void setApplicantHeadImgUrl(String applicantHeadImgUrl) {
        ApplicantHeadImgUrl = applicantHeadImgUrl;
    }
    /*  public String getDoctorPatientRelation() {
        return DoctorPatientRelation;
    }

    public void setDoctorPatientRelation(String doctorPatientRelation) {
        DoctorPatientRelation = doctorPatientRelation;
    }*/

    public DoctorPatientRelationEntity getDoctorPatientRelation() {
        return DoctorPatientRelation;
    }

    public void setDoctorPatientRelation(DoctorPatientRelationEntity doctorPatientRelation) {
        DoctorPatientRelation = doctorPatientRelation;
    }

    public String getDiseaseName() {
        return DiseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        DiseaseName = diseaseName;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getApplicantName() {
        return ApplicantName;
    }

    public void setApplicantName(String applicantName) {
        ApplicantName = applicantName;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int doctorId) {
        DoctorId = doctorId;
    }

    public String getOrgName() {
        return OrgName;
    }

    public void setOrgName(String orgName) {
        OrgName = orgName;
    }

    public String getOrgPath() {
        return OrgPath;
    }

    public void setOrgPath(String orgPath) {
        OrgPath = orgPath;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getConsultPrice() {
        return ConsultPrice;
    }

    public void setConsultPrice(String consultPrice) {
        ConsultPrice = consultPrice;
    }

    public String getPayChannel() {
        return PayChannel;
    }

    public void setPayChannel(String payChannel) {
        PayChannel = payChannel;
    }

    public String getPayTime() {
        return PayTime;
    }

    public void setPayTime(String payTime) {
        PayTime = payTime;
    }

    public String getPayOrderNo() {
        return PayOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        PayOrderNo = payOrderNo;
    }

    public String getEvaluationStatus() {
        return EvaluationStatus;
    }

    public void setEvaluationStatus(String evaluationStatus) {
        EvaluationStatus = evaluationStatus;
    }

    public String getEvaluationTime() {
        return EvaluationTime;
    }

    public void setEvaluationTime(String evaluationTime) {
        EvaluationTime = evaluationTime;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getDiseaseId() {
        return DiseaseId;
    }

    public void setDiseaseId(int diseaseId) {
        DiseaseId = diseaseId;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getPatientId() {
        return PatientId;
    }

    public void setPatientId(int patientId) {
        PatientId = patientId;
    }

    public int getApplicantId() {
        return ApplicantId;
    }

    public void setApplicantId(int applicantId) {
        ApplicantId = applicantId;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public int getOrgId() {
        return OrgId;
    }

    public void setOrgId(int orgId) {
        OrgId = orgId;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getRejectReason() {
        return RejectReason;
    }

    public void setRejectReason(String rejectReason) {
        RejectReason = rejectReason;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public int getEndType() {
        return EndType;
    }

    public void setEndType(int endType) {
        EndType = endType;
    }

    public int getPayStatus() {
        return PayStatus;
    }

    public void setPayStatus(int payStatus) {
        PayStatus = payStatus;
    }

    public int getCreateId() {
        return CreateId;
    }

    public void setCreateId(int createId) {
        CreateId = createId;
    }



    public PatientInfoEntity getPatientInfo() {
        return PatientInfo;
    }

    public void setPatientInfo(PatientInfoEntity patientInfo) {
        PatientInfo = patientInfo;
    }

    public long getUnReadNum() {
        return unReadNum;
    }

    public void setUnReadNum(long unReadNum) {
        this.unReadNum = unReadNum;
    }

    public String getDealWithTime() {
        return DealWithTime;
    }

    public void setDealWithTime(String dealWithTime) {
        DealWithTime = dealWithTime;
    }

    public String getApplicantIMId() {
        return ApplicantIMId;
    }

    public void setApplicantIMId(String applicantIMId) {
        ApplicantIMId = applicantIMId;
    }
}

