package com.kyx.biz.orders.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrdersServe implements Serializable {
	private Integer id;

	private Integer iidno;

	private Integer orderId;

	private Integer serveId;

	private String serveName;

	private Integer quantity;

	private BigDecimal price;

	private Integer serveStatus;

	private Date doneTime;

	private String remark;

	private Integer shopId;

	private Integer serveUser;
	
	private String serveUserName;

	private Integer sellUser;
	
	private String sellUserName;

	private BigDecimal serveRoyal;

	private BigDecimal sellRoyal;

	private Integer status;
	
	private Integer mealPayRecordId;

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

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getServeId() {
		return serveId;
	}

	public void setServeId(Integer serveId) {
		this.serveId = serveId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getServeStatus() {
		return serveStatus;
	}

	public void setServeStatus(Integer serveStatus) {
		this.serveStatus = serveStatus;
	}

	public Date getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(Date doneTime) {
		this.doneTime = doneTime;
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

	public Integer getServeUser() {
		return serveUser;
	}

	public void setServeUser(Integer serveUser) {
		this.serveUser = serveUser;
	}

	public Integer getSellUser() {
		return sellUser;
	}

	public void setSellUser(Integer sellUser) {
		this.sellUser = sellUser;
	}

	public String getServeName() {
		return serveName;
	}

	public void setServeName(String serveName) {
		this.serveName = serveName;
	}

	public BigDecimal getServeRoyal() {
		return serveRoyal;
	}

	public void setServeRoyal(BigDecimal serveRoyal) {
		this.serveRoyal = serveRoyal;
	}

	public BigDecimal getSellRoyal() {
		return sellRoyal;
	}

	public void setSellRoyal(BigDecimal sellRoyal) {
		this.sellRoyal = sellRoyal;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getMealPayRecordId() {
		return mealPayRecordId;
	}

	public void setMealPayRecordId(Integer mealPayRecordId) {
		this.mealPayRecordId = mealPayRecordId;
	}

	public String getServeUserName() {
		return serveUserName;
	}

	public void setServeUserName(String serveUserName) {
		this.serveUserName = serveUserName;
	}

	public String getSellUserName() {
		return sellUserName;
	}

	public void setSellUserName(String sellUserName) {
		this.sellUserName = sellUserName;
	}
	
}