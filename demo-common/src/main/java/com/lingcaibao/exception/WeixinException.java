package com.lingcaibao.exception;

/**
 * 投注出票相关异常
 */
public class WeixinException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * message key
     */
    private String code;

    /**
     * message params
     */
    private Object[] values;

    public WeixinException() {
    }

    public WeixinException(String s) {
        super(s);
    }

    public WeixinException(String s, String code) {
        super(s);
        this.code = code;
    }

    public WeixinException(String s, Throwable throwable, String code, Object[] values) {
        super(s, throwable);
        this.code = code;
        this.values = values;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }
}
