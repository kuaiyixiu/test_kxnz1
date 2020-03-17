package com.kyx.biz.wechat.model;

import java.io.Serializable;

public class WechatTemplate implements Serializable {
    private Integer id;

    private String templateId;

    private Integer wechatId;
    /**
     * 模板类型1用户预约提醒2服务进度提醒3消费提醒4还款通知
     */
    private Integer type;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public Integer getWechatId() {
        return wechatId;
    }

    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}