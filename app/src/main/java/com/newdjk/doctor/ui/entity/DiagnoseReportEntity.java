package com.newdjk.doctor.ui.entity;

import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   AllDoctorCheckEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/6 14:48
 *  @描述：
 */
public class DiagnoseReportEntity {
    String title;
    String desc;
    int imageCount;
    String message;
    public List<DataBean>  Data;

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean>  data) {
        Data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public static class DataBean {
        int imageCount;

        public int getImageCount() {
            return imageCount;
        }

        public void setImageCount(int imageCount) {
            this.imageCount = imageCount;
        }
    }
}
