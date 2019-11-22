package com.newdjk.doctor.ui.entity;

import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   HosGroupEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/9/10 9:40
 *  @描述：
 */
public class HosGroupEntity {

    /**
     * Total : 4
     * ReturnData : [{"SpecialistHosGroupId":2,"HosGroupCode":"002","HosGroupName":"特朗普专家团队","OrgId":1,"OrgName":"新起点互联网医院","OrgPath":",0,1,","Logo":"http://resource.newstarthealth.cn/SpecialistHosGroup/759505b1-9120-4585-b166-0ef5a8bac9e2.bmp","Description":"亲爱的中国朋友们，只要我在推特说一句话，你们的股市就会震一震，FU BU FU！","RemoteClinicTime":"9:00-18:00","RemoteClinicPrice":"0.00","DisplayOrder":200,"IsDisplay":1,"IsMDT":0,"EntranceImgPath":null,"HeaderImgPath":null,"MDTNum":0,"CreateId":1,"CreateTime":"2019-05-08 10:04:25","UpdateId":263,"UpdateTime":"2019-09-04 19:15:50","Invalid":0,"NumMember":11,"GroupMemberList":null},{"SpecialistHosGroupId":16,"HosGroupCode":"886","HosGroupName":"NEW测试","OrgId":1,"OrgName":"新起点互联网医院","OrgPath":",0,1,","Logo":null,"Description":"这是一个测试的专科团队，这是一个测试的专科团队，这是一个测试的专科团队，这是一个测试的专科团队，这是一个测试的专科团队，这是一个测试的专科团队，这是一个测试的专科团队，这是一个测试的专科团队，这是一个测试的专科团队，这是一个测试的专科团队，这是一个测试的专科团队，这是一个测试的专科团队，这是一个测试的专科团队，这是一个测试的专科团队，这是一个测试的专科团队，这是一个测试的专科团队，这是一个测试的专科团队，这是一个测试的专科团队，","RemoteClinicTime":"工作日","RemoteClinicPrice":"0.00","DisplayOrder":3,"IsDisplay":0,"IsMDT":0,"EntranceImgPath":null,"HeaderImgPath":null,"MDTNum":0,"CreateId":263,"CreateTime":"2019-06-14 10:46:40","UpdateId":263,"UpdateTime":"2019-09-04 19:15:50","Invalid":0,"NumMember":4,"GroupMemberList":null},{"SpecialistHosGroupId":5,"HosGroupCode":"003","HosGroupName":"专治疑难杂症","OrgId":1,"OrgName":"新起点互联网医院","OrgPath":",0,1,","Logo":null,"Description":"专治疑难杂症","RemoteClinicTime":"7*24小时服务","RemoteClinicPrice":"0.00","DisplayOrder":2,"IsDisplay":0,"IsMDT":1,"EntranceImgPath":null,"HeaderImgPath":null,"MDTNum":17,"CreateId":823,"CreateTime":"2019-05-13 11:34:14","UpdateId":263,"UpdateTime":"2019-09-09 19:29:48","Invalid":0,"NumMember":14,"GroupMemberList":null},{"SpecialistHosGroupId":1,"HosGroupCode":"001","HosGroupName":"呼吸科专家团队","OrgId":1,"OrgName":"新起点互联网医院","OrgPath":",0,1,","Logo":null,"Description":null,"RemoteClinicTime":"10:00-18:00","RemoteClinicPrice":"0.01","DisplayOrder":0,"IsDisplay":0,"IsMDT":0,"EntranceImgPath":null,"HeaderImgPath":null,"MDTNum":0,"CreateId":1,"CreateTime":"2019-05-08 10:02:35","UpdateId":263,"UpdateTime":"2019-09-04 19:15:50","Invalid":0,"NumMember":8,"GroupMemberList":null}]
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
         * SpecialistHosGroupId : 2
         * HosGroupCode : 002
         * HosGroupName : 特朗普专家团队
         * OrgId : 1
         * OrgName : 新起点互联网医院
         * OrgPath : ,0,1,
         * Logo : http://resource.newstarthealth.cn/SpecialistHosGroup/759505b1-9120-4585-b166-0ef5a8bac9e2.bmp
         * Description : 亲爱的中国朋友们，只要我在推特说一句话，你们的股市就会震一震，FU BU FU！
         * RemoteClinicTime : 9:00-18:00
         * RemoteClinicPrice : 0.00
         * DisplayOrder : 200
         * IsDisplay : 1
         * IsMDT : 0
         * EntranceImgPath : null
         * HeaderImgPath : null
         * MDTNum : 0
         * CreateId : 1
         * CreateTime : 2019-05-08 10:04:25
         * UpdateId : 263
         * UpdateTime : 2019-09-04 19:15:50
         * Invalid : 0
         * NumMember : 11
         * GroupMemberList : null
         */

        private int SpecialistHosGroupId;
        private String HosGroupCode;
        private String HosGroupName;
        private int OrgId;
        private String OrgName;
        private String OrgPath;
        private String Logo;
        private String Description;
        private String RemoteClinicTime;
        private String RemoteClinicPrice;
        private int DisplayOrder;
        private int IsDisplay;
        private int IsMDT;
        private Object EntranceImgPath;
        private Object HeaderImgPath;
        private int MDTNum;
        private int CreateId;
        private String CreateTime;
        private int UpdateId;
        private String UpdateTime;
        private int Invalid;
        private int NumMember;
        private Object GroupMemberList;

        public int getSpecialistHosGroupId() {
            return SpecialistHosGroupId;
        }

        public void setSpecialistHosGroupId(int SpecialistHosGroupId) {
            this.SpecialistHosGroupId = SpecialistHosGroupId;
        }

        public String getHosGroupCode() {
            return HosGroupCode;
        }

        public void setHosGroupCode(String HosGroupCode) {
            this.HosGroupCode = HosGroupCode;
        }

        public String getHosGroupName() {
            return HosGroupName;
        }

        public void setHosGroupName(String HosGroupName) {
            this.HosGroupName = HosGroupName;
        }

        public int getOrgId() {
            return OrgId;
        }

        public void setOrgId(int OrgId) {
            this.OrgId = OrgId;
        }

        public String getOrgName() {
            return OrgName;
        }

        public void setOrgName(String OrgName) {
            this.OrgName = OrgName;
        }

        public String getOrgPath() {
            return OrgPath;
        }

        public void setOrgPath(String OrgPath) {
            this.OrgPath = OrgPath;
        }

        public String getLogo() {
            return Logo;
        }

        public void setLogo(String Logo) {
            this.Logo = Logo;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public String getRemoteClinicTime() {
            return RemoteClinicTime;
        }

        public void setRemoteClinicTime(String RemoteClinicTime) {
            this.RemoteClinicTime = RemoteClinicTime;
        }

        public String getRemoteClinicPrice() {
            return RemoteClinicPrice;
        }

        public void setRemoteClinicPrice(String RemoteClinicPrice) {
            this.RemoteClinicPrice = RemoteClinicPrice;
        }

        public int getDisplayOrder() {
            return DisplayOrder;
        }

        public void setDisplayOrder(int DisplayOrder) {
            this.DisplayOrder = DisplayOrder;
        }

        public int getIsDisplay() {
            return IsDisplay;
        }

        public void setIsDisplay(int IsDisplay) {
            this.IsDisplay = IsDisplay;
        }

        public int getIsMDT() {
            return IsMDT;
        }

        public void setIsMDT(int IsMDT) {
            this.IsMDT = IsMDT;
        }

        public Object getEntranceImgPath() {
            return EntranceImgPath;
        }

        public void setEntranceImgPath(Object EntranceImgPath) {
            this.EntranceImgPath = EntranceImgPath;
        }

        public Object getHeaderImgPath() {
            return HeaderImgPath;
        }

        public void setHeaderImgPath(Object HeaderImgPath) {
            this.HeaderImgPath = HeaderImgPath;
        }

        public int getMDTNum() {
            return MDTNum;
        }

        public void setMDTNum(int MDTNum) {
            this.MDTNum = MDTNum;
        }

        public int getCreateId() {
            return CreateId;
        }

        public void setCreateId(int CreateId) {
            this.CreateId = CreateId;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getUpdateId() {
            return UpdateId;
        }

        public void setUpdateId(int UpdateId) {
            this.UpdateId = UpdateId;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public int getInvalid() {
            return Invalid;
        }

        public void setInvalid(int Invalid) {
            this.Invalid = Invalid;
        }

        public int getNumMember() {
            return NumMember;
        }

        public void setNumMember(int NumMember) {
            this.NumMember = NumMember;
        }

        public Object getGroupMemberList() {
            return GroupMemberList;
        }

        public void setGroupMemberList(Object GroupMemberList) {
            this.GroupMemberList = GroupMemberList;
        }
    }
}
