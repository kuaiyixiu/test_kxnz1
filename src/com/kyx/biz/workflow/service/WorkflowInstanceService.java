package com.kyx.biz.workflow.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.workflow.model.WorkflowCheckUser;
import com.kyx.biz.workflow.model.WorkflowInstance;
import com.kyx.biz.workflow.model.WorkflowInstanceNode;

public interface WorkflowInstanceService {

	GrdData getInstanceList(WorkflowInstance workflowInstance, Pager pager,Integer shopId);
	
	GrdData getInstanceNodeList(WorkflowInstanceNode workflowInstanceNode, Pager pager);
	
	
	@Transactional
	RetInfo saveInstance(WorkflowInstance workflowInstance);
	
	@Transactional
	RetInfo updateInstance(WorkflowInstance workflowInstance);
	
	/**
	 * 待审核列表
	 * @param workflowInstance
	 * @param pager
	 * @return
	 */
	GrdData getCheckList(WorkflowInstanceNode workflowInstanceNode, Pager pager);
	
	/**
	 * 联查节点表的信息
	 * @param id
	 * @return
	 */
	WorkflowInstanceNode selectNodeById(Integer id);
	
	/**
	 * 处理审核结果
	 * @param workflowInstanceNode
	 * @return
	 */
	@Transactional
	RetInfo updateCheckNode(WorkflowCheckUser workflowCheckUser);
	
    List<WorkflowCheckUser> selectUserListByInstanceNodeId(Integer shopId,Integer instanceNodeId);
    
    /**
     * 创建审批主记录（并创建第一个子流程）
     * @param workflowInstance
     * @return
     */
    @Transactional
    RetInfo createInstance(WorkflowInstance workflowInstance);
    
    @Transactional
    RetInfo backInstance(Integer ids);
    
    /**
     * 提交订单
     * @param instanceId
     * @return
     */
    @Transactional
    void submitSlip(Integer instanceId);
    
    /**
     * 拒绝订单
     * @param instanceId
     * @return
     */
    @Transactional
    void refuseSlip(Integer instanceId);
    

	WorkflowInstance selectById(Integer id, int shopId);    
   
    
}
