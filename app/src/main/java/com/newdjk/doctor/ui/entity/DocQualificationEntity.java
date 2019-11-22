package com.newdjk.doctor.ui.entity;

import java.util.List;

/**
 * Created by Struggle on 2018/10/13.
 */

public class DocQualificationEntity {


    /**
     * Code : 0
     * Message :
     * Data : [{"DrRegImgId":2324,"DrId":57,"ImgType":1,"ImgPath":"http://resource.newstarthealth.cn/Doctor/201809/2018092806324328.jpg","Number":"99999999","RegisterTime":"2009-09-28 00:00:00","ValidTime":"2038-09-28 00:00:00","SchoolNumber":"","CertifyType":"QT","Invalid":0,"CreateTime":"2018-09-28 18:09:00","UpdateTime":"2018-09-28 18:09:00"},{"DrRegImgId":2325,"DrId":57,"ImgType":3,"ImgPath":"http://resource.newstarthealth.cn/Doctor/201809/2018092806373207.jpg","Number":"99999999","RegisterTime":"2009-09-28 00:00:00","ValidTime":"2038-09-28 00:00:00","SchoolNumber":"","CertifyType":"QT","Invalid":0,"CreateTime":"2018-09-28 18:09:00","UpdateTime":"2018-09-28 18:09:00"},{"DrRegImgId":2326,"DrId":57,"ImgType":4,"ImgPath":"http://resource.newstarthealth.cn/Doctor/201809/2018092808248942.jpg","Number":"99999999","RegisterTime":"2009-09-28 00:00:00","ValidTime":"2038-09-28 00:00:00","SchoolNumber":"","CertifyType":"QT","Invalid":0,"CreateTime":"2018-09-28 18:09:00","UpdateTime":"2018-09-28 18:09:00"},{"DrRegImgId":2327,"DrId":57,"ImgType":4,"ImgPath":"http://resource.newstarthealth.cn/Doctor/201809/2018092808329795.jpg","Number":"99999999","RegisterTime":"2009-09-28 00:00:00","ValidTime":"2038-09-28 00:00:00","SchoolNumber":"","CertifyType":"QT","Invalid":0,"CreateTime":"2018-09-28 18:09:00","UpdateTime":"2018-09-28 18:09:00"}]
     */

    private int Code;
    private String Message;
    private List<DataBean> Data;

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

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * DrRegImgId : 2324
         * DrId : 57
         * ImgType : 1
         * ImgPath : http://resource.newstarthealth.cn/Doctor/201809/2018092806324328.jpg
         * Number : 99999999
         * RegisterTime : 2009-09-28 00:00:00
         * ValidTime : 2038-09-28 00:00:00
         * SchoolNumber :
         * CertifyType : QT
         * Invalid : 0
         * CreateTime : 2018-09-28 18:09:00
         * UpdateTime : 2018-09-28 18:09:00
         */

        private int DrRegImgId;
        private int DrId;
        private int ImgType;
        private String ImgPath;
        private String Number;
        private String RegisterTime;
        private String ValidTime;
        private String SchoolNumber;
        private String CertifyType;
        private int Invalid;
        private String CreateTime;
        private String UpdateTime;

        public int getDrRegImgId() {
            return DrRegImgId;
        }

        public void setDrRegImgId(int DrRegImgId) {
            this.DrRegImgId = DrRegImgId;
        }

        public int getDrId() {
            return DrId;
        }

        public void setDrId(int DrId) {
            this.DrId = DrId;
        }

        public int getImgType() {
            return ImgType;
        }

        public void setImgType(int ImgType) {
            this.ImgType = ImgType;
        }

        public String getImgPath() {
            return ImgPath;
        }

        public void setImgPath(String ImgPath) {
            this.ImgPath = ImgPath;
        }

        public String getNumber() {
            return Number;
        }

        public void setNumber(String Number) {
            this.Number = Number;
        }

        public String getRegisterTime() {
            return RegisterTime;
        }

        public void setRegisterTime(String RegisterTime) {
            this.RegisterTime = RegisterTime;
        }

        public String getValidTime() {
            return ValidTime;
        }

        public void setValidTime(String ValidTime) {
            this.ValidTime = ValidTime;
        }

        public String getSchoolNumber() {
            return SchoolNumber;
        }

        public void setSchoolNumber(String SchoolNumber) {
            this.SchoolNumber = SchoolNumber;
        }

        public String getCertifyType() {
            return CertifyType;
        }

        public void setCertifyType(String CertifyType) {
            this.CertifyType = CertifyType;
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

        @Override
        public String toString() {
            return "DataBean{" +
                    "DrRegImgId=" + DrRegImgId +
                    ", DrId=" + DrId +
                    ", ImgType=" + ImgType +
                    ", ImgPath='" + ImgPath + '\'' +
                    ", Number='" + Number + '\'' +
                    ", RegisterTime='" + RegisterTime + '\'' +
                    ", ValidTime='" + ValidTime + '\'' +
                    ", SchoolNumber='" + SchoolNumber + '\'' +
                    ", CertifyType='" + CertifyType + '\'' +
                    ", Invalid=" + Invalid +
                    ", CreateTime='" + CreateTime + '\'' +
                    ", UpdateTime='" + UpdateTime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DocQualificationEntity{" +
                "Code=" + Code +
                ", Message='" + Message + '\'' +
                ", Data=" + Data +
                '}';
    }
}
