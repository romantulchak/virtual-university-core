package com.romantulchak.virtualuniversity.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDateTime;

public class LessonAtThatTimeAlreadyExistsException extends RuntimeException {
    public LessonAtThatTimeAlreadyExistsException(LocalDateTime dateStart, LocalDateTime dateEnd, MessageSource messageSource) {
        super(messageSource.getMessage("lesson.in.time.already.exists", new Object[]{dateStart, dateEnd}, LocaleContextHolder.getLocale()));
    }
}
