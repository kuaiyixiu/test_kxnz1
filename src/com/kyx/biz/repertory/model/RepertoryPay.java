package com.kyx.biz.repertory.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RepertoryPay implements Serializable {
    private Integer id;

    private Integer iidno;

    private Integer repertoryId;

    private Integer payId;

    private BigDecimal payAmount;

    private Date addTime;

    private String remark;

    private Integer shopId;
    
    private Integer canEdit;

    private static final long serialVersionUID = 1L;
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIidno() {
        return iidno;
    }

    public void setIidno(Integer iidno) {
        this.iidno = iidno;
    }

    public Integer getRepertoryId() {
        return repertoryId;
    }

    public void setRepertoryId(Integer repertoryId) {
        this.repertoryId = repertoryId;
    }

    public Integer getPayId() {
        return payId;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

	public Integer getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(Integer canEdit) {
		this.canEdit = canEdit;
	}
    
}