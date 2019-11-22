package com.newdjk.doctor.ui.entity;

public class ResponseEntity<T> {
    private int Code;
    private T Data;
    private String Message;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "Code=" + Code +
                ", Data=" + Data +
                ", Message='" + Message + '\'' +
                '}';
    }
}
