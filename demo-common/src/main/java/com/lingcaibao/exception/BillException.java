package com.lingcaibao.exception;

/**
 * 交易相关异常
 */
public class BillException extends Exception {
	
    private static final long serialVersionUID = 1L;

    /**
     * message key
     */
    private String code;

    /**
     * message params
     */
    private Object[] values;

    public BillException() {
    }

    public BillException(String s) {
        super(s);
    }

    public BillException(String s, String code) {
        super(s);
        this.code = code;
    }

    public BillException(String s, Throwable throwable, String code, Object[] values) {
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
