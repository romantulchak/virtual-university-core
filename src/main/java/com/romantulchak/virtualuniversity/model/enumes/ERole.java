package com.romantulchak.virtualuniversity.model.enumes;

public enum ERole {
    ROLE_STUDENT(1), ROLE_TEACHER(2), ROLE_ADMIN(3), ROLE_MANAGER(4);

    private long id;
    ERole(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
