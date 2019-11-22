package com.newdjk.doctor.ui.entity;

public class HospitalMessageDataEntity {
    private int HospitalId;
    private int HospitalLevel;
    private int Order;
    private int IsHot;
    private int IsDisplay;
    private int Invalid;
    private String HospitalName;
    private String CreateTime;
    private String UpdateTime;

    public int getHospitalId() {
        return HospitalId;
    }

    public void setHospitalId(int hospitalId) {
        HospitalId = hospitalId;
    }

    public int getHospitalLevel() {
        return HospitalLevel;
    }

    public void setHospitalLevel(int hospitalLevel) {
        HospitalLevel = hospitalLevel;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int order) {
        Order = order;
    }

    public int getIsHot() {
        return IsHot;
    }

    public void setIsHot(int isHot) {
        IsHot = isHot;
    }

    public int getIsDisplay() {
        return IsDisplay;
    }

    public void setIsDisplay(int isDisplay) {
        IsDisplay = isDisplay;
    }

    public int getInvalid() {
        return Invalid;
    }

    public void setInvalid(int invalid) {
        Invalid = invalid;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
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
}
