package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   AllApplicationJson
 *  @创建者:   huhai
 *  @创建时间:  2019/1/17 15:02
 *  @描述：
 */
public class AllApplicationJson {

    /**
     * appDesc : 网上咨询
     * appID : 1
     * imageID : 2131558423
     * isselect : false
     */

    private String appDesc;
    private int appID;
    private int imageID;
    private boolean isselect;

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public int getAppID() {
        return appID;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public boolean isIsselect() {
        return isselect;
    }

    public void setIsselect(boolean isselect) {
        this.isselect = isselect;
    }
}
