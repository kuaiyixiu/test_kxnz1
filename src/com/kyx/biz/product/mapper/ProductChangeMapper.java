package com.kyx.biz.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.product.model.Product;
import com.kyx.biz.product.model.ProductChange;
@Repository("productChangeMapper")
public interface ProductChangeMapper {
	int deleteByPrimaryKey(Integer id);
    int insert(ProductChange record);
    int insertSelective(ProductChange record);

    ProductChange selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(ProductChange record);
    int updateByPrimaryKey(ProductChange record);
	int insertBatches(@Param("list") List<ProductChange> productChanges);
	
	List<Product> getInfo(ProductChange productChange);
}