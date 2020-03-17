package com.kyx.biz.customprice.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.customprice.mapper.CustomPriceMapper;
import com.kyx.biz.customprice.model.CustomPrice;
import com.kyx.biz.customprice.service.CustomPriceService;
@Service("customPriceService")
public class CustomPriceServiceImpl implements CustomPriceService {
	@Resource
	private CustomPriceMapper customPriceMapper;

	@Override
	public GrdData getInfo(CustomPrice customPrice, Pager pager) {
		if(pager!=null){
			Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
			List<CustomPrice> list=customPriceMapper.getInfo(customPrice);
			return new GrdData(page.getTotal(),list);
		}else{
			List<CustomPrice> list=new ArrayList<CustomPrice>();
			if(customPrice.getCustType()!=null&&customPrice.getClassId()!=null){
				list=customPriceMapper.getInfo(customPrice);
			}
			return new GrdData(Long.valueOf(list.size()),list);
		}

	}

	@Override
	public RetInfo saveData(CustomPrice customPrice) {
		RetInfo retInfo;
		Integer id=customPrice.getId();
		int count=0;
		if(id==null){//存档
			count=customPriceMapper.insertSelective(customPrice);
		}else{//修改
			count=customPriceMapper.updateByPrimaryKeySelective(customPrice);
		}
		if(count>0)
			retInfo=new RetInfo("success","保存成功");
		else
			retInfo=new RetInfo("error","保存失败");
		return retInfo;
	}

	@Override
	public RetInfo saveBatchData(CustomPrice customPrice) {
		RetInfo retInfo=RetInfo.Success("保存成功");
		String ids=customPrice.getIds();
		JSONArray jsonArray=JSONArray.parseArray(ids);
		for(int i=0;i<jsonArray.size();i++){
			CustomPrice cp=jsonArray.getObject(i, CustomPrice.class);
			CustomPrice addObject=new CustomPrice();
			BeanUtils.copyProperties(customPrice, addObject);
			addObject.setId(cp.getId());
			addObject.setItemId(cp.getItemId());
			addObject.setPrice(new BigDecimal(customPrice.getZc().doubleValue()*cp.getOldprice().doubleValue()*0.01));
			retInfo=saveData(addObject);
			if(retInfo.getRetCode()==retInfo.ERROR){
				break;
			}
		}
		return retInfo;
	}

}
