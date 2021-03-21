package com.romantulchak.virtualuniversity.exception;

public class GroupWithNameAlreadyExistsException extends RuntimeException {
    public GroupWithNameAlreadyExistsException(String name) {
        super(String.format("Group with name %s already exists", name));
    }
}
