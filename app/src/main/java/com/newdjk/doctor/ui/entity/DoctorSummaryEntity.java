package com.newdjk.doctor.ui.entity;

public class DoctorSummaryEntity {
    private String DrName;
    private String PositionName;
    private String PicturePath;
    private String TreatMent;
    private String  DoctorSkill;
    private String Description;
    private int DrId;
    private int Position;
    private double PraiseRate;
    private double TotalIncome;
    private int PatientCount;

    public String getDrName() {
        return DrName;
    }

    public void setDrName(String drName) {
        DrName = drName;
    }

    public String getPositionName() {
        return PositionName;
    }

    public void setPositionName(String positionName) {
        PositionName = positionName;
    }

    public String getPicturePath() {
        return PicturePath;
    }

    public void setPicturePath(String picturePath) {
        PicturePath = picturePath;
    }

    public String getTreatMent() {
        return TreatMent;
    }

    public void setTreatMent(String treatMent) {
        TreatMent = treatMent;
    }

    public String getDoctorSkill() {
        return DoctorSkill;
    }

    public void setDoctorSkill(String doctorSkill) {
        DoctorSkill = doctorSkill;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getDrId() {
        return DrId;
    }

    public void setDrId(int drId) {
        DrId = drId;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public double getPraiseRate() {
        return PraiseRate;
    }

    public void setPraiseRate(double praiseRate) {
        PraiseRate = praiseRate;
    }

    public double getTotalIncome() {
        return TotalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        TotalIncome = totalIncome;
    }

    public int getPatientCount() {
        return PatientCount;
    }

    public void setPatientCount(int patientCount) {
        PatientCount = patientCount;
    }
}
