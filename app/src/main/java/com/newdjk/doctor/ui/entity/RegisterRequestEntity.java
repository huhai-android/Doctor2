package com.newdjk.doctor.ui.entity;

public class RegisterRequestEntity {
    private int DrType;
    private int HospitalId;
    private int DepartmentId;
    private String DrName;
    private String Mobile;
    private String Password;
    private String HospitalName;
    private String DepartmentName;
    private String DoctorGroup;
    private int Position;


    public int getDrType() {
        return DrType;
    }

    public void setDrType(int drType) {
        DrType = drType;
    }

    public int getHospitalId() {
        return HospitalId;
    }

    public void setHospitalId(int hospitalId) {
        HospitalId = hospitalId;
    }

    public int getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(int departmentId) {
        DepartmentId = departmentId;
    }



    public String getDrName() {
        return DrName;
    }

    public void setDrName(String drName) {
        DrName = drName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getDoctorGroup() {
        return DoctorGroup;
    }

    public void setDoctorGroup(String doctorGroup) {
        DoctorGroup = doctorGroup;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }
}
