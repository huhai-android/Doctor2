package com.newdjk.doctor.ui.entity;

import java.util.List;

public class ForVisistEntity {
    private int Total;
    private List<ForVisistMessageEntity> ReturnData;

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public List<ForVisistMessageEntity> getReturnData() {
        return ReturnData;
    }

    public void setReturnData(List<ForVisistMessageEntity> returnData) {
        ReturnData = returnData;
    }
}
