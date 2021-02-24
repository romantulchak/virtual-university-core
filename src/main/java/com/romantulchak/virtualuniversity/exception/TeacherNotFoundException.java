package com.romantulchak.virtualuniversity.exception;

public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException(long userId) {
        super(String.format("Teacher with id %d not found", userId));
    }
}
