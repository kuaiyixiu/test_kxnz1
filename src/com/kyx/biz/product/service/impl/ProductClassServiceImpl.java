package com.kyx.biz.product.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.product.mapper.ProductClassMapper;
import com.kyx.biz.product.model.Product;
import com.kyx.biz.product.model.ProductClass;
import com.kyx.biz.product.service.ProductClassService;
import com.kyx.biz.product.service.ProductService;
@Service("productClassService")
public class ProductClassServiceImpl implements ProductClassService {
	@Resource
    private ProductClassMapper productClassMapper;
	@Resource
	private ProductService productService;
	@Override
	public GrdData getInfo(ProductClass productClass, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<ProductClass> list=productClassMapper.getInfo(productClass);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public RetInfo saveData(ProductClass productClass) {
		RetInfo retInfo;
		int count=0;
		if(productClass.getId()==null){//新增
			int c=productClassMapper.getByName(productClass);
			if(c>0){
				retInfo=new RetInfo("error","产品分类名称不能重复");
				return retInfo;
			}
			count=productClassMapper.insertSelective(productClass);
		}else{
			count=productClassMapper.updateByPrimaryKey(productClass);
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
			int count=productService.getByClassId(Integer.valueOf(idArr[i]));
			if(count>0){
				canDel=false;
				break;
			}
		}
		if(!canDel){
			retInfo=new RetInfo("error","删除失败，分类包含已使用分类！");
			return retInfo;
		}
		int count=0;
		for(int i=0;i<idArr.length;i++){
			count=count+productClassMapper.deleteByPrimaryKey(Integer.valueOf(idArr[i]));
		}
		if(count==idArr.length)
			retInfo=new RetInfo("success","删除成功");
		else
			retInfo=new RetInfo("error","删除失败");
		return retInfo;
	}

	@Override
	public ProductClass getById(Integer id) {
		return productClassMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ProductClass> getAll(HttpSession session) {
    	Shops shops=(Shops) session.getAttribute("shopsSession");
		return productClassMapper.getAll(shops.getId());
	}

}
