package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   SignReportSuccess
 *  @创建者:   huhai
 *  @创建时间:  2018/11/8 16:42
 *  @描述：
 */
public class ZhuanhuiSuccess {
    public  boolean zhenzhuan;

    public ZhuanhuiSuccess(boolean zhenzhuan) {
        this.zhenzhuan = zhenzhuan;
    }

    public boolean isZhenzhuan() {
        return zhenzhuan;
    }

    public void setZhenzhuan(boolean zhenzhuan) {
        this.zhenzhuan = zhenzhuan;
    }
}
