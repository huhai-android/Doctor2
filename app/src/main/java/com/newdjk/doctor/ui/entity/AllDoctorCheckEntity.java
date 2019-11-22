package com.newdjk.doctor.ui.entity;

import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   AllDoctorCheckEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/6 14:48
 *  @描述：
 */
public class AllDoctorCheckEntity {


    /**
     * Total : 1
     * ReturnData : [{"AskId":1,"PatientId":2,"PatientName":"sample string 3","AreaName":"sample string 4","Sex":5,"Age":6,"Replytime":"2018-11-06T13:41:51.5771784+08:00","Result":1,"CreateTime":"2018-11-06T13:41:51.5771784+08:00","IsNormal":false,"Weeks":"孕40周+0天"},{"AskId":1,"PatientId":2,"PatientName":"sample string 3","AreaName":"sample string 4","Sex":5,"Age":6,"Replytime":"2018-11-06T13:41:51.5771784+08:00","Result":1,"CreateTime":"2018-11-06T13:41:51.5771784+08:00","IsNormal":false,"Weeks":"孕40周+0天"}]
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
         * AskId : 1
         * PatientId : 2
         * PatientName : sample string 3
         * AreaName : sample string 4
         * Sex : 5
         * Age : 6
         * Replytime : 2018-11-06T13:41:51.5771784+08:00
         * Result : 1
         * CreateTime : 2018-11-06T13:41:51.5771784+08:00
         * IsNormal : false
         * Weeks : 孕40周+0天
         */

        private int AskId;
        private int PatientId;
        private String PatientName;
        private String AreaName;
        private int Sex;
        private int Age;
        private String Replytime;
        private int Result;
        private String CreateTime;
        private boolean IsNormal;
        private String Weeks;
        private String PicturePath;

        public String getPicturePath() {
            return PicturePath;
        }

        public void setPicturePath(String picturePath) {
            PicturePath = picturePath;
        }

        public int getAskId() {
            return AskId;
        }

        public void setAskId(int AskId) {
            this.AskId = AskId;
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

        public String getAreaName() {
            return AreaName;
        }

        public void setAreaName(String AreaName) {
            this.AreaName = AreaName;
        }

        public int getSex() {
            return Sex;
        }

        public void setSex(int Sex) {
            this.Sex = Sex;
        }

        public int getAge() {
            return Age;
        }

        public void setAge(int Age) {
            this.Age = Age;
        }

        public String getReplytime() {
            return Replytime;
        }

        public void setReplytime(String Replytime) {
            this.Replytime = Replytime;
        }

        public int getResult() {
            return Result;
        }

        public void setResult(int Result) {
            this.Result = Result;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public boolean isIsNormal() {
            return IsNormal;
        }

        public void setIsNormal(boolean IsNormal) {
            this.IsNormal = IsNormal;
        }

        public String getWeeks() {
            return Weeks;
        }

        public void setWeeks(String Weeks) {
            this.Weeks = Weeks;
        }

        @Override
        public String toString() {
            return "ReturnDataBean{" +
                    "AskId=" + AskId +
                    ", PatientId=" + PatientId +
                    ", PatientName='" + PatientName + '\'' +
                    ", AreaName='" + AreaName + '\'' +
                    ", Sex=" + Sex +
                    ", Age=" + Age +
                    ", Replytime='" + Replytime + '\'' +
                    ", Result=" + Result +
                    ", CreateTime='" + CreateTime + '\'' +
                    ", IsNormal=" + IsNormal +
                    ", Weeks='" + Weeks + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AllDoctorCheckEntity{" +
                "Total=" + Total +
                ", ReturnData=" + ReturnData +
                '}';
    }
}
