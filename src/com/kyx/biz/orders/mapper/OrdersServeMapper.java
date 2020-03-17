package com.kyx.biz.orders.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.orders.model.OrdersServe;

@Repository("ordersServeMapper")
public interface OrdersServeMapper {
    
	int deleteByOrderId(Integer orderId);
	
	int deleteByPrimaryKey(Integer id);

    int insertSelective(OrdersServe record);

    OrdersServe selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrdersServe record);

	int insertBatches(@Param("list") List<OrdersServe> ordersServes);
	
	List<OrdersServe> queryListByOrderId(Integer orderId);
	
	BigDecimal querySumPrice(Integer orderId);
	
}