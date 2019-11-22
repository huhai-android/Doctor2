package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   ChufangIDEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/12/19 14:47
 *  @描述：
 */
public class ChufangIDEntity {


    /**
     * type : 101
     * data : {"PrescriptionId":"847"}
     * serviceCode : 0
     * endStauts : 0
     * time : 2018-12-19 14:32:53
     * userId : 311
     * title : 新的待审处方提醒
     * msgType : 3
     * moduleType : 5
     * id : 3218
     */

    private int type;
    private DataBean data;
    private int serviceCode;
    private int endStauts;
    private String time;
    private String userId;
    private String title;
    private int msgType;
    private int moduleType;
    private int id;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(int serviceCode) {
        this.serviceCode = serviceCode;
    }

    public int getEndStauts() {
        return endStauts;
    }

    public void setEndStauts(int endStauts) {
        this.endStauts = endStauts;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getModuleType() {
        return moduleType;
    }

    public void setModuleType(int moduleType) {
        this.moduleType = moduleType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class DataBean {
        /**
         * PrescriptionId : 847
         */

        private String PrescriptionId;

        public int getPrescriptionType() {
            return PrescriptionType;
        }

        public void setPrescriptionType(int prescriptionType) {
            PrescriptionType = prescriptionType;
        }

        private int PrescriptionType;

        public String getPrescriptionId() {
            return PrescriptionId;
        }

        public void setPrescriptionId(String PrescriptionId) {
            this.PrescriptionId = PrescriptionId;
        }
    }
}
