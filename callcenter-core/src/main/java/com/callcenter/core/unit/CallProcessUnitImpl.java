package com.callcenter.core.unit;

import com.callcenter.core.CallHandler;
import com.callcenter.core.CallProcessUnit;
import com.callcenter.intrf.core.AttenderMaxLimit;
import com.callcenter.intrf.exception.DuplicateObjectException;
import com.callcenter.intrf.exception.LimitExceededException;
import com.callcenter.intrf.exception.NoAttenderException;
import com.callcenter.model.Call;
import com.callcenter.model.User;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 8:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallProcessUnitImpl implements CallProcessUnit {

    private Set<AttenderCallQueue> attenders;
    private CallHandler callHandler;
    private AttenderMaxLimit attenderMaxLimit;

    public CallProcessUnitImpl(CallHandler callHandler) {
        this.callHandler = callHandler;
        init();
    }

    public CallProcessUnitImpl(CallHandler callHandler, AttenderMaxLimit attenderMaxLimit) {
        this.callHandler = callHandler;
        this.attenderMaxLimit = attenderMaxLimit;
        init();
    }

    private void init(){
       this.attenders = Collections.synchronizedSet(new HashSet<AttenderCallQueue>());
    }

    public AttenderMaxLimit getAttenderMaxLimit() {
        return attenderMaxLimit;
    }

    public void setAttenderMaxLimit(AttenderMaxLimit attenderMaxLimit) {
        this.attenderMaxLimit = attenderMaxLimit;
    }

    public void addAttender(User user){
        AttenderCallQueue attender  = new AttenderCallQueue(callHandler, user, attenderMaxLimit);
        addAttender(attender);
    }

    public Collection<AttenderCallQueue> getAttenders() {
        return attenders;
    }

    public int getAttendersCount(){
        return null != getAttenders()? getAttenders().size():0;
    }

    public void addAttender(AttenderCallQueue attender)throws DuplicateObjectException{
        if(!getAttenders().contains(attender)){
            this.getAttenders().add(attender);
        }else{
            throw new DuplicateObjectException(" User already available :- "+ attender.getUser().getId());
        }
    }

    public void assign(User user, Call call)throws LimitExceededException {
        AttenderCallQueue attenderCallQueue = null;
        for(AttenderCallQueue acq : this.attenders){
            if(acq.getUser().equals(user))
                attenderCallQueue = acq;
        }
        if(null == attenderCallQueue){
            attenderCallQueue = new AttenderCallQueue(callHandler, user, attenderMaxLimit);
            addAttender(attenderCallQueue);
            //throw new NoRecordFoundException("User not found for user id:- "+ user.getId());
        }
        attenderCallQueue.addCall(call);
    }

    public void assign(Call call) throws NoAttenderException, LimitExceededException {
        AttenderCallQueue attenderCallQueue = getLeastLoadedByWaitingTime();
        if(null != attenderCallQueue)
            attenderCallQueue.addCall(call);
        else{
            callHandler.escalate(null, call);
            //throw new NoAttenderException("No attender available to take call");
        }
    }

    private AttenderCallQueue getLeastLoadedByCount(){
        AttenderCallQueue attender = null;
         for(AttenderCallQueue attenderCallQueue : this.attenders){
             if(!attenderCallQueue.isOverFlow() && (null == attender || attenderCallQueue.getSize() < attender.getSize()))
                 attender = attenderCallQueue;
         }
        return attender;
    }

    private AttenderCallQueue getLeastLoadedByWaitingTime(){
        AttenderCallQueue attender = null;
        for(AttenderCallQueue attenderCallQueue : this.attenders){
            if(!attenderCallQueue.isOverFlow() && ( null == attender
                    || attenderCallQueue.getMaxWaitingTimeInMinute() < attender.getMaxWaitingTimeInMinute()))
                attender = attenderCallQueue;
        }
        return attender;
    }

    private void heapyfy(){
        //TODO: need to build min heap to get less loaded attender;
    }

}
