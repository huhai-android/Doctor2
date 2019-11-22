package com.newdjk.doctor.ui.entity;

import java.util.List;

public class ZhenZhenCountEntity<T> {

    /**
     * Code : 0
     * Message :
     * Data : [{"ReferralType":1,"Num":1},{"ReferralType":3,"Num":1}]
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
         * ReferralType : 1
         * Num : 1
         */

        private int ReferralType;
        private int Num;

        public int getReferralType() {
            return ReferralType;
        }

        public void setReferralType(int ReferralType) {
            this.ReferralType = ReferralType;
        }

        public int getNum() {
            return Num;
        }

        public void setNum(int Num) {
            this.Num = Num;
        }
    }
}
