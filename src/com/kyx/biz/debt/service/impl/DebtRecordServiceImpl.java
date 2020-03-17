package com.kyx.biz.debt.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
import com.kyx.biz.debt.mapper.DebtRecordMapper;
import com.kyx.biz.debt.model.DebtRecord;
import com.kyx.biz.debt.service.DebtRecordService;
import com.kyx.biz.payRecord.mapper.PayRecordMapper;
import com.kyx.biz.payRecord.model.PayRecord;
import com.kyx.biz.repertory.mapper.RepertoryPayMapper;
import com.kyx.biz.repertory.model.RepertoryPay;
@Service("debtRecordService")
public class DebtRecordServiceImpl implements DebtRecordService {
	@Resource
	private DebtRecordMapper debtRecordMapper;
	@Resource
	private RepertoryPayMapper repertoryPayMapper;
	@Resource
	private PayRecordMapper payRecordMapper;

	@Override
	public int save(DebtRecord debtRecord) {
		return debtRecordMapper.insertSelective(debtRecord);
	}

	@Override
	public GrdData getInfo(DebtRecord debtRecord, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<DebtRecord> list=debtRecordMapper.getInfo(debtRecord);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public DebtRecord getById(Integer id) {
		return debtRecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public RetInfo saveReturnDebt(DebtRecord debtRecord) {
		//采购入库挂账还款 
		RetInfo retInfo;
		debtRecord=debtRecordMapper.selectByPrimaryKey(debtRecord.getId());
		Integer repertoryId=debtRecord.getSourceId();
		Integer kind=debtRecord.getKind();
		Integer payKind=5;//门店挂账还款
		if(kind==3)//门店退货挂账还款
			payKind=7;
		//查询入库单付款记录
		RepertoryPay repertoryPay=new RepertoryPay();
		repertoryPay.setRepertoryId(repertoryId);
		List<RepertoryPay> payList=repertoryPayMapper.getInfo(repertoryPay);
		double returnAmt=0;
		for(RepertoryPay pay:payList){//付款列表
			if(pay.getCanEdit()==1){//待还款记录
				pay.setCanEdit(0);
				repertoryPayMapper.updateByPrimaryKeySelective(pay);
				returnAmt=returnAmt+pay.getPayAmount().doubleValue();
				//生成付款记录
				PayRecord payRecord=new PayRecord();
				payRecord.setAddTime(new Date());
				payRecord.setAmount(pay.getPayAmount());
				payRecord.setKind(payKind);
				payRecord.setSourceId(repertoryId);
				payRecord.setTypeId(pay.getPayId());
				payRecord.setShopId(pay.getShopId());
				int count=payRecordMapper.insertSelective(payRecord);
				if(count<1){
					retInfo=new RetInfo("error","生成付款记录有误！");
					return retInfo;
				}	
				
			}
		}
		//更新挂账表
		DebtRecord updebtRecord=new DebtRecord();
		updebtRecord.setReturnAmount(new BigDecimal(debtRecord.getReturnAmount().doubleValue()+returnAmt));
		updebtRecord.setLeftAmount(new BigDecimal(debtRecord.getDebtAmount().doubleValue()-updebtRecord.getReturnAmount().doubleValue()));
		updebtRecord.setId(debtRecord.getId());
		int count=debtRecordMapper.updateByPrimaryKeySelective(updebtRecord);
		if(count>0){
			retInfo=new RetInfo("success","还款成功");
		}else
			retInfo=new RetInfo("error","还款失败");
		return retInfo;
	}

	@Override
	public RetInfo saveReturnDebt(Integer payId, String ids,Integer shopId) {
		RetInfo retInfo=new RetInfo("error","还款失败");;
		//产生入库支付表
		String[] idArr=ids.split(";");
//		Shops shops=(Shops) session.getAttribute("shopsSession");
		for(String id:idArr){
			if(StringUtils.isEmpty(id))
				continue;
			DebtRecord debtRecord=debtRecordMapper.selectByPrimaryKey(Integer.valueOf(id));
			Integer repertoryId=debtRecord.getSourceId();//入库id
			//插入库存付账表
			RepertoryPay repertoryPay=new RepertoryPay();
			repertoryPay.setAddTime(new Date());
			repertoryPay.setCanEdit(0);
			repertoryPay.setPayAmount(debtRecord.getLeftAmount());
			repertoryPay.setPayId(payId);
			repertoryPay.setRepertoryId(repertoryId);
			repertoryPay.setShopId(shopId);
			repertoryPayMapper.insertSelective(repertoryPay);
			//更新挂账记录表
			DebtRecord debt=new DebtRecord();
			debt.setId(debtRecord.getId());
			debt.setLeftAmount(new BigDecimal(0.00));
			debt.setReturnAmount(debtRecord.getLeftAmount());
			debtRecordMapper.updateByPrimaryKeySelective(debt);
			//产生payrecord记录表
			PayRecord payRecord=new PayRecord();
			payRecord.setAddTime(new Date());
			payRecord.setAmount(debtRecord.getLeftAmount());
			payRecord.setKind(5);//门店挂账还款
			payRecord.setSourceId(repertoryId);
			payRecord.setTypeId(payId);
			payRecord.setShopId(shopId);
			payRecordMapper.insertSelective(payRecord);	
		}	
		retInfo=new RetInfo("success","还款成功");
		return retInfo;
	}

	@Override
	public BigDecimal getSumDebtAmount(DebtRecord debtRecord) {
		return debtRecordMapper.getSumDebtAmount(debtRecord);
	}

}
