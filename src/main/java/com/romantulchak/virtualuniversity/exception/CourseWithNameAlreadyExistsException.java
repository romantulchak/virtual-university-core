package com.romantulchak.virtualuniversity.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class CourseWithNameAlreadyExistsException extends RuntimeException {
    public CourseWithNameAlreadyExistsException(String name, MessageSource messageSource) {
        super(messageSource.getMessage("course.with.name.already.exists", new Object[]{name}, LocaleContextHolder.getLocale()));
    }
}
