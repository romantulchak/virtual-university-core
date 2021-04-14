package com.romantulchak.virtualuniversity.exception;

public class LessonNotFoundException extends RuntimeException {
    public LessonNotFoundException(long lessonId) {
        super(String.format("Lesson with id %d not found", lessonId));
    }
}
