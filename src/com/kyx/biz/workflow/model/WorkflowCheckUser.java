package com.kyx.biz.workflow.model;

import java.io.Serializable;
import java.util.Date;

public class WorkflowCheckUser implements Serializable {
    private Integer id;

    private Integer instanceNodeId;

    private Integer instanceId;

    private Integer optUser;

    private Date optTime;

    private Integer result;

    private String remark;
    
    private String resultStr;
    
    private String optUserName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInstanceNodeId() {
        return instanceNodeId;
    }

    public void setInstanceNodeId(Integer instanceNodeId) {
        this.instanceNodeId = instanceNodeId;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getOptUser() {
        return optUser;
    }

    public void setOptUser(Integer optUser) {
        this.optUser = optUser;
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getResultStr() {
		return resultStr;
	}

	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}

	public String getOptUserName() {
		return optUserName;
	}

	public void setOptUserName(String optUserName) {
		this.optUserName = optUserName;
	}
	
}