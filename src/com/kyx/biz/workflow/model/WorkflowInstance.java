package com.kyx.biz.workflow.model;

import java.io.Serializable;
import java.util.Date;

public class WorkflowInstance implements Serializable {
    private Integer id;

    private Integer templateId;

    private Integer slipType;

    private Integer slipId;

    private Integer currentInstanceNodeId;

    private String name;

    private Integer status;

    private Integer createUser;
    
    private String createUserName;

    private Date createTime;

    private Date endTime;

    private String remark;
    
    /**
     * 
     */
    private String currentInstanceNodeName;

    private static final long serialVersionUID = 1L;
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getSlipType() {
        return slipType;
    }

    public void setSlipType(Integer slipType) {
        this.slipType = slipType;
    }

    public Integer getSlipId() {
        return slipId;
    }

    public void setSlipId(Integer slipId) {
        this.slipId = slipId;
    }

    public Integer getCurrentInstanceNodeId() {
        return currentInstanceNodeId;
    }

    public void setCurrentInstanceNodeId(Integer currentInstanceNodeId) {
        this.currentInstanceNodeId = currentInstanceNodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getCurrentInstanceNodeName() {
		return currentInstanceNodeName;
	}

	public void setCurrentInstanceNodeName(String currentInstanceNodeName) {
		this.currentInstanceNodeName = currentInstanceNodeName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
}