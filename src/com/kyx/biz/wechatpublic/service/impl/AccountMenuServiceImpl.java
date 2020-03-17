package com.kyx.biz.wechatpublic.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kyx.basic.util.Constants;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.wechatpublic.mapper.AccountMenuMapper;
import com.kyx.biz.wechatpublic.model.AccountMenu;
import com.kyx.biz.wechatpublic.service.AccountMenuService;
@Service("accountMenuService")
public class AccountMenuServiceImpl implements AccountMenuService {
	@Resource
	private AccountMenuMapper accountMenuMapper;

	@Override
	public List<AccountMenu> listWxMenus() {
		
		return accountMenuMapper.listWxMenus();
	}

	@Override
	public RetInfo save(String menus) {
		RetInfo info=RetInfo.Success("保存成功");
		JSONArray jsons = JSONArray.parseArray(menus);
		//每次先行删除公众号所有菜单
		accountMenuMapper.delete();
		if (CollectionUtils.isNotEmpty(jsons)) {
			for (int i = 0; i < jsons.size(); i++) {
				JSONObject json = jsons.getJSONObject(i);
				if (null != json) {
					AccountMenu accountMenu = new AccountMenu();
//					String pid = CommonUtil.getUID();
//					accountMenu.setId(pid);
					accountMenu.setName(json.getString("name"));
					accountMenu.setSort(i + 1);
					accountMenu.setParentId(0);
					if (json.containsKey("type")) {
						accountMenu.setMtype(json.getString("type"));
						//判断是否设置key
						if (Constants.MENU_NEED_KEY.contains(json.getString("type"))) {
							accountMenu.setEventType("fix");
                            accountMenu.setMsgType(json.getString("msgType"));
                            accountMenu.setMsgId(json.getString("msgId"));
						}
					}
					if (json.containsKey("url")) {
						accountMenu.setUrl(json.getString("url"));
					}
					if (json.containsKey("media_id")) {
						accountMenu.setMsgId(json.getString("media_id"));
					}
					accountMenu.setCreateTime(new Date());
					//保存
					accountMenuMapper.insertSelective(accountMenu);
					//判断是否有subbutton
					if (json.containsKey("sub_button")) {
						JSONArray buttons = json.getJSONArray("sub_button");
						if (CollectionUtils.isNotEmpty(buttons)) {
							Integer pid = accountMenu.getId();
							for (int j = 0; j < buttons.size(); j++) {
								json = buttons.getJSONObject(j);
								accountMenu = new AccountMenu();
								accountMenu.setParentId(pid);
								accountMenu.setName(json.getString("name"));
								accountMenu.setSort(j + 1);
								if (json.containsKey("type")) {
									accountMenu.setMtype(json.getString("type"));
									//判断是否设置key
									if (Constants.MENU_NEED_KEY.contains(json.getString("type"))) {
										accountMenu.setEventType("fix");
                                        accountMenu.setMsgType(json.getString("msgType"));
                                        accountMenu.setMsgId(json.getString("msgId"));
									}
								}
								if (json.containsKey("url")) {
									accountMenu.setUrl(json.getString("url"));
								}
								if (json.containsKey("media_id")) {
									accountMenu.setMsgId(json.getString("media_id"));
								}
								accountMenu.setCreateTime(new Date());
								accountMenuMapper.insertSelective(accountMenu);
							}
						}
					}
				}
			}
		}
		return info;
	}

	@Override
	public RetInfo doPublishMenu(WxMpService wxMpService) {
		RetInfo info;
		try {
			WxMenu wxMenu=getWxMenu(wxMpService);
			wxMpService.getMenuService().menuCreate(wxMenu);
			info=RetInfo.Success("同步成功");
		} catch (WxErrorException e) {
			info=RetInfo.Error("同步失败");
			e.printStackTrace();
		}
		return info;
	}
    //获取微信菜单
	private WxMenu getWxMenu(WxMpService wxMpService) {
		List<AccountMenu> accountMenus=accountMenuMapper.listWxMenus();
		WxMenu wxMenu=new WxMenu();
		Map<Integer,WxMenuButton> map=new HashMap<Integer,WxMenuButton>();
		for(AccountMenu menu:accountMenus){
			if(menu.getParentId()==0){//一级菜单
				WxMenuButton wxMenuButton=new WxMenuButton();
				wxMenuButton.setType(menu.getMtype());
				wxMenuButton.setName(menu.getName());
				if(StringUtils.isNotEmpty(menu.getUrl()))
					wxMenuButton.setUrl(wxMpService.oauth2buildAuthorizationUrl(menu.getUrl(), WxConsts.OAuth2Scope.SNSAPI_USERINFO, wxMpService.getWxMpConfigStorage().getAppId()));
				map.put(menu.getId(), wxMenuButton);
			}else{//二级菜单
				WxMenuButton wxMenuButton=new WxMenuButton();
				wxMenuButton.setType(menu.getMtype());
				wxMenuButton.setName(menu.getName());
				if(StringUtils.isNotEmpty(menu.getUrl()))
					wxMenuButton.setUrl(wxMpService.oauth2buildAuthorizationUrl(menu.getUrl(), WxConsts.OAuth2Scope.SNSAPI_USERINFO, wxMpService.getWxMpConfigStorage().getAppId()));
				WxMenuButton parentButton=map.get(menu.getParentId());
				parentButton.getSubButtons().add(wxMenuButton);			
			}
		}
		for (Integer key : map.keySet()) { 
			  WxMenuButton button = map.get(key); 
			  wxMenu.getButtons().add(button);
			}
		
		return wxMenu;
	}

}
