package com.newdjk.doctor.ui.entity;

import java.util.List;

/**
 * Created by EDZ on 2018/11/5.
 */

public class GroupSendEntity {


    /**
     * Code : 0
     * Message :
     * Data : {"Total":2,"ReturnData":[{"GroupMesId":6,"StatusID":3,"AllPeople":0,"DrId":107,"DrName":"谭医生","Content":"魔图","PatientText":null},{"GroupMesId":23,"StatusID":1,"AllPeople":0,"DrId":107,"DrName":"谭医生","Content":"体力里咯啦咯","PatientText":null}]}
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
         * Total : 2
         * ReturnData : [{"GroupMesId":6,"StatusID":3,"AllPeople":0,"DrId":107,"DrName":"谭医生","Content":"魔图","PatientText":null},{"GroupMesId":23,"StatusID":1,"AllPeople":0,"DrId":107,"DrName":"谭医生","Content":"体力里咯啦咯","PatientText":null}]
         */

        private int Total;
        private List<ReturnDataBean> ReturnData;

        public int getTotal() {
            return Total;
        }

        public void setTotal(int Total) {
            this.Total = Total;
        }

        public List<ReturnDataBean> getReturnData() {
            return ReturnData;
        }

        public void setReturnData(List<ReturnDataBean> ReturnData) {
            this.ReturnData = ReturnData;
        }

        public static class ReturnDataBean {
            /**
             * GroupMesId : 6
             * StatusID : 3
             * AllPeople : 0
             * DrId : 107
             * DrName : 谭医生
             * Content : 魔图
             * PatientText : null
             */

            private int GroupMesId;
            private int StatusID;
            private int AllPeople;
            private int DrId;
            private String DrName;
            private String Content;
            private String PatientText;
            private String CreateTime;

            public int getGroupMesId() {
                return GroupMesId;
            }

            public void setGroupMesId(int GroupMesId) {
                this.GroupMesId = GroupMesId;
            }

            public int getStatusID() {
                return StatusID;
            }

            public void setStatusID(int StatusID) {
                this.StatusID = StatusID;
            }

            public int getAllPeople() {
                return AllPeople;
            }

            public void setAllPeople(int AllPeople) {
                this.AllPeople = AllPeople;
            }

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

            public String getContent() {
                return Content;
            }

            public void setContent(String Content) {
                this.Content = Content;
            }

            public String getPatientText() {
                return PatientText;
            }

            public void setPatientText(String PatientText) {
                this.PatientText = PatientText;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String createTime) {
                CreateTime = createTime;
            }
        }
    }
}
