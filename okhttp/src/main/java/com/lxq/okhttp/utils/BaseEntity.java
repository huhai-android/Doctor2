package com.lxq.okhttp.utils;

/*
 *  @项目名：  Doctor
 *  @包名：    com.lxq.okhttp.utils
 *  @文件名:   BaseEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/27 18:49
 *  @描述：
 */
public class BaseEntity {
    private int Code;
    private Object Data;
    private String Message;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        this.Code = code;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        this.Data = data;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String msg) {
        this.Message = msg;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "Code=" + Code +
                ", Data=" + Data +
                ", Message='" + Message + '\'' +
                '}';
    }
}
