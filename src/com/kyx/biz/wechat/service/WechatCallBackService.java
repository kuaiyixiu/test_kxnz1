package com.kyx.biz.wechat.service;

import java.util.Map;

import com.kyx.biz.wxutil.AccessToken;


public interface WechatCallBackService {
    /**
     * 订阅、取消订阅事件
     * @param map
     * @return
     */
	String subscribeNotice(Map<String, String> map);
	
	
	//AccessToken getAccessToken(SendInfo sendInfo) throws Exception;

}
