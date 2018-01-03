package com.callcenter.repository;

import com.callcenter.intrf.dto.UserPerformance;
import com.callcenter.intrf.repository.CallCenterDao;
import com.callcenter.model.Call;
import com.callcenter.model.CallAttender;
import com.callcenter.model.User;
import com.callcenter.model.type.CaseStatus;
import com.callcenter.model.type.RoleEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 8:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallCenterDaoImpl implements CallCenterDao {

    //public static CallCenterDao INSTANCE = null;

    public CallCenterDaoImpl() {
    }

    /*public static void create(){
        if(null == INSTANCE){
            synchronized (CallCenterDaoImpl.class){
                if(null == INSTANCE)
                    INSTANCE = new CallCenterDaoImpl();
            }
        }
    }*/

    @Override
    public void save(User user) {
        if(null == user.getId()){
            user.setId(CacheDB.users.size()+1+"");
        }else if(CacheDB.users.contains(user)){
            CacheDB.users.remove(user);
        }
        CacheDB.users.add(user);
    }

    @Override
    public void save(Call call) {
        if(null ==call.getId()){
            call.setId(CacheDB.calls.size()+1);
        }else if(CacheDB.calls.contains(call)){
                CacheDB.calls.remove(call);
        }
        CacheDB.calls.add(call);
    }

    @Override
    public void save(CallAttender attender) {
        CacheDB.callAttenders.add(attender);
    }

    public User getUserById(String userId){
        for(User user : CacheDB.users){
            if(user.getId().equals(userId)){
                return user;
            }
        }
        return null;
    }

    @Override
    public int getTotalNumberOfCall() {
        return CacheDB.calls.size();
    }

    public int getCallCountByStatus(CaseStatus status) {
        int count = 0;
        for (Call call : CacheDB.calls) {
            if (call.getStatus().equals(status))
                count = count + 1;
        }
        return count;
    }

    @Override
    public List<UserPerformance> getPerformanceByRole(RoleEnum role) {
        Map<User, UserPerformance> performanceMap = new HashMap<User, UserPerformance>();
        for(CallAttender attender : CacheDB.callAttenders){
            if(attender.getUser().getRole().equals(role)){
                UserPerformance userPerformance = performanceMap.get(attender.getUser());
                if(null == userPerformance)
                    userPerformance = new UserPerformance();
                userPerformance.setId(attender.getUser().getId());
                userPerformance.setCallAttended(userPerformance.getCallAttended() + 1);
                userPerformance.setTimeTakenInMinutes(userPerformance.getTimeTakenInMinutes()+attender.getDuration());
                if(attender.getStatus().equals(CaseStatus.RESOLVED))
                    userPerformance.setResolved(userPerformance.getResolved()+1);
                else if(attender.getStatus().equals(CaseStatus.UNRESOLVED))
                    userPerformance.setUnresolved(userPerformance.getUnresolved()+1);
                else if(attender.getStatus().equals(CaseStatus.ESCALATED))
                    userPerformance.setEscalated(userPerformance.getEscalated()+1);
                performanceMap.put(attender.getUser(), userPerformance);
            }
        }
        return new ArrayList<UserPerformance>(performanceMap.values());
    }

}
