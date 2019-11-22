package com.newdjk.doctor.ui.entity;

public class ConsultSettingEntity {
    private String DrName;
    //private String OrgName;
    //private String OrgPath;
    private String OriginalPrice;
    private String  Price;

    //private int ItemOpenId;
    private int ServiceItemId;
    private int SericeItemCode;
    private int DrId;
    private int OrgId;
    private String NumberSource;
    private int IsOn;
    private String PriceRemark;
    private int IsRecommend;

    public int getIsRecommend() {
        return IsRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        IsRecommend = isRecommend;
    }

    public int getIsOn() {
        return IsOn;
    }

    public void setIsOn(int isOn) {
        IsOn = isOn;
    }

    public String getPriceRemark() {
        return PriceRemark;
    }

    public void setPriceRemark(String priceRemark) {
        PriceRemark = priceRemark;
    }

    public String getDrName() {
        return DrName;
    }

    public void setDrName(String drName) {
        DrName = drName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public int getServiceItemId() {
        return ServiceItemId;
    }

    public void setServiceItemId(int serviceItemId) {
        ServiceItemId = serviceItemId;
    }

    public int getSericeItemCode() {
        return SericeItemCode;
    }

    public void setSericeItemCode(int sericeItemCode) {
        SericeItemCode = sericeItemCode;
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

    public String getNumberSource() {
        return NumberSource;
    }

    public void setNumberSource(String numberSource) {
        NumberSource = numberSource;
    }



    public int isOn() {
        return IsOn;
    }

    public void setOn(int on) {
        IsOn = on;
    }

    public String getOriginalPrice() {
        return OriginalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        OriginalPrice = originalPrice;
    }
}
