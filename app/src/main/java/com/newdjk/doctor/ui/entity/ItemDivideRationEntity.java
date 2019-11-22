package com.newdjk.doctor.ui.entity;

import java.io.Serializable;

public class ItemDivideRationEntity implements Serializable {


    /**
     * DrId : 57
     * SericeItemCode : 1102
     * ItemDivideRation : 0.3000
     */

    private int DrId;
    private String SericeItemCode;
    private String ItemDivideRation;

    public int getDrId() {
        return DrId;
    }

    public void setDrId(int DrId) {
        this.DrId = DrId;
    }

    public String getSericeItemCode() {
        return SericeItemCode;
    }

    public void setSericeItemCode(String SericeItemCode) {
        this.SericeItemCode = SericeItemCode;
    }

    public String getItemDivideRation() {
        return ItemDivideRation;
    }

    public void setItemDivideRation(String ItemDivideRation) {
        this.ItemDivideRation = ItemDivideRation;
    }
}
