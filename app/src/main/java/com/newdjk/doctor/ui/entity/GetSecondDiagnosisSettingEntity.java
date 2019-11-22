package com.newdjk.doctor.ui.entity;

import java.io.Serializable;
import java.util.List;

public class GetSecondDiagnosisSettingEntity {



    /**
     * ServiceItemId : 1
     * SericeItemCode : sample string 2
     * DrId : 3
     * DrName : sample string 4
     * OrgId : 5
     * OrgName : sample string 6
     * OrgPath : sample string 7
     * OriginalPrice : 8.0
     * Price : 9.0
     * NumberSource : 10
     * IsOn : 64
     * DoctorDiseases : [{"DrDiseaseId":1,"DiseaseId":2,"DiseaseName":"sample string 3","DrId":4,"DrName":"sample string 5","Invalid":64,"CreateTime":"2018-12-24T15:12:39.34804+08:00","UpdateTime":"2018-12-24T15:12:39.34804+08:00"},{"DrDiseaseId":1,"DiseaseId":2,"DiseaseName":"sample string 3","DrId":4,"DrName":"sample string 5","Invalid":64,"CreateTime":"2018-12-24T15:12:39.34804+08:00","UpdateTime":"2018-12-24T15:12:39.34804+08:00"}]
     */



    private int ServiceItemId;
    private String SericeItemCode;
    private int DrId;
    private String DrName;
    private int OrgId;
    private String OrgName;
    private String OrgPath;
    private double OriginalPrice;
    private double Price;
    private int NumberSource;
    private int IsOn;
    private int IsRecommend;

    public int getIsRecommend() {
        return IsRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        IsRecommend = isRecommend;
    }

    private String PriceRemark;
    private List<DoctorDiseasesBean> DoctorDiseases;

    public String getPriceRemark() {
        return PriceRemark;
    }

    public void setPriceRemark(String priceRemark) {
        PriceRemark = priceRemark;
    }

    public int getServiceItemId() {
        return ServiceItemId;
    }

    public void setServiceItemId(int ServiceItemId) {
        this.ServiceItemId = ServiceItemId;
    }

    public String getSericeItemCode() {
        return SericeItemCode;
    }

    public void setSericeItemCode(String SericeItemCode) {
        this.SericeItemCode = SericeItemCode;
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

    public int getOrgId() {
        return OrgId;
    }

    public void setOrgId(int OrgId) {
        this.OrgId = OrgId;
    }

    public String getOrgName() {
        return OrgName;
    }

    public void setOrgName(String OrgName) {
        this.OrgName = OrgName;
    }

    public String getOrgPath() {
        return OrgPath;
    }

    public void setOrgPath(String OrgPath) {
        this.OrgPath = OrgPath;
    }

    public double getOriginalPrice() {
        return OriginalPrice;
    }

    public void setOriginalPrice(double OriginalPrice) {
        this.OriginalPrice = OriginalPrice;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public int getNumberSource() {
        return NumberSource;
    }

    public void setNumberSource(int NumberSource) {
        this.NumberSource = NumberSource;
    }

    public int getIsOn() {
        return IsOn;
    }

    public void setIsOn(int IsOn) {
        this.IsOn = IsOn;
    }

    public List<DoctorDiseasesBean> getDoctorDiseases() {
        return DoctorDiseases;
    }

    public void setDoctorDiseases(List<DoctorDiseasesBean> DoctorDiseases) {
        this.DoctorDiseases = DoctorDiseases;
    }



    public static class DoctorDiseasesBean implements Serializable {
        /**
         * DrDiseaseId : 1
         * DiseaseId : 2
         * DiseaseName : sample string 3
         * DrId : 4
         * DrName : sample string 5
         * Invalid : 64
         * CreateTime : 2018-12-24T15:12:39.34804+08:00
         * UpdateTime : 2018-12-24T15:12:39.34804+08:00
         */

        private int DrDiseaseId;
        private int DiseaseId;
        private String DiseaseName;
        private int DrId;
        private String DrName;
        private int Invalid;
        private String CreateTime;
        private String UpdateTime;

        public int getDrDiseaseId() {
            return DrDiseaseId;
        }

        public void setDrDiseaseId(int DrDiseaseId) {
            this.DrDiseaseId = DrDiseaseId;
        }

        public int getDiseaseId() {
            return DiseaseId;
        }

        public void setDiseaseId(int DiseaseId) {
            this.DiseaseId = DiseaseId;
        }

        public String getDiseaseName() {
            return DiseaseName;
        }

        public void setDiseaseName(String DiseaseName) {
            this.DiseaseName = DiseaseName;
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
    }
}
