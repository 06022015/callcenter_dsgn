package com.callcenter.intrf.repository;

import com.callcenter.intrf.dto.UserPerformance;
import com.callcenter.model.Call;
import com.callcenter.model.CallAttender;
import com.callcenter.model.User;
import com.callcenter.model.type.CaseStatus;
import com.callcenter.model.type.RoleEnum;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 8:39 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CallCenterDao {

    void save(User user);

    void save(Call call);

    void save(CallAttender attender);

    User getUserById(String userId);

    int getTotalNumberOfCall();

    int getCallCountByStatus(CaseStatus status);

    List<UserPerformance> getPerformanceByRole(RoleEnum role);

}
