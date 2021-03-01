package com.romantulchak.virtualuniversity.exception;

public class SemesterAlreadyExistsException extends RuntimeException{
    public SemesterAlreadyExistsException(String name){
        super(String.format("Semester with name %s already exists", name));
    }
}
