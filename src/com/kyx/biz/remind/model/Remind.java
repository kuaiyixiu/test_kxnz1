package com.kyx.biz.remind.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Remind implements Serializable {
	private Integer id;

	private Integer remindType;

	private String remindName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date remindDate;

	private Integer userId;

	private String remindContent;

	private String remark;

	private Integer remindStatus;

	private Integer shopId;

	private Integer remindUserId;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRemindType() {
		return remindType;
	}

	public void setRemindType(Integer remindType) {
		this.remindType = remindType;
	}

	public String getRemindName() {
		return remindName;
	}

	public void setRemindName(String remindName) {
		this.remindName = remindName == null ? null : remindName.trim();
	}

	public Date getRemindDate() {
		return remindDate;
	}

	public void setRemindDate(Date remindDate) {
		this.remindDate = remindDate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRemindContent() {
		return remindContent;
	}

	public void setRemindContent(String remindContent) {
		this.remindContent = remindContent == null ? null : remindContent
				.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Integer getRemindStatus() {
		return remindStatus;
	}

	public void setRemindStatus(Integer remindStatus) {
		this.remindStatus = remindStatus;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	// 今日提醒， 以前和现在的时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date oldDate;

	// 待办提醒，当天以后的时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date futureDate;

	public Date getOldDate() {
		return oldDate;
	}

	public void setRemindUserId(Integer remindUserId) {
		this.remindUserId = remindUserId;
	}

	public void setOldDate(Date oldDate) {
		this.oldDate = oldDate;
	}

	public Date getFutureDate() {
		return futureDate;
	}

	public void setFutureDate(Date futureDate) {
		this.futureDate = futureDate;
	}

	public Integer getRemindUserId() {
		return remindUserId;
	}

	// 日常使用
	public static final int NORMAL_USING = 1;

	// 状态
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	// 查询服务的状态
	public static final Integer INVITATION_STATUS = 0;
}