package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   ZhuanzhenEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/3/18 14:13
 *  @描述：
 */
public class ZhuanzhenEntity {

    /**
     * Code : 0
     * Message :
     * Data : true
     */

    private int Code;
    private String Message;
    private boolean Data;

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

    public boolean isData() {
        return Data;
    }

    public void setData(boolean Data) {
        this.Data = Data;
    }
}
