package com.newdjk.doctor.ui.entity;

import java.io.Serializable;

public class DoctorPatientRelationEntity implements Serializable {
    private String CreateTime;
    private String UpdateTime;
    private int DrPatientId;
    private int DrId;
    private int PatientId;
    private int RelationStatus;
    private int IsDrKey;
    private int IsPatientMain;
    private String Disease;
    private int Invalid;

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public int getDrPatientId() {
        return DrPatientId;
    }

    public void setDrPatientId(int drPatientId) {
        DrPatientId = drPatientId;
    }

    public int getDrId() {
        return DrId;
    }

    public void setDrId(int drId) {
        DrId = drId;
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

    public String getDisease() {
        return Disease;
    }

    public void setDisease(String disease) {
        Disease = disease;
    }

    public int getInvalid() {
        return Invalid;
    }

    public void setInvalid(int invalid) {
        Invalid = invalid;
    }
}
