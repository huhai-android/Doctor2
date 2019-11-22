package com.newdjk.doctor.ui.entity;

public class ServicePackageDetails {
    private String ServiceItemName;
    private String MasterPicture;
    private String SericeItemCode;
    private String Description;
    private int ServiceDetailId;
    private int ServicePackId;
    private int ServiceItemId;
    private int NumValue;
    private int NumType;
    private double EachPrice;
    private double TotalPrice;

    public String getServiceItemName() {
        return ServiceItemName;
    }

    public void setServiceItemName(String serviceItemName) {
        ServiceItemName = serviceItemName;
    }

    public String getMasterPicture() {
        return MasterPicture;
    }

    public void setMasterPicture(String masterPicture) {
        MasterPicture = masterPicture;
    }

    public String getSericeItemCode() {
        return SericeItemCode;
    }

    public void setSericeItemCode(String sericeItemCode) {
        SericeItemCode = sericeItemCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getServiceDetailId() {
        return ServiceDetailId;
    }

    public void setServiceDetailId(int serviceDetailId) {
        ServiceDetailId = serviceDetailId;
    }

    public int getServicePackId() {
        return ServicePackId;
    }

    public void setServicePackId(int servicePackId) {
        ServicePackId = servicePackId;
    }

    public int getServiceItemId() {
        return ServiceItemId;
    }

    public void setServiceItemId(int serviceItemId) {
        ServiceItemId = serviceItemId;
    }

    public int getNumValue() {
        return NumValue;
    }

    public void setNumValue(int numValue) {
        NumValue = numValue;
    }

    public int getNumType() {
        return NumType;
    }

    public void setNumType(int numType) {
        NumType = numType;
    }

    public double getEachPrice() {
        return EachPrice;
    }

    public void setEachPrice(double eachPrice) {
        EachPrice = eachPrice;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }
}
