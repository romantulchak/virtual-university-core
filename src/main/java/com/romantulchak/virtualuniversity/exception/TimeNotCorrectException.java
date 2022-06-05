package com.romantulchak.virtualuniversity.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class TimeNotCorrectException extends RuntimeException {
    public TimeNotCorrectException(MessageSource messageSource){
        super(messageSource.getMessage("lesson.time.is.incorrect", null, LocaleContextHolder.getLocale()));
    }
}
