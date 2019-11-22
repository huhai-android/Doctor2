package com.newdjk.doctor.ui.entity;

import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   WenZhenDiseaseEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/10/31 14:32
 *  @描述：
 */
public class WenZhenDiseaseEntity {


    /**
     * Title : 胡歌 男 30岁
     * Content : [{"Type":"Text","ContentElem":{"Text":"问诊内容：1112131331313134343313131","Detail":null}}]
     * ExtData : {"Type":38,"Data":{"RecordId":"4469","ServiceType":"1"}}
     * IsSystem : false
     * IsShowDividingLine : false
     * ShowType : 0
     * LinkUrl : null
     * LinkUrlOfDoctor : null
     * LinkUrlOfPatient : null
     * IsSendWxMsg : true
     * IsPushOffline : true
     * PushDesc : [胡歌]发起新的图文问诊请求
     */

    private String Title;
    private ExtDataBean ExtData;
    private boolean IsSystem;
    private boolean IsShowDividingLine;
    private int ShowType;
    private Object LinkUrl;
    private Object LinkUrlOfDoctor;
    private Object LinkUrlOfPatient;
    private boolean IsSendWxMsg;
    private boolean IsPushOffline;
    private String PushDesc;
    private List<ContentBean> Content;

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

    public Object getLinkUrl() {
        return LinkUrl;
    }

    public void setLinkUrl(Object LinkUrl) {
        this.LinkUrl = LinkUrl;
    }

    public Object getLinkUrlOfDoctor() {
        return LinkUrlOfDoctor;
    }

    public void setLinkUrlOfDoctor(Object LinkUrlOfDoctor) {
        this.LinkUrlOfDoctor = LinkUrlOfDoctor;
    }

    public Object getLinkUrlOfPatient() {
        return LinkUrlOfPatient;
    }

    public void setLinkUrlOfPatient(Object LinkUrlOfPatient) {
        this.LinkUrlOfPatient = LinkUrlOfPatient;
    }

    public boolean isIsSendWxMsg() {
        return IsSendWxMsg;
    }

    public void setIsSendWxMsg(boolean IsSendWxMsg) {
        this.IsSendWxMsg = IsSendWxMsg;
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
         * Type : 38
         * Data : {"RecordId":"4469","ServiceType":"1"}
         */

        private int Type;
        private DataBean Data;

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }

        public DataBean getData() {
            return Data;
        }

        public void setData(DataBean Data) {
            this.Data = Data;
        }

        public static class DataBean {
            /**
             * RecordId : 4469
             * ServiceType : 1
             */

            private String RecordId;
            private String ServiceType;

            public String getRecordId() {
                return RecordId;
            }

            public void setRecordId(String RecordId) {
                this.RecordId = RecordId;
            }

            public String getServiceType() {
                return ServiceType;
            }

            public void setServiceType(String ServiceType) {
                this.ServiceType = ServiceType;
            }
        }
    }

    public static class ContentBean {
        /**
         * Type : Text
         * ContentElem : {"Text":"问诊内容：1112131331313134343313131","Detail":null}
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
             * Text : 问诊内容：1112131331313134343313131
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
