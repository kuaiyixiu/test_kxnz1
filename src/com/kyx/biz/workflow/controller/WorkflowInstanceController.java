package com.kyx.biz.workflow.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.workflow.model.WorkflowCheckUser;
import com.kyx.biz.workflow.model.WorkflowInstance;
import com.kyx.biz.workflow.model.WorkflowInstanceNode;
import com.kyx.biz.workflow.model.WorkflowTemplate;
import com.kyx.biz.workflow.service.WorkflowInstanceService;
import com.kyx.biz.workflow.service.WorkflowTemplateService;


@Controller
@RequestMapping(value = "/workflowInstance")
public class WorkflowInstanceController  extends BaseController{
	
	@Resource
	private WorkflowInstanceService workflowInstanceService;
	
	@Resource
	private WorkflowTemplateService workflowTemplateService;
	
	
	@RequestMapping("/instancelist")
	public String instancelist(Model model) {
		model.addAttribute("kind","1"); //我发起的审批
		return "workflow/instancelist";
	}
	
	@RequestMapping("/allinstancelist")
	public String allinstancelist(Model model) {
		model.addAttribute("kind","2"); //全部审批
		return "workflow/instancelist";
	}
	
	@RequestMapping("/instancenodelist/{instanceId}")
	public String instancenodelist(Model model,@PathVariable("instanceId") Integer instanceId) {
		model.addAttribute("instanceId", instanceId);
		return "workflow/instancenodelist";
	}
	
	//kind 1我发起的审批  2全部审批
	@RequestMapping(value = "/getInstanceList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getInstanceList(HttpServletRequest request,WorkflowInstance workflowInstance,Integer kind, Pager pager) {
		if(kind == 1){
			workflowInstance.setCreateUser(getUserId(request));
		}
		GrdData result = workflowInstanceService.getInstanceList(workflowInstance, pager,getShopId(request));
		return JSON.toJSONString(result);
	}
	@RequestMapping(value = "/getInstanceNodeList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getInstanceNodeList(HttpServletRequest request,WorkflowInstanceNode workflowInstanceNode, Pager pager) {
		GrdData result = workflowInstanceService.getInstanceNodeList(workflowInstanceNode, pager);
		return JSON.toJSONString(result);
	}
	
	
	/**
	 * 待审核列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/checklist")
	public String checklist(Model model) {
		return "workflow/checklist";
	}
	
	@RequestMapping(value = "/getCheckList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getCheckList(HttpServletRequest request,WorkflowInstanceNode workflowInstanceNode, Pager pager) {
		workflowInstanceNode.setCheckUser(getUserId(request));
		GrdData result = workflowInstanceService.getCheckList(workflowInstanceNode, pager);
		return JSON.toJSONString(result);
	}
	
	@RequestMapping("/checknode")
	public String checknode(Model model,Integer id) {
		WorkflowInstanceNode node = workflowInstanceService.selectNodeById(id);
		model.addAttribute("node", node);
		return "workflow/checknode";
	}
	
	
	@RequestMapping(value = "/updateCheckNode", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String updateCheckNode(HttpServletRequest request,WorkflowCheckUser workflowInstanceNode) {
		workflowInstanceNode.setOptUser(getUserId(request));
		RetInfo result = workflowInstanceService.updateCheckNode(workflowInstanceNode);
		return JSON.toJSONString(result);
	}
	
	@RequestMapping("/checkUserList")
	public String checkUserList(Model model,HttpServletRequest request,Integer id) {
		List<WorkflowCheckUser>  userList = workflowInstanceService.selectUserListByInstanceNodeId(getShopId(request), id);
		model.addAttribute("userList",userList);
		return "workflow/checkuserlist";
	}
	
	
	@RequestMapping("/addinstance")
	public String addinstance(Model model) {
		List<WorkflowTemplate> templateList = workflowTemplateService.getList(new WorkflowTemplate());
		model.addAttribute("templateList", templateList);
		return "workflow/addinstance";
	}
	
	
	@RequestMapping(value = "/createInstance", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String createInstance(HttpServletRequest request,WorkflowInstance workflowInstance) {
		workflowInstance.setCreateUser(getUserId(request));
		workflowInstance.setCreateTime(new Date());
		RetInfo result = workflowInstanceService.createInstance(workflowInstance);
		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/backInstance", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String backInstance(HttpServletRequest request,Integer ids) {
		RetInfo result = workflowInstanceService.backInstance(ids);
		return JSON.toJSONString(result);
	}
}
