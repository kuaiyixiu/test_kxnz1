package com.kyx.biz.custom.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.kyx.biz.car.model.Car;

public class Custom implements Serializable {
	private Integer id;

	private String cardNo;

	private Integer custType;

	private String custName;

	private String cellphone;

	private Integer score;
	
	private Integer addScore;
	
	private Integer subScore;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date birthday;

	private Integer sex;

	private String remark;

	private BigDecimal balance;
	
	private BigDecimal addBalance;
	
	private BigDecimal subBalance;

	private Integer shopId;

	private Integer enabled;
	
	private String chooseAdids;//虚拟字段 微信推送卡卷使用
	
	private Integer sumNum;//发送卷数量
	
	private String wechatOpenId;//关注微信公众号标识
	
	private String wechatName;
	
	private Integer wechatCardId;
	
	private Integer bindFlag;//绑定标识  是否绑定微信号 1是0否
	
	private Date addTime;
	
	private Date dateRangeStartTime;
	private Date dateRangeEndTime;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo == null ? null : cardNo.trim();
	}

	public Integer getCustType() {
		return custType;
	}

	public void setCustType(Integer custType) {
		this.custType = custType;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName == null ? null : custName.trim();
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone == null ? null : cellphone.trim();
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	/**
	 * 车辆数据列表
	 */
	private String carDataList;

	public String getCarDataList() {
		return carDataList;
	}

	public void setCarDataList(String carDataList) {
		this.carDataList = carDataList;
	}

	// 生日
	private String birthdayStr;

	public String getBirthdayStr() {
		return birthdayStr;
	}

	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}

	// 关键字
	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	// 车辆列表
	private List<Car> carList;

	public List<Car> getCarList() {
		return carList;
	}

	public void setCarList(List<Car> carList) {
		this.carList = carList;
	}

	// 会员类型中文名
	private String customTypeName;

	public String getCustomTypeName() {
		return customTypeName;
	}

	public void setCustomTypeName(String customTypeName) {
		this.customTypeName = customTypeName;
	}

	// 用于wechatcustomer的参数
	private String appid;

	private String openId;
	
	//openid不等于空
	private Integer notNullOpenId;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getAddScore() {
		return addScore;
	}

	public void setAddScore(Integer addScore) {
		this.addScore = addScore;
	}

	public Integer getSubScore() {
		return subScore;
	}

	public void setSubScore(Integer subScore) {
		this.subScore = subScore;
	}

	public BigDecimal getAddBalance() {
		return addBalance;
	}

	public void setAddBalance(BigDecimal addBalance) {
		this.addBalance = addBalance;
	}

	public BigDecimal getSubBalance() {
		return subBalance;
	}

	public void setSubBalance(BigDecimal subBalance) {
		this.subBalance = subBalance;
	}

	public String getChooseAdids() {
		return chooseAdids;
	}

	public void setChooseAdids(String chooseAdids) {
		this.chooseAdids = chooseAdids;
	}

	public Integer getSumNum() {
		return sumNum;
	}

	public void setSumNum(Integer sumNum) {
		this.sumNum = sumNum;
	}

	public String getWechatOpenId() {
		return wechatOpenId;
	}

	public void setWechatOpenId(String wechatOpenId) {
		this.wechatOpenId = wechatOpenId;
	}

	public Integer getWechatCardId() {
		return wechatCardId;
	}

	public void setWechatCardId(Integer wechatCardId) {
		this.wechatCardId = wechatCardId;
	}

	public String getWechatName() {
		return wechatName;
	}

	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}

	public Integer getBindFlag() {
		return bindFlag;
	}

	public void setBindFlag(Integer bindFlag) {
		this.bindFlag = bindFlag;
	}

	public Integer getNotNullOpenId() {
		return notNullOpenId;
	}

	public void setNotNullOpenId(Integer notNullOpenId) {
		this.notNullOpenId = notNullOpenId;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

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
	
	
	
}