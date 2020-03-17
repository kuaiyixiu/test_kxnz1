package com.kyx.biz.wechat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.api.WxConsts.MenuButtonType;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

import com.kyx.biz.wechat.config.MainConfiguration;
import com.kyx.biz.wechat.service.WechatConfigService;
import com.kyx.biz.wxutil.AccessToken;
import com.kyx.biz.wxutil.WechatUtil;

/**
 * 微信公众号添加菜单
 * 
 * @author 严大灯
 * @data 2019-5-13 下午5:36:51
 * @Descripton
 */
public class AddMenu {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String url = "https://api.weixin.qq.com/cgi-bin/menu/create";
		String deleteUrl = "https://api.weixin.qq.com/cgi-bin/menu/delete";
		String appId ="wx3c957523d27e8cb0"; 
				//"wx021ed3fa345145ba";
				//"wx3c957523d27e8cb0";
		String appsecret="1aaf204872fc276bfedf9c655150a9b5";
				//"985d51ee34e92b2e28041649e7d80745";
				//"1aaf204872fc276bfedf9c655150a9b5";
		String encodingAESKey="pYDx1Th7wx7S8JSTm0Um2kkOuf1DnmJ8uWJxCpmg4A8";
				//"lbOwh6P6f2M6olAllxXApnSA7a4b7HbX5Peu2leFkKh";
				//"pYDx1Th7wx7S8JSTm0Um2kkOuf1DnmJ8uWJxCpmg4A8";
		String token="LIANTU";
		String url1 = "http://tongyigang.oicp.net/liantu/wechat/bindcustinfo.do";
		String url2 = "http://tongyigang.oicp.net/liantu/wechat/serviceappointment.do";
		String url3 = "http://tongyigang.oicp.net/liantu/wechat/exitBand.do";
		try {
			MainConfiguration mainConfig = new MainConfiguration(appId, appsecret,
					token, encodingAESKey);
			WxMpService wxMpService = mainConfig.wxMpService();
			String btn1 = wxMpService.oauth2buildAuthorizationUrl(url1,
					WxConsts.OAuth2Scope.SNSAPI_BASE, appId);
			String btn2 = wxMpService.oauth2buildAuthorizationUrl(url2,
					WxConsts.OAuth2Scope.SNSAPI_BASE, appId);
			String btn3 = wxMpService.oauth2buildAuthorizationUrl(url3,
					WxConsts.OAuth2Scope.SNSAPI_BASE, appId);
			WxMenuButton button1 = new WxMenuButton();
			button1.setType(MenuButtonType.VIEW);
			button1.setName("我的会员卡");
			button1.setUrl(btn1);
			WxMenuButton button2 = new WxMenuButton();
			button2.setType(MenuButtonType.VIEW);
			button2.setName("服务预约");
			button2.setUrl(btn2);
//			WxMenuButton button3 = new WxMenuButton();
//			button3.setType(MenuButtonType.VIEW);
//			button3.setName("切换绑定");
//			button3.setUrl(btn3);
			WxMenu menu = new WxMenu();
			menu.getButtons().add(button1);
			menu.getButtons().add(button2);
			//menu.getButtons().add(button3);
			wxMpService.getMenuService().menuCreate(menu);
			
			// 获取token
			// AccessToken accessToken=WechatUtil.getAccessToken("wx3c957523d27e8cb0","1aaf204872fc276bfedf9c655150a9b5");
			// String token=accessToken.getToken();
			//String token = "21_C8WndT_GI2A-6gVzqGtfbBThifmkyZayggrQ1mp1gQ8EzZ9NtvGZtWajX-vNkZnzb_uQnsvjKW_tsVZn8B986_MGCMVYk2b2VXZZ6vavwulwzYTujFcVMiyhlziOhmk6WxDP6OiI3SYrUbRSXCMcABAFKU";
//			Map map = new HashMap();
//			List<ButtonDetail> dtList = new ArrayList<ButtonDetail>();
//			ButtonDetail bt1 = new ButtonDetail();
//			bt1.setType("view");
//			bt1.setName("我的会员卡");
//			bt1.setUrl(btn1);
//			dtList.add(bt1);
//			ButtonDetail bt2 = new ButtonDetail();
//			bt2.setType("view");
//			bt2.setName("服务预约");
//			bt2.setUrl(btn2);
//			dtList.add(bt2);
//			map.put("button", dtList);
//			WechatUtil.doPostStr(url + "?access_token=" + token, map);
			// 删除菜单
			// WechatUtil.doGetStr(deleteUrl+"?access_token="+token);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
