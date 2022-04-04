package com.example.demo.business.exception;


import com.example.demo.domain.system.ErrorEnum;

/**
 * @author yeqing
 * @date 2021-10-26
 */
public class DemoException extends RuntimeException {

    private final int code;
    private final String message;

    public DemoException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public DemoException(ErrorEnum errorEnum) {
        super(errorEnum.getName());
        this.code = errorEnum.getValue();
        this.message = errorEnum.getName();
    }

    public DemoException(String message) {
        super(message);
        this.code = ErrorEnum.DEFAULT_ERROR.getValue();
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static DemoException build(String message) {
        return new DemoException(ErrorEnum.DEFAULT_ERROR.getValue(), message);
    }

    public static DemoException build(ErrorEnum errorEnum) {
        return new DemoException(errorEnum.getValue(), errorEnum.getName());
    }
}
