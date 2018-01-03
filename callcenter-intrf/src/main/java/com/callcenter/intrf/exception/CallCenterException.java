package com.callcenter.intrf.exception;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 12:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallCenterException extends RuntimeException{

    private int code = 500;

    public CallCenterException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CallCenterException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public CallCenterException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public CallCenterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
