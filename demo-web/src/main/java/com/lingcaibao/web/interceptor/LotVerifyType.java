package com.lingcaibao.web.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title:验签注解
 * @Description:(验签注解)
 * @author kelylmall
 * @email ming.li@lingcaibao.com
 * @date 2014年10月16日 下午5:19:44
 * @version V1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LotVerifyType{
	/**
	 * 验证渠道是否存在
	 * @return
	 */
	boolean verifyChannelExist() default false;
	/**
	 * 验证签名
	 * @return
	 */
	boolean verifyLotSign() default false;
	
	/**
	 *  报文验签,从header获取data
	 * @return
	 */
	boolean verifySignHeaderData() default false;
	
}
