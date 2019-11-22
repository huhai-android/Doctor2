package com.newdjk.doctor.ui.entity;

import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   AdviceGoodEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/4/18 21:36
 *  @描述：
 */
public class AdviceGoodEntity {


    /**
     * Title : 优选推荐
     * Content : [{"Type":"Text","ContentElem":{"Text":"张飞医生向您推荐：<<111>>","Detail":null}}]
     * ExtData : {"Type":302,"Data":null}
     * IsSystem : false
     * IsShowDividingLine : false
     * ShowType : 0
     * LinkUrl : /27/20190418210958877
     * IsPushOffline : false
     * PushDesc : null
     */




    private String Title;
    private ExtDataBean ExtData;
    private boolean IsSystem;
    private boolean IsShowDividingLine;
    private int ShowType;
    private String LinkUrl;
    private boolean IsPushOffline;
    private String PushDesc;
    private List<ContentBean> Content;
    private String LinkUrlOfDoctor;
    private String LinkUrlOfPatient;

    public boolean isShowDividingLine() {
        return IsShowDividingLine;
    }

    public void setShowDividingLine(boolean showDividingLine) {
        IsShowDividingLine = showDividingLine;
    }

    public boolean isPushOffline() {
        return IsPushOffline;
    }

    public void setPushOffline(boolean pushOffline) {
        IsPushOffline = pushOffline;
    }

    public String getLinkUrlOfDoctor() {
        return LinkUrlOfDoctor;
    }

    public void setLinkUrlOfDoctor(String linkUrlOfDoctor) {
        LinkUrlOfDoctor = linkUrlOfDoctor;
    }

    public String getLinkUrlOfPatient() {
        return LinkUrlOfPatient;
    }

    public void setLinkUrlOfPatient(String linkUrlOfPatient) {
        LinkUrlOfPatient = linkUrlOfPatient;
    }

    public boolean isSystem() {

        return IsSystem;
    }

    public void setSystem(boolean system) {
        IsSystem = system;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public ExtDataBean getExtData() {
        return ExtData;
    }

    public void setExtData(ExtDataBean ExtData) {
        this.ExtData = ExtData;
    }

    public boolean isIsSystem() {
        return IsSystem;
    }

    public void setIsSystem(boolean IsSystem) {
        this.IsSystem = IsSystem;
    }

    public boolean isIsShowDividingLine() {
        return IsShowDividingLine;
    }

    public void setIsShowDividingLine(boolean IsShowDividingLine) {
        this.IsShowDividingLine = IsShowDividingLine;
    }

    public int getShowType() {
        return ShowType;
    }

    public void setShowType(int ShowType) {
        this.ShowType = ShowType;
    }

    public String getLinkUrl() {
        return LinkUrl;
    }

    public void setLinkUrl(String LinkUrl) {
        this.LinkUrl = LinkUrl;
    }

    public boolean isIsPushOffline() {
        return IsPushOffline;
    }

    public void setIsPushOffline(boolean IsPushOffline) {
        this.IsPushOffline = IsPushOffline;
    }

    public String getPushDesc() {
        return PushDesc;
    }

    public void setPushDesc(String PushDesc) {
        this.PushDesc = PushDesc;
    }

    public List<ContentBean> getContent() {
        return Content;
    }

    public void setContent(List<ContentBean> Content) {
        this.Content = Content;
    }

    public static class ExtDataBean {
        /**
         * Type : 302
         * Data : null
         */

        private int Type;
        private Object Data;

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }

        public Object getData() {
            return Data;
        }

        public void setData(Object Data) {
            this.Data = Data;
        }
    }

    public static class ContentBean {
        /**
         * Type : Text
         * ContentElem : {"Text":"张飞医生向您推荐：<<111>>","Detail":null}
         */

        private String Type;
        private ContentElemBean ContentElem;

        public String getType() {
            return Type;
        }

        public void setType(String Type) {
            this.Type = Type;
        }

        public ContentElemBean getContentElem() {
            return ContentElem;
        }

        public void setContentElem(ContentElemBean ContentElem) {
            this.ContentElem = ContentElem;
        }

        public static class ContentElemBean {
            /**
             * Text : 张飞医生向您推荐：<<111>>
             * Detail : null
             */

            private String Text;
            private Object Detail;

            public String getText() {
                return Text;
            }

            public void setText(String Text) {
                this.Text = Text;
            }

            public Object getDetail() {
                return Detail;
            }

            public void setDetail(Object Detail) {
                this.Detail = Detail;
            }
        }
    }
}
