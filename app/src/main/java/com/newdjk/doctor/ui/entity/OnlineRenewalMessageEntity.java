package com.newdjk.doctor.ui.entity;

import java.io.Serializable;
import java.util.List;

public class OnlineRenewalMessageEntity implements Serializable {
    private int Total;
    private List<OnlineRenewalDataEntity> ReturnData;

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public List<OnlineRenewalDataEntity> getReturnData() {
        return ReturnData;
    }

    public void setReturnData(List<OnlineRenewalDataEntity> returnData) {
        ReturnData = returnData;
    }
}
