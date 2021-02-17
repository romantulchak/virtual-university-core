package com.romantulchak.virtualuniversity.model.enumes;

public enum ERole {
    STUDENT(1), TEACHER(2), ADMIN(3), MANAGER(4);

    private long id;
    ERole(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
