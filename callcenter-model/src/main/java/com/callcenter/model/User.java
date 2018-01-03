package com.callcenter.model;


import com.callcenter.model.type.RoleEnum;

/**
 * Created by IntelliJ IDEA.
 * User: ashqures
 * Date: 11/6/17
 * Time: 8:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class User {

    private String id;
    private RoleEnum role;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
