package com.newdjk.doctor.ui.entity;

import java.util.Objects;

public class ServiceTypeOfPatientDoctorEntity {
    private String  ApplicantIM;
    private int ServiceType;
    private int Status;

    public String getApplicantIM() {
        return ApplicantIM;
    }

    public void setApplicantIM(String applicantIM) {
        ApplicantIM = applicantIM;
    }

    public int getServiceType() {
        return ServiceType;
    }

    public void setServiceType(int serviceType) {
        ServiceType = serviceType;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }



}
