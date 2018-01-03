package com.callcenter.intrf.dto;

import org.json.JSONObject;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 9:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserPerformance {

    private String id;
    private int timeTakenInMinutes;
    private int callAttended;
    private int resolved;
    private int escalated;
    private int unresolved;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTimeTakenInMinutes() {
        return timeTakenInMinutes;
    }

    public void setTimeTakenInMinutes(Integer timeTakenInMinutes) {
        this.timeTakenInMinutes = timeTakenInMinutes;
    }

    public Integer getCallAttended() {
        return callAttended;
    }

    public void setCallAttended(Integer callAttended) {
        this.callAttended = callAttended;
    }

    public Integer getResolved() {
        return resolved;
    }

    public void setResolved(Integer resolved) {
        this.resolved = resolved;
    }

    public Integer getEscalated() {
        return escalated;
    }

    public void setEscalated(Integer escalated) {
        this.escalated = escalated;
    }

    public Integer getUnresolved() {
        return unresolved;
    }

    public void setUnresolved(Integer unresolved) {
        this.unresolved = unresolved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPerformance that = (UserPerformance) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserPerformance{" +
                "id='" + id + '\'' +
                ", timeTakenInMinutes=" + timeTakenInMinutes +
                ", callAttended=" + callAttended +
                ", resolved=" + resolved +
                ", escalated=" + escalated +
                ", unresolved=" + unresolved +
                '}';
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("timeTakenInMinutes", timeTakenInMinutes);
        json.put("callAttended", callAttended);
        json.put("resolved", resolved);
        json.put("escalated", escalated);
        json.put("unresolved", unresolved);
        return json;
    }
}
