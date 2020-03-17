package com.kyx.biz.wechatpublic.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

public class WechatCardRecord implements Serializable {
    private Integer id;

    private String wechatCardName;

    private BigDecimal wechatCardValue;

    private String wechatName;

    private Integer custId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date endDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date getDate;

    private String remark;

    private Integer state;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date useDate;

    private String useCar;

    private String slipNo;

    private BigDecimal slipAmount;

    private String optUserName;
    
    private String custName;//绑定客户名称
    private String phone;//客户号码
    
    private String voidRemark;
    
    private String cardsetRemark;
    
    private Integer cardsetId;
    
    private Integer orderId;

    private static final long serialVersionUID = 1L;
    
    public static String SYSSHARE="系统派发";//
    public static String AWARD="关注奖励";
    
    private String keyword;
    
    private Integer effect; //优惠卷有效    state = 0  and date(end_date) >= CURDATE() 

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWechatCardName() {
        return wechatCardName;
    }

    public void setWechatCardName(String wechatCardName) {
        this.wechatCardName = wechatCardName == null ? null : wechatCardName.trim();
    }

    public BigDecimal getWechatCardValue() {
        return wechatCardValue;
    }

    public void setWechatCardValue(BigDecimal wechatCardValue) {
        this.wechatCardValue = wechatCardValue;
    }

    public String getWechatName() {
        return wechatName;
    }

    public void setWechatName(String wechatName) {
        this.wechatName = wechatName == null ? null : wechatName.trim();
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getGetDate() {
        return getDate;
    }

    public void setGetDate(Date getDate) {
        this.getDate = getDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    public String getUseCar() {
        return useCar;
    }

    public void setUseCar(String useCar) {
        this.useCar = useCar == null ? null : useCar.trim();
    }

    public String getSlipNo() {
        return slipNo;
    }

    public void setSlipNo(String slipNo) {
        this.slipNo = slipNo == null ? null : slipNo.trim();
    }

    public BigDecimal getSlipAmount() {
        return slipAmount;
    }

    public void setSlipAmount(BigDecimal slipAmount) {
        this.slipAmount = slipAmount;
    }

    public String getOptUserName() {
        return optUserName;
    }

    public void setOptUserName(String optUserName) {
        this.optUserName = optUserName == null ? null : optUserName.trim();
    }

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getVoidRemark() {
		return voidRemark;
	}

	public void setVoidRemark(String voidRemark) {
		this.voidRemark = voidRemark;
	}

	public String getCardsetRemark() {
		return cardsetRemark;
	}

	public void setCardsetRemark(String cardsetRemark) {
		this.cardsetRemark = cardsetRemark;
	}

	public Integer getCardsetId() {
		return cardsetId;
	}

	public void setCardsetId(Integer cardsetId) {
		this.cardsetId = cardsetId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getEffect() {
		return effect;
	}

	public void setEffect(Integer effect) {
		this.effect = effect;
	}
	
}