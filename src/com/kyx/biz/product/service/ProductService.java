package com.kyx.biz.product.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.product.model.Product;

public interface ProductService {
    
	/**
	 * 根據主鍵查詢
	 * @param id
	 * @return
	 */
	Product getById(Integer id);

	
	/**
	 * 根据条件查询列表
	 * @param serve
	 * @return
	 */
	List<Product> getProductList(Product product);
	
	/**
	 * 获取产品会员价格
	 * @param record
	 * @return
	 */
    List<Product> getProductCustomPrice(Product record);

    /**
     * 根据分类查询产品数量
     * @param classId
     * @return
     */
	int getByClassId(Integer classId);

    /**
     * 分页查询
     * @param product
     * @param pager
     * @return
     */
	GrdData getInfo(Product product, Pager pager);
    /**
     * 存档
     * @param product
     * @return
     */
    @Transactional
	RetInfo saveData(Product product);
    /**
     * 删除
     * @param ids
     * @return
     */
    @Transactional
	RetInfo delData(String ids);

    /**
     * 校验当前门店产品在另一个门店系统中是否拥有同名的产品
     * @param product
     * @return
     */
	RetInfo checkExist(Product product);
	
	/**
	 * 根据shopId和名称用equal查询是否有该产品 
	 * @param productName
	 * @param shopId
	 * @return
	 */
	Product selectByName(String productName,Integer shopId);

    /**
     * 返回当前门店产品库存报警数量
     * @param shopId
     * @return
     */
	Integer getArertCount(Integer shopId);

    /**
     * 获取预警的产品列表
     * @param product
     * @param pager
     * @return
     */
	GrdData getPreviewList(Product product, Pager pager);
}
