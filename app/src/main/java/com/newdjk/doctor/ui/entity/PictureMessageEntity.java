package com.newdjk.doctor.ui.entity;

import java.util.List;

public class PictureMessageEntity  {
    private int position;
    private List<String> url;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }
}
