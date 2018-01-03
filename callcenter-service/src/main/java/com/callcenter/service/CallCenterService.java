package com.callcenter.service;

import com.callcenter.core.CallCenterImpl;
import com.callcenter.core.handler.CallHandlerFactory;
import com.callcenter.core.unit.CallProcessingUnitFactory;
import com.callcenter.intrf.core.CallCenterApi;
import com.callcenter.intrf.dto.CallCenterReport;
import com.callcenter.intrf.exception.LimitExceededException;
import com.callcenter.intrf.exception.NoAttenderException;
import com.callcenter.intrf.exception.NoRecordFoundException;
import com.callcenter.intrf.repository.CallCenterDao;
import com.callcenter.model.User;
import com.callcenter.model.type.RoleEnum;
import com.callcenter.repository.CallCenterDaoImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 8:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallCenterService implements Constant{

    private CallCenterApi callCenter;

    public CallCenterService(CallCenterApi callCenter) {
        this.callCenter = callCenter;
    }

    public void addUser(String prefix, int count, RoleEnum role){
        for(int i=1;i<= count;i++){
            User user = new User();
            if(role.equals(RoleEnum.MANGER))
                user.setId(prefix);
            else
                user.setId(prefix+i);
            user.setRole(role);
            callCenter.addAttender(user);
        }
    }
    
    public int handleRequest(JSONObject request){
        int responseCode = 200;
        try{
            callCenter.setTotalNumberOfCall(request.getInt(NUMBER_OF_CALLS));
            if(request.has(MGR_USER)){
                addUser(MGR_USER, 1,RoleEnum.MANGER);
                String mgrCalls = request.getString(MGR_USER);
                int calls[] = getCalls(mgrCalls);
                handleUserCall(MGR_USER, calls, RoleEnum.MANGER);
            }
            if(request.has(SE_USER)){
                JSONArray ses = request.getJSONArray(SE_USER);
                addUser(SE_USER, ses.length(),RoleEnum.SENIOR_EXECUTIVE);
                for(int i=0; i< ses.length();i++){
                    int calls[] = getCalls(ses.getString(i));
                    handleUserCall(SE_USER+(i+1), calls, RoleEnum.SENIOR_EXECUTIVE);
                }
            }
            if(request.has(JE_USER)){
                JSONArray jes = request.getJSONArray(JE_USER);
                addUser(JE_USER, jes.length(),RoleEnum.JUNIOR_EXECUTIVE);
                for(int i=0; i< jes.length();i++){
                    int calls[] = getCalls(jes.getString(i));
                    handleUserCall(JE_USER+(i+1), calls, RoleEnum.JUNIOR_EXECUTIVE);
                }
            }
        }catch (NumberFormatException ne){
            System.out.println("Invalid input:-" + ne.getMessage());
            responseCode = 400;
        }
        return responseCode;
    }
    
    private int[] getCalls(String callsAsString){
        String calls[] = callsAsString.split(",");
        int callsAsInt[] = new int[calls.length];
        for(int j=0;j< calls.length;j++){
            callsAsInt[j] = Integer.parseInt(calls[j].trim());
        }
        return callsAsInt;
    }
    
    public int handleUserCall(String user, int calls[], RoleEnum role){
        int responseCode = 200;
        try{
            callCenter.handleCall(user, calls, role);
        }catch (NoRecordFoundException nfe){
            System.out.println(nfe.getMessage());
            responseCode = nfe.getCode();
        }catch (NoAttenderException nae){
            System.out.println(nae.getMessage());
            responseCode = nae.getCode();
        }catch (LimitExceededException lee){
            System.out.println(lee.getMessage());
            responseCode = lee.getCode();
        }
        return responseCode;
    }

    public CallCenterReport getPerformance(){
        return callCenter.getReport();
    }
    
    
    public static void main(String args[]){
        CallCenterService callCenterService = get();
        //String input = "{\"number_of_calls\": \"20\",\"je\":[[9,12,15,13,6,3,3,3],[10,9,11,1,4,1,1,4],[1,4,12,8,4,5,9,2],[9,6,6,3,1,12,15,15],[6,14,6,13,3,14,13,2]],\"se\":[[6,5,4,8,10,9,2,10],[2,13,2,8,6,15,10,14],[11,14,2,13,13,15,4,13]],\"mgr\":[9,12,20,13,20,3,3,3,9,2,7,1,7,11,10,5,4,14,14,12]}";
       // String input = "{\"number_of_calls\": \"30\", \"je\":[\"5,7,6,4,6\",\"5,8,7,5,10\",\"7,5,6,14,6\",\"10,4,9,5,12\",\"6,10,11,4,6\" ],\"se\":[\"6,14,12,10,5\",\"18,8,6,4,12\",\"8,6,13,7,1\"],\"mgr\": {\"20,12,25,13,20,3,3,3,9,2,7,1,7,11,10\"}";
        String input = "{\n" +
                "  \"number_of_calls\": \"30\",\n" +
                "  \"je\": [\n" +
                "    \"5,7,6,4,6\",\n" +
                "    \"5,8,7,5,10\",\n" +
                "    \"7,5,6,14,6\",\n" +
                "    \"10,4,9,5,12\",\n" +
                "    \"6,10,11,4,6\"\n" +
                "  ],\n" +
                "  \"se\": [\n" +
                "    \"6,14,12,10,5\",\n" +
                "    \"18,8,6,4,12\",\n" +
                "    \"8,6,13,7,1\"\n" +
                "  ],\n" +
                "  \"mgr\": \"20,12,25,13,20,3,3,3,9,2,7,1,7,11,10\"\n" +
                "       }\n" +
                "";
        try{
            JSONObject request = new JSONObject(input);
            callCenterService.handleRequest(request);
        }catch (JSONException ne) {
            ne.printStackTrace();
            System.out.println("invalid json format:- " + ne.getMessage());
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(callCenterService.getPerformance().toJSON());
    }

    public static CallCenterService get(){
        CallCenterDao callCenterDao = new CallCenterDaoImpl();
        CallHandlerFactory.create(callCenterDao);
        CallProcessingUnitFactory.create();
        CallCenterApi callCenterApi = new CallCenterImpl(callCenterDao);
        CallProcessingUnitFactory.INSTANCE.setAttenderMaxLimit(callCenterApi);
        return new CallCenterService(callCenterApi);
    }

}
