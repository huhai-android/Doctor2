package com.newdjk.doctor.ui.entity;

import java.util.List;

/**
 * Created by user on 2018/4/17.
 */

public class DoctorIdEntity {


    /**
     * Code : 0
     * Message :
     * Data : {"DrId":57,"DrName":"奥巴马","Mobile":"13622485183","DrType":1,"DrSex":1,"DrStatus":1,"HospitalId":6633,"HospitalName":"其他","DepartmentId":0,"DepartmentName":"外科","Position":1,"PositionName":"主任医师","DoctorGroup":"","PicturePath":"http://userimage.newstarthealth.cn/doc/0/57.jpg?dt=201901150006444","TreatMent":"就看看，糖尿病糖尿病糖尿病糖尿病糖尿病","DoctorSkill":"叫姐姐啊迦南男男女女扭扭捏捏女娲娘娘你你没上课上课开始快上课考砸卡卡真卡手机手机是十几集手机手机手机叫撒看着看看书玛莎妮娜你奶奶那你忙你那就看李莉莉咯空哦⊙∀⊙！OK空了空了聊了看看空空快快快空哦哦搜搜外网哦我哦去我去青","Description":"滴！哒哒","IsOnline":1,"CreateTime":"2018-09-17 15:29:04","PraiseRate":98,"ConsultCount":0,"ResponseRate":70,"ConsultVolume":160,"OnPrescription":53,"VisitCount":0,"Telenursing":0,"VolumeVideo":8,"FetalHeart":0,"SericeItemCodes":"1101|图文问诊|1.00|0.00,1102|视频问诊|6.00|0.01,1103|名医续方|10.00|0.01,1110|第二诊疗意见|1000.00|0.00","DrServiceItems":[{"SericeItemCode":"1101","SericeItemName":"图文问诊","OriginalPrice":"1.00","Price":"0.00"},{"SericeItemCode":"1102","SericeItemName":"视频问诊","OriginalPrice":"6.00","Price":"0.01"},{"SericeItemCode":"1103","SericeItemName":"名医续方","OriginalPrice":"10.00","Price":"0.01"},{"SericeItemCode":"1110","SericeItemName":"第二诊疗意见","OriginalPrice":"1000.00","Price":"0.00"}],"IsPatientMain":false,"IsAttantDr":false,"IsHasPack":1,"PatientAttentDrNum":60,"MedicalRecord":0,"MedicalPrice":"0.00","InterVolume":221}
     */

    private int Code;
    private String Message;
    private DataBean Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * DrId : 57
         * DrName : 奥巴马
         * Mobile : 13622485183
         * DrType : 1
         * DrSex : 1
         * DrStatus : 1
         * HospitalId : 6633
         * HospitalName : 其他
         * DepartmentId : 0
         * DepartmentName : 外科
         * Position : 1
         * PositionName : 主任医师
         * DoctorGroup :
         * PicturePath : http://userimage.newstarthealth.cn/doc/0/57.jpg?dt=201901150006444
         * TreatMent : 就看看，糖尿病糖尿病糖尿病糖尿病糖尿病
         * DoctorSkill : 叫姐姐啊迦南男男女女扭扭捏捏女娲娘娘你你没上课上课开始快上课考砸卡卡真卡手机手机是十几集手机手机手机叫撒看着看看书玛莎妮娜你奶奶那你忙你那就看李莉莉咯空哦⊙∀⊙！OK空了空了聊了看看空空快快快空哦哦搜搜外网哦我哦去我去青
         * Description : 滴！哒哒
         * IsOnline : 1
         * CreateTime : 2018-09-17 15:29:04
         * PraiseRate : 98
         * ConsultCount : 0
         * ResponseRate : 70
         * ConsultVolume : 160
         * OnPrescription : 53
         * VisitCount : 0
         * Telenursing : 0
         * VolumeVideo : 8
         * FetalHeart : 0
         * SericeItemCodes : 1101|图文问诊|1.00|0.00,1102|视频问诊|6.00|0.01,1103|名医续方|10.00|0.01,1110|第二诊疗意见|1000.00|0.00
         * DrServiceItems : [{"SericeItemCode":"1101","SericeItemName":"图文问诊","OriginalPrice":"1.00","Price":"0.00"},{"SericeItemCode":"1102","SericeItemName":"视频问诊","OriginalPrice":"6.00","Price":"0.01"},{"SericeItemCode":"1103","SericeItemName":"名医续方","OriginalPrice":"10.00","Price":"0.01"},{"SericeItemCode":"1110","SericeItemName":"第二诊疗意见","OriginalPrice":"1000.00","Price":"0.00"}]
         * IsPatientMain : false
         * IsAttantDr : false
         * IsHasPack : 1
         * PatientAttentDrNum : 60
         * MedicalRecord : 0
         * MedicalPrice : 0.00
         * InterVolume : 221
         */

        private int DrId;
        private String DrName;
        private String Mobile;
        private int DrType;
        private int DrSex;
        private int DrStatus;
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
        private int MedicalRecord;
        private String MedicalPrice;
        private int InterVolume;
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

        public int getDrStatus() {
            return DrStatus;
        }

        public void setDrStatus(int DrStatus) {
            this.DrStatus = DrStatus;
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
             * OriginalPrice : 1.00
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
