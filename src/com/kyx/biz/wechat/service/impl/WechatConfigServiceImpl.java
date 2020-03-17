package com.kyx.biz.wechat.service.impl;

import javax.annotation.Resource;

import com.kyx.biz.wechat.config.MainConfiguration;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Service;

import com.kyx.biz.wechat.mapper.WechatConfigMapper;
import com.kyx.biz.wechat.model.WechatConfig;
import com.kyx.biz.wechat.service.WechatConfigService;
@Service("wechatConfigService")
public class WechatConfigServiceImpl implements WechatConfigService {
	@Resource
    private WechatConfigMapper wechatConfigMapper;
	@Override
	public WechatConfig getByAppId(String appId) {
		return wechatConfigMapper.getByAppId(appId);
	}
	@Override
	public WechatConfig getById(Integer wechatId) {
		
		return wechatConfigMapper.selectByPrimaryKey(wechatId);
	}

    @Override
    public String getOpenId(String appId, String code) {
        String openId = "";
        try {
            WechatConfig wechatConfig = wechatConfigMapper.getByAppId(appId);
            MainConfiguration mainConfig = new MainConfiguration(wechatConfig.getAppid(), wechatConfig.getAppsecret(),
                    wechatConfig.getToken(), wechatConfig.getEncodingaeskey());
            WxMpService wxMpService = mainConfig.wxMpService();
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            openId = wxMpOAuth2AccessToken.getOpenId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return openId;
    }

    @Override
    public WxMpUser getWxMpUser(String appId, String code) {
        try {
            WechatConfig wechatConfig = wechatConfigMapper.getByAppId(appId);
            MainConfiguration mainConfig = new MainConfiguration(wechatConfig.getAppid(), wechatConfig.getAppsecret(),
                    wechatConfig.getToken(), wechatConfig.getEncodingaeskey());
            WxMpService wxMpService = mainConfig.wxMpService();
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            String openId = wxMpOAuth2AccessToken.getOpenId();
            WxMpUserService wxMpuserService = wxMpService.getUserService();
            return wxMpuserService.userInfo(openId);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }


}
