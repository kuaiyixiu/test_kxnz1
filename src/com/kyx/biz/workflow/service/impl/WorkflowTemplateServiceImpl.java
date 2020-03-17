package com.kyx.biz.workflow.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.workflow.mapper.WorkflowInstanceMapper;
import com.kyx.biz.workflow.mapper.WorkflowNodeMapper;
import com.kyx.biz.workflow.mapper.WorkflowNodeUserMapper;
import com.kyx.biz.workflow.mapper.WorkflowTemplateMapper;
import com.kyx.biz.workflow.model.WorkflowInstance;
import com.kyx.biz.workflow.model.WorkflowNode;
import com.kyx.biz.workflow.model.WorkflowNodeUser;
import com.kyx.biz.workflow.model.WorkflowTemplate;
import com.kyx.biz.workflow.service.WorkflowTemplateService;

@Service("workflowTemplateService")
public class WorkflowTemplateServiceImpl implements WorkflowTemplateService {

	@Resource
	private WorkflowTemplateMapper workflowTemplateMapper;

	@Resource
	private WorkflowNodeMapper workflowNodeMapper;

	@Resource
	private WorkflowNodeUserMapper workflowNodeUserMapper;
	
	
	@Resource
	private WorkflowInstanceMapper workflowInstanceMapper;

	@Override
	public GrdData getList(WorkflowTemplate workflowTemplate, Pager pager) {
		Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<WorkflowTemplate> list = workflowTemplateMapper.getList(workflowTemplate);
		return new GrdData(page.getTotal(), list);
	}

	@Override
	public RetInfo saveTemplate(WorkflowTemplate workflowTemplate) {
		int count = workflowTemplateMapper.insertSelective(workflowTemplate);
		if (count > 0)
			return new RetInfo("success", "保存成功");
		else
			return new RetInfo("error", "保存失败");
	}

	@Override
	public WorkflowTemplate selectByPrimaryKey(Integer id) {
		return workflowTemplateMapper.selectByPrimaryKey(id);
	}

	@Override
	public GrdData getNodeList(WorkflowNode workflowNode, Pager pager) {
		List<WorkflowNode> nodes = workflowNodeMapper.getListByTemplateId(workflowNode.getTemplateId());
		Map<Integer, String> typeMap = new HashMap<Integer, String>();
		if (!ObjectUtils.isEmpty(nodes)) {
			for (WorkflowNode pay : nodes) {
				typeMap.put(pay.getId(), pay.getName());
			}
		}

		Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<WorkflowNode> list = workflowNodeMapper.getList(workflowNode);
		for (WorkflowNode node : list) {
			if (node.getPreNode() != null) {
				node.setPreNodeName(typeMap.get(node.getPreNode()));
			}
			if (node.getNextNode() != null) {
				node.setNextNodeName(typeMap.get(node.getNextNode()));
			}
		}
		return new GrdData(page.getTotal(), list);
	}

	@Override
	public List<WorkflowNode> getListByTemplateId(Integer templateId) {
		return workflowNodeMapper.getListByTemplateId(templateId);
	}

	@Override
	public RetInfo saveNode(WorkflowNode workflowNode) {
		int existCount1 = 0;
		int existCount2 = 0;
		if(workflowNode.getPreNode() != null){
			existCount1 = workflowNodeMapper.selectCountByPreNode(workflowNode.getPreNode(), workflowNode.getId());
		}
		
		if(workflowNode.getNextNode() != null){
			existCount2 = workflowNodeMapper.selectCountByNextNode(workflowNode.getNextNode(), workflowNode.getId());
		}
		
		if(existCount1 > 0){
			return new RetInfo("error", "前节点有重复");
		}
		
		if(existCount2 > 0){
			return new RetInfo("error", "后节点有重复");
		}
		
		int count = workflowNodeMapper.insertSelective(workflowNode);
		
		if(workflowNode.getPreNode() != null){
			workflowNodeMapper.setNextNode(workflowNode.getPreNode(), workflowNode.getId());
		}
		
		if(workflowNode.getNextNode() != null){
			workflowNodeMapper.setPreNode(workflowNode.getNextNode(), workflowNode.getId());
		}
		
		if (count > 0)
			return new RetInfo("success", "保存成功");
		else
			return new RetInfo("error", "保存失败");
	}

	@Override
	public RetInfo updateNode(WorkflowNode workflowNode) {
		int existCount1 = 0;
		int existCount2 = 0;
		if(workflowNode.getPreNode() != null){
			existCount1 = workflowNodeMapper.selectCountByPreNode(workflowNode.getPreNode(), workflowNode.getId());
		}
		
		if(workflowNode.getNextNode() != null){
			existCount2 = workflowNodeMapper.selectCountByNextNode(workflowNode.getNextNode(), workflowNode.getId());
		}

		if(existCount1 > 0){
			return new RetInfo("error", "前节点有重复");
		}
		
		if(existCount2 > 0){
			return new RetInfo("error", "后节点有重复");
		}
		
		WorkflowNode oldNode = workflowNodeMapper.selectByPrimaryKey(workflowNode.getId());
		if(oldNode.getPreNode() != null){
			workflowNodeMapper.clearNextNode(oldNode.getPreNode());
		}
		
		if(oldNode.getNextNode() != null){
			workflowNodeMapper.clearPreNode(oldNode.getNextNode());
		}
		
		
		if(workflowNode.getPreNode() != null){
			workflowNodeMapper.setNextNode(workflowNode.getPreNode(), workflowNode.getId());
		}
		
		
		if(workflowNode.getNextNode() != null){
			workflowNodeMapper.setPreNode(workflowNode.getNextNode(), workflowNode.getId());
		}
		
		
		int count = workflowNodeMapper.updateByPrimaryKey(workflowNode);
		if (count > 0)
			return new RetInfo("success", "修改成功");
		else
			return new RetInfo("error", "修改失败");
	}

	@Override
	public WorkflowNode selectNodeById(Integer id) {
		return workflowNodeMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Integer> selectUserIdByNodeId(Integer userId) {
		return workflowNodeUserMapper.selectUserIdByNodeId(userId);
	}

	@Override
	public RetInfo saveNodeUser(String[] userIdArr, Integer nodeId) {
		if (!ObjectUtils.isEmpty(userIdArr)) {
			workflowNodeUserMapper.deleteByNodeId(nodeId);
			for (String userId : userIdArr) {
				WorkflowNodeUser user = new WorkflowNodeUser(Integer.parseInt(userId), nodeId);
				workflowNodeUserMapper.insert(user);
			}
		}

		return new RetInfo("success", "保存成功");
	}

	@Override
	public RetInfo deleteNode(Integer nodeId) {
		workflowNodeUserMapper.deleteByNodeId(nodeId);
		workflowNodeMapper.deleteByPrimaryKey(nodeId);
		return new RetInfo("success", "保存成功");
	}

	@Override
	public RetInfo updateTemplate(WorkflowTemplate workflowTemplate) {
		int count = workflowTemplateMapper.updateByPrimaryKeySelective(workflowTemplate);
		if (count > 0)
			return new RetInfo("success", "修改成功");
		else
			return new RetInfo("error", "修改失败");
	}

	@Override
	public RetInfo deleteTemplate(Integer id) {
		WorkflowInstance workflowInstance = new WorkflowInstance();
		workflowInstance.setTemplateId(id);
		List<WorkflowInstance>  instances = workflowInstanceMapper.getList(workflowInstance);
		if(instances.size() > 0 ){
			return new RetInfo("error", "无法删除，已有审批使用该模版");
		}
		
		workflowTemplateMapper.deleteByPrimaryKey(id);
		List<WorkflowNode>  list = workflowNodeMapper.getListByTemplateId(id);
		for(WorkflowNode node : list){
			deleteNode(node.getId());
		}
		return new RetInfo("success", "修改成功");
	}

	@Override
	public List<WorkflowTemplate> getList(WorkflowTemplate workflowTemplate) {
		return workflowTemplateMapper.getList(workflowTemplate);
	}

	@Override
	public int getMaxIidnoByTemplateId(Integer templateId) {
		return workflowNodeMapper.getMaxIidnoByTemplateId(templateId);
	}

	@Override
	public RetInfo saveNodeList(Integer templateId, List<WorkflowNode> nodeList) {
		workflowNodeMapper.deleteByTemplateId(templateId);
		List<Integer> idList = new ArrayList<>();
		if(nodeList != null){
			for(int i = 0;i<nodeList.size();i++){
				WorkflowNode node = nodeList.get(i);
				node.setTemplateId(templateId);
				node.setIidno(i);
				node.setCreateTime(new Date());
				workflowNodeMapper.insertSelective(node);
				idList.add(node.getId());
			}
			
			//赋值上节点和下节点
			for(int i = 0;i<idList.size();i++){
				WorkflowNode node = new WorkflowNode();
				node.setId(idList.get(i));
				if(i > 0)
					node.setPreNode(idList.get(i-1));
				
				if (i < idList.size() - 1)
					node.setNextNode(idList.get(i+1));
				
				workflowNodeMapper.updateByPrimaryKeySelective(node);
			}
		}
		
		return new RetInfo("success", "保存成功");
	}

}
