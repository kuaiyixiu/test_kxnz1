package com.kyx.biz.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.product.model.ProductConsume;

@Repository("productConsumeMapper")
public interface ProductConsumeMapper {
    int deleteByPrimaryKey(Integer id);
    
    int deleteByOrderId(@Param("orderId")Integer orderId,@Param("kind")Integer kind);

    int insertSelective(ProductConsume record);

    ProductConsume selectByPrimaryKey(Integer id);
    
    ProductConsume selecyByOrder(@Param("orderId")Integer orderId,@Param("productId")Integer productId,@Param("productRepertoryId")Integer productRepertoryId);
    
    List<ProductConsume> selecyListByOrder(@Param("orderId")Integer orderId,@Param("productId")Integer productId);

    int updateByPrimaryKeySelective(ProductConsume record);

    int insertBatches(@Param("list") List<ProductConsume> productConsumes);
    
    /**
     * kind 单据种类 1.订单产生 2调拨单产生
     */
    List<ProductConsume> getList(ProductConsume productConsume);
    
    /**
     * 连表查询库存表，取产品进价
     * @return
     */
    List<ProductConsume> getPriceList(ProductConsume productConsume);
}