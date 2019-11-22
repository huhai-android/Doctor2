package com.newdjk.doctor.ui.entity;

import java.util.List;

/**
 * Created by EDZ on 2018/10/11.
 */

public class SendGroupMessageEntity {

    private int QueryTypeId;
    private String QueryText;
    private int DrId;
    private String DrName;
    private String Content;
    private List<String> ImagePaths;

    public SendGroupMessageEntity() {}

    public int getQueryTypeId() {
        return QueryTypeId;
    }

    public void setQueryTypeId(int queryTypeId) {
        QueryTypeId = queryTypeId;
    }

    public String getQueryText() {
        return QueryText;
    }

    public void setQueryText(String queryText) {
        QueryText = queryText;
    }

    public int getDrId() {
        return DrId;
    }

    public void setDrId(int drId) {
        DrId = drId;
    }

    public String getDrName() {
        return DrName;
    }

    public void setDrName(String drName) {
        DrName = drName;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public  List<String> getImagePaths() {
        return ImagePaths;
    }

    public void setImagePaths( List<String> imagePaths) {
        ImagePaths = imagePaths;
    }
}


