package com.newdjk.doctor.ui.entity;

import java.io.Serializable;
import java.util.List;

public class DoctorInfoByIdEntity implements Serializable {

    /**
     * DrId : 1
     * DrName : sample string 2
     * Mobile : sample string 3
     * DrType : 64
     * DrSex : 64
     * HospitalId : 6
     * HospitalName : sample string 7
     * DepartmentId : 8
     * DepartmentName : sample string 9
     * Position : 64
     * PositionName : sample string 11
     * DoctorGroup : sample string 12
     * PicturePath : sample string 13
     * TreatMent : sample string 14
     * DoctorSkill : sample string 15
     * Description : sample string 16
     * IsOnline : 64
     * CreateTime : 2018-12-14T15:58:33.2799447+08:00
     * PraiseRate : 19
     * ConsultCount : 20
     * ResponseRate : 21
     * ConsultVolume : 22
     * OnPrescription : 23
     * VisitCount : 24
     * Telenursing : 25
     * VolumeVideo : 26
     * FetalHeart : 27
     * SericeItemCodes : sample string 28
     * DrServiceItems : [{"SericeItemCode":"sample string 1","SericeItemName":"sample string 2","OriginalPrice":3,"Price":4},{"SericeItemCode":"sample string 1","SericeItemName":"sample string 2","OriginalPrice":3,"Price":4}]
     * IsPatientMain : true
     * IsAttantDr : true
     * IsHasPack : 64
     * PatientAttentDrNum : 32
     */

    private int DrId;
    private String DrName;
    private String Mobile;
    private int DrType;
    private int DrSex;
    private int HospitalId;
    private String HospitalName;
    private int DepartmentId;
    private String DepartmentName;
    private int Position;
    private String PositionName;
    private String DoctorGroup;
    private String PicturePath;
    private String TreatMent;
    private String DoctorSkill;
    private String Description;
    private int IsOnline;
    private String CreateTime;
    private int PraiseRate;
    private int ConsultCount;
    private int ResponseRate;
    private int ConsultVolume;
    private int OnPrescription;
    private int VisitCount;
    private int Telenursing;
    private int VolumeVideo;
    private int FetalHeart;
    private String SericeItemCodes;
    private boolean IsPatientMain;
    private boolean IsAttantDr;
    private int IsHasPack;
    private int PatientAttentDrNum;
    private int ProfessionalLevel;

    public int getProfessionalLevel() {
        return ProfessionalLevel;
    }

    public void setProfessionalLevel(int professionalLevel) {
        ProfessionalLevel = professionalLevel;
    }

    private List<DrServiceItemsBean> DrServiceItems;

    public int getDrId() {
        return DrId;
    }

    public void setDrId(int DrId) {
        this.DrId = DrId;
    }

    public String getDrName() {
        return DrName;
    }

    public void setDrName(String DrName) {
        this.DrName = DrName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public int getDrType() {
        return DrType;
    }

    public void setDrType(int DrType) {
        this.DrType = DrType;
    }

    public int getDrSex() {
        return DrSex;
    }

    public void setDrSex(int DrSex) {
        this.DrSex = DrSex;
    }

    public int getHospitalId() {
        return HospitalId;
    }

    public void setHospitalId(int HospitalId) {
        this.HospitalId = HospitalId;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String HospitalName) {
        this.HospitalName = HospitalName;
    }

    public int getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(int DepartmentId) {
        this.DepartmentId = DepartmentId;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String DepartmentName) {
        this.DepartmentName = DepartmentName;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int Position) {
        this.Position = Position;
    }

    public String getPositionName() {
        return PositionName;
    }

    public void setPositionName(String PositionName) {
        this.PositionName = PositionName;
    }

    public String getDoctorGroup() {
        return DoctorGroup;
    }

    public void setDoctorGroup(String DoctorGroup) {
        this.DoctorGroup = DoctorGroup;
    }

    public String getPicturePath() {
        return PicturePath;
    }

    public void setPicturePath(String PicturePath) {
        this.PicturePath = PicturePath;
    }

    public String getTreatMent() {
        return TreatMent;
    }

    public void setTreatMent(String TreatMent) {
        this.TreatMent = TreatMent;
    }

    public String getDoctorSkill() {
        return DoctorSkill;
    }

    public void setDoctorSkill(String DoctorSkill) {
        this.DoctorSkill = DoctorSkill;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getIsOnline() {
        return IsOnline;
    }

    public void setIsOnline(int IsOnline) {
        this.IsOnline = IsOnline;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public int getPraiseRate() {
        return PraiseRate;
    }

    public void setPraiseRate(int PraiseRate) {
        this.PraiseRate = PraiseRate;
    }

    public int getConsultCount() {
        return ConsultCount;
    }

    public void setConsultCount(int ConsultCount) {
        this.ConsultCount = ConsultCount;
    }

    public int getResponseRate() {
        return ResponseRate;
    }

    public void setResponseRate(int ResponseRate) {
        this.ResponseRate = ResponseRate;
    }

    public int getConsultVolume() {
        return ConsultVolume;
    }

    public void setConsultVolume(int ConsultVolume) {
        this.ConsultVolume = ConsultVolume;
    }

    public int getOnPrescription() {
        return OnPrescription;
    }

    public void setOnPrescription(int OnPrescription) {
        this.OnPrescription = OnPrescription;
    }

    public int getVisitCount() {
        return VisitCount;
    }

    public void setVisitCount(int VisitCount) {
        this.VisitCount = VisitCount;
    }

    public int getTelenursing() {
        return Telenursing;
    }

    public void setTelenursing(int Telenursing) {
        this.Telenursing = Telenursing;
    }

    public int getVolumeVideo() {
        return VolumeVideo;
    }

    public void setVolumeVideo(int VolumeVideo) {
        this.VolumeVideo = VolumeVideo;
    }

    public int getFetalHeart() {
        return FetalHeart;
    }

    public void setFetalHeart(int FetalHeart) {
        this.FetalHeart = FetalHeart;
    }

    public String getSericeItemCodes() {
        return SericeItemCodes;
    }

    public void setSericeItemCodes(String SericeItemCodes) {
        this.SericeItemCodes = SericeItemCodes;
    }

    public boolean isIsPatientMain() {
        return IsPatientMain;
    }

    public void setIsPatientMain(boolean IsPatientMain) {
        this.IsPatientMain = IsPatientMain;
    }

    public boolean isIsAttantDr() {
        return IsAttantDr;
    }

    public void setIsAttantDr(boolean IsAttantDr) {
        this.IsAttantDr = IsAttantDr;
    }

    public int getIsHasPack() {
        return IsHasPack;
    }

    public void setIsHasPack(int IsHasPack) {
        this.IsHasPack = IsHasPack;
    }

    public int getPatientAttentDrNum() {
        return PatientAttentDrNum;
    }

    public void setPatientAttentDrNum(int PatientAttentDrNum) {
        this.PatientAttentDrNum = PatientAttentDrNum;
    }

    public List<DrServiceItemsBean> getDrServiceItems() {
        return DrServiceItems;
    }

    public void setDrServiceItems(List<DrServiceItemsBean> DrServiceItems) {
        this.DrServiceItems = DrServiceItems;
    }

    public static class DrServiceItemsBean implements Serializable{
        /**
         * SericeItemCode : sample string 1
         * SericeItemName : sample string 2
         * OriginalPrice : 3.0
         * Price : 4.0
         */

        private String SericeItemCode;
        private String SericeItemName;
        private double OriginalPrice;
        private double Price;

        public String getSericeItemCode() {
            return SericeItemCode;
        }

        public void setSericeItemCode(String SericeItemCode) {
            this.SericeItemCode = SericeItemCode;
        }

        public String getSericeItemName() {
            return SericeItemName;
        }

        public void setSericeItemName(String SericeItemName) {
            this.SericeItemName = SericeItemName;
        }

        public double getOriginalPrice() {
            return OriginalPrice;
        }

        public void setOriginalPrice(double OriginalPrice) {
            this.OriginalPrice = OriginalPrice;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }
    }
}
