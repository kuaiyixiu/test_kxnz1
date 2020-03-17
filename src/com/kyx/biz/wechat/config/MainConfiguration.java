package com.kyx.biz.wechat.config;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;

/**
 * 微信公众号配置类
 * @author 严大灯
 * @data 2019-5-14 下午4:27:01
 * @Descripton
 */
public class MainConfiguration {
	private String appId;

	private String appSecret;

	private String token;

	private String aesKey;

	public MainConfiguration(String appId, String appSecret, String token,String aesKey) {
		this.appId=appId;
		this.appSecret=appSecret;
		this.token=token;
		this.aesKey=aesKey;
	}

	public WxMpConfigStorage wxMpConfigStorage() {
		WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
	//	WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
		configStorage.setAppId(this.appId);
		configStorage.setSecret(this.appSecret);
		configStorage.setToken(this.token);
		configStorage.setAesKey(this.aesKey);
		return configStorage;
	}

	public WxMpService wxMpService() {
		WxMpService wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
		return wxMpService;
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
	
	
}
