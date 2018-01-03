package com.callcenter.intrf.dto;

import org.json.JSONObject;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 10:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallCenterReport {

    private Integer numberOfCalls;
    private Integer resolved;
    private Integer unresolved;
    private Integer totalTimeTakenInMinutes;
    private Performance performance;

    public Integer getNumberOfCalls() {
        return numberOfCalls;
    }

    public void setNumberOfCalls(Integer numberOfCalls) {
        this.numberOfCalls = numberOfCalls;
    }

    public Integer getResolved() {
        return resolved;
    }

    public void setResolved(Integer resolved) {
        this.resolved = resolved;
    }

    public Integer getUnresolved() {
        return unresolved;
    }

    public void setUnresolved(Integer unresolved) {
        this.unresolved = unresolved;
    }

    public Integer getTotalTimeTakenInMinutes() {
        return totalTimeTakenInMinutes;
    }

    public void setTotalTimeTakenInMinutes(Integer totalTimeTakenInMinutes) {
        this.totalTimeTakenInMinutes = totalTimeTakenInMinutes;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    @Override
    public String toString() {
        return "CallCenterReport{" +
                "numberOfCalls=" + numberOfCalls +
                ", resolved=" + resolved +
                ", unresolved=" + unresolved +
                ", totalTimeTakenInMinutes=" + totalTimeTakenInMinutes +
                ", performance=" + performance +
                '}';
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("number_of_calls",numberOfCalls);
        json.put("resolved",resolved);
        json.put("unresolved",unresolved);
        json.put("timeTakenInMinutes",totalTimeTakenInMinutes);
        if(null != performance){
            json.put("performance", performance.toJSON());
        }
        return json;
    }
}
