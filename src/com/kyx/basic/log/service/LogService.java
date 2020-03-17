package com.kyx.basic.log.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.log.model.Log;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;

public interface LogService {
	
	@Transactional
	int saveLog(HttpServletRequest request,Log log);
	
	/**
	 * 分页获取列表
	 * @param orders
	 * @param pager
	 * @return
	 */
	GrdData getList(Log log, Pager pager);
}
