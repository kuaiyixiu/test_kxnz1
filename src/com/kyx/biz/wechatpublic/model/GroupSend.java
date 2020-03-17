package com.kyx.biz.wechatpublic.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class GroupSend implements Serializable {
    private Integer id;

    private Integer sendUserType; //发送对象类型  1客户  2微信粉丝

    private String tagId;//标签ID all标签 

    private Integer contentType;//内容类型 1文本  2图片 3图文

    private String sendContent;// 文本内容

    private String mediaId;

    private String chooseOpenids;

    private Integer userCount;

    private String appId;

    private Date createTime;

    private Integer sendStatus;//0发送中 1发送成功 2发送失败
    
    private String errorMsg;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeStartTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeEndTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSendUserType() {
        return sendUserType;
    }

    public void setSendUserType(Integer sendUserType) {
        this.sendUserType = sendUserType;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId == null ? null : tagId.trim();
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent == null ? null : sendContent.trim();
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId == null ? null : mediaId.trim();
    }

    public String getChooseOpenids() {
        return chooseOpenids;
    }

    public void setChooseOpenids(String chooseOpenids) {
        this.chooseOpenids = chooseOpenids == null ? null : chooseOpenids.trim();
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Date getDateRangeStartTime() {
		return dateRangeStartTime;
	}

	public void setDateRangeStartTime(Date dateRangeStartTime) {
		this.dateRangeStartTime = dateRangeStartTime;
	}

	public Date getDateRangeEndTime() {
		return dateRangeEndTime;
	}

	public void setDateRangeEndTime(Date dateRangeEndTime) {
		this.dateRangeEndTime = dateRangeEndTime;
	}

	
    
}