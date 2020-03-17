package com.kyx.basic.sms.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.sms.mapper.SmsRechargeMapper;
import com.kyx.basic.sms.mapper.SmsSendMapper;
import com.kyx.basic.sms.model.SmsRecharge;
import com.kyx.basic.sms.model.SmsSend;
import com.kyx.basic.sms.service.SmsSendService;
import com.kyx.basic.sms.service.SmsService;
import com.kyx.basic.sms.util.SyMsgUtil;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.ResourceUtil;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.wechat.vo.TemplateDataVo;
@Service("smsService")
public class SmsServiceImpl implements SmsService {
	@Resource
	private SmsRechargeMapper smsRechargeMapper;
	@Resource
	private SmsSendMapper smsSendMapper;
	@Resource
	private SmsSendService smsSendService;

	@Override
	public GrdData getRechargePage(SmsRecharge smsRecharge, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<SmsRecharge> list=smsRechargeMapper.getList(smsRecharge);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public GrdData getSendPage(SmsSend smsSend, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<SmsSend> list=smsSendMapper.getList(smsSend);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public RetInfo sendSms(TemplateDataVo tplVo,String mobile, Integer msgType,Integer shopId) throws ClientProtocolException, IOException {
		RetInfo retInfo = RetInfo.Error("");
		if(StringUtils.isNotBlank(mobile)){
			List<String> mms = new ArrayList<String>();//组装关键词list
			mms.add(tplVo.getFirst()==null?"":tplVo.getFirst());
			mms.add(tplVo.getKeyword1()==null?"":tplVo.getKeyword1());
			mms.add(tplVo.getKeyword2()==null?"":tplVo.getKeyword2());
			mms.add(tplVo.getKeyword3()==null?"":tplVo.getKeyword3());
			mms.add(tplVo.getKeyword4()==null?"":tplVo.getKeyword4());
			mms.add(tplVo.getKeyword5()==null?"":tplVo.getKeyword5());
			mms.add(tplVo.getRemark()==null?"":tplVo.getRemark());
			
			
			String configFileName = "sms.properties";
			String smsModel =  ResourceUtil.getConfigNoCache(configFileName,msgType.toString()); //短信模版
			StringBuffer content = new StringBuffer("");
			
			if (smsModel != null) {
				String[] ss = smsModel.split("\\{\\d\\}");
				List<String> mml = new ArrayList<String>();
				for (int j = 0; j < ss.length; j++) {
					mml.add(ss[j]);
				}
				for (int j = 0; j < mml.size(); j++) {
					content.append(mml.get(j));
					content.append(j < mms.size() ? mms.get(j) : "");
				}
			}
			retInfo = smsSendService.sendMessage(shopId, mobile, content.toString(), 1);
		}
		return retInfo;
	}

}
