package com.newdjk.doctor.ui.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by EDZ on 2018/9/19.
 */

public class AdBannerInfo implements Serializable{


    /**
     * Code : 0
     * Message :
     * Data : [{"AdClassName":"医生APP首页Banner","Id":3,"AdClassId":4,"Name":"活动1","ContentType":2,"ContentLink":null,"Content":"http://resource.newstarthealth.cn//ad/image/20181018/6367547067504569352448781.png","Sort":1,"EffectiveTime":"2018-12-01 00:00:00","Status":1,"CreateTime":"2018-09-22 16:02:15","LinkContent":"<p style=\"width: 100%;\"><img style=\"width:100%;\" src=\"http://resource.newstarthealth.cn//ad/image/20190220/6368628657471166174619152.png\" title=\"微信图片_20190220190859.png\" alt=\"微信图片_20190220190859.png\"/><\/p>","IsShared":1,"ShareTitle":null,"ShareCotent":null,"ShareLink":null},{"AdClassName":"医生APP首页Banner","Id":20,"AdClassId":4,"Name":"医生APP首页Banner 1","ContentType":2,"ContentLink":null,"Content":"http://resource.newstarthealth.cn//ad/image/20190219/6368618559313888872617415.png","Sort":1,"EffectiveTime":"2019-02-28 00:00:00","Status":1,"CreateTime":"2019-02-19 15:07:56","LinkContent":"<p>据景山公园官方微信消息，景山公园将于2月19日(正月十五）晚18:30\u201422:00在园内举办元宵节灯会活动。鉴于活动于晚间举行，受场地条件限制，活动将采取凭票入场的方式进行，无票不能进园（持免费入园的所有证件无效）。<br/><img src=\"http://resource.newstarthealth.cn//ad/image/20190220/6368628062116848008790905.png\" title=\"icon1.png\" alt=\"icon1.png\"/><\/p><div><\/div><p>2月19日（正月十五）活动当天，为使活动得以顺利举办，景山公园将于14：00停止售票，游客停止入园（含持免费入园所有证件入园游客），17：00静园。考虑晚间安全问题，园内山体封闭。2月20日，鉴于园内撤场施工等因素，公园将于14:00闭园。<\/p><p><br/><\/p>","IsShared":1,"ShareTitle":null,"ShareCotent":null,"ShareLink":null}]
     */

    private int Code;
    private String Message;
    private List<DataBean> Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean implements Serializable {
        /**
         * AdClassName : 医生APP首页Banner
         * Id : 3
         * AdClassId : 4
         * Name : 活动1
         * ContentType : 2
         * ContentLink : null
         * Content : http://resource.newstarthealth.cn//ad/image/20181018/6367547067504569352448781.png
         * Sort : 1
         * EffectiveTime : 2018-12-01 00:00:00
         * Status : 1
         * CreateTime : 2018-09-22 16:02:15
         * LinkContent : <p style="width: 100%;"><img style="width:100%;" src="http://resource.newstarthealth.cn//ad/image/20190220/6368628657471166174619152.png" title="微信图片_20190220190859.png" alt="微信图片_20190220190859.png"/></p>
         * IsShared : 1
         * ShareTitle : null
         * ShareCotent : null
         * ShareLink : null
         */

        private String AdClassName;
        private int Id;
        private int AdClassId;
        private String Name;
        private int ContentType;
        private String ContentLink;
        private String Content;
        private int Sort;
        private String EffectiveTime;
        private int Status;
        private String CreateTime;
        private String LinkContent;
        private int IsShared;
        private String ShareTitle;
        private String ShareCotent;
        private String ShareLink;

        @Override
        public String toString() {
            return "DataBean{" +
                    "AdClassName='" + AdClassName + '\'' +
                    ", Id=" + Id +
                    ", AdClassId=" + AdClassId +
                    ", Name='" + Name + '\'' +
                    ", ContentType=" + ContentType +
                    ", ContentLink='" + ContentLink + '\'' +
                    ", Content='" + Content + '\'' +
                    ", Sort=" + Sort +
                    ", EffectiveTime='" + EffectiveTime + '\'' +
                    ", Status=" + Status +
                    ", CreateTime='" + CreateTime + '\'' +
                    ", LinkContent='" + LinkContent + '\'' +
                    ", IsShared=" + IsShared +
                    ", ShareTitle='" + ShareTitle + '\'' +
                    ", ShareCotent='" + ShareCotent + '\'' +
                    ", ShareLink='" + ShareLink + '\'' +
                    '}';
        }

        public String getAdClassName() {
            return AdClassName;
        }

        public void setAdClassName(String AdClassName) {
            this.AdClassName = AdClassName;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getAdClassId() {
            return AdClassId;
        }

        public void setAdClassId(int AdClassId) {
            this.AdClassId = AdClassId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getContentType() {
            return ContentType;
        }

        public void setContentType(int ContentType) {
            this.ContentType = ContentType;
        }

        public String getContentLink() {
            return ContentLink;
        }

        public void setContentLink(String ContentLink) {
            this.ContentLink = ContentLink;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int Sort) {
            this.Sort = Sort;
        }

        public String getEffectiveTime() {
            return EffectiveTime;
        }

        public void setEffectiveTime(String EffectiveTime) {
            this.EffectiveTime = EffectiveTime;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getLinkContent() {
            return LinkContent;
        }

        public void setLinkContent(String LinkContent) {
            this.LinkContent = LinkContent;
        }

        public int getIsShared() {
            return IsShared;
        }

        public void setIsShared(int IsShared) {
            this.IsShared = IsShared;
        }

        public String getShareTitle() {
            return ShareTitle;
        }

        public void setShareTitle(String ShareTitle) {
            this.ShareTitle = ShareTitle;
        }

        public String getShareCotent() {
            return ShareCotent;
        }

        public void setShareCotent(String ShareCotent) {
            this.ShareCotent = ShareCotent;
        }

        public String getShareLink() {
            return ShareLink;
        }

        public void setShareLink(String ShareLink) {
            this.ShareLink = ShareLink;
        }
    }
}
