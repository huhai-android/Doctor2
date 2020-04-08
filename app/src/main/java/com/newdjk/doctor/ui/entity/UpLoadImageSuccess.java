package com.newdjk.doctor.ui.entity;

public class UpLoadImageSuccess {
    public String imagePath;
    public String fileimagePath;

    public UpLoadImageSuccess(String imagePath, String fileimagePath) {
        this.imagePath = imagePath;
        this.fileimagePath = fileimagePath;
    }

    public String getFileimagePath() {
        return fileimagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setFileimagePath(String fileimagePath) {
        this.fileimagePath = fileimagePath;
    }
}
