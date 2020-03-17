package com.kyx.biz.workflow.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.workflow.model.WorkflowInstance;
import com.kyx.biz.workflow.model.WorkflowTemplate;

@Repository("workflowInstanceMapper")
public interface WorkflowInstanceMapper {
    int deleteByPrimaryKey(Integer id);


    int insertSelective(WorkflowInstance record);

    WorkflowInstance selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorkflowInstance record);

    
    List<WorkflowInstance> getList(WorkflowInstance workflowInstance);
}