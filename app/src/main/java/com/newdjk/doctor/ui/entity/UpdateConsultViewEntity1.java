package com.newdjk.doctor.ui.entity;

public class UpdateConsultViewEntity1 {
    private boolean isReflash;

    public UpdateConsultViewEntity1(boolean isReflash) {
        this.isReflash = isReflash;
    }

    public boolean isReflash() {
        return isReflash;
    }

    public void setReflash(boolean reflash) {
        isReflash = reflash;
    }
}
