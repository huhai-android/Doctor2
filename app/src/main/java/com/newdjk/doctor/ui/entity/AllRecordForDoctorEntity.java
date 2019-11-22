package com.newdjk.doctor.ui.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

@Entity
public class AllRecordForDoctorEntity implements Serializable {
    @Id(autoincrement = true)
    private Long id;
    private String Content;
    private String PatientName;
    private String ApplicantHeadImgUrl;
    private String EndTime;
    private String PayTime;
    private String CreateTime;
    private int RecordId;
    private int Type;
    private int Status;
    private String DealWithTime;
    private String StartTime;
    private String ApplicantIMId;
    private long unReadNum;
    private String Diagnoses;
    private int OrderStatus;
    private String ReExaminationDate;
    private String ReExaminationStartTime;
    private String ReExaminationEndTime;

    private String Age;
    private String AreaName;
    private int PatientSex;
    private int IsDrKey;
    private int IsPatientMain;
    private String Disease;
    private String ServiceCode;
    private long TimeStamp;
    private String RecordData;
    private int ApplicantId;
    private int PatientId;
    private String DateTime;
    public String getDateTime() {
        return this.DateTime;
    }
    public void setDateTime(String DateTime) {
        this.DateTime = DateTime;
    }
    public int getPatientId() {
        return this.PatientId;
    }
    public void setPatientId(int PatientId) {
        this.PatientId = PatientId;
    }
    public int getApplicantId() {
        return this.ApplicantId;
    }
    public void setApplicantId(int ApplicantId) {
        this.ApplicantId = ApplicantId;
    }
    public String getRecordData() {
        return this.RecordData;
    }
    public void setRecordData(String RecordData) {
        this.RecordData = RecordData;
    }
    public long getTimeStamp() {
        return this.TimeStamp;
    }
    public void setTimeStamp(long TimeStamp) {
        this.TimeStamp = TimeStamp;
    }
    public String getServiceCode() {
        return this.ServiceCode;
    }
    public void setServiceCode(String ServiceCode) {
        this.ServiceCode = ServiceCode;
    }
    public String getDisease() {
        return this.Disease;
    }
    public void setDisease(String Disease) {
        this.Disease = Disease;
    }
    public int getIsPatientMain() {
        return this.IsPatientMain;
    }
    public void setIsPatientMain(int IsPatientMain) {
        this.IsPatientMain = IsPatientMain;
    }
    public int getIsDrKey() {
        return this.IsDrKey;
    }
    public void setIsDrKey(int IsDrKey) {
        this.IsDrKey = IsDrKey;
    }
    public int getPatientSex() {
        return this.PatientSex;
    }
    public void setPatientSex(int PatientSex) {
        this.PatientSex = PatientSex;
    }
    public String getAreaName() {
        return this.AreaName;
    }
    public void setAreaName(String AreaName) {
        this.AreaName = AreaName;
    }
    public String getAge() {
        return this.Age;
    }
    public void setAge(String Age) {
        this.Age = Age;
    }
    public String getReExaminationEndTime() {
        return this.ReExaminationEndTime;
    }
    public void setReExaminationEndTime(String ReExaminationEndTime) {
        this.ReExaminationEndTime = ReExaminationEndTime;
    }
    public String getReExaminationStartTime() {
        return this.ReExaminationStartTime;
    }
    public void setReExaminationStartTime(String ReExaminationStartTime) {
        this.ReExaminationStartTime = ReExaminationStartTime;
    }
    public String getReExaminationDate() {
        return this.ReExaminationDate;
    }
    public void setReExaminationDate(String ReExaminationDate) {
        this.ReExaminationDate = ReExaminationDate;
    }
    public int getOrderStatus() {
        return this.OrderStatus;
    }
    public void setOrderStatus(int OrderStatus) {
        this.OrderStatus = OrderStatus;
    }
    public String getDiagnoses() {
        return this.Diagnoses;
    }
    public void setDiagnoses(String Diagnoses) {
        this.Diagnoses = Diagnoses;
    }
    public long getUnReadNum() {
        return this.unReadNum;
    }
    public void setUnReadNum(long unReadNum) {
        this.unReadNum = unReadNum;
    }
    public String getApplicantIMId() {
        return this.ApplicantIMId;
    }
    public void setApplicantIMId(String ApplicantIMId) {
        this.ApplicantIMId = ApplicantIMId;
    }
    public String getStartTime() {
        return this.StartTime;
    }
    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }
    public String getDealWithTime() {
        return this.DealWithTime;
    }
    public void setDealWithTime(String DealWithTime) {
        this.DealWithTime = DealWithTime;
    }
    public int getStatus() {
        return this.Status;
    }
    public void setStatus(int Status) {
        this.Status = Status;
    }
    public int getType() {
        return this.Type;
    }
    public void setType(int Type) {
        this.Type = Type;
    }
    public int getRecordId() {
        return this.RecordId;
    }
    public void setRecordId(int RecordId) {
        this.RecordId = RecordId;
    }
    public String getCreateTime() {
        return this.CreateTime;
    }
    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }
    public String getPayTime() {
        return this.PayTime;
    }
    public void setPayTime(String PayTime) {
        this.PayTime = PayTime;
    }
    public String getEndTime() {
        return this.EndTime;
    }
    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }
    public String getApplicantHeadImgUrl() {
        return this.ApplicantHeadImgUrl;
    }
    public void setApplicantHeadImgUrl(String ApplicantHeadImgUrl) {
        this.ApplicantHeadImgUrl = ApplicantHeadImgUrl;
    }
    public String getPatientName() {
        return this.PatientName;
    }
    public void setPatientName(String PatientName) {
        this.PatientName = PatientName;
    }
    public String getContent() {
        return this.Content;
    }
    public void setContent(String Content) {
        this.Content = Content;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 580160864)
    public AllRecordForDoctorEntity(Long id, String Content, String PatientName,
            String ApplicantHeadImgUrl, String EndTime, String PayTime,
            String CreateTime, int RecordId, int Type, int Status,
            String DealWithTime, String StartTime, String ApplicantIMId,
            long unReadNum, String Diagnoses, int OrderStatus,
            String ReExaminationDate, String ReExaminationStartTime,
            String ReExaminationEndTime, String Age, String AreaName,
            int PatientSex, int IsDrKey, int IsPatientMain, String Disease,
            String ServiceCode, long TimeStamp, String RecordData, int ApplicantId,
            int PatientId, String DateTime) {
        this.id = id;
        this.Content = Content;
        this.PatientName = PatientName;
        this.ApplicantHeadImgUrl = ApplicantHeadImgUrl;
        this.EndTime = EndTime;
        this.PayTime = PayTime;
        this.CreateTime = CreateTime;
        this.RecordId = RecordId;
        this.Type = Type;
        this.Status = Status;
        this.DealWithTime = DealWithTime;
        this.StartTime = StartTime;
        this.ApplicantIMId = ApplicantIMId;
        this.unReadNum = unReadNum;
        this.Diagnoses = Diagnoses;
        this.OrderStatus = OrderStatus;
        this.ReExaminationDate = ReExaminationDate;
        this.ReExaminationStartTime = ReExaminationStartTime;
        this.ReExaminationEndTime = ReExaminationEndTime;
        this.Age = Age;
        this.AreaName = AreaName;
        this.PatientSex = PatientSex;
        this.IsDrKey = IsDrKey;
        this.IsPatientMain = IsPatientMain;
        this.Disease = Disease;
        this.ServiceCode = ServiceCode;
        this.TimeStamp = TimeStamp;
        this.RecordData = RecordData;
        this.ApplicantId = ApplicantId;
        this.PatientId = PatientId;
        this.DateTime = DateTime;
    }
    @Generated(hash = 1219133272)
    public AllRecordForDoctorEntity() {
    }


    @Override
    public String toString() {
        return "AllRecordForDoctorEntity{" +
                "id=" + id +
                ", Content='" + Content + '\'' +
                ", PatientName='" + PatientName + '\'' +
                ", ApplicantHeadImgUrl='" + ApplicantHeadImgUrl + '\'' +
                ", EndTime='" + EndTime + '\'' +
                ", PayTime='" + PayTime + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", RecordId=" + RecordId +
                ", Type=" + Type +
                ", Status=" + Status +
                ", DealWithTime='" + DealWithTime + '\'' +
                ", StartTime='" + StartTime + '\'' +
                ", ApplicantIMId='" + ApplicantIMId + '\'' +
                ", unReadNum=" + unReadNum +
                ", Diagnoses='" + Diagnoses + '\'' +
                ", OrderStatus=" + OrderStatus +
                ", ReExaminationDate='" + ReExaminationDate + '\'' +
                ", ReExaminationStartTime='" + ReExaminationStartTime + '\'' +
                ", ReExaminationEndTime='" + ReExaminationEndTime + '\'' +
                ", Age='" + Age + '\'' +
                ", AreaName='" + AreaName + '\'' +
                ", PatientSex=" + PatientSex +
                ", IsDrKey=" + IsDrKey +
                ", IsPatientMain=" + IsPatientMain +
                ", Disease='" + Disease + '\'' +
                ", ServiceCode='" + ServiceCode + '\'' +
                ", TimeStamp=" + TimeStamp +
                ", RecordData='" + RecordData + '\'' +
                ", ApplicantId=" + ApplicantId +
                ", PatientId=" + PatientId +
                ", DateTime='" + DateTime + '\'' +
                '}';
    }
}
