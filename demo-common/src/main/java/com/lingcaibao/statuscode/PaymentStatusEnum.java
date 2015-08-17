package com.lingcaibao.statuscode;
/**
 * 
* 标题：   支付状态枚举
* 项目名称：market-common   
* 类名称：PaymentStatusEnum   
* 类描述：   
* 创建人：xpj   
* 创建时间：2015年4月3日 下午12:02:23   
* 公司：北京零彩宝网络技术有限公司 
* 版权：Copyright (c) 2015  
* 修改备注：   
* @version   1.0 
*
 */
public enum PaymentStatusEnum {
	WAITFORPAYMENT("等待支付"), 
	PAYMENTSUCCESS("支付成功"), 
	PAYMENTFAILED("支付失败"), 
	PAYBACKORDER("订单过期");

	private String name;

	private PaymentStatusEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * 根据编码获取中文名称
	 * 
	 * @param value
	 * @return
	 */
	public static String getName(int value) {
		for (PaymentStatusEnum statusEnum : PaymentStatusEnum.values()) {
			if (statusEnum.ordinal() == value) {
				return statusEnum.name;
			}
		}
		return "";
	}
}
