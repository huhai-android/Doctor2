package com.newdjk.doctor.ui.entity;

public class ServiceItemPriceEntity {

    /**
     * IsSet : 64
     * CommonPrice : 2.0
     * Price : 3.0
     */

    private int IsSet;
    private double CommonPrice;
    private double Price;

    public int getIsSet() {
        return IsSet;
    }

    public void setIsSet(int IsSet) {
        this.IsSet = IsSet;
    }

    public double getCommonPrice() {
        return CommonPrice;
    }

    public void setCommonPrice(double CommonPrice) {
        this.CommonPrice = CommonPrice;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }
}
