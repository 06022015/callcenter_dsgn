package com.callcenter.core.unit;

import com.callcenter.core.CallProcessUnit;
import com.callcenter.intrf.core.AttenderMaxLimit;
import com.callcenter.core.handler.CallHandlerFactory;
import com.callcenter.model.type.RoleEnum;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 8:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallProcessingUnitFactory {

    public static CallProcessingUnitFactory INSTANCE = null;
    private CallProcessUnit jeProcessUnit;
    private CallProcessUnit seProcessUnit;
    private CallProcessUnit mgrProcessUnit;
    private AttenderMaxLimit attenderMaxLimit;

    private CallProcessingUnitFactory() {
    }

    private CallProcessingUnitFactory(AttenderMaxLimit attenderMaxLimit) {
        this.attenderMaxLimit = attenderMaxLimit;
    }

    public static void create(){
        if(null == INSTANCE){
            synchronized (CallProcessingUnitFactory.class){
                if(null == INSTANCE)
                    INSTANCE = new CallProcessingUnitFactory();
            }
        }
    }

    public static void create(AttenderMaxLimit attenderMaxLimit){
        if(null == INSTANCE){
            synchronized (CallProcessingUnitFactory.class){
                if(null == INSTANCE)
                    INSTANCE = new CallProcessingUnitFactory(attenderMaxLimit);
            }
        }
    }

    public void setAttenderMaxLimit(AttenderMaxLimit attenderMaxLimit) {
        this.attenderMaxLimit = attenderMaxLimit;
    }

    public CallProcessUnit getProcessingUnit(RoleEnum role){
        CallProcessUnit callProcessUnit =null;
        switch (role){
            case MANGER: callProcessUnit = getMgrProcessUnit(); break;
            case SENIOR_EXECUTIVE: callProcessUnit = getSeProcessUnit(); break;
            case JUNIOR_EXECUTIVE: callProcessUnit = getJeProcessUnit(); break;
        }
        return callProcessUnit;
    }

    public CallProcessUnit getJeProcessUnit() {
        if(null == jeProcessUnit)
            jeProcessUnit = new CallProcessUnitImpl(CallHandlerFactory.INSTANCE.getJeCallHandler(), this.attenderMaxLimit);
        return jeProcessUnit;
    }

    public CallProcessUnit getSeProcessUnit() {
        if(null == seProcessUnit)
            seProcessUnit = new CallProcessUnitImpl(CallHandlerFactory.INSTANCE.getSeCallHandler(), this.attenderMaxLimit);
        return seProcessUnit;
    }

    public synchronized CallProcessUnit getMgrProcessUnit() {
        if(null == mgrProcessUnit)
            mgrProcessUnit = new CallProcessUnitImpl(CallHandlerFactory.INSTANCE.getMgrCallHandler());
        return mgrProcessUnit;
    }
}
