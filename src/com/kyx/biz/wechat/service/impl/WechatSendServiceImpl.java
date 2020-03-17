package com.kyx.biz.wechat.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.kyx.basic.db.Dbs;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.shops.service.ShopsService;
import com.kyx.basic.sms.service.SmsService;
import com.kyx.basic.sysparam.service.SysParamService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.custom.mapper.CustomMapper;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.orders.mapper.OrdersMapper;
import com.kyx.biz.orders.model.Orders;
import com.kyx.biz.remind.model.Invitation;
import com.kyx.biz.wechat.config.MainConfiguration;
import com.kyx.biz.wechat.mapper.WechatSendRecordMapper;
import com.kyx.biz.wechat.mapper.WechatTemplateMapper;
import com.kyx.biz.wechat.model.WechatConfig;
import com.kyx.biz.wechat.model.WechatCustomer;
import com.kyx.biz.wechat.model.WechatTemplate;
import com.kyx.biz.wechat.service.WechatConfigService;
import com.kyx.biz.wechat.service.WechatCustomerService;
import com.kyx.biz.wechat.service.WechatSendService;
import com.kyx.biz.wechat.vo.TemplateDataVo;
import com.kyx.biz.wechat.vo.WechartSendVo;

@Service("wechatSendService")
public class WechatSendServiceImpl implements WechatSendService {
	@Resource
	WechatTemplateMapper wechatTemplateMapper;
	@Resource
	WechatSendRecordMapper wechatSendRecordMapper;

	@Resource
	private ShopsService shopsService;

	@Resource
	private WechatCustomerService wechatCustomerService;

	@Resource
	private WechatConfigService wechatConfigService;

	@Resource
	private OrdersMapper ordersMapper;

	@Resource
	private CustomMapper customMapper;

	private static final String COMPULSORY_MSG = "交强险即将到期";

	private static final String COMMERCIAL_MSG = "商业险险即将到期";

	private static final String CHECK_MSG = "车辆年检已到期";

	private static final String MAINTAIN_MSG = "车辆保养已到期";

	private static final String CHECK_REMARK = "在办理年检之前，请确认无任何未处理违法记录。我们提供待办年检业务，可以立刻进行【预约】";

	@Resource
	private SysParamService sysParamService;

	@Resource
	private SmsService smsService;

	@Override
	public RetInfo sendInfo(WechartSendVo sendVo) throws Exception {
		RetInfo ret = RetInfo.Success("推送成功");
		WechatTemplate wechatTemplate = wechatTemplateMapper.selectByWechatIdAndType(sendVo.getWechatId(), sendVo.getType());
		MainConfiguration mainConfig = new MainConfiguration(sendVo.getAppId(), sendVo.getAppSecret(), sendVo.getToken(), sendVo.getAesKey());
		WxMpService wxMpService = mainConfig.wxMpService();
		WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder().toUser(sendVo.getOpenId()).templateId(wechatTemplate.getTemplateId()).url(sendVo.getUrl()).build();
		TemplateDataVo templateDataVo = sendVo.getTemplateDataVo();
		if (templateDataVo == null) {
			ret.setSuccess(false);
			ret = RetInfo.Error("推送内容不正确，请检查后重新推送");
			return ret;
		}
		ret.setSuccess(true);
		setInfoToTemplateMessage(templateMessage, templateDataVo);
		String msg = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
		// 保存微信推送记录
		// WechatSendRecord record = new WechatSendRecord();
		// record.setOpenid(sendVo.getOpenId());
		// record.setSendTime(new Date());
		// record.setShopId(sendVo.getShopId());
		// record.setContent(templateMessage);
		// record.setContent("111");
		// wechatSendRecordMapper.insertSelective(record);

		// System.out.println("微信推送返回的信息:" + msg);
		return ret;

	}

	/**
	 * 通过反射拼装数据
	 * 
	 * @param templateMessage
	 * @param templateDataVo
	 */
	private void setInfoToTemplateMessage(WxMpTemplateMessage templateMessage, TemplateDataVo templateDataVo) {
		Field[] field = templateDataVo.getClass().getDeclaredFields();
		for (int i = 0; i < field.length; i++) {
			String fieldName = field[i].getName();
			String value = "";
			try {
				String methname = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1); // 将属性的首字符大写，方便构造get，set方法
				Method m = templateDataVo.getClass().getMethod("get" + methname);
				value = (String) m.invoke(templateDataVo);
			} catch (Exception e) {
				value = "";
				// e.printStackTrace();
			}
			if (StringUtils.isNotEmpty(value)) {// 通过反射对templateMessage
				templateMessage.addData(new WxMpTemplateData(fieldName, value, "#004a96"));
			}
		}
	}

	@Override
	public RetInfo pcSendInfo(WechartSendVo sendVo, Integer shopId) throws Exception {
		Dbs.setDbName(Dbs.getMainDbName());
		RetInfo ret = sendInfo(sendVo);
		Shops shops = shopsService.getById(shopId);
		Dbs.setDbName(shops.getDbName());

		return ret;
	}

	@Override
	public RetInfo sendAppointInfo(TemplateDataVo tplVo, String cardNo, Integer shopId) throws Exception {
		RetInfo ret = RetInfo.Success("");
		if (sysParamService.getValueByName(SysParamService.ENABLE_WECHAT_PUSH, shopId) == "1") { // 启用微信推送
			Dbs.setDbName(Dbs.getMainDbName());
			WechatCustomer customer = wechatCustomerService.getWechatCustomerByCardNo(cardNo);// 查询appid和openid
			if (StringUtils.isEmpty(customer.getAppId())) {
				return RetInfo.Error("用户没有绑定微信");
			}

			String appId = customer.getAppId();
			String openId = customer.getOpenId();

			// 在查config
			WechatConfig config = wechatConfigService.getByAppId(appId);
			WechartSendVo sendVo = AppUtils.getWechatConfig(config);
			sendVo.setOpenId(openId);
			sendVo.setTemplateDataVo(tplVo);
			sendVo.setType(WechartSendVo.APPOINT_TYPE);

			ret = pcSendInfo(sendVo, shopId);

		}
		if (sysParamService.getValueByName(SysParamService.ENABLE_SMS_PUSH, shopId) == "1") {// 启用短信推送
			Custom custom = customMapper.selectByCardNo(cardNo);
			ret = smsService.sendSms(tplVo, custom.getCellphone(), WechartSendVo.APPOINT_TYPE, shopId);
		}
		return ret;
	}

	@Override
	public RetInfo sendOrderConsume(Integer orderId, Shops shops) throws Exception {
		 	RetInfo ret = RetInfo.Success("");
			Orders orders = ordersMapper.selectByPrimaryKey(orderId);
			if (orders.getCustId() != null) {
				TemplateDataVo tplVo = new TemplateDataVo();
				tplVo.setFirst(orders.getCarNumber());
				tplVo.setKeyword1(AppUtils.date2String(new Date(), AppUtils.TIME_FORMAT_TO_DATE_TIME));
				tplVo.setKeyword2(orders.getOrderAmt().toString() + "元");
				tplVo.setKeyword3(shops.getShopName());
				tplVo.setRemark("感谢您的使用，点击查看详细信息");
	
				Custom custom = customMapper.selectByPrimaryKey(orders.getCustId());
				if (sysParamService.getValueByName(SysParamService.ENABLE_WECHAT_PUSH, shops.getId()) == "1") { // 启用微信推送
					Dbs.setDbName(Dbs.getMainDbName());
					// 查询appid和openid
					WechatCustomer customer = wechatCustomerService.getWechatCustomerByCardNo(custom.getCardNo());
					if (customer != null) {
						String appId = customer.getAppId();
						String openId = customer.getOpenId();
						
						// 在查config
						WechatConfig config = wechatConfigService.getByAppId(appId);
						WechartSendVo sendVo = AppUtils.getWechatConfig(config);
						sendVo.setOpenId(openId);
						sendVo.setTemplateDataVo(tplVo);
						sendVo.setType(WechartSendVo.CONSUME_TYPE);
						ret = pcSendInfo(sendVo, shops.getId());
					}
				}
				
				if (sysParamService.getValueByName(SysParamService.ENABLE_SMS_PUSH, shops.getId()) == "1") {// 启用短信推送
					ret = smsService.sendSms(tplVo, custom.getCellphone(), WechartSendVo.CONSUME_TYPE, shops.getId());
				}
			}
			return ret;
	}

	@Override
	public RetInfo sendCarInvitation(Invitation invitation, Integer shopId) throws Exception {
		RetInfo ret = new RetInfo();
		String msg = "";
		boolean bool = true;
		invitation.setShopId(shopId);
		// 车检
		if (invitation.getIsCheckDate()) {
			RetInfo retInfo = sendCarCheck(invitation, shopId);
			bool = retInfo.isSuccess();
			msg += "车检" + retInfo.getRetMsg() + ";";
		}

		if (!bool) {
			return RetInfo.Error("用户没有绑定微信");
		}

		// 交强险
		if (invitation.getIsCompulsoryDate()) {
			RetInfo retInfo = sendCompulsory(invitation, shopId);
			bool = retInfo.isSuccess();
			msg += "交强险" + retInfo.getRetMsg() + ";";
		}

		if (!bool) {
			return RetInfo.Error("用户没有绑定微信");
		}

		// 商业险
		if (invitation.getIsCommercialDate()) {
			RetInfo retInfo = sendCommercial(invitation, shopId);
			bool = retInfo.isSuccess();
			msg += "商业险" + retInfo.getRetMsg() + ";";
		}

		if (!bool) {
			return RetInfo.Error("用户没有绑定微信");
		}

		// 保养
		if (invitation.getIsMaintainDate()) {
			RetInfo retInfo = sendMaintain(invitation,shopId);
			bool = retInfo.isSuccess();
			msg += "保养" + retInfo.getRetMsg() + ";";
		}

		if (!bool) {
			return RetInfo.Error("用户没有绑定微信");
		}

		ret = new RetInfo(bool, msg);
		return ret;
	}

	/**
	 * 发送车辆保险到期
	 * 
	 * @param invitation
	 * @return
	 * @throws Exception
	 */
	private RetInfo sendCarInsurance(Invitation invitation, TemplateDataVo tplVo, Integer shopId) throws Exception {
		RetInfo ret = RetInfo.Error("");
		tplVo.setKeyword1(invitation.getCarNumber());
		if (sysParamService.getValueByName(SysParamService.ENABLE_WECHAT_PUSH, shopId) == "1") { // 启用微信推送
			WechartSendVo sendVo = getWechartSendVo(invitation.getCardNo(), tplVo, WechartSendVo.CAR_INSURANCE_TYPE);
			if (sendVo == null) {
				return RetInfo.Error("用户没有绑定微信");
			}

			ret = pcSendInfo(sendVo, invitation.getShopId());
		}
		
		if (sysParamService.getValueByName(SysParamService.ENABLE_SMS_PUSH,shopId) == "1") {// 启用短信推送
			Custom custom = customMapper.selectByCardNo(invitation.getCardNo());
			ret = smsService.sendSms(tplVo, custom.getCellphone(), WechartSendVo.CAR_INSURANCE_TYPE, shopId);
		}

		return ret;
	}

	/**
	 * 交强险
	 * 
	 * @param invitation
	 * @return
	 * @throws Exception
	 */
	private RetInfo sendCompulsory(Invitation invitation, Integer shopId) throws Exception {
		TemplateDataVo tplVo = new TemplateDataVo();
		String carNo = invitation.getCarNumber();
		String title = BasicContant.CAR_INVITATION_TITLE + carNo + COMPULSORY_MSG;
		tplVo.setFirst(title);

		String date = AppUtils.date2String1(invitation.getCompulsoryDate());
		tplVo.setKeyword2(date);

		RetInfo ret = sendCarInsurance(invitation, tplVo, shopId);
		return ret;
	}

	/**
	 * 年检到期
	 * 
	 * @param invitation
	 * @return
	 * @throws Exception
	 */
	@Override
	public RetInfo sendCarCheck(Invitation invitation, Integer shopId) throws Exception {
		RetInfo ret = RetInfo.Error("");
		// 回复模板
		TemplateDataVo tplVo = new TemplateDataVo();
		String carNo = invitation.getCarNumber();
		String title = BasicContant.CAR_INVITATION_TITLE + carNo + CHECK_MSG;
		tplVo.setFirst(title);
		tplVo.setKeyword1(carNo);
		
		String date = AppUtils.date2String1(invitation.getCheckDate());
		tplVo.setKeyword2(date);
		tplVo.setRemark(CHECK_REMARK);
		if (sysParamService.getValueByName(SysParamService.ENABLE_WECHAT_PUSH, shopId) == "1") { // 启用微信推送
			WechartSendVo sendVo = getWechartSendVo(invitation.getCardNo(), tplVo, WechartSendVo.CAR_CHECKDATE_TYPE);
			if (sendVo == null) {
				return RetInfo.Error("用户没有绑定微信");
			}
			ret = pcSendInfo(sendVo, invitation.getShopId());
		}
		
		
		if (sysParamService.getValueByName(SysParamService.ENABLE_SMS_PUSH, shopId) == "1") {// 启用短信推送
			Custom custom = customMapper.selectByCardNo(invitation.getCardNo());
			ret = smsService.sendSms(tplVo, custom.getCellphone(), WechartSendVo.CAR_CHECKDATE_TYPE, shopId);
		}
		

		return ret;
	}

	/**
	 * 商业险
	 * 
	 * @param invitation
	 * @return
	 * @throws Exception
	 */
	private RetInfo sendCommercial(Invitation invitation, Integer shopId) throws Exception {
		TemplateDataVo tplVo = new TemplateDataVo();
		String carNo = invitation.getCarNumber();
		String title = BasicContant.CAR_INVITATION_TITLE + carNo + COMMERCIAL_MSG;
		tplVo.setFirst(title);

		String date = AppUtils.date2String1(invitation.getCommercialDate());
		tplVo.setKeyword2(date);

		RetInfo ret = sendCarInsurance(invitation, tplVo, shopId);
		return ret;
	}

	/**
	 * 发送保养
	 * 
	 * @param invitation
	 * @return
	 * @throws Exception
	 */
	@Override
	public RetInfo sendMaintain(Invitation invitation,Integer shopId) throws Exception {
		TemplateDataVo tplVo = new TemplateDataVo();
		String carNo = invitation.getCarNumber();
		String title = BasicContant.CAR_INVITATION_TITLE + carNo + MAINTAIN_MSG;
		tplVo.setFirst(title);

		String date = AppUtils.date2String1(invitation.getMaintainDate());
		tplVo.setKeyword2(date);

		tplVo.setKeyword1(invitation.getCarNumber());
		
		RetInfo ret = RetInfo.Success("");
		if(sysParamService.getValueByName(SysParamService.ENABLE_WECHAT_PUSH, shopId) == "1"){ //启用微信推送
			WechartSendVo sendVo = getWechartSendVo(invitation.getCardNo(), tplVo, WechartSendVo.CAR_MAINTAIN_TYPE);
			ret = pcSendInfo(sendVo, invitation.getShopId());
		}
		
		if (sysParamService.getValueByName(SysParamService.ENABLE_SMS_PUSH, shopId) == "1") {// 启用短信推送
			Custom custom = customMapper.selectByCardNo(invitation.getCardNo());
			ret = smsService.sendSms(tplVo, custom.getCellphone(), WechartSendVo.CAR_MAINTAIN_TYPE,shopId);
		}

		return ret;
	}

	private WechartSendVo getWechartSendVo(String cardNo, TemplateDataVo tplVo, Integer sendVoType) {
		Dbs.setDbName(Dbs.getMainDbName());
		// 查询appid和openid
		WechatCustomer customer = wechatCustomerService.getWechatCustomerByCardNo(cardNo);
		if (customer == null || StringUtils.isEmpty(customer.getAppId())) {
			return null;
		}
		String appId = customer.getAppId();
		String openId = customer.getOpenId();

		// 在查config
		WechatConfig config = wechatConfigService.getByAppId(appId);
		WechartSendVo sendVo = AppUtils.getWechatConfig(config);
		sendVo.setOpenId(openId);
		sendVo.setTemplateDataVo(tplVo);
		sendVo.setType(sendVoType);

		return sendVo;
	}

	@Override
	public RetInfo sendServer(TemplateDataVo tplVo, Integer shopId, String cardNo) throws Exception {
		RetInfo ret = RetInfo.Success("");
		if(sysParamService.getValueByName(SysParamService.ENABLE_WECHAT_PUSH, shopId) == "1"){ //启用微信推送
			WechartSendVo sendVo = getWechartSendVo(cardNo, tplVo, WechartSendVo.SERVER_TYPE);
			if (sendVo == null) {
				return RetInfo.Error("用户没有绑定微信");
			}
			ret = pcSendInfo(sendVo, shopId);
		}
		
		if (sysParamService.getValueByName(SysParamService.ENABLE_SMS_PUSH, shopId) == "1") {// 启用短信推送
			Custom custom = customMapper.selectByCardNo(cardNo);
			ret = smsService.sendSms(tplVo, custom.getCellphone(), WechartSendVo.APPOINT_TYPE,shopId);
		}
		
		return ret;
	}
}
