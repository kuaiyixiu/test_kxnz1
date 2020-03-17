package com.kyx.biz.orders.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.orders.model.OrdersRoyalty;

@Repository("ordersRoyaltyMapper")
public interface OrdersRoyaltyMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(OrdersRoyalty record);

    OrdersRoyalty selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrdersRoyalty record);
    
    /**
     * 根据订单ID和king删除提成， kind可为null，即删除全部订单的提成
     * @param orderId
     * @param kind
     * @return
     */
    int deleteByOrderIdKind(@Param("orderId")Integer orderId,@Param("kind")Integer kind); 
    
    
    int deleteByDtId(@Param("ordersDtId")Integer ordersDtId,@Param("kind")Integer kind);
    
	int insertBatches(@Param("list") List<OrdersRoyalty> ordersRoyalties);
	
	List<OrdersRoyalty> getRoyalList(OrdersRoyalty ordersRoyalty);
	
	/**
	 * 查询员工提成概览
	 * @param ordersRoyalty
	 * @return
	 */
	List<OrdersRoyalty> getUserRoyalList(OrdersRoyalty ordersRoyalty);
	
	
	
	/**
	 * 根据用户编号，查询对应员工的服务提成
	 * @param ordersRoyalty
	 * @return
	 */
	List<OrdersRoyalty> queryUserServeRoy(OrdersRoyalty ordersRoyalty);
	
	
	/**
	 * 根据用户编号，查询对应员工的产品提成
	 * @param ordersRoyalty
	 * @return
	 */
	List<OrdersRoyalty> queryUserProductRoy(OrdersRoyalty ordersRoyalty);
	
	/**
	 * 查询服务列表 根据订单状态
	 * @param ordersRoyalty
	 * @return
	 */
	List<OrdersRoyalty> queryServeRoyList(OrdersRoyalty ordersRoyalty);
	
	/**
	 * 查询服务产品列表 根据订单状态
	 * @param ordersRoyalty
	 * @return
	 */
	List<OrdersRoyalty> queryProductRoyList(OrdersRoyalty ordersRoyalty);
	
	/**
	 * 查询产品提成报表
	 * @return
	 */
	List<OrdersRoyalty> getOrdersRoyaltyReport(@Param("shopId")Integer shopId,@Param("dateRangeStartTime")Date dateRangeStartTime,@Param("dateRangeEndTime")Date dateRangeEndTime);

}