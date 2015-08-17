package com.lingcaibao.web.interceptor;

/**
 * @Title: VerifyTypeEnum.java
 * @Description:(验证签名状态枚举类)
 * @author kelylmall
 * @email ming.li@lingcaibao.com
 * @date 2014年5月27日 下午2:46:21
 * @version V1.0
 */
public enum VerifyTypeEnum {
	PARAM_EMPTY("PARAM_EMPTY", "40001", "参数为空"), 
	SIGN_ERROR("SIGN_ERROR","40002", "验签错误"),
	INVALID_CHANNELAPPKEY("INVALID_CHANNELAPPKEY","40003","无效的渠道key"),
	NOT_BETS("NOT_BETS","40004", "当前期不能投注"), 
	NOT_SUPPORT("LOT_NOT_SUPPORT","40005", "彩种暂不支持"),
	;
	private String alias;
	private String code;
	private String msg;

	VerifyTypeEnum(String alias, String code, String msg) {
		this.alias = alias;
		this.code = code;
		this.msg = msg;
	}

	public String getAlias() {
		return alias;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
