package com.kyx.biz.wechat.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.kyx.biz.wechat.service.WechatCallBackService;
import com.kyx.biz.wxutil.MessageFormat;
import com.kyx.biz.wxutil.MessageUtil;

@Service("wechatCallBackService")
public class WechatCallBackServiceImpl implements WechatCallBackService {

	@Override
	public String subscribeNotice(Map<String, String> map) {
		String event = map.get("Event");
		String message = null;
		String fromUserName = map.get("FromUserName");// 公众号
		String toUserName = map.get("ToUserName");// 粉丝号
		String msgType = map.get("MsgType");// 发送的消息类型[比如 文字,图片,语音。。。]
		String content = map.get("Content");// 发送的消息内容
		if (MessageUtil.MESSAGE_EVENT.equals(msgType)&& "subscribe".equals(event)) {// 关注
			message = MessageFormat.initText(toUserName, fromUserName,"感谢您关注!点击【我的会员卡】完成绑定或自助开卡后即可享受便捷的点子会员卡服务，查询、预约、消费提醒，随时随地为您服务。");
		} else if (MessageUtil.MESSAGE_EVENT.equals(msgType)&& "unsubscribe".equals(event)) {// 取消关注

		}else if(MessageUtil.MESSAGE_EVENT.equals(msgType)&& "VIEW".equals(event)){//我的会员菜单//服务预约
			
		}
		return message;
	}

}
