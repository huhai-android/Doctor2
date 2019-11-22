package com.newdjk.doctor.ui.entity;

import java.util.List;

/**
 * Created by EDZ on 2018/10/11.
 */

public class SuggesttionEntity {

    public SuggesttionEntity() {
    }

    private int SourceId;
    private String SourceName;
    private int SourceType;
    private String Contact;
    private String Advice;
    private List<String> ImgPaths;

    public int getSourceId() {
        return SourceId;
    }

    public void setSourceId(int sourceId) {
        SourceId = sourceId;
    }

    public String getSourceName() {
        return SourceName;
    }

    public void setSourceName(String sourceName) {
        SourceName = sourceName;
    }

    public int getSourceType() {
        return SourceType;
    }

    public void setSourceType(int sourceType) {
        SourceType = sourceType;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getAdvice() {
        return Advice;
    }

    public void setAdvice(String advice) {
        Advice = advice;
    }

    public List<String> getImgPaths() {
        return ImgPaths;
    }

    public void setImgPaths(List<String> imgPaths) {
        ImgPaths = imgPaths;
    }
}
