package com.newdjk.doctor.ui.entity;

import java.io.Serializable;

public class OnlineRenewalDataEntity implements Serializable {
    //private String PrescriptionFullInfo;
    private String PrescriptionId;
    private String Diagnoses;
    private String Appeal;
    private String AllergicHistory;
    private String PatientName;
    private String ApplicantHeadImgUrl;
    private String ApplicantName;
    private String DoctorName;
    private String OrgName;
    private String OrgPath;
    private String RejectReason;
    private String PayTime;
    private String PayOrderNo;
    private String Remark;
    private String UpdateTime;
    private String CreateTime;
    private String DealWithTime;
    private String ApplicantIMId;
    private String EndTime;
    private String StartTime;
    private int OrderStatus;
    private int Id;
    private int Type;
    private int Method;
    private int PatientId;
    private int ApplicantId;
    private int DoctorId;
    private int OrgId;
    private int StartMethod;
    private int Status;
    private int PayStatus;
    private long unReadNum;
    private String  recordId;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    private String DoctorIMId;

    public String getDoctorIMId() {
        return DoctorIMId;
    }

    public void setDoctorIMId(String doctorIMId) {
        DoctorIMId = doctorIMId;
    }

    private DoctorPatientRelationEntity DoctorPatientRelation;
    private PatientInfoEntity PatientInfo;

    public String getApplicantHeadImgUrl() {
        return ApplicantHeadImgUrl;
    }

    public void setApplicantHeadImgUrl(String applicantHeadImgUrl) {
        ApplicantHeadImgUrl = applicantHeadImgUrl;
    }


    /*ublic String getPrescriptionFullInfo() {
        return PrescriptionFullInfo;
    }

    public void setPrescriptionFullInfo(String prescriptionFullInfo) {
        PrescriptionFullInfo = prescriptionFullInfo;
    }*/

    public DoctorPatientRelationEntity getDoctorPatientRelation() {
        return DoctorPatientRelation;
    }

    public void setDoctorPatientRelation(DoctorPatientRelationEntity doctorPatientRelation) {
        DoctorPatientRelation = doctorPatientRelation;
    }

    public String getPrescriptionId() {
        return PrescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        PrescriptionId = prescriptionId;
    }

    public String getDiagnoses() {
        return Diagnoses;
    }

    public void setDiagnoses(String diagnoses) {
        Diagnoses = diagnoses;
    }

    public String getAppeal() {
        return Appeal;
    }

    public void setAppeal(String appeal) {
        Appeal = appeal;
    }

    public String getAllergicHistory() {
        return AllergicHistory;
    }

    public void setAllergicHistory(String allergicHistory) {
        AllergicHistory = allergicHistory;
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

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
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

    public String getRejectReason() {
        return RejectReason;
    }

    public void setRejectReason(String rejectReason) {
        RejectReason = rejectReason;
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

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        OrderStatus = orderStatus;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getMethod() {
        return Method;
    }

    public void setMethod(int method) {
        Method = method;
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

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int doctorId) {
        DoctorId = doctorId;
    }

    public int getOrgId() {
        return OrgId;
    }

    public void setOrgId(int orgId) {
        OrgId = orgId;
    }

    public int getStartMethod() {
        return StartMethod;
    }

    public void setStartMethod(int startMethod) {
        StartMethod = startMethod;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getPayStatus() {
        return PayStatus;
    }

    public void setPayStatus(int payStatus) {
        PayStatus = payStatus;
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

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }
}
