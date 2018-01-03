package com.callcenter.core.handler;

import com.callcenter.core.CallHandler;
import com.callcenter.intrf.repository.CallCenterDao;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 9:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallHandlerFactory {

    public static CallHandlerFactory INSTANCE = null;
    private CallCenterDao callCenterDao;
    private CallHandler jeCallHandler;
    private CallHandler seCallHandler;
    private CallHandler mgrCallHandler;

    private CallHandlerFactory(CallCenterDao callCenterDao) {
        this.callCenterDao = callCenterDao;
        init();
    }

    public static void create(CallCenterDao callCenterDao) {
        if (null == INSTANCE) {
            synchronized (CallHandlerFactory.class) {
                if (null == INSTANCE) {
                    INSTANCE = new CallHandlerFactory(callCenterDao);
                }
            }
        }
    }

    private void init() {
        jeCallHandler = new JECallHandler(callCenterDao);
        seCallHandler = new SECallHandler(callCenterDao);
        mgrCallHandler = new ManagerCallHandler(callCenterDao);
    }

    public CallHandler getJeCallHandler() {
        /*if (null == jeCallHandler)
            jeCallHandler = new JECallHandler(callCenterDao);*/
        return jeCallHandler;
    }

    public CallHandler getSeCallHandler() {
        /*if (null == seCallHandler)
            seCallHandler = new SECallHandler(callCenterDao);*/
        return seCallHandler;
    }

    public CallHandler getMgrCallHandler() {
        /*if (null == mgrCallHandler)
            mgrCallHandler = new ManagerCallHandler(callCenterDao);*/
        return mgrCallHandler;
    }
}
