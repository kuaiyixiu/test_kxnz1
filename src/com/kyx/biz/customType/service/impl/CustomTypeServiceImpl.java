package com.kyx.biz.customType.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.customType.mapper.CustomTypeMapper;
import com.kyx.biz.customType.model.CustomType;
import com.kyx.biz.customType.service.CustomTypeService;
import com.kyx.biz.paytype.model.PayType;

@Service("customTypeService")
public class CustomTypeServiceImpl implements CustomTypeService {
	@Resource
	private CustomTypeMapper customTypeMapper;

	@Override
	public List<CustomType> selectCustomTypes(CustomType customType) {

		return customTypeMapper.selectCustomTypes(customType);
	}

	@Override
	public GrdData queryAllCustomTypes(CustomType customType, Pager pager) {
		Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<CustomType> list = this.selectCustomTypes(customType);

		return new GrdData(page.getTotal(), list);
	}

	@Override
	public RetInfo addCustomType(CustomType customType) {
		customType.setTypeName(customType.getTypeName().trim());
		if (hasTypeName(customType.getTypeName())) {
			return RetInfo.Error("该会员类型名已存在");
		}

		return new RetInfo(customTypeMapper.insertSelective(customType) > 0,
				"添加会员类型");
	}

	@Override
	public RetInfo delCustomType(Integer id) {

		return new RetInfo(customTypeMapper.deleteByPrimaryKey(id) > 0,
				"删除会员类型");
	}

	@Override
	public CustomType queryCustomTypeById(Integer id) {

		return customTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public RetInfo updateCustomType(CustomType customType) {
		customType.setTypeName(customType.getTypeName().trim());
		if (hasTypeName(customType.getTypeName(), customType.getId())) {
			return RetInfo.Error("该会员类型名已存在");
		}

		return new RetInfo(
				customTypeMapper.updateByPrimaryKeySelective(customType) > 0,
				"更新会员类型");
	}

	/**
	 * 该类型名字是否存在
	 * 
	 * @param typeName
	 * @return
	 */
	private boolean hasTypeName(String typeName) {
		return customTypeMapper.selectByName(typeName).size() > 0;
	}

	/**
	 * 该类型名不存在，并且不是编辑的会员类型
	 * 
	 * @param typeName
	 * @param id
	 * @return
	 */
	private boolean hasTypeName(String typeName, Integer id) {

		return customTypeMapper.selectByNameAndNotId(typeName, id).size() > 0;
	}

	@Override
	public List<CustomType> getByShopId(Integer shopId) {
		CustomType customType = new CustomType();
		customType.setShopId(shopId);
		return this.selectCustomTypes(customType);
	}

	@Override
	public CustomType selectByPrimaryKey(Integer id) {
		return customTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public Map<Integer, String> getCustomTypeMap(Integer shopId) {
		CustomType type = new CustomType();
		type.setShopId(shopId);
        List<CustomType> customTypes = customTypeMapper.selectCustomTypes(type);
		Map<Integer, String> typeMap = new HashMap<Integer, String>();
		if (!ObjectUtils.isEmpty(customTypes)){
			for (CustomType pay : customTypes){
				typeMap.put(pay.getId(), pay.getTypeName());
			}
		}
		return typeMap;
			
	}
}
