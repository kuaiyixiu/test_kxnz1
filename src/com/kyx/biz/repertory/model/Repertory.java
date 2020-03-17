package com.kyx.biz.repertory.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

public class Repertory implements Serializable {
    private Integer id;

    private Integer supplyId;

    private String remark;
    
    private Integer repertoryStatus; //'状态 0.临时单 2.已入库3作废 4审批中 5审批完成' 
    @JSONField(format="yyyy-MM-dd hh:mm:ss")
    private Date addTime;

    private String userName;

    private Integer kind;

    private Integer shopId;

    private static final long serialVersionUID = 1L;
    
    private String supplyName;
    
    private String proLidt;
    
    private BigDecimal needPay;
    private BigDecimal hasPay;
    private BigDecimal payAll;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeStartTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeEndTime;
	
	
	private String optUserName;//新增字段 传用户实际姓名 

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getRepertoryStatus() {
        return repertoryStatus;
    }

    public void setRepertoryStatus(Integer repertoryStatus) {
        this.repertoryStatus = repertoryStatus;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

	public String getSupplyName() {
		return supplyName;
	}

	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}

	public String getProLidt() {
		return proLidt;
	}

	public void setProLidt(String proLidt) {
		this.proLidt = proLidt;
	}

	public BigDecimal getNeedPay() {
		return needPay;
	}

	public void setNeedPay(BigDecimal needPay) {
		this.needPay = needPay;
	}

	public BigDecimal getHasPay() {
		return hasPay;
	}

	public void setHasPay(BigDecimal hasPay) {
		this.hasPay = hasPay;
	}

	public BigDecimal getPayAll() {
		return payAll;
	}

	public void setPayAll(BigDecimal payAll) {
		this.payAll = payAll;
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

	public String getOptUserName() {
		return optUserName;
	}

	public void setOptUserName(String optUserName) {
		this.optUserName = optUserName;
	}
    
    
}