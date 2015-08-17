package com.lingcaibao.statuscode;

/**
 * 微信异常码枚举类
 * 
 * @author nzh
 * 
 */
public enum WarningEnum {
	MARKETPLAN("2000021", "活动相关异常（保存，列表，抽奖）"), 
	PAYMENT("2000022", "支付接口异常"),
	LINGCAI_API("200004", "lingcai-api返回结果异常");

	private String code;
	private String desc;

	WarningEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}
}
