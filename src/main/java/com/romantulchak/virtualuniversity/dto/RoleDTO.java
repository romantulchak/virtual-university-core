package com.romantulchak.virtualuniversity.dto;

import com.romantulchak.virtualuniversity.model.Role;
import com.romantulchak.virtualuniversity.model.enumes.ERole;

public class RoleDTO {
    private long id;

    private ERole name;

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

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}
