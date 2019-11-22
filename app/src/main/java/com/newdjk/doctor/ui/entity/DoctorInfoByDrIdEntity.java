package com.newdjk.doctor.ui.entity;

import java.util.List;

public class DoctorInfoByDrIdEntity {
    private int DrId;
    private int DrType;
    private int Position;
    private int IsOnline;
    private int PraiseRate;
    private int ConsultCount;
    private int ResponseRate;
    private int ConsultVolume;
    private String OnPrescription;
    private int VisitCount;
    private int Telenursing;
    private int VolumeVideo;
    private int FetalHeart;
    private int IsHasPack;
    private boolean IsPatientMain;
    private String DrName;
    private String Mobile;
    private String HospitalName;
    private String DepartmentName;
    private String PositionName;
    private String DoctorGroup;
    private String  PicturePath;
    private String TreatMent;
    private String DoctorSkill;
    private String Description;
    private String CreateTime;
    private String SericeItemCodes;
    private List<DrServiceItemEntity>  DrServiceItems;

    public int getDrId() {
        return DrId;
    }

    public void setDrId(int drId) {
        DrId = drId;
    }

    public int getDrType() {
        return DrType;
    }

    public void setDrType(int drType) {
        DrType = drType;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public int getIsOnline() {
        return IsOnline;
    }

    public void setIsOnline(int isOnline) {
        IsOnline = isOnline;
    }

    public int getPraiseRate() {
        return PraiseRate;
    }

    public void setPraiseRate(int praiseRate) {
        PraiseRate = praiseRate;
    }

    public int getConsultCount() {
        return ConsultCount;
    }

    public void setConsultCount(int consultCount) {
        ConsultCount = consultCount;
    }

    public int getResponseRate() {
        return ResponseRate;
    }

    public void setResponseRate(int responseRate) {
        ResponseRate = responseRate;
    }

    public int getConsultVolume() {
        return ConsultVolume;
    }

    public void setConsultVolume(int consultVolume) {
        ConsultVolume = consultVolume;
    }

    public String getOnPrescription() {
        return OnPrescription;
    }

    public void setOnPrescription(String onPrescription) {
        OnPrescription = onPrescription;
    }

    public int getVisitCount() {
        return VisitCount;
    }

    public void setVisitCount(int visitCount) {
        VisitCount = visitCount;
    }

    public int getTelenursing() {
        return Telenursing;
    }

    public void setTelenursing(int telenursing) {
        Telenursing = telenursing;
    }

    public int getVolumeVideo() {
        return VolumeVideo;
    }

    public void setVolumeVideo(int volumeVideo) {
        VolumeVideo = volumeVideo;
    }

    public int getFetalHeart() {
        return FetalHeart;
    }

    public void setFetalHeart(int fetalHeart) {
        FetalHeart = fetalHeart;
    }

    public int getIsHasPack() {
        return IsHasPack;
    }

    public void setIsHasPack(int isHasPack) {
        IsHasPack = isHasPack;
    }

    public boolean isPatientMain() {
        return IsPatientMain;
    }

    public void setPatientMain(boolean patientMain) {
        IsPatientMain = patientMain;
    }

    public String getDrName() {
        return DrName;
    }

    public void setDrName(String drName) {
        DrName = drName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getPositionName() {
        return PositionName;
    }

    public void setPositionName(String positionName) {
        PositionName = positionName;
    }

    public String getDoctorGroup() {
        return DoctorGroup;
    }

    public void setDoctorGroup(String doctorGroup) {
        DoctorGroup = doctorGroup;
    }

    public String getPicturePath() {
        return PicturePath;
    }

    public void setPicturePath(String picturePath) {
        PicturePath = picturePath;
    }

    public String getTreatMent() {
        return TreatMent;
    }

    public void setTreatMent(String treatMent) {
        TreatMent = treatMent;
    }

    public String getDoctorSkill() {
        return DoctorSkill;
    }

    public void setDoctorSkill(String doctorSkill) {
        DoctorSkill = doctorSkill;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getSericeItemCodes() {
        return SericeItemCodes;
    }

    public void setSericeItemCodes(String sericeItemCodes) {
        SericeItemCodes = sericeItemCodes;
    }

    public List<DrServiceItemEntity> getDrServiceItems() {
        return DrServiceItems;
    }

    public void setDrServiceItems(List<DrServiceItemEntity> drServiceItems) {
        DrServiceItems = drServiceItems;
    }
}
