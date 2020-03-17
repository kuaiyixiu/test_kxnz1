package com.kyx.biz.orders.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.orders.model.OrdersProduct;

public interface OrdersProductService {

	/**
	 * 保存订单产品和提成
	 * 
	 * @param request
	 * @param ordersProducts
	 * @param orderId
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	RetInfo saveOrdersProductList(List<OrdersProduct> ordersProducts, Integer orderId) throws Exception;
	
	@Transactional
	RetInfo updateOrdersProduct(OrdersProduct ordersProduct) throws Exception;
	
	/**
	 * 删除产品
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	RetInfo delOrdersProduct(Integer ordersProductId) throws Exception;

	/**
	 * 根据ID查订单产品
	 * 
	 * @param request
	 * @param id
	 * @param shopId
	 * @return
	 */
	OrdersProduct queryById(Integer id, Integer shopId);

	/**
	 * 根据订单ID查产品列表
	 * 
	 * @param request
	 * @param orderId
	 * @return
	 */
	List<OrdersProduct> queryListByOrderId(Integer orderId);

	/**
	 * 查询产品
	 * 
	 * @param orderId
	 * @return
	 */
	GrdData queryProductsByOrderId(Integer orderId);
}
