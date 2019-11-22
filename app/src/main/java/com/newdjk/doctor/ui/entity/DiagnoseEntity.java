package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   AllDoctorCheckEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/6 14:48
 *  @描述：
 */
public class DiagnoseEntity {
    String question;
    String number;
    String audioText;

    public String getAudioText() {
        return audioText;
    }

    public void setAudioText(String audioText) {
        this.audioText = audioText;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "DiagnoseEntity{" +
                "question='" + question + '\'' +
                ", number='" + number + '\'' +
                ", audioText='" + audioText + '\'' +
                '}';
    }
}
