package com.kyx.biz.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.product.model.Product;

@Repository("productMapper")
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);


    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);
    
    Product selectByName(@Param("productName")String productName,@Param("shopId")Integer shopId);

    int updateByPrimaryKeySelective(Product record);

    List<Product> getProductList(Product record);
    
    List<Product> getProductCustomPrice(Product record);

	int getByClassId(Integer classId);

	List<Product> getInfo(Product product);

    /**
     * 根据门店和产品名称查询产品
     * @param product
     * @return
     */
	Product getByName(Product product);


	Integer getArertCount(Integer shopId);

    /**
     * 获取预警信息
     * @param product
     * @return
     */
	List<Product> getPreviewList(Product product);

    /**
     * 查询产品消耗报表
     * @param product
     * @return
     */
	List<Product> queryProductRep(Product product);
}