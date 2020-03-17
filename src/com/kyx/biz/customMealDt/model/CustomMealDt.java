package com.kyx.biz.customMealDt.model;

import java.io.Serializable;
import java.util.Date;

public class CustomMealDt implements Serializable {
	private Integer id;

	private Integer customMealId;

	private Integer mealDtId;

	private Integer quantity;

	private Integer used;
	
	private Integer addQuantity; //使用数量 异动产品信息时用到 sql语句用+=
    
    private Integer subQuantity;//使用数量 异动产品信息时用到 sql语句用-=

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomMealId() {
		return customMealId;
	}

	public void setCustomMealId(Integer customMealId) {
		this.customMealId = customMealId;
	}

	public Integer getMealDtId() {
		return mealDtId;
	}

	public void setMealDtId(Integer mealDtId) {
		this.mealDtId = mealDtId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getUsed() {
		return used;
	}

	public void setUsed(Integer used) {
		this.used = used;
	}

	// 未使用
	public static final Integer NO_USE = 0;

	// 使用过
	public static final Integer USED = 1;

	private Integer itemId;

	private Integer type;

	private String cardNo;

	private String custName;

	private String cellphone;

	private Date createDate;

	private Date endDate;

	private String mealDtName;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getMealDtName() {
		return mealDtName;
	}

	public void setMealDtName(String mealDtName) {
		this.mealDtName = mealDtName;
	}

	public Integer getAddQuantity() {
		return addQuantity;
	}

	public void setAddQuantity(Integer addQuantity) {
		this.addQuantity = addQuantity;
	}

	public Integer getSubQuantity() {
		return subQuantity;
	}

	public void setSubQuantity(Integer subQuantity) {
		this.subQuantity = subQuantity;
	}
	
}