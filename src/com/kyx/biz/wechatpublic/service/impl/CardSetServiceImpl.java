package com.kyx.biz.wechatpublic.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import me.chanjar.weixin.mp.api.WxMpService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.custom.mapper.CustomMapper;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.wechatpublic.mapper.CardSetMapper;
import com.kyx.biz.wechatpublic.model.CardSet;
import com.kyx.biz.wechatpublic.model.WechatCardRecord;
import com.kyx.biz.wechatpublic.service.CardSetService;
import com.kyx.biz.wechatpublic.service.WechatCardRecordService;
@Service("cardSetService")
public class CardSetServiceImpl implements CardSetService {
	@Resource
    private CardSetMapper cardSetMapper;
	@Resource
	private CustomMapper customMapper;
	@Resource
	private WechatCardRecordService wechatCardRecordService;
	
	
	@Override
	public GrdData getInfo(CardSet cardSet, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<CardSet> list=cardSetMapper.getInfo(cardSet);
		return new GrdData(page.getTotal(),list);
	}
	@Override
	public RetInfo saveData(CardSet cardSet, HttpSession httpSession) {
		RetInfo retInfo;
		int count=0;
		if(cardSet.getId()==null){//新增
			count=cardSetMapper.insertSelective(cardSet);
		}else{
			count=cardSetMapper.updateByPrimaryKeySelective(cardSet);
		}
		if(count>0){
			retInfo=new RetInfo("success","保存成功");
		}else
			retInfo=new RetInfo("error","保存失败");
		return retInfo;
	}
	@Override
	public CardSet getById(Integer id) {
		return cardSetMapper.selectByPrimaryKey(id);
	}
	@Override
	public RetInfo sendCardInfo(WxMpService wxMpService,Custom custom) {
		if(StringUtils.isNotEmpty(custom.getChooseAdids())){//点选客户发送
			String[] ids=custom.getChooseAdids().split(",");
			for(String id:ids){
				Custom cust=customMapper.selectByPrimaryKey(Integer.valueOf(id));
				cust.setWechatCardId(custom.getWechatCardId());
				for(int i=0;i<custom.getSumNum();i++)
					sendCard(wxMpService,cust);
				
			}
		}else{//全选发送
			List<Custom> customs=customMapper.queryCustoms(custom);
			for(Custom cust:customs){
				cust.setWechatCardId(custom.getWechatCardId());
				for(int i=0;i<custom.getSumNum();i++)
					sendCard(wxMpService,cust);
			}
		}
		
		return RetInfo.Success("派送成功");
	}
	private void sendCard(WxMpService wxMpService, Custom cust) {
		CardSet cardSet=cardSetMapper.selectByPrimaryKey(cust.getWechatCardId());
		//产生优惠券记录
		WechatCardRecord record=new WechatCardRecord();
		record.setWechatCardName(cardSet.getCardName());
		record.setWechatCardValue(cardSet.getCardValue());
		record.setWechatName(cust.getWechatName());
		record.setCustId(cust.getId());
		record.setEndDate(cardSet.getEndDate());
		record.setGetDate(new Date());
		record.setState(0);//未使用
		record.setCardsetId(cardSet.getId());
		record.setCardsetRemark(cardSet.getRemark());
		record.setRemark(WechatCardRecord.SYSSHARE);//系统派发
		wechatCardRecordService.save(record);
		//存在关注公众号的记录进行公众号信息推送
		//查看指定连接到优惠券列表
		//wxMpService.s
		
	}
	@Override
	public CardSet selectByKey(Integer id) {
		return cardSetMapper.selectByPrimaryKey(id);
	}
	

}
