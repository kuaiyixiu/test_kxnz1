package com.kyx.biz.wechat.config;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.api.WxConsts.MenuButtonType;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;

/**
 * 生成微信菜单类
 * @author 严大灯
 * @data 2019-5-14 下午4:28:41
 * @Descripton
 */
public class MenuConfig {
	public static String appId="wx3c957523d27e8cb0";
	public static String appsecret="1aaf204872fc276bfedf9c655150a9b5";
	public static String token="LIANTU";
	public static String aesKey="pYDx1Th7wx7S8JSTm0Um2kkOuf1DnmJ8uWJxCpmg4A8";

	/**
	 * 定义菜单结构
	 *
	 * @return
	 */
	protected static WxMenu getMenu() {
		MainConfiguration mainConfig = new MainConfiguration(appId, appsecret, token, aesKey);
		WxMpService wxMpService = mainConfig.wxMpService();
		String url1 = "http://tongyigang.oicp.net/kxnz/wechat/bindcustinfo.do";
		String url2 = "http://tongyigang.oicp.net/kxnz/wechat/serviceappointment.do";
		WxMenu menu = new WxMenu();
		WxMenuButton button1 = new WxMenuButton();
		button1.setType(MenuButtonType.VIEW);
		button1.setName("我的会员卡");
		button1.setUrl(wxMpService.oauth2buildAuthorizationUrl(url1, WxConsts.OAuth2Scope.SNSAPI_USERINFO, mainConfig.getAppId()));
		WxMenuButton button2 = new WxMenuButton();
		button2.setType(MenuButtonType.VIEW);
		button2.setName("服务预约");
		button2.setUrl(wxMpService.oauth2buildAuthorizationUrl(url2, WxConsts.OAuth2Scope.SNSAPI_USERINFO, mainConfig.getAppId()));

		menu.getButtons().add(button1);
		return menu;
	}




	/**
	 * 运行此main函数即可生成公众号自定义菜单，注意要修改MainConfig构造方法行代码的对应四个参数为实际值
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		MainConfiguration mainConfig = new MainConfiguration(appId, appsecret, token, aesKey);
		WxMpService wxMpService = mainConfig.wxMpService();
		try {
			wxMpService.getMenuService().menuCreate(getMenu());
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
	}

}
