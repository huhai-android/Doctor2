package com.newdjk.doctor.ui.entity;

public class RecordDataEntity {
    private RecordInfoEntity RecordInfo;
    private PatientInfoEntity PatientInfo;
    private DoctorPatientRelationEntity DoctorPatientRelation;
    private String PatientIMId;

    public RecordInfoEntity getRecordInfo() {
        return RecordInfo;
    }

    public void setRecordInfo(RecordInfoEntity recordInfo) {
        RecordInfo = recordInfo;
    }

    public PatientInfoEntity getPatientInfo() {
        return PatientInfo;
    }

    public void setPatientInfo(PatientInfoEntity patientInfo) {
        PatientInfo = patientInfo;
    }

    public DoctorPatientRelationEntity getDoctorPatientRelation() {
        return DoctorPatientRelation;
    }

    public void setDoctorPatientRelation(DoctorPatientRelationEntity doctorPatientRelation) {
        DoctorPatientRelation = doctorPatientRelation;
    }

    public String getPatientIMId() {
        return PatientIMId;
    }

    public void setPatientIMId(String patientIMId) {
        PatientIMId = patientIMId;
    }
}
