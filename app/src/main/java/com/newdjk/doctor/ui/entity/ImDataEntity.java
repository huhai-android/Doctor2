package com.newdjk.doctor.ui.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ImDataEntity {
    @Id(autoincrement = true)
    private Long id;
    private String lastMsg;
    private long lastTime;
    private String identifier;
    private String faceUrl;
    private String nickName;
    private long unReadNum;
    public long getUnReadNum() {
        return this.unReadNum;
    }
    public void setUnReadNum(long unReadNum) {
        this.unReadNum = unReadNum;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getFaceUrl() {
        return this.faceUrl;
    }
    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }
    public String getIdentifier() {
        return this.identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    public long getLastTime() {
        return this.lastTime;
    }
    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }
    public String getLastMsg() {
        return this.lastMsg;
    }
    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1076819820)
    public ImDataEntity(Long id, String lastMsg, long lastTime, String identifier,
            String faceUrl, String nickName, long unReadNum) {
        this.id = id;
        this.lastMsg = lastMsg;
        this.lastTime = lastTime;
        this.identifier = identifier;
        this.faceUrl = faceUrl;
        this.nickName = nickName;
        this.unReadNum = unReadNum;
    }
    @Generated(hash = 1543652068)
    public ImDataEntity() {
    }





}
