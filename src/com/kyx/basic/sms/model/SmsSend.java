package com.kyx.basic.sms.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class SmsSend implements Serializable {
    private Long id;

    private Date createTime;

    private Date updateTime;

    private String content;

    private String mobile;

    private String responseContent;

    private Integer orderId;

    private Integer msgStatus;

    private String statusCode;

    private String msgId;

    private Integer platType;
    
    private Integer useCount;
    
    private Integer shopId;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeStartTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeEndTime;
	
	private String shopName;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent == null ? null : responseContent.trim();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(Integer msgStatus) {
        this.msgStatus = msgStatus;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode == null ? null : statusCode.trim();
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
    }

    public Integer getPlatType() {
        return platType;
    }

    public void setPlatType(Integer platType) {
        this.platType = platType;
    }

	public Integer getUseCount() {
		return useCount;
	}

	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
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

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
    
}