package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   DiseaseLabelEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/3/12 15:31
 *  @描述：
 */
public class DiseaseLabelEntity {

    /**
     * DisGroupId : 17
     * DisGroupName : 糖尿病
     */

    private int DisGroupId;
    private String DisGroupName;

    public int getDisGroupId() {
        return DisGroupId;
    }

    public void setDisGroupId(int DisGroupId) {
        this.DisGroupId = DisGroupId;
    }

    public String getDisGroupName() {
        return DisGroupName;
    }

    public void setDisGroupName(String DisGroupName) {
        this.DisGroupName = DisGroupName;
    }
}
