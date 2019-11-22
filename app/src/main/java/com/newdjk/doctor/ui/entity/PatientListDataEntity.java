package com.newdjk.doctor.ui.entity;

public class PatientListDataEntity {
    private String sortLetters;
    private int DrPatientId;
    private int DrId;
    private String  DrName;
    private String DrPicturePath;
    private String  PatientName;
    private String  Birthday;
    private String  PaPicturePath;
    private String  CreateTime;
    private String  UpdateTime;
    private int PatientId;
    private String  NameLetter;
    private String Age;
    private int RelationStatus;
    private int IsDrKey;
    private int PatientSex;
    private int IsPatientMain;
    private String Disease;
    private int AccountId;
    private String PatientIMId;
    private boolean iselect;

    public boolean isIselect() {
        return iselect;
    }

    public void setIselect(boolean iselect) {
        this.iselect = iselect;
    }

    public String getSortLetters() {
        return sortLetters;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
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

    public String getDrName() {
        return DrName;
    }

    public void setDrName(String drName) {
        DrName = drName;
    }

    public String getDrPicturePath() {
        return DrPicturePath;
    }

    public void setDrPicturePath(String drPicturePath) {
        DrPicturePath = drPicturePath;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
     }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getPaPicturePath() {
        return PaPicturePath;
    }

    public void setPaPicturePath(String paPicturePath) {
        PaPicturePath = paPicturePath;
    }

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

    public int getPatientId() {
        return PatientId;
    }

    public void setPatientId(int patientId) {
        PatientId = patientId;
    }

    public String getNameLetter() {
        return NameLetter;
    }

    public void setNameLetter(String nameLetter) {
        NameLetter = nameLetter;
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

    public int getPatientSex() {
        return PatientSex;
    }

    public void setPatientSex(int patientSex) {
        PatientSex = patientSex;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }

    public String getPatientIMId() {
        return PatientIMId;
    }

    public void setPatientIMId(String patientIMId) {
        PatientIMId = patientIMId;
    }
}
