package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   IsShowIncome
 *  @创建者:   huhai
 *  @创建时间:  2019/8/14 15:31
 *  @描述：
 */
public class IsShowIncome {


    /**
     * incomeState : 0
     */

    private int incomeState;

    public int getIncomeState() {
        return incomeState;
    }

    public void setIncomeState(int incomeState) {
        this.incomeState = incomeState;
    }

    public IsShowIncome(int incomeState) {
        this.incomeState = incomeState;
    }
}
