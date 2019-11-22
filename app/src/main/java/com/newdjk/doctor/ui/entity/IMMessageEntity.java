package com.newdjk.doctor.ui.entity;

public class IMMessageEntity {

    /**
     * Id : 1
     * UserType : 64
     * UserId : 3
     * Identifier : sample string 4
     * UserSig : sample string 5
     * Nick : sample string 6
     * FaceUrl : sample string 7
     * UpdateTime : 2018-12-27T16:03:18.6508381+08:00
     * CreateTime : 2018-12-27T16:03:18.6508381+08:00
     */

    private int Id;
    private int UserType;
    private int UserId;
    private String Identifier;
    private String UserSig;
    private String Nick;
    private String FaceUrl;
    private String UpdateTime;
    private String CreateTime;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int UserType) {
        this.UserType = UserType;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String Identifier) {
        this.Identifier = Identifier;
    }

    public String getUserSig() {
        return UserSig;
    }

    public void setUserSig(String UserSig) {
        this.UserSig = UserSig;
    }

    public String getNick() {
        return Nick;
    }

    public void setNick(String Nick) {
        this.Nick = Nick;
    }

    public String getFaceUrl() {
        return FaceUrl;
    }

    public void setFaceUrl(String FaceUrl) {
        this.FaceUrl = FaceUrl;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }
}
