package com.kyx.basic.resourcesrole.model;

import java.io.Serializable;

public class ResourcesRole implements Serializable {
    private Integer id;

    private Integer rescId;

    private Integer roleId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRescId() {
        return rescId;
    }

    public void setRescId(Integer rescId) {
        this.rescId = rescId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}