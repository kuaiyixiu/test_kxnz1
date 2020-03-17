package com.kyx.basic.sms.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kyx.basic.sms.model.SmsCallback;
import com.kyx.basic.sms.service.SmsCallBackService;
import com.kyx.biz.base.controller.BaseController;

@Controller
@RequestMapping("/smsCallback")
public class SmsCallbackController extends BaseController {
	
	@Resource
	private SmsCallBackService smsCallBackService;
	
//	Status	
//	MBBLACK 黑名单号码
//	NOROUTE 无通道
//	ROUTEERR 通道异常
//	REJECT 审核驳回
//	DISTURB 手机号码发送次数过多
//	EMSERR 长短信不完整
//	SIGNERR 签名错
//	KEYWORD 敏感词
//	WHITESMS 短信内容不在白名单中
//	CMPP20ERR:XXX / SMGPERR:XXX /SGIPERR:XXX /
//	RESP:XXX
//	提交到网关时网关返回了 XXX
//	的错误码
//	其他 网关状态
	@RequestMapping(value = "/receiveMsg")
	@ResponseBody
	public String receiveMsg(String report) throws ParseException {
		System.out.println(report);
		if (StringUtils.isNotBlank(report)){
			List<SmsCallback> list = new ArrayList<SmsCallback>();
			String[] msgArr = report.split(";");
			for(String msg : msgArr){
				String[] infoArr = msg.split(",");
				String msgId = infoArr[0];
				String reportTime = infoArr[1];
				String mobile = infoArr[2];
				String status = infoArr[3];
				SmsCallback sc = new SmsCallback();
				sc.setMsgId(msgId);
				sc.setMobile(mobile);
				sc.setStatus(status);
				sc.setPlatType(2);
				sc.setTime(DateUtils.parseDate(reportTime, "yyyyMMddHHmmss"));
				list.add(sc);
			}
			if(list.size() > 0 ){
				smsCallBackService.batchSaveSmsCallBack(list);
			}		
		}
		return "success";
	}
	
    
	
}
