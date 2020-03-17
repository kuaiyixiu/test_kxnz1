package com.kyx.biz.wechat.service;

import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.remind.model.Invitation;
import com.kyx.biz.wechat.vo.TemplateDataVo;
import com.kyx.biz.wechat.vo.WechartSendVo;

public interface WechatSendService {
	// @Transactional(rollbackFor=Exception.class)
	RetInfo sendInfo(WechartSendVo sendVo) throws Exception;

	/**
	 * 电脑端发送接口，发送后切换为主库
	 * 
	 * @param sendVo
	 * @return
	 * @throws Exception
	 */
	RetInfo pcSendInfo(WechartSendVo sendVo, Integer shopId) throws Exception;

	/**
	 * 发送预约消息
	 * 
	 * @param tplVo
	 * @param cardNo
	 * @param shopId
	 * @return
	 * @throws Exception
	 */
	RetInfo sendAppointInfo(TemplateDataVo tplVo, String cardNo, Integer shopId)
			throws Exception;

	/**
	 * 发送订单消费推送
	 * 
	 * @return
	 * @throws Exceptio
	 */
	RetInfo sendOrderConsume(Integer orderId, Shops shops) throws Exception;

	/**
	 * 发送车辆邀约
	 * 
	 * @param invitation
	 * @param shopId
	 * @return
	 * @throws Exception
	 */
	RetInfo sendCarInvitation(Invitation invitation, Integer shopId)
			throws Exception;

	/**
	 * 发送服务邀约
	 * 
	 * @param tplVo
	 * @param shopId
	 * @param cardNo
	 * @return
	 * @throws Exception
	 */
	RetInfo sendServer(TemplateDataVo tplVo, Integer shopId, String cardNo)
			throws Exception;

	/**
	 * 发送年检邀约
	 * 
	 * @param invitation
	 * @return
	 */
	RetInfo sendCarCheck(Invitation invitation, Integer shopId) throws Exception;

	/**
	 * 发送保养邀约
	 * 
	 * @param invitation
	 * @return
	 */
	RetInfo sendMaintain(Invitation invitation,Integer shopId) throws Exception;

}
