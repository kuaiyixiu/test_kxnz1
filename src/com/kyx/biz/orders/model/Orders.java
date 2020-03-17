package com.kyx.biz.orders.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.kyx.biz.custom.model.Custom;

public class Orders implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -460183055369690776L;

	private Integer id;

	private String orderNo;

	private String carNumber;

	private Integer custId;

	private Integer orderStatus; // 订单状态 0作废 1编辑，2待施工（已提交），3施工中， 4施工完成 5入账 6挂账 7反入账
									// 8反挂账
	
	private Integer orderType;//订单类型  0临牌开单，1非会员开单 2会员开单

	private BigDecimal orderAmt;

	private BigDecimal payAmount;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creatTime;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date submitTime;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date doingTime;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date doneTime;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date finishTime;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date operateTime;

	private String remark;

	private Integer shopId;

	/* 订单服务列表 */
	private List<OrdersServe> ordersServeList;

	/* 订单产品列表 */
	private List<OrdersProduct> ordersProductList;

	/* 订单付款列表 */
	private List<OrdersPay> ordersPayList;

	private String createUser;

	private Custom custom;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date evaluateTime; // 评价时间
	
	private BigDecimal debtAmt; //挂账金额
	
	
	private Integer notOrderStatus; //状态不等于
	
	private String notInOrderStatus;//状态 not in
	
	private Integer notNullCustId;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber == null ? null : carNumber.trim();
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public BigDecimal getOrderAmt() {
		return orderAmt;
	}

	public void setOrderAmt(BigDecimal orderAmt) {
		this.orderAmt = orderAmt;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Date getDoingTime() {
		return doingTime;
	}

	public void setDoingTime(Date doingTime) {
		this.doingTime = doingTime;
	}

	public Date getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(Date doneTime) {
		this.doneTime = doneTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
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

	public List<OrdersServe> getOrdersServeList() {
		return ordersServeList;
	}

	public void setOrdersServeList(List<OrdersServe> ordersServeList) {
		this.ordersServeList = ordersServeList;
	}

	public List<OrdersProduct> getOrdersProductList() {
		return ordersProductList;
	}

	public void setOrdersProductList(List<OrdersProduct> ordersProductList) {
		this.ordersProductList = ordersProductList;
	}

	public List<OrdersPay> getOrdersPayList() {
		return ordersPayList;
	}

	public void setOrdersPayList(List<OrdersPay> ordersPayList) {
		this.ordersPayList = ordersPayList;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeStartTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeEndTime;

	// 会员门店id
	private Integer customShopId;

	// 客户名
	private String custName;

	// 门店名
	private String shopName;

	// 会员卡号
	private String cardNo;
	
	private Integer count; //报表使用 消费次数
	
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

	public Integer getCustomShopId() {
		return customShopId;
	}

	public void setCustomShopId(Integer customShopId) {
		this.customShopId = customShopId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	// 状态中文
	private String orderStatusName;

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Custom getCustom() {
		return custom;
	}

	public void setCustom(Custom custom) {
		this.custom = custom;
	}

	public Date getEvaluateTime() {
		return evaluateTime;
	}

	public void setEvaluateTime(Date evaluateTime) {
		this.evaluateTime = evaluateTime;
	}

	public BigDecimal getDebtAmt() {
		return debtAmt;
	}

	public void setDebtAmt(BigDecimal debtAmt) {
		this.debtAmt = debtAmt;
	}

	public Integer getNotOrderStatus() {
		return notOrderStatus;
	}

	public void setNotOrderStatus(Integer notOrderStatus) {
		this.notOrderStatus = notOrderStatus;
	}

	public Integer getNotNullCustId() {
		return notNullCustId;
	}

	public void setNotNullCustId(Integer notNullCustId) {
		this.notNullCustId = notNullCustId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getNotInOrderStatus() {
		return notInOrderStatus;
	}

	public void setNotInOrderStatus(String notInOrderStatus) {
		this.notInOrderStatus = notInOrderStatus;
	}
	
}