package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   AudioToTextEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/12/21 9:52
 *  @描述：
 */
public class AudioToTextEntity {


    /**
     * rtn : 0
     * message : success
     * requestId : 36d50f6896d94dccbc687289bb6229b4
     * resultText : 你写的吗？这个语音。喂喂喂喂喂喂喂。
     */

    private int rtn;
    private String message;
    private String requestId;
    private String resultText;

    public int getRtn() {
        return rtn;
    }

    public void setRtn(int rtn) {
        this.rtn = rtn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }
}
