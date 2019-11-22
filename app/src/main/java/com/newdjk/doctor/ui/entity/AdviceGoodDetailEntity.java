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
public class AdviceGoodDetailEntity {


    /**
     * Title : 优选推荐
     * Content : [{"Type":"Text","ContentElem":{"Text":"向您推荐：穴位贴敷治疗贴 等共计1款 优选商品","Detail":null}}]
     * ExtData : {"Type":304,"Data":{"DataSource":"4","DataId":"49","DataSourceOfDoc":"1","DataIdOfDoc":"80"}}
     * IsSystem : false
     * IsShowDividingLine : false
     * ShowType : 0
     * LinkUrl : http://wx.newstarthealth.cn/Home/PageRedirect?redirectUrl=http%3a%2f%2fwx.newstarthealth.cn%2findex.html%23%2fprefRecom%2forder%3fDataSource%3d4%26DataId%3d49%26PatientId%3d54%26appid%3dwxce06749b2a6e9352%26orgid%3d1
     * LinkUrlOfDoctor : null
     * LinkUrlOfPatient : null
     * IsPushOffline : false
     * PushDesc : 优选推荐
     */

    private String Title;
    private ExtDataBean ExtData;
    private boolean IsSystem;
    private boolean IsShowDividingLine;
    private int ShowType;
    private String LinkUrl;
    private Object LinkUrlOfDoctor;
    private Object LinkUrlOfPatient;
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
         * Type : 304
         * Data : {"DataSource":"4","DataId":"49","DataSourceOfDoc":"1","DataIdOfDoc":"80"}
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
             * DataSource : 4
             * DataId : 49
             * DataSourceOfDoc : 1
             * DataIdOfDoc : 80
             */

            private String DataSource;
            private String DataId;
            private String DataSourceOfDoc;
            private String DataIdOfDoc;

            public String getDataSource() {
                return DataSource;
            }

            public void setDataSource(String DataSource) {
                this.DataSource = DataSource;
            }

            public String getDataId() {
                return DataId;
            }

            public void setDataId(String DataId) {
                this.DataId = DataId;
            }

            public String getDataSourceOfDoc() {
                return DataSourceOfDoc;
            }

            public void setDataSourceOfDoc(String DataSourceOfDoc) {
                this.DataSourceOfDoc = DataSourceOfDoc;
            }

            public String getDataIdOfDoc() {
                return DataIdOfDoc;
            }

            public void setDataIdOfDoc(String DataIdOfDoc) {
                this.DataIdOfDoc = DataIdOfDoc;
            }
        }
    }

    public static class ContentBean {
        /**
         * Type : Text
         * ContentElem : {"Text":"向您推荐：穴位贴敷治疗贴 等共计1款 优选商品","Detail":null}
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
             * Text : 向您推荐：穴位贴敷治疗贴 等共计1款 优选商品
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
