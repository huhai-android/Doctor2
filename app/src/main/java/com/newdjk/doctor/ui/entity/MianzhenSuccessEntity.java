package com.newdjk.doctor.ui.entity;

import java.io.Serializable;

public class MianzhenSuccessEntity implements Serializable {

  private boolean issuccess;

    public MianzhenSuccessEntity(boolean issuccess) {
        this.issuccess = issuccess;
    }

    public boolean isIssuccess() {
        return issuccess;
    }

    public void setIssuccess(boolean issuccess) {
        this.issuccess = issuccess;
    }
}
