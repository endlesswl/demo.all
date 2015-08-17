package com.lingcaibao.web.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lingcaibao.statuscode.RegexType;

/**
 * Verification
 * @author nzh
 * key 验证表单元素name
 * isAble 是否为空，默认不为空false
 * maxLen 最大长度，默认为0,不验证
 * minLen 最小长度，默认为0，不验证
 * regexType 验证规则
 * description 自定义描述类型，如：手机
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Verify {	
	//标签名
	String key();
	
	//是否可以为空
	boolean isAble() default false;
	
	//最大长度
	int maxLen() default 0;
	
	//最小长度
	int minLen() default 0;
	
	//提供几种常用的正则验证
	RegexType[] regexType() default RegexType.NONE;
	
	//参数或者字段描述,这样能够显示友好的异常信息
	String desc() default "";
}


