package com.kyx.biz.orders.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrdersProduct implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1524852228504344110L;

	private Integer id;

	private Integer iidno;

	private Integer orderId;

	private Integer productId;

	private String productName;

	private Integer quantity;

	private BigDecimal price;

	private String remark;

	private Integer shopId;

	private Integer productUser;
	
	private String productUserName;

	private BigDecimal productRoyal;

	// 产品类型
	private String productType;
	
	private Integer mealPayRecordId;

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

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
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

	public Integer getProductUser() {
		return productUser;
	}

	public void setProductUser(Integer productUser) {
		this.productUser = productUser;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getProductRoyal() {
		return productRoyal;
	}

	public void setProductRoyal(BigDecimal productRoyal) {
		this.productRoyal = productRoyal;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Integer getMealPayRecordId() {
		return mealPayRecordId;
	}

	public void setMealPayRecordId(Integer mealPayRecordId) {
		this.mealPayRecordId = mealPayRecordId;
	}

	public String getProductUserName() {
		return productUserName;
	}

	public void setProductUserName(String productUserName) {
		this.productUserName = productUserName;
	}
	
}