package com.romantulchak.virtualuniversity.exception;

public class CourseIsNullException extends RuntimeException {
    public CourseIsNullException(){
        super("Course cannot be null");
    }
}
