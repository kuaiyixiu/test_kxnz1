package com.kyx.biz.wechatpublic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.wechatpublic.mapper.ImgResourceMapper;
import com.kyx.biz.wechatpublic.model.ImgResource;
import com.kyx.biz.wechatpublic.service.ImgResourceService;
@Service("imgResourceService")
public class ImgResourceServiceImpl implements ImgResourceService {
	@Resource
	private ImgResourceMapper imgResourceMapper;

	@Override
	public GrdData getImgListByPage(ImgResource imgResource, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<ImgResource> list=imgResourceMapper.getInfo(imgResource);
		GrdData grdData=new GrdData(page.getTotal(),list);
		pager.setTotal(page.getTotal());
		grdData.setPager(pager);
		return grdData;
	}

	@Override
	public RetInfo addImg(ImgResource img) {
		RetInfo ret=RetInfo.Error("新增失败");
		int count=imgResourceMapper.insertSelective(img);
		if(count>0)
			ret=RetInfo.Success("新增成功");
		return ret;
	}

	@Override
	public ImgResource getImg(Integer id) {
		return imgResourceMapper.selectByPrimaryKey(id);
	}

	@Override
	public int delImg(Integer id) {
		
		return imgResourceMapper.deleteByPrimaryKey(id);
	}

}
