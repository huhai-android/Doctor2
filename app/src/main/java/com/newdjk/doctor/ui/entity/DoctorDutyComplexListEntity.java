package com.newdjk.doctor.ui.entity;

import java.util.List;

public class DoctorDutyComplexListEntity {
    private ForVisistEntity  ComingDutyList;
    private List<ForVisistMessageEntity> TodayDutyList;

    public ForVisistEntity getComingDutyList() {
        return ComingDutyList;
    }

    public void setComingDutyList(ForVisistEntity comingDutyList) {
        ComingDutyList = comingDutyList;
    }

    public List<ForVisistMessageEntity> getTodayDutyList() {
        return TodayDutyList;
    }

    public void setTodayDutyList(List<ForVisistMessageEntity> todayDutyList) {
        TodayDutyList = todayDutyList;
    }
}
