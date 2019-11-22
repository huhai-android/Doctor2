package com.newdjk.doctor.ui.entity;

/**
 * Created by Struggle on 2018/10/15.
 */

public class AboutUsEntity {

    /**
     * Code : 0
     * Message :
     * Data : {"Mobile":"13410716158","Mail":"liuchuanyong@newdjk.com"}
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
         * Mobile : 13410716158
         * Mail : liuchuanyong@newdjk.com
         */

        private String Mobile;
        private String Mail;

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public String getMail() {
            return Mail;
        }

        public void setMail(String Mail) {
            this.Mail = Mail;
        }
    }
}
