package com.kyx.basic.sysparam.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.sysparam.model.SysParam;
import com.kyx.basic.util.RetInfo;


public interface SysParamService {
	
	/**
	 * 库存是否启用审批
	 */
	String REPOSITORY_OA = "repository_oa";
	
	/**
	 * 短信推送是否启用
	 */
	String ENABLE_SMS_PUSH = "enable_sms_push";
	
	/**
	 * 微信推送是否启用
	 */
	String ENABLE_WECHAT_PUSH = "enable_wechat_push";
	
	List<SysParam> getListByShopId(Integer shopId);
	
	@Transactional
	RetInfo saveParam(SysParam sysParam);
	
	@Transactional
	RetInfo saveParam(List<SysParam> paramList,Integer userId,Integer shopId);

	String getValueByName(String paramName,Integer shopId);
	
}
