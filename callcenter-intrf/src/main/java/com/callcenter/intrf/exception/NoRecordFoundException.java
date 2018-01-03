package com.callcenter.intrf.exception;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 3:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class NoRecordFoundException extends CallCenterException{

    public static int code = 404;

    public NoRecordFoundException(String message) {
        super(code, message);
    }

    public NoRecordFoundException(String message, Throwable cause) {
        super(message, cause, code);
    }

    public NoRecordFoundException(Throwable cause) {
        super(cause, code);
    }

    public NoRecordFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
