package com.kyx.biz.remind.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.annotation.SystemControllerLog;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.user.model.User;
import com.kyx.basic.user.service.UserService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.appoint.service.AppointService;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.remind.model.Invitation;
import com.kyx.biz.remind.model.Remind;
import com.kyx.biz.remind.service.RemindService;

/**
 * 提醒控制层
 * 
 * @author daibin
 * @date 2019-4-10 上午10:04:24
 * 
 */
@Controller
@RequestMapping(value = "/remind")
public class RemindController extends BaseController {

	@Resource
	private RemindService remindService;

	@Resource
	private UserService userService;

	@Resource
	private AppointService appointService;

	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model) {
		Dbs.setDbName(Dbs.getMainDbName());
		Map<Integer, String> userMap = userService
				.getUserMap(getShopId(request));
		model.addAttribute("userMap", JSON.toJSONString(userMap));

		return "remind/remindInfo";
	}

	/**
	 * 查询日常提醒
	 * 
	 * @param remind
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/queryOldReminds", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String queryOldReminds(Remind remind, Pager pager) {
		GrdData result = remindService.queryOldReminds(remind, pager);

		return JSON.toJSONString(result);
	}

	/**
	 * 结束界面
	 * 
	 * @return
	 */
	@RequestMapping("/finishIndex")
	public String finishIndex(HttpServletRequest request, Model model) {
		Dbs.setDbName(Dbs.getMainDbName());
		Map<Integer, String> userMap = userService
				.getUserMap(getShopId(request));
		model.addAttribute("userMap", JSON.toJSONString(userMap));

		return "remind/finishIndex";
	}

	/**
	 * 邀约首页
	 * 
	 * @return
	 */
	@RequestMapping("/invitationIndex")
	public String invitationIndex() {

		return "remind/invitationIndex";
	}

	/**
	 * 查询邀约服务信息
	 * 
	 * @param remind
	 * @return
	 */
	@RequestMapping(value = "/queryInvitationServer", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String queryInvitationServer(Remind remind,
			HttpServletRequest request) {
		remind.setShopId(getShopId(request));
		GrdData result = remindService.queryInvitationServer(remind);

		return JSON.toJSONString(result);
	}

	/**
	 * 查询邀约车辆信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryInvitationCar", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String queryInvitationCar(Remind remind, HttpServletRequest request) {
		remind.setShopId(getShopId(request));
		GrdData result = remindService.queryInvitationCar(remind);

		return JSON.toJSONString(result);
	}

	/**
	 * 添加提醒界面
	 * 
	 * @return
	 */
	@RequestMapping("/addRemindView")
	public String addRemindView(Model model, HttpServletRequest request) {
		Dbs.setDbName(Dbs.getMainDbName());
		List<User> userList = userService.getByShopId(getShopId(request));
		model.addAttribute("userList", JSON.toJSONString(userList));

		return "remind/addRemind";
	}

	/**
	 * 添加提醒
	 * 
	 * @param remind
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addRemind", produces = "text/plain; charset=utf-8")
	@ResponseBody
	@SystemControllerLog(module = "提醒", description = "添加提醒")
	public String addRemind(Remind remind, HttpServletRequest request) {
		remind.setShopId(getShopId(request));
		remind.setUserId(getUserId(request));
		RetInfo retInfo = remindService.addRemind(remind);

		return AppUtils.getReturnInfo(retInfo);
	}

	/**
	 * 完成服务提醒
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/carryOutServer", produces = "text/plain; charset=utf-8")
	@ResponseBody
	@SystemControllerLog(module = "提醒", description = "完成服务提醒")
	public String carryOutServer(Integer id, HttpServletRequest request) {
		RetInfo retInfo = remindService.carryOutServer(id);

		return AppUtils.getReturnInfo(retInfo);
	}

	/**
	 * 完成车辆提醒
	 * 
	 * @param invitation
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/carryOutCar", produces = "text/plain; charset=utf-8")
	@ResponseBody
	@SystemControllerLog(module = "提醒", description = "完成车辆提醒")
	public String carryOutCar(Invitation invitation, HttpServletRequest request) {
		RetInfo retInfo = remindService.carryOutCar(invitation);

		return AppUtils.getReturnInfo(retInfo);
	}

	/**
	 * 邀约历史
	 * 
	 * @return
	 */
	@RequestMapping("/invitationHistory")
	public String invitationHistory() {

		return "remind/invitationHistory";
	}

	/**
	 * 回复预约提醒
	 * 
	 * @return
	 */
	@RequestMapping("/replyRemindView")
	public String replyRemindView(Integer id, Model model) {
		model.addAttribute("appoint", appointService.getAppointById(id));

		return "remind/replyRemind";
	}

	/**
	 * 完成提醒
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/carryOutRemind", produces = "text/plain; charset=utf-8")
	@ResponseBody
	@SystemControllerLog(module = "提醒", description = "完成提醒")
	public String carryOutRemind(Integer id, HttpServletRequest request) {
		RetInfo retInfo = remindService.carryOutRemind(id);

		return AppUtils.getReturnInfo(retInfo);
	}

	/**
	 * 回复预约提醒
	 * 
	 * @return
	 */
	@RequestMapping("/replyServerView")
	public String replyServerView(Remind remind, Model model,
			HttpServletRequest request) {
		remind.setShopId(getShopId(request));
		model.addAttribute("invitation",
				remindService.queryInvitationServerById(remind));

		return "remind/replyServer";
	}
}
