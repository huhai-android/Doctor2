package com.newdjk.doctor.ui.entity;

public class DoctorRegImgEntity {
    private int ImgType;
    private String ImgPath;
    private String Number;
    private String RegisterTime;
    private String ValidTime;
    private String SchoolNumber;
    private String CertifyType;

    public int getImgType() {
        return ImgType;
    }

    public void setImgType(int imgType) {
        ImgType = imgType;
    }

    public String getImgPath() {
        return ImgPath;
    }

    public void setImgPath(String imgPath) {
        ImgPath = imgPath;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getRegisterTime() {
        return RegisterTime;
    }

    public void setRegisterTime(String registerTime) {
        RegisterTime = registerTime;
    }

    public String getValidTime() {
        return ValidTime;
    }

    public void setValidTime(String validTime) {
        ValidTime = validTime;
    }

    public String getSchoolNumber() {
        return SchoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        SchoolNumber = schoolNumber;
    }

    public String getCertifyType() {
        return CertifyType;
    }

    public void setCertifyType(String certifyType) {
        CertifyType = certifyType;
    }
}
