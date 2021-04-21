package com.romantulchak.virtualuniversity.exception;

public class SubjectAlreadyExistsException extends RuntimeException {
    public SubjectAlreadyExistsException(String name, String type) {
        super(String.format("Subject %s %s already exists", name, type));
    }
}
