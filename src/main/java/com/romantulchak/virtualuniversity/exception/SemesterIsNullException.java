package com.romantulchak.virtualuniversity.exception;

public class SemesterIsNullException extends RuntimeException {
    public SemesterIsNullException(){
        super("Semester cannot be null");
    }
}
