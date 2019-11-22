package com.newdjk.doctor.ui.entity;

public class DrServiceItemEntity {
    private int SericeItemCode;
    private double OriginalPrice;
    private double Price;
    private String SericeItemName;

    public int getSericeItemCode() {
        return SericeItemCode;
    }

    public void setSericeItemCode(int sericeItemCode) {
        SericeItemCode = sericeItemCode;
    }

    public double getOriginalPrice() {
        return OriginalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        OriginalPrice = originalPrice;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getSericeItemName() {
        return SericeItemName;
    }

    public void setSericeItemName(String sericeItemName) {
        SericeItemName = sericeItemName;
    }
}
