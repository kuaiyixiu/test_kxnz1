package com.kyx.biz.custom.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

public class CustCoupon implements Serializable {
    private Integer id;

    private Integer couponId;
    
    private String couponName;

    private BigDecimal totalAmount;

    private BigDecimal availAmount;
    
    private BigDecimal addAvailAmount;
    
    private BigDecimal subAvailAmount;

    private Integer custId;
	@JSONField(format = "yyyy-MM-dd")
    private Date createTime;
	@JSONField(format = "yyyy-MM-dd")
    private Date endTime;

    private Integer state;

    private Integer sourceId;
    private Integer sourceType;
    
    private String remark;

    private static final long serialVersionUID = 1L;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeStartTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeEndTime;
	
	private Integer shopId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getAvailAmount() {
        return availAmount;
    }

    public void setAvailAmount(BigDecimal availAmount) {
        this.availAmount = availAmount;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getAddAvailAmount() {
		return addAvailAmount;
	}

	public void setAddAvailAmount(BigDecimal addAvailAmount) {
		this.addAvailAmount = addAvailAmount;
	}

	public BigDecimal getSubAvailAmount() {
		return subAvailAmount;
	}

	public void setSubAvailAmount(BigDecimal subAvailAmount) {
		this.subAvailAmount = subAvailAmount;
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

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	
}