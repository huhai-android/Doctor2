package com.newdjk.doctor.ui.entity;

public class UserEntity {
    private int Code;
    private IMMessageEntity Data;
    private String Message;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public IMMessageEntity getData() {
        return Data;
    }

    public void setData(IMMessageEntity data) {
        Data = data;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
