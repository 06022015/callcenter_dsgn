package com.callcenter.repository;

import com.callcenter.model.Call;
import com.callcenter.model.CallAttender;
import com.callcenter.model.User;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 11:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class CacheDB {

    public static Set<User> users = Collections.synchronizedSet(new HashSet<User>());
    public static Set<Call> calls = Collections.synchronizedSet(new HashSet<Call>());
    public static Set<CallAttender> callAttenders = Collections.synchronizedSet(new HashSet<CallAttender>());

}
