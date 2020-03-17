package com.kyx.biz.recharge.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

public class Recharge implements Serializable {
    private Integer id;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date rechargeTime;

    private Integer rechargeType;
    
    private String rechargeName;//充值方式

    private BigDecimal rechargeAmount;

    private String cardNo;

    private String optUser;

    private String remark;

    private Integer sendCouponType;

    private Integer couponId;

    private static final long serialVersionUID = 1L;
    
    private Integer couponDivDisplay;//1赠送代金券0不赠送
    
    private Integer custId;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeStartTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeEndTime;
	
	private String custName;
	
	private Integer shopId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public Integer getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(Integer rechargeType) {
        this.rechargeType = rechargeType;
    }

    public BigDecimal getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(BigDecimal rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser == null ? null : optUser.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getSendCouponType() {
        return sendCouponType;
    }

    public void setSendCouponType(Integer sendCouponType) {
        this.sendCouponType = sendCouponType;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

	public Integer getCouponDivDisplay() {
		return couponDivDisplay;
	}

	public void setCouponDivDisplay(Integer couponDivDisplay) {
		this.couponDivDisplay = couponDivDisplay;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getRechargeName() {
		return rechargeName;
	}

	public void setRechargeName(String rechargeName) {
		this.rechargeName = rechargeName;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
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

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
    
}