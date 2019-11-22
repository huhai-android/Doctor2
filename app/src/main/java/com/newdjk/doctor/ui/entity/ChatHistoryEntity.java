package com.newdjk.doctor.ui.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ChatHistoryEntity {

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
     * MsgSendTime : 2018-11-16T17:37:51.3120626+08:00
     * MsgRandom : 11
     * UpdateTime : 2018-11-16T17:37:51.3120626+08:00
     * CreateTime : 2018-11-16T17:37:51.3120626+08:00
     */
    @Id
    public long Id;
    public int PatientId;
    public int DoctorId;
    public long MsgSeq;
    public String From_Account;
    public String To_Account;
    public int MsgType;
    public String MsgBody;
    public long MsgTimestamp;
    public String MsgSendTime;
    public String MsgRandom;
    public String UpdateTime;
    public String CreateTime;
    public String getCreateTime() {
        return this.CreateTime;
    }
    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }
    public String getUpdateTime() {
        return this.UpdateTime;
    }
    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }
    public String getMsgRandom() {
        return this.MsgRandom;
    }
    public void setMsgRandom(String MsgRandom) {
        this.MsgRandom = MsgRandom;
    }
    public String getMsgSendTime() {
        return this.MsgSendTime;
    }
    public void setMsgSendTime(String MsgSendTime) {
        this.MsgSendTime = MsgSendTime;
    }
    public long getMsgTimestamp() {
        return this.MsgTimestamp;
    }
    public void setMsgTimestamp(long MsgTimestamp) {
        this.MsgTimestamp = MsgTimestamp;
    }
    public String getMsgBody() {
        return this.MsgBody;
    }
    public void setMsgBody(String MsgBody) {
        this.MsgBody = MsgBody;
    }
    public int getMsgType() {
        return this.MsgType;
    }
    public void setMsgType(int MsgType) {
        this.MsgType = MsgType;
    }
    public String getTo_Account() {
        return this.To_Account;
    }
    public void setTo_Account(String To_Account) {
        this.To_Account = To_Account;
    }
    public String getFrom_Account() {
        return this.From_Account;
    }
    public void setFrom_Account(String From_Account) {
        this.From_Account = From_Account;
    }
    public long getMsgSeq() {
        return this.MsgSeq;
    }
    public void setMsgSeq(long MsgSeq) {
        this.MsgSeq = MsgSeq;
    }
    public int getDoctorId() {
        return this.DoctorId;
    }
    public void setDoctorId(int DoctorId) {
        this.DoctorId = DoctorId;
    }
    public int getPatientId() {
        return this.PatientId;
    }
    public void setPatientId(int PatientId) {
        this.PatientId = PatientId;
    }
    public long getId() {
        return this.Id;
    }
    public void setId(long Id) {
        this.Id = Id;
    }
    @Generated(hash = 831800240)
    public ChatHistoryEntity(long Id, int PatientId, int DoctorId, long MsgSeq,
            String From_Account, String To_Account, int MsgType, String MsgBody,
            long MsgTimestamp, String MsgSendTime, String MsgRandom,
            String UpdateTime, String CreateTime) {
        this.Id = Id;
        this.PatientId = PatientId;
        this.DoctorId = DoctorId;
        this.MsgSeq = MsgSeq;
        this.From_Account = From_Account;
        this.To_Account = To_Account;
        this.MsgType = MsgType;
        this.MsgBody = MsgBody;
        this.MsgTimestamp = MsgTimestamp;
        this.MsgSendTime = MsgSendTime;
        this.MsgRandom = MsgRandom;
        this.UpdateTime = UpdateTime;
        this.CreateTime = CreateTime;
    }
    @Generated(hash = 481743750)
    public ChatHistoryEntity() {
    }
   


}
