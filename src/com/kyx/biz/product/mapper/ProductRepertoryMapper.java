package com.kyx.biz.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.orders.model.OrdersServe;
import com.kyx.biz.product.model.ProductRepertory;

@Repository("productRepertoryMapper")
public interface ProductRepertoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(ProductRepertory record);

    ProductRepertory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductRepertory record);

	List<ProductRepertory> selectByRepertoryId(@Param("repertoryId")Integer repertoryId, @Param("kind")Integer kind);

	List<ProductRepertory> getInfo(ProductRepertory productRepertory);

	int updateAvailable(@Param("available")Integer available);
	
	/**
	 * 查询列表
	 * @return
	 */
	List<ProductRepertory> getList(ProductRepertory productRepertory);

}