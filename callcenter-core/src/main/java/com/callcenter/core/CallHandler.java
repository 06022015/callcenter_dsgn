package com.callcenter.core;

import com.callcenter.intrf.exception.NoAttenderException;
import com.callcenter.model.Call;
import com.callcenter.model.User;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 8:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CallHandler {

    void process(User user, Call call);

    void escalate(User escalator, Call call) throws NoAttenderException;

    void handleOverFlow(User user, Call call);

    int getAttenderMaxProcessTimeInMinute();

}
