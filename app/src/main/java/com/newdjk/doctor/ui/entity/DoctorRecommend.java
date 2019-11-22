package com.newdjk.doctor.ui.entity;

import java.util.List;

public class DoctorRecommend {


    /**
     * Total : 1
     * ReturnData : [{"DrId":1,"DrName":"sample string 2","DrNicke":"sample string 3","NameLetter":"sample string 4","DrAccount":"sample string 5","Password":"sample string 6","DrSex":64,"Birthday":"2019-01-23T15:26:15.8399521+08:00","CredType":64,"CredNo":"sample string 8","Mobile":"sample string 9","DrType":64,"AdminType":64,"HospitalId":12,"HospitalName":"sample string 13","DepartmentId":14,"DepartmentName":"sample string 15","Position":64,"DrOrder":17,"DoctorGroup":"sample string 18","PicturePath":"sample string 19","TreatMent":"sample string 20","DoctorSkill":"sample string 21","Description":"sample string 22","Source":64,"IsOnline":64,"DrStatus":64,"AuditTime":"2019-01-23T15:26:15.8409287+08:00","AuditName":"sample string 26","IsHot":64,"ReviewNotDesc":"sample string 28","CheckId":29,"Invalid":64,"CreateTime":"2019-01-23T15:26:15.8419053+08:00","UpdateTime":"2019-01-23T15:26:15.8419053+08:00","RecommendId":33,"RecommendName":"sample string 34","IsOpenPresc":64,"OpratePrescDrId":36},{"DrId":1,"DrName":"sample string 2","DrNicke":"sample string 3","NameLetter":"sample string 4","DrAccount":"sample string 5","Password":"sample string 6","DrSex":64,"Birthday":"2019-01-23T15:26:15.8399521+08:00","CredType":64,"CredNo":"sample string 8","Mobile":"sample string 9","DrType":64,"AdminType":64,"HospitalId":12,"HospitalName":"sample string 13","DepartmentId":14,"DepartmentName":"sample string 15","Position":64,"DrOrder":17,"DoctorGroup":"sample string 18","PicturePath":"sample string 19","TreatMent":"sample string 20","DoctorSkill":"sample string 21","Description":"sample string 22","Source":64,"IsOnline":64,"DrStatus":64,"AuditTime":"2019-01-23T15:26:15.8409287+08:00","AuditName":"sample string 26","IsHot":64,"ReviewNotDesc":"sample string 28","CheckId":29,"Invalid":64,"CreateTime":"2019-01-23T15:26:15.8419053+08:00","UpdateTime":"2019-01-23T15:26:15.8419053+08:00","RecommendId":33,"RecommendName":"sample string 34","IsOpenPresc":64,"OpratePrescDrId":36}]
     */

    private int Total;
    private List<ReturnDataBean> ReturnData;

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public List<ReturnDataBean> getReturnData() {
        return ReturnData;
    }

    public void setReturnData(List<ReturnDataBean> ReturnData) {
        this.ReturnData = ReturnData;
    }

    public static class ReturnDataBean {
        /**
         * DrId : 1
         * DrName : sample string 2
         * DrNicke : sample string 3
         * NameLetter : sample string 4
         * DrAccount : sample string 5
         * Password : sample string 6
         * DrSex : 64
         * Birthday : 2019-01-23T15:26:15.8399521+08:00
         * CredType : 64
         * CredNo : sample string 8
         * Mobile : sample string 9
         * DrType : 64
         * AdminType : 64
         * HospitalId : 12
         * HospitalName : sample string 13
         * DepartmentId : 14
         * DepartmentName : sample string 15
         * Position : 64
         * DrOrder : 17
         * DoctorGroup : sample string 18
         * PicturePath : sample string 19
         * TreatMent : sample string 20
         * DoctorSkill : sample string 21
         * Description : sample string 22
         * Source : 64
         * IsOnline : 64
         * DrStatus : 64
         * AuditTime : 2019-01-23T15:26:15.8409287+08:00
         * AuditName : sample string 26
         * IsHot : 64
         * ReviewNotDesc : sample string 28
         * CheckId : 29
         * Invalid : 64
         * CreateTime : 2019-01-23T15:26:15.8419053+08:00
         * UpdateTime : 2019-01-23T15:26:15.8419053+08:00
         * RecommendId : 33
         * RecommendName : sample string 34
         * IsOpenPresc : 64
         * OpratePrescDrId : 36
         */

        private int DrId;
        private String DrName;
        private String DrNicke;
        private String NameLetter;
        private String DrAccount;
        private String Password;
        private int DrSex;
        private String Birthday;
        private int CredType;
        private String CredNo;
        private String Mobile;
        private int DrType;
        private int AdminType;
        private int HospitalId;
        private String HospitalName;
        private int DepartmentId;
        private String DepartmentName;
        private int Position;
        private int DrOrder;
        private String DoctorGroup;
        private String PicturePath;
        private String TreatMent;
        private String DoctorSkill;
        private String Description;
        private int Source;
        private int IsOnline;
        private int DrStatus;
        private String AuditTime;
        private String AuditName;
        private int IsHot;
        private String ReviewNotDesc;
        private int CheckId;
        private int Invalid;
        private String CreateTime;
        private String UpdateTime;
        private int RecommendId;
        private String RecommendName;
        private int IsOpenPresc;
        private int OpratePrescDrId;

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

        public String getDrNicke() {
            return DrNicke;
        }

        public void setDrNicke(String DrNicke) {
            this.DrNicke = DrNicke;
        }

        public String getNameLetter() {
            return NameLetter;
        }

        public void setNameLetter(String NameLetter) {
            this.NameLetter = NameLetter;
        }

        public String getDrAccount() {
            return DrAccount;
        }

        public void setDrAccount(String DrAccount) {
            this.DrAccount = DrAccount;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String Password) {
            this.Password = Password;
        }

        public int getDrSex() {
            return DrSex;
        }

        public void setDrSex(int DrSex) {
            this.DrSex = DrSex;
        }

        public String getBirthday() {
            return Birthday;
        }

        public void setBirthday(String Birthday) {
            this.Birthday = Birthday;
        }

        public int getCredType() {
            return CredType;
        }

        public void setCredType(int CredType) {
            this.CredType = CredType;
        }

        public String getCredNo() {
            return CredNo;
        }

        public void setCredNo(String CredNo) {
            this.CredNo = CredNo;
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

        public int getAdminType() {
            return AdminType;
        }

        public void setAdminType(int AdminType) {
            this.AdminType = AdminType;
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

        public int getDrOrder() {
            return DrOrder;
        }

        public void setDrOrder(int DrOrder) {
            this.DrOrder = DrOrder;
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

        public int getSource() {
            return Source;
        }

        public void setSource(int Source) {
            this.Source = Source;
        }

        public int getIsOnline() {
            return IsOnline;
        }

        public void setIsOnline(int IsOnline) {
            this.IsOnline = IsOnline;
        }

        public int getDrStatus() {
            return DrStatus;
        }

        public void setDrStatus(int DrStatus) {
            this.DrStatus = DrStatus;
        }

        public String getAuditTime() {
            return AuditTime;
        }

        public void setAuditTime(String AuditTime) {
            this.AuditTime = AuditTime;
        }

        public String getAuditName() {
            return AuditName;
        }

        public void setAuditName(String AuditName) {
            this.AuditName = AuditName;
        }

        public int getIsHot() {
            return IsHot;
        }

        public void setIsHot(int IsHot) {
            this.IsHot = IsHot;
        }

        public String getReviewNotDesc() {
            return ReviewNotDesc;
        }

        public void setReviewNotDesc(String ReviewNotDesc) {
            this.ReviewNotDesc = ReviewNotDesc;
        }

        public int getCheckId() {
            return CheckId;
        }

        public void setCheckId(int CheckId) {
            this.CheckId = CheckId;
        }

        public int getInvalid() {
            return Invalid;
        }

        public void setInvalid(int Invalid) {
            this.Invalid = Invalid;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public int getRecommendId() {
            return RecommendId;
        }

        public void setRecommendId(int RecommendId) {
            this.RecommendId = RecommendId;
        }

        public String getRecommendName() {
            return RecommendName;
        }

        public void setRecommendName(String RecommendName) {
            this.RecommendName = RecommendName;
        }

        public int getIsOpenPresc() {
            return IsOpenPresc;
        }

        public void setIsOpenPresc(int IsOpenPresc) {
            this.IsOpenPresc = IsOpenPresc;
        }

        public int getOpratePrescDrId() {
            return OpratePrescDrId;
        }

        public void setOpratePrescDrId(int OpratePrescDrId) {
            this.OpratePrescDrId = OpratePrescDrId;
        }
    }
}
