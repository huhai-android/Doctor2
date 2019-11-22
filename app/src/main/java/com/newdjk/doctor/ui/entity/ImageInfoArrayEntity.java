package com.newdjk.doctor.ui.entity;

public class ImageInfoArrayEntity {
    private int Type;
    private int Size;
    private int Width;
    private int Height;
    private String URL;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int size) {
        Size = size;
    }

    public int getWidth() {
        return Width;
    }

    public void setWidth(int width) {
        Width = width;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
