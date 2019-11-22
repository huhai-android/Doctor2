package com.newdjk.doctor.ui.entity;

import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   DeleteMedicaEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/12/25 14:50
 *  @描述：
 */
public class DeleteMedicaEntity {

    private int DrId;
    private String DrName;

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

    private List<Integer> MedicalIds;

    public List<Integer> getMedicalIds() {
        return MedicalIds;
    }

    public void setMedicalIds(List<Integer> MedicalIds) {
        this.MedicalIds = MedicalIds;
    }
}
