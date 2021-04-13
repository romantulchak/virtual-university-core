package com.romantulchak.virtualuniversity.exception;

public class CannotConvertStringToLocalDate extends RuntimeException {
    public CannotConvertStringToLocalDate(String dateAsString) {
        super(String.format("Cannot convert String: %s to Local Date", dateAsString));
    }
}
