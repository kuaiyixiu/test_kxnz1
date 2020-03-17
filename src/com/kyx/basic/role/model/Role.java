package com.kyx.basic.role.model;

import java.io.Serializable;

public class Role implements Serializable {
    private Integer id;

    private String name;

    private String roleKey;

    private String description;

    private String enable;
    private String enableText;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey == null ? null : roleKey.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable == null ? null : enable.trim();
    }

	public String getEnableText() {
		if("1".equals(this.enable))
			return "启用";
		else
			return "停用";
	}

	public void setEnableText(String enableText) {
		this.enableText = enableText;
	}
    
}