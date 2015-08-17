package com.lingcaibao.exception;

/**
 * 用户领彩频率异常处理
 * Created by jianhe on 13-12-20.
 */
public class FrequencyException extends Exception {
    private static final long serialVersionUID = -7867713004171563795L;

    public FrequencyException() {
    }

    public FrequencyException(String s) {
        super(s);
    }
}
