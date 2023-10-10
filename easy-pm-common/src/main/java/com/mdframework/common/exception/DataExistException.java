package com.mdframework.common.exception;

/**
 * 数据已经存在
 */
public class DataExistException extends RuntimeException {
    public DataExistException() {
        super();
    }
    public DataExistException(String message) {
        super(message);
    }
}
