package com.newdjk.doctor.ui.entity;

import java.util.List;

/**
 * Created by Struggle on 2018/10/15.
 */

public class QueryOrgDoctorSearchByPageEntity {


    /**
     * Total : 2
     * ReturnData : [{"SpecialistHosGroupId":0,"DrId":1701,"DrName":"胡海","Mobile":"13622485182","CredNo":"421124198902080516","DrType":1,"PharmacistType":0,"DrSex":1,"DrStatus":3,"ReviewNotDesc":null,"HospitalId":553,"HospitalName":"深圳南山医院","DepartmentId":45,"DepartmentName":"骨外科","Position":7,"PositionName":"主任技师","DoctorGroup":"","PicturePath":null,"TreatMent":null,"DoctorSkill":"哈哈哈哈彼此彼此查查哈哈哈哈嘿嘿和","Description":null,"IsOnline":1,"CreateTime":"2020-04-03 11:07:28","PraiseRate":100,"ConsultCount":0,"ResponseRate":100,"ConsultVolume":0,"OnPrescription":0,"VisitCount":0,"Telenursing":0,"VolumeVideo":0,"FetalHeart":0,"SericeItemCodes":"1101|图文问诊|0.00|0.00","DrServiceItems":[{"SericeItemCode":"1101","SericeItemName":"图文问诊","OriginalPrice":"0.00","Price":"0.00"}],"IsPatientMain":false,"IsAttantDr":false,"IsHasPack":0,"PatientAttentDrNum":0,"MedicalRecord":0,"MedicalPrice":"0.00","InterVolume":0,"FaceAppointment":0,"PrescriptionNum":0,"InspectionNum":0,"ManuPatientAttentDrNum":89,"ManuInterVolume":74,"MDTNum":0,"IsOpenPresc":0,"OpratePrescDrId":0,"OrgId":1,"OrgName":"新起点互联网医院","IsClinic":0,"IsReferral":0,"ProfessionalLevel":0,"AreaId":234,"AreaName":"深圳市","AreaPath":",0,19,234,","IsSpecialReferral":0,"SpecialReferralAmount":"0.00","IsHead":0,"DrTitle":null},{"SpecialistHosGroupId":0,"DrId":1702,"DrName":"胡海","Mobile":"13622485181","CredNo":null,"DrType":1,"PharmacistType":0,"DrSex":1,"DrStatus":0,"ReviewNotDesc":null,"HospitalId":558,"HospitalName":"蛇口人民医院","DepartmentId":49,"DepartmentName":"口腔科","Position":9,"PositionName":"主管技师","DoctorGroup":"","PicturePath":null,"TreatMent":null,"DoctorSkill":"红米明明哦明明您明明哦","Description":null,"IsOnline":1,"CreateTime":"2020-04-03 11:18:30","PraiseRate":100,"ConsultCount":0,"ResponseRate":100,"ConsultVolume":1,"OnPrescription":0,"VisitCount":0,"Telenursing":0,"VolumeVideo":0,"FetalHeart":0,"SericeItemCodes":"1101|图文问诊|0.00|0.00","DrServiceItems":[{"SericeItemCode":"1101","SericeItemName":"图文问诊","OriginalPrice":"0.00","Price":"0.00"}],"IsPatientMain":false,"IsAttantDr":false,"IsHasPack":0,"PatientAttentDrNum":1,"MedicalRecord":0,"MedicalPrice":"0.00","InterVolume":1,"FaceAppointment":0,"PrescriptionNum":0,"InspectionNum":0,"ManuPatientAttentDrNum":27,"ManuInterVolume":12,"MDTNum":0,"IsOpenPresc":0,"OpratePrescDrId":0,"OrgId":1,"OrgName":"新起点互联网医院","IsClinic":0,"IsReferral":0,"ProfessionalLevel":0,"AreaId":234,"AreaName":"深圳市","AreaPath":",0,19,234,","IsSpecialReferral":0,"SpecialReferralAmount":"0.00","IsHead":0,"DrTitle":null}]
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
         * SpecialistHosGroupId : 0
         * DrId : 1701
         * DrName : 胡海
         * Mobile : 13622485182
         * CredNo : 421124198902080516
         * DrType : 1
         * PharmacistType : 0
         * DrSex : 1
         * DrStatus : 3
         * ReviewNotDesc : null
         * HospitalId : 553
         * HospitalName : 深圳南山医院
         * DepartmentId : 45
         * DepartmentName : 骨外科
         * Position : 7
         * PositionName : 主任技师
         * DoctorGroup :
         * PicturePath : null
         * TreatMent : null
         * DoctorSkill : 哈哈哈哈彼此彼此查查哈哈哈哈嘿嘿和
         * Description : null
         * IsOnline : 1
         * CreateTime : 2020-04-03 11:07:28
         * PraiseRate : 100
         * ConsultCount : 0
         * ResponseRate : 100
         * ConsultVolume : 0
         * OnPrescription : 0
         * VisitCount : 0
         * Telenursing : 0
         * VolumeVideo : 0
         * FetalHeart : 0
         * SericeItemCodes : 1101|图文问诊|0.00|0.00
         * DrServiceItems : [{"SericeItemCode":"1101","SericeItemName":"图文问诊","OriginalPrice":"0.00","Price":"0.00"}]
         * IsPatientMain : false
         * IsAttantDr : false
         * IsHasPack : 0
         * PatientAttentDrNum : 0
         * MedicalRecord : 0
         * MedicalPrice : 0.00
         * InterVolume : 0
         * FaceAppointment : 0
         * PrescriptionNum : 0
         * InspectionNum : 0
         * ManuPatientAttentDrNum : 89
         * ManuInterVolume : 74
         * MDTNum : 0
         * IsOpenPresc : 0
         * OpratePrescDrId : 0
         * OrgId : 1
         * OrgName : 新起点互联网医院
         * IsClinic : 0
         * IsReferral : 0
         * ProfessionalLevel : 0
         * AreaId : 234
         * AreaName : 深圳市
         * AreaPath : ,0,19,234,
         * IsSpecialReferral : 0
         * SpecialReferralAmount : 0.00
         * IsHead : 0
         * DrTitle : null
         */

        private int SpecialistHosGroupId;
        private int DrId;
        private String DrName;
        private String Mobile;
        private String CredNo;
        private int DrType;
        private int PharmacistType;
        private int DrSex;
        private int DrStatus;
        private Object ReviewNotDesc;
        private int HospitalId;
        private String HospitalName;
        private int DepartmentId;
        private String DepartmentName;
        private int Position;
        private String PositionName;
        private String DoctorGroup;
        private Object PicturePath;
        private Object TreatMent;
        private String DoctorSkill;
        private Object Description;
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
        private int MedicalRecord;
        private String MedicalPrice;
        private int InterVolume;
        private int FaceAppointment;
        private int PrescriptionNum;
        private int InspectionNum;
        private int ManuPatientAttentDrNum;
        private int ManuInterVolume;
        private int MDTNum;
        private int IsOpenPresc;
        private int OpratePrescDrId;
        private int OrgId;
        private String OrgName;
        private int IsClinic;
        private int IsReferral;
        private int ProfessionalLevel;
        private int AreaId;
        private String AreaName;
        private String AreaPath;
        private int IsSpecialReferral;
        private String SpecialReferralAmount;
        private int IsHead;
        private Object DrTitle;
        private List<DrServiceItemsBean> DrServiceItems;

        public int getSpecialistHosGroupId() {
            return SpecialistHosGroupId;
        }

        public void setSpecialistHosGroupId(int SpecialistHosGroupId) {
            this.SpecialistHosGroupId = SpecialistHosGroupId;
        }

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

        public String getCredNo() {
            return CredNo;
        }

        public void setCredNo(String CredNo) {
            this.CredNo = CredNo;
        }

        public int getDrType() {
            return DrType;
        }

        public void setDrType(int DrType) {
            this.DrType = DrType;
        }

        public int getPharmacistType() {
            return PharmacistType;
        }

        public void setPharmacistType(int PharmacistType) {
            this.PharmacistType = PharmacistType;
        }

        public int getDrSex() {
            return DrSex;
        }

        public void setDrSex(int DrSex) {
            this.DrSex = DrSex;
        }

        public int getDrStatus() {
            return DrStatus;
        }

        public void setDrStatus(int DrStatus) {
            this.DrStatus = DrStatus;
        }

        public Object getReviewNotDesc() {
            return ReviewNotDesc;
        }

        public void setReviewNotDesc(Object ReviewNotDesc) {
            this.ReviewNotDesc = ReviewNotDesc;
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

        public Object getPicturePath() {
            return PicturePath;
        }

        public void setPicturePath(Object PicturePath) {
            this.PicturePath = PicturePath;
        }

        public Object getTreatMent() {
            return TreatMent;
        }

        public void setTreatMent(Object TreatMent) {
            this.TreatMent = TreatMent;
        }

        public String getDoctorSkill() {
            return DoctorSkill;
        }

        public void setDoctorSkill(String DoctorSkill) {
            this.DoctorSkill = DoctorSkill;
        }

        public Object getDescription() {
            return Description;
        }

        public void setDescription(Object Description) {
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

        public int getMedicalRecord() {
            return MedicalRecord;
        }

        public void setMedicalRecord(int MedicalRecord) {
            this.MedicalRecord = MedicalRecord;
        }

        public String getMedicalPrice() {
            return MedicalPrice;
        }

        public void setMedicalPrice(String MedicalPrice) {
            this.MedicalPrice = MedicalPrice;
        }

        public int getInterVolume() {
            return InterVolume;
        }

        public void setInterVolume(int InterVolume) {
            this.InterVolume = InterVolume;
        }

        public int getFaceAppointment() {
            return FaceAppointment;
        }

        public void setFaceAppointment(int FaceAppointment) {
            this.FaceAppointment = FaceAppointment;
        }

        public int getPrescriptionNum() {
            return PrescriptionNum;
        }

        public void setPrescriptionNum(int PrescriptionNum) {
            this.PrescriptionNum = PrescriptionNum;
        }

        public int getInspectionNum() {
            return InspectionNum;
        }

        public void setInspectionNum(int InspectionNum) {
            this.InspectionNum = InspectionNum;
        }

        public int getManuPatientAttentDrNum() {
            return ManuPatientAttentDrNum;
        }

        public void setManuPatientAttentDrNum(int ManuPatientAttentDrNum) {
            this.ManuPatientAttentDrNum = ManuPatientAttentDrNum;
        }

        public int getManuInterVolume() {
            return ManuInterVolume;
        }

        public void setManuInterVolume(int ManuInterVolume) {
            this.ManuInterVolume = ManuInterVolume;
        }

        public int getMDTNum() {
            return MDTNum;
        }

        public void setMDTNum(int MDTNum) {
            this.MDTNum = MDTNum;
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

        public int getOrgId() {
            return OrgId;
        }

        public void setOrgId(int OrgId) {
            this.OrgId = OrgId;
        }

        public String getOrgName() {
            return OrgName;
        }

        public void setOrgName(String OrgName) {
            this.OrgName = OrgName;
        }

        public int getIsClinic() {
            return IsClinic;
        }

        public void setIsClinic(int IsClinic) {
            this.IsClinic = IsClinic;
        }

        public int getIsReferral() {
            return IsReferral;
        }

        public void setIsReferral(int IsReferral) {
            this.IsReferral = IsReferral;
        }

        public int getProfessionalLevel() {
            return ProfessionalLevel;
        }

        public void setProfessionalLevel(int ProfessionalLevel) {
            this.ProfessionalLevel = ProfessionalLevel;
        }

        public int getAreaId() {
            return AreaId;
        }

        public void setAreaId(int AreaId) {
            this.AreaId = AreaId;
        }

        public String getAreaName() {
            return AreaName;
        }

        public void setAreaName(String AreaName) {
            this.AreaName = AreaName;
        }

        public String getAreaPath() {
            return AreaPath;
        }

        public void setAreaPath(String AreaPath) {
            this.AreaPath = AreaPath;
        }

        public int getIsSpecialReferral() {
            return IsSpecialReferral;
        }

        public void setIsSpecialReferral(int IsSpecialReferral) {
            this.IsSpecialReferral = IsSpecialReferral;
        }

        public String getSpecialReferralAmount() {
            return SpecialReferralAmount;
        }

        public void setSpecialReferralAmount(String SpecialReferralAmount) {
            this.SpecialReferralAmount = SpecialReferralAmount;
        }

        public int getIsHead() {
            return IsHead;
        }

        public void setIsHead(int IsHead) {
            this.IsHead = IsHead;
        }

        public Object getDrTitle() {
            return DrTitle;
        }

        public void setDrTitle(Object DrTitle) {
            this.DrTitle = DrTitle;
        }

        public List<DrServiceItemsBean> getDrServiceItems() {
            return DrServiceItems;
        }

        public void setDrServiceItems(List<DrServiceItemsBean> DrServiceItems) {
            this.DrServiceItems = DrServiceItems;
        }

        public static class DrServiceItemsBean {
            /**
             * SericeItemCode : 1101
             * SericeItemName : 图文问诊
             * OriginalPrice : 0.00
             * Price : 0.00
             */

            private String SericeItemCode;
            private String SericeItemName;
            private String OriginalPrice;
            private String Price;

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

            public String getOriginalPrice() {
                return OriginalPrice;
            }

            public void setOriginalPrice(String OriginalPrice) {
                this.OriginalPrice = OriginalPrice;
            }

            public String getPrice() {
                return Price;
            }

            public void setPrice(String Price) {
                this.Price = Price;
            }
        }
    }
}
