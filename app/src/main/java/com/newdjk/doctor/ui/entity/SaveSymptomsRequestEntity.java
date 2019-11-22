package com.newdjk.doctor.ui.entity;

import java.util.List;

public class SaveSymptomsRequestEntity {

    /**
     * DrId : 1
     * DrName : sample string 2
     * Diseases : [{"DiseaseId":1,"DiseaseName":"sample string 2"},{"DiseaseId":1,"DiseaseName":"sample string 2"}]
     */

    private int DrId;
    private String DrName;
    private List<DiseasesBean> Diseases;

    public int getDrId() {
        return DrId;
    }

    public void setDrId(int DrId) {
        this.DrId = DrId;
    }

    public String getDrName() {
        return DrName;
    }

    public void setDrName(String DrName) {
        this.DrName = DrName;
    }

    public List<DiseasesBean> getDiseases() {
        return Diseases;
    }

    public void setDiseases(List<DiseasesBean> Diseases) {
        this.Diseases = Diseases;
    }

    public static class DiseasesBean {
        /**
         * DiseaseId : 1
         * DiseaseName : sample string 2
         */

        private int DiseaseId;
        private String DiseaseName;

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
    }
}
