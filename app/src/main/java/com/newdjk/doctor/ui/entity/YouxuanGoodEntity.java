package com.newdjk.doctor.ui.entity;

/**
 * Created by Struggle on 2018/10/15.
 */

public class YouxuanGoodEntity {


    /**
     * data : {"GoodsRecomBuyId":"355"}
     * endStauts : 0
     * id : 9005
     * moduleType : 9
     * msgType : 2
     * serviceCode : 0
     * time : 2019-06-20 17:44:13
     * title : 优选推荐
     * type : 227
     * userId : 306
     */

    private DataBean data;
    private int endStauts;
    private int id;
    private int moduleType;
    private int msgType;
    private int serviceCode;
    private String time;
    private String title;
    private int type;
    private String userId;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModuleType() {
        return moduleType;
    }

    public void setModuleType(int moduleType) {
        this.moduleType = moduleType;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public static class DataBean {
        /**
         * GoodsRecomBuyId : 355
         */

        private String GoodsRecomBuyId;

        public String getGoodsRecomBuyId() {
            return GoodsRecomBuyId;
        }

        public void setGoodsRecomBuyId(String GoodsRecomBuyId) {
            this.GoodsRecomBuyId = GoodsRecomBuyId;
        }
    }
}
