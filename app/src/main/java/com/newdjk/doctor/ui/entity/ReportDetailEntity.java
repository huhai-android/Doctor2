package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   ReportDetailEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/8 10:12
 *  @描述：
 */
public class ReportDetailEntity {

    /**
     * AskId : 1
     * AskNo : 2000001
     * ScType : 2
     * ScTypeName : sample string 3
     * Score : sample string 4
     * Result : sample string 5
     * Advice : sample string 6
     * DoctorName : sample string 7
     * ReadTime : 2018-11-08T09:52:59.3710075+08:00
     * PatientName : sample string 8
     * Age : 1
     * Weeks : sample string 9
     * Mobile : sample string 10
     * Sex : 11
     * Duration : 12
     * BeginDate : 2018-11-08T09:52:59.3710075+08:00
     * EndDate : 2018-11-08T09:52:59.3710075+08:00
     * MonitorTime : 2018-11-08T09:52:59.3710075+08:00
     */

    private int AskId;
    private String AskNo;
    private int ScType;
    private String ScTypeName;
    private String Score;
    private String Result;
    private String Advice;
    private String DoctorName;
    private String ReadTime;
    private String PatientName;
    private int Age;
    private String Weeks;
    private String Mobile;
    private int Sex;
    private int Duration;
    private String BeginDate;
    private String EndDate;
    private String MonitorTime;

    public int getAskId() {
        return AskId;
    }

    public void setAskId(int AskId) {
        this.AskId = AskId;
    }

    public String getAskNo() {
        return AskNo;
    }

    public void setAskNo(String AskNo) {
        this.AskNo = AskNo;
    }

    public int getScType() {
        return ScType;
    }

    public void setScType(int ScType) {
        this.ScType = ScType;
    }

    public String getScTypeName() {
        return ScTypeName;
    }

    public void setScTypeName(String ScTypeName) {
        this.ScTypeName = ScTypeName;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String Score) {
        this.Score = Score;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public String getAdvice() {
        return Advice;
    }

    public void setAdvice(String Advice) {
        this.Advice = Advice;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String DoctorName) {
        this.DoctorName = DoctorName;
    }

    public String getReadTime() {
        return ReadTime;
    }

    public void setReadTime(String ReadTime) {
        this.ReadTime = ReadTime;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String PatientName) {
        this.PatientName = PatientName;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public String getWeeks() {
        return Weeks;
    }

    public void setWeeks(String Weeks) {
        this.Weeks = Weeks;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int Sex) {
        this.Sex = Sex;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int Duration) {
        this.Duration = Duration;
    }

    public String getBeginDate() {
        return BeginDate;
    }

    public void setBeginDate(String BeginDate) {
        this.BeginDate = BeginDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public String getMonitorTime() {
        return MonitorTime;
    }

    public void setMonitorTime(String MonitorTime) {
        this.MonitorTime = MonitorTime;
    }

    @Override
    public String toString() {
        return "ReportDetailEntity{" +
                "AskId=" + AskId +
                ", AskNo='" + AskNo + '\'' +
                ", ScType=" + ScType +
                ", ScTypeName='" + ScTypeName + '\'' +
                ", Score='" + Score + '\'' +
                ", Result='" + Result + '\'' +
                ", Advice='" + Advice + '\'' +
                ", DoctorName='" + DoctorName + '\'' +
                ", ReadTime='" + ReadTime + '\'' +
                ", PatientName='" + PatientName + '\'' +
                ", Age=" + Age +
                ", Weeks='" + Weeks + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Sex=" + Sex +
                ", Duration=" + Duration +
                ", BeginDate='" + BeginDate + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", MonitorTime='" + MonitorTime + '\'' +
                '}';
    }
}
