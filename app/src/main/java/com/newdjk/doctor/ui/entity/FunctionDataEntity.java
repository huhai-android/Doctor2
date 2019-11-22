package com.newdjk.doctor.ui.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class FunctionDataEntity {
    @Id(autoincrement = true)
    private Long id;
    private String data;
    @Unique
    private int doctorId;
    private String allFunctionData;
    public String getAllFunctionData() {
        return this.allFunctionData;
    }
    public void setAllFunctionData(String allFunctionData) {
        this.allFunctionData = allFunctionData;
    }
    public int getDoctorId() {
        return this.doctorId;
    }
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1439054806)
    public FunctionDataEntity(Long id, String data, int doctorId,
            String allFunctionData) {
        this.id = id;
        this.data = data;
        this.doctorId = doctorId;
        this.allFunctionData = allFunctionData;
    }
    @Generated(hash = 1311620624)
    public FunctionDataEntity() {
    }
    
}
