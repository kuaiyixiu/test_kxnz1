package com.kyx.biz.wechatpublic.service;

import javax.servlet.http.HttpSession;

import me.chanjar.weixin.mp.api.WxMpService;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.wechatpublic.model.CardSet;

public interface CardSetService {

	GrdData getInfo(CardSet cardSet, Pager pager);
    @Transactional
	RetInfo saveData(CardSet cardSet, HttpSession httpSession);
    
	CardSet getById(Integer id);
	/**
	 * 卡卷推送
	 * @param wxMpService 
	 * @param custom
	 * @return
	 */
	RetInfo sendCardInfo(WxMpService wxMpService, Custom custom);
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	CardSet selectByKey(Integer id);

}
