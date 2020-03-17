package com.kyx.biz.customMeal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.kyx.biz.mealdt.model.MealDt;

public class CustomMeal implements Serializable {
	private Integer id;

	private Integer custId;

	private Integer mealId;

	private BigDecimal price;

	private Date endDate;

	private Date createDate;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public Integer getMealId() {
		return mealId;
	}

	public void setMealId(Integer mealId) {
		this.mealId = mealId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	// 套餐中文名
	private String name;

	// 门店id
	private Integer shopId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	// 套餐明细
	private List<MealDt> customMealDts;

	public List<MealDt> getCustomMealDts() {
		return customMealDts;
	}

	public void setCustomMealDts(List<MealDt> customMealDts) {
		this.customMealDts = customMealDts;
	}

	private List<MealDt> productMealDts;

	private List<MealDt> serveMealDt;

	public List<MealDt> getProductMealDts() {
		return productMealDts;
	}

	public void setProductMealDts(List<MealDt> productMealDts) {
		this.productMealDts = productMealDts;
	}

	public List<MealDt> getServeMealDt() {
		return serveMealDt;
	}

	public void setServeMealDt(List<MealDt> serveMealDt) {
		this.serveMealDt = serveMealDt;
	}

	// 结束时间字符串
	private String endDateStr;

	// 开始时间字符串
	private String createDateStr;

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

}