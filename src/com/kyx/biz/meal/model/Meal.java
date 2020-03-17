package com.kyx.biz.meal.model;

import java.io.Serializable;
import java.util.List;

import com.kyx.biz.mealdt.model.MealDt;

public class Meal implements Serializable {
	private Integer id;

	private Integer day;

	private String name;

	private Long amount;

	private String status;

	private Integer shopId;

	private String remark;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	// 逗号分割多个门店
	private String shopIds;

	public String getShopIds() {
		return shopIds;
	}

	public void setShopIds(String shopIds) {
		this.shopIds = shopIds;
	}

	// 产品
	private List<MealDt> mealDts;

	public List<MealDt> getMealDts() {
		return mealDts;
	}

	public void setMealDts(List<MealDt> mealDts) {
		this.mealDts = mealDts;
	}

	// 上架
	public static final String SHELF = "1";

	// 下架
	public static final String OBTAINED = "0";

	// 订单明细字符串
	private String mealDtStr;

	public String getMealDtStr() {
		return mealDtStr;
	}

	public void setMealDtStr(String mealDtStr) {
		this.mealDtStr = mealDtStr;
	}

	// 状态逗号分割
	private String statusStr;

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	// 门店名
	private String shopName;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	// 原价，总价值
	private int originalPrice;

	public int getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(int originalPrice) {
		this.originalPrice = originalPrice;
	}

	// 套餐有效期结束时间
	private String endTime;

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}