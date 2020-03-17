package com.kyx.biz.appoint.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.appoint.mapper.AppointMapper;
import com.kyx.biz.appoint.model.Appoint;
import com.kyx.biz.appoint.service.AppointService;

@Service("appointService")
public class AppointServiceImpl implements AppointService {

	@Resource
	private AppointMapper appointMapper;

	@Override
	public RetInfo saveAppoint(Appoint appoint) {
		String msg = checkAppoint(appoint);
		if (StringUtils.isNotBlank(msg)) {
			return RetInfo.Error(msg);
		}
		appointMapper.insertSelective(appoint);
		return RetInfo.Success("");
	}

	private String checkAppoint(Appoint appoint) {
		if (appoint.getCustId() == null) {
			return "客户编号不能为空";
		} else if (appoint.getShopId() == null) {
			return "手机号不能为空";
		} else if (StringUtils.isBlank(appoint.getCarNumber())) {
			return "车牌号不能为空";
		} else if (StringUtils.isBlank(appoint.getCellphone())) {
			return "手机号不能为空";
		} else if (appoint.getItemId() == null) {
			return "预约项目不能为空";
		} else if (appoint.getAppointTime() == null) {
			return "预约时间不能为空";
		}
		return "";
	}

	@Override
	public GrdData getList(Appoint appoint, Pager pager) {
		Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<Appoint> list = appointMapper.selectList(appoint);
		for (Appoint app : list) {
			app.setItemName(getItemName(app.getItemId()));
		}

		return new GrdData(page.getTotal(), list);
	}

	private String getItemName(Integer itemId) {
		if (itemId == 1) {
			return "保养";
		} else if (itemId == 2) {
			return "打蜡";
		} else if (itemId == 3) {
			return "洗车";
		} else if (itemId == 4) {
			return "其他";
		}
		return "";
	}

	@Override
	public GrdData queryCustomAppoints() {
		// 查询当天 ~ 前三天内的预约
		Date dateRangeEndTime = AppUtils.getEndTime(new Date());
		Date dateRangeStartTime = AppUtils.getDayByCycle(dateRangeEndTime, -3);

		List<Appoint> list = appointMapper.selectCustomAppoints(
				dateRangeStartTime, dateRangeEndTime);

		return new GrdData(Long.valueOf(list.size()), list);
	}

	@Override
	public int queryCustomAppointsCount() {
		GrdData grd = queryCustomAppoints();
		int count = grd.getCount();

		return count;
	}

	@Override
	public Appoint getAppointById(Integer id) {
		Appoint appoint = appointMapper.selectByPrimaryKey(id);
		String addTimeStr = AppUtils.date2String1(appoint.getAddTime());
		appoint.setAddTimeStr(addTimeStr);

		String itemName = getItemName(appoint.getItemId());
		appoint.setItemName(itemName);

		return appoint;
	}

	@Override
	public RetInfo carryOutAppoint(Integer id) {
		boolean bool = appointMapper.deleteByPrimaryKey(id) > 0;

		return new RetInfo(bool, "预约");
	}
}
