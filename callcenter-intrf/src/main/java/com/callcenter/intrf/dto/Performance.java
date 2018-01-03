package com.callcenter.intrf.dto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 10:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class Performance {

    private UserPerformance manager;
    private List<UserPerformance> juniorExecutive;
    private List<UserPerformance> seniorExecutive;

    public UserPerformance getManager() {
        return manager;
    }

    public void setManager(UserPerformance manager) {
        this.manager = manager;
    }

    public List<UserPerformance> getJuniorExecutive() {
        return juniorExecutive;
    }

    public void setJuniorExecutive(List<UserPerformance> juniorExecutive) {
        this.juniorExecutive = juniorExecutive;
    }

    public List<UserPerformance> getSeniorExecutive() {
        return seniorExecutive;
    }

    public void setSeniorExecutive(List<UserPerformance> seniorExecutive) {
        this.seniorExecutive = seniorExecutive;
    }

    @Override
    public String toString() {
        return "Performance{" +
                "manager=" + manager +
                ", juniorExecutive=" + juniorExecutive +
                ", seniorExecutive=" + seniorExecutive +
                '}';
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        if(null != manager)
            json.put("manager", manager.toJSON());
        if(null != juniorExecutive){
            JSONArray jeArray = new JSONArray();
            for(UserPerformance je : juniorExecutive){
                jeArray.put(je.toJSON());
            }
            json.put("junior-executives", jeArray);
        }
        if(null != seniorExecutive){
            JSONArray seArray = new JSONArray();
            for(UserPerformance se : seniorExecutive){
                seArray.put(se.toJSON());
            }
            json.put("senior-executives", seArray);
        }
        return json;
    }
}
