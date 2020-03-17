package com.kyx.biz.supply.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.supply.mapper.SupplyMapper;
import com.kyx.biz.supply.model.Supply;
import com.kyx.biz.supply.service.SupplyService;
@Service("supplyService")
public class SupplyServiceImpl implements SupplyService {
	@Resource
    private SupplyMapper supplyMapper;
	@Override
	public GrdData getInfo(Supply supply, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<Supply> list=supplyMapper.getInfo(supply);
		return new GrdData(page.getTotal(),list);
	}
	@Override
	public Supply getById(Integer id) {
		return supplyMapper.selectByPrimaryKey(id);
	}
	@Override
	public RetInfo saveData(Supply supply) {
		RetInfo retInfo;
		int count=0;
		if(supply.getEnabled()==null)
			supply.setEnabled(0);
		if(supply.getId()==null){//新增
			count=supplyMapper.insertSelective(supply);
		}else{
			count=supplyMapper.updateByPrimaryKeySelective(supply);
		}
		if(count>0)
			retInfo=new RetInfo("success","保存成功");
		else
			retInfo=new RetInfo("error","保存失败");
		return retInfo;
	}
	@Override
	public RetInfo delData(String ids) {
		RetInfo retInfo;
		String[] idArr=ids.split(";");
		//校验是否被使用
		Boolean canDel=true;
//		for(int i=0;i<idArr.length;i++){
//			int count=serveService.getByClassId(Integer.valueOf(idArr[i]));
//			if(count>0){
//				canDel=false;
//				break;
//			}
//		}
		if(!canDel){
			retInfo=new RetInfo("error","删除失败，存在已使用的数据！");
			return retInfo;
		}
		int count=0;
		for(int i=0;i<idArr.length;i++){
			count=count+supplyMapper.deleteByPrimaryKey(Integer.valueOf(idArr[i]));
		}
		if(count==idArr.length)
			retInfo=new RetInfo("success","删除成功");
		else
			retInfo=new RetInfo("error","删除失败");
		return retInfo;
	}
	@Override
	public List<Supply> getInfo(Supply supply) {
		
		return supplyMapper.getInfo(supply);
	}

}
