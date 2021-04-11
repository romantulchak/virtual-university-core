package com.romantulchak.virtualuniversity.exception;

import java.time.LocalDateTime;

public class LessonAtThatTimeAlreadyExistsException extends RuntimeException {
    public LessonAtThatTimeAlreadyExistsException(LocalDateTime dateStart, LocalDateTime dateEnd) {
        super(String.format("Lesson Time Start From %s to Time End %s already exists", dateStart, dateEnd));
    }
}
