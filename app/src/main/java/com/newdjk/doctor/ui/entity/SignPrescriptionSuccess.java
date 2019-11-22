package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   SignReportSuccess
 *  @创建者:   huhai
 *  @创建时间:  2018/11/8 16:42
 *  @描述：
 */
public class SignPrescriptionSuccess {
    public  boolean signResule;
    public boolean isRefuse;

    public boolean isSignResule() {
        return signResule;
    }

    public void setSignResule(boolean signResule) {
        this.signResule = signResule;
    }

    public SignPrescriptionSuccess(boolean signResule) {
        this.signResule = signResule;
    }

    public boolean isRefuse() {
        return isRefuse;
    }

    public void setRefuse(boolean refuse) {
        isRefuse = refuse;
    }

    public SignPrescriptionSuccess(boolean signResule, boolean isRefuse) {
        this.signResule = signResule;
        this.isRefuse = isRefuse;
    }
}
