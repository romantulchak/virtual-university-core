package com.romantulchak.virtualuniversity.exception;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String name){
        super(String.format("Role with name %s not found", name));
    }
}
