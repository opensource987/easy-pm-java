package com.mdframework.common.exception;

/**
 * 数据不存在
 */
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException() {
        super();
    }
    public DataNotFoundException(String message) {
        super(message);
    }
}
