package com.kyx.biz.wechat.vo;

public class WechartSendVo {
	private String openId;//
	private Integer wechatId;// 模板id
	private Integer type;// 模板类型1用户预约提醒2服务进度提醒3消费提醒4还款通知5车辆保险到期6车辆年检到期
	private String url;// 详情url
	// 微信公众号信息
	private String appId;
	private String appSecret;
	private String token;
	private String aesKey;
	// 微信推送内容
	private TemplateDataVo templateDataVo;

	private Integer shopId;// 记录登录门店

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getWechatId() {
		return wechatId;
	}

	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAesKey() {
		return aesKey;
	}

	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}

	public TemplateDataVo getTemplateDataVo() {
		return templateDataVo;
	}

	public void setTemplateDataVo(TemplateDataVo templateDataVo) {
		this.templateDataVo = templateDataVo;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	// 客户
	public static final Integer APPOINT_TYPE = 1;

	// 消费通知
	public static final Integer CONSUME_TYPE = 3;

	// 车辆保险
	public static final Integer CAR_INSURANCE_TYPE = 5;

	// 车辆年检
	public static final Integer CAR_CHECKDATE_TYPE = 6;

	// 车辆保养
	public static final Integer CAR_MAINTAIN_TYPE = 7;

	// 邀约服务
	public static final Integer SERVER_TYPE = 8;
}
