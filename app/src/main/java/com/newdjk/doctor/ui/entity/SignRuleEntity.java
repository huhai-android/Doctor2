package com.newdjk.doctor.ui.entity;

import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   SignRuleEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/1/21 14:47
 *  @描述：
 */
public class SignRuleEntity {


    /**
     * Code : 0
     * Message :
     * Data : [{"IntegralTypeId":1,"IntegralTypeName":"推荐医生","IntegralTypeCode":"101","IntegralSets":[{"IntegralSetId":1,"IntegralTypeId":1,"IntegralTypeName":"推荐医生","IntegralTypeCode":"101","IntegralTypeClass":2,"MinPrice":null,"MaxPrice":null,"IntegralSetValue":50,"Description":"推荐一个医生注册并认证成功","Invalid":0,"IsClose":1,"CreateTime":"2019-01-21 13:47:02","UpdateTime":"2019-01-21 13:52:50"}]},{"IntegralTypeId":8,"IntegralTypeName":"药品销售","IntegralTypeCode":"108","IntegralSets":[{"IntegralSetId":2,"IntegralTypeId":8,"IntegralTypeName":"药品销售","IntegralTypeCode":"108","IntegralTypeClass":2,"MinPrice":null,"MaxPrice":"50.00","IntegralSetValue":1,"Description":"用户成功购买50元以内药品时可获得","Invalid":0,"IsClose":1,"CreateTime":"2019-01-21 13:49:59","UpdateTime":"2019-01-21 13:52:53"},{"IntegralSetId":3,"IntegralTypeId":8,"IntegralTypeName":"药品销售","IntegralTypeCode":"108","IntegralTypeClass":2,"MinPrice":"50.00","MaxPrice":null,"IntegralSetValue":2,"Description":"用户成功购买50元以上药品时可获得","Invalid":0,"IsClose":1,"CreateTime":"2019-01-21 13:50:51","UpdateTime":"2019-01-21 13:52:54"}]},{"IntegralTypeId":4,"IntegralTypeName":"销售积分","IntegralTypeCode":"104","IntegralSets":[{"IntegralSetId":4,"IntegralTypeId":4,"IntegralTypeName":"销售积分","IntegralTypeCode":"104","IntegralTypeClass":1,"MinPrice":"10.00","MaxPrice":null,"IntegralSetValue":10,"Description":"每笔订单收费在10元以上可获得","Invalid":0,"IsClose":1,"CreateTime":"2019-01-21 13:52:35","UpdateTime":"2019-01-21 13:53:00"}]}]
     */

    private int Code;
    private String Message;
    private List<DataBean> Data;

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

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * IntegralTypeId : 1
         * IntegralTypeName : 推荐医生
         * IntegralTypeCode : 101
         * IntegralSets : [{"IntegralSetId":1,"IntegralTypeId":1,"IntegralTypeName":"推荐医生","IntegralTypeCode":"101","IntegralTypeClass":2,"MinPrice":null,"MaxPrice":null,"IntegralSetValue":50,"Description":"推荐一个医生注册并认证成功","Invalid":0,"IsClose":1,"CreateTime":"2019-01-21 13:47:02","UpdateTime":"2019-01-21 13:52:50"}]
         */

        private int IntegralTypeId;
        private String IntegralTypeName;
        private String IntegralTypeCode;
        private List<IntegralSetsBean> IntegralSets;
        private boolean IsCompleted;

        public boolean isCompleted() {
            return IsCompleted;
        }

        public void setCompleted(boolean completed) {
            IsCompleted = completed;
        }

        public int getIntegralTypeId() {
            return IntegralTypeId;
        }

        public void setIntegralTypeId(int IntegralTypeId) {
            this.IntegralTypeId = IntegralTypeId;
        }

        public String getIntegralTypeName() {
            return IntegralTypeName;
        }

        public void setIntegralTypeName(String IntegralTypeName) {
            this.IntegralTypeName = IntegralTypeName;
        }

        public String getIntegralTypeCode() {
            return IntegralTypeCode;
        }

        public void setIntegralTypeCode(String IntegralTypeCode) {
            this.IntegralTypeCode = IntegralTypeCode;
        }

        public List<IntegralSetsBean> getIntegralSets() {
            return IntegralSets;
        }

        public void setIntegralSets(List<IntegralSetsBean> IntegralSets) {
            this.IntegralSets = IntegralSets;
        }

        public static class IntegralSetsBean {
            /**
             * IntegralSetId : 1
             * IntegralTypeId : 1
             * IntegralTypeName : 推荐医生
             * IntegralTypeCode : 101
             * IntegralTypeClass : 2
             * MinPrice : null
             * MaxPrice : null
             * IntegralSetValue : 50
             * Description : 推荐一个医生注册并认证成功
             * Invalid : 0
             * IsClose : 1
             * CreateTime : 2019-01-21 13:47:02
             * UpdateTime : 2019-01-21 13:52:50
             */

            private int IntegralSetId;
            private int IntegralTypeId;
            private String IntegralTypeName;
            private String IntegralTypeCode;
            private int IntegralTypeClass;
            private Object MinPrice;
            private Object MaxPrice;
            private int IntegralSetValue;
            private String Description;
            private int Invalid;
            private int IsClose;
            private String CreateTime;
            private String UpdateTime;

            public int getIntegralSetId() {
                return IntegralSetId;
            }

            public void setIntegralSetId(int IntegralSetId) {
                this.IntegralSetId = IntegralSetId;
            }

            public int getIntegralTypeId() {
                return IntegralTypeId;
            }

            public void setIntegralTypeId(int IntegralTypeId) {
                this.IntegralTypeId = IntegralTypeId;
            }

            public String getIntegralTypeName() {
                return IntegralTypeName;
            }

            public void setIntegralTypeName(String IntegralTypeName) {
                this.IntegralTypeName = IntegralTypeName;
            }

            public String getIntegralTypeCode() {
                return IntegralTypeCode;
            }

            public void setIntegralTypeCode(String IntegralTypeCode) {
                this.IntegralTypeCode = IntegralTypeCode;
            }

            public int getIntegralTypeClass() {
                return IntegralTypeClass;
            }

            public void setIntegralTypeClass(int IntegralTypeClass) {
                this.IntegralTypeClass = IntegralTypeClass;
            }

            public Object getMinPrice() {
                return MinPrice;
            }

            public void setMinPrice(Object MinPrice) {
                this.MinPrice = MinPrice;
            }

            public Object getMaxPrice() {
                return MaxPrice;
            }

            public void setMaxPrice(Object MaxPrice) {
                this.MaxPrice = MaxPrice;
            }

            public int getIntegralSetValue() {
                return IntegralSetValue;
            }

            public void setIntegralSetValue(int IntegralSetValue) {
                this.IntegralSetValue = IntegralSetValue;
            }

            public String getDescription() {
                return Description;
            }

            public void setDescription(String Description) {
                this.Description = Description;
            }

            public int getInvalid() {
                return Invalid;
            }

            public void setInvalid(int Invalid) {
                this.Invalid = Invalid;
            }

            public int getIsClose() {
                return IsClose;
            }

            public void setIsClose(int IsClose) {
                this.IsClose = IsClose;
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
