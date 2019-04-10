package com.company.project.exception;

/**
 * @author: chenyin
 * @date: 2019-03-07 18:31
 */
public class BizExcetion extends RuntimeException{
    /**
     * 错误编码
     */
    private int errorCode;

    public BizExcetion(String message) {
        super(message);
    }

    public BizExcetion(ErrorCode errorCode) {
        super(errorCode.getErrorMsg());
        this.setErrorCode(errorCode.getErrorCode());
    }

    public BizExcetion(int errorCode, String message) {
        super(message);
        this.setErrorCode(errorCode);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
