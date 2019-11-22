package com.newdjk.doctor.ui.entity;

import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   MDTreportDetailEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/9/9 19:36
 *  @描述：
 */
public class MDTreportDetailEntity {

    /**
     * Title : 
     * Content : [{"Type":"Text","ContentElem":{"Text":"患者：","Detail":null}},{"Type":"Text","ContentElem":{"Text":"HUU 女 26岁","Detail":null}},{"Type":"Text","ContentElem":{"Text":"","Detail":null}},{"Type":"Text","ContentElem":{"Text":"疾病：","Detail":null}},{"Type":"Text","ContentElem":{"Text":"444444444444444","Detail":null}},{"Type":"Text","ContentElem":{"Text":"","Detail":null}},{"Type":"Text","ContentElem":{"Text":"病情主诉：","Detail":null}},{"Type":"Text","ContentElem":{"Text":"44444444444444444444444","Detail":null}},{"Type":"Text","ContentElem":{"Text":"","Detail":null}}]
     * ExtData : {"Type":309,"Data":{"SubjectBuyRecordId":"49"}}
     * IsSystem : false
     * IsShowDividingLine : false
     * ShowType : 0
     * LinkUrl : 
     * LinkUrlOfDoctor : null
     * LinkUrlOfPatient : null
     * IsSendWxMsg : false
     * IsPushOffline : false
     * PushDesc : 
     */

    private String Title;
    private ExtDataBean ExtData;
    private boolean IsSystem;
    private boolean IsShowDividingLine;
    private int ShowType;
    private String LinkUrl;
    private String LinkUrlOfDoctor;
    private String LinkUrlOfPatient;
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

    public String getLinkUrl() {
        return LinkUrl;
    }

    public void setLinkUrl(String LinkUrl) {
        this.LinkUrl = LinkUrl;
    }

    public String getLinkUrlOfDoctor() {
        return LinkUrlOfDoctor;
    }

    public void setLinkUrlOfDoctor(String LinkUrlOfDoctor) {
        this.LinkUrlOfDoctor = LinkUrlOfDoctor;
    }

    public String getLinkUrlOfPatient() {
        return LinkUrlOfPatient;
    }

    public void setLinkUrlOfPatient(String LinkUrlOfPatient) {
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
         * Type : 309
         * Data : {"SubjectBuyRecordId":"49"}
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
             * SubjectBuyRecordId : 49
             */

            private String SubjectBuyRecordId;

            public String getSubjectBuyRecordId() {
                return SubjectBuyRecordId;
            }

            public void setSubjectBuyRecordId(String SubjectBuyRecordId) {
                this.SubjectBuyRecordId = SubjectBuyRecordId;
            }
        }
    }

    public static class ContentBean {
        /**
         * Type : Text
         * ContentElem : {"Text":"患者：","Detail":null}
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
             * Text : 患者：
             * Detail : null
             */

            private String Text;
            private String Detail;

            public String getText() {
                return Text;
            }

            public void setText(String Text) {
                this.Text = Text;
            }

            public String getDetail() {
                return Detail;
            }

            public void setDetail(String Detail) {
                this.Detail = Detail;
            }
        }
    }
}
