package com.newdjk.doctor.ui.entity;

import java.util.List;

public class ServicePackageEntity {
    private String ServicePackName;
    private String Description;
    private String FitPeople;

    private int ServicePackId;
    private double OriginalPrice;
    private double Price;
    private int PackStatus;
    private int ServiceLong;
    private int ServiceLongType;
    private int BuyNum;
   private List<ServicePackageDetails>  Details;

    public String getServicePackName() {
        return ServicePackName;
    }

    public void setServicePackName(String servicePackName) {
        ServicePackName = servicePackName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getFitPeople() {
        return FitPeople;
    }

    public void setFitPeople(String fitPeople) {
        FitPeople = fitPeople;
    }

    public int getServicePackId() {
        return ServicePackId;
    }

    public void setServicePackId(int servicePackId) {
        ServicePackId = servicePackId;
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

    public int getPackStatus() {
        return PackStatus;
    }

    public void setPackStatus(int packStatus) {
        PackStatus = packStatus;
    }

    public int getServiceLong() {
        return ServiceLong;
    }

    public void setServiceLong(int serviceLong) {
        ServiceLong = serviceLong;
    }

    public int getServiceLongType() {
        return ServiceLongType;
    }

    public void setServiceLongType(int serviceLongType) {
        ServiceLongType = serviceLongType;
    }

    public int getBuyNum() {
        return BuyNum;
    }

    public void setBuyNum(int buyNum) {
        BuyNum = buyNum;
    }

    public List<ServicePackageDetails> getDetails() {
        return Details;
    }

    public void setDetails(List<ServicePackageDetails> details) {
        Details = details;
    }
}
