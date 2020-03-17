package com.kyx.biz.workflow.model;

import java.io.Serializable;
import java.util.Date;

public class WorkflowNode implements Serializable {
    private Integer id;

    private Integer templateId;

    private Integer iidno;

    private String name;

    private Integer type;

    private Integer preNode;
    
    private String preNodeName;

    private Integer nextNode;
    
    private String nextNodeName;

    private String remark;

    private Date createTime;

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

    public Integer getIidno() {
        return iidno;
    }

    public void setIidno(Integer iidno) {
        this.iidno = iidno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }


    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPreNode() {
        return preNode;
    }

    public void setPreNode(Integer preNode) {
        this.preNode = preNode;
    }

    public Integer getNextNode() {
        return nextNode;
    }

    public void setNextNode(Integer nextNode) {
        this.nextNode = nextNode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getPreNodeName() {
		return preNodeName;
	}

	public void setPreNodeName(String preNodeName) {
		this.preNodeName = preNodeName;
	}

	public String getNextNodeName() {
		return nextNodeName;
	}

	public void setNextNodeName(String nextNodeName) {
		this.nextNodeName = nextNodeName;
	}
    
}