package com.kyx.biz.wechatpublic.service;

import java.util.List;

import me.chanjar.weixin.mp.api.WxMpService;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.RetInfo;
import com.kyx.biz.wechatpublic.model.AccountMenu;

public interface AccountMenuService {

	List<AccountMenu> listWxMenus();
    /**
     * 保存菜单
     * @param menus
     * @return
     */
	@Transactional
	RetInfo save(String menus);
	/**
	 * 菜单同步到微信公众号
	 * @param wxMpService
	 * @return
	 */
	RetInfo doPublishMenu(WxMpService wxMpService);

}
