package com.kyx.biz.product.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.shops.service.ShopsService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.payRecord.mapper.PayRecordMapper;
import com.kyx.biz.payRecord.model.PayRecord;
import com.kyx.biz.product.mapper.ProductAllocationMapper;
import com.kyx.biz.product.mapper.ProductChangeMapper;
import com.kyx.biz.product.mapper.ProductConsumeMapper;
import com.kyx.biz.product.mapper.ProductMapper;
import com.kyx.biz.product.mapper.ProductRepertoryMapper;
import com.kyx.biz.product.model.Product;
import com.kyx.biz.product.model.ProductAllocation;
import com.kyx.biz.product.model.ProductChange;
import com.kyx.biz.product.model.ProductConsume;
import com.kyx.biz.product.model.ProductRepertory;
import com.kyx.biz.product.service.ProductAllocationService;
import com.kyx.biz.product.vo.ProductAllocationVo;
@Service("productAllocationService")
public class ProductAllocationServiceImpl implements ProductAllocationService {
	@Resource
	private ProductAllocationMapper productAllocationMapper;
	@Resource
	private ProductMapper productMapper;
	@Resource
	private ProductRepertoryMapper productRepertoryMapper;
	@Resource
	private ProductChangeMapper productChangeMapper;
	@Resource
	private ProductConsumeMapper productConsumeMapper;
	@Resource
	private ShopsService shopsService;
	@Resource
	private PayRecordMapper payRecordMapper;

	@Override
	public RetInfo saveAllocationInfo(ProductAllocationVo productAllocationVo,HttpSession session) {
		String grdData=productAllocationVo.getGrdData();
		JSONArray arr=JSONArray.parseArray(grdData);
		if(arr.size()<1){
			return RetInfo.Error("调拨数量异常");
		}
		//调入信息没有匹配的产品要产生产品
		Product product;
		if(productAllocationVo.getRelationProductId()==null){
			//产生
			Integer productId=productAllocationVo.getProductId();
			Product oldProduct=productMapper.selectByPrimaryKey(productId);
			product=new Product();
			product.setProductName(oldProduct.getProductName());
			product.setClassId(oldProduct.getClassId());
			product.setPrice(oldProduct.getPrice());
			product.setQuantity(0);
			product.setShopId(productAllocationVo.getRelationShop());
			productMapper.insertSelective(product);
			productAllocationVo.setRelationProductId(product.getId());
		}
		if(productAllocationVo.getRelationProductId()==null)
			return RetInfo.Error("调入产品异常");
		//计算总数量和总价
		int count=0;
		double amount=0;
		for(int i=0;i<arr.size();i++){
			JSONObject jsonObject=arr.getJSONObject(i);
			Integer id=jsonObject.getInteger("id");//批次id
			Integer allocation=jsonObject.getInteger("allocation");//调拨数量
			count=count+allocation;
			ProductRepertory pr=productRepertoryMapper.selectByPrimaryKey(id);
			amount=amount+pr.getPrice().doubleValue()*allocation;
		}
		//新增调出产品异动信息
		product=productMapper.selectByPrimaryKey(productAllocationVo.getProductId());
		ProductChange productChange=new ProductChange();
		productChange.setBeforeCount(product.getQuantity());
		productChange.setCount(count);
		int afterCount=product.getQuantity()-count;
		productChange.setAfterCount(afterCount);
		productChange.setChangeReason(productAllocationVo.getRelationShopName());
		productChange.setChangeReasonId(productAllocationVo.getRelationShop());
		productChange.setChangeTime(new Date());
		productChange.setOptUser(AppUtils.getOptUserName(session));
		productChange.setProductId(productAllocationVo.getProductId());
		productChange.setType(7);//库存调出
		productChange.setChangeTime(new Date());
		productChange.setChangeAmt(new BigDecimal(amount));//产品异动总价
		productChangeMapper.insertSelective(productChange);
		//调出产品总数减少
		product=new Product();
		product.setId(productAllocationVo.getProductId());
		product.setSubQuantity(count);
		productMapper.updateByPrimaryKeySelective(product);	
		//新增调入产品异动信息
		product=productMapper.selectByPrimaryKey(productAllocationVo.getRelationProductId());
		productChange=new ProductChange();
		productChange.setBeforeCount(product.getQuantity());
		productChange.setCount(count);
		afterCount=product.getQuantity()+count;
		productChange.setAfterCount(afterCount);
		productChange.setChangeTime(new Date());
		productChange.setOptUser(AppUtils.getOptUserName(session));
		productChange.setProductId(productAllocationVo.getRelationProductId());
		productChange.setType(6);//库存调入
		productChange.setChangeTime(new Date());
		productChange.setChangeAmt(new BigDecimal(amount));//产品异动总价
		productChangeMapper.insertSelective(productChange);
		//调入产品总数增加
		product=new Product();
		product.setId(productAllocationVo.getRelationProductId());
		product.setAddQuantity(count);
		productMapper.updateByPrimaryKeySelective(product);	
		//生成调拨单
		ProductAllocation productAllocation=new ProductAllocation();
		productAllocation.setAddtime(new Date());
		productAllocation.setAmount(new BigDecimal(amount));
		productAllocation.setCallinId(productAllocationVo.getRelationProductId());
		productAllocation.setCalloutId(productAllocationVo.getProductId());
		productAllocation.setCount(count);
		productAllocation.setEnable(1);//正常
		productAllocation.setRelationShop(productAllocationVo.getRelationShop());
		productAllocation.setShopId(productAllocationVo.getShopId());
		productAllocationMapper.insertSelective(productAllocation);	
		ProductRepertory productRepertory;
		for(int i=0;i<arr.size();i++){
			JSONObject jsonObject=arr.getJSONObject(i);
			Integer id=jsonObject.getInteger("id");//批次id
			Integer allocation=jsonObject.getInteger("allocation");//调拨数量
			//调出门店批次数量减少
			productRepertory=new ProductRepertory();
			productRepertory.setSubQuantity(allocation);
			productRepertory.setId(id);
			productRepertoryMapper.updateByPrimaryKeySelective(productRepertory);
			//调出门店库存消耗记录
			ProductConsume productConsume=new ProductConsume();
			productConsume.setProductRepertoryId(id);
			productConsume.setAddTime(new Date());
			productConsume.setKind(2);//调拨单
			productConsume.setOrderId(productAllocation.getId());
			productConsume.setQuantity(allocation);
			productConsume.setShopId(product.getShopId());
			productConsumeMapper.insertSelective(productConsume);
			//调入门店批次产生
			ProductRepertory pr=productRepertoryMapper.selectByPrimaryKey(id);
			productRepertory=new ProductRepertory();
			productRepertory.setAddTime(new Date());
			productRepertory.setAvailable(1);
			productRepertory.setPrice(pr.getPrice());
			productRepertory.setProductId(productAllocationVo.getRelationProductId());
			productRepertory.setQuantity(allocation);
			productRepertory.setTotalQuantity(allocation);
			productRepertory.setKind(2);//调拨单产生
			productRepertory.setRepertoryId(productAllocation.getId());
			productRepertoryMapper.insertSelective(productRepertory);
		}
		productRepertoryMapper.updateAvailable(0);
		return RetInfo.Success("调拨成功");
	}

	@Override
	public GrdData getInfo(ProductAllocation productAllocation, Pager pager,HttpSession session) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<ProductAllocation> list=productAllocationMapper.getInfo(productAllocation);
		Shops shops=(Shops) session.getAttribute("shopsSession");
		String dbName=shops.getDbName();
		//关联门店
		List<Shops> shopList=shopsService.getShops().get(dbName);
		for(ProductAllocation allocation:list){
			allocation.setRelationShopName(getShopName(shopList,allocation.getRelationShop()));
		}
		return new GrdData(page.getTotal(),list);
	}

	private String getShopName(List<Shops> shopList, Integer relationShop) {
		String shopName="";
		for(Shops shop:shopList){
			if(shop.getId().intValue()==relationShop.intValue()){
				shopName=shop.getShopName();
			}
		}
		
		return shopName;
	}

	@Override
	public ProductAllocation getById(Integer id) {
		return productAllocationMapper.selectByPrimaryKey(id);
	}

	@Override
	public RetInfo savePayInfo(ProductAllocation productAllocation) {
		RetInfo retInfo;
		int count=productAllocationMapper.updateByPrimaryKeySelective(productAllocation);
		//产生payrecord
		productAllocation=productAllocationMapper.selectByPrimaryKey(productAllocation.getId());
		PayRecord payRecord=new PayRecord();
		payRecord.setAddTime(new Date());
		payRecord.setAmount(productAllocation.getAmount());
		payRecord.setTypeId(productAllocation.getPayType());
		payRecord.setKind(11);
		payRecord.setSourceId(productAllocation.getId());
		payRecord.setShopId(productAllocation.getShopId());
		payRecordMapper.insertSelective(payRecord);
		if(count>0)
			retInfo=new RetInfo("success","保存成功");
		else
			retInfo=new RetInfo("error","保存失败");
		return retInfo;
	}

	@Override
	public RetInfo saveDestoryData(ProductAllocation productAllocation,HttpSession httpSession) {
		RetInfo retInfo;
		//校验点入门店的产品是否已使用  已使用不能作废
		retInfo=chkProductRepertory(productAllocation);
		if(RetInfo.ERROR.equals(retInfo.getRetCode())){
			return retInfo;
		}
		//调拨单作废
		productAllocation.setEnable(2);
		productAllocationMapper.updateByPrimaryKeySelective(productAllocation);
		//产品还原回到之前门店中
		productAllocation=productAllocationMapper.selectByPrimaryKey(productAllocation.getId());
		//产品异动
		Product product=productMapper.selectByPrimaryKey(productAllocation.getCallinId());
		ProductChange productChange=new ProductChange();
		productChange.setBeforeCount(product.getQuantity());
		productChange.setCount(productAllocation.getCount());
		int afterCount=product.getQuantity()-productAllocation.getCount();
		productChange.setAfterCount(afterCount);
		productChange.setChangeReason("");
//		productChange.setChangeReasonId(productAllocationVo.getShopId());
		productChange.setChangeTime(new Date());
		productChange.setOptUser(AppUtils.getOptUserName(httpSession));
		productChange.setProductId(productAllocation.getCallinId());
		productChange.setType(8);//库存调出作废
		productChange.setChangeTime(new Date());
		productChange.setChangeAmt(productAllocation.getAmount());
		productChangeMapper.insertSelective(productChange);
		//调入门店产品还原
		product=new Product();
		product.setId(productAllocation.getCallinId());
		product.setSubQuantity(productAllocation.getCount());
		productMapper.updateByPrimaryKeySelective(product);
		//调出门店产品异动
		product=productMapper.selectByPrimaryKey(productAllocation.getCalloutId());
		productChange=new ProductChange();
		productChange.setBeforeCount(product.getQuantity());
		productChange.setCount(productAllocation.getCount());
		afterCount=product.getQuantity()+productAllocation.getCount();
		productChange.setAfterCount(afterCount);
		productChange.setChangeReason("");
//		productChange.setChangeReasonId(productAllocationVo.getShopId());
		productChange.setChangeTime(new Date());
		productChange.setOptUser(AppUtils.getOptUserName(httpSession));
		productChange.setProductId(productAllocation.getCalloutId());
		productChange.setType(8);//库存调出作废
		productChange.setChangeTime(new Date());
		productChange.setChangeAmt(productAllocation.getAmount());
		productChangeMapper.insertSelective(productChange);
		//调出门店产品还原
		product=new Product();
		product.setId(productAllocation.getCalloutId());
		product.setAddQuantity(productAllocation.getCount());
		productMapper.updateByPrimaryKeySelective(product);
		//产品批次表
		ProductConsume productConsume=new ProductConsume();
		productConsume.setKind(2);//库存调拨
		productConsume.setOrderId(productAllocation.getId());
		List<ProductConsume> consumes=productConsumeMapper.getList(productConsume);
		for(ProductConsume consume:consumes){
			ProductRepertory productRepertory=new ProductRepertory();
			productRepertory.setId(consume.getProductRepertoryId());
			productRepertory.setAddQuantity(consume.getQuantity());
			productRepertoryMapper.updateByPrimaryKeySelective(productRepertory);
		}
		productRepertoryMapper.updateAvailable(1);
		retInfo=new RetInfo("success","作废成功");
		return retInfo;
	}
    /**
     * 校验调入的产品是否已被使用 可以作废删除批次信息
     * @param productAllocation
     * @return
     */
	private RetInfo chkProductRepertory(ProductAllocation productAllocation) {
		RetInfo retInfo;
		List<ProductRepertory> list=productRepertoryMapper.selectByRepertoryId(productAllocation.getId(), 2);
		for(ProductRepertory productRepertory:list){
			if(productRepertory.getQuantity().intValue()!=productRepertory.getTotalQuantity().intValue()){
				retInfo=RetInfo.Error("调入产品已被使用，不能作废！");
				return retInfo;
			}
		}
		//遍历库存明细
		for(ProductRepertory productRepertory:list){
			productRepertoryMapper.deleteByPrimaryKey(productRepertory.getId());
		}
		retInfo=RetInfo.Success("");
		return retInfo;
	}
	

}
