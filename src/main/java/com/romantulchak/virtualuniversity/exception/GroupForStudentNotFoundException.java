package com.romantulchak.virtualuniversity.exception;

public class GroupForStudentNotFoundException extends RuntimeException {
    public GroupForStudentNotFoundException(long id) {
        super(String.format("Group for Student with id %d not found", id));
    }
}
