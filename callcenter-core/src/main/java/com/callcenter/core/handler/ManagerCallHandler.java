package com.callcenter.core.handler;

import com.callcenter.core.CallHandler;
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
public class ManagerCallHandler implements CallHandler {
    private CallCenterDao callCenterDao;

    public ManagerCallHandler(CallCenterDao callCenterDao) {
        this.callCenterDao = callCenterDao;
    }

    @Override
    public void escalate(User escalator, Call call) throws NoAttenderException {
        call.setStatus(CaseStatus.UNRESOLVED);
        callCenterDao.save(call);
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
        } else {
            callAttender.setStatus(CaseStatus.UNRESOLVED);
            call.setStatus(CaseStatus.UNRESOLVED);
        }
        callAttender.setDuration(call.getDuration()-totalCallDuration);
        call.addAttenders(callAttender);
        callCenterDao.save(call);
        callCenterDao.save(callAttender);
        /*if(callAttender.getStatus().equals(CaseStatus.UNRESOLVED))
            escalate(user, call);*/
    }

    @Override
    public void handleOverFlow(User user, Call call) {
        System.out.println("User "+ user.getId()+" limit exceeded. Discarding call "+ call.getId());
        escalate(user, call);
    }

    @Override
    public int getAttenderMaxProcessTimeInMinute() {
        return Constant.LEVEL_3_MAX_PROCESS_TIME;
    }


}
