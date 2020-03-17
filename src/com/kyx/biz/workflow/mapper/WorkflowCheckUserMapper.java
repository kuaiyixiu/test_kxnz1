package com.kyx.biz.workflow.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.workflow.model.WorkflowCheckUser;
@Repository("workflowCheckUserMapper")
public interface WorkflowCheckUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorkflowCheckUser record);

    int insertSelective(WorkflowCheckUser record);

    WorkflowCheckUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorkflowCheckUser record);

    int updateByPrimaryKey(WorkflowCheckUser record);
    
    /**
     * 根据流程节点ID,查出全部审核人员的审核列表
     * @param instanceNodeId
     * @return
     */
    List<WorkflowCheckUser> selectUserListByInstanceNodeId(Integer instanceNodeId);
    
    /**
     * 校验是否重复提交
     * @param instanceId
     * @param optUser
     * @return
     */
    int selectCountByUserAndNode(@Param("instanceNodeId")Integer instanceNodeId,@Param("optUser")Integer optUser);
}