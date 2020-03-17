package com.kyx.biz.paytype.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.paytype.mapper.PayTypeMapper;
import com.kyx.biz.paytype.model.PayType;
import com.kyx.biz.paytype.service.PayTypeService;

@Service("payTypeService")
public class PayTypeServiceImpl implements PayTypeService {
	
	@Resource
	private PayTypeMapper payTypeMapper;

//	@Override
//	public List<PayType> getByShopId(Integer shopId) {
//		PayType payType = new PayType();
//		payType.setShopId(shopId);
//		return payTypeMapper.getPayTypeList(payType);
//	}

	@Override
	public Map<Integer, String> getPayTypeMap() {
		PayType type = new PayType();
        List<PayType> payTypes = payTypeMapper.getPayTypeList(type);
		Map<Integer, String> typeMap = new HashMap<Integer, String>();
		if (!ObjectUtils.isEmpty(payTypes)){
			for (PayType pay : payTypes){
				typeMap.put(pay.getId(), pay.getPayName());
			}
		}
		return typeMap;
	}

	
	@Override
	public GrdData getList(PayType payType, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<PayType> list = payTypeMapper.getPayTypeList(payType);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public List<PayType>  getArrayList(PayType payType) {
	//	Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<PayType> list = payTypeMapper.getPayTypeList(payType);
		return list;
	}
	
	
	@Override
	public RetInfo saveData(PayType payType) {
		RetInfo retInfo;
		int count=0;
		if(payType.getId()==null){//新增
			count=payTypeMapper.insertSelective(payType);
		}else{
			count=payTypeMapper.updateByPrimaryKeySelective(payType);
		}
		if(count>0)
			retInfo=new RetInfo("success","保存成功");
		else
			retInfo=new RetInfo("error","保存失败");
		return retInfo;
	}
	
	@Override
	public PayType queryById(Integer id) {
		return payTypeMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public RetInfo deleteType(Integer id) {
		if (id == null){
			return RetInfo.Success("类型编号不能为空");
		}
		if (payTypeMapper.deleteByPrimaryKey(id) > 0 ){
			return RetInfo.Success("支付方式删除成功");
		}else{
			return RetInfo.Success("支付方式删除失败");
			
		}
	}

	@Override
	public RetInfo chkName(String payName) {
		RetInfo retInfo=new RetInfo(RetInfo.SUCCESS,"校验通过");
		int quantity=payTypeMapper.getCountByName(payName);
		if(quantity >0){
			retInfo=new RetInfo(RetInfo.ERROR,"支付类型名称重复，请重新输入");
		}
		return retInfo;
	}

	@Override
	public List<PayType> getPayType(Integer type) {
		return payTypeMapper.getPayType(type);
	}


	/**
	 * 针对储值充值功能单独查询付款方式
	 *
	 */
	@Override
	public List<PayType> getPayTypes(Integer type) {
		
		return payTypeMapper.getPayTypes(type);
	}
}
