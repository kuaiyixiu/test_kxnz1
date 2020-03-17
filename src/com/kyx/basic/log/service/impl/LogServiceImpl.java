package com.kyx.basic.log.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.log.mapper.LogMapper;
import com.kyx.basic.log.model.Log;
import com.kyx.basic.log.service.LogService;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.biz.payRecord.model.PayRecord;

@Service("logService")
public class LogServiceImpl implements LogService {
	@Resource
	private LogMapper logMapper;

	@Override
	public int saveLog(HttpServletRequest request, Log log) {
		Shops shops = (Shops) request.getSession().getAttribute(Shops.SHOPS_SESSION);
		if (shops != null)
		log.setShopId(shops.getId());
		log.setResUrl(request.getRequestURL().toString());
		log.setUserIp(request.getRemoteAddr());
		log.setUserName(AppUtils.getOptUserName(request.getSession()));
		return logMapper.insertSelective(log);
	}

	@Override
	public GrdData getList(Log log, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<Log> list=logMapper.getList(log);
		return new GrdData(page.getTotal(),list);
	}

}
