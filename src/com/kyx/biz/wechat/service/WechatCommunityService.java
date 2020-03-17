package com.kyx.biz.wechat.service;

import com.kyx.basic.user.model.User;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.wechat.model.WechatCommunity;
import com.kyx.biz.wechat.model.WechatCustomer;

import javax.servlet.http.HttpSession;

/**
 * 社区模块用户绑定
 */
public interface WechatCommunityService {
    /**
     * 根据公众号和openid查询师傅登录的信息
     *
     * @param appid
     * @param openId
     * @return
     */
    WechatCommunity getByAppidAndOpenId(String appid, String openId);

    /**
     * 添加微信客户
     *
     * @param appId
     * @param openId
     * @param userName
     * @return
     */
    boolean addWechatCommunity(String appId, String openId, String userName);

    /**
     * 添加微信客户
     *
     * @param session
     * @param userName
     * @return
     */
    boolean addWechatCommunity(HttpSession session, String userName);

    /**
     * 按照用户名称得到微信用户信息
     *
     * @param userName
     * @return
     */
    WechatCommunity getWechatCommunityByUserName(String userName);
}
