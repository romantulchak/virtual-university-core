package com.romantulchak.virtualuniversity.exception;

public class SubjectIsNullException extends RuntimeException {
    public SubjectIsNullException(){
        super("Subject cannot be null");
    }
}
