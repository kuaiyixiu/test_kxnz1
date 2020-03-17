package com.kyx.biz.workflow.model;

import java.io.Serializable;

public class WorkflowNodeUser implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer nodeId;

    private static final long serialVersionUID = 1L;
    

    public WorkflowNodeUser() {
		super();
	}

	public WorkflowNodeUser(Integer userId, Integer nodeId) {
		super();
		this.userId = userId;
		this.nodeId = nodeId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }
}