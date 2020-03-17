package com.kyx.biz.orders.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

public class OrdersPay implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4791475056082863941L;

	private Integer id;

    private Integer iidno;

    private Integer orderId;

    private Integer payId;
    
    private String payName;
    
    private BigDecimal payAmount;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    private String remark;
    
    private Integer shopId;
    
    private Integer canEdit; //是否能编辑 1能编辑 0不能  结算后不能编辑，反挂账或者反入账后可以编辑
    
    private Integer sourceId;//来源ID（代金券：关联cust_coupon）


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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public Integer getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(Integer canEdit) {
		this.canEdit = canEdit;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	
}