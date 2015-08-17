package com.lingcaibao.statuscode;
/**
 * 
* 标题：充值状态枚举  
* 项目名称：market-common   
* 类名称：DepositStatusEnum   
* 类描述：   
* 创建人：xpj   
* 创建时间：2015年4月2日 下午5:55:19   
* 公司：北京零彩宝网络技术有限公司 
* 版权：Copyright (c) 2015  
* 修改备注：   
* @version   1.0 
*
 */
public enum DepositStatusEnum {
    WAITFORPAY("充值中"),
	PAYSUCCESS("充值成功"),
	PAYFAILURE("充值失败"),
	PAYBACKORDER("订单过期")
	;
    private String name;

    private DepositStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    /**
     * 根据编码获取中文名称
     * @param value
     * @return
     */
    public static String getName(int value) {
        for (DepositStatusEnum depositStatusEnum : DepositStatusEnum.values()) {
            if (depositStatusEnum.ordinal() == value) {
                return depositStatusEnum.name;
            }
        }
        return "";
    }
   /* public static void main(String[] args) {
		System.out.println(PAYBACKORDER.ordinal());
	}*/
}
