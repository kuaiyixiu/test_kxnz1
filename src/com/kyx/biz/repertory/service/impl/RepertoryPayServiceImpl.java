package com.kyx.biz.repertory.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.repertory.mapper.RepertoryPayMapper;
import com.kyx.biz.repertory.model.RepertoryPay;
import com.kyx.biz.repertory.service.RepertoryPayService;
@Service("repertoryPayService")
public class RepertoryPayServiceImpl implements RepertoryPayService {
	@Resource
	private RepertoryPayMapper repertoryPayMapper;

	@Override
	public GrdData getInfo(RepertoryPay repertoryPay, Pager pager) {
		//Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<RepertoryPay> list=repertoryPayMapper.getInfo(repertoryPay);
		return new GrdData(Long.valueOf(list.size()),list);
	}

	@Override
	public RetInfo saveData(RepertoryPay repertoryPay) {
		RetInfo retInfo;
		int count=0;
		if(repertoryPay.getId()!=null){
			count=repertoryPayMapper.updateByPrimaryKeySelective(repertoryPay);
		}else{
			repertoryPay.setAddTime(new Date());
			count=repertoryPayMapper.insertSelective(repertoryPay);
		}
		if(count>0){
			retInfo=new RetInfo("success","添加成功");
		}else
			retInfo=new RetInfo("error","添加失败");
		return retInfo;
	}

	@Override
	public RetInfo delData(Integer ids) {
		RetInfo retInfo;
		int count=repertoryPayMapper.deleteByPrimaryKey(ids);
		if(count>0)
			retInfo=new RetInfo("success","删除成功");
		else
			retInfo=new RetInfo("error","删除失败");
		return retInfo;
	}



}
