package com.newdjk.doctor.ui.entity;

public class TestEntity {


    /**
     * Id : 1
     * PatientId : 2
     * DoctorId : 3
     * MsgSeq : 4
     * From_Account : sample string 5
     * To_Account : sample string 6
     * MsgType : 64
     * MsgBody : sample string 8
     * MsgTimestamp : 9
     * MsgSendTime : 2018-11-17T13:20:45.3830509+08:00
     * MsgRandom : 11
     * UpdateTime : 2018-11-17T13:20:45.3830509+08:00
     * CreateTime : 2018-11-17T13:20:45.3830509+08:00
     */

    private int Id;
    private int PatientId;
    private int DoctorId;
    private int MsgSeq;
    private String From_Account;
    private String To_Account;
    private String MsgType;
    private String MsgBody;
    private int MsgTimestamp;
    private String MsgSendTime;
    private String MsgRandom;
    private String UpdateTime;
    private String CreateTime;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getPatientId() {
        return PatientId;
    }

    public void setPatientId(int patientId) {
        PatientId = patientId;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int doctorId) {
        DoctorId = doctorId;
    }

    public String getFrom_Account() {
        return From_Account;
    }

    public void setFrom_Account(String from_Account) {
        From_Account = from_Account;
    }

    public String getTo_Account() {
        return To_Account;
    }

    public void setTo_Account(String to_Account) {
        To_Account = to_Account;
    }

    public String getMsgBody() {
        return MsgBody;
    }

    public void setMsgBody(String msgBody) {
        MsgBody = msgBody;
    }

    public String getMsgSendTime() {
        return MsgSendTime;
    }

    public void setMsgSendTime(String msgSendTime) {
        MsgSendTime = msgSendTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public int getMsgSeq() {
        return MsgSeq;
    }

    public void setMsgSeq(int msgSeq) {
        MsgSeq = msgSeq;
    }

    public String getMsgRandom() {
        return MsgRandom;
    }

    public void setMsgRandom(String msgRandom) {
        MsgRandom = msgRandom;
    }

    public int getMsgTimestamp() {
        return MsgTimestamp;
    }

    public void setMsgTimestamp(int msgTimestamp) {
        MsgTimestamp = msgTimestamp;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }
}
