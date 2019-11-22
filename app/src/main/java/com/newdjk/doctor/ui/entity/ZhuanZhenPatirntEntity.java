package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   ZhuanZhenPatirntEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/3/18 16:56
 *  @描述：
 */
public class ZhuanZhenPatirntEntity {


    /**
     * IsSuccess : true
     * PatientId : 16
     * PatientName : HUU
     * PatientSex : 2
     * Birthday : 1993-04-27 00:00:00
     * Age : 26岁
     * PaPicturePath : http://userimage.newstarthealth.cn/pat/0/16.jpg?dt=201905093823271
     * ReferralRecordId : 174
     * DisGroupId : 0
     * DisGroupName :
     * FromDrId : 47
     * FromDrName : 赵季
     * ToDrId : 57
     * ToDrName : 奥巴马
     * ReferralRemark : 1111
     * ReferralStatus : 2
     * ReferralTime : 2019-05-09 11:38:23
     * ReceiveTime : 2019-05-09 10:55:58
     * ReferralClass : 1
     * CreateTime : 2019-05-08 13:48:26
     */

    private boolean IsSuccess;
    private int PatientId;
    private String PatientName;
    private int PatientSex;
    private String Birthday;
    private String Age;
    private String PaPicturePath;
    private int ReferralRecordId;
    private int DisGroupId;
    private String DisGroupName;
    private int FromDrId;
    private String FromDrName;
    private int ToDrId;
    private String ToDrName;
    private String ReferralRemark;
    private int ReferralStatus;
    private String ReferralTime;
    private String ReceiveTime;
    private int ReferralClass;
    private String CreateTime;

    public boolean isIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean IsSuccess) {
        this.IsSuccess = IsSuccess;
    }

    public int getPatientId() {
        return PatientId;
    }

    public void setPatientId(int PatientId) {
        this.PatientId = PatientId;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String PatientName) {
        this.PatientName = PatientName;
    }

    public int getPatientSex() {
        return PatientSex;
    }

    public void setPatientSex(int PatientSex) {
        this.PatientSex = PatientSex;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String Birthday) {
        this.Birthday = Birthday;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String Age) {
        this.Age = Age;
    }

    public String getPaPicturePath() {
        return PaPicturePath;
    }

    public void setPaPicturePath(String PaPicturePath) {
        this.PaPicturePath = PaPicturePath;
    }

    public int getReferralRecordId() {
        return ReferralRecordId;
    }

    public void setReferralRecordId(int ReferralRecordId) {
        this.ReferralRecordId = ReferralRecordId;
    }

    public int getDisGroupId() {
        return DisGroupId;
    }

    public void setDisGroupId(int DisGroupId) {
        this.DisGroupId = DisGroupId;
    }

    public String getDisGroupName() {
        return DisGroupName;
    }

    public void setDisGroupName(String DisGroupName) {
        this.DisGroupName = DisGroupName;
    }

    public int getFromDrId() {
        return FromDrId;
    }

    public void setFromDrId(int FromDrId) {
        this.FromDrId = FromDrId;
    }

    public String getFromDrName() {
        return FromDrName;
    }

    public void setFromDrName(String FromDrName) {
        this.FromDrName = FromDrName;
    }

    public int getToDrId() {
        return ToDrId;
    }

    public void setToDrId(int ToDrId) {
        this.ToDrId = ToDrId;
    }

    public String getToDrName() {
        return ToDrName;
    }

    public void setToDrName(String ToDrName) {
        this.ToDrName = ToDrName;
    }

    public String getReferralRemark() {
        return ReferralRemark;
    }

    public void setReferralRemark(String ReferralRemark) {
        this.ReferralRemark = ReferralRemark;
    }

    public int getReferralStatus() {
        return ReferralStatus;
    }

    public void setReferralStatus(int ReferralStatus) {
        this.ReferralStatus = ReferralStatus;
    }

    public String getReferralTime() {
        return ReferralTime;
    }

    public void setReferralTime(String ReferralTime) {
        this.ReferralTime = ReferralTime;
    }

    public String getReceiveTime() {
        return ReceiveTime;
    }

    public void setReceiveTime(String ReceiveTime) {
        this.ReceiveTime = ReceiveTime;
    }

    public int getReferralClass() {
        return ReferralClass;
    }

    public void setReferralClass(int ReferralClass) {
        this.ReferralClass = ReferralClass;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }
}
