package com.newdjk.doctor.ui.entity;

import java.io.Serializable;
import java.util.List;

public class ConsultDataEntity implements Serializable {
    private double Total;
    private List<ConsultMessageEntity> ReturnData;

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public List<ConsultMessageEntity> getReturnData() {
        return ReturnData;
    }

    public void setReturnData(List<ConsultMessageEntity> returnData) {
        ReturnData = returnData;
    }
}
