package com.newdjk.doctor.ui.entity;

import java.io.Serializable;
import java.util.List;

public class InquiryRecordListEntity implements Serializable {
    private double Total;
    private List<InquiryRecordListDataEntity> ReturnData;

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public List<InquiryRecordListDataEntity> getReturnData() {
        return ReturnData;
    }

    public void setReturnData(List<InquiryRecordListDataEntity> returnData) {
        ReturnData = returnData;
    }
}
