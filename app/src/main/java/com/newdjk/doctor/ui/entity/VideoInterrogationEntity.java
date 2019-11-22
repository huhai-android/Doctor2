package com.newdjk.doctor.ui.entity;

import java.util.List;

public class VideoInterrogationEntity {
    private List<AllRecordForDoctorEntity> allRecordForDoctorEntity;
    private String dateTime;
    private int number;

    public List<AllRecordForDoctorEntity> getAllRecordForDoctorEntity() {
        return allRecordForDoctorEntity;
    }

    public void setAllRecordForDoctorEntity(List<AllRecordForDoctorEntity> allRecordForDoctorEntity) {
        this.allRecordForDoctorEntity = allRecordForDoctorEntity;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
