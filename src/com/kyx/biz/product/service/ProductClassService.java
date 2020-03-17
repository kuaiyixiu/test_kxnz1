package com.kyx.biz.product.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.product.model.ProductClass;

public interface ProductClassService {
    /**
     * 查询列表
     * @param productClass
     * @param pager
     * @return
     */
	GrdData getInfo(ProductClass productClass, Pager pager);
    /**
     * 保存数据
     * @param productClass
     * @return
     */
	@Transactional
	RetInfo saveData(ProductClass productClass);
	/**
	 * 删除数据
	 * @param ids
	 * @return
	 */
	@Transactional
	RetInfo delData(String ids);
	/**
	 * 根據主鍵查詢
	 * @param id
	 * @return
	 */
	ProductClass getById(Integer id);
	/**
	 * 根据门店信息查询
	 * @param session
	 * @return
	 */
	List<ProductClass> getAll(HttpSession session);


}
