package com.newdjk.doctor.ui.entity;

import java.util.List;
import java.util.Set;

public class ServiceTypeOfPatientDoctorRequestEntity {
    private String DoctorIMId;
    private Set<String> ApplicantIMIdList;

    public String getDoctorIMId() {
        return DoctorIMId;
    }

    public void setDoctorIMId(String doctorIMId) {
        DoctorIMId = doctorIMId;
    }

    public Set<String> getApplicantIMIdList() {
        return ApplicantIMIdList;
    }

    public void setApplicantIMIdList(Set<String> applicantIMIdList) {
        ApplicantIMIdList = applicantIMIdList;
    }
}
