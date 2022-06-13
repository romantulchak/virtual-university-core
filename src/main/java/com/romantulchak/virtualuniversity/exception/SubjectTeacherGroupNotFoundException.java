package com.romantulchak.virtualuniversity.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class SubjectTeacherGroupNotFoundException extends RuntimeException {

    public SubjectTeacherGroupNotFoundException(MessageSource messageSource){
        super(messageSource.getMessage("subject.teacher.group.not.found", null, LocaleContextHolder.getLocale()));
    }
}
