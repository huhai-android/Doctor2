package com.newdjk.doctor.ui.entity;

import java.util.List;

public class H5PictureMessageEntity {

    /**
     * position : 0
     * url : [{"DisplayPath":"https://resource.newstartcare.com/PatientArchives/201905/1905170944136794513.jpg","SavePath":"PatientArchives/201905/1905170944136794513.jpg"}]
     */

    private int position;
    private List<UrlBean> url;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<UrlBean> getUrl() {
        return url;
    }

    public void setUrl(List<UrlBean> url) {
        this.url = url;
    }

    public static class UrlBean {
        /**
         * DisplayPath : https://resource.newstartcare.com/PatientArchives/201905/1905170944136794513.jpg
         * SavePath : PatientArchives/201905/1905170944136794513.jpg
         */

        private String DisplayPath;
        private String SavePath;

        public String getDisplayPath() {
            return DisplayPath;
        }

        public void setDisplayPath(String DisplayPath) {
            this.DisplayPath = DisplayPath;
        }

        public String getSavePath() {
            return SavePath;
        }

        public void setSavePath(String SavePath) {
            this.SavePath = SavePath;
        }
    }
}
