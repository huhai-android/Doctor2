package com.newdjk.doctor.ui.entity;

import java.util.List;

public class QueryDiseaseEntity {

    /**
     * Total : 1
     * ReturnData : [{"DiseaseId":1,"DiseaseName":"sample string 2","DiseasePinyin":"sample string 3","Symptom":"sample string 4","Summary":"sample string 5","ImagePath":"sample string 6","DiseaseOrder":7,"Invalid":64,"CreateTime":"2018-12-20T13:40:15.5828028+08:00","UpdateTime":"2018-12-20T13:40:15.5828028+08:00"},{"DiseaseId":1,"DiseaseName":"sample string 2","DiseasePinyin":"sample string 3","Symptom":"sample string 4","Summary":"sample string 5","ImagePath":"sample string 6","DiseaseOrder":7,"Invalid":64,"CreateTime":"2018-12-20T13:40:15.5828028+08:00","UpdateTime":"2018-12-20T13:40:15.5828028+08:00"}]
     */

    private int Total;
    private List<ReturnDataBean> ReturnData;

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public List<ReturnDataBean> getReturnData() {
        return ReturnData;
    }

    public void setReturnData(List<ReturnDataBean> ReturnData) {
        this.ReturnData = ReturnData;
    }

    public static class ReturnDataBean {
        /**
         * DiseaseId : 1
         * DiseaseName : sample string 2
         * DiseasePinyin : sample string 3
         * Symptom : sample string 4
         * Summary : sample string 5
         * ImagePath : sample string 6
         * DiseaseOrder : 7
         * Invalid : 64
         * CreateTime : 2018-12-20T13:40:15.5828028+08:00
         * UpdateTime : 2018-12-20T13:40:15.5828028+08:00
         */

        private int DiseaseId;
        private String DiseaseName;
        private String DiseasePinyin;
        private String Symptom;
        private String Summary;
        private String ImagePath;
        private int DiseaseOrder;
        private int Invalid;
        private String CreateTime;
        private String UpdateTime;
        private boolean isSelected = false;

        public int getDiseaseId() {
            return DiseaseId;
        }

        public void setDiseaseId(int DiseaseId) {
            this.DiseaseId = DiseaseId;
        }

        public String getDiseaseName() {
            return DiseaseName;
        }

        public void setDiseaseName(String DiseaseName) {
            this.DiseaseName = DiseaseName;
        }

        public String getDiseasePinyin() {
            return DiseasePinyin;
        }

        public void setDiseasePinyin(String DiseasePinyin) {
            this.DiseasePinyin = DiseasePinyin;
        }

        public String getSymptom() {
            return Symptom;
        }

        public void setSymptom(String Symptom) {
            this.Symptom = Symptom;
        }

        public String getSummary() {
            return Summary;
        }

        public void setSummary(String Summary) {
            this.Summary = Summary;
        }

        public String getImagePath() {
            return ImagePath;
        }

        public void setImagePath(String ImagePath) {
            this.ImagePath = ImagePath;
        }

        public int getDiseaseOrder() {
            return DiseaseOrder;
        }

        public void setDiseaseOrder(int DiseaseOrder) {
            this.DiseaseOrder = DiseaseOrder;
        }

        public int getInvalid() {
            return Invalid;
        }

        public void setInvalid(int Invalid) {
            this.Invalid = Invalid;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        @Override
        public String toString() {
            return "ReturnDataBean{" +
                    "DiseaseId=" + DiseaseId +
                    ", DiseaseName='" + DiseaseName + '\'' +
                    ", DiseasePinyin='" + DiseasePinyin + '\'' +
                    ", Symptom='" + Symptom + '\'' +
                    ", Summary='" + Summary + '\'' +
                    ", ImagePath='" + ImagePath + '\'' +
                    ", DiseaseOrder=" + DiseaseOrder +
                    ", Invalid=" + Invalid +
                    ", CreateTime='" + CreateTime + '\'' +
                    ", UpdateTime='" + UpdateTime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "QueryDiseaseEntity{" +
                "Total=" + Total +
                ", ReturnData=" + ReturnData +
                '}';
    }
}
