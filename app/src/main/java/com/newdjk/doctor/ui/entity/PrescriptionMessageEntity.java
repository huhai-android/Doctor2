package com.newdjk.doctor.ui.entity;

public class PrescriptionMessageEntity<T> {
    private LoginEntity doctor;
    private T patient;

    public LoginEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(LoginEntity doctor) {
        this.doctor = doctor;
    }

    public T getPatient() {
        return patient;
    }

    public void setPatient(T patient) {
        this.patient = patient;
    }
}
