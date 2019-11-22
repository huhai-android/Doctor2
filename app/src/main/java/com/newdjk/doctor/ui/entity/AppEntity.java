package com.newdjk.doctor.ui.entity;

public class AppEntity {


    /**
     * Code : 0
     * Message :
     * Data : {"AppCode":"59c9ed656e18ccece96537ec","AppName":"健康保定APP-患者","AppVersion":7,"AppPath":"http://resource.newstarthealth.cn/AppManage/4ee0fab8-32a8-4b7b-a3ac-61d683e95af3.apk","Remark":"7版本\n测试升级","MustUpdate":0,"ReleaseVersion":""}
     */

    private int Code;
    private String Message;
    private DataBean Data;

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

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * AppCode : 59c9ed656e18ccece96537ec
         * AppName : 健康保定APP-患者
         * AppVersion : 7
         * AppPath : http://resource.newstarthealth.cn/AppManage/4ee0fab8-32a8-4b7b-a3ac-61d683e95af3.apk
         * Remark : 7版本
         测试升级
         * MustUpdate : 0
         * ReleaseVersion :
         */

        private String AppCode;
        private String AppName;
        private String AppVersion;
        private String AppPath;
        private String Remark;
        private int MustUpdate;
        private String ReleaseVersion;

        public String getAppCode() {
            return AppCode;
        }

        public void setAppCode(String AppCode) {
            this.AppCode = AppCode;
        }

        public String getAppName() {
            return AppName;
        }

        public void setAppName(String AppName) {
            this.AppName = AppName;
        }

        public String getAppVersion() {
            return AppVersion;
        }

        public void setAppVersion(String AppVersion) {
            this.AppVersion = AppVersion;
        }

        public String getAppPath() {
            return AppPath;
        }

        public void setAppPath(String AppPath) {
            this.AppPath = AppPath;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public int getMustUpdate() {
            return MustUpdate;
        }

        public void setMustUpdate(int MustUpdate) {
            this.MustUpdate = MustUpdate;
        }

        public String getReleaseVersion() {
            return ReleaseVersion;
        }

        public void setReleaseVersion(String ReleaseVersion) {
            this.ReleaseVersion = ReleaseVersion;
        }
    }
}
