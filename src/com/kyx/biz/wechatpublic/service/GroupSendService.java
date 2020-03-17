package com.kyx.biz.wechatpublic.service;

import javax.servlet.http.HttpServletRequest;

import me.chanjar.weixin.common.error.WxErrorException;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.wechatpublic.model.GroupSend;

public interface GroupSendService {
	
	//群发
	@Transactional(rollbackFor=Exception.class)
	RetInfo sendMsg(HttpServletRequest request,GroupSend groupSend) throws WxErrorException;
	
	/**
	 * 获取粉丝总数量
	 * @param request
	 * @return
	 * @throws WxErrorException
	 */
	int getAllFansCount(HttpServletRequest request) throws WxErrorException;
	
	/**
	 * 分页获取列表
	 * @param groupSend
	 * @param pager
	 * @return
	 */
	GrdData getList(GroupSend groupSend, Pager pager);
}
