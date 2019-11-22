package com.newdjk.doctor.ui.entity;

public class JPushDataMessageEntity {

    /**
     * data : {"RecordId":"797"}
     * endStauts : 0
     * serviceCode : 0
     * time : 2018-10-23 15:09:13
     * type : 14
     */

    private DataBean data;
    private int endStauts;
    private int serviceCode;
    private String time;
    private int type;
    private String userId;
    private int id;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getEndStauts() {
        return endStauts;
    }

    public void setEndStauts(int endStauts) {
        this.endStauts = endStauts;
    }

    public int getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(int serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class DataBean {
        /**
         * RecordId : 797
         */

        private String RecordId;

        public String getRecordId() {
            return RecordId;
        }

        public void setRecordId(String RecordId) {
            this.RecordId = RecordId;
        }
    }
}
