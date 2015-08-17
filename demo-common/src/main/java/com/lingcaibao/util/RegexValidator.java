package com.lingcaibao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则验证工具类
 * Created by jianhe on 13-12-15.
 */
public class RegexValidator {

    private static Logger logger = LoggerFactory
            .getLogger(RegexValidator.class);

    /**
     * 验证是否为手机号
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(147))\\d{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 验证是否为邮箱格式
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static void main(String args[]) {
        String mobile = "13522505810";
        String emailStr ="1542556436@qq.com";
        logger.info("---{}", RegexValidator.isMobile(mobile));
        logger.info("---{}", RegexValidator.isEmail(emailStr));
    }
}
