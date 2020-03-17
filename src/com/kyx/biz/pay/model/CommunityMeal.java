package com.kyx.biz.pay.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * community_meal
 * @author 
 */
public class CommunityMeal implements Serializable {
    private Integer id;

    /**
     * 按月购买
     */
    private Integer month;

    /**
     * 套餐标题（一个月VIP会员）
     */
    private Integer title;

    /**
     * 套餐价格
     */
    private BigDecimal money;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}