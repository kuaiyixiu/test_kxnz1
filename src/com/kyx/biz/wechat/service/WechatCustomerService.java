package com.kyx.biz.wechat.service;

import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.wechat.model.WechatCustomer;

public interface WechatCustomerService {
	/**
	 * 根据公众号和openid查询客户登录的信息
	 * 
	 * @param appid
	 * @param openId
	 * @return
	 */
	WechatCustomer getByAppidAndOpenId(String appid, String openId);

	/**
	 * 添加微信客户
	 * 
	 * @param custom
	 * @return
	 */
	boolean addWechatCustomer(Custom custom);

	/**
	 * 按照会员卡号得到微信用户信息
	 * 
	 * @param cardNo
	 * @return
	 */
	WechatCustomer getWechatCustomerByCardNo(String cardNo);
}
