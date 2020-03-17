package com.kyx.biz.wechatpublic.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.wechatpublic.mapper.WechatCardRecordMapper;
import com.kyx.biz.wechatpublic.model.WechatCardRecord;
import com.kyx.biz.wechatpublic.service.WechatCardRecordService;
@Service("wechatCardRecordService")
public class WechatCardRecordServiceImpl implements WechatCardRecordService {
	@Resource
	private WechatCardRecordMapper wechatCardRecordMapper;

	@Override
	public int save(WechatCardRecord record) {
		
		return wechatCardRecordMapper.insertSelective(record);
	}

	@Override
	public GrdData getInfo(WechatCardRecord wechatCardRecord, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<WechatCardRecord> list=wechatCardRecordMapper.getInfo(wechatCardRecord);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public RetInfo updateData(String ids, String voidRemark) {
		RetInfo retInfo;
		if(StringUtils.isEmpty(ids)){
			retInfo=RetInfo.Error("请选择作废的卡卷");
			return retInfo;
		}
		if(StringUtils.isEmpty(voidRemark)){
			retInfo=RetInfo.Error("作废原因不能为空");
			return retInfo;
		}
		String[] idArr=ids.split(",");
		for(String sid:idArr){
			Integer id=Integer.valueOf(sid);
			WechatCardRecord record=new WechatCardRecord();
			record.setId(id);
			record.setVoidRemark(voidRemark);
			record.setState(2);//作废
			wechatCardRecordMapper.updateByPrimaryKeySelective(record);
		}
		retInfo=RetInfo.Success("作废成功");
		return retInfo;
	}




	@Override
	public List<WechatCardRecord> selectByCustId(Integer custId) {
		return wechatCardRecordMapper.selectByCustId(custId);
	}

	@Override
	public int selectCountByCustId(Integer custId) {
		return wechatCardRecordMapper.selectCountByCustId(custId);
	}

	@Override
	public WechatCardRecord selectById(Integer id) {
		
		return wechatCardRecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public WechatCardRecord selectByPrimaryKey(Integer id) {
		return wechatCardRecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public RetInfo updateWechatCardRecord(WechatCardRecord record) {
		wechatCardRecordMapper.updateByPrimaryKeySelective(record);
		return RetInfo.Success("更新成功");
	}
	
	@Override
	public RetInfo submitCardset(String cardsetIds, Integer custId, Integer orderId) {
		if(cardsetIds == null ||  custId == null || orderId == null){
			return RetInfo.Error("选择优惠券有误");
		}
		String [] cardsetIdArr = cardsetIds.split(",");
		for(int i = 0;i < cardsetIdArr.length; i++){
			if(StringUtils.isNotBlank(cardsetIdArr[i])){
				Integer cardsetId = Integer.valueOf(cardsetIdArr[i]);
				WechatCardRecord cardRecord =  selectByPrimaryKey(cardsetId);
				if(cardRecord != null){
					if(cardRecord.getState() == 0){
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(new Date());
						calendar.set(Calendar.HOUR_OF_DAY, 0);
			            calendar.set(Calendar.MINUTE, 0);
			            calendar.set(Calendar.SECOND, 0);
			            if (cardRecord.getEndDate().after(calendar.getTime())){
			            	
			            	
						}else{
							return RetInfo.Error("优惠券已过期");
						}
					}else{
						return RetInfo.Error("优惠券已被使用或已作废");
					}
				}else{
					return RetInfo.Error("优惠券信息有误");
				}
			}
		}
		
		for(int i = 0;i < cardsetIdArr.length; i++){
			if(StringUtils.isNotBlank(cardsetIdArr[i])){
				Integer cardsetId = Integer.valueOf(cardsetIdArr[i]);
				WechatCardRecord updateRecord = new WechatCardRecord();
				updateRecord.setId(cardsetId);
				updateRecord.setState(3); //已占用
				updateWechatCardRecord(updateRecord); //更新成已使用
			}
		}
		
		 int cardsetCount =  selectCountByCustId(custId);
		 return RetInfo.Success(cardsetCount+"");
	}
}
