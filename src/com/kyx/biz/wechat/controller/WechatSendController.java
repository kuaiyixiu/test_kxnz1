package com.kyx.biz.wechat.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.remind.model.Invitation;
import com.kyx.biz.wechat.service.WechatSendService;
import com.kyx.biz.wechat.vo.TemplateDataVo;

/**
 * 推送接口
 * 
 * @author daibin
 * @date 2019-5-22 上午9:28:10
 * 
 */
@RestController
@RequestMapping(value = "/send")
public class WechatSendController extends BaseController {
	@Resource
	private WechatSendService wechatSendService;

	/**
	 * 发送客户预约
	 * 
	 * @param request
	 * @param sendVo
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/appoint")
	public String sendAppoint(HttpServletRequest request, TemplateDataVo tplVo,
			String cardNo) throws Exception {
		RetInfo ret = wechatSendService.sendAppointInfo(tplVo, cardNo,
				getShopId(request));

		return AppUtils.getReturnInfo(ret);
	}

	/**
	 * 车辆邀约
	 * 
	 * @param request
	 * @param invitation
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/carInvitation")
	public String sendCarInvitation(HttpServletRequest request,
			Invitation invitation) throws Exception {
		invitation.setShopId(getShopId(request));
		RetInfo ret = wechatSendService.sendCarInvitation(invitation,
				getShopId(request));

		return AppUtils.getReturnInfo(ret);
	}

	@PostMapping("/server")
	public String sendServer(HttpServletRequest request, TemplateDataVo tplVo,
			String cardNo) throws Exception {
		RetInfo ret = wechatSendService.sendServer(tplVo, getShopId(request),
				cardNo);

		return AppUtils.getReturnInfo(ret);
	}

}
