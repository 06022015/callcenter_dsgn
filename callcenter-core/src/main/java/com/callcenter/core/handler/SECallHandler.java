package com.callcenter.core.handler;

import com.callcenter.core.CallHandler;
import com.callcenter.core.CallProcessUnit;
import com.callcenter.core.unit.CallProcessingUnitFactory;
import com.callcenter.intrf.exception.NoAttenderException;
import com.callcenter.intrf.repository.CallCenterDao;
import com.callcenter.model.Call;
import com.callcenter.model.CallAttender;
import com.callcenter.model.User;
import com.callcenter.model.type.CaseStatus;
import com.callcenter.repository.Constant;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 9:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class SECallHandler implements CallHandler {

    private CallCenterDao callCenterDao;

    public SECallHandler(CallCenterDao callCenterDao) {
        this.callCenterDao = callCenterDao;
    }

    @Override
    public void escalate(User escalator, Call call)  throws NoAttenderException {
        call.setStatus(CaseStatus.ESCALATED);
        CallProcessUnit callProcessUnit = CallProcessingUnitFactory.INSTANCE.getMgrProcessUnit();
        callProcessUnit.assign(call);
    }

    @Override
    public void process(User user, Call call) {
        CallAttender callAttender = new CallAttender();
        callAttender.setUser(user);
        callAttender.setCall(call);
        int totalCallDuration = 0;
        int maxProcessTime = getAttenderMaxProcessTimeInMinute();
        if(null != call.getAttenders()){
            for(CallAttender attndr : call.getAttenders())
                totalCallDuration = totalCallDuration+attndr.getDuration();
        }
        if (call.getDuration()-totalCallDuration <= maxProcessTime) {
            callAttender.setStatus(CaseStatus.RESOLVED);
            call.setStatus(CaseStatus.RESOLVED);
            callAttender.setDuration(call.getDuration()-totalCallDuration);
        } else {
            callAttender.setDuration(maxProcessTime);
            callAttender.setStatus(CaseStatus.ESCALATED);
            call.setStatus(CaseStatus.ESCALATED);
        }
        call.addAttenders(callAttender);
        callCenterDao.save(call);
        callCenterDao.save(callAttender);
        if(callAttender.getStatus().equals(CaseStatus.ESCALATED)) {
            try{
                escalate(user, call);
            }catch (NoAttenderException nae){
                System.out.println("Call can not be escalated. "+ nae.getMessage());
            }
        }
    }

    @Override
    public void handleOverFlow(User user, Call call) {
        System.out.println("User "+ user.getId()+" limit exceeded. Discarding call "+ call.getId());
        escalate(user, call);
    }

    @Override
    public int getAttenderMaxProcessTimeInMinute() {
        return Constant.LEVEL_2_MAX_PROCESS_TIME;
    }
}
