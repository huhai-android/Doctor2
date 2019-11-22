package com.newdjk.doctor.ui.entity;

import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   PrescriptionDescEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/12/19 15:24
 *  @描述：
 */
public class PrescriptionDescEntity {

    /**
     * ListDetails : [{"Id":1327,"PrescriptionId":822,"MedicationId":13,"Name":"琥珀酸美托洛尔缓释片","Code":"","Brand":"倍他乐克","Manufacturer":"无锡市新区黄山路2号","Specification":"47.5mg","UsageTimeId":0,"UsageTime":"","UsageMethodId":4,"UsageMethod":"静注","DayDosageId":8,"DayDosage":"每3日一次","Dosage":1,"DosageUnitId":4,"DosageUnit":"袋","DosageDaysId":0,"DosageDays":"","Price":0.01,"Quantity":1,"PackageUnitId":6,"PackageUnit":"板","TotalPrice":0.01,"Remark":null,"UpdateTime":"2018-12-14 16:18:38","CreateTime":"2018-12-14 16:18:38"}]
     * ListMedications : null
     * PrescriptionCategory : 0
     * PrescriptionBuyRecord : null
     * DoctorSignImgUrl : http://resource.newstarthealth.cn/Recipe/20181214/822/306.png
     * ApothecarySignImgUrl :
     * Id : 822
     * No : XQD201812141618121794
     * ApplyId : 0
     * MPrescriptionId : 0
     * MPrescriptionNo : null
     * PatientId : 314
     * PatientName : 赵永生
     * PatientSex : 2
     * Age : 24
     * Mobile : 18565780347
     * AreaName : null
     * Region : null
     * ApplicantId : 94
     * ApplicantName : null
     * DoctorId : 306
     * DoctorName : 艾美
     * MedicationCompanyId : 1
     * OrgId : 66
     * OrgName : 健康保定
     * OrgPath : ,0,1,66,
     * Type : 2
     * CostType : 自费
     * MedicalNo : null
     * MedicalRecordNo : null
     * Department : 妇科
     * Diagnoses : 法国红酒坎坎坷坷
     * ValidDays : 1
     * ValidDaysRemark : null
     * DosageDays : 1
     * DosageDaysRemark : null
     * TotalPrice : 0.01
     * PrescribeTime : 2018-12-14 16:18:38
     * Status : 0
     * SignStatus : 1
     * AuditorId : 311
     * AuditorName : 老胡
     * AuditTime :
     * AuditRemark : XXXXXXXXXXXX
     * RejectReasonId : null
     * RandomCode : 1863
     * Remark : null
     * UpdateTime : 2018-12-18 15:42:30
     * CreateTime : 2018-12-14 16:18:38
     */

    private Object ListMedications;
    private int PrescriptionCategory;
    private Object PrescriptionBuyRecord;
    private String DoctorSignImgUrl;
    private String ApothecarySignImgUrl;
    private int Id;
    private String No;
    private int ApplyId;
    private int MPrescriptionId;
    private Object MPrescriptionNo;
    private int PatientId;
    private String PatientName;
    private int PatientSex;
    private int Age;
    private String Mobile;
    private Object AreaName;
    private Object Region;
    private int ApplicantId;
    private Object ApplicantName;
    private int DoctorId;
    private String DoctorName;
    private int MedicationCompanyId;
    private int OrgId;
    private String OrgName;
    private String OrgPath;
    private int Type;
    private String CostType;
    private Object MedicalNo;
    private Object MedicalRecordNo;
    private String Department;
    private String Diagnoses;
    private int ValidDays;
    private Object ValidDaysRemark;
    private int DosageDays;
    private Object DosageDaysRemark;
    private double TotalPrice;
    private String PrescribeTime;
    private int Status;
    private int SignStatus;
    private int AuditorId;
    private String AuditorName;
    private String AuditTime;
    private String AuditRemark;
    private Object RejectReasonId;
    private String RandomCode;
    private Object Remark;
    private String UpdateTime;
    private String CreateTime;
    private List<ListDetailsBean> ListDetails;

    public Object getListMedications() {
        return ListMedications;
    }

    public void setListMedications(Object ListMedications) {
        this.ListMedications = ListMedications;
    }

    public int getPrescriptionCategory() {
        return PrescriptionCategory;
    }

    public void setPrescriptionCategory(int PrescriptionCategory) {
        this.PrescriptionCategory = PrescriptionCategory;
    }

    public Object getPrescriptionBuyRecord() {
        return PrescriptionBuyRecord;
    }

    public void setPrescriptionBuyRecord(Object PrescriptionBuyRecord) {
        this.PrescriptionBuyRecord = PrescriptionBuyRecord;
    }

    public String getDoctorSignImgUrl() {
        return DoctorSignImgUrl;
    }

    public void setDoctorSignImgUrl(String DoctorSignImgUrl) {
        this.DoctorSignImgUrl = DoctorSignImgUrl;
    }

    public String getApothecarySignImgUrl() {
        return ApothecarySignImgUrl;
    }

    public void setApothecarySignImgUrl(String ApothecarySignImgUrl) {
        this.ApothecarySignImgUrl = ApothecarySignImgUrl;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String No) {
        this.No = No;
    }

    public int getApplyId() {
        return ApplyId;
    }

    public void setApplyId(int ApplyId) {
        this.ApplyId = ApplyId;
    }

    public int getMPrescriptionId() {
        return MPrescriptionId;
    }

    public void setMPrescriptionId(int MPrescriptionId) {
        this.MPrescriptionId = MPrescriptionId;
    }

    public Object getMPrescriptionNo() {
        return MPrescriptionNo;
    }

    public void setMPrescriptionNo(Object MPrescriptionNo) {
        this.MPrescriptionNo = MPrescriptionNo;
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

    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public Object getAreaName() {
        return AreaName;
    }

    public void setAreaName(Object AreaName) {
        this.AreaName = AreaName;
    }

    public Object getRegion() {
        return Region;
    }

    public void setRegion(Object Region) {
        this.Region = Region;
    }

    public int getApplicantId() {
        return ApplicantId;
    }

    public void setApplicantId(int ApplicantId) {
        this.ApplicantId = ApplicantId;
    }

    public Object getApplicantName() {
        return ApplicantName;
    }

    public void setApplicantName(Object ApplicantName) {
        this.ApplicantName = ApplicantName;
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

    public int getMedicationCompanyId() {
        return MedicationCompanyId;
    }

    public void setMedicationCompanyId(int MedicationCompanyId) {
        this.MedicationCompanyId = MedicationCompanyId;
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

    public String getOrgPath() {
        return OrgPath;
    }

    public void setOrgPath(String OrgPath) {
        this.OrgPath = OrgPath;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public String getCostType() {
        return CostType;
    }

    public void setCostType(String CostType) {
        this.CostType = CostType;
    }

    public Object getMedicalNo() {
        return MedicalNo;
    }

    public void setMedicalNo(Object MedicalNo) {
        this.MedicalNo = MedicalNo;
    }

    public Object getMedicalRecordNo() {
        return MedicalRecordNo;
    }

    public void setMedicalRecordNo(Object MedicalRecordNo) {
        this.MedicalRecordNo = MedicalRecordNo;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public String getDiagnoses() {
        return Diagnoses;
    }

    public void setDiagnoses(String Diagnoses) {
        this.Diagnoses = Diagnoses;
    }

    public int getValidDays() {
        return ValidDays;
    }

    public void setValidDays(int ValidDays) {
        this.ValidDays = ValidDays;
    }

    public Object getValidDaysRemark() {
        return ValidDaysRemark;
    }

    public void setValidDaysRemark(Object ValidDaysRemark) {
        this.ValidDaysRemark = ValidDaysRemark;
    }

    public int getDosageDays() {
        return DosageDays;
    }

    public void setDosageDays(int DosageDays) {
        this.DosageDays = DosageDays;
    }

    public Object getDosageDaysRemark() {
        return DosageDaysRemark;
    }

    public void setDosageDaysRemark(Object DosageDaysRemark) {
        this.DosageDaysRemark = DosageDaysRemark;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

    public String getPrescribeTime() {
        return PrescribeTime;
    }

    public void setPrescribeTime(String PrescribeTime) {
        this.PrescribeTime = PrescribeTime;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public int getSignStatus() {
        return SignStatus;
    }

    public void setSignStatus(int SignStatus) {
        this.SignStatus = SignStatus;
    }

    public int getAuditorId() {
        return AuditorId;
    }

    public void setAuditorId(int AuditorId) {
        this.AuditorId = AuditorId;
    }

    public String getAuditorName() {
        return AuditorName;
    }

    public void setAuditorName(String AuditorName) {
        this.AuditorName = AuditorName;
    }

    public String getAuditTime() {
        return AuditTime;
    }

    public void setAuditTime(String AuditTime) {
        this.AuditTime = AuditTime;
    }

    public String getAuditRemark() {
        return AuditRemark;
    }

    public void setAuditRemark(String AuditRemark) {
        this.AuditRemark = AuditRemark;
    }

    public Object getRejectReasonId() {
        return RejectReasonId;
    }

    public void setRejectReasonId(Object RejectReasonId) {
        this.RejectReasonId = RejectReasonId;
    }

    public String getRandomCode() {
        return RandomCode;
    }

    public void setRandomCode(String RandomCode) {
        this.RandomCode = RandomCode;
    }

    public Object getRemark() {
        return Remark;
    }

    public void setRemark(Object Remark) {
        this.Remark = Remark;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public List<ListDetailsBean> getListDetails() {
        return ListDetails;
    }

    public void setListDetails(List<ListDetailsBean> ListDetails) {
        this.ListDetails = ListDetails;
    }

    public static class ListDetailsBean {
        /**
         * Id : 1327
         * PrescriptionId : 822
         * MedicationId : 13
         * Name : 琥珀酸美托洛尔缓释片
         * Code :
         * Brand : 倍他乐克
         * Manufacturer : 无锡市新区黄山路2号
         * Specification : 47.5mg
         * UsageTimeId : 0
         * UsageTime :
         * UsageMethodId : 4
         * UsageMethod : 静注
         * DayDosageId : 8
         * DayDosage : 每3日一次
         * Dosage : 1.0
         * DosageUnitId : 4
         * DosageUnit : 袋
         * DosageDaysId : 0
         * DosageDays :
         * Price : 0.01
         * Quantity : 1
         * PackageUnitId : 6
         * PackageUnit : 板
         * TotalPrice : 0.01
         * Remark : null
         * UpdateTime : 2018-12-14 16:18:38
         * CreateTime : 2018-12-14 16:18:38
         */

        private int Id;
        private int PrescriptionId;
        private int MedicationId;
        private String Name;
        private String Code;
        private String Brand;
        private String Manufacturer;
        private String Specification;
        private int UsageTimeId;
        private String UsageTime;
        private int UsageMethodId;
        private String UsageMethod;
        private int DayDosageId;
        private String DayDosage;
        private double Dosage;
        private int DosageUnitId;
        private String DosageUnit;
        private int DosageDaysId;
        private String DosageDays;
        private double Price;
        private int Quantity;
        private int PackageUnitId;
        private String PackageUnit;
        private double TotalPrice;
        private Object Remark;
        private String UpdateTime;
        private String CreateTime;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getPrescriptionId() {
            return PrescriptionId;
        }

        public void setPrescriptionId(int PrescriptionId) {
            this.PrescriptionId = PrescriptionId;
        }

        public int getMedicationId() {
            return MedicationId;
        }

        public void setMedicationId(int MedicationId) {
            this.MedicationId = MedicationId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getBrand() {
            return Brand;
        }

        public void setBrand(String Brand) {
            this.Brand = Brand;
        }

        public String getManufacturer() {
            return Manufacturer;
        }

        public void setManufacturer(String Manufacturer) {
            this.Manufacturer = Manufacturer;
        }

        public String getSpecification() {
            return Specification;
        }

        public void setSpecification(String Specification) {
            this.Specification = Specification;
        }

        public int getUsageTimeId() {
            return UsageTimeId;
        }

        public void setUsageTimeId(int UsageTimeId) {
            this.UsageTimeId = UsageTimeId;
        }

        public String getUsageTime() {
            return UsageTime;
        }

        public void setUsageTime(String UsageTime) {
            this.UsageTime = UsageTime;
        }

        public int getUsageMethodId() {
            return UsageMethodId;
        }

        public void setUsageMethodId(int UsageMethodId) {
            this.UsageMethodId = UsageMethodId;
        }

        public String getUsageMethod() {
            return UsageMethod;
        }

        public void setUsageMethod(String UsageMethod) {
            this.UsageMethod = UsageMethod;
        }

        public int getDayDosageId() {
            return DayDosageId;
        }

        public void setDayDosageId(int DayDosageId) {
            this.DayDosageId = DayDosageId;
        }

        public String getDayDosage() {
            return DayDosage;
        }

        public void setDayDosage(String DayDosage) {
            this.DayDosage = DayDosage;
        }

        public double getDosage() {
            return Dosage;
        }

        public void setDosage(double Dosage) {
            this.Dosage = Dosage;
        }

        public int getDosageUnitId() {
            return DosageUnitId;
        }

        public void setDosageUnitId(int DosageUnitId) {
            this.DosageUnitId = DosageUnitId;
        }

        public String getDosageUnit() {
            return DosageUnit;
        }

        public void setDosageUnit(String DosageUnit) {
            this.DosageUnit = DosageUnit;
        }

        public int getDosageDaysId() {
            return DosageDaysId;
        }

        public void setDosageDaysId(int DosageDaysId) {
            this.DosageDaysId = DosageDaysId;
        }

        public String getDosageDays() {
            return DosageDays;
        }

        public void setDosageDays(String DosageDays) {
            this.DosageDays = DosageDays;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }

        public int getQuantity() {
            return Quantity;
        }

        public void setQuantity(int Quantity) {
            this.Quantity = Quantity;
        }

        public int getPackageUnitId() {
            return PackageUnitId;
        }

        public void setPackageUnitId(int PackageUnitId) {
            this.PackageUnitId = PackageUnitId;
        }

        public String getPackageUnit() {
            return PackageUnit;
        }

        public void setPackageUnit(String PackageUnit) {
            this.PackageUnit = PackageUnit;
        }

        public double getTotalPrice() {
            return TotalPrice;
        }

        public void setTotalPrice(double TotalPrice) {
            this.TotalPrice = TotalPrice;
        }

        public Object getRemark() {
            return Remark;
        }

        public void setRemark(Object Remark) {
            this.Remark = Remark;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }
    }
}
