package com.kyx.biz.product.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kyx.biz.product.model.ProductClass;
import com.kyx.biz.report.vo.ReportQueryVo;

@Repository("productClassMapper")
public interface ProductClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductClass record);

    int insertSelective(ProductClass record);

    ProductClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductClass record);

    int updateByPrimaryKey(ProductClass record);

	List<ProductClass> getInfo(ProductClass productClass);

	List<ProductClass> getAll(Integer shopId);
    /**
     * 根据shopid 和name查询有无重复类别
     * @param productClass
     * @return
     */
	int getByName(ProductClass productClass);

	ProductClass getInfoByName(ProductClass productClass);

	List<Map> queryProductRep(ReportQueryVo reportQueryVo);
}