package com.callcenter.core;


import com.callcenter.core.unit.CallProcessingUnitFactory;
import com.callcenter.intrf.core.AttenderMaxLimit;
import com.callcenter.intrf.core.CallCenterApi;
import com.callcenter.intrf.dto.CallCenterReport;
import com.callcenter.intrf.dto.Performance;
import com.callcenter.intrf.dto.UserPerformance;
import com.callcenter.intrf.exception.NoRecordFoundException;
import com.callcenter.intrf.repository.CallCenterDao;
import com.callcenter.model.Call;
import com.callcenter.model.User;
import com.callcenter.model.type.CaseStatus;
import com.callcenter.model.type.RoleEnum;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 10:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallCenterImpl implements  CallCenterApi, AttenderMaxLimit {

    private CallCenterDao callCenterDao;
    private int totalNumberOfCall = -1;

    public CallCenterImpl(CallCenterDao callCenterDao) {
        this.callCenterDao = callCenterDao;
    }

    @Override
    public void setTotalNumberOfCall(int numberOfCall) {
        this.totalNumberOfCall = numberOfCall;
    }

    private int calculateAttenderMaxLimit(int totalNumberOfCall){
        int jeCount = CallProcessingUnitFactory.INSTANCE.getJeProcessUnit().getAttendersCount();
        int seCount = CallProcessingUnitFactory.INSTANCE.getSeProcessUnit().getAttendersCount();
        return totalNumberOfCall/(jeCount+seCount);
    }

    @Override
    public int getAttenderMaxLimit(RoleEnum role) {
        int numOfCall = -1;
        if(totalNumberOfCall>0 && !role.equals(RoleEnum.MANGER)){
           numOfCall =  calculateAttenderMaxLimit(this.totalNumberOfCall);
        }
        return numOfCall;
    }

    @Override
    public void addAttender(User user) {
        callCenterDao.save(user);
        CallProcessUnit callProcessUnit = CallProcessingUnitFactory.INSTANCE.getProcessingUnit(user.getRole());
        callProcessUnit.addAttender(user);

    }

    @Override
    public void handleCall(String userId, int[] calls, RoleEnum role) {
        User user = callCenterDao.getUserById(userId);
        if(null == user){
            throw new NoRecordFoundException("User not found for id:- "+ userId);
        }
        CallProcessUnit callProcessUnit = CallProcessingUnitFactory.INSTANCE.getProcessingUnit(role);
        for(int c : calls){
            Call call = new Call();
            call.setDuration(c);
            callCenterDao.save(call);
            callProcessUnit.assign(user, call);
        }
    }

    @Override
    public CallCenterReport getReport() {
        CallCenterReport report = new CallCenterReport();
        report.setNumberOfCalls(callCenterDao.getTotalNumberOfCall());
        report.setResolved(callCenterDao.getCallCountByStatus(CaseStatus.RESOLVED));
        report.setUnresolved(callCenterDao.getCallCountByStatus(CaseStatus.UNRESOLVED));
        Performance performance = new Performance();
        List<UserPerformance> juniorExecutives = callCenterDao.getPerformanceByRole(RoleEnum.JUNIOR_EXECUTIVE);
        List<UserPerformance> seniorExecutives = callCenterDao.getPerformanceByRole(RoleEnum.SENIOR_EXECUTIVE);
        List<UserPerformance> managers = callCenterDao.getPerformanceByRole(RoleEnum.MANGER);
        int totalTimeTakenInMinute = 0;
        //int totalResolve = 0;
        //int totalUnResolve = 0;
        for(UserPerformance userPerformance : juniorExecutives){
            //totalResolve= totalResolve+(null != userPerformance.getResolved()?userPerformance.getResolved():0);
            totalTimeTakenInMinute = totalTimeTakenInMinute+userPerformance.getTimeTakenInMinutes();
        }
        for(UserPerformance userPerformance : seniorExecutives){
            //totalResolve= totalResolve+(null != userPerformance.getResolved()?userPerformance.getResolved():0);
            totalTimeTakenInMinute = totalTimeTakenInMinute+userPerformance.getTimeTakenInMinutes();
        }
        for(UserPerformance userPerformance : managers){
            //totalResolve= totalResolve+(null != userPerformance.getResolved()?userPerformance.getResolved():0);
            totalTimeTakenInMinute = totalTimeTakenInMinute+userPerformance.getTimeTakenInMinutes();
            //totalUnResolve = totalUnResolve + (null != userPerformance.getUnresolved()? userPerformance.getUnresolved():0);
        }
        if(managers.size()>0)
            performance.setManager(managers.get(0));
        performance.setJuniorExecutive(juniorExecutives);
        performance.setSeniorExecutive(seniorExecutives);
        report.setPerformance(performance);
        report.setTotalTimeTakenInMinutes(totalTimeTakenInMinute);
        //report.setResolved(totalResolve);
        //report.setUnresolved(totalUnResolve);
        return report;
    }




}
