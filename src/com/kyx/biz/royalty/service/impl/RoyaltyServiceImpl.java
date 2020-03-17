package com.kyx.biz.royalty.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.royalty.mapper.RoyaltyMapper;
import com.kyx.biz.royalty.model.Royalty;
import com.kyx.biz.royalty.service.RoyaltyService;
@Service("royaltyService")
public class RoyaltyServiceImpl implements RoyaltyService {
	@Resource
	private RoyaltyMapper royaltyMapper; 

	@Override
	public GrdData getInfo(Royalty royalty, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<Royalty> list;
		if("3".equals(royalty.getKind()))
			list=royaltyMapper.getProInfo(royalty);
		else
			list=royaltyMapper.getInfo(royalty);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public Royalty getById(Integer id) {
		return royaltyMapper.selectByPrimaryKey(id);
	}

	@Override
	public RetInfo saveData(Royalty ro, String ids,HttpSession session, String statue) {
		RetInfo retInfo;
		//先删除提成配置
		Shops shops=(Shops) session.getAttribute("shopsSession");
		if("add".equals(statue)){
			Map map=new HashMap();
			map.put("shopId", shops.getId());
			String[] arr=ids.split(";");
			List<Integer> list=new ArrayList<Integer>();
			for(int i=0;i<arr.length;i++){ 
				list.add(Integer.parseInt(arr[i]));
				}
			map.put("list", list);
			map.put("kind", ro.getKind());
			royaltyMapper.doRemoveeMore(map);
		}else{//修改
			//能否查询到
			Integer id=Integer.valueOf(ids.split(";")[0]);
			ro.setRoyaltyId(id);
			Royalty r=royaltyMapper.getByRoyaltyId(ro);
			if(r!=null)
				royaltyMapper.deleteByPrimaryKey(r.getId());
		}
		String[] idArr=ids.split(";");
		List<Royalty> list=new ArrayList<Royalty>();
		for(String sid:idArr){
			if(StringUtils.isNotEmpty(sid)){
				Royalty royalty=new Royalty();
				royalty.setKind(ro.getKind());
				royalty.setRoyaltyId(Integer.valueOf(sid));
				royalty.setRoyaltyType(ro.getRoyaltyType());
				royalty.setRoyaltyCount(ro.getRoyaltyCount());
				royalty.setShopId(shops.getId());
				list.add(royalty);
			}
		}
		int count=royaltyMapper.insertBatches(list);
		if(count==list.size())
			retInfo=new RetInfo("success","保存成功");
		else
			retInfo=new RetInfo("error","保存失败");
		return retInfo;
	}

	@Override
	public Royalty getByRoyaltyId(Royalty ro) {
		Royalty royalty=royaltyMapper.getByRoyaltyId(ro);
		if(royalty==null){
			royalty=new Royalty();
			royalty.setRoyaltyCount(new BigDecimal(0.00));	
		}
			
		return royalty;
	}

	@Override
	public List<Royalty> getList(Royalty royalty) {
		return royaltyMapper.getList(royalty);
	}

}
