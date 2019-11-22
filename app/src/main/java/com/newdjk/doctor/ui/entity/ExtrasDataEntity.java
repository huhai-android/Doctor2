package com.newdjk.doctor.ui.entity;

public class ExtrasDataEntity  {
        private String time;
        private int endStauts;
        private int serviceCode;
        private int type;
        private Data data;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getEndStauts() {
            return endStauts;
        }

        public void setEndStauts(int endStauts) {
            this.endStauts = endStauts;
        }

        public int getServiceCode() {
            return serviceCode;
        }

        public void setServiceCode(int serviceCode) {
            this.serviceCode = serviceCode;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }



public class Data {
    private String DrName;
    private String PatientName;
    private int DrId;
    private int IsDrKey;
    private int IsPatientMain;
    private int PatientId;
    private int RelationStatus;
    private String RecordId;
    private String  PrescriptionId;
    private int MedicalRecordId;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;
    public String getDrName() {
        return DrName;
    }

    public void setDrName(String drName) {
        DrName = drName;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public int getDrId() {
        return DrId;
    }

    public void setDrId(int drId) {
        DrId = drId;
    }

    public int getIsDrKey() {
        return IsDrKey;
    }

    public void setIsDrKey(int isDrKey) {
        IsDrKey = isDrKey;
    }

    public int getIsPatientMain() {
        return IsPatientMain;
    }

    public void setIsPatientMain(int isPatientMain) {
        IsPatientMain = isPatientMain;
    }

    public int getPatientId() {
        return PatientId;
    }

    public void setPatientId(int patientId) {
        PatientId = patientId;
    }

    public int getRelationStatus() {
        return RelationStatus;
    }

    public void setRelationStatus(int relationStatus) {
        RelationStatus = relationStatus;
    }

    public String getRecordId() {
        return RecordId;
    }

    public void setRecordId(String recordId) {
        RecordId = recordId;
    }

    public String getPrescriptionId() {
        return PrescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        PrescriptionId = prescriptionId;
    }

    public int getMedicalRecordId() {
        return MedicalRecordId;
    }

    public void setMedicalRecordId(int medicalRecordId) {
        MedicalRecordId = medicalRecordId;
    }
}

}
