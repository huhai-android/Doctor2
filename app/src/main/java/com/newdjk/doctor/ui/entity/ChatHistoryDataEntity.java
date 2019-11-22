package com.newdjk.doctor.ui.entity;

import java.util.List;

public class ChatHistoryDataEntity {
    private double Total;
    private List<ChatHistoryEntity> ReturnData;

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public List<ChatHistoryEntity> getReturnData() {
        return ReturnData;
    }

    public void setReturnData(List<ChatHistoryEntity> returnData) {
        ReturnData = returnData;
    }
}
