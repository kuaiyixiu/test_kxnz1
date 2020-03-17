package com.kyx.biz.mealdt.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class MealDt implements Serializable {
	private Integer id;

	private Integer type;

	private Integer mealId;

	private Integer itemId;

	private Integer quantity;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getMealId() {
		return mealId;
	}

	public void setMealId(Integer mealId) {
		this.mealId = mealId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	// 售价
	private BigDecimal price;

	// 订单详情中文名
	private String mealDtName;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getMealDtName() {
		return mealDtName;
	}

	public void setMealDtName(String mealDtName) {
		this.mealDtName = mealDtName;
	}

	// 类型中文名
	private String typeName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	// 产品
	public static final int PRODUCT = 1;

	// 服务
	public static final int SERVER = 2;
}