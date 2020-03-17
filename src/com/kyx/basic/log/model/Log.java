package com.kyx.basic.log.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Log implements Serializable {
    private Integer id;

    private String userName;

    private String module;

    private String description;

    private String resUrl;

    private String userIp;

    private Date operTime;

    private String className;

    private String methodName;

    private String params;
    
    private Integer shopId;
    
    private Integer source;
    
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module == null ? null : module.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl == null ? null : resUrl.trim();
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp == null ? null : userIp.trim();
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
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

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}
	
}