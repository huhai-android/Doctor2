package com.newdjk.doctor.ui.entity;

import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   MDTDocumentEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/9/11 16:36
 *  @描述：
 */
public class MDTDocumentEntity {

    /**
     * SubjectBuyRecordId : 112
     * UnDefalutMedicals : [{"MedicalId":424,"MedicalName":"门（急）诊病历","SubjectRecordId":74,"MedicalDesc":"请务必上传最近半年的门诊病历报告（门、急诊病历）","MedicalOrder":10,"PatientId":186,"PatientName":"庄周","IsRemark":0,"IsMust":0,"IsOther":0,"IsCustom":0,"IsPerfect":1,"SupplyStatus":0,"Remark":null,"Invalid":0,"CreateTime":"2019-09-11 11:38:54","UpdateTime":"2019-09-11 11:38:54"},{"MedicalId":425,"MedicalName":"住院病历（复印件）","SubjectRecordId":74,"MedicalDesc":"包括住院期间的化验、检查报告，病理报告，基因检测报告、治疗记录、手术和体检记录及出院小结等","MedicalOrder":9,"PatientId":186,"PatientName":"庄周","IsRemark":0,"IsMust":0,"IsOther":0,"IsCustom":0,"IsPerfect":1,"SupplyStatus":0,"Remark":null,"Invalid":0,"CreateTime":"2019-09-11 11:38:54","UpdateTime":"2019-09-11 11:38:54"},{"MedicalId":426,"MedicalName":"用药记录","SubjectRecordId":74,"MedicalDesc":"处方单等","MedicalOrder":8,"PatientId":186,"PatientName":"庄周","IsRemark":1,"IsMust":0,"IsOther":0,"IsCustom":0,"IsPerfect":1,"SupplyStatus":0,"Remark":null,"Invalid":0,"CreateTime":"2019-09-11 11:38:54","UpdateTime":"2019-09-11 11:38:54"},{"MedicalId":427,"MedicalName":"实验室检验单","SubjectRecordId":74,"MedicalDesc":"血液检验单、尿液检查单、大便检查单等","MedicalOrder":7,"PatientId":186,"PatientName":"庄周","IsRemark":0,"IsMust":0,"IsOther":0,"IsCustom":0,"IsPerfect":1,"SupplyStatus":0,"Remark":null,"Invalid":0,"CreateTime":"2019-09-11 11:38:54","UpdateTime":"2019-09-11 11:38:54"},{"MedicalId":428,"MedicalName":"检查报告单","SubjectRecordId":74,"MedicalDesc":"心电图、胃肠镜、喉镜检查单等","MedicalOrder":6,"PatientId":186,"PatientName":"庄周","IsRemark":0,"IsMust":0,"IsOther":0,"IsCustom":0,"IsPerfect":1,"SupplyStatus":0,"Remark":null,"Invalid":0,"CreateTime":"2019-09-11 11:38:54","UpdateTime":"2019-09-11 11:38:54"},{"MedicalId":429,"MedicalName":"病理资料","SubjectRecordId":74,"MedicalDesc":"病理诊断报告、病理切片及组织蜡块","MedicalOrder":5,"PatientId":186,"PatientName":"庄周","IsRemark":0,"IsMust":0,"IsOther":0,"IsCustom":0,"IsPerfect":1,"SupplyStatus":0,"Remark":null,"Invalid":0,"CreateTime":"2019-09-11 11:38:54","UpdateTime":"2019-09-11 11:38:54"},{"MedicalId":430,"MedicalName":"影像资料","SubjectRecordId":74,"MedicalDesc":"X线、CT、MRI、PET-CT、ECT、B超、骨扫描等高清摄影或扫描件资料","MedicalOrder":4,"PatientId":186,"PatientName":"庄周","IsRemark":0,"IsMust":0,"IsOther":0,"IsCustom":0,"IsPerfect":1,"SupplyStatus":0,"Remark":null,"Invalid":0,"CreateTime":"2019-09-11 11:38:54","UpdateTime":"2019-09-11 11:38:54"},{"MedicalId":431,"MedicalName":"放疗记录","SubjectRecordId":74,"MedicalDesc":"包括开始时间、放疗方式、靶区边界、射线类型，剂量等","MedicalOrder":3,"PatientId":186,"PatientName":"庄周","IsRemark":0,"IsMust":0,"IsOther":0,"IsCustom":0,"IsPerfect":1,"SupplyStatus":0,"Remark":null,"Invalid":0,"CreateTime":"2019-09-11 11:38:54","UpdateTime":"2019-09-11 11:38:54"},{"MedicalId":432,"MedicalName":"其他材料","SubjectRecordId":74,"MedicalDesc":"如肝炎、吸烟史、家族病史等情况","MedicalOrder":2,"PatientId":186,"PatientName":"庄周","IsRemark":0,"IsMust":0,"IsOther":1,"IsCustom":0,"IsPerfect":1,"SupplyStatus":0,"Remark":null,"Invalid":0,"CreateTime":"2019-09-11 11:38:54","UpdateTime":"2019-09-11 11:38:54"}]
     * DefinedMedicals : []
     */

    private int SubjectBuyRecordId;
    private List<UnDefalutMedicalsBean> UnDefalutMedicals;
    private List<UnDefalutMedicalsBean> DefinedMedicals;

    public int getSubjectBuyRecordId() {
        return SubjectBuyRecordId;
    }

    public void setSubjectBuyRecordId(int SubjectBuyRecordId) {
        this.SubjectBuyRecordId = SubjectBuyRecordId;
    }

    public List<UnDefalutMedicalsBean> getUnDefalutMedicals() {
        return UnDefalutMedicals;
    }

    public void setUnDefalutMedicals(List<UnDefalutMedicalsBean> UnDefalutMedicals) {
        this.UnDefalutMedicals = UnDefalutMedicals;
    }

    public List<UnDefalutMedicalsBean> getDefinedMedicals() {
        return DefinedMedicals;
    }

    public void setDefinedMedicals(List<UnDefalutMedicalsBean> DefinedMedicals) {
        this.DefinedMedicals = DefinedMedicals;
    }

    public static class UnDefalutMedicalsBean {
        /**
         * MedicalId : 424
         * MedicalName : 门（急）诊病历
         * SubjectRecordId : 74
         * MedicalDesc : 请务必上传最近半年的门诊病历报告（门、急诊病历）
         * MedicalOrder : 10
         * PatientId : 186
         * PatientName : 庄周
         * IsRemark : 0
         * IsMust : 0
         * IsOther : 0
         * IsCustom : 0
         * IsPerfect : 1
         * SupplyStatus : 0
         * Remark : null
         * Invalid : 0
         * CreateTime : 2019-09-11 11:38:54
         * UpdateTime : 2019-09-11 11:38:54
         */

        private int MedicalId;
        private String MedicalName;
        private int SubjectRecordId;
        private String MedicalDesc;
        private int MedicalOrder;
        private int PatientId;
        private String PatientName;
        private int IsRemark;
        private int IsMust;
        private int IsOther;
        private int IsCustom;
        private int IsPerfect;
        private int SupplyStatus;
        private String Remark;
        private int Invalid;
        private String CreateTime;
        private String UpdateTime;
        private boolean isSelected = false;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public int getMedicalId() {
            return MedicalId;
        }

        public void setMedicalId(int MedicalId) {
            this.MedicalId = MedicalId;
        }

        public String getMedicalName() {
            return MedicalName;
        }

        public void setMedicalName(String MedicalName) {
            this.MedicalName = MedicalName;
        }

        public int getSubjectRecordId() {
            return SubjectRecordId;
        }

        public void setSubjectRecordId(int SubjectRecordId) {
            this.SubjectRecordId = SubjectRecordId;
        }

        public String getMedicalDesc() {
            return MedicalDesc;
        }

        public void setMedicalDesc(String MedicalDesc) {
            this.MedicalDesc = MedicalDesc;
        }

        public int getMedicalOrder() {
            return MedicalOrder;
        }

        public void setMedicalOrder(int MedicalOrder) {
            this.MedicalOrder = MedicalOrder;
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

        public int getIsRemark() {
            return IsRemark;
        }

        public void setIsRemark(int IsRemark) {
            this.IsRemark = IsRemark;
        }

        public int getIsMust() {
            return IsMust;
        }

        public void setIsMust(int IsMust) {
            this.IsMust = IsMust;
        }

        public int getIsOther() {
            return IsOther;
        }

        public void setIsOther(int IsOther) {
            this.IsOther = IsOther;
        }

        public int getIsCustom() {
            return IsCustom;
        }

        public void setIsCustom(int IsCustom) {
            this.IsCustom = IsCustom;
        }

        public int getIsPerfect() {
            return IsPerfect;
        }

        public void setIsPerfect(int IsPerfect) {
            this.IsPerfect = IsPerfect;
        }

        public int getSupplyStatus() {
            return SupplyStatus;
        }

        public void setSupplyStatus(int SupplyStatus) {
            this.SupplyStatus = SupplyStatus;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
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
    }


}
