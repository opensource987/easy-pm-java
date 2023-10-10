package com.mdframework.common.exception;

/**
 * 参数异常
 */
public class ParamException extends RuntimeException {
    public ParamException() {
        super();
    }
    public ParamException(String message) {
        super(message);
    }
}
