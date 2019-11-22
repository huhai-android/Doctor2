package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   DoctorReportSettingEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/7 9:12
 *  @描述：
 */
public class DoctorReportSettingEntity {

    /**
     * IsOpen : true
     * ReceptionNum : 1
     * UnitPrice : 1.0
     * DiscountPrice : 1.0
     */

    private boolean IsOpen;
    private int ReceptionNum;
    private double UnitPrice;
    private double DiscountPrice;

    public boolean isIsOpen() {
        return IsOpen;
    }

    public void setIsOpen(boolean IsOpen) {
        this.IsOpen = IsOpen;
    }

    public int getReceptionNum() {
        return ReceptionNum;
    }

    public void setReceptionNum(int ReceptionNum) {
        this.ReceptionNum = ReceptionNum;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double UnitPrice) {
        this.UnitPrice = UnitPrice;
    }

    public double getDiscountPrice() {
        return DiscountPrice;
    }

    public void setDiscountPrice(double DiscountPrice) {
        this.DiscountPrice = DiscountPrice;
    }

    @Override
    public String toString() {
        return "DoctorReportSettingEntity{" +
                "IsOpen=" + IsOpen +
                ", ReceptionNum=" + ReceptionNum +
                ", UnitPrice=" + UnitPrice +
                ", DiscountPrice=" + DiscountPrice +
                '}';
    }
}
