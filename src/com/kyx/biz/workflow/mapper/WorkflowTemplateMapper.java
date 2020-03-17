package com.kyx.biz.workflow.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.workflow.model.WorkflowTemplate;
@Repository("workflowTemplateMapper")
public interface WorkflowTemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorkflowTemplate record);

    int insertSelective(WorkflowTemplate record);

    WorkflowTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorkflowTemplate record);

    int updateByPrimaryKey(WorkflowTemplate record);
    
    List<WorkflowTemplate> getList(WorkflowTemplate workflowTemplate);
}