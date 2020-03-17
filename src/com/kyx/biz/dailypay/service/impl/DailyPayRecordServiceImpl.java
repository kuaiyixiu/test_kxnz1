package com.kyx.biz.dailypay.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.dailypay.mapper.DailyPayRecordMapper;
import com.kyx.biz.dailypay.model.DailyPayRecord;
import com.kyx.biz.dailypay.service.DailyPayRecordService;
import com.kyx.biz.payRecord.vo.ProfitVo;

@Service("dailyPayRecordService")
public class DailyPayRecordServiceImpl implements DailyPayRecordService {
	
	@Resource
	private DailyPayRecordMapper dailyPayRecordMapper;

	@Override
	public GrdData getList(DailyPayRecord dailyPayRecord, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<DailyPayRecord> list = dailyPayRecordMapper.getList(dailyPayRecord);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public RetInfo saveRecord(DailyPayRecord dailyPayRecord) {
		dailyPayRecordMapper.insertSelective(dailyPayRecord);
		return RetInfo.Success("日常收支保存成功");
	}

	@Override
	public RetInfo updateRecord(DailyPayRecord dailyPayRecord) {
		if (dailyPayRecord.getId() == null){
			return RetInfo.Error("记录编号不能为空");
		}
		if (dailyPayRecordMapper.updateByPrimaryKeySelective( dailyPayRecord) > 0 ){
			return RetInfo.Success("日常收支修改成功");
		}else {
			return RetInfo.Success("日常收支修改失败");
		}
	}

	@Override
	public RetInfo deleteRecord(Integer id) {
		if (id == null){
			return RetInfo.Success("记录编号不能为空");
		}
		if (dailyPayRecordMapper.deleteByPrimaryKey(id) > 0 ){
			return RetInfo.Success("日常收支删除成功");
		}else{
			return RetInfo.Success("日常收支删除失败");
			
		}
	}

	@Override
	public DailyPayRecord queryById(Integer id) {
		return dailyPayRecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ProfitVo> getProfitList(Date dateRangeStartPeriod, Date dateRangeEndPeriod) {
		DailyPayRecord dailyPayRecord = new DailyPayRecord();
		dailyPayRecord.setDateRangeStartPeriod(dateRangeStartPeriod);
		dailyPayRecord.setDateRangeEndPeriod(dateRangeEndPeriod);
		List<DailyPayRecord> dailyPayRecords =  dailyPayRecordMapper.getList(dailyPayRecord);
		BigDecimal amt1 = BigDecimal.ZERO;
		BigDecimal amt2 = BigDecimal.ZERO;
		BigDecimal amt3 = BigDecimal.ZERO;
		for(DailyPayRecord record : dailyPayRecords){
			if (record.getKind() == 1){ //收入
				amt1 = amt1.add(record.getAmount());
			}else{
				if (DateUtils.isSameDay(record.getStartSharePeriod(), record.getEndSharePeriod())){ 
					amt2 = amt2.add(record.getAmount());//一次性支出
				}else{
					amt3 = amt3.add(record.getAmount());  //分摊支出
				}
			}
		}
		
		ProfitVo p1 = new ProfitVo("日常收支","收入");  //收入
		p1.setAmount(amt1);
		ProfitVo p2 = new ProfitVo("日常收支","一次性支出"); //一次性支出
		p2.setAmount(amt2);
		ProfitVo p3 = new ProfitVo("日常收支","分摊支出"); //分摊支出
		p3.setAmount(amt3);
		
		List<ProfitVo> list = new ArrayList<ProfitVo>();
		list.add(p1);
		list.add(p2);
		list.add(p3);
		return list;
	}

}
