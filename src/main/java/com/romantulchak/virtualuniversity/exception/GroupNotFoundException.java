package com.romantulchak.virtualuniversity.exception;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(long groupId) {
        super(String.format("Group with id %d not found", groupId));
    }
    public GroupNotFoundException() {
        super("Group not found");
    }
}
