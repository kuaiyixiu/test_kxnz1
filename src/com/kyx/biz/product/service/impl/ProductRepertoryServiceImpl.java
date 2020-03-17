package com.kyx.biz.product.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.orders.mapper.OrdersMapper;
import com.kyx.biz.orders.mapper.OrdersProductMapper;
import com.kyx.biz.orders.mapper.OrdersServeMapper;
import com.kyx.biz.orders.model.Orders;
import com.kyx.biz.orders.model.OrdersProduct;
import com.kyx.biz.product.mapper.ProductChangeMapper;
import com.kyx.biz.product.mapper.ProductConsumeMapper;
import com.kyx.biz.product.mapper.ProductMapper;
import com.kyx.biz.product.mapper.ProductRepertoryMapper;
import com.kyx.biz.product.model.Product;
import com.kyx.biz.product.model.ProductChange;
import com.kyx.biz.product.model.ProductConsume;
import com.kyx.biz.product.model.ProductRepertory;
import com.kyx.biz.product.service.ProductRepertoryService;
@Service("productRepertory")
public class ProductRepertoryServiceImpl implements ProductRepertoryService {

	@Resource
    private ProductRepertoryMapper productRepertoryMapper;
	
	@Resource
	private ProductConsumeMapper productConsumeMapper;
	
	@Resource
	private ProductMapper productMapper;
	
	@Resource
	private ProductChangeMapper productChangeMapper;
	
	@Resource
	private OrdersMapper ordersMapper;
	
	@Resource
	private OrdersProductMapper ordersProductMapper;
	
	@Resource
	private OrdersServeMapper ordersServeMapper;
	
	
	
	@Override
	public GrdData getInfo(ProductRepertory productRepertory, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<ProductRepertory> list=productRepertoryMapper.getInfo(productRepertory);
		return new GrdData(page.getTotal(),list);
	}
	@Override
	public RetInfo chkDtData(Integer id, Integer count) {
		RetInfo retInfo=new RetInfo(RetInfo.SUCCESS,"校验通过");
		int quantity=productRepertoryMapper.selectByPrimaryKey(id).getQuantity();
		if(quantity < count){
			retInfo=new RetInfo(RetInfo.ERROR,"请输入有效的退货数量：不应大于库存数量！");
		}
		return retInfo;
	}
	@Override
	public List<ProductRepertory> getByProductId(Integer productId) {
		ProductRepertory productRepertory=new ProductRepertory();
		productRepertory.setProductId(productId);
		productRepertory.setAvailable(1);	
		return productRepertoryMapper.getInfo(productRepertory);
	}
//	@Override
//	public String useProduct(Integer productId, Integer quantity,Orders orders) {
//		Integer remainQuantity = quantity;
//		ProductRepertory pr = new ProductRepertory();
//		pr.setProductId(productId);
//		pr.setAvailable(1);
//		List<ProductRepertory> prList =  productRepertoryMapper.getList(pr); 	//1.根据产品ID返回可用产品库存
//		List<ProductConsume> pcList = new ArrayList<ProductConsume>();
//		for(ProductRepertory repertory : prList){ //2遍历库存列表 减数量
//			if (repertory.getQuantity() < remainQuantity){ //说明不够扣，还要继续遍历
//				ProductRepertory updateVo = new ProductRepertory();
//				updateVo.setId(repertory.getId());
//				updateVo.setSubQuantity(repertory.getQuantity());//数量清空
//				productRepertoryMapper.updateByPrimaryKeySelective(updateVo);//更新库存信息
//				
//				ProductConsume pc = new  ProductConsume(); //新增产品消耗记录
//				pc.setKind(1);
//				pc.setOrderId(orders.getId());
//				pc.setProductRepertoryId(repertory.getId());
//				pc.setQuantity(repertory.getQuantity());
//				pcList.add(pc);
//				remainQuantity -= repertory.getQuantity();  //扣除数量
//				
//			}else{ //够扣了，break;
//				ProductRepertory updateVo = new ProductRepertory();
//				updateVo.setId(repertory.getId());
//				updateVo.setSubQuantity(remainQuantity);//数量不清空，只减去剩余数量
//				productRepertoryMapper.updateByPrimaryKeySelective(updateVo); //更新库存信息
//				
//				ProductConsume pc = new  ProductConsume(); //新增产品消耗记录
//				pc.setKind(1);
//				pc.setOrderId(orders.getId());
//				pc.setProductRepertoryId(repertory.getId());
//				pc.setQuantity(remainQuantity);
//				pcList.add(pc);
//				remainQuantity = 0;
//				break;//够扣了，break;
//			}
//		}
//		
//		if (!ObjectUtils.isEmpty(pcList)){ //批量新增产品消耗表
//			productConsumeMapper.insertBatches(pcList);
//		}
//		
//		
//		Product pro = productMapper.selectByPrimaryKey(productId);
//		ProductChange productChange=new ProductChange();
//		productChange.setBeforeCount(pro.getQuantity());
//		productChange.setCount(quantity);
//		productChange.setAfterCount(pro.getQuantity()-quantity);   //减
//		productChange.setChangeReason(orders.getCarNumber());
//		productChange.setChangeTime(new Date());
//		productChange.setOptUser(orders.getCreateUser());
//		productChange.setProductId(productId);
//		productChange.setRelationId( orders.getId());
//		productChange.setType(3);
//		productChangeMapper.insertSelective(productChange);
//		
//		
//		Product p = new Product();
//		p.setId(productId);
//		p.setSubQuantity(quantity);
//		productMapper.updateByPrimaryKeySelective(p);
//		
//		productRepertoryMapper.updateAvailable(0); //更新状态
//		
//		return null;
//	}
//	@Override
//	public String returnProduct(Orders orders) {
//		//1 根据订单Id查询产品消耗表里列表
//		ProductConsume pcQuery = new ProductConsume();
//		pcQuery.setOrderId(orders.getId());
//		pcQuery.setKind(1);
//		List<ProductConsume> pcList =  productConsumeMapper.getList(pcQuery);
//		//2遍历列表找到产品库存表product_repertory
//		for(ProductConsume pc : pcList){
//			ProductRepertory pr = productRepertoryMapper.selectByPrimaryKey(pc.getProductRepertoryId());
//			
//			ProductRepertory updateVo = new ProductRepertory();
//			updateVo.setId(pc.getProductRepertoryId());
//			updateVo.setSubQuantity(pc.getQuantity());//3把消耗记录的数量加回库存表里，该更新available状态的更新
//			productRepertoryMapper.updateByPrimaryKeySelective(updateVo); //更新库存信息
//			
//			Product p = new Product();
//			p.setId(pr.getProductId());
//			p.setAddQuantity(pc.getQuantity());
//			productMapper.updateByPrimaryKeySelective(p); 	//5更新产品quantity
//			
//			Product pro = productMapper.selectByPrimaryKey(pr.getProductId()); 	//6更新产品异动表
//			ProductChange productChange=new ProductChange();
//			productChange.setBeforeCount(pro.getQuantity());
//			productChange.setCount(pc.getQuantity());
//			productChange.setAfterCount(pro.getQuantity()+pc.getQuantity());  //加
//			productChange.setChangeReason(orders.getCarNumber());
//			productChange.setChangeTime(new Date());
//			productChange.setOptUser(orders.getCreateUser());
//			productChange.setProductId(pr.getProductId());
//			productChange.setRelationId( orders.getId());
//			productChange.setType(9); //9订单反入账/挂账
//			productChangeMapper.insertSelective(productChange);
//
//		}
//		productConsumeMapper.deleteByOrderId(orders.getId(), 1); //4删除产品消耗表记录
//		productRepertoryMapper.updateAvailable(1);
//		
//		return null;
//	}
	@Override
	public String useProduct(Integer productId, Integer quantity, Integer orderId) throws Exception {
		Integer remainQuantity = quantity;
		ProductRepertory pr = new ProductRepertory();
		pr.setProductId(productId);
		pr.setAvailable(1);
		List<ProductRepertory> prList =  productRepertoryMapper.getList(pr); 	//1.根据产品ID返回可用产品库存
		if(ObjectUtils.isEmpty(prList)){
			throw new Exception("产品ID："+productId+"库存异常");
		}
		double amt=0;
		for(ProductRepertory repertory : prList){ //2遍历库存列表 减数量
			
			Integer saveQuantity = 0;
			if (repertory.getQuantity() < remainQuantity){ //说明不够扣，还要继续遍历
				saveQuantity = repertory.getQuantity();
				remainQuantity -= repertory.getQuantity();  //扣除数量
			}else{//够扣了
				saveQuantity = remainQuantity;
				remainQuantity = 0;
			}
			amt=amt+saveQuantity*repertory.getPrice().doubleValue();
			ProductRepertory updateVo = new ProductRepertory();
			updateVo.setId(repertory.getId());
			updateVo.setSubQuantity(saveQuantity);//数量清空
			productRepertoryMapper.updateByPrimaryKeySelective(updateVo);//更新库存信息
			
			ProductConsume pc =  productConsumeMapper.selecyByOrder(orderId, productId, repertory.getId());
			if (pc == null){ //需要新增
				pc = new  ProductConsume(); //新增产品消耗记录
				pc.setKind(1);
				pc.setOrderId(orderId);
				pc.setProductRepertoryId(repertory.getId());
				pc.setQuantity(saveQuantity);
				pc.setProductId(productId);
				pc.setShopId(repertory.getShopId());
				productConsumeMapper.insertSelective(pc);
			}else {//修改数量
				ProductConsume upPc = new ProductConsume();
				upPc.setId(pc.getId());
				upPc.setAddQuantity(saveQuantity);
				productConsumeMapper.updateByPrimaryKeySelective(upPc);
			}
			
			if (remainQuantity == 0 ){
				break;//够扣了，break;
			}
			
		}
		
		Orders orders = ordersMapper.selectByPrimaryKey(orderId);
		Product pro = productMapper.selectByPrimaryKey(productId);
		ProductChange productChange=new ProductChange();
		productChange.setBeforeCount(pro.getQuantity());
		productChange.setCount(quantity);
		productChange.setAfterCount(pro.getQuantity()-quantity);   //减
		productChange.setChangeReason(orders.getCarNumber());
		productChange.setChangeTime(new Date());
		productChange.setOptUser(orders.getCreateUser());
		productChange.setProductId(productId);
		productChange.setRelationId( orders.getId());
		productChange.setType(3);
		productChange.setChangeAmt(new BigDecimal(amt));
		productChangeMapper.insertSelective(productChange);
		
		
		Product p = new Product();
		p.setId(productId);
		p.setSubQuantity(quantity);
		productMapper.updateByPrimaryKeySelective(p);
		
		productRepertoryMapper.updateAvailable(0); //更新状态
		
		return "";
	}
	@Override
	public String updateProduct(Integer ordersProductId, Integer newQuantity) throws Exception {
		OrdersProduct oldOp  =  ordersProductMapper.selectByPrimaryKey(ordersProductId);
		if (oldOp.getQuantity() == newQuantity){ //数量一样 就不用异动了
			return "";
		}
		
		Integer modifyQuantity = newQuantity - oldOp.getQuantity();
		if (modifyQuantity > 0 ){  //库存减少
			Product pro = productMapper.selectByPrimaryKey(oldOp.getProductId());
			if (pro.getQuantity() <modifyQuantity){
				return pro.getProductName()+"库存不足";
			}
			useProduct(oldOp.getProductId(), modifyQuantity, oldOp.getOrderId());
		}else{  //库存增加
			retrunProduct(ordersProductId, Math.abs(modifyQuantity));
		}
		return "";
	}
	@Override
	public String retrunProduct(Integer ordersProductId, Integer returnQuantity) throws Exception {
		OrdersProduct oldOp  =  ordersProductMapper.selectByPrimaryKey(ordersProductId);
		
		List<ProductConsume> pcList =  productConsumeMapper.selecyListByOrder(oldOp.getOrderId(), oldOp.getProductId());
		if (ObjectUtils.isEmpty(pcList)){
			throw new Exception("产品ID："+oldOp.getProductId()+"消耗表异常"+oldOp.getOrderId());
		}
		Integer remainQuantity = 0;
		if (returnQuantity == -1){ //退还全部数量（即删除这笔明细）
			returnQuantity = oldOp.getQuantity();
		}
		
		remainQuantity = returnQuantity;
			
		Integer saveQuantity = 0;
		double amt=0;
		for(ProductConsume pc : pcList){
			if (pc.getQuantity() < remainQuantity){ //说明不够扣，还要继续遍历
				saveQuantity = pc.getQuantity();
				remainQuantity -= pc.getQuantity();  //扣除数量
			}else{//够扣了
				saveQuantity = remainQuantity;
				remainQuantity = 0;
			}
			ProductRepertory updateVo = new ProductRepertory();
			updateVo.setId(pc.getProductRepertoryId());
			updateVo.setAddQuantity(saveQuantity);//数量增加
			productRepertoryMapper.updateByPrimaryKeySelective(updateVo);//更新库存信息
			ProductRepertory pr=productRepertoryMapper.selectByPrimaryKey(pc.getProductRepertoryId());
			amt=amt+saveQuantity*pr.getPrice().doubleValue();
			
			if(saveQuantity == pc.getQuantity()){ //扣除数量 等于消耗数量  可以把这条记录删除了
				productConsumeMapper.deleteByPrimaryKey(pc.getId());
			}else{  //更新记录数量
				ProductConsume upPc = new ProductConsume();
				upPc.setId(pc.getId());
				upPc.setSubQuantity(saveQuantity);
				productConsumeMapper.updateByPrimaryKeySelective(upPc);
			}
			
			if (remainQuantity == 0 ){
				break;//够扣了，break;
			}
		}
		
		Orders orders = ordersMapper.selectByPrimaryKey(oldOp.getOrderId());
		Product pro = productMapper.selectByPrimaryKey(oldOp.getProductId());
		ProductChange productChange=new ProductChange();
		productChange.setBeforeCount(pro.getQuantity());
		productChange.setCount(returnQuantity);
		productChange.setAfterCount(pro.getQuantity() + returnQuantity);   //加
		productChange.setChangeReason(orders.getCarNumber());
		productChange.setChangeTime(new Date());
		productChange.setOptUser(orders.getCreateUser());
		productChange.setProductId(oldOp.getProductId());
		productChange.setRelationId( orders.getId());
		productChange.setType(3);
		productChange.setChangeAmt(new BigDecimal(amt));
		productChangeMapper.insertSelective(productChange);
		
		
		Product p = new Product();
		p.setId(oldOp.getProductId());
		p.setAddQuantity(returnQuantity);
		productMapper.updateByPrimaryKeySelective(p);
		
		return "";
	}
	
	
	@Override
	public void updateOrderAmt(Integer orderId) {
		BigDecimal b1 = ordersProductMapper.querySumPrice(orderId);
		if (b1 == null){
			b1 = BigDecimal.ZERO;
		}
		BigDecimal b2 = ordersServeMapper.querySumPrice(orderId);
		if (b2 == null){
			b2 = BigDecimal.ZERO;
		}
		
		Orders orders = new Orders();
		orders.setId(orderId);
		orders.setOrderAmt(b1.add(b2));
		orders.setPayAmount(b1.add(b2));
		ordersMapper.updateByPrimaryKeySelective(orders);
	}
}
