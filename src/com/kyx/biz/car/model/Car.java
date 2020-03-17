package com.kyx.biz.car.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

public class Car implements Serializable {
	private Integer id;

	private Integer carType;

	private String ownerName;

	private String ownerCellphone;

	private Integer vehicleType;

	private String carBrand;

	private String carVin;

	private String carEngine;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date compulsoryDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date commercialDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date checkDate;

	private Integer customId;

	private String remark;

	private String carNumber;

	private Integer compulsoryStatus;

	private Integer commercialStatus;

	private Integer checkStatus;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCarType() {
		return carType;
	}

	public void setCarType(Integer carType) {
		this.carType = carType;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName == null ? null : ownerName.trim();
	}

	public String getOwnerCellphone() {
		return ownerCellphone;
	}

	public void setOwnerCellphone(String ownerCellphone) {
		this.ownerCellphone = ownerCellphone == null ? null : ownerCellphone
				.trim();
	}

	public Integer getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(Integer vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand == null ? null : carBrand.trim();
	}

	public String getCarVin() {
		return carVin;
	}

	public void setCarVin(String carVin) {
		this.carVin = carVin == null ? null : carVin.trim();
	}

	public String getCarEngine() {
		return carEngine;
	}

	public void setCarEngine(String carEngine) {
		this.carEngine = carEngine == null ? null : carEngine.trim();
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date getCompulsoryDate() {
		return compulsoryDate;
	}

	public void setCompulsoryDate(Date compulsoryDate) {
		this.compulsoryDate = compulsoryDate;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date getCommercialDate() {
		return commercialDate;
	}

	public void setCommercialDate(Date commercialDate) {
		this.commercialDate = commercialDate;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Integer getCustomId() {
		return customId;
	}

	public void setCustomId(Integer customId) {
		this.customId = customId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber == null ? null : carNumber.trim();
	}

	// 会员
	public static final int VIP = 1;

	// 非会员
	public static final int DEFAULT = 0;

	// 会员名
	private String custName;

	// 会员电话
	private String cellphone;

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

	public void setCompulsoryStatus(Integer compulsoryStatus) {
		this.compulsoryStatus = compulsoryStatus;
	}

	public Integer getCommercialStatus() {
		return commercialStatus;
	}

	public void setCommercialStatus(Integer commercialStatus) {
		this.commercialStatus = commercialStatus;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	// 关键字
	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeStartTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeEndTime;

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

	// 会员卡号
	private String cardNo;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	// 会员id
	private String custId;

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	// 会员开卡类型
	private Integer custType;

	public Integer getCustType() {
		return custType;
	}

	public void setCustType(Integer custType) {
		this.custType = custType;
	}

	// 会员开卡类型中文名
	private String custTypeName;

	public String getCustTypeName() {
		return custTypeName;
	}

	public void setCustTypeName(String custTypeName) {
		this.custTypeName = custTypeName;
	}

	// 车辆类型中文名
	private String carTypeName;

	public String getCarTypeName() {
		return carTypeName;
	}

	public void setCarTypeName(String carTypeName) {
		this.carTypeName = carTypeName;
	}

	// 交强险日期
	private String compulsoryDateStr;

	// 商业险日期
	private String commercialDateStr;

	// 年检到期
	private String checkDateStr;

	public String getCompulsoryDateStr() {
		return compulsoryDateStr;
	}

	public void setCompulsoryDateStr(String compulsoryDateStr) {
		this.compulsoryDateStr = compulsoryDateStr;
	}

	public String getCommercialDateStr() {
		return commercialDateStr;
	}

	public void setCommercialDateStr(String commercialDateStr) {
		this.commercialDateStr = commercialDateStr;
	}

	public String getCheckDateStr() {
		return checkDateStr;
	}

	public void setCheckDateStr(String checkDateStr) {
		this.checkDateStr = checkDateStr;
	}

}