package com.kyx.biz.workflow.controller;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.user.model.User;
import com.kyx.basic.user.service.UserService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.workflow.model.WorkflowNode;
import com.kyx.biz.workflow.model.WorkflowTemplate;
import com.kyx.biz.workflow.service.WorkflowTemplateService;


@Controller
@RequestMapping(value = "/workflowTemplate")
public class WorkflowTemplateController {
	
	@Resource
	private WorkflowTemplateService workflowTemplateService;
	
	@Resource
	private UserService userService;
	
	@RequestMapping("/templatelist")
	public String templatelist(Model model) {
		return "workflow/templatelist";
	}
	
	@RequestMapping("/nodelist/{templateId}")
	public String nodelist(Model model,@PathVariable("templateId") Integer templateId) {
		WorkflowNode node = new WorkflowNode();
		node.setTemplateId(templateId);
		model.addAttribute("node", node);
		return "workflow/nodelist";
	}
	
	
	@RequestMapping("/addtemplate")
	public String addtemplate(Model model) {
		return "workflow/addtemplate";
	}
	
	@RequestMapping(value = "/getTemplateList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getTemplateList(HttpServletRequest request, WorkflowTemplate workflowTemplate, Pager pager) {
		GrdData result = workflowTemplateService.getList(workflowTemplate, pager);
		return JSON.toJSONString(result);
	}
	@RequestMapping(value = "/getNodeList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getList(HttpServletRequest request, WorkflowNode workflowNode, Pager pager) {
		GrdData result = workflowTemplateService.getNodeList(workflowNode, pager);
		return JSON.toJSONString(result);
	}
	
	@RequestMapping("/addnode/{templateId}")
	public String addnode(Model model,@PathVariable("templateId") Integer templateId) {
		List<WorkflowNode> nodelist = workflowTemplateService.getListByTemplateId(templateId);
		model.addAttribute("nodeList", nodelist);
		model.addAttribute("templateId", templateId);
		return "workflow/addnode2";
	}
	
	
	 
	 @RequestMapping("/updateNode")
	 public String updateNode(Model model, Integer id){
		WorkflowNode node =  workflowTemplateService.selectNodeById(id);
	    model.addAttribute("node", node);
	    List<WorkflowNode> nodelist = workflowTemplateService.getListByTemplateId(node.getTemplateId());
	    Iterator<WorkflowNode> it = nodelist.iterator(); //把自己排除了
	    while(it.hasNext()){
	    	WorkflowNode n = it.next();
	    	if(n.getId().equals(id)){
		  		it.remove();
	  		}
	    }
		model.addAttribute("nodelist", nodelist);
	    return "workflow/addnode";
	 }
	 
	 @RequestMapping("/updateTemplate")
	 public String updateTemplate(Model model, Integer id){
		 WorkflowTemplate template =  workflowTemplateService.selectByPrimaryKey(id);
		 model.addAttribute("template", template);
		 return "workflow/addtemplate";
	 }
	 
	
	@RequestMapping("/saveNode")
	@ResponseBody
	public String saveNode(WorkflowNode workflowNode) {
		if (workflowNode.getId() != null) {
			RetInfo retInfo = workflowTemplateService.updateNode(workflowNode);
			return AppUtils.getReturnInfo(retInfo);
		} else {
			RetInfo retInfo = workflowTemplateService.saveNode(workflowNode);
			return AppUtils.getReturnInfo(retInfo);
		}
	}
	
	@RequestMapping("/saveNodeList")
	@ResponseBody
	public String saveNodeList(Integer templateId, @RequestBody List<WorkflowNode> nodeList) {
		RetInfo retInfo = workflowTemplateService.saveNodeList(templateId,nodeList);
		return AppUtils.getReturnInfo(retInfo);
	}
	
	@RequestMapping("/saveTemplate")
	@ResponseBody
	public String saveTemplate(WorkflowTemplate workflowTemplate) {
		if (workflowTemplate.getId() != null) {
			RetInfo retInfo = workflowTemplateService.updateTemplate(workflowTemplate);
			return AppUtils.getReturnInfo(retInfo);
		} else {
			RetInfo retInfo = workflowTemplateService.saveTemplate(workflowTemplate);
			return AppUtils.getReturnInfo(retInfo);
		}
	}
	
	@RequestMapping("/nodeUserList")
	public String nodeUserList(Model model,Integer id,HttpSession session) {
		String currentDbName=Dbs.getDbName();
		Dbs.setDbName(Dbs.getMainDbName());
		Shops shops=(Shops) session.getAttribute(Shops.SHOPS_SESSION);
		List<User> users =  userService.getByShopId(shops.getId());
		model.addAttribute("users",users);
		JSONArray array = new JSONArray();
		for(User user : users){
			JSONObject kq = new JSONObject();
			kq.put("value",user.getId());
			kq.put("title",user.getUserRealname());
			array.add(kq);
		}
		model.addAttribute("userlist", array.toString());
		
		Dbs.setDbName(currentDbName);
		List<Integer> checkUser =  workflowTemplateService.selectUserIdByNodeId(id);
		JSONArray checkId = new JSONArray();
		for(Integer userId : checkUser){
			checkId.add(userId);
		}
		model.addAttribute("checkId", checkId.toString());
		
		WorkflowNode node = workflowTemplateService.selectNodeById(id);
		model.addAttribute("node", node);
		return "workflow/nodeUserList";
	}
	
	@RequestMapping("/saveNodeUser")
	@ResponseBody
	public String saveNodeUser(String userIds,Integer nodeId) {
		if(StringUtils.isNotBlank(userIds)){
			String[] userIdArr =  userIds.split("#");
			RetInfo retInfo = workflowTemplateService.saveNodeUser(userIdArr,nodeId);
			return AppUtils.getReturnInfo(retInfo);
		}else{
			RetInfo retInfo = new RetInfo("success","保存失败");
			return AppUtils.getReturnInfo(retInfo);
		}
	}	
	
	@RequestMapping("/delNode")
    @ResponseBody
    public String delNode(Integer ids,HttpServletRequest request) {
		RetInfo retInfo= workflowTemplateService.deleteNode(ids);
        return AppUtils.getReturnInfo(retInfo);
    }	
	@RequestMapping("/delTemplate")
	@ResponseBody
	public String delTemplate(Integer ids,HttpServletRequest request) {
		RetInfo retInfo= workflowTemplateService.deleteTemplate(ids);
		return AppUtils.getReturnInfo(retInfo);
	}	
}
