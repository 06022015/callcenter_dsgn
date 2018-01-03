package com.callcenter.model;


import com.callcenter.model.type.CaseStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 8:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Call {

    private Integer id;
    private int duration;
    private List<CallAttender> attenders;
    private CaseStatus status = CaseStatus.ASSIGNED;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<CallAttender> getAttenders() {
        return attenders;
    }

    public void setAttenders(List<CallAttender> attenders) {
        this.attenders = attenders;
    }

    public void addAttenders(CallAttender callAttender){
        if(null == getAttenders())
            setAttenders(new ArrayList<CallAttender>());
        getAttenders().add(callAttender);
    }

    public CaseStatus getStatus() {
        return status;
    }

    public void setStatus(CaseStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Call call = (Call) o;

        if (!id.equals(call.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
