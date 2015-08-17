package com.lingcaibao.web.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证
 * @author nzh
 * 格式如下：
 * @VerifyKey({
		@Verify(key = "email", regexType = RegexType.EMAIL, minLen = 6, maxLen = 12, desc = "邮箱"),
		@Verify(key = "name", isAble=true, regexType = RegexType.STRING, minLen = 2, maxLen = 5, desc = "姓名"),
		@Verify(key = "mobile", isAble=true, regexType = RegexType.MOBILE, minLen = 11, maxLen = 11, desc = "手机"),
		@Verif(key = "id", isAble=true, regexType = RegexType.NUMBER, minLen = 11, maxLen = 11, desc = "id")
	})
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface VerifyKey {
	Verify[] value();
}
