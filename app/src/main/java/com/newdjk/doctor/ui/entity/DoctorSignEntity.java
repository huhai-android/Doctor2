package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   DoctorSignEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/1/21 15:51
 *  @描述：
 */
public class DoctorSignEntity {

    /**
     * DrId : 1
     * DrName : sample string 2
     * PicturePath : sample string 3
     * Position : sample string 4
     * CurrentIntegral : 5
     * IsSignin : true
     */

    private int DrId;
    private String DrName;
    private String PicturePath;
    private String Position;
    private int CurrentIntegral;
    private int SigninEarnIntegral;

    public int getSigninEarnIntegral() {
        return SigninEarnIntegral;
    }

    public void setSigninEarnIntegral(int signinEarnIntegral) {
        SigninEarnIntegral = signinEarnIntegral;
    }

    private boolean IsSignin;
    private boolean IsOpenSignin;

    public boolean isSignin() {
        return IsSignin;
    }

    public void setSignin(boolean signin) {
        IsSignin = signin;
    }

    public boolean isOpenSignin() {
        return IsOpenSignin;
    }

    public void setOpenSignin(boolean openSignin) {
        IsOpenSignin = openSignin;
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

    public String getPicturePath() {
        return PicturePath;
    }

    public void setPicturePath(String PicturePath) {
        this.PicturePath = PicturePath;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String Position) {
        this.Position = Position;
    }

    public int getCurrentIntegral() {
        return CurrentIntegral;
    }

    public void setCurrentIntegral(int CurrentIntegral) {
        this.CurrentIntegral = CurrentIntegral;
    }

    public boolean isIsSignin() {
        return IsSignin;
    }

    public void setIsSignin(boolean IsSignin) {
        this.IsSignin = IsSignin;
    }
}
