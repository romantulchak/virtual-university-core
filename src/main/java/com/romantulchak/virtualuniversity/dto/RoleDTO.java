package com.romantulchak.virtualuniversity.dto;

import com.romantulchak.virtualuniversity.model.Role;
import com.romantulchak.virtualuniversity.model.enumes.RoleType;

public class RoleDTO {
    private long id;

    private RoleType name;

    public RoleDTO(){

    }
    public RoleDTO(Role role){
        this.id = role.getId();
        this.name = role.getName();
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoleType getName() {
        return name;
    }

    public void setName(RoleType name) {
        this.name = name;
    }
}
