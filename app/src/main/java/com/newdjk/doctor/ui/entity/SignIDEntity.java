package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   SignIDEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/4/9 17:19
 *  @描述：
 */
public class SignIDEntity {

    public String UniqueId;
    public String PrescriptionId;

    public String getUniqueId() {
        return UniqueId;
    }

    public void setUniqueId(String uniqueId) {
        UniqueId = uniqueId;
    }

    public String getPrescriptionId() {
        return PrescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        PrescriptionId = prescriptionId;
    }
}
