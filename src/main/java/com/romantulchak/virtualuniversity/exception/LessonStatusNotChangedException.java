package com.romantulchak.virtualuniversity.exception;

public class LessonStatusNotChangedException extends RuntimeException {
    public LessonStatusNotChangedException(){
        super("Lesson status not changed!");
    }
}
