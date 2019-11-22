package com.newdjk.doctor.ui.entity;

import java.util.List;

/**
 * Created by Struggle on 2018/10/15.
 */

public class SendGoodEntity {


    /**
     * DataSource : 64
     * DataId : 2
     * PatientIdList : [1,2]
     */

    private int DataSource;
    private int DataId;
    private List<Integer> PatientIdList;

    public int getDataSource() {
        return DataSource;
    }

    public void setDataSource(int DataSource) {
        this.DataSource = DataSource;
    }

    public int getDataId() {
        return DataId;
    }

    public void setDataId(int DataId) {
        this.DataId = DataId;
    }

    public List<Integer> getPatientIdList() {
        return PatientIdList;
    }

    public void setPatientIdList(List<Integer> PatientIdList) {
        this.PatientIdList = PatientIdList;
    }
}
