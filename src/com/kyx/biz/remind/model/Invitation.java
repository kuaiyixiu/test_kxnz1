package com.kyx.biz.remind.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 邀约bean
 * 
 * @author daibin
 * @date 2019-5-6 上午8:51:11
 * 
 */
public class Invitation {

	// 会员名
	private String custName;

	// 车牌号
	private String carNumber;

	// 会员电话
	private String customPhone;

	// 服务名
	private String serverName;

	// 周期
	private BigDecimal cycle;

	// 上次服务结束时间
	private Date doneTime;

	// 邀约时间
	private Date invitationTime;

	// 展示开始时间
	private Date showStartTime;

	// 展示结束时间
	private Date showEndTime;

	// 年检
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date checkDate;

	// 开始展示车检日期
	private Date showStartCheckDate;

	// 结束展示车检日期
	private Date showEndCheckDate;

	// 是否车检到期
	private Boolean isCheckDate;

	// 交强险
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date compulsoryDate;

	// 开始展示交强险日期
	private Date showStartCompulsoryDate;

	// 结束展示交强险日期
	private Date showEndCompulsoryDate;

	// 是否交强险到期
	private Boolean isCompulsoryDate;

	// 商业险
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date commercialDate;

	// 开始展示商业险日期
	private Date showStartCommercialDate;

	// 结束展示商业险日期
	private Date showEndCommercialDate;

	// 是否商业险到期
	private Boolean isCommercialDate;

	private Integer id;

	// 保养到期时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date maintainDate;

	// 开始展示保养时间
	private Date showStartMaintainDate;

	// 结束展示保养时间
	private Date showEndMaintainDate;

	// 是否保养到期
	private Boolean isMaintainDate;

	// 会员卡号
	private String cardNo;

	// 门店id
	private Integer shopId;

	// 上次服务时间字符串
	private String doneTimeStr;

	// 交强险状态
	private Integer compulsoryStatus;

	// 商业险状态
	private Integer commercialStatus;

	// 年检状态
	private Integer checkStatus;

	// 保养状态
	private Integer maintainStatus;

	// 车辆保养批次id
	private Integer carMainTainId;

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getCustomPhone() {
		return customPhone;
	}

	public void setCustomPhone(String customPhone) {
		this.customPhone = customPhone;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public BigDecimal getCycle() {
		return cycle;
	}

	public void setCycle(BigDecimal cycle) {
		this.cycle = cycle;
	}

	public Date getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(Date doneTime) {
		this.doneTime = doneTime;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Date getCompulsoryDate() {
		return compulsoryDate;
	}

	public void setCompulsoryDate(Date compulsoryDate) {
		this.compulsoryDate = compulsoryDate;
	}

	public Date getCommercialDate() {
		return commercialDate;
	}

	public void setCommercialDate(Date commercialDate) {
		this.commercialDate = commercialDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getShowStartTime() {
		return showStartTime;
	}

	public void setShowStartTime(Date showStartTime) {
		this.showStartTime = showStartTime;
	}

	public Date getShowEndTime() {
		return showEndTime;
	}

	public void setShowEndTime(Date showEndTime) {
		this.showEndTime = showEndTime;
	}

	public Date getShowStartCheckDate() {
		return showStartCheckDate;
	}

	public void setShowStartCheckDate(Date showStartCheckDate) {
		this.showStartCheckDate = showStartCheckDate;
	}

	public Date getShowEndCheckDate() {
		return showEndCheckDate;
	}

	public void setShowEndCheckDate(Date showEndCheckDate) {
		this.showEndCheckDate = showEndCheckDate;
	}

	public Date getShowStartCompulsoryDate() {
		return showStartCompulsoryDate;
	}

	public void setShowStartCompulsoryDate(Date showStartCompulsoryDate) {
		this.showStartCompulsoryDate = showStartCompulsoryDate;
	}

	public Date getShowEndCompulsoryDate() {
		return showEndCompulsoryDate;
	}

	public void setShowEndCompulsoryDate(Date showEndCompulsoryDate) {
		this.showEndCompulsoryDate = showEndCompulsoryDate;
	}

	public Date getShowStartCommercialDate() {
		return showStartCommercialDate;
	}

	public void setShowStartCommercialDate(Date showStartCommercialDate) {
		this.showStartCommercialDate = showStartCommercialDate;
	}

	public Date getShowEndCommercialDate() {
		return showEndCommercialDate;
	}

	public void setShowEndCommercialDate(Date showEndCommercialDate) {
		this.showEndCommercialDate = showEndCommercialDate;
	}

	public Boolean getIsCheckDate() {
		return isCheckDate;
	}

	public void setIsCheckDate(Boolean isCheckDate) {
		this.isCheckDate = isCheckDate;
	}

	public Boolean getIsCompulsoryDate() {
		return isCompulsoryDate;
	}

	public void setIsCompulsoryDate(Boolean isCompulsoryDate) {
		this.isCompulsoryDate = isCompulsoryDate;
	}

	public Boolean getIsCommercialDate() {
		return isCommercialDate;
	}

	public void setIsCommercialDate(Boolean isCommercialDate) {
		this.isCommercialDate = isCommercialDate;
	}

	public Date getInvitationTime() {
		return invitationTime;
	}

	public void setInvitationTime(Date invitationTime) {
		this.invitationTime = invitationTime;
	}

	public Date getMaintainDate() {
		return maintainDate;
	}

	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
	}

	public Date getShowStartMaintainDate() {
		return showStartMaintainDate;
	}

	public void setShowStartMaintainDate(Date showStartMaintainDate) {
		this.showStartMaintainDate = showStartMaintainDate;
	}

	public Date getShowEndMaintainDate() {
		return showEndMaintainDate;
	}

	public void setShowEndMaintainDate(Date showEndMaintainDate) {
		this.showEndMaintainDate = showEndMaintainDate;
	}

	public Boolean getIsMaintainDate() {
		return isMaintainDate;
	}

	public void setIsMaintainDate(Boolean isMaintainDate) {
		this.isMaintainDate = isMaintainDate;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getDoneTimeStr() {
		return doneTimeStr;
	}

	public void setDoneTimeStr(String doneTimeStr) {
		this.doneTimeStr = doneTimeStr;
	}

	public Integer getCompulsoryStatus() {
		return compulsoryStatus;
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

	public Integer getMaintainStatus() {
		return maintainStatus;
	}

	public void setMaintainStatus(Integer maintainStatus) {
		this.maintainStatus = maintainStatus;
	}

	public Integer getCarMainTainId() {
		return carMainTainId;
	}

	public void setCarMainTainId(Integer carMainTainId) {
		this.carMainTainId = carMainTainId;
	}

}
