package com.romantulchak.virtualuniversity.exception;

public class SemesterNotFoundException extends RuntimeException{
    public SemesterNotFoundException(){
        super("Semester not found");
    }
}
