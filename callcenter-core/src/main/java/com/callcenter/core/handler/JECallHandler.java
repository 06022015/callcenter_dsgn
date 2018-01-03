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
 * Time: 9:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class JECallHandler implements CallHandler {

    private CallCenterDao callCenterDao;

    public JECallHandler(CallCenterDao callCenterDao) {
        this.callCenterDao = callCenterDao;
    }

    @Override
    public void escalate(User escalator, Call call) throws NoAttenderException{
        call.setStatus(CaseStatus.ESCALATED);
        CallProcessUnit callProcessUnit = CallProcessingUnitFactory.INSTANCE.getSeProcessUnit();
        callProcessUnit.assign(call);
    }

    @Override
    public void handleOverFlow(User user, Call call) {
        System.out.println("User "+ user.getId()+" limit exceeded. Discarding call "+ call.getId());
        escalate(user, call);
    }

    @Override
    public int getAttenderMaxProcessTimeInMinute() {
        return Constant.LEVEL_1_MAX_PROCESS_TIME;
    }

    @Override
    public void process(User user, Call call) {
        CallAttender callAttender = new CallAttender();
        callAttender.setUser(user);
        callAttender.setCall(call);
        int maxProcessTime = getAttenderMaxProcessTimeInMinute();
        if (call.getDuration() <= maxProcessTime) {
            callAttender.setStatus(CaseStatus.RESOLVED);
            callAttender.setDuration(call.getDuration());
            call.setStatus(CaseStatus.RESOLVED);
        } else {
            callAttender.setStatus(CaseStatus.ESCALATED);
            call.setStatus(CaseStatus.ESCALATED);
            callAttender.setDuration(maxProcessTime);
        }
        call.addAttenders(callAttender);
        callCenterDao.save(call);
        callCenterDao.save(callAttender);
        if(callAttender.getStatus().equals(CaseStatus.ESCALATED))
            try{
                escalate(user, call);
            }catch (NoAttenderException nae){
                System.out.println("Call can not be escalated. "+ nae.getMessage());
            }
    }
}
