package com.romantulchak.virtualuniversity.exception;

public class LessonRequestNotFound extends RuntimeException {

    public LessonRequestNotFound(){
        super("Lesson request not found");
    }

}
