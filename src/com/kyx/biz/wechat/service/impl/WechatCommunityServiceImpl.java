package com.kyx.biz.wechat.service.impl;

import com.kyx.basic.user.model.User;
import com.kyx.basic.util.BasicContant;
import com.kyx.biz.wechat.mapper.WechatCommunityMapper;
import com.kyx.biz.wechat.model.WechatCommunity;
import com.kyx.biz.wechat.service.WechatCommunityService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Service("wechatCommunityService")
public class WechatCommunityServiceImpl implements WechatCommunityService {
    @Resource
    private WechatCommunityMapper wechatCommunityMapper;

    @Override
    public WechatCommunity getByAppidAndOpenId(String appid, String openId) {
        return wechatCommunityMapper.getByAppidAndOpenId(appid, openId);
    }

    @Override
    public boolean addWechatCommunity(String appId, String openId, String userName) {
        WechatCommunity community = new WechatCommunity(appId, openId, userName);
        return wechatCommunityMapper.insertSelective(community) > 0;
    }

    @Override
    public boolean addWechatCommunity(HttpSession session, String userName) {
        Object attribute = session.getAttribute(BasicContant.WXMPUSER_SESSION);
        if (attribute != null) {
            WxMpUser wxMpUser = (WxMpUser) attribute;
            String appid = (String) session.getAttribute(BasicContant.WXMP_APPID_SESSION);
            WechatCommunity community = new WechatCommunity(appid, wxMpUser.getOpenId(), userName);
            return wechatCommunityMapper.insertSelective(community) > 0;
        }
        return false;
    }

    @Override
    public WechatCommunity getWechatCommunityByUserName(String userName) {
        return wechatCommunityMapper.selectByUserName(userName);
    }
}
