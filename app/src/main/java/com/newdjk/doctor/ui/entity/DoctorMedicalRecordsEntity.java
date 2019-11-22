package com.newdjk.doctor.ui.entity;

import java.util.List;

public class DoctorMedicalRecordsEntity {

    /**
     * Total : 1
     * ReturnData : [{"MedicalRecordId":1,"DrId":2,"MedicalUnitId":3,"PatientId":4,"PatientName":"sample string 5","PatientPic":"sample string 6","Sex":"sample string 7","Age":"sample string 8","Description":"sample string 9","PayOrderTime":"2018-12-20T15:53:03.5710505+08:00"},{"MedicalRecordId":1,"DrId":2,"MedicalUnitId":3,"PatientId":4,"PatientName":"sample string 5","PatientPic":"sample string 6","Sex":"sample string 7","Age":"sample string 8","Description":"sample string 9","PayOrderTime":"2018-12-20T15:53:03.5710505+08:00"}]
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
         * MedicalRecordId : 1
         * DrId : 2
         * MedicalUnitId : 3
         * PatientId : 4
         * PatientName : sample string 5
         * PatientPic : sample string 6
         * Sex : sample string 7
         * Age : sample string 8
         * Description : sample string 9
         * PayOrderTime : 2018-12-20T15:53:03.5710505+08:00
         */

        private int MedicalRecordId;
        private int DrId;
        private int MedicalUnitId;
        private int PatientId;
        private String PatientName;
        private String PatientPic;
        private String Sex;
        private String Age;
        private String Description;
        private String PayOrderTime;
        private int UnReadNum;

        public int getMedicalStatus() {
            return MedicalStatus;
        }

        public void setMedicalStatus(int medicalStatus) {
            MedicalStatus = medicalStatus;
        }

        private int MedicalStatus;
        public int getMedicalRecordId() {
            return MedicalRecordId;
        }

        public void setMedicalRecordId(int MedicalRecordId) {
            this.MedicalRecordId = MedicalRecordId;
        }

        public int getDrId() {
            return DrId;
        }

        public void setDrId(int DrId) {
            this.DrId = DrId;
        }

        public int getMedicalUnitId() {
            return MedicalUnitId;
        }

        public void setMedicalUnitId(int MedicalUnitId) {
            this.MedicalUnitId = MedicalUnitId;
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

        public String getPatientPic() {
            return PatientPic;
        }

        public void setPatientPic(String PatientPic) {
            this.PatientPic = PatientPic;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String Sex) {
            this.Sex = Sex;
        }

        public String getAge() {
            return Age;
        }

        public void setAge(String Age) {
            this.Age = Age;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public String getPayOrderTime() {
            return PayOrderTime;
        }

        public void setPayOrderTime(String PayOrderTime) {
            this.PayOrderTime = PayOrderTime;
        }

        public int getUnReadNum() {
            return UnReadNum;
        }

        public void setUnReadNum(int unReadNum) {
            UnReadNum = unReadNum;
        }

        @Override
        public String toString() {
            return "ReturnDataBean{" +
                    "MedicalRecordId=" + MedicalRecordId +
                    ", DrId=" + DrId +
                    ", MedicalUnitId=" + MedicalUnitId +
                    ", PatientId=" + PatientId +
                    ", PatientName='" + PatientName + '\'' +
                    ", PatientPic='" + PatientPic + '\'' +
                    ", Sex='" + Sex + '\'' +
                    ", Age='" + Age + '\'' +
                    ", Description='" + Description + '\'' +
                    ", PayOrderTime='" + PayOrderTime + '\'' +
                    ", UnReadNum=" + UnReadNum +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DoctorMedicalRecordsEntity{" +
                "Total=" + Total +
                ", ReturnData=" + ReturnData +
                '}';
    }
}
