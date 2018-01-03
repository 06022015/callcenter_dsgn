package com.callcenter.intrf.exception;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 12:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class NoAttenderException extends CallCenterException{

    private static int code = 404;

    public NoAttenderException(String message) {
        super(code, message);
    }

    public NoAttenderException(String message, Throwable cause) {
        super(message, cause, code);
    }

    public NoAttenderException(Throwable cause) {
        super(cause, code);
    }

    public NoAttenderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
