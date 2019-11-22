package com.newdjk.doctor.ui.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Struggle on 2018/10/15.
 */

public class MianzhenListEntity implements Serializable {


    /**
     * Total : 2
     * ReturnData : [{"AppointmentDate":"2019-06-28 00:00:00","ReserveNums":2,"FaceConsultationRecords":[{"PatientPic":"http://userimage.newstarthealth.cn/pat/0/1492.jpg?dt=201906280416746","WeekDay":5,"CanCancel":false,"RecordId":56,"DrId":306,"DrName":"艾美","DepartmentId":0,"DepartmentName":null,"PatientAccountId":1017,"PatientId":1492,"PatientName":"理解","PatientSex":2,"PatientAge":"20岁","PatientMobile":"15877565355","ReserveNo":"201906280000056","ConsultationHospital":null,"ConsultationAddress":"深圳市飞飞哥","AppointmentDate":"2019-06-28 00:00:00","StartTime":"19:00:00","EndTime":"23:00:00","TimePeriod":3,"DutyId":53,"Description":null,"AppointmentPrice":0,"PayChannel":1,"PayStatus":5,"PayNo":"","PayOrderTime":"2019-06-27 14:24:39","PayTime":"2019-06-27 14:24:39","PayOrderNo":"20190627142439303501","PayAmount":null,"AppointmentStatus":2,"CancelType":1,"CancelOpId":1492,"CancelReason":null,"CancelTime":null,"Invalid":0,"CreateTime":"2019-06-27 14:24:39","UpdateTime":"2019-06-27 16:09:32"},{"PatientPic":"http://userimage.newstarthealth.cn/pat/0/1492.jpg?dt=201906280416746","WeekDay":5,"CanCancel":false,"RecordId":57,"DrId":306,"DrName":"艾美","DepartmentId":0,"DepartmentName":null,"PatientAccountId":1017,"PatientId":1492,"PatientName":"理解","PatientSex":2,"PatientAge":"20岁","PatientMobile":"15877565355","ReserveNo":"201906280000057","ConsultationHospital":null,"ConsultationAddress":"深圳市飞飞哥","AppointmentDate":"2019-06-28 00:00:00","StartTime":"19:00:00","EndTime":"23:00:00","TimePeriod":3,"DutyId":53,"Description":null,"AppointmentPrice":0.1,"PayChannel":1,"PayStatus":5,"PayNo":"4200000315201906272837173508","PayOrderTime":"2019-06-27 14:25:57","PayTime":"2019-06-27 14:26:04","PayOrderNo":"20190627142556665598","PayAmount":null,"AppointmentStatus":2,"CancelType":2,"CancelOpId":306,"CancelReason":null,"CancelTime":null,"Invalid":0,"CreateTime":"2019-06-27 14:25:56","UpdateTime":"2019-06-27 16:09:36"},{"PatientPic":"http://userimage.newstarthealth.cn/pat/0/1497.jpg?dt=201906280416746","WeekDay":5,"CanCancel":false,"RecordId":68,"DrId":306,"DrName":"艾美","DepartmentId":0,"DepartmentName":null,"PatientAccountId":1021,"PatientId":1497,"PatientName":"ceshui ","PatientSex":1,"PatientAge":"18岁","PatientMobile":"15088135555","ReserveNo":"201906280000068","ConsultationHospital":null,"ConsultationAddress":"深圳市飞飞哥","AppointmentDate":"2019-06-28 00:00:00","StartTime":"19:00:00","EndTime":"23:00:00","TimePeriod":3,"DutyId":53,"Description":null,"AppointmentPrice":0.01,"PayChannel":1,"PayStatus":8,"PayNo":"","PayOrderTime":"2019-06-27 16:57:33","PayTime":"2019-06-27 17:47:00","PayOrderNo":"20190627165733439382","PayAmount":null,"AppointmentStatus":0,"CancelType":null,"CancelOpId":null,"CancelReason":null,"CancelTime":null,"Invalid":0,"CreateTime":"2019-06-27 16:57:33","UpdateTime":"2019-06-27 17:47:00"},{"PatientPic":"http://userimage.newstarthealth.cn/pat/0/368.jpg?dt=201906280416746","WeekDay":5,"CanCancel":false,"RecordId":69,"DrId":306,"DrName":"艾美","DepartmentId":0,"DepartmentName":null,"PatientAccountId":10,"PatientId":368,"PatientName":"胡","PatientSex":2,"PatientAge":"27岁","PatientMobile":"15088131721","ReserveNo":"201906280000069","ConsultationHospital":null,"ConsultationAddress":"深圳市飞飞哥","AppointmentDate":"2019-06-28 00:00:00","StartTime":"19:00:00","EndTime":"23:00:00","TimePeriod":3,"DutyId":53,"Description":null,"AppointmentPrice":0.01,"PayChannel":1,"PayStatus":8,"PayNo":"","PayOrderTime":"2019-06-27 16:59:09","PayTime":"2019-06-27 17:47:00","PayOrderNo":"20190627165909448179","PayAmount":null,"AppointmentStatus":0,"CancelType":null,"CancelOpId":null,"CancelReason":null,"CancelTime":null,"Invalid":0,"CreateTime":"2019-06-27 16:59:09","UpdateTime":"2019-06-27 17:47:00"}]},{"AppointmentDate":"2019-06-29 00:00:00","ReserveNums":3,"FaceConsultationRecords":[{"PatientPic":"http://userimage.newstarthealth.cn/pat/0/318.jpg?dt=201906280416800","WeekDay":6,"CanCancel":false,"RecordId":59,"DrId":306,"DrName":"艾美","DepartmentId":0,"DepartmentName":null,"PatientAccountId":34,"PatientId":318,"PatientName":"花生","PatientSex":2,"PatientAge":"29岁","PatientMobile":"13410328112","ReserveNo":"201906290000059","ConsultationHospital":null,"ConsultationAddress":"深圳市飞飞哥","AppointmentDate":"2019-06-29 00:00:00","StartTime":"19:00:00","EndTime":"23:00:00","TimePeriod":3,"DutyId":55,"Description":null,"AppointmentPrice":0.1,"PayChannel":1,"PayStatus":5,"PayNo":"4200000328201906273083313502","PayOrderTime":"2019-06-27 15:17:54","PayTime":"2019-06-27 15:18:03","PayOrderNo":"20190627151753954431","PayAmount":null,"AppointmentStatus":2,"CancelType":3,"CancelOpId":null,"CancelReason":"医生停诊","CancelTime":null,"Invalid":0,"CreateTime":"2019-06-27 15:17:53","UpdateTime":"2019-06-27 16:09:37"},{"PatientPic":"http://userimage.newstarthealth.cn/pat/0/1496.jpg?dt=201906280416800","WeekDay":6,"CanCancel":false,"RecordId":62,"DrId":306,"DrName":"艾美","DepartmentId":0,"DepartmentName":null,"PatientAccountId":1020,"PatientId":1496,"PatientName":"","PatientSex":3,"PatientAge":"","PatientMobile":"15878855805","ReserveNo":"201906290000062","ConsultationHospital":null,"ConsultationAddress":"深圳市飞飞哥","AppointmentDate":"2019-06-29 00:00:00","StartTime":"09:00:00","EndTime":"10:00:00","TimePeriod":1,"DutyId":58,"Description":null,"AppointmentPrice":0.1,"PayChannel":0,"PayStatus":0,"PayNo":null,"PayOrderTime":"2019-06-27 15:59:40","PayTime":null,"PayOrderNo":null,"PayAmount":null,"AppointmentStatus":0,"CancelType":null,"CancelOpId":null,"CancelReason":null,"CancelTime":null,"Invalid":0,"CreateTime":"2019-06-27 15:59:39","UpdateTime":"2019-06-27 15:59:39"},{"PatientPic":"http://userimage.newstarthealth.cn/pat/0/1496.jpg?dt=201906280416800","WeekDay":6,"CanCancel":false,"RecordId":63,"DrId":306,"DrName":"艾美","DepartmentId":0,"DepartmentName":null,"PatientAccountId":1020,"PatientId":1496,"PatientName":"","PatientSex":3,"PatientAge":"","PatientMobile":"15878855805","ReserveNo":"201906290000063","ConsultationHospital":null,"ConsultationAddress":"深圳市飞飞哥","AppointmentDate":"2019-06-29 00:00:00","StartTime":"09:00:00","EndTime":"10:00:00","TimePeriod":1,"DutyId":58,"Description":null,"AppointmentPrice":0.1,"PayChannel":0,"PayStatus":0,"PayNo":null,"PayOrderTime":"2019-06-27 16:00:27","PayTime":null,"PayOrderNo":null,"PayAmount":null,"AppointmentStatus":0,"CancelType":null,"CancelOpId":null,"CancelReason":null,"CancelTime":null,"Invalid":0,"CreateTime":"2019-06-27 16:00:26","UpdateTime":"2019-06-27 16:00:26"},{"PatientPic":"http://userimage.newstarthealth.cn/pat/0/1492.jpg?dt=201906280416800","WeekDay":6,"CanCancel":false,"RecordId":64,"DrId":306,"DrName":"艾美","DepartmentId":0,"DepartmentName":null,"PatientAccountId":1017,"PatientId":1492,"PatientName":"理解","PatientSex":2,"PatientAge":"20岁","PatientMobile":"15877565355","ReserveNo":"201906290000064","ConsultationHospital":null,"ConsultationAddress":"深圳市飞飞哥","AppointmentDate":"2019-06-29 00:00:00","StartTime":"11:00:00","EndTime":"11:59:00","TimePeriod":1,"DutyId":59,"Description":null,"AppointmentPrice":0.01,"PayChannel":1,"PayStatus":5,"PayNo":"4200000318201906275484959344","PayOrderTime":"2019-06-27 16:04:49","PayTime":"2019-06-27 16:04:57","PayOrderNo":"20190627160448798230","PayAmount":null,"AppointmentStatus":2,"CancelType":1,"CancelOpId":1492,"CancelReason":null,"CancelTime":null,"Invalid":0,"CreateTime":"2019-06-27 16:04:48","UpdateTime":"2019-06-27 16:09:38"},{"PatientPic":"http://userimage.newstarthealth.cn/pat/0/1492.jpg?dt=201906280416800","WeekDay":6,"CanCancel":true,"RecordId":65,"DrId":306,"DrName":"艾美","DepartmentId":0,"DepartmentName":null,"PatientAccountId":1017,"PatientId":1492,"PatientName":"理解","PatientSex":2,"PatientAge":"20岁","PatientMobile":"15877565355","ReserveNo":"201906290000065","ConsultationHospital":null,"ConsultationAddress":"深圳市飞飞哥","AppointmentDate":"2019-06-29 00:00:00","StartTime":"11:00:00","EndTime":"11:59:00","TimePeriod":1,"DutyId":59,"Description":null,"AppointmentPrice":0.01,"PayChannel":1,"PayStatus":2,"PayNo":"4200000323201906277840142143","PayOrderTime":"2019-06-27 16:07:00","PayTime":"2019-06-27 16:07:06","PayOrderNo":"20190627160700397247","PayAmount":null,"AppointmentStatus":0,"CancelType":null,"CancelOpId":null,"CancelReason":null,"CancelTime":null,"Invalid":0,"CreateTime":"2019-06-27 16:06:59","UpdateTime":"2019-06-27 16:07:07"},{"PatientPic":"http://userimage.newstarthealth.cn/pat/0/29.jpg?dt=201906280416800","WeekDay":6,"CanCancel":false,"RecordId":72,"DrId":306,"DrName":"艾美","DepartmentId":0,"DepartmentName":null,"PatientAccountId":17,"PatientId":29,"PatientName":"王伟标","PatientSex":1,"PatientAge":"30岁","PatientMobile":"15989447175","ReserveNo":"201906290000072","ConsultationHospital":"方法","ConsultationAddress":"深圳市飞飞哥","AppointmentDate":"2019-06-29 00:00:00","StartTime":"19:00:00","EndTime":"23:00:00","TimePeriod":3,"DutyId":55,"Description":null,"AppointmentPrice":0.01,"PayChannel":1,"PayStatus":5,"PayNo":"4200000335201906275498746617","PayOrderTime":"2019-06-27 20:18:19","PayTime":"2019-06-27 20:18:32","PayOrderNo":"20190627201819404241","PayAmount":null,"AppointmentStatus":2,"CancelType":1,"CancelOpId":29,"CancelReason":null,"CancelTime":null,"Invalid":0,"CreateTime":"2019-06-27 20:18:19","UpdateTime":"2019-06-27 20:20:03"}]}]
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
         * AppointmentDate : 2019-06-28 00:00:00
         * ReserveNums : 2
         * FaceConsultationRecords : [{"PatientPic":"http://userimage.newstarthealth.cn/pat/0/1492.jpg?dt=201906280416746","WeekDay":5,"CanCancel":false,"RecordId":56,"DrId":306,"DrName":"艾美","DepartmentId":0,"DepartmentName":null,"PatientAccountId":1017,"PatientId":1492,"PatientName":"理解","PatientSex":2,"PatientAge":"20岁","PatientMobile":"15877565355","ReserveNo":"201906280000056","ConsultationHospital":null,"ConsultationAddress":"深圳市飞飞哥","AppointmentDate":"2019-06-28 00:00:00","StartTime":"19:00:00","EndTime":"23:00:00","TimePeriod":3,"DutyId":53,"Description":null,"AppointmentPrice":0,"PayChannel":1,"PayStatus":5,"PayNo":"","PayOrderTime":"2019-06-27 14:24:39","PayTime":"2019-06-27 14:24:39","PayOrderNo":"20190627142439303501","PayAmount":null,"AppointmentStatus":2,"CancelType":1,"CancelOpId":1492,"CancelReason":null,"CancelTime":null,"Invalid":0,"CreateTime":"2019-06-27 14:24:39","UpdateTime":"2019-06-27 16:09:32"},{"PatientPic":"http://userimage.newstarthealth.cn/pat/0/1492.jpg?dt=201906280416746","WeekDay":5,"CanCancel":false,"RecordId":57,"DrId":306,"DrName":"艾美","DepartmentId":0,"DepartmentName":null,"PatientAccountId":1017,"PatientId":1492,"PatientName":"理解","PatientSex":2,"PatientAge":"20岁","PatientMobile":"15877565355","ReserveNo":"201906280000057","ConsultationHospital":null,"ConsultationAddress":"深圳市飞飞哥","AppointmentDate":"2019-06-28 00:00:00","StartTime":"19:00:00","EndTime":"23:00:00","TimePeriod":3,"DutyId":53,"Description":null,"AppointmentPrice":0.1,"PayChannel":1,"PayStatus":5,"PayNo":"4200000315201906272837173508","PayOrderTime":"2019-06-27 14:25:57","PayTime":"2019-06-27 14:26:04","PayOrderNo":"20190627142556665598","PayAmount":null,"AppointmentStatus":2,"CancelType":2,"CancelOpId":306,"CancelReason":null,"CancelTime":null,"Invalid":0,"CreateTime":"2019-06-27 14:25:56","UpdateTime":"2019-06-27 16:09:36"},{"PatientPic":"http://userimage.newstarthealth.cn/pat/0/1497.jpg?dt=201906280416746","WeekDay":5,"CanCancel":false,"RecordId":68,"DrId":306,"DrName":"艾美","DepartmentId":0,"DepartmentName":null,"PatientAccountId":1021,"PatientId":1497,"PatientName":"ceshui ","PatientSex":1,"PatientAge":"18岁","PatientMobile":"15088135555","ReserveNo":"201906280000068","ConsultationHospital":null,"ConsultationAddress":"深圳市飞飞哥","AppointmentDate":"2019-06-28 00:00:00","StartTime":"19:00:00","EndTime":"23:00:00","TimePeriod":3,"DutyId":53,"Description":null,"AppointmentPrice":0.01,"PayChannel":1,"PayStatus":8,"PayNo":"","PayOrderTime":"2019-06-27 16:57:33","PayTime":"2019-06-27 17:47:00","PayOrderNo":"20190627165733439382","PayAmount":null,"AppointmentStatus":0,"CancelType":null,"CancelOpId":null,"CancelReason":null,"CancelTime":null,"Invalid":0,"CreateTime":"2019-06-27 16:57:33","UpdateTime":"2019-06-27 17:47:00"},{"PatientPic":"http://userimage.newstarthealth.cn/pat/0/368.jpg?dt=201906280416746","WeekDay":5,"CanCancel":false,"RecordId":69,"DrId":306,"DrName":"艾美","DepartmentId":0,"DepartmentName":null,"PatientAccountId":10,"PatientId":368,"PatientName":"胡","PatientSex":2,"PatientAge":"27岁","PatientMobile":"15088131721","ReserveNo":"201906280000069","ConsultationHospital":null,"ConsultationAddress":"深圳市飞飞哥","AppointmentDate":"2019-06-28 00:00:00","StartTime":"19:00:00","EndTime":"23:00:00","TimePeriod":3,"DutyId":53,"Description":null,"AppointmentPrice":0.01,"PayChannel":1,"PayStatus":8,"PayNo":"","PayOrderTime":"2019-06-27 16:59:09","PayTime":"2019-06-27 17:47:00","PayOrderNo":"20190627165909448179","PayAmount":null,"AppointmentStatus":0,"CancelType":null,"CancelOpId":null,"CancelReason":null,"CancelTime":null,"Invalid":0,"CreateTime":"2019-06-27 16:59:09","UpdateTime":"2019-06-27 17:47:00"}]
         */

        private String AppointmentDate;
        private int ReserveNums;
        private List<FaceConsultationRecordsBean> FaceConsultationRecords;
        private boolean Ishide;

        public boolean isIshide() {
            return Ishide;
        }

        public void setIshide(boolean ishide) {
            Ishide = ishide;
        }

        public String getAppointmentDate() {
            return AppointmentDate;
        }

        public void setAppointmentDate(String AppointmentDate) {
            this.AppointmentDate = AppointmentDate;
        }

        public int getReserveNums() {
            return ReserveNums;
        }

        public void setReserveNums(int ReserveNums) {
            this.ReserveNums = ReserveNums;
        }

        public List<FaceConsultationRecordsBean> getFaceConsultationRecords() {
            return FaceConsultationRecords;
        }

        public void setFaceConsultationRecords(List<FaceConsultationRecordsBean> FaceConsultationRecords) {
            this.FaceConsultationRecords = FaceConsultationRecords;
        }

        public static class FaceConsultationRecordsBean {
            /**
             * PatientPic : http://userimage.newstarthealth.cn/pat/0/1492.jpg?dt=201906280416746
             * WeekDay : 5
             * CanCancel : false
             * RecordId : 56
             * DrId : 306
             * DrName : 艾美
             * DepartmentId : 0
             * DepartmentName : null
             * PatientAccountId : 1017
             * PatientId : 1492
             * PatientName : 理解
             * PatientSex : 2
             * PatientAge : 20岁
             * PatientMobile : 15877565355
             * ReserveNo : 201906280000056
             * ConsultationHospital : null
             * ConsultationAddress : 深圳市飞飞哥
             * AppointmentDate : 2019-06-28 00:00:00
             * StartTime : 19:00:00
             * EndTime : 23:00:00
             * TimePeriod : 3
             * DutyId : 53
             * Description : null
             * AppointmentPrice : 0.0
             * PayChannel : 1
             * PayStatus : 5
             * PayNo :
             * PayOrderTime : 2019-06-27 14:24:39
             * PayTime : 2019-06-27 14:24:39
             * PayOrderNo : 20190627142439303501
             * PayAmount : null
             * AppointmentStatus : 2
             * CancelType : 1
             * CancelOpId : 1492
             * CancelReason : null
             * CancelTime : null
             * Invalid : 0
             * CreateTime : 2019-06-27 14:24:39
             * UpdateTime : 2019-06-27 16:09:32
             */

            private String PatientPic;
            private int WeekDay;
            private boolean CanCancel;
            private int RecordId;
            private int DrId;
            private String DrName;
            private int DepartmentId;
            private Object DepartmentName;
            private int PatientAccountId;
            private int PatientId;
            private String PatientName;
            private int PatientSex;
            private String PatientAge;
            private String PatientMobile;
            private String ReserveNo;
            private Object ConsultationHospital;
            private String ConsultationAddress;
            private String AppointmentDate;
            private String StartTime;
            private String EndTime;
            private int TimePeriod;
            private int DutyId;
            private String Description;
            private double AppointmentPrice;
            private int PayChannel;
            private int PayStatus;
            private String PayNo;
            private String PayOrderTime;
            private String PayTime;
            private String PayOrderNo;
            private Object PayAmount;
            private int AppointmentStatus;
            private int CancelType;
            private int CancelOpId;
            private Object CancelReason;
            private Object CancelTime;
            private int Invalid;
            private String CreateTime;
            private String UpdateTime;



            public String getPatientPic() {
                return PatientPic;
            }

            public void setPatientPic(String PatientPic) {
                this.PatientPic = PatientPic;
            }

            public int getWeekDay() {
                return WeekDay;
            }

            public void setWeekDay(int WeekDay) {
                this.WeekDay = WeekDay;
            }

            public boolean isCanCancel() {
                return CanCancel;
            }

            public void setCanCancel(boolean CanCancel) {
                this.CanCancel = CanCancel;
            }

            public int getRecordId() {
                return RecordId;
            }

            public void setRecordId(int RecordId) {
                this.RecordId = RecordId;
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

            public int getDepartmentId() {
                return DepartmentId;
            }

            public void setDepartmentId(int DepartmentId) {
                this.DepartmentId = DepartmentId;
            }

            public Object getDepartmentName() {
                return DepartmentName;
            }

            public void setDepartmentName(Object DepartmentName) {
                this.DepartmentName = DepartmentName;
            }

            public int getPatientAccountId() {
                return PatientAccountId;
            }

            public void setPatientAccountId(int PatientAccountId) {
                this.PatientAccountId = PatientAccountId;
            }

            public int getPatientId() {
                return PatientId;
            }

            public void setPatientId(int PatientId) {
                this.PatientId = PatientId;
            }

            public String getPatientName() {
                return PatientName;
            }

            public void setPatientName(String PatientName) {
                this.PatientName = PatientName;
            }

            public int getPatientSex() {
                return PatientSex;
            }

            public void setPatientSex(int PatientSex) {
                this.PatientSex = PatientSex;
            }

            public String getPatientAge() {
                return PatientAge;
            }

            public void setPatientAge(String PatientAge) {
                this.PatientAge = PatientAge;
            }

            public String getPatientMobile() {
                return PatientMobile;
            }

            public void setPatientMobile(String PatientMobile) {
                this.PatientMobile = PatientMobile;
            }

            public String getReserveNo() {
                return ReserveNo;
            }

            public void setReserveNo(String ReserveNo) {
                this.ReserveNo = ReserveNo;
            }

            public Object getConsultationHospital() {
                return ConsultationHospital;
            }

            public void setConsultationHospital(Object ConsultationHospital) {
                this.ConsultationHospital = ConsultationHospital;
            }

            public String getConsultationAddress() {
                return ConsultationAddress;
            }

            public void setConsultationAddress(String ConsultationAddress) {
                this.ConsultationAddress = ConsultationAddress;
            }

            public String getAppointmentDate() {
                return AppointmentDate;
            }

            public void setAppointmentDate(String AppointmentDate) {
                this.AppointmentDate = AppointmentDate;
            }

            public String getStartTime() {
                return StartTime;
            }

            public void setStartTime(String StartTime) {
                this.StartTime = StartTime;
            }

            public String getEndTime() {
                return EndTime;
            }

            public void setEndTime(String EndTime) {
                this.EndTime = EndTime;
            }

            public int getTimePeriod() {
                return TimePeriod;
            }

            public void setTimePeriod(int TimePeriod) {
                this.TimePeriod = TimePeriod;
            }

            public int getDutyId() {
                return DutyId;
            }

            public void setDutyId(int DutyId) {
                this.DutyId = DutyId;
            }

            public String getDescription() {
                return Description;
            }

            public void setDescription(String Description) {
                this.Description = Description;
            }

            public double getAppointmentPrice() {
                return AppointmentPrice;
            }

            public void setAppointmentPrice(double AppointmentPrice) {
                this.AppointmentPrice = AppointmentPrice;
            }

            public int getPayChannel() {
                return PayChannel;
            }

            public void setPayChannel(int PayChannel) {
                this.PayChannel = PayChannel;
            }

            public int getPayStatus() {
                return PayStatus;
            }

            public void setPayStatus(int PayStatus) {
                this.PayStatus = PayStatus;
            }

            public String getPayNo() {
                return PayNo;
            }

            public void setPayNo(String PayNo) {
                this.PayNo = PayNo;
            }

            public String getPayOrderTime() {
                return PayOrderTime;
            }

            public void setPayOrderTime(String PayOrderTime) {
                this.PayOrderTime = PayOrderTime;
            }

            public String getPayTime() {
                return PayTime;
            }

            public void setPayTime(String PayTime) {
                this.PayTime = PayTime;
            }

            public String getPayOrderNo() {
                return PayOrderNo;
            }

            public void setPayOrderNo(String PayOrderNo) {
                this.PayOrderNo = PayOrderNo;
            }

            public Object getPayAmount() {
                return PayAmount;
            }

            public void setPayAmount(Object PayAmount) {
                this.PayAmount = PayAmount;
            }

            public int getAppointmentStatus() {
                return AppointmentStatus;
            }

            public void setAppointmentStatus(int AppointmentStatus) {
                this.AppointmentStatus = AppointmentStatus;
            }

            public int getCancelType() {
                return CancelType;
            }

            public void setCancelType(int CancelType) {
                this.CancelType = CancelType;
            }

            public int getCancelOpId() {
                return CancelOpId;
            }

            public void setCancelOpId(int CancelOpId) {
                this.CancelOpId = CancelOpId;
            }

            public Object getCancelReason() {
                return CancelReason;
            }

            public void setCancelReason(Object CancelReason) {
                this.CancelReason = CancelReason;
            }

            public Object getCancelTime() {
                return CancelTime;
            }

            public void setCancelTime(Object CancelTime) {
                this.CancelTime = CancelTime;
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
