package com.newdjk.doctor.ui.entity;

import java.util.List;

public class MedicalItemsEntity {

    /**
     * MedicalRecordId : 1
     * UnDefalutMedicals : [{"MedicalId":1,"MedicalName":"sample string 2","MedicalRecordId":3,"MedicalDesc":"sample string 4","MedicalOrder":5,"PatientId":6,"PatientName":"sample string 7","IsMust":64,"IsCustom":64,"IsPerfect":64,"SupplyStatus":64,"Remark":"sample string 12","Invalid":64,"CreateTime":"2018-12-20T17:59:36.1582634+08:00","UpdateTime":"2018-12-20T17:59:36.1582634+08:00"},{"MedicalId":1,"MedicalName":"sample string 2","MedicalRecordId":3,"MedicalDesc":"sample string 4","MedicalOrder":5,"PatientId":6,"PatientName":"sample string 7","IsMust":64,"IsCustom":64,"IsPerfect":64,"SupplyStatus":64,"Remark":"sample string 12","Invalid":64,"CreateTime":"2018-12-20T17:59:36.1582634+08:00","UpdateTime":"2018-12-20T17:59:36.1582634+08:00"}]
     * DefinedMedicals : [{"MedicalId":1,"MedicalName":"sample string 2","MedicalRecordId":3,"MedicalDesc":"sample string 4","MedicalOrder":5,"PatientId":6,"PatientName":"sample string 7","IsMust":64,"IsCustom":64,"IsPerfect":64,"SupplyStatus":64,"Remark":"sample string 12","Invalid":64,"CreateTime":"2018-12-20T17:59:36.1582634+08:00","UpdateTime":"2018-12-20T17:59:36.1582634+08:00"},{"MedicalId":1,"MedicalName":"sample string 2","MedicalRecordId":3,"MedicalDesc":"sample string 4","MedicalOrder":5,"PatientId":6,"PatientName":"sample string 7","IsMust":64,"IsCustom":64,"IsPerfect":64,"SupplyStatus":64,"Remark":"sample string 12","Invalid":64,"CreateTime":"2018-12-20T17:59:36.1582634+08:00","UpdateTime":"2018-12-20T17:59:36.1582634+08:00"}]
     */

    private int MedicalRecordId;
    private List<MedicalsBean> UnDefalutMedicals;
    private List<MedicalsBean> DefinedMedicals;

    public int getMedicalRecordId() {
        return MedicalRecordId;
    }

    public void setMedicalRecordId(int MedicalRecordId) {
        this.MedicalRecordId = MedicalRecordId;
    }

    public List<MedicalsBean> getUnDefalutMedicals() {
        return UnDefalutMedicals;
    }

    public void setUnDefalutMedicals(List<MedicalsBean> UnDefalutMedicals) {
        this.UnDefalutMedicals = UnDefalutMedicals;
    }

    public List<MedicalsBean> getDefinedMedicals() {
        return DefinedMedicals;
    }

    public void setDefinedMedicals(List<MedicalsBean> DefinedMedicals) {
        this.DefinedMedicals = DefinedMedicals;
    }

    public static class MedicalsBean {
        /**
         * MedicalId : 1
         * MedicalName : sample string 2
         * MedicalRecordId : 3
         * MedicalDesc : sample string 4
         * MedicalOrder : 5
         * PatientId : 6
         * PatientName : sample string 7
         * IsMust : 64
         * IsCustom : 64
         * IsPerfect : 64
         * SupplyStatus : 64
         * Remark : sample string 12
         * Invalid : 64
         * CreateTime : 2018-12-20T17:59:36.1582634+08:00
         * UpdateTime : 2018-12-20T17:59:36.1582634+08:00
         */

        private int MedicalId;
        private String MedicalName;
        private int MedicalRecordId;
        private String MedicalDesc;
        private int MedicalOrder;
        private int PatientId;
        private String PatientName;
        private int IsMust;
        private int IsCustom;
        private int IsPerfect;
        private int SupplyStatus;
        private String Remark;
        private int Invalid;
        private String CreateTime;
        private String UpdateTime;
        private boolean isSelected = false;

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

        public int getMedicalRecordId() {
            return MedicalRecordId;
        }

        public void setMedicalRecordId(int MedicalRecordId) {
            this.MedicalRecordId = MedicalRecordId;
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

        public int getIsMust() {
            return IsMust;
        }

        public void setIsMust(int IsMust) {
            this.IsMust = IsMust;
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

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }


}
