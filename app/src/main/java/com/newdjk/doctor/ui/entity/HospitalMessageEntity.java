package com.newdjk.doctor.ui.entity;

import java.util.List;

public class HospitalMessageEntity {
    private int Total;
    private List<HospitalMessageDataEntity> ReturnData;

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public List<HospitalMessageDataEntity> getReturnData() {
        return ReturnData;
    }

    public void setReturnData(List<HospitalMessageDataEntity> returnData) {
        ReturnData = returnData;
    }
}
