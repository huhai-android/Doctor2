package com.newdjk.doctor.ui.entity;

import java.io.Serializable;

public class GongDetailEntity implements Serializable{


    /**
     * NoticeManageId : 1
     * Title : sample string 2
     * MasterMap : sample string 3
     * Content : sample string 4
     * Remark : sample string 5
     * NoticeStatus : 64
     * OrderNo : 7
     * PublishTime : 2019-10-25T10:06:22.0953207+08:00
     * Invalid : 64
     * CreateId : 9
     * CreateTime : 2019-10-25T10:06:22.0953207+08:00
     * UpdateId : 11
     * UpdateTime : 2019-10-25T10:06:22.0953207+08:00
     */

    private int NoticeManageId;
    private String Title;
    private String MasterMap;
    private String Content;
    private String Remark;
    private int NoticeStatus;
    private int OrderNo;
    private String PublishTime;
    private int Invalid;
    private int CreateId;
    private String CreateTime;
    private int UpdateId;
    private String UpdateTime;

    public int getNoticeManageId() {
        return NoticeManageId;
    }

    public void setNoticeManageId(int NoticeManageId) {
        this.NoticeManageId = NoticeManageId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getMasterMap() {
        return MasterMap;
    }

    public void setMasterMap(String MasterMap) {
        this.MasterMap = MasterMap;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public int getNoticeStatus() {
        return NoticeStatus;
    }

    public void setNoticeStatus(int NoticeStatus) {
        this.NoticeStatus = NoticeStatus;
    }

    public int getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(int OrderNo) {
        this.OrderNo = OrderNo;
    }

    public String getPublishTime() {
        return PublishTime;
    }

    public void setPublishTime(String PublishTime) {
        this.PublishTime = PublishTime;
    }

    public int getInvalid() {
        return Invalid;
    }

    public void setInvalid(int Invalid) {
        this.Invalid = Invalid;
    }

    public int getCreateId() {
        return CreateId;
    }

    public void setCreateId(int CreateId) {
        this.CreateId = CreateId;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public int getUpdateId() {
        return UpdateId;
    }

    public void setUpdateId(int UpdateId) {
        this.UpdateId = UpdateId;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }
}
