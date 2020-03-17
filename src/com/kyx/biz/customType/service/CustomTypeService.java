package com.kyx.biz.customType.service;

import java.util.List;
import java.util.Map;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.customType.model.CustomType;

public interface CustomTypeService {

	/**
	 * 查询所有会员类型
	 * 
	 * @return
	 */
	List<CustomType> selectCustomTypes(CustomType customType);

	/**
	 * 查询所有会员类型
	 * 
	 * @param customType
	 * @param pager
	 * @return
	 */
	GrdData queryAllCustomTypes(CustomType customType, Pager pager);

	/**
	 * 添加会员类型
	 * 
	 * @param customType
	 * @return
	 */
	RetInfo addCustomType(CustomType customType);

	/**
	 * 删除会员类型
	 * 
	 * @param id
	 * @return
	 */
	RetInfo delCustomType(Integer id);

	/**
	 * 按照id查看会员类型
	 * 
	 * @param id
	 * @return
	 */
	CustomType queryCustomTypeById(Integer id);

	/**
	 * 更新会员类型
	 * 
	 * @param customType
	 * @return
	 */
	RetInfo updateCustomType(CustomType customType);

	/**
	 * 根据门店信息查询会员类型列表
	 * 
	 * @param shopId
	 * @return
	 */
	List<CustomType> getByShopId(Integer shopId);

	/**
	 * 按照id去查询会员类型
	 * 
	 * @param id
	 * @return
	 */
	CustomType selectByPrimaryKey(Integer id);
	
	Map<Integer, String> getCustomTypeMap(Integer shopId);
}
