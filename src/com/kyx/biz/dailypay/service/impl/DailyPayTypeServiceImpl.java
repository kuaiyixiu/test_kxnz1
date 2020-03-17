package com.kyx.biz.dailypay.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.user.model.User;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.dailypay.mapper.DailyPayRecordMapper;
import com.kyx.biz.dailypay.mapper.DailyPayTypeMapper;
import com.kyx.biz.dailypay.model.DailyPayType;
import com.kyx.biz.dailypay.service.DailyPayTypeService;

@Service("dailyPayTypeService")
public class DailyPayTypeServiceImpl implements DailyPayTypeService {
	
	@Resource
	private DailyPayTypeMapper dailyPayTypeMapper;
	
	@Resource
	private DailyPayRecordMapper dailyPayRecordMapper;

	@Override
	public GrdData getList(DailyPayType dailyPayType, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<DailyPayType> list = dailyPayTypeMapper.getList(dailyPayType);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public RetInfo saveType(DailyPayType dailyPayType) {
		dailyPayTypeMapper.insertSelective(dailyPayType);
		return RetInfo.Success("收支类型保存成功");
	}

	@Override
	public RetInfo updateType( DailyPayType dailyPayType) {
		if (dailyPayType.getId() == null){
			return RetInfo.Error("类型编号不能为空");
		}
		if (dailyPayTypeMapper.updateByPrimaryKeySelective( dailyPayType) > 0 ){
			return RetInfo.Success("收支类型修改成功");
		}else {
			return RetInfo.Success("收支类型修改失败");
		}
	}

	@Override
	public RetInfo deleteType(Integer id) {
		if (dailyPayRecordMapper.selectCountByPayTypeId(id) > 0 ){
			return RetInfo.Error("日常收支流水包含此类型，不能删除");
		}
		if (id == null){
			return RetInfo.Success("类型编号不能为空");
		}
		if (dailyPayTypeMapper.deleteByPrimaryKey(id) > 0 ){
			return RetInfo.Success("收支类型删除成功");
		}else{
			return RetInfo.Success("收支类型删除失败");
			
		}
	}

	@Override
	public DailyPayType queryById(Integer id) {
		return dailyPayTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<DailyPayType> getTypeList(DailyPayType dailyPayType) {
		return  dailyPayTypeMapper.getList(dailyPayType);
	}

	@Override
	public Map<Integer, String> getPayTypeMap(Integer shopId) {
		List<DailyPayType> types =  getByShopId(shopId);
		Map<Integer, String> typeMap = new HashMap<Integer, String>();
		if (!ObjectUtils.isEmpty(types)){
			for (DailyPayType u : types){
				typeMap.put(u.getId(), u.getTypeName());
			}
		}
		return typeMap;
	}

	@Override
	public List<DailyPayType> getByShopId(Integer shopId) {
		DailyPayType type=new DailyPayType();
		type.setShopId(shopId);
		return dailyPayTypeMapper.getList(type);
	}

}
