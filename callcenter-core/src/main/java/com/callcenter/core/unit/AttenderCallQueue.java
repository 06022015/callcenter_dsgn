package com.callcenter.core.unit;

import com.callcenter.core.CallHandler;
import com.callcenter.intrf.core.AttenderMaxLimit;
import com.callcenter.intrf.exception.CallCenterException;
import com.callcenter.intrf.exception.LimitExceededException;
import com.callcenter.model.Call;
import com.callcenter.model.User;

import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 11:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class AttenderCallQueue {

    private User user;
    private Queue<Call> queue;
    private boolean processing = false;
    private CallHandler callHandler;
    private Integer count = 0;
    private AttenderMaxLimit attenderMaxLimit;
    private ExecutorService executorService;
    private Integer maxWaitingTimeInMinute;

    public AttenderCallQueue(CallHandler callHandler, User user) {
        this.callHandler = callHandler;
        this.user = user;
        init();
    }

    public AttenderCallQueue(CallHandler callHandler, User user, AttenderMaxLimit attenderMaxLimit) {
        this.callHandler = callHandler;
        this.user = user;
        this.attenderMaxLimit = attenderMaxLimit;
        init();
    }

    private void init(){
        this.executorService = Executors.newFixedThreadPool(1);
        this.queue = new LinkedBlockingQueue<Call>();
        this.maxWaitingTimeInMinute = 0;
        //setQueue(new LinkedList<Call>());
    }

    public User getUser() {
        return user;
    }

    private Queue<Call> getQueue() {
        return queue;
    }

    private void setQueue(Queue<Call> queue) {
        this.queue = queue;
    }

    public Integer getSize() {
        return queue.size();
    }

    public Integer getMaxWaitingTimeInMinute() {
        return maxWaitingTimeInMinute;
    }

    public boolean isOverFlow(){
        int maxLimit = null != attenderMaxLimit?attenderMaxLimit.getAttenderMaxLimit(user.getRole()):-1;
        return maxLimit >0 && count >= maxLimit;
    }

    public void addCall(Call call)throws LimitExceededException{
        if(isOverFlow()){
            callHandler.handleOverFlow(user, call);
            //throw new LimitExceededException("Attender limit exceeded");
        }else{
            getQueue().add(call);
            count++;
            increaseWaitTime(call.getDuration());
            //processAsync();
            process();
        }
    }

    private synchronized void increaseWaitTime(int callDuration) {
        if (callDuration <= callHandler.getAttenderMaxProcessTimeInMinute())
            maxWaitingTimeInMinute = maxWaitingTimeInMinute + callDuration;
        else
            maxWaitingTimeInMinute = maxWaitingTimeInMinute + callHandler.getAttenderMaxProcessTimeInMinute();

    }

    private synchronized void decreaseWaitTime(int callDuration){
        if (callDuration <= callHandler.getAttenderMaxProcessTimeInMinute())
            maxWaitingTimeInMinute = maxWaitingTimeInMinute - callDuration;
        else
            maxWaitingTimeInMinute = maxWaitingTimeInMinute - callHandler.getAttenderMaxProcessTimeInMinute();
    }

    private void processAsync(){
        if(this.processing)
            return;
        this.processing = true;
        executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                try{
                    process();
                }finally {
                    processing = false;
                }
                return null;
            }
        });
    }

    private void process(){
        while(!queue.isEmpty()){
            Call call = null;
            try{
                call = queue.poll();
                callHandler.process(user, call);
            }catch (CallCenterException ce){
                System.out.print(ce.getMessage());
            }finally {
                if(null != call)
                    decreaseWaitTime(call.getDuration());
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttenderCallQueue that = (AttenderCallQueue) o;

        if (!user.equals(that.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }
}
