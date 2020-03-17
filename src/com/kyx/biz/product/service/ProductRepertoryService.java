package com.kyx.biz.product.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.orders.model.Orders;
import com.kyx.biz.product.model.ProductRepertory;

public interface ProductRepertoryService {

	GrdData getInfo(ProductRepertory productRepertory, Pager pager);
    /**
     * 校验库存产品数量
     * @param id
     * @param count
     * @return
     */
	RetInfo chkDtData(Integer id, Integer count);
	/**
	 * 根据产品信息查询所有有库存的产品批次信息
	 * @param productId
	 * @return
	 */
	List<ProductRepertory> getByProductId(Integer productId);
	
	
	/**
	 *	使用产品 
	 */
//	String useProduct(Integer productId,Integer quantity,Orders orders);
	
	String useProduct(Integer productId,Integer quantity,Integer orderId) throws Exception;
	
	/**
	 * 退还产品
	 * @throws Exception 
	 */
	String retrunProduct(Integer ordersProductId,Integer returnQuantity) throws Exception;

	String updateProduct(Integer ordersProductId,Integer newQuantity)throws Exception;
	
	/**
	 * 退回产品
	 */
//	String returnProduct(Orders orders);
	
	
	/**
	 * 更新订单金额
	 * @param orderId
	 */
	@Transactional
	void updateOrderAmt(Integer orderId);
}
