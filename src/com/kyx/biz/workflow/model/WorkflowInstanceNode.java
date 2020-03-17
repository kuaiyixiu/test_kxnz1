package com.kyx.biz.workflow.model;

import java.io.Serializable;
import java.util.Date;

public class WorkflowInstanceNode implements Serializable {
    private Integer id;

    private Integer iidno;

    private Integer instanceId;

    private Integer nodeId;
    
    private String name;

    private Integer status;


    private Date createTime;

    private String remark;
    
    
    /***/
    private Integer checkUser;
    
    private Integer instanceStatus;
    
    private String instanceName;
    
    private Integer instanceSlipType;
    
    private Integer instanceSlipId;
    
    private Integer type;
    
    private String typeName;
    
    private Integer result;
    
    private Integer optUser;

    private Date optTime;
    
    
    
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIidno() {
        return iidno;
    }

    public void setIidno(Integer iidno) {
        this.iidno = iidno;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
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

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public Integer getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(Integer checkUser) {
		this.checkUser = checkUser;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public Integer getInstanceSlipType() {
		return instanceSlipType;
	}

	public void setInstanceSlipType(Integer instanceSlipType) {
		this.instanceSlipType = instanceSlipType;
	}

	public Integer getInstanceSlipId() {
		return instanceSlipId;
	}

	public void setInstanceSlipId(Integer instanceSlipId) {
		this.instanceSlipId = instanceSlipId;
	}

	public Integer getInstanceStatus() {
		return instanceStatus;
	}

	public void setInstanceStatus(Integer instanceStatus) {
		this.instanceStatus = instanceStatus;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

}