package com.newdjk.doctor.ui.entity;

import java.util.List;

public class MsgContentEntity {
    private String UUID;
    private String Text;
    private String Desc;
    private String Data;
    private long Size;
    private int Second;
    private String FileUrl;
    private List<ImageInfoArrayEntity> ImageInfoArray;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public long getSize() {
        return Size;
    }

    public void setSize(long size) {
        Size = size;
    }

    public int getSecond() {
        return Second;
    }

    public void setSecond(int second) {
        Second = second;
    }

    public String getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(String fileUrl) {
        FileUrl = fileUrl;
    }

    public List<ImageInfoArrayEntity> getImageInfoArray() {
        return ImageInfoArray;
    }

    public void setImageInfoArray(List<ImageInfoArrayEntity> imageInfoArray) {
        ImageInfoArray = imageInfoArray;
    }
}
