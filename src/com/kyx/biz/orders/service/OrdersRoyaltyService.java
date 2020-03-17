package com.kyx.biz.orders.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.biz.orders.model.OrdersRoyalty;

public interface OrdersRoyaltyService {
	
	GrdData  getRoyalList(OrdersRoyalty ordersRoyalty, Pager pager);

	GrdData getUserRoyalList(OrdersRoyalty ordersRoyalty, Pager pager);
	
	/**
	 * 根据用户编号，查询对应员工的服务提成
	 * @param ordersRoyalty
	 * @return
	 */
	GrdData queryUserServeRoy(OrdersRoyalty ordersRoyalty, Pager pager);
	
	
	/**
	 * 根据用户编号，查询对应员工的产品提成
	 * @param ordersRoyalty
	 * @return
	 */
	GrdData queryUserProductRoy(OrdersRoyalty ordersRoyalty, Pager pager);
	
	/**
	 * 查询服务列表 根据订单状态
	 * @param ordersRoyalty
	 * @return
	 */
	GrdData queryServeRoyList(OrdersRoyalty ordersRoyalty, Pager pager);
	
	/**
	 * 查询服务产品列表 根据订单状态
	 * @param ordersRoyalty
	 * @return
	 */
	GrdData queryProductRoyList(OrdersRoyalty ordersRoyalty, Pager pager);
	

}
