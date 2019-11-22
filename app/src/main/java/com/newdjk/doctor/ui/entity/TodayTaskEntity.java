package com.newdjk.doctor.ui.entity;

public class TodayTaskEntity {


    /**
     * DrId : 1
     * TodayNum : 2
     * ReceiveNum : 3
     * FutureNum : 4
     */

    private int DrId;
    private int TodayNum;
    private int ReceiveNum;
    private int FutureNum;

    public int getDrId() {
        return DrId;
    }

    public void setDrId(int DrId) {
        this.DrId = DrId;
    }

    public int getTodayNum() {
        return TodayNum;
    }

    public void setTodayNum(int TodayNum) {
        this.TodayNum = TodayNum;
    }

    public int getReceiveNum() {
        return ReceiveNum;
    }

    public void setReceiveNum(int ReceiveNum) {
        this.ReceiveNum = ReceiveNum;
    }

    public int getFutureNum() {
        return FutureNum;
    }

    public void setFutureNum(int FutureNum) {
        this.FutureNum = FutureNum;
    }
}
