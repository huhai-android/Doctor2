package com.newdjk.doctor.ui.entity;

public class SymptomEntity {

    /**
     * DrDiseaseId : 1
     * DiseaseId : 2
     * DiseaseName : sample string 3
     * DrId : 4
     * DrName : sample string 5
     * Invalid : 64
     * CreateTime : 2018-12-20T10:08:41.325955+08:00
     * UpdateTime : 2018-12-20T10:08:41.325955+08:00
     */

    private int DrDiseaseId;
    private int DiseaseId;
    private String DiseaseName;
    private int DrId;
    private String DrName;
    private int Invalid;
    private String CreateTime;
    private String UpdateTime;
    private boolean isSelected;

    public int getDrDiseaseId() {
        return DrDiseaseId;
    }

    public void setDrDiseaseId(int DrDiseaseId) {
        this.DrDiseaseId = DrDiseaseId;
    }

    public int getDiseaseId() {
        return DiseaseId;
    }

    public void setDiseaseId(int DiseaseId) {
        this.DiseaseId = DiseaseId;
    }

    public String getDiseaseName() {
        return DiseaseName;
    }

    public void setDiseaseName(String DiseaseName) {
        this.DiseaseName = DiseaseName;
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
