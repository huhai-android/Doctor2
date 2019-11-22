package com.newdjk.doctor.ui.entity;

public class UserMessageEntity {

    /**
     * Code : 0
     * Message :
     * Data : {"DrId":161,"DrName":"张扬","Mobile":"13823230159","Password":"zz123456","DrType":2,"HospitalId":826,"HospitalName":"五华县人民医院","DepartmentId":59,"DepartmentName":"肝胆外科","Position":0,"DoctorGroup":null}
     */

    private int Code;
    private String Message;
    private DataBean Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * DrId : 161
         * DrName : 张扬
         * Mobile : 13823230159
         * Password : zz123456
         * DrType : 2
         * HospitalId : 826
         * HospitalName : 五华县人民医院
         * DepartmentId : 59
         * DepartmentName : 肝胆外科
         * Position : 0
         * DoctorGroup : null
         */

        private int DrId;
        private String DrName;
        private String Mobile;
        private String Password;
        private int DrType;
        private int HospitalId;
        private String HospitalName;
        private int DepartmentId;
        private String DepartmentName;
        private int Position;
        private Object DoctorGroup;

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

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String Password) {
            this.Password = Password;
        }

        public int getDrType() {
            return DrType;
        }

        public void setDrType(int DrType) {
            this.DrType = DrType;
        }

        public int getHospitalId() {
            return HospitalId;
        }

        public void setHospitalId(int HospitalId) {
            this.HospitalId = HospitalId;
        }

        public String getHospitalName() {
            return HospitalName;
        }

        public void setHospitalName(String HospitalName) {
            this.HospitalName = HospitalName;
        }

        public int getDepartmentId() {
            return DepartmentId;
        }

        public void setDepartmentId(int DepartmentId) {
            this.DepartmentId = DepartmentId;
        }

        public String getDepartmentName() {
            return DepartmentName;
        }

        public void setDepartmentName(String DepartmentName) {
            this.DepartmentName = DepartmentName;
        }

        public int getPosition() {
            return Position;
        }

        public void setPosition(int Position) {
            this.Position = Position;
        }

        public Object getDoctorGroup() {
            return DoctorGroup;
        }

        public void setDoctorGroup(Object DoctorGroup) {
            this.DoctorGroup = DoctorGroup;
        }
    }
}
