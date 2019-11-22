package com.newdjk.doctor.ui.entity;

import java.io.Serializable;
import java.util.List;

public class GongGaoListEntity  implements Serializable{


    /**
     * Total : 2
     * ReturnData : [{"NoticeManageId":1,"Title":"111111","MasterMap":"http://resource.newstarthealth.cn/Notice/dd91d9ea-233d-4010-9bbf-600e4304914a.jpg","Content":"<p><img src=\"http://resource.newstarthealth.cn/upload/image/20191111/6370908548010031766813002.png\" title=\"血糖仪活动button.png\" alt=\"血糖仪活动button.png\"/><\/p>","Remark":null,"LinkUrl":"http://drwechat.newstarthealth.cn/index.html#/goods132","NoticeStatus":1,"OrderNo":0,"PublishTime":"2019-11-11 16:06:12","Invalid":0,"CreateId":263,"CreateTime":"2019-11-11 16:06:21","UpdateId":0,"UpdateTime":"2019-11-11 18:45:28"},{"NoticeManageId":2,"Title":"qqqqq","MasterMap":"http://resource.newstarthealth.cn/Notice/3eaca034-1c32-49df-b307-147cea468ef2.jpg","Content":"<p>sdf sdf sdfs111<\/p>","Remark":null,"LinkUrl":null,"NoticeStatus":1,"OrderNo":0,"PublishTime":"2019-11-11 00:00:00","Invalid":0,"CreateId":263,"CreateTime":"2019-11-11 16:22:43","UpdateId":0,"UpdateTime":"2019-11-11 18:44:18"}]
     */

    private int Total;
    private List<ReturnDataBean> ReturnData;

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public List<ReturnDataBean> getReturnData() {
        return ReturnData;
    }

    public void setReturnData(List<ReturnDataBean> ReturnData) {
        this.ReturnData = ReturnData;
    }

    public static class ReturnDataBean  implements Serializable {
        /**
         * NoticeManageId : 1
         * Title : 111111
         * MasterMap : http://resource.newstarthealth.cn/Notice/dd91d9ea-233d-4010-9bbf-600e4304914a.jpg
         * Content : <p><img src="http://resource.newstarthealth.cn/upload/image/20191111/6370908548010031766813002.png" title="血糖仪活动button.png" alt="血糖仪活动button.png"/></p>
         * Remark : null
         * LinkUrl : http://drwechat.newstarthealth.cn/index.html#/goods132
         * NoticeStatus : 1
         * OrderNo : 0
         * PublishTime : 2019-11-11 16:06:12
         * Invalid : 0
         * CreateId : 263
         * CreateTime : 2019-11-11 16:06:21
         * UpdateId : 0
         * UpdateTime : 2019-11-11 18:45:28
         */

        private int NoticeManageId;
        private String Title;
        private String MasterMap;
        private String Content;
        private String Remark;
        private String LinkUrl;
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

        public String getLinkUrl() {
            return LinkUrl;
        }

        public void setLinkUrl(String LinkUrl) {
            this.LinkUrl = LinkUrl;
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
}
