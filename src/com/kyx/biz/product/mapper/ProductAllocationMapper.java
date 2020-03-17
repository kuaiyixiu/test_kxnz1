package com.kyx.biz.product.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.product.model.ProductAllocation;
@Repository("productAllocationMapper")
public interface ProductAllocationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductAllocation record);

    int insertSelective(ProductAllocation record);

    ProductAllocation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductAllocation record);

    int updateByPrimaryKey(ProductAllocation record);

	List<ProductAllocation> getInfo(ProductAllocation productAllocation);
}