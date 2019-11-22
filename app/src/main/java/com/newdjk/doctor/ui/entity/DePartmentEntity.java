package com.newdjk.doctor.ui.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by EDZ on 2018/10/10.
 */

public class DePartmentEntity implements Serializable {


    /**
     * Code : 0
     * Message :
     * Data : {"Total":16,"ReturnData":[{"DepartmentId":7,"DepartmentName":"儿科","ParentId":0,"Path":"0,7,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":8,"DepartmentName":"急诊科","ParentId":0,"Path":"0,8,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":9,"DepartmentName":"内科","ParentId":0,"Path":"0,9,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":10,"DepartmentName":"肿瘤科","ParentId":0,"Path":"0,10,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":11,"DepartmentName":"外科","ParentId":0,"Path":"0,11,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":12,"DepartmentName":"传染科","ParentId":0,"Path":"0,12,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":13,"DepartmentName":"妇产科","ParentId":0,"Path":"0,13,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":14,"DepartmentName":"五官科","ParentId":0,"Path":"0,14,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":15,"DepartmentName":"肝病科","ParentId":0,"Path":"0,15,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":16,"DepartmentName":"皮肤性病科","ParentId":0,"Path":"0,16,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":17,"DepartmentName":"中医科","ParentId":0,"Path":"0,17,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":18,"DepartmentName":"男科","ParentId":0,"Path":"0,18,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":19,"DepartmentName":"精神科","ParentId":0,"Path":"0,19,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":20,"DepartmentName":"营养科","ParentId":0,"Path":"0,20,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":21,"DepartmentName":"心理科","ParentId":0,"Path":"0,21,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":22,"DepartmentName":"生殖健康","ParentId":0,"Path":"0,22,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"}]}
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

    public static class DataBean implements Serializable {
        /**
         * Total : 16
         * ReturnData : [{"DepartmentId":7,"DepartmentName":"儿科","ParentId":0,"Path":"0,7,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":8,"DepartmentName":"急诊科","ParentId":0,"Path":"0,8,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":9,"DepartmentName":"内科","ParentId":0,"Path":"0,9,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":10,"DepartmentName":"肿瘤科","ParentId":0,"Path":"0,10,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":11,"DepartmentName":"外科","ParentId":0,"Path":"0,11,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":12,"DepartmentName":"传染科","ParentId":0,"Path":"0,12,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":13,"DepartmentName":"妇产科","ParentId":0,"Path":"0,13,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":14,"DepartmentName":"五官科","ParentId":0,"Path":"0,14,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":15,"DepartmentName":"肝病科","ParentId":0,"Path":"0,15,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":16,"DepartmentName":"皮肤性病科","ParentId":0,"Path":"0,16,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":17,"DepartmentName":"中医科","ParentId":0,"Path":"0,17,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":18,"DepartmentName":"男科","ParentId":0,"Path":"0,18,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":19,"DepartmentName":"精神科","ParentId":0,"Path":"0,19,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":20,"DepartmentName":"营养科","ParentId":0,"Path":"0,20,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":21,"DepartmentName":"心理科","ParentId":0,"Path":"0,21,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"},{"DepartmentId":22,"DepartmentName":"生殖健康","ParentId":0,"Path":"0,22,","Order":0,"IsHot":0,"IsDisplay":1,"Remark":"","Invalid":0,"CreateTime":"2018-09-21 11:24:37","UpdateTime":"2018-09-21 13:52:15"}]
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

        public static class ReturnDataBean implements Serializable{
            /**
             * DepartmentId : 7
             * DepartmentName : 儿科
             * ParentId : 0
             * Path : 0,7,
             * Order : 0
             * IsHot : 0
             * IsDisplay : 1
             * Remark :
             * Invalid : 0
             * CreateTime : 2018-09-21 11:24:37
             * UpdateTime : 2018-09-21 13:52:15
             */

            private int DepartmentId;
            private String DepartmentName;
            private int ParentId;
            private String Path;
            private int Order;
            private int IsHot;
            private int IsDisplay;
            private String Remark;
            private int Invalid;
            private String CreateTime;
            private String UpdateTime;

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

            public int getParentId() {
                return ParentId;
            }

            public void setParentId(int ParentId) {
                this.ParentId = ParentId;
            }

            public String getPath() {
                return Path;
            }

            public void setPath(String Path) {
                this.Path = Path;
            }

            public int getOrder() {
                return Order;
            }

            public void setOrder(int Order) {
                this.Order = Order;
            }

            public int getIsHot() {
                return IsHot;
            }

            public void setIsHot(int IsHot) {
                this.IsHot = IsHot;
            }

            public int getIsDisplay() {
                return IsDisplay;
            }

            public void setIsDisplay(int IsDisplay) {
                this.IsDisplay = IsDisplay;
            }

            public String getRemark() {
                return Remark;
            }

            public void setRemark(String Remark) {
                this.Remark = Remark;
            }

            public int getInvalid() {
                return Invalid;
            }

            public void setInvalid(int Invalid) {
                this.Invalid = Invalid;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }

            public String getUpdateTime() {
                return UpdateTime;
            }

            public void setUpdateTime(String UpdateTime) {
                this.UpdateTime = UpdateTime;
            }
        }
    }
}
