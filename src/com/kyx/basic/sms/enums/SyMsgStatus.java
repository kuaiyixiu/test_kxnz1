package com.kyx.basic.sms.enums;


/**
 * 示远短信状态返回枚举
 */
public enum SyMsgStatus {
    /**
     * 提交成功
     */
	SyMsgStatus_0(0, "提交成功"),
	
	/**
	 * 无此用户
	 */
	SyMsgStatus_1(1, "无此用户"),
	
	
	/**
	 * 密码错
	 */
	SyMsgStatus_2(2, "密码错"),
	
	/**
	 * 提交过快（提交速度超过流速限制）
	 */
	SyMsgStatus_3(3, "提交过快（提交速度超过流速限制）"),
	
	/**
	 * 系统忙（因平台侧原因，暂时无法处理提交的短信）
	 */
	SyMsgStatus_4(4, "系统忙（因平台侧原因，暂时无法处理提交的短信）"),
	
	
	/**
	 * 敏感短信（短信内容包含敏感词）
	 */
	SyMsgStatus_5(5, "敏感短信（短信内容包含敏感词）"),
	
	/**
	 * 消息长度错（>500 或<=0）
	 */
	SyMsgStatus_6(6, "消息长度错（>500 或<=0）"),
	
	/**
	 * 包含错误的手机号码
	 */
	SyMsgStatus_7(7, "包含错误的手机号码"),
	
	/**
	 * 手机号码个数错（群发>50000 或<=0;单发>200 或<=0）
	 */
	SyMsgStatus_8(8, "手机号码个数错（群发>50000 或<=0;单发>200 或<=0）"),
	
	/**
	 * 无发送额度（该用户可用短信数已使用完）
	 */
	SyMsgStatus_9(9, "无发送额度（该用户可用短信数已使用完）"),

	
	/**
	 * 不在发送时间内
	 */
	SyMsgStatus_10(10, "不在发送时间内"),
	
	/**
	 * 超出该账户当月发送额度限制
	 */
	SyMsgStatus_11(11, "超出该账户当月发送额度限制"),
	
	/**
	 * 无此产品，用户没有订购该产品
	 */
	SyMsgStatus_12(12, "无此产品，用户没有订购该产品"),
	
	
	/**
	 * extno 格式错（非数字或者长度不对）
	 */
	SyMsgStatus_13(13, "extno 格式错（非数字或者长度不对）"),
	
	
	/**
	 * 错误的短信发送模式
	 */
	SyMsgStatus_14(14, "错误的短信发送模式"),
	
	/**
	 * 自动审核驳回
	 */
	SyMsgStatus_15(15, "自动审核驳回"),
	
	/**
	 * 签名不合法，未带签名（用户必须带签名的前提下）
	 */
	SyMsgStatus_16(16, "签名不合法，未带签名（用户必须带签名的前提下）"),
	
	
	/**
	 * IP 地址认证错,请求调用的 IP 地址不是系统登记的 IP 地址
	 */
	SyMsgStatus_17(17, "IP 地址认证错,请求调用的 IP 地址不是系统登记的 IP 地址"),
	
	
	/**
	 * 用户没有相应的发送权限
	 */
	SyMsgStatus_18(18, "用户没有相应的发送权限"),
	
	
	/**
	 * 用户已过期
	 */
	SyMsgStatus_19(19, "用户已过期"),
	
	
	/**
	 * 内容不在白名单模板中
	 */
	SyMsgStatus_20(20, "内容不在白名单模板中"),
	
	/**
	 * 可用参数组个数错误（小于最小设定值或者大于 1000）
	 */
	SyMsgStatus_21(21, "可用参数组个数错误（小于最小设定值或者大于 1000）");
	
	
    /**
     * 结果码
     */
    Integer status;

    /**
     * 结果描述
     */
    String value;

    /**
     * 返回结果枚举构造方法
     *
     * @param code        结果码
     * @param description 结果描述
     */
    private SyMsgStatus(Integer status, String value) {
        this.status = status;
        this.value = value;
    }

    /**
     * 通过code得到返回结果对象
     *
     * @param code 结果码
     * @return 结果枚举对象
     */
    public static SyMsgStatus get(Integer status) {
        SyMsgStatus[] list = values();
        for (SyMsgStatus statusType : list) {
            if (statusType.status.equals(status)) {
                return statusType;
            }
        }
        return null;
    }
    
    
    public static String getValue(Integer status) {
        SyMsgStatus[] list = values();
        for (SyMsgStatus statusType : list) {
            if (statusType.status.equals(status)) {
                return statusType.value;
            }
        }
        return "";
    }
    
    
    public static Integer getStatus(String value) {
        SyMsgStatus[] list = values();
        for (SyMsgStatus resultType : list) {
            if (resultType.value.equals(value)) {
                return resultType.status;
            }
        }
        return -1;
    }
    

    /**
     * 获得key
     *
     * @return 结果码
     */
    public Integer getKey() {
        return status;
    }

    /**
     * 获得value
     *
     * @return 结果描述
     */
    public String getValue() {
        return value;
    }

   
}