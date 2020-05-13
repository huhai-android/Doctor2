package com.newdjk.doctor.ui.entity;

import java.util.List;

public class CustomMessageEntity {

    /**
     * Title : 续方详情如下，共2种：
     * Content : [{"Type":"TextDetail","ContentElem":{"Text":"头孢呋辛酯0.25g*12片","Detail":"X1"}},{"Type":"Text","ContentElem":{"Text":"用法：每日2次 每次0.25g 用药3天\u2026","Detail":null}}]
     * ExtData : {"Type":31,"Data":{"PrescriptionId":"10000"}}
     * IsSystem : false
     */
    private String FocusTitle;
    private String Title;
    private ExtDataBean ExtData;
    private boolean IsSystem;
    private List<ContentBean> Content;
    private String LinkUrl;
    private boolean IsShowDividingLine;
    private  int ShowType;
    private  String PushDesc;


    public boolean isSystem() {
        return IsSystem;
    }

    @Override
    public String toString() {
        return "CustomMessageEntity{" +
                "FocusTitle='" + FocusTitle + '\'' +
                ", Title='" + Title + '\'' +
                ", ExtData=" + ExtData +
                ", IsSystem=" + IsSystem +
                ", Content=" + Content +
                ", LinkUrl='" + LinkUrl + '\'' +
                ", IsShowDividingLine=" + IsShowDividingLine +
                ", ShowType=" + ShowType +
                ", pushDesc='" + PushDesc + '\'' +
                '}';
    }

    public void setSystem(boolean system) {
        IsSystem = system;
    }

    public String getPushDesc() {
        return PushDesc;
    }

    public void setPushDesc(String pushDesc) {
        this.PushDesc = pushDesc;
    }

    public boolean isShowDividingLine() {
        return IsShowDividingLine;
    }

    public void setShowDividingLine(boolean showDividingLine) {
        IsShowDividingLine = showDividingLine;
    }

    public int getShowType() {
        return ShowType;
    }

    public void setShowType(int showType) {
        ShowType = showType;
    }

    public String getFocusTitle() {
        return FocusTitle;
    }

    public void setFocusTitle(String focusTitle) {
        FocusTitle = focusTitle;
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

    public List<ContentBean> getContent() {
        return Content;
    }

    public void setContent(List<ContentBean> Content) {
        this.Content = Content;
    }

    public String getLinkUrl() {
        return LinkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        LinkUrl = linkUrl;
    }

    public static class ExtDataBean {
        @Override
        public String toString() {
            return "ExtDataBean{" +
                    "Type=" + Type +
                    ", Data=" + Data +
                    '}';
        }

        /**
         * Type : 31
         * Data : {"PrescriptionId":"10000"}
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
             * PrescriptionId : 10000
             */
            private String PatientName;
            private int ImageId;
            private int PrescriptionId;
            private  int serviceId;
            private int  MedicalTempId;
            private String LinkURL;
            private String Stamp;
            private int AVRoomID;
            private String Targets;
            private String userSig;
            private int ServiceType;
            private int RecordId;
            private String  Id;
            private int ImagePathHeight;
            private int ImagePathWith;
            private String ImagePath;
            private String time;
            private String CensorateRecordId;
            private  String SubjectBuyRecordId;

            public String getPatMedicationOrderId() {
                return PatMedicationOrderId;
            }

            public void setPatMedicationOrderId(String patMedicationOrderId) {
                PatMedicationOrderId = patMedicationOrderId;
            }

            public String getPatRequireOrderId() {
                return PatRequireOrderId;
            }

            public void setPatRequireOrderId(String patRequireOrderId) {
                PatRequireOrderId = patRequireOrderId;
            }

            public String getMedicationCompanyOrgId() {
                return MedicationCompanyOrgId;
            }

            public void setMedicationCompanyOrgId(String medicationCompanyOrgId) {
                MedicationCompanyOrgId = medicationCompanyOrgId;
            }

            private String PatMedicationOrderId;
            private String PatRequireOrderId;
            private  String MedicationCompanyOrgId;
            public String getSubjectBuyRecordId() {
                return SubjectBuyRecordId;
            }

            public void setSubjectBuyRecordId(String subjectBuyRecordId) {
                SubjectBuyRecordId = subjectBuyRecordId;
            }

            @Override
            public String toString() {
                return "DataBean{" +
                        "PatientName='" + PatientName + '\'' +
                        ", ImageId=" + ImageId +
                        ", PrescriptionId=" + PrescriptionId +
                        ", serviceId=" + serviceId +
                        ", MedicalTempId=" + MedicalTempId +
                        ", LinkURL='" + LinkURL + '\'' +
                        ", Stamp='" + Stamp + '\'' +
                        ", AVRoomID=" + AVRoomID +
                        ", Targets='" + Targets + '\'' +
                        ", userSig='" + userSig + '\'' +
                        ", ServiceType=" + ServiceType +
                        ", RecordId=" + RecordId +
                        ", Id='" + Id + '\'' +
                        ", ImagePathHeight=" + ImagePathHeight +
                        ", ImagePathWith=" + ImagePathWith +
                        ", ImagePath='" + ImagePath + '\'' +
                        ", time='" + time + '\'' +
                        ", CensorateRecordId='" + CensorateRecordId + '\'' +
                        '}';
            }

            public String getCensorateRecordId() {
                return CensorateRecordId;
            }

            public void setCensorateRecordId(String censorateRecordId) {
                CensorateRecordId = censorateRecordId;
            }

            public String getId() {
                return Id;
            }

            public void setId(String id) {
                Id = id;
            }

            public String getUserSig() {
                return userSig;
            }

            public void setUserSig(String userSig) {
                this.userSig = userSig;
            }

            public int getAVRoomID() {
                return AVRoomID;
            }

            public void setAVRoomID(int AVRoomID) {
                this.AVRoomID = AVRoomID;
            }

            public String getTargets() {
                return Targets;
            }

            public void setTargets(String targets) {
                Targets = targets;
            }

            public String getStamp() {
                return Stamp;
            }

            public void setStamp(String stamp) {
                Stamp = stamp;
            }

            public String getLinkURL() {
                return LinkURL;
            }

            public void setLinkURL(String linkURL) {
                LinkURL = linkURL;
            }

            public String getPatientName() {
                return PatientName;
            }

            public void setPatientName(String patientName) {
                PatientName = patientName;
            }

            public int getImageId() {
                return ImageId;
            }

            public void setImageId(int imageId) {
                ImageId = imageId;
            }

            public int getMedicalTempId() {
                return MedicalTempId;
            }

            public void setMedicalTempId(int medicalTempId) {
                MedicalTempId = medicalTempId;
            }

            public int getPrescriptionId() {
                return PrescriptionId;
            }

            public void setPrescriptionId(int PrescriptionId) {
                this.PrescriptionId = PrescriptionId;
            }

            public int getServiceId() {
                return serviceId;
            }

            public void setServiceId(int serviceId) {
                serviceId = serviceId;
            }

            public int getServiceType() {
                return ServiceType;
            }

            public void setServiceType(int serviceType) {
                ServiceType = serviceType;
            }

            public int getRecordId() {
                return RecordId;
            }

            public void setRecordId(int recordId) {
                RecordId = recordId;
            }

            public int getImagePathHeight() {
                return ImagePathHeight;
            }

            public void setImagePathHeight(int imagePathHeight) {
                ImagePathHeight = imagePathHeight;
            }

            public int getImagePathWith() {
                return ImagePathWith;
            }

            public void setImagePathWith(int imagePathWith) {
                ImagePathWith = imagePathWith;
            }

            public String getImagePath() {
                return ImagePath;
            }

            public void setImagePath(String imagePath) {
                ImagePath = imagePath;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }



    }

    public static class ContentBean {
        @Override
        public String toString() {
            return "ContentBean{" +
                    "Type='" + Type + '\'' +
                    ", ContentElem=" + ContentElem +
                    '}';
        }

        /**
         * Type : TextDetail
         * ContentElem : {"Text":"头孢呋辛酯0.25g*12片","Detail":"X1"}
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
             * Text : 头孢呋辛酯0.25g*12片
             * Detail : X1
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

            @Override
            public String toString() {
                return "ContentElemBean{" +
                        "Text='" + Text + '\'' +
                        ", Detail='" + Detail + '\'' +
                        '}';
            }
        }
    }
}
