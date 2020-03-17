package com.kyx.biz.wechat.service;

import com.kyx.biz.wechat.model.WechatConfig;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

public interface WechatConfigService {

	WechatConfig getByAppId(String appId);

	WechatConfig getById(Integer wechatId);

	String getOpenId(String appId, String code);

	WxMpUser getWxMpUser(String appId, String code);

}
