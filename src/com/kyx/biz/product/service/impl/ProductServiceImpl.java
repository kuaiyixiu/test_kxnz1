package com.kyx.biz.product.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.custom.mapper.CustomMapper;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.mealdt.service.MealDtService;
import com.kyx.biz.product.mapper.ProductMapper;
import com.kyx.biz.product.model.Product;
import com.kyx.biz.product.service.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService {
	
	@Resource
	private ProductMapper productMapper;
	
	@Resource
	private CustomMapper customMapper;
	@Resource
	private MealDtService mealDtService;

	@Override
	public Product getById(Integer id) {
		return productMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Product> getProductList(Product product) {
		return productMapper.getProductList(product);
	}

	@Override
	public int getByClassId(Integer classId) {
		
		return productMapper.getByClassId(classId);
	}

	@Override
	public GrdData getInfo(Product product, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<Product> list=productMapper.getInfo(product);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public RetInfo saveData(Product product) {
		RetInfo retInfo;
		int count=0;
		if(product.getId()==null){//新增
			//判断产品名称是否重复
			Product p=productMapper.getByName(product);
			if(p!=null){
				retInfo=new RetInfo("error","产品名称重复，请重新输入");
				return retInfo;
			}
			count=productMapper.insertSelective(product);
		}else{
			count=productMapper.updateByPrimaryKeySelective(product);
		}
		if(count>0)
			retInfo=new RetInfo("success","保存成功");
		else
			retInfo=new RetInfo("error","保存失败");
		return retInfo;
	}

	@Override
	public RetInfo delData(String ids) {
		RetInfo retInfo=chkCanDel(ids);
		if(retInfo.ERROR.equals(retInfo.getRetCode()))
			return retInfo;
		String[] idArr=ids.split(";");
		int count=0;
		for(int i=0;i<idArr.length;i++){
			Product product=new Product();
			product.setId(Integer.valueOf(idArr[i]));
			product.setDel(1);
			count=count+productMapper.updateByPrimaryKeySelective(product);
					//.deleteByPrimaryKey(Integer.valueOf(idArr[i]));
		}
		if(count==idArr.length)
			retInfo=new RetInfo("success","删除成功");
		else
			retInfo=new RetInfo("error","删除失败");
		return retInfo;
	}
	
    /**
     * 校验是否可以删除
     * @param ids
     * @return
     */
	private RetInfo chkCanDel(String ids) {
		RetInfo retInfo=RetInfo.Success("");
		String[] idArr=ids.split(";");
		int count=0;
		Boolean canDel=true;
		for(int i=0;i<idArr.length;i++){
			count=mealDtService.getCountByTypeAndId(1,Integer.valueOf(idArr[i]));
			if(count>0){
				canDel=false;
				break;
			}
		}
		if(!canDel){
			retInfo=RetInfo.Error("套餐中存在该产品，不允许删除");
		}
		return retInfo;
	}

	@Override
	public RetInfo checkExist(Product product) {
		RetInfo retInfo=null;
		Product pro=productMapper.selectByPrimaryKey(product.getId());
		product.setProductName(pro.getProductName());
		pro=productMapper.getByName(product);
		if(pro!=null){
			retInfo=RetInfo.Success("");
			retInfo.setRetDesc(String.valueOf(pro.getId()));
		}else
			retInfo=RetInfo.Error("调入门店中不存在该产品，您可以：");      
		return retInfo;
	}

	@Override
	public Product selectByName(String productName, Integer shopId) {
		return productMapper.selectByName(productName, shopId);
	}

	@Override
	public Integer getArertCount(Integer shopId) {
		return productMapper.getArertCount(shopId);
	}

	@Override
	public GrdData getPreviewList(Product product, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<Product> list=productMapper.getPreviewList(product);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public List<Product> getProductCustomPrice(Product record) {
		List<Product> products =  null;
		if (record.getCustId() != null){
			Custom custom = customMapper.selectByPrimaryKey(record.getCustId());
			if (custom != null && custom.getCustType() != null){
				record.setCustType(custom.getCustType());
			}
			products =  productMapper.getProductCustomPrice(record);
			for(Product product : products){
				if (product.getCustPrice() != null && !BigDecimal.ZERO.equals(product.getCustPrice())){ //产品价格不为空切不为0
					product.setPrice(product.getCustPrice()); //把会员价格赋给产品价格
					product.setIsCust(1);//是会员价格
				}
			}
		}else{
			products = productMapper.getProductList(record);	
		}
		return products;
	}
}
