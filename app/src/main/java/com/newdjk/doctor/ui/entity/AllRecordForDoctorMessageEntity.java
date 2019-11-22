package com.newdjk.doctor.ui.entity;

import java.util.List;

public class AllRecordForDoctorMessageEntity {

    /**
     * ConsultInfoList : [{"RecordInfo":{"Id":1,"Content":"sample string 2","Type":64,"PatientId":4,"PatientName":"sample string 5","ApplicantId":6,"DoctorId":7,"DoctorName":"sample string 8","OrgId":9,"Status":64,"DealWithTime":"2018-11-13T16:37:25.2597681+08:00","StartTime":"2018-11-13T16:37:25.2597681+08:00","EndTime":"2018-11-13T16:37:25.2597681+08:00","PayStatus":64,"PayTime":"2018-11-13T16:37:25.2597681+08:00","CreateTime":"2018-11-13T16:37:25.2597681+08:00"},"PatientInfo":{"PatientId":1,"AccountId":2,"PatientName":"sample string 3","PatientSex":64,"Birthday":"2018-11-13T16:37:25.2607682+08:00","AreaName":"sample string 6","Age":"1天","PatientHeadImgUrl":"http://172.18.30.4:8006/pat/0/1.jpg"},"DoctorPatientRelation":{"DrPatientId":1,"DrId":2,"DrName":"sample string 3","PatientId":4,"PatientName":"sample string 5","RelationStatus":64,"IsDrKey":64,"IsPatientMain":64,"Disease":"sample string 9"},"PatientIMId":"sample string 1"},{"RecordInfo":{"Id":1,"Content":"sample string 2","Type":64,"PatientId":4,"PatientName":"sample string 5","ApplicantId":6,"DoctorId":7,"DoctorName":"sample string 8","OrgId":9,"Status":64,"DealWithTime":"2018-11-13T16:37:25.2597681+08:00","StartTime":"2018-11-13T16:37:25.2597681+08:00","EndTime":"2018-11-13T16:37:25.2597681+08:00","PayStatus":64,"PayTime":"2018-11-13T16:37:25.2597681+08:00","CreateTime":"2018-11-13T16:37:25.2597681+08:00"},"PatientInfo":{"PatientId":1,"AccountId":2,"PatientName":"sample string 3","PatientSex":64,"Birthday":"2018-11-13T16:37:25.2607682+08:00","AreaName":"sample string 6","Age":"1天","PatientHeadImgUrl":"http://172.18.30.4:8006/pat/0/1.jpg"},"DoctorPatientRelation":{"DrPatientId":1,"DrId":2,"DrName":"sample string 3","PatientId":4,"PatientName":"sample string 5","RelationStatus":64,"IsDrKey":64,"IsPatientMain":64,"Disease":"sample string 9"},"PatientIMId":"sample string 1"}]
     * InquiryInfoList : [{"RecordInfo":{"Id":1,"Content":"sample string 2","Type":64,"PatientId":4,"PatientName":"sample string 5","ApplicantId":6,"DoctorId":7,"DoctorName":"sample string 8","OrgId":9,"Status":64,"DealWithTime":"2018-11-13T16:37:25.2627684+08:00","StartTime":"2018-11-13T16:37:25.2637685+08:00","EndTime":"2018-11-13T16:37:25.2637685+08:00","PayStatus":64,"PayTime":"2018-11-13T16:37:25.2637685+08:00","CreateTime":"2018-11-13T16:37:25.2637685+08:00","ReExaminationDate":"2018-11-13T16:37:25.2637685+08:00","ReExaminationStartTime":"00:00:00.1234567","ReExaminationEndTime":"00:00:00.1234567"},"PatientInfo":{"PatientId":1,"AccountId":2,"PatientName":"sample string 3","PatientSex":64,"Birthday":"2018-11-13T16:37:25.2607682+08:00","AreaName":"sample string 6","Age":"1天","PatientHeadImgUrl":"http://172.18.30.4:8006/pat/0/1.jpg"},"DoctorPatientRelation":{"DrPatientId":1,"DrId":2,"DrName":"sample string 3","PatientId":4,"PatientName":"sample string 5","RelationStatus":64,"IsDrKey":64,"IsPatientMain":64,"Disease":"sample string 9"},"PatientIMId":"sample string 1"},{"RecordInfo":{"Id":1,"Content":"sample string 2","Type":64,"PatientId":4,"PatientName":"sample string 5","ApplicantId":6,"DoctorId":7,"DoctorName":"sample string 8","OrgId":9,"Status":64,"DealWithTime":"2018-11-13T16:37:25.2627684+08:00","StartTime":"2018-11-13T16:37:25.2637685+08:00","EndTime":"2018-11-13T16:37:25.2637685+08:00","PayStatus":64,"PayTime":"2018-11-13T16:37:25.2637685+08:00","CreateTime":"2018-11-13T16:37:25.2637685+08:00","ReExaminationDate":"2018-11-13T16:37:25.2637685+08:00","ReExaminationStartTime":"00:00:00.1234567","ReExaminationEndTime":"00:00:00.1234567"},"PatientInfo":{"PatientId":1,"AccountId":2,"PatientName":"sample string 3","PatientSex":64,"Birthday":"2018-11-13T16:37:25.2607682+08:00","AreaName":"sample string 6","Age":"1天","PatientHeadImgUrl":"http://172.18.30.4:8006/pat/0/1.jpg"},"DoctorPatientRelation":{"DrPatientId":1,"DrId":2,"DrName":"sample string 3","PatientId":4,"PatientName":"sample string 5","RelationStatus":64,"IsDrKey":64,"IsPatientMain":64,"Disease":"sample string 9"},"PatientIMId":"sample string 1"}]
     * ApplyInfoList : [{"RecordInfo":{"Id":1,"Type":64,"PrescriptionId":1,"Diagnoses":"sample string 3","Method":64,"Appeal":"sample string 5","AllergicHistory":"sample string 6","PatientId":7,"PatientName":"sample string 8","ApplicantId":9,"DoctorId":10,"DoctorName":"sample string 11","OrgId":12,"StartMethod":64,"Status":64,"DealWithTime":"2018-11-13T16:37:25.2667688+08:00","StartTime":"2018-11-13T16:37:25.2667688+08:00","EndTime":"2018-11-13T16:37:25.2667688+08:00","PrescribeStatus":64,"PayStatus":64,"PayTime":"2018-11-13T16:37:25.2667688+08:00","CreateTime":"2018-11-13T16:37:25.2677689+08:00"},"PatientInfo":{"PatientId":1,"AccountId":2,"PatientName":"sample string 3","PatientSex":64,"Birthday":"2018-11-13T16:37:25.2607682+08:00","AreaName":"sample string 6","Age":"1天","PatientHeadImgUrl":"http://172.18.30.4:8006/pat/0/1.jpg"},"DoctorPatientRelation":{"DrPatientId":1,"DrId":2,"DrName":"sample string 3","PatientId":4,"PatientName":"sample string 5","RelationStatus":64,"IsDrKey":64,"IsPatientMain":64,"Disease":"sample string 9"},"PatientIMId":"sample string 1"},{"RecordInfo":{"Id":1,"Type":64,"PrescriptionId":1,"Diagnoses":"sample string 3","Method":64,"Appeal":"sample string 5","AllergicHistory":"sample string 6","PatientId":7,"PatientName":"sample string 8","ApplicantId":9,"DoctorId":10,"DoctorName":"sample string 11","OrgId":12,"StartMethod":64,"Status":64,"DealWithTime":"2018-11-13T16:37:25.2667688+08:00","StartTime":"2018-11-13T16:37:25.2667688+08:00","EndTime":"2018-11-13T16:37:25.2667688+08:00","PrescribeStatus":64,"PayStatus":64,"PayTime":"2018-11-13T16:37:25.2667688+08:00","CreateTime":"2018-11-13T16:37:25.2677689+08:00"},"PatientInfo":{"PatientId":1,"AccountId":2,"PatientName":"sample string 3","PatientSex":64,"Birthday":"2018-11-13T16:37:25.2607682+08:00","AreaName":"sample string 6","Age":"1天","PatientHeadImgUrl":"http://172.18.30.4:8006/pat/0/1.jpg"},"DoctorPatientRelation":{"DrPatientId":1,"DrId":2,"DrName":"sample string 3","PatientId":4,"PatientName":"sample string 5","RelationStatus":64,"IsDrKey":64,"IsPatientMain":64,"Disease":"sample string 9"},"PatientIMId":"sample string 1"}]
     * ServerTime : 2018-11-13T16:37:25.2677689+08:00
     */

    private String ServerTime;
    private List<ConsultInfoListBean> ConsultInfoList;
    private List<InquiryInfoListBean> InquiryInfoList;
    private List<ApplyInfoListBean> ApplyInfoList;

    public String getServerTime() {
        return ServerTime;
    }

    public void setServerTime(String ServerTime) {
        this.ServerTime = ServerTime;
    }

    public List<ConsultInfoListBean> getConsultInfoList() {
        return ConsultInfoList;
    }

    public void setConsultInfoList(List<ConsultInfoListBean> ConsultInfoList) {
        this.ConsultInfoList = ConsultInfoList;
    }

    public List<InquiryInfoListBean> getInquiryInfoList() {
        return InquiryInfoList;
    }

    public void setInquiryInfoList(List<InquiryInfoListBean> InquiryInfoList) {
        this.InquiryInfoList = InquiryInfoList;
    }

    public List<ApplyInfoListBean> getApplyInfoList() {
        return ApplyInfoList;
    }

    public void setApplyInfoList(List<ApplyInfoListBean> ApplyInfoList) {
        this.ApplyInfoList = ApplyInfoList;
    }

    public static class ConsultInfoListBean {
        /**
         * RecordInfo : {"Id":1,"Content":"sample string 2","Type":64,"PatientId":4,"PatientName":"sample string 5","ApplicantId":6,"DoctorId":7,"DoctorName":"sample string 8","OrgId":9,"Status":64,"DealWithTime":"2018-11-13T16:37:25.2597681+08:00","StartTime":"2018-11-13T16:37:25.2597681+08:00","EndTime":"2018-11-13T16:37:25.2597681+08:00","PayStatus":64,"PayTime":"2018-11-13T16:37:25.2597681+08:00","CreateTime":"2018-11-13T16:37:25.2597681+08:00"}
         * PatientInfo : {"PatientId":1,"AccountId":2,"PatientName":"sample string 3","PatientSex":64,"Birthday":"2018-11-13T16:37:25.2607682+08:00","AreaName":"sample string 6","Age":"1天","PatientHeadImgUrl":"http://172.18.30.4:8006/pat/0/1.jpg"}
         * DoctorPatientRelation : {"DrPatientId":1,"DrId":2,"DrName":"sample string 3","PatientId":4,"PatientName":"sample string 5","RelationStatus":64,"IsDrKey":64,"IsPatientMain":64,"Disease":"sample string 9"}
         * PatientIMId : sample string 1
         */

        private RecordInfoBean RecordInfo;
        private PatientInfoBean PatientInfo;
        private DoctorPatientRelationBean DoctorPatientRelation;
        private String PatientIMId;

        public RecordInfoBean getRecordInfo() {
            return RecordInfo;
        }

        public void setRecordInfo(RecordInfoBean RecordInfo) {
            this.RecordInfo = RecordInfo;
        }

        public PatientInfoBean getPatientInfo() {
            return PatientInfo;
        }

        public void setPatientInfo(PatientInfoBean PatientInfo) {
            this.PatientInfo = PatientInfo;
        }

        public DoctorPatientRelationBean getDoctorPatientRelation() {
            return DoctorPatientRelation;
        }

        public void setDoctorPatientRelation(DoctorPatientRelationBean DoctorPatientRelation) {
            this.DoctorPatientRelation = DoctorPatientRelation;
        }

        public String getPatientIMId() {
            return PatientIMId;
        }

        public void setPatientIMId(String PatientIMId) {
            this.PatientIMId = PatientIMId;
        }

        public static class RecordInfoBean {
            /**
             * Id : 1
             * Content : sample string 2
             * Type : 64
             * PatientId : 4
             * PatientName : sample string 5
             * ApplicantId : 6
             * DoctorId : 7
             * DoctorName : sample string 8
             * OrgId : 9
             * Status : 64
             * DealWithTime : 2018-11-13T16:37:25.2597681+08:00
             * StartTime : 2018-11-13T16:37:25.2597681+08:00
             * EndTime : 2018-11-13T16:37:25.2597681+08:00
             * PayStatus : 64
             * PayTime : 2018-11-13T16:37:25.2597681+08:00
             * CreateTime : 2018-11-13T16:37:25.2597681+08:00
             */

            private int Id;
            private String Content;
            private int Type;
            private int PatientId;
            private String PatientName;
            private int ApplicantId;
            private int DoctorId;
            private String DoctorName;
            private int OrgId;
            private int Status;
            private String DealWithTime;
            private String StartTime;
            private String EndTime;
            private int PayStatus;
            private String PayTime;
            private String CreateTime;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getContent() {
                return Content;
            }

            public void setContent(String Content) {
                this.Content = Content;
            }

            public int getType() {
                return Type;
            }

            public void setType(int Type) {
                this.Type = Type;
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

            public int getApplicantId() {
                return ApplicantId;
            }

            public void setApplicantId(int ApplicantId) {
                this.ApplicantId = ApplicantId;
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

            public int getOrgId() {
                return OrgId;
            }

            public void setOrgId(int OrgId) {
                this.OrgId = OrgId;
            }

            public int getStatus() {
                return Status;
            }

            public void setStatus(int Status) {
                this.Status = Status;
            }

            public String getDealWithTime() {
                return DealWithTime;
            }

            public void setDealWithTime(String DealWithTime) {
                this.DealWithTime = DealWithTime;
            }

            public String getStartTime() {
                return StartTime;
            }

            public void setStartTime(String StartTime) {
                this.StartTime = StartTime;
            }

            public String getEndTime() {
                return EndTime;
            }

            public void setEndTime(String EndTime) {
                this.EndTime = EndTime;
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

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }
        }

        public static class PatientInfoBean {
            /**
             * PatientId : 1
             * AccountId : 2
             * PatientName : sample string 3
             * PatientSex : 64
             * Birthday : 2018-11-13T16:37:25.2607682+08:00
             * AreaName : sample string 6
             * Age : 1天
             * PatientHeadImgUrl : http://172.18.30.4:8006/pat/0/1.jpg
             */

            private int PatientId;
            private int AccountId;
            private String PatientName;
            private int PatientSex;
            private String Birthday;
            private String AreaName;
            private String Age;
            private String PatientHeadImgUrl;

            public int getPatientId() {
                return PatientId;
            }

            public void setPatientId(int PatientId) {
                this.PatientId = PatientId;
            }

            public int getAccountId() {
                return AccountId;
            }

            public void setAccountId(int AccountId) {
                this.AccountId = AccountId;
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

            public String getBirthday() {
                return Birthday;
            }

            public void setBirthday(String Birthday) {
                this.Birthday = Birthday;
            }

            public String getAreaName() {
                return AreaName;
            }

            public void setAreaName(String AreaName) {
                this.AreaName = AreaName;
            }

            public String getAge() {
                return Age;
            }

            public void setAge(String Age) {
                this.Age = Age;
            }

            public String getPatientHeadImgUrl() {
                return PatientHeadImgUrl;
            }

            public void setPatientHeadImgUrl(String PatientHeadImgUrl) {
                this.PatientHeadImgUrl = PatientHeadImgUrl;
            }
        }

        public static class DoctorPatientRelationBean {
            /**
             * DrPatientId : 1
             * DrId : 2
             * DrName : sample string 3
             * PatientId : 4
             * PatientName : sample string 5
             * RelationStatus : 64
             * IsDrKey : 64
             * IsPatientMain : 64
             * Disease : sample string 9
             */

            private int DrPatientId;
            private int DrId;
            private String DrName;
            private int PatientId;
            private String PatientName;
            private int RelationStatus;
            private int IsDrKey;
            private int IsPatientMain;
            private String Disease;

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

            public String getDisease() {
                return Disease;
            }

            public void setDisease(String Disease) {
                this.Disease = Disease;
            }
        }
    }

    public static class InquiryInfoListBean {
        /**
         * RecordInfo : {"Id":1,"Content":"sample string 2","Type":64,"PatientId":4,"PatientName":"sample string 5","ApplicantId":6,"DoctorId":7,"DoctorName":"sample string 8","OrgId":9,"Status":64,"DealWithTime":"2018-11-13T16:37:25.2627684+08:00","StartTime":"2018-11-13T16:37:25.2637685+08:00","EndTime":"2018-11-13T16:37:25.2637685+08:00","PayStatus":64,"PayTime":"2018-11-13T16:37:25.2637685+08:00","CreateTime":"2018-11-13T16:37:25.2637685+08:00","ReExaminationDate":"2018-11-13T16:37:25.2637685+08:00","ReExaminationStartTime":"00:00:00.1234567","ReExaminationEndTime":"00:00:00.1234567"}
         * PatientInfo : {"PatientId":1,"AccountId":2,"PatientName":"sample string 3","PatientSex":64,"Birthday":"2018-11-13T16:37:25.2607682+08:00","AreaName":"sample string 6","Age":"1天","PatientHeadImgUrl":"http://172.18.30.4:8006/pat/0/1.jpg"}
         * DoctorPatientRelation : {"DrPatientId":1,"DrId":2,"DrName":"sample string 3","PatientId":4,"PatientName":"sample string 5","RelationStatus":64,"IsDrKey":64,"IsPatientMain":64,"Disease":"sample string 9"}
         * PatientIMId : sample string 1
         */

        private RecordInfoBeanX RecordInfo;
        private PatientInfoBeanX PatientInfo;
        private DoctorPatientRelationBeanX DoctorPatientRelation;
        private String PatientIMId;

        public RecordInfoBeanX getRecordInfo() {
            return RecordInfo;
        }

        public void setRecordInfo(RecordInfoBeanX RecordInfo) {
            this.RecordInfo = RecordInfo;
        }

        public PatientInfoBeanX getPatientInfo() {
            return PatientInfo;
        }

        public void setPatientInfo(PatientInfoBeanX PatientInfo) {
            this.PatientInfo = PatientInfo;
        }

        public DoctorPatientRelationBeanX getDoctorPatientRelation() {
            return DoctorPatientRelation;
        }

        public void setDoctorPatientRelation(DoctorPatientRelationBeanX DoctorPatientRelation) {
            this.DoctorPatientRelation = DoctorPatientRelation;
        }

        public String getPatientIMId() {
            return PatientIMId;
        }

        public void setPatientIMId(String PatientIMId) {
            this.PatientIMId = PatientIMId;
        }

        public static class RecordInfoBeanX {
            /**
             * Id : 1
             * Content : sample string 2
             * Type : 64
             * PatientId : 4
             * PatientName : sample string 5
             * ApplicantId : 6
             * DoctorId : 7
             * DoctorName : sample string 8
             * OrgId : 9
             * Status : 64
             * DealWithTime : 2018-11-13T16:37:25.2627684+08:00
             * StartTime : 2018-11-13T16:37:25.2637685+08:00
             * EndTime : 2018-11-13T16:37:25.2637685+08:00
             * PayStatus : 64
             * PayTime : 2018-11-13T16:37:25.2637685+08:00
             * CreateTime : 2018-11-13T16:37:25.2637685+08:00
             * ReExaminationDate : 2018-11-13T16:37:25.2637685+08:00
             * ReExaminationStartTime : 00:00:00.1234567
             * ReExaminationEndTime : 00:00:00.1234567
             */

            private int Id;
            private String Content;
            private int Type;
            private int PatientId;
            private String PatientName;
            private int ApplicantId;
            private int DoctorId;
            private String DoctorName;
            private int OrgId;
            private int Status;
            private String DealWithTime;
            private String StartTime;
            private String EndTime;
            private int PayStatus;
            private String PayTime;
            private String CreateTime;
            private String ReExaminationDate;
            private String ReExaminationStartTime;
            private String ReExaminationEndTime;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getContent() {
                return Content;
            }

            public void setContent(String Content) {
                this.Content = Content;
            }

            public int getType() {
                return Type;
            }

            public void setType(int Type) {
                this.Type = Type;
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

            public int getApplicantId() {
                return ApplicantId;
            }

            public void setApplicantId(int ApplicantId) {
                this.ApplicantId = ApplicantId;
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

            public int getOrgId() {
                return OrgId;
            }

            public void setOrgId(int OrgId) {
                this.OrgId = OrgId;
            }

            public int getStatus() {
                return Status;
            }

            public void setStatus(int Status) {
                this.Status = Status;
            }

            public String getDealWithTime() {
                return DealWithTime;
            }

            public void setDealWithTime(String DealWithTime) {
                this.DealWithTime = DealWithTime;
            }

            public String getStartTime() {
                return StartTime;
            }

            public void setStartTime(String StartTime) {
                this.StartTime = StartTime;
            }

            public String getEndTime() {
                return EndTime;
            }

            public void setEndTime(String EndTime) {
                this.EndTime = EndTime;
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

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }

            public String getReExaminationDate() {
                return ReExaminationDate;
            }

            public void setReExaminationDate(String ReExaminationDate) {
                this.ReExaminationDate = ReExaminationDate;
            }

            public String getReExaminationStartTime() {
                return ReExaminationStartTime;
            }

            public void setReExaminationStartTime(String ReExaminationStartTime) {
                this.ReExaminationStartTime = ReExaminationStartTime;
            }

            public String getReExaminationEndTime() {
                return ReExaminationEndTime;
            }

            public void setReExaminationEndTime(String ReExaminationEndTime) {
                this.ReExaminationEndTime = ReExaminationEndTime;
            }
        }

        public static class PatientInfoBeanX {
            /**
             * PatientId : 1
             * AccountId : 2
             * PatientName : sample string 3
             * PatientSex : 64
             * Birthday : 2018-11-13T16:37:25.2607682+08:00
             * AreaName : sample string 6
             * Age : 1天
             * PatientHeadImgUrl : http://172.18.30.4:8006/pat/0/1.jpg
             */

            private int PatientId;
            private int AccountId;
            private String PatientName;
            private int PatientSex;
            private String Birthday;
            private String AreaName;
            private String Age;
            private String PatientHeadImgUrl;

            public int getPatientId() {
                return PatientId;
            }

            public void setPatientId(int PatientId) {
                this.PatientId = PatientId;
            }

            public int getAccountId() {
                return AccountId;
            }

            public void setAccountId(int AccountId) {
                this.AccountId = AccountId;
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

            public String getBirthday() {
                return Birthday;
            }

            public void setBirthday(String Birthday) {
                this.Birthday = Birthday;
            }

            public String getAreaName() {
                return AreaName;
            }

            public void setAreaName(String AreaName) {
                this.AreaName = AreaName;
            }

            public String getAge() {
                return Age;
            }

            public void setAge(String Age) {
                this.Age = Age;
            }

            public String getPatientHeadImgUrl() {
                return PatientHeadImgUrl;
            }

            public void setPatientHeadImgUrl(String PatientHeadImgUrl) {
                this.PatientHeadImgUrl = PatientHeadImgUrl;
            }
        }

        public static class DoctorPatientRelationBeanX {
            /**
             * DrPatientId : 1
             * DrId : 2
             * DrName : sample string 3
             * PatientId : 4
             * PatientName : sample string 5
             * RelationStatus : 64
             * IsDrKey : 64
             * IsPatientMain : 64
             * Disease : sample string 9
             */

            private int DrPatientId;
            private int DrId;
            private String DrName;
            private int PatientId;
            private String PatientName;
            private int RelationStatus;
            private int IsDrKey;
            private int IsPatientMain;
            private String Disease;

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

            public String getDisease() {
                return Disease;
            }

            public void setDisease(String Disease) {
                this.Disease = Disease;
            }
        }
    }

    public static class ApplyInfoListBean {
        /**
         * RecordInfo : {"Id":1,"Type":64,"PrescriptionId":1,"Diagnoses":"sample string 3","Method":64,"Appeal":"sample string 5","AllergicHistory":"sample string 6","PatientId":7,"PatientName":"sample string 8","ApplicantId":9,"DoctorId":10,"DoctorName":"sample string 11","OrgId":12,"StartMethod":64,"Status":64,"DealWithTime":"2018-11-13T16:37:25.2667688+08:00","StartTime":"2018-11-13T16:37:25.2667688+08:00","EndTime":"2018-11-13T16:37:25.2667688+08:00","PrescribeStatus":64,"PayStatus":64,"PayTime":"2018-11-13T16:37:25.2667688+08:00","CreateTime":"2018-11-13T16:37:25.2677689+08:00"}
         * PatientInfo : {"PatientId":1,"AccountId":2,"PatientName":"sample string 3","PatientSex":64,"Birthday":"2018-11-13T16:37:25.2607682+08:00","AreaName":"sample string 6","Age":"1天","PatientHeadImgUrl":"http://172.18.30.4:8006/pat/0/1.jpg"}
         * DoctorPatientRelation : {"DrPatientId":1,"DrId":2,"DrName":"sample string 3","PatientId":4,"PatientName":"sample string 5","RelationStatus":64,"IsDrKey":64,"IsPatientMain":64,"Disease":"sample string 9"}
         * PatientIMId : sample string 1
         */

        private RecordInfoBeanXX RecordInfo;
        private PatientInfoBeanXX PatientInfo;
        private DoctorPatientRelationBeanXX DoctorPatientRelation;
        private String PatientIMId;

        public RecordInfoBeanXX getRecordInfo() {
            return RecordInfo;
        }

        public void setRecordInfo(RecordInfoBeanXX RecordInfo) {
            this.RecordInfo = RecordInfo;
        }

        public PatientInfoBeanXX getPatientInfo() {
            return PatientInfo;
        }

        public void setPatientInfo(PatientInfoBeanXX PatientInfo) {
            this.PatientInfo = PatientInfo;
        }

        public DoctorPatientRelationBeanXX getDoctorPatientRelation() {
            return DoctorPatientRelation;
        }

        public void setDoctorPatientRelation(DoctorPatientRelationBeanXX DoctorPatientRelation) {
            this.DoctorPatientRelation = DoctorPatientRelation;
        }

        public String getPatientIMId() {
            return PatientIMId;
        }

        public void setPatientIMId(String PatientIMId) {
            this.PatientIMId = PatientIMId;
        }

        public static class RecordInfoBeanXX {
            /**
             * Id : 1
             * Type : 64
             * PrescriptionId : 1
             * Diagnoses : sample string 3
             * Method : 64
             * Appeal : sample string 5
             * AllergicHistory : sample string 6
             * PatientId : 7
             * PatientName : sample string 8
             * ApplicantId : 9
             * DoctorId : 10
             * DoctorName : sample string 11
             * OrgId : 12
             * StartMethod : 64
             * Status : 64
             * DealWithTime : 2018-11-13T16:37:25.2667688+08:00
             * StartTime : 2018-11-13T16:37:25.2667688+08:00
             * EndTime : 2018-11-13T16:37:25.2667688+08:00
             * PrescribeStatus : 64
             * PayStatus : 64
             * PayTime : 2018-11-13T16:37:25.2667688+08:00
             * CreateTime : 2018-11-13T16:37:25.2677689+08:00
             */

            private int Id;
            private int Type;
            private int PrescriptionId;
            private String Diagnoses;
            private int Method;
            private String Appeal;
            private String AllergicHistory;
            private int PatientId;
            private String PatientName;
            private int ApplicantId;
            private int DoctorId;
            private String DoctorName;
            private int OrgId;
            private int StartMethod;
            private int Status;
            private String DealWithTime;
            private String StartTime;
            private String EndTime;
            private int PrescribeStatus;
            private int PayStatus;
            private String PayTime;
            private String CreateTime;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public int getType() {
                return Type;
            }

            public void setType(int Type) {
                this.Type = Type;
            }

            public int getPrescriptionId() {
                return PrescriptionId;
            }

            public void setPrescriptionId(int PrescriptionId) {
                this.PrescriptionId = PrescriptionId;
            }

            public String getDiagnoses() {
                return Diagnoses;
            }

            public void setDiagnoses(String Diagnoses) {
                this.Diagnoses = Diagnoses;
            }

            public int getMethod() {
                return Method;
            }

            public void setMethod(int Method) {
                this.Method = Method;
            }

            public String getAppeal() {
                return Appeal;
            }

            public void setAppeal(String Appeal) {
                this.Appeal = Appeal;
            }

            public String getAllergicHistory() {
                return AllergicHistory;
            }

            public void setAllergicHistory(String AllergicHistory) {
                this.AllergicHistory = AllergicHistory;
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

            public int getApplicantId() {
                return ApplicantId;
            }

            public void setApplicantId(int ApplicantId) {
                this.ApplicantId = ApplicantId;
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

            public int getOrgId() {
                return OrgId;
            }

            public void setOrgId(int OrgId) {
                this.OrgId = OrgId;
            }

            public int getStartMethod() {
                return StartMethod;
            }

            public void setStartMethod(int StartMethod) {
                this.StartMethod = StartMethod;
            }

            public int getStatus() {
                return Status;
            }

            public void setStatus(int Status) {
                this.Status = Status;
            }

            public String getDealWithTime() {
                return DealWithTime;
            }

            public void setDealWithTime(String DealWithTime) {
                this.DealWithTime = DealWithTime;
            }

            public String getStartTime() {
                return StartTime;
            }

            public void setStartTime(String StartTime) {
                this.StartTime = StartTime;
            }

            public String getEndTime() {
                return EndTime;
            }

            public void setEndTime(String EndTime) {
                this.EndTime = EndTime;
            }

            public int getPrescribeStatus() {
                return PrescribeStatus;
            }

            public void setPrescribeStatus(int PrescribeStatus) {
                this.PrescribeStatus = PrescribeStatus;
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

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }
        }

        public static class PatientInfoBeanXX {
            /**
             * PatientId : 1
             * AccountId : 2
             * PatientName : sample string 3
             * PatientSex : 64
             * Birthday : 2018-11-13T16:37:25.2607682+08:00
             * AreaName : sample string 6
             * Age : 1天
             * PatientHeadImgUrl : http://172.18.30.4:8006/pat/0/1.jpg
             */

            private int PatientId;
            private int AccountId;
            private String PatientName;
            private int PatientSex;
            private String Birthday;
            private String AreaName;
            private String Age;
            private String PatientHeadImgUrl;

            public int getPatientId() {
                return PatientId;
            }

            public void setPatientId(int PatientId) {
                this.PatientId = PatientId;
            }

            public int getAccountId() {
                return AccountId;
            }

            public void setAccountId(int AccountId) {
                this.AccountId = AccountId;
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

            public String getBirthday() {
                return Birthday;
            }

            public void setBirthday(String Birthday) {
                this.Birthday = Birthday;
            }

            public String getAreaName() {
                return AreaName;
            }

            public void setAreaName(String AreaName) {
                this.AreaName = AreaName;
            }

            public String getAge() {
                return Age;
            }

            public void setAge(String Age) {
                this.Age = Age;
            }

            public String getPatientHeadImgUrl() {
                return PatientHeadImgUrl;
            }

            public void setPatientHeadImgUrl(String PatientHeadImgUrl) {
                this.PatientHeadImgUrl = PatientHeadImgUrl;
            }
        }

        public static class DoctorPatientRelationBeanXX {
            /**
             * DrPatientId : 1
             * DrId : 2
             * DrName : sample string 3
             * PatientId : 4
             * PatientName : sample string 5
             * RelationStatus : 64
             * IsDrKey : 64
             * IsPatientMain : 64
             * Disease : sample string 9
             */

            private int DrPatientId;
            private int DrId;
            private String DrName;
            private int PatientId;
            private String PatientName;
            private int RelationStatus;
            private int IsDrKey;
            private int IsPatientMain;
            private String Disease;

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

            public String getDisease() {
                return Disease;
            }

            public void setDisease(String Disease) {
                this.Disease = Disease;
            }
        }
    }
}
