package com.kyx.biz.orders.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.orders.model.OrdersServe;

public interface OrdersServeService {

	/**
	 * 保存订单服务和提成
	 * 
	 * @param request
	 * @param ordersServes
	 * @param orderId
	 * @return
	 */
	@Transactional
	RetInfo saveOrdersServeList(List<OrdersServe> ordersServes, Integer orderId);
	
	
	/**
	 * 删除服务
	 * @param ordersServeId
	 * @return
	 */
	@Transactional
	RetInfo delOrdersServe(Integer ordersServeId);
	
	
	@Transactional
	RetInfo updateOrdersServe(OrdersServe ordersServe) throws Exception;
	

	/**
	 * 根据ID查订单服务
	 * 
	 * @param request
	 * @param id
	 * @param shopId
	 * @return
	 */
	OrdersServe queryById(HttpServletRequest request, Integer id, Integer shopId);

	/**
	 * 根据订单ID查服务列表
	 * 
	 * @param request
	 * @param orderId
	 * @return
	 */
	List<OrdersServe> queryListByOrderId(Integer orderId);

	/**
	 * 查询服务
	 * 
	 * @param orderId
	 * @return
	 */
	GrdData queryServesByOrderId(Integer orderId);
	
	
}
