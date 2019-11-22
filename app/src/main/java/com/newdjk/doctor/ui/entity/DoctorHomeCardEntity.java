package com.newdjk.doctor.ui.entity;

public class DoctorHomeCardEntity {
    private String QRCodePath;
    private String QRCodeUrl;
    private String CreateTime;
    private String UpdateTime;

    private int DrQRCodeId;
    private int DrId;
    private int OrgId;
    private int HosGroupId;
    private int Invalid;

    public String getQRCodePath() {
        return QRCodePath;
    }

    public void setQRCodePath(String QRCodePath) {
        this.QRCodePath = QRCodePath;
    }

    public String getQRCodeUrl() {
        return QRCodeUrl;
    }

    public void setQRCodeUrl(String QRCodeUrl) {
        this.QRCodeUrl = QRCodeUrl;
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

    public int getDrQRCodeId() {
        return DrQRCodeId;
    }

    public void setDrQRCodeId(int drQRCodeId) {
        DrQRCodeId = drQRCodeId;
    }

    public int getDrId() {
        return DrId;
    }

    public void setDrId(int drId) {
        DrId = drId;
    }

    public int getOrgId() {
        return OrgId;
    }

    public void setOrgId(int orgId) {
        OrgId = orgId;
    }

    public int getHosGroupId() {
        return HosGroupId;
    }

    public void setHosGroupId(int hosGroupId) {
        HosGroupId = hosGroupId;
    }

    public int getInvalid() {
        return Invalid;
    }

    public void setInvalid(int invalid) {
        Invalid = invalid;
    }
}
