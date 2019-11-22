package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   ConSultEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/4/25 17:24
 *  @描述：
 */
public class ConSultEntity {

    /**
     * PatientIMId : pat_774
     * PatientInfo : {"AccountId":774,"Age":"21岁","Birthday":"1998-03-27 00:00:00","PatientHeadImgUrl":"http://userImage.newstarthealth.cn/pat/0/1179.jpg","PatientId":1179,"PatientName":"复古","PatientSex":2}
     * RecordInfo : {"Appeal":"哈哈哈哈哈哈爱别个呵呵给的给弟弟","ApplicantId":774,"CreateTime":"2019-04-25 17:23:10","DealWithTime":"","DoctorId":131,"DoctorName":"马超","EndTime":"","Id":945,"Method":0,"OrgId":1,"PatientId":1179,"PatientName":"复古","PayStatus":2,"PayTime":"2019-04-25 17:23:12","PrescribeStatus":0,"PrescriptionId":0,"StartMethod":0,"StartTime":"","Status":0,"Type":3}
     */

    private String PatientIMId;
    private PatientInfoBean PatientInfo;
    private RecordInfoBean RecordInfo;

    public String getPatientIMId() {
        return PatientIMId;
    }

    public void setPatientIMId(String PatientIMId) {
        this.PatientIMId = PatientIMId;
    }

    public PatientInfoBean getPatientInfo() {
        return PatientInfo;
    }

    public void setPatientInfo(PatientInfoBean PatientInfo) {
        this.PatientInfo = PatientInfo;
    }

    public RecordInfoBean getRecordInfo() {
        return RecordInfo;
    }

    public void setRecordInfo(RecordInfoBean RecordInfo) {
        this.RecordInfo = RecordInfo;
    }

    public static class PatientInfoBean {
        /**
         * AccountId : 774
         * Age : 21岁
         * Birthday : 1998-03-27 00:00:00
         * PatientHeadImgUrl : http://userImage.newstarthealth.cn/pat/0/1179.jpg
         * PatientId : 1179
         * PatientName : 复古
         * PatientSex : 2
         */

        private int AccountId;
        private String Age;
        private String Birthday;
        private String PatientHeadImgUrl;
        private int PatientId;
        private String PatientName;
        private int PatientSex;

        public int getAccountId() {
            return AccountId;
        }

        public void setAccountId(int AccountId) {
            this.AccountId = AccountId;
        }

        public String getAge() {
            return Age;
        }

        public void setAge(String Age) {
            this.Age = Age;
        }

        public String getBirthday() {
            return Birthday;
        }

        public void setBirthday(String Birthday) {
            this.Birthday = Birthday;
        }

        public String getPatientHeadImgUrl() {
            return PatientHeadImgUrl;
        }

        public void setPatientHeadImgUrl(String PatientHeadImgUrl) {
            this.PatientHeadImgUrl = PatientHeadImgUrl;
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

        public int getPatientSex() {
            return PatientSex;
        }

        public void setPatientSex(int PatientSex) {
            this.PatientSex = PatientSex;
        }
    }

    public static class RecordInfoBean {
        /**
         * Appeal : 哈哈哈哈哈哈爱别个呵呵给的给弟弟
         * ApplicantId : 774
         * CreateTime : 2019-04-25 17:23:10
         * DealWithTime :
         * DoctorId : 131
         * DoctorName : 马超
         * EndTime :
         * Id : 945
         * Method : 0
         * OrgId : 1
         * PatientId : 1179
         * PatientName : 复古
         * PayStatus : 2
         * PayTime : 2019-04-25 17:23:12
         * PrescribeStatus : 0
         * PrescriptionId : 0
         * StartMethod : 0
         * StartTime :
         * Status : 0
         * Type : 3
         */

        private String Appeal;
        private int ApplicantId;
        private String CreateTime;
        private String DealWithTime;
        private int DoctorId;
        private String DoctorName;
        private String EndTime;
        private int Id;
        private int Method;
        private int OrgId;
        private int PatientId;
        private String PatientName;
        private int PayStatus;
        private String PayTime;
        private int PrescribeStatus;
        private int PrescriptionId;
        private int StartMethod;
        private String StartTime;
        private int Status;
        private int Type;

        public String getAppeal() {
            return Appeal;
        }

        public void setAppeal(String Appeal) {
            this.Appeal = Appeal;
        }

        public int getApplicantId() {
            return ApplicantId;
        }

        public void setApplicantId(int ApplicantId) {
            this.ApplicantId = ApplicantId;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getDealWithTime() {
            return DealWithTime;
        }

        public void setDealWithTime(String DealWithTime) {
            this.DealWithTime = DealWithTime;
        }

        public int getDoctorId() {
            return DoctorId;
        }

        public void setDoctorId(int DoctorId) {
            this.DoctorId = DoctorId;
        }

        public String getDoctorName() {
            return DoctorName;
        }

        public void setDoctorName(String DoctorName) {
            this.DoctorName = DoctorName;
        }

        public String getEndTime() {
            return EndTime;
        }

        public void setEndTime(String EndTime) {
            this.EndTime = EndTime;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getMethod() {
            return Method;
        }

        public void setMethod(int Method) {
            this.Method = Method;
        }

        public int getOrgId() {
            return OrgId;
        }

        public void setOrgId(int OrgId) {
            this.OrgId = OrgId;
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

        public int getPayStatus() {
            return PayStatus;
        }

        public void setPayStatus(int PayStatus) {
            this.PayStatus = PayStatus;
        }

        public String getPayTime() {
            return PayTime;
        }

        public void setPayTime(String PayTime) {
            this.PayTime = PayTime;
        }

        public int getPrescribeStatus() {
            return PrescribeStatus;
        }

        public void setPrescribeStatus(int PrescribeStatus) {
            this.PrescribeStatus = PrescribeStatus;
        }

        public int getPrescriptionId() {
            return PrescriptionId;
        }

        public void setPrescriptionId(int PrescriptionId) {
            this.PrescriptionId = PrescriptionId;
        }

        public int getStartMethod() {
            return StartMethod;
        }

        public void setStartMethod(int StartMethod) {
            this.StartMethod = StartMethod;
        }

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String StartTime) {
            this.StartTime = StartTime;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }
    }
}
