package com.newdjk.doctor.ui.entity;

public class DoctorIncomeEntity {


    /**
     * DrId : 57
     * UnCashIncome : 0.07
     * MonthIncome : 0
     * TotalIncome : 1.80
     */

    private int DrId;
    private String UnCashIncome;
    private String MonthIncome;
    private String TotalIncome;

    public int getDrId() {
        return DrId;
    }

    public void setDrId(int DrId) {
        this.DrId = DrId;
    }

    public String getUnCashIncome() {
        return UnCashIncome;
    }

    public void setUnCashIncome(String UnCashIncome) {
        this.UnCashIncome = UnCashIncome;
    }

    public String getMonthIncome() {
        return MonthIncome;
    }

    public void setMonthIncome(String MonthIncome) {
        this.MonthIncome = MonthIncome;
    }

    public String getTotalIncome() {
        return TotalIncome;
    }

    public void setTotalIncome(String TotalIncome) {
        this.TotalIncome = TotalIncome;
    }
}
