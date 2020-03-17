package com.kyx.biz.remind.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.car.mapper.CarMapper;
import com.kyx.biz.car.model.Car;
import com.kyx.biz.car.service.CarService;
import com.kyx.biz.carMaintain.mapper.CarMaintainMapper;
import com.kyx.biz.carMaintain.model.CarMaintain;
import com.kyx.biz.orders.mapper.OrdersMapper;
import com.kyx.biz.orders.mapper.OrdersServeMapper;
import com.kyx.biz.orders.model.OrdersServe;
import com.kyx.biz.remind.mapper.RemindMapper;
import com.kyx.biz.remind.model.Invitation;
import com.kyx.biz.remind.model.Remind;
import com.kyx.biz.remind.service.RemindService;

@Service("remindService")
public class RemindServiceImpl implements RemindService {
	@Resource
	private RemindMapper remindMapper;

	@Resource
	private OrdersMapper ordersMapper;

	@Resource
	private CarMapper carMapper;

	@Resource
	private CarMaintainMapper carMaintainMapper;

	@Resource
	private CarService carService;

	@Resource
	private OrdersServeMapper ordersServeMapper;

	@Override
	public GrdData queryOldReminds(Remind remind, Pager pager) {
		Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<Remind> list = remindMapper.selectReminds(remind);

		return new GrdData(page.getTotal(), list);
	}

	/**
	 * 查询邀约服务
	 */
	public GrdData queryInvitationServer(Remind remind) {
		List<Invitation> servers = ordersMapper.selectInvitationServer(
				remind.getStatus(), remind.getShopId());
		// servers = convertInvitationsServers(servers);

		return new GrdData(Long.valueOf(servers.size()), servers);
	}

	/**
	 * 服务列表转换
	 * 
	 * @param invitations
	 * @return
	 */
	private List<Invitation> convertInvitationsServers(
			List<Invitation> invitations) {
		List<Invitation> invitationServers = new ArrayList<Invitation>();

		for (Invitation invitation : invitations) {
			setParam(invitation);
			if (isInShowTime(invitation.getShowStartTime(),
					invitation.getShowEndTime())) {
				invitationServers.add(invitation);
			}
		}

		return invitationServers;
	}

	/**
	 * 是否在展示时间内
	 * 
	 * @param invitation
	 * @return
	 */
	private Boolean isInShowTime(Date beginDate, Date endDate) {
		if (beginDate == null || endDate == null) {
			return false;
		}
		Date nowDate = new Date();
		beginDate = AppUtils.getZeroTime(beginDate);
		endDate = AppUtils.getEndTime(endDate);

		return nowDate.after(beginDate) && nowDate.before(endDate);
	}

	private void setParam(Invitation invitation) {
		// 开始时间：到期时间-展示时间
		Date showStartTime = AppUtils.getDayByCycle(invitation.getDoneTime(),
				-BasicContant.INVITATION_SERVER_SHOW_DAY);
		invitation.setShowStartTime(showStartTime);

		// 结束时间：到期时间+服务周期+展示时间
		Integer cycel = invitation.getCycle().intValue();
		Integer showCycel = cycel + BasicContant.INVITATION_SERVER_SHOW_DAY;
		Date showEndTime = AppUtils.getDayByCycle(invitation.getDoneTime(),
				showCycel);
		invitation.setShowEndTime(showEndTime);

		// 邀约时间：上次服务时间+服务周期
		Date invitationTime = AppUtils.getDayByCycle(invitation.getDoneTime(),
				cycel);
		invitation.setInvitationTime(invitationTime);
	}

	@Override
	public GrdData queryInvitationCar(Remind remind) {
		List<Invitation> carList = queryInvitationCarList(remind);

		return new GrdData(Long.valueOf(carList.size()), carList);
	}

	/**
	 * 转换邀约车辆信息列表
	 * 
	 * @param carList
	 * @return
	 */
	private List<Invitation> convertInvitationCar(List<Invitation> carList,
			List<CarMaintain> carMaintains, Integer status) {
		carList = setCarsMaintain(carList, carMaintains);

		List<Invitation> invitationServers = new ArrayList<>();
		for (Invitation invitation : carList) {
			setInvitationCarParam(invitation);
			if (isInCarShowTime(invitation, status)) {
				invitationServers.add(invitation);
			}
		}

		return invitationServers;
	}

	/**
	 * 设置车辆保养时间
	 * 
	 * @param carList
	 * @param carMaintains
	 * @return
	 */
	private List<Invitation> setCarsMaintain(List<Invitation> carList,
			List<CarMaintain> carMaintains) {
		List<CarMaintain> tansferCarMaintains = carService
				.tansferCarMaintains(carMaintains);

		// 给车辆设置保养时间
		for (CarMaintain carMaintain : tansferCarMaintains) {
			int mainTainCarId = carMaintain.getCarId();
			Date carMainTain = carMaintain.getRemindTime();
			for (Invitation invitation : carList) {
				if (mainTainCarId == invitation.getId()) {
					invitation.setMaintainDate(carMainTain);
					invitation.setCarMainTainId(carMaintain.getId());
					invitation.setMaintainStatus(carMaintain.getStatus());
					break;
				}
			}
		}

		return carList;
	}

	/**
	 * 设置邀约车辆参数
	 * 
	 * @param invitation
	 */
	private void setInvitationCarParam(Invitation invitation) {
		// 车检开始时间
		Date checkDate = invitation.getCheckDate();
		Date showStartCheckDate = AppUtils.getDayByCycle(checkDate,
				-BasicContant.INVITATION_CAR_SHOW_DAY);
		invitation.setShowStartCheckDate(showStartCheckDate);
		// 结束时间
		Date showCheckDate = AppUtils.getDayByCycle(checkDate,
				BasicContant.INVITATION_CAR_SHOW_DAY);
		invitation.setShowEndCheckDate(showCheckDate);

		// 商业险开始时间
		Date commercialDate = invitation.getCommercialDate();
		Date showStartCommercialDate = AppUtils.getDayByCycle(commercialDate,
				-BasicContant.INVITATION_CAR_SHOW_DAY);
		invitation.setShowStartCommercialDate(showStartCommercialDate);
		// 结束时间
		Date showEndCommercialDate = AppUtils.getDayByCycle(commercialDate,
				BasicContant.INVITATION_CAR_SHOW_DAY);
		invitation.setShowEndCommercialDate(showEndCommercialDate);

		// 交强险开始时间
		Date compulsoryDate = invitation.getCompulsoryDate();
		Date showStartCompulsoryDate = AppUtils.getDayByCycle(compulsoryDate,
				-BasicContant.INVITATION_CAR_SHOW_DAY);
		invitation.setShowStartCompulsoryDate(showStartCompulsoryDate);
		// 结束时间
		Date showEndCompulsoryDate = AppUtils.getDayByCycle(compulsoryDate,
				BasicContant.INVITATION_CAR_SHOW_DAY);
		invitation.setShowEndCompulsoryDate(showEndCompulsoryDate);

		// 保养时间开始时间
		Date maintainDate = invitation.getMaintainDate();
		invitation.setShowStartMaintainDate(maintainDate);
		// 结束时间
		Date showEndMaintainDate = AppUtils.getDayByCycle(maintainDate,
				BasicContant.MAINTAIN_CAR_SHOW_DAY);
		invitation.setShowEndMaintainDate(showEndMaintainDate);
	}

	/**
	 * <pre>
	 * 在车辆展示时间内 
	 * 车检/交强险/商业险任一在展示时间内则展示
	 * </pre>
	 * 
	 * @param invitation
	 * @return
	 */
	private Boolean isInCarShowTime(Invitation invitation, Integer status) {
		boolean bool1 = isInShowTime(invitation.getShowStartCheckDate(),
				invitation.getShowEndCheckDate());
		bool1 = isShowTimeAndIsUnused(bool1, invitation.getCheckStatus(),
				status);
		invitation.setIsCheckDate(bool1);

		boolean bool2 = isInShowTime(invitation.getShowStartCommercialDate(),
				invitation.getShowEndCommercialDate());
		bool2 = isShowTimeAndIsUnused(bool2, invitation.getCommercialStatus(),
				status);
		invitation.setIsCommercialDate(bool2);

		boolean bool3 = isInShowTime(invitation.getShowStartCompulsoryDate(),
				invitation.getShowEndCompulsoryDate());
		bool3 = isShowTimeAndIsUnused(bool3, invitation.getCompulsoryStatus(),
				status);
		invitation.setIsCompulsoryDate(bool3);

		boolean bool4 = isInShowTime(invitation.getShowStartMaintainDate(),
				invitation.getShowEndMaintainDate());
		bool4 = isShowTimeAndIsUnused(bool4, invitation.getMaintainStatus(),
				status);
		invitation.setIsMaintainDate(bool4);

		return bool1 || bool2 || bool3 || bool4;
	}

	/**
	 * 年检/交强险/商业险在展示时间内，且状态为查询状态（邀请过/未邀请过）
	 * 
	 * @param isInShowTime
	 * @param used
	 * @return
	 */
	private boolean isShowTimeAndIsUnused(boolean isInShowTime, Integer used,
			Integer status) {
		if (used == null) {
			return false;
		}
		int usedInt = used.intValue();
		int statusInt = status.intValue();

		boolean isUnUsed = usedInt == statusInt ? true : false;

		return isInShowTime && isUnUsed;
	}

	@Override
	public RetInfo addRemind(Remind remind) {
		remind.setRemindStatus(BasicContant.UN_USED);
		remind.setRemindType(Remind.NORMAL_USING);

		return new RetInfo(remindMapper.insertSelective(remind) > 0, "添加提醒");
	}

	@Override
	public RetInfo carryOutServer(Integer id) {
		OrdersServe record = new OrdersServe();
		record.setId(id);
		record.setStatus(BasicContant.USED);

		return new RetInfo(
				ordersServeMapper.updateByPrimaryKeySelective(record) > 0,
				"完成服务提醒");
	}

	@Override
	public RetInfo carryOutCar(Invitation invitation) {

		boolean bool1 = true;
		if (invitation.getIsCheckDate() || invitation.getIsCommercialDate()
				|| invitation.getIsCompulsoryDate()) {
			Car car = setCarryOutCarParam(invitation);
			bool1 = carMapper.updateByPrimaryKeySelective(car) > 0;
		}

		boolean bool2 = true;
		if (invitation.getIsMaintainDate()) {
			bool2 = updateCarMaintain(invitation);
		}

		return new RetInfo(bool1 && bool2, "完成车辆提醒");
	}

	/**
	 * 更新车辆保养批次的状态
	 * 
	 * @param invitation
	 * @return
	 */
	private boolean updateCarMaintain(Invitation invitation) {
		CarMaintain car = new CarMaintain();
		car.setId(invitation.getCarMainTainId());
		car.setStatus(BasicContant.USED);

		return carMaintainMapper.updateByPrimaryKeySelective(car) > 0;
	}

	private Car setCarryOutCarParam(Invitation invitation) {
		Car car = new Car();
		car.setId(invitation.getId());
		Integer used = BasicContant.USED;

		if (invitation.getIsCheckDate()) {
			car.setCheckStatus(used);
		}

		if (invitation.getIsCommercialDate()) {
			car.setCommercialStatus(used);
		}

		if (invitation.getIsCompulsoryDate()) {
			car.setCompulsoryStatus(used);
		}

		return car;
	}

	@Override
	public int queryOldRemindsCount() {
		Remind remind = new Remind();
		remind.setOldDate(new Date());
		remind.setRemindType(Remind.NORMAL_USING);
		remind.setRemindStatus(BasicContant.UN_USED);

		int count = remindMapper.selectRemindsCount(remind);
		return count;
	}

	@Override
	public int queryInvitationCount(Integer shopId) {
		Remind remind = new Remind();
		remind.setStatus(BasicContant.UN_USED);
		remind.setShopId(shopId);
		GrdData grd = queryInvitationServer(remind);
		int count = grd.getCount();

		GrdData carGrd = queryInvitationCar(remind);
		int carCount = carGrd.getCount();

		int resultCount = count + carCount;
		return resultCount;
	}

	@Override
	public List<Invitation> queryInvitationCarList(Remind remind) {
		List<Invitation> carList = carMapper.selectInvitationCar(
				remind.getStatus(), remind.getShopId());

		if (carList.size() != 0) {
			List<CarMaintain> carMaintains = carMaintainMapper
					.selectCarMaintainByCarIds(carList, remind.getStatus());
			carList = convertInvitationCar(carList, carMaintains,
					remind.getStatus());
		}

		return carList;
	}

	@Override
	public RetInfo carryOutRemind(Integer id) {
		Remind remind = new Remind();
		remind.setId(id);
		remind.setRemindStatus(Remind.NORMAL_USING);

		boolean bool = remindMapper.updateByPrimaryKeySelective(remind) > 0;

		return new RetInfo(bool, "完成提醒");
	}

	@Override
	public Invitation queryInvitationServerById(Remind remind) {
		Invitation result = ordersMapper.selectInvitationServerById(
				Remind.INVITATION_STATUS, remind.getShopId(), remind.getId());

		String doneTimeStr = AppUtils.date2String1(result.getDoneTime());
		result.setDoneTimeStr(doneTimeStr);

		return result;
	}
}
