package com.newdjk.doctor.ui.entity;

/**
 * Created by user on 2018/4/17.
 */

public class LoginEntity {
//    {"Code":0,"Message":"登录成功","Data":{"Token":"eyJpZCI6NCwiaWF0IjoxNTM1NDI2MDk5LCJleHAiOjE1MzU1MTI0OTksInR5cGUiOjEsImNsaWVudCI6bnVsbCwicmVnaXN0cmF0aW9uSWQiOm51bGx9.MeeJGUytVaMDNTcLBr18AMI6obOHvPfPWrzPonNBhro","User":{"Id":4,"Name":"13265558043","Sex":3,"Mobile":"13265558043"}}}
    private int Code;
    private DataEntity Data;
    private String Message;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public DataEntity getData() {
        return Data;
    }

    public void setData(DataEntity data) {
        Data = data;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public class DataEntity {
        private String Token;
        private UserBean User;

        public String getToken() {
            return Token;
        }

        public void setToken(String token) {
            Token = token;
        }

        public UserBean getData() {
            return User;
        }

        public void setData(UserBean user) {
            User = user;
        }

        public class UserBean {
            private int DoctorId;
            private int[] OrgId;
            private int DrType;
            private int DepartmentId;
            private int Position;
            private int Sex;
            private int Status;

            private String DoctorName;
            private String OrgName;
            private String Mobile;
            private String DoctorLevelId;
            private String DoctorLevelName;

            public String getDoctorLevelId() {
                return DoctorLevelId;
            }

            public void setDoctorLevelId(String doctorLevelId) {
                DoctorLevelId = doctorLevelId;
            }

            public String getDoctorLevelName() {
                return DoctorLevelName;
            }

            public void setDoctorLevelName(String doctorLevelName) {
                DoctorLevelName = doctorLevelName;
            }

            public int getDoctorId() {
                return DoctorId;
            }

            public void setDoctorId(int doctorId) {
                DoctorId = doctorId;
            }

            public int[] getOrgId() {
                return OrgId;
            }

            public void setOrgId(int[] orgId) {
                OrgId = orgId;
            }

            public int getDrType() {
                return DrType;
            }

            public void setDrType(int drType) {
                DrType = drType;
            }

            public int getDepartmentId() {
                return DepartmentId;
            }

            public void setDepartmentId(int departmentId) {
                DepartmentId = departmentId;
            }

            public int getPosition() {
                return Position;
            }

            public void setPosition(int position) {
                Position = position;
            }

            public int getSex() {
                return Sex;
            }

            public void setSex(int sex) {
                Sex = sex;
            }

            public String getDoctorName() {
                return DoctorName;
            }

            public void setDoctorName(String doctorName) {
                DoctorName = doctorName;
            }

            public String getOrgName() {
                return OrgName;
            }

            public void setOrgName(String orgName) {
                OrgName = orgName;
            }

            public String getMobile() {
                return Mobile;
            }

            public void setMobile(String mobile) {
                Mobile = mobile;
            }
            public String toString() {
                return "DoctorId="+DoctorId+",DoctorName="+DoctorName;
            }

            public int getStatus() {
                return Status;
            }

            public void setStatus(int status) {
                Status = status;
            }
        }

    }

}
