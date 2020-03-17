package com.kyx.biz.dailypay.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

public class DailyPayRecord implements Serializable {
    private Integer id;

    private Integer kind;

    private Integer payTypeId;

    private Integer payId;

    private BigDecimal amount;
    @JSONField(format="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startSharePeriod;
    @JSONField(format="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endSharePeriod;

    private String remark;
    
    private Integer shopId;
    
    private Date addTime;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeStartTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeEndTime;

	private Date dateRangeStartPeriod;
	
	private Date dateRangeEndPeriod;
	
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public Integer getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(Integer payTypeId) {
        this.payTypeId = payTypeId;
    }

    public Integer getPayId() {
        return payId;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getStartSharePeriod() {
        return startSharePeriod;
    }

    public void setStartSharePeriod(Date startSharePeriod) {
        this.startSharePeriod = startSharePeriod;
    }

    public Date getEndSharePeriod() {
        return endSharePeriod;
    }

    public void setEndSharePeriod(Date endSharePeriod) {
        this.endSharePeriod = endSharePeriod;
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

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
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

	public Date getDateRangeStartPeriod() {
		return dateRangeStartPeriod;
	}

	public void setDateRangeStartPeriod(Date dateRangeStartPeriod) {
		this.dateRangeStartPeriod = dateRangeStartPeriod;
	}

	public Date getDateRangeEndPeriod() {
		return dateRangeEndPeriod;
	}

	public void setDateRangeEndPeriod(Date dateRangeEndPeriod) {
		this.dateRangeEndPeriod = dateRangeEndPeriod;
	}
	
}