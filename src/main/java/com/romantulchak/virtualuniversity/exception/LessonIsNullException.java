package com.romantulchak.virtualuniversity.exception;

public class LessonIsNullException extends RuntimeException {
    public LessonIsNullException(){
        super("Lesson cannot be null");
    }
}
