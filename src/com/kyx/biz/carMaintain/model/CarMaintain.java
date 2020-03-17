package com.kyx.biz.carMaintain.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CarMaintain implements Serializable {
	private Integer id;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date remindTime;

	private Integer progressMileage;

	private Integer remindMileage;

	private Integer carId;

	private String remark;

	private Integer status;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(Date remindTime) {
		this.remindTime = remindTime;
	}

	public Integer getProgressMileage() {
		return progressMileage;
	}

	public void setProgressMileage(Integer progressMileage) {
		this.progressMileage = progressMileage;
	}

	public Integer getRemindMileage() {
		return remindMileage;
	}

	public void setRemindMileage(Integer remindMileage) {
		this.remindMileage = remindMileage;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	// 车牌号
	private String carNumber;

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}