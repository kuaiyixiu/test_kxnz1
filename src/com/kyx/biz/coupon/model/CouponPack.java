package com.kyx.biz.coupon.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CouponPack implements Serializable {
    private Integer id;

    private String name;

    private BigDecimal value;
    
    private BigDecimal addValue;
    
    private BigDecimal subValue;

    private String remark;

    private Date createTime;

    private Integer shopId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

	public BigDecimal getAddValue() {
		return addValue;
	}

	public void setAddValue(BigDecimal addValue) {
		this.addValue = addValue;
	}

	public BigDecimal getSubValue() {
		return subValue;
	}

	public void setSubValue(BigDecimal subValue) {
		this.subValue = subValue;
	}
    
}