package com.kyx.biz.orders.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.orders.model.Orders;
import com.kyx.biz.remind.model.Invitation;

@Repository("ordersMapper")
public interface OrdersMapper {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(Orders record);

	Orders selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Orders record);

	/**
	 * 查询订单列表
	 * 
	 * @param orders
	 * @return
	 */
	List<Orders> getOrdersList(Orders orders);
	
	/**
	 * 查询订单列表(带客户名称)
	 * 
	 * @param orders
	 * @return
	 */
	List<Orders> getOrdersCustList(Orders orders);

	/**
	 * 查询客户订单
	 * 
	 * @param orders
	 * @return
	 */
	List<Orders> selectCustomOrders(Orders orders);

	/**
	 * 查询消费订单记录
	 * 
	 * @param orders
	 * @return
	 */
	List<Orders> selectConsumeOrders(Orders orders);

	/**
	 * 查询邀约服务
	 * 
	 * @param status
	 * @return
	 */
	List<Invitation> selectInvitationServer(@Param("status") Integer status,
			@Param("shopId") Integer shopId);

	/**
	 * 查询订单列表，和用户名关联
	 * 
	 * @param orders
	 * @return
	 */
	List<Orders> selectOrdersList(Orders orders);

	/**
	 * 通过车牌号查询最近一次订单
	 * 
	 * @param carNumber
	 * @return
	 */
	Orders selectByCarNumber(String carNumber);

	/**
	 * 根据创建时间查询开单数
	 * 
	 * @param time
	 * @param shopId
	 * @param orderStatus
	 * @return
	 */
	Integer getCountsByCreatTime(@Param(value = "time") Date time,@Param(value = "shopId") Integer shopId);

	/**
	 * 获取入账订单金额
	 * 
	 * @param dateRangeStartTime
	 * @param dateRangeEndTime
	 * @return
	 */
	BigDecimal getFinishOrderAmt(@Param("shopId") Integer shopId,
			@Param("dateRangeStartTime") Date dateRangeStartTime,
			@Param("dateRangeEndTime") Date dateRangeEndTime);

	/**
	 * 按照id查询一条邀约服务
	 * 
	 * @param status
	 * @param shopId
	 * @param id
	 * @return
	 */
	Invitation selectInvitationServerById(@Param("status") Integer status,
			@Param("shopId") Integer shopId, @Param("id") Integer id);
	
	/**
	 * 获取汽车消费报表
	 * @param shopId
	 * @param dateRangeStartTime
	 * @param dateRangeEndTime
	 * @return
	 */
	List<Orders> getCarConsumeReport(@Param("shopId")Integer shopId,@Param("dateRangeStartTime")Date dateRangeStartTime,@Param("dateRangeEndTime")Date dateRangeEndTime);
	
}