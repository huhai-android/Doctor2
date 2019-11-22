package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   AdviceDoctorEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/1/21 17:18
 *  @描述：
 */
public class AdviceDoctorEntity {

    /**
     * RecomCodeId : 1
     * DrId : 2
     * DrName : sample string 3
     * CodeAddress : sample string 4
     * LinkAddress : sample string 5
     * Invalid : 64
     * CreateTime : 2019-01-21T17:07:31.4711219+08:00
     * UpdateTime : 2019-01-21T17:07:31.4711219+08:00
     */

    private int RecomCodeId;
    private int DrId;
    private String DrName;
    private String CodeAddress;
    private String LinkAddress;
    private int Invalid;
    private String CreateTime;
    private String UpdateTime;

    public int getRecomCodeId() {
        return RecomCodeId;
    }

    public void setRecomCodeId(int RecomCodeId) {
        this.RecomCodeId = RecomCodeId;
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

    public String getCodeAddress() {
        return CodeAddress;
    }

    public void setCodeAddress(String CodeAddress) {
        this.CodeAddress = CodeAddress;
    }

    public String getLinkAddress() {
        return LinkAddress;
    }

    public void setLinkAddress(String LinkAddress) {
        this.LinkAddress = LinkAddress;
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

    @Override
    public String toString() {
        return "AdviceDoctorEntity{" +
                "RecomCodeId=" + RecomCodeId +
                ", DrId=" + DrId +
                ", DrName='" + DrName + '\'' +
                ", CodeAddress='" + CodeAddress + '\'' +
                ", LinkAddress='" + LinkAddress + '\'' +
                ", Invalid=" + Invalid +
                ", CreateTime='" + CreateTime + '\'' +
                ", UpdateTime='" + UpdateTime + '\'' +
                '}';
    }
}
