package com.kyx.biz.wechat.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.wechat.mapper.WechatCustomerMapper;
import com.kyx.biz.wechat.model.WechatCustomer;
import com.kyx.biz.wechat.service.WechatCustomerService;

@Service("wechatCustomerService")
public class WechatCustomerServiceImpl implements WechatCustomerService {
	@Resource
	private WechatCustomerMapper wechatCustomerMapper;

	@Override
	public WechatCustomer getByAppidAndOpenId(String appid, String openId) {

		return wechatCustomerMapper.getByAppidAndOpenId(appid, openId);
	}

	@Override
	public boolean addWechatCustomer(Custom custom) {
		WechatCustomer record = setParam(custom);

		return wechatCustomerMapper.insertSelective(record) > 0;
	}

	private WechatCustomer setParam(Custom custom) {
		WechatCustomer wechatCustomer = new WechatCustomer();
		wechatCustomer.setAppId(custom.getAppid());
		wechatCustomer.setAddTime(new Date());
		wechatCustomer.setCardNo(custom.getCardNo());
		wechatCustomer.setOpenId(custom.getOpenId());
		wechatCustomer.setShopId(custom.getShopId());
		wechatCustomer.setLastLogin(WechatCustomer.IS_LAST_LOGIN);

		return wechatCustomer;
	}

	@Override
	public WechatCustomer getWechatCustomerByCardNo(String cardNo) {

		return wechatCustomerMapper.selectByCardNo(cardNo);
	}

}
