package com.romantulchak.virtualuniversity.exception;

public class SubjectNotFoundException extends RuntimeException{
    public SubjectNotFoundException(long id){
        super(String.format("Subject id: %d not found exception", id));
    }
}
