package com.kyx.biz.orders.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.orders.model.OrdersProduct;

@Repository("ordersProductMapper")
public interface OrdersProductMapper {
	
	int deleteByOrderId(Integer orderId);
	
    int deleteByPrimaryKey(Integer id);

    int insertSelective(OrdersProduct record);

    OrdersProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrdersProduct record);

	int insertBatches(@Param("list") List<OrdersProduct> ordersProducts);
	
	List<OrdersProduct> queryListByOrderId(Integer orderId);
	
	BigDecimal querySumPrice(Integer orderId);
}