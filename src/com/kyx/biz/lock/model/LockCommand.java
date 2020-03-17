package com.kyx.biz.lock.model;

import java.io.Serializable;

public class LockCommand implements Serializable {
    private Integer id;

    private String command;

    private Integer lockNumber;

    private Integer boxAddress;

    private String lockAddress;

    private Integer type;

    private Integer enabled;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command == null ? null : command.trim();
    }

    public Integer getLockNumber() {
        return lockNumber;
    }

    public void setLockNumber(Integer lockNumber) {
        this.lockNumber = lockNumber;
    }

    public Integer getBoxAddress() {
        return boxAddress;
    }

    public void setBoxAddress(Integer boxAddress) {
        this.boxAddress = boxAddress;
    }

    public String getLockAddress() {
        return lockAddress;
    }

    public void setLockAddress(String lockAddress) {
        this.lockAddress = lockAddress == null ? null : lockAddress.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
}