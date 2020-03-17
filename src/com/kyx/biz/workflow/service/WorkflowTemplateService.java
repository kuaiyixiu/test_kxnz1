package com.kyx.biz.workflow.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.workflow.model.WorkflowNode;
import com.kyx.biz.workflow.model.WorkflowTemplate;

public interface WorkflowTemplateService {
	
	GrdData getList(WorkflowTemplate workflowTemplate, Pager pager);
	
	GrdData getNodeList(WorkflowNode workflowNode, Pager pager);

	@Transactional
	RetInfo saveTemplate(WorkflowTemplate workflowTemplate);
	
	@Transactional
	RetInfo updateTemplate(WorkflowTemplate workflowTemplate);
	
	WorkflowTemplate selectByPrimaryKey(Integer id);
	
	WorkflowNode selectNodeById(Integer id);
	
	List<WorkflowNode> getListByTemplateId(Integer templateId);
	
	@Transactional
	RetInfo saveNode(WorkflowNode workflowNode);
	
	
	@Transactional
	RetInfo updateNode(WorkflowNode workflowNode); 
	
	List<Integer> selectUserIdByNodeId(Integer userId);
	 
	
	@Transactional
	RetInfo saveNodeUser(String[] userIdArr,Integer nodeId);

	
	@Transactional
	RetInfo deleteNode(Integer nodeId);
	
	@Transactional
	RetInfo deleteTemplate(Integer id);
	
	List<WorkflowTemplate> getList(WorkflowTemplate workflowTemplate);
	 
	int getMaxIidnoByTemplateId(Integer templateId);
	
	/**
	 * 保存节点列表
	 * @param templateId
	 * @param nodeList
	 * @return
	 */
	RetInfo saveNodeList(Integer templateId,List<WorkflowNode> nodeList);
}
