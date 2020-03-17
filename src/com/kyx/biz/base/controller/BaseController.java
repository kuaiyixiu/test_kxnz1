package com.kyx.biz.base.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.stereotype.Controller;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.user.model.User;
import com.kyx.basic.util.BasicContant;
import com.kyx.biz.custom.mapper.CustomMapper;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.wechat.config.MainConfiguration;
import com.kyx.biz.wechat.model.WechatConfig;
import com.kyx.biz.wechat.model.WechatCustomer;

/**
 * 公用controller
 * 
 * @author daibin
 * @date 2019-4-25 上午10:26:02
 * 
 */
@Controller
public class BaseController {
	
	  @Resource
	  private CustomMapper customMapper;
  /**
   * 得到客户id
   * 
   * @param request
   * @return
   */
  public Integer getShopId(HttpServletRequest request) {
    Shops shops = (Shops) request.getSession().getAttribute(Shops.SHOPS_SESSION);
    return shops.getId();
  }

  /**
   * 得到用户id
   * 
   * @param request
   * @return
   */
  public Integer getUserId(HttpServletRequest request) {
    User user = (User) request.getSession().getAttribute(User.USER_SESSION);

    return user.getId();
  }

  /**
   * 获取会员custid
   * 
   * @param request
   * @return
   */
  public Integer getCustomId(HttpServletRequest request) {
    Custom custom = (Custom) request.getSession().getAttribute(BasicContant.CUSTOMER_SESSION);
    Integer id = null;
    if (custom != null)
      id = custom.getId();
    return id;
  }

  /**
   * 得到微信会员相关信息
   * 
   * @param request
   * @return
   */
  public WechatCustomer getWechatCustomer(HttpServletRequest request) {
    WechatCustomer wechatCustomer =
        (WechatCustomer) request.getSession().getAttribute(BasicContant.WECHAT_CUSTOMER_SESSION);

    return wechatCustomer;
  }

  /**
   * 微信端使用appId
   * 
   * @param request
   * @return
   */
  public String getAppId(HttpServletRequest request) {
    String appId = (String) request.getSession().getAttribute(BasicContant.APPID);

    return appId;
  }

  /**
   * 微信端使用openId
   * 
   * @param request
   * @return
   */
  public String getOpenId(HttpServletRequest request) {
    String openId = (String) request.getSession().getAttribute(BasicContant.OPENID);

    return openId;
  }

  public User getUser(HttpServletRequest request) {
    User user = (User) request.getSession().getAttribute(User.USER_SESSION);

    return user;
  }

  public WxMpService getWxMapService(HttpServletRequest request) {
    WechatConfig wechatConfig =
        (WechatConfig) request.getSession().getAttribute(BasicContant.SHOP_WECHAT_CONFIG_SESSION);
    MainConfiguration mainConfig =
        new MainConfiguration(wechatConfig.getAppid(), wechatConfig.getAppsecret(),
            wechatConfig.getToken(), wechatConfig.getEncodingaeskey());
    WxMpService wxMpService = mainConfig.wxMpService();
    return wxMpService;
  }

  public String getAccount(HttpServletRequest request) {
    WechatConfig wechatConfig =
        (WechatConfig) request.getSession().getAttribute(BasicContant.SHOP_WECHAT_CONFIG_SESSION);
    return wechatConfig.getAppid();
  }

  /**
   * 获取门店名称
   * 
   * @param request
   * @return
   */
  public String getShopName(HttpServletRequest request) {
    Shops shops = (Shops) request.getSession().getAttribute(Shops.SHOPS_SESSION);
    return shops.getShopName();
  }

  /**
   * 获取当前门店信息
   * 
   * @param request
   * @return
   */
  public Shops getShops(HttpServletRequest request) {
    Shops shops = (Shops) request.getSession().getAttribute(Shops.SHOPS_SESSION);
    return shops;
  }

  /**
   * 微信公众号配置信息
   * 
   * @param request
   * @return
   */
  public WechatConfig getWechatConfig(HttpServletRequest request) {
    WechatConfig wechatConfig =
        (WechatConfig) request.getSession().getAttribute(BasicContant.SHOP_WECHAT_CONFIG_SESSION);
    return wechatConfig;
  }

  /**
   * 得到微信用户信息
   * 
   * @param request
   * @return
   */
  public Custom getWechatCustom(HttpServletRequest request) {
    Custom custom = (Custom) request.getSession().getAttribute(BasicContant.CUSTOMER_SESSION);
    custom = customMapper.selectByPrimaryKey(custom.getId());
    return custom;
  }
}
