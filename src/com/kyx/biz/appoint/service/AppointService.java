package com.kyx.biz.appoint.service;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.appoint.model.Appoint;

public interface AppointService {

	@Transactional
	RetInfo saveAppoint(Appoint appoint);

	GrdData getList(Appoint appoint, Pager pager);

	/**
	 * 查询客户预约
	 * 
	 * @return
	 */
	GrdData queryCustomAppoints();

	/**
	 * 查询客户预约数量
	 * 
	 * @return
	 */
	int queryCustomAppointsCount();

	/**
	 * 按照id查询
	 * 
	 * @param id
	 * @return
	 */
	Appoint getAppointById(Integer id);

	/**
	 * 完成预约
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	RetInfo carryOutAppoint(Integer id);
}
