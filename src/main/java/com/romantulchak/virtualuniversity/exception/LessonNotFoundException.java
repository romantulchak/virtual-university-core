package com.romantulchak.virtualuniversity.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class LessonNotFoundException extends RuntimeException {

    public LessonNotFoundException(long lessonId, MessageSource messageSource) {
        super(messageSource.getMessage("lesson.with.id.not.found", new Object[]{lessonId}, LocaleContextHolder.getLocale()));
    }

    public LessonNotFoundException(MessageSource messageSource) {
        super(messageSource.getMessage("lesson.with.not.found", null, LocaleContextHolder.getLocale()));
    }
}
