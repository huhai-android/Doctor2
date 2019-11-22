package com.newdjk.doctor.ui.entity;

import java.io.Serializable;
import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   MianDetailEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/9/7 11:09
 *  @描述：
 */
public class MDTDetailEntity implements Serializable {


    /**
     * SubjectBuyRecordId : 37
     * SubjectRecordId : 28
     * PatientId : 16
     * PatientName : HUU
     * PatientMobile : 15088131724
     * PatientSex : 2
     * PatientAge : 26岁
     * PatPicturePath : http://userimage.newstarthealth.cn/pat/0/16.jpg?dt=201909072501498
     * SpecialistHosGroupId : 0
     * HosGroupName : null
     * Description : eeeeeeeeeeee
     * Diseases : null
     * DiseasesTimeNo : 0
     * DiseasesTimeText : null
     * SeeDoctorNo : 0
     * SeeDoctorText : null
     * SeeDoctor : null
     * SeeDepartment : null
     * IsHasImg : 0
     * IsDrug : 0
     * DrugUse : null
     * OperationDes : null
     * ChemoDes : null
     * ChronicDisease : null
     * SeriousDisease : null
     * IsAllergy : 0
     * AllergyDes : null
     * Advice : null
     * ConsultAmount : 0.00
     * IMGroupId : G201909071604232084
     * PayStatus : 2
     * PayOrderNo : 20190907160423376698
     * PayOrderTime : null
     * PayTime : 2019-09-07 16:04:23
     * ReceiptTime : null
     * IssueTime : null
     * SubjectStatus : 0
     * DrId : 57
     * DrName : 奥巴马
     * DoctorLevelId : 0
     * DoctorLevelName : null
     * LevelShortName : null
     * LevelDesc : null
     * SubjectBuyRecordNo : 1909071604224857841
     * SubjectReportPath : null
     * SubjectType : 1
     * AllDrIds : 57
     * AllDrNames : 奥巴马
     * MDTReportDoctors : [{"SubjectDoctorId":81,"SubjectBuyRecordId":37,"DrId":57,"DrName":"奥巴马","PicturePath":"http://userimage.newstarthealth.cn/doc/0/57.jpg?dt=201909072501520","HospitalId":440,"HospitalName":"中山一院","DepartmentId":32,"DepartmentName":"耳鼻喉科","Position":1,"PositionName":"主任医师","DoctorLevelId":0,"DoctorLevelName":null,"LevelShortName":null,"LevelDesc":null,"IsMajor":0,"Advice":"","AdviceTime":"","IsSubmit":1}]
     */

    private int SubjectBuyRecordId;
    private int SubjectRecordId;
    private int PatientId;
    private String PatientName;
    private String PatientMobile;
    private int PatientSex;
    private String PatientAge;
    private String PatPicturePath;
    private int SpecialistHosGroupId;
    private String HosGroupName;
    private String Description;
    private String Diseases;
    private int DiseasesTimeNo;
    private String DiseasesTimeText;
    private int SeeDoctorNo;
    private String SeeDoctorText;
    private String SeeDoctor;
    private String SeeDepartment;
    private int IsHasImg;
    private int IsDrug;
    private String DrugUse;
    private String OperationDes;
    private String ChemoDes;
    private String ChronicDisease;
    private String SeriousDisease;
    private int IsAllergy;
    private String AllergyDes;
    private String Advice;
    private String ConsultAmount;
    private String IMGroupId;
    private int PayStatus;
    private String PayOrderNo;
    private String PayOrderTime;
    private String PayTime;
    private String ReceiptTime;
    private String IssueTime;
    private int SubjectStatus;
    private int DrId;
    private String DrName;
    private int DoctorLevelId;
    private String DoctorLevelName;
    private String LevelShortName;
    private String LevelDesc;
    private String SubjectBuyRecordNo;
    private String SubjectReportPath;
    private int SubjectType;
    private String AllDrIds;
    private String AllDrNames;
    private List<MDTReportDoctorsBean> MDTReportDoctors;
    private int ConsultDrId;
    private int MajorDrId;
    private int IsNext;

    public int getNextBuyRecordId() {
        return NextBuyRecordId;
    }

    public void setNextBuyRecordId(int nextBuyRecordId) {
        NextBuyRecordId = nextBuyRecordId;
    }

    private int NextBuyRecordId;

    public int getConsultDrId() {
        return ConsultDrId;
    }

    public void setConsultDrId(int consultDrId) {
        ConsultDrId = consultDrId;
    }

    public int getMajorDrId() {
        return MajorDrId;
    }

    public void setMajorDrId(int majorDrId) {
        MajorDrId = majorDrId;
    }

    public int getIsNext() {
        return IsNext;
    }

    public void setIsNext(int isNext) {
        IsNext = isNext;
    }

    public int getSubjectBuyRecordId() {
        return SubjectBuyRecordId;
    }

    public void setSubjectBuyRecordId(int SubjectBuyRecordId) {
        this.SubjectBuyRecordId = SubjectBuyRecordId;
    }

    public int getSubjectRecordId() {
        return SubjectRecordId;
    }

    public void setSubjectRecordId(int SubjectRecordId) {
        this.SubjectRecordId = SubjectRecordId;
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

    public String getPatientMobile() {
        return PatientMobile;
    }

    public void setPatientMobile(String PatientMobile) {
        this.PatientMobile = PatientMobile;
    }

    public int getPatientSex() {
        return PatientSex;
    }

    public void setPatientSex(int PatientSex) {
        this.PatientSex = PatientSex;
    }

    public String getPatientAge() {
        return PatientAge;
    }

    public void setPatientAge(String PatientAge) {
        this.PatientAge = PatientAge;
    }

    public String getPatPicturePath() {
        return PatPicturePath;
    }

    public void setPatPicturePath(String PatPicturePath) {
        this.PatPicturePath = PatPicturePath;
    }

    public int getSpecialistHosGroupId() {
        return SpecialistHosGroupId;
    }

    public void setSpecialistHosGroupId(int SpecialistHosGroupId) {
        this.SpecialistHosGroupId = SpecialistHosGroupId;
    }

    public String getHosGroupName() {
        return HosGroupName;
    }

    public void setHosGroupName(String HosGroupName) {
        this.HosGroupName = HosGroupName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getDiseases() {
        return Diseases;
    }

    public void setDiseases(String Diseases) {
        this.Diseases = Diseases;
    }

    public int getDiseasesTimeNo() {
        return DiseasesTimeNo;
    }

    public void setDiseasesTimeNo(int DiseasesTimeNo) {
        this.DiseasesTimeNo = DiseasesTimeNo;
    }

    public String getDiseasesTimeText() {
        return DiseasesTimeText;
    }

    public void setDiseasesTimeText(String DiseasesTimeText) {
        this.DiseasesTimeText = DiseasesTimeText;
    }

    public int getSeeDoctorNo() {
        return SeeDoctorNo;
    }

    public void setSeeDoctorNo(int SeeDoctorNo) {
        this.SeeDoctorNo = SeeDoctorNo;
    }

    public String getSeeDoctorText() {
        return SeeDoctorText;
    }

    public void setSeeDoctorText(String SeeDoctorText) {
        this.SeeDoctorText = SeeDoctorText;
    }

    public String getSeeDoctor() {
        return SeeDoctor;
    }

    public void setSeeDoctor(String SeeDoctor) {
        this.SeeDoctor = SeeDoctor;
    }

    public String getSeeDepartment() {
        return SeeDepartment;
    }

    public void setSeeDepartment(String SeeDepartment) {
        this.SeeDepartment = SeeDepartment;
    }

    public int getIsHasImg() {
        return IsHasImg;
    }

    public void setIsHasImg(int IsHasImg) {
        this.IsHasImg = IsHasImg;
    }

    public int getIsDrug() {
        return IsDrug;
    }

    public void setIsDrug(int IsDrug) {
        this.IsDrug = IsDrug;
    }

    public String getDrugUse() {
        return DrugUse;
    }

    public void setDrugUse(String DrugUse) {
        this.DrugUse = DrugUse;
    }

    public String getOperationDes() {
        return OperationDes;
    }

    public void setOperationDes(String OperationDes) {
        this.OperationDes = OperationDes;
    }

    public String getChemoDes() {
        return ChemoDes;
    }

    public void setChemoDes(String ChemoDes) {
        this.ChemoDes = ChemoDes;
    }

    public String getChronicDisease() {
        return ChronicDisease;
    }

    public void setChronicDisease(String ChronicDisease) {
        this.ChronicDisease = ChronicDisease;
    }

    public String getSeriousDisease() {
        return SeriousDisease;
    }

    public void setSeriousDisease(String SeriousDisease) {
        this.SeriousDisease = SeriousDisease;
    }

    public int getIsAllergy() {
        return IsAllergy;
    }

    public void setIsAllergy(int IsAllergy) {
        this.IsAllergy = IsAllergy;
    }

    public String getAllergyDes() {
        return AllergyDes;
    }

    public void setAllergyDes(String AllergyDes) {
        this.AllergyDes = AllergyDes;
    }

    public String getAdvice() {
        return Advice;
    }

    public void setAdvice(String Advice) {
        this.Advice = Advice;
    }

    public String getConsultAmount() {
        return ConsultAmount;
    }

    public void setConsultAmount(String ConsultAmount) {
        this.ConsultAmount = ConsultAmount;
    }

    public String getIMGroupId() {
        return IMGroupId;
    }

    public void setIMGroupId(String IMGroupId) {
        this.IMGroupId = IMGroupId;
    }

    public int getPayStatus() {
        return PayStatus;
    }

    public void setPayStatus(int PayStatus) {
        this.PayStatus = PayStatus;
    }

    public String getPayOrderNo() {
        return PayOrderNo;
    }

    public void setPayOrderNo(String PayOrderNo) {
        this.PayOrderNo = PayOrderNo;
    }

    public String getPayOrderTime() {
        return PayOrderTime;
    }

    public void setPayOrderTime(String PayOrderTime) {
        this.PayOrderTime = PayOrderTime;
    }

    public String getPayTime() {
        return PayTime;
    }

    public void setPayTime(String PayTime) {
        this.PayTime = PayTime;
    }

    public String getReceiptTime() {
        return ReceiptTime;
    }

    public void setReceiptTime(String ReceiptTime) {
        this.ReceiptTime = ReceiptTime;
    }

    public String getIssueTime() {
        return IssueTime;
    }

    public void setIssueTime(String IssueTime) {
        this.IssueTime = IssueTime;
    }

    public int getSubjectStatus() {
        return SubjectStatus;
    }

    public void setSubjectStatus(int SubjectStatus) {
        this.SubjectStatus = SubjectStatus;
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

    public int getDoctorLevelId() {
        return DoctorLevelId;
    }

    public void setDoctorLevelId(int DoctorLevelId) {
        this.DoctorLevelId = DoctorLevelId;
    }

    public String getDoctorLevelName() {
        return DoctorLevelName;
    }

    public void setDoctorLevelName(String DoctorLevelName) {
        this.DoctorLevelName = DoctorLevelName;
    }

    public String getLevelShortName() {
        return LevelShortName;
    }

    public void setLevelShortName(String LevelShortName) {
        this.LevelShortName = LevelShortName;
    }

    public String getLevelDesc() {
        return LevelDesc;
    }

    public void setLevelDesc(String LevelDesc) {
        this.LevelDesc = LevelDesc;
    }

    public String getSubjectBuyRecordNo() {
        return SubjectBuyRecordNo;
    }

    public void setSubjectBuyRecordNo(String SubjectBuyRecordNo) {
        this.SubjectBuyRecordNo = SubjectBuyRecordNo;
    }

    public String getSubjectReportPath() {
        return SubjectReportPath;
    }

    public void setSubjectReportPath(String SubjectReportPath) {
        this.SubjectReportPath = SubjectReportPath;
    }

    public int getSubjectType() {
        return SubjectType;
    }

    public void setSubjectType(int SubjectType) {
        this.SubjectType = SubjectType;
    }

    public String getAllDrIds() {
        return AllDrIds;
    }

    public void setAllDrIds(String AllDrIds) {
        this.AllDrIds = AllDrIds;
    }

    public String getAllDrNames() {
        return AllDrNames;
    }

    public void setAllDrNames(String AllDrNames) {
        this.AllDrNames = AllDrNames;
    }

    public List<MDTReportDoctorsBean> getMDTReportDoctors() {
        return MDTReportDoctors;
    }

    public void setMDTReportDoctors(List<MDTReportDoctorsBean> MDTReportDoctors) {
        this.MDTReportDoctors = MDTReportDoctors;
    }

    public static class MDTReportDoctorsBean implements Serializable {
        /**
         * SubjectDoctorId : 81
         * SubjectBuyRecordId : 37
         * DrId : 57
         * DrName : 奥巴马
         * PicturePath : http://userimage.newstarthealth.cn/doc/0/57.jpg?dt=201909072501520
         * HospitalId : 440
         * HospitalName : 中山一院
         * DepartmentId : 32
         * DepartmentName : 耳鼻喉科
         * Position : 1
         * PositionName : 主任医师
         * DoctorLevelId : 0
         * DoctorLevelName : null
         * LevelShortName : null
         * LevelDesc : null
         * IsMajor : 0
         * Advice : 
         * AdviceTime : 
         * IsSubmit : 1
         */

        private int SubjectDoctorId;
        private int SubjectBuyRecordId;
        private int DrId;
        private String DrName;
        private String PicturePath;
        private int HospitalId;
        private String HospitalName;
        private int DepartmentId;
        private String DepartmentName;
        private int Position;
        private String PositionName;
        private int DoctorLevelId;
        private String DoctorLevelName;
        private String LevelShortName;
        private String LevelDesc;
        private int IsMajor;
        private String Advice;
        private String AdviceTime;
        private int IsSubmit;

        public int getSubjectDoctorId() {
            return SubjectDoctorId;
        }

        public void setSubjectDoctorId(int SubjectDoctorId) {
            this.SubjectDoctorId = SubjectDoctorId;
        }

        public int getSubjectBuyRecordId() {
            return SubjectBuyRecordId;
        }

        public void setSubjectBuyRecordId(int SubjectBuyRecordId) {
            this.SubjectBuyRecordId = SubjectBuyRecordId;
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

        public String getPicturePath() {
            return PicturePath;
        }

        public void setPicturePath(String PicturePath) {
            this.PicturePath = PicturePath;
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

        public int getDoctorLevelId() {
            return DoctorLevelId;
        }

        public void setDoctorLevelId(int DoctorLevelId) {
            this.DoctorLevelId = DoctorLevelId;
        }

        public String getDoctorLevelName() {
            return DoctorLevelName;
        }

        public void setDoctorLevelName(String DoctorLevelName) {
            this.DoctorLevelName = DoctorLevelName;
        }

        public String getLevelShortName() {
            return LevelShortName;
        }

        public void setLevelShortName(String LevelShortName) {
            this.LevelShortName = LevelShortName;
        }

        public String getLevelDesc() {
            return LevelDesc;
        }

        public void setLevelDesc(String LevelDesc) {
            this.LevelDesc = LevelDesc;
        }

        public int getIsMajor() {
            return IsMajor;
        }

        public void setIsMajor(int IsMajor) {
            this.IsMajor = IsMajor;
        }

        public String getAdvice() {
            return Advice;
        }

        public void setAdvice(String Advice) {
            this.Advice = Advice;
        }

        public String getAdviceTime() {
            return AdviceTime;
        }

        public void setAdviceTime(String AdviceTime) {
            this.AdviceTime = AdviceTime;
        }

        public int getIsSubmit() {
            return IsSubmit;
        }

        public void setIsSubmit(int IsSubmit) {
            this.IsSubmit = IsSubmit;
        }
    }

    @Override
    public String toString() {
        return "MDTDetailEntity{" +
                "SubjectBuyRecordId=" + SubjectBuyRecordId +
                ", SubjectRecordId=" + SubjectRecordId +
                ", PatientId=" + PatientId +
                ", PatientName='" + PatientName + '\'' +
                ", PatientMobile='" + PatientMobile + '\'' +
                ", PatientSex=" + PatientSex +
                ", PatientAge='" + PatientAge + '\'' +
                ", PatPicturePath='" + PatPicturePath + '\'' +
                ", SpecialistHosGroupId=" + SpecialistHosGroupId +
                ", HosGroupName='" + HosGroupName + '\'' +
                ", Description='" + Description + '\'' +
                ", Diseases='" + Diseases + '\'' +
                ", DiseasesTimeNo=" + DiseasesTimeNo +
                ", DiseasesTimeText='" + DiseasesTimeText + '\'' +
                ", SeeDoctorNo=" + SeeDoctorNo +
                ", SeeDoctorText='" + SeeDoctorText + '\'' +
                ", SeeDoctor='" + SeeDoctor + '\'' +
                ", SeeDepartment='" + SeeDepartment + '\'' +
                ", IsHasImg=" + IsHasImg +
                ", IsDrug=" + IsDrug +
                ", DrugUse='" + DrugUse + '\'' +
                ", OperationDes='" + OperationDes + '\'' +
                ", ChemoDes='" + ChemoDes + '\'' +
                ", ChronicDisease='" + ChronicDisease + '\'' +
                ", SeriousDisease='" + SeriousDisease + '\'' +
                ", IsAllergy=" + IsAllergy +
                ", AllergyDes='" + AllergyDes + '\'' +
                ", Advice='" + Advice + '\'' +
                ", ConsultAmount='" + ConsultAmount + '\'' +
                ", IMGroupId='" + IMGroupId + '\'' +
                ", PayStatus=" + PayStatus +
                ", PayOrderNo='" + PayOrderNo + '\'' +
                ", PayOrderTime='" + PayOrderTime + '\'' +
                ", PayTime='" + PayTime + '\'' +
                ", ReceiptTime='" + ReceiptTime + '\'' +
                ", IssueTime='" + IssueTime + '\'' +
                ", SubjectStatus=" + SubjectStatus +
                ", DrId=" + DrId +
                ", DrName='" + DrName + '\'' +
                ", DoctorLevelId=" + DoctorLevelId +
                ", DoctorLevelName='" + DoctorLevelName + '\'' +
                ", LevelShortName='" + LevelShortName + '\'' +
                ", LevelDesc='" + LevelDesc + '\'' +
                ", SubjectBuyRecordNo='" + SubjectBuyRecordNo + '\'' +
                ", SubjectReportPath='" + SubjectReportPath + '\'' +
                ", SubjectType=" + SubjectType +
                ", AllDrIds='" + AllDrIds + '\'' +
                ", AllDrNames='" + AllDrNames + '\'' +
                ", MDTReportDoctors=" + MDTReportDoctors +
                ", ConsultDrId=" + ConsultDrId +
                ", MajorDrId=" + MajorDrId +
                ", IsNext=" + IsNext +
                ", NextBuyRecordId=" + NextBuyRecordId +
                '}';
    }
}
