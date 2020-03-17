package com.kyx.biz.wechat.model;

import java.io.Serializable;
import java.util.Date;

public class WechatCommunity implements Serializable {
    private Integer id;

    private String openId;

    private String userName;

    private Integer lastLogin;

    private Date addTime;

    private String appId;

    private static final long serialVersionUID = 1L;

    public WechatCommunity() {
    }

    public WechatCommunity(String appId, String openId, String userName) {
        this.openId = openId;
        this.userName = userName;
        this.appId = appId;
        this.lastLogin = IS_LAST_LOGIN;
        this.addTime = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public Integer getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Integer lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static final int IS_LAST_LOGIN = 1;
}