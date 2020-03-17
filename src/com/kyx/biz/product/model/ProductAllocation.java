package com.kyx.biz.product.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

public class ProductAllocation implements Serializable {
    private Integer id;
    @JSONField(format="yyyy-MM-dd hh:mm:ss")
    private Date addtime;

    private Integer calloutId;
    private String calloutName;

    private Integer callinId;
    private String callinName;

    private Integer count;

    private BigDecimal amount;

    private Integer shopId;

    private Integer relationShop;
    private String relationShopName;

    private Integer kind;//1调出 2调入

    private Integer payType;

    private Integer enable;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeStartTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeEndTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Integer getCalloutId() {
        return calloutId;
    }

    public void setCalloutId(Integer calloutId) {
        this.calloutId = calloutId;
    }

    public Integer getCallinId() {
        return callinId;
    }

    public void setCallinId(Integer callinId) {
        this.callinId = callinId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getRelationShop() {
        return relationShop;
    }

    public void setRelationShop(Integer relationShop) {
        this.relationShop = relationShop;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

	public Date getDateRangeStartTime() {
		return dateRangeStartTime;
	}

	public void setDateRangeStartTime(Date dateRangeStartTime) {
		this.dateRangeStartTime = dateRangeStartTime;
	}

	public Date getDateRangeEndTime() {
		return dateRangeEndTime;
	}

	public void setDateRangeEndTime(Date dateRangeEndTime) {
		this.dateRangeEndTime = dateRangeEndTime;
	}

	public String getCalloutName() {
		return calloutName;
	}

	public void setCalloutName(String calloutName) {
		this.calloutName = calloutName;
	}

	public String getCallinName() {
		return callinName;
	}

	public void setCallinName(String callinName) {
		this.callinName = callinName;
	}

	public String getRelationShopName() {
		return relationShopName;
	}

	public void setRelationShopName(String relationShopName) {
		this.relationShopName = relationShopName;
	}
    
}