package com.newdjk.doctor.ui.entity;

import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   ShopLoginEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/4/18 17:30
 *  @描述：
 */
public class ShopLoginEntity {

    /**
     * doctor : {"Code":0,"Data":{"Token":"eyJpZCI6MTMwLCJpYXQiOjE1NTU1NzgyOTIsImV4cCI6MTU4NzExNDI5MiwidHlwZSI6MiwiY2xpZW50IjoiQVBQIiwicmVnaXN0cmF0aW9uSWQiOiIxNDBmZTFkYTllZmYwZTY4MjVjIiwiYWNjb3VudElkIjoxMzB9.pDOyUpXvWvwUkxZhtHISfffDkSHUMN67EMlh0ySoPpA","User":{"DepartmentId":0,"DoctorId":130,"DoctorName":"张飞","DrType":1,"Mobile":"13488881115","OrgId":[1],"Position":0,"Sex":3,"Status":1}},"Message":"登录成功"}
     */

    private DoctorBean doctor;

    public DoctorBean getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorBean doctor) {
        this.doctor = doctor;
    }

    public static class DoctorBean {
        /**
         * Code : 0
         * Data : {"Token":"eyJpZCI6MTMwLCJpYXQiOjE1NTU1NzgyOTIsImV4cCI6MTU4NzExNDI5MiwidHlwZSI6MiwiY2xpZW50IjoiQVBQIiwicmVnaXN0cmF0aW9uSWQiOiIxNDBmZTFkYTllZmYwZTY4MjVjIiwiYWNjb3VudElkIjoxMzB9.pDOyUpXvWvwUkxZhtHISfffDkSHUMN67EMlh0ySoPpA","User":{"DepartmentId":0,"DoctorId":130,"DoctorName":"张飞","DrType":1,"Mobile":"13488881115","OrgId":[1],"Position":0,"Sex":3,"Status":1}}
         * Message : 登录成功
         */

        private int Code;
        private DataBean Data;
        private String Message;

        public int getCode() {
            return Code;
        }

        public void setCode(int Code) {
            this.Code = Code;
        }

        public DataBean getData() {
            return Data;
        }

        public void setData(DataBean Data) {
            this.Data = Data;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public static class DataBean {
            /**
             * Token : eyJpZCI6MTMwLCJpYXQiOjE1NTU1NzgyOTIsImV4cCI6MTU4NzExNDI5MiwidHlwZSI6MiwiY2xpZW50IjoiQVBQIiwicmVnaXN0cmF0aW9uSWQiOiIxNDBmZTFkYTllZmYwZTY4MjVjIiwiYWNjb3VudElkIjoxMzB9.pDOyUpXvWvwUkxZhtHISfffDkSHUMN67EMlh0ySoPpA
             * User : {"DepartmentId":0,"DoctorId":130,"DoctorName":"张飞","DrType":1,"Mobile":"13488881115","OrgId":[1],"Position":0,"Sex":3,"Status":1}
             */

            private String Token;
            private UserBean User;

            public String getToken() {
                return Token;
            }

            public void setToken(String Token) {
                this.Token = Token;
            }

            public UserBean getUser() {
                return User;
            }

            public void setUser(UserBean User) {
                this.User = User;
            }

            public static class UserBean {
                /**
                 * DepartmentId : 0
                 * DoctorId : 130
                 * DoctorName : 张飞
                 * DrType : 1
                 * Mobile : 13488881115
                 * OrgId : [1]
                 * Position : 0
                 * Sex : 3
                 * Status : 1
                 */

                private int DepartmentId;
                private int DoctorId;
                private String DoctorName;
                private int DrType;
                private String Mobile;
                private int Position;
                private int Sex;
                private int Status;
                private List<Integer> OrgId;

                public int getDepartmentId() {
                    return DepartmentId;
                }

                public void setDepartmentId(int DepartmentId) {
                    this.DepartmentId = DepartmentId;
                }

                public int getDoctorId() {
                    return DoctorId;
                }

                public void setDoctorId(int DoctorId) {
                    this.DoctorId = DoctorId;
                }

                public String getDoctorName() {
                    return DoctorName;
                }

                public void setDoctorName(String DoctorName) {
                    this.DoctorName = DoctorName;
                }

                public int getDrType() {
                    return DrType;
                }

                public void setDrType(int DrType) {
                    this.DrType = DrType;
                }

                public String getMobile() {
                    return Mobile;
                }

                public void setMobile(String Mobile) {
                    this.Mobile = Mobile;
                }

                public int getPosition() {
                    return Position;
                }

                public void setPosition(int Position) {
                    this.Position = Position;
                }

                public int getSex() {
                    return Sex;
                }

                public void setSex(int Sex) {
                    this.Sex = Sex;
                }

                public int getStatus() {
                    return Status;
                }

                public void setStatus(int Status) {
                    this.Status = Status;
                }

                public List<Integer> getOrgId() {
                    return OrgId;
                }

                public void setOrgId(List<Integer> OrgId) {
                    this.OrgId = OrgId;
                }
            }
        }
    }
}
