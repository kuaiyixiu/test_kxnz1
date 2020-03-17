package com.kyx.biz.remind.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.remind.model.Invitation;
import com.kyx.biz.remind.model.Remind;

/**
 * 车辆信息
 * 
 * @author daibin
 * @date 2019-4-15 上午9:50:03
 * 
 */
public interface RemindService {

	/**
	 * 查询所有车辆信息，会员/非会员
	 * 
	 * @param car
	 * @return
	 */
	GrdData queryOldReminds(Remind remind, Pager pager);

	/**
	 * 查询邀约服务信息
	 * 
	 * @param remind
	 * @return
	 */
	GrdData queryInvitationServer(Remind remind);

	/**
	 * 查询邀约车辆信息
	 * 
	 * @param shopId
	 * @return
	 */
	GrdData queryInvitationCar(Remind remind);

	/**
	 * 添加提醒
	 * 
	 * @param remind
	 * @return
	 */
	@Transactional
	RetInfo addRemind(Remind remind);

	/**
	 * 完成服务提醒
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	RetInfo carryOutServer(Integer id);

	/**
	 * 完成车辆提醒
	 * 
	 * @param invitation
	 * @return
	 */
	@Transactional
	RetInfo carryOutCar(Invitation invitation);

	/**
	 * 查询今日提醒数量
	 * 
	 * @return
	 */
	int queryOldRemindsCount();

	/**
	 * 查询邀约数量
	 * 
	 * @param shopId
	 * @return
	 */
	int queryInvitationCount(Integer shopId);

	/**
	 * 查询车辆邀约列表
	 * 
	 * @param remind
	 * @return
	 */
	List<Invitation> queryInvitationCarList(Remind remind);

	/**
	 * 完成提醒
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	RetInfo carryOutRemind(Integer id);

	/**
	 * 按照id查询邀约服务
	 * 
	 * @param remind
	 * @return
	 */
	Invitation queryInvitationServerById(Remind remind);
}
