package com.newdjk.doctor.ui.entity;

import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   GotoGroupChatEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/9/19 11:29
 *  @描述：
 */
public class GotoGroupChatEntity {

    /**
     * Title :
     * Content : [{"Type":"Text","ContentElem":{"Text":"刘付医生已接诊","Detail":null}},{"Type":"Text","ContentElem":{"Text":"保定市第一中心医院","Detail":null}},{"Type":"Text","ContentElem":{"Text":"主治医师|内科","Detail":null}}]
     * ExtData : {"Type":319,"Data":{"SubjectBuyRecordId":"465","IMGroupId":"G201909191126408661"}}
     * IsSystem : false
     * IsShowDividingLine : false
     * ShowType : 0
     * LinkUrl :
     * LinkUrlOfDoctor : null
     * LinkUrlOfPatient : null
     * IsSendWxMsg : false
     * IsPushOffline : false
     * PushDesc : 刘付医生已接诊
     */

    private String Title;
    private ExtDataBean ExtData;
    private boolean IsSystem;
    private boolean IsShowDividingLine;
    private int ShowType;
    private String LinkUrl;
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

    public String getLinkUrl() {
        return LinkUrl;
    }

    public void setLinkUrl(String LinkUrl) {
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
         * Type : 319
         * Data : {"SubjectBuyRecordId":"465","IMGroupId":"G201909191126408661"}
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
             * SubjectBuyRecordId : 465
             * IMGroupId : G201909191126408661
             */

            private String SubjectBuyRecordId;
            private String IMGroupId;

            public String getSubjectBuyRecordId() {
                return SubjectBuyRecordId;
            }

            public void setSubjectBuyRecordId(String SubjectBuyRecordId) {
                this.SubjectBuyRecordId = SubjectBuyRecordId;
            }

            public String getIMGroupId() {
                return IMGroupId;
            }

            public void setIMGroupId(String IMGroupId) {
                this.IMGroupId = IMGroupId;
            }
        }
    }

    public static class ContentBean {
        /**
         * Type : Text
         * ContentElem : {"Text":"刘付医生已接诊","Detail":null}
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
             * Text : 刘付医生已接诊
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
