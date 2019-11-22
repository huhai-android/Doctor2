package com.newdjk.doctor.ui.entity;

import java.util.List;

public class LookMedicalEntity {

    /**
     * PatientId : 1
     * PatientName : sample string 2
     * PersonalDesc : sample string 3
     * PatientMedicals : [{"MedicalId":1,"MedicalName":"sample string 2","MedicalRecordId":3,"MedicalDesc":"sample string 4","MedicalOrder":5,"IsMust":64,"IsCustom":64,"IsPerfect":64,"Remark":"sample string 9","ImagePahts":[{"ImagePath":"sample string 1"},{"ImagePath":"sample string 1"}]},{"MedicalId":1,"MedicalName":"sample string 2","MedicalRecordId":3,"MedicalDesc":"sample string 4","MedicalOrder":5,"IsMust":64,"IsCustom":64,"IsPerfect":64,"Remark":"sample string 9","ImagePahts":[{"ImagePath":"sample string 1"},{"ImagePath":"sample string 1"}]}]
     */

    private int PatientId;
    private String PatientName;
    private String PersonalDesc;
    private List<PatientMedicalsBean> PatientMedicals;

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

    public String getPersonalDesc() {
        return PersonalDesc;
    }

    public void setPersonalDesc(String PersonalDesc) {
        this.PersonalDesc = PersonalDesc;
    }

    public List<PatientMedicalsBean> getPatientMedicals() {
        return PatientMedicals;
    }

    public void setPatientMedicals(List<PatientMedicalsBean> PatientMedicals) {
        this.PatientMedicals = PatientMedicals;
    }

    public static class PatientMedicalsBean {
        /**
         * MedicalId : 1
         * MedicalName : sample string 2
         * MedicalRecordId : 3
         * MedicalDesc : sample string 4
         * MedicalOrder : 5
         * IsMust : 64
         * IsCustom : 64
         * IsPerfect : 64
         * Remark : sample string 9
         * ImagePahts : [{"ImagePath":"sample string 1"},{"ImagePath":"sample string 1"}]
         */

        private int MedicalId;
        private String MedicalName;
        private int MedicalRecordId;
        private String MedicalDesc;
        private int MedicalOrder;
        private int IsMust;
        private int IsCustom;
        private int IsPerfect;
        private String Remark;
        private List<ImagePahtsBean> ImagePahts;

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

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public List<ImagePahtsBean> getImagePahts() {
            return ImagePahts;
        }

        public void setImagePahts(List<ImagePahtsBean> ImagePahts) {
            this.ImagePahts = ImagePahts;
        }

        public static class ImagePahtsBean {
            /**
             * ImagePath : sample string 1
             */

            private String ImagePath;

            public String getImagePath() {
                return ImagePath;
            }

            public void setImagePath(String ImagePath) {
                this.ImagePath = ImagePath;
            }
        }
    }
}
