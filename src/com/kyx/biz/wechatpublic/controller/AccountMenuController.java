package com.kyx.biz.wechatpublic.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import me.chanjar.weixin.mp.api.WxMpService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kyx.basic.db.Dbs;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.wechat.model.WechatConfig;
import com.kyx.biz.wechatpublic.model.AccountMenu;
import com.kyx.biz.wechatpublic.service.AccountMenuService;
import com.kyx.biz.wxutil.WxUtil;

/**
 * 微信公众号菜单配置
 * @author 严大灯
 * @data 2019-9-5 上午11:19:32
 * @Descripton
 */
@Controller
@RequestMapping("/wechatmenu")
public class AccountMenuController extends WechatPublicController {
	@Resource
	private AccountMenuService accountMenuService;
    /**
     * 获取菜单
     * @param accountMenu
     * @return
     */
	@RequestMapping(value = "/list")
	@ResponseBody
	public String list(HttpServletRequest request) {
		//判断当前数据库是否是主库
		String dbName=Dbs.getDbName();
	    WechatConfig wechatConfig =
			  (WechatConfig) request.getSession().getAttribute(BasicContant.SHOP_WECHAT_CONFIG_SESSION);
	    if(!BasicContant.PLAT_DBNAME.equals(dbName) && wechatConfig != null && 1 == wechatConfig.getId()) {
			    	
	      return  AppUtils.getReturnInfo (RetInfo.Error("请先修改公众号配置"));
			   
	    }
		List<AccountMenu> menus = accountMenuService.listWxMenus();
		RetInfo info=RetInfo.Success("");
		info.setRetData(WxUtil.prepareMenus(menus));
		return AppUtils.getReturnInfo(info);
	}
	/**
	 * 保存菜单
	 * @param menus
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(String menus,HttpServletRequest request) {
		RetInfo retInfo=accountMenuService.save(menus);
		return AppUtils.getReturnInfo(retInfo);
	}
	
	@RequestMapping(value = "/doPublishMenu")
	@ResponseBody
	public String doPublishMenu(HttpServletRequest request) {
		//判断当前数据库是否是主库(因为创建的门店默认挂在连途公众号这里为了防止修改全局的公众号做判断)
		String dbName=Dbs.getDbName();
		WechatConfig wechatConfig =
	    (WechatConfig) request.getSession().getAttribute(BasicContant.SHOP_WECHAT_CONFIG_SESSION);
	    if(!BasicContant.PLAT_DBNAME.equals(dbName) && wechatConfig != null && 1 == wechatConfig.getId()) {
					    	
		 return  AppUtils.getReturnInfo (RetInfo.Error("请先去修改公众号配置"));
					   
	    }
		WxMpService wxMpService=getWxMapService(request);
		RetInfo retInfo=accountMenuService.doPublishMenu(wxMpService);
		return AppUtils.getReturnInfo(retInfo);
	}
}
