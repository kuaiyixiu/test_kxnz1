package com.kyx.biz.repertory.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.user.model.User;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.debt.mapper.DebtRecordMapper;
import com.kyx.biz.debt.model.DebtRecord;
import com.kyx.biz.payRecord.mapper.PayRecordMapper;
import com.kyx.biz.payRecord.model.PayRecord;
import com.kyx.biz.payRecord.vo.ProfitVo;
import com.kyx.biz.product.mapper.ProductChangeMapper;
import com.kyx.biz.product.mapper.ProductMapper;
import com.kyx.biz.product.mapper.ProductRepertoryMapper;
import com.kyx.biz.product.model.Product;
import com.kyx.biz.product.model.ProductChange;
import com.kyx.biz.product.model.ProductRepertory;
import com.kyx.biz.repertory.mapper.RepertoryDtMapper;
import com.kyx.biz.repertory.mapper.RepertoryMapper;
import com.kyx.biz.repertory.mapper.RepertoryPayMapper;
import com.kyx.biz.repertory.model.Repertory;
import com.kyx.biz.repertory.model.RepertoryDt;
import com.kyx.biz.repertory.model.RepertoryPay;
import com.kyx.biz.repertory.service.RepertoryService;
import com.kyx.biz.workflow.mapper.WorkflowTemplateMapper;
import com.kyx.biz.workflow.model.WorkflowInstance;
import com.kyx.biz.workflow.model.WorkflowTemplate;
import com.kyx.biz.workflow.service.WorkflowInstanceService;
@Service("repertoryService")
public class RepertoryServiceImpl implements RepertoryService {
	@Resource
	private RepertoryMapper repertoryMapper;
	@Resource
	private DebtRecordMapper debtRecordMapper; 
	@Resource
	private ProductChangeMapper productChangeMapper;
	@Resource
	private RepertoryDtMapper repertoryDtMapper;
	@Resource
	private PayRecordMapper payRecordMapper;
	@Resource
	private RepertoryPayMapper repertoryPayMapper;
	@Resource
	private ProductMapper productMapper;
	@Resource
	private ProductRepertoryMapper productRepertoryMapper;
	
	@Resource
	private WorkflowInstanceService workflowInstanceService;
	
	@Resource
	private WorkflowTemplateMapper workflowTemplateMapper;

	@Override
	public GrdData getInfo(Repertory repertory, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<Repertory> list=repertoryMapper.getInfo(repertory);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public RetInfo saveData(Repertory repertory,HttpSession httpSession) {
		RetInfo retInfo;
		int count=0;
		if(repertory.getId()==null){//新增
	    	User user = (User) httpSession.getAttribute("userSession");
	    	repertory.setUserName(user.getUserRealname());
	    	repertory.setAddTime(new Date());
			count=repertoryMapper.insertSelective(repertory);
		}else{
			count=repertoryMapper.updateByPrimaryKeySelective(repertory);
		}
		if(count>0){
			retInfo=new RetInfo("success","保存成功");
			retInfo.setRetDesc(String.valueOf(repertory.getId()));
		}else
			retInfo=new RetInfo("error","保存失败");
		return retInfo;
	}

	@Override
	public Repertory getById(Integer id) {
		return repertoryMapper.selectByPrimaryKey(Integer.valueOf(id));
	}

	@Override
	public RetInfo saveRepertoryin(Repertory repertory) {
		RetInfo retInfo = null;
		//如果是退货 要校验产品数量
		retInfo=checkProduct(repertory);
		if(RetInfo.ERROR.equals(retInfo.getRetCode())){
			return retInfo;
		}
		BigDecimal slipPrice=repertory.getPayAll();
		BigDecimal needPrice=repertory.getNeedPay();
		BigDecimal hasPrice=repertory.getHasPay();
		if(needPrice.compareTo(slipPrice.subtract(hasPrice)) != 0){
			String msg="";
			if(repertory.getKind().intValue()==1)
				msg="入库产品总价异常,请确认无误后再入库！";
			else if(repertory.getKind().intValue()==2)
				msg="退货产品总价异常,请确认无误后再退货！";
			retInfo=new RetInfo("error",msg);
			return retInfo;
		}
		//挂账表
		if(needPrice.compareTo(BigDecimal.ZERO) > 0){//付款金额小于订单金额  产生挂账订单
			DebtRecord debtRecord=new DebtRecord();
			debtRecord.setAddtime(new Date());
			debtRecord.setDebtAmount(repertory.getPayAll());
			debtRecord.setKind(repertory.getKind()+1);//门店入库挂账
			debtRecord.setReturnAmount(repertory.getHasPay());
			debtRecord.setLeftAmount(repertory.getNeedPay());
			debtRecord.setSourceId(repertory.getId());
			debtRecord.setEnable(1);
			int count=debtRecordMapper.insertSelective(debtRecord);
			if(count<1){
				retInfo=new RetInfo("error","生成挂账单有误！");
				return retInfo;
			}
		}
		RepertoryPay repertoryPay=new RepertoryPay();
		repertoryPay.setRepertoryId(repertory.getId());
		List<RepertoryPay> repertoryPays=repertoryPayMapper.getInfo(repertoryPay);
		for(RepertoryPay pay:repertoryPays){
			RepertoryPay uppay=new RepertoryPay();
			uppay.setId(pay.getId());
			uppay.setCanEdit(0);
			repertoryPayMapper.updateByPrimaryKeySelective(uppay);
			//付款记录表
			PayRecord payRecord=new PayRecord();
			payRecord.setAddTime(new Date());
			payRecord.setAmount(pay.getPayAmount());
			int kind=2;
			if(repertory.getKind().intValue()==2)
				kind=6;
			payRecord.setKind(kind);
			payRecord.setSourceId(repertory.getId());
			payRecord.setTypeId(pay.getPayId());
			payRecord.setShopId(pay.getShopId());
			int count=payRecordMapper.insertSelective(payRecord);
			if(count<1){
				retInfo=new RetInfo("error","生成付款记录有误！");
				return retInfo;
			}	
		}
		//更改入库表
		repertory.setRepertoryStatus(2);//已入库
		repertory.setAddTime(new Date());//更新入库时间
		repertoryMapper.updateByPrimaryKeySelective(repertory);
		String optUserName=repertory.getOptUserName();
		repertory=repertoryMapper.selectByPrimaryKey(repertory.getId());
		//查询入库产品表
		RepertoryDt dt=new RepertoryDt();
		dt.setRepertory(repertory.getId());
		List<RepertoryDt> repertoryDts=repertoryDtMapper.getInfo(dt);
		List<ProductChange> productChanges=new ArrayList<ProductChange>();
		for(RepertoryDt rdt:repertoryDts){
			ProductChange productChange=new ProductChange();
			productChange.setBeforeCount(rdt.getQuantity());
			productChange.setCount(rdt.getInQuantity());
			int afterCount=rdt.getQuantity()+rdt.getInQuantity();
			if(repertory.getKind().intValue()==2)
				afterCount=rdt.getQuantity()-rdt.getInQuantity();
			productChange.setAfterCount(afterCount);
			productChange.setChangeReason(repertory.getSupplyName());
			productChange.setChangeReasonId(repertory.getSupplyId());
			productChange.setChangeTime(new Date());
			productChange.setOptUser(optUserName);
			productChange.setProductId(rdt.getProductId());
			productChange.setRelationId(repertory.getId());
			productChange.setType(repertory.getKind());
			productChange.setChangeAmt(new BigDecimal(rdt.getInprice().doubleValue()*rdt.getInQuantity()));
			productChanges.add(productChange);	
			Product product=new Product();
			product.setId(rdt.getProductId());
			if(repertory.getKind().intValue()==1){
				product.setAddQuantity(rdt.getInQuantity());
				product.setAmount(rdt.getInprice());
			}else{
				product.setSubQuantity(rdt.getInQuantity());
			}
			productMapper.updateByPrimaryKeySelective(product);
			if(repertory.getKind().intValue()==1){//入库 产生产品明细表
				ProductRepertory productRepertory=new ProductRepertory();
				productRepertory.setAddTime(new Date());
				productRepertory.setAvailable(1);
				productRepertory.setPrice(rdt.getInprice());
				productRepertory.setProductId(rdt.getProductId());
				productRepertory.setQuantity(rdt.getInQuantity());
				productRepertory.setRepertoryId(rdt.getRepertory());
				productRepertory.setTotalQuantity(rdt.getInQuantity());
				productRepertoryMapper.insertSelective(productRepertory);
			}else if(repertory.getKind().intValue()==2){//退货
				ProductRepertory productRepertory=new ProductRepertory();
				productRepertory.setId(rdt.getIidno());
				productRepertory.setSubQuantity(rdt.getInQuantity());//减少库存数量
				productRepertoryMapper.updateByPrimaryKeySelective(productRepertory);
				productRepertoryMapper.updateAvailable(1);
			}
		}
		//产品异动表
		int count=productChangeMapper.insertBatches(productChanges);
		if(count!=productChanges.size()){
			retInfo=new RetInfo("error","生成产品异动表有误！");
			return retInfo;
		}	
		retInfo=new RetInfo("success","保存成功");
		return retInfo;
	}
    /**
     * 校验退货时产品数量
     * @param repertory
     * @return
     */
	private RetInfo checkProduct(Repertory repertory) {
		RetInfo retInfo=new RetInfo("success","保存成功");
		if(repertory.getKind().intValue()==1) //只校验退货
			return retInfo;
		RepertoryDt dt=new RepertoryDt();
		dt.setRepertory(repertory.getId());
		List<RepertoryDt> repertoryDts=repertoryDtMapper.getInfo(dt);
		for(RepertoryDt rdt:repertoryDts){
			ProductRepertory productRepertory=productRepertoryMapper.selectByPrimaryKey(rdt.getIidno());
			if(productRepertory.getQuantity().intValue()<rdt.getInQuantity().intValue()){
				retInfo.setRetCode(RetInfo.ERROR);
				retInfo.setRetMsg("退货失败，退货数量不应大于库存数量");
				break;
			}
		}
		
		return retInfo;
	}

	@Override
	public RetInfo delData(String ids) {
		RetInfo retInfo;
		String[] idArr=ids.split(";");
		int count=0;
		for(int i=0;i<idArr.length;i++){
			//先删除明细
			repertoryDtMapper.deleteByRepertoryId(Integer.valueOf(idArr[i]));
			//删除付款记录表
			repertoryPayMapper.deleteByRepertoryId(Integer.valueOf(idArr[i]));
			count=count+repertoryMapper.deleteByPrimaryKey(Integer.valueOf(idArr[i]));;
		}
		if(count==idArr.length)
			retInfo=new RetInfo("success","删除成功");
		else
			retInfo=new RetInfo("error","删除失败");
		return retInfo;
	}

	@Override
	public RetInfo saveDestoryData(Repertory repertory, HttpSession httpSession) {
		RetInfo retInfo;
		Repertory rep=repertoryMapper.selectByPrimaryKey(repertory.getId());
		int kind=rep.getKind();
		//进货单作废需要判断进货批次是否已经消耗 消耗不能作废
		retInfo=checkZFProduct(repertory);
		if(RetInfo.ERROR.equals(retInfo.getRetCode())){
			return retInfo;
		}
		//查询是否有挂账表
		DebtRecord debtRecord=debtRecordMapper.selectBySourceIdAndKind(repertory.getId(), kind+1);//查询是否有挂账单
		if(debtRecord!=null){//清空挂账记录设置
			//查看挂账是否还款
			if(debtRecord.getReturnAmount().doubleValue()>0){//挂账单已经产生了还款
				PayRecord payRecord=new PayRecord();
				payRecord.setSourceId(rep.getId());
				int pkind=5;
				if(kind==2)
					pkind=7;
				payRecord.setInKind(pkind);
				List<PayRecord> list=payRecordMapper.getList(payRecord);
				for(PayRecord record:list){//查看还款记录
					PayRecord pay=new PayRecord();
					pay.setAddTime(new Date());
					pay.setAmount(record.getAmount());
					pay.setKind(kind+8);
					pay.setSourceId(rep.getId());
					pay.setTypeId(record.getTypeId());
					pay.setShopId(record.getShopId());
					payRecordMapper.insertSelective(pay);
				}
			}
			debtRecord.setEnable(0);
			debtRecordMapper.updateByPrimaryKeySelective(debtRecord);
		}else{
			//查询还款记录表
			PayRecord payRecord=new PayRecord();
			payRecord.setSourceId(rep.getId());
			payRecord.setInKind(kind);
			List<PayRecord> list=payRecordMapper.getList(payRecord);
			for(PayRecord record:list){//查看还款记录
				PayRecord pay=new PayRecord();
				pay.setAddTime(new Date());
				pay.setAmount(record.getAmount());
				pay.setKind(kind+8);
				pay.setSourceId(rep.getId());
				pay.setTypeId(record.getTypeId());
				pay.setShopId(record.getShopId());
				payRecordMapper.insertSelective(pay);
			}	
		}
		//更改入库表
		repertory.setRepertoryStatus(3);//单据作废
		repertoryMapper.updateByPrimaryKeySelective(repertory);
		//查询入库产品表
		RepertoryDt dt=new RepertoryDt();
		dt.setRepertory(rep.getId());
		List<RepertoryDt> repertoryDts=repertoryDtMapper.getInfo(dt);
		List<ProductChange> productChanges=new ArrayList<ProductChange>();
		String optUserName=AppUtils.getOptUserName(httpSession);
		for(RepertoryDt rdt:repertoryDts){
			ProductChange productChange=new ProductChange();
			productChange.setBeforeCount(rdt.getQuantity());
			productChange.setCount(rdt.getInQuantity());
			int afterCount=rdt.getQuantity()-rdt.getInQuantity();
			if(rep.getKind().intValue()==2)
				afterCount=rdt.getQuantity()+rdt.getInQuantity();
			productChange.setAfterCount(afterCount);
			productChange.setChangeReason(rep.getSupplyName());
			productChange.setChangeReasonId(rep.getSupplyId());
			productChange.setChangeTime(new Date());
			productChange.setOptUser(optUserName);
			productChange.setProductId(rdt.getProductId());
			productChange.setRelationId(rep.getId());
			productChange.setType(rep.getKind()+3);
			productChange.setChangeAmt(new BigDecimal(productChange.getCount()*rdt.getInprice().doubleValue()));
			productChanges.add(productChange);	
			Product product=new Product();
			product.setId(rdt.getProductId());
			if(rep.getKind().intValue()==1){//入库单作废
				product.setSubQuantity(rdt.getInQuantity());
			}else{//退货单作废
				product.setAddQuantity(rdt.getInQuantity());
				ProductRepertory productRepertory=new ProductRepertory();
				productRepertory.setId(rdt.getIidno());
				productRepertory.setAddQuantity(rdt.getInQuantity());
				productRepertoryMapper.updateByPrimaryKeySelective(productRepertory);
			}
			productMapper.updateByPrimaryKeySelective(product);
		}
		productRepertoryMapper.updateAvailable(1);
		//产品异动表
		int count=productChangeMapper.insertBatches(productChanges);
		if(count!=productChanges.size()){
			retInfo=new RetInfo("error","生成产品异动表有误！");
			return retInfo;
		}	
		retInfo=new RetInfo("success","作废成功");
		return retInfo;
	}
    /**
     * 校验进货单是否可以作废 
     * @param repertory
     * @return
     */
	private RetInfo checkZFProduct(Repertory repertory) {
		RetInfo retInfo=new RetInfo("success","保存成功");
		repertory=repertoryMapper.selectByPrimaryKey(repertory.getId());
		if(repertory.getKind().intValue()!=1)
			return retInfo;
		List<ProductRepertory> list=productRepertoryMapper.selectByRepertoryId(repertory.getId(),1);
		for(ProductRepertory productRepertory:list){
			//校验当前入库单的产品是否使用
			if(productRepertory.getQuantity().intValue()!=productRepertory.getTotalQuantity().intValue()){
				retInfo.setRetCode(RetInfo.ERROR);
				retInfo.setRetMsg("作废失败，采购单中产品已被使用。");
				break;
			}
		}	
		if(RetInfo.SUCCESS.equals(retInfo.getRetCode())){
			for(ProductRepertory productRepertory:list){
				productRepertoryMapper.deleteByPrimaryKey(productRepertory.getId());
			}
		}
		return retInfo;
	}

	@Override
	public ProfitVo getOrderReturn(Date dateRangeStartTime, Date dateRangeEndTime) {
		BigDecimal amt =  BigDecimal.ZERO;
		Repertory repertory = new Repertory();
		repertory.setKind(2);
		repertory.setRepertoryStatus(2);
		repertory.setDateRangeStartTime(dateRangeStartTime);
		repertory.setDateRangeEndTime(dateRangeEndTime);
		List<Repertory> list=repertoryMapper.getInfo(repertory);
		for(Repertory rep : list){
			RepertoryDt queryDt = new RepertoryDt();
			queryDt.setRepertory(rep.getId());
			List<RepertoryDt> repertoryDts = repertoryDtMapper.getRepertoryBack(queryDt);
			for(RepertoryDt repDt : repertoryDts){
				BigDecimal backPrice = repDt.getInprice(); //退货价
				Integer backQuantity = repDt.getInQuantity(); //退货数量
				BigDecimal price = repDt.getPrice(); //进货价
				amt = amt.add(backPrice.subtract(price).multiply(new BigDecimal(backQuantity)));  // 数量*（退价 - 进价）
			}
		}
		
		ProfitVo profitVo = new ProfitVo("退货", "采购退货");
		profitVo.setAmount(amt);
		return profitVo;
	}

	@Override
	public RetInfo save(Repertory repertory) {
		RetInfo retInfo=RetInfo.Error("保存失败");
		int count=0;
		if(repertory.getId()==null)
			count=repertoryMapper.insertSelective(repertory);
		else
			count=repertoryMapper.updateByPrimaryKeySelective(repertory);
		if(count>0){
			retInfo=RetInfo.Success("保存成功");
			retInfo.setRetData(repertory.getId());
		}
		return retInfo;
	}

	@Override
	public RetInfo applyRepertoryin(HttpServletRequest request,Repertory repertory) {
		RetInfo retInfo = null;
		retInfo=checkProduct(repertory);
		if(RetInfo.ERROR.equals(retInfo.getRetCode())){
			return retInfo;
		}
		BigDecimal slipPrice=repertory.getPayAll();
		BigDecimal needPrice=repertory.getNeedPay();
		BigDecimal hasPrice=repertory.getHasPay();
		if(needPrice.compareTo(slipPrice.subtract(hasPrice)) != 0){
			String msg="";
			if(repertory.getKind().intValue()==1)
				msg="入库产品总价异常,请确认无误后再入库！";
			else if(repertory.getKind().intValue()==2)
				msg="退货产品总价异常,请确认无误后再退货！";
			retInfo=new RetInfo("error",msg);
			return retInfo;
		}
		repertory.setRepertoryStatus(4);// 4审批中
		repertoryMapper.updateByPrimaryKeySelective(repertory);
		
		//kind 1入库 2退货
		WorkflowInstance instance = new WorkflowInstance();
		Integer templateId = null;
		if(repertory.getKind() == 1){
			templateId = BasicContant.WorkFlowContant.RK_TEMPLATEID;
			instance.setSlipType(1); //入库
			
		}else{
			templateId = BasicContant.WorkFlowContant.TH_TEMPLATEID;
			instance.setSlipType(2); //退货
		}
		instance.setTemplateId(templateId);
		WorkflowTemplate template = workflowTemplateMapper.selectByPrimaryKey(templateId);
		instance.setName(template.getName()+" "+AppUtils.date2String(new Date(),AppUtils.TIME_FORMAT_TO_DATE_TIME));
		instance.setSlipId(repertory.getId());
		User user = (User) request.getSession().getAttribute("userSession");
		instance.setCreateUser(user.getId());
		instance.setCreateTime(new Date());
		workflowInstanceService.createInstance(instance);
		return retInfo;
	}
//
//	@Override
//	public RetInfo submitRepertoryin(HttpServletRequest request,Integer id) {
//		RetInfo retInfo=new RetInfo("success","提交审批成功");
//		Repertory repertory = new Repertory();
//		repertory.setRepertoryStatus(5);// 5审批完成
//		repertoryMapper.updateByPrimaryKeySelective(repertory);
//		return retInfo;
//	}
	
	
}
