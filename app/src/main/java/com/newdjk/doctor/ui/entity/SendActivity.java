package com.newdjk.doctor.ui.entity;

import java.util.List;

/**
 * Created by Struggle on 2018/10/15.
 */

public class SendActivity {


    /**
     * DrId : 1
     * ShareLink : sample string 2
     * Title : sample string 3
     * Content : sample string 4
     * Patients : [{"AccountId":1,"PatientName":"sample string 2"},{"AccountId":1,"PatientName":"sample string 2"}]
     */

    private int DrId;
    private String ShareLink;
    private String Title;
    private String Content;
    private List<PatientsBean> Patients;

    public int getDrId() {
        return DrId;
    }

    public void setDrId(int DrId) {
        this.DrId = DrId;
    }

    public String getShareLink() {
        return ShareLink;
    }

    public void setShareLink(String ShareLink) {
        this.ShareLink = ShareLink;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public List<PatientsBean> getPatients() {
        return Patients;
    }

    public void setPatients(List<PatientsBean> Patients) {
        this.Patients = Patients;
    }

    public static class PatientsBean {
        /**
         * AccountId : 1
         * PatientName : sample string 2
         */

        private int AccountId;
        private String PatientName;

        public int getAccountId() {
            return AccountId;
        }

        public void setAccountId(int AccountId) {
            this.AccountId = AccountId;
        }

        public String getPatientName() {
            return PatientName;
        }

        public void setPatientName(String PatientName) {
            this.PatientName = PatientName;
        }
    }
}
