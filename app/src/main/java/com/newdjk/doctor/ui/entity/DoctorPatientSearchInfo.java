package com.newdjk.doctor.ui.entity;

import java.util.List;

/**
 * Created by Struggle on 2018/10/11.
 */

public class DoctorPatientSearchInfo {


    /**
     * Code : 0
     * Message :
     * Data : {"Total":1,"ReturnData":[{"DrPatientId":52,"DrId":57,"DrName":"奥巴马","DrPicturePath":"http://userimage.newstarthealth.cn/doc/0/57.jpg?dt=2018/10/11 14:39:49","PatientId":40,"PatientName":"等等","NameLetter":"DD","PatientSex":1,"Birthday":"1991-09-22 00:00:00","Age":"27","PaPicturePath":null,"RelationStatus":1,"IsDrKey":0,"IsPatientMain":1,"Disease":null,"CreateTime":"2018-09-29 21:01:18","UpdateTime":"2018-09-29 21:01:18"}]}
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
         * Total : 1
         * ReturnData : [{"DrPatientId":52,"DrId":57,"DrName":"奥巴马","DrPicturePath":"http://userimage.newstarthealth.cn/doc/0/57.jpg?dt=2018/10/11 14:39:49","PatientId":40,"PatientName":"等等","NameLetter":"DD","PatientSex":1,"Birthday":"1991-09-22 00:00:00","Age":"27","PaPicturePath":null,"RelationStatus":1,"IsDrKey":0,"IsPatientMain":1,"Disease":null,"CreateTime":"2018-09-29 21:01:18","UpdateTime":"2018-09-29 21:01:18"}]
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
             * DrPatientId : 52
             * DrId : 57
             * DrName : 奥巴马
             * DrPicturePath : http://userimage.newstarthealth.cn/doc/0/57.jpg?dt=2018/10/11 14:39:49
             * PatientId : 40
             * PatientName : 等等
             * NameLetter : DD
             * PatientSex : 1
             * Birthday : 1991-09-22 00:00:00
             * Age : 27
             * PaPicturePath : null
             * RelationStatus : 1
             * IsDrKey : 0
             * IsPatientMain : 1
             * Disease : null
             * CreateTime : 2018-09-29 21:01:18
             * UpdateTime : 2018-09-29 21:01:18
             */

            private int DrPatientId;
            private int DrId;
            private String DrName;
            private String DrPicturePath;
            private int PatientId;
            private String PatientName;
            private String NameLetter;
            private int PatientSex;
            private String Birthday;
            private String Age;
            private Object PaPicturePath;
            private int RelationStatus;
            private int IsDrKey;
            private int IsPatientMain;
            private Object Disease;
            private String CreateTime;
            private String UpdateTime;

            public int getDrPatientId() {
                return DrPatientId;
            }

            public void setDrPatientId(int DrPatientId) {
                this.DrPatientId = DrPatientId;
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

            public String getDrPicturePath() {
                return DrPicturePath;
            }

            public void setDrPicturePath(String DrPicturePath) {
                this.DrPicturePath = DrPicturePath;
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

            public String getNameLetter() {
                return NameLetter;
            }

            public void setNameLetter(String NameLetter) {
                this.NameLetter = NameLetter;
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

            public Object getPaPicturePath() {
                return PaPicturePath;
            }

            public void setPaPicturePath(Object PaPicturePath) {
                this.PaPicturePath = PaPicturePath;
            }

            public int getRelationStatus() {
                return RelationStatus;
            }

            public void setRelationStatus(int RelationStatus) {
                this.RelationStatus = RelationStatus;
            }

            public int getIsDrKey() {
                return IsDrKey;
            }

            public void setIsDrKey(int IsDrKey) {
                this.IsDrKey = IsDrKey;
            }

            public int getIsPatientMain() {
                return IsPatientMain;
            }

            public void setIsPatientMain(int IsPatientMain) {
                this.IsPatientMain = IsPatientMain;
            }

            public Object getDisease() {
                return Disease;
            }

            public void setDisease(Object Disease) {
                this.Disease = Disease;
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
        }
    }
}
