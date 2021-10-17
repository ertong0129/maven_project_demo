package com.example.demo.exception;


import com.example.demo.result.Result;

public class DemoException extends RuntimeException {

    /**
     * 错误码
     */
    private int errorCode;
    /**
     * 错误消息
     */
    private String errorMsg;

    public static DemoException create(String errorMsg){
        return new DemoException(ErrorEnum.DEFAULT_ERROR.getValue(), errorMsg);
    }

    public DemoException(ErrorEnum errorEnum) {
        super(errorEnum.getName());
        this.errorCode = errorEnum.getValue();
        this.errorMsg = errorEnum.getName();
    }

    public DemoException(Result result) {
        super(result.getMessage());
        this.errorCode = result.getResponseCode();
        this.errorMsg = result.getMessage();
    }

    public DemoException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public DemoException(String errorMsg) {
        super(errorMsg);
        this.errorCode = ErrorEnum.DEFAULT_ERROR.getValue();
        this.errorMsg = errorMsg;
    }

    public DemoException(Throwable t) {
        super(t);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
