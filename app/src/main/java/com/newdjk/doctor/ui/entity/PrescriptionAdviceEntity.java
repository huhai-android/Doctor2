package com.newdjk.doctor.ui.entity;

import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   PrescriptionAdviceEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/29 15:47
 *  @描述：
 */
public class PrescriptionAdviceEntity {

    /**
     * Code : 0
     * Message :
     * Data : [{"CategoryItemId":524,"CategoryId":37,"CategoryItemValue":1,"CategoryItemName":"药品剂型或给药途径不适宜","OrderNo":1},{"CategoryItemId":525,"CategoryId":37,"CategoryItemValue":2,"CategoryItemName":"用法、用量不适宜","OrderNo":2},{"CategoryItemId":526,"CategoryId":37,"CategoryItemValue":3,"CategoryItemName":"联合用药不适宜","OrderNo":3},{"CategoryItemId":527,"CategoryId":37,"CategoryItemValue":4,"CategoryItemName":"重复给药","OrderNo":4},{"CategoryItemId":528,"CategoryId":37,"CategoryItemValue":5,"CategoryItemName":"处方用药存在配伍禁忌或者不良相互作用","OrderNo":5},{"CategoryItemId":529,"CategoryId":37,"CategoryItemValue":6,"CategoryItemName":"处方用药与临床诊断不相符","OrderNo":6},{"CategoryItemId":530,"CategoryId":37,"CategoryItemValue":7,"CategoryItemName":"超说明书用药且未注明原因","OrderNo":7},{"CategoryItemId":531,"CategoryId":37,"CategoryItemValue":8,"CategoryItemName":"门诊处方用量超过7日且未注明原因","OrderNo":8},{"CategoryItemId":532,"CategoryId":37,"CategoryItemValue":9,"CategoryItemName":"规定必须做皮试的药品，未注明过敏试验及结果的判定","OrderNo":9},{"CategoryItemId":533,"CategoryId":37,"CategoryItemValue":10,"CategoryItemName":"其他，请注明","OrderNo":10}]
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
         * CategoryItemId : 524
         * CategoryId : 37
         * CategoryItemValue : 1
         * CategoryItemName : 药品剂型或给药途径不适宜
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
