package com.newdjk.doctor.ui.entity;

import java.util.List;

public class JpushDataListEntity {
    private int Total;
    private List<JpushDataEntity> ReturnData;

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public List<JpushDataEntity> getReturnData() {
        return ReturnData;
    }

    public void setReturnData(List<JpushDataEntity> returnData) {
        ReturnData = returnData;
    }
}
