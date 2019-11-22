package com.newdjk.doctor.ui.entity;

import java.io.Serializable;

public class RefeshTaskListEntity implements Serializable {

  private boolean issuccess;

    public RefeshTaskListEntity(boolean issuccess) {
        this.issuccess = issuccess;
    }

    public boolean isIssuccess() {
        return issuccess;
    }

    public void setIssuccess(boolean issuccess) {
        this.issuccess = issuccess;
    }
}
