package com.kyx.biz.workflow.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.workflow.model.WorkflowInstanceNode;
@Repository("workflowInstanceNodeMapper")
public interface WorkflowInstanceNodeMapper {
    int deleteByPrimaryKey(Integer id);


    int insertSelective(WorkflowInstanceNode record);

    WorkflowInstanceNode selectByPrimaryKey(Integer id);
    
    WorkflowInstanceNode selectById(Integer id); 

    int updateByPrimaryKeySelective(WorkflowInstanceNode record);

    
    /**
     * 待审核列表
     * @param record
     * @return
     */
    List<WorkflowInstanceNode> getCheckList(WorkflowInstanceNode record);
    
    /**
     * 根据nodeId查询
     * @param nodeId
     * @return
     */
    List<WorkflowInstanceNode> getListByNodeId(Integer nodeId);
    
    /**
     * 获取iidno的最大值
     * @param instanceId
     * @return
     */
    int getMaxIidnoByInstanceId(Integer instanceId);
    
    List<WorkflowInstanceNode> getList(WorkflowInstanceNode record);
    
}