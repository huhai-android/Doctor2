package com.newdjk.doctor.ui.entity;

import java.util.List;

public class DepartmentMessageEntity {
    private int Total;
    private List<DepartmentMessageDataEntity> ReturnData;

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public List<DepartmentMessageDataEntity> getReturnData() {
        return ReturnData;
    }

    public void setReturnData(List<DepartmentMessageDataEntity> returnData) {
        ReturnData = returnData;
    }
}
