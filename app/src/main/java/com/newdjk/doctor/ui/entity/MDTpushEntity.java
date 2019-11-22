package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   MDTpushEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/9/9 9:52
 *  @描述：
 */
public class MDTpushEntity {

    /**
     * data : {"IMGroupId":"G201909071604232084","PatientId":"16","SubjectBuyRecordId":"37"}
     * endStauts : 0
     * id : 9761
     * moduleType : 9
     * msgType : 2
     * serviceCode : 0
     * time : 2019-09-07 16:04:25
     * title : 多学科会诊提醒
     * type : 239
     * userId : 57
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
         * IMGroupId : G201909071604232084
         * PatientId : 16
         * SubjectBuyRecordId : 37
         */

        private String IMGroupId;
        private String PatientId;
        private String SubjectBuyRecordId;

        public String getIMGroupId() {
            return IMGroupId;
        }

        public void setIMGroupId(String IMGroupId) {
            this.IMGroupId = IMGroupId;
        }

        public String getPatientId() {
            return PatientId;
        }

        public void setPatientId(String PatientId) {
            this.PatientId = PatientId;
        }

        public String getSubjectBuyRecordId() {
            return SubjectBuyRecordId;
        }

        public void setSubjectBuyRecordId(String SubjectBuyRecordId) {
            this.SubjectBuyRecordId = SubjectBuyRecordId;
        }
    }
}
