package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   LoginSuccess
 *  @创建者:   huhai
 *  @创建时间:  2019/4/15 15:56
 *  @描述：
 */
public class LoginSuccess {
    boolean issuccess;

    public LoginSuccess(boolean issuccess) {
        this.issuccess = issuccess;
    }

    public boolean isIssuccess() {
        return issuccess;
    }

    public void setIssuccess(boolean issuccess) {
        this.issuccess = issuccess;
    }
}
