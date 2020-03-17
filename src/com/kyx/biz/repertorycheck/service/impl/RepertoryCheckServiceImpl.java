package com.kyx.biz.repertorycheck.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.repertorycheck.mapper.RepertoryCheckMapper;
import com.kyx.biz.repertorycheck.model.RepertoryCheck;
import com.kyx.biz.repertorycheck.service.RepertoryCheckService;
@Service("repertoryCheckService")
public class RepertoryCheckServiceImpl implements RepertoryCheckService {
	@Resource
    private RepertoryCheckMapper repertoryCheckMapper;
	@Override
	public GrdData getInfo(RepertoryCheck repertoryCheck, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<RepertoryCheck> list=repertoryCheckMapper.getInfo(repertoryCheck);
		return new GrdData(page.getTotal(),list);
	}
	@Override
	public RetInfo savePDData(RepertoryCheck repertoryCheck) {
		RetInfo retInfo;
		int count=0;
		if(repertoryCheck.getId()==null){//新增
			count=repertoryCheckMapper.insertSelective(repertoryCheck);
		}else{
			count=repertoryCheckMapper.updateByPrimaryKeySelective(repertoryCheck);
		}
		if(count>0)
			retInfo=new RetInfo("success","保存成功");
		else
			retInfo=new RetInfo("error","保存失败");
		return retInfo;
	}
	@Override
	public GrdData getAppInfo(RepertoryCheck repertoryCheck, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<RepertoryCheck> list=repertoryCheckMapper.getAppInfo(repertoryCheck);
		return new GrdData(page.getTotal(),list);
	}

}
