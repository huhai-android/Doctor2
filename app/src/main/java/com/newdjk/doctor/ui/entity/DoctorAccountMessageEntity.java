package com.newdjk.doctor.ui.entity;

public class DoctorAccountMessageEntity {
    private String  DrName;
    private String Mobile;
    private String DrAccount;
    private int DrId;

    public String getDrName() {
        return DrName;
    }

    public void setDrName(String drName) {
        DrName = drName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getDrAccount() {
        return DrAccount;
    }

    public void setDrAccount(String drAccount) {
        DrAccount = drAccount;
    }

    public int getDrId() {
        return DrId;
    }

    public void setDrId(int drId) {
        DrId = drId;
    }
}
