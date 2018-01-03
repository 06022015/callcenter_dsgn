package com.callcenter.intrf.core;

import com.callcenter.intrf.dto.CallCenterReport;
import com.callcenter.model.User;
import com.callcenter.model.type.RoleEnum;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 10:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CallCenterApi extends AttenderMaxLimit{


    void setTotalNumberOfCall(int numberOfCall);
    
    void handleCall(String userId, int call[], RoleEnum role);

    CallCenterReport getReport();

    void addAttender(User user);


}
