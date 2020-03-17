package com.kyx.biz.workflow.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.workflow.model.WorkflowNode;
@Repository("workflowNodeMapper")
public interface WorkflowNodeMapper {
    int deleteByPrimaryKey(Integer id);
    
    int deleteByTemplateId(Integer templateId);

    int insert(WorkflowNode record);

    int insertSelective(WorkflowNode record);

    WorkflowNode selectByPrimaryKey(Integer id);
    
//    /**
//     * 查找
//     * @param nextNodeId
//     * @return
//     */
//    WorkflowNode selectByNextNode(Integer nextNodeId);

    int updateByPrimaryKeySelective(WorkflowNode record);

    int updateByPrimaryKey(WorkflowNode record);
    
    List<WorkflowNode> getList(WorkflowNode workflowNode);
    
    List<WorkflowNode> getListByTemplateId(Integer templateId);
    
    
    int selectCountByPreNode(@Param("preNodeId")Integer preNodeId,@Param("nodeId")Integer nodeId);
    
    int selectCountByNextNode(@Param("nextNodeId")Integer nextNodeId,@Param("nodeId")Integer nodeId);
    
    int clearPreNode(Integer preNodeId);
    
    int setPreNode(@Param("preNodeId")Integer preNodeId,@Param("nodeId")Integer nodeId);
    
    int clearNextNode(Integer nextNodeId);
    
    int setNextNode(@Param("nextNodeId")Integer nextNodeId,@Param("nodeId")Integer nodeId);
    
    WorkflowNode selectFirstNodeByTemplateId(Integer templateId);
    
    int getMaxIidnoByTemplateId(Integer templateId);
    
}