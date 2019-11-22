package com.newdjk.doctor.ui.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by EDZ on 2018/10/10.
 */

public class TitleEntity implements Serializable {


    /**
     * Code : 0
     * Message :
     * Data : [{"CategoryItemId":147,"CategoryId":8,"CategoryItemValue":1,"CategoryItemName":"主任护师","OrderNo":1},{"CategoryItemId":148,"CategoryId":8,"CategoryItemValue":2,"CategoryItemName":"副主任护士","OrderNo":2},{"CategoryItemId":149,"CategoryId":8,"CategoryItemValue":3,"CategoryItemName":"专科护士","OrderNo":3},{"CategoryItemId":150,"CategoryId":8,"CategoryItemValue":4,"CategoryItemName":"主管护师","OrderNo":4},{"CategoryItemId":151,"CategoryId":8,"CategoryItemValue":5,"CategoryItemName":"护师","OrderNo":5},{"CategoryItemId":152,"CategoryId":8,"CategoryItemValue":6,"CategoryItemName":"护士","OrderNo":6}]
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

    public static class DataBean implements Serializable {
        /**
         * CategoryItemId : 147
         * CategoryId : 8
         * CategoryItemValue : 1
         * CategoryItemName : 主任护师
         * OrderNo : 1
         */

        private int CategoryItemId;
        private int CategoryId;
        private int CategoryItemValue;
        private String CategoryItemName;
        private int OrderNo;

        public int getCategoryItemId() {
            return CategoryItemId;
        }

        public void setCategoryItemId(int CategoryItemId) {
            this.CategoryItemId = CategoryItemId;
        }

        public int getCategoryId() {
            return CategoryId;
        }

        public void setCategoryId(int CategoryId) {
            this.CategoryId = CategoryId;
        }

        public int getCategoryItemValue() {
            return CategoryItemValue;
        }

        public void setCategoryItemValue(int CategoryItemValue) {
            this.CategoryItemValue = CategoryItemValue;
        }

        public String getCategoryItemName() {
            return CategoryItemName;
        }

        public void setCategoryItemName(String CategoryItemName) {
            this.CategoryItemName = CategoryItemName;
        }

        public int getOrderNo() {
            return OrderNo;
        }

        public void setOrderNo(int OrderNo) {
            this.OrderNo = OrderNo;
        }
    }
}
