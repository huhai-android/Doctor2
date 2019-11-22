package com.newdjk.doctor.ui.entity;

public class CategoryMessageEntity {
    private int CategoryItemId;
    private int CategoryId;
    private int CategoryItemValue;
    private int OrderNo;
    private String CategoryItemName;

    public int getCategoryItemId() {
        return CategoryItemId;
    }

    public void setCategoryItemId(int categoryItemId) {
        CategoryItemId = categoryItemId;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public int getCategoryItemValue() {
        return CategoryItemValue;
    }

    public void setCategoryItemValue(int categoryItemValue) {
        CategoryItemValue = categoryItemValue;
    }

    public int getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(int orderNo) {
        OrderNo = orderNo;
    }

    public String getCategoryItemName() {
        return CategoryItemName;
    }

    public void setCategoryItemName(String categoryItemName) {
        CategoryItemName = categoryItemName;
    }
}
