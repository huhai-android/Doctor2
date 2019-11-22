package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   ConsuLEndtentity
 *  @创建者:   huhai
 *  @创建时间:  2019/4/25 18:12
 *  @描述：
 */
public class ConsuLEndtentity {

    /**
     * Appeal : gfghhhjjgggh
     * ApplicantHeadImgUrl : http://userImage.newstarthealth.cn/pat/0/1331.jpg
     * ApplicantIMId : pat_894
     * ApplicantId : 894
     * ApplicantName : yu
     * CreateTime : 2019-04-23 17:57:52
     * DealWithTime : 2019-04-23 17:58:29
     * DoctorId : 130
     * DoctorName : 张飞
     * DoctorPatientRelation : {"CreateTime":"","Disease":".","DrId":130,"DrPatientId":1562,"Invalid":0,"IsDrKey":0,"IsPatientMain":1,"PatientId":1331,"RelationStatus":1,"UpdateTime":""}
     * EndTime : 2019-04-23 17:58:29
     * Id : 934
     * Method : 1
     * OrderStatus : 0
     * OrgId : 1
     * PatientId : 1331
     * PatientInfo : {"AccountId":894,"Age":"14岁","Birthday":"2005-04-23 00:00:00","CreateId":0,"CredType":"0","Education":0,"Invalid":0,"Job":0,"MedicalType":0,"Mobile":"18946462315","NameLetter":"YU","PatientId":1331,"PatientName":"yu","PatientSex":1,"PatientType":0,"Region":0,"UpdateId":0}
     * PatientName : yu
     * PayOrderNo : 20190423175753343738
     * PayStatus : 2
     * PayTime : 2019-04-23 17:57:53
     * StartMethod : 1
     * StartTime : 2019-04-23 17:58:10
     * Status : 5
     * Type : 3
     * UpdateTime : 2019-04-23 17:58:28
     * unReadNum : 0
     */

    private String Appeal;
    private String ApplicantHeadImgUrl;
    private String ApplicantIMId;
    private int ApplicantId;
    private String ApplicantName;
    private String CreateTime;
    private String DealWithTime;
    private int DoctorId;
    private String DoctorName;
    private DoctorPatientRelationBean DoctorPatientRelation;
    private String EndTime;
    private int Id;
    private int Method;
    private int OrderStatus;
    private int OrgId;
    private int PatientId;
    private PatientInfoBean PatientInfo;
    private String PatientName;
    private String PayOrderNo;
    private int PayStatus;
    private String PayTime;
    private int StartMethod;
    private String StartTime;
    private int Status;
    private int Type;
    private String UpdateTime;
    private int unReadNum;

    public String getAppeal() {
        return Appeal;
    }

    public void setAppeal(String Appeal) {
        this.Appeal = Appeal;
    }

    public String getApplicantHeadImgUrl() {
        return ApplicantHeadImgUrl;
    }

    public void setApplicantHeadImgUrl(String ApplicantHeadImgUrl) {
        this.ApplicantHeadImgUrl = ApplicantHeadImgUrl;
    }

    public String getApplicantIMId() {
        return ApplicantIMId;
    }

    public void setApplicantIMId(String ApplicantIMId) {
        this.ApplicantIMId = ApplicantIMId;
    }

    public int getApplicantId() {
        return ApplicantId;
    }

    public void setApplicantId(int ApplicantId) {
        this.ApplicantId = ApplicantId;
    }

    public String getApplicantName() {
        return ApplicantName;
    }

    public void setApplicantName(String ApplicantName) {
        this.ApplicantName = ApplicantName;
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

    public DoctorPatientRelationBean getDoctorPatientRelation() {
        return DoctorPatientRelation;
    }

    public void setDoctorPatientRelation(DoctorPatientRelationBean DoctorPatientRelation) {
        this.DoctorPatientRelation = DoctorPatientRelation;
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

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int OrderStatus) {
        this.OrderStatus = OrderStatus;
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

    public PatientInfoBean getPatientInfo() {
        return PatientInfo;
    }

    public void setPatientInfo(PatientInfoBean PatientInfo) {
        this.PatientInfo = PatientInfo;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String PatientName) {
        this.PatientName = PatientName;
    }

    public String getPayOrderNo() {
        return PayOrderNo;
    }

    public void setPayOrderNo(String PayOrderNo) {
        this.PayOrderNo = PayOrderNo;
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

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }

    public int getUnReadNum() {
        return unReadNum;
    }

    public void setUnReadNum(int unReadNum) {
        this.unReadNum = unReadNum;
    }

    public static class DoctorPatientRelationBean {
        /**
         * CreateTime :
         * Disease : .
         * DrId : 130
         * DrPatientId : 1562
         * Invalid : 0
         * IsDrKey : 0
         * IsPatientMain : 1
         * PatientId : 1331
         * RelationStatus : 1
         * UpdateTime :
         */

        private String CreateTime;
        private String Disease;
        private int DrId;
        private int DrPatientId;
        private int Invalid;
        private int IsDrKey;
        private int IsPatientMain;
        private int PatientId;
        private int RelationStatus;
        private String UpdateTime;

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getDisease() {
            return Disease;
        }

        public void setDisease(String Disease) {
            this.Disease = Disease;
        }

        public int getDrId() {
            return DrId;
        }

        public void setDrId(int DrId) {
            this.DrId = DrId;
        }

        public int getDrPatientId() {
            return DrPatientId;
        }

        public void setDrPatientId(int DrPatientId) {
            this.DrPatientId = DrPatientId;
        }

        public int getInvalid() {
            return Invalid;
        }

        public void setInvalid(int Invalid) {
            this.Invalid = Invalid;
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

        public int getPatientId() {
            return PatientId;
        }

        public void setPatientId(int PatientId) {
            this.PatientId = PatientId;
        }

        public int getRelationStatus() {
            return RelationStatus;
        }

        public void setRelationStatus(int RelationStatus) {
            this.RelationStatus = RelationStatus;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }
    }

    public static class PatientInfoBean {
        /**
         * AccountId : 894
         * Age : 14岁
         * Birthday : 2005-04-23 00:00:00
         * CreateId : 0
         * CredType : 0
         * Education : 0
         * Invalid : 0
         * Job : 0
         * MedicalType : 0
         * Mobile : 18946462315
         * NameLetter : YU
         * PatientId : 1331
         * PatientName : yu
         * PatientSex : 1
         * PatientType : 0
         * Region : 0
         * UpdateId : 0
         */

        private int AccountId;
        private String Age;
        private String Birthday;
        private int CreateId;
        private String CredType;
        private int Education;
        private int Invalid;
        private int Job;
        private int MedicalType;
        private String Mobile;
        private String NameLetter;
        private int PatientId;
        private String PatientName;
        private int PatientSex;
        private int PatientType;
        private int Region;
        private int UpdateId;

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

        public int getCreateId() {
            return CreateId;
        }

        public void setCreateId(int CreateId) {
            this.CreateId = CreateId;
        }

        public String getCredType() {
            return CredType;
        }

        public void setCredType(String CredType) {
            this.CredType = CredType;
        }

        public int getEducation() {
            return Education;
        }

        public void setEducation(int Education) {
            this.Education = Education;
        }

        public int getInvalid() {
            return Invalid;
        }

        public void setInvalid(int Invalid) {
            this.Invalid = Invalid;
        }

        public int getJob() {
            return Job;
        }

        public void setJob(int Job) {
            this.Job = Job;
        }

        public int getMedicalType() {
            return MedicalType;
        }

        public void setMedicalType(int MedicalType) {
            this.MedicalType = MedicalType;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public String getNameLetter() {
            return NameLetter;
        }

        public void setNameLetter(String NameLetter) {
            this.NameLetter = NameLetter;
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

        public int getPatientType() {
            return PatientType;
        }

        public void setPatientType(int PatientType) {
            this.PatientType = PatientType;
        }

        public int getRegion() {
            return Region;
        }

        public void setRegion(int Region) {
            this.Region = Region;
        }

        public int getUpdateId() {
            return UpdateId;
        }

        public void setUpdateId(int UpdateId) {
            this.UpdateId = UpdateId;
        }
    }
}
