package com.callcenter.core;

import com.callcenter.core.unit.AttenderCallQueue;
import com.callcenter.intrf.exception.LimitExceededException;
import com.callcenter.intrf.exception.NoAttenderException;
import com.callcenter.model.Call;
import com.callcenter.model.User;

import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 12:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CallProcessUnit {

    Collection<AttenderCallQueue> getAttenders();

    int getAttendersCount();

    void addAttender(User user);

    void addAttender(AttenderCallQueue attender);

    void assign(User user, Call call) throws LimitExceededException;

    void assign(Call call) throws NoAttenderException, LimitExceededException;

}
