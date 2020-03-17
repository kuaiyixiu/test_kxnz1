package com.kyx.biz.payRecord.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class PayRecord implements Serializable {
    private Integer id;

    private Integer typeId;
    
    private String typeName;
    
    private String payName;

    private BigDecimal amount;
    
    private Integer count; //使用次数（报表使用）

    private String remark;

    private Integer mealDtId;

    private Integer kind;//付款来源  1客户订单支付 （收入）2门店入库（支出） 3客户购买套餐（收入） 4客户挂账还款（收入） 5.门店挂账还款（支出）6门店退货（收入） 7.门店退货挂账还款（收入 供应商还款） 8订单反入账或反挂账（支出）9入库单作废(收入) 10退货单作废(支出)11调拨还款(收入)

    private Integer sourceId;

    private Integer shopId;

    private Date addTime;

    private static final long serialVersionUID = 1L;
    
    private Integer inKind;  //3:(1,3,4)

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeStartTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeEndTime;
	
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getMealDtId() {
        return mealDtId;
    }

    public void setMealDtId(Integer mealDtId) {
        this.mealDtId = mealDtId;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
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
    
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	// 套餐
	public static final Integer KEY_MEAL = 3;

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

	public Integer getInKind() {
		return inKind;
	}

	public void setInKind(Integer inKind) {
		this.inKind = inKind;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}
	
}