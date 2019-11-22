package com.newdjk.doctor.ui.activity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by EDZ on 2018/10/15.
 */

public class ServiceEntity implements Serializable {

    /**
     * Code : 0
     * Message :
     * Data : {"DrId":57,"DrName":"奥巴马","Mobile":"13622485183","DrType":1,"DrSex":3,"SericeItemCodes":"1101|图文问诊|12.00|0.01,1102|视频问诊|6.00|0.01,1103|名医续方|1.00|0.01","DrServiceItems":[{"SericeItemCode":"1101","SericeItemName":"图文问诊","OriginalPrice":12,"Price":0.01},{"SericeItemCode":"1102","SericeItemName":"视频问诊","OriginalPrice":6,"Price":0.01},{"SericeItemCode":"1103","SericeItemName":"名医续方","OriginalPrice":1,"Price":0.01}],"AuditedPack":12,"InPack":1}
     */

    private int Code;
    private String Message;
    private DataBean Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * DrId : 57
         * DrName : 奥巴马
         * Mobile : 13622485183
         * DrType : 1
         * DrSex : 3
         * SericeItemCodes : 1101|图文问诊|12.00|0.01,1102|视频问诊|6.00|0.01,1103|名医续方|1.00|0.01
         * DrServiceItems : [{"SericeItemCode":"1101","SericeItemName":"图文问诊","OriginalPrice":12,"Price":0.01},{"SericeItemCode":"1102","SericeItemName":"视频问诊","OriginalPrice":6,"Price":0.01},{"SericeItemCode":"1103","SericeItemName":"名医续方","OriginalPrice":1,"Price":0.01}]
         * AuditedPack : 12
         * InPack : 1
         */

        private int DrId;
        private String DrName;
        private String Mobile;
        private int DrType;
        private int DrSex;
        private String SericeItemCodes;
        private int AuditedPack;
        private int InPack;
        private List<DrServiceItemsBean> DrServiceItems;

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

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public int getDrType() {
            return DrType;
        }

        public void setDrType(int DrType) {
            this.DrType = DrType;
        }

        public int getDrSex() {
            return DrSex;
        }

        public void setDrSex(int DrSex) {
            this.DrSex = DrSex;
        }

        public String getSericeItemCodes() {
            return SericeItemCodes;
        }

        public void setSericeItemCodes(String SericeItemCodes) {
            this.SericeItemCodes = SericeItemCodes;
        }

        public int getAuditedPack() {
            return AuditedPack;
        }

        public void setAuditedPack(int AuditedPack) {
            this.AuditedPack = AuditedPack;
        }

        public int getInPack() {
            return InPack;
        }

        public void setInPack(int InPack) {
            this.InPack = InPack;
        }

        public List<DrServiceItemsBean> getDrServiceItems() {
            return DrServiceItems;
        }

        public void setDrServiceItems(List<DrServiceItemsBean> DrServiceItems) {
            this.DrServiceItems = DrServiceItems;
        }

        public static class DrServiceItemsBean {
            /**
             * SericeItemCode : 1101
             * SericeItemName : 图文问诊
             * OriginalPrice : 12.0
             * Price : 0.01
             */

            private String SericeItemCode;
            private String SericeItemName;
            private double OriginalPrice;
            private double Price;

            public String getSericeItemCode() {
                return SericeItemCode;
            }

            public void setSericeItemCode(String SericeItemCode) {
                this.SericeItemCode = SericeItemCode;
            }

            public String getSericeItemName() {
                return SericeItemName;
            }

            public void setSericeItemName(String SericeItemName) {
                this.SericeItemName = SericeItemName;
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
        }
    }
}
