package com.kyx.biz.payRecord.model;

import java.io.Serializable;
import java.util.Date;

public class MealPayRecord implements Serializable {
    private Integer id;

    private Integer custId;

    private Integer custMealDtId;

    private Integer quantity;

    private Integer orderId;

    private Date addTime;

    private Integer shopId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public Integer getCustMealDtId() {
        return custMealDtId;
    }

    public void setCustMealDtId(Integer custMealDtId) {
        this.custMealDtId = custMealDtId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}