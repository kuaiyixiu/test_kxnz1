package com.kyx.biz.serve.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.serve.mapper.ServeClassMapper;
import com.kyx.biz.serve.model.Serve;
import com.kyx.biz.serve.model.ServeClass;
import com.kyx.biz.serve.service.ServeClassService;
import com.kyx.biz.serve.service.ServeService;
@Service("serveClassService")
public class ServeClassServiceImpl implements ServeClassService {
	@Resource
    private ServeClassMapper serveClassMapper;
	@Resource
	private ServeService serveService;
	@Override
	public GrdData getInfo(ServeClass serveClass, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<ServeClass> list=serveClassMapper.getInfo(serveClass);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public RetInfo saveData(ServeClass serveClass) {
		RetInfo retInfo;
		int count=0;
		if(serveClass.getId()==null){//新增
			int c=serveClassMapper.getCountByName(serveClass);
			if(c>0){
				retInfo=new RetInfo("error","服务分类名称不能重复");
				return retInfo;
			}
			count=serveClassMapper.insertSelective(serveClass);
		}else{
			count=serveClassMapper.updateByPrimaryKey(serveClass);
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
		for(int i=0;i<idArr.length;i++){
			int count=serveService.getByClassId(Integer.valueOf(idArr[i]));
			if(count>0){
				canDel=false;
				break;
			}
		}
		if(!canDel){
			retInfo=new RetInfo("error","删除失败，分类包含被使用分类！");
			return retInfo;
		}
		int count=0;
		for(int i=0;i<idArr.length;i++){
			count=count+serveClassMapper.deleteByPrimaryKey(Integer.valueOf(idArr[i]));
		}
		if(count==idArr.length)
			retInfo=new RetInfo("success","删除成功");
		else
			retInfo=new RetInfo("error","删除失败");
		return retInfo;
	}

	@Override
	public ServeClass getById(Integer id) {
		return serveClassMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ServeClass> getAll() {
		return serveClassMapper.getInfo(new ServeClass());
	}

	@Override
	public List<Map> getAllInfo(Integer userId, HttpSession session) {
		List<Map> result=new ArrayList<Map>();
		ServeClass sc=new ServeClass();
		Shops shops=(Shops) session.getAttribute("shopsSession");
		sc.setShopId(shops.getId());
		List<ServeClass> classList=serveClassMapper.getInfo(sc);
		for(ServeClass serveClass:classList){
			Map map=new HashMap();
			map.put("serveClass", serveClass);
			List<Serve> serveList=serveService.getListByClassId(userId,serveClass.getId());
			if(serveList==null||serveList.size()==0)
				continue;
			map.put("serveList", serveList);	
			result.add(map);
		}
		return result;
	}

	@Override
	public List<ServeClass> getByServeClass(ServeClass serveClass) {
		
		return serveClassMapper.getInfo(serveClass);
	}

}
