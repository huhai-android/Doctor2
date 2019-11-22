package com.lxq.okhttp.utils;

/*
 *  @项目名：  Doctor
 *  @包名：    com.lxq.okhttp.utils
 *  @文件名:   BaseEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/27 18:49
 *  @描述：
 */
public class BaseEntityCopy {

    /**
     * Code : 2
     * Message : 账号已禁用
     * Data :
     */

    private int Code;
    private String Message;
    private String Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }
}
