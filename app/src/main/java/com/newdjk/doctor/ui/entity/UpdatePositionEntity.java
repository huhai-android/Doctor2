package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   UpdatePositionEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/9/18 13:46
 *  @描述：
 */
public class UpdatePositionEntity {
    int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public UpdatePositionEntity(int position) {
        this.position = position;
    }
}
