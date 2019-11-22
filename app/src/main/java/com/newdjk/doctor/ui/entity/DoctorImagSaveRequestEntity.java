package com.newdjk.doctor.ui.entity;

import java.util.List;

public class DoctorImagSaveRequestEntity {
    private int DrId;
    private String DrName;
    private String CredNo;
    private List<DoctorRegImgEntity> DoctorRegImgs;

    public int getDrId() {
        return DrId;
    }

    public void setDrId(int drId) {
        DrId = drId;
    }

    public String getDrName() {
        return DrName;
    }

    public void setDrName(String drName) {
        DrName = drName;
    }

    public String getCredNo() {
        return CredNo;
    }

    public void setCredNo(String credNo) {
        CredNo = credNo;
    }

    public List<DoctorRegImgEntity> getDoctorRegImgs() {
        return DoctorRegImgs;
    }

    public void setDoctorRegImgs(List<DoctorRegImgEntity> doctorRegImgs) {
        DoctorRegImgs = doctorRegImgs;
    }
}
