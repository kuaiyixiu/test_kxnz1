package com.kyx.biz.workflow.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.workflow.model.WorkflowNodeUser;
@Repository("workflowNodeUserMapper")
public interface WorkflowNodeUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorkflowNodeUser record);

    int insertSelective(WorkflowNodeUser record);

    WorkflowNodeUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorkflowNodeUser record);

    int updateByPrimaryKey(WorkflowNodeUser record);
    
    List<Integer> selectUserIdByNodeId(Integer userId);
    
    int deleteByNodeId(Integer nodeId);
    
    int selectCountByUserAndNode(@Param("nodeId")Integer nodeId,@Param("userId")Integer userId);
}