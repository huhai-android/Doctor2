package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   WebShopUseinfoEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/4/18 17:26
 *  @描述：
 */
public class WebShopUseinfoEntity {

    public ShopLoginEntity mShopLoginEntity;
    public PatientEntity mPatientEntity;

    public ShopLoginEntity getShopLoginEntity() {
        return mShopLoginEntity;
    }

    public void setShopLoginEntity(ShopLoginEntity shopLoginEntity) {
        mShopLoginEntity = shopLoginEntity;
    }

    public PatientEntity getPatientEntity() {
        return mPatientEntity;
    }

    public void setPatientEntity(PatientEntity patientEntity) {
        mPatientEntity = patientEntity;
    }
}
