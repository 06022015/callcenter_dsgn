package com.callcenter.intrf.exception;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 12:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class LimitExceededException extends CallCenterException{

    private static int code = 429;

    public LimitExceededException(String message) {
        super(code, message);
    }

    public LimitExceededException(String message, Throwable cause) {
        super(message, cause, code);
    }

    public LimitExceededException(Throwable cause) {
        super(cause, code);
    }

    public LimitExceededException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
