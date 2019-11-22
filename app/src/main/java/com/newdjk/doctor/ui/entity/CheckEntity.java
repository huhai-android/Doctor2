package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   CheckEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/6 13:55
 *  @描述：
 */
public class CheckEntity {

    /**
     * ReadedCount : 1
     * ResponseRate : 2.1
     */

    private int ReadedCount;
    private double ResponseRate;

    public int getReadedCount() {
        return ReadedCount;
    }

    public void setReadedCount(int ReadedCount) {
        this.ReadedCount = ReadedCount;
    }

    public double getResponseRate() {
        return ResponseRate;
    }

    public void setResponseRate(double ResponseRate) {
        this.ResponseRate = ResponseRate;
    }

    @Override
    public String toString() {
        return "CheckEntity{" +
                "ReadedCount=" + ReadedCount +
                ", ResponseRate=" + ResponseRate +
                '}';
    }
}
