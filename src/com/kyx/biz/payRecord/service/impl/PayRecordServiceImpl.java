package com.kyx.biz.payRecord.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.kyx.biz.orders.mapper.OrdersMapper;
import com.kyx.biz.payRecord.mapper.PayRecordMapper;
import com.kyx.biz.payRecord.model.PayRecord;
import com.kyx.biz.payRecord.service.PayRecordService;
import com.kyx.biz.paytype.service.PayTypeService;
@Service("payRecordService")
public class PayRecordServiceImpl implements PayRecordService {
	
	@Resource
	private PayRecordMapper payRecordMapper;
	
	@Resource
	private PayTypeService payTypeService;
	@Resource
	private OrdersMapper ordersMapper;

	@Override
	public GrdData getList(PayRecord payRecord, Pager pager) {
		Map<Integer, String> typeMap = payTypeService.getPayTypeMap();
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<PayRecord> list=payRecordMapper.getList(payRecord);
		if (!ObjectUtils.isEmpty(list)){
			for(PayRecord record : list){
				record.setTypeName(typeMap.get(record.getTypeId()));
			}
			
		}
		
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public Map getEchatInfo(Integer shopId) {
		Map resultMap=new HashMap();
		//x坐标
		List<String> xlist=new ArrayList<String>();
		List<Integer> ylist1=new ArrayList<Integer>();//订单数
		List<BigDecimal> ylist2=new ArrayList<BigDecimal>();//收入
		List<BigDecimal> ylist3=new ArrayList<BigDecimal>();//支出
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -6);
		xlist.add(format.format(cal.getTime()));
		//Y坐标 
		ylist1.add(ordersMapper.getCountsByCreatTime(cal.getTime(),shopId));
		ylist2.add(init(payRecordMapper.getInAmtByAddTime(cal.getTime(),shopId)));
		ylist3.add(init(payRecordMapper.getOutAmtByAddTime(cal.getTime(),shopId)));	
		cal.add(Calendar.DAY_OF_MONTH, 1);
		String s6=format.format(cal.getTime());
		xlist.add(s6);
		ylist1.add(ordersMapper.getCountsByCreatTime(cal.getTime(),shopId));
		ylist2.add(init(payRecordMapper.getInAmtByAddTime(cal.getTime(),shopId)));
		ylist3.add(init(payRecordMapper.getOutAmtByAddTime(cal.getTime(),shopId)));		
		cal.add(Calendar.DAY_OF_MONTH, 1);
		String s5=format.format(cal.getTime());
		xlist.add(s5);
		ylist1.add(ordersMapper.getCountsByCreatTime(cal.getTime(),shopId));
		ylist2.add(init(payRecordMapper.getInAmtByAddTime(cal.getTime(),shopId)));
		ylist3.add(init(payRecordMapper.getOutAmtByAddTime(cal.getTime(),shopId)));	
		cal.add(Calendar.DAY_OF_MONTH, 1);
		String s4=format.format(cal.getTime());
		xlist.add(s4);
		ylist1.add(ordersMapper.getCountsByCreatTime(cal.getTime(),shopId));
		ylist2.add(init(payRecordMapper.getInAmtByAddTime(cal.getTime(),shopId)));
		ylist3.add(init(payRecordMapper.getOutAmtByAddTime(cal.getTime(),shopId)));	
		cal.add(Calendar.DAY_OF_MONTH, 1);
		String s3=format.format(cal.getTime());
		xlist.add(s3);
		ylist1.add(ordersMapper.getCountsByCreatTime(cal.getTime(),shopId));
		ylist2.add(init(payRecordMapper.getInAmtByAddTime(cal.getTime(),shopId)));
		ylist3.add(init(payRecordMapper.getOutAmtByAddTime(cal.getTime(),shopId)));	
		cal.add(Calendar.DAY_OF_MONTH, 1);
		String s2=format.format(cal.getTime());
		xlist.add(s2);
		ylist1.add(ordersMapper.getCountsByCreatTime(cal.getTime(),shopId));
		ylist2.add(init(payRecordMapper.getInAmtByAddTime(cal.getTime(),shopId)));
		ylist3.add(init(payRecordMapper.getOutAmtByAddTime(cal.getTime(),shopId)));	
		cal.add(Calendar.DAY_OF_MONTH, 1);
		String s1=format.format(cal.getTime());
		xlist.add(s1);
		ylist1.add(ordersMapper.getCountsByCreatTime(cal.getTime(),shopId));
		ylist2.add(init(payRecordMapper.getInAmtByAddTime(cal.getTime(),shopId)));
		ylist3.add(init(payRecordMapper.getOutAmtByAddTime(cal.getTime(),shopId)));		
		resultMap.put("x", xlist);
		resultMap.put("y1", ylist1);
		resultMap.put("y2", ylist2);
		resultMap.put("y3", ylist3);
		return resultMap;
	}

	private BigDecimal init(BigDecimal bigDecimal) {
		if(bigDecimal==null)
			return new BigDecimal(0);
		return bigDecimal;
	}
}
