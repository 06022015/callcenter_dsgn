package com.callcenter.intrf.exception;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/5/17
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class DuplicateObjectException extends CallCenterException{

    public static int code = 409;

    public DuplicateObjectException(String message) {
        super(code, message);
    }

    public DuplicateObjectException(String message, Throwable cause) {
        super(message, cause, code);
    }

    public DuplicateObjectException(Throwable cause) {
        super(cause, code);
    }

    public DuplicateObjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
