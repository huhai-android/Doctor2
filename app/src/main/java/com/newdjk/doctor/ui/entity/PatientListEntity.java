package com.newdjk.doctor.ui.entity;

import java.util.List;

public class PatientListEntity {
    private int Total;
    private List<PatientListDataEntity> ReturnData;

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public List<PatientListDataEntity> getReturnData() {
        return ReturnData;
    }

    public void setReturnData(List<PatientListDataEntity> returnData) {
        ReturnData = returnData;
    }
}
