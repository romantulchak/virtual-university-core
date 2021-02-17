package com.romantulchak.virtualuniversity.payload.response;

import com.romantulchak.virtualuniversity.model.Role;

import java.util.Set;

public class JwtResponse {
    private String jwt;
    private long id;
    private String login;
    private Set<Role> roles;


    public JwtResponse(String jwt, long id, String login, Set<Role> roles) {
        this.jwt = jwt;
        this.id = id;
        this.login = login;
        this.roles = roles;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
