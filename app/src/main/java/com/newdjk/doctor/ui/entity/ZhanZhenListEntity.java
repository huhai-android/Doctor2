package com.newdjk.doctor.ui.entity;

import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   ZhanZhenListEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/3/18 14:26
 *  @描述：
 */
public class ZhanZhenListEntity {

    /**
     * Total : 7
     * ReturnData : [{"ReferralRecordId":1,"DoctorDisGroupId":0,"DisGroupId":16,"DisGroupName":"高血压","FormDrId":0,"FromDrName":"周星驰","ToDrId":68,"ToDrName":"刘德华","ReferralRemark":"发给他","PatientId":16,"PatientName":"HUU","ReferralStatus":1,"ReferralTime":"2019-03-16 14:51:31","ReceiveTime":null,"ReferralQRCode":"http://resource.newstarthealth.cn/Doctor/ReferralQRCode/1.png","ReferralType":1,"PatientSex":2,"Birthday":"1993-04-27 00:00:00","Age":"25岁","PicturePath":"http://userimage.newstarthealth.cn/pat/0/16.jpg?dt=201903182527790"},{"ReferralRecordId":2,"DoctorDisGroupId":0,"DisGroupId":16,"DisGroupName":"高血压","FormDrId":0,"FromDrName":"周星驰","ToDrId":68,"ToDrName":"刘德华","ReferralRemark":"发给他","PatientId":16,"PatientName":"HUU","ReferralStatus":1,"ReferralTime":"2019-03-16 14:51:31","ReceiveTime":null,"ReferralQRCode":"http://resource.newstarthealth.cn/Doctor/ReferralQRCode/2.png","ReferralType":1,"PatientSex":2,"Birthday":"1993-04-27 00:00:00","Age":"25岁","PicturePath":"http://userimage.newstarthealth.cn/pat/0/16.jpg?dt=201903182527790"},{"ReferralRecordId":3,"DoctorDisGroupId":0,"DisGroupId":16,"DisGroupName":"高血压","FormDrId":0,"FromDrName":"周星驰","ToDrId":68,"ToDrName":"刘德华","ReferralRemark":"发给他","PatientId":16,"PatientName":"HUU","ReferralStatus":1,"ReferralTime":"2019-03-16 14:51:31","ReceiveTime":null,"ReferralQRCode":"http://resource.newstarthealth.cn/Doctor/ReferralQRCode/3.png","ReferralType":1,"PatientSex":2,"Birthday":"1993-04-27 00:00:00","Age":"25岁","PicturePath":"http://userimage.newstarthealth.cn/pat/0/16.jpg?dt=201903182527790"},{"ReferralRecordId":4,"DoctorDisGroupId":0,"DisGroupId":16,"DisGroupName":"高血压","FormDrId":0,"FromDrName":"周星驰","ToDrId":68,"ToDrName":"刘德华","ReferralRemark":"发给他","PatientId":16,"PatientName":"HUU","ReferralStatus":1,"ReferralTime":"2019-03-16 14:51:31","ReceiveTime":null,"ReferralQRCode":"http://resource.newstarthealth.cn/Doctor/ReferralQRCode/4.png","ReferralType":1,"PatientSex":2,"Birthday":"1993-04-27 00:00:00","Age":"25岁","PicturePath":"http://userimage.newstarthealth.cn/pat/0/16.jpg?dt=201903182527790"},{"ReferralRecordId":5,"DoctorDisGroupId":0,"DisGroupId":16,"DisGroupName":"高血压","FormDrId":0,"FromDrName":"周星驰","ToDrId":68,"ToDrName":"刘德华","ReferralRemark":"发给他","PatientId":16,"PatientName":"HUU","ReferralStatus":1,"ReferralTime":"2019-03-16 14:51:31","ReceiveTime":null,"ReferralQRCode":"http://resource.newstarthealth.cn/Doctor/ReferralQRCode/5.png","ReferralType":1,"PatientSex":2,"Birthday":"1993-04-27 00:00:00","Age":"25岁","PicturePath":"http://userimage.newstarthealth.cn/pat/0/16.jpg?dt=201903182527790"},{"ReferralRecordId":6,"DoctorDisGroupId":0,"DisGroupId":16,"DisGroupName":"高血压","FormDrId":0,"FromDrName":"周星驰","ToDrId":68,"ToDrName":"刘德华","ReferralRemark":null,"PatientId":16,"PatientName":"HUU","ReferralStatus":1,"ReferralTime":"2019-03-16 14:52:05","ReceiveTime":null,"ReferralQRCode":"http://resource.newstarthealth.cn/Doctor/ReferralQRCode/6.png","ReferralType":1,"PatientSex":2,"Birthday":"1993-04-27 00:00:00","Age":"25岁","PicturePath":"http://userimage.newstarthealth.cn/pat/0/16.jpg?dt=201903182527790"},{"ReferralRecordId":7,"DoctorDisGroupId":0,"DisGroupId":16,"DisGroupName":"高血压","FormDrId":0,"FromDrName":"周星驰","ToDrId":68,"ToDrName":"刘德华","ReferralRemark":null,"PatientId":16,"PatientName":"HUU","ReferralStatus":1,"ReferralTime":"2019-03-16 16:07:09","ReceiveTime":null,"ReferralQRCode":"http://resource.newstarthealth.cn/Doctor/ReferralQRCode/7.png","ReferralType":1,"PatientSex":2,"Birthday":"1993-04-27 00:00:00","Age":"25岁","PicturePath":"http://userimage.newstarthealth.cn/pat/0/16.jpg?dt=201903182527790"}]
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
         * ReferralRecordId : 1
         * DoctorDisGroupId : 0
         * DisGroupId : 16
         * DisGroupName : 高血压
         * FormDrId : 0
         * FromDrName : 周星驰
         * ToDrId : 68
         * ToDrName : 刘德华
         * ReferralRemark : 发给他
         * PatientId : 16
         * PatientName : HUU
         * ReferralStatus : 1
         * ReferralTime : 2019-03-16 14:51:31
         * ReceiveTime : null
         * ReferralQRCode : http://resource.newstarthealth.cn/Doctor/ReferralQRCode/1.png
         * ReferralType : 1
         * PatientSex : 2
         * Birthday : 1993-04-27 00:00:00
         * Age : 25岁
         * PicturePath : http://userimage.newstarthealth.cn/pat/0/16.jpg?dt=201903182527790
         */



        private int ReferralRecordId;
        private int DoctorDisGroupId;
        private int DisGroupId;
        private String DisGroupName;
        private int FormDrId;
        private String FromDrName;
        private int ToDrId;
        private String ToDrName;
        private String ReferralRemark;
        private int PatientId;
        private String PatientName;
        private int ReferralStatus;
        private String ReferralTime;
        private String ReceiveTime;
        private String ReferralQRCode;
        private int ReferralType;
        private int PatientSex;
        private String Birthday;
        private String Age;
        private String PicturePath;
        private int IsReturnBack;
        private int ReferralClass;

        public int getReferralClass() {
            return ReferralClass;
        }

        public void setReferralClass(int referralClass) {
            ReferralClass = referralClass;
        }

        public int getIsReturnBack() {
            return IsReturnBack;
        }

        public void setIsReturnBack(int isReturnBack) {
            IsReturnBack = isReturnBack;
        }

        public int getReferralRecordId() {
            return ReferralRecordId;
        }

        public void setReferralRecordId(int ReferralRecordId) {
            this.ReferralRecordId = ReferralRecordId;
        }

        public int getDoctorDisGroupId() {
            return DoctorDisGroupId;
        }

        public void setDoctorDisGroupId(int DoctorDisGroupId) {
            this.DoctorDisGroupId = DoctorDisGroupId;
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

        public int getFormDrId() {
            return FormDrId;
        }

        public void setFormDrId(int FormDrId) {
            this.FormDrId = FormDrId;
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

        public String getReferralQRCode() {
            return ReferralQRCode;
        }

        public void setReferralQRCode(String ReferralQRCode) {
            this.ReferralQRCode = ReferralQRCode;
        }

        public int getReferralType() {
            return ReferralType;
        }

        public void setReferralType(int ReferralType) {
            this.ReferralType = ReferralType;
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

        public String getPicturePath() {
            return PicturePath;
        }

        public void setPicturePath(String PicturePath) {
            this.PicturePath = PicturePath;
        }
    }
}
